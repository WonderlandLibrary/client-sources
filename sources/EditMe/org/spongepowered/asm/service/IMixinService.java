package org.spongepowered.asm.service;

import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.ReEntranceLock;

public interface IMixinService {
   String getName();

   boolean isValid();

   void prepare();

   MixinEnvironment.Phase getInitialPhase();

   void init();

   void beginPhase();

   void checkEnv(Object var1);

   ReEntranceLock getReEntranceLock();

   IClassProvider getClassProvider();

   IClassBytecodeProvider getBytecodeProvider();

   Collection getPlatformAgents();

   InputStream getResourceAsStream(String var1);

   void registerInvalidClass(String var1);

   boolean isClassLoaded(String var1);

   Collection getTransformers();

   String getSideName();
}
