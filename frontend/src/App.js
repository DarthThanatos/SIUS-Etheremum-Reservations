import React, { Component } from 'react';
import Main from './container/Main';
import './css/App.css';
import SockJsClient from 'react-stomp';
import { CSSGrid, layout } from 'react-stonecutter';

const ReactDOM = require('react-dom')

class WS extends Component{
  constructor(props) {
    super(props);
    console.log("constructor")
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

			<br/>
			<br/>
			<p>
			    Notifications received on the web socket channel will be displayed below:
			</p>
			<textarea id="notifications-area" ref="notifications-area" cols="100" rows="10" readOnly="readonly">
			    {localStorage.getItem("eventsHistory")}
			</textarea>
			<br/>
			<button onClick={this.clearHistory}>Clean</button>
			<div>
				<SockJsClient url='http://localhost:8080/ws' topics={['/events']}
				onMessage={
                    (msg) => {
                        var history = localStorage.getItem( 'eventsHistory');
                        history = history == null ? "" : history;
                        console.log(msg)
                        console.log(history)
                        var txt = "Event: " + msg["eventName"] + "\n->" + "estatesOwnerAddress: " + msg["estatesOwnerAddressString"] + "\n->" + "estate name: " + msg["name"] + "\n->estate price: " + msg["price"] + "\n->estate index: " + msg["estateIndex"];
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

class App extends Component {

  render() {
    return (
      <div className="App">
        <Main />
        <WS/>
      </div>
    );
  }
}


export default App;
