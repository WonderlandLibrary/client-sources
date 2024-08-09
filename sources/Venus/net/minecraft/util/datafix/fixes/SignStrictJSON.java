/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.StringUtils;

public class SignStrictJSON
extends NamedEntityFix {
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter((Type)((Object)ITextComponent.class), new JsonDeserializer<ITextComponent>(){

        @Override
        public IFormattableTextComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonPrimitive()) {
                return new StringTextComponent(jsonElement.getAsString());
            }
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                IFormattableTextComponent iFormattableTextComponent = null;
                for (JsonElement jsonElement2 : jsonArray) {
                    IFormattableTextComponent iFormattableTextComponent2 = this.deserialize(jsonElement2, jsonElement2.getClass(), jsonDeserializationContext);
                    if (iFormattableTextComponent == null) {
                        iFormattableTextComponent = iFormattableTextComponent2;
                        continue;
                    }
                    iFormattableTextComponent.append(iFormattableTextComponent2);
                }
                return iFormattableTextComponent;
            }
            throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }).create();

    public SignStrictJSON(Schema schema, boolean bl) {
        super(schema, bl, "BlockEntitySignTextStrictJsonFix", TypeReferences.BLOCK_ENTITY, "Sign");
    }

    private Dynamic<?> updateLine(Dynamic<?> dynamic, String string) {
        String string2 = dynamic.get(string).asString("");
        ITextComponent iTextComponent = null;
        if (!"null".equals(string2) && !StringUtils.isEmpty(string2)) {
            if (string2.charAt(0) == '\"' && string2.charAt(string2.length() - 1) == '\"' || string2.charAt(0) == '{' && string2.charAt(string2.length() - 1) == '}') {
                try {
                    iTextComponent = JSONUtils.fromJson(GSON, string2, ITextComponent.class, true);
                    if (iTextComponent == null) {
                        iTextComponent = StringTextComponent.EMPTY;
                    }
                } catch (JsonParseException jsonParseException) {
                    // empty catch block
                }
                if (iTextComponent == null) {
                    try {
                        iTextComponent = ITextComponent.Serializer.getComponentFromJson(string2);
                    } catch (JsonParseException jsonParseException) {
                        // empty catch block
                    }
                }
                if (iTextComponent == null) {
                    try {
                        iTextComponent = ITextComponent.Serializer.getComponentFromJsonLenient(string2);
                    } catch (JsonParseException jsonParseException) {
                        // empty catch block
                    }
                }
                if (iTextComponent == null) {
                    iTextComponent = new StringTextComponent(string2);
                }
            } else {
                iTextComponent = new StringTextComponent(string2);
            }
        } else {
            iTextComponent = StringTextComponent.EMPTY;
        }
        return dynamic.set(string, dynamic.createString(ITextComponent.Serializer.toJson(iTextComponent)));
    }

    @Override
    protected Typed<?> fix(Typed<?> typed) {
        return typed.update(DSL.remainderFinder(), this::lambda$fix$0);
    }

    private Dynamic lambda$fix$0(Dynamic dynamic) {
        dynamic = this.updateLine(dynamic, "Text1");
        dynamic = this.updateLine(dynamic, "Text2");
        dynamic = this.updateLine(dynamic, "Text3");
        return this.updateLine(dynamic, "Text4");
    }
}

