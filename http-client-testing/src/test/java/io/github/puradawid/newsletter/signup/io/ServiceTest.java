package io.github.puradawid.newsletter.signup.io;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServiceTest {

    private Service service;
    private File f;

    @Before
    public void setUp() throws Exception {
        f = File.createTempFile("testing_data", "temp");
        service = new Service(8080, f.getPath());
        service.start();
    }

    @After
    public void tearDown() throws Exception {
        service.stop();
    }

    @Test
    public void shouldReturn404OnWrongRequest() throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/signup")).GET().build();
        HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
        assertThat(response.statusCode(), is(404));
    }

    @Test
    public void shouldReturn200WhenSendingCorrectEmail() throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/signup")).POST(
            HttpRequest.BodyPublishers.ofString("email=correct@email.com"))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build();
        HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
        assertThat(response.statusCode(), is(200));
        BufferedReader reader = new BufferedReader(new FileReader(new File(f.getPath())));
        assertThat(reader.readLine(), is("correct@email.com"));
    }

}