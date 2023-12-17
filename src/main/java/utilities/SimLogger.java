package utilities;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import types.EventType;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class SimLogger {
    private static final ExecutorService providerThread = Executors.newSingleThreadExecutor();
    private static Channel channel;

    static {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            EventType[] eventTypes = EventType.values();
            for (EventType eventType : eventTypes) {
                String queueName = eventType.name();
                channel.queueDeclare(queueName, false, false, false, null);
                channel.queuePurge(queueName);
            }
        } catch (IOException | TimeoutException e) {
            System.err.println("Error connecting to RabbitMQ: " + e.getMessage());
        }
    }

    public static void sendMessage(EventType event, String message) {
        providerThread.execute(() -> {
            try {
                channel.queueDeclare(event.name(), false, false, false, null);
                channel.basicPublish("", event.name(), false, null, message.getBytes());
            } catch (IOException e) {
                System.err.println("Error sending message to RabbitMQ: " + e.getMessage());
            }
        });
    }
}
