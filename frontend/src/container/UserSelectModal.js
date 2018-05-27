import Modal from '';
import SelectUserForm from "./SelectUserForm"
import React, { Component } from 'react';
import axios from 'axios'
import { updateUrlParameter } from "./Utils"

const customStyles = {
    content : {
        top                   : '50%',
        left                  : '50%',
        right                 : 'auto',
        bottom                : 'auto',
        marginRight           : '-50%',
        transform             : 'translate(-50%, -50%)',
        width                 : '500px',
        height                : '500px'
    }
};

class UserSelectModal extends ReactModal{
    constructor(props){
        super(props);
        this.state = {
            user: ''
        };
        this.handleUserChange = this.handleUserChange.bind(this);
        this.addEstate = this.addEstate.bind(this);
    }


    handleUserChange(user){
        console.log("Changing user");
        this.setState([
            {user: user}
        ])
    }

    render () {
        return (
            <ReactModal
                isOpen={this.props.modalIsOpen}
                onAfterOpen={this.props.afterOpenModal}
                onRequestClose={this.props.closeModal}
                handleUserChange={this.handleUserChange}
                style={customStyles}
                contentLabel="Select User Modal"
                containerClassName="UserSelectModal"
            >
                <SelectUserForm handleUserChange={this.handleUserChange}  />
                <button onClick={this.props.closeModal}> Close </button>
                <button onClick={this.addEstate}> Add estate </button>
            </ReactModal>
        )
    }

}

export default UserSelectModal