/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import io.netty.buffer.ByteBuf;

@Deprecated
public class Particle1_13_2Type
extends Type<Particle> {
    public Particle1_13_2Type() {
        super("Particle", Particle.class);
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
        switch (type2) {
            case 3: 
            case 20: {
                particle2.getArguments().add(new Particle.ParticleData(Type.VAR_INT, Type.VAR_INT.readPrimitive(buffer)));
                break;
            }
            case 11: {
                particle2.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
                particle2.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
                particle2.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
                particle2.getArguments().add(new Particle.ParticleData(Type.FLOAT, Float.valueOf(Type.FLOAT.readPrimitive(buffer))));
                break;
            }
            case 27: {
                particle2.getArguments().add(new Particle.ParticleData(Type.FLAT_VAR_INT_ITEM, Type.FLAT_VAR_INT_ITEM.read(buffer)));
            }
        }
        return particle2;
    }
}

