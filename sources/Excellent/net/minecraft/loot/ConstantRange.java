package net.minecraft.loot;

import com.google.gson.*;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.Random;

public final class ConstantRange implements IRandomRange
{
    private final int value;

    public ConstantRange(int value)
    {
        this.value = value;
    }

    public int generateInt(Random rand)
    {
        return this.value;
    }

    public ResourceLocation getType()
    {
        return CONSTANT;
    }

    public static ConstantRange of(int constant)
    {
        return new ConstantRange(constant);
    }

    public static class Serializer implements JsonDeserializer<ConstantRange>, JsonSerializer<ConstantRange>
    {
        public ConstantRange deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException
        {
            return new ConstantRange(JSONUtils.getInt(p_deserialize_1_, "value"));
        }

        public JsonElement serialize(ConstantRange p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
        {
            return new JsonPrimitive(p_serialize_1_.value);
        }
    }
}
