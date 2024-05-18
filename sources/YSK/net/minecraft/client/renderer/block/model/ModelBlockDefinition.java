package net.minecraft.client.renderer.block.model;

import java.lang.reflect.*;
import java.io.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import com.google.gson.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelBlockDefinition
{
    private final Map<String, Variants> mapVariants;
    static final Gson GSON;
    
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)ModelBlockDefinition.class, (Object)new Deserializer()).registerTypeAdapter((Type)Variant.class, (Object)new Variant.Deserializer()).create();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (o instanceof ModelBlockDefinition) {
            return this.mapVariants.equals(((ModelBlockDefinition)o).mapVariants);
        }
        return "".length() != 0;
    }
    
    public ModelBlockDefinition(final List<ModelBlockDefinition> list) {
        this.mapVariants = (Map<String, Variants>)Maps.newHashMap();
        final Iterator<ModelBlockDefinition> iterator = list.iterator();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.mapVariants.putAll(iterator.next().mapVariants);
        }
    }
    
    public Variants getVariants(final String s) {
        final Variants variants = this.mapVariants.get(s);
        if (variants == null) {
            throw new MissingVariantException();
        }
        return variants;
    }
    
    public static ModelBlockDefinition parseFromReader(final Reader reader) {
        return (ModelBlockDefinition)ModelBlockDefinition.GSON.fromJson(reader, (Class)ModelBlockDefinition.class);
    }
    
    @Override
    public int hashCode() {
        return this.mapVariants.hashCode();
    }
    
    public ModelBlockDefinition(final Collection<Variants> collection) {
        this.mapVariants = (Map<String, Variants>)Maps.newHashMap();
        final Iterator<Variants> iterator = collection.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Variants variants = iterator.next();
            this.mapVariants.put(Variants.access$0(variants), variants);
        }
    }
    
    public class MissingVariantException extends RuntimeException
    {
        final ModelBlockDefinition this$0;
        
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
        
        public MissingVariantException(final ModelBlockDefinition this$0) {
            this.this$0 = this$0;
        }
    }
    
    public static class Variants
    {
        private final String name;
        private final List<Variant> listVariants;
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return " ".length() != 0;
            }
            if (!(o instanceof Variants)) {
                return "".length() != 0;
            }
            final Variants variants = (Variants)o;
            int n;
            if (!this.name.equals(variants.name)) {
                n = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                n = (this.listVariants.equals(variants.listVariants) ? 1 : 0);
            }
            return n != 0;
        }
        
        static String access$0(final Variants variants) {
            return variants.name;
        }
        
        public List<Variant> getVariants() {
            return this.listVariants;
        }
        
        public Variants(final String name, final List<Variant> listVariants) {
            this.name = name;
            this.listVariants = listVariants;
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
                if (0 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public int hashCode() {
            return (0x79 ^ 0x66) * this.name.hashCode() + this.listVariants.hashCode();
        }
    }
    
    public static class Variant
    {
        private final ModelRotation modelRotation;
        private final int weight;
        private final boolean uvLock;
        private final ResourceLocation modelLocation;
        
        @Override
        public int hashCode() {
            final int n = (0x8A ^ 0x95) * this.modelLocation.hashCode();
            int n2;
            if (this.modelRotation != null) {
                n2 = this.modelRotation.hashCode();
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            final int n3 = (0xB0 ^ 0xAF) * (n + n2);
            int n4;
            if (this.uvLock) {
                n4 = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            return n3 + n4;
        }
        
        public Variant(final ResourceLocation modelLocation, final ModelRotation modelRotation, final boolean uvLock, final int weight) {
            this.modelLocation = modelLocation;
            this.modelRotation = modelRotation;
            this.uvLock = uvLock;
            this.weight = weight;
        }
        
        public int getWeight() {
            return this.weight;
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
                if (-1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public ResourceLocation getModelLocation() {
            return this.modelLocation;
        }
        
        public ModelRotation getRotation() {
            return this.modelRotation;
        }
        
        public boolean isUvLocked() {
            return this.uvLock;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return " ".length() != 0;
            }
            if (!(o instanceof Variant)) {
                return "".length() != 0;
            }
            final Variant variant = (Variant)o;
            if (this.modelLocation.equals(variant.modelLocation) && this.modelRotation == variant.modelRotation && this.uvLock == variant.uvLock) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public static class Deserializer implements JsonDeserializer<Variant>
        {
            private static final String[] I;
            
            protected ModelRotation parseRotation(final JsonObject jsonObject) {
                final int int1 = JsonUtils.getInt(jsonObject, Deserializer.I["  ".length()], "".length());
                final int int2 = JsonUtils.getInt(jsonObject, Deserializer.I["   ".length()], "".length());
                final ModelRotation modelRotation = ModelRotation.getModelRotation(int1, int2);
                if (modelRotation == null) {
                    throw new JsonParseException(Deserializer.I[0x3F ^ 0x3B] + int1 + Deserializer.I[0x57 ^ 0x52] + int2);
                }
                return modelRotation;
            }
            
            private static void I() {
                (I = new String[0x0 ^ 0x8])["".length()] = I("\u0007\t\u0005&$J", "eejEO");
                Deserializer.I[" ".length()] = I("\u0010=>#\u0010\u000e", "eKRLs");
                Deserializer.I["  ".length()] = I("\u001c", "dgPke");
                Deserializer.I["   ".length()] = I("\u0018", "adrzs");
                Deserializer.I[0x72 ^ 0x76] = I("\u0005<>#5%6h\u00005#1#\u000f6(7$\u0010683<+6\"r0xy", "LRHBY");
                Deserializer.I[0x51 ^ 0x54] = I("ik\u0013br", "EKjXR");
                Deserializer.I[0x4D ^ 0x4B] = I("\u00148\t*\t", "yWmOe");
                Deserializer.I[0x29 ^ 0x2E] = I("\u001e30*!\u001d", "iVYMI");
            }
            
            private boolean parseUvLock(final JsonObject jsonObject) {
                return JsonUtils.getBoolean(jsonObject, Deserializer.I[" ".length()], "".length() != 0);
            }
            
            protected String parseModel(final JsonObject jsonObject) {
                return JsonUtils.getString(jsonObject, Deserializer.I[0x6F ^ 0x69]);
            }
            
            public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return this.deserialize(jsonElement, type, jsonDeserializationContext);
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
                    if (3 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            protected int parseWeight(final JsonObject jsonObject) {
                return JsonUtils.getInt(jsonObject, Deserializer.I[0x2E ^ 0x29], " ".length());
            }
            
            private ResourceLocation makeModelLocation(final String s) {
                final ResourceLocation resourceLocation = new ResourceLocation(s);
                return new ResourceLocation(resourceLocation.getResourceDomain(), Deserializer.I["".length()] + resourceLocation.getResourcePath());
            }
            
            public Variant deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                return new Variant(this.makeModelLocation(this.parseModel(asJsonObject)), this.parseRotation(asJsonObject), this.parseUvLock(asJsonObject), this.parseWeight(asJsonObject));
            }
            
            static {
                I();
            }
        }
    }
    
    public static class Deserializer implements JsonDeserializer<ModelBlockDefinition>
    {
        private static final String[] I;
        
        public ModelBlockDefinition deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ModelBlockDefinition(this.parseVariantsList(jsonDeserializationContext, jsonElement.getAsJsonObject()));
        }
        
        static {
            I();
        }
        
        protected List<Variants> parseVariantsList(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final JsonObject jsonObject2 = JsonUtils.getJsonObject(jsonObject, Deserializer.I["".length()]);
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator iterator = jsonObject2.entrySet().iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                arrayList.add(this.parseVariants(jsonDeserializationContext, iterator.next()));
            }
            return (List<Variants>)arrayList;
        }
        
        protected Variants parseVariants(final JsonDeserializationContext jsonDeserializationContext, final Map.Entry<String, JsonElement> entry) {
            final String s = entry.getKey();
            final ArrayList arrayList = Lists.newArrayList();
            final JsonElement jsonElement = entry.getValue();
            if (jsonElement.isJsonArray()) {
                final Iterator iterator = jsonElement.getAsJsonArray().iterator();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize((JsonElement)iterator.next(), (Type)Variant.class));
                }
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else {
                arrayList.add(jsonDeserializationContext.deserialize(jsonElement, (Type)Variant.class));
            }
            return new Variants(s, arrayList);
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u001a8\u001d\f$\u0002-\u001c", "lYoeE");
        }
    }
}
