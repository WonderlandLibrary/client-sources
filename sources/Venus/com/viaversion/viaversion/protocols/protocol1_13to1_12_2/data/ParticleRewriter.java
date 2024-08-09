/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets.WorldPackets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ParticleRewriter {
    private static final List<NewParticle> particles = new ArrayList<NewParticle>();

    public static Particle rewriteParticle(int n, Integer[] integerArray) {
        if (n >= particles.size()) {
            Via.getPlatform().getLogger().severe("Failed to transform particles with id " + n + " and data " + Arrays.toString((Object[])integerArray));
            return null;
        }
        NewParticle newParticle = particles.get(n);
        return newParticle.handle(new Particle(newParticle.getId()), integerArray);
    }

    private static void add(int n) {
        particles.add(new NewParticle(n, null));
    }

    private static void add(int n, ParticleDataHandler particleDataHandler) {
        particles.add(new NewParticle(n, particleDataHandler));
    }

    private static ParticleDataHandler reddustHandler() {
        return new ParticleDataHandler(){

            @Override
            public Particle handler(Particle particle, Integer[] integerArray) {
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(ParticleRewriter.access$000() ? 1.0f : 0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(ParticleRewriter.access$000() ? 1.0f : 0.0f)));
                particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(1.0f)));
                return particle;
            }
        };
    }

    private static boolean randomBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static ParticleDataHandler iconcrackHandler() {
        return new ParticleDataHandler(){

            @Override
            public Particle handler(Particle particle, Integer[] integerArray) {
                DataItem dataItem;
                if (integerArray.length == 1) {
                    dataItem = new DataItem(integerArray[0], 1, 0, null);
                } else if (integerArray.length == 2) {
                    dataItem = new DataItem(integerArray[0], 1, integerArray[5].shortValue(), null);
                } else {
                    return particle;
                }
                Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class).getItemRewriter().handleItemToClient(dataItem);
                particle.getArguments().add(new Particle.ParticleData(Type.FLAT_ITEM, dataItem));
                return particle;
            }
        };
    }

    private static ParticleDataHandler blockHandler() {
        return new ParticleDataHandler(){

            @Override
            public Particle handler(Particle particle, Integer[] integerArray) {
                int n = integerArray[0];
                int n2 = (n & 0xFFF) << 4 | n >> 12 & 0xF;
                int n3 = WorldPackets.toNewId(n2);
                particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, n3));
                return particle;
            }
        };
    }

    static boolean access$000() {
        return ParticleRewriter.randomBool();
    }

    static {
        ParticleRewriter.add(34);
        ParticleRewriter.add(19);
        ParticleRewriter.add(18);
        ParticleRewriter.add(21);
        ParticleRewriter.add(4);
        ParticleRewriter.add(43);
        ParticleRewriter.add(22);
        ParticleRewriter.add(42);
        ParticleRewriter.add(42);
        ParticleRewriter.add(6);
        ParticleRewriter.add(14);
        ParticleRewriter.add(37);
        ParticleRewriter.add(30);
        ParticleRewriter.add(12);
        ParticleRewriter.add(26);
        ParticleRewriter.add(17);
        ParticleRewriter.add(0);
        ParticleRewriter.add(44);
        ParticleRewriter.add(10);
        ParticleRewriter.add(9);
        ParticleRewriter.add(1);
        ParticleRewriter.add(24);
        ParticleRewriter.add(32);
        ParticleRewriter.add(33);
        ParticleRewriter.add(35);
        ParticleRewriter.add(15);
        ParticleRewriter.add(23);
        ParticleRewriter.add(31);
        ParticleRewriter.add(-1);
        ParticleRewriter.add(5);
        ParticleRewriter.add(11, ParticleRewriter.reddustHandler());
        ParticleRewriter.add(29);
        ParticleRewriter.add(34);
        ParticleRewriter.add(28);
        ParticleRewriter.add(25);
        ParticleRewriter.add(2);
        ParticleRewriter.add(27, ParticleRewriter.iconcrackHandler());
        ParticleRewriter.add(3, ParticleRewriter.blockHandler());
        ParticleRewriter.add(3, ParticleRewriter.blockHandler());
        ParticleRewriter.add(36);
        ParticleRewriter.add(-1);
        ParticleRewriter.add(13);
        ParticleRewriter.add(8);
        ParticleRewriter.add(16);
        ParticleRewriter.add(7);
        ParticleRewriter.add(40);
        ParticleRewriter.add(20, ParticleRewriter.blockHandler());
        ParticleRewriter.add(41);
        ParticleRewriter.add(38);
    }

    private static class NewParticle {
        private final int id;
        private final ParticleDataHandler handler;

        public NewParticle(int n, ParticleDataHandler particleDataHandler) {
            this.id = n;
            this.handler = particleDataHandler;
        }

        public Particle handle(Particle particle, Integer[] integerArray) {
            if (this.handler != null) {
                return this.handler.handler(particle, integerArray);
            }
            return particle;
        }

        public int getId() {
            return this.id;
        }

        public ParticleDataHandler getHandler() {
            return this.handler;
        }
    }

    static interface ParticleDataHandler {
        public Particle handler(Particle var1, Integer[] var2);
    }
}

