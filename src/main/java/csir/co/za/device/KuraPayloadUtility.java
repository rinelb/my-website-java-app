package csir.co.za.device;

import com.google.protobuf.InvalidProtocolBufferException;
import csir.co.za.plug.PlugDevice;
import org.eclipse.kura.core.message.protobuf.KuraPayload;
import org.jboss.logging.Logger;

import java.sql.Timestamp;

public class KuraPayloadUtility {
    private static final Logger LOG = Logger.getLogger(KuraPayloadUtility.class);

    // COMMON METRICS
    public static final String METRIC_NAME_DEVICE_ID= "device_id";

    // COMMAND METRICS
    public static final String METRIC_NAME_PUBLISH_INTERVAL = "publish_interval";
    public static final String METRIC_NAME_VIBRATE = "vibrate";

    //STATUS METRICS
    public static final String METRIC_NAME_STATUS = "status";
    public static final String METRIC_NAME_Error = "error";

    // STATE METRICS
    public static final String METRIC_NAME_TEMPERATURE = "temperature";
    public static final String METRIC_NAME_BATTERY = "battery";
    public static final String METRIC_NAME_RSSI = "rssi";
    public static final String METRIC_NAME_TAMPER = "tamper";

    // BIRTH CERTIFICATE METRICS
    public static final String METRIC_NAME_FIRMWARE_VERSION = "firmware_version";
    public static final String METRIC_NAME_BIOS_VERSION = "bios_version";
    public static final String METRIC_NAME_OS_VERSION = "os_version";
    public static final String METRIC_NAME_MODEL_ID = "model_id";
    public static final String METRIC_NAME_MODEL_NAME = "model_name";
    public static final String METRIC_NAME_SERIAL_NUMBER = "serial_number";
    public static final String METRIC_NAME_UNITS = "units";

    public String getDeviceId(String mqttTopic) {
        String result = null;

        if (null != mqttTopic) {
            /** Kapua MQTT are of the following formats:
             *  1. CONTROL TOPICS Format: $EDC/account-name/device-id/semanticTopic
             *  2. NORMAL Format: account-name/device-id/semanticTopic
             */
            String[] topicSegments = mqttTopic.split("/");
            if (topicSegments.length >= 3) {
                if (topicSegments[0].trim().equalsIgnoreCase("$EDC")) {
                    result = topicSegments[2];
                } else {
                    result = topicSegments[1];
                }
            }
        }

        return result;
    }

    public KuraPayload getKuraPayload (byte[] data) {
        KuraPayload result = null;

        try {
            result = KuraPayload.parseFrom(data);
        } catch (InvalidProtocolBufferException t) {
            LOG.error("Failed to deserialize Kura Payload due to InvalidProtocolBufferException. " + t.getMessage());
        } catch (Throwable t) {
            LOG.error("Failed to deserialize Kura Payload. " + t.getMessage());
        }

        return result;
    }

    public Device updateDeviceBasicDetails(Device device, KuraPayload kuraPayload) {
        if (null != kuraPayload) {
            if (null == device) {
                device = new Device();
            }

            if (kuraPayload.hasTimestamp()) {
                device.setTimestamp(kuraPayload.getTimestamp());
            }

            if (kuraPayload.hasPosition()) {
                if (kuraPayload.getPosition().hasAltitude()) {
                    device.setAltitude(kuraPayload.getPosition().getAltitude());
                }

                if (kuraPayload.getPosition().hasLongitude()) {
                    device.setLongitude(kuraPayload.getPosition().getLongitude());
                }

                if (kuraPayload.getPosition().hasLatitude()) {
                    device.setLatitude(kuraPayload.getPosition().getLatitude());
                }
            }

            for (KuraPayload.KuraMetric kuraMetric : kuraPayload.getMetricList()) {
                if  (kuraMetric.hasName()) {
                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_DEVICE_ID)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setDeviceId(kuraMetric.getStringValue());
                        }
                    }
                }
            }
        }

        return device;
    }


    public Device updatePlugDeviceBasicDetails(Device device, KuraPayload kuraPayload) {
        if (null != kuraPayload) {
            if (null == device) {
                device = new Device();
            }

            if (kuraPayload.hasTimestamp()) {
                device.setTimestamp(kuraPayload.getTimestamp());
            }

            if (kuraPayload.hasPosition()) {
                if (kuraPayload.getPosition().hasAltitude()) {
                    device.setAltitude(kuraPayload.getPosition().getAltitude());
                }

                if (kuraPayload.getPosition().hasLongitude()) {
                    device.setLongitude(kuraPayload.getPosition().getLongitude());
                }

                if (kuraPayload.getPosition().hasLatitude()) {
                    device.setLatitude(kuraPayload.getPosition().getLatitude());
                }
            }

            for (KuraPayload.KuraMetric kuraMetric : kuraPayload.getMetricList()) {
                if  (kuraMetric.hasName()) {
                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_DEVICE_ID)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setDeviceId(kuraMetric.getStringValue());
                        }
                    }
                }
            }
        }

        return device;
    }




    //@Override
