import React, { Component } from 'react';
import { FormErrors } from "./FormErrors";
import "./../css/Form.css"
import axios from 'axios'
import {baseUrl, errorClass} from './Utils'


class AddAccInputForm extends Component {
    render () {
        return (
            <form className="addAccInputForm">
                <h2>Add new acc</h2>
                <div className={`form-group ${errorClass(this.state.formErrors.username)}` }>
                    <label htmlFor="username">Username</label>
                    <input type="text" className="form-control" name="username" value={this.state.username} onChange={(event) => this.handleUserInput(event)} />
                </div>
                <div className="btnContainer">
                    <button className="btn btn-primary" disabled={!this.state.formValid} onClick={(e) => this.addAcc(e)}>
                        Add user
                    </button>
                </div>
                <div className="panel panel-default">
                    <FormErrors formErrors={this.state.formErrors} />
                </div>
            </form>
        )
    }


    constructor (props){
        super(props);
        this.state = {
            username: '',
            formErrors: {username: ''},
            usernameValid: false,
            formValid: false,
        };
        this.addAcc = this.addAcc.bind(this)
    }


    handleUserInput (e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }


    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let usernameValid = this.state.usernameValid;

        switch(fieldName) {
            case 'username':
                usernameValid = value.match(/^(.*)$/i);
                fieldValidationErrors.username = usernameValid ? '' : ' is invalid';
                break;
            default:
                break;
        }

        this.setState({formErrors: fieldValidationErrors,
            usernameValid: usernameValid
        }, this.validateForm);
    }


    validateForm() {
        this.setState({formValid: this.state.usernameValid});
    }


    addAcc(e) {
        // Example: http://localhost:8080/accounts/new/bob
        let uri = baseUrl + "/accounts/new/" + this.state.username;

        axios.put(uri, {}, {})
            .then(res => {
                console.log('Added new user ' + res.data)
            }, err => {
                alert("Server rejected response with: " + err);
            });
    }

}

export default AddAccInputForm;