/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class ArgumentSerializer<T extends ArgumentType<?>>
implements IArgumentSerializer<T> {
    private final Supplier<T> factory;

    public ArgumentSerializer(Supplier<T> supplier) {
        this.factory = supplier;
    }

    @Override
    public void write(T t, PacketBuffer packetBuffer) {
    }

    @Override
    public T read(PacketBuffer packetBuffer) {
        return (T)((ArgumentType)this.factory.get());
    }

    @Override
    public void write(T t, JsonObject jsonObject) {
    }
}

