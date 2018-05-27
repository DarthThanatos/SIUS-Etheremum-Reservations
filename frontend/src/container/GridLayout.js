import GridLayout from 'react-grid-layout';
import React, { Component } from 'react';
import axios from 'axios'
import ReserveEstateModal from './ReserveEstateModal'

var COLS=3;

class MyFirstGrid extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: [],
            owner: '',
            modalIsOpen: false
        }
        this.openModal = this.openModal.bind(this);
        this.afterOpenModal = this.afterOpenModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    componentDidMount() {
        var _this = this;
        // TODO: this endpoint gets all estates not only main
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

    openModal(owner, e) {
        this.setState({modalIsOpen: true,
                      owner: owner});
        e.preventDefault()
    }

    afterOpenModal() {
        // references are now sync'd and can be accessed.
    }

    closeModal() {
        this.setState({modalIsOpen: false});
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
            const owner = item.estateOwnerHexString;

            return (
                <div className="container" key={i} data-grid={{x: i % COLS, y: 0, w: 1, h: 1, static: true}}>
                    Estate name: {item.name} <br />
                    Price: {item.price} <br />
                    Owner: {owner} <br />
                    Available: {available} <br />
                    Reserved: {reserved}
                    <button onClick={(e) => this.openModal(owner, e)}>Reserve</button>
                </div>

            )
        })

        return (
            <div>
            <GridLayout className="layout" cols={COLS} rowHeight={200} width={800}>
                {renderItems}
            </GridLayout>
            <ReserveEstateModal
                isOpen={this.state.modalIsOpen}
                onClose={() => this.closeModal()}
                owner={this.state.owner}

            >
        Title

        <p><button onClick={() => this.closeModal()}>Close</button></p>
        </ReserveEstateModal>
            </div>
        )
    }
}

export default MyFirstGrid;