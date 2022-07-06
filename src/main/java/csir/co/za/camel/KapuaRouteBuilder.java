package csir.co.za.camel;



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

@ApplicationScoped
public class KapuaRouteBuilder extends RouteBuilder {
    private static final Logger LOG = Logger.getLogger(KapuaBirthCertificateRouteBuilder.class);
    private PropertiesUtility propertiesUtility = new PropertiesUtility();

    @Override
    public void configure() throws Exception {


        String kapuaUriFirmware = "paho:" + propertiesUtility.getMqttBrokerFirmwareTopic() + "?" +
                "brokerUrl=" + propertiesUtility.getMqttBrokerUrl() +
                "&userName=" + propertiesUtility.getMqttBrokerUserName() +
                "&password=" + propertiesUtility.getMqttBrokerPassword() +
                "&qos=2" +
                "&cleanSession=true" +
                "&automaticReconnect=true" +
                "&clientId=" + propertiesUtility.getMqttBrokerStatusClientId();

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

        String kapuaDeviceFirmwareStatus = "paho:" + propertiesUtility.getMqttBrokerFirmwareTopic() + "?" +
                "brokerUrl=" + propertiesUtility.getMqttBrokerUrl() +
                "&userName=" + propertiesUtility.getMqttBrokerUserName() +
                "&password=" + propertiesUtility.getMqttBrokerPassword() +
                "&qos=2" +
                "&cleanSession=true" +
                "&automaticReconnect=true" +
                "&clientId=" + propertiesUtility.getMqttBrokerStatusClientId();





        LOG.info("Configuring Camel Route ... uri = " + kapuaUriFirmware);

        from(kapuaUriFirmware)
                .process(firmwareProcessor)

        //.process(rabbitMqPreparationProcessor)
        //.to(rabbitMqUri)
        //.process(kafKaPreparationProcessor)
        //.to(kafkaUri)
        ;

    }

//    private Processor databaseStorageProcessor = new Processor() {
//        @Override
//        public void process(Exchange exchange) throws Exception {
//            LOG.info("Database Storage Processor processing received data ... ");
//
//            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);
//
//            KuraPayloadUtility kuraPayloadUtility = new KuraPayloadUtility();
//
//            Device device = null;
//            String topic = (String) header;
//            Message camelMessage = exchange.getIn();
//            byte[] body = (byte[]) camelMessage.getBody();
//
//            String deviceId = kuraPayloadUtility.getDeviceId(topic);
//            KuraPayload kuraPayload = kuraPayloadUtility.getKuraPayload(body);
//
//            if (null != kuraPayload) {
//                LOG.info("Deserialization of KuraPayload successful...");
//                device = kuraPayloadUtility.updateDeviceBirthCertificateDetails (new Device(), kuraPayload);
//                device.setDeviceId(deviceId);
//                Device.createDevice(device);
//            } else {
//                LOG.warn("Deserialization of KuraPayload failed...");
//            }
//
//            String deviceAsJsonString = device.toJsonString();
//            LOG.info("Topic = " + topic);
//            LOG.info("Device Id = " + deviceId);
//            LOG.info("Device = " + deviceAsJsonString);
//            exchange.getIn().setBody(deviceAsJsonString);
//        }
//    };

    private Processor firmwareProcessor = new Processor() {
        @Override
        public void process(Exchange exchange) throws Exception {
            LOG.info("Mesage from topic Firmware_status ... ");

            Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);

            KuraPayloadUtility kuraPayloadUtility = new KuraPayloadUtility();

            Device device = null;
            String topic = (String) header;
            Message camelMessage = exchange.getIn();
            byte[] body = (byte[]) camelMessage.getBody();

            String deviceId = kuraPayloadUtility.getDeviceId(topic);
            KuraPayload kuraPayload = kuraPayloadUtility.getKuraPayload(body);

            if (null != kuraPayload) {
                LOG.info("Deserialization of KuraPayload successful...");
                device = kuraPayloadUtility.updateDeviceFirmwareStatusDetails(new Device(), kuraPayload);
                device.setDeviceId(deviceId);
                LOG.info("--------- Updating device " + device.getFirmwareStatus());
                switch (device.getFirmwareStatus()){
                    case "send_file":
                        Device.updateDeviceStringVar(device,"firmwareStatus",device.getFirmwareStatus());
                        break;
                    case "updated_successful":
                        Device.updateDeviceStringVar(device,"firmwareStatus",device.getFirmwareStatus());

                        break;
                    case "updated_failed":
                        LOG.info("updated_failed");
                        Device.updateDeviceStringVar(device,"firmwareStatus",device.getFirmwareStatus());

                        if (device.getFirwareError() != null) {
                            Device.updateDeviceStringVar(device, "errorMessage", device.getFirwareError());
                        }

                        break;
                }
            } else {
                LOG.warn("Deserialization of KuraPayload failed...");
            }

            String deviceAsJsonString = device.toJsonString();
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
