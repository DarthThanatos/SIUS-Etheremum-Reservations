import React, { Component} from 'react';
import Select from 'react-select';
import axios from 'axios'
import '../css/SelectUserForm.css'
import 'react-select/dist/react-select.css';


class SelectCurrencyForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currencies: ['ether', 'custom'],
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
        this.props.handleCurrencyChange(newValue)
    }


    render() {
        var items = this.state.currencies.map(function(item) {
            return { value: item, label: item, className: item};
        });

        return (
            <div className="section">
                <h3 className="section-heading"> {this.props.title} </h3>
                <Select
                    id="currency-select"
                    ref={(ref) => { this.select = ref; }}
                    onBlurResetsInput={false}
                    onSelectResetsInput={false}
                    autoFocus
                    options={items}
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


export default SelectCurrencyForm;
