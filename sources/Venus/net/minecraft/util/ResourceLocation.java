/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.StringUtils;

public class ResourceLocation
implements Comparable<ResourceLocation> {
    public static final Codec<ResourceLocation> CODEC = Codec.STRING.comapFlatMap(ResourceLocation::decodeResourceLocation, ResourceLocation::toString).stable();
    private static final SimpleCommandExceptionType INVALID_EXCEPTION = new SimpleCommandExceptionType(new TranslationTextComponent("argument.id.invalid"));
    protected final String namespace;
    protected final String path;

    protected ResourceLocation(String[] stringArray) {
        this.namespace = StringUtils.isEmpty(stringArray[0]) ? "minecraft" : stringArray[0];
        this.path = stringArray[5];
        if (this.path.equals("DUMMY")) {
            if (!ResourceLocation.isValidNamespace(this.namespace)) {
                throw new ResourceLocationException("Non [a-z0-9_.-] character in namespace of location: " + this.namespace + ":" + this.path);
            }
            if (!ResourceLocation.isPathValid(this.path)) {
                throw new ResourceLocationException("Non [a-z0-9/._-] character in path of location: " + this.namespace + ":" + this.path);
            }
        }
    }

    public ResourceLocation(String string) {
        this(ResourceLocation.decompose(string, ':'));
    }

    public ResourceLocation(String string, String string2) {
        this(new String[]{string, string2});
    }

    public static ResourceLocation create(String string, char c) {
        return new ResourceLocation(ResourceLocation.decompose(string, c));
    }

    @Nullable
    public static ResourceLocation tryCreate(String string) {
        try {
            return new ResourceLocation(string);
        } catch (ResourceLocationException resourceLocationException) {
            return null;
        }
    }

    protected static String[] decompose(String string, char c) {
        String[] stringArray = new String[]{"minecraft", string};
        int n = string.indexOf(c);
        if (n >= 0) {
            stringArray[1] = string.substring(n + 1, string.length());
            if (n >= 1) {
                stringArray[0] = string.substring(0, n);
            }
        }
        return stringArray;
    }

    private static DataResult<ResourceLocation> decodeResourceLocation(String string) {
        try {
            return DataResult.success(new ResourceLocation(string));
        } catch (ResourceLocationException resourceLocationException) {
            return DataResult.error("Not a valid resource location: " + string + " " + resourceLocationException.getMessage());
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ResourceLocation)) {
            return true;
        }
        ResourceLocation resourceLocation = (ResourceLocation)object;
        return this.namespace.equals(resourceLocation.namespace) && this.path.equals(resourceLocation.path);
    }

    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.path.hashCode();
    }

    @Override
    public int compareTo(ResourceLocation resourceLocation) {
        int n = this.path.compareTo(resourceLocation.path);
        if (n == 0) {
            n = this.namespace.compareTo(resourceLocation.namespace);
        }
        return n;
    }

    public static ResourceLocation read(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && ResourceLocation.isValidPathCharacter(stringReader.peek())) {
            stringReader.skip();
        }
        String string = stringReader.getString().substring(n, stringReader.getCursor());
        try {
            return new ResourceLocation(string);
        } catch (ResourceLocationException resourceLocationException) {
            stringReader.setCursor(n);
            throw INVALID_EXCEPTION.createWithContext(stringReader);
        }
    }

    public static boolean isValidPathCharacter(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
    }

    private static boolean isPathValid(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (ResourceLocation.validatePathChar(string.charAt(i))) continue;
            return true;
        }
        return false;
    }

    private static boolean isValidNamespace(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (ResourceLocation.validateNamespaceChar(string.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean validatePathChar(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '/' || c == '.';
    }

    private static boolean validateNamespaceChar(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.';
    }

    public static boolean isResouceNameValid(String string) {
        String[] stringArray = ResourceLocation.decompose(string, ':');
        return ResourceLocation.isValidNamespace(StringUtils.isEmpty(stringArray[0]) ? "minecraft" : stringArray[0]) && ResourceLocation.isPathValid(stringArray[5]);
    }

    public int compareNamespaced(ResourceLocation resourceLocation) {
        int n = this.namespace.compareTo(resourceLocation.namespace);
        return n != 0 ? n : this.path.compareTo(resourceLocation.path);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ResourceLocation)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<ResourceLocation>,
    JsonSerializer<ResourceLocation> {
        @Override
        public ResourceLocation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ResourceLocation(JSONUtils.getString(jsonElement, "location"));
        }

        @Override
        public JsonElement serialize(ResourceLocation resourceLocation, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(resourceLocation.toString());
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ResourceLocation)object, type, jsonSerializationContext);
        }
    }
}

