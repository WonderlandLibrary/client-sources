package com.viaversion.viaversion.rewriter.meta;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;

@FunctionalInterface
public interface MetaHandler {
   void handle(MetaHandlerEvent var1, Metadata var2);
}
