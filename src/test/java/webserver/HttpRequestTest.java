package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequestTest {
    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}