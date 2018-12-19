package io.github.puradawid.newsletter.signup.io;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod("http://localhost:8080");
        client.executeMethod(get);
        assertThat(get.getStatusCode(), is(404));
    }

    @Test
    public void shouldReturn200WhenSendingCorrectEmail() throws Exception {
        HttpClient client = new HttpClient();
        PostMethod get = new PostMethod("http://localhost:8080/signup");
        get.addParameter("Content-Type", "application/x-www-form-urlencoded");
        get.addParameter("email", "correct@email.com");
        client.executeMethod(get);
        assertThat(get.getStatusCode(), is(200));
        BufferedReader reader = new BufferedReader(new FileReader(new File(f.getPath())));
        assertThat(reader.readLine(), is("correct@email.com"));
    }

}