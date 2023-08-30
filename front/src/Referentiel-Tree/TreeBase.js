import React, { useState, useEffect } from 'react';
import TreeNode from './TreeNode';
import "./TreeCards.css"


export default function TreeBase({ selectedOption }) {

  const [schemaName, setSchemaName] = useState(null); // Track the schema data
  const [shouldRestartTree, setShouldRestartTree] = useState(true);

  useEffect(() => {
    setSchemaName("http://localhost:8080/schemas/"+selectedOption);
  }, [selectedOption,schemaName]);



  return (
  <div className="App">
    <div className='card-list-container'>
      <div className='card-list'>
        { schemaName !== null && <TreeNode  schemaName={schemaName} setShouldRestartTree={setShouldRestartTree}  />}
      </div>
    </div>
  </div>
  );
    
}
