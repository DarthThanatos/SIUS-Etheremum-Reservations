import React, { Component } from 'react';
import axios from 'axios'
import SelectUserForm from "./SelectUserForm";
import "../css/AccountDetails.css"
import Slider, { Range } from 'rc-slider';
import 'rc-slider/assets/index.css';
import { FormErrors } from "./FormErrors";
import { updateUrlParameter, errorClass, baseUrl } from "./Utils"
import SelectCurrencyForm from "./SelectCurrencyForm";


class SendPanel extends Component {
    render () {
        // const SliderWithTooltip = Slider.createSliderWithTooltip(Slider)

        return (
            <div className="sendPanel">
                <h2>Send currency:</h2>
                <p> From: <SelectUserForm handleUserChange={this.handleIssuerChange} /> </p>
                <p> To: <SelectUserForm handleUserChange={this.handleTargetUserChange} /> </p>
                <p> Currency: <SelectCurrencyForm handleCurrencyChange={this.handleCurrencyChange} /> </p>
                {/*<p><SliderWithTooltip*/}
                    {/*value={this.state.amount}*/}
                    {/*max={this.state.max}>*/}
                {/*</SliderWithTooltip></p>*/}

                <form className="sendCurrInputForm">
                    <div className={`form-group ${errorClass(this.state.formErrors.amount)}` }>
                        <label htmlFor="amount">Amount</label>
                        <input type="text" className="form-control" name="amount" value={this.state.amount} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <button className="btn btn-primary" disabled={!this.state.formValid} onClick={(e) => this.sendCurr(e)}>
                        Send
                    </button>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                </form>
            </div>
        )
    }


    constructor(props){
        super(props);
        this.state = {
            issuer: '',
            targetName: '',
            amount: 0,
            max: 0,
            formErrors: {amount: ''},
            amountValid: false,
            formValid: false,
            currency: ''
        };
        this.handleIssuerChange = this.handleIssuerChange.bind(this)
        this.handleTargetUserChange = this.handleTargetUserChange.bind(this)
        this.handleCurrencyChange = this.handleCurrencyChange.bind(this)
        this.sendCurr = this.sendCurr.bind(this)
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
                amountValid = value.match(/^\d+$/i);
                fieldValidationErrors.amount = amountValid ? '' : ' has to be integer';
                if(parseInt(value) > this.state.max) {
                    amountValid = false;
                    fieldValidationErrors.amount = 'error. Issuer has not enough currency!';
                }
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


    handleIssuerChange(user){
        var _this = this;

        // Example: http://localhost:8080/accounts/bob
        axios.get(baseUrl + "/accounts/" + user)
            .then(function(res){
                _this.setState({
                    issuer: res.data.name,
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });

        // Example: http://localhost:8080/currency/balance/ether/bob
        axios.get(baseUrl + "/currency/balance/" + _this.state.currency + "/" + user)
            .then(function(res){
                if(_this.state.currency.localeCompare("ether") === 0){
                    _this.setState({
                        max: res.data.ether,
                    });
                }
                else{
                    _this.setState({
                        max: res.data.value,
                    });
                }

            })
            .catch(function(e) {
                console.log("ERROR ", e);
            });
    }

    handleTargetUserChange(user){
        var _this = this;

        // Example: http://localhost:8080/accounts/bob
        axios.get(baseUrl + "/accounts/" + user)
            .then(function(res){
                _this.setState({
                    targetName: res.data.name
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });
    }

    handleCurrencyChange(currency){
        var _this = this;

        _this.setState({
            currency: currency
        });

        // Example: http://localhost:8080/currency/balance/ether/bob
        axios.get(baseUrl + "/currency/balance/" + currency + "/" + _this.state.issuer)
            .then(function(res){
                if(currency.localeCompare("ether") === 0){
                    _this.setState({
                        max: res.data.ether,
                    });
                }
                else{
                    _this.setState({
                        max: res.data.value,
                    });
                }

            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });
    }

    sendCurr(e) {
        e.preventDefault()

        // Example: http://localhost:8080/currency/send/ether/bob?targetName=main&amount=20
        let uri = baseUrl + "/currency/send/" + this.state.currency + "/" + this.state.issuer;
        uri = updateUrlParameter(uri, 'targetName', this.state.targetName);
        uri = updateUrlParameter(uri, 'amount', this.state.amount);

        axios.post(uri, {}, {})
            .then(function(res){
                console.log('Sent currency. Result: ' + res.data)
            })
            .catch(function(e) {
                console.log("ERROR ", e);
            });
    }

}

export default SendPanel