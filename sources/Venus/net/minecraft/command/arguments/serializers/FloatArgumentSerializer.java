/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatArgumentSerializer
implements IArgumentSerializer<FloatArgumentType> {
    @Override
    public void write(FloatArgumentType floatArgumentType, PacketBuffer packetBuffer) {
        boolean bl = floatArgumentType.getMinimum() != -3.4028235E38f;
        boolean bl2 = floatArgumentType.getMaximum() != Float.MAX_VALUE;
        packetBuffer.writeByte(BrigadierSerializers.minMaxFlags(bl, bl2));
        if (bl) {
            packetBuffer.writeFloat(floatArgumentType.getMinimum());
        }
        if (bl2) {
            packetBuffer.writeFloat(floatArgumentType.getMaximum());
        }
    }

    @Override
    public FloatArgumentType read(PacketBuffer packetBuffer) {
        byte by = packetBuffer.readByte();
        float f = BrigadierSerializers.hasMin(by) ? packetBuffer.readFloat() : -3.4028235E38f;
        float f2 = BrigadierSerializers.hasMax(by) ? packetBuffer.readFloat() : Float.MAX_VALUE;
        return FloatArgumentType.floatArg(f, f2);
    }

    @Override
    public void write(FloatArgumentType floatArgumentType, JsonObject jsonObject) {
        if (floatArgumentType.getMinimum() != -3.4028235E38f) {
            jsonObject.addProperty("min", Float.valueOf(floatArgumentType.getMinimum()));
        }
        if (floatArgumentType.getMaximum() != Float.MAX_VALUE) {
            jsonObject.addProperty("max", Float.valueOf(floatArgumentType.getMaximum()));
        }
    }

    @Override
    public void write(ArgumentType argumentType, JsonObject jsonObject) {
        this.write((FloatArgumentType)argumentType, jsonObject);
    }

    @Override
    public ArgumentType read(PacketBuffer packetBuffer) {
        return this.read(packetBuffer);
    }

    @Override
    public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
        this.write((FloatArgumentType)argumentType, packetBuffer);
    }
}

