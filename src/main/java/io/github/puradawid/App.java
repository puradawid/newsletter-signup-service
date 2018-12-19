package io.github.puradawid;

import io.github.puradawid.newsletter.signup.io.Service;

import java.io.File;

public class App
{
    public static void main(String[] args)  throws Exception {

        // Two params are here: port and filename

        if (args.length != 2) {
           System.out.println("Ups it doesn't work");
           return;
        }

        boolean created = new File(args[1]).createNewFile();
        if (created) {
            Service s = new Service(Integer.parseInt(args[0]), args[1]);
            s.start();
        } else {
            System.out.println("The file doesn't exist");
        }
    }
}
