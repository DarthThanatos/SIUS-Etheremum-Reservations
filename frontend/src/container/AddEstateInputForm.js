import React, { Component } from 'react';
import { FormErrors } from "./FormErrors";
import "./../css/Form.css"
import AddEstateModal from "./AddEstateModal"
import {errorClass} from "./Utils"


class AddEstateInputForm extends Component {
    render () {
        return (
            <form className="addEstateInputForm">
                <h2>Add estate</h2>
                    <div className={`form-group ${errorClass(this.state.formErrors.estateName)}` }>
                        <label htmlFor="estateName">Estate name</label>
                        <input type="text" className="form-control" name="estateName" value={this.state.estateName} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <div className={`form-group ${errorClass(this.state.formErrors.price)}` }>
                        <label htmlFor="price">Price</label>
                        <input type="text" className="form-control" name="price" value={this.state.price} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <button className="btn btn-primary" disabled={!this.state.formValid} onClick={(e) => this.openModal(e)}>
                        Add estate
                    </button>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                <AddEstateModal
                    isOpen={this.state.modalIsOpen}
                    onClose={() => this.closeModal()}
                    estateName={this.state.estateName}
                    price={this.state.price}
                >
                    <h2>Select user modal</h2>
                </AddEstateModal>
            </form>
        )
    }


    openModal(e) {
        this.setState({modalIsOpen: true});
        e.preventDefault()
    }


    afterOpenModal() {
        // references are now sync'd and can be accessed.
    }


    closeModal() {
        this.setState({modalIsOpen: false});
    }


    constructor (props){
        super(props);
        this.state = {
            estateName: '',
            price: '',
            formErrors: {estateName: '', price: ''},
            estateNameValid: false,
            priceValid: false,
            formValid: false,
            modalIsOpen: false,
            user: 'main'
        };
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }


    handleUserInput (e) {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({[name]: value},
            () => { this.validateField(name, value) });
    }


    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let estateNameValid = this.state.estateNameValid;
        let priceValid = this.state.priceValid;

        switch(fieldName) {
            case 'estateName':
                estateNameValid = value.match(/^(.*)$/i);
                fieldValidationErrors.estateName = estateNameValid ? '' : ' is invalid';
                break;
            case 'price':
                priceValid = value.match(/^([\d]+)$/i);
                fieldValidationErrors.price = priceValid ? '': 'has to be integer';
                break;
            default:
                break;
        }

        this.setState({formErrors: fieldValidationErrors,
            estateNameValid: estateNameValid,
            priceValid: priceValid
        }, this.validateForm);
    }


    validateForm() {
        this.setState({formValid: this.state.estateNameValid && this.state.priceValid});
    }
}

export default AddEstateInputForm;