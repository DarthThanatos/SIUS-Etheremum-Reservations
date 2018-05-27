import GridLayout from 'react-grid-layout';
import React, { Component } from 'react';
import axios from 'axios'

var COLS=3;

class MyFirstGrid extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: []
        }
    }

    componentDidMount() {
        var _this = this;
        axios.get("http://localhost:8080/estates/main")
            .then(function(res){
                _this.setState({
                    items: res.data
                });
            })
            .catch(function(e) {
                console.log("ERROR ", e);
            })
    }

    render() {
        const renderItems = this.state.items.map(function(item, i) {
            return (
                <div className="container" key={i} data-grid={{x: i % COLS, y: 0, w: 1, h: 1, static: true}}>
                    Estate name: {item.name} <br />
                    Price: {item.price} <br />
                    Owner: {item.estateOwnerHexString}
                </div>
            )
        });

        return (
            <GridLayout className="layout" cols={COLS} rowHeight={200} width={800}>
                {renderItems}
            </GridLayout>
        )
    }
}

export default MyFirstGrid;