package csir.co.za.plug;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import csir.co.za.camel.PropertiesUtility;
import csir.co.za.device.Device;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name="PlugData")
public class PlugData extends PanacheEntity {
    private static PropertiesUtility propertiesUtility = new PropertiesUtility();
    @SequenceGenerator(name = "PlugDeviceSeq", sequenceName = "PlugDevice_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "PlugDeviceSeq")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Device Basic Details
    @Column(name="plug_device_id")
    private Long plugDeviceId = null;

    @Column(name="time_stamp")
    private Long timestamp = null;

    // Device Status Details
    @Column(name="status")
    private String status = null;


    @Column(name="voltage")
    private Double voltage = null;


    @Column(name="current")
    private Double current = null;

    @Column(name="actuation")
    private String actuation = null;

    public Long getPlugDeviceId() {
        return plugDeviceId;
    }

    public void setPlugDeviceId(Long plugDeviceId) {
        this.plugDeviceId = plugDeviceId;
    }

    public void setActuation(String actuation) {
        this.actuation = actuation;
    }

    public String getActuation() {
        return actuation;
    }

    public Double getVoltage() {return voltage;}

    public void  setVoltage(double voltage) {this.voltage = voltage;}

    public Double getCurrent() {return current;}

    public void  setCurrent(double current) {this.current = current;}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    public String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(this);

    }

    public static PlugData findByDeviceId(String plugDeviceKey){
        find("plug_device_id", plugDeviceKey).firstResult();
        return find("plug_device_id", plugDeviceKey).firstResult();
    }


    @Transactional
    public static PlugData insertData(PlugData device) {
//        LOG.info(" device.getDeviceId(): "+ device.getDeviceId()   );

           device.persist(); //insert
//

        return device;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlugData device = (PlugData) o;

        if (!Objects.equals(plugDeviceId, device.plugDeviceId)) return false;
        if (!Objects.equals(timestamp, device.timestamp)) return false;
        if (!Objects.equals(voltage, device.voltage)) return false;
        if (!Objects.equals(current, device.current)) return false;
        if (!Objects.equals(actuation, device.actuation)) return false;

    return Objects.equals(status, device.status);
    }

    @Override
    public int hashCode() {
        int result = plugDeviceId != null ? plugDeviceId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (voltage != null ? voltage.hashCode() : 0);
        result = 31 * result + (current != null ? current.hashCode() : 0);
        result = 31 * result + (actuation != null ? actuation.hashCode() : 0);
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
        return "{" +
                "id=" + id +
                ", plugDeviceId='" + plugDeviceId + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", voltage=" +  voltage + '\'' +
                ", current=" +  current + '\'' +
                ", actuation=" +  actuation + '\'' +
                ", status='" + status + '\'' +
                '}';
    }




}
