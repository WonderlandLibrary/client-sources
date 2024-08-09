/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringArgumentSerializer
implements IArgumentSerializer<StringArgumentType> {
    @Override
    public void write(StringArgumentType stringArgumentType, PacketBuffer packetBuffer) {
        packetBuffer.writeEnumValue(stringArgumentType.getType());
    }

    @Override
    public StringArgumentType read(PacketBuffer packetBuffer) {
        StringArgumentType.StringType stringType = packetBuffer.readEnumValue(StringArgumentType.StringType.class);
        switch (1.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[stringType.ordinal()]) {
            case 1: {
                return StringArgumentType.word();
            }
            case 2: {
                return StringArgumentType.string();
            }
        }
        return StringArgumentType.greedyString();
    }

    @Override
    public void write(StringArgumentType stringArgumentType, JsonObject jsonObject) {
        switch (1.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[stringArgumentType.getType().ordinal()]) {
            case 1: {
                jsonObject.addProperty("type", "word");
                break;
            }
            case 2: {
                jsonObject.addProperty("type", "phrase");
                break;
            }
            default: {
                jsonObject.addProperty("type", "greedy");
            }
        }
    }

    @Override
    public void write(ArgumentType argumentType, JsonObject jsonObject) {
        this.write((StringArgumentType)argumentType, jsonObject);
    }

    @Override
    public ArgumentType read(PacketBuffer packetBuffer) {
        return this.read(packetBuffer);
    }

    @Override
    public void write(ArgumentType argumentType, PacketBuffer packetBuffer) {
        this.write((StringArgumentType)argumentType, packetBuffer);
    }
}

