/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import net.raphimc.mcauth.MinecraftAuth;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.msa.MsaCodeStep;
import net.raphimc.mcauth.step.msa.MsaResponseHandler;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepMsaDeviceCodeMsaCode
extends MsaCodeStep<StepMsaDeviceCode.MsaDeviceCode> {
    public static final String TOKEN_URL = "https://login.live.com/oauth20_token.srf";
    private final int timeout;

    public StepMsaDeviceCodeMsaCode(AbstractStep<?, StepMsaDeviceCode.MsaDeviceCode> abstractStep, String string, String string2, int n) {
        this(abstractStep, string, string2, null, n);
    }

    public StepMsaDeviceCodeMsaCode(AbstractStep<?, StepMsaDeviceCode.MsaDeviceCode> abstractStep, String string, String string2, String string3, int n) {
        super(abstractStep, string, string2, string3);
        this.timeout = n;
    }

    @Override
    public MsaCodeStep.MsaCode applyStep(HttpClient httpClient, StepMsaDeviceCode.MsaDeviceCode msaDeviceCode) throws Exception {
        MinecraftAuth.LOGGER.info("Waiting for MSA login via device code...");
        long l = System.currentTimeMillis();
        while (!msaDeviceCode.isExpired() && System.currentTimeMillis() - l <= (long)this.timeout) {
            ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
            arrayList.add(new BasicNameValuePair("client_id", this.clientId));
            arrayList.add(new BasicNameValuePair("device_code", msaDeviceCode.deviceCode()));
            arrayList.add(new BasicNameValuePair("grant_type", "device_code"));
            HttpPost httpPost = new HttpPost(TOKEN_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(arrayList, StandardCharsets.UTF_8));
            try {
                String string = httpClient.execute((HttpUriRequest)httpPost, new MsaResponseHandler());
                JsonObject jsonObject = JsonParser.parseString(string).getAsJsonObject();
                MsaCodeStep.MsaCode msaCode = new MsaCodeStep.MsaCode(jsonObject.get("refresh_token").getAsString(), this.clientId, this.scope, this.clientSecret, null);
                MinecraftAuth.LOGGER.info("Got MSA Code");
                return msaCode;
            } catch (HttpResponseException httpResponseException) {
                if (httpResponseException.getStatusCode() == 400 && httpResponseException.getReasonPhrase().startsWith("authorization_pending")) {
                    Thread.sleep(msaDeviceCode.intervalMs());
                    continue;
                }
                throw httpResponseException;
            }
        }
        throw new TimeoutException("Failed to get MSA Code. Login timed out");
    }

    @Override
    public MsaCodeStep.MsaCode applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepMsaDeviceCode.MsaDeviceCode)stepResult);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, (StepMsaDeviceCode.MsaDeviceCode)stepResult);
    }
}

