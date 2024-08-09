/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectArrayMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.util.Key;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ParticleType
extends Type<Particle> {
    private final Int2ObjectMap<ParticleReader> readers;

    public ParticleType(Int2ObjectMap<ParticleReader> int2ObjectMap) {
        super("Particle", Particle.class);
        this.readers = int2ObjectMap;
    }

    public ParticleType() {
        this(new Int2ObjectArrayMap<ParticleReader>());
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol) {
        return this.filler(protocol, false);
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol, boolean bl) {
        return new ParticleTypeFiller(this, protocol, bl, null);
    }

    @Override
    public void write(ByteBuf byteBuf, Particle particle) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, particle.getId());
        for (Particle.ParticleData particleData : particle.getArguments()) {
            particleData.getType().write(byteBuf, particleData.getValue());
        }
    }

    @Override
    public Particle read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        Particle particle = new Particle(n);
        ParticleReader particleReader = (ParticleReader)this.readers.get(n);
        if (particleReader != null) {
            particleReader.read(byteBuf, particle);
        }
        return particle;
    }

    public static ParticleReader itemHandler(Type<Item> type) {
        return (arg_0, arg_1) -> ParticleType.lambda$itemHandler$0(type, arg_0, arg_1);
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Particle)object);
    }

    private static void lambda$itemHandler$0(Type type, ByteBuf byteBuf, Particle particle) throws Exception {
        particle.add(type, type.read(byteBuf));
    }

    static Int2ObjectMap access$100(ParticleType particleType) {
        return particleType.readers;
    }

    @FunctionalInterface
    public static interface ParticleReader {
        public void read(ByteBuf var1, Particle var2) throws Exception;
    }

    public final class ParticleTypeFiller {
        private final ParticleMappings mappings;
        private final boolean useMappedNames;
        final ParticleType this$0;

        private ParticleTypeFiller(ParticleType particleType, Protocol<?, ?, ?, ?> protocol, boolean bl) {
            this.this$0 = particleType;
            this.mappings = protocol.getMappingData().getParticleMappings();
            this.useMappedNames = bl;
        }

        public ParticleTypeFiller reader(String string, ParticleReader particleReader) {
            ParticleType.access$100(this.this$0).put(this.useMappedNames ? this.mappings.mappedId(string) : this.mappings.id(string), particleReader);
            return this;
        }

        public ParticleTypeFiller reader(int n, ParticleReader particleReader) {
            ParticleType.access$100(this.this$0).put(n, particleReader);
            return this;
        }

        ParticleTypeFiller(ParticleType particleType, Protocol protocol, boolean bl, 1 var4_4) {
            this(particleType, protocol, bl);
        }
    }

    public static final class Readers {
        public static final ParticleReader BLOCK = Readers::lambda$static$0;
        public static final ParticleReader ITEM = ParticleType.itemHandler(Type.FLAT_ITEM);
        public static final ParticleReader VAR_INT_ITEM = ParticleType.itemHandler(Type.FLAT_VAR_INT_ITEM);
        public static final ParticleReader DUST = Readers::lambda$static$1;
        public static final ParticleReader DUST_TRANSITION = Readers::lambda$static$2;
        public static final ParticleReader VIBRATION = Readers::lambda$static$3;
        public static final ParticleReader VIBRATION1_19 = Readers::lambda$static$4;
        public static final ParticleReader SCULK_CHARGE = Readers::lambda$static$5;
        public static final ParticleReader SHRIEK = Readers::lambda$static$6;

        private static void lambda$static$6(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }

        private static void lambda$static$5(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
        }

        private static void lambda$static$4(ByteBuf byteBuf, Particle particle) throws Exception {
            String string = (String)Type.STRING.read(byteBuf);
            particle.add(Type.STRING, string);
            string = Key.stripMinecraftNamespace(string);
            if (string.equals("block")) {
                particle.add(Type.POSITION1_14, Type.POSITION1_14.read(byteBuf));
            } else if (string.equals("entity")) {
                particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
                particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + string);
            }
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }

        private static void lambda$static$3(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.POSITION1_14, Type.POSITION1_14.read(byteBuf));
            String string = (String)Type.STRING.read(byteBuf);
            particle.add(Type.STRING, string);
            string = Key.stripMinecraftNamespace(string);
            if (string.equals("block")) {
                particle.add(Type.POSITION1_14, Type.POSITION1_14.read(byteBuf));
            } else if (string.equals("entity")) {
                particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + string);
            }
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }

        private static void lambda$static$2(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
        }

        private static void lambda$static$1(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
            particle.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf)));
        }

        private static void lambda$static$0(ByteBuf byteBuf, Particle particle) throws Exception {
            particle.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf));
        }
    }
}

