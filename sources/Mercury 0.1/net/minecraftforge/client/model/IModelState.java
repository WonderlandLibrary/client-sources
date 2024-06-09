/*
 * Decompiled with CFR 0.145.
 */
package net.minecraftforge.client.model;

import com.google.common.base.Function;
import net.minecraftforge.client.model.IModelPart;
import net.minecraftforge.client.model.TRSRTransformation;

public interface IModelState
extends Function<IModelPart, TRSRTransformation> {
    @Override
    public TRSRTransformation apply(IModelPart var1);
}

