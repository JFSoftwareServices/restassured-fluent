package request;

public enum HttpMethod {
    GET(new GetRequest()), POST(new PostRequest()), PUT(new PutRequest()), DELETE(new DeleteRequest());

    private final HttpRequest httpRequest;

    HttpMethod(final HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }
}