import React, { useState } from 'react';
import { Tooltip } from 'antd';

const DescriptionToggleWidget = (props) => {
  const [showDescription, setShowDescription] = useState(false);

  const toggleDescription = () => {
    setShowDescription(!showDescription);
  };

  const { schema } = props;

  return (
    <div className="description-toggle-widget">
      <label className="control-label">
        {schema.title || ''}
        {schema.description && (
          <Tooltip title="Toggle description">
            <button
              type="button"
              className="description-toggle-button"
              onClick={toggleDescription}
            >
              <span className="icon">?</span>
            </button>
          </Tooltip>
        )}
      </label>
      {showDescription && <p className="field-description">{schema.description}</p>}
      {/* Render the field directly */}
      {props.children}
    </div>
  );
};

export default DescriptionToggleWidget;
