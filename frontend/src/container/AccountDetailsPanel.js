import React, { Component } from 'react';
import axios from 'axios'
import SelectUserForm from "./SelectUserForm";
import "../css/AccountDetails.css"
import AddAccInputForm from "./AddAccInputForm";
import baseUrl from "./Utils";


class AccountDetailsPanel extends Component {
    render () {
        return (
            <div className="accountDetailsPanel" autofocus>
                <h2>Account details</h2>
                <p>
                    Username: {this.state.user.name} <br />
                    &ensp; Ether balance: <br />
                    &ensp; &ensp; In ethers: {this.state.etherBalance} <br />
                    &ensp; &ensp; In weis: {this.state.weiBalance} <br />
                    &ensp; Custom currency balance: {this.state.customBalance} <br />
                </p>
                <SelectUserForm
                    handleUserChange={this.handleUserChange}
                    title="Select user"
                />
                <AddAccInputForm/>
            </div>
        )
    }

    constructor(props){
        super(props);
        this.state = AccountDetailsPanel.getEmptyState();
        this.handleUserChange = this.handleUserChange.bind(this)
    }

    handleUserChange(user){
        var _this = this;

        if(user === null){
            _this.setState(AccountDetailsPanel.getEmptyState());
            return
        }

        // Example: http://localhost:8080/currency/balance/ether/bob
        axios.get(baseUrl + "/currency/balance/ether/" + user)
            .then(function(res){
                _this.setState({
                    etherBalance: res.data.ether,
                    weiBalance: res.data.wei
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });

        // Example: http://localhost:8080/currency/balance/custom/bob
        axios.get(baseUrl + "/currency/balance/custom/" + user)
            .then(function(res){
                _this.setState({
                    customBalance: res.data.value
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            });
    }

    static getEmptyState() {
        return {
            user: '',
            customBalance: 0,
            etherBalance: 0,
            weiBalance: 0
        }
    }
}

export default AccountDetailsPanel