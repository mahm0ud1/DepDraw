import { useEffect, useState } from "react";
import { Description, Sidebar, Node } from "./ToolsBar.Style";

const ToolsBar = () => {
    const [resources, setResources] = useState([]);
    useEffect(() => {
      // Fetch resource data from the backend
      fetch('http://localhost:8080/resourcecatalogs')
        .then(response => response.json())
        .then(apiResponse => {
          setResources(apiResponse);
        })
        .catch(error => {
          console.error('Error fetching resource data:', error);
        });
    }, []);
  
    const onDragStart = (event:any, resource:any) => {
      const data = {
        nodeType: 'ResizableNodeSelected',
        additionalData: resource,
      };
      event.dataTransfer.setData('application/json', JSON.stringify(data));
      event.dataTransfer.effectAllowed = 'move';
    };
  
    return (
      <Sidebar>
        <Description>Draggable Items <br /> Resource Catalog</Description>
        {resources.map((resource:any) => (
          <Node
            key={resource.uuid}
            className="dndnode ResizableNodeSelected"
            onDragStart={(event) => onDragStart(event, resource)}
            draggable
          >
            {resource.name}
          </Node>
        ))}
      </Sidebar>
    );
}

export default ToolsBar;