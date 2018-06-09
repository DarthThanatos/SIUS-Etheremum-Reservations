import React, { Component} from 'react';
import Select from 'react-select';
import axios from 'axios'
import '../css/SelectUserForm.css'
import 'react-select/dist/react-select.css';


class SelectWeekdayForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            weekdays: [],
            selectValue: -1,
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
        this.props.handleDayChange(newValue)
    }

    render() {
        const weekdays = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

        var items = weekdays.map(function(item, i) {
            return { value: i, label: item, className: item };
        });

        return (
            <div className="section">
                <h3 className="section-heading"> Please select weekday </h3>
                <Select
                    id="user-select"
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


export default SelectWeekdayForm;