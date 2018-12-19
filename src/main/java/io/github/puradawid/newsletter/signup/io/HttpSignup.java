package io.github.puradawid.newsletter.signup.io;

import io.github.puradawid.newsletter.signup.Email;
import io.github.puradawid.newsletter.signup.EmailStorage;
import io.github.puradawid.newsletter.signup.Error;
import io.github.puradawid.newsletter.signup.Signup;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class HttpSignup extends AbstractHandler {

    private final Signup signup;

    public HttpSignup(EmailStorage emailStorage) {
        this.signup = new Signup(emailStorage);
    }

    @Override
    public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        if ("POST".equals(httpServletRequest.getMethod()) && "/signup".equals(s)) {
            String email = httpServletRequest.getParameter("email");
            if (email != null) {
                Optional<Error> error = signup.process(new Email(email));
                if (error.isPresent()) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    System.out.println("Error happened with " + email);
                } else {
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    httpServletResponse.flushBuffer();
                }
            } else {
                System.out.println("No email in request");
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
