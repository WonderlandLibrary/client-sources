/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IntArgumentSerializer
implements IArgumentSerializer<IntegerArgumentType> {
    @Override
    public void write(IntegerArgumentType integerArgumentType, PacketBuffer packetBuffer) {
        boolean bl = integerArgumentType.getMinimum() != Integer.MIN_VALUE;
        boolean bl2 = integerArgumentType.getMaximum() != Integer.MAX_VALUE;
        packetBuffer.writeByte(BrigadierSerializers.minMaxFlags(bl, bl2));
        if (bl) {
            packetBuffer.writeInt(integerArgumentType.getMinimum());
        }
        if (bl2) {
            packetBuffer.writeInt(integerArgumentType.getMaximum());
        }
    }

    @Override
    public IntegerArgumentType read(PacketBuffer packetBuffer) {
        byte by = packetBuffer.readByte();
        int n = BrigadierSerializers.hasMin(by) ? packetBuffer.readInt() : Integer.MIN_VALUE;
        int n2 = BrigadierSerializers.hasMax(by) ? packetBuffer.readInt() : Integer.MAX_VALUE;
        return IntegerArgumentType.integer(n, n2);
    }

    @Override
    public void write(IntegerArgumentType integerArgumentType, JsonObject jsonObject) {
        if (integerArgumentType.getMinimum() != Integer.MIN_VALUE) {
            jsonObject.addProperty("min", integerArgumentType.getMinimum());
        }
        if (integerArgumentType.getMaximum() != Integer.MAX_VALUE) {
            jsonObject.addProperty("max", integerArgumentType.getMaximum());
        }
    }

    @Override
    public void write(ArgumentType argumentType, JsonObject jsonObject) {
        this.write((IntegerArgumentType)argumentType, jsonObject);
    }

    @Override
    public ArgumentType read(PacketBuffer packetBuffer) {
        return this.read(packetBuffer);
    }

    @Override
    public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
        this.write((IntegerArgumentType)argumentType, packetBuffer);
    }
}

