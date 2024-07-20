/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.util;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Vector;

public class RelativeMoveUtil {
    public static Vector[] calculateRelativeMoves(UserConnection user, int entityId, int relX, int relY, int relZ) {
        Vector[] moves;
        int sentRelZ;
        int sentRelY;
        int sentRelX;
        int z;
        int y;
        int x;
        EntityTracker tracker = user.get(EntityTracker.class);
        Vector offset = tracker.getEntityOffset(entityId);
        if (offset != null) {
            relX += offset.blockX();
            relY += offset.blockY();
            relZ += offset.blockZ();
        }
        if (relX > Short.MAX_VALUE) {
            x = relX - Short.MAX_VALUE;
            relX = Short.MAX_VALUE;
        } else if (relX < Short.MIN_VALUE) {
            x = relX - Short.MIN_VALUE;
            relX = Short.MIN_VALUE;
        } else {
            x = 0;
        }
        if (relY > Short.MAX_VALUE) {
            y = relY - Short.MAX_VALUE;
            relY = Short.MAX_VALUE;
        } else if (relY < Short.MIN_VALUE) {
            y = relY - Short.MIN_VALUE;
            relY = Short.MIN_VALUE;
        } else {
            y = 0;
        }
        if (relZ > Short.MAX_VALUE) {
            z = relZ - Short.MAX_VALUE;
            relZ = Short.MAX_VALUE;
        } else if (relZ < Short.MIN_VALUE) {
            z = relZ - Short.MIN_VALUE;
            relZ = Short.MIN_VALUE;
        } else {
            z = 0;
        }
        if (relX > 16256 || relX < -16384 || relY > 16256 || relY < -16384 || relZ > 16256 || relZ < -16384) {
            byte relX1 = (byte)(relX / 256);
            byte relX2 = (byte)Math.round((float)(relX - relX1 * 128) / 128.0f);
            byte relY1 = (byte)(relY / 256);
            byte relY2 = (byte)Math.round((float)(relY - relY1 * 128) / 128.0f);
            byte relZ1 = (byte)(relZ / 256);
            byte relZ2 = (byte)Math.round((float)(relZ - relZ1 * 128) / 128.0f);
            sentRelX = relX1 + relX2;
            sentRelY = relY1 + relY2;
            sentRelZ = relZ1 + relZ2;
            moves = new Vector[]{new Vector(relX1, relY1, relZ1), new Vector(relX2, relY2, relZ2)};
        } else {
            sentRelX = Math.round((float)relX / 128.0f);
            sentRelY = Math.round((float)relY / 128.0f);
            sentRelZ = Math.round((float)relZ / 128.0f);
            moves = new Vector[]{new Vector(sentRelX, sentRelY, sentRelZ)};
        }
        x = x + relX - sentRelX * 128;
        y = y + relY - sentRelY * 128;
        z = z + relZ - sentRelZ * 128;
        tracker.setEntityOffset(entityId, new Vector(x, y, z));
        return moves;
    }
}

