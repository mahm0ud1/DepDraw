import React, { useState, useEffect } from 'react';

const DragComponent = () => {
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

  const onDragStart = (event, resource) => {
    const data = {
      nodeType: 'ResizableNodeSelected',
      additionalData: resource,
    };
    event.dataTransfer.setData('application/json', JSON.stringify(data));
    event.dataTransfer.effectAllowed = 'move';
  };

  const sidebarStyle = {
    backgroundColor: 'rgb(40, 44, 52)',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    fontSize: 'calc(10px + 2vmin)',
    color: 'white',
    minWidth: '200px',
    borderRight: '4px solid goldenrod', // Add the golden border
  };

  const descriptionStyle = {
    fontSize: '14px',
    marginBottom: '10px',
  };

  const nodeStyle = {
    height: '30px',
    padding: '8px',
    border: '1px solid rgb(26, 25, 43)',
    borderRadius: '4px',
    marginBottom: '10px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    cursor: 'grab',
    backgroundColor: 'rgb(240, 240, 240)',
    width: '80%',
    color: 'rgb(51, 51, 51)',
  };

  return (
    <aside style={sidebarStyle}>
      <div style={descriptionStyle}>Draggable Items <br /> Resource Catalog</div>
      {resources.map((resource) => (
        <div
          key={resource.uuid}
          className="dndnode ResizableNodeSelected"
          style={nodeStyle}
          onDragStart={(event) => onDragStart(event, resource)}
          draggable
        >
          {resource.name}
        </div>
      ))}
    </aside>
  );
};

export default DragComponent;
