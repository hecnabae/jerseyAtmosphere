package com.mesview.rest;

import com.mesview.Track;
import org.atmosphere.annotation.Publish;
import org.atmosphere.annotation.Subscribe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("metallica")
public class MetallicaService {

    @GET
    @Path("/suscripcion")
    @Subscribe(value = "metallica", timeout = -1)
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
        track.setTitle("Enter Sandman");
        track.setSinger("Metallica");

        return track;

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Publish("metallica")
    //@Broadcast("channel")
    public String createTrackInJSON(Track track) {

        String result = "Metallica -- Track saved : " + track;
        //return Response.status(201).entity(result).build();
        return result;

    }

}