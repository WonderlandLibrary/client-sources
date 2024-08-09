/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step;

import net.raphimc.mcauth.step.AbstractStep;
import org.apache.http.client.HttpClient;

public abstract class OptionalMergeStep<I1 extends AbstractStep.StepResult<?>, I2 extends AbstractStep.StepResult<?>, O extends StepResult<?, ?>>
extends AbstractStep<I1, O> {
    public final AbstractStep<?, I2> prevStep2;

    public OptionalMergeStep(AbstractStep<?, I1> abstractStep, AbstractStep<?, I2> abstractStep2) {
        super(abstractStep);
        this.prevStep2 = abstractStep2;
    }

    @Override
    public O applyStep(HttpClient httpClient, I1 I1) throws Exception {
        return this.applyStep(httpClient, I1, null);
    }

    public abstract O applyStep(HttpClient var1, I1 var2, I2 var3) throws Exception;

    @Override
    public O refresh(HttpClient httpClient, O o) throws Exception {
        I1 I1 = this.prevStep.refresh(httpClient, o != null ? (Object)o.prevResult() : null);
        I2 I2 = this.prevStep2 != null ? this.prevStep2.refresh(httpClient, o != null ? (Object)o.prevResult2() : null) : null;
        return this.applyStep(httpClient, I1, I2);
    }

    @Override
    public O getFromInput(HttpClient httpClient, Object object) throws Exception {
        Object o = this.prevStep.getFromInput(httpClient, object);
        I2 I2 = this.prevStep2 != null ? (I2)this.prevStep2.getFromInput(httpClient, object) : null;
        return this.applyStep(httpClient, o, I2);
    }

    @Override
    public AbstractStep.StepResult getFromInput(HttpClient httpClient, Object object) throws Exception {
        return this.getFromInput(httpClient, object);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (O)((StepResult)stepResult));
    }

    @Override
    public AbstractStep.StepResult applyStep(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.applyStep(httpClient, stepResult);
    }

    public static interface StepResult<P1 extends AbstractStep.StepResult<?>, P2 extends AbstractStep.StepResult<?>>
    extends AbstractStep.StepResult<P1> {
        public P2 prevResult2();

        @Override
        default public boolean isExpired() throws Exception {
            return AbstractStep.StepResult.super.isExpired() || this.prevResult2() != null && this.prevResult2().isExpired();
        }
    }
}

