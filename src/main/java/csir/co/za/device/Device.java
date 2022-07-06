package csir.co.za.device;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import csir.co.za.camel.PropertiesUtility;

import java.util.Objects;

@Entity
@Table(name="device",
        uniqueConstraints= @UniqueConstraint(columnNames={"device_id"}))
public class Device extends PanacheEntity {
    private static final Logger LOG = Logger.getLogger(Device.class);

    private static PropertiesUtility propertiesUtility = new PropertiesUtility();

    @SequenceGenerator(name = "deviceSeq", sequenceName = "device_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "deviceSeq")

    public Long getId() {
        return id;
    }
    // Device Basic Details
    @Column(name="device_id")
    private String deviceId = null;

    @Column(name="time_stamp")
    private Long timestamp = null;

    @Column(name="longitude")
    private Double longitude = null;

    @Column(name="latitude")
    private Double latitude = null;

    @Column(name="altitude")
    private Double altitude = null;


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


    //Device State Details
    @Column(name="file_location")
    private String fileLocation = null;

    @Column(name="file_url")
    private String fileUrl = null;

    @Column(name="deploymentUrl")
    private String deploymentUrl = null;


    @Column(name="Rollout_id")
    private String rolloutId = null;

    @Column(name="firmware_Status")
    private String firmwareStatus = null;

    @Column(name="error")
    private String firwareError = null;

    @Column(name="access_token")
    private String accessToken = null;

    // Device Status Details
    @Column(name="status")
    private String status = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRolloutId() {
        return rolloutId;
    }

    public void setRolloutId(String rolloutId) {
        this.rolloutId = rolloutId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDeploymentUrl() {
        return deploymentUrl;
    }

    public void setDeploymentUrl(String deploymentUrl) {
        this.deploymentUrl = deploymentUrl;
    }

    public String getFirmwareStatus() {
        return firmwareStatus;
    }

    public void setFirmwareStatus(String firmwareStatus) {
        this.firmwareStatus = firmwareStatus;
    }

    public String getFirwareError() {
        return firwareError;
    }

    public void setFirwareError(String firwareError) {
        this.firwareError = firwareError;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(this);

    }


    @Transactional
    public static Device createDevice(Device device) {
//        LOG.info(" device.getDeviceId(): "+ device.getDeviceId()   );
        boolean exists = (null != findByDeviceId(device.getDeviceId()));
//        LOG.info(" createDevice Exists: "+ exists   );
        if (false == exists) {
            device.setAccessToken(propertiesUtility.getHawkbitDeviceAcessToken());
            device.setFirmwareStatus("New_device_running");
            device.persist(); //insert
//            LOG.info(device.getDeviceId() + " created.");
        } else {
//            LOG.info(device.getDeviceId() + " already exists. updating.");
            //device.setFirmwareVersion(device.getFirmwareVersion());
            //update
            //device.setFirmwareVersion("V1.20");
            Device.updateDevice(device);
        }

        return device;
    }


    //when set<attribute> is done the database is automatically updated when method exists only! If error during method
    // not update of database will occur
    @Transactional
    public static Device updateDevice(Device device) {

        Device existingDevice =  findByDeviceId(device.getDeviceId());
        if (existingDevice.getDeviceId() != null) {

            //checking to if there is update to firmware update on the device from last birthmessage
            if (!existingDevice.getFirmwareVersion().equals(device.getFirmwareVersion())) {
                existingDevice.setFirmwareVersion(device.getFirmwareVersion());
//                LOG.info(" Update FirmwareVersion for: " + device.getDeviceId() );
            }

        } else {
//            LOG.info(device.getDeviceId() + " already does not exist ");
        }

        return device;
    }



    //when set<attribute> is done the database is automatically updated when method exists only! If error during method
    // not update of database will occur
    @Transactional
    public static Device updateDeviceStatus(Device device,String status) {

        Device existingDevice =  findByDeviceId(device.getDeviceId());

        //checking to if there is update to firmware update on the device from last birthmessage

        existingDevice.setFirmwareStatus(status);
//            LOG.info(" Update FirmwareVersion for: " + device.getDeviceId() );

        return device;
    }


    //when set<attribute> is done the database is automatically updated when method exists only! If error during method
    // not update of database will occur
    @Transactional
    public static Device updateDeviceStringVar(Device device,String variable, String value) {

        Device existingDevice =  findByDeviceId(device.getDeviceId());

        //checking to if there is update to firmware update on the device from last birthmessage
        switch (variable) {
            case "firmwareStatus":
                existingDevice.setFirmwareStatus(value);
                break;
            case "deploymentUrl":
                existingDevice.setDeploymentUrl(value);
//                LOG.info(" insidee updateDeviceStringVar value" + value);
                break;
            case "fileUrl":
                existingDevice.setFileUrl(value);
                break;
            case "fileLocation":
                existingDevice.setFileLocation(value);
                break;
            case "status_file_ready":
                existingDevice.setFirmwareStatus(value);
                break;
            case "rolloutId":
                existingDevice.setRolloutId(value);

            case "errorMessage":
                existingDevice.setFirwareError(value);

        }
//        LOG.info(" Update FirmwareVersion for: " + device.getDeviceId() );

        return device;
    }


    @Transactional
    public static Device updateDeviceStatusMqttVar(Device device,String variable, String value) {

        Device existingDevice =  findByDeviceId(device.getDeviceId());

        //checking to if there is update to firmware update on the device from last birthmessage
        switch (variable) {
            case "firmwareStatus":
                existingDevice.setFirmwareStatus(value);
                break;
            case "deploymentUrl":
                existingDevice.setDeploymentUrl(value);
//                LOG.info(" insidee updateDeviceStringVar value" + value);
                break;
            case "fileUrl":
                existingDevice.setFileUrl(value);
                break;
            case "fileLocation":
                existingDevice.setFileLocation(value);
                break;
            case "status_file_ready":
                existingDevice.setFirmwareStatus(value);
                break;
        }
//        LOG.info(" Update FirmwareVersion for: " + device.getDeviceId() );

        return device;
    }




//  device  @Transactional
//    public Device update(Device device) {
//        Device original_device = findById(device.getDeviceId());
//
//        person.setName("updated updated updated");  // <--- this way works
//        repository.persist(person);
//        return person;
//    }



    @Transactional
    public static void deleteDevice(Device device) {
        device.delete();
    }

    public static Device findById(Long id){
        LOG.info("ID - " + id);
        Device foundDevice = find("id", id).firstResult();
        String answer = foundDevice.toString();
        LOG.info(answer);
        answer = answer.replace("Device","");
        return foundDevice;
    }

    public static Device findByDeviceId(String deviceId){
        find("device_id", deviceId).firstResult();
        return find("device_id", deviceId).firstResult();
    }

    public static List<Device> findAllDevices(){
        return Device.listAll();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (!Objects.equals(deviceId, device.deviceId)) return false;
        if (!Objects.equals(timestamp, device.timestamp)) return false;
        if (!Objects.equals(longitude, device.longitude)) return false;
        if (!Objects.equals(latitude, device.latitude)) return false;
        if (!Objects.equals(altitude, device.altitude)) return false;
        if (!Objects.equals(modelId, device.modelId)) return false;
        if (!Objects.equals(modelName, device.modelName)) return false;
        if (!Objects.equals(osVersion, device.osVersion)) return false;
        if (!Objects.equals(bioVersion, device.bioVersion)) return false;
        if (!Objects.equals(units, device.units)) return false;
        if (!Objects.equals(serialNumber, device.serialNumber))
            return false;
        if (!Objects.equals(firmwareVersion, device.firmwareVersion))
            return false;
        if (!Objects.equals(fileLocation, device.fileLocation)) return false;
        if (!Objects.equals(fileUrl, device.fileUrl)) return false;
        if (!Objects.equals(deploymentUrl, device.deploymentUrl)) return false;
        if (!Objects.equals(firmwareStatus, device.firmwareStatus)) return false;
        if (!Objects.equals(rolloutId, device.rolloutId)) return false;

        if (!Objects.equals(firwareError, device.firwareError)) return false;
        return Objects.equals(status, device.status);
    }

    @Override
    public int hashCode() {
        int result = deviceId != null ? deviceId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        result = 31 * result + (modelId != null ? modelId.hashCode() : 0);
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (bioVersion != null ? bioVersion.hashCode() : 0);
        result = 31 * result + (units != null ? units.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        result = 31 * result + (firmwareVersion != null ? firmwareVersion.hashCode() : 0);
        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        result = 31 * result + (deploymentUrl != null ? deploymentUrl.hashCode() : 0);
        result = 31 * result + (firwareError != null ? firwareError.hashCode() : 0);
        result = 31 * result + (rolloutId != null ? rolloutId.hashCode() : 0);

        result = 31 * result + (firmwareStatus != null ? firmwareStatus.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", altitude=" + altitude +
                ", modelId='" + modelId + '\'' +
                ", modelName='" + modelName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", bioVersion='" + bioVersion + '\'' +
                ", units='" + units + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", firmwareStatus=" + firmwareStatus +
                ", firwareError=" + firwareError +
                ", fileLocation=" + fileLocation +
                ", fileUrl=" + fileUrl +
                ", deploymentUrl=" + deploymentUrl +
                ", rolloutId=" + rolloutId +
                ", status='" + status + '\'' +
                '}';
    }
}



