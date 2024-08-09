/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LongArgumentSerializer
implements IArgumentSerializer<LongArgumentType> {
    @Override
    public void write(LongArgumentType longArgumentType, PacketBuffer packetBuffer) {
        boolean bl = longArgumentType.getMinimum() != Long.MIN_VALUE;
        boolean bl2 = longArgumentType.getMaximum() != Long.MAX_VALUE;
        packetBuffer.writeByte(BrigadierSerializers.minMaxFlags(bl, bl2));
        if (bl) {
            packetBuffer.writeLong(longArgumentType.getMinimum());
        }
        if (bl2) {
            packetBuffer.writeLong(longArgumentType.getMaximum());
        }
    }

    @Override
    public LongArgumentType read(PacketBuffer packetBuffer) {
        byte by = packetBuffer.readByte();
        long l = BrigadierSerializers.hasMin(by) ? packetBuffer.readLong() : Long.MIN_VALUE;
        long l2 = BrigadierSerializers.hasMax(by) ? packetBuffer.readLong() : Long.MAX_VALUE;
        return LongArgumentType.longArg(l, l2);
    }

    @Override
    public void write(LongArgumentType longArgumentType, JsonObject jsonObject) {
        if (longArgumentType.getMinimum() != Long.MIN_VALUE) {
            jsonObject.addProperty("min", longArgumentType.getMinimum());
        }
        if (longArgumentType.getMaximum() != Long.MAX_VALUE) {
            jsonObject.addProperty("max", longArgumentType.getMaximum());
        }
    }

    @Override
    public void write(ArgumentType argumentType, JsonObject jsonObject) {
        this.write((LongArgumentType)argumentType, jsonObject);
    }

    @Override
    public ArgumentType read(PacketBuffer packetBuffer) {
        return this.read(packetBuffer);
    }

    @Override
    public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
        this.write((LongArgumentType)argumentType, packetBuffer);
    }
}

