package me.amiralles;

import me.amiralles.quarkus.MovieCreated;
import me.amiralles.quarkus.MovieUpdated;
import org.apache.avro.generic.GenericRecord;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    private static final Logger LOGGER = Logger.getLogger("MovieResource");

    @Inject @Channel("movies") Emitter<GenericRecord> emitter;


    @POST
    @Path("/create")
    public Response createMovie(MovieCreated movie) {
        LOGGER.infof("Sending movie %s to Kafka", movie.getTitle());
        emitter.send(movie);
        return Response.accepted().build();
    }

    @POST
    @Path("/update")
    public Response updateMovie(MovieUpdated movie) {
        LOGGER.infof("Sending movie %s to Kafka", movie.getTitle());
        emitter.send(movie);
        return Response.accepted().build();
    }

}
