package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.response.HttpResponseBody;
import webserver.http.response.HttpResponseHeaders;
import webserver.http.response.HttpResponseMessage;
import webserver.http.response.HttpResponseStatusLine;

import java.io.DataOutputStream;
import java.io.IOException;

import static webserver.http.response.HttpResponseMessage.RESPONSE_END_OF_LINE_MARKER;

public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static void handleResponse(DataOutputStream dos, HttpResponseMessage httpResponseMessage) {
        try {
            handleResponseStatusLine(dos, httpResponseMessage.getHttpResponseStatusLine());
            handleResponseHeaders(dos, httpResponseMessage.getHttpResponseHeaders());
            handleResponseBody(dos, httpResponseMessage.getHttpResponseBody());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void handleResponseStatusLine(DataOutputStream dos, HttpResponseStatusLine httpResponseStatusLine) throws IOException {
        dos.writeBytes(httpResponseStatusLine.rawStatusLine());
    }

    private static void handleResponseHeaders(DataOutputStream dos, HttpResponseHeaders httpResponseHeaders) throws IOException {
        for (String rawHeader : httpResponseHeaders.rawHeaders()) {
            dos.writeBytes(rawHeader);
        }
        dos.writeBytes(RESPONSE_END_OF_LINE_MARKER);
    }

    private static void handleResponseBody(DataOutputStream dos, HttpResponseBody httpResponseBody) throws IOException {
        dos.write(httpResponseBody.getBodyBytes());
    }
}
