package io.github.puradawid.newsletter.signup;

import java.util.Optional;

public class Signup {

    private final EmailStorage storage;

    public Signup(EmailStorage storage) {
        this.storage = storage;
    }

    public Optional<Error> process(Email request) {
        if (!request.isValid()) {
            return Optional.of(new EmailMalformed());
        } else {
            storage.store(request);
            return Optional.empty();
        }
    }

    private class EmailMalformed implements Error {

    }
}
