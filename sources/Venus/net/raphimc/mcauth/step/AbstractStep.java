/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step;

import com.google.gson.JsonObject;
import org.apache.http.client.HttpClient;

public abstract class AbstractStep<I extends StepResult<?>, O extends StepResult<?>> {
    public final AbstractStep<?, I> prevStep;

    public AbstractStep(AbstractStep<?, I> abstractStep) {
        this.prevStep = abstractStep;
    }

    public abstract O applyStep(HttpClient var1, I var2) throws Exception;

    public O refresh(HttpClient httpClient, O o) throws Exception {
        return this.applyStep(httpClient, this.prevStep != null ? this.prevStep.refresh(httpClient, o != null ? (O)o.prevResult() : null) : null);
    }

    public O getFromInput(HttpClient httpClient, Object object) throws Exception {
        return this.applyStep(httpClient, this.prevStep != null ? this.prevStep.getFromInput(httpClient, object) : (StepResult)object);
    }

    public abstract O fromJson(JsonObject var1) throws Exception;

    public static interface InitialInput
    extends StepResult<StepResult<?>> {
        @Override
        default public StepResult<?> prevResult() {
            throw new UnsupportedOperationException();
        }

        @Override
        default public JsonObject toJson() throws Exception {
            throw new UnsupportedOperationException();
        }

        @Override
        default public boolean isExpired() throws Exception {
            throw new UnsupportedOperationException();
        }
    }

    public static interface StepResult<P extends StepResult<?>> {
        public P prevResult();

        public JsonObject toJson() throws Exception;

        default public boolean isExpired() throws Exception {
            return false;
        }
    }
}

