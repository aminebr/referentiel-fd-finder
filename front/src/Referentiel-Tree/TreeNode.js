import React, { useState, useEffect } from 'react';
import axios from 'axios';


export default function TreeNode({ schemaName , setShouldRestartTree}) {

    const [nextNodeName, setNextNodeName] = useState(null);
    const [node, setNode] = useState(null);
    const [expandButtonContent, setExpandButtonContent]= useState("+");

    useEffect(() => {
      if(setShouldRestartTree) {
        setNextNodeName(null)
        setExpandButtonContent("+")
      }
       axios.get(schemaName)
        .then(response => {
          setNode(response.data);
        })
        .catch(error => console.error('Error fetching schema:', error));
    }, [schemaName]);

    

    const handleNodeClick = (ref) => {
      if (nextNodeName != null) {
        setNextNodeName(null); // Close the node if it's already open
        setExpandButtonContent("+")
      } else {
        setNextNodeName(ref);
        setExpandButtonContent("-")
      }
    };

  
    return (
      <>
        {node !== null && 
        
        <div className="card" >


        <p> <strong> id : </strong>  {node?.$id}</p>
        <p> <strong> type : </strong> {node?.type}</p>
        <p> <strong> properties : </strong> </p>
        <ul>
          {Object.entries(node?.properties).map(([propertyName, property]) => (
            <li key={propertyName}>
              <strong style={{color : "#007bff"}}>{propertyName}</strong>: {JSON.stringify(property)}
              {property["$ref"] && (
                <button onClick={() => handleNodeClick(property["$ref"])}>{expandButtonContent}</button>
              )}
            </li>
          ))}
        </ul>
        <p> <strong style={{color : "green"}}> additional properties :  </strong> {JSON.stringify(node?.additionalProperties)}  </p>
        <p> <strong style={{color : "red"}}> required :  </strong> {JSON.stringify(node?.required)}  </p>
      </div> }
        



        {nextNodeName !== null && (
          <TreeNode schemaName={nextNodeName}   />
        ) }
      </>
     
    );
}
