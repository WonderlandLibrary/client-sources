package net.minecraft.command.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

public class ArgumentSerializer < T extends ArgumentType<? >> implements IArgumentSerializer<T>
{
    private final Supplier<T> factory;

    public ArgumentSerializer(Supplier<T> factory)
    {
        this.factory = factory;
    }

    public void write(T argument, PacketBuffer buffer)
    {
    }

    public T read(PacketBuffer buffer)
    {
        return this.factory.get();
    }

    public void write(T p_212244_1_, JsonObject p_212244_2_)
    {
    }
}
