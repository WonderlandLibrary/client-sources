/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.msa;

import com.google.gson.JsonObject;
import java.util.Objects;
import net.raphimc.mcauth.step.AbstractStep;
import org.apache.http.client.HttpClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MsaCodeStep<I extends AbstractStep.StepResult<?>>
extends AbstractStep<I, MsaCode> {
    protected final String clientId;
    protected final String scope;
    protected final String clientSecret;

    public MsaCodeStep(AbstractStep<?, I> abstractStep, String string, String string2, String string3) {
        super(abstractStep);
        this.clientId = string;
        this.scope = string2;
        this.clientSecret = string3;
    }

    @Override
    public MsaCode applyStep(HttpClient httpClient, I i) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public MsaCode fromJson(JsonObject jsonObject) throws Exception {
        return new MsaCode(jsonObject.get("code").getAsString(), jsonObject.get("clientId").getAsString(), jsonObject.get("scope").getAsString(), jsonObject.get("clientSecret") != null && !jsonObject.get("clientSecret").isJsonNull() ? jsonObject.get("clientSecret").getAsString() : null, null);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, stepResult);
    }

    public static final class MsaCode
    implements AbstractStep.StepResult<AbstractStep.StepResult<?>> {
        private final String code;
        private final String clientId;
        private final String scope;
        private final String clientSecret;
        private final String redirectUri;

        public MsaCode(String string, String string2, String string3, String string4, String string5) {
            this.code = string;
            this.clientId = string2;
            this.scope = string3;
            this.clientSecret = string4;
            this.redirectUri = string5;
        }

        @Override
        public AbstractStep.StepResult<?> prevResult() {
            return null;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code", this.code);
            jsonObject.addProperty("clientId", this.clientId);
            jsonObject.addProperty("scope", this.scope);
            jsonObject.addProperty("clientSecret", this.clientSecret);
            return jsonObject;
        }

        public String code() {
            return this.code;
        }

        public String clientId() {
            return this.clientId;
        }

        public String scope() {
            return this.scope;
        }

        public String clientSecret() {
            return this.clientSecret;
        }

        public String redirectUri() {
            return this.redirectUri;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            MsaCode msaCode = (MsaCode)object;
            return Objects.equals(this.code, msaCode.code) && Objects.equals(this.clientId, msaCode.clientId) && Objects.equals(this.scope, msaCode.scope) && Objects.equals(this.clientSecret, msaCode.clientSecret) && Objects.equals(this.redirectUri, msaCode.redirectUri);
        }

        public int hashCode() {
            return Objects.hash(this.code, this.clientId, this.scope, this.clientSecret, this.redirectUri);
        }

        public String toString() {
            return "MsaCode{code='" + this.code + '\'' + ", clientId='" + this.clientId + '\'' + ", scope='" + this.scope + '\'' + ", clientSecret='" + this.clientSecret + '\'' + ", redirectUri='" + this.redirectUri + '\'' + '}';
        }
    }
}

