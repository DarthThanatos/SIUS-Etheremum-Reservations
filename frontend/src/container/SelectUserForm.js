import React, { Component} from 'react';
import Select from 'react-select';
import axios from 'axios'
import '../css/SelectUserForm.css'
import 'react-select/dist/react-select.css';


class SelectUserForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            selectValue: '',
            searchable: true,
            clearable: true,
            rtl: false,
            disabled: false
        };
        this.updateValue = this.updateValue.bind(this);
    }


    updateValue (newValue) {
        this.setState({
            selectValue: newValue,
        });
        console.log("Changing to" + newValue);
        this.props.handleUserChange(newValue)
    }


    componentDidMount() {
        var _this = this;
        axios.get("http://localhost:8080/accounts")
            .then(function(res){
                _this.setState({
                    users: res.data
                });
            })
            .catch(function(e) {
                console.log("ERROR ", e);
            })
    }


    render() {
        var names = this.state.users.map(function(item) {
            return { value: item.name, label: item.name, className: item.name };
        });

        console.log(names);

        return (
            <div className="section">
                <h3 className="section-heading"> {this.props.title} </h3>
                <Select
                    id="user-select"
                    ref={(ref) => { this.select = ref; }}
                    onBlurResetsInput={false}
                    onSelectResetsInput={false}
                    autoFocus
                    options={names}
                    simpleValue
                    clearable={this.state.clearable}
                    name="selected-user"
                    disabled={this.state.disabled}
                    value={this.state.selectValue}
                    onChange={this.updateValue}
                    rtl={this.state.rtl}
                    searchable={this.state.searchable}
                />
            </div>
        );
    }
}


export default SelectUserForm;