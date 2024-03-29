package example.test.db;

import io.micronaut.core.annotation.NonNull;

@FunctionalInterface
public interface IdGenerator {

    @NonNull
    String generate();
}
