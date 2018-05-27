import React, { Component } from 'react';
import { FormErrors } from "./FormErrors";
import "./../css/Form.css"
import Modal from "./Modal"
import SelectUserForm from "./SelectUserForm";


class AddEstateInputForm extends Component {
    render () {
        return (
            <form className="addEstateInputForm">
                <h2>Add estate</h2>
                    <div className={`form-group ${this.errorClass(this.state.formErrors.estateName)}` }>
                        <label htmlFor="estateName">Estate name</label>
                        <input type="text" className="form-control" name="estateName" value={this.state.estateName} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <div className={`form-group ${this.errorClass(this.state.formErrors.price)}` }>
                        <label htmlFor="price">Price</label>
                        <input type="text" className="form-control" name="price" value={this.state.price} onChange={(event) => this.handleUserInput(event)} />
                    </div>
                    <button className="btn btn-primary" disabled={!this.state.formValid} onClick={(e) => this.openModal(e)}>
                        Add estate
                    </button>
                    <div className="panel panel-default">
                        <FormErrors formErrors={this.state.formErrors} />
                    </div>
                <Modal
                    isOpen={this.state.modalIsOpen}
                    onClose={() => this.closeModal()}
                    estateName={this.state.estateName}
                    price={this.state.price}

                >
                Title

                    <p><button onClick={() => this.closeModal()}>Close</button></p>
                </Modal>
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
                priceValid = value.match(/^([\d]*\.?[\d]*)$/i);
                fieldValidationErrors.price = priceValid ? '': 'has to be number';
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

    errorClass(error) {
        return(error.length === 0 ? '' : 'has-error');
    }


}

export default AddEstateInputForm;