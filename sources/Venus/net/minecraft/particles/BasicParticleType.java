/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BasicParticleType
extends ParticleType<BasicParticleType>
implements IParticleData {
    private static final IParticleData.IDeserializer<BasicParticleType> DESERIALIZER = new IParticleData.IDeserializer<BasicParticleType>(){

        @Override
        public BasicParticleType deserialize(ParticleType<BasicParticleType> particleType, StringReader stringReader) throws CommandSyntaxException {
            return (BasicParticleType)particleType;
        }

        @Override
        public BasicParticleType read(ParticleType<BasicParticleType> particleType, PacketBuffer packetBuffer) {
            return (BasicParticleType)particleType;
        }

        @Override
        public IParticleData read(ParticleType particleType, PacketBuffer packetBuffer) {
            return this.read(particleType, packetBuffer);
        }

        @Override
        public IParticleData deserialize(ParticleType particleType, StringReader stringReader) throws CommandSyntaxException {
            return this.deserialize(particleType, stringReader);
        }
    };
    private final Codec<BasicParticleType> field_239825_b_ = Codec.unit(this::getType);

    protected BasicParticleType(boolean bl) {
        super(bl, DESERIALIZER);
    }

    public BasicParticleType getType() {
        return this;
    }

    @Override
    public Codec<BasicParticleType> func_230522_e_() {
        return this.field_239825_b_;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public String getParameters() {
        return Registry.PARTICLE_TYPE.getKey(this).toString();
    }

    public ParticleType getType() {
        return this.getType();
    }
}

