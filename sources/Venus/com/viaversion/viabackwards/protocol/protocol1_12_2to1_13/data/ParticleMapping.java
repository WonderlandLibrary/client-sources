/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import java.util.List;

public class ParticleMapping {
    private static final ParticleData[] particles;

    public static ParticleData getMapping(int n) {
        return particles[n];
    }

    private static ParticleData rewrite(int n) {
        return new ParticleData(n, null);
    }

    private static ParticleData rewrite(int n, ParticleHandler particleHandler) {
        return new ParticleData(n, particleHandler, null);
    }

    static {
        ParticleHandler particleHandler = new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, PacketWrapper packetWrapper) throws Exception {
                return this.rewrite(packetWrapper.read(Type.VAR_INT));
            }

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, List<Particle.ParticleData> list) {
                return this.rewrite((Integer)list.get(0).getValue());
            }

            private int[] rewrite(int n) {
                int n2 = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(n);
                int n3 = n2 >> 4;
                int n4 = n2 & 0xF;
                return new int[]{n3 + (n4 << 12)};
            }

            @Override
            public boolean isBlockHandler() {
                return false;
            }
        };
        particles = new ParticleData[]{ParticleMapping.rewrite(16), ParticleMapping.rewrite(20), ParticleMapping.rewrite(35), ParticleMapping.rewrite(37, particleHandler), ParticleMapping.rewrite(4), ParticleMapping.rewrite(29), ParticleMapping.rewrite(9), ParticleMapping.rewrite(44), ParticleMapping.rewrite(42), ParticleMapping.rewrite(19), ParticleMapping.rewrite(18), ParticleMapping.rewrite(30, new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, PacketWrapper packetWrapper) throws Exception {
                float f = packetWrapper.read(Type.FLOAT).floatValue();
                float f2 = packetWrapper.read(Type.FLOAT).floatValue();
                float f3 = packetWrapper.read(Type.FLOAT).floatValue();
                float f4 = packetWrapper.read(Type.FLOAT).floatValue();
                packetWrapper.set(Type.FLOAT, 3, Float.valueOf(f));
                packetWrapper.set(Type.FLOAT, 4, Float.valueOf(f2));
                packetWrapper.set(Type.FLOAT, 5, Float.valueOf(f3));
                packetWrapper.set(Type.FLOAT, 6, Float.valueOf(f4));
                packetWrapper.set(Type.INT, 1, 0);
                return null;
            }

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, List<Particle.ParticleData> list) {
                return null;
            }
        }), ParticleMapping.rewrite(13), ParticleMapping.rewrite(41), ParticleMapping.rewrite(10), ParticleMapping.rewrite(25), ParticleMapping.rewrite(43), ParticleMapping.rewrite(15), ParticleMapping.rewrite(2), ParticleMapping.rewrite(1), ParticleMapping.rewrite(46, particleHandler), ParticleMapping.rewrite(3), ParticleMapping.rewrite(6), ParticleMapping.rewrite(26), ParticleMapping.rewrite(21), ParticleMapping.rewrite(34), ParticleMapping.rewrite(14), ParticleMapping.rewrite(36, new ParticleHandler(){

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, PacketWrapper packetWrapper) throws Exception {
                return this.rewrite(protocol1_12_2To1_13, packetWrapper.read(Type.FLAT_ITEM));
            }

            @Override
            public int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, List<Particle.ParticleData> list) {
                return this.rewrite(protocol1_12_2To1_13, (Item)list.get(0).getValue());
            }

            private int[] rewrite(Protocol1_12_2To1_13 protocol1_12_2To1_13, Item item) {
                Item item2 = protocol1_12_2To1_13.getItemRewriter().handleItemToClient(item);
                return new int[]{item2.identifier(), item2.data()};
            }
        }), ParticleMapping.rewrite(33), ParticleMapping.rewrite(31), ParticleMapping.rewrite(12), ParticleMapping.rewrite(27), ParticleMapping.rewrite(22), ParticleMapping.rewrite(23), ParticleMapping.rewrite(0), ParticleMapping.rewrite(24), ParticleMapping.rewrite(39), ParticleMapping.rewrite(11), ParticleMapping.rewrite(48), ParticleMapping.rewrite(12), ParticleMapping.rewrite(45), ParticleMapping.rewrite(47), ParticleMapping.rewrite(7), ParticleMapping.rewrite(5), ParticleMapping.rewrite(17), ParticleMapping.rewrite(4), ParticleMapping.rewrite(4), ParticleMapping.rewrite(4), ParticleMapping.rewrite(18), ParticleMapping.rewrite(18)};
    }

    public static final class ParticleData {
        private final int historyId;
        private final ParticleHandler handler;

        private ParticleData(int n, ParticleHandler particleHandler) {
            this.historyId = n;
            this.handler = particleHandler;
        }

        private ParticleData(int n) {
            this(n, (ParticleHandler)null);
        }

        public int[] rewriteData(Protocol1_12_2To1_13 protocol1_12_2To1_13, PacketWrapper packetWrapper) throws Exception {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol1_12_2To1_13, packetWrapper);
        }

        public int[] rewriteMeta(Protocol1_12_2To1_13 protocol1_12_2To1_13, List<Particle.ParticleData> list) {
            if (this.handler == null) {
                return null;
            }
            return this.handler.rewrite(protocol1_12_2To1_13, list);
        }

        public int getHistoryId() {
            return this.historyId;
        }

        public ParticleHandler getHandler() {
            return this.handler;
        }

        ParticleData(int n, 1 var2_2) {
            this(n);
        }

        ParticleData(int n, ParticleHandler particleHandler, 1 var3_3) {
            this(n, particleHandler);
        }
    }

    public static interface ParticleHandler {
        public int[] rewrite(Protocol1_12_2To1_13 var1, PacketWrapper var2) throws Exception;

        public int[] rewrite(Protocol1_12_2To1_13 var1, List<Particle.ParticleData> var2);

        default public boolean isBlockHandler() {
            return true;
        }
    }
}

