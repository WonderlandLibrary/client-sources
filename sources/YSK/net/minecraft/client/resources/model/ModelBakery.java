package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.*;
import org.apache.commons.io.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;

public class ModelBakery
{
    private static final ModelBlock MODEL_ENTITY;
    private final Map<ResourceLocation, ModelBlockDefinition> blockDefinitions;
    private static final Joiner JOINER;
    private final IResourceManager resourceManager;
    private final FaceBakery faceBakery;
    private Map<String, ResourceLocation> itemLocations;
    protected static final ModelResourceLocation MODEL_MISSING;
    private RegistrySimple<ModelResourceLocation, IBakedModel> bakedRegistry;
    private static final Logger LOGGER;
    private static final ModelBlock MODEL_CLOCK;
    private static final ModelBlock MODEL_GENERATED;
    private final BlockModelShapes blockModelShapes;
    private final Map<ResourceLocation, ModelBlock> models;
    private Map<Item, List<String>> variantNames;
    private final Map<ResourceLocation, TextureAtlasSprite> sprites;
    private static final ModelBlock MODEL_COMPASS;
    private final TextureMap textureMap;
    private final ItemModelGenerator itemModelGenerator;
    private final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants;
    private static final Set<ResourceLocation> LOCATIONS_BUILTIN_TEXTURES;
    private static final String[] I;
    private static final Map<String, String> BUILT_IN_MODELS;
    
