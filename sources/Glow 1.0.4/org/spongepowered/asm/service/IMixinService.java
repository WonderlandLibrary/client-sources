package org.spongepowered.asm.service;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import java.util.*;
import java.io.*;

public interface IMixinService
{
    String getName();
    
    boolean isValid();
    
    void prepare();
    
    MixinEnvironment.Phase getInitialPhase();
    
    void init();
    
    void beginPhase();
    
    void checkEnv(final Object p0);
    
    ReEntranceLock getReEntranceLock();
    
    IClassProvider getClassProvider();
    
    IClassBytecodeProvider getBytecodeProvider();
    
    Collection<String> getPlatformAgents();
    
    InputStream getResourceAsStream(final String p0);
    
    void registerInvalidClass(final String p0);
    
    boolean isClassLoaded(final String p0);
    
    String getClassRestrictions(final String p0);
    
    Collection<ITransformer> getTransformers();
    
    String getSideName();
}
