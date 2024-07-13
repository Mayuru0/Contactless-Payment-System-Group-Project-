package com.mycompany.cotactless.payment.system.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("jakartaee10")
public class JakartaEE10Resource {

    @GET
    public Response ping() {
        return Response.ok("ping").build();
    }
}
