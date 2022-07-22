package webserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HttpQueryStringTest {

    @Test
    void create() {
        HttpQueryString httpQueryString = HttpQueryString.from("queryName=queryValue");

        assertThat(httpQueryString.getName()).isEqualTo("queryName");
        assertThat(httpQueryString.getValue()).isEqualTo("queryValue");
    }

    @Test
    void create_only_queryName() {
        HttpQueryString httpQueryString = HttpQueryString.from("queryName=");

        assertThat(httpQueryString.getName()).isEqualTo("queryName");
        assertThat(httpQueryString.getValue()).isEmpty();
    }

    @Test
    void isNotEmpty() {
        HttpQueryString httpQueryString = HttpQueryString.from("");
        HttpQueryString httpQueryString2 = HttpQueryString.from(null);

        assertThat(httpQueryString.isNotEmpty()).isFalse();
        assertThat(httpQueryString2.isNotEmpty()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"queryName=queryValue=1", "queryValue", " "})
    void create_exception(String queryString) {
        assertThrows(IllegalArgumentException.class, () -> HttpQueryString.from(queryString));
    }
}
