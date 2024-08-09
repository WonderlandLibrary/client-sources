/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.command.ITimerCallback;
import net.minecraft.command.TimedFunction;
import net.minecraft.command.TimedFunctionTag;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbackSerializers<C> {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final TimerCallbackSerializers<MinecraftServer> field_216342_a = new TimerCallbackSerializers<MinecraftServer>().func_216340_a(new TimedFunction.Serializer()).func_216340_a(new TimedFunctionTag.Serializer());
    private final Map<ResourceLocation, ITimerCallback.Serializer<C, ?>> field_216344_c = Maps.newHashMap();
    private final Map<Class<?>, ITimerCallback.Serializer<C, ?>> field_216345_d = Maps.newHashMap();

    public TimerCallbackSerializers<C> func_216340_a(ITimerCallback.Serializer<C, ?> serializer) {
        this.field_216344_c.put(serializer.func_216310_a(), serializer);
        this.field_216345_d.put(serializer.func_216311_b(), serializer);
        return this;
    }

    private <T extends ITimerCallback<C>> ITimerCallback.Serializer<C, T> func_216338_a(Class<?> clazz) {
        return this.field_216345_d.get(clazz);
    }

    public <T extends ITimerCallback<C>> CompoundNBT func_216339_a(T t) {
        ITimerCallback.Serializer<T, T> serializer = this.func_216338_a(t.getClass());
        CompoundNBT compoundNBT = new CompoundNBT();
        serializer.write(compoundNBT, t);
        compoundNBT.putString("Type", serializer.func_216310_a().toString());
        return compoundNBT;
    }

    @Nullable
    public ITimerCallback<C> func_216341_a(CompoundNBT compoundNBT) {
        ResourceLocation resourceLocation = ResourceLocation.tryCreate(compoundNBT.getString("Type"));
        ITimerCallback.Serializer<C, ?> serializer = this.field_216344_c.get(resourceLocation);
        if (serializer == null) {
            LOGGER.error("Failed to deserialize timer callback: " + compoundNBT);
            return null;
        }
        try {
            return serializer.read(compoundNBT);
        } catch (Exception exception) {
            LOGGER.error("Failed to deserialize timer callback: " + compoundNBT, (Throwable)exception);
            return null;
        }
    }
}

