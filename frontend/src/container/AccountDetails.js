import React, { Component } from 'react';
import axios from 'axios'
import SelectUserForm from "./SelectUserForm";
import "../css/AccountDetails.css"

class AccountDetails extends Component {
    render () {
        console.log("Rerendering...")
        console.log("username" + this.state.user.name)
        return (
            <div className="accountDetails">
                <h2>Account details</h2>
                <p> Username: {this.state.user.name} </p>
                <SelectUserForm handleUserChange={this.handleUserChange} />
            </div>
        )
    }

    constructor(props){
        super(props);
        this.state = {
            user: ''
        };
        this.handleUserChange = this.handleUserChange.bind(this)
    }

    handleUserChange(user){
        var _this = this;

        axios.get("http://localhost:8080/accounts/" + user)
            .then(function(res){
                _this.setState({
                    user: res.data
                });
            })
            .catch(function(e) {
                console.log("ERROR ", e);
            })
        // window.location.reload()
    }

    componentDidMount() {
        var _this = this;
        axios.get("http://localhost:8080/accounts/" + this.state.user)
            .then(function(res){
                _this.setState({
                    user: res.data
                });
            })
            .catch(function(e) {
                console.log("ERROR ", e);
            })
    }
}

export default AccountDetails