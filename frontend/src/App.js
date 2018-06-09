import React, { Component } from 'react';
import Main from './container/Main';
import './css/App.css';
import SockJsClient from 'react-stomp';
import { CSSGrid, layout } from 'react-stonecutter';
import WS from "./container/WS";

const ReactDOM = require('react-dom')


class App extends Component {

  render() {
    return (
      <div className="App">
        <Main />
      </div>
    );
  }
}

export default App;
