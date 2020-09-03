package net.valux.legacy.proxy.messaging;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

public class Mqtt implements MqttCallback {

    private MqttClient client;

    private String host;
    private String clientId;
    private MemoryPersistence persistence;

    private MqttConnectOptions options;
    private int qos;

    public Mqtt() {

        this.host = "tcp://###";
        this.clientId = "###";
        this.persistence = new MemoryPersistence();

        this.options = new MqttConnectOptions();
        this.options.setUserName("###");
        this.options.setPassword("###".toCharArray());
        this.options.setCleanSession(true);
        this.qos = 2;

        try {

            this.client = new MqttClient(this.host, this.clientId, this.persistence);
            connect();
            this.client.setCallback(this);
            this.client.subscribe("#");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void connectionLost(Throwable t) {
        System.out.println("[Proxy] Connection has been lost to the MQTT server on ###");
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.print("Successfully delivered message for " + token.getTopics() + " containing: " + new String(token.getMessage().getPayload()));
        } catch (Exception e) {

        }
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.print("Received a message from " + topic + " containing: " + new String(message.getPayload()));
    }


    public boolean connect() {

        try {
            this.client.connect(this.options);

            System.out.print("[Proxy] Successfully connected to the MQTT server on 193.70.41.171");
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void disconnect() {
        try {
            this.client.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String channel, String message) {

        if (!this.client.isConnected()) {
            connect();
        }

        MqttMessage m = new MqttMessage(message.getBytes());
        m.setQos(this.qos);
        try {
            this.client.publish(channel, m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
