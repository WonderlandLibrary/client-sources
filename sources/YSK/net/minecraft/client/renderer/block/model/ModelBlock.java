package net.minecraft.client.renderer.block.model;

import java.io.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import com.google.gson.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelBlock
{
    protected ResourceLocation parentLocation;
    static final Gson SERIALIZER;
    private static final Logger LOGGER;
    private final List<BlockPart> elements;
    private static final String[] I;
    private final boolean gui3d;
    protected ModelBlock parent;
    private final boolean ambientOcclusion;
    public String name;
    protected final Map<String, String> textures;
    private ItemCameraTransforms cameraTransforms;
    
    protected ModelBlock(final ResourceLocation resourceLocation, final Map<String, String> map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(resourceLocation, Collections.emptyList(), map, b, b2, itemCameraTransforms);
    }
    
    public static ModelBlock deserialize(final String s) {
        return deserialize(new StringReader(s));
    }
    
    public boolean isResolved() {
        if (this.parentLocation != null && (this.parent == null || !this.parent.isResolved())) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
        SERIALIZER = new GsonBuilder().registerTypeAdapter((Type)ModelBlock.class, (Object)new Deserializer()).registerTypeAdapter((Type)BlockPart.class, (Object)new BlockPart.Deserializer()).registerTypeAdapter((Type)BlockPartFace.class, (Object)new BlockPartFace.Deserializer()).registerTypeAdapter((Type)BlockFaceUV.class, (Object)new BlockFaceUV.Deserializer()).registerTypeAdapter((Type)ItemTransformVec3f.class, (Object)new ItemTransformVec3f.Deserializer()).registerTypeAdapter((Type)ItemCameraTransforms.class, (Object)new ItemCameraTransforms.Deserializer()).create();
    }
    
    public ModelBlock getRootModel() {
        ModelBlock rootModel;
        if (this.hasParent()) {
            rootModel = this.parent.getRootModel();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            rootModel = this;
        }
        return rootModel;
    }
    
    public boolean isTexturePresent(final String s) {
        int n;
        if (ModelBlock.I[" ".length()].equals(this.resolveTextureName(s))) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public List<BlockPart> getElements() {
        List<BlockPart> list;
        if (this.hasParent()) {
            list = this.parent.getElements();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            list = this.elements;
        }
        return list;
    }
    
    protected ModelBlock(final List<BlockPart> list, final Map<String, String> map, final boolean b, final boolean b2, final ItemCameraTransforms itemCameraTransforms) {
        this(null, list, map, b, b2, itemCameraTransforms);
    }
    
    private boolean startsWithHash(final String s) {
        if (s.charAt("".length()) == (0x23 ^ 0x0)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private String resolveTextureName(final String s, final Bookkeep bookkeep) {
        if (!this.startsWithHash(s)) {
            return s;
        }
        if (this == bookkeep.modelExt) {
            ModelBlock.LOGGER.warn(ModelBlock.I["  ".length()] + s + ModelBlock.I["   ".length()] + this.name);
            return ModelBlock.I[0x2E ^ 0x2A];
        }
        String s2 = this.textures.get(s.substring(" ".length()));
        if (s2 == null && this.hasParent()) {
            s2 = this.parent.resolveTextureName(s, bookkeep);
        }
        bookkeep.modelExt = this;
        if (s2 != null && this.startsWithHash(s2)) {
            s2 = bookkeep.model.resolveTextureName(s2, bookkeep);
        }
        String s3;
        if (s2 != null && !this.startsWithHash(s2)) {
            s3 = s2;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            s3 = ModelBlock.I[0x67 ^ 0x62];
        }
        return s3;
    }
    
    public ItemCameraTransforms func_181682_g() {
        return new ItemCameraTransforms(this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON), this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON), this.func_181681_a(ItemCameraTransforms.TransformType.HEAD), this.func_181681_a(ItemCameraTransforms.TransformType.GUI), this.func_181681_a(ItemCameraTransforms.TransformType.GROUND), this.func_181681_a(ItemCameraTransforms.TransformType.FIXED));
    }
    
    public boolean isGui3d() {
        return this.gui3d;
    }
    
    public String resolveTextureName(String string) {
        if (!this.startsWithHash(string)) {
            string = String.valueOf((char)(0x3A ^ 0x19)) + string;
        }
        return this.resolveTextureName(string, new Bookkeep(this, null));
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static ModelBlock deserialize(final Reader reader) {
        return (ModelBlock)ModelBlock.SERIALIZER.fromJson(reader, (Class)ModelBlock.class);
    }
    
    public void getParentFromMap(final Map<ResourceLocation, ModelBlock> map) {
        if (this.parentLocation != null) {
            this.parent = map.get(this.parentLocation);
        }
    }
    
    public ResourceLocation getParentLocation() {
        return this.parentLocation;
    }
    
    private ItemTransformVec3f func_181681_a(final ItemCameraTransforms.TransformType transformType) {
        ItemTransformVec3f itemTransformVec3f;
        if (this.parent != null && !this.cameraTransforms.func_181687_c(transformType)) {
            itemTransformVec3f = this.parent.func_181681_a(transformType);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            itemTransformVec3f = this.cameraTransforms.getTransform(transformType);
        }
        return itemTransformVec3f;
    }
    
    public boolean isAmbientOcclusion() {
        boolean b;
        if (this.hasParent()) {
            b = this.parent.isAmbientOcclusion();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            b = this.ambientOcclusion;
        }
        return b;
    }
    
    private boolean hasParent() {
        if (this.parent != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void checkModelHierarchy(final Map<ResourceLocation, ModelBlock> map) {
        final Iterator<ModelBlock> iterator = map.values().iterator();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelBlock modelBlock = iterator.next();
            try {
                ModelBlock modelBlock2 = modelBlock.parent;
                ModelBlock modelBlock3 = modelBlock2.parent;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (modelBlock2 != modelBlock3) {
                    modelBlock2 = modelBlock2.parent;
                    modelBlock3 = modelBlock3.parent.parent;
                }
                throw new LoopException();
            }
            catch (NullPointerException ex) {}
        }
    }
    
    private ModelBlock(final ResourceLocation parentLocation, final List<BlockPart> elements, final Map<String, String> textures, final boolean ambientOcclusion, final boolean gui3d, final ItemCameraTransforms cameraTransforms) {
        this.name = ModelBlock.I["".length()];
        this.elements = elements;
        this.ambientOcclusion = ambientOcclusion;
        this.gui3d = gui3d;
        this.textures = textures;
        this.parentLocation = parentLocation;
        this.cameraTransforms = cameraTransforms;
    }
    
    private static void I() {
        (I = new String[0xA6 ^ 0xA0])["".length()] = I("", "rPezn");
        ModelBlock.I[" ".length()] = I("\"\u0004\u0016\u0014\n!\n\u000b\b", "Omegc");
        ModelBlock.I["  ".length()] = I("\u0000/\"$$0a7)h'$0)$#$c2--564-u%6#h!.c38\" 1\"h'$%#:0/ #ru", "UACFH");
        ModelBlock.I["   ".length()] = I("d\"*s", "DKDSo");
        ModelBlock.I[0x25 ^ 0x21] = I("\u0015.8\u0010\f\u0016 %\f", "xGKce");
        ModelBlock.I[0x5F ^ 0x5A] = I("\u001f\f57!\u001c\u0002(+", "reFDH");
    }
    
    public static class Deserializer implements JsonDeserializer<ModelBlock>
    {
        private static final String[] I;
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        protected boolean getAmbientOcclusionEnabled(final JsonObject jsonObject) {
            return JsonUtils.getBoolean(jsonObject, Deserializer.I[0x4D ^ 0x45], " ".length() != 0);
        }
        
        public ModelBlock deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final List<BlockPart> modelElements = this.getModelElements(jsonDeserializationContext, asJsonObject);
            final String parent = this.getParent(asJsonObject);
            final boolean empty = StringUtils.isEmpty((CharSequence)parent);
            final boolean empty2 = modelElements.isEmpty();
            if (empty2 && empty) {
                throw new JsonParseException(Deserializer.I["".length()]);
            }
            if (!empty && !empty2) {
                throw new JsonParseException(Deserializer.I[" ".length()]);
            }
            final Map<String, String> textures = this.getTextures(asJsonObject);
            final boolean ambientOcclusionEnabled = this.getAmbientOcclusionEnabled(asJsonObject);
            ItemCameraTransforms default1 = ItemCameraTransforms.DEFAULT;
            if (asJsonObject.has(Deserializer.I["  ".length()])) {
                default1 = (ItemCameraTransforms)jsonDeserializationContext.deserialize((JsonElement)JsonUtils.getJsonObject(asJsonObject, Deserializer.I["   ".length()]), (Type)ItemCameraTransforms.class);
            }
            ModelBlock modelBlock;
            if (empty2) {
                modelBlock = new ModelBlock(new ResourceLocation(parent), textures, ambientOcclusionEnabled, " ".length() != 0, default1);
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                modelBlock = new ModelBlock(modelElements, textures, ambientOcclusionEnabled, " ".length() != 0, default1);
            }
            return modelBlock;
        }
        
        protected List<BlockPart> getModelElements(final JsonDeserializationContext jsonDeserializationContext, final JsonObject jsonObject) {
            final ArrayList arrayList = Lists.newArrayList();
            if (jsonObject.has(Deserializer.I[0x3F ^ 0x36])) {
                final Iterator iterator = JsonUtils.getJsonArray(jsonObject, Deserializer.I[0xB ^ 0x1]).iterator();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    arrayList.add(jsonDeserializationContext.deserialize((JsonElement)iterator.next(), (Type)BlockPart.class));
                }
            }
            return (List<BlockPart>)arrayList;
        }
        
        static {
            I();
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Map<String, String> getTextures(final JsonObject jsonObject) {
            final HashMap hashMap = Maps.newHashMap();
            if (jsonObject.has(Deserializer.I[0x72 ^ 0x76])) {
                final Iterator<Map.Entry<String, V>> iterator = jsonObject.getAsJsonObject(Deserializer.I[0x9F ^ 0x9A]).entrySet().iterator();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Map.Entry<String, V> entry = iterator.next();
                    hashMap.put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
                }
            }
            return (Map<String, String>)hashMap;
        }
        
        private static void I() {
            (I = new String[0x27 ^ 0x2C])["".length()] = I("\u000b>&7:\u0004=-1=i ,%$  ,'q,;=<4;r,84$7' \"i=;t!( ,:%er/;$'6i:4 &!1#", "IRITQ");
            Deserializer.I[" ".length()] = I("/'\u0003\t& $\b\u000f!M9\t\u001b8\u00049\t\u0019m\b\"\u0018\u0002(\u001fk\t\u0006(\u0000.\u0002\u001e>M$\u001eJ=\f9\t\u00049Ak\n\u00058\u0003/L\b\"\u0019#", "mKljM");
            Deserializer.I["  ".length()] = I("\u0001+\u0012\u0018\n\u0004;", "eBahf");
            Deserializer.I["   ".length()] = I("\u000f1\u0002\u001e\n\n!", "kXqnf");
            Deserializer.I[0x5F ^ 0x5B] = I("\u00102\u001a\u001e\u000f\u00162\u0011", "dWbjz");
            Deserializer.I[0x4C ^ 0x49] = I("\u0006\u00152;\u0000\u0000\u00159", "rpJOu");
            Deserializer.I[0x9E ^ 0x98] = I("2.\u0011*#6", "BOcOM");
            Deserializer.I[0x32 ^ 0x35] = I("", "SKnwY");
            Deserializer.I[0x2A ^ 0x22] = I("\u000b\u0018#>\u000f\u0004\u0001.4\t\u0006\u00002>\u0005\u0004", "juAWj");
            Deserializer.I[0x55 ^ 0x5C] = I("4\u000f)\"\u0011?\u0017?", "QcLOt");
            Deserializer.I[0x6D ^ 0x67] = I("\u0000\u001b\u001d\u001c\u0001\u000b\u0003\u000b", "ewxqd");
        }
        
        private String getParent(final JsonObject jsonObject) {
            return JsonUtils.getString(jsonObject, Deserializer.I[0x71 ^ 0x77], Deserializer.I[0x88 ^ 0x8F]);
        }
    }
    
    static final class Bookkeep
    {
        public ModelBlock modelExt;
        public final ModelBlock model;
        
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
                if (4 < 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        Bookkeep(final ModelBlock modelBlock, final Bookkeep bookkeep) {
            this(modelBlock);
        }
        
        private Bookkeep(final ModelBlock model) {
            this.model = model;
        }
    }
    
    public static class LoopException extends RuntimeException
    {
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
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
