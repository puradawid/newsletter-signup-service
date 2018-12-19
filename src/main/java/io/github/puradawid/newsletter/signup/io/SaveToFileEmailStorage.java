package io.github.puradawid.newsletter.signup.io;

import io.github.puradawid.newsletter.signup.Email;
import io.github.puradawid.newsletter.signup.EmailStorage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToFileEmailStorage implements EmailStorage {

    private final File file;

    public SaveToFileEmailStorage(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void store(Email email) {
        if (file.canWrite()) {
            try (FileWriter w = new FileWriter(file, true)) {
                w.append(email.toString() + "\n");
                w.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
