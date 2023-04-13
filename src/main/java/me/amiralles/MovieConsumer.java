package me.amiralles;

import me.amiralles.quarkus.MovieCreated;
import me.amiralles.quarkus.MovieUpdated;
import org.apache.avro.generic.GenericRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieConsumer {

    private static final Logger LOGGER = Logger.getLogger("MovieConsumer");

    @Incoming("movies-from-kafka")
    public void receive(Object movie) {

        if(movie instanceof MovieCreated) {
            handle((MovieCreated) movie);
        } else if(movie instanceof MovieUpdated) {
            handle((MovieUpdated) movie);
        } else {
            LOGGER.error("Event type not recognized");
        }
    }

    public void handle(MovieCreated event) {
        LOGGER.infof("Movie created: %s", event);
    }

    public void handle(MovieUpdated event) {
        LOGGER.infof("Movie updated: %s", event);
    }

}
