package csir.co.za.managementDevice;

import csir.co.za.camel.PropertiesUtility;
import csir.co.za.plug.PlugData;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="ManagementDeviceData")
public class ManagementDeviceData extends PanacheEntity {

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
    private String managementDeviceId = null;

    @Column(name="time_stamp")
    private Long timestamp = null;

    // Device Status Details
    @Column(name="status")
    private String status = null;

    @Column(name="solar_voltage")
    private Double solarVoltage = null;

    @Column(name="solar_current")
    private Double solarCurrent = null;

    @Column(name="battery_voltage")
    private Double batteryVoltage = null;

    @Column(name="mppt")
    private Integer mppt = null;

    @Column(name="actuation")
    private String actuation = null;

    public Double getSolarVoltage() {return solarVoltage;}

    public void  setSolarVoltage(double solarVoltage) {this.solarVoltage = solarVoltage;}

    public Double getSolarCurrent() {return solarCurrent;}

    public void  setSolarCurrent(double solarCurrent) {this.solarCurrent = solarCurrent;}

    public Double getBatteryVoltage() {return batteryVoltage;}

    public void  setBatteryVoltage(double batteryVoltage) {this.batteryVoltage = batteryVoltage;}

    public int getMppt() {return mppt;}

    public void  setMppt(Integer mppt) {this.mppt = mppt;}

    public void setActuation(String actuation) {
        this.actuation = actuation;
    }

    public String getActuation() {
        return actuation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ManagementDeviceData device = (ManagementDeviceData) o;

        if (!Objects.equals(managementDeviceId, device.managementDeviceId)) return false;
        if (!Objects.equals(timestamp, device.timestamp)) return false;
        if (!Objects.equals(solarCurrent, device.solarCurrent)) return false;
        if (!Objects.equals(solarVoltage, device.solarVoltage)) return false;
        if (!Objects.equals(batteryVoltage, device.batteryVoltage)) return false;
        if (!Objects.equals(mppt, device.mppt)) return false;
        if (!Objects.equals(actuation, device.actuation)) return false;

        return Objects.equals(status, device.status);
    }


    @Override
    public int hashCode() {
        int result = managementDeviceId != null ? managementDeviceId.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (solarCurrent != null ? solarCurrent.hashCode() : 0);
        result = 31 * result + (solarVoltage != null ? solarVoltage.hashCode() : 0);
        result = 31 * result + (batteryVoltage != null ? batteryVoltage.hashCode() : 0);
        result = 31 * result + (mppt != null ? mppt.hashCode() : 0);
        result = 31 * result + (actuation != null ? actuation.hashCode() : 0);//        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
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
                ", managementDeviceId='" + managementDeviceId + '\'' +
                ", timestamp=" + timestamp + '\'' +
                ", solarCurrent=" +  solarCurrent + '\'' +
                ", solarCurrent=" +  solarCurrent + '\'' +
                ", batteryVoltage=" +  batteryVoltage + '\'' +
                ", actuation=" +  actuation + '\'' +
                ", mppt=" +  mppt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }





}