//    public static KuraPayload createKuraMessage(String messageName,String messageString){
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        KuraPayload temp = KuraPayload.toBuilder().build();
//
//
//        KuraPayload.KuraMetric messageMetric = KuraPayload.KuraMetric.toBuilder()
//                .setName(messageString)
//                .setName(messageName)
//                .build();
//
//        //KuraPayload.KuraMetric messageMetric
//
////        KuraPayload.Builder builder = KuraPayload.toBuilder();
////        builder.setTimestamp(timestamp.getTime());
////        builder.setMetric(0,messageMetric);
////
////        KuraPayload messageKura = builder.build();
//
//
//        return KuraPayload.toBuilder()
//                .setTimestamp(timestamp.getTime())
//                .setMetric(0,messageMetric)
//                .build();
//    }

    public Device updateDeviceFirmwareStatusDetails(Device device, KuraPayload kuraPayload) {
        if (null != kuraPayload) {
            if (null == device) {
                device = new Device();
            }

            device = updateDeviceBasicDetails(device, kuraPayload);

            for (KuraPayload.KuraMetric kuraMetric : kuraPayload.getMetricList()) {
                if  (kuraMetric.hasName()) {
                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_STATUS)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setFirmwareStatus(kuraMetric.getStringValue());
                        }
                    }
                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_Error)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setFirwareError(kuraMetric.getStringValue());
                        }
                    }
                }
            }

        }

        return device;
    }

    public Device updateDeviceStateDetails(Device device, KuraPayload kuraPayload) {
        if (null != kuraPayload) {
            if (null == device) {
                device = new Device();
            }

            device = updateDeviceBasicDetails(device, kuraPayload);

//            for (KuraPayload.KuraMetric kuraMetric : kuraPayload.getMetricList()) {
//                if  (kuraMetric.hasName()) {
//                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_TAMPER)) {
//                        if (kuraMetric.hasBoolValue()) {
//                            device.setTamper(kuraMetric.getBoolValue());
//                        }
//                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_TEMPERATURE)) {
//                        if (kuraMetric.hasFloatValue()) {
//                            device.setTemperature(kuraMetric.getFloatValue());
//                        }
//                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_BATTERY)) {
//                        if (kuraMetric.hasFloatValue()) {
//                            device.setBattery(kuraMetric.getFloatValue());
//                        }
//                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_RSSI)) {
//                        if (kuraMetric.hasFloatValue()) {
//                            device.setRssi(kuraMetric.getFloatValue());
//                        }
//                    }
//                }
//            }
        }

        return device;
    }

    public Device updateDeviceBirthCertificateDetails(Device device, KuraPayload kuraPayload) {
        if (null != kuraPayload) {
            if (null == device) {
                device = new Device();
            }

            device = updateDeviceBasicDetails(device, kuraPayload);

            for (KuraPayload.KuraMetric kuraMetric : kuraPayload.getMetricList()) {
                if  (kuraMetric.hasName()) {
                    if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_MODEL_ID)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setModelId(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_MODEL_NAME)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setModelName(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_SERIAL_NUMBER)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setSerialNumber(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_OS_VERSION)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setOsVersion(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_BIOS_VERSION)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setBioVersion(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_UNITS)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setUnits(kuraMetric.getStringValue());
                        }
                    } else if (kuraMetric.getName().equalsIgnoreCase(this.METRIC_NAME_FIRMWARE_VERSION)) {
                        if (kuraMetric.hasStringValue()) {
                            device.setFirmwareVersion(kuraMetric.getStringValue());
                        }
                    }
                }
            }
        }

        return device;
    }

}
