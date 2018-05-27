import axios from 'axios'
import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import MyFirstGrid from "./GridLayout";
import SelectUserForm from "./SelectUserForm"

class Main extends Component {
    render() {
        return (
            <div className="Main">
              <header className="App-header">
                <h1 className="App-title">Bob account:</h1>
              </header>
              <p className="App-intro">
                <div>
                  <button onClick={this.getBobAcc}>Get</button>
                    <button onClick={this.addEstate}>Put</button>
                  <div> Response: {this.state.response} </div>
                </div>
              </p>
              <MyFirstGrid />
            </div>
        );
    }

    addEstate (){
        var config = {
            headers: {'Access-Control-Allow-Origin': '*'}

        };

        axios.post("http://localhost:8080/reservations/publish/main?estateName=est_main_0&estatePrice=20",
            {}, config).
        then(res => {
            console.log('saved successfully')
        });
    }

    getBobAcc (){

        axios.get("http://localhost:8080/estates/main").then(res => {

        }, err => {
            alert("Server rejected respons with: " + err);
        });
        console.log("Get clicked");
    }

  constructor(props) {
    super(props);
    this.state = {response: 'Didn\'t get response yet'};
    this.getBobAcc = this.getBobAcc.bind(this);
    this.addEstate = this.addEstate.bind(this);
  }
}

export default Main;
