package io.github.puradawid.newsletter.signup;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class SignupTest {

    @Test
    public void shouldReturnErrorWhenEmailmalformed() {
        Optional<Error> result = new Signup(new DoNothingStorage()).process(new Email("malformed@email .com"));
        assertTrue(result.isPresent());
    }

    @Test
    public void shouldAcceptRequestIfEmailValid() {
        Optional<Error> result = new Signup(new DoNothingStorage()).process(new Email("malformed@email.com"));
        assertTrue(!result.isPresent());
    }

    @Test
    public void shouldWriteToStorageIfEmailValid() {
        FakeEmailStorage fakeEmailStorage = new FakeEmailStorage();
        Optional<Error> result = new Signup(fakeEmailStorage).process(new Email("malformed@email.com"));

        assertTrue(!result.isPresent());
        assertTrue(fakeEmailStorage.wasInvokedWith("malformed@email.com"));
    }

}

class DoNothingStorage implements EmailStorage {

    @Override
    public void store(Email email) {
    }
}

class FakeEmailStorage implements EmailStorage {

    private String emailAddress;

    @Override
    public void store(Email email) {
        emailAddress = email.toString();
    }

    boolean wasInvokedWith(String emailAddress) {
        return emailAddress.equals(this.emailAddress);
    }
}