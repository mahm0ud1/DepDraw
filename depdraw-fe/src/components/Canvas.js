import React, { useState, useRef, useCallback, useEffect } from 'react';

import ReactFlow, {
  MiniMap,
  Background,
  BackgroundVariant,
  addEdge,
  useNodesState,
  useEdgesState,
  Controls,
} from 'reactflow';
import 'reactflow/dist/style.css';
// import CustomNode from './CustomNode';
import ResizableNodeSelected from './ResizableNodeSelected';
import NodeForm from './NodeForm'; // Import your NodeForm component

const nodeTypes = {
  ResizableNodeSelected,
};
const initialNodes = [];

const edgeTypes = {
};

let id = 0;
const getId = () => `dndnode_${id++}`;

const Canvas = ({ onNodeSelected }) => {
  const reactFlowWrapper = useRef(null);
  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);
  const [reactFlowInstance, setReactFlowInstance] = useState(null);
  const [selectedNode, setSelectedNode] = useState(null); // State to track the selected node

  const onConnect = useCallback((params) => setEdges((eds) => addEdge(params, eds)), [setEdges]);

  const onDragOver = useCallback((event) => {
    event.preventDefault();
    event.dataTransfer.dropEffect = 'move';
  }, []);

  // Fetch existing nodes from the backend and populate the nodes state
  // eslint-disable-next-line react-hooks/exhaustive-deps
  useEffect(() => {
    // Fetch node UUIDs from the backend
    fetch('http://localhost:8080/diagrams/4df979b0-a93e-4211-a340-1333c032f801')
      .then(response => response.json())
      .then(responseData => {
        const { resourcesID } = responseData;
        const fetchNodePromises = resourcesID.map(nodeUuid =>
          fetch(`http://localhost:8080/diagrams/4df979b0-a93e-4211-a340-1333c032f801/resources/${nodeUuid}`)
            .then(response => response.json())
        );

        Promise.all(fetchNodePromises)
          .then(fetchedNodes => {
            // Transform fetched nodes into the format expected by the ResizableNodeSelected component
            const transformedNodes = fetchedNodes.map(fetchedNode => ({
              id: fetchedNode.uuid,
              type: 'ResizableNodeSelected',
              position: { x: 100, y: 100 },
              data: { label: fetchedNode.name, background_data: fetchedNode },
              style: { background: '#fff', border: '1px solid black', borderRadius: 15, fontSize: 12 },
            }));

            setNodes(transformedNodes);
          })
          .catch(error => {
            console.error('Error fetching nodes:', error);
          });
      })
      .catch(error => {
        console.error('Error fetching node UUIDs:', error);
      });
  }, [setNodes]);

  const onDrop = useCallback(
    async (event) => {
      event.preventDefault();

      const reactFlowBounds = reactFlowWrapper.current.getBoundingClientRect();
      const data = JSON.parse(event.dataTransfer.getData('application/json'));

      const type = data.nodeType;
      const additionalData = data.additionalData;

      if (typeof type === 'undefined' || !type) {
        console.log(type);
        return;
      }

      const position = reactFlowInstance.project({
        x: event.clientX - reactFlowBounds.left,
        y: event.clientY - reactFlowBounds.top,
      });

      // Set background_data as the label when selected
      const background_data = additionalData;
      
      const newNode = {
        id: getId(),
        type,
        position,
        style: { background: '#fff', border: '1px solid black', borderRadius: 15, fontSize: 12 },
        data: { label: `${background_data.name}`, background_data }, // Pass background_data as part of the data
      };

      try {
        // Make the POST request to the backend
        const response = await fetch('http://localhost:8080/diagrams/4df979b0-a93e-4211-a340-1333c032f801/resources', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            name: background_data.name,
            resourceCatalogID: background_data.uuid,
          }),
        });

        if (response.ok) {
          // Get the response JSON
          const responseJson = await response.json();

          // Update the background_data of the new node with the response data
          newNode.data.background_data = responseJson;

          // Add the new node to the list of nodes
          setNodes((nds) => nds.concat(newNode));
        } else {
          console.error('POST request failed');
        }
      } catch (error) {
        console.error('Error making POST request:', error);
      }
    },
    [reactFlowInstance, setNodes]
  );

  useEffect(() => {
    const handleKeyDown = async (event) => {
      if (event.key === 'Delete') {
        const selectedNodes = nodes.filter(node => node.selected);
        
        if (selectedNodes.length > 0) {
          const selectedNodeIds = selectedNodes.map(node => node.id);
          const updatedNodes = nodes.filter(node => !selectedNodeIds.includes(node.id));

          // Delete selected nodes from backend and then update state
          try {
            for (const selectedNode of selectedNodes) {
              await fetch(`http://localhost:8080/diagrams/4df979b0-a93e-4211-a340-1333c032f801/resources/${selectedNode.data.background_data.uuid}`, {
                method: 'DELETE',
              });
            }
            
            setNodes(updatedNodes);
          } catch (error) {
            console.error('Error deleting nodes:', error);
          }
        }
      }
    };

    document.addEventListener('keydown', handleKeyDown);

    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [nodes, setNodes]);

  const onNodeClick = useCallback((event, node) => {
    event.preventDefault();
    if (onNodeSelected) {
      onNodeSelected(node.data); // Pass the node data to the onNodeSelected prop
    }
  }, [onNodeSelected]);

  return (
    <div className="reactflow-wrapper" ref={reactFlowWrapper}>
      <ReactFlow
        nodes={nodes}
        nodeTypes={nodeTypes}
        edgeTypes={edgeTypes}
        edges={edges}
        onNodesChange={onNodesChange}
        onEdgesChange={onEdgesChange}
        onConnect={onConnect}
        onInit={setReactFlowInstance}
        onDrop={onDrop}
        onDragOver={onDragOver}
        onNodeDoubleClick={onNodeClick}
        fitView
      >
        <Background variant={BackgroundVariant.Dots} />
        <MiniMap />
        <Controls />
      </ReactFlow>

      {/* Render the NodeForm when a node is selected */}
      {selectedNode && (
        <div className="node-form-container">
          <NodeForm nodeData={selectedNode.data} />
        </div>
      )}
    </div>
  );
};

export default Canvas;