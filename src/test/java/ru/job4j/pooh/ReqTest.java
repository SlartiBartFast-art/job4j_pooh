package ru.job4j.pooh;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ReqTest {
    @Test
    public void whenPostMethod() {
        var content = """
                POST /topic/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.67.0
                Accept: */*
                Content-Length: 7
                Content-Type: application/x-www-form-urlencoded
                                
                text=13
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param("text"), is("13"));
    }

    @Test
    public void whenGetMethod() {
        String s = null;
        var content = """
                GET /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.67.0
                Accept: */*                       
             
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param(null), is(s));
    }
}