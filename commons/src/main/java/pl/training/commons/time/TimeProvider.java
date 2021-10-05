package pl.training.commons.time;

import java.time.Instant;

public interface TimeProvider {

    Instant getTimestamp();

}
