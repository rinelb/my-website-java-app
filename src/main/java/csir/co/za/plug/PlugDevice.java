package csir.co.za.plug;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csir.co.za.camel.KapuaPlugDataBuilder;
import csir.co.za.camel.PropertiesUtility;
import csir.co.za.device.Device;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.jboss.logging.Logger;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name="PlugDevice",
        uniqueConstraints= @UniqueConstraint(columnNames={"plug_device_key"}))
public class PlugDevice extends PanacheEntity {
    private static final Logger LOG = Logger.getLogger(PlugDevice.class);


    private static PropertiesUtility propertiesUtility = new PropertiesUtility();

    @SequenceGenerator(name = "plugDeviceSeq", sequenceName = "plug_device_key_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "plugDeviceSeq")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Device Basic Details
    @Column(name="plug_device_key")
    private String plugDeviceKey = null;

    @Column(name="time_stamp")
    private Long timestamp = null;

    @Column(name="local_IP")
    private String localIP = null;

    // Device Birth Certificate Details
    @Column(name="model_id")
    private String modelId = null;

    @Column(name="model_name")
    private String modelName = null;

    @Column(name="os_version")
    private String osVersion = null;

    @Column(name="bios_version")
    private String bioVersion = null;

    @Column(name="units")
    private String units = null;

    @Column(name="serial_number")
    private String serialNumber = null;

    @Column(name="firmware_version")
    private String firmwareVersion = null;

    @Column(name="description")
    private String description=null;

    @Column(name="profile5")
    private String profile5 = null;

    @Column(name="profile9")
    private String profile9 = null;

    @Column(name="profile15")
    private String profile15 = null;

    @Column(name="profile20")
    private String profile20= null;

    //Device Firmware   Details for later use
//    @Column(name="file_location")
//    private String fileLocation = null;
//
//    @Column(name="file_url")
//    private String fileUrl = null;
//
//    @Column(name="deploymentUrl")
//    private String deploymentUrl = null;
//
//    @Column(name="Rollout_id")
//    private String rolloutId = null;
//
//    @Column(name="firmware_Status")
//    private String firmwareStatus = null;
//
//    @Column(name="error")
//    private String firwareError = null;
//
//    @Column(name="access_token")
//    private String accessToken = null;


    // Device Status Details
    @Column(name="status")
    private String status = null;


    public String getProfile5() {
        return profile5;
    }

    public void setProfile5(String profile5) {
        this.profile5 = profile5;
    }

    public String getProfile9() {
        return profile9;
    }

    public void setProfile9(String profile9) {
        this.profile9 = profile9;
    }

    public String getProfile15() {
        return profile15;
    }

    public void setProfile15(String profile15) {
        this.profile15 = profile15;
    }

    public String getProfile20() {
        return profile20;
    }

    public void setProfile20(String profile20) {
        this.profile20 = profile20;
    }


    public String getPlugDeviceKey() {
        return plugDeviceKey;
    }

    public void setPlugDeviceKey(String plugDeviceKey) {
        this.plugDeviceKey = plugDeviceKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalIP() {
        return localIP;
    }

    public void setLocalIP(String localIP) {
        this.localIP = localIP;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBioVersion() {
        return bioVersion;
    }

    public void setBioVersion(String bioVersion) {
        this.bioVersion = bioVersion;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    // For firmware Update later on
//    public String getFileLocation() {
//        return fileLocation;
//    }
//
//    public void setFileLocation(String fileLocation) {
//        this.fileLocation = fileLocation;
//    }
//
//    public String getFileUrl() {
//        return fileUrl;
//    }
//
//    public void setFileUrl(String fileUrl) {
//        this.fileUrl = fileUrl;
//    }
//
//    public String getDeploymentUrl() {
//        return deploymentUrl;
//    }
//
//    public void setDeploymentUrl(String deploymentUrl) {
//        this.deploymentUrl = deploymentUrl;
//    }
//
//    public String getFirmwareStatus() {
//        return firmwareStatus;
//    }
//
//    public void setFirmwareStatus(String firmwareStatus) {
//        this.firmwareStatus = firmwareStatus;
//    }
//
//    public String getFirwareError() {
//        return firwareError;
//    }
//
//    public void setFirwareError(String firwareError) {
//        this.firwareError = firwareError;
//    }
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }


    @Transactional
    public static PlugDevice createDevice(PlugDevice plugDevice ) {
        LOG.info(" plugDevice.getPlugDeviceKey(): "+ plugDevice.getPlugDeviceKey()   );
        boolean exists = (null != findByDeviceKey(plugDevice.getPlugDeviceKey()));

        LOG.info(" createDevice Exists: "+ exists   );
        if (false == exists) {
//            plugDevice.setAccessToken(propertiesUtility.getHawkbitDeviceAcessToken());
//            plugDevice.setFirmwareStatus("New_device_running");
            plugDevice.persist(); //insert
            LOG.info(plugDevice.getPlugDeviceKey() + " created.");
        } else {
            LOG.info(plugDevice.getPlugDeviceKey() + " already exists. updating.");
            //device.setFirmwareVersion(device.getFirmwareVersion());
            //update
            //device.setFirmwareVersion("V1.20");
            PlugDevice.updateDevice(plugDevice);
        }

        return plugDevice;
    }


    //when set<attribute> is done the database is automatically updated when method exists only! If error during method
    // not update of database will occur
    @Transactional
    public static PlugDevice updateDevice(PlugDevice plugDevice) {

        PlugDevice existingDevice =  findByDeviceKey(plugDevice.getPlugDeviceKey());
        if (existingDevice.getPlugDeviceKey() != null) {

            //checking to if there is update to firmware update on the device from last birthmessage
            if (!existingDevice.getFirmwareVersion().equals(plugDevice.getFirmwareVersion())) {
                existingDevice.setFirmwareVersion(plugDevice.getFirmwareVersion());
//                LOG.info(" Update FirmwareVersion for: " + device.getDeviceId() );
            }

        } else {
//            LOG.info(device.getDeviceId() + " already does not exist ");
        }

        return plugDevice;
    }


    public Long getPlugkey(String mqttTopic) {
        Long result = null;

        if (null != mqttTopic) {
            /** Kapua MQTT are of the following formats:
             *  1. CONTROL TOPICS Format: $EDC/account-name/device-id/semanticTopic
             *  2. NORMAL Format: account-name/device-id/semanticTopic
             */
            String[] topicSegments = mqttTopic.split("/");
//            if (topicSegments.length >= 3) {
//                if (topicSegments[0].trim().equalsIgnoreCase("$EDC")) {
//                    result = PlugDevice.findByDeviceKey(topicSegments[2]).getId();
//                } else {
//                    result = PlugDevice.findByDeviceKey(topicSegments[1]).getId();
//                }
//            }
            LOG.info("topicSegments[1] = " + topicSegments[1]);
            result = PlugDevice.findByDeviceKey(topicSegments[1]).getId();
        }

        return result;
    }




    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static Long getPlugIDPostgre(String plugDeviceKey){
        PlugDevice plugDevice = new PlugDevice();
        LOG.info("plugDeviceKey = " +plugDeviceKey);
        plugDevice = findByDeviceKey(plugDeviceKey);
        if (plugDevice != null) {
            LOG.info("ID= " + plugDevice.getId());
            return plugDevice.getId();
        }else{
            return null;
            }
    }
    @Transactional
    public static PlugDevice findByDeviceKey(String plugDeviceKey){
        LOG.info("plugDeviceKey = "+ plugDeviceKey);
        //LOG.info( "find = "+PlugDevice.find("SELECT * FROM plugdevice WHERE plug_device_key ='"+plugDeviceKey+"'").firstResult());
        //LOG.info( "find = "+PlugDevice.find("plug_device_key ",plugDeviceKey).firstResult());
        //return  find("plug_device_key",plugDeviceKey).firstResult();

        return find("plug_device_key ",plugDeviceKey).firstResult();
    }









    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlugDevice device = (PlugDevice) o;

        if (!Objects.equals(plugDeviceKey, device.plugDeviceKey)) return false;
        if (!Objects.equals(timestamp, device.timestamp)) return false;
        if (!Objects.equals(modelName, device.modelName)) return false;
        if (!Objects.equals(osVersion, device.osVersion)) return false;
        if (!Objects.equals(bioVersion, device.bioVersion)) return false;
        if (!Objects.equals(units, device.units)) return false;
        if (!Objects.equals(description, device.description)) return false;
        if (!Objects.equals(serialNumber, device.serialNumber))
            return false;
        if (!Objects.equals(firmwareVersion, device.firmwareVersion))
            return false;
//        if (!Objects.equals(fileLocation, device.fileLocation)) return false;
//        if (!Objects.equals(fileUrl, device.fileUrl)) return false;
//        if (!Objects.equals(deploymentUrl, device.deploymentUrl)) return false;
//        if (!Objects.equals(firmwareStatus, device.firmwareStatus)) return false;
//        if (!Objects.equals(rolloutId, device.rolloutId)) return false;
//        if (!Objects.equals(firwareError, device.firwareError)) return false;
        return Objects.equals(status, device.status);
    }

    @Override
    public int hashCode() {
        int result = plugDeviceKey != null ? plugDeviceKey.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (bioVersion != null ? bioVersion.hashCode() : 0);

        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (firmwareVersion != null ? firmwareVersion.hashCode() : 0);
//        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
//        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
//        result = 31 * result + (deploymentUrl != null ? deploymentUrl.hashCode() : 0);
//        result = 31 * result + (firwareError != null ? firwareError.hashCode() : 0);
//        result = 31 * result + (rolloutId != null ? rolloutId.hashCode() : 0);
//        result = 31 * result + (firmwareStatus != null ? firmwareStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", plugDeviceKey='" + plugDeviceKey + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", description=" + description + '\'' +
                ", modelId='" + modelId + '\'' +
                ", modelName='" + modelName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", bioVersion='" + bioVersion + '\'' +
                ", units='" + units + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }


}
