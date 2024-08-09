/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleType;

public interface IParticleData {
    public ParticleType<?> getType();

    public void write(PacketBuffer var1);

    public String getParameters();

    @Deprecated
    public static interface IDeserializer<T extends IParticleData> {
        public T deserialize(ParticleType<T> var1, StringReader var2) throws CommandSyntaxException;

        public T read(ParticleType<T> var1, PacketBuffer var2);
    }
}

