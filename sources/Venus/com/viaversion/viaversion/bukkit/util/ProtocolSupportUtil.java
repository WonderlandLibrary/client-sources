/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.viaversion.viaversion.bukkit.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public final class ProtocolSupportUtil {
    private static final Method PROTOCOL_VERSION_METHOD;
    private static final Method GET_ID_METHOD;

    public static int getProtocolVersion(Player player) {
        if (PROTOCOL_VERSION_METHOD == null) {
            return 1;
        }
        try {
            Object object = PROTOCOL_VERSION_METHOD.invoke(null, player);
            return (Integer)GET_ID_METHOD.invoke(object, new Object[0]);
        } catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            reflectiveOperationException.printStackTrace();
            return 1;
        }
    }

    static {
        Method method = null;
        Method method2 = null;
        try {
            method = Class.forName("protocolsupport.api.ProtocolSupportAPI").getMethod("getProtocolVersion", Player.class);
            method2 = Class.forName("protocolsupport.api.ProtocolVersion").getMethod("getId", new Class[0]);
        } catch (ReflectiveOperationException reflectiveOperationException) {
            // empty catch block
        }
        PROTOCOL_VERSION_METHOD = method;
        GET_ID_METHOD = method2;
    }
}

