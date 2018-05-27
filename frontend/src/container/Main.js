import axios from 'axios'
import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import 'react-grid-layout/css/styles.css';
import 'react-resizable/css/styles.css';
import MyFirstGrid from "./GridLayout";
import SelectUserForm from "./SelectUserForm"
import AccountDetails from "./AccountDetails";
import AddEstateInputForm from './AddEstateInputForm'

class Main extends Component {
    render() {
        return (
            <div className="Main">
              <header className="App-header">
                <h1 className="App-title">Etheremum Reservation Application </h1>
              </header>
              <p className="App-intro">
                <div>
                </div>
              </p>
                <AccountDetails/>
                <AddEstateInputForm />
              <MyFirstGrid />
            </div>
        );
    }


  constructor(props) {
    super(props);
  }
}

export default Main;
