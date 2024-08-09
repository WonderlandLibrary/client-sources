/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public abstract class AbstractParticleType
extends Type<Particle> {
    protected final Int2ObjectMap<ParticleReader> readers = new Int2ObjectOpenHashMap<ParticleReader>();

    protected AbstractParticleType() {
        super("Particle", Particle.class);
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

    protected ParticleReader blockHandler() {
        return AbstractParticleType::lambda$blockHandler$0;
    }

    protected ParticleReader itemHandler(Type<Item> type) {
        return (arg_0, arg_1) -> AbstractParticleType.lambda$itemHandler$1(type, arg_0, arg_1);
    }

    protected ParticleReader dustHandler() {
        return AbstractParticleType::lambda$dustHandler$2;
    }

    protected ParticleReader dustTransitionHandler() {
        return AbstractParticleType::lambda$dustTransitionHandler$3;
    }

    protected ParticleReader vibrationHandler(Type<Position> type) {
        return (arg_0, arg_1) -> AbstractParticleType.lambda$vibrationHandler$4(type, arg_0, arg_1);
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Particle)object);
    }

    private static void lambda$vibrationHandler$4(Type type, ByteBuf byteBuf, Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
        String string = (String)Type.STRING.read(byteBuf);
        if (string.equals("block")) {
            particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
        } else if (string.equals("entity")) {
            particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
        } else {
            Via.getPlatform().getLogger().warning("Unknown vibration path position source type: " + string);
        }
        particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
    }

    private static void lambda$dustTransitionHandler$3(ByteBuf byteBuf, Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
    }

    private static void lambda$dustHandler$2(ByteBuf byteBuf, Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
        particle.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(byteBuf))));
    }

    private static void lambda$itemHandler$1(Type type, ByteBuf byteBuf, Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(type, type.read(byteBuf)));
    }

    private static void lambda$blockHandler$0(ByteBuf byteBuf, Particle particle) throws Exception {
        particle.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(byteBuf)));
    }

    @FunctionalInterface
    public static interface ParticleReader {
        public void read(ByteBuf var1, Particle var2) throws Exception;
    }
}

