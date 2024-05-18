package net.minecraft.client.renderer.entity;

import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.vertex.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.tileentity.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class RenderItem implements IResourceManagerReloadListener
{
    private boolean field_175058_l;
    private static final String[] I;
    private final ItemModelMesher itemModelMesher;
    public float zLevel;
    private static final ResourceLocation RES_ITEM_GLINT;
    private final TextureManager textureManager;
    
    protected void registerItem(final Item item, final int n, final String s) {
        this.itemModelMesher.register(item, n, new ModelResourceLocation(s, RenderItem.I[" ".length()]));
    }
    
    public RenderItem(final TextureManager textureManager, final ModelManager modelManager) {
        this.field_175058_l = (" ".length() != 0);
        this.textureManager = textureManager;
        this.itemModelMesher = new ItemModelMesher(modelManager);
        this.registerItems();
    }
    
    private void renderModel(final IBakedModel bakedModel, final int n, final ItemStack itemStack) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x70 ^ 0x77, DefaultVertexFormats.ITEM);
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < length) {
            this.renderQuads(worldRenderer, bakedModel.getFaceQuads(values[i]), n, itemStack);
            ++i;
        }
        this.renderQuads(worldRenderer, bakedModel.getGeneralQuads(), n, itemStack);
        instance.draw();
    }
    
    private void renderModel(final IBakedModel bakedModel, final int n) {
        this.renderModel(bakedModel, n, null);
    }
    
    private void setupGuiTransform(final int n, final int n2, final boolean b) {
        GlStateManager.translate(n, n2, 100.0f + this.zLevel);
        GlStateManager.translate(8.0f, 8.0f, 0.0f);
        GlStateManager.scale(1.0f, 1.0f, -1.0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        if (b) {
            GlStateManager.scale(40.0f, 40.0f, 40.0f);
            GlStateManager.rotate(210.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.enableLighting();
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            GlStateManager.scale(64.0f, 64.0f, 64.0f);
            GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.disableLighting();
        }
    }
    
    private void registerBlock(final Block block, final String s) {
        this.registerBlock(block, "".length(), s);
    }
    
    private void preTransform(final ItemStack itemStack) {
        final IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        if (itemStack.getItem() != null) {
            if (!itemModel.isGui3d()) {
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void renderItemIntoGUI(final ItemStack itemStack, final int n, final int n2) {
        final IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        GlStateManager.pushMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap("".length() != 0, "".length() != 0);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(213 + 333 - 153 + 123, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(646 + 32 - 395 + 487, 172 + 695 - 490 + 394);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.setupGuiTransform(n, n2, itemModel.isGui3d());
        itemModel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
        this.renderItem(itemStack, itemModel);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }
    
    private void func_181565_a(final WorldRenderer worldRenderer, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final int n8) {
        worldRenderer.begin(0xB1 ^ 0xB6, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(n + "".length(), n2 + "".length(), 0.0).color(n5, n6, n7, n8).endVertex();
        worldRenderer.pos(n + "".length(), n2 + n4, 0.0).color(n5, n6, n7, n8).endVertex();
        worldRenderer.pos(n + n3, n2 + n4, 0.0).color(n5, n6, n7, n8).endVertex();
        worldRenderer.pos(n + n3, n2 + "".length(), 0.0).color(n5, n6, n7, n8).endVertex();
        Tessellator.getInstance().draw();
    }
    
    public void renderItemAndEffectIntoGUI(final ItemStack itemStack, final int n, final int n2) {
        if (itemStack != null && itemStack.getItem() != null) {
            this.zLevel += 50.0f;
            try {
                this.renderItemIntoGUI(itemStack, n, n2);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, RenderItem.I[0xB4 ^ 0xBE]);
                final CrashReportCategory category = crashReport.makeCategory(RenderItem.I[0x6D ^ 0x66]);
                category.addCrashSectionCallable(RenderItem.I[0x20 ^ 0x2C], new Callable<String>(this, itemStack) {
                    private final ItemStack val$stack;
                    final RenderItem this$0;
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$stack.getItem());
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                            if (2 <= 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                });
                category.addCrashSectionCallable(RenderItem.I[0x48 ^ 0x45], new Callable<String>(this, itemStack) {
                    private final ItemStack val$stack;
                    final RenderItem this$0;
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$stack.getMetadata());
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
                            if (4 <= 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                });
                category.addCrashSectionCallable(RenderItem.I[0x9B ^ 0x95], new Callable<String>(this, itemStack) {
                    final RenderItem this$0;
                    private final ItemStack val$stack;
                    
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
                            if (false) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$stack.getTagCompound());
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                });
                category.addCrashSectionCallable(RenderItem.I[0x78 ^ 0x77], new Callable<String>(this, itemStack) {
                    private final ItemStack val$stack;
                    final RenderItem this$0;
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
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
                            if (3 < 1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return String.valueOf(this.val$stack.hasEffect());
                    }
                });
                throw new ReportedException(crashReport);
            }
            this.zLevel -= 50.0f;
        }
    }
    
    public void renderItemModelForEntity(final ItemStack itemStack, final EntityLivingBase entityLivingBase, final ItemCameraTransforms.TransformType transformType) {
        if (itemStack != null && entityLivingBase != null) {
            IBakedModel bakedModel = this.itemModelMesher.getItemModel(itemStack);
            if (entityLivingBase instanceof EntityPlayer) {
                final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
                final Item item = itemStack.getItem();
                ModelResourceLocation modelResourceLocation = null;
                if (item == Items.fishing_rod && entityPlayer.fishEntity != null) {
                    modelResourceLocation = new ModelResourceLocation(RenderItem.I["  ".length()], RenderItem.I["   ".length()]);
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                }
                else if (item == Items.bow && entityPlayer.getItemInUse() != null) {
                    final int n = itemStack.getMaxItemUseDuration() - entityPlayer.getItemInUseCount();
                    if (n >= (0x17 ^ 0x5)) {
                        modelResourceLocation = new ModelResourceLocation(RenderItem.I[0xC ^ 0x8], RenderItem.I[0x64 ^ 0x61]);
                        "".length();
                        if (1 < 1) {
                            throw null;
                        }
                    }
                    else if (n > (0x0 ^ 0xD)) {
                        modelResourceLocation = new ModelResourceLocation(RenderItem.I[0x31 ^ 0x37], RenderItem.I[0xC3 ^ 0xC4]);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else if (n > 0) {
                        modelResourceLocation = new ModelResourceLocation(RenderItem.I[0x36 ^ 0x3E], RenderItem.I[0x66 ^ 0x6F]);
                    }
                }
                if (modelResourceLocation != null) {
                    bakedModel = this.itemModelMesher.getModelManager().getModel(modelResourceLocation);
                }
            }
            this.renderItemModelTransform(itemStack, bakedModel, transformType);
        }
    }
    
    private boolean func_183005_a(final ItemTransformVec3f itemTransformVec3f) {
        int n;
        if (itemTransformVec3f.scale.x < 0.0f) {
            n = " ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2;
        if (itemTransformVec3f.scale.y < 0.0f) {
            n2 = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n ^ n2;
        int n4;
        if (itemTransformVec3f.scale.z < 0.0f) {
            n4 = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        return (n3 ^ n4) != 0x0;
    }
    
    public void func_181564_a(final ItemStack itemStack, final ItemCameraTransforms.TransformType transformType) {
        if (itemStack != null) {
            this.renderItemModelTransform(itemStack, this.itemModelMesher.getItemModel(itemStack), transformType);
        }
    }
    
    static {
        I();
        RES_ITEM_GLINT = new ResourceLocation(RenderItem.I["".length()]);
    }
    
    protected void renderItemModelTransform(final ItemStack itemStack, final IBakedModel bakedModel, final ItemCameraTransforms.TransformType transformType) {
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap("".length() != 0, "".length() != 0);
        this.preTransform(itemStack);
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(405 + 256 - 381 + 236, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(668 + 164 - 642 + 580, 240 + 386 + 95 + 50, " ".length(), "".length());
        GlStateManager.pushMatrix();
        final ItemCameraTransforms itemCameraTransforms = bakedModel.getItemCameraTransforms();
        itemCameraTransforms.applyTransform(transformType);
        if (this.func_183005_a(itemCameraTransforms.getTransform(transformType))) {
            GlStateManager.cullFace(819 + 929 - 1212 + 492);
        }
        this.renderItem(itemStack, bakedModel);
        GlStateManager.cullFace(515 + 229 + 250 + 35);
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
        this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
    }
    
    public void func_175039_a(final boolean field_175058_l) {
        this.field_175058_l = field_175058_l;
    }
    
    public ItemModelMesher getItemModelMesher() {
        return this.itemModelMesher;
    }
    
    public void renderItemOverlays(final FontRenderer fontRenderer, final ItemStack itemStack, final int n, final int n2) {
        this.renderItemOverlayIntoGUI(fontRenderer, itemStack, n, n2, null);
    }
    
    private static void I() {
        (I = new String[55 + 178 - 158 + 450])["".length()] = I("\"\u0013\u001c!<$\u0013\u0017z$?\u0005\u0007z,8\u0015\f4'\"\u0013\u0000\n \"\u0013\t\n.:\u001f\n!g&\u0018\u0003", "VvdUI");
        RenderItem.I[" ".length()] = I(";\u0006\u0000\u0012\u0007&\u0007\u0004\u000e", "Rhvwi");
        RenderItem.I["  ".length()] = I("%\u00005,\u0000-\u000e\u00196\u0006'6%%\u001a7", "CiFDi");
        RenderItem.I["   ".length()] = I("\u0011)\u000e\u0010\u001a\f(\n\f", "xGxut");
        RenderItem.I[0x7C ^ 0x78] = I("3*\u0005\u00161$)\u001e /6\u001a@", "QErIA");
        RenderItem.I[0x33 ^ 0x36] = I("%\"1\u0012$8#5\u000e", "LLGwJ");
        RenderItem.I[0x11 ^ 0x17] = I("\u0003=\"\u001c>\u0014>9* \u0006\rd", "aRUCN");
        RenderItem.I[0xB3 ^ 0xB4] = I("&&.(\n;'*4", "OHXMd");
        RenderItem.I[0x4 ^ 0xC] = I("\u001a\u0017\u0018->\r\u0014\u0003\u001b \u001f'_", "xxorN");
        RenderItem.I[0xBF ^ 0xB6] = I("\u000f7\u0014,6\u00126\u00100", "fYbIX");
        RenderItem.I[0xB9 ^ 0xB3] = I("\u001864\u000e\u00008:4\rE#'?\u0007", "JSZje");
        RenderItem.I[0x16 ^ 0x1D] = I("\b\u001b-\fG#\n!\u000f\u0000a\u001d-\u000f\u0003$\u001d-\u0005", "AoHag");
        RenderItem.I[0x42 ^ 0x4E] = I("\u001e\u000648Y\u0003\u000b!0", "WrQUy");
        RenderItem.I[0xC9 ^ 0xC4] = I("\u00049-:q\f80", "MMHWQ");
        RenderItem.I[0x94 ^ 0x9A] = I("\u0010\u0017\t8q\u0017!8", "YclUQ");
        RenderItem.I[0x94 ^ 0x9B] = I("\u001c\u0004+\u0006J\u0013\u001f'\u0007", "UpNkj");
        RenderItem.I[0x65 ^ 0x75] = I("1-\u0010\u00028\u000f*\b\u001f537", "PCfkT");
        RenderItem.I[0x8C ^ 0x9D] = I("\u0010\u00143\u0018\u0004.\t)\u0018\u000f\u0019\u000e)\b7\u0015\u001b(\u0010\u000f\u0014\u001e", "qzEqh");
        RenderItem.I[0xAF ^ 0xBD] = I("\u0017 $\f\u001d)87\u0017\b)*3\b\u0010\u0011+6", "vNReq");
        RenderItem.I[0xA9 ^ 0xBA] = I("\u0015\u00023\u0010&(\r3\u0001=\u0012\u001a", "wnRsM");
        RenderItem.I[0x70 ^ 0x64] = I("3\u0019\u0006\u001d.2\u0014\u0001\b\u0014%", "Qusxq");
        RenderItem.I[0xA7 ^ 0xB2] = I("\f; $\u00161*.!\b\u000b=", "nIOSx");
        RenderItem.I[0x3D ^ 0x2B] = I("\u0007\u0003\u0013-\u0018\u0007\u001b\u00003\"\u0010", "dzrCG");
        RenderItem.I[0x43 ^ 0x54] = I("*\u0003 -\u001b.\u00103$!9", "MqATD");
        RenderItem.I[0xA0 ^ 0xB8] = I("1=)\u0001\u0004\t,-\u0016\u001a3;", "VOLdj");
        RenderItem.I[0x35 ^ 0x2C] = I("6=\f\u001e\u0010\u00056\u0007\u0003\u0001\u00057\n\u0004\u0014? ", "ZTkvd");
        RenderItem.I[0x9E ^ 0x84] = I("\u0007 \u0019\u00143\b(\u0006\u0001\t\u001f", "kItql");
        RenderItem.I[0xDC ^ 0xC7] = I(" \b(+'9\b\u0010-(?\u0019*:", "MiONI");
        RenderItem.I[0x23 ^ 0x3F] = I("\u0001+9%7\u000b\u0006;*\"\u001e<,", "nYXKP");
        RenderItem.I[0x31 ^ 0x2C] = I("=\u000f\u001f\u0012..\u0007\u0003\t\u00149", "Mfqyq");
        RenderItem.I[0x64 ^ 0x7A] = I("\u0019%\u0014\b\u001a\f\u000f\u0005\u0019\u0004\u00195\u0012", "iPfxv");
        RenderItem.I[0x5A ^ 0x45] = I("*\n\u001c0\n9\u001d\b\n\u001d", "Xoxoi");
        RenderItem.I[0x3 ^ 0x23] = I("53\u000f\u0011&4\u0005\u0000\u000616?\u0017", "FZcgC");
        RenderItem.I[0x48 ^ 0x69] = I("\u0019\u001a+6\t1\u0011#0\u001c\u000b\u0006", "nrBBl");
        RenderItem.I[0x67 ^ 0x45] = I("\u001e5!\r\u0005\u0010\u000f.\u0000\u0018\u001759", "gPMaj");
        RenderItem.I[0xB9 ^ 0x9A] = I("))\u001a\u0005#\u001b%\u0006\u00148(#\u001a\u00025*#6\u0001;(*", "DFivZ");
        RenderItem.I[0x76 ^ 0x52] = I("6*\b%\u001906\u001e(\u001b0\u001a\u001d&\u00199", "UEjGu");
        RenderItem.I[0x9F ^ 0xBA] = I(",\u0007%1\u0012*7 *\u0013;", "OhDCa");
        RenderItem.I[0xAF ^ 0x89] = I("\u0010\u001a\u0017\u001a", "tsenQ");
        RenderItem.I[0x74 ^ 0x53] = I("\u0016\u001f\u0011?\u0000\n", "fpuEo");
        RenderItem.I[0xB ^ 0x23] = I("\u001c!\u0007\u0017>\u001d\u0011\u0014\u0010 \u0016", "xNruR");
        RenderItem.I[0x5A ^ 0x73] = I(",\u001b\u0016\u0000\u0019-+\u0004\u0010\u0014;\u0007", "Htcbu");
        RenderItem.I[0xED ^ 0xC7] = I("57\u000f\u001b\u0014,7", "EVjtz");
        RenderItem.I[0x62 ^ 0x49] = I("\u0010\u0019\u0005*\u0015\u0011)\u0002'\n\u0011", "tvpHy");
        RenderItem.I[0x43 ^ 0x6F] = I("\u0015\u0006\u001a\t;\t\u0004\u0011\u001d", "fstoW");
        RenderItem.I[0x41 ^ 0x6C] = I("\u0017\u0012<'\u0000\u0003\n", "dkNNn");
        RenderItem.I[0xBB ^ 0x95] = I(":'?.1\u0007\"(,/==", "XNMMY");
        RenderItem.I[0x7B ^ 0x54] = I("0\u0007=\u0000\u0016?-?\u0002\u001b,\u0017 ", "ZrSgz");
        RenderItem.I[0x9A ^ 0xAA] = I("\u00062?*\u0006\f2\"\u0010\u0019", "iSTuj");
        RenderItem.I[0x7C ^ 0x4D] = I("$6\u0001\u0000 2\u0019\u001f\u0010\"!#\u0000", "WFsuC");
        RenderItem.I[0xF0 ^ 0xC2] = I("\u000f\u0019\u0003 ,\u000f%\u000e&$\u0018\u001f\u0011", "nzbCE");
        RenderItem.I[0xA7 ^ 0x94] = I("5\r\u0017\u001d=>\r\u000e)\u000e4\r\u0013\u0013\u0011", "Qlevb");
        RenderItem.I[0x51 ^ 0x65] = I("\u0004?\u0013\r<9:\u000e\t", "fVanT");
        RenderItem.I[0x1B ^ 0x2E] = I("\u0005;\u000629\n\u0011\u0004:2", "oNhUU");
        RenderItem.I[0xBC ^ 0x8A] = I("%;),\u0014%=", "JZBsx");
        RenderItem.I[0x5C ^ 0x6B] = I("=:\u0004\f\u0000+\u0015\u001a\u0016\u0004", "NJvyc");
        RenderItem.I[0x41 ^ 0x79] = I("6\u001b\u00174\n6'\u001a8\u0004", "WxvWc");
        RenderItem.I[0x3B ^ 0x2] = I("5\u001b\u0006'>>\u001b\u001f\u0013\r>\u001d", "QztLa");
        RenderItem.I[0x98 ^ 0xA2] = I("3\u0003?1\r<\u000e2\u001d\n\"\u00025)7=\u000481\u001c5\u0019\t'\u000f7", "PkVBh");
        RenderItem.I[0x94 ^ 0xAF] = I("\"\u0016\u0005\u0011\b$\n\u0013\u001c\n$&\n\u001c\n2\r\u0002\u0001;$\u001e\u0000", "Aygsd");
        RenderItem.I[0xBD ^ 0x81] = I("7\u001a%\u0015\u00021\f\u001b\u0014\u001b=\u000b/)\u0004;\u00067\u0002\f&7!\u0011\u000e", "ThDvi");
        RenderItem.I[0x59 ^ 0x64] = I("=\f06\u000b\u000f\u00011,\u0011;<.*\u001c#\u0017&7-5\u0004$", "PcCEr");
        RenderItem.I[0xA7 ^ 0x99] = I("\u0017\u0006\u0018:\r;\u001f\u0018:\u001b\u0010\u0017\u0005\u000b\r\u0003\u0015", "drwTh");
        RenderItem.I[0x94 ^ 0xAB] = I("\u0000\u0017\"\u000b\u0003,\u0001?\f\u0005\u0018< \n\b\u0000\u0017(\u00179\u0016\u0004*", "scMef");
        RenderItem.I[0x7C ^ 0x3C] = I("\u0013%\u000b\u0017\f\u0013\u0019\u001a\u0018\u0004\u001c-\u0019", "rFjte");
        RenderItem.I[0x7B ^ 0x3A] = I("&\u0011\u001b)-\u001b\b\u0005++/\u000b", "DxiJE");
        RenderItem.I[0xD6 ^ 0x94] = I("\u00176 \u0004\u000b\u001c690$\u001f6<\u0004'", "sWRoT");
        RenderItem.I[0xEE ^ 0xAD] = I("\f\u001b;\n=\u00031%\u00010\b\u0005&", "fnUmQ");
        RenderItem.I[0xEA ^ 0xAE] = I("653*\u0007556\u001e\u0004", "YTXuw");
        RenderItem.I[0x13 ^ 0x56] = I("\u001a48\u00106\f\u001b:\t4\u0007/9", "iDJeU");
        RenderItem.I[0x2 ^ 0x44] = I(")5\u0000?\u001f85\u0000\"\u0017\u0006%\u001b%\u001124", "YGiLr");
        RenderItem.I[0x7D ^ 0x3A] = I("\u001c&\u001f\u0006\u0007\b5\u0004\u001e5\u00195\u0004\u0003=", "xGmmX");
        RenderItem.I[0x8B ^ 0xC3] = I("\u001a\u001d(\u0001\u001a\u000b\u001d(\u001c\u0012", "joArw");
        RenderItem.I[0x65 ^ 0x2C] = I(",-\u0010%\u0015# \u001d\t\u0001:$\u000b\"\n\u0010'\u00159\u0013$", "OEyVp");
        RenderItem.I[0x3F ^ 0x75] = I("+%'\u0014$ \u000f$\n?9;", "ZPFfP");
        RenderItem.I[0x5 ^ 0x4E] = I("\u001e>\u00063\u0012\u0015\u0014\u0004.\n\u001a&\t", "oKgAf");
        RenderItem.I[0x3 ^ 0x4F] = I("2\u001c.\u001c\u001e>", "SpBuk");
        RenderItem.I[0x68 ^ 0x25] = I("'<\u001e\b\u001b*\"\b\u0005-!", "EPkmD");
        RenderItem.I[0x38 ^ 0x76] = I("0\u00036&#7\u0002*4", "XlCUW");
        RenderItem.I[0x1F ^ 0x50] = I("\"\u0004\u0000\u0000\u0001()\u0015\u001b\n$\u0006", "Mvanf");
        RenderItem.I[0xD ^ 0x5D] = I("\u001c\u000f\u000b\u0016(,\u0013\u000f\u0006>\n", "swnoM");
        RenderItem.I[0xF0 ^ 0xA1] = I("7:\u0014(\u00123&\u0016*=", "GSzCM");
        RenderItem.I[0x4A ^ 0x18] = I("<\f*\u0011\f", "LcZau");
        RenderItem.I[0x1A ^ 0x49] = I("&\u0001\u0010\u001e\u0016!\b\u001d1", "TdtAb");
        RenderItem.I[0x2D ^ 0x79] = I("\u001c'\u00052\u000e4;\u0019*\u0002\u001b", "kOlFk");
        RenderItem.I[0x11 ^ 0x44] = I("\u00167#\f\u0012\u0005<#", "dRGSa");
        RenderItem.I[0x92 ^ 0xC4] = I("+\u0012>\u0002", "XsPfy");
        RenderItem.I[0x2A ^ 0x7D] = I("\n\u0003\u0019\u001d\b\u0005\u000e\u00141\u001e\b\u0005\u0014\u001d\u0019\u0006\u0005\u0015", "ikpnm");
        RenderItem.I[0x4C ^ 0x14] = I("\t\b=\u0017\u0016\u000e\u0006=\u0016", "ziSse");
        RenderItem.I[0x9E ^ 0xC7] = I("2\u0003!\u00006)1=\u000e,%\u001d:\u0000,$", "AnNoB");
        RenderItem.I[0x24 ^ 0x7E] = I("\u0014\u0010:$)\u001b\u001d7\b>\u0012\u001c\f$-\u0019\u001c ##\u0019\u001d", "wxSWL");
        RenderItem.I[0x71 ^ 0x2A] = I("6+'\u001e6% '21+ &", "DNCAE");
        RenderItem.I[0xE8 ^ 0xB4] = I("\u0003\u0006\u0003\"!\u00184\u001e(1/\u0018\r#1\u0003\u001f\u0003#0", "pklMU");
        RenderItem.I[0x2 ^ 0x5F] = I("\u000e\"\u0005!8\u000e\u001e\u0017#!\u0003(\n%", "oAdBQ");
        RenderItem.I[0x76 ^ 0x28] = I("\u0010$&$\u0003->57\u0007\u001b#3", "rMTGk");
        RenderItem.I[0x32 ^ 0x6D] = I("\r\u0011:;\n\u0006\u0011#\u000f&\b\u0000$9;\u000e", "ipHPU");
        RenderItem.I[0xD5 ^ 0xB5] = I(">\u0016\r3\u000b1<\u00105\u00178\n\r3", "TccTg");
        RenderItem.I[0x22 ^ 0x43] = I(">\u000535$0\u00144\u000396", "QdXjW");
        RenderItem.I[0xF6 ^ 0x94] = I(")\n \u00121?%!\u0006\"6\u0013<\u0000", "ZzRgR");
        RenderItem.I[0xF6 ^ 0x95] = I("\t\b\u0002\u0005 \u001f", "zxmkG");
        RenderItem.I[0xC4 ^ 0xA0] = I("\u0018\u001d\u0006\u001c*\u000e2\u001e\u00179", "kmirM");
        RenderItem.I[0xFB ^ 0x9E] = I("\u0006>*\t,;!?\u000b.\n7/5 \b38\u0019", "dRKjG");
        RenderItem.I[0xFC ^ 0x9A] = I("\u0014\u00168\u00140\u0005\u000e,\u0018\u0001\u0013\u001e\u0012\u0016\u0003\u0017\t>", "vzMqo");
        RenderItem.I[0x20 ^ 0x47] = I("\u0011(.%\n,)53\r\u001d?%\r\u0003\u001f;2!", "sZARd");
        RenderItem.I[0xD7 ^ 0xBF] = I("*\u0017\u001b\u000f\u0019:\u001a\u001b\b(,\n%\u0006*(\u001d\t", "InzaF");
        RenderItem.I[0x7C ^ 0x15] = I("?%()'+#(9\u0016=3\u00167\u00149$:", "XWIPx");
        RenderItem.I[0x40 ^ 0x2A] = I("\u0013;= \u001c+:,$\u001b\u001a,<\u001a\u0015\u0018(+6", "tIXEr");
        RenderItem.I[0x13 ^ 0x78] = I("\"\"\u000f\u00036\u0011)\u0004\u001e'\u00118\u001c\n+ .\f4%\"*\u001b\u0018", "NKhkB");
        RenderItem.I[0x7E ^ 0x12] = I("\u0005\">$\u000f\u001a?2(>\f/\f&<\b8 ", "iKSAP");
        RenderItem.I[0xF6 ^ 0x9B] = I("\u000f0=\n\u0006\u00160\u0005\u001c\u001c\u000384\n\f=66\u000e\u001b\u0011", "bQZoh");
        RenderItem.I[0xE9 ^ 0x87] = I("\b\u0002*,\n\u0002/86\f\u000e\u001e.&2\u0000\u001c*1\u001e", "gpKBm");
        RenderItem.I[0x3E ^ 0x51] = I("&*420%7;0\u00013'\u0005>\u000370)", "VCZYo");
        RenderItem.I[0xC1 ^ 0xB1] = I("\t>4\u001b-\u001c\u00145\u001f \u0010%#\u000f\u001e\u001e''\u00182", "yKFkA");
        RenderItem.I[0x6 ^ 0x77] = I("\u0003\b\u0012\u001b2\u0005\f\u001f*$\u00152\u0011( \u0002\u001e", "qmvDA");
        RenderItem.I[0xB3 ^ 0xC1] = I("\u0019\u0018)\u0000\u0015\u0018.6\u0002\u0011\u0003\u001f \u0012/\r\u001d$\u0005\u0003", "jqEvp");
        RenderItem.I[0x1F ^ 0x6C] = I("\u000f\r\u000f%\u0012'\u0016\u00120\u001e\u0016\u0000\u0002\u000e\u0010\u0014\u0004\u0015\"", "xefQw");
        RenderItem.I[0x52 ^ 0x26] = I("\u000e\u0016!\u000e \u0000,>\u0016.\u001e\u001d(\u0006\u0010\u0010\u001f,\u0011<", "wsMbO");
        RenderItem.I[0x42 ^ 0x37] = I("-;\r\u0015 \u0010$\u0018\u0017\"!2\b),#6\u001f\u0005\u0014?6\u0002\u0013", "OWlvK");
        RenderItem.I[0x52 ^ 0x24] = I("7\u000b?)=&\u0013+%\f0\u0003\u0015+\u000e4\u00149\u0013\u00124\t/", "UgJLb");
        RenderItem.I[0x1A ^ 0x6D] = I("0\"+\"\u000b\r#04\f<5 \n\u0002>17&:\"1*0", "RPDUe");
        RenderItem.I[0x75 ^ 0xD] = I("1\u001d\t\u000f\u0019!\u0010\t\b(7\u00007\u0006*3\u0017\u001b>63\n\r", "RdhaF");
        RenderItem.I[0x7 ^ 0x7E] = I("\u0010\u001c\u0007\u001b5\u0004\u001a\u0007\u000b\u0004\u0012\n9\u0005\u0006\u0016\u001d\u0015=\u001a\u0016\u0000\u0003", "wnfbj");
        RenderItem.I[0x70 ^ 0xA] = I("\u0003\u0019*\t ;\u0018;\r'\n\u000e+3)\b\n<\u001f\u0011\u0014\n!\t", "dkOlN");
        RenderItem.I[0x47 ^ 0x3C] = I("#=\u0015'\u0019\u00106\u001e:\b\u0010'\u0006.\u0004!1\u0016\u0010\n#5\u0001<2?5\u001c*", "OTrOm");
        RenderItem.I[0x70 ^ 0xC] = I("\u0006;.\f;\u0019&\"\u0000\n\u000f6\u001c\u000e\b\u000b!06\u0014\u000b<&", "jRCid");
        RenderItem.I[0x7C ^ 0x1] = I("\u00186\u0011\f\u0019\u00016)\u001a\u0003\u0014>\u0018\f\u0013*0\u001a\b\u0004\u0006\b\u0006\b\u0019\u0010", "uWviw");
        RenderItem.I[0xCC ^ 0xB2] = I(")\u001d/\u0001 #0=\u001b&/\u0001+\u000b\u0018!\u0003/\u001c4\u0019\u001f/\u0001\"", "FoNoG");
        RenderItem.I[96 + 26 - 52 + 57] = I("9%\u001b\u0007\u0018:8\u0014\u0005),(*\u000b+(?\u000637(\"\u0010", "ILulG");
        RenderItem.I[67 + 62 - 77 + 76] = I("\u001f>\n\u0001\u000b\n\u0014\u000b\u0005\u0006\u0006%\u001d\u00158\b'\u0019\u0002\u00140;\u0019\u001f\u0002", "oKxqg");
        RenderItem.I[6 + 15 + 94 + 14] = I(":!\u000b\u00065<%\u00067#,\u001b\b5';70)'&!", "HDoYF");
        RenderItem.I[15 + 63 - 50 + 102] = I("2\u0000>9336!;7(\u00077+\t&\u00053<%\u001e\u00193!3", "AiROV");
        RenderItem.I[27 + 8 + 7 + 89] = I("\u0005;\u001c\u0017!- \u0001\u0002-\u001c6\u0011<#\u001e2\u0006\u0010\u001b\u00022\u001b\u0006", "rSucD");
        RenderItem.I[58 + 125 - 173 + 122] = I("1+5/7?\u0011*79! <'\u0007/\"80+\u0017>8-=", "HNYCX");
        RenderItem.I[102 + 17 - 85 + 99] = I("\u001a?8\u0000!' -\u0002#\u00166=<\"\u0019!=\u0006$\u001d7\u0006\u0000&\u0019*", "xSYcJ");
        RenderItem.I[67 + 24 - 84 + 127] = I("% 90\u000548-<4\"(\u0013=;5();?#\u0013/9;>", "GLLUZ");
        RenderItem.I[57 + 122 - 128 + 84] = I("0\u001e\u0005:\"\r\u001f\u001e,%<\t\u000e\u0012$3\u001e\u000e(\"7\b5. 3\u0015", "RljML");
        RenderItem.I[10 + 131 - 41 + 36] = I("\u0014=9\u0014\u0018\u000409\u0013)\u0012 \u0007\u0012&\u0005 =\u0014\"\u0013\u001b;\u0016&\u000e", "wDXzG");
        RenderItem.I[34 + 1 + 67 + 35] = I("+$%\u0016&?\"%\u0006\u0017)2\u001b\u0007\u0018>2!\u0001\u001c(\t'\u0003\u00185", "LVDoy");
        RenderItem.I[87 + 42 - 18 + 27] = I("\u0014(*?4,);;3\u001d?+\u00052\u0012(+?4\u0016>\u001096\u0012#", "sZOZZ");
        RenderItem.I[15 + 128 - 59 + 55] = I("4!%><\u0007*.#-\u0007;67!6-&\t 9:&3&=,\u001d5$91", "XHBVH");
        RenderItem.I[132 + 108 - 208 + 108] = I("4\u001a\u000f$,+\u0007\u0003(\u001d=\u0017=)\u0012*\u0017\u0007/\u0016<,\u0001-\u0012!", "XsbAs");
        RenderItem.I[39 + 51 - 0 + 51] = I("+&\u0016\u001582&.\u0003\"'.\u001f\u00152\u0019/\u0010\u00022#)\u0014\u0014\t%+\u0010\t", "FGqpV");
        RenderItem.I[60 + 54 + 12 + 16] = I("\u001d\u001e$\u00067\u001736\u001c1\u001b\u0002 \f\u000f\u001a\r7\f5\u001c\t!73\u001e\r<", "rlEhP");
        RenderItem.I[13 + 10 + 92 + 28] = I("&$%36%9*1\u00073)\u00140\b$).6\f2\u0012(4\b/", "VMKXi");
        RenderItem.I[38 + 63 - 60 + 103] = I(" \u00111<\u00145;08\u00199\n&('8\u00051(\u001d>\u0001'\u0013\u001b<\u0005:", "PdCLx");
        RenderItem.I[7 + 76 - 36 + 98] = I("(/>\u0016\u001e.+3'\b>\u00152(\u001f>/4,\t\u0005)6(\u0014", "ZJZIm");
        RenderItem.I[76 + 69 - 91 + 92] = I("#<\"#\u000b\"\n=!\u000f9;+1184<1\u000b>0*\n\r<47", "PUNUn");
        RenderItem.I[46 + 62 - 24 + 63] = I("-:\u0005\u0001\u0003\u0005!\u0018\u0014\u000f47\b*\u000e; \b\u0010\b?63\u0016\n;+", "ZRluf");
        RenderItem.I[95 + 104 - 79 + 28] = I("#$\u00055:-\u001e\u001a-43/\f=\n2 \u001b=04$\r\u000666 \u0010", "ZAiYU");
        RenderItem.I[26 + 109 - 76 + 90] = I("9+(\b\u000411)", "XELmw");
        RenderItem.I[96 + 69 - 40 + 25] = I("\u000f'\u0005<0\u0007=\u0004\u00060\u0003&\u000e-+", "nIaYC");
        RenderItem.I[73 + 118 - 164 + 124] = I(" \u001c*#90\u0010", "DuEQP");
        RenderItem.I[122 + 88 - 62 + 4] = I("\u0016\u0010&\u0015'\u0006\u001c\u0016\u0014#\u001d\u0016=\u000f", "ryIgN");
        RenderItem.I[138 + 24 - 30 + 21] = I("6\u0004\u0006\t\u0002%\u0013", "Qvggk");
        RenderItem.I[143 + 101 - 193 + 103] = I("(64\u001a';!\n\u0007# +!\u001c", "ODUtN");
        RenderItem.I[18 + 74 + 29 + 34] = I("=$\u000b%'", "NPdKB");
        RenderItem.I[62 + 42 - 49 + 101] = I("9+\n\u0017\r?=4\u0007\u001257\u000e\u0016\u00143:\u0000", "ZYktf");
        RenderItem.I[39 + 34 - 17 + 101] = I("\u0018!%\r\u0012\t'#\u0000\u001c", "kUJcw");
        RenderItem.I[126 + 14 + 6 + 12] = I(")\u0010\u0006\"0&\u001d\u000b\u000e&>\u0017\u0001478\u0011\f:", "JxoQU");
        RenderItem.I[115 + 90 - 110 + 64] = I(".;\u0002\u00154\u001c'\u0005\t#&6\u0003\u000f.(", "CTqfM");
        RenderItem.I[152 + 56 - 76 + 28] = I("\u001b:-' &;(%)", "yHDDK");
        RenderItem.I[24 + 155 - 67 + 49] = I("\u00059\u0005\f\u001a\u0003%\u0013\u0001\u0018\u0003\t\u0014\u0002\u0017\u0004", "fVgnv");
        RenderItem.I[158 + 19 - 99 + 84] = I("\u0007\u000f,\u0013\u001d\u0007\f,\u0013\u0019\u0004\u0002*", "hcHLj");
        RenderItem.I[21 + 10 + 79 + 53] = I("\u0005\u0012#%\u0012\u0019(5?\u001e\b\u001c\b>\u001b\n\u0015", "kwWMw");
        RenderItem.I[106 + 16 + 7 + 35] = I("9&/\u0003\u00022\f=\u001d\u0017*", "HSNqv");
        RenderItem.I[160 + 48 - 129 + 86] = I("'\u000f\u001b\u001e& \u0001\u001b\u001f\n'\u0002\u0014\u0018", "TnuzU");
        RenderItem.I[24 + 115 - 17 + 44] = I("$.\u0019\t=\b8\u0004\u000e;<\u0005\u0005\u000b95", "WZvgX");
        RenderItem.I[105 + 17 + 12 + 33] = I("$!\b\u000f\u0015\b&\u000b\u0000\u0012", "WUgap");
        RenderItem.I[120 + 42 - 126 + 132] = I("!\f#\u001302\u0007#?7<\u0007\"\u00130?\b%", "SiGLC");
        RenderItem.I[32 + 19 - 16 + 134] = I("\u001c\u0017\u0017&\u0019\u001a\u0007\u0005*", "xrvBF");
        RenderItem.I[157 + 102 - 190 + 101] = I("5\u0002\u0015$", "SggJH");
        RenderItem.I[62 + 126 - 122 + 105] = I("<\u0002\t*\u0011/\u0011\u00045=", "HceFN");
        RenderItem.I[129 + 166 - 135 + 12] = I("7 0 \u000b7\u001c\"/\u00034", "VCQCb");
        RenderItem.I[11 + 60 + 58 + 44] = I("0\u001e<)\u0010\r\u0004\"+\u001a", "RwNJx");
        RenderItem.I[158 + 118 - 143 + 41] = I("<  \u001b17 9/\u001d4 0", "XARpn");
        RenderItem.I[153 + 61 - 87 + 48] = I("%;,)5*\u00111\"8-", "ONBNY");
        RenderItem.I[48 + 11 + 93 + 24] = I("\"\u000e\u0012>\n!\u000e\u001b", "Moyay");
        RenderItem.I[34 + 69 - 73 + 147] = I(";\u001b\u001a:\u0006-4\u001b#\u0004*", "HkhOe");
        RenderItem.I[79 + 23 - 54 + 130] = I("\u0007\u001d2\u000f :\u0006<\u0003'", "eqSlK");
        RenderItem.I[49 + 4 + 18 + 108] = I("\u0001\u0018\u001d\u00165\u0014\u001b\u0007\u001f", "cthsj");
        RenderItem.I[154 + 42 - 34 + 18] = I("\u000e<*=\u000139*%\u0003", "lNEJo");
        RenderItem.I[129 + 93 - 101 + 60] = I("\"\u001d\u0014 ;6\u000b\u001a\"", "AduNd");
        RenderItem.I[40 + 80 - 53 + 115] = I("\u000b0\u0013\u0011\n\u001b-\u001d\u0004", "lBrhU");
        RenderItem.I[172 + 14 - 150 + 147] = I("-\u001a30\u0017\u0015\u001f9:\u0015", "JhVUy");
        RenderItem.I[181 + 90 - 119 + 32] = I("\t\u000e\u0001\t.:\u0005\n\u0014?:\u0010\t\u000e6", "egfaZ");
        RenderItem.I[64 + 31 + 72 + 18] = I("*$\n\u0000,1\"\b\t", "FMges");
        RenderItem.I[65 + 134 - 186 + 173] = I(";\u000e\f*-\"\u000e48,9\u0003", "VokOC");
        RenderItem.I[61 + 182 - 227 + 171] = I("\u0005\u0013\u0013\t\u0001\u000f>\u0005\b\t\u0006", "jargf");
        RenderItem.I[138 + 161 - 196 + 85] = I("\u0014\u001c\u001e\u0000\u0007\u0013\u001a\u001f\u0007", "dupkX");
        RenderItem.I[84 + 160 - 89 + 34] = I("9\u00169\u00018,<<\u001e;%", "IcKqT");
        RenderItem.I[167 + 78 - 94 + 39] = I("8\u000f!7$%\u0005)", "JjEhS");
        RenderItem.I[183 + 85 - 138 + 61] = I("\u00181#80\u0019\u00078!:\u0007", "kXONU");
        RenderItem.I[39 + 57 - 29 + 125] = I("8\r\r\u0004\u0004\u0010\u0012\u000b\u001f\r", "Oedpa");
        RenderItem.I[106 + 74 - 96 + 109] = I("73\u001f\u0018,9\t\u0004\u001b,\"", "NVstC");
        RenderItem.I[49 + 126 - 76 + 95] = I(" +\u00153\u001e \u0017\u0007$\u0016(:\u0007", "AHtPw");
        RenderItem.I[2 + 41 - 33 + 185] = I("2\u0000\u001a>\u001d2\u0017\u0001%4!\u0002\u0007;", "ScnWk");
        RenderItem.I[136 + 53 - 81 + 88] = I(".\n\u0014\u0004\u0018\"", "Lougw");
        RenderItem.I[177 + 16 - 38 + 42] = I("7(1\"#6&", "UMUPL");
        RenderItem.I[162 + 189 - 219 + 66] = I("1\u0005$\u0000?\f\u001f\"\u0002>!\u001f", "SlVcW");
        RenderItem.I[157 + 96 - 147 + 93] = I(" \u0000\u0017\u0004\u0016*\n\u0014\t", "Boxoe");
        RenderItem.I[136 + 34 - 117 + 147] = I("&\n\u0018\")\u001b\u001a\u001d.!/", "DxqAB");
        RenderItem.I[198 + 113 - 159 + 49] = I("53\u0013\u000e\"\b#\u0016\u0002*<", "WAzmI");
        RenderItem.I[71 + 198 - 238 + 171] = I("1\u001c\u00181\u001f\f\u001d\u00053\u001d!\u001d", "SnqRt");
        RenderItem.I[3 + 98 + 58 + 44] = I("(\u001a\u000b%6\u0015\u0005\u0011!08\u0007\u000b?", "JhdRX");
        RenderItem.I[84 + 6 - 22 + 136] = I("\f/\u000e<\u0003\u001c", "oNmHv");
        RenderItem.I[161 + 55 - 35 + 24] = I("\u0010\u0016;>", "szZGs");
        RenderItem.I[161 + 180 - 311 + 176] = I("\u0014\u0015 &\u001a\u0015\u0016.).", "wzAJE");
        RenderItem.I[137 + 75 - 178 + 173] = I(" \u000e\u0017\u00181,\u0013\u0013", "Cavtn");
        RenderItem.I[116 + 130 - 230 + 192] = I("\u0000%+\t\n\u00069=\u0004\b\u0006", "cJIkf");
        RenderItem.I[206 + 79 - 172 + 96] = I("\u0013\u001f\u00104-\u0019\u0003\u0016\r-\u0011\u000f\u001d7", "pmqRY");
        RenderItem.I[182 + 51 - 75 + 52] = I("\u0015\u0002\u0006\r\u0018\u001e\u0002\u001f94\u0005\u0002\u001d\u00144", "qctfG");
        RenderItem.I[8 + 168 - 1 + 36] = I("') $\u000f$ -\u0017\u0002&<<+\u0012,:", "CHYHf");
        RenderItem.I[30 + 89 + 7 + 86] = I("\u0001\u0007 >\u001c\u0007\u001722", "ebAZC");
        RenderItem.I[150 + 104 - 208 + 167] = I(")\u0017\u001b6\u00149\u001d\u001d\f\u0005,\u001b\u0003", "MroSw");
        RenderItem.I[122 + 113 - 43 + 22] = I("5>%\u001a\u000b?3\u001b\u0015\b>4/", "QWDwd");
        RenderItem.I[34 + 150 - 173 + 204] = I("+\u000e3\f\u001d!\u0003\r\u000e\u0000*", "OgRar");
        RenderItem.I[81 + 170 - 77 + 42] = I("+\u0011)1\u000b!\u000b?3", "OxZAn");
        RenderItem.I[43 + 183 - 130 + 121] = I("\u0002<\u0002\u0005 \u0003<", "fNmuP");
        RenderItem.I[206 + 99 - 129 + 42] = I("\u0012.\u001c\u0016\u0019\u001b'&\u0006\u0014\u0018 \u0012", "wCydx");
        RenderItem.I[130 + 166 - 248 + 171] = I("\r\u0006\u000e\u001d\b\u0004\u000f4\u0000\u001b\r", "hkkoi");
        RenderItem.I[186 + 64 - 114 + 84] = I("\u0012\u0018\u001719\u0019\u0002\u001d7?(\u0002\u0015;4\u0012", "wvtYX");
        RenderItem.I[218 + 78 - 228 + 153] = I("\u0002\b\u0001\u000f:\b\u0014\u00111&8\u0000\u00171'\u0002", "gfePJ");
        RenderItem.I[218 + 197 - 247 + 54] = I("/\u0000\u0017\u0016\u001d>\u0001\u001d,", "JnsIn");
        RenderItem.I[58 + 171 - 223 + 217] = I("(\u000e,0\u0000\"\u0001$\n", "GoGof");
        RenderItem.I[142 + 106 - 203 + 179] = I("*\u0014\u000205<;\u0016 8:\u0001", "YdpEV");
        RenderItem.I[2 + 55 + 92 + 76] = I("\u0011.\u0019)\u001b,!\u000e$\u0010\u0016", "sGkJs");
        RenderItem.I[133 + 169 - 299 + 223] = I("8\u0011\u001e2\u000e7;\u00160\f1\u0001", "RdpUb");
        RenderItem.I[187 + 97 - 141 + 84] = I(".0\u0006\u001d-%0\u001f)\u0014/?\u0017\u0013", "JQtvr");
        RenderItem.I[120 + 9 - 108 + 207] = I("1\u0010\u0018\f\u00111,\u001f\n\u00163\u0016", "Psyox");
        RenderItem.I[202 + 226 - 204 + 5] = I("(\r(\u000b.\"\u0002 1\u0017 \r71", "GlCTH");
        RenderItem.I[163 + 55 - 40 + 52] = I("8\u0018<6 .7(&-(\r\u0011$\"?\r", "KhNCC");
        RenderItem.I[67 + 21 + 92 + 51] = I("\u000e-\n\u0014%3\"\u001d\u0019.\t\u001b\u001f\u00169\t", "lDxwM");
        RenderItem.I[142 + 213 - 130 + 7] = I("=\u0016\u0014282<\u001c0:4\u0006%25#\u0006", "WczUT");
        RenderItem.I[190 + 128 - 136 + 51] = I("\u0000\"\u0001,=\u000b\"\u0018\u0018\u0004\u0001-\u0010\"=\u0003\"\u0007\"", "dCsGb");
        RenderItem.I[201 + 113 - 117 + 37] = I("\r%\u0015\t\u0005\r\u0019\u0012\u000f\u0002\u000f#+\r\r\u0018#", "lFtjl");
        RenderItem.I[177 + 202 - 202 + 58] = I("(\u000f5\u0016\t-\u001f", "NzGxh");
        RenderItem.I[177 + 91 - 192 + 160] = I("=)4#\u0014", "ZEUPg");
        RenderItem.I[210 + 150 - 166 + 43] = I("\u0011\u0006\u0005\u0017')\u001a\u0005\n1", "vjddT");
        RenderItem.I[31 + 38 + 169 + 0] = I(",\u001c\u0015\u001b#?\u001f\u0014\t", "KpzlP");
        RenderItem.I[169 + 213 - 307 + 164] = I("\f!54=\u0005\u0011+11\u0007", "kNYPX");
        RenderItem.I[77 + 177 - 105 + 91] = I("\"?\u0019\b='<\u001a\u000f\t", "EPulb");
        RenderItem.I[215 + 163 - 260 + 123] = I("\u0002\u0018&-\u000f\n\u0005/", "ewJIP");
        RenderItem.I[161 + 151 - 171 + 101] = I("5\u0014 \u001a%", "RfAiV");
        RenderItem.I[231 + 90 - 310 + 232] = I("3\u0003\f\"\u00158", "TqmTp");
        RenderItem.I[94 + 193 - 196 + 153] = I("\f\u001b8\b5\n\u001f.33\b\u001b3", "dzJlP");
        RenderItem.I[10 + 215 - 164 + 184] = I(":)\u0016)\u000e>'\f\u001d", "RHovl");
        RenderItem.I[129 + 70 - 66 + 113] = I("\u0005\u00121\"\u00122\u00005=\f\u0005\u0003504\u001d\u00055'\u0018\u0018\u00055\u000b\u001b\u0001\u0016$1", "mwPTk");
        RenderItem.I[77 + 131 - 10 + 49] = I("\u0011;\")#\u000b", "yTRYF");
        RenderItem.I[240 + 100 - 274 + 182] = I("\"6\u0017", "KUrSu");
        RenderItem.I[143 + 184 - 134 + 56] = I("8!\u0015>\u001d32\b#", "QSzPB");
        RenderItem.I[75 + 189 - 232 + 218] = I(" \u001a8\u001e\u0011+\u00048\u0013%", "IhWpN");
        RenderItem.I[223 + 199 - 192 + 21] = I("9\b$,\u0006?\b.", "PzKBY");
        RenderItem.I[174 + 91 - 229 + 216] = I("\f\n\n$\u001b\u0011\n\u0004: \n\u0017\u0017", "exeJD");
        RenderItem.I[165 + 84 - 110 + 114] = I("\u0012\u00021+2\u0017\u000f", "xwZNP");
        RenderItem.I[225 + 219 - 287 + 97] = I("\u0004\u0011\u00020&\u000b;\u001f#+\u0007\u0016\u001f", "ndlWJ");
        RenderItem.I[15 + 52 + 88 + 100] = I("=\f\u000e\u000e$#", "QmjjA");
        RenderItem.I[248 + 51 - 48 + 5] = I("\u001e\u001b\b8=-\u0018\u0014>-\u0019", "rzxQN");
        RenderItem.I[52 + 212 - 115 + 108] = I("=;$\n\u001a\u000e5&\u0006", "QZTci");
        RenderItem.I[83 + 71 - 92 + 196] = I("\u001a/\u0012$7", "vJdAE");
        RenderItem.I[149 + 155 - 164 + 119] = I("\u000b\u0002. .8\u001c,!=\u000f\u001f,,\u0005\u0017\u0019,;)\u0012\u0019,\u0017*\u000b\n=-", "gkIHZ");
        RenderItem.I[111 + 135 - 199 + 213] = I("<,6\u0018\u0006%(2,\u001f>", "PEBGv");
        RenderItem.I[217 + 229 - 309 + 124] = I(":7*\u0007\u001c\b0*\u0007\u0011<", "WRFhr");
        RenderItem.I[118 + 238 - 334 + 240] = I("%8 &.\u00174<75$2 !8&2", "HWSUW");
        RenderItem.I[207 + 18 - 216 + 254] = I("\u0018 5\u0015!\u001c,;", "uYVpM");
        RenderItem.I[142 + 24 - 153 + 251] = I("*\u00070'<6\u0010%,2", "DbDOY");
        RenderItem.I[250 + 246 - 423 + 192] = I("\u000f\u000b\r\u00000\u00131\u001b\u001a<\u0002\u0005", "anyhU");
        RenderItem.I[85 + 99 - 126 + 208] = I(":.\u0006\u0019\u0001&\u0014\u0010\u0003\r7 -\u0017\u0001:(\u0017", "TKrqd");
        RenderItem.I[148 + 104 - 6 + 21] = I("9\"!\u0004\u0006%\u00187\u001e\n4,\n\u001f\u00176.'\u001f", "WGUlc");
        RenderItem.I[70 + 257 - 94 + 35] = I("\u000f\u0015\u00116\u0014\r\u0015\u00068", "azeSv");
        RenderItem.I[266 + 42 - 118 + 79] = I("6%\"\f2-% !2", "YDISA");
        RenderItem.I[192 + 199 - 241 + 120] = I("\n'\u0015\u0002=\f$\b", "eEfkY");
        RenderItem.I[157 + 0 + 104 + 10] = I("\u001b\u0017\u0016\u0000\u0003\u000f)\u001c\b\u0003", "kvukf");
        RenderItem.I[86 + 241 - 124 + 69] = I("8;\u0012\f\"&", "HRaxM");
        RenderItem.I[70 + 109 - 139 + 233] = I("8\u0006?\u0016\u0003!\u001d", "HsRfh");
        RenderItem.I[14 + 172 - 109 + 197] = I("\u000b';\u0001\u0018\u0000\r5\u0001\t", "zRZsl");
        RenderItem.I[77 + 241 - 295 + 252] = I("\u0014\u0003)\u001d\"\u001f);\u001b7\f\u0004;", "evHoV");
        RenderItem.I[210 + 232 - 305 + 139] = I("&8,\u0018", "TYEtT");
        RenderItem.I[236 + 149 - 285 + 177] = I("\u000b?#\t\u0004\u00164\"%\u0012\u00155$\u0011", "yZGzp");
        RenderItem.I[16 + 208 - 13 + 67] = I("\u0007=\u0005\u0000\u0005\u001a6\u0004,\u001d\u00145\u0011", "uXasq");
        RenderItem.I[34 + 254 - 36 + 27] = I("<=(1\u0000!6)\u001d\u001b<=", "NXLBt");
        RenderItem.I[15 + 219 + 37 + 9] = I("%02\u0003\u00198;3/\u00198'5\u0018", "WUVpm");
        RenderItem.I[120 + 104 - 136 + 193] = I("5,0\u0010\u00182:<=\u001a($", "GITOu");
        RenderItem.I[28 + 173 - 127 + 208] = I("\u001f/;'4\u0018!;&\u0018\u001f:4*5\u001f", "lNUCG");
        RenderItem.I[237 + 103 - 193 + 136] = I("#2,\u001a609,61>9-\u001a6%6!76", "QWHEE");
        RenderItem.I[137 + 148 - 122 + 121] = I("\u0015)\"\b+\u0007\"725\b", "fLCWG");
        RenderItem.I[244 + 248 - 418 + 211] = I("#&\u0013*<", "PJzGY");
        RenderItem.I[127 + 273 - 117 + 3] = I("\u001b\u0006\r=", "hhbJO");
        RenderItem.I[83 + 240 - 191 + 155] = I(";\u000f\u0017\u0016\u001a$\u0000\u0001\u00047", "HaxaE");
        RenderItem.I[176 + 143 - 46 + 15] = I("\u0014\u0005\u001c\u00049\u0014\u000b\u0007\f", "gjihf");
        RenderItem.I[13 + 117 - 21 + 180] = I("\u0012\u0007\u001b\r\u0004\u0004(\u001a\f\u0006\b\u0005\u001a", "awixg");
        RenderItem.I[239 + 264 - 364 + 151] = I("\u0014 \u000f\u0019#\u001e\u000b\u0016\u0013;\u0013;\b", "gTfzH");
        RenderItem.I[113 + 213 - 114 + 79] = I("\"\u0012\u0004\u0002\f\u000e\u0004\u0019\u0005\n:9\u0018\u0018\b8\u0014\u0018", "Qfkli");
        RenderItem.I[228 + 171 - 368 + 261] = I(";9\u001c$+\u0017/\u0006>:'#", "HMsJN");
        RenderItem.I[193 + 184 - 111 + 27] = I("\u001c3?7\u000607\"<\u0010\u001c2\"<<\u001f+1-\u0006", "oGPYc");
        RenderItem.I[144 + 30 + 17 + 103] = I("'$>'\u0013\u000b#%(\u001f&#", "TPQIv");
        RenderItem.I[189 + 175 - 206 + 137] = I("!\u000b\u0002", "UevPp");
        RenderItem.I[28 + 207 - 22 + 83] = I("\u0003\u0016\u0011$-", "wycGE");
        RenderItem.I[51 + 210 - 156 + 192] = I("#\u0003#\u0003\u00058\u001e0", "WqBsa");
        RenderItem.I[13 + 281 - 167 + 171] = I("\u0013=&$\"\u000e=*\u000b=\b $", "gOOTU");
        RenderItem.I[26 + 51 + 96 + 126] = I("\u0001\u0019\n\u000e", "wpdkO");
        RenderItem.I[17 + 273 - 168 + 178] = I(">\u000b\u0004!\u0014%\u0003\u001c=", "IjpDf");
        RenderItem.I[105 + 118 + 55 + 23] = I("\u001c \u0000", "kEbNK");
        RenderItem.I[232 + 171 - 140 + 39] = I("/\u001e\u0016=\u00106.\u001b,\u0001,\u001e\u0017", "XqyYu");
        RenderItem.I[55 + 80 + 94 + 74] = I("<-\u00054$%\u001d\u001a\"$81\u001f\"$\u00142\u000615.", "KBjPA");
        RenderItem.I[173 + 241 - 345 + 235] = I("\r+\u001e\u000b3\u0005#\u001f\u0001", "iJpoV");
        RenderItem.I[230 + 100 - 278 + 253] = I("\r)\u0016\u0001\u0010", "nAsrd");
        RenderItem.I[164 + 243 - 182 + 81] = I("\u0019#\"!&\b5\u001c2>\b\"7", "mQCQV");
        RenderItem.I[188 + 82 - 141 + 178] = I("\u00039<\f(940\f)\u0012", "fWXiZ");
        RenderItem.I[291 + 172 - 182 + 27] = I("!>\u0015 \t;$\u001583$", "HLzNV");
        RenderItem.I[245 + 242 - 308 + 130] = I("\u00044\u0002!-\u001d/\u000e$\u0013\u0015#", "mFmOr");
        RenderItem.I[228 + 109 - 46 + 19] = I("+\b)45#\u0002#", "BzFZj");
        RenderItem.I[147 + 209 - 81 + 36] = I("\f'\u0018-$5*\u001f'\u000f\u0019?\u0014&<", "jKqCP");
        RenderItem.I[82 + 92 + 69 + 69] = I("-\u0019\u0006\u0006\u0007", "Livjb");
        RenderItem.I[30 + 5 + 187 + 91] = I("\u0006\u001f\u0003", "dptKV");
        RenderItem.I[18 + 142 - 110 + 264] = I("\u0000 6\u001d%\u0017#-+;\u0005\u0010q", "bOABU");
        RenderItem.I[269 + 149 - 178 + 75] = I("\n+\u0010'(\u001d(\u000b\u00116\u000f\u001bV", "hDgxX");
        RenderItem.I[303 + 53 - 313 + 273] = I("\u0018\u0002:\u0016>\u000f\u0001!  \u001d2\u007f", "zmMIN");
        RenderItem.I[26 + 192 - 170 + 269] = I("*\u0019+\u0018.", "KkYwY");
        RenderItem.I[84 + 281 - 122 + 75] = I("\n(4\u0005", "iGUiL");
        RenderItem.I[26 + 74 + 182 + 37] = I("\u0002\u001d20;\u000e\u0014?", "auSBX");
        RenderItem.I[228 + 154 - 170 + 108] = I("\u001d!\u0016\u001b\u0000\u0017,", "yHwvo");
        RenderItem.I[137 + 239 - 226 + 171] = I("\u0011 \b<>\u0011<\u0000=\u0015", "xRgRa");
        RenderItem.I[191 + 301 - 282 + 112] = I(" #&\u00037.\"-\b\u001c", "GLJgh");
        RenderItem.I[276 + 106 - 128 + 69] = I("0!\u00176&*$\u0017*\u001d", "YSxXy");
        RenderItem.I[75 + 301 - 365 + 313] = I("\u00057\u00072\u0016\u001c\u0007\u001b!\u001c\u0000<", "rXhVs");
        RenderItem.I[303 + 305 - 539 + 256] = I("6<\u001e\u0006\u0001/\f\u0002\n\u000b76\u001d", "ASqbd");
        RenderItem.I[174 + 188 - 174 + 138] = I("9<\u0004\u0006' \f\u001b\u000b!%2\u0013\u0007", "NSkbB");
        RenderItem.I[187 + 93 - 185 + 232] = I("\u0018\r\t\u0002/\u0001=\u0007\u001e/", "obffJ");
        RenderItem.I[299 + 117 - 139 + 51] = I("8\u0000\u0001\u001a\u001f\u0014\u0007\u0019\u001b\b/", "Ktntz");
        RenderItem.I[271 + 167 - 356 + 247] = I("\u0018!:*\u00024&=+\u0011\u000e9", "kUUDg");
        RenderItem.I[2 + 239 - 97 + 186] = I("\u0014?:;\u00178;<6\u0019\u000630", "gKUUr");
        RenderItem.I[309 + 220 - 452 + 254] = I("\u000b\u0004&++'\u00111 ", "xpIEN");
        RenderItem.I[330 + 206 - 241 + 37] = I("\u0012\u000e\u000b\u000f=\u0018\u00035\u0011%\u0019\u0015\u000e", "vgjbR");
        RenderItem.I[77 + 156 + 76 + 24] = I("<%\u0013\"$6(-<#7:\u0017#", "XLrOK");
        RenderItem.I[122 + 199 - 191 + 204] = I("2=85,80\u0006(*5?8 &", "VTYXC");
        RenderItem.I[77 + 20 + 43 + 195] = I("/\u00199(\u001e%\u0014\u0007$\t.", "KpXEq");
        RenderItem.I[188 + 155 - 142 + 135] = I("5\u0010\u0010\u0001\u001e", "Fdybu");
        RenderItem.I[13 + 198 - 30 + 156] = I("\u00037\u0019\u0007", "aXnkC");
        RenderItem.I[225 + 287 - 342 + 168] = I(" -9\u000e\u000b\"7'9\n9==", "MXJfy");
        RenderItem.I[155 + 320 - 361 + 225] = I(".\u0007\u0001\u0017\u0010'7\u001e\u0004\u001a;\f", "Ihmsu");
        RenderItem.I[236 + 220 - 290 + 174] = I("\u0014'91\u0011\u001d\u0017&=\u001b\u0005-9", "sHUUt");
        RenderItem.I[303 + 36 - 166 + 168] = I("\u0016\u001a\u001b%\u0006\u001f*\u0007(\u0000\u001a\u0014\u000f$", "quwAc");
        RenderItem.I[311 + 314 - 601 + 318] = I("!\u0015\u001f\u0012\u000b(%\u0012\u000e\u000b", "Fzsvn");
        RenderItem.I[314 + 64 - 94 + 59] = I("9\u0018\u0018\b\u0003-", "Jljam");
        RenderItem.I[101 + 11 - 40 + 272] = I("\u0015.(\r>\u00169", "sKIyV");
        RenderItem.I[289 + 242 - 298 + 112] = I("\u0004\u0002\u001f!:\u0014\u0013\u0014#", "cwqQU");
        RenderItem.I[135 + 115 + 40 + 56] = I("\u0003=\u001b\u0015(\u001a\r\u001c\u001e(", "tRtqM");
        RenderItem.I[254 + 159 - 229 + 163] = I("\u001e#-\"\u00172?-)", "mWBLr");
        RenderItem.I[293 + 289 - 564 + 330] = I("*7\u0017:=+*\u001d", "CExTb");
        RenderItem.I[27 + 187 + 30 + 105] = I("#\u0000\u0005\b*)\r;\r*\"", "GideE");
        RenderItem.I[172 + 110 - 274 + 342] = I("\t\u0017(*\u0007\u0000',!\u0007", "nxDNb");
        RenderItem.I[309 + 156 - 453 + 339] = I("\u000f\u000707\u001c'\u001c03\f\u000b", "xoUVh");
        RenderItem.I[190 + 262 - 179 + 79] = I("\u000e.\u001f-\u0011", "yFzLe");
        RenderItem.I[262 + 343 - 259 + 7] = I("\u0013\u0011\"5\u0003", "qcGTg");
        RenderItem.I[110 + 144 - 229 + 329] = I("6\u001c+\u000e\u0010?\u000b\u0015\u0012\u001d6\u0014/\u000e", "ZyJzx");
        RenderItem.I[9 + 152 + 51 + 143] = I("\u0015\n)\u0006\t\u001c\u001d\u0017\u0011\t\u001c\u001c<\u0002\r\u0018\u001b-", "yoHra");
        RenderItem.I[29 + 210 + 71 + 46] = I("<\r\u0004\u0013,5\u001a:\u000b!7\u000f\f\t##", "PhegD");
        RenderItem.I[128 + 227 - 78 + 80] = I("\t7\u00115+\u0000 /#,\n&\u0003", "eRpAC");
        RenderItem.I[304 + 339 - 325 + 40] = I(",\u000f\u0000#/\"\u0006\b&\u001e'\u0002\r'$;", "OgaJA");
        RenderItem.I[227 + 281 - 415 + 266] = I("\u0014;8\u000e\u0005\u001a20\u000b4\u0014;<\u0014\u001f\u0007?8\u0013\u000e", "wSYgk");
        RenderItem.I[177 + 328 - 496 + 351] = I("\u0000\u0007\u000e\u0005\u001e\u000e\u000e\u0006\u0000/\u000f\n\b\u000b\u0019\r\b\u001c", "coolp");
        RenderItem.I[324 + 48 - 13 + 2] = I("\f$\u0003#'\u0002-\u000b&\u0016\r#\r>:", "oLbJI");
        RenderItem.I[155 + 46 + 26 + 135] = I(">; ,\u0019?,#/##", "WIOBF");
        RenderItem.I[255 + 62 - 14 + 60] = I("& &*(,:,7\u0003?>(0\u0012", "ORIDw");
        RenderItem.I[180 + 196 - 279 + 267] = I("\u0013;\u000e \u0005\u0016,\u0006)3\u0014.\u0012", "zIaNZ");
        RenderItem.I[287 + 54 - 288 + 312] = I("#<!\u001e\u0017(!!\u0004;", "JNNpH");
        RenderItem.I[228 + 229 - 100 + 9] = I("\b<\u0012\u001b&\u00021,\u001e,\u00008\u0016\u0002", "lUsvI");
        RenderItem.I[170 + 148 - 232 + 281] = I("\u0010\u001e,%.\u001a\u0013\u0012+)\u0011\u000498-\u0015\u0003(", "twMHA");
        RenderItem.I[209 + 317 - 213 + 55] = I("\u000e=%8\u0005\u00040\u001b9\u000f\r3-;\r\u0019", "jTDUj");
        RenderItem.I[153 + 256 - 198 + 158] = I("\u0000'/\u001d\u001f\n*\u0011\u0012\u001f\u000b:=", "dNNpp");
        RenderItem.I[75 + 269 - 44 + 70] = I("\r\u001a\u0004\u0016\f\u0004*\u0000\u0017\u0005\u0007\u0010\u001c", "juhri");
        RenderItem.I[287 + 86 - 25 + 23] = I("\t?\u000b\u0006\u0001\u0000\u000f\u0004\n\u0001\u001d$\u0017\u000e\u0005\u001a5", "nPgbd");
        RenderItem.I[130 + 176 - 162 + 228] = I("\b*\u001e\t,\u0001\u001a\u001e\b.\b,\u001c\n:", "oErmI");
        RenderItem.I[316 + 288 - 596 + 365] = I("3\u001e.=\u000b:. 6\u0001 \u0002", "TqBYn");
        RenderItem.I[89 + 133 - 0 + 152] = I("?)>\u0017?", "YEWyK");
        RenderItem.I[15 + 309 - 59 + 110] = I("\t*;\u000f\u0015\u0011*9", "yEIdv");
        RenderItem.I[36 + 84 + 11 + 245] = I(",-\u0006\u0013<+\u001d\u0019\u0017+$!\u0001\u0017)", "OBixY");
        RenderItem.I[40 + 60 - 83 + 360] = I("\u000623\u001d6\u001f==", "vSZsB");
        RenderItem.I[360 + 191 - 336 + 163] = I("2\u001b+\n+;+&\u001e>9\u0011", "UtGnN");
        RenderItem.I[359 + 268 - 467 + 219] = I("$&\u00055\u0001-\u0016\b!\u0014/,", "CIiQd");
        RenderItem.I[330 + 32 - 66 + 84] = I(")\r\u00119", "ZdvWR");
        RenderItem.I[27 + 155 - 155 + 354] = I("+\u0017\b\u001b!+\u0019\u0011", "DvcDE");
        RenderItem.I[187 + 339 - 225 + 81] = I("\u00197\u001b6&\u000f\u0018\r,*\u0018", "jGiCE");
        RenderItem.I[271 + 287 - 275 + 100] = I("3\u0000\u001412\u000e\r\t=(", "QifRZ");
        RenderItem.I[187 + 383 - 269 + 83] = I(">\")\u0011\u00161\b#\u0019\u0015&", "TWGvz");
        RenderItem.I[127 + 306 - 231 + 183] = I("07\u00162*0\u000b\u0013>,#", "QTwQC");
        RenderItem.I[10 + 190 - 156 + 342] = I("\u0006&4!<\r&-\u0015\u0007\r(4", "bGFJc");
        RenderItem.I[96 + 196 + 16 + 79] = I("\u0018'\u00138<\u000e", "zRpSY");
        RenderItem.I[265 + 177 - 341 + 287] = I(":\u0010\u001e *\u0012\u0013\u001f&3(\u0005", "MqjEX");
        RenderItem.I[219 + 94 + 73 + 3] = I("\u00003;\"8\u000e'.(\u0002\u0018", "lRMCg");
        RenderItem.I[342 + 27 - 33 + 54] = I("\u001e?$2*\u0012$>", "sVJWI");
        RenderItem.I[27 + 325 - 179 + 218] = I("01\u00013\u0007&", "CPeWk");
        RenderItem.I[24 + 298 - 22 + 92] = I("!\u0017#/\u0018,\n#3", "HeLAG");
        RenderItem.I[148 + 86 - 10 + 169] = I("\b3\u0007\u00191\u00158\u0006", "zVcjE");
        RenderItem.I[11 + 162 - 0 + 221] = I("\u0019%5\u00075\u000b'6", "jKZpW");
        RenderItem.I[264 + 296 - 503 + 338] = I("5\u000e1\"", "WaPVb");
        RenderItem.I[367 + 347 - 335 + 17] = I(">?\u0010;\u00117(", "RZqOy");
        RenderItem.I[207 + 337 - 515 + 368] = I("*'\u001b\u001e\b%;\u0014\u001e23", "GNwuW");
        RenderItem.I[29 + 158 + 22 + 189] = I("1$\u00074.", "SVnWE");
        RenderItem.I[369 + 223 - 333 + 140] = I("5\u0016\u0019\u0011-4\u001b\u0014\u0004", "Vzxhr");
        RenderItem.I[214 + 272 - 412 + 326] = I("#\u0015&2\u0018", "QpCVk");
        RenderItem.I[68 + 384 - 90 + 39] = I("\n95.&", "zXEKT");
        RenderItem.I[128 + 100 + 53 + 121] = I("0)\u000b>", "RFdUj");
        RenderItem.I[330 + 393 - 628 + 308] = I("\u0018;<\u000b+454\n\"", "kWUfN");
        RenderItem.I[308 + 261 - 277 + 112] = I("\u000f \u0014\u0017\u00153%\u0018\n\u0004\u000f)\u0003\u0010", "lHqda");
        RenderItem.I[118 + 334 - 431 + 384] = I(".\u001f\u0019?#+\u000f4<+&\u000f\b00<", "HjkQB");
        RenderItem.I[138 + 380 - 474 + 362] = I("\f!\u0015", "iFrKf");
        RenderItem.I[199 + 90 - 230 + 348] = I("-\u0018\u001d<6=\u0004", "NwpLW");
        RenderItem.I[187 + 315 - 334 + 240] = I(" '\u0015\u0003\u001e()9\u0019\u0018\"", "FNfkw");
        RenderItem.I[37 + 355 - 167 + 184] = I("%\r#\u0007\"-\u0003\u000f\u001d$';3\u000e87", "CdPoK");
        RenderItem.I[112 + 201 + 73 + 24] = I("\u0010\b\u0000\u0006\u001d", "sdoev");
        RenderItem.I[22 + 222 + 17 + 150] = I("\n&\u0005\u001a<\u0019%\u0004\b\u0010\t?\u0019\u0019", "mJjmO");
        RenderItem.I[314 + 143 - 105 + 60] = I("4+5", "WDQOr");
        RenderItem.I[299 + 272 - 384 + 226] = I("!\u0017\u0002\u0015\u001a<", "Rvnxu");
        RenderItem.I[127 + 294 - 26 + 19] = I("4%-=\u00011 1\"", "WIBJo");
        RenderItem.I[139 + 353 - 169 + 92] = I("<;\u0007$!>(\b1,", "LNaBD");
        RenderItem.I[343 + 241 - 348 + 180] = I("\u0002\u000e<\u0013\t\u0005>0\u0017\b", "aaSxl");
        RenderItem.I[204 + 161 - 194 + 246] = I("/.8\t$(\u001e$\u0003-!.9", "LAWbA");
        RenderItem.I[255 + 119 - 157 + 201] = I("\u0015\u0001,;\u0005\u001d\u0019*\u000f", "qxIdg");
        RenderItem.I[4 + 304 - 14 + 125] = I("\u0011\u0003\r\u000e1\u0010\u001e", "uzhQC");
        RenderItem.I[340 + 38 - 167 + 209] = I("\u000f=-67\u0019!-\u0007", "kDHiP");
        RenderItem.I[358 + 223 - 319 + 159] = I("(<\u000b\u001d!>*\u0019,", "LEnBC");
        RenderItem.I[24 + 356 - 19 + 61] = I("3 \u0000*;;,\u0000", "WYeuY");
        RenderItem.I[70 + 374 - 255 + 234] = I("&\b\u0014\u0010 7\u0003\u0001#5", "BqqOP");
        RenderItem.I[325 + 240 - 209 + 68] = I("7\u0015\u0006\u001a\u001a*\r\r", "SlcEy");
        RenderItem.I[235 + 250 - 288 + 228] = I("2\u0010/0\u0016?\u0005<\n\u0017", "ViJoe");
        RenderItem.I[378 + 230 - 236 + 54] = I("\u001e\u0003 \u001e\u0006\b\u001b<", "zzEAa");
        RenderItem.I[106 + 286 + 11 + 24] = I("\u001655,\u0001\u001b\";", "rLPsq");
        RenderItem.I[184 + 76 - 130 + 298] = I("\u0002=\u0001\u0018*\u000f)\u0001", "fDdGF");
        RenderItem.I[114 + 299 - 27 + 43] = I(".\b-\u001c\b/\u001d$,\u0006", "JqHCq");
        RenderItem.I[349 + 217 - 139 + 3] = I("\u0003\u0010(\u0014?\u000e\u000e%?\f\u0005\u00058.", "giMKS");
        RenderItem.I[157 + 56 - 59 + 277] = I("\u0013\u001653\u0018\u0016\b5\u0002\u0001\u0016", "woPlu");
        RenderItem.I[344 + 99 - 43 + 32] = I("7\u0016\n3!!\u000e\u0001\u000b+", "SoolN");
        RenderItem.I[369 + 289 - 421 + 196] = I("\u00100?\u0005\u000e\u001c .?", "tIZZy");
        RenderItem.I[349 + 339 - 548 + 294] = I("-6\u0014\u0002", "OYzgt");
        RenderItem.I[142 + 295 - 318 + 316] = I("%:\u0014\u000f\u0017", "VOsne");
        RenderItem.I[166 + 14 + 181 + 75] = I("\u000b%;\u001f", "hDPzu");
        RenderItem.I[395 + 1 - 13 + 54] = I(":'=", "XBYzZ");
        RenderItem.I[372 + 59 - 160 + 167] = I("\u001e\u001f\u0007=\n\u0018\u001f\u0005", "lzwXk");
        RenderItem.I[130 + 331 - 107 + 85] = I(".\u0015=\u0013\u001c(", "MzRxu");
        RenderItem.I[310 + 107 - 158 + 181] = I("\"\u0006(8\u001e\"", "QnMYl");
        RenderItem.I[387 + 196 - 403 + 261] = I("\u000b5\u001b\t\u0006", "fPwfh");
        RenderItem.I[302 + 422 - 321 + 39] = I("*\u001b\n\u0012$3\u00008\u0011*?\n\u0014", "ZngbO");
        RenderItem.I[395 + 121 - 465 + 392] = I("\f,-<\u001a>:$6\u0010\u0012", "aIASt");
        RenderItem.I[350 + 289 - 513 + 318] = I("\u000f\r\u000b*", "mhnLz");
        RenderItem.I[309 + 31 - 267 + 372] = I("\b5%\t\u000e\u000f\u0005(\u0007\u000e\r", "kZJbk");
        RenderItem.I[111 + 232 - 196 + 299] = I("&\u000f;\u000b. \t", "EgRhE");
        RenderItem.I[170 + 272 - 303 + 308] = I("\u0012\u00169\u000e3\u0015&5\r?\u0012\u00123\u000b", "qyVeV");
        RenderItem.I[15 + 264 - 5 + 174] = I("\u001b\u0011:\u0015\u0005\u001d", "ipXwl");
        RenderItem.I[146 + 184 - 63 + 182] = I("2\u001a\u001d\u000f\t5*\u0000\u0005\u000e3\u001c\u0006", "Qurdl");
        RenderItem.I[113 + 376 - 410 + 371] = I("!6\u0000\u0012\u001d\"", "LCtfr");
        RenderItem.I[137 + 288 - 313 + 339] = I("\u0013\b)&)\u00148+88\u0004\b(", "pgFML");
        RenderItem.I[117 + 114 + 145 + 76] = I("\u0001\u0006(($\u00078,%\"\u0007", "sgJJM");
        RenderItem.I[32 + 349 - 229 + 301] = I("=\u00062 ';88+**", "OgPBN");
        RenderItem.I[47 + 430 - 62 + 39] = I("?&#\u000e\u00119\u00182\u0018\u001d:", "MGAlx");
        RenderItem.I[109 + 329 - 408 + 425] = I("\u001a; ??\u0006\u000b2'?\u001b<", "hTTKZ");
        RenderItem.I[180 + 45 + 194 + 37] = I("=:\r= \u0007$\f9 4", "XTiXR");
        RenderItem.I[442 + 285 - 481 + 211] = I("\u00055\b+\u00168+\u00065", "gYiQs");
        RenderItem.I[115 + 26 + 233 + 84] = I(" <673\u0018 2%5", "GTWDG");
        RenderItem.I[323 + 414 - 700 + 422] = I("4\u0017\u0019+\f=\r\u0012(6'", "SxuOS");
        RenderItem.I[150 + 446 - 176 + 40] = I("\u001772\u0003\u0001\u000b\r1\n\u0016\r", "yRFkd");
        RenderItem.I[316 + 180 - 100 + 65] = I("\"(;7;\u001a&50<)!", "EDZDH");
        RenderItem.I[378 + 256 - 566 + 394] = I("\u0005;\n\u0000+\u0004\u0014\u0006\u001d+", "vKcdN");
        RenderItem.I[71 + 171 + 17 + 204] = I("4?;!5<.,(\u000f!* (5 \u0005,55", "RZILP");
        RenderItem.I[227 + 209 - 341 + 369] = I("1\u0007\u0005;&\f\u001b\u000b6'6\u0019", "SkdAC");
        RenderItem.I[340 + 190 - 300 + 235] = I("\u0004(/\u001b\u001b6*:\u0013\u001b\u0004", "iIHvz");
        RenderItem.I[427 + 176 - 204 + 67] = I("\t62\u0012\b\u0005#\b\u0016\u0015\n*3", "kDWea");
        RenderItem.I[127 + 321 - 241 + 260] = I("\b(\"\u0004!\u0019&9", "kIWhE");
        RenderItem.I[173 + 379 - 324 + 240] = I("! \u0014\t\u0006\u001b+\t\t", "DNplt");
        RenderItem.I[88 + 96 - 9 + 294] = I("#'\u0013\u001b\t<2\u0012'\u000f5;\u0019\u0016", "PWvxb");
        RenderItem.I[148 + 86 - 116 + 352] = I("\u000f/2\u001d\u0000\u00032,\u001b\u001755-\f\u0006\u00062", "jWBxr");
        RenderItem.I[278 + 289 - 430 + 334] = I("\u001f\u00077\u0000(\u001a\u0006$\u0017\u0010\u001c", "ynEew");
        RenderItem.I[344 + 281 - 353 + 200] = I("\"\u000788\u00137\u00194\u0013\u0010:\u001a:", "UuQLr");
        RenderItem.I[459 + 29 - 49 + 34] = I("=\u0003<\u0010-4\n", "XnYbL");
        RenderItem.I[195 + 209 - 13 + 83] = I("\u0013\u0001\b\t\u001b\u001c\u0007\f\t!", "zumdD");
        RenderItem.I[27 + 385 - 172 + 235] = I("$\u0004%/\u001007:7\u0001", "BhJXu");
        RenderItem.I[342 + 454 - 726 + 406] = I("\u0013\u00028\u0005>\u0004", "pcJwQ");
        RenderItem.I[425 + 460 - 828 + 420] = I("\u0002\n\u00193#\u001d", "remRW");
        RenderItem.I[274 + 222 - 163 + 145] = I(" \u0019,,\u0003\u001d\b(=\u00066\u0017", "BxGIg");
        RenderItem.I[302 + 237 - 538 + 478] = I("7\b\u0005'5)\b\u0019'\u00057\b\u00185.(", "GglTZ");
        RenderItem.I[253 + 367 - 617 + 477] = I("8\u0013&", "UrVsY");
        RenderItem.I[261 + 320 - 116 + 16] = I(",!-\u0005\u000f%\u0011\"\u0000\u00189!5", "KNAaj");
        RenderItem.I[311 + 158 - 265 + 278] = I("81>9\u001f\u0014) 0\u001f..$;", "KZKUs");
        RenderItem.I[436 + 20 - 207 + 234] = I("5:=9$\u0019&!! ##", "FQHUH");
        RenderItem.I[293 + 56 - 308 + 443] = I("6.\u001a?#\u001a?\u0000>-, ", "EEoSO");
        RenderItem.I[421 + 155 - 507 + 416] = I(">\r\u0000;\u0007\u0012\u0005\u001d6\u0019", "MfuWk");
        RenderItem.I[192 + 81 - 0 + 213] = I("\u0002\t\u0012(\n.\u0001\u0015!\u0003\u0001\u0007\u0015", "qbgDf");
        RenderItem.I[243 + 303 - 292 + 233] = I("4\u0000\u0016\u001a\"#>\u000b\u0006\u00126>\u0017\u001c$4\n", "WadhM");
        RenderItem.I[145 + 7 + 238 + 98] = I("%7#)59\r$519", "KRWAP");
        RenderItem.I[194 + 10 - 176 + 461] = I("(/\u00056\u00011476\u0003=", "XZhFj");
        RenderItem.I[385 + 467 - 507 + 145] = I("\"&8/-+=!\u00159,.8-?", "DOJJZ");
        RenderItem.I[327 + 15 - 121 + 270] = I("\u0015\u0001:5\r\u0004\u000f#*\u001e", "vnWEl");
        RenderItem.I[445 + 185 - 479 + 341] = I("\u0000\u000b\u0010/!\u001c\f\u0016.'\u0005", "nndGD");
        RenderItem.I[345 + 330 - 281 + 99] = I("\u0018#9\u0007#\u0013", "iVXuW");
        RenderItem.I[119 + 245 - 275 + 405] = I("\u0007\u001f34\u0003\u001a\u001f\"\b\u000f\u0001\u0005", "sqGkn");
        RenderItem.I[92 + 254 - 113 + 262] = I("\u0003\"\u0013\u0006 \u0019\u0012\u000e\u001f+\u000e.\u0002\u00041", "kMcvE");
        RenderItem.I[467 + 277 - 349 + 101] = I("*\u001e\u0001\u001e*\u0014\u001f\u0018\u00106/", "KllqX");
        RenderItem.I[363 + 64 - 191 + 261] = I("\f'\u0004\u0004.\r:\u0019\u0019\u0014:4\u0019\u0007\u001e\u0017", "eUkjq");
        RenderItem.I[299 + 139 - 270 + 330] = I("1?>0\n8\u000f:;\u001d%5\r5\u001d;? ", "VPRTo");
        RenderItem.I[377 + 46 + 66 + 10] = I("\b\u001e\u001b%7\u0002\u0013% 7\u001e\u0004\u001f\u00179\u001e\u001a\u0015:", "lwzHX");
        RenderItem.I[100 + 153 - 121 + 368] = I("+\u0006\u0016\r", "Gcwiu");
        RenderItem.I[444 + 456 - 774 + 375] = I("\u001c$4\u00104\u0006$>", "rEYuk");
        RenderItem.I[357 + 5 - 170 + 310] = I("\u0018+\u000b*\u0001\u000e\u0011Yv", "jNhEs");
        RenderItem.I[495 + 392 - 597 + 213] = I("\u0011\"\u0019\u001a3\u0007\u0018\u0019\u00145", "cGzuA");
        RenderItem.I[127 + 17 - 38 + 398] = I("?\u0016\u00137>),\u00124#.\u0018\u0003", "MspXL");
        RenderItem.I[503 + 319 - 335 + 18] = I("\u001d\u0004\n>?\u000b>\n9$\u001d\u0011", "oaiQM");
        RenderItem.I[93 + 223 - 131 + 321] = I("\u001a=$6\n\f\u0007!8\n", "hXGYx");
        RenderItem.I[19 + 298 - 209 + 399] = I("\u001d\r5\u0015\n\u000b7;\u001b\u0014\u0003", "ohVzx");
        RenderItem.I[288 + 54 - 117 + 283] = I("32\u000b,'%\b\u0005&9-8\u0000*", "AWhCU");
        RenderItem.I[38 + 424 - 459 + 506] = I(";\u0004 +\u0000->00\u0013%", "IaCDr");
        RenderItem.I[154 + 400 - 72 + 28] = I("\u0017#+=+\u0001\u0019;&+\u0004\"", "eFHRY");
        RenderItem.I[194 + 243 - 425 + 499] = I(" 6%>\u00046\f10\u00046", "RSFQv");
        RenderItem.I[180 + 63 - 211 + 480] = I("\u0014\u00027\u00024\u00028e\\", "fgTmF");
        RenderItem.I[349 + 233 - 76 + 7] = I("9*&\u000b\u0011/\u00102\u0005\n?", "KOEdc");
        RenderItem.I[502 + 2 - 258 + 268] = I("\u0012%>'\u001e\u0003%>:\u0016=$?5\u0001\u0006", "bWWTs");
        RenderItem.I[83 + 117 + 125 + 190] = I("&\u0015\u0005\u0016\u001b7\u0015\u0005\u000b\u0013\t\u0004\u001e\u001c\u0005\"\u0006\u0000\u0016", "Vglev");
        RenderItem.I[511 + 480 - 862 + 387] = I("\f\t\u0019\u00150\u0001\u0002+\u001a=\u0000\u0005\u001f", "oftxQ");
        RenderItem.I[188 + 127 - 34 + 236] = I("\f3\u0007,#\u0005(\u001e:", "jZuIT");
        RenderItem.I[262 + 288 - 46 + 14] = I("5\n\t\u000e\u00118\u0001;\u0001\u001c9\u0006\u000f<\u001d?\u000b\u0001\u0000\u0011$\u0011", "Vedcp");
        RenderItem.I[432 + 334 - 298 + 51] = I("#\u000f$\u0005>$\u001c", "AnVwW");
        RenderItem.I[432 + 504 - 759 + 343] = I(" \b\u0001\u00162=\u0006\u0014'$?", "MgcIA");
        RenderItem.I[51 + 372 - 222 + 320] = I("\r'<%,\u001f;\n37\u0015>", "zUUQX");
        RenderItem.I[74 + 80 + 325 + 43] = I("27\u0019'\u0000\u000f(\u0003#\u0006\"*\u0019=12)\u00193\u0005", "PEvPn");
        RenderItem.I[237 + 228 - 433 + 491] = I("<\b\u0007\u0014\u0019;\u001e\u000b9\u001b!\u0000<)\u0018!\u000e\b", "NmcKt");
        RenderItem.I[182 + 462 - 365 + 245] = I("\u0007\u000b2=\n\r&6=\u0002", "cySZe");
    }
    
    private void renderEffect(final IBakedModel bakedModel) {
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.depthFunc(30 + 106 - 33 + 411);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(576 + 421 - 735 + 506, " ".length());
        this.textureManager.bindTexture(RenderItem.RES_ITEM_GLINT);
        GlStateManager.matrixMode(5669 + 3563 - 4264 + 922);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        GlStateManager.translate(Minecraft.getSystemTime() % 3000L / 3000.0f / 8.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-50.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(bakedModel, -(7328381 + 791749 - 6987400 + 7239290));
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0f, 8.0f, 8.0f);
        GlStateManager.translate(-(Minecraft.getSystemTime() % 4873L / 4873.0f / 8.0f), 0.0f, 0.0f);
        GlStateManager.rotate(10.0f, 0.0f, 0.0f, 1.0f);
        this.renderModel(bakedModel, -(3151943 + 4223674 + 548763 + 447640));
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5486 + 5094 - 6063 + 1371);
        GlStateManager.blendFunc(238 + 672 - 373 + 233, 767 + 39 - 149 + 114);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(488 + 25 - 280 + 282);
        GlStateManager.depthMask(" ".length() != 0);
        this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
    }
    
    private void registerItem(final Item item, final String s) {
        this.registerItem(item, "".length(), s);
    }
    
    private void renderModel(final IBakedModel bakedModel, final ItemStack itemStack) {
        this.renderModel(bakedModel, -" ".length(), itemStack);
    }
    
    public void renderItemOverlayIntoGUI(final FontRenderer fontRenderer, final ItemStack itemStack, final int n, final int n2, final String s) {
        if (itemStack != null) {
            if (itemStack.stackSize != " ".length() || s != null) {
                String value;
                if (s == null) {
                    value = String.valueOf(itemStack.stackSize);
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                else {
                    value = s;
                }
                String string = value;
                if (s == null && itemStack.stackSize < " ".length()) {
                    string = EnumChatFormatting.RED + String.valueOf(itemStack.stackSize);
                }
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableBlend();
                fontRenderer.drawStringWithShadow(string, n + (0x33 ^ 0x20) - "  ".length() - fontRenderer.getStringWidth(string), n2 + (0x2B ^ 0x2D) + "   ".length(), 15424180 + 2165799 - 1775323 + 962559);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            if (itemStack.isItemDamaged()) {
                final int n3 = (int)Math.round(13.0 - itemStack.getItemDamage() * 13.0 / itemStack.getMaxDamage());
                final int n4 = (int)Math.round(255.0 - itemStack.getItemDamage() * 255.0 / itemStack.getMaxDamage());
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.disableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
                this.func_181565_a(worldRenderer, n + "  ".length(), n2 + (0x57 ^ 0x5A), 0x8 ^ 0x5, "  ".length(), "".length(), "".length(), "".length(), 132 + 172 - 138 + 89);
                this.func_181565_a(worldRenderer, n + "  ".length(), n2 + (0x42 ^ 0x4F), 0x1B ^ 0x17, " ".length(), (68 + 73 - 92 + 206 - n4) / (0x22 ^ 0x26), 0x86 ^ 0xC6, "".length(), 135 + 161 - 286 + 245);
                this.func_181565_a(worldRenderer, n + "  ".length(), n2 + (0x7B ^ 0x76), n3, " ".length(), 112 + 65 - 120 + 198 - n4, n4, "".length(), 119 + 9 + 102 + 25);
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
    }
    
    private void putQuadNormal(final WorldRenderer worldRenderer, final BakedQuad bakedQuad) {
        final Vec3i directionVec = bakedQuad.getFace().getDirectionVec();
        worldRenderer.putNormal(directionVec.getX(), directionVec.getY(), directionVec.getZ());
    }
    
    private void registerItems() {
        this.registerBlock(Blocks.anvil, RenderItem.I[0x95 ^ 0x85]);
        this.registerBlock(Blocks.anvil, " ".length(), RenderItem.I[0xBD ^ 0xAC]);
        this.registerBlock(Blocks.anvil, "  ".length(), RenderItem.I[0x85 ^ 0x97]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLACK.getMetadata(), RenderItem.I[0x94 ^ 0x87]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.BLUE.getMetadata(), RenderItem.I[0x2C ^ 0x38]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.BROWN.getMetadata(), RenderItem.I[0xB3 ^ 0xA6]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.CYAN.getMetadata(), RenderItem.I[0x41 ^ 0x57]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.GRAY.getMetadata(), RenderItem.I[0x59 ^ 0x4E]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.GREEN.getMetadata(), RenderItem.I[0x39 ^ 0x21]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.LIGHT_BLUE.getMetadata(), RenderItem.I[0x84 ^ 0x9D]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.LIME.getMetadata(), RenderItem.I[0x5A ^ 0x40]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.getMetadata(), RenderItem.I[0x80 ^ 0x9B]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.getMetadata(), RenderItem.I[0x99 ^ 0x85]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.PINK.getMetadata(), RenderItem.I[0x5C ^ 0x41]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.getMetadata(), RenderItem.I[0x7B ^ 0x65]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.RED.getMetadata(), RenderItem.I[0xAF ^ 0xB0]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.SILVER.getMetadata(), RenderItem.I[0x8B ^ 0xAB]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.WHITE.getMetadata(), RenderItem.I[0x8 ^ 0x29]);
        this.registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.getMetadata(), RenderItem.I[0xC ^ 0x2E]);
        this.registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.MOSSY.getMetadata(), RenderItem.I[0x9C ^ 0xBF]);
        this.registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.NORMAL.getMetadata(), RenderItem.I[0x6 ^ 0x22]);
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), RenderItem.I[0x3B ^ 0x1E]);
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.DIRT.getMetadata(), RenderItem.I[0xA2 ^ 0x84]);
        this.registerBlock(Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata(), RenderItem.I[0x42 ^ 0x65]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.FERN.getMeta(), RenderItem.I[0x45 ^ 0x6D]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.GRASS.getMeta(), RenderItem.I[0x70 ^ 0x59]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), RenderItem.I[0x55 ^ 0x7F]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.ROSE.getMeta(), RenderItem.I[0xED ^ 0xC6]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), RenderItem.I[0x4D ^ 0x61]);
        this.registerBlock(Blocks.double_plant, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), RenderItem.I[0x39 ^ 0x14]);
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.BIRCH.getMetadata(), RenderItem.I[0x54 ^ 0x7A]);
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.JUNGLE.getMetadata(), RenderItem.I[0x5C ^ 0x73]);
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.OAK.getMetadata(), RenderItem.I[0x45 ^ 0x75]);
        this.registerBlock(Blocks.leaves, BlockPlanks.EnumType.SPRUCE.getMetadata(), RenderItem.I[0x7D ^ 0x4C]);
        this.registerBlock(Blocks.leaves2, BlockPlanks.EnumType.ACACIA.getMetadata() - (0xBC ^ 0xB8), RenderItem.I[0x65 ^ 0x57]);
        this.registerBlock(Blocks.leaves2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - (0xBC ^ 0xB8), RenderItem.I[0xBA ^ 0x89]);
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.BIRCH.getMetadata(), RenderItem.I[0x94 ^ 0xA0]);
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.JUNGLE.getMetadata(), RenderItem.I[0x14 ^ 0x21]);
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.OAK.getMetadata(), RenderItem.I[0xA7 ^ 0x91]);
        this.registerBlock(Blocks.log, BlockPlanks.EnumType.SPRUCE.getMetadata(), RenderItem.I[0xA0 ^ 0x97]);
        this.registerBlock(Blocks.log2, BlockPlanks.EnumType.ACACIA.getMetadata() - (0x50 ^ 0x54), RenderItem.I[0x96 ^ 0xAE]);
        this.registerBlock(Blocks.log2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - (0x9B ^ 0x9F), RenderItem.I[0x7D ^ 0x44]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), RenderItem.I[0x3A ^ 0x0]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), RenderItem.I[0x9C ^ 0xA7]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), RenderItem.I[0x15 ^ 0x29]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), RenderItem.I[0x7B ^ 0x46]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONE.getMetadata(), RenderItem.I[0x2B ^ 0x15]);
        this.registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONEBRICK.getMetadata(), RenderItem.I[0x4C ^ 0x73]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.ACACIA.getMetadata(), RenderItem.I[0x50 ^ 0x10]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.BIRCH.getMetadata(), RenderItem.I[0x19 ^ 0x58]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.DARK_OAK.getMetadata(), RenderItem.I[0x67 ^ 0x25]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.JUNGLE.getMetadata(), RenderItem.I[0xE2 ^ 0xA1]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.OAK.getMetadata(), RenderItem.I[0x44 ^ 0x0]);
        this.registerBlock(Blocks.planks, BlockPlanks.EnumType.SPRUCE.getMetadata(), RenderItem.I[0x24 ^ 0x61]);
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.BRICKS.getMetadata(), RenderItem.I[0xE ^ 0x48]);
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.DARK.getMetadata(), RenderItem.I[0x53 ^ 0x14]);
        this.registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.ROUGH.getMetadata(), RenderItem.I[0x15 ^ 0x5D]);
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.CHISELED.getMetadata(), RenderItem.I[0xE3 ^ 0xAA]);
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.DEFAULT.getMetadata(), RenderItem.I[0x1F ^ 0x55]);
        this.registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.LINES_Y.getMetadata(), RenderItem.I[0x1 ^ 0x4A]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.ALLIUM.getMeta(), RenderItem.I[0x37 ^ 0x7B]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), RenderItem.I[0xC7 ^ 0x8A]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), RenderItem.I[0xF3 ^ 0xBD]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), RenderItem.I[0x54 ^ 0x1B]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), RenderItem.I[0x6D ^ 0x3D]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), RenderItem.I[0x19 ^ 0x48]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.POPPY.getMeta(), RenderItem.I[0x48 ^ 0x1A]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), RenderItem.I[0xD0 ^ 0x83]);
        this.registerBlock(Blocks.red_flower, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), RenderItem.I[0x73 ^ 0x27]);
        this.registerBlock(Blocks.sand, BlockSand.EnumType.RED_SAND.getMetadata(), RenderItem.I[0x7E ^ 0x2B]);
        this.registerBlock(Blocks.sand, BlockSand.EnumType.SAND.getMetadata(), RenderItem.I[0x13 ^ 0x45]);
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.CHISELED.getMetadata(), RenderItem.I[0xC3 ^ 0x94]);
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.DEFAULT.getMetadata(), RenderItem.I[0x50 ^ 0x8]);
        this.registerBlock(Blocks.sandstone, BlockSandStone.EnumType.SMOOTH.getMetadata(), RenderItem.I[0x0 ^ 0x59]);
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.CHISELED.getMetadata(), RenderItem.I[0xE ^ 0x54]);
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.DEFAULT.getMetadata(), RenderItem.I[0xD0 ^ 0x8B]);
        this.registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.SMOOTH.getMetadata(), RenderItem.I[0xFA ^ 0xA6]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.ACACIA.getMetadata(), RenderItem.I[0x16 ^ 0x4B]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.BIRCH.getMetadata(), RenderItem.I[0xDC ^ 0x82]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.DARK_OAK.getMetadata(), RenderItem.I[0x23 ^ 0x7C]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.JUNGLE.getMetadata(), RenderItem.I[0xFD ^ 0x9D]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.OAK.getMetadata(), RenderItem.I[0x30 ^ 0x51]);
        this.registerBlock(Blocks.sapling, BlockPlanks.EnumType.SPRUCE.getMetadata(), RenderItem.I[0x62 ^ 0x0]);
        this.registerBlock(Blocks.sponge, "".length(), RenderItem.I[0x26 ^ 0x45]);
        this.registerBlock(Blocks.sponge, " ".length(), RenderItem.I[0x57 ^ 0x33]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BLACK.getMetadata(), RenderItem.I[0xC2 ^ 0xA7]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BLUE.getMetadata(), RenderItem.I[0x37 ^ 0x51]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.BROWN.getMetadata(), RenderItem.I[0x5A ^ 0x3D]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.CYAN.getMetadata(), RenderItem.I[0x42 ^ 0x2A]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.GRAY.getMetadata(), RenderItem.I[0x5A ^ 0x33]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.GREEN.getMetadata(), RenderItem.I[0xC6 ^ 0xAC]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.LIGHT_BLUE.getMetadata(), RenderItem.I[0xE4 ^ 0x8F]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.LIME.getMetadata(), RenderItem.I[0xD ^ 0x61]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.MAGENTA.getMetadata(), RenderItem.I[0x7 ^ 0x6A]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.ORANGE.getMetadata(), RenderItem.I[0x24 ^ 0x4A]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.PINK.getMetadata(), RenderItem.I[0xE8 ^ 0x87]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.PURPLE.getMetadata(), RenderItem.I[0x74 ^ 0x4]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.RED.getMetadata(), RenderItem.I[0x46 ^ 0x37]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.SILVER.getMetadata(), RenderItem.I[0x65 ^ 0x17]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.WHITE.getMetadata(), RenderItem.I[0x54 ^ 0x27]);
        this.registerBlock(Blocks.stained_glass, EnumDyeColor.YELLOW.getMetadata(), RenderItem.I[0xB ^ 0x7F]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BLACK.getMetadata(), RenderItem.I[0xF2 ^ 0x87]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BLUE.getMetadata(), RenderItem.I[0xE0 ^ 0x96]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.BROWN.getMetadata(), RenderItem.I[0xE9 ^ 0x9E]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.CYAN.getMetadata(), RenderItem.I[0xF2 ^ 0x8A]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.GRAY.getMetadata(), RenderItem.I[0xD2 ^ 0xAB]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.GREEN.getMetadata(), RenderItem.I[0x6F ^ 0x15]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.LIGHT_BLUE.getMetadata(), RenderItem.I[0x55 ^ 0x2E]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.LIME.getMetadata(), RenderItem.I[0x2A ^ 0x56]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.MAGENTA.getMetadata(), RenderItem.I[0x5E ^ 0x23]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.ORANGE.getMetadata(), RenderItem.I[0x7E ^ 0x0]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.PINK.getMetadata(), RenderItem.I[46 + 8 - 0 + 73]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.PURPLE.getMetadata(), RenderItem.I[25 + 115 - 90 + 78]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.RED.getMetadata(), RenderItem.I[47 + 8 + 11 + 63]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.SILVER.getMetadata(), RenderItem.I[93 + 20 - 110 + 127]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.WHITE.getMetadata(), RenderItem.I[97 + 123 - 124 + 35]);
        this.registerBlock(Blocks.stained_glass_pane, EnumDyeColor.YELLOW.getMetadata(), RenderItem.I[31 + 27 + 19 + 55]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLACK.getMetadata(), RenderItem.I[52 + 95 - 130 + 116]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLUE.getMetadata(), RenderItem.I[114 + 107 - 190 + 103]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BROWN.getMetadata(), RenderItem.I[9 + 4 + 101 + 21]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.CYAN.getMetadata(), RenderItem.I[0 + 93 - 89 + 132]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GRAY.getMetadata(), RenderItem.I[99 + 74 - 162 + 126]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GREEN.getMetadata(), RenderItem.I[49 + 7 - 5 + 87]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE.getMetadata(), RenderItem.I[36 + 103 - 20 + 20]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIME.getMetadata(), RenderItem.I[67 + 61 - 88 + 100]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.MAGENTA.getMetadata(), RenderItem.I[14 + 2 + 97 + 28]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.ORANGE.getMetadata(), RenderItem.I[12 + 94 + 26 + 10]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PINK.getMetadata(), RenderItem.I[80 + 123 - 71 + 11]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE.getMetadata(), RenderItem.I[80 + 40 - 75 + 99]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.RED.getMetadata(), RenderItem.I[101 + 48 - 73 + 69]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.SILVER.getMetadata(), RenderItem.I[95 + 37 - 84 + 98]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.WHITE.getMetadata(), RenderItem.I[61 + 99 - 83 + 70]);
        this.registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.YELLOW.getMetadata(), RenderItem.I[10 + 6 + 104 + 28]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE.getMetadata(), RenderItem.I[121 + 15 - 19 + 32]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), RenderItem.I[147 + 99 - 134 + 38]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE.getMetadata(), RenderItem.I[73 + 27 + 2 + 49]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), RenderItem.I[98 + 95 - 102 + 61]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE.getMetadata(), RenderItem.I[13 + 128 - 124 + 136]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), RenderItem.I[31 + 43 + 40 + 40]);
        this.registerBlock(Blocks.stone, BlockStone.EnumType.STONE.getMetadata(), RenderItem.I[32 + 51 + 48 + 24]);
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CRACKED.getMetadata(), RenderItem.I[48 + 124 - 117 + 101]);
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.DEFAULT.getMetadata(), RenderItem.I[91 + 136 - 186 + 116]);
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CHISELED.getMetadata(), RenderItem.I[10 + 148 - 138 + 138]);
        this.registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.MOSSY.getMetadata(), RenderItem.I[102 + 76 - 48 + 29]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.BRICK.getMetadata(), RenderItem.I[99 + 38 - 120 + 143]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), RenderItem.I[102 + 14 - 66 + 111]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.WOOD.getMetadata(), RenderItem.I[70 + 10 + 48 + 34]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), RenderItem.I[98 + 29 - 6 + 42]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.QUARTZ.getMetadata(), RenderItem.I[76 + 137 - 193 + 144]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.SAND.getMetadata(), RenderItem.I[33 + 143 - 76 + 65]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), RenderItem.I[128 + 125 - 129 + 42]);
        this.registerBlock(Blocks.stone_slab, BlockStoneSlab.EnumType.STONE.getMetadata(), RenderItem.I[44 + 151 - 66 + 38]);
        this.registerBlock(Blocks.stone_slab2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), RenderItem.I[32 + 103 - 117 + 150]);
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), RenderItem.I[14 + 61 + 4 + 90]);
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.FERN.getMeta(), RenderItem.I[80 + 105 - 160 + 145]);
        this.registerBlock(Blocks.tallgrass, BlockTallGrass.EnumType.GRASS.getMeta(), RenderItem.I[154 + 151 - 155 + 21]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.ACACIA.getMetadata(), RenderItem.I[118 + 86 - 199 + 167]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.BIRCH.getMetadata(), RenderItem.I[99 + 143 - 195 + 126]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.DARK_OAK.getMetadata(), RenderItem.I[38 + 11 + 55 + 70]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.JUNGLE.getMetadata(), RenderItem.I[130 + 52 - 45 + 38]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.OAK.getMetadata(), RenderItem.I[136 + 136 - 96 + 0]);
        this.registerBlock(Blocks.wooden_slab, BlockPlanks.EnumType.SPRUCE.getMetadata(), RenderItem.I[105 + 176 - 210 + 106]);
        this.registerBlock(Blocks.wool, EnumDyeColor.BLACK.getMetadata(), RenderItem.I[114 + 75 - 17 + 6]);
        this.registerBlock(Blocks.wool, EnumDyeColor.BLUE.getMetadata(), RenderItem.I[148 + 147 - 133 + 17]);
        this.registerBlock(Blocks.wool, EnumDyeColor.BROWN.getMetadata(), RenderItem.I[87 + 79 - 59 + 73]);
        this.registerBlock(Blocks.wool, EnumDyeColor.CYAN.getMetadata(), RenderItem.I[70 + 165 - 199 + 145]);
        this.registerBlock(Blocks.wool, EnumDyeColor.GRAY.getMetadata(), RenderItem.I[137 + 142 - 248 + 151]);
        this.registerBlock(Blocks.wool, EnumDyeColor.GREEN.getMetadata(), RenderItem.I[16 + 93 + 59 + 15]);
        this.registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.getMetadata(), RenderItem.I[78 + 50 - 103 + 159]);
        this.registerBlock(Blocks.wool, EnumDyeColor.LIME.getMetadata(), RenderItem.I[79 + 147 - 59 + 18]);
        this.registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.getMetadata(), RenderItem.I[26 + 10 - 32 + 182]);
        this.registerBlock(Blocks.wool, EnumDyeColor.ORANGE.getMetadata(), RenderItem.I[130 + 13 - 75 + 119]);
        this.registerBlock(Blocks.wool, EnumDyeColor.PINK.getMetadata(), RenderItem.I[180 + 116 - 222 + 114]);
        this.registerBlock(Blocks.wool, EnumDyeColor.PURPLE.getMetadata(), RenderItem.I[128 + 135 - 109 + 35]);
        this.registerBlock(Blocks.wool, EnumDyeColor.RED.getMetadata(), RenderItem.I[21 + 12 - 5 + 162]);
        this.registerBlock(Blocks.wool, EnumDyeColor.SILVER.getMetadata(), RenderItem.I[147 + 8 + 35 + 1]);
        this.registerBlock(Blocks.wool, EnumDyeColor.WHITE.getMetadata(), RenderItem.I[85 + 87 - 114 + 134]);
        this.registerBlock(Blocks.wool, EnumDyeColor.YELLOW.getMetadata(), RenderItem.I[15 + 74 + 22 + 82]);
        this.registerBlock(Blocks.acacia_stairs, RenderItem.I[83 + 5 + 58 + 48]);
        this.registerBlock(Blocks.activator_rail, RenderItem.I[62 + 27 + 11 + 95]);
        this.registerBlock(Blocks.beacon, RenderItem.I[191 + 8 - 193 + 190]);
        this.registerBlock(Blocks.bedrock, RenderItem.I[122 + 136 - 79 + 18]);
        this.registerBlock(Blocks.birch_stairs, RenderItem.I[15 + 122 + 28 + 33]);
        this.registerBlock(Blocks.bookshelf, RenderItem.I[167 + 119 - 190 + 103]);
        this.registerBlock(Blocks.brick_block, RenderItem.I[11 + 154 + 9 + 26]);
        this.registerBlock(Blocks.brick_block, RenderItem.I[187 + 52 - 182 + 144]);
        this.registerBlock(Blocks.brick_stairs, RenderItem.I[142 + 1 - 128 + 187]);
        this.registerBlock(Blocks.brown_mushroom, RenderItem.I[74 + 135 - 140 + 134]);
        this.registerBlock(Blocks.cactus, RenderItem.I[71 + 128 - 5 + 10]);
        this.registerBlock(Blocks.clay, RenderItem.I[55 + 120 - 123 + 153]);
        this.registerBlock(Blocks.coal_block, RenderItem.I[34 + 41 - 47 + 178]);
        this.registerBlock(Blocks.coal_ore, RenderItem.I[165 + 181 - 292 + 153]);
        this.registerBlock(Blocks.cobblestone, RenderItem.I[79 + 9 + 5 + 115]);
        this.registerBlock(Blocks.crafting_table, RenderItem.I[157 + 182 - 216 + 86]);
        this.registerBlock(Blocks.dark_oak_stairs, RenderItem.I[61 + 113 - 81 + 117]);
        this.registerBlock(Blocks.daylight_detector, RenderItem.I[26 + 187 - 31 + 29]);
        this.registerBlock(Blocks.deadbush, RenderItem.I[119 + 139 - 228 + 182]);
        this.registerBlock(Blocks.detector_rail, RenderItem.I[33 + 5 + 25 + 150]);
        this.registerBlock(Blocks.diamond_block, RenderItem.I[10 + 77 + 29 + 98]);
        this.registerBlock(Blocks.diamond_ore, RenderItem.I[29 + 4 - 6 + 188]);
        this.registerBlock(Blocks.dispenser, RenderItem.I[20 + 85 - 66 + 177]);
        this.registerBlock(Blocks.dropper, RenderItem.I[197 + 104 - 241 + 157]);
        this.registerBlock(Blocks.emerald_block, RenderItem.I[194 + 122 - 172 + 74]);
        this.registerBlock(Blocks.emerald_ore, RenderItem.I[99 + 38 - 5 + 87]);
        this.registerBlock(Blocks.enchanting_table, RenderItem.I[67 + 143 - 28 + 38]);
        this.registerBlock(Blocks.end_portal_frame, RenderItem.I[139 + 34 + 37 + 11]);
        this.registerBlock(Blocks.end_stone, RenderItem.I[190 + 177 - 242 + 97]);
        this.registerBlock(Blocks.oak_fence, RenderItem.I[198 + 0 - 8 + 33]);
        this.registerBlock(Blocks.spruce_fence, RenderItem.I[156 + 22 - 138 + 184]);
        this.registerBlock(Blocks.birch_fence, RenderItem.I[115 + 129 - 231 + 212]);
        this.registerBlock(Blocks.jungle_fence, RenderItem.I[68 + 131 + 24 + 3]);
        this.registerBlock(Blocks.dark_oak_fence, RenderItem.I[153 + 165 - 260 + 169]);
        this.registerBlock(Blocks.acacia_fence, RenderItem.I[132 + 205 - 318 + 209]);
        this.registerBlock(Blocks.oak_fence_gate, RenderItem.I[190 + 216 - 234 + 57]);
        this.registerBlock(Blocks.spruce_fence_gate, RenderItem.I[211 + 91 - 103 + 31]);
        this.registerBlock(Blocks.birch_fence_gate, RenderItem.I[11 + 122 + 66 + 32]);
        this.registerBlock(Blocks.jungle_fence_gate, RenderItem.I[174 + 18 - 45 + 85]);
        this.registerBlock(Blocks.dark_oak_fence_gate, RenderItem.I[50 + 74 - 107 + 216]);
        this.registerBlock(Blocks.acacia_fence_gate, RenderItem.I[17 + 65 + 78 + 74]);
        this.registerBlock(Blocks.furnace, RenderItem.I[17 + 1 + 94 + 123]);
        this.registerBlock(Blocks.glass, RenderItem.I[84 + 234 - 127 + 45]);
        this.registerBlock(Blocks.glass_pane, RenderItem.I[93 + 149 - 127 + 122]);
        this.registerBlock(Blocks.glowstone, RenderItem.I[174 + 136 - 75 + 3]);
        this.registerBlock(Blocks.golden_rail, RenderItem.I[133 + 50 - 96 + 152]);
        this.registerBlock(Blocks.gold_block, RenderItem.I[193 + 230 - 318 + 135]);
        this.registerBlock(Blocks.gold_ore, RenderItem.I[0 + 112 - 15 + 144]);
        this.registerBlock(Blocks.grass, RenderItem.I[75 + 175 - 200 + 192]);
        this.registerBlock(Blocks.gravel, RenderItem.I[33 + 232 - 83 + 61]);
        this.registerBlock(Blocks.hardened_clay, RenderItem.I[192 + 191 - 234 + 95]);
        this.registerBlock(Blocks.hay_block, RenderItem.I[176 + 232 - 384 + 221]);
        this.registerBlock(Blocks.heavy_weighted_pressure_plate, RenderItem.I[166 + 148 - 312 + 244]);
        this.registerBlock(Blocks.hopper, RenderItem.I[187 + 181 - 343 + 222]);
        this.registerBlock(Blocks.ice, RenderItem.I[231 + 185 - 296 + 128]);
        this.registerBlock(Blocks.iron_bars, RenderItem.I[125 + 80 - 76 + 120]);
        this.registerBlock(Blocks.iron_block, RenderItem.I[207 + 139 - 172 + 76]);
        this.registerBlock(Blocks.iron_ore, RenderItem.I[148 + 22 + 6 + 75]);
        this.registerBlock(Blocks.iron_trapdoor, RenderItem.I[246 + 200 - 330 + 136]);
        this.registerBlock(Blocks.jukebox, RenderItem.I[151 + 14 - 7 + 95]);
        this.registerBlock(Blocks.jungle_stairs, RenderItem.I[96 + 243 - 326 + 241]);
        this.registerBlock(Blocks.ladder, RenderItem.I[45 + 45 - 55 + 220]);
        this.registerBlock(Blocks.lapis_block, RenderItem.I[217 + 10 - 63 + 92]);
        this.registerBlock(Blocks.lapis_ore, RenderItem.I[23 + 179 - 142 + 197]);
        this.registerBlock(Blocks.lever, RenderItem.I[169 + 23 - 130 + 196]);
        this.registerBlock(Blocks.light_weighted_pressure_plate, RenderItem.I[89 + 31 + 92 + 47]);
        this.registerBlock(Blocks.lit_pumpkin, RenderItem.I[19 + 27 + 105 + 109]);
        this.registerBlock(Blocks.melon_block, RenderItem.I[24 + 243 - 192 + 186]);
        this.registerBlock(Blocks.mossy_cobblestone, RenderItem.I[63 + 85 + 102 + 12]);
        this.registerBlock(Blocks.mycelium, RenderItem.I[249 + 117 - 230 + 127]);
        this.registerBlock(Blocks.netherrack, RenderItem.I[180 + 107 - 154 + 131]);
        this.registerBlock(Blocks.nether_brick, RenderItem.I[19 + 167 - 60 + 139]);
        this.registerBlock(Blocks.nether_brick_fence, RenderItem.I[155 + 0 - 114 + 225]);
        this.registerBlock(Blocks.nether_brick_stairs, RenderItem.I[80 + 163 - 142 + 166]);
        this.registerBlock(Blocks.noteblock, RenderItem.I[162 + 133 - 212 + 185]);
        this.registerBlock(Blocks.oak_stairs, RenderItem.I[258 + 108 - 228 + 131]);
        this.registerBlock(Blocks.obsidian, RenderItem.I[8 + 203 + 20 + 39]);
        this.registerBlock(Blocks.packed_ice, RenderItem.I[128 + 0 + 68 + 75]);
        this.registerBlock(Blocks.piston, RenderItem.I[137 + 154 - 109 + 90]);
        this.registerBlock(Blocks.pumpkin, RenderItem.I[3 + 219 - 89 + 140]);
        this.registerBlock(Blocks.quartz_ore, RenderItem.I[145 + 169 - 102 + 62]);
        this.registerBlock(Blocks.quartz_stairs, RenderItem.I[31 + 68 + 147 + 29]);
        this.registerBlock(Blocks.rail, RenderItem.I[144 + 134 - 236 + 234]);
        this.registerBlock(Blocks.redstone_block, RenderItem.I[197 + 243 - 419 + 256]);
        this.registerBlock(Blocks.redstone_lamp, RenderItem.I[265 + 119 - 267 + 161]);
        this.registerBlock(Blocks.redstone_ore, RenderItem.I[32 + 0 + 160 + 87]);
        this.registerBlock(Blocks.redstone_torch, RenderItem.I[268 + 253 - 449 + 208]);
        this.registerBlock(Blocks.red_mushroom, RenderItem.I[107 + 69 + 84 + 21]);
        this.registerBlock(Blocks.sandstone_stairs, RenderItem.I[264 + 173 - 180 + 25]);
        this.registerBlock(Blocks.red_sandstone_stairs, RenderItem.I[20 + 124 - 77 + 216]);
        this.registerBlock(Blocks.sea_lantern, RenderItem.I[247 + 59 - 94 + 72]);
        this.registerBlock(Blocks.slime_block, RenderItem.I[45 + 231 - 189 + 198]);
        this.registerBlock(Blocks.snow, RenderItem.I[233 + 283 - 417 + 187]);
        this.registerBlock(Blocks.snow_layer, RenderItem.I[278 + 72 - 268 + 205]);
        this.registerBlock(Blocks.soul_sand, RenderItem.I[88 + 46 + 26 + 128]);
        this.registerBlock(Blocks.spruce_stairs, RenderItem.I[180 + 89 - 203 + 223]);
        this.registerBlock(Blocks.sticky_piston, RenderItem.I[275 + 98 - 148 + 65]);
        this.registerBlock(Blocks.stone_brick_stairs, RenderItem.I[133 + 118 - 35 + 75]);
        this.registerBlock(Blocks.stone_button, RenderItem.I[248 + 230 - 285 + 99]);
        this.registerBlock(Blocks.stone_pressure_plate, RenderItem.I[241 + 204 - 259 + 107]);
        this.registerBlock(Blocks.stone_stairs, RenderItem.I[90 + 122 - 135 + 217]);
        this.registerBlock(Blocks.tnt, RenderItem.I[253 + 117 - 168 + 93]);
        this.registerBlock(Blocks.torch, RenderItem.I[82 + 65 - 3 + 152]);
        this.registerBlock(Blocks.trapdoor, RenderItem.I[217 + 11 - 137 + 206]);
        this.registerBlock(Blocks.tripwire_hook, RenderItem.I[267 + 39 - 75 + 67]);
        this.registerBlock(Blocks.vine, RenderItem.I[201 + 103 - 76 + 71]);
        this.registerBlock(Blocks.waterlily, RenderItem.I[70 + 36 + 66 + 128]);
        this.registerBlock(Blocks.web, RenderItem.I[235 + 5 - 224 + 285]);
        this.registerBlock(Blocks.wooden_button, RenderItem.I[231 + 272 - 297 + 96]);
        this.registerBlock(Blocks.wooden_pressure_plate, RenderItem.I[201 + 16 + 70 + 16]);
        this.registerBlock(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION.getMeta(), RenderItem.I[132 + 115 + 35 + 22]);
        this.registerBlock(Blocks.chest, RenderItem.I[170 + 203 - 344 + 276]);
        this.registerBlock(Blocks.trapped_chest, RenderItem.I[272 + 49 - 77 + 62]);
        this.registerBlock(Blocks.ender_chest, RenderItem.I[34 + 176 - 0 + 97]);
        this.registerItem(Items.iron_shovel, RenderItem.I[161 + 17 - 123 + 253]);
        this.registerItem(Items.iron_pickaxe, RenderItem.I[59 + 294 - 117 + 73]);
        this.registerItem(Items.iron_axe, RenderItem.I[212 + 228 - 227 + 97]);
        this.registerItem(Items.flint_and_steel, RenderItem.I[224 + 298 - 343 + 132]);
        this.registerItem(Items.apple, RenderItem.I[166 + 252 - 358 + 252]);
        this.registerItem(Items.bow, "".length(), RenderItem.I[193 + 34 - 172 + 258]);
        this.registerItem(Items.bow, " ".length(), RenderItem.I[296 + 305 - 388 + 101]);
        this.registerItem(Items.bow, "  ".length(), RenderItem.I[111 + 285 - 360 + 279]);
        this.registerItem(Items.bow, "   ".length(), RenderItem.I[168 + 281 - 352 + 219]);
        this.registerItem(Items.arrow, RenderItem.I[31 + 208 + 16 + 62]);
        this.registerItem(Items.coal, "".length(), RenderItem.I[91 + 4 + 110 + 113]);
        this.registerItem(Items.coal, " ".length(), RenderItem.I[225 + 163 - 245 + 176]);
        this.registerItem(Items.diamond, RenderItem.I[228 + 191 - 336 + 237]);
        this.registerItem(Items.iron_ingot, RenderItem.I[202 + 133 - 155 + 141]);
        this.registerItem(Items.gold_ingot, RenderItem.I[37 + 223 - 165 + 227]);
        this.registerItem(Items.iron_sword, RenderItem.I[64 + 166 - 138 + 231]);
        this.registerItem(Items.wooden_sword, RenderItem.I[23 + 91 + 12 + 198]);
        this.registerItem(Items.wooden_shovel, RenderItem.I[225 + 206 - 117 + 11]);
        this.registerItem(Items.wooden_pickaxe, RenderItem.I[248 + 214 - 174 + 38]);
        this.registerItem(Items.wooden_axe, RenderItem.I[2 + 15 + 85 + 225]);
        this.registerItem(Items.stone_sword, RenderItem.I[260 + 7 - 258 + 319]);
        this.registerItem(Items.stone_shovel, RenderItem.I[313 + 51 - 134 + 99]);
        this.registerItem(Items.stone_pickaxe, RenderItem.I[54 + 64 + 199 + 13]);
        this.registerItem(Items.stone_axe, RenderItem.I[317 + 37 - 346 + 323]);
        this.registerItem(Items.diamond_sword, RenderItem.I[114 + 288 - 199 + 129]);
        this.registerItem(Items.diamond_shovel, RenderItem.I[126 + 308 - 287 + 186]);
        this.registerItem(Items.diamond_pickaxe, RenderItem.I[15 + 327 - 230 + 222]);
        this.registerItem(Items.diamond_axe, RenderItem.I[254 + 245 - 370 + 206]);
        this.registerItem(Items.stick, RenderItem.I[157 + 203 - 48 + 24]);
        this.registerItem(Items.bowl, RenderItem.I[242 + 107 - 152 + 140]);
        this.registerItem(Items.mushroom_stew, RenderItem.I[246 + 317 - 362 + 137]);
        this.registerItem(Items.golden_sword, RenderItem.I[49 + 75 + 124 + 91]);
        this.registerItem(Items.golden_shovel, RenderItem.I[322 + 139 - 236 + 115]);
        this.registerItem(Items.golden_pickaxe, RenderItem.I[5 + 180 - 141 + 297]);
        this.registerItem(Items.golden_axe, RenderItem.I[157 + 22 - 105 + 268]);
        this.registerItem(Items.string, RenderItem.I[67 + 128 - 45 + 193]);
        this.registerItem(Items.feather, RenderItem.I[26 + 154 - 110 + 274]);
        this.registerItem(Items.gunpowder, RenderItem.I[189 + 67 - 69 + 158]);
        this.registerItem(Items.wooden_hoe, RenderItem.I[41 + 41 - 25 + 289]);
        this.registerItem(Items.stone_hoe, RenderItem.I[12 + 293 - 271 + 313]);
        this.registerItem(Items.iron_hoe, RenderItem.I[191 + 309 - 471 + 319]);
        this.registerItem(Items.diamond_hoe, RenderItem.I[326 + 52 - 232 + 203]);
        this.registerItem(Items.golden_hoe, RenderItem.I[186 + 325 - 310 + 149]);
        this.registerItem(Items.wheat_seeds, RenderItem.I[293 + 164 - 153 + 47]);
        this.registerItem(Items.wheat, RenderItem.I[309 + 142 - 298 + 199]);
        this.registerItem(Items.bread, RenderItem.I[14 + 299 - 143 + 183]);
        this.registerItem(Items.leather_helmet, RenderItem.I[339 + 13 - 88 + 90]);
        this.registerItem(Items.leather_chestplate, RenderItem.I[232 + 265 - 387 + 245]);
        this.registerItem(Items.leather_leggings, RenderItem.I[5 + 13 + 52 + 286]);
        this.registerItem(Items.leather_boots, RenderItem.I[129 + 177 - 73 + 124]);
        this.registerItem(Items.chainmail_helmet, RenderItem.I[65 + 226 - 287 + 354]);
        this.registerItem(Items.chainmail_chestplate, RenderItem.I[150 + 117 - 100 + 192]);
        this.registerItem(Items.chainmail_leggings, RenderItem.I[165 + 203 - 189 + 181]);
        this.registerItem(Items.chainmail_boots, RenderItem.I[345 + 256 - 485 + 245]);
        this.registerItem(Items.iron_helmet, RenderItem.I[114 + 316 - 371 + 303]);
        this.registerItem(Items.iron_chestplate, RenderItem.I[305 + 36 - 257 + 279]);
        this.registerItem(Items.iron_leggings, RenderItem.I[208 + 8 + 67 + 81]);
        this.registerItem(Items.iron_boots, RenderItem.I[95 + 62 + 128 + 80]);
        this.registerItem(Items.diamond_helmet, RenderItem.I[299 + 195 - 351 + 223]);
        this.registerItem(Items.diamond_chestplate, RenderItem.I[321 + 138 - 213 + 121]);
        this.registerItem(Items.diamond_leggings, RenderItem.I[81 + 111 - 140 + 316]);
        this.registerItem(Items.diamond_boots, RenderItem.I[137 + 266 - 205 + 171]);
        this.registerItem(Items.golden_helmet, RenderItem.I[123 + 241 - 272 + 278]);
        this.registerItem(Items.golden_chestplate, RenderItem.I[351 + 116 - 337 + 241]);
        this.registerItem(Items.golden_leggings, RenderItem.I[254 + 276 - 334 + 176]);
        this.registerItem(Items.golden_boots, RenderItem.I[99 + 92 + 138 + 44]);
        this.registerItem(Items.flint, RenderItem.I[51 + 191 - 199 + 331]);
        this.registerItem(Items.porkchop, RenderItem.I[347 + 160 - 378 + 246]);
        this.registerItem(Items.cooked_porkchop, RenderItem.I[107 + 226 + 23 + 20]);
        this.registerItem(Items.painting, RenderItem.I[7 + 375 - 152 + 147]);
        this.registerItem(Items.golden_apple, RenderItem.I[209 + 235 - 282 + 216]);
        this.registerItem(Items.golden_apple, " ".length(), RenderItem.I[167 + 75 - 106 + 243]);
        this.registerItem(Items.sign, RenderItem.I[330 + 324 - 585 + 311]);
        this.registerItem(Items.oak_door, RenderItem.I[205 + 51 - 242 + 367]);
        this.registerItem(Items.spruce_door, RenderItem.I[301 + 36 - 50 + 95]);
        this.registerItem(Items.birch_door, RenderItem.I[265 + 11 - 91 + 198]);
        this.registerItem(Items.jungle_door, RenderItem.I[375 + 246 - 453 + 216]);
        this.registerItem(Items.acacia_door, RenderItem.I[6 + 204 - 37 + 212]);
        this.registerItem(Items.dark_oak_door, RenderItem.I[132 + 190 - 59 + 123]);
        this.registerItem(Items.bucket, RenderItem.I[197 + 52 - 136 + 274]);
        this.registerItem(Items.water_bucket, RenderItem.I[300 + 73 - 245 + 260]);
        this.registerItem(Items.lava_bucket, RenderItem.I[105 + 55 - 126 + 355]);
        this.registerItem(Items.minecart, RenderItem.I[52 + 314 - 201 + 225]);
        this.registerItem(Items.saddle, RenderItem.I[93 + 291 - 186 + 193]);
        this.registerItem(Items.iron_door, RenderItem.I[202 + 199 - 63 + 54]);
        this.registerItem(Items.redstone, RenderItem.I[69 + 132 + 59 + 133]);
        this.registerItem(Items.snowball, RenderItem.I[78 + 300 - 241 + 257]);
        this.registerItem(Items.boat, RenderItem.I[288 + 65 - 315 + 357]);
        this.registerItem(Items.leather, RenderItem.I[199 + 196 - 229 + 230]);
        this.registerItem(Items.milk_bucket, RenderItem.I[186 + 207 - 327 + 331]);
        this.registerItem(Items.brick, RenderItem.I[376 + 256 - 515 + 281]);
        this.registerItem(Items.clay_ball, RenderItem.I[342 + 240 - 504 + 321]);
        this.registerItem(Items.reeds, RenderItem.I[347 + 395 - 435 + 93]);
        this.registerItem(Items.paper, RenderItem.I[48 + 363 - 390 + 380]);
        this.registerItem(Items.book, RenderItem.I[23 + 169 + 174 + 36]);
        this.registerItem(Items.slime_ball, RenderItem.I[285 + 400 - 427 + 145]);
        this.registerItem(Items.chest_minecart, RenderItem.I[287 + 297 - 401 + 221]);
        this.registerItem(Items.furnace_minecart, RenderItem.I[69 + 358 - 226 + 204]);
        this.registerItem(Items.egg, RenderItem.I[260 + 61 - 193 + 278]);
        this.registerItem(Items.compass, RenderItem.I[276 + 192 - 97 + 36]);
        this.registerItem(Items.fishing_rod, RenderItem.I[270 + 330 - 543 + 351]);
        this.registerItem(Items.fishing_rod, " ".length(), RenderItem.I[248 + 254 - 462 + 369]);
        this.registerItem(Items.clock, RenderItem.I[181 + 4 - 116 + 341]);
        this.registerItem(Items.glowstone_dust, RenderItem.I[3 + 302 + 13 + 93]);
        this.registerItem(Items.fish, ItemFishFood.FishType.COD.getMetadata(), RenderItem.I[24 + 357 - 217 + 248]);
        this.registerItem(Items.fish, ItemFishFood.FishType.SALMON.getMetadata(), RenderItem.I[289 + 370 - 458 + 212]);
        this.registerItem(Items.fish, ItemFishFood.FishType.CLOWNFISH.getMetadata(), RenderItem.I[348 + 283 - 404 + 187]);
        this.registerItem(Items.fish, ItemFishFood.FishType.PUFFERFISH.getMetadata(), RenderItem.I[74 + 249 - 80 + 172]);
        this.registerItem(Items.cooked_fish, ItemFishFood.FishType.COD.getMetadata(), RenderItem.I[341 + 85 - 311 + 301]);
        this.registerItem(Items.cooked_fish, ItemFishFood.FishType.SALMON.getMetadata(), RenderItem.I[188 + 149 + 5 + 75]);
        this.registerItem(Items.dye, EnumDyeColor.BLACK.getDyeDamage(), RenderItem.I[363 + 268 - 537 + 324]);
        this.registerItem(Items.dye, EnumDyeColor.RED.getDyeDamage(), RenderItem.I[348 + 251 - 264 + 84]);
        this.registerItem(Items.dye, EnumDyeColor.GREEN.getDyeDamage(), RenderItem.I[297 + 114 - 289 + 298]);
        this.registerItem(Items.dye, EnumDyeColor.BROWN.getDyeDamage(), RenderItem.I[274 + 361 - 410 + 196]);
        this.registerItem(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), RenderItem.I[95 + 267 - 95 + 155]);
        this.registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeDamage(), RenderItem.I[352 + 417 - 644 + 298]);
        this.registerItem(Items.dye, EnumDyeColor.CYAN.getDyeDamage(), RenderItem.I[358 + 321 - 565 + 310]);
        this.registerItem(Items.dye, EnumDyeColor.SILVER.getDyeDamage(), RenderItem.I[283 + 397 - 578 + 323]);
        this.registerItem(Items.dye, EnumDyeColor.GRAY.getDyeDamage(), RenderItem.I[95 + 59 - 56 + 328]);
        this.registerItem(Items.dye, EnumDyeColor.PINK.getDyeDamage(), RenderItem.I[86 + 55 - 120 + 406]);
        this.registerItem(Items.dye, EnumDyeColor.LIME.getDyeDamage(), RenderItem.I[70 + 301 - 214 + 271]);
        this.registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeDamage(), RenderItem.I[154 + 3 - 55 + 327]);
        this.registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeDamage(), RenderItem.I[18 + 34 + 316 + 62]);
        this.registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeDamage(), RenderItem.I[5 + 330 - 257 + 353]);
        this.registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeDamage(), RenderItem.I[120 + 155 - 268 + 425]);
        this.registerItem(Items.dye, EnumDyeColor.WHITE.getDyeDamage(), RenderItem.I[35 + 201 - 20 + 217]);
        this.registerItem(Items.bone, RenderItem.I[214 + 69 + 51 + 100]);
        this.registerItem(Items.sugar, RenderItem.I[400 + 373 - 535 + 197]);
        this.registerItem(Items.cake, RenderItem.I[356 + 373 - 642 + 349]);
        this.registerItem(Items.bed, RenderItem.I[114 + 95 + 96 + 132]);
        this.registerItem(Items.repeater, RenderItem.I[282 + 103 - 367 + 420]);
        this.registerItem(Items.cookie, RenderItem.I[405 + 37 - 300 + 297]);
        this.registerItem(Items.shears, RenderItem.I[198 + 252 - 240 + 230]);
        this.registerItem(Items.melon, RenderItem.I[161 + 193 - 323 + 410]);
        this.registerItem(Items.pumpkin_seeds, RenderItem.I[118 + 395 - 395 + 324]);
        this.registerItem(Items.melon_seeds, RenderItem.I[323 + 360 - 334 + 94]);
        this.registerItem(Items.beef, RenderItem.I[224 + 308 - 435 + 347]);
        this.registerItem(Items.cooked_beef, RenderItem.I[224 + 232 - 360 + 349]);
        this.registerItem(Items.chicken, RenderItem.I[135 + 426 - 365 + 250]);
        this.registerItem(Items.cooked_chicken, RenderItem.I[388 + 424 - 498 + 133]);
        this.registerItem(Items.rabbit, RenderItem.I[251 + 389 - 530 + 338]);
        this.registerItem(Items.cooked_rabbit, RenderItem.I[270 + 300 - 381 + 260]);
        this.registerItem(Items.mutton, RenderItem.I[384 + 326 - 442 + 182]);
        this.registerItem(Items.cooked_mutton, RenderItem.I[29 + 35 + 157 + 230]);
        this.registerItem(Items.rabbit_foot, RenderItem.I[144 + 396 - 170 + 82]);
        this.registerItem(Items.rabbit_hide, RenderItem.I[184 + 229 - 118 + 158]);
        this.registerItem(Items.rabbit_stew, RenderItem.I[89 + 31 - 74 + 408]);
        this.registerItem(Items.rotten_flesh, RenderItem.I[190 + 420 - 430 + 275]);
        this.registerItem(Items.ender_pearl, RenderItem.I[357 + 19 - 305 + 385]);
        this.registerItem(Items.blaze_rod, RenderItem.I[28 + 285 - 307 + 451]);
        this.registerItem(Items.ghast_tear, RenderItem.I[325 + 410 - 528 + 251]);
        this.registerItem(Items.gold_nugget, RenderItem.I[111 + 374 - 174 + 148]);
        this.registerItem(Items.nether_wart, RenderItem.I[315 + 314 - 553 + 384]);
        this.itemModelMesher.register(Items.potionitem, new ItemMeshDefinition(this) {
            private static final String[] I;
            final RenderItem this$0;
            
            private static void I() {
                (I = new String[0x43 ^ 0x47])["".length()] = I("5*-,?2\u001a*(?661", "WEYXS");
                RenderItem$5.I[" ".length()] = I("#;\u001e&\u0005>:\u001a:", "JUhCk");
                RenderItem$5.I["  ".length()] = I(":\u0006%\u0004+=65\u0002.6\u00020\u0012+=", "XiQpG");
                RenderItem$5.I["   ".length()] = I("\"\u0018\u0001\u0001;?\u0019\u0005\u001d", "KvwdU");
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
                    if (4 <= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                ModelResourceLocation modelResourceLocation;
                if (ItemPotion.isSplash(itemStack.getMetadata())) {
                    modelResourceLocation = new ModelResourceLocation(RenderItem$5.I["".length()], RenderItem$5.I[" ".length()]);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    modelResourceLocation = new ModelResourceLocation(RenderItem$5.I["  ".length()], RenderItem$5.I["   ".length()]);
                }
                return modelResourceLocation;
            }
        });
        this.registerItem(Items.glass_bottle, RenderItem.I[254 + 19 - 159 + 347]);
        this.registerItem(Items.spider_eye, RenderItem.I[87 + 453 - 432 + 354]);
        this.registerItem(Items.fermented_spider_eye, RenderItem.I[307 + 131 - 410 + 435]);
        this.registerItem(Items.blaze_powder, RenderItem.I[4 + 171 + 263 + 26]);
        this.registerItem(Items.magma_cream, RenderItem.I[269 + 266 - 443 + 373]);
        this.registerItem(Items.brewing_stand, RenderItem.I[258 + 291 - 405 + 322]);
        this.registerItem(Items.cauldron, RenderItem.I[305 + 200 - 493 + 455]);
        this.registerItem(Items.ender_eye, RenderItem.I[377 + 13 + 38 + 40]);
        this.registerItem(Items.speckled_melon, RenderItem.I[439 + 103 - 531 + 458]);
        this.itemModelMesher.register(Items.spawn_egg, new ItemMeshDefinition(this) {
            private static final String[] I;
            final RenderItem this$0;
            
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
                    if (4 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u001b\u0004,\u0004\u00197\u0011*\u0014", "htMsw");
                RenderItem$6.I[" ".length()] = I("\u0018'\u0017(?\u0005&\u00134", "qIaMQ");
            }
            
            static {
                I();
            }
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation(RenderItem$6.I["".length()], RenderItem$6.I[" ".length()]);
            }
        });
        this.registerItem(Items.experience_bottle, RenderItem.I[106 + 13 + 326 + 25]);
        this.registerItem(Items.fire_charge, RenderItem.I[73 + 251 - 122 + 269]);
        this.registerItem(Items.writable_book, RenderItem.I[56 + 213 + 28 + 175]);
        this.registerItem(Items.emerald, RenderItem.I[376 + 360 - 693 + 430]);
        this.registerItem(Items.item_frame, RenderItem.I[430 + 279 - 236 + 1]);
        this.registerItem(Items.flower_pot, RenderItem.I[144 + 8 + 27 + 296]);
        this.registerItem(Items.carrot, RenderItem.I[316 + 286 - 367 + 241]);
        this.registerItem(Items.potato, RenderItem.I[300 + 45 - 87 + 219]);
        this.registerItem(Items.baked_potato, RenderItem.I[258 + 394 - 434 + 260]);
        this.registerItem(Items.poisonous_potato, RenderItem.I[38 + 442 - 177 + 176]);
        this.registerItem(Items.map, RenderItem.I[151 + 68 - 173 + 434]);
        this.registerItem(Items.golden_carrot, RenderItem.I[381 + 476 - 600 + 224]);
        this.registerItem(Items.skull, "".length(), RenderItem.I[168 + 109 - 91 + 296]);
        this.registerItem(Items.skull, " ".length(), RenderItem.I[115 + 6 + 204 + 158]);
        this.registerItem(Items.skull, "  ".length(), RenderItem.I[390 + 324 - 278 + 48]);
        this.registerItem(Items.skull, "   ".length(), RenderItem.I[422 + 117 - 477 + 423]);
        this.registerItem(Items.skull, 0xB4 ^ 0xB0, RenderItem.I[206 + 388 - 160 + 52]);
        this.registerItem(Items.carrot_on_a_stick, RenderItem.I[288 + 328 - 358 + 229]);
        this.registerItem(Items.nether_star, RenderItem.I[50 + 50 + 340 + 48]);
        this.registerItem(Items.pumpkin_pie, RenderItem.I[458 + 361 - 677 + 347]);
        this.registerItem(Items.firework_charge, RenderItem.I[317 + 325 - 173 + 21]);
        this.registerItem(Items.comparator, RenderItem.I[209 + 23 + 69 + 190]);
        this.registerItem(Items.netherbrick, RenderItem.I[128 + 37 + 280 + 47]);
        this.registerItem(Items.quartz, RenderItem.I[205 + 168 - 344 + 464]);
        this.registerItem(Items.tnt_minecart, RenderItem.I[126 + 313 - 7 + 62]);
        this.registerItem(Items.hopper_minecart, RenderItem.I[444 + 251 - 208 + 8]);
        this.registerItem(Items.armor_stand, RenderItem.I[407 + 109 - 385 + 365]);
        this.registerItem(Items.iron_horse_armor, RenderItem.I[307 + 32 - 201 + 359]);
        this.registerItem(Items.golden_horse_armor, RenderItem.I[393 + 391 - 319 + 33]);
        this.registerItem(Items.diamond_horse_armor, RenderItem.I[16 + 471 - 456 + 468]);
        this.registerItem(Items.lead, RenderItem.I[304 + 167 - 43 + 72]);
        this.registerItem(Items.name_tag, RenderItem.I[394 + 407 - 511 + 211]);
        this.itemModelMesher.register(Items.banner, new ItemMeshDefinition(this) {
            final RenderItem this$0;
            private static final String[] I;
            
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
                    if (4 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("$':+*4", "FFTEO");
                RenderItem$7.I[" ".length()] = I("<\u0014\u001d\u00114!\u0015\u0019\r", "UzktZ");
            }
            
            static {
                I();
            }
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation(RenderItem$7.I["".length()], RenderItem$7.I[" ".length()]);
            }
        });
        this.registerItem(Items.record_13, RenderItem.I[461 + 47 - 61 + 55]);
        this.registerItem(Items.record_cat, RenderItem.I[256 + 424 - 224 + 47]);
        this.registerItem(Items.record_blocks, RenderItem.I[194 + 332 - 183 + 161]);
        this.registerItem(Items.record_chirp, RenderItem.I[281 + 450 - 326 + 100]);
        this.registerItem(Items.record_far, RenderItem.I[140 + 35 + 104 + 227]);
        this.registerItem(Items.record_mall, RenderItem.I[328 + 339 - 263 + 103]);
        this.registerItem(Items.record_mellohi, RenderItem.I[326 + 438 - 306 + 50]);
        this.registerItem(Items.record_stal, RenderItem.I[136 + 442 - 375 + 306]);
        this.registerItem(Items.record_strad, RenderItem.I[380 + 278 - 305 + 157]);
        this.registerItem(Items.record_ward, RenderItem.I[163 + 361 - 479 + 466]);
        this.registerItem(Items.record_11, RenderItem.I[373 + 34 - 107 + 212]);
        this.registerItem(Items.record_wait, RenderItem.I[392 + 352 - 323 + 92]);
        this.registerItem(Items.prismarine_shard, RenderItem.I[414 + 167 - 197 + 130]);
        this.registerItem(Items.prismarine_crystals, RenderItem.I[413 + 137 - 128 + 93]);
        this.itemModelMesher.register(Items.enchanted_book, new ItemMeshDefinition(this) {
            final RenderItem this$0;
            private static final String[] I;
            
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
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation(RenderItem$8.I["".length()], RenderItem$8.I[" ".length()]);
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u0010\"0\u00054\u001b86\t\n\u0017#<\u0006", "uLSmU");
                RenderItem$8.I[" ".length()] = I("\u0005\u001a\u001e\u0011\u0019\u0018\u001b\u001a\r", "lthtw");
            }
            
            static {
                I();
            }
        });
        this.itemModelMesher.register(Items.filled_map, new ItemMeshDefinition(this) {
            final RenderItem this$0;
            private static final String[] I;
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("\u0013\n+* \u0011<*'5", "ucGFE");
                RenderItem$9.I[" ".length()] = I("\u0001$:5\u0014\u001c%>)", "hJLPz");
            }
            
            @Override
            public ModelResourceLocation getModelLocation(final ItemStack itemStack) {
                return new ModelResourceLocation(RenderItem$9.I["".length()], RenderItem$9.I[" ".length()]);
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
                    if (false) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.registerBlock(Blocks.command_block, RenderItem.I[101 + 34 + 375 + 6]);
        this.registerItem(Items.fireworks, RenderItem.I[53 + 51 - 22 + 435]);
        this.registerItem(Items.command_block_minecart, RenderItem.I[12 + 283 + 42 + 181]);
        this.registerBlock(Blocks.barrier, RenderItem.I[314 + 471 - 445 + 179]);
        this.registerBlock(Blocks.mob_spawner, RenderItem.I[195 + 78 - 60 + 307]);
        this.registerItem(Items.written_book, RenderItem.I[389 + 237 - 463 + 358]);
        this.registerBlock(Blocks.brown_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), RenderItem.I[352 + 368 - 704 + 506]);
        this.registerBlock(Blocks.red_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), RenderItem.I[489 + 383 - 534 + 185]);
        this.registerBlock(Blocks.dragon_egg, RenderItem.I[22 + 143 + 166 + 193]);
    }
    
    private void renderQuad(final WorldRenderer worldRenderer, final BakedQuad bakedQuad, final int n) {
        worldRenderer.addVertexData(bakedQuad.getVertexData());
        worldRenderer.putColor4(n);
        this.putQuadNormal(worldRenderer, bakedQuad);
    }
    
    protected void registerBlock(final Block block, final int n, final String s) {
        this.registerItem(Item.getItemFromBlock(block), n, s);
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.itemModelMesher.rebuildCache();
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean shouldRenderItemIn3D(final ItemStack itemStack) {
        final IBakedModel itemModel = this.itemModelMesher.getItemModel(itemStack);
        int n;
        if (itemModel == null) {
            n = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n = (itemModel.isGui3d() ? 1 : 0);
        }
        return n != 0;
    }
    
    public void renderItem(final ItemStack itemStack, final IBakedModel bakedModel) {
        if (itemStack != null) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            if (bakedModel.isBuiltInRenderer()) {
                GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(-0.5f, -0.5f, -0.5f);
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableRescaleNormal();
                TileEntityItemStackRenderer.instance.renderByItem(itemStack);
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                GlStateManager.translate(-0.5f, -0.5f, -0.5f);
                this.renderModel(bakedModel, itemStack);
                if (itemStack.hasEffect()) {
                    this.renderEffect(bakedModel);
                }
            }
            GlStateManager.popMatrix();
        }
    }
    
    private void renderQuads(final WorldRenderer worldRenderer, final List<BakedQuad> list, final int n, final ItemStack itemStack) {
        int n2;
        if (n == -" ".length() && itemStack != null) {
            n2 = " ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n2;
        int i = "".length();
        final int size = list.size();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < size) {
            final BakedQuad bakedQuad = list.get(i);
            int n4 = n;
            if (n3 != 0 && bakedQuad.hasTintIndex()) {
                int n5 = itemStack.getItem().getColorFromItemStack(itemStack, bakedQuad.getTintIndex());
                if (EntityRenderer.anaglyphEnable) {
                    n5 = TextureUtil.anaglyphColor(n5);
                }
                n4 = (n5 | -(4369454 + 5238800 + 5131939 + 2037023));
            }
            this.renderQuad(worldRenderer, bakedQuad, n4);
            ++i;
        }
    }
}
