package csir.co.za.plug;

import org.jboss.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/plug/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlugDataResource {
    private static final Logger LOG = Logger.getLogger(PlugDataResource.class);

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findById(@PathParam("id") String id) {
        String answer = PlugData.findByDeviceId(id).toString();
        LOG.info(answer);
        answer = answer.replace("Device","");
        return answer;
    }


}
