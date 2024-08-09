/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step.xbl.session;

import com.google.gson.JsonObject;
import java.util.Objects;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.OptionalMergeStep;
import net.raphimc.mcauth.step.SameInputOptionalMergeStep;
import net.raphimc.mcauth.step.xbl.StepXblTitleToken;
import net.raphimc.mcauth.step.xbl.StepXblUserToken;
import net.raphimc.mcauth.step.xbl.session.StepInitialXblSession;
import org.apache.http.client.HttpClient;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StepFullXblSession
extends SameInputOptionalMergeStep<StepXblUserToken.XblUserToken, StepXblTitleToken.XblTitleToken, StepInitialXblSession.InitialXblSession, FullXblSession> {
    public StepFullXblSession(AbstractStep<StepInitialXblSession.InitialXblSession, StepXblUserToken.XblUserToken> abstractStep, AbstractStep<StepInitialXblSession.InitialXblSession, StepXblTitleToken.XblTitleToken> abstractStep2) {
        super(abstractStep, abstractStep2);
    }

    @Override
    public FullXblSession applyStep(HttpClient httpClient, StepXblUserToken.XblUserToken xblUserToken, StepXblTitleToken.XblTitleToken xblTitleToken) throws Exception {
        return new FullXblSession(xblUserToken, xblTitleToken);
    }

    @Override
    public FullXblSession fromJson(JsonObject jsonObject) throws Exception {
        StepXblUserToken.XblUserToken xblUserToken = this.prevStep != null ? (StepXblUserToken.XblUserToken)this.prevStep.fromJson(jsonObject.getAsJsonObject("prev")) : null;
        StepXblTitleToken.XblTitleToken xblTitleToken = this.prevStep2 != null ? (StepXblTitleToken.XblTitleToken)this.prevStep2.fromJson(jsonObject.getAsJsonObject("prev2")) : null;
        return new FullXblSession(xblUserToken, xblTitleToken);
    }

    @Override
    public OptionalMergeStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult, AbstractStep.StepResult stepResult2) throws Exception {
        return this.applyStep(httpClient, (StepXblUserToken.XblUserToken)stepResult, (StepXblTitleToken.XblTitleToken)stepResult2);
    }

    @Override
    public AbstractStep.StepResult fromJson(JsonObject jsonObject) throws Exception {
        return this.fromJson(jsonObject);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class FullXblSession
    implements OptionalMergeStep.StepResult<StepXblUserToken.XblUserToken, StepXblTitleToken.XblTitleToken> {
        private final StepXblUserToken.XblUserToken prevResult;
        private final StepXblTitleToken.XblTitleToken prevResult2;

        public FullXblSession(StepXblUserToken.XblUserToken xblUserToken, StepXblTitleToken.XblTitleToken xblTitleToken) {
            this.prevResult = xblUserToken;
            this.prevResult2 = xblTitleToken;
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
        public StepXblUserToken.XblUserToken prevResult() {
            return this.prevResult;
        }

        @Override
        public StepXblTitleToken.XblTitleToken prevResult2() {
            return this.prevResult2;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null || object.getClass() != this.getClass()) {
                return true;
            }
            FullXblSession fullXblSession = (FullXblSession)object;
            return Objects.equals(this.prevResult, fullXblSession.prevResult) && Objects.equals(this.prevResult2, fullXblSession.prevResult2);
        }

        public int hashCode() {
            return Objects.hash(this.prevResult, this.prevResult2);
        }

        public String toString() {
            return "FullXblSession[prevResult=" + this.prevResult + ", prevResult2=" + this.prevResult2 + ']';
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

