package net.minecraftforge.client.model;

import com.google.common.base.Function;

public interface IModelState extends Function<IModelPart, TRSRTransformation> {
   TRSRTransformation apply(IModelPart var1);

   default Object apply(Object var1) {
      return this.apply((IModelPart)var1);
   }
}
