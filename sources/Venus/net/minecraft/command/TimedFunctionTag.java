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
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class TimedFunctionTag
implements ITimerCallback<MinecraftServer> {
    private final ResourceLocation tagName;

    public TimedFunctionTag(ResourceLocation resourceLocation) {
        this.tagName = resourceLocation;
    }

    @Override
    public void run(MinecraftServer minecraftServer, TimerCallbackManager<MinecraftServer> timerCallbackManager, long l) {
        FunctionManager functionManager = minecraftServer.getFunctionManager();
        ITag<FunctionObject> iTag = functionManager.getFunctionTag(this.tagName);
        for (FunctionObject functionObject : iTag.getAllElements()) {
            functionManager.execute(functionObject, functionManager.getCommandSource());
        }
    }

    @Override
    public void run(Object object, TimerCallbackManager timerCallbackManager, long l) {
        this.run((MinecraftServer)object, (TimerCallbackManager<MinecraftServer>)timerCallbackManager, l);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends ITimerCallback.Serializer<MinecraftServer, TimedFunctionTag> {
        public Serializer() {
            super(new ResourceLocation("function_tag"), TimedFunctionTag.class);
        }

        @Override
        public void write(CompoundNBT compoundNBT, TimedFunctionTag timedFunctionTag) {
            compoundNBT.putString("Name", timedFunctionTag.tagName.toString());
        }

        @Override
        public TimedFunctionTag read(CompoundNBT compoundNBT) {
            ResourceLocation resourceLocation = new ResourceLocation(compoundNBT.getString("Name"));
            return new TimedFunctionTag(resourceLocation);
        }

        @Override
        public ITimerCallback read(CompoundNBT compoundNBT) {
            return this.read(compoundNBT);
        }

        @Override
        public void write(CompoundNBT compoundNBT, ITimerCallback iTimerCallback) {
            this.write(compoundNBT, (TimedFunctionTag)iTimerCallback);
        }
    }
}

