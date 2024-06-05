package net.minecraft.client.audio;

import java.lang.reflect.*;
import java.util.*;

static final class SoundHandler$1 implements ParameterizedType {
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { String.class, SoundList.class };
    }
    
    @Override
    public Type getRawType() {
        return Map.class;
    }
    
    @Override
    public Type getOwnerType() {
        return null;
    }
}