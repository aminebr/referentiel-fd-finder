import React , { useState }  from 'react';

import "./App.css"
import TreeBase from './Referentiel-Tree/TreeBase';
import Sidebar from './Sidebar/Sidebar';


export default function App() {
  

  const [selectedOption, setSelectedOption] = useState('taas');

  const handleSelectOption = (option) => {
    setSelectedOption(option);
  };


  return (
    <div className="App">
      <div className="sidebar">
        <Sidebar onSelectOption={handleSelectOption} selectedOption={selectedOption} />
      </div>
      <div className="tree-container">
        <TreeBase selectedOption={selectedOption} />
      </div>
    </div>
  )
}
