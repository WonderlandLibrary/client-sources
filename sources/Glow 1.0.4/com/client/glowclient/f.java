package com.client.glowclient;

import net.minecraft.entity.*;
import javax.annotation.*;
import java.util.*;

public interface f extends e
{
    int M(@Nonnull final String p0, @Nullable final Entity p1);
    
    @Nullable
    String k(@Nonnull final String p0);
    
    @Nullable
    String A(@Nonnull final String p0);
    
    @Nonnull
    Set<String> M();
    
    @Nullable
    String M(@Nonnull final Entity p0);
    
    @Nullable
    String D(@Nonnull final String p0);
    
    boolean D(@Nonnull final String p0);
}
