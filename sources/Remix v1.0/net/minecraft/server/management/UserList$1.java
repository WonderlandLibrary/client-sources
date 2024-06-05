package net.minecraft.server.management;

import java.lang.reflect.*;
import java.util.*;

static final class UserList$1 implements ParameterizedType {
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] { UserListEntry.class };
    }
    
    @Override
    public Type getRawType() {
        return List.class;
    }
    
    @Override
    public Type getOwnerType() {
        return null;
    }
}