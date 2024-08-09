/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import net.minecraft.command.TimerCallbackManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

@FunctionalInterface
public interface ITimerCallback<T> {
    public void run(T var1, TimerCallbackManager<T> var2, long var3);

    public static abstract class Serializer<T, C extends ITimerCallback<T>> {
        private final ResourceLocation typeId;
        private final Class<?> clazz;

        public Serializer(ResourceLocation resourceLocation, Class<?> clazz) {
            this.typeId = resourceLocation;
            this.clazz = clazz;
        }

        public ResourceLocation func_216310_a() {
            return this.typeId;
        }

        public Class<?> func_216311_b() {
            return this.clazz;
        }

        public abstract void write(CompoundNBT var1, C var2);

        public abstract C read(CompoundNBT var1);
    }
}

