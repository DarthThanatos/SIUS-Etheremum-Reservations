import React, { Component } from 'react';
import { FormErrors } from "./FormErrors";
import "./../css/Form.css"
import axios from 'axios'
import {baseUrl, errorClass, updateUrlParameter} from './Utils'
import SelectUserForm from "./SelectUserForm";
import "../css/MintCurrency.css"


class MintCurrency extends Component {
    render () {
        return (
            <form className="mintCurrency">
                <h2>Mint currency</h2>
                <SelectUserForm
                    handleUserChange={this.handleUserChange}
                    title="Select issuer"
                />
                <div className={`form-group ${errorClass(this.state.formErrors.amount)}` }>
                    <label htmlFor="amount">Amount</label>
                    <input type="text" className="form-control" name="amount" value={this.state.amount} onChange={(event) => this.handleUserInput(event)} />
                </div>
                <div className="btnContainer">
                <button className="btn btn-primary" disabled={!this.state.formValid} onClick={(e) => this.mintCurrency(e)}>
                    Mint
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
            amount: 0,
            formErrors: {amount: ''},
            usernameValid: false,
            formValid: false,
            targetUser: ''
        };
        this.mintCurrency = this.mintCurrency.bind(this)
        this.handleUserChange = this.handleUserChange.bind(this)
    }


    handleUserChange(user){
        var _this = this;

        // Example: http://localhost:8080/accounts/bob
        axios.get(baseUrl + "/accounts/" + user)
            .then(function(res){
                _this.setState({
                    targetUser: res.data.name
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });
    }

    handleUserInput (e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }


    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let amountValid = this.state.amountValid;

        switch(fieldName) {
            case 'amount':
                amountValid = value.match(/^(\d+)$/i);
                fieldValidationErrors.amount = amountValid ? '' : ' is invalid';
                break;
            default:
                break;
        }

        this.setState({formErrors: fieldValidationErrors,
            amountValid: amountValid
        }, this.validateForm);
    }


    validateForm() {
        this.setState({formValid: this.state.amountValid});
    }


    mintCurrency(e) {
        e.preventDefault()
        // Example: http://localhost:8080/currency/mint/bob?targetName=main&amount=20
        let uri = baseUrl + "/currency/mint/main";

        uri = updateUrlParameter(uri, 'targetName', this.state.targetUser);
        uri = updateUrlParameter(uri, 'amount', this.state.amount);

        axios.post(uri, {}, {})
            .then(res => {
                console.log('Currency minted. Res: ' + res.data)
            }, err => {
                alert("Server rejected response with: " + err);
            });
    }

}

export default MintCurrency;