    private void bakeBlockModels() {
        final Iterator<ModelResourceLocation> iterator = this.variants.keySet().iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelResourceLocation modelResourceLocation = iterator.next();
            final WeightedBakedModel.Builder builder = new WeightedBakedModel.Builder();
            int length = "".length();
            final Iterator<ModelBlockDefinition.Variant> iterator2 = this.variants.get(modelResourceLocation).getVariants().iterator();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final ModelBlockDefinition.Variant variant = iterator2.next();
                final ModelBlock modelBlock = this.models.get(variant.getModelLocation());
                if (modelBlock != null && modelBlock.isResolved()) {
                    ++length;
                    builder.add(this.bakeModel(modelBlock, variant.getRotation(), variant.isUvLocked()), variant.getWeight());
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    continue;
                }
                else {
                    ModelBakery.LOGGER.warn(ModelBakery.I[90 + 167 - 133 + 153] + modelResourceLocation);
                }
            }
            if (length == 0) {
                ModelBakery.LOGGER.warn(ModelBakery.I[86 + 253 - 80 + 19] + modelResourceLocation);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                continue;
            }
            else if (length == " ".length()) {
                this.bakedRegistry.putObject(modelResourceLocation, builder.first());
                "".length();
                if (4 == 3) {
                    throw null;
                }
                continue;
            }
            else {
                this.bakedRegistry.putObject(modelResourceLocation, builder.build());
            }
        }
        final Iterator<Map.Entry<String, ResourceLocation>> iterator3 = this.itemLocations.entrySet().iterator();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (iterator3.hasNext()) {
            final Map.Entry<String, ResourceLocation> entry = iterator3.next();
            final ResourceLocation resourceLocation = entry.getValue();
            final ModelResourceLocation modelResourceLocation2 = new ModelResourceLocation(entry.getKey(), ModelBakery.I[128 + 157 - 224 + 218]);
            final ModelBlock modelBlock2 = this.models.get(resourceLocation);
            if (modelBlock2 != null && modelBlock2.isResolved()) {
                if (this.isCustomRenderer(modelBlock2)) {
                    this.bakedRegistry.putObject(modelResourceLocation2, new BuiltInModel(modelBlock2.func_181682_g()));
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    this.bakedRegistry.putObject(modelResourceLocation2, this.bakeModel(modelBlock2, ModelRotation.X0_Y0, (boolean)("".length() != 0)));
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    continue;
                }
            }
            else {
                ModelBakery.LOGGER.warn(ModelBakery.I[192 + 9 - 42 + 121] + resourceLocation);
            }
        }
    }
    
    private ResourceLocation getBlockStateLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), ModelBakery.I[0x9B ^ 0xB2] + resourceLocation.getResourcePath() + ModelBakery.I[0x80 ^ 0xAA]);
    }
    
    private Set<ResourceLocation> getVariantsTextureLocations() {
        final HashSet hashSet = Sets.newHashSet();
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.variants.keySet());
        Collections.sort((List<Object>)arrayList, (Comparator<? super Object>)new Comparator<ModelResourceLocation>(this) {
            final ModelBakery this$0;
            
            @Override
            public int compare(final ModelResourceLocation modelResourceLocation, final ModelResourceLocation modelResourceLocation2) {
                return modelResourceLocation.toString().compareTo(modelResourceLocation2.toString());
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
                    if (3 < 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((ModelResourceLocation)o, (ModelResourceLocation)o2);
            }
        });
        final Iterator<ModelResourceLocation> iterator = arrayList.iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelResourceLocation modelResourceLocation = iterator.next();
            final Iterator<ModelBlockDefinition.Variant> iterator2 = this.variants.get(modelResourceLocation).getVariants().iterator();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final ModelBlock modelBlock = this.models.get(iterator2.next().getModelLocation());
                if (modelBlock == null) {
                    ModelBakery.LOGGER.warn(ModelBakery.I[167 + 233 - 274 + 155] + modelResourceLocation);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    continue;
                }
                else {
                    hashSet.addAll(this.getTextureLocations(modelBlock));
                }
            }
        }
        hashSet.addAll(ModelBakery.LOCATIONS_BUILTIN_TEXTURES);
        return (Set<ResourceLocation>)hashSet;
    }
    
    private void loadSprites() {
        final Set<ResourceLocation> variantsTextureLocations = this.getVariantsTextureLocations();
        variantsTextureLocations.addAll(this.getItemsTextureLocations());
        variantsTextureLocations.remove(TextureMap.LOCATION_MISSING_TEXTURE);
        this.textureMap.loadSprites(this.resourceManager, new IIconCreator(this, variantsTextureLocations) {
            private final Set val$set;
            final ModelBakery this$0;
            
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
                    if (2 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void registerSprites(final TextureMap textureMap) {
                final Iterator<ResourceLocation> iterator = this.val$set.iterator();
                "".length();
                if (0 == 4) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final ResourceLocation resourceLocation = iterator.next();
                    ModelBakery.access$0(this.this$0).put(resourceLocation, textureMap.registerSprite(resourceLocation));
                }
            }
        });
        this.sprites.put(new ResourceLocation(ModelBakery.I[43 + 58 + 2 + 184]), this.textureMap.getMissingSprite());
    }
    
    private void loadItemModels() {
        this.registerVariantNames();
        final Iterator<Item> iterator = Item.itemRegistry.iterator();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            final Iterator<String> iterator2 = this.getVariantNames(item).iterator();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final String s = iterator2.next();
                final ResourceLocation itemLocation = this.getItemLocation(s);
                this.itemLocations.put(s, itemLocation);
                if (this.models.get(itemLocation) == null) {
                    try {
                        this.models.put(itemLocation, this.loadModel(itemLocation));
                        "".length();
                        if (-1 == 0) {
                            throw null;
                        }
                        continue;
                    }
                    catch (Exception ex) {
                        ModelBakery.LOGGER.warn(ModelBakery.I[0xBF ^ 0x89] + itemLocation + ModelBakery.I[0x45 ^ 0x72] + Item.itemRegistry.getNameForObject(item) + ModelBakery.I[0x51 ^ 0x69], (Throwable)ex);
                    }
                }
            }
        }
    }
    
    private void loadVariantModels() {
        final Iterator<ModelResourceLocation> iterator = this.variants.keySet().iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelResourceLocation modelResourceLocation = iterator.next();
            final Iterator<ModelBlockDefinition.Variant> iterator2 = this.variants.get(modelResourceLocation).getVariants().iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final ResourceLocation modelLocation = iterator2.next().getModelLocation();
                if (this.models.get(modelLocation) == null) {
                    try {
                        this.models.put(modelLocation, this.loadModel(modelLocation));
                        "".length();
                        if (3 < 2) {
                            throw null;
                        }
                        continue;
                    }
                    catch (Exception ex) {
                        ModelBakery.LOGGER.warn(ModelBakery.I[0xA7 ^ 0x8C] + modelLocation + ModelBakery.I[0x3C ^ 0x10] + modelResourceLocation + ModelBakery.I[0xEB ^ 0xC6], (Throwable)ex);
                    }
                }
            }
        }
    }
    
    public IRegistry<ModelResourceLocation, IBakedModel> setupModelRegistry() {
        this.loadVariantItemModels();
        this.loadModelsCheck();
        this.loadSprites();
        this.bakeItemModels();
        this.bakeBlockModels();
        return this.bakedRegistry;
    }
    
    private List<String> getVariantNames(final Item item) {
        List<String> singletonList = this.variantNames.get(item);
        if (singletonList == null) {
            singletonList = Collections.singletonList(Item.itemRegistry.getNameForObject(item).toString());
        }
        return singletonList;
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private ResourceLocation getItemLocation(final String s) {
        final ResourceLocation resourceLocation = new ResourceLocation(s);
        return new ResourceLocation(resourceLocation.getResourceDomain(), ModelBakery.I[176 + 170 - 282 + 212] + resourceLocation.getResourcePath());
    }
    
    private void loadModelsCheck() {
        this.loadModels();
        final Iterator<ModelBlock> iterator = this.models.values().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().getParentFromMap(this.models);
        }
        ModelBlock.checkModelHierarchy(this.models);
    }
    
    private static void I() {
        (I = new String[193 + 60 - 18 + 54])["".length()] = I("\f\u001d8\u0004\r\u001d^ \u0006\u0012\u000b\u0003\b\u0001\n\u0001\u0006", "nqWgf");
        ModelBakery.I[" ".length()] = I("\r-\u00182&\u001cn\u000009\n3(\"9\u0006-\u001b", "oAwQM");
        ModelBakery.I["  ".length()] = I("\u0000)=5\"\u0011j>7?\u0003\u001a4:&\u0015", "bERVI");
        ModelBakery.I["   ".length()] = I("\u001b!8'\u001e\nb;%\u0003\u0018\u0012$0\u001c\u0015!", "yMWDu");
        ModelBakery.I[0x8A ^ 0x8E] = I("\u0018\u0005\u001b%\u0003\tF\u0010#\u001b\u000e\u001b\u001b?7\t\u001d\u0015!\r%Y", "zitFh");
        ModelBakery.I[0x94 ^ 0x91] = I("\u0016=\u000b\u0001*\u0007~\u0000\u00072\u0000#\u000b\u001b\u001e\u0007%\u0005\u0005$+`", "tQdbA");
        ModelBakery.I[0x17 ^ 0x11] = I("\u0015\u0005\u0017*3\u0004F\u001c,+\u0003\u001b\u00170\u0007\u0004\u001d\u0019.=([", "wixIX");
        ModelBakery.I[0x76 ^ 0x71] = I("\t\u0002'\u0002&\u0018A,\u0004>\u001f\u001c'\u0018\u0012\u0018\u001a)\u0006(4]", "knHaM");
        ModelBakery.I[0x69 ^ 0x61] = I("\n-\u0000!,\u001bn\u000b'4\u001c3\u0000;\u0018\u001b5\u000e%\"7u", "hAoBG");
        ModelBakery.I[0x12 ^ 0x1B] = I("!\b=\u0002\u00030K6\u0004\u001b7\u0016=\u001870\u00103\u0006\r\u001cQ", "CdRah");
        ModelBakery.I[0xA3 ^ 0xA9] = I("&>\r9\u00037}\u0006?\u001b0 \r#77&\u0003=\r\u001bd", "DRbZh");
        ModelBakery.I[0xB0 ^ 0xBB] = I(")\u00157\u0010\u001c8V<\u0016\u0004?\u000b7\n(8\r9\u0014\u0012\u0014N", "KyXsw");
        ModelBakery.I[0x62 ^ 0x6E] = I("\t? .\u0001\u0018|+(\u0019\u001f! 45\u0018'.*\u000f4k", "kSOMj");
        ModelBakery.I[0x42 ^ 0x4F] = I("&<\u00056\n7\u007f\u000e0\u00120\"\u0005,>7$\u000b2\u0004\u001bi", "DPjUa");
        ModelBakery.I[0x72 ^ 0x7C] = I("\u0006\u001e2\u0001\u0000@\u000f:\u001c\u0007\u001656\u001e\u001e\u0000\u0018\b\u001f\u001f\u0000\u001e\b\u0004\u0016\u0003\u00072\u0018", "ojWls");
        ModelBakery.I[0x46 ^ 0x49] = I("9!\u0014\u001c#\u007f0\u001c\u0001$)\n\u0010\u0003=?'.\u0002<?!.\u001285&\u0005\u0001<1!\u0014", "PUqqP");
        ModelBakery.I[0x5D ^ 0x4D] = I("\u0011\u00110\n\u0007W\u00008\u0017\u0000\u0001:4\u0015\u0019\u0017\u0017\n\u0014\u0018\u0017\u0011\n\u000b\u0011\u001f\u0002<\t\u0013\u000b", "xeUgt");
        ModelBakery.I[0xD5 ^ 0xC4] = I("?:\u0014#=y+\u001c>:/\u0011\u0010<#9<.=\"9:.,!9:\u0002", "VNqNN");
        ModelBakery.I[0x12 ^ 0x0] = I("\u0017; \u0019 \u001c f\u0018=\u0006= \u001b3", "uNIuT");
        ModelBakery.I[0x3F ^ 0x2C] = I("5\u0006'\u001a\u00056\b", "XoTil");
        ModelBakery.I[0x4D ^ 0x59] = I("GZ|B", "gwBbI");
        ModelBakery.I[0xB8 ^ 0xAD] = I("\u0013g# 4\u0005 (8\"J\u007f\u001d7qHg >>\u0005g|l\nXif|}Hu\u001b`qHed8>J\u007ff\u0017`^if}gDewz\fDefls\u000e$%)\"J\u007ff7qHeflqHg\"#&\u0006g|l*J00nkH\u001ev`qXif}gDewz\fDed84\u001013>4J\u007fdn,Hef1,58", "hEFLQ");
        ModelBakery.I[0x3D ^ 0x2B] = I("7H\"?&!\u000f)'0nP\u001c(clH!!,!H}s\u0018|FgcolZ\u001a\u007fclJe',nPg\brzFgbu`Jve\u001e`Jgsa*\u000b$60nPg(clJgsclH#<4\"H}s8n\u001f1qyl1w\u007fc|Fgbu`Jve\u001e`Je'&4\u001e2!&nPeq>lJg.>\u0011\u0017", "LjGSC");
        ModelBakery.I[0x5C ^ 0x4B] = I("\u0003t&\u000b&\u00153-\u00130Zl\u0018\u001ccXt%\u0015,\u0015tyG\u0018HzcWoXf\u001eKcXva\u0013,Zlc<rNzcVuTvrQ\u001eTvcGa\u001e7 \u00020Zlc\u001ccXvcGcXt'\b4\u0016tyG8Z#5EyX\rsKcHzcVuTvrQ\u001eTva\u0013&\u0000\"6\u0015&ZlaE>Xvc\u001a>%+", "xVCgC");
        ModelBakery.I[0xB4 ^ 0xAC] = I("+L+\u000b\u0003=\u000b \u0013\u0015rT\u0015\u001cFpL(\u0015\t=LtG=`BnWJp^\u0013KFpNl\u0013\trTn<WfBnVP|N\u007fQ;|NnGD6\u000f-\u0002\u0015rTn\u001cFpNnGFpL*\b\u0011>LtG\u001dr\u001b8E\\p5~KF`BnVP|N\u007fQ;|Nl\u0013\u0003(\u001a;\u0015\u0003rTlE\u001bpNn\u001a\u001b\r\u0013", "PnNgf");
        ModelBakery.I[0xDD ^ 0xC4] = I("#>0)\u001a 0", "NWCZs");
        ModelBakery.I[0x2F ^ 0x35] = I("\u0004\u0010?\u0011\u0017\u0002\u00018\u001b\u000bC\u00180\u0006\u000e\u0006\u0007", "cuQte");
        ModelBakery.I[0x85 ^ 0x9E] = I("\f<>\u0004-\u001c s\u0013)\u00016!\u00158\u0006<=T!\u000e!8\u0011>", "oSStL");
        ModelBakery.I[0x10 ^ 0xC] = I("\t*\u0019 2J!\u001d=$\u0018'\f:.\u0004f\u001523\u0001#\n", "jFxSA");
        ModelBakery.I[0x31 ^ 0x2C] = I("\u0006\u001f;\u0005)D\u0016:\u0012+\u0010\nt\u000b#\u0016\u00181\u0014", "dsTfB");
        ModelBakery.I[0x6F ^ 0x71] = I("\u001b\u0001-#\u000b\u0014\u0007)#1", "ruHNT");
        ModelBakery.I[0x21 ^ 0x3E] = I("!\u00179 2#", "OxKMS");
        ModelBakery.I[0x7B ^ 0x5B] = I("\b\b=", "eiMKB");
        ModelBakery.I[0x8A ^ 0xAB] = I("\u001a\n-\u0016#*D8\u001bo#\u000b-\u0010o9\u0005>\u001d.!\u0010vT", "OdLtO");
        ModelBakery.I[0x38 ^ 0x1A] = I("a\u0016+'\u001ba", "ApYHv");
        ModelBakery.I[0xE4 ^ 0xC7] = I("\f:\u0007\u0011<<t\u0012\u001cp5;\u0007\u0017p=1\u0000\u001a>0 \u000f\u001c>y", "YTfsP");
        ModelBakery.I[0x60 ^ 0x44] = I("\u001d \f \u00056:\n=\u0015<n\u000e!P=6\f*\u0000,'\u0000!P/&\n!P4!\u000e+\u00196)O\"\u001f<+\u0003o\u0014=(\u0006!\u0019,'\u0000!P7(Oh", "XNoOp");
        ModelBakery.I[0x87 ^ 0xA2] = I("sU\u0017\u001b\u00169OQN", "Tuqiy");
        ModelBakery.I[0x3C ^ 0x1A] = I("Dh\u001c\u001ee\u0011-\u0006\u001f0\u0011+\u0010\u0000$\u0000#OPb", "cHupE");
        ModelBakery.I[0x74 ^ 0x53] = I("C", "ddaUs");
        ModelBakery.I[0x41 ^ 0x69] = I("\u0010\u0018*+\u0004;\u0002,6\u00141V(*Q0\u000e*!\u0001!\u001f&*Q\"\u001e,*Q9\u0019( \u0018;\u0011i)\u001e1\u0013%d\u00150\u0010 *\u0018!\u001f&*Q:\u0010i)\u001e1\u0013%d", "UvIDq");
        ModelBakery.I[0xA2 ^ 0x8B] = I("\n\u0001%!3\u001b\u0019+6=\u001bB", "hmJBX");
        ModelBakery.I[0x1C ^ 0x36] = I("I\u0004! \u000f", "gnROa");
        ModelBakery.I[0x2F ^ 0x4] = I("\u000f&\u0016*\u001e?h\u0003'R6'\u0016,R8$\u0018+\u0019z%\u0018,\u00176rWo", "ZHwHr");
        ModelBakery.I[0x6E ^ 0x42] = I("hO\u000b.0o\u0019\f3+.\u0001\u0019{bh", "OomAB");
        ModelBakery.I[0x7B ^ 0x56] = I("S", "tqsCe");
        ModelBakery.I[0xA3 ^ 0x8D] = I("\u00179\u001e+,\u001c\"X =\u001b)\u0005&,\u0010(", "uLwGX");
        ModelBakery.I[0x42 ^ 0x6D] = I("\u0012\u00018\u000b9\u0019\u001a~\u0004\"\u001d\u00040\u0014>", "ptQgM");
        ModelBakery.I[0x30 ^ 0x0] = I("\u0000\u001f+:6\u000b\u0004m5.\r\t)", "bjBVB");
        ModelBakery.I[0x81 ^ 0xB0] = I("\u0006'0'\u0011\r<v.\u000b\u0010;-2", "dRYKe");
        ModelBakery.I[0x75 ^ 0x47] = I(")\u0003/\u0007\u0011\"\u0018i", "KvFke");
        ModelBakery.I[0x4E ^ 0x7D] = I("5 '\u000e\u0000>;a", "WUNbt");
        ModelBakery.I[0x1B ^ 0x2F] = I(">5%=\u001c u", "SZAXp");
        ModelBakery.I[0x95 ^ 0xA0] = I("L\u0000\u0014\u0003:", "bjglT");
        ModelBakery.I[0x3D ^ 0xB] = I("7845\n\u0007v!8F\u000e943F\u000b\"0:F\u000f912\nXvr", "bVUWf");
        ModelBakery.I[0x95 ^ 0xA2] = I("kV \u0002\u0003l\u001f2\b\u001cvVa", "LvFmq");
        ModelBakery.I[0x51 ^ 0x69] = I("C", "dnOuT");
        ModelBakery.I[0x16 ^ 0x2F] = I("\u0001556)", "rAZXL");
        ModelBakery.I[0x62 ^ 0x58] = I(",\u001b\u000b\r#?\f", "KijcJ");
        ModelBakery.I[0x75 ^ 0x4E] = I("\u00179.%%\u0004.\u00108!\u001f$;#", "pKOKL");
        ModelBakery.I[0x14 ^ 0x28] = I("\u000e\u0007<%#\u001e\u000b", "jnSWJ");
        ModelBakery.I[0xF9 ^ 0xC4] = I(",\u0005'\u0000-<\t\u0017\u0001)'\u0003<\u001a", "HlHrD");
        ModelBakery.I[0x33 ^ 0xD] = I("\n\u001e.4=\u0002\u0004/", "kpJQN");
        ModelBakery.I[0x9F ^ 0xA0] = I("2\r\u0003/\u0014:\u0017\u0002\u0015\u0014>\f\b>\u000f", "ScgJg");
        ModelBakery.I[0xC4 ^ 0x84] = I(">' \u0011", "ZNRev");
        ModelBakery.I[0x10 ^ 0x51] = I("$\u00078\u0011\u0006\"7=\n\u00073", "GhYcu");
        ModelBakery.I[0xCE ^ 0x8C] = I("9,>\u0013<%", "ICZiS");
        ModelBakery.I[0x11 ^ 0x52] = I(")\r#\r\u001f*\r&9\u001c", "FlHRo");
        ModelBakery.I[0x36 ^ 0x72] = I("\u00033\b>\u0017\u0015\u001c\n'\u0015\u001e(\t", "pCzKt");
        ModelBakery.I[0x20 ^ 0x65] = I("\u000b;?\u000416\"!\u00067\u0002!", "iRMgY");
        ModelBakery.I[0x7E ^ 0x38] = I("'\u001a\u0007\"\u001d(0\u0019)\u0010#\u0004\u001a", "MoiEq");
        ModelBakery.I[0xE5 ^ 0xA2] = I("#\u0010-\u0004/#,<\u000b',\u0018?", "BsLgF");
        ModelBakery.I[0x75 ^ 0x3D] = I("37:-(87#\u0019\u0007;7&-\u0004", "WVHFw");
        ModelBakery.I[0x7E ^ 0x37] = I("\u0015\b\t&'\u001b\u0019\u000e\u0010:\u001d", "zibyT");
        ModelBakery.I[0x32 ^ 0x78] = I("5\n4\u000f.#%5\u001b=*\u0013(\u001d", "FzFzM");
        ModelBakery.I[0x63 ^ 0x28] = I("+-\"\u00052\u001671\u00166 *7", "IDPfZ");
        ModelBakery.I[0x2A ^ 0x66] = I("\u001d\u0014!.\u001e\u0012><(\u0002\u001b\b!.", "waOIr");
        ModelBakery.I[0xE ^ 0x43] = I("/4\u000b2$/\b\u00190=\">\u00046", "NWjQM");
        ModelBakery.I[0xC2 ^ 0x8C] = I(",\u0012\u001d\u001e\t'\u0012\u0004*%)\u0003\u0003\u001c8/", "HsouV");
        ModelBakery.I[0x7C ^ 0x33] = I("\u001c\u0016\u0001#", "owoGj");
        ModelBakery.I[0xDF ^ 0x8F] = I("\u0019\u0001\u0015\u0018\t\n\n\u0015", "kdqGz");
        ModelBakery.I[0x1C ^ 0x4D] = I("!7\u000f))!1", "NVdvE");
        ModelBakery.I[0x2E ^ 0x7C] = I("\u0012)5>\u0002\u0004\u0006+$\u0006", "aYGKa");
        ModelBakery.I[0x43 ^ 0x10] = I("$ \u0003-\f\u0019%\u001e)", "FIqNd");
        ModelBakery.I[0x32 ^ 0x66] = I("<0;%\u001f3\u001a9-\u0014", "VEUBs");
        ModelBakery.I[0x2 ^ 0x57] = I("\u00189'7\b\u00129:\r\u0017", "wXLhd");
        ModelBakery.I[0x46 ^ 0x10] = I("$\u001d'\u0018+229\b)!\b&", "WmUmH");
        ModelBakery.I[0x27 ^ 0x70] = I("!\u0003?\f\u0005\u001c\u0006(\u000e\u001b&\u0019", "CjMom");
        ModelBakery.I[0xFD ^ 0xA5] = I("!\u0011\u001d\u001f(.;\u001f\u001d%=\u0001\u0000", "KdsxD");
        ModelBakery.I[0xE1 ^ 0xB8] = I("\u001b5\u0005\u001d.\r", "hEjsI");
        ModelBakery.I[0x10 ^ 0x4A] = I("\u001e\u0014\u001a\t\u0012\b;\u0002\u0002\u0001", "mdugu");
        ModelBakery.I[0x67 ^ 0x3C] = I("\u001d\u0013(>\u0005\u001a\u001d(?", "nrFZv");
        ModelBakery.I[0xDA ^ 0x86] = I(":#\u001f\u0004\r5.\u0012(\u001b8%\u0012\u0004\u001c6%\u0013", "YKvwh");
        ModelBakery.I[0x41 ^ 0x1C] = I(" .\b9\u0004;\u001c\u00147\u001e70\u00139\u001e6", "SCgVp");
        ModelBakery.I[0xCE ^ 0x90] = I("0\u0000\u000e\b\u001d#\u000b\u000e$\u001a-\u000b\u000f", "BejWn");
        ModelBakery.I[0x2F ^ 0x70] = I("\"\n:6\b-\u00077\u001a\u001f$\u0006\f6\f/\u0006 1\u0002/\u0007", "AbSEm");
        ModelBakery.I[0xA3 ^ 0xC3] = I("\u0018\u001b$\u0003\u0001\u0003)9\t\u00114\u0005*\u0002\u0011\u0018\u0002$\u0002\u0010", "kvKlu");
        ModelBakery.I[0x13 ^ 0x72] = I("02\u0003\n56\"\u0011\u0006", "TWbnj");
        ModelBakery.I[0xA7 ^ 0xC5] = I("\u0016\u0000\u000e\u000b,\u0005\u0013\u0003\u0014\u0000", "babgs");
        ModelBakery.I[0x5A ^ 0x39] = I("\u0015\u00020\u001e", "sgBpb");
        ModelBakery.I[0x2F ^ 0x4B] = I(">5\u0010\u001d-8%\u0002\u0011", "ZPqyr");
        ModelBakery.I[0xCB ^ 0xAE] = I("%5\t7<\u0018.\u0007;;", "GYhTW");
        ModelBakery.I[0x11 ^ 0x77] = I("\u001f7\u0002\u000f\u0006\u0002=\n", "mRfPq");
        ModelBakery.I[0xE3 ^ 0x84] = I("\u0014\u0014\u0011*#,\u0011\u001b !", "sftOM");
        ModelBakery.I[0x41 ^ 0x29] = I("+&\f$>\u0016#\f<<", "ITcSP");
        ModelBakery.I[0xEA ^ 0x83] = I(":*\u00033\u0013/)\u0019:", "XFvVL");
        ModelBakery.I[0x31 ^ 0x5B] = I("*\u0007'\u0002&?-\"\u001d%6", "ZrUrJ");
        ModelBakery.I[0x6 ^ 0x6D] = I("\u000e2\u0013&\u000e\u001a$\u001d$", "mKrHQ");
        ModelBakery.I[0x43 ^ 0x2F] = I("!\n\u0016\u00126 <\r\u000b<>", "RczdS");
        ModelBakery.I[0xF1 ^ 0x9C] = I("?6241/+<!", "XDSMn");
        ModelBakery.I[0x2A ^ 0x44] = I("2\u00138\u0013\u00115\u00159\u0014", "BzVxN");
        ModelBakery.I[0xEE ^ 0x81] = I("\t?\t*'\u00129\u000b#", "eVdOx");
        ModelBakery.I[0xB0 ^ 0xC0] = I("\u001f\u0017/\u0003.\u0011-4\u0000.\n", "frCoA");
        ModelBakery.I[0xC6 ^ 0xB7] = I("\u000f? \u001a\u0013<4+\u0007\u0002<!(\u001d\u000b", "cVGrg");
        ModelBakery.I[0x19 ^ 0x6B] = I(" *\r\u0001\u00039*5\u0013\u0002\"'", "MKjdm");
        ModelBakery.I[0xCB ^ 0xB8] = I("9\u00113'\r3<%&\u0005:", "VcRIj");
        ModelBakery.I[0x4E ^ 0x3A] = I("\u0000)\u001b\u0003\u000b(6\u001d\u0018\u0002", "wArwn");
        ModelBakery.I[0x1B ^ 0x6E] = I("\u0014\u0018\u0007\u001e0\u001c\u0010\u0006\u0014", "pyizU");
        ModelBakery.I[0xE7 ^ 0x91] = I("\u0017-\u0002\n-", "gBrzT");
        ModelBakery.I[0xFD ^ 0x8A] = I("\u001b+:\u0003\u0019\u00165,\u000e/\u001d", "yGOfF");
        ModelBakery.I[0xDA ^ 0xA2] = I("\u000b\u000b'\u0010\u001f\u0007", "jgKyj");
        ModelBakery.I[0x5 ^ 0x7C] = I(" *\u0014<\u001b'+\b.", "HEaOo");
        ModelBakery.I[0x4D ^ 0x37] = I("+\u00126\u001d.,\u001b;2", "YwRBZ");
        ModelBakery.I[0x1E ^ 0x65] = I("\u001a9):\n\u0010\u0014<!\u0001\u001c;", "uKHTm");
        ModelBakery.I[0x3E ^ 0x42] = I("\u0005!>%\u0012-=\"=\u001e\u0002", "rIWQw");
        ModelBakery.I[0x44 ^ 0x39] = I("3\"\u0003867>\u0001:\u0019", "CKmSi");
        ModelBakery.I[0xD0 ^ 0xAE] = I("=5\u0015?\u0004\r)\u0011/\u0012+", "RMpFa");
        ModelBakery.I[65 + 97 - 131 + 96] = I("73\u0007\u0014\u0016\u001b4\u0004\u001b\u0011", "DGhzs");
        ModelBakery.I[20 + 6 + 90 + 12] = I("\u001c\u001b\u001f\u0006\n\u001b\u0015\u001f\u0007&\u001c\u0016\u0010\u0000", "ozqby");
        ModelBakery.I[66 + 1 + 9 + 53] = I(")<\f\u001a\u0006/ \u001a\u0017\u0004/\f\u001d\u0014\u000b(", "JSnxj");
        ModelBakery.I[106 + 83 - 118 + 59] = I("\u0005\u00181\u0014\u00188\u00194\u0016\u0011", "gjXws");
        ModelBakery.I[105 + 67 - 52 + 11] = I("\u001a\r+(\u00106\u001b6/\u0016\u0002&7*\u0014\u000b", "iyDFu");
        ModelBakery.I[2 + 73 + 36 + 21] = I(" \u000f\f\u0011\t<5\u001a\u000b\u0005-\u0001'\n\u0000/\b", "Njxyl");
        ModelBakery.I[107 + 28 - 122 + 120] = I(";\u0012\u000b\u0002%08\u0019\u001c0(", "JgjpQ");
        ModelBakery.I[4 + 35 - 35 + 130] = I("0-\u0013(\u0011#&\u0013\u0004\u0016-&\u0012(\u0011.)\u0015", "BHwwb");
        ModelBakery.I[119 + 62 - 148 + 102] = I("\u0003  \u0002.>?5\u0000,\u000f)%>\"\r-2\u0012", "aLAaE");
        ModelBakery.I[4 + 12 + 40 + 80] = I("?\r5-\u00179\t8\u001c\u0001)76\u001e\u0005>\u001b", "MhQrd");
        ModelBakery.I[119 + 70 - 172 + 120] = I(">:\u0014\u0004\b\u0006;\u0005\u0000\u000f7-\u0015>\u00015)\u0002\u0012", "YHqaf");
        ModelBakery.I[40 + 79 - 15 + 34] = I("4;54>\t:.\"98,>\u001c7:()0", "VIZCP");
        ModelBakery.I[19 + 23 + 66 + 31] = I("3\u0018\u0011,6\"\u0000\u0005 \u00074\u0010;.\u00050\u0007\u0017", "QtdIi");
        ModelBakery.I[134 + 122 - 152 + 36] = I("\u001a\u001b\u0006\u0001\u0014\u000f1\u0007\u0005\u0019\u0003\u0000\u0011\u0015'\r\u0002\u0015\u0002\u000b", "jntqx");
        ModelBakery.I[17 + 47 + 45 + 32] = I("!\u000b(#91\u0006($\b'\u0016\u0016*\n#\u0001:", "BrIMf");
        ModelBakery.I[64 + 14 - 10 + 74] = I("\u000b\n\u001f\u0001(\n<\u0000\u0003,\u0011\r\u0016\u0013\u0012\u001f\u000f\u0012\u0004>", "xcswM");
        ModelBakery.I[39 + 18 + 80 + 6] = I("\b\"'\u0000\u0015\u001c$'\u0010$\n4\u0019\u001e&\u000e#5", "oPFyJ");
        ModelBakery.I[47 + 97 - 42 + 42] = I("9\u000b\n\u0019(:\u0016\u0005\u001b\u0019,\u0006;\u0015\u001b(\u0011\u0017", "Ibdrw");
        ModelBakery.I[98 + 102 - 65 + 10] = I("\r#*2\t\u0012>&>8\u0004.\u00180:\u000094", "aJGWV");
        ModelBakery.I[130 + 125 - 176 + 67] = I("7\u0004<<\u00009>#$\u000e'\u000f540)\r1#\u001c", "NaPPo");
        ModelBakery.I[13 + 120 - 15 + 29] = I("6\u001d#\u0018.\u0005\u0016(\u0005?\u0005\u00070\u001134\u0011 /=6\u00157\u0003", "ZtDpZ");
        ModelBakery.I[38 + 28 - 52 + 134] = I(";;>\u0011#\";\u0006\u00079737\u0011)\t=5\u0015>%", "VZYtM");
        ModelBakery.I[64 + 20 - 27 + 92] = I("\u001a+\t*4\u0010\u0006\u001b02\u001c7\r \f\u00125\t7 ", "uYhDS");
        ModelBakery.I[116 + 19 - 13 + 28] = I("9;-\"-\u0011 07! 6 \t/\"27%", "NSDVH");
        ModelBakery.I[75 + 35 - 31 + 72] = I("\u0001=#$--$#$;\u0006,>\u0015-\u0015.", "rILJH");
        ModelBakery.I[147 + 14 - 115 + 106] = I("3\u0017\u001a\u000545\u000b\f\b65'\u0015\b6#\f\u001d\u0015\u00075\u001f\u001f", "PxxgX");
        ModelBakery.I[6 + 27 + 19 + 101] = I("$>\u001e\u0001*\b(\u0003\u0006,<\u0015\u001c\u0000!$>\u0014\u001d\u00102-\u0016", "WJqoO");
        ModelBakery.I[29 + 90 - 94 + 129] = I("95$\u001d\f\u000b8%\u0007\u0016?\u0005:\u0001\u001b'.2\u001c*1=0", "TZWnu");
        ModelBakery.I[151 + 100 - 162 + 66] = I("7472\u001a1\"\t3\u0003=%=\u000e\u001c;(%%\u0014&\u001936\u0016", "TFVQq");
        ModelBakery.I[141 + 92 - 133 + 56] = I("\u00019\u0002\u001d$\u000e4\u000f1#\u00108\b\u0005\u001e\u000f>\u0005\u001d5\u0007#4\u000b&\u0005", "bQknA");
        ModelBakery.I[144 + 151 - 182 + 44] = I("\u0006\u0010\u0000\u0014\u0004\u0017\u0016\u0006\u0019\n", "udoza");
        ModelBakery.I[32 + 9 + 16 + 101] = I("\u001c\u0004\u001f\u000b>.\u0018\u0018\u0017)\u0014\t\u001e\u0011$\u001a", "qklxG");
        ModelBakery.I[68 + 16 - 35 + 110] = I("98):\t?.\u0017*\u00165$-;\u00103)#", "ZJHYb");
        ModelBakery.I[44 + 71 - 99 + 144] = I("\u0011\u001d96\u000f\u001e\u00104\u001a\u0019\u0006\u001a> \b\u0000\u001c3.", "ruPEj");
        ModelBakery.I[54 + 40 - 46 + 113] = I("\u001c\u0007\")\u0019\u001f\u0007+", "sfIvj");
        ModelBakery.I[102 + 50 - 10 + 20] = I("#$82\"5\u000b9+ 2", "PTJGA");
        ModelBakery.I[0 + 91 - 41 + 113] = I(",:5\u0010\n\u0011 +\u0012\u0000", "NSGsb");
        ModelBakery.I[29 + 75 + 31 + 29] = I(":\u0010 \u000f\u001a5:=\u0004\u00172", "PeNhv");
        ModelBakery.I[61 + 13 + 27 + 64] = I("#\f\n;\u0007#0\u00184\u000f ", "BokXn");
        ModelBakery.I[72 + 44 - 75 + 125] = I("2;!\u0001\u00149;858:;1", "VZSjK");
        ModelBakery.I[111 + 163 - 240 + 133] = I("'>\r//!\"\u001b\"-!\u000e\u0018,/(", "DQoMC");
        ModelBakery.I[34 + 31 + 38 + 65] = I("\u0015 \u001e\u00070',\u0002\u0016+\u0014*\u001e\u0000&\u0016*2\u0003(\u0014#", "xOmtI");
        ModelBakery.I[139 + 50 - 178 + 158] = I("$\r\u0004\u001c\u0014\u001a\n\u001c\u0001\u0019&\u0017", "Ecrux");
        ModelBakery.I[78 + 94 - 131 + 129] = I("\u0006\u0017\u0013\n)8\n\t\n\"\u000f\r\t\u001a\u001a\u0003\u0018\b\u0002\"\u0002\u001d", "gyecE");
        ModelBakery.I[57 + 93 + 14 + 7] = I("#\u000b4#\u0014\u001d\u0013'8\u0001\u001d\u0001#'\u0019%\u0000&", "BeBJx");
        ModelBakery.I[164 + 38 - 170 + 140] = I("\u0012\u0005;\u00105\u0019/8\u000e.\u0000\u001b", "cpZbA");
        ModelBakery.I[104 + 150 - 125 + 44] = I("\u0007\n 9\u000e\b\u0007-\u0015\u001a\u0011\u0003;>\u0011;\u0000%%\b\u000f", "dbIJk");
        ModelBakery.I[121 + 144 - 108 + 17] = I("\u0017\u001d\u0016\u0000\u001c\u001c7\u0014\u001d\u0004\u0013\u0005\u0019", "fhwrh");
        ModelBakery.I[51 + 139 - 146 + 131] = I("\u000f\u001a\u000f632\u0005\u001a41\u0003\u0013\n\n0\f\u0004\n06\b\u0012164\f\u000f", "mvnUX");
        ModelBakery.I[161 + 166 - 233 + 82] = I("\u001d)\u000b\u0007\u001a\u001b-\u00066\f\u000b\u0013\u00079\u001b\u000b)\u0001=\r0/\u00039\u0010", "oLoXi");
        ModelBakery.I[27 + 46 - 13 + 117] = I("\u0001<\u001d\u0011;9=\f\u0015<\b+\u001c+=\u0007<\u001c\u0011;\u0003*'\u00179\u00077", "fNxtU");
        ModelBakery.I[22 + 69 - 76 + 163] = I("3\u0011\u0019<:\u000e\u0010\u0002*=?\u0006\u0012\u0014<0\u0011\u0012.:4\u0007)(80\u001a", "QcvKT");
        ModelBakery.I[4 + 86 - 49 + 138] = I("%\u0001<\u0002\u00194\u0019(\u000e(\"\t\u0016\u000f'5\t,\t##2*\u000b'>", "GmIgF");
        ModelBakery.I[112 + 31 - 13 + 50] = I("\u001d\u0017:4\u0002\b=;0\u000f\u0004\f- 1\u0005\u0003: \u000b\u0003\u0007,\u001b\r\u0001\u00031", "mbHDn");
        ModelBakery.I[176 + 38 - 56 + 23] = I("\u000e\u0012\n\t\u0013\u001e\u001f\n\u000e\"\b\u000f4\u000f-\u001f\u000f\u000e\t)\t4\b\u000b-\u0014", "mkkgL");
        ModelBakery.I[108 + 140 - 128 + 62] = I("\u000b\"82\f\n\u0014'0\b\u0011%1 6\u0010*& \f\u0016.0\u001b\n\u0014*-", "xKTDi");
        ModelBakery.I[99 + 49 - 0 + 35] = I("\u0013\u0006\u0013\u001a'\u0007\u0000\u0013\n\u0016\u0011\u0010-\u000b\u0019\u0006\u0010\u0017\r\u001d\u0010+\u0011\u000f\u0019\r", "ttrcx");
        ModelBakery.I[20 + 179 - 74 + 59] = I("\u001d0\u0019-:\u001e-\u0016/\u000b\b=(.\u0004\u001f=\u0012(\u0000\t\u0006\u0014*\u0004\u0014", "mYwFe");
        ModelBakery.I[135 + 44 - 79 + 85] = I("&1\u0018\u0007=9,\u0014\u000b\f/<*\n\u00038<\u0010\f\u0007.\u0007\u0016\u000e\u00033", "JXubb");
        ModelBakery.I[10 + 105 - 97 + 168] = I("-#\n\r5#\u0019\u0015\u0015;=(\u0003\u0005\u0005<'\u0014\u0005?:#\u0002>98'\u001f", "TFfaZ");
        ModelBakery.I[147 + 66 - 62 + 36] = I("\u001d&\r8\u0005.-\u0006%\u0014.<\u001e1\u0018\u001f*\u000e\u000f\u0019\u0010=\u000e5\u001f\u0014+53\u001d\u00106", "qOjPq");
        ModelBakery.I[68 + 174 - 172 + 118] = I(">*\u000e4\u0014'*6\"\u000e2\"\u00074\u001e\f#\b#\u001e6%\f5%0'\b(", "SKiQz");
        ModelBakery.I[172 + 111 - 281 + 187] = I("\u001d#8-!\u0017\u000e*7'\u001b?<'\u0019\u001a0+'#\u001c4=\u001c%\u001e0 ", "rQYCF");
        ModelBakery.I[42 + 140 - 14 + 22] = I(";1/\u0002\u0004\u0013*2\u0017\b\"<\")\t-+\"\u0013\u000f)=\u0019\u0015\r- ", "LYFva");
        ModelBakery.I[89 + 27 - 80 + 155] = I("\u000e-\"-(327/*\u0002$'\u0011$\u0000 0=\u001c\u001c -+", "lACNC");
        ModelBakery.I[169 + 89 - 136 + 70] = I("\u0005$2\u0016\u001c\u0003 ?'\n\u0013\u001e1%\u000e\u00042\t9\u000e\u0019$", "wAVIo");
        ModelBakery.I[22 + 104 - 8 + 75] = I("6\u0018\u0014\u0013=\u000e\u0019\u0005\u0017:?\u000f\u0015)4=\u000b\u0002\u0005\f!\u000b\u001f\u0013", "QjqvS");
        ModelBakery.I[119 + 146 - 87 + 16] = I("\u0012\u0019\u0003\u001a\u0014/\u0018\u0018\f\u0013\u001e\u000e\b2\u001d\u001c\n\u001f\u001e%\u0000\n\u0002\b", "pklmz");
        ModelBakery.I[161 + 150 - 252 + 136] = I("\u0015&\u001d\u0017\n\u0004>\t\u001b;\u0012.7\u00159\u00169\u001b-%\u0016$\r", "wJhrU");
        ModelBakery.I[158 + 156 - 154 + 36] = I("\u0003\r?<$\u0016'>8)\u001a\u0016((\u0017\u0014\u0014,?;,\b,\"-", "sxMLH");
        ModelBakery.I[21 + 36 + 36 + 104] = I("\r?\u0004\u001a2\u001d2\u0004\u001d\u0003\u000b\":\u0013\u0001\u000f5\u0016+\u001d\u000f(\u0000", "nFetm");
        ModelBakery.I[87 + 155 - 146 + 102] = I("\n9)06\u000b\u000f622\u0010> \"\f\u001e<$5 & $(6", "yPEFS");
        ModelBakery.I[117 + 75 - 171 + 178] = I(",\u000b\f\n\u00158\r\f\u001a$.\u001d2\u0014&*\n\u001e,:*\u0017\b", "KymsJ");
        ModelBakery.I[185 + 29 - 212 + 198] = I("$\u0013\u00188<'\u000e\u0017:\r1\u001e)4\u000f5\t\u0005\f\u00135\u0014\u0013", "TzvSc");
        ModelBakery.I[187 + 60 - 50 + 4] = I("(85$)7%9(\u0018!5\u0007&\u001a%\"+\u001e\u0006%?=", "DQXAv");
        ModelBakery.I[90 + 201 - 128 + 39] = I("5+\u0000*\u001e;\u0011\u001f2\u0010% \t\".+\"\r5\u0002\u0013>\r(\u0014", "LNlFq");
        ModelBakery.I[153 + 126 - 102 + 26] = I("\u001d\u000e,<\u001d.\u0005'!\f.\u0014?5\u0000\u001f\u0002/\u000b\u000e\u001d\u00068'6\u0001\u0006%1", "qgKTi");
        ModelBakery.I[167 + 175 - 325 + 187] = I("\t0%'/\u00100\u001d15\u00058,'%;6.#2\u0017\u000e2#/\u0001", "dQBBA");
        ModelBakery.I[184 + 111 - 236 + 146] = I("\u0004\u001a46\u0002\u000e7&,\u0004\u0002\u00060<:\f\u00044+\u00164\u001846\u0000", "khUXe");
        ModelBakery.I[173 + 108 - 125 + 50] = I("\u001c.?3\u000145\"&\r\u0005#2\u0018\u0003\u0007'%4;\u001b'8\"", "kFVGd");
        ModelBakery.I[59 + 66 - 96 + 178] = I("\u0012\u000f\u0006\u0002(\u00123\u000b\u0004 \u0005\t\u0014", "slgaA");
        ModelBakery.I[157 + 28 - 107 + 130] = I("&5\u0018>8-5\u0001\n\u000b'5\u001c0\u0014", "BTjUg");
        ModelBakery.I[171 + 130 - 222 + 130] = I("\u000f\u0013\n;\u0019\u000f/\u00077\u0017", "npkXp");
        ModelBakery.I[163 + 201 - 166 + 12] = I("#\n*\b\u001b(\n3<((\f", "GkXcD");
        ModelBakery.I[87 + 147 - 124 + 101] = I("8*\u0003>\u001e)*\u0003#\u0016", "HXjMs");
        ModelBakery.I[98 + 107 - 188 + 195] = I("\u0003\u0018\u0003\u001e&\u0012\u0018\u0003\u0003.,\b\u0018\u0004(\u0018\u0019", "sjjmK");
        ModelBakery.I[68 + 209 - 255 + 191] = I("\u001d9>\u0011'\t*%\t\u0015\u0018*%\u0014\u001d", "yXLzx");
        ModelBakery.I[168 + 139 - 203 + 110] = I("/!\u0002\u0012\u001c\u0012.\u0002\u0003\u0007(9", "MMcqw");
        ModelBakery.I[210 + 212 - 229 + 22] = I("*\n\u0006\b\u00069\u001d\u00122\u0011", "XobWe");
        ModelBakery.I[98 + 96 - 170 + 192] = I("\"+5<\n\u001a:1+\u0014 -", "EYPYd");
        ModelBakery.I[18 + 57 + 48 + 94] = I("\u00009\u0002\u001e\u0000=(\f\u001b\u001e\u0007?", "bKmin");
        ModelBakery.I[185 + 179 - 162 + 16] = I("\u001a)3\u0010\u0010\u001b$4\u0005*\f", "xEFuO");
        ModelBakery.I[176 + 51 - 29 + 21] = I("\u0018\u0019\u0002%\u0000\r3\u00134\u001e\u0018\t\u0004", "hlpUl");
        ModelBakery.I[18 + 130 + 20 + 52] = I("\u00062\u00186\u000e\u0006*\u000b(4\u0011", "eKyXQ");
        ModelBakery.I[44 + 149 - 56 + 84] = I("\"\u0018-2<#.\"%+!\u00145", "QqADY");
        ModelBakery.I[154 + 42 - 69 + 95] = I(".\b0-\u0018*\u001b#$\"=", "IzQTG");
        ModelBakery.I[178 + 9 - 157 + 193] = I("\u001d\u0004,26\u000e\f0)\f\u0019", "mmBYi");
        ModelBakery.I[54 + 170 - 122 + 122] = I("9\u001a\u0001\u0017\u00076\u0012\u001e\u0002=!", "UslrX");
        ModelBakery.I[203 + 223 - 290 + 89] = I(":\u0016\r%)4,\u0002(43\u0016\u0015", "CsaIF");
        ModelBakery.I[78 + 104 - 124 + 168] = I("?\u0006\u0006*\f\f\r\r7\u001d\f\f\u00000\b6\u001b", "SoaBx");
        ModelBakery.I[157 + 147 - 196 + 119] = I("(\f)7\u001f1\f\u00111\u00107\u001d+&", "EmNRq");
        ModelBakery.I[40 + 178 - 208 + 218] = I(":!\u001b\u0016\r0\f\u0019\u0019\u0018%6\u000e", "USzxj");
        ModelBakery.I[8 + 26 + 75 + 120] = I("$8:\u0011<\f32\u0017)6$", "SPSeY");
        ModelBakery.I[88 + 176 - 163 + 129] = I("\u0010\u0010\u000f\u000f-\f\u0012\u0004\u001b", "ceaiA");
        ModelBakery.I[183 + 137 - 206 + 117] = I("\u0017\u001a\u0011\b\u0002\u0003\u0002", "dccal");
        ModelBakery.I[178 + 151 - 269 + 172] = I(" \u0000%.<!07>17\u001c", "DoPLP");
        ModelBakery.I[110 + 200 - 279 + 202] = I("\n7#\n.\u000b\u00070\r0\u0000", "nXVhB");
        ModelBakery.I[90 + 143 - 167 + 168] = I("\u0010)\u0017\u0003\u000f\u0011\u0019\u0010\u000e\u0010\u0011", "tFbac");
        ModelBakery.I[8 + 58 + 52 + 117] = I("\u0005\u00127-8\u001c\u0012", "usRBV");
        ModelBakery.I[14 + 218 - 188 + 192] = I("\u0006#\u0001", "dLvvH");
        ModelBakery.I[72 + 54 - 111 + 222] = I(" 9\u0016'\u00117:\r\u0011\u000f%\tQ", "BVaxa");
        ModelBakery.I[133 + 229 - 127 + 3] = I("1\u0017\u0005<\u0013&\u0014\u001e\n\r4'C", "Sxrcc");
        ModelBakery.I[12 + 178 - 47 + 96] = I("1\u0017\u001e.\n&\u0014\u0005\u0018\u00144'[", "Sxiqz");
        ModelBakery.I[111 + 223 - 243 + 149] = I(",\f/\u001c", "OcNpw");
        ModelBakery.I[232 + 153 - 369 + 225] = I("\t\u0018\u0010\u001d\u0004\u0005\u0011\u001d", "jpqog");
        ModelBakery.I[194 + 118 - 194 + 124] = I("-\"\u0001\n-%,-\u0010+/", "KKrbD");
        ModelBakery.I[145 + 64 + 24 + 10] = I("7 \u001b+\f?.71\n5\u0016\u000b\"\u0016%", "QIhCe");
        ModelBakery.I[96 + 168 - 21 + 1] = I("+\u0015\u0015", "Hzqnr");
        ModelBakery.I[203 + 82 - 281 + 241] = I("$8:\u0006\u001f9", "WYVkp");
        ModelBakery.I[218 + 29 - 44 + 43] = I("\u0006.:8=\u0003+&'", "eBUOS");
        ModelBakery.I[220 + 231 - 447 + 243] = I("$\u00174<.&\u0004;)#", "TbRZK");
        ModelBakery.I[122 + 160 - 204 + 170] = I("0\u0016\u001c\"\u000e7&\u0010&\u000f", "SysIk");
        ModelBakery.I[56 + 246 - 174 + 121] = I("0$\t\u001f*7\u0014\u0015\u0015#>$\b", "SKftO");
        ModelBakery.I[212 + 159 - 219 + 98] = I("\f>$\u001e \u0004&\"*", "hGAAB");
        ModelBakery.I[122 + 65 + 2 + 62] = I("\u001e\n.\u001d\u0002\u001f\u0017", "zsKBp");
        ModelBakery.I[66 + 46 + 134 + 6] = I("\u0003\u0001#\u0015?\u0015\u001d#$", "gxFJX");
        ModelBakery.I[108 + 130 - 128 + 143] = I("\u0016\n\u0011\n8\u0000\u001c\u0003;", "rstUZ");
        ModelBakery.I[139 + 247 - 217 + 85] = I(".:\u00037.&6\u0003", "JCfhL");
        ModelBakery.I[212 + 202 - 359 + 200] = I("\"\u0012\u000e\u0006\u00193\u0019\u001b5\f", "FkkYi");
        ModelBakery.I[1 + 225 - 50 + 80] = I("\u001c-\"(0\u00015)", "xTGwS");
        ModelBakery.I[245 + 69 - 108 + 51] = I("=8\u000e&*0-\u001d\u001c+", "YAkyY");
        ModelBakery.I[146 + 47 - 41 + 106] = I(">\u000e\u001d7\u000b(\u0016\u0001", "Zwxhl");
        ModelBakery.I[70 + 202 - 101 + 88] = I("\u0013+\u00101:\u001e<\u001e", "wRunJ");
        ModelBakery.I[132 + 127 - 246 + 247] = I("(5\u0014&\t%!\u0014", "LLqye");
        ModelBakery.I[79 + 26 + 90 + 66] = I("\u000e?\r\u000b\u000b\u000f*\u0004;\u0005", "jFhTr");
        ModelBakery.I[87 + 40 - 2 + 137] = I("\u000e\u001d4\u0018(\u0003\u000393\u001b\b\b$\"", "jdQGD");
        ModelBakery.I[162 + 116 - 127 + 112] = I("'\u000b02)\"\u00150\u00030\"", "CrUmD");
        ModelBakery.I[27 + 60 + 82 + 95] = I(")\u000e#\u0006;?\u0016(>1", "MwFYT");
        ModelBakery.I[204 + 106 - 224 + 179] = I("\u001d\u001c\u0015\u001a\u001b\u0011\f\u0004 ", "yepEl");
        ModelBakery.I[101 + 157 - 90 + 98] = I("6<\u001b\u001c&1\f\u000b\u001a#:8\u000e\n&1", "TSohJ");
        ModelBakery.I[104 + 259 - 259 + 163] = I("\u001a.:\u001b\u0015\u001d\u001e=\u001f\u0015\u00192&", "xANoy");
        ModelBakery.I[83 + 89 + 18 + 78] = I("$<'\u0004\u001a\b$9\r\u001a2#=\u0006", "WWRhv");
        ModelBakery.I[177 + 251 - 184 + 25] = I("\u0003<-.\u0006/ 16\u0002\u0015%", "pWXBj");
        ModelBakery.I[135 + 101 - 198 + 232] = I("#?&?%\u000f.<>+91", "PTSSI");
        ModelBakery.I[142 + 19 - 123 + 233] = I("6\u0019\u0010\r\"\u001a\u0011\r\u0000<", "EreaN");
        ModelBakery.I[211 + 106 - 100 + 55] = I("6<\u000f)\u000e\u001a4\b \u000752\b", "EWzEb");
        ModelBakery.I[127 + 268 - 323 + 201] = I("$\b\u0000\u001a+.\u0007\b \u0012,\b\u001f ", "KikEM");
        ModelBakery.I[219 + 109 - 244 + 190] = I("=+'\u0018\u00027$/\"", "RJLGd");
        ModelBakery.I[213 + 149 - 133 + 46] = I("<68;\u0016<8!", "SWSdr");
        ModelBakery.I[1 + 76 - 61 + 260] = I("\u0004\u001c57x", "mhPZW");
        ModelBakery.I[245 + 105 - 139 + 66] = I("=\u0002\"\u0011<\u001e\fq\u000f:\u0014\u000e=B3\u001f\u0019kB", "pkQbU");
        ModelBakery.I[33 + 12 - 42 + 275] = I("\u0002\u0018E0\u0016%\u0010\r3\u0016(W\b(\u0017)\u001b\u0016g\u0015#\u0005_g", "LweGs");
        ModelBakery.I[175 + 270 - 305 + 139] = I("-\u001d\u001c\u0017!0\u001c\u0018\u000b", "DsjrO");
        ModelBakery.I[270 + 104 - 350 + 256] = I("\u0015>&\t\u000f60u\u0017\t<29Z\u00007%oZ", "XWUzf");
        ModelBakery.I[71 + 58 + 15 + 137] = I("\u000b\u001e*&!(\u0010y8'\"\u00125u.)\u0005cu", "FwYUH");
        ModelBakery.I[76 + 20 + 133 + 53] = I("\u001d\u00077\u0006!\u000e\n ", "mfErH");
        ModelBakery.I[61 + 210 + 9 + 3] = I("!8d8 \u001a3*<a\u000b>%!/Rv", "hVDHA");
        ModelBakery.I[237 + 280 - 510 + 277] = I("jQ4473\u001d$z\">Q-575Q,524\u001d{zq", "QqAZV");
        ModelBakery.I[276 + 66 - 126 + 69] = I("B", "eWobO");
        ModelBakery.I[259 + 249 - 341 + 119] = I("7 #\u001b\r$-4", "GAQod");
        ModelBakery.I[34 + 278 - 307 + 282] = I("=.#>\f> >\"", "PGPMe");
        ModelBakery.I[82 + 184 - 244 + 266] = I("\u0001+$\"\u0018\u0012&3", "qJVVq");
    }
    
    private void loadVariantItemModels() {
        this.loadVariants(this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
        final Map<ModelResourceLocation, ModelBlockDefinition.Variants> variants = this.variants;
        final ModelResourceLocation model_MISSING = ModelBakery.MODEL_MISSING;
        final String variant = ModelBakery.MODEL_MISSING.getVariant();
        final ModelBlockDefinition.Variant[] array = new ModelBlockDefinition.Variant[" ".length()];
        array["".length()] = new ModelBlockDefinition.Variant(new ResourceLocation(ModelBakery.MODEL_MISSING.getResourcePath()), ModelRotation.X0_Y0, "".length() != 0, " ".length());
        variants.put(model_MISSING, new ModelBlockDefinition.Variants(variant, Lists.newArrayList((Object[])array)));
        final ResourceLocation resourceLocation = new ResourceLocation(ModelBakery.I[0x54 ^ 0x4A]);
        final ModelBlockDefinition modelBlockDefinition = this.getModelBlockDefinition(resourceLocation);
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, ModelBakery.I[0x16 ^ 0x9]));
        this.registerVariant(modelBlockDefinition, new ModelResourceLocation(resourceLocation, ModelBakery.I[0x61 ^ 0x41]));
        this.loadVariantModels();
        this.loadItemModels();
    }
    
    static Map access$0(final ModelBakery modelBakery) {
        return modelBakery.sprites;
    }
    
    private Set<ResourceLocation> getTextureLocations(final ModelBlock modelBlock) {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<BlockPart> iterator = modelBlock.getElements().iterator();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Iterator<BlockPartFace> iterator2 = iterator.next().mapFaces.values().iterator();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (iterator2.hasNext()) {
                hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(iterator2.next().texture)));
            }
        }
        hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(ModelBakery.I[136 + 219 - 120 + 51])));
        return (Set<ResourceLocation>)hashSet;
    }
    
    private void loadVariants(final Collection<ModelResourceLocation> collection) {
        final Iterator<ModelResourceLocation> iterator = collection.iterator();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelResourceLocation modelResourceLocation = iterator.next();
            try {
                final ModelBlockDefinition modelBlockDefinition = this.getModelBlockDefinition(modelResourceLocation);
                try {
                    this.registerVariant(modelBlockDefinition, modelResourceLocation);
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                    continue;
                }
                catch (Exception ex2) {
                    ModelBakery.LOGGER.warn(ModelBakery.I[0x9D ^ 0xBC] + modelResourceLocation.getVariant() + ModelBakery.I[0x5 ^ 0x27] + modelResourceLocation);
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                    continue;
                }
            }
            catch (Exception ex) {
                ModelBakery.LOGGER.warn(ModelBakery.I[0xA4 ^ 0x87] + modelResourceLocation, (Throwable)ex);
            }
        }
    }
    
    private ModelBlock loadModel(final ResourceLocation resourceLocation) throws IOException {
        final String resourcePath = resourceLocation.getResourcePath();
        if (ModelBakery.I[0x5E ^ 0x70].equals(resourcePath)) {
            return ModelBakery.MODEL_GENERATED;
        }
        if (ModelBakery.I[0xBB ^ 0x94].equals(resourcePath)) {
            return ModelBakery.MODEL_COMPASS;
        }
        if (ModelBakery.I[0x3 ^ 0x33].equals(resourcePath)) {
            return ModelBakery.MODEL_CLOCK;
        }
        if (ModelBakery.I[0x5E ^ 0x6F].equals(resourcePath)) {
            return ModelBakery.MODEL_ENTITY;
        }
        Reader reader;
        if (resourcePath.startsWith(ModelBakery.I[0x65 ^ 0x57])) {
            final Object o = resourcePath.substring(ModelBakery.I[0x17 ^ 0x24].length());
            final String s = ModelBakery.BUILT_IN_MODELS.get(o);
            if (s == null) {
                throw new FileNotFoundException(resourceLocation.toString());
            }
            reader = new StringReader(s);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            final Object o = this.resourceManager.getResource(this.getModelLocation(resourceLocation));
            reader = new InputStreamReader(((IResource)o).getInputStream(), Charsets.UTF_8);
        }
        Object o;
        try {
            final ModelBlock deserialize = ModelBlock.deserialize(reader);
            deserialize.name = resourceLocation.toString();
            o = deserialize;
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        finally {
            reader.close();
        }
        reader.close();
        return (ModelBlock)o;
    }
    
    private void loadModels() {
        final ArrayDeque arrayDeque = Queues.newArrayDeque();
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<ResourceLocation> iterator = this.models.keySet().iterator();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ResourceLocation resourceLocation = iterator.next();
            hashSet.add(resourceLocation);
            final ResourceLocation parentLocation = this.models.get(resourceLocation).getParentLocation();
            if (parentLocation != null) {
                arrayDeque.add(parentLocation);
            }
        }
        "".length();
        if (false) {
            throw null;
        }
        while (!arrayDeque.isEmpty()) {
            final ResourceLocation resourceLocation2 = arrayDeque.pop();
            try {
                if (this.models.get(resourceLocation2) != null) {
                    "".length();
                    if (false) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final ModelBlock loadModel = this.loadModel(resourceLocation2);
                    this.models.put(resourceLocation2, loadModel);
                    final ResourceLocation parentLocation2 = loadModel.getParentLocation();
                    if (parentLocation2 != null && !hashSet.contains(parentLocation2)) {
                        arrayDeque.add(parentLocation2);
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                }
            }
            catch (Exception ex) {
                ModelBakery.LOGGER.warn(ModelBakery.I[173 + 40 - 35 + 105] + ModelBakery.JOINER.join((Iterable)this.getParentPath(resourceLocation2)) + ModelBakery.I[211 + 56 - 60 + 77] + resourceLocation2 + ModelBakery.I[238 + 243 - 442 + 246], (Throwable)ex);
            }
            hashSet.add(resourceLocation2);
        }
    }
    
    private void registerVariantNames() {
        final Map<Item, List<String>> variantNames = this.variantNames;
        final Item itemFromBlock = Item.getItemFromBlock(Blocks.stone);
        final String[] array = new String[0x40 ^ 0x47];
        array["".length()] = ModelBakery.I[0x95 ^ 0xAC];
        array[" ".length()] = ModelBakery.I[0x49 ^ 0x73];
        array["  ".length()] = ModelBakery.I[0x2F ^ 0x14];
        array["   ".length()] = ModelBakery.I[0x54 ^ 0x68];
        array[0x5F ^ 0x5B] = ModelBakery.I[0x2C ^ 0x11];
        array[0xA9 ^ 0xAC] = ModelBakery.I[0x29 ^ 0x17];
        array[0x6E ^ 0x68] = ModelBakery.I[0x50 ^ 0x6F];
        variantNames.put(itemFromBlock, Lists.newArrayList((Object[])array));
        final Map<Item, List<String>> variantNames2 = this.variantNames;
        final Item itemFromBlock2 = Item.getItemFromBlock(Blocks.dirt);
        final String[] array2 = new String["   ".length()];
        array2["".length()] = ModelBakery.I[0x3 ^ 0x43];
        array2[" ".length()] = ModelBakery.I[0xDB ^ 0x9A];
        array2["  ".length()] = ModelBakery.I[0x62 ^ 0x20];
        variantNames2.put(itemFromBlock2, Lists.newArrayList((Object[])array2));
        final Map<Item, List<String>> variantNames3 = this.variantNames;
        final Item itemFromBlock3 = Item.getItemFromBlock(Blocks.planks);
        final String[] array3 = new String[0x9B ^ 0x9D];
        array3["".length()] = ModelBakery.I[0x38 ^ 0x7B];
        array3[" ".length()] = ModelBakery.I[0xFD ^ 0xB9];
        array3["  ".length()] = ModelBakery.I[0xCA ^ 0x8F];
        array3["   ".length()] = ModelBakery.I[0x2D ^ 0x6B];
        array3[0x2F ^ 0x2B] = ModelBakery.I[0xFC ^ 0xBB];
        array3[0x77 ^ 0x72] = ModelBakery.I[0x6C ^ 0x24];
        variantNames3.put(itemFromBlock3, Lists.newArrayList((Object[])array3));
        final Map<Item, List<String>> variantNames4 = this.variantNames;
        final Item itemFromBlock4 = Item.getItemFromBlock(Blocks.sapling);
        final String[] array4 = new String[0xBE ^ 0xB8];
        array4["".length()] = ModelBakery.I[0x4F ^ 0x6];
        array4[" ".length()] = ModelBakery.I[0x76 ^ 0x3C];
        array4["  ".length()] = ModelBakery.I[0x52 ^ 0x19];
        array4["   ".length()] = ModelBakery.I[0x63 ^ 0x2F];
        array4[0x6 ^ 0x2] = ModelBakery.I[0x35 ^ 0x78];
        array4[0xB3 ^ 0xB6] = ModelBakery.I[0x68 ^ 0x26];
        variantNames4.put(itemFromBlock4, Lists.newArrayList((Object[])array4));
        final Map<Item, List<String>> variantNames5 = this.variantNames;
        final Item itemFromBlock5 = Item.getItemFromBlock(Blocks.sand);
        final String[] array5 = new String["  ".length()];
        array5["".length()] = ModelBakery.I[0x1C ^ 0x53];
        array5[" ".length()] = ModelBakery.I[0xCD ^ 0x9D];
        variantNames5.put(itemFromBlock5, Lists.newArrayList((Object[])array5));
        final Map<Item, List<String>> variantNames6 = this.variantNames;
        final Item itemFromBlock6 = Item.getItemFromBlock(Blocks.log);
        final String[] array6 = new String[0x79 ^ 0x7D];
        array6["".length()] = ModelBakery.I[0xC3 ^ 0x92];
        array6[" ".length()] = ModelBakery.I[0x8 ^ 0x5A];
        array6["  ".length()] = ModelBakery.I[0xC3 ^ 0x90];
        array6["   ".length()] = ModelBakery.I[0xF9 ^ 0xAD];
        variantNames6.put(itemFromBlock6, Lists.newArrayList((Object[])array6));
        final Map<Item, List<String>> variantNames7 = this.variantNames;
        final Item itemFromBlock7 = Item.getItemFromBlock(Blocks.leaves);
        final String[] array7 = new String[0xC7 ^ 0xC3];
        array7["".length()] = ModelBakery.I[0xFA ^ 0xAF];
        array7[" ".length()] = ModelBakery.I[0x4C ^ 0x1A];
        array7["  ".length()] = ModelBakery.I[0x71 ^ 0x26];
        array7["   ".length()] = ModelBakery.I[0xCC ^ 0x94];
        variantNames7.put(itemFromBlock7, Lists.newArrayList((Object[])array7));
        final Map<Item, List<String>> variantNames8 = this.variantNames;
        final Item itemFromBlock8 = Item.getItemFromBlock(Blocks.sponge);
        final String[] array8 = new String["  ".length()];
        array8["".length()] = ModelBakery.I[0x7D ^ 0x24];
        array8[" ".length()] = ModelBakery.I[0x30 ^ 0x6A];
        variantNames8.put(itemFromBlock8, Lists.newArrayList((Object[])array8));
        final Map<Item, List<String>> variantNames9 = this.variantNames;
        final Item itemFromBlock9 = Item.getItemFromBlock(Blocks.sandstone);
        final String[] array9 = new String["   ".length()];
        array9["".length()] = ModelBakery.I[0x5F ^ 0x4];
        array9[" ".length()] = ModelBakery.I[0x2B ^ 0x77];
        array9["  ".length()] = ModelBakery.I[0x65 ^ 0x38];
        variantNames9.put(itemFromBlock9, Lists.newArrayList((Object[])array9));
        final Map<Item, List<String>> variantNames10 = this.variantNames;
        final Item itemFromBlock10 = Item.getItemFromBlock(Blocks.red_sandstone);
        final String[] array10 = new String["   ".length()];
        array10["".length()] = ModelBakery.I[0x68 ^ 0x36];
        array10[" ".length()] = ModelBakery.I[0x11 ^ 0x4E];
        array10["  ".length()] = ModelBakery.I[0x2D ^ 0x4D];
        variantNames10.put(itemFromBlock10, Lists.newArrayList((Object[])array10));
        final Map<Item, List<String>> variantNames11 = this.variantNames;
        final Item itemFromBlock11 = Item.getItemFromBlock(Blocks.tallgrass);
        final String[] array11 = new String["   ".length()];
        array11["".length()] = ModelBakery.I[0x1E ^ 0x7F];
        array11[" ".length()] = ModelBakery.I[0x59 ^ 0x3B];
        array11["  ".length()] = ModelBakery.I[0x71 ^ 0x12];
        variantNames11.put(itemFromBlock11, Lists.newArrayList((Object[])array11));
        final Map<Item, List<String>> variantNames12 = this.variantNames;
        final Item itemFromBlock12 = Item.getItemFromBlock(Blocks.deadbush);
        final String[] array12 = new String[" ".length()];
        array12["".length()] = ModelBakery.I[0xFF ^ 0x9B];
        variantNames12.put(itemFromBlock12, Lists.newArrayList((Object[])array12));
        final Map<Item, List<String>> variantNames13 = this.variantNames;
        final Item itemFromBlock13 = Item.getItemFromBlock(Blocks.wool);
        final String[] array13 = new String[0xD3 ^ 0xC3];
        array13["".length()] = ModelBakery.I[0xD9 ^ 0xBC];
        array13[" ".length()] = ModelBakery.I[0x49 ^ 0x2F];
        array13["  ".length()] = ModelBakery.I[0x17 ^ 0x70];
        array13["   ".length()] = ModelBakery.I[0x32 ^ 0x5A];
        array13[0x7A ^ 0x7E] = ModelBakery.I[0x6F ^ 0x6];
        array13[0x66 ^ 0x63] = ModelBakery.I[0xD9 ^ 0xB3];
        array13[0x8C ^ 0x8A] = ModelBakery.I[0x76 ^ 0x1D];
        array13[0x96 ^ 0x91] = ModelBakery.I[0x39 ^ 0x55];
        array13[0x64 ^ 0x6C] = ModelBakery.I[0xF4 ^ 0x99];
        array13[0xAD ^ 0xA4] = ModelBakery.I[0x29 ^ 0x47];
        array13[0x5B ^ 0x51] = ModelBakery.I[0x67 ^ 0x8];
        array13[0x44 ^ 0x4F] = ModelBakery.I[0x76 ^ 0x6];
        array13[0x81 ^ 0x8D] = ModelBakery.I[0x48 ^ 0x39];
        array13[0xB0 ^ 0xBD] = ModelBakery.I[0x44 ^ 0x36];
        array13[0xB4 ^ 0xBA] = ModelBakery.I[0x43 ^ 0x30];
        array13[0xB5 ^ 0xBA] = ModelBakery.I[0x23 ^ 0x57];
        variantNames13.put(itemFromBlock13, Lists.newArrayList((Object[])array13));
        final Map<Item, List<String>> variantNames14 = this.variantNames;
        final Item itemFromBlock14 = Item.getItemFromBlock(Blocks.yellow_flower);
        final String[] array14 = new String[" ".length()];
        array14["".length()] = ModelBakery.I[0x38 ^ 0x4D];
        variantNames14.put(itemFromBlock14, Lists.newArrayList((Object[])array14));
        final Map<Item, List<String>> variantNames15 = this.variantNames;
        final Item itemFromBlock15 = Item.getItemFromBlock(Blocks.red_flower);
        final String[] array15 = new String[0x93 ^ 0x9A];
        array15["".length()] = ModelBakery.I[0x7F ^ 0x9];
        array15[" ".length()] = ModelBakery.I[0xB4 ^ 0xC3];
        array15["  ".length()] = ModelBakery.I[0x2A ^ 0x52];
        array15["   ".length()] = ModelBakery.I[0x18 ^ 0x61];
        array15[0xA9 ^ 0xAD] = ModelBakery.I[0xC8 ^ 0xB2];
        array15[0x12 ^ 0x17] = ModelBakery.I[0xC7 ^ 0xBC];
        array15[0x9A ^ 0x9C] = ModelBakery.I[0xD7 ^ 0xAB];
        array15[0x21 ^ 0x26] = ModelBakery.I[0xFA ^ 0x87];
        array15[0x2C ^ 0x24] = ModelBakery.I[0xF9 ^ 0x87];
        variantNames15.put(itemFromBlock15, Lists.newArrayList((Object[])array15));
        final Map<Item, List<String>> variantNames16 = this.variantNames;
        final Item itemFromBlock16 = Item.getItemFromBlock(Blocks.stone_slab);
        final String[] array16 = new String[0x9B ^ 0x9C];
        array16["".length()] = ModelBakery.I[29 + 48 - 14 + 64];
        array16[" ".length()] = ModelBakery.I[95 + 30 - 62 + 65];
        array16["  ".length()] = ModelBakery.I[1 + 72 - 7 + 63];
        array16["   ".length()] = ModelBakery.I[58 + 60 - 103 + 115];
        array16[0x1A ^ 0x1E] = ModelBakery.I[74 + 40 - 19 + 36];
        array16[0xA5 ^ 0xA0] = ModelBakery.I[95 + 13 - 50 + 74];
        array16[0x9A ^ 0x9C] = ModelBakery.I[5 + 107 - 39 + 60];
        variantNames16.put(itemFromBlock16, Lists.newArrayList((Object[])array16));
        final Map<Item, List<String>> variantNames17 = this.variantNames;
        final Item itemFromBlock17 = Item.getItemFromBlock(Blocks.stone_slab2);
        final String[] array17 = new String[" ".length()];
        array17["".length()] = ModelBakery.I[6 + 14 + 65 + 49];
        variantNames17.put(itemFromBlock17, Lists.newArrayList((Object[])array17));
        final Map<Item, List<String>> variantNames18 = this.variantNames;
        final Item itemFromBlock18 = Item.getItemFromBlock(Blocks.stained_glass);
        final String[] array18 = new String[0x56 ^ 0x46];
        array18["".length()] = ModelBakery.I[80 + 133 - 147 + 69];
        array18[" ".length()] = ModelBakery.I[61 + 67 - 24 + 32];
        array18["  ".length()] = ModelBakery.I[97 + 46 - 62 + 56];
        array18["   ".length()] = ModelBakery.I[59 + 125 - 138 + 92];
        array18[0x6E ^ 0x6A] = ModelBakery.I[83 + 52 - 121 + 125];
        array18[0x19 ^ 0x1C] = ModelBakery.I[23 + 128 - 69 + 58];
        array18[0x7E ^ 0x78] = ModelBakery.I[5 + 102 + 4 + 30];
        array18[0x33 ^ 0x34] = ModelBakery.I[7 + 112 + 3 + 20];
        array18[0x77 ^ 0x7F] = ModelBakery.I[36 + 41 - 58 + 124];
        array18[0x2E ^ 0x27] = ModelBakery.I[49 + 20 - 21 + 96];
        array18[0x12 ^ 0x18] = ModelBakery.I[41 + 85 - 108 + 127];
        array18[0x56 ^ 0x5D] = ModelBakery.I[2 + 31 + 80 + 33];
        array18[0x7A ^ 0x76] = ModelBakery.I[46 + 22 - 8 + 87];
        array18[0x4B ^ 0x46] = ModelBakery.I[7 + 76 - 64 + 129];
        array18[0x31 ^ 0x3F] = ModelBakery.I[51 + 43 - 75 + 130];
        array18[0x33 ^ 0x3C] = ModelBakery.I[135 + 119 - 249 + 145];
        variantNames18.put(itemFromBlock18, Lists.newArrayList((Object[])array18));
        final Map<Item, List<String>> variantNames19 = this.variantNames;
        final Item itemFromBlock19 = Item.getItemFromBlock(Blocks.monster_egg);
        final String[] array19 = new String[0x8D ^ 0x8B];
        array19["".length()] = ModelBakery.I[94 + 143 - 107 + 21];
        array19[" ".length()] = ModelBakery.I[126 + 10 - 112 + 128];
        array19["  ".length()] = ModelBakery.I[9 + 41 - 42 + 145];
        array19["   ".length()] = ModelBakery.I[107 + 7 - 7 + 47];
        array19[0x4D ^ 0x49] = ModelBakery.I[98 + 121 - 106 + 42];
        array19[0x26 ^ 0x23] = ModelBakery.I[133 + 27 - 143 + 139];
        variantNames19.put(itemFromBlock19, Lists.newArrayList((Object[])array19));
        final Map<Item, List<String>> variantNames20 = this.variantNames;
        final Item itemFromBlock20 = Item.getItemFromBlock(Blocks.stonebrick);
        final String[] array20 = new String[0x4A ^ 0x4E];
        array20["".length()] = ModelBakery.I[34 + 38 - 70 + 155];
        array20[" ".length()] = ModelBakery.I[48 + 106 - 70 + 74];
        array20["  ".length()] = ModelBakery.I[123 + 154 - 219 + 101];
        array20["   ".length()] = ModelBakery.I[95 + 71 - 29 + 23];
        variantNames20.put(itemFromBlock20, Lists.newArrayList((Object[])array20));
        final Map<Item, List<String>> variantNames21 = this.variantNames;
        final Item itemFromBlock21 = Item.getItemFromBlock(Blocks.wooden_slab);
        final String[] array21 = new String[0xC ^ 0xA];
        array21["".length()] = ModelBakery.I[21 + 21 - 4 + 123];
        array21[" ".length()] = ModelBakery.I[35 + 68 - 41 + 100];
        array21["  ".length()] = ModelBakery.I[146 + 34 - 174 + 157];
        array21["   ".length()] = ModelBakery.I[40 + 3 + 17 + 104];
        array21[0x99 ^ 0x9D] = ModelBakery.I[86 + 152 - 168 + 95];
        array21[0x44 ^ 0x41] = ModelBakery.I[3 + 155 - 129 + 137];
        variantNames21.put(itemFromBlock21, Lists.newArrayList((Object[])array21));
        final Map<Item, List<String>> variantNames22 = this.variantNames;
        final Item itemFromBlock22 = Item.getItemFromBlock(Blocks.cobblestone_wall);
        final String[] array22 = new String["  ".length()];
        array22["".length()] = ModelBakery.I[114 + 11 - 82 + 124];
        array22[" ".length()] = ModelBakery.I[121 + 2 + 30 + 15];
        variantNames22.put(itemFromBlock22, Lists.newArrayList((Object[])array22));
        final Map<Item, List<String>> variantNames23 = this.variantNames;
        final Item itemFromBlock23 = Item.getItemFromBlock(Blocks.anvil);
        final String[] array23 = new String["   ".length()];
        array23["".length()] = ModelBakery.I[72 + 108 - 138 + 127];
        array23[" ".length()] = ModelBakery.I[131 + 50 - 152 + 141];
        array23["  ".length()] = ModelBakery.I[2 + 129 - 95 + 135];
        variantNames23.put(itemFromBlock23, Lists.newArrayList((Object[])array23));
        final Map<Item, List<String>> variantNames24 = this.variantNames;
        final Item itemFromBlock24 = Item.getItemFromBlock(Blocks.quartz_block);
        final String[] array24 = new String["   ".length()];
        array24["".length()] = ModelBakery.I[5 + 19 + 117 + 31];
        array24[" ".length()] = ModelBakery.I[31 + 139 - 83 + 86];
        array24["  ".length()] = ModelBakery.I[147 + 49 - 25 + 3];
        variantNames24.put(itemFromBlock24, Lists.newArrayList((Object[])array24));
        final Map<Item, List<String>> variantNames25 = this.variantNames;
        final Item itemFromBlock25 = Item.getItemFromBlock(Blocks.stained_hardened_clay);
        final String[] array25 = new String[0xB0 ^ 0xA0];
        array25["".length()] = ModelBakery.I[55 + 96 - 28 + 52];
        array25[" ".length()] = ModelBakery.I[169 + 168 - 268 + 107];
        array25["  ".length()] = ModelBakery.I[42 + 59 - 35 + 111];
        array25["   ".length()] = ModelBakery.I[104 + 164 - 221 + 131];
        array25[0x96 ^ 0x92] = ModelBakery.I[164 + 15 - 25 + 25];
        array25[0xAE ^ 0xAB] = ModelBakery.I[101 + 59 - 40 + 60];
        array25[0x82 ^ 0x84] = ModelBakery.I[135 + 83 - 147 + 110];
        array25[0x32 ^ 0x35] = ModelBakery.I[36 + 56 - 23 + 113];
        array25[0x16 ^ 0x1E] = ModelBakery.I[30 + 50 - 18 + 121];
        array25[0x30 ^ 0x39] = ModelBakery.I[147 + 34 - 39 + 42];
        array25[0x72 ^ 0x78] = ModelBakery.I[123 + 20 - 106 + 148];
        array25[0x5 ^ 0xE] = ModelBakery.I[176 + 66 - 149 + 93];
        array25[0x1 ^ 0xD] = ModelBakery.I[80 + 79 - 144 + 172];
        array25[0x6E ^ 0x63] = ModelBakery.I[167 + 182 - 328 + 167];
        array25[0x9F ^ 0x91] = ModelBakery.I[168 + 181 - 253 + 93];
        array25[0x84 ^ 0x8B] = ModelBakery.I[23 + 147 + 9 + 11];
        variantNames25.put(itemFromBlock25, Lists.newArrayList((Object[])array25));
        final Map<Item, List<String>> variantNames26 = this.variantNames;
        final Item itemFromBlock26 = Item.getItemFromBlock(Blocks.stained_glass_pane);
        final String[] array26 = new String[0x85 ^ 0x95];
        array26["".length()] = ModelBakery.I[188 + 13 - 130 + 120];
        array26[" ".length()] = ModelBakery.I[167 + 145 - 258 + 138];
        array26["  ".length()] = ModelBakery.I[52 + 16 + 99 + 26];
        array26["   ".length()] = ModelBakery.I[148 + 186 - 242 + 102];
        array26[0x77 ^ 0x73] = ModelBakery.I[149 + 125 - 213 + 134];
        array26[0x8E ^ 0x8B] = ModelBakery.I[178 + 12 - 173 + 179];
        array26[0x4A ^ 0x4C] = ModelBakery.I[36 + 130 + 30 + 1];
        array26[0xAC ^ 0xAB] = ModelBakery.I[12 + 177 - 183 + 192];
        array26[0x22 ^ 0x2A] = ModelBakery.I[146 + 142 - 151 + 62];
        array26[0x71 ^ 0x78] = ModelBakery.I[29 + 48 + 69 + 54];
        array26[0xCC ^ 0xC6] = ModelBakery.I[179 + 1 - 130 + 151];
        array26[0x59 ^ 0x52] = ModelBakery.I[156 + 18 - 13 + 41];
        array26[0x7C ^ 0x70] = ModelBakery.I[90 + 187 - 106 + 32];
        array26[0x91 ^ 0x9C] = ModelBakery.I[82 + 138 - 142 + 126];
        array26[0x13 ^ 0x1D] = ModelBakery.I[33 + 55 - 8 + 125];
        array26[0xC ^ 0x3] = ModelBakery.I[183 + 12 - 65 + 76];
        variantNames26.put(itemFromBlock26, Lists.newArrayList((Object[])array26));
        final Map<Item, List<String>> variantNames27 = this.variantNames;
        final Item itemFromBlock27 = Item.getItemFromBlock(Blocks.leaves2);
        final String[] array27 = new String["  ".length()];
        array27["".length()] = ModelBakery.I[56 + 162 - 94 + 83];
        array27[" ".length()] = ModelBakery.I[202 + 77 - 263 + 192];
        variantNames27.put(itemFromBlock27, Lists.newArrayList((Object[])array27));
        final Map<Item, List<String>> variantNames28 = this.variantNames;
        final Item itemFromBlock28 = Item.getItemFromBlock(Blocks.log2);
        final String[] array28 = new String["  ".length()];
        array28["".length()] = ModelBakery.I[124 + 119 - 139 + 105];
        array28[" ".length()] = ModelBakery.I[127 + 60 - 151 + 174];
        variantNames28.put(itemFromBlock28, Lists.newArrayList((Object[])array28));
        final Map<Item, List<String>> variantNames29 = this.variantNames;
        final Item itemFromBlock29 = Item.getItemFromBlock(Blocks.prismarine);
        final String[] array29 = new String["   ".length()];
        array29["".length()] = ModelBakery.I[30 + 129 - 149 + 201];
        array29[" ".length()] = ModelBakery.I[173 + 109 - 254 + 184];
        array29["  ".length()] = ModelBakery.I[200 + 158 - 168 + 23];
        variantNames29.put(itemFromBlock29, Lists.newArrayList((Object[])array29));
        final Map<Item, List<String>> variantNames30 = this.variantNames;
        final Item itemFromBlock30 = Item.getItemFromBlock(Blocks.carpet);
        final String[] array30 = new String[0x98 ^ 0x88];
        array30["".length()] = ModelBakery.I[112 + 119 - 71 + 54];
        array30[" ".length()] = ModelBakery.I[152 + 180 - 262 + 145];
        array30["  ".length()] = ModelBakery.I[184 + 46 - 166 + 152];
        array30["   ".length()] = ModelBakery.I[10 + 122 + 67 + 18];
        array30[0x3D ^ 0x39] = ModelBakery.I[115 + 95 - 5 + 13];
        array30[0xB6 ^ 0xB3] = ModelBakery.I[51 + 164 - 156 + 160];
        array30[0x24 ^ 0x22] = ModelBakery.I[96 + 192 - 84 + 16];
        array30[0xC0 ^ 0xC7] = ModelBakery.I[8 + 101 - 37 + 149];
        array30[0xA ^ 0x2] = ModelBakery.I[27 + 158 + 5 + 32];
        array30[0xBB ^ 0xB2] = ModelBakery.I[9 + 55 + 141 + 18];
        array30[0xBE ^ 0xB4] = ModelBakery.I[175 + 21 - 44 + 72];
        array30[0x81 ^ 0x8A] = ModelBakery.I[119 + 6 - 68 + 168];
        array30[0xCE ^ 0xC2] = ModelBakery.I[61 + 142 - 39 + 62];
        array30[0x15 ^ 0x18] = ModelBakery.I[141 + 146 - 214 + 154];
        array30[0x78 ^ 0x76] = ModelBakery.I[10 + 223 - 33 + 28];
        array30[0x80 ^ 0x8F] = ModelBakery.I[197 + 169 - 210 + 73];
        variantNames30.put(itemFromBlock30, Lists.newArrayList((Object[])array30));
        final Map<Item, List<String>> variantNames31 = this.variantNames;
        final Item itemFromBlock31 = Item.getItemFromBlock(Blocks.double_plant);
        final String[] array31 = new String[0x6D ^ 0x6B];
        array31["".length()] = ModelBakery.I[150 + 199 - 185 + 66];
        array31[" ".length()] = ModelBakery.I[136 + 207 - 256 + 144];
        array31["  ".length()] = ModelBakery.I[133 + 200 - 306 + 205];
        array31["   ".length()] = ModelBakery.I[1 + 161 - 61 + 132];
        array31[0x19 ^ 0x1D] = ModelBakery.I[58 + 175 - 65 + 66];
        array31[0x66 ^ 0x63] = ModelBakery.I[86 + 170 - 87 + 66];
        variantNames31.put(itemFromBlock31, Lists.newArrayList((Object[])array31));
        final Map<Item, List<String>> variantNames32 = this.variantNames;
        final ItemBow bow = Items.bow;
        final String[] array32 = new String[0x4A ^ 0x4E];
        array32["".length()] = ModelBakery.I[169 + 225 - 317 + 159];
        array32[" ".length()] = ModelBakery.I[84 + 169 - 51 + 35];
        array32["  ".length()] = ModelBakery.I[182 + 50 - 90 + 96];
        array32["   ".length()] = ModelBakery.I[73 + 235 - 202 + 133];
        variantNames32.put(bow, Lists.newArrayList((Object[])array32));
        final Map<Item, List<String>> variantNames33 = this.variantNames;
        final Item coal = Items.coal;
        final String[] array33 = new String["  ".length()];
        array33["".length()] = ModelBakery.I[10 + 166 - 22 + 86];
        array33[" ".length()] = ModelBakery.I[28 + 194 - 0 + 19];
        variantNames33.put(coal, Lists.newArrayList((Object[])array33));
        final Map<Item, List<String>> variantNames34 = this.variantNames;
        final ItemFishingRod fishing_rod = Items.fishing_rod;
        final String[] array34 = new String["  ".length()];
        array34["".length()] = ModelBakery.I[119 + 34 + 26 + 63];
        array34[" ".length()] = ModelBakery.I[174 + 66 - 54 + 57];
        variantNames34.put(fishing_rod, Lists.newArrayList((Object[])array34));
        final Map<Item, List<String>> variantNames35 = this.variantNames;
        final Item fish = Items.fish;
        final String[] array35 = new String[0x33 ^ 0x37];
        array35["".length()] = ModelBakery.I[57 + 135 - 162 + 214];
        array35[" ".length()] = ModelBakery.I[33 + 226 - 110 + 96];
        array35["  ".length()] = ModelBakery.I[169 + 182 - 173 + 68];
        array35["   ".length()] = ModelBakery.I[55 + 107 - 143 + 228];
        variantNames35.put(fish, Lists.newArrayList((Object[])array35));
        final Map<Item, List<String>> variantNames36 = this.variantNames;
        final Item cooked_fish = Items.cooked_fish;
        final String[] array36 = new String["  ".length()];
        array36["".length()] = ModelBakery.I[30 + 227 - 94 + 85];
        array36[" ".length()] = ModelBakery.I[63 + 222 - 275 + 239];
        variantNames36.put(cooked_fish, Lists.newArrayList((Object[])array36));
        final Map<Item, List<String>> variantNames37 = this.variantNames;
        final Item dye = Items.dye;
        final String[] array37 = new String[0x94 ^ 0x84];
        array37["".length()] = ModelBakery.I[70 + 119 - 50 + 111];
        array37[" ".length()] = ModelBakery.I[80 + 26 + 12 + 133];
        array37["  ".length()] = ModelBakery.I[145 + 41 - 134 + 200];
        array37["   ".length()] = ModelBakery.I[231 + 153 - 253 + 122];
        array37[0x33 ^ 0x37] = ModelBakery.I[91 + 13 + 11 + 139];
        array37[0xB ^ 0xE] = ModelBakery.I[47 + 226 - 138 + 120];
        array37[0xF ^ 0x9] = ModelBakery.I[31 + 124 - 115 + 216];
        array37[0x88 ^ 0x8F] = ModelBakery.I[29 + 55 + 38 + 135];
        array37[0x5F ^ 0x57] = ModelBakery.I[73 + 115 - 86 + 156];
        array37[0xCF ^ 0xC6] = ModelBakery.I[183 + 133 - 97 + 40];
        array37[0xAF ^ 0xA5] = ModelBakery.I[63 + 246 - 144 + 95];
        array37[0x54 ^ 0x5F] = ModelBakery.I[223 + 73 - 151 + 116];
        array37[0x94 ^ 0x98] = ModelBakery.I[244 + 250 - 465 + 233];
        array37[0x7C ^ 0x71] = ModelBakery.I[139 + 47 - 25 + 102];
        array37[0xA9 ^ 0xA7] = ModelBakery.I[96 + 88 - 152 + 232];
        array37[0x12 ^ 0x1D] = ModelBakery.I[215 + 262 - 395 + 183];
        variantNames37.put(dye, Lists.newArrayList((Object[])array37));
        final Map<Item, List<String>> variantNames38 = this.variantNames;
        final ItemPotion potionitem = Items.potionitem;
        final String[] array38 = new String["  ".length()];
        array38["".length()] = ModelBakery.I[116 + 169 - 70 + 51];
        array38[" ".length()] = ModelBakery.I[203 + 245 - 425 + 244];
        variantNames38.put(potionitem, Lists.newArrayList((Object[])array38));
        final Map<Item, List<String>> variantNames39 = this.variantNames;
        final Item skull = Items.skull;
        final String[] array39 = new String[0x2F ^ 0x2A];
        array39["".length()] = ModelBakery.I[180 + 86 - 186 + 188];
        array39[" ".length()] = ModelBakery.I[136 + 235 - 289 + 187];
        array39["  ".length()] = ModelBakery.I[76 + 114 + 29 + 51];
        array39["   ".length()] = ModelBakery.I[253 + 17 - 87 + 88];
        array39[0x12 ^ 0x16] = ModelBakery.I[250 + 123 - 347 + 246];
        variantNames39.put(skull, Lists.newArrayList((Object[])array39));
        final Map<Item, List<String>> variantNames40 = this.variantNames;
        final Item itemFromBlock32 = Item.getItemFromBlock(Blocks.oak_fence_gate);
        final String[] array40 = new String[" ".length()];
        array40["".length()] = ModelBakery.I[120 + 100 + 12 + 41];
        variantNames40.put(itemFromBlock32, Lists.newArrayList((Object[])array40));
        final Map<Item, List<String>> variantNames41 = this.variantNames;
        final Item itemFromBlock33 = Item.getItemFromBlock(Blocks.oak_fence);
        final String[] array41 = new String[" ".length()];
        array41["".length()] = ModelBakery.I[22 + 61 - 1 + 192];
        variantNames41.put(itemFromBlock33, Lists.newArrayList((Object[])array41));
        final Map<Item, List<String>> variantNames42 = this.variantNames;
        final Item oak_door = Items.oak_door;
        final String[] array42 = new String[" ".length()];
        array42["".length()] = ModelBakery.I[195 + 164 - 312 + 228];
        variantNames42.put(oak_door, Lists.newArrayList((Object[])array42));
    }
    
    private boolean isCustomRenderer(final ModelBlock modelBlock) {
        if (modelBlock == null) {
            return "".length() != 0;
        }
        if (modelBlock.getRootModel() == ModelBakery.MODEL_ENTITY) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private ModelBlockDefinition getModelBlockDefinition(final ResourceLocation resourceLocation) {
        final ResourceLocation blockStateLocation = this.getBlockStateLocation(resourceLocation);
        ModelBlockDefinition modelBlockDefinition = this.blockDefinitions.get(blockStateLocation);
        if (modelBlockDefinition == null) {
            final ArrayList arrayList = Lists.newArrayList();
            try {
                final Iterator<IResource> iterator = this.resourceManager.getAllResources(blockStateLocation).iterator();
                "".length();
                if (4 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final IResource resource = iterator.next();
                    InputStream inputStream = null;
                    try {
                        inputStream = resource.getInputStream();
                        arrayList.add(ModelBlockDefinition.parseFromReader(new InputStreamReader(inputStream, Charsets.UTF_8)));
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        throw new RuntimeException(ModelBakery.I[0xE3 ^ 0xC7] + resourceLocation + ModelBakery.I[0x1F ^ 0x3A] + resource.getResourceLocation() + ModelBakery.I[0x84 ^ 0xA2] + resource.getResourcePackName() + ModelBakery.I[0x58 ^ 0x7F], ex);
                    }
                    finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                    IOUtils.closeQuietly(inputStream);
                }
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (IOException ex2) {
                throw new RuntimeException(ModelBakery.I[0x24 ^ 0xC] + blockStateLocation.toString(), ex2);
            }
            modelBlockDefinition = new ModelBlockDefinition((List<ModelBlockDefinition>)arrayList);
            this.blockDefinitions.put(blockStateLocation, modelBlockDefinition);
        }
        return modelBlockDefinition;
    }
    
    private BakedQuad makeBakedQuad(final BlockPart blockPart, final BlockPartFace blockPartFace, final TextureAtlasSprite textureAtlasSprite, final EnumFacing enumFacing, final ModelRotation modelRotation, final boolean b) {
        return this.faceBakery.makeBakedQuad(blockPart.positionFrom, blockPart.positionTo, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, blockPart.partRotation, b, blockPart.shade);
    }
    
    private List<ResourceLocation> getParentPath(final ResourceLocation resourceLocation) {
        final ResourceLocation[] array = new ResourceLocation[" ".length()];
        array["".length()] = resourceLocation;
        final ArrayList arrayList = Lists.newArrayList((Object[])array);
        ResourceLocation parentLocation = resourceLocation;
        "".length();
        if (1 == -1) {
            throw null;
        }
        while ((parentLocation = this.getParentLocation(parentLocation)) != null) {
            arrayList.add("".length(), parentLocation);
        }
        return (List<ResourceLocation>)arrayList;
    }
    
    private boolean hasItemModel(final ModelBlock modelBlock) {
        if (modelBlock == null) {
            return "".length() != 0;
        }
        final ModelBlock rootModel = modelBlock.getRootModel();
        if (rootModel != ModelBakery.MODEL_GENERATED && rootModel != ModelBakery.MODEL_COMPASS && rootModel != ModelBakery.MODEL_CLOCK) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private IBakedModel bakeModel(final ModelBlock modelBlock, final ModelRotation modelRotation, final boolean b) {
        final SimpleBakedModel.Builder setTexture = new SimpleBakedModel.Builder(modelBlock).setTexture(this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName(ModelBakery.I[260 + 179 - 423 + 266]))));
        final Iterator<BlockPart> iterator = modelBlock.getElements().iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPart blockPart = iterator.next();
            final Iterator<EnumFacing> iterator2 = blockPart.mapFaces.keySet().iterator();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final EnumFacing enumFacing = iterator2.next();
                final BlockPartFace blockPartFace = blockPart.mapFaces.get(enumFacing);
                final TextureAtlasSprite textureAtlasSprite = this.sprites.get(new ResourceLocation(modelBlock.resolveTextureName(blockPartFace.texture)));
                if (blockPartFace.cullFace == null) {
                    setTexture.addGeneralQuad(this.makeBakedQuad(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    setTexture.addFaceQuad(modelRotation.rotateFace(blockPartFace.cullFace), this.makeBakedQuad(blockPart, blockPartFace, textureAtlasSprite, enumFacing, modelRotation, b));
                }
            }
        }
        return setTexture.makeBakedModel();
    }
    
    private ResourceLocation getParentLocation(final ResourceLocation resourceLocation) {
        final Iterator<Map.Entry<ResourceLocation, ModelBlock>> iterator = this.models.entrySet().iterator();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<ResourceLocation, ModelBlock> entry = iterator.next();
            final ModelBlock modelBlock = entry.getValue();
            if (modelBlock != null && resourceLocation.equals(modelBlock.getParentLocation())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private ResourceLocation getModelLocation(final ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getResourceDomain(), ModelBakery.I[0xF1 ^ 0xC5] + resourceLocation.getResourcePath() + ModelBakery.I[0xAD ^ 0x98]);
    }
    
    private void registerVariant(final ModelBlockDefinition modelBlockDefinition, final ModelResourceLocation modelResourceLocation) {
        this.variants.put(modelResourceLocation, modelBlockDefinition.getVariants(modelResourceLocation.getVariant()));
    }
    
    private ModelBlock makeItemModel(final ModelBlock modelBlock) {
        return this.itemModelGenerator.makeItemModel(this.textureMap, modelBlock);
    }
    
    private void bakeItemModels() {
        final Iterator<ResourceLocation> iterator = this.itemLocations.values().iterator();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ResourceLocation resourceLocation = iterator.next();
            final ModelBlock modelBlock = this.models.get(resourceLocation);
            if (this.hasItemModel(modelBlock)) {
                final ModelBlock itemModel = this.makeItemModel(modelBlock);
                if (itemModel != null) {
                    itemModel.name = resourceLocation.toString();
                }
                this.models.put(resourceLocation, itemModel);
                "".length();
                if (4 < -1) {
                    throw null;
                }
                continue;
            }
            else {
                if (!this.isCustomRenderer(modelBlock)) {
                    continue;
                }
                this.models.put(resourceLocation, modelBlock);
            }
        }
        final Iterator<TextureAtlasSprite> iterator2 = this.sprites.values().iterator();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final TextureAtlasSprite textureAtlasSprite = iterator2.next();
            if (!textureAtlasSprite.hasAnimationMetadata()) {
                textureAtlasSprite.clearFramesTextureData();
            }
        }
    }
    
    private Set<ResourceLocation> getItemsTextureLocations() {
        final HashSet hashSet = Sets.newHashSet();
        final Iterator<ResourceLocation> iterator = this.itemLocations.values().iterator();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ModelBlock modelBlock = this.models.get(iterator.next());
            if (modelBlock != null) {
                hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(ModelBakery.I[49 + 28 - 50 + 261])));
                if (this.hasItemModel(modelBlock)) {
                    final Iterator<String> iterator2 = ItemModelGenerator.LAYERS.iterator();
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final ResourceLocation resourceLocation = new ResourceLocation(modelBlock.resolveTextureName(iterator2.next()));
                        if (modelBlock.getRootModel() == ModelBakery.MODEL_COMPASS && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourceLocation)) {
                            TextureAtlasSprite.setLocationNameCompass(resourceLocation.toString());
                            "".length();
                            if (3 <= 2) {
                                throw null;
                            }
                        }
                        else if (modelBlock.getRootModel() == ModelBakery.MODEL_CLOCK && !TextureMap.LOCATION_MISSING_TEXTURE.equals(resourceLocation)) {
                            TextureAtlasSprite.setLocationNameClock(resourceLocation.toString());
                        }
                        hashSet.add(resourceLocation);
                    }
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    if (this.isCustomRenderer(modelBlock)) {
                        continue;
                    }
                    final Iterator<BlockPart> iterator3 = modelBlock.getElements().iterator();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    while (iterator3.hasNext()) {
                        final Iterator<BlockPartFace> iterator4 = iterator3.next().mapFaces.values().iterator();
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                        while (iterator4.hasNext()) {
                            hashSet.add(new ResourceLocation(modelBlock.resolveTextureName(iterator4.next().texture)));
                        }
                    }
                }
            }
        }
        return (Set<ResourceLocation>)hashSet;
    }
    
    public ModelBakery(final IResourceManager resourceManager, final TextureMap textureMap, final BlockModelShapes blockModelShapes) {
        this.sprites = (Map<ResourceLocation, TextureAtlasSprite>)Maps.newHashMap();
        this.models = (Map<ResourceLocation, ModelBlock>)Maps.newLinkedHashMap();
        this.variants = (Map<ModelResourceLocation, ModelBlockDefinition.Variants>)Maps.newLinkedHashMap();
        this.faceBakery = new FaceBakery();
        this.itemModelGenerator = new ItemModelGenerator();
        this.bakedRegistry = new RegistrySimple<ModelResourceLocation, IBakedModel>();
        this.itemLocations = (Map<String, ResourceLocation>)Maps.newLinkedHashMap();
        this.blockDefinitions = (Map<ResourceLocation, ModelBlockDefinition>)Maps.newHashMap();
        this.variantNames = (Map<Item, List<String>>)Maps.newIdentityHashMap();
        this.resourceManager = resourceManager;
        this.textureMap = textureMap;
        this.blockModelShapes = blockModelShapes;
    }
    
    static {
        I();
        final ResourceLocation[] array = new ResourceLocation[0xA3 ^ 0xB1];
        array["".length()] = new ResourceLocation(ModelBakery.I["".length()]);
        array[" ".length()] = new ResourceLocation(ModelBakery.I[" ".length()]);
        array["  ".length()] = new ResourceLocation(ModelBakery.I["  ".length()]);
        array["   ".length()] = new ResourceLocation(ModelBakery.I["   ".length()]);
        array[0xBA ^ 0xBE] = new ResourceLocation(ModelBakery.I[0xA7 ^ 0xA3]);
        array[0x8A ^ 0x8F] = new ResourceLocation(ModelBakery.I[0x2D ^ 0x28]);
        array[0x4C ^ 0x4A] = new ResourceLocation(ModelBakery.I[0xB9 ^ 0xBF]);
        array[0x86 ^ 0x81] = new ResourceLocation(ModelBakery.I[0x75 ^ 0x72]);
        array[0xB1 ^ 0xB9] = new ResourceLocation(ModelBakery.I[0x59 ^ 0x51]);
        array[0xB1 ^ 0xB8] = new ResourceLocation(ModelBakery.I[0xB0 ^ 0xB9]);
        array[0x13 ^ 0x19] = new ResourceLocation(ModelBakery.I[0x30 ^ 0x3A]);
        array[0x66 ^ 0x6D] = new ResourceLocation(ModelBakery.I[0x5A ^ 0x51]);
        array[0x71 ^ 0x7D] = new ResourceLocation(ModelBakery.I[0xA9 ^ 0xA5]);
        array[0x55 ^ 0x58] = new ResourceLocation(ModelBakery.I[0x4C ^ 0x41]);
        array[0x1C ^ 0x12] = new ResourceLocation(ModelBakery.I[0x23 ^ 0x2D]);
        array[0x0 ^ 0xF] = new ResourceLocation(ModelBakery.I[0x11 ^ 0x1E]);
        array[0x9F ^ 0x8F] = new ResourceLocation(ModelBakery.I[0x7 ^ 0x17]);
        array[0xD3 ^ 0xC2] = new ResourceLocation(ModelBakery.I[0x82 ^ 0x93]);
        LOCATIONS_BUILTIN_TEXTURES = Sets.newHashSet((Object[])array);
        LOGGER = LogManager.getLogger();
        MODEL_MISSING = new ModelResourceLocation(ModelBakery.I[0xBA ^ 0xA8], ModelBakery.I[0xD0 ^ 0xC3]);
        BUILT_IN_MODELS = Maps.newHashMap();
        JOINER = Joiner.on(ModelBakery.I[0xB ^ 0x1F]);
        MODEL_GENERATED = ModelBlock.deserialize(ModelBakery.I[0x5B ^ 0x4E]);
        MODEL_COMPASS = ModelBlock.deserialize(ModelBakery.I[0x42 ^ 0x54]);
        MODEL_CLOCK = ModelBlock.deserialize(ModelBakery.I[0x62 ^ 0x75]);
        MODEL_ENTITY = ModelBlock.deserialize(ModelBakery.I[0xA0 ^ 0xB8]);
        ModelBakery.BUILT_IN_MODELS.put(ModelBakery.I[0x20 ^ 0x39], "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"}, \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],     \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },         \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\", \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"south\", \"texture\": \"#missingno\" },         \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\": \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
        ModelBakery.MODEL_GENERATED.name = ModelBakery.I[0xBB ^ 0xA1];
        ModelBakery.MODEL_COMPASS.name = ModelBakery.I[0x51 ^ 0x4A];
        ModelBakery.MODEL_CLOCK.name = ModelBakery.I[0xBE ^ 0xA2];
        ModelBakery.MODEL_ENTITY.name = ModelBakery.I[0x19 ^ 0x4];
    }
}
