import React, { useState } from 'react';
import Canvas from './components/Canvas';
import Sidebar from './components/Sidebar';
import NodeForm from './components/NodeForm';
import Header from './components/Header';
import './index.css';
import { ReactFlowProvider } from 'reactflow';

const App = () => {
  const [selectedNodeData, setSelectedNodeData] = useState(null);
  const [formData, setFormData] = useState({});

  const handleNodeSelected = (nodeData) => {
    setSelectedNodeData(nodeData);
  };

  const handleFormSubmit = (data) => {
    setFormData(data);
  };

  return (
    <div className="app-container">
      <Header />
      <div className="dndflow" style={{ width: '100%', height: 'calc(100vh - 80px)' }}>
        {/* Adjust the height value as needed */}
        <ReactFlowProvider>
          <Sidebar />
          <Canvas onNodeSelected={handleNodeSelected} />
        </ReactFlowProvider>
        {selectedNodeData && (
          <NodeForm
            nodeData={selectedNodeData}
            onSubmit={handleFormSubmit}
            initialFormData={formData}
          />
        )}
      </div>
    </div>
  );
};

export default App;
