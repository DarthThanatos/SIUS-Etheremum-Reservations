import axios from 'axios'
import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import MyFirstGrid from "./GridLayout";
import WS from "./WS";



class Main extends Component {
    render() {
        return (
            <div className="Main">
              <header className="App-header">
              </header>
                <body>
                    <div className="body" >
                        <div style={{display:"flex", flexDirection:"row", justifyContent:"center"}}>
                            <div className="header" style={{background:"white", opacity:0.8, width: "90%", marginTop:20, marginBottom:20, border:".1px solid #000000",color: "#555555", fontSize: 60}}>
                                Ethereum Reservations
                            </div>
                        </div>
                        <MyFirstGrid />
                        <div style={{display:"flex", flexDirection:"row", justifyContent:"center", marginTop:50, marginBottom:50}}>
                            <WS />
                        </div>
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
