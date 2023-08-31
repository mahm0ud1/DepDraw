import { memo } from 'react';
import { Handle, Position, NodeResizer } from 'reactflow';

const ResizableNodeSelected = ({ data, selected }) => {
  // Display background_data as label when the node is selected
  const label = selected
    ? Object.entries(data.background_data)
        .map(([key, value]) => `${key}: ${value}`)
        .join(' | ')
    : data.label;

  return (
    <>
      <NodeResizer color="#ff0071" isVisible={selected} minWidth={100} minHeight={30} />
      <Handle type="target" position={Position.Left} />
      <div style={{ padding: 10 }}>{label}</div>
      <Handle type="source" position={Position.Right} />
    </>
  );
};

export default memo(ResizableNodeSelected);
