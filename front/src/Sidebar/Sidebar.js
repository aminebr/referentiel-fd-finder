import React from 'react';
import "./sidebar.css"
export default function Sidebar({ onSelectOption }) {
  return (
    <div className="sidebar">
      <h3>Referentiels:</h3>
      <ul>
        <li>
          <span onClick={() => onSelectOption('taas')}>Taas</span>
        </li>
        <li>
          <span onClick={() => onSelectOption('scripta')}>Scripta</span>
        </li>
        <li>
          <span onClick={() => onSelectOption('cloudera')}>Cloudera</span>
        </li>
      </ul>
    </div>
  );
}
