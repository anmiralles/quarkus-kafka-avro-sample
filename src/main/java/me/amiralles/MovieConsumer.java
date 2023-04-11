package me.amiralles;

import org.apache.avro.generic.GenericRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MovieConsumer {

    private static final Logger LOGGER = Logger.getLogger("MovieConsumer");

    @Incoming("movies-from-kafka")
    public void receive(GenericRecord movie) {
        String name = movie.getSchema().getFullName();
        LOGGER.infof("Received movie: %s", name);
    }

}
