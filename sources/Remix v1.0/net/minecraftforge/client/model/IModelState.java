package net.minecraftforge.client.model;

import com.google.common.base.*;

public interface IModelState extends Function<IModelPart, TRSRTransformation>
{
    TRSRTransformation apply(final IModelPart p0);
}
