package com.viaversion.viaversion.api.protocol;

import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolPipeline extends SimpleProtocol {
   void add(Protocol var1);

   void add(Collection<Protocol> var1);

   boolean contains(Class<? extends Protocol> var1);

   @Nullable
   <P extends Protocol> P getProtocol(Class<P> var1);

   List<Protocol> pipes();

   List<Protocol> reversedPipes();

   boolean hasNonBaseProtocols();

   void cleanPipes();
}
