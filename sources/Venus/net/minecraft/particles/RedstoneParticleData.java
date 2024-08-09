/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class RedstoneParticleData
implements IParticleData {
    public static final RedstoneParticleData REDSTONE_DUST = new RedstoneParticleData(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Codec<RedstoneParticleData> field_239802_b_ = RecordCodecBuilder.create(RedstoneParticleData::lambda$static$4);
    public static final IParticleData.IDeserializer<RedstoneParticleData> DESERIALIZER = new IParticleData.IDeserializer<RedstoneParticleData>(){

        @Override
        public RedstoneParticleData deserialize(ParticleType<RedstoneParticleData> particleType, StringReader stringReader) throws CommandSyntaxException {
            stringReader.expect(' ');
            float f = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float f2 = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float f3 = (float)stringReader.readDouble();
            stringReader.expect(' ');
            float f4 = (float)stringReader.readDouble();
            return new RedstoneParticleData(f, f2, f3, f4);
        }

        @Override
        public RedstoneParticleData read(ParticleType<RedstoneParticleData> particleType, PacketBuffer packetBuffer) {
            return new RedstoneParticleData(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat());
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
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public RedstoneParticleData(float f, float f2, float f3, float f4) {
        this.red = f;
        this.green = f2;
        this.blue = f3;
        this.alpha = MathHelper.clamp(f4, 0.01f, 4.0f);
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeFloat(this.red);
        packetBuffer.writeFloat(this.green);
        packetBuffer.writeFloat(this.blue);
        packetBuffer.writeFloat(this.alpha);
    }

    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), Float.valueOf(this.red), Float.valueOf(this.green), Float.valueOf(this.blue), Float.valueOf(this.alpha));
    }

    public ParticleType<RedstoneParticleData> getType() {
        return ParticleTypes.DUST;
    }

    public float getRed() {
        return this.red;
    }

    public float getGreen() {
        return this.green;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getAlpha() {
        return this.alpha;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Codec.FLOAT.fieldOf("r")).forGetter(RedstoneParticleData::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("g")).forGetter(RedstoneParticleData::lambda$static$1), ((MapCodec)Codec.FLOAT.fieldOf("b")).forGetter(RedstoneParticleData::lambda$static$2), ((MapCodec)Codec.FLOAT.fieldOf("scale")).forGetter(RedstoneParticleData::lambda$static$3)).apply(instance, RedstoneParticleData::new);
    }

    private static Float lambda$static$3(RedstoneParticleData redstoneParticleData) {
        return Float.valueOf(redstoneParticleData.alpha);
    }

    private static Float lambda$static$2(RedstoneParticleData redstoneParticleData) {
        return Float.valueOf(redstoneParticleData.blue);
    }

    private static Float lambda$static$1(RedstoneParticleData redstoneParticleData) {
        return Float.valueOf(redstoneParticleData.green);
    }

    private static Float lambda$static$0(RedstoneParticleData redstoneParticleData) {
        return Float.valueOf(redstoneParticleData.red);
    }
}

