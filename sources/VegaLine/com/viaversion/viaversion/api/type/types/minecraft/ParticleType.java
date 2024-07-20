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

public class ParticleType
extends Type<Particle> {
    private final Int2ObjectMap<ParticleReader> readers;

    public ParticleType(Int2ObjectMap<ParticleReader> readers) {
        super("Particle", Particle.class);
        this.readers = readers;
    }

    public ParticleType() {
        this(new Int2ObjectArrayMap<ParticleReader>());
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol) {
        return this.filler(protocol, true);
    }

    public ParticleTypeFiller filler(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
        return new ParticleTypeFiller(protocol, useMappedNames);
    }

    @Override
    public void write(ByteBuf buffer, Particle object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.getId());
        for (Particle.ParticleData data : object.getArguments()) {
            data.getType().write(buffer, data.getValue());
        }
    }

    @Override
    public Particle read(ByteBuf buffer) throws Exception {
        int type2 = Type.VAR_INT.readPrimitive(buffer);
        Particle particle2 = new Particle(type2);
        ParticleReader reader = (ParticleReader)this.readers.get(type2);
        if (reader != null) {
            reader.read(buffer, particle2);
        }
        return particle2;
    }

    public static ParticleReader itemHandler(Type<Item> itemType) {
        return (buf, particle2) -> particle2.add(itemType, itemType.read(buf));
    }

    @FunctionalInterface
    public static interface ParticleReader {
        public void read(ByteBuf var1, Particle var2) throws Exception;
    }

    public final class ParticleTypeFiller {
        private final ParticleMappings mappings;
        private final boolean useMappedNames;

        private ParticleTypeFiller(Protocol<?, ?, ?, ?> protocol, boolean useMappedNames) {
            this.mappings = protocol.getMappingData().getParticleMappings();
            this.useMappedNames = useMappedNames;
        }

        public ParticleTypeFiller reader(String identifier, ParticleReader reader) {
            ParticleType.this.readers.put(this.useMappedNames ? this.mappings.mappedId(identifier) : this.mappings.id(identifier), reader);
            return this;
        }

        public ParticleTypeFiller reader(int id, ParticleReader reader) {
            ParticleType.this.readers.put(id, reader);
            return this;
        }
    }

    public static final class Readers {
        public static final ParticleReader BLOCK = (buf, particle2) -> particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
        public static final ParticleReader ITEM = ParticleType.itemHandler(Type.FLAT_ITEM);
        public static final ParticleReader VAR_INT_ITEM = ParticleType.itemHandler(Type.FLAT_VAR_INT_ITEM);
        public static final ParticleReader DUST = (buf, particle2) -> {
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        };
        public static final ParticleReader DUST_TRANSITION = (buf, particle2) -> {
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        };
        public static final ParticleReader VIBRATION = (buf, particle2) -> {
            particle2.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
            String resourceLocation = (String)Type.STRING.read(buf);
            particle2.add(Type.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
                particle2.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
                particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }
            particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
        };
        public static final ParticleReader VIBRATION1_19 = (buf, particle2) -> {
            String resourceLocation = (String)Type.STRING.read(buf);
            particle2.add(Type.STRING, resourceLocation);
            resourceLocation = Key.stripMinecraftNamespace(resourceLocation);
            if (resourceLocation.equals("block")) {
                particle2.add(Type.POSITION1_14, Type.POSITION1_14.read(buf));
            } else if (resourceLocation.equals("entity")) {
                particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
                particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
            } else {
                Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + resourceLocation);
            }
            particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
        };
        public static final ParticleReader SCULK_CHARGE = (buf, particle2) -> particle2.add(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buf)));
        public static final ParticleReader SHRIEK = (buf, particle2) -> particle2.add(Type.VAR_INT, Type.VAR_INT.readPrimitive(buf));
    }
}

