package webserver.http.request.requestline;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HttpQueryStrings {
    public static final String PRIMARY_QUERY_STRING_SYMBOL_WITHOUT_ESCAPE = HttpPath.PRIMARY_QUERY_STRING_SYMBOL.replace("\\", "");
    public static final String SECONDARY_QUERY_STRING_SYMBOL = "&";

    private final List<HttpQueryString> httpQueryStrings;
    private final String fullQueryString;

    public HttpQueryStrings(String fullQueryString) {
        this.httpQueryStrings = parseQueryString(fullQueryString);
        this.fullQueryString = toFullQueryString(fullQueryString);
    }

    private List<HttpQueryString> parseQueryString(String fullQueryString) {
        if (isEmptyQueryString(fullQueryString)) {
            return Collections.emptyList();
        }

        String[] fullQueryStringSchemas = fullQueryString.split(SECONDARY_QUERY_STRING_SYMBOL);

        return Arrays.stream(fullQueryStringSchemas)
                .map(HttpQueryString::from)
                .filter(HttpQueryString::isNotEmpty)
                .collect(Collectors.toList());
    }

    private String toFullQueryString(String fullQueryString) {
        if (isEmptyQueryString(fullQueryString)) {
            return "";
        }

        return PRIMARY_QUERY_STRING_SYMBOL_WITHOUT_ESCAPE + fullQueryString;
    }

    private boolean isEmptyQueryString(String fullQueryString) {
        return fullQueryString == null || fullQueryString.isEmpty();
    }

    public HttpQueryString get(int index) {
        if (index > httpQueryStrings.size() - 1) {
            return HttpQueryString.EMPTY;
        }

        return httpQueryStrings.get(index);
    }

    public String getQueryValue(String queryName) {
        return httpQueryStrings.stream()
                .filter(httpQueryString -> queryName.equals(httpQueryString.getName()))
                .map(HttpQueryString::getValue)
                .findAny()
                .orElse("");
    }

    public String getFullQueryString() {
        return fullQueryString;
    }
}
