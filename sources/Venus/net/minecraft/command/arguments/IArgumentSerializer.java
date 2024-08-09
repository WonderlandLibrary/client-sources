/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.network.PacketBuffer;

public interface IArgumentSerializer<T extends ArgumentType<?>> {
    public void write(T var1, PacketBuffer var2);

    public T read(PacketBuffer var1);

    public void write(T var1, JsonObject var2);
}

