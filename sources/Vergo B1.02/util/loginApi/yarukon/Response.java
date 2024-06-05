package xyz.vergoclient.util.loginApi.yarukon;

import java.io.OutputStream;

public class Response {
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        try {
            String succMessage = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type:text/html\r\n" +
                    "\r\n" +
                    "<h1>Successfully obtain your token!</h1>" +
                    "<br />" +
                    "<h3>Microsoft Intergration by Yarukon. All credit goes to them.</h3>";
            output.write(succMessage.getBytes());
        } catch (Exception ignored) {}
    }
}