/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.step;

import java.lang.reflect.ParameterizedType;
import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.OptionalMergeStep;
import org.apache.http.client.HttpClient;

public abstract class SameInputOptionalMergeStep<I1 extends AbstractStep.StepResult<?>, I2 extends AbstractStep.StepResult<?>, I extends AbstractStep.StepResult<?>, O extends OptionalMergeStep.StepResult<?, ?>>
extends OptionalMergeStep<I1, I2, O> {
    public SameInputOptionalMergeStep(AbstractStep<I, I1> abstractStep, AbstractStep<I, I2> abstractStep2) {
        super(abstractStep, abstractStep2);
        if (this.prevStep2 != null && !((ParameterizedType)this.prevStep.getClass().getGenericSuperclass()).getActualTypeArguments()[0].equals(((ParameterizedType)this.prevStep2.getClass().getGenericSuperclass()).getActualTypeArguments()[0])) {
            throw new IllegalStateException("Steps do not take the same input");
        }
    }

    @Override
    public O refresh(HttpClient httpClient, O o) throws Exception {
        AbstractStep.StepResult stepResult = this.prevStep.refresh(httpClient, o != null ? (Object)o.prevResult() : null);
        Object I2 = this.prevStep2 != null && o == null ? (Object)this.prevStep2.applyStep(httpClient, stepResult.prevResult()) : (this.prevStep2 != null ? (Object)this.prevStep2.refresh(httpClient, o.prevResult2()) : null);
        return this.applyStep(httpClient, stepResult, I2);
    }

    @Override
    public AbstractStep.StepResult refresh(HttpClient httpClient, AbstractStep.StepResult stepResult) throws Exception {
        return this.refresh(httpClient, (O)((OptionalMergeStep.StepResult)stepResult));
    }
}

