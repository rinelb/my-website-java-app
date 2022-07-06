package csir.co.za.mqtt;


        import com.google.protobuf.StringValue;
        import org.apache.commons.io.IOUtils;
        import org.eclipse.kura.core.message.protobuf.KuraPayload;
        import org.eclipse.paho.client.mqttv3.MqttClient;

        import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
        import org.eclipse.paho.client.mqttv3.MqttException;
        import org.eclipse.paho.client.mqttv3.MqttMessage;
        import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


        import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
        import org.eclipse.paho.client.mqttv3.MqttCallback;


        import java.io.*;
        import java.nio.charset.StandardCharsets;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.sql.Timestamp;
        import java.util.*;

        import com.google.protobuf.ByteString;
        import org.jboss.logging.Logger;

//time
        import javax.enterprise.context.ApplicationScoped;
        import javax.print.DocFlavor;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.time.Instant;
        import java.time.LocalDateTime;
        import java.time.ZoneId;


public class Mqtt {
//	public static void main(String... args) {
//        Quarkus.run(MyApp.class, args);
//    }
//
//    public static class MyApp implements QuarkusApplication {
    // TODO Auto-generated method stub


    private static final Logger LOG = Logger.getLogger(Mqtt.class);


    String topicBirth        = "$EDC/kapua-sys/+/MQTT/BIRTH";
    String subscribeTopic = "kapua-sys/+/firmware_status";
    int count = 0;


    private   MqttClient sampleClient;
    public final void connect() {
        String content      = "Message from MqttPublishSample";
        int qos             = 0;
        String broker       = "tcp://146.64.8.98:1883";
        Random rand = new Random();
        int randInt = (int)Math.floor(Math.random()*(99999-10000+1)+10000);
        String clientId     = "client-DFMS" + randInt;
        String username 	 = "kapua-sys";
        String password 	 = "kapua-password";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());

            sampleClient.connect(connOpts);
            LOG.info("Connected");
            sampleClient.setCallback(new MqttCallback() {

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    LOG.info("\nReceived a Message! in main method" +
                            "\n\tTopic:   " + topic +
                            "\n\tMessage: " + new String(message.getPayload()) +
                            "\n\tQoS:     " + message.getQos() + "\n");
                    // find the topic type
                    if (topic.contains("firmware_status")){
                        //Device.updateDeviceStatusMqttVar()
                    }



                }

                public void connectionLost(Throwable cause) {
                    LOG.info("Connection to broker lost!" + cause.getMessage());
                    connect();

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }

            });
            //LOG.info("Message subscribe");
            //sampleClient.subscribe(subscribeTopic, 0);
            //sampleClient.subscribe(subscribeTopic,0);





            //LOG.info("Message published");

        } catch(MqttException me) {
            LOG.info("reason "+me.getReasonCode());
            LOG.info("msg "+me.getMessage());
            LOG.info("loc "+me.getLocalizedMessage());
            LOG.info("cause "+me.getCause());
            LOG.info("excep "+me);
            me.printStackTrace();
        }

    }

    public void close(){
        try {
            sampleClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage( String id,String message){

        //get time
        Date today = Calendar.getInstance().getTime();

        // Constructs a SimpleDateFormat using the given pattern
        SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");

        // format() formats a Date into a date/time string.
        String currentTime = crunchifyFormat.format(today);
        LOG.info("Current Time = " + currentTime);
        long epochTime = 0;
        try {

            // parse() parses text from the beginning of the given string to produce a date.
            Date date = crunchifyFormat.parse(currentTime);

            // getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
            epochTime = date.getTime();

            LOG.info("Current Time in Epoch: " + epochTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //end get time


//        MqttMessage messageFile = new MqttMessage();
//        messageFile.setQos(0);
//
//        messageFile.setPayload(message);
//        try {
//            if (messageType.equalsIgnoreCase("firmware_available")) {
//                String sendTopic = "kapua-sys/"+device.getDeviceId()+"/firmware";
//                sampleClient.publish(sendTopic, messageFile);
//                LOG.info("message sent");
//            }
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }



//        com.google.protobuf.ByteString byteString = ByteString.copyFrom(a);
      //  com.google.protobuf.StringValue vauleString = com.google.protobuf.StringValue.of(message);
        KuraPayload.Builder fileMessage = KuraPayload.newBuilder();
        fileMessage.setTimestamp(epochTime);

        KuraPayload.KuraMetric.Builder matric = KuraPayload.KuraMetric.newBuilder();

        //working byte code
        matric.setType(KuraPayload.KuraMetric.ValueType.STRING);
        matric.setStringValue(message);

//        matric.setType(KuraPayload.KuraMetric.ValueType.INT32);
//        count = count +1;
//        matric.setIntValue(count);

        matric.setName("pro");
        matric.build();
        fileMessage.addMetric(matric);

        MqttMessage messageFile1 = new MqttMessage();
        messageFile1.setQos(0);
        String dummy = "Rinel";
        //messageFile1.setPayload(fileMessage.build().toByteArray());
        byte [] temp = fileMessage.build().toByteArray();
        messageFile1.setPayload(temp);
        try {
            String sendTopic = "kapua-sys/"+id+"/actuate";
            sampleClient.publish(sendTopic, messageFile1);
            LOG.info(sendTopic+ " Message = " + messageFile1.toString());
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }






    public static String convertByteToHexadecimal(byte[] byteArray)
    {
        String hex = "";

        // Iterating through each byte in the array
        for (byte i : byteArray) {
            hex += String.format("%02X ", i);
        }

        return hex;
    }



}

