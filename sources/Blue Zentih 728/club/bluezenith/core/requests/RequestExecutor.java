package club.bluezenith.core.requests;

import club.bluezenith.core.requests.data.ContentType;
import club.bluezenith.core.requests.data.RequestOption;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static club.bluezenith.core.requests.data.ContentType.APPLICATION_ENCODED;
import static club.bluezenith.core.requests.data.ContentType.QUERY;
import static club.bluezenith.core.requests.data.OptionType.HEADER;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.apache.http.impl.client.HttpClients.createDefault;

public class RequestExecutor {
    private CloseableHttpClient httpClient = createDefault();

    @SafeVarargs
    public final CompletableFuture<Response> queue(Request request, Consumer<Response>... callbacks) {
        return CompletableFuture.supplyAsync(() -> {
            httpClient = HttpClients.createDefault();
            HttpRequestBase entity = request.method.getAppropriateEntity();

           final List<RequestOption> headers = newArrayList();
           final List<RequestOption> parameters = newArrayList();

            for (RequestOption option : request.options) {
                if(option.type == HEADER)
                    headers.add(option);
                else parameters.add(option);
            }

            for (RequestOption header : headers) {
                entity.setHeader(header.name, header.value);
            }

            entity = appendParameters(request.route, entity, parameters, request.contentType);
            CloseableHttpResponse response;
            try {
                response = httpClient.execute(entity);
            } catch (IOException e) {
                throw new Error(e);
            }
            final Response realResponse = new Response(request.route, request, readEntityIntoString(response.getEntity()), response.getEntity());
            realResponse.setCode(response.getStatusLine().getStatusCode());
            for (Consumer<Response> callback : callbacks) {
                if(callback != null) {
                    callback.accept(realResponse);
                }
            }
            return realResponse;
        });
    }

    private HttpRequestBase appendParameters(String route, HttpRequestBase entity, List<RequestOption> parameters, ContentType type) {
        if(entity instanceof HttpPost || entity instanceof HttpPut) {
            final List<BasicNameValuePair> pairs = newArrayList();
            String jsonContent = null;

            for (RequestOption param : parameters) {
                if(type == APPLICATION_ENCODED || type == QUERY)
                    pairs.add(new BasicNameValuePair(param.name, param.value));
                    else jsonContent = param.value;
            }

            try {
                if (jsonContent != null) {
                    final StringEntity ent = new StringEntity(jsonContent);
                    if (entity instanceof HttpPut)
                        ((HttpPut) entity).setEntity(ent);
                    else ((HttpPost) entity).setEntity(ent);
                } else {
                    final UrlEncodedFormEntity ent = new UrlEncodedFormEntity(pairs);
                    if (entity instanceof HttpPut)
                        ((HttpPut) entity).setEntity(ent);
                    else ((HttpPost) entity).setEntity(ent);
                }
            } catch (Exception exception) {
                throw new Error(exception);
            }
        } else if(entity instanceof HttpGet) {
            boolean appendedFirst = false;
            StringBuilder routeBuilder = new StringBuilder(route);
            for (RequestOption param : parameters) {
                routeBuilder.append(appendedFirst ? format("&%s=%s", param.name, param.value) : format("?%s=%s", param.name, param.value));
                appendedFirst = true;
            }
            route = routeBuilder.toString();
        } else throw new UnsupportedOperationException();
        entity.setURI(URI.create(route));
        return entity;
    }

    private static String readEntityIntoString(HttpEntity entity) {
        final StringBuilder builder = new StringBuilder();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            return builder.toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "error";
    }
}
