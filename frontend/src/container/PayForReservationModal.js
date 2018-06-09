import React, { Component } from 'react';
import SelectUserForm from "./SelectUserForm";
import axios from 'axios'
import {updateUrlParameter, baseUrl, errorClass} from "./Utils"
import SelectWeekdayForm from "./SelectWeekdayForm"
import {FormErrors} from "./FormErrors";


class PayForReservationModal extends React.Component {
    constructor(props){
        super(props);
        this.state =
            {
                user: '',
                day: -1,
                amount: 0,
                formErrors: {amount: ''},
                amountValid: false,
                formValid: false
            };

        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleDayChange = this.handleDayChange.bind(this);
        this.payForReservation = this.payForReservation.bind(this);
    }


    handleUserChange(user){
        var newState = {};
        newState['user'] = user;
        this.setState(
            newState
        )
    }


    handleDayChange(day){
        var newState = {};
        newState['day'] = day;
        this.setState(
            newState
        )
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



    render() {
        if (this.props.isOpen === false)
            return null

        let modalStyle = {
            position: 'fixed',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            zIndex: '9999',
            background: '#fff',
            width: '50%',
            height: 'fit-content',
            padding: '16px 16px',
            border: '3px solid #000',
            'overflow-wrap': 'break-word',
            'box-shadow': '0 4px 8px 0 rgba(0,0,0,0.2)',
            'transition': '0.3s',
            'border-radius': '5px' /* 5px rounded corners */
        }


        if (this.props.width && this.props.height) {
            modalStyle.width = this.props.width + 'px'
            modalStyle.height = this.props.height + 'px'
            modalStyle.marginLeft = '-' + (this.props.width/2) + 'px',
                modalStyle.marginTop = '-' + (this.props.height/2) + 'px',
                modalStyle.transform = null
        }


        if (this.props.style) {
            for (let key in this.props.style) {
                modalStyle[key] = this.props.style[key]
            }
        }


        let backdropStyle = {
            position: 'fixed',
            width: '100%',
            height: '100%',
            top: '0px',
            left: '0px',
            zIndex: '9998',
            background: 'rgba(0, 0, 0, 0.3)'
        }


        if (this.props.backdropStyle) {
            for (let key in this.props.backdropStyle) {
                backdropStyle[key] = this.props.backdropStyle[key]
            }
        }


        var modifiers = {
            'weekend': function(weekday) {
                return weekday == 0 || weekday == 6;
            }
        };


        return (

            <div className={this.props.containerClassName}>
                <div className={this.props.className} style={modalStyle}>
                    {this.props.children}
                    <SelectUserForm
                        handleUserChange={this.handleUserChange}
                        title="Select user"
                    />
                    <SelectWeekdayForm
                        handleDayChange={this.handleDayChange}
                        title="Select weekday"
                    />
                    <div className={`form-group ${errorClass(this.state.formErrors.amount)}` }>
                        <label htmlFor="amount">Amount</label>
                        <input type="text" className="form-control" name="amount" value={this.state.amount} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                    <div className="btnContainer">
                        <button className="btn btn-primary" onClick={e => this.payForReservation(e)}>Pay</button>
                        <button className="btn btn-primary" onClick={e => this.close(e)}>Close</button>
                    </div>
                </div>
                {!this.props.noBackdrop &&
                <div className={this.props.backdropClassName} style={backdropStyle}
                     onClick={e => this.close(e)}/>}
            </div>
        )
    }


    close(e) {
        e.preventDefault()

        if (this.props.onClose) {
            this.props.onClose()
        }
    }


    payForReservation (e){
        e.preventDefault()

        // Example: http://localhost:8080/accounts/hex/janusz
        let getNameUri = baseUrl + "/accounts/hex/" + this.props.owner;
        let username = '';

        axios.get(getNameUri)
            .then(res => {
                username = res.data;

                // Example: http://localhost:8080/currency/pay/bob?ownerName=main&index=1&amount=5&weekDay=3
                let uri = baseUrl + "/currency/pay/" + this.state.user;

                uri = updateUrlParameter(uri, 'ownerName', username);
                uri = updateUrlParameter(uri, 'index', this.props.id);
                uri = updateUrlParameter(uri, 'amount', this.state.amount);
                uri = updateUrlParameter(uri, 'weekDay', this.state.day);

                axios.post(uri, {}, {})
                    .then(res => {
                        console.log('Reservation paid. Res: ' + res.data)
                    }, err => {
                        alert("Server rejected response with: " + err);
                    });
                this.close(e);
            }, err => {
                alert("Server rejected response with: " + err);
            });
    }
}

export default PayForReservationModal;