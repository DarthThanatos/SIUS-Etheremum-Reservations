import React, { Component } from 'react';
import SelectUserForm from "./SelectUserForm";
import axios from 'axios'
import { updateUrlParameter, baseUrl } from "./Utils"


class AddEstateModal extends React.Component {
    constructor(props){
        super(props);
        this.state =
            {
                user : ''
            };

        this.handleUserChange = this.handleUserChange.bind(this);
        this.addEstate = this.addEstate.bind(this);
        this.close = this.close.bind(this);
    }


    handleUserChange(user){
        var newState = {};
        newState['user'] = user;
        this.setState(
            newState
        );
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
            width: '75%',
            height: '50%',
            padding: '16px 16px',
            border: '3px solid #000',
            'overflow-wrap': 'break-word',
            'box-shadow': '0 4px 8px 0 rgba(0,0,0,0.2)',
            'transition': '0.3s',
            'border-radius': '5px' /* 5px rounded corners */
        };


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


        return (
            <div className={this.props.containerClassName}>
                <div className={this.props.className} style={modalStyle}>
                    {this.props.children}
                    <SelectUserForm handleUserChange={this.handleUserChange} />
                    <div>
                        <button className="btn btn-primary" onClick={e => this.addEstate(e)}>Add estate</button>
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


    addEstate (e){
        e.preventDefault()

        // Example: http://localhost:8080/reservations/publish/main?estateName=est_main_0&estatePrice=20
        let uri = baseUrl + "/reservations/publish/" + this.state.user;

        uri = updateUrlParameter(uri, 'estateName', this.props.estateName);
        uri = updateUrlParameter(uri, 'estatePrice', this.props.price);

        axios.post(uri, {}, {})
            .then(res => {
                console.log('Published estate. Res: ' + res.data)
            }, err => {
                alert("Server rejected response with: " + err);
            });
        this.close(e);
        window.location.reload()
    }
}


export default AddEstateModal