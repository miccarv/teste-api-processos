const amqp = require("amqplib/callback_api");

module.exports = async function sendMessage(message) {
  amqp.connect("amqp://localhost", function (err, connection) {
    if (err) {
      throw err;
    }

    const queue = "processos";

    connection.createChannel(function (err, channel) {
      if (err) {
        throw err;
      }

      channel.purgeQueue(queue);

      channel.assertQueue(queue, {
        durable: false,
      });

      channel.sendToQueue(queue, Buffer.from(message));
      console.log(" [x] Sent %s", message);
    });

    setTimeout(function () {
      connection.close();
    }, 500);
  });
};
