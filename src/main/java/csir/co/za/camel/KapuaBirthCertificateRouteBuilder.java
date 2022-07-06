package csir.co.za.camel;

import csir.co.za.plug.KuraPayloadUtilityPlug;
import csir.co.za.plug.PlugDevice;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.paho.PahoConstants;
import org.eclipse.kura.core.message.protobuf.KuraPayload;
import org.jboss.logging.Logger;
import csir.co.za.device.Device;
import csir.co.za.device.KuraPayloadUtility;

import javax.enterprise.context.ApplicationScoped;

/** Reference examples:
 * https://github.com/apache/camel-examples/blob/main/examples/main-artemis/src/main/java/org/apache/camel/example/MyRouteBuilder.java
 * https://stackoverflow.com/questions/26462048/apache-camel-rabbitmq-how-to-send-messages-objects
 */

@ApplicationScoped //makes one scope of the class
public class KapuaBirthCertificateRouteBuilder extends RouteBuilder {



    private static final Logger LOG = Logger.getLogger(KapuaBirthCertificateRouteBuilder.class);
    private PropertiesUtility propertiesUtility = new PropertiesUtility();
    @Override
    public void configure() throws Exception {
        String kapuaUri = "paho:" + propertiesUtility.getMqttBrokerTopic() + "?" +
                "brokerUrl=" + propertiesUtility.getMqttBrokerUrl() +
                "&userName=" + propertiesUtility.getMqttBrokerUserName() +
                "&password=" + propertiesUtility.getMqttBrokerPassword() +
                "&qos=2" +
                "&cleanSession=true" +
                "&automaticReconnect=true" +
                "&clientId=" + propertiesUtility.getMqttBrokerClientId();

        String rabbitMqUri = "rabbitmq:" + propertiesUtility.getAmqpBrokerExchange() + "?" +
                "hostname=" + propertiesUtility.getAmqpBrokerHost() +
                "&portNumber=" + propertiesUtility.getAmqpBrokerPort() +
                "&username=" + propertiesUtility.getAmqpBrokerUserName() +
                "&password=" + propertiesUtility.getAmqpBrokerPassword() +
                "&autoDelete=false" +
                "&durable=true" +
                "&queue=" + propertiesUtility.getAmqpBrokerQueue();

        String kafkaUri = "kafka:" + propertiesUtility.getKafkaTopic() + "?" +
                "brokers=" + propertiesUtility.getKafkaBrokers() +
                "&clientId=" + propertiesUtility.getKafkaClientId();

        LOG.info("Configuring Camel Route ... uri = " + kapuaUri);

        from(kapuaUri)
                .process(databaseStorageProcessor)
        //.process(rabbitMqPreparationProcessor)
        //.to(rabbitMqUri)
        //.process(kafKaPreparationProcessor)
        //.to(kafkaUri)
        ;
    }

    private Processor databaseStorageProcessor = new Processor() {


        @Override
        public void process(Exchange exchange) throws Exception {
            CharSequence TYPE_PLUG = "plug";


            LOG.info("Database Storage Processor processing received data ... ");

            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);

            KuraPayloadUtility kuraPayloadUtility = new KuraPayloadUtility();

            String topic = (String) header;
            Message camelMessage = exchange.getIn();
            byte[] body = (byte[]) camelMessage.getBody();

            String deviceId = kuraPayloadUtility.getDeviceId(topic);
            KuraPayload kuraPayload = kuraPayloadUtility.getKuraPayload(body);
            PlugDevice plugDevice = new PlugDevice();
            if (null != kuraPayload) {
                LOG.info("Deserialization of KuraPayload successful... for "+deviceId);
                if (deviceId.toLowerCase().contains(TYPE_PLUG)){

                    KuraPayloadUtilityPlug kuraPayloadUtilityPlug = new KuraPayloadUtilityPlug();
                    plugDevice = kuraPayloadUtilityPlug.updatePlugDeviceBirthCertificateDetails (new PlugDevice(), kuraPayload);
                    LOG.info("device info = " + plugDevice.toString());
                    plugDevice.setPlugDeviceKey(deviceId);
                    PlugDevice.createDevice(plugDevice);
                }

            } else {
                LOG.warn("Deserialization of KuraPayload failed...");
            }

            String deviceAsJsonString = plugDevice.toJsonString();
            LOG.info("Topic = " + topic);
            LOG.info("Device Id = " + deviceId);
            LOG.info("Device = " + deviceAsJsonString);
            exchange.getIn().setBody(deviceAsJsonString);

        }
    };


    private Processor rabbitMqPreparationProcessor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("RabbitMQ Preparation Processor processing received data ... ");

            Message camelMessage = exchange.getIn();
            String jsonPayload = (String) camelMessage.getBody();

            /** Either comment line below if no changes are to be done
             * on the received object before it is sent to RabbitMQ,
             * Or add the necessary code to update the JSON string and
             * then call exchange.getIn().setBody(), passing it the
             * updated JSON string.
             */
            exchange.getIn().setBody(jsonPayload);
        }
    };

    private Processor kafKaPreparationProcessor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("KafKa Preparation Processor processing received data ... ");

            Message camelMessage = exchange.getIn();
            String jsonPayload = (String) camelMessage.getBody();

            /** Either comment line below if no changes are to be done
             * on the received object before it is sent to kafka,
             * Or add the necessary code to update the JSON string and
             * then call exchange.getIn().setBody(), passing it the
             * updated JSON string.
             */
            exchange.getIn().setBody(jsonPayload);
        }
    };
}
