const socket = new WebSocket('ws://localhost:8080/lucky-numbers');
const num_messages = 1000
const msg_interval = 10 //millis

socket.addEventListener('open', (event) => {
  const sendRandomRequest = (count) => {
    const randomPlayers = Math.floor(Math.random() * 21);
    const requestType = Math.random() < 0.5 ? 'request.play' : 'request.ping';

    let request;
    if (requestType === 'request.play') {
      request = {
        "message_type": requestType,
        "players": randomPlayers
      };
    } else {
      request = {
        "id": count,
        "message_type": "request.ping",
        "timestamp": Date.now()
      };
    }

    socket.send(JSON.stringify(request));
  };

  let count = 1;
  const intervalId = setInterval(() => {
    if (count <= num_messages) {
      sendRandomRequest(count);
      count++;
    } else {
      clearInterval(intervalId);
    }
  }, msg_interval);

  socket.addEventListener('message', (event) => {
    console.log('Received message from server:', event.data);
  });

  socket.addEventListener('error', (event) => {
    console.error('WebSocket error:', event);
  });
});