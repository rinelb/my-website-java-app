package csir.co.za.managementDevice;

import csir.co.za.plug.PlugDevice;
import csir.co.za.plug.PlugDeviceResource;
import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/management/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManagementDeviceDataResource {
    private static final Logger LOG = Logger.getLogger(ManagementDeviceDataResource.class);

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ManagementDeviceData findById(@PathParam("id") Long id) {
//        String answer = Device.findById(id).toString();
//        LOG.info(answer);
//        answer = answer.replace("Device","");
        return ManagementDeviceData.findById(id);
    }





}
