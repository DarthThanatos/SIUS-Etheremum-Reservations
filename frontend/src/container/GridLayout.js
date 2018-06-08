import GridLayout from 'react-grid-layout';
import React, { Component } from 'react';
import axios from 'axios'
import ReserveEstateModal from './ReserveEstateModal'
import baseUrl from './Utils'
import PayForReservationModal from "./PayForReservationModal";


var COLS=4;

class MyFirstGrid extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: [],
            owner: '',
            id: -1,
            reservationModalIsOpen: false,
            payReservationModalIsOpen: false
        }
        this.openReservationModal = this.openReservationModal.bind(this);
        this.openPayReservationModal = this.openPayReservationModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }


    componentDidMount() {
        var _this = this;

        //http://localhost:8080/estates/main
        axios.get(baseUrl + "/estates/main")
            .then(function(res){
                _this.setState({
                    items: res.data
                });
            })
            .catch(function(e) {
                console.log("ERROR: ", e);
            })
    }


    openReservationModal(id, owner, e) {
        this.setState({reservationModalIsOpen: true,
                       owner: owner,
                       id: id});
        e.preventDefault()
    }

    openPayReservationModal(id, owner, e) {
        this.setState({payReservationModalIsOpen: true,
            owner: owner,
            id: id});
        e.preventDefault()
    }


    afterOpenModal() {
        // references are now sync'd and can be accessed.
    }


    closeModal() {
        this.setState({reservationModalIsOpen: false,
                       payReservationModalIsOpen: false});
    }


    render() {
        const renderItems = this.state.items.map((item, i) => {
            const weekdays = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

            const available = item.daysAvailabilityStates.map(function(item, j) {
                if (item)
                    return weekdays[j] + " "
            });

            const reserved = item.daysReservationStates.map(function(item, j) {
                if (item)
                    return weekdays[j] + " "
            });

            const tenantsNames = item.tenantsNames.map(function(item, j) {
                if(item)
                    return weekdays[j] + ": " + item + " "
            });

            const owner = item.estateOwnerHexString;
            const id = item.estateIndex;


            return (
                <div className="container" key={i} data-grid={{x: i % COLS, y: 0, w: 1, h: 1, static: true}}>
                    Estate name: {item.name} <br />
                    Price: {item.price} <br />
                    Owner: {owner} <br />
                    Available: {available} <br />
                    Reserved: {reserved} <br />
                    Tenants names: {tenantsNames} <br />
                    <button onClick={(e) => this.openReservationModal(id, owner, e)}>Reserve</button>
                    <button onClick={(e) => this.openPayReservationModal(id, owner, e)}>Pay reservation</button>
                </div>

            )
        });

        return (
            <div>
                <GridLayout className="layout" cols={COLS} rowHeight={300} width={window.innerWidth}>
                    {renderItems}
                </GridLayout>
                <PayForReservationModal
                    isOpen={this.state.payReservationModalIsOpen}
                    onClose={() => this.closeModal()}
                    owner={this.state.owner}
                    id={this.state.id}>

                    <h2> Pay for reservation modal </h2>
                    <p><button onClick={() => this.closeModal()}>Close</button></p>

                </PayForReservationModal>

                <ReserveEstateModal
                    isOpen={this.state.reservationModalIsOpen}
                    onClose={() => this.closeModal()}
                    owner={this.state.owner}
                    id={this.state.id}
                >
                    <h2> Reserve estate modal </h2>
                    <p><button onClick={() => this.closeModal()}>Close</button></p>
                </ReserveEstateModal>
            </div>
        )
    }
}

export default MyFirstGrid;