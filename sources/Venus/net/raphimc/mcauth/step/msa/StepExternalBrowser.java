/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import com.google.gson.JsonObject;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.Consumer;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepExternalBrowser
extends AbstractStep<ExternalBrowserCallback, ExternalBrowser> {
    public static final String AUTHORIZE_URL = "https://login.live.com/oauth20_authorize.srf";
    private final String clientId;
    private final String scope;
    private final String redirectUri;

    public StepExternalBrowser(String string, String string2, String string3) {
        super(null);
        this.clientId = string;
        this.scope = string2;
        this.redirectUri = string3;
        if (this.redirectUri.endsWith("/")) {
            throw new IllegalArgumentException("Redirect URI must not end with a slash");
        }
    }

    @Override
    public ExternalBrowser applyStep(HttpClient httpClient, ExternalBrowserCallback externalBrowserCallback) throws Exception {
        MinecraftAuth.LOGGER.info("Creating URL for MSA login via external browser...");
        if (externalBrowserCallback == null) {
            throw new IllegalStateException("Missing StepExternalBrowser.ExternalBrowserCallback input");
        }
        try (ServerSocket serverSocket = new ServerSocket(0);){
            int n = serverSocket.getLocalPort();
            ExternalBrowser externalBrowser = new ExternalBrowser(this.getAuthenticationUrl(n), this.redirectUri + ":" + n, n);
            MinecraftAuth.LOGGER.info("Created external browser MSA authentication URL: " + ExternalBrowser.access$000(externalBrowser));
            ExternalBrowserCallback.access$100(externalBrowserCallback).accept(externalBrowser);
            ExternalBrowser externalBrowser2 = externalBrowser;
            return externalBrowser2;
        }
    }

    @Override
    public ExternalBrowser fromJson(JsonObject jsonObject) throws Exception {
        return new ExternalBrowser(jsonObject.get("authenticationUrl").getAsString(), jsonObject.get("redirectUri").getAsString(), jsonObject.get("port").getAsInt());
    }

    private String getAuthenticationUrl(int n) throws URISyntaxException {
        return new URIBuilder(AUTHORIZE_URL).setParameter("client_id", this.clientId).setParameter("redirect_uri", this.redirectUri + ":" + n).setParameter("response_type", "code").setParameter("prompt", "select_account").setParameter("scope", this.scope).build().toString();
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (ExternalBrowserCallback)stepResult);
    }

    public static final class ExternalBrowserCallback
    implements AbstractStep.InitialInput {
        private final Consumer<ExternalBrowser> callback;

        public ExternalBrowserCallback(Consumer<ExternalBrowser> consumer) {
            this.callback = consumer;
        }

        public Consumer<ExternalBrowser> callback() {
            return this.callback;
        }

        static Consumer access$100(ExternalBrowserCallback externalBrowserCallback) {
            return externalBrowserCallback.callback;
        }
    }

    public static final class ExternalBrowser
    implements AbstractStep.StepResult<AbstractStep.StepResult<?>> {
        private final String authenticationUrl;
        private final String redirectUri;
        private final int port;

        public ExternalBrowser(String string, String string2, int n) {
            this.authenticationUrl = string;
            this.redirectUri = string2;
            this.port = n;
        }

        @Override
        public AbstractStep.StepResult<?> prevResult() {
            return null;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("authenticationUrl", this.authenticationUrl);
            jsonObject.addProperty("redirectUri", this.redirectUri);
            jsonObject.addProperty("port", this.port);
            return jsonObject;
        }

        public String authenticationUrl() {
            return this.authenticationUrl;
        }

        public String redirectUri() {
            return this.redirectUri;
        }

        public int port() {
            return this.port;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            ExternalBrowser externalBrowser = (ExternalBrowser)object;
            return Objects.equals(this.authenticationUrl, externalBrowser.authenticationUrl) && Objects.equals(this.redirectUri, externalBrowser.redirectUri) && this.port == externalBrowser.port;
        }

        public int hashCode() {
            return Objects.hash(this.authenticationUrl, this.redirectUri, this.port);
        }

        public String toString() {
            return "ExternalBrowser[authenticationUrl=" + this.authenticationUrl + ", redirectUri=" + this.redirectUri + ", port=" + this.port + ']';
        }

        static String access$000(ExternalBrowser externalBrowser) {
            return externalBrowser.authenticationUrl;
        }
    }
}

