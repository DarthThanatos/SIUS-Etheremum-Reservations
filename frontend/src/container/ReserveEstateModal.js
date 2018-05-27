import React, { Component } from 'react';
import SelectUserForm from "./SelectUserForm";
import axios from 'axios'
import { updateUrlParameter } from "./Utils"
import SelectWeekdayForm from "./SelectWeekdayForm"

class ReserveEstateModal extends React.Component {
    constructor(props){
        super(props);
        this.state =
            {
                user: '',
                day: -1
            };

        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleDayChange = this.handleDayChange.bind(this);
        this.reserveEstate = this.reserveEstate.bind(this);
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

    render() {
        if (this.props.isOpen === false)
            return null

        let modalStyle = {
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            zIndex: '9999',
            background: '#fff',
            width: '50%',
            height: '50%',
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
            position: 'absolute',
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
                    <SelectUserForm handleUserChange={this.handleUserChange} />
                    <SelectWeekdayForm handleDayChange={this.handleDayChange}/>
                    <p><button onClick={e => this.reserveEstate(e)}>Reserve estate</button></p>
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

    reserveEstate (e){
        e.preventDefault()
        let config = {
            headers: {'Access-Control-Allow-Origin': '*'}

        };

        let getNameUri = "http://localhost:8080/accounts/hex/" + this.props.owner;
        console.log(getNameUri)
        let username = '';

        axios.get(getNameUri)
            .then(res => {
                username = res.data;
                let uri = "http://localhost:8080/reservations/reserve/" + this.state.user;

                console.log("username: " + username);

                uri = updateUrlParameter(uri, 'ownerName', username);
                uri = updateUrlParameter(uri, 'day', this.state.day);
                uri = updateUrlParameter(uri, 'index', this.props.id);

                console.log("URI" + uri)

                console.log("Reserving for: " + this.props.owner)

                axios.put(uri, {}, config)
                    .then(res => {
                        console.log('Result:' + res.data)
                    }, err => {
                        alert("Server rejected response with: " + err);
                    });
                this.close(e);
            }, err => {
                alert("Server rejected response with: " + err);
            });

        // window.location.reload()
    }


}

export default ReserveEstateModal