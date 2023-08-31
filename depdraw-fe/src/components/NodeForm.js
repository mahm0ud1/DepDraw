import React, { useState, useEffect } from 'react';
import { Form as AntdForm } from '@rjsf/antd';
import validator from '@rjsf/validator-ajv8';
import 'antd/dist/antd.css';
import './NodeForm.css';

import ToggleDescriptionWidget from './widgets/ToggleDescriptionWidget'; // Update the path

const NodeForm = ({ nodeData, initialFormData }) => {
  const [schema, setSchema] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/k8sresourceschemas/f54012e8-8311-11ed-a1eb-0242ac120002`)
      .then((response) => response.json())
      .then((fetchedSchema) => {
        setSchema(fetchedSchema);
      })
      .catch((error) => {
        console.error('Error fetching schema:', error);
      });
  }, [nodeData]);

  const handleSubmit = ({ formData }) => {
    console.log('Form submitted', formData);
  };

  if (!schema) {
    return <div>Loading schema...</div>;
  }

  if (!schema) {
    return <div>Loading schema...</div>;
  }

  const widgets = {
    toggleDescriptionWidget: ToggleDescriptionWidget,
  };

  return (
    <div className="form-modal">
      <AntdForm
        schema={schema}
        onSubmit={handleSubmit}
        formData={initialFormData} // Use the initial form data here
        validator={validator}
        widgets={widgets}
        className="custom-form"
      />
    </div>
  );
};

export default NodeForm;
