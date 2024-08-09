/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.msa.MsaCodeStep;
import net.raphimc.mcauth.step.msa.MsaCredentialsResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepCredentialsMsaCode
extends MsaCodeStep<MsaCredentials> {
    public static final String AUTHORIZE_URL = "https://login.live.com/oauth20_authorize.srf";
    private final String redirectUri;

    public StepCredentialsMsaCode(String string, String string2, String string3) {
        this(string, string2, null, string3);
    }

    public StepCredentialsMsaCode(String string, String string2, String string3, String string4) {
        super(null, string, string2, string3);
        this.redirectUri = string4;
    }

    @Override
    public MsaCodeStep.MsaCode applyStep(HttpClient httpClient, MsaCredentials msaCredentials) throws Exception {
        MinecraftAuth.LOGGER.info("Trying to get MSA Code using email and password...");
        if (msaCredentials == null) {
            throw new IllegalStateException("Missing StepCredentialsMsaCode.MsaCredentials input");
        }
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(basicCookieStore);
        HttpGet httpGet = new HttpGet(this.getAuthenticationUrl());
        httpGet.setHeader("Accept", ContentType.TEXT_HTML.getMimeType());
        String string = httpClient.execute(httpGet, new BasicResponseHandler(), (HttpContext)httpClientContext);
        String string2 = string.substring(string.indexOf("urlPost:"));
        string2 = string2.substring(string2.indexOf("'") + 1);
        string2 = string2.substring(0, string2.indexOf("'"));
        String string3 = string.substring(string.indexOf("sFTTag:"));
        string3 = string3.substring(string3.indexOf("value=\""));
        string3 = string3.substring(string3.indexOf("\"") + 1);
        string3 = string3.substring(0, string3.indexOf("\""));
        ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
        arrayList.add(new BasicNameValuePair("login", MsaCredentials.access$000(msaCredentials)));
        arrayList.add(new BasicNameValuePair("loginfmt", MsaCredentials.access$000(msaCredentials)));
        arrayList.add(new BasicNameValuePair("passwd", MsaCredentials.access$100(msaCredentials)));
        arrayList.add(new BasicNameValuePair("PPFT", string3));
        HttpPost httpPost = new HttpPost(string2);
        httpPost.setHeader("Accept", ContentType.TEXT_HTML.getMimeType());
        httpPost.setEntity(new UrlEncodedFormEntity(arrayList, StandardCharsets.UTF_8));
        String string4 = httpClient.execute(httpPost, new MsaCredentialsResponseHandler(), (HttpContext)httpClientContext);
        MsaCodeStep.MsaCode msaCode = new MsaCodeStep.MsaCode(string4, this.clientId, this.scope, this.clientSecret, this.redirectUri);
        MinecraftAuth.LOGGER.info("Got MSA Code");
        return msaCode;
    }

    private URI getAuthenticationUrl() throws URISyntaxException {
        return new URIBuilder(AUTHORIZE_URL).setParameter("client_id", this.clientId).setParameter("redirect_uri", this.redirectUri).setParameter("response_type", "code").setParameter("scope", this.scope).build();
    }

    @Override
    public MsaCodeStep.MsaCode applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (MsaCredentials)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (MsaCredentials)stepResult);
    }

    public static final class MsaCredentials
    implements AbstractStep.InitialInput {
        private final String email;
        private final String password;

        public MsaCredentials(String string, String string2) {
            this.email = string;
            this.password = string2;
        }

        public static MsaCredentials fromJson(JsonObject jsonObject) {
            return new MsaCredentials(jsonObject.get("email").getAsString(), jsonObject.get("password").getAsString());
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", this.email);
            jsonObject.addProperty("password", this.password);
            return jsonObject;
        }

        public String email() {
            return this.email;
        }

        public String password() {
            return this.password;
        }

        static String access$000(MsaCredentials msaCredentials) {
            return msaCredentials.email;
        }

        static String access$100(MsaCredentials msaCredentials) {
            return msaCredentials.password;
        }
    }
}

