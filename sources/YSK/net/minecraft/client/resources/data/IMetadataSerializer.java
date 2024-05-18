package net.minecraft.client.resources.data;

import net.minecraft.util.*;
import com.google.gson.*;
import java.lang.reflect.*;

public class IMetadataSerializer
{
    private final GsonBuilder gsonBuilder;
    private final IRegistry<String, Registration<? extends IMetadataSection>> metadataSectionSerializerRegistry;
    private static final String[] I;
    private Gson gson;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    public IMetadataSerializer() {
        this.metadataSectionSerializerRegistry = new RegistrySimple<String, Registration<? extends IMetadataSection>>();
        (this.gsonBuilder = new GsonBuilder()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer());
        this.gsonBuilder.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
        this.gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
    }
    
    private static void I() {
        (I = new String[0x67 ^ 0x62])["".length()] = I("$\u0011\u0005$1\b\u0000\u0010e&\f\u0017\u0005,:\u0007T\u001f$8\fT\u0012$;\u0007\u001b\u0005e7\fT\u001f09\u0005", "itqEU");
        IMetadataSerializer.I[" ".length()] = I(">\"/\u0010\u0002\u001e(y\u001c\u000b\u0003-=\u0010\u001a\u0016l?\u001e\u001cWk", "wLYqn");
        IMetadataSerializer.I["  ".length()] = I("ehoY\u0013:8'\u001a\u0002',b\u0016\u0014(-!\rZb.-\f\u0018&h", "BHByv");
        IMetadataSerializer.I["   ".length()] = I("\u0006.\u0005u\u0015b*\u0005=\u0016b)\u0004%A6.K:\u0000,%\u00077A/$\u001f3\u0005#5\nr\u0012'\"\u001f;\u000e,aL", "BAkRa");
        IMetadataSerializer.I[0xB3 ^ 0xB7] = I("`", "GYsNt");
    }
    
    public <T extends IMetadataSection> T parseMetadataSection(final String s, final JsonObject jsonObject) {
        if (s == null) {
            throw new IllegalArgumentException(IMetadataSerializer.I["".length()]);
        }
        if (!jsonObject.has(s)) {
            return null;
        }
        if (!jsonObject.get(s).isJsonObject()) {
            throw new IllegalArgumentException(IMetadataSerializer.I[" ".length()] + s + IMetadataSerializer.I["  ".length()] + jsonObject.get(s));
        }
        final Registration<? extends IMetadataSection> registration = this.metadataSectionSerializerRegistry.getObject(s);
        if (registration == null) {
            throw new IllegalArgumentException(IMetadataSerializer.I["   ".length()] + s + IMetadataSerializer.I[0xB1 ^ 0xB5]);
        }
        return (T)this.getGson().fromJson((JsonElement)jsonObject.getAsJsonObject(s), (Class)registration.field_110500_b);
    }
    
    private Gson getGson() {
        if (this.gson == null) {
            this.gson = this.gsonBuilder.create();
        }
        return this.gson;
    }
    
    public <T extends IMetadataSection> void registerMetadataSectionType(final IMetadataSectionSerializer<T> metadataSectionSerializer, final Class<T> clazz) {
        this.metadataSectionSerializerRegistry.putObject(metadataSectionSerializer.getSectionName(), new Registration<IMetadataSection>(metadataSectionSerializer, clazz, null));
        this.gsonBuilder.registerTypeAdapter((Type)clazz, (Object)metadataSectionSerializer);
        this.gson = null;
    }
    
    class Registration<T extends IMetadataSection>
    {
        final Class<T> field_110500_b;
        final IMetadataSerializer this$0;
        final IMetadataSectionSerializer<T> field_110502_a;
        
        private Registration(final IMetadataSerializer this$0, final IMetadataSectionSerializer<T> field_110502_a, final Class<T> field_110500_b) {
            this.this$0 = this$0;
            this.field_110502_a = field_110502_a;
            this.field_110500_b = field_110500_b;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        Registration(final IMetadataSerializer metadataSerializer, final IMetadataSectionSerializer metadataSectionSerializer, final Class clazz, final Registration registration) {
            this(metadataSerializer, metadataSectionSerializer, clazz);
        }
    }
}
