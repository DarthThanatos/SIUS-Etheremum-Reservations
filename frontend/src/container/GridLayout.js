import GridLayout from 'react-grid-layout';
import React, { Component } from 'react';
import axios from 'axios'
import ReserveEstateModal from './ReserveEstateModal'
import baseUrl from './Utils'
import PayForReservationModal from "./PayForReservationModal";
import AccountDetailsPanel from "./AccountDetailsPanel";
import AddEstateInputForm from "./AddEstateInputForm";
import SendPanel from "./SendPanel";
import MintCurrency from "./MintCurrency";
import AddEstateModal from "./AddEstateModal";
import WS from "./WS";


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
        window.location.reload()
        this.setState({reservationModalIsOpen: false,
                       payReservationModalIsOpen: false});
    }


    render() {
        var COLS= Math.floor((window.innerWidth - 10) / 350);
        let id = 0;
        console.log(COLS)
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
            const eid = item.estateIndex;

            id = id + 1;

            if(id % COLS === 0)
                id = id + 1;
            if(id % COLS === COLS - 1)
                id = id + 2;
            console.log("elem i: " + i + " id: " + id + "y " + Math.floor(id / COLS))

            return (
                <div className="container" key={id} data-grid={{x: id % COLS,
                    y: Math.floor(id / COLS), w: 1, h: 1, static: true}}>
                    <h4>{item.name} </h4>
                    <p>
                    Price: {item.price} <br />
                    Owner: {owner} <br />
                    Available: {available} <br />
                    Reserved: {reserved} <br />
                    Tenants names: {tenantsNames} <br />
                        <div className="estBtnContainer">
                    <button className="btn btn-primary est-btn" onClick={(e) => this.openReservationModal(eid, owner, e)}>Reserve</button>
                    <button className="btn btn-primary est-btn" onClick={(e) => this.openPayReservationModal(eid, owner, e)}>Pay reservation</button>
                        </div>
                    </p>
                </div>

            )
        });

        return (
            <div>
                <GridLayout className="layout" cols={COLS} rowHeight={300} width={(window.innerWidth - 10)}>
                    <div className="container" key={0} data-grid={{x: 0 % COLS, y: 0, w: 1, h: 2, static: true}}>
                        <AccountDetailsPanel/>
                    </div>
                    <div className="container" key={2 * COLS} data-grid={{x: 0 % COLS, y: 2, w: 1, h: 2, static: true}}>
                        <AddEstateInputForm />
                    </div>
                    <div className="container" key={COLS - 1} data-grid={{x: COLS - 1, y: 0, w: 1, h: 2, static: true}}>
                        <SendPanel/>
                    </div>
                    <div className="container" key={4 * COLS - 1} data-grid={{x: COLS - 1, y: 2, w: 1, h: 2, static: true}}>
                        <MintCurrency/>
                    </div>
                    <div className="container" key={6 * COLS - 1} data-grid={{x: COLS - 1, y: 4, w: 1, h: 1, static: true}}>
                        <WS/>
                    </div>
                    {renderItems}
                </GridLayout>
                <PayForReservationModal
                    isOpen={this.state.payReservationModalIsOpen}
                    onClose={() => this.closeModal()}
                    owner={this.state.owner}
                    id={this.state.id}>

                    <h2> Pay for reservation modal </h2>

                </PayForReservationModal>

                <ReserveEstateModal
                    isOpen={this.state.reservationModalIsOpen}
                    onClose={() => this.closeModal()}
                    owner={this.state.owner}
                    id={this.state.id}
                >
                    <h2> Reserve estate modal </h2>
                </ReserveEstateModal>
            </div>
        )
    }
}

export default MyFirstGrid;