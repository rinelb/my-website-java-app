package csir.co.za.camel;

import org.eclipse.microprofile.config.ConfigProvider;

/**
 * This class provides access to properties from classes that are
 * neither CDI beans or JAX-RS, which means that such classes cannot
 * use @Inject and @ConfigProperty to access the properties.
 */
public class PropertiesUtility {
    private String mqttBrokerUrl;
    private String mqttBrokerUserName;
    private String mqttBrokerPassword;
    private String mqttBrokerTopic;
    private String mqttBrokerFirmwareTopic;
    private String mqttBrokerClientId;
    private String mqttBrokerStatusClientId;
    private String mqttBrokerPlugDataTopic;

    private String amqpBrokerExchange;
    private String amqpBrokerHost;
    private String amqpBrokerPort;
    private String amqpBrokerUserName;
    private String amqpBrokerPassword;
    private String amqpBrokerQueue;

    private String kafkaTopic;
    private String kafkaBrokers;
    private String kafkaClientId;

    private String hawkbitUrl;
    private String hawkbitDeviceCreateUrl;
    private String hawkbitDeviceDetailUrl;
    private String hawkbitDeviceAcessToken;
    private String hawkbitDeviceManagamentUrl;

    public PropertiesUtility() {
        mqttBrokerUrl = ConfigProvider.getConfig().getValue("mqtt.broker.url", String.class);
        mqttBrokerUserName = ConfigProvider.getConfig().getValue("mqtt.broker.username", String.class);
        mqttBrokerPassword = ConfigProvider.getConfig().getValue("mqtt.broker.password", String.class);
        mqttBrokerTopic = ConfigProvider.getConfig().getValue("mqtt.broker.topic", String.class);
        mqttBrokerStatusClientId = ConfigProvider.getConfig().getValue("mqtt.broker.status.client.id", String.class);
        mqttBrokerClientId = ConfigProvider.getConfig().getValue("mqtt.broker.client.id", String.class);
        mqttBrokerFirmwareTopic = ConfigProvider.getConfig().getValue("mqtt.broker.firmware.topic", String.class);
        mqttBrokerPlugDataTopic = ConfigProvider.getConfig().getValue("mqtt.broker.plug.data.topic", String.class);



        amqpBrokerExchange = ConfigProvider.getConfig().getValue("amqp.broker.exchange", String.class);
        amqpBrokerHost = ConfigProvider.getConfig().getValue("amqp.broker.host", String.class);
        amqpBrokerPort = ConfigProvider.getConfig().getValue("amqp.broker.port", String.class);
        amqpBrokerUserName = ConfigProvider.getConfig().getValue("amqp.broker.username", String.class);
        amqpBrokerPassword = ConfigProvider.getConfig().getValue("amqp.broker.password", String.class);
        amqpBrokerQueue = ConfigProvider.getConfig().getValue("amqp.broker.queue", String.class);

        kafkaTopic = ConfigProvider.getConfig().getValue("kafka.topic", String.class);
        kafkaBrokers = ConfigProvider.getConfig().getValue("kafka.brokers", String.class);
        kafkaClientId = ConfigProvider.getConfig().getValue("kafka.client.id", String.class);


        hawkbitUrl = ConfigProvider.getConfig().getValue("hawkbit.url", String.class);
        hawkbitDeviceCreateUrl = ConfigProvider.getConfig().getValue("hawkbit.device.create.url", String.class);
        hawkbitDeviceDetailUrl = ConfigProvider.getConfig().getValue("hawkbit.device.detail.url", String.class);
        hawkbitDeviceAcessToken = ConfigProvider.getConfig().getValue("hawkbit.device.acess.token", String.class);
        hawkbitDeviceManagamentUrl = ConfigProvider.getConfig().getValue("hawkbit.device.managament.url", String.class);


    }


    public String getHawkbitUrl() {
        return hawkbitUrl;
    }

    public String getHawkbitDeviceManagamentUrl() { return hawkbitDeviceManagamentUrl; }

    public String getHawkbitDeviceCreateUrl() {
        return hawkbitDeviceCreateUrl;
    }

    public String getHawkbitDeviceDetailUrl() {
        return hawkbitDeviceDetailUrl;
    }

    public String getHawkbitDeviceAcessToken() {
        return hawkbitDeviceAcessToken;
    }

    public String getMqttBrokerUrl() {
        return mqttBrokerUrl;
    }

    public String getMqttBrokerPlugDataTopic() {
        return mqttBrokerPlugDataTopic;
    }

    public String getMqttBrokerUserName() {
        return mqttBrokerUserName;
    }

    public String getMqttBrokerPassword() {
        return mqttBrokerPassword;
    }

    public String getMqttBrokerTopic() { return mqttBrokerTopic;    }

    public String getMqttBrokerFirmwareTopic() { return mqttBrokerFirmwareTopic; }

    public String getMqttBrokerClientId() {
        return mqttBrokerClientId;
    }

    public String getMqttBrokerStatusClientId() {
        return mqttBrokerStatusClientId;
    }

    public String getAmqpBrokerExchange() {
        return amqpBrokerExchange;
    }

    public String getAmqpBrokerHost() {
        return amqpBrokerHost;
    }

    public String getAmqpBrokerPort() {
        return amqpBrokerPort;
    }

    public String getAmqpBrokerUserName() {
        return amqpBrokerUserName;
    }

    public String getAmqpBrokerPassword() {
        return amqpBrokerPassword;
    }

    public String getAmqpBrokerQueue() {
        return amqpBrokerQueue;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public String getKafkaBrokers() {
        return kafkaBrokers;
    }

    public String getKafkaClientId() {
        return kafkaClientId;
    }
}

