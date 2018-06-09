import axios from 'axios'
import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import MyFirstGrid from "./GridLayout";


class Main extends Component {
    render() {
        return (
            <div className="Main">
              <header className="App-header">
              </header>
                <body>
                    <div className="body">
                        <div className="header">
                            <h1 className="App-title">Etheremum Reservation Application </h1>
                        </div>
                    <MyFirstGrid />
                    </div>
                </body>
            </div>
        );
    }


  constructor(props) {
    super(props);
  }
}

export default Main;
