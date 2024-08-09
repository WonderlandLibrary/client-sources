/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DoubleArgumentSerializer
implements IArgumentSerializer<DoubleArgumentType> {
    @Override
    public void write(DoubleArgumentType doubleArgumentType, PacketBuffer packetBuffer) {
        boolean bl = doubleArgumentType.getMinimum() != -1.7976931348623157E308;
        boolean bl2 = doubleArgumentType.getMaximum() != Double.MAX_VALUE;
        packetBuffer.writeByte(BrigadierSerializers.minMaxFlags(bl, bl2));
        if (bl) {
            packetBuffer.writeDouble(doubleArgumentType.getMinimum());
        }
        if (bl2) {
            packetBuffer.writeDouble(doubleArgumentType.getMaximum());
        }
    }

    @Override
    public DoubleArgumentType read(PacketBuffer packetBuffer) {
        byte by = packetBuffer.readByte();
        double d = BrigadierSerializers.hasMin(by) ? packetBuffer.readDouble() : -1.7976931348623157E308;
        double d2 = BrigadierSerializers.hasMax(by) ? packetBuffer.readDouble() : Double.MAX_VALUE;
        return DoubleArgumentType.doubleArg(d, d2);
    }

    @Override
    public void write(DoubleArgumentType doubleArgumentType, JsonObject jsonObject) {
        if (doubleArgumentType.getMinimum() != -1.7976931348623157E308) {
            jsonObject.addProperty("min", doubleArgumentType.getMinimum());
        }
        if (doubleArgumentType.getMaximum() != Double.MAX_VALUE) {
            jsonObject.addProperty("max", doubleArgumentType.getMaximum());
        }
    }

    @Override
    public void write(ArgumentType argumentType, JsonObject jsonObject) {
        this.write((DoubleArgumentType)argumentType, jsonObject);
    }

    @Override
    public ArgumentType read(PacketBuffer packetBuffer) {
        return this.read(packetBuffer);
    }

    @Override
    public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
        this.write((DoubleArgumentType)argumentType, packetBuffer);
    }
}

