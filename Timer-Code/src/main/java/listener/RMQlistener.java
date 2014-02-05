package listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class RMQlistener implements Runnable{
    public static Logger logger = LoggerFactory.getLogger(RMQlistener.class) ;

    public void run() {
        try
        {	
        	logger.info("Started Listening RMQ Now");
            String queueName = "cts_job" ;
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection;
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            final QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName,true,consumer);


            boolean autoAck = false;
            channel.basicConsume(queueName,autoAck,"JobMessageTag",
                    new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                long deliveryTag = envelope.getDeliveryTag();
                logger.info("Delivery tag is -------- " + deliveryTag);
                try{
                    String message = new String(body);
                    logger.info("--------GETTING THE MESSAGE NOW-------------" + message);
                    //SchedulerThread.SchedulerThread_(message, scheduler);
                      CreateJob.createJob(message);
                    channel.basicAck(deliveryTag, false);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
          }
       );

        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
