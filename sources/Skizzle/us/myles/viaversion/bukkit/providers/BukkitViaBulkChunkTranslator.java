/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package us.myles.ViaVersion.bukkit.providers;

import com.google.common.collect.Lists;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import us.myles.ViaVersion.ViaVersionPlugin;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.bukkit.util.NMSUtil;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BulkChunkTranslatorProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;
import us.myles.ViaVersion.util.ReflectionUtil;

public class BukkitViaBulkChunkTranslator
extends BulkChunkTranslatorProvider {
    private static ReflectionUtil.ClassReflection mapChunkBulkRef;
    private static ReflectionUtil.ClassReflection mapChunkRef;
    private static Method obfuscateRef;

    @Override
    public List<Object> transformMapChunkBulk(Object packet, ClientChunks clientChunks) {
        ArrayList list = Lists.newArrayList();
        try {
            int[] xcoords = mapChunkBulkRef.getFieldValue("a", packet, int[].class);
            int[] zcoords = mapChunkBulkRef.getFieldValue("b", packet, int[].class);
            Object[] chunkMaps = mapChunkBulkRef.getFieldValue("c", packet, Object[].class);
            if (Via.getConfig().isAntiXRay() && ((ViaVersionPlugin)Via.getPlatform()).isSpigot()) {
                try {
                    Object world = mapChunkBulkRef.getFieldValue("world", packet, Object.class);
                    Object spigotConfig = ReflectionUtil.getPublic(world, "spigotConfig", Object.class);
                    Object antiXrayInstance = ReflectionUtil.getPublic(spigotConfig, "antiXrayInstance", Object.class);
                    for (int i = 0; i < xcoords.length; ++i) {
                        Object b = ReflectionUtil.get(chunkMaps[i], "b", Object.class);
                        Object a = ReflectionUtil.get(chunkMaps[i], "a", Object.class);
                        obfuscateRef.invoke(antiXrayInstance, xcoords[i], zcoords[i], b, a, world);
                    }
                }
                catch (Exception world) {
                    // empty catch block
                }
            }
            for (int i = 0; i < chunkMaps.length; ++i) {
                int x = xcoords[i];
                int z = zcoords[i];
                Object chunkMap = chunkMaps[i];
                Object chunkPacket = mapChunkRef.newInstance();
                mapChunkRef.setFieldValue("a", chunkPacket, x);
                mapChunkRef.setFieldValue("b", chunkPacket, z);
                mapChunkRef.setFieldValue("c", chunkPacket, chunkMap);
                mapChunkRef.setFieldValue("d", chunkPacket, true);
                clientChunks.getBulkChunks().add(ClientChunks.toLong(x, z));
                list.add(chunkPacket);
            }
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to transform chunks bulk", e);
        }
        return list;
    }

    @Override
    public boolean isFiltered(Class<?> packetClass) {
        return packetClass.getName().endsWith("PacketPlayOutMapChunkBulk");
    }

    @Override
    public boolean isPacketLevel() {
        return false;
    }

    static {
        try {
            mapChunkBulkRef = new ReflectionUtil.ClassReflection(NMSUtil.nms("PacketPlayOutMapChunkBulk"));
            mapChunkRef = new ReflectionUtil.ClassReflection(NMSUtil.nms("PacketPlayOutMapChunk"));
            if (((ViaVersionPlugin)Via.getPlatform()).isSpigot()) {
                obfuscateRef = Class.forName("org.spigotmc.AntiXray").getMethod("obfuscate", Integer.TYPE, Integer.TYPE, Integer.TYPE, byte[].class, NMSUtil.nms("World"));
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to initialise chunks reflection", e);
        }
    }
}

