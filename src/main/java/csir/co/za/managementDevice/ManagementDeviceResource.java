package csir.co.za.managementDevice;


import csir.co.za.plug.PlugDevice;
import csir.co.za.plug.PlugDeviceResource;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/management/device")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManagementDeviceResource {
    private static final Logger LOG = Logger.getLogger(ManagementDeviceResource.class);

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ManagementDevice findById(@PathParam("id") Long id) {
//        String answer = Device.findById(id).toString();
//        LOG.info(answer);
//        answer = answer.replace("Device","");
        return ManagementDevice.findById(id);
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public ManagementDevice update(@PathParam("id") Long id, ManagementDevice device) {
        ManagementDevice entity = ManagementDevice.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }

        // map all appropriate fields from the 'device' parameter to the existing entity
        // For fields without new values coming in, do not overwrite the entity values
        //entity.setXYZ(device.getXYZ());

        // the data will be saved in the database when this method exists, due to the @Transactional.
        return entity;
    }




}
