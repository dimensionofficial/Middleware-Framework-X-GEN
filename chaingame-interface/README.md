RabbitMq

introduce

  Rabbitmq is a message broker: it accepts and forwards messages. You can think of it as a post office: when you want to post a letter, you put it in the delivery box and make sure that the postman will eventually deliver it to the recipient. In this case, rabbitmq is equivalent to a delivery box, post office, and postman.

  The core idea of AMQP protocol is that producers and consumers are isolated, and producers never send messages directly to queues. Producers often don't know if a message will be sent to the queue, just to a switch. It is received by exchange, and then exchange forwards to queue for storage according to specific policies. Similarly, the same is true for consumers. Exchange is similar to a switch, forwarding messages to corresponding queues.
  
  
Six working modes

Official website: https://www.rabbitmq.com/getstarted.html

Here is a brief introduction to the main features of the following six working modes:

1.Simple model: one producer, one consumer

2.Work mode: one producer, multiple consumers, each consumer gets a unique message.

3.Subscription mode: messages sent by one producer will be obtained by multiple consumers.

4.Routing mode: Send a message to the switch and specify the routing key. The consumer needs to specify the routing key when binding the queue to the switch

5.Topic pattern: match the routing key with a pattern. At this time, the queue needs to be bound to a pattern, where "ා" matches one or more words, and "*" only matches one word.
