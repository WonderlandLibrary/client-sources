/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import net.minecraft.advancements.FunctionManager;
import net.minecraft.command.FunctionObject;
import net.minecraft.command.ITimerCallback;
import net.minecraft.command.TimerCallbackManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class TimedFunction
implements ITimerCallback<MinecraftServer> {
    private final ResourceLocation field_216318_a;

    public TimedFunction(ResourceLocation resourceLocation) {
        this.field_216318_a = resourceLocation;
    }

    @Override
    public void run(MinecraftServer minecraftServer, TimerCallbackManager<MinecraftServer> timerCallbackManager, long l) {
        FunctionManager functionManager = minecraftServer.getFunctionManager();
        functionManager.get(this.field_216318_a).ifPresent(arg_0 -> TimedFunction.lambda$run$0(functionManager, arg_0));
    }

    @Override
    public void run(Object object, TimerCallbackManager timerCallbackManager, long l) {
        this.run((MinecraftServer)object, (TimerCallbackManager<MinecraftServer>)timerCallbackManager, l);
    }

    private static void lambda$run$0(FunctionManager functionManager, FunctionObject functionObject) {
        functionManager.execute(functionObject, functionManager.getCommandSource());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends ITimerCallback.Serializer<MinecraftServer, TimedFunction> {
        public Serializer() {
            super(new ResourceLocation("function"), TimedFunction.class);
        }

        @Override
        public void write(CompoundNBT compoundNBT, TimedFunction timedFunction) {
            compoundNBT.putString("Name", timedFunction.field_216318_a.toString());
        }

        @Override
        public TimedFunction read(CompoundNBT compoundNBT) {
            ResourceLocation resourceLocation = new ResourceLocation(compoundNBT.getString("Name"));
            return new TimedFunction(resourceLocation);
        }

        @Override
        public ITimerCallback read(CompoundNBT compoundNBT) {
            return this.read(compoundNBT);
        }

        @Override
        public void write(CompoundNBT compoundNBT, ITimerCallback iTimerCallback) {
            this.write(compoundNBT, (TimedFunction)iTimerCallback);
        }
    }
}

