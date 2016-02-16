package com.mesview.rest;

import com.mesview.Track;
import org.atmosphere.annotation.Publish;
import org.atmosphere.annotation.Subscribe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by hector on 16/02/2016.
 */
@Path("ramones")
public class RamonesService {
    @GET
    @Path("/suscripcion")
    @Subscribe("ramones")
    //@Suspend
    public String handshake() {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //@Publish("channel")
    //@Broadcast()
    public Track getTrackInJSON() {

        Track track = new Track();
        track.setTitle("Beat on the Brat");
        track.setSinger("Ramones");

        return track;

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Publish("ramones")
    //@Broadcast("channel")
    public String createTrackInJSON(Track track) {

        String result = "Ramones -- Track saved : " + track;
        //return Response.status(201).entity(result).build();
        return result;

    }
}
