package net.minecraft.src;

public final class McoOptionNone extends McoOption
{
    @Override
    public Object func_98155_a() {
        throw new RuntimeException("None has no value");
    }
}
