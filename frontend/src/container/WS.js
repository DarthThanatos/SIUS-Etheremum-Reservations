import React, { Component } from 'react';
import '../css/App.css';
import '../css/WS.css';
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
            <div id="WS" className="WS" >
                <h2 style={{marginTop: 20}}>
                    Notifications
                </h2>
                <p style={{textAlign:"center"}}>
                    Notifications received on the web socket channel will be displayed below:
                </p>
                <div  style={{display:"flex", flexDirection:"row", justifyContent:"center"}}>
                    <textarea id="notifications-area" ref="notifications-area"  style={{width:"90%", height:200}} readOnly="readonly">
                                {localStorage.getItem("eventsHistory")}
                    </textarea>
                </div>
                <br/>
                <div className="btnContainer">
                <button className="btn btn-primary"  style={{marginBottom:20}} onClick={this.clearHistory}>Clean</button>
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