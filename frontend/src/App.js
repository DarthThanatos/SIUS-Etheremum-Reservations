import React, { Component } from 'react';
import Main from './container/Main';
import AddEstateInputForm from './container/AddEstateInputForm'
import './css/App.css';
import { CSSGrid, layout } from 'react-stonecutter';


class App extends Component {

  render() {
    return (
      <div className="App">
        <Main />
        <AddEstateInputForm />
      </div>
    );
  }
}

export default App;