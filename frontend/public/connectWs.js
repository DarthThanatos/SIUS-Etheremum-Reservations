
/**
 * Open the web socket connection and subscribe the "/topic/events" channel.
 */
function connect() {

  // Create and init the SockJS object
  var socket = new SockJS("/ws");
  console.log("socket")
  var stompClient = Stomp.over(socket);
  console.log("stomp over")
  stompClient.connect({}, function(frame) {
    console.log("connected")
    stompClient.subscribe('/events', function(notification) {

      // Call the notify function when receive a notification
      var msg = notification.body;
      console.log("Got notification " + msg)
      notify(msg);

    });

    // stompClient.send("/app/send/event", {}, "message");
    // ^ this way a client can prompt websocket notification via endpoint defined in NotificationController.java

  });

}

/**
 * Display the notification message.
 */
function notify(message) {
  $("#notifications-area").append(message + "\n\n=========================================================================================\n\n");
  return;
}

/**
 * Init operations.
 */
$(document).ready(function() {

  // Start the web socket connection.
  connect();

});