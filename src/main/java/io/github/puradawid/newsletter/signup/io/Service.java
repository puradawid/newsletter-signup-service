package io.github.puradawid.newsletter.signup.io;

import org.eclipse.jetty.server.Server;

import java.io.File;

public class Service {

    private final int port;

    private final String filePath;

    private Server server;

    public Service(int port, String filePath) {
        this.port = port;
        this.filePath = filePath;
    }

    public void start() {
        server = new Server(port);
        server.setHandler(new HttpSignup(new SaveToFileEmailStorage(filePath)));
        try {
            server.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
