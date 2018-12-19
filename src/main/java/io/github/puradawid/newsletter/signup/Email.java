package io.github.puradawid.newsletter.signup;

import org.apache.commons.validator.routines.EmailValidator;

public class Email {

    private final String text;

    public Email(String text) {
        this.text = text;
    }

    public boolean isValid() {
        return EmailValidator.getInstance().isValid(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
