package webserver;

import java.util.Objects;

public class HttpQueryString {
    public static final String QUERY_STRING_DELIMITER = "=";
    public static final int QUERY_STRING_SCHEMA_SIZE = 2;
    public static final int QUERY_STRING_SCHEMA_NAME_INDEX = 0;
    public static final int QUERY_STRING_SCHEMA_VALUE_INDEX = 1;
    private final String name;
    private final String value;

    public HttpQueryString(String queryString) {
        String[] queryStringSchemas = queryString.split(QUERY_STRING_DELIMITER);
        validateQueryStringSchemas(queryString, queryStringSchemas);
        this.name = queryStringSchemas[QUERY_STRING_SCHEMA_NAME_INDEX];
        this.value = toQueryStringValue(queryString, queryStringSchemas);
    }

    private String toQueryStringValue(String queryString, String[] queryStringSchemas) {
        if (isEmptyValueQueryString(queryString, queryStringSchemas)) {
            return "";
        }

        return queryStringSchemas[QUERY_STRING_SCHEMA_VALUE_INDEX];
    }

    private void validateQueryStringSchemas(String queryString, String[] queryStringSchemas) {
        /**
         * 오직 queryString의 name만 존재하는 경우(value 없이) 유효한 queryString으로 간주한다.
         * ex) queryName=
         */
        if (isEmptyValueQueryString(queryString, queryStringSchemas)) {
            return;
        }

        if (queryStringSchemas == null || queryStringSchemas.length != QUERY_STRING_SCHEMA_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 queryString: [%s]", queryString));
        }
    }

    private boolean isEmptyValueQueryString(String queryString, String[] queryStringSchemas) {
        if (queryString == null || queryString.isEmpty()) {
            return false;
        }

        char queryStringLastChar = queryString.charAt(queryString.length() - 1);

        boolean isEqualsQueryStringLastCharAndDelimiter = queryStringLastChar == QUERY_STRING_DELIMITER.charAt(0);
        boolean hasOnlyQueryStringName = queryStringSchemas.length == 1;

        return isEqualsQueryStringLastCharAndDelimiter && hasOnlyQueryStringName;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpQueryString that = (HttpQueryString) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}