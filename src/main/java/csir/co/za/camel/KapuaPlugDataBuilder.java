package csir.co.za.camel;




import csir.co.za.plug.PlugData;
import csir.co.za.plug.PlugDevice;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.paho.PahoConstants;
import org.eclipse.kura.core.message.protobuf.KuraPayload;
import org.jboss.logging.Logger;
import csir.co.za.device.Device;
import csir.co.za.plug.KuraPayloadUtilityPlug;

import javax.enterprise.context.ApplicationScoped;

/** Reference examples:
 * https://github.com/apache/camel-examples/blob/main/examples/main-artemis/src/main/java/org/apache/camel/example/MyRouteBuilder.java
 * https://stackoverflow.com/questions/26462048/apache-camel-rabbitmq-how-to-send-messages-objects
 */

@ApplicationScoped
public class KapuaPlugDataBuilder extends RouteBuilder{

    private static final Logger LOG = Logger.getLogger(KapuaPlugDataBuilder.class);
    private PropertiesUtility propertiesUtility = new PropertiesUtility();

    @Override
    public void configure() throws Exception {
        String kapuaPlugDataUri = "paho:" + propertiesUtility.getMqttBrokerPlugDataTopic() + "?" +
                "brokerUrl=" + propertiesUtility.getMqttBrokerUrl() +
                "&userName=" + propertiesUtility.getMqttBrokerUserName() +
                "&password=" + propertiesUtility.getMqttBrokerPassword() +
                "&qos=2" +
                "&cleanSession=true" +
                "&automaticReconnect=true" +
                "&clientId=" + propertiesUtility.getMqttBrokerClientId();

        //frontend using kapua
//        String kapuaPlugDataFrontEnd = "paho:" + "kapua-sys/${header.userName}/frontEnd/data" + "?" +
//                "brokerUrl=" + propertiesUtility.getMqttBrokerUrl() +
//                "&userName=" + propertiesUtility.getMqttBrokerUserName() +
//                "&password=" + propertiesUtility.getMqttBrokerPassword() +
//                "&qos=2" +
//                "&cleanSession=true" +
//                "&automaticReconnect=true" +
//                "&clientId=" + "frontend-data";

        //tcp://146.64.8.98:1883
        String kapuaPlugDataFrontEnd = "paho:" + "kapua-sys/plug0/frontEnd/data" + "?" +
                "brokerUrl=tcp://broker.emqx.io:"+1883 +//+ propertiesUtility.getMqttBrokerUrl() +
                "&qos=0" +
                "&cleanSession=true" +
                "&automaticReconnect=true" +
                "&clientId=" + "rinel-frontend-data";







//        String rabbitMqUri = "rabbitmq:" + propertiesUtility.getAmqpBrokerExchange() + "?" +
//                "hostname=" + propertiesUtility.getAmqpBrokerHost() +
////                "&portNumber=" + propertiesUtility.getAmqpBrokerPort() +
////                "&username=" + propertiesUtility.getAmqpBrokerUserName() +
////                "&password=" + propertiesUtility.getAmqpBrokerPassword() +
//                "&autoDelete=false" +
//                "&durable=true" +
//                "&queue=" + propertiesUtility.getAmqpBrokerQueue();
        String rabbitMqUri ;
//        String rabbitMqUri = "rabbitmq:tasks?hostname=localhost&port=5672&autoDelete=false&routingKey=camel";

        //this is working code
        //rabbitMqUri = "rabbitmq:localhost:5672/amq.direct?username=quarkus&password=guest&routingKey=camel&queue=test&autoDelete=false&durable=true";
        //rabbitMqUri = "rabbitmq:amq.direct?hostname=localhost&port=5672&username=guest&password=guest&routingKey=camel&queue=test&autoDelete=false&durable=true";


        //rabbitMqUri = "rabbitmq:localhost:5672/amq.direct?username=guest&password=guest&routingKey=camel&queue=plug0&autoDelete=false&durable=true";


        rabbitMqUri = "rabbitmq:localhost:5672/amq.direct?username=guest&password=guest&immediate=false&routingKey=camel&queue=${header.userName}&autoDelete=false&durable=true";


        String kafkaUri = "kafka:" + propertiesUtility.getKafkaTopic() + "?" +
                "brokers=" + propertiesUtility.getKafkaBrokers() +
                "&clientId=" + propertiesUtility.getKafkaClientId();

        LOG.info("Configuring Camel Route ... uri = " + kapuaPlugDataUri);

//        from(kapuaPlugDataUri)
//                .process(plugDataStorage)
////        .process(rabbitMqPreparationProcessor)
//        //.toD(rabbitMqUri)
//        .toD(kapuaPlugDataFrontEnd)
        //.process(kafKaPreparationProcessor)
        //.to(kafkaUri)
        ;
    }


    private Processor rabbitMqPreparationProcessor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("RabbitMQ Preparation Processor processing received data ... ");
            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);

            Message camelMessage = exchange.getIn();
            String jsonPayload = (String) camelMessage.getBody();
            String topic = (String) header;
            LOG.info("message to send = " + jsonPayload);
            exchange.getIn().setBody(jsonPayload);
            String[] topicSegments = topic.split("/");
            LOG.info("message to userName = " + topicSegments[1]);
            exchange.getIn().setHeader("userName", topicSegments[1]);
            /** Either comment line below if no changes are to be done
             * on the received object before it is sent to RabbitMQ,
             * Or add the necessary code to update the JSON string and
             * then call exchange.getIn().setBody(), passing it the
             * updated JSON string.
             */

        }
    };


    private final Processor plugDataStorage = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("Database Storage for plug data ... ");

            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);

            KuraPayloadUtilityPlug kuraPayloadUtility = new KuraPayloadUtilityPlug();

            PlugData plugData = new PlugData();
            String topic = (String) header;
            Message camelMessage = exchange.getIn();
            byte[] body = (byte[]) camelMessage.getBody();

            String[] topicSegments = topic.split("/");
            PlugDevice findPlugDevice = new PlugDevice() ;
            LOG.info("start finding plug from db");

            Long plugID =null;
            LOG.info(" plugID = PlugDevice.getPlugIDPostgre(topicSegments[1]); ");
            plugID = PlugDevice.getPlugIDPostgre(topicSegments[1]);
            LOG.info("plugID = "+plugID);
            if (plugID == null) {
                KuraPayload kuraPayload = kuraPayloadUtility.getKuraPayload(body);

                if (null != kuraPayload) {
                    LOG.info("Deserialization of KuraPayload successful...");
                    plugData = kuraPayloadUtility.getPlugData(new PlugData(), kuraPayload);
                    plugData.setPlugDeviceId(plugID);
                    plugData.insertData(plugData);
                } else {
                    LOG.warn("Deserialization of KuraPayload failed...");
                }

                String deviceAsJsonString = plugData.toJsonString();
                LOG.info("Topic = " + topic);
                LOG.info("Device Id = " + plugData.getId() + " = ");
                LOG.info("Device = " + deviceAsJsonString);
                exchange.getIn().setBody(deviceAsJsonString);
            }

        }
    };


}
