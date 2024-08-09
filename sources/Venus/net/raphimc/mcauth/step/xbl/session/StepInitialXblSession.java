/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl.session;

import com.google.gson.JsonObject;
import java.util.Objects;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.OptionalMergeStep;
import net.raphimc.mcauth.step.msa.StepMsaToken;
import net.raphimc.mcauth.step.xbl.StepXblDeviceToken;
import org.apache.http.client.HttpClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepInitialXblSession
extends OptionalMergeStep<StepMsaToken.MsaToken, StepXblDeviceToken.XblDeviceToken, InitialXblSession> {
    public StepInitialXblSession(AbstractStep<?, StepMsaToken.MsaToken> abstractStep, AbstractStep<?, StepXblDeviceToken.XblDeviceToken> abstractStep2) {
        super(abstractStep, abstractStep2);
    }

    @Override
    public InitialXblSession applyStep(HttpClient httpClient, StepMsaToken.MsaToken msaToken, StepXblDeviceToken.XblDeviceToken xblDeviceToken) throws Exception {
        return new InitialXblSession(msaToken, xblDeviceToken);
    }

    @Override
    public InitialXblSession fromJson(JsonObject jsonObject) throws Exception {
        StepMsaToken.MsaToken msaToken = this.prevStep != null ? (StepMsaToken.MsaToken)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        StepXblDeviceToken.XblDeviceToken xblDeviceToken = this.prevStep2 != null ? (StepXblDeviceToken.XblDeviceToken)this.prevStep2.fromJson(jsonObject.getAsJsonObject("prev2")) : null;
        return new InitialXblSession(msaToken, xblDeviceToken);
    }

    @Override
    public OptionalMergeStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult, AbstractStep.StepResult stepResult2) throws Exception {
        return this.applyStep(httpClient, (StepMsaToken.MsaToken)stepResult, (StepXblDeviceToken.XblDeviceToken)stepResult2);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class InitialXblSession
    implements OptionalMergeStep.StepResult<StepMsaToken.MsaToken, StepXblDeviceToken.XblDeviceToken> {
        private final StepMsaToken.MsaToken prevResult;
        private final StepXblDeviceToken.XblDeviceToken prevResult2;

        public InitialXblSession(StepMsaToken.MsaToken msaToken, StepXblDeviceToken.XblDeviceToken xblDeviceToken) {
            this.prevResult = msaToken;
            this.prevResult2 = xblDeviceToken;
        }

        @Override
        public JsonObject toJson() {
            JsonObject jsonObject = new JsonObject();
            if (this.prevResult != null) {
                jsonObject.add("prev", this.prevResult.toJson());
            }
            if (this.prevResult2 != null) {
                jsonObject.add("prev2", this.prevResult2.toJson());
            }
            return jsonObject;
        }

        @Override
        public StepMsaToken.MsaToken prevResult() {
            return this.prevResult;
        }

        @Override
        public StepXblDeviceToken.XblDeviceToken prevResult2() {
            return this.prevResult2;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            InitialXblSession initialXblSession = (InitialXblSession)object;
            return Objects.equals(this.prevResult, initialXblSession.prevResult) && Objects.equals(this.prevResult2, initialXblSession.prevResult2);
        }

        public int hashCode() {
            return Objects.hash(this.prevResult, this.prevResult2);
        }

        public String toString() {
            return "InitialXblSession[prevResult=" + this.prevResult + ", prevResult2=" + this.prevResult2 + ']';
        }

        @Override
        public AbstractStep.StepResult prevResult2() {
            return this.prevResult2();
        }

        @Override
        public AbstractStep.StepResult prevResult() {
            return this.prevResult();
        }
    }
}

