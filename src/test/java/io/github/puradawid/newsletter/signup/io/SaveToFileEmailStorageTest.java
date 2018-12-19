package io.github.puradawid.newsletter.signup.io;

import io.github.puradawid.newsletter.signup.Email;
import io.github.puradawid.newsletter.signup.EmailStorage;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SaveToFileEmailStorageTest {

    @Test
    public void shouldCreateAFileAndWriteJustOneName() throws IOException {
        File f = File.createTempFile("test", ".txt");
        EmailStorage storage = new SaveToFileEmailStorage(f.getPath());
        storage.store(new Email("puradawid@gmail.com"));
        storage.store(new Email("puradawid@gmail.com"));
        storage.store(new Email("admin@gmail.com"));

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            assertThat(reader.readLine(), is("puradawid@gmail.com"));
            assertThat(reader.readLine(), is("puradawid@gmail.com"));
            assertThat(reader.readLine(), is("admin@gmail.com"));
        }
    }

}