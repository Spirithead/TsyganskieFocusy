package ru.mosit.pahotest;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import ru.mosit.pahotest.data.JsonData;
import ru.mosit.pahotest.data.Topic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PahoTest {
    // Параметры подключения к брокеру
    static String host = "192.168.1.13";
    static int ip = 13;
    static long delay = 5000L;
    static Integer port = 1883;
    static List<JsonData> jsonDataList = new ArrayList<>();
    static TimerTask task;

    public static void main(String[] args) throws MqttException {
        Timer timer = new Timer();

        // Создание MQTT клиента для подключения
        MqttClient client = new MqttClient(
                String.format("tcp://%s:%d", host, port),
                MqttClient.generateClientId());

        // Создание класса для формирования JSON
        var g = new Gson();
        // Объект для хранения данных
        var jsonData = new JsonData(0.0F, 0.0F, 0.0F, 0.0F, ip, null);

        // Создание колбэков для обработки событий, возникающих на клиенте
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("client lost connection " + cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                var payload = new String(message.getPayload());
                System.out.println(topic + ": " + payload);

                var param = Topic.fromValue(topic);

                if (param != null) {
                    jsonData.setDate(new Date());
                    switch (param) {
                        case CO2 -> jsonData.setCo2(Float.valueOf(payload));
                        case LIGHT -> jsonData.setLight(Float.valueOf(payload));
                        case TEMPERATURE -> jsonData.setTemperature(Float.valueOf(payload));
                        case SOUND -> jsonData.setSound(Float.valueOf(payload));
                    }
                } else
                    System.out.println("Not known topic");

                jsonDataList.add(new JsonData(jsonData));
                //writeUsingFiles(g.toJson(jsonDataList));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("delivery complete " + token);
            }
        });

        client.connect();

        // Подписывание на топики
        for (var topic : Topic.values()) {
            client.subscribe(topic.getValue(), 1);
        }

        task = new TimerTask() {
            @Override
            public void run() {
                writeUsingFiles(g.toJson(jsonDataList));
            }
        };

        timer.scheduleAtFixedRate(task, delay, delay);//writing data into json every 5 seconds
    }

    private static void writeUsingFiles(String data) {
        try {
            Files.write(Paths.get("data.json"), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
