package webserver.http.request.requestline;

import webserver.http.HttpProtocolSchema;
import webserver.http.header.HttpStaticResourceFileExtension;

public class HttpRequestLine {
    private static final String REQUEST_LINE_SCHEMA_DELIMITER = " ";
    private static final int REQUEST_LINE_SCHEMA_SIZE = 3;
    private static final int METHOD_SCHEMA_INDEX = 0;
    private static final int PATH_SCHEMA_INDEX = 1;
    private static final int PROTOCOL_AND_VERSION_SCHEMA_INDEX = 2;

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final HttpProtocolSchema httpProtocolSchema;

    public HttpRequestLine(String rawRequestLine) {
        String[] requestLineSchemas = toRequestLineSchemas(rawRequestLine);

        validateRequestLineSchemas(requestLineSchemas);

        this.httpMethod = HttpMethod.of(requestLineSchemas[METHOD_SCHEMA_INDEX]);
        this.httpPath = new HttpPath(requestLineSchemas[PATH_SCHEMA_INDEX]);
        this.httpProtocolSchema = new HttpProtocolSchema(requestLineSchemas[PROTOCOL_AND_VERSION_SCHEMA_INDEX]);
    }

    private String[] toRequestLineSchemas(String rawRequestLine) {
        if (rawRequestLine == null || rawRequestLine.isEmpty()) {
            return new String[]{};
        }

        return rawRequestLine.split(REQUEST_LINE_SCHEMA_DELIMITER);
    }

    private void validateRequestLineSchemas(String[] requestLineSchemas) {
        if (requestLineSchemas == null || requestLineSchemas.length != REQUEST_LINE_SCHEMA_SIZE) {
            throw new IllegalArgumentException("잘못된 HTTP 요청 라인입니다.");
        }
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpPath getHttpPath() {
        return httpPath;
    }

    public HttpProtocolSchema getHttpProtocolSchema() {
        return httpProtocolSchema;
    }

    public HttpStaticResourceFileExtension getHttpPathExtension() {
        return httpPath.pathExtension();
    }
}
