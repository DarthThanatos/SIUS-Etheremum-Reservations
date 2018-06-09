import React, { Component } from 'react';
import '../css/App.css';
import SockJsClient from 'react-stomp';
import { CSSGrid, layout } from 'react-stonecutter';

class WS extends Component{
    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    clearHistory(){
        console.log("clearing events history")
        localStorage.setItem("eventsHistory", "")
        document.getElementById("notifications-area").value = ""
    }

    render(){
        return (
            <div className="WS">
                <h2>
                    Notifications
                </h2>
                <p>
                    Notifications received on the web socket channel will be displayed below:
                </p>
                <textarea id="notifications-area" ref="notifications-area" cols="80" rows="10" readOnly="readonly">
                            {localStorage.getItem("eventsHistory")}
                        </textarea>
                <br/>
                <div className="btnContainer">
                <button className="btn btn-primary" onClick={this.clearHistory}>Clean</button>
                </div>
                    <div>
                    <SockJsClient url='http://localhost:8080/ws' topics={['/events']}
                                  onMessage={
                                      (msg) => {
                                            var history = localStorage.getItem( 'eventsHistory');
                                            history = history == null ? "" : history;
                                            console.log(msg)
                                            console.log(history)
                                            var txt = "Event: " + msg["eventName"] + "\n"
                                            for(var key in msg){
                                              txt += "-> " + key + ": " + msg[key] + "\n"
                                            }
                                            localStorage.setItem("eventsHistory", history + "\n==========================\n" + txt)
                                            alert("Got msg:\n " + txt);
                                      }
                                  }
                                  ref={ (client) => { this.clientRef = client }} />
                </div>
            </div>
        )
    }
}

export default WS;