package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiFlatPresets extends GuiScreen
{
    private final GuiCreateFlatWorld parentScreen;
    private GuiButton field_146434_t;
    private static final List<LayerItem> FLAT_WORLD_PRESETS;
    private GuiTextField field_146433_u;
    private String presetsTitle;
    private static final String[] I;
    private String field_146436_r;
    private String presetsShare;
    private ListSlot field_146435_s;
    
    public void func_146426_g() {
        this.field_146434_t.enabled = this.func_146430_p();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146435_s.handleMouseInput();
    }
    
    public GuiFlatPresets(final GuiCreateFlatWorld parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    private static void func_175354_a(final String s, final Item item, final int n, final BiomeGenBase biomeGenBase, final List<String> list, final FlatLayerInfo... array) {
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        int i = array.length - " ".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i >= 0) {
            flatGeneratorInfo.getFlatLayers().add(array[i]);
            --i;
        }
        flatGeneratorInfo.setBiome(biomeGenBase.biomeID);
        flatGeneratorInfo.func_82645_d();
        if (list != null) {
            final Iterator<String> iterator = list.iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                flatGeneratorInfo.getWorldFeatures().put(iterator.next(), Maps.newHashMap());
            }
        }
        GuiFlatPresets.FLAT_WORLD_PRESETS.add(new LayerItem(item, n, s, flatGeneratorInfo.toString()));
    }
    
    static List access$0() {
        return GuiFlatPresets.FLAT_WORLD_PRESETS;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.presetsTitle = I18n.format(GuiFlatPresets.I[0x78 ^ 0x5A], new Object["".length()]);
        this.presetsShare = I18n.format(GuiFlatPresets.I[0x8B ^ 0xA8], new Object["".length()]);
        this.field_146436_r = I18n.format(GuiFlatPresets.I[0xA3 ^ 0x87], new Object["".length()]);
        this.field_146433_u = new GuiTextField("  ".length(), this.fontRendererObj, 0x50 ^ 0x62, 0xB7 ^ 0x9F, this.width - (0x63 ^ 0x7), 0xAF ^ 0xBB);
        this.field_146435_s = new ListSlot();
        this.field_146433_u.setMaxStringLength(459 + 1224 - 1038 + 585);
        this.field_146433_u.setText(this.parentScreen.func_146384_e());
        this.buttonList.add(this.field_146434_t = new GuiButton("".length(), this.width / "  ".length() - (92 + 64 - 99 + 98), this.height - (0x2 ^ 0x1E), 42 + 112 - 86 + 82, 0x6 ^ 0x12, I18n.format(GuiFlatPresets.I[0x5B ^ 0x7E], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() + (0xA9 ^ 0xAC), this.height - (0xF ^ 0x13), 134 + 138 - 206 + 84, 0x7D ^ 0x69, I18n.format(GuiFlatPresets.I[0xA1 ^ 0x87], new Object["".length()])));
        this.func_146426_g();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0 && this.func_146430_p()) {
            this.parentScreen.func_146383_a(this.field_146433_u.getText());
            this.mc.displayGuiScreen(this.parentScreen);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    static ListSlot access$2(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146435_s;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_146433_u.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    static {
        I();
        FLAT_WORLD_PRESETS = Lists.newArrayList();
        final String s = GuiFlatPresets.I["".length()];
        final Item itemFromBlock = Item.getItemFromBlock(Blocks.grass);
        final BiomeGenBase plains = BiomeGenBase.plains;
        final String[] array = new String[" ".length()];
        array["".length()] = GuiFlatPresets.I[" ".length()];
        final List<String> list = Arrays.asList(array);
        final FlatLayerInfo[] array2 = new FlatLayerInfo["   ".length()];
        array2["".length()] = new FlatLayerInfo(" ".length(), Blocks.grass);
        array2[" ".length()] = new FlatLayerInfo("  ".length(), Blocks.dirt);
        array2["  ".length()] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146421_a(s, itemFromBlock, plains, list, array2);
        final String s2 = GuiFlatPresets.I["  ".length()];
        final Item itemFromBlock2 = Item.getItemFromBlock(Blocks.stone);
        final BiomeGenBase extremeHills = BiomeGenBase.extremeHills;
        final String[] array3 = new String[0x9D ^ 0x98];
        array3["".length()] = GuiFlatPresets.I["   ".length()];
        array3[" ".length()] = GuiFlatPresets.I[0x8A ^ 0x8E];
        array3["  ".length()] = GuiFlatPresets.I[0x8 ^ 0xD];
        array3["   ".length()] = GuiFlatPresets.I[0x84 ^ 0x82];
        array3[0x8D ^ 0x89] = GuiFlatPresets.I[0x69 ^ 0x6E];
        final List<String> list2 = Arrays.asList(array3);
        final FlatLayerInfo[] array4 = new FlatLayerInfo[0x6E ^ 0x6A];
        array4["".length()] = new FlatLayerInfo(" ".length(), Blocks.grass);
        array4[" ".length()] = new FlatLayerInfo(0x29 ^ 0x2C, Blocks.dirt);
        array4["  ".length()] = new FlatLayerInfo(161 + 14 - 121 + 176, Blocks.stone);
        array4["   ".length()] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146421_a(s2, itemFromBlock2, extremeHills, list2, array4);
        final String s3 = GuiFlatPresets.I[0x96 ^ 0x9E];
        final Item water_bucket = Items.water_bucket;
        final BiomeGenBase deepOcean = BiomeGenBase.deepOcean;
        final String[] array5 = new String["  ".length()];
        array5["".length()] = GuiFlatPresets.I[0xBC ^ 0xB5];
        array5[" ".length()] = GuiFlatPresets.I[0x56 ^ 0x5C];
        final List<String> list3 = Arrays.asList(array5);
        final FlatLayerInfo[] array6 = new FlatLayerInfo[0x5D ^ 0x58];
        array6["".length()] = new FlatLayerInfo(0xFC ^ 0xA6, Blocks.water);
        array6[" ".length()] = new FlatLayerInfo(0xAB ^ 0xAE, Blocks.sand);
        array6["  ".length()] = new FlatLayerInfo(0x39 ^ 0x3C, Blocks.dirt);
        array6["   ".length()] = new FlatLayerInfo(0x5C ^ 0x59, Blocks.stone);
        array6[0xA9 ^ 0xAD] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146421_a(s3, water_bucket, deepOcean, list3, array6);
        final String s4 = GuiFlatPresets.I[0x9B ^ 0x90];
        final Item itemFromBlock3 = Item.getItemFromBlock(Blocks.tallgrass);
        final int meta = BlockTallGrass.EnumType.GRASS.getMeta();
        final BiomeGenBase plains2 = BiomeGenBase.plains;
        final String[] array7 = new String[0xB7 ^ 0xBF];
        array7["".length()] = GuiFlatPresets.I[0x3F ^ 0x33];
        array7[" ".length()] = GuiFlatPresets.I[0x36 ^ 0x3B];
        array7["  ".length()] = GuiFlatPresets.I[0xB2 ^ 0xBC];
        array7["   ".length()] = GuiFlatPresets.I[0x2F ^ 0x20];
        array7[0x80 ^ 0x84] = GuiFlatPresets.I[0xAF ^ 0xBF];
        array7[0x34 ^ 0x31] = GuiFlatPresets.I[0x4C ^ 0x5D];
        array7[0x39 ^ 0x3F] = GuiFlatPresets.I[0x5D ^ 0x4F];
        array7[0x60 ^ 0x67] = GuiFlatPresets.I[0x18 ^ 0xB];
        final List<String> list4 = Arrays.asList(array7);
        final FlatLayerInfo[] array8 = new FlatLayerInfo[0xBB ^ 0xBF];
        array8["".length()] = new FlatLayerInfo(" ".length(), Blocks.grass);
        array8[" ".length()] = new FlatLayerInfo("   ".length(), Blocks.dirt);
        array8["  ".length()] = new FlatLayerInfo(0x75 ^ 0x4E, Blocks.stone);
        array8["   ".length()] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_175354_a(s4, itemFromBlock3, meta, plains2, list4, array8);
        final String s5 = GuiFlatPresets.I[0x13 ^ 0x7];
        final Item itemFromBlock4 = Item.getItemFromBlock(Blocks.snow_layer);
        final BiomeGenBase icePlains = BiomeGenBase.icePlains;
        final String[] array9 = new String["  ".length()];
        array9["".length()] = GuiFlatPresets.I[0x62 ^ 0x77];
        array9[" ".length()] = GuiFlatPresets.I[0x67 ^ 0x71];
        final List<String> list5 = Arrays.asList(array9);
        final FlatLayerInfo[] array10 = new FlatLayerInfo[0x8E ^ 0x8B];
        array10["".length()] = new FlatLayerInfo(" ".length(), Blocks.snow_layer);
        array10[" ".length()] = new FlatLayerInfo(" ".length(), Blocks.grass);
        array10["  ".length()] = new FlatLayerInfo("   ".length(), Blocks.dirt);
        array10["   ".length()] = new FlatLayerInfo(0x87 ^ 0xBC, Blocks.stone);
        array10[0xB4 ^ 0xB0] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146421_a(s5, itemFromBlock4, icePlains, list5, array10);
        final String s6 = GuiFlatPresets.I[0x8A ^ 0x9D];
        final Item feather = Items.feather;
        final BiomeGenBase plains3 = BiomeGenBase.plains;
        final String[] array11 = new String["  ".length()];
        array11["".length()] = GuiFlatPresets.I[0x26 ^ 0x3E];
        array11[" ".length()] = GuiFlatPresets.I[0x2C ^ 0x35];
        final List<String> list6 = Arrays.asList(array11);
        final FlatLayerInfo[] array12 = new FlatLayerInfo["   ".length()];
        array12["".length()] = new FlatLayerInfo(" ".length(), Blocks.grass);
        array12[" ".length()] = new FlatLayerInfo("   ".length(), Blocks.dirt);
        array12["  ".length()] = new FlatLayerInfo("  ".length(), Blocks.cobblestone);
        func_146421_a(s6, feather, plains3, list6, array12);
        final String s7 = GuiFlatPresets.I[0x42 ^ 0x58];
        final Item itemFromBlock5 = Item.getItemFromBlock(Blocks.sand);
        final BiomeGenBase desert = BiomeGenBase.desert;
        final String[] array13 = new String[0x26 ^ 0x20];
        array13["".length()] = GuiFlatPresets.I[0xB6 ^ 0xAD];
        array13[" ".length()] = GuiFlatPresets.I[0x2F ^ 0x33];
        array13["  ".length()] = GuiFlatPresets.I[0xD ^ 0x10];
        array13["   ".length()] = GuiFlatPresets.I[0x6C ^ 0x72];
        array13[0xA9 ^ 0xAD] = GuiFlatPresets.I[0xD ^ 0x12];
        array13[0xC6 ^ 0xC3] = GuiFlatPresets.I[0xBF ^ 0x9F];
        final List<String> list7 = Arrays.asList(array13);
        final FlatLayerInfo[] array14 = new FlatLayerInfo[0x70 ^ 0x74];
        array14["".length()] = new FlatLayerInfo(0xBD ^ 0xB5, Blocks.sand);
        array14[" ".length()] = new FlatLayerInfo(0x67 ^ 0x53, Blocks.sandstone);
        array14["  ".length()] = new FlatLayerInfo("   ".length(), Blocks.stone);
        array14["   ".length()] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146421_a(s7, itemFromBlock5, desert, list7, array14);
        final String s8 = GuiFlatPresets.I[0x7A ^ 0x5B];
        final Item redstone = Items.redstone;
        final BiomeGenBase desert2 = BiomeGenBase.desert;
        final FlatLayerInfo[] array15 = new FlatLayerInfo["   ".length()];
        array15["".length()] = new FlatLayerInfo(0x5F ^ 0x6B, Blocks.sandstone);
        array15[" ".length()] = new FlatLayerInfo("   ".length(), Blocks.stone);
        array15["  ".length()] = new FlatLayerInfo(" ".length(), Blocks.bedrock);
        func_146425_a(s8, redstone, desert2, array15);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    private static void func_146421_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final List<String> list, final FlatLayerInfo... array) {
        func_175354_a(s, item, "".length(), biomeGenBase, list, array);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!this.field_146433_u.textboxKeyTyped(c, n)) {
            super.keyTyped(c, n);
        }
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean func_146430_p() {
        if ((this.field_146435_s.field_148175_k <= -" ".length() || this.field_146435_s.field_148175_k >= GuiFlatPresets.FLAT_WORLD_PRESETS.size()) && this.field_146433_u.getText().length() <= " ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146435_s.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.presetsTitle, this.width / "  ".length(), 0x64 ^ 0x6C, 1685581 + 7251029 - 3953112 + 11793717);
        this.drawString(this.fontRendererObj, this.presetsShare, 0x49 ^ 0x7B, 0x2A ^ 0x34, 6292855 + 3615636 - 3733313 + 4351702);
        this.drawString(this.fontRendererObj, this.field_146436_r, 0x4F ^ 0x7D, 0x63 ^ 0x25, 7125237 + 4126964 - 7375030 + 6649709);
        this.field_146433_u.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[0xE6 ^ 0xC1])["".length()] = I("6\u000205+\u001c\rq\u00004\u0014\u001a", "unQFX");
        GuiFlatPresets.I[" ".length()] = I(":(+%\"+$", "LAGIC");
        GuiFlatPresets.I["  ".length()] = I("\u0010\u001d\u001f\r?(\r\u0003\u0010}d,\u0003\u0006;)", "DhqcZ");
        GuiFlatPresets.I["   ".length()] = I("\f\b:\u0019\n1P", "naUto");
        GuiFlatPresets.I[0x4D ^ 0x49] = I(")76\u0013\u0015\",", "MBXtp");
        GuiFlatPresets.I[0x3C ^ 0x39] = I(",3$ \u000b)\". \u0017", "HVGOy");
        GuiFlatPresets.I[0x38 ^ 0x3E] = I("!!\u001e\u000b\t5=\u0003\b\u0003", "RUldg");
        GuiFlatPresets.I[0x5B ^ 0x5C] = I("!16\u0015\u0018$9>\u0004", "LXXpk");
        GuiFlatPresets.I[0x88 ^ 0x80] = I("\u001b\u0014=0\"l\"&'<(", "LuIUP");
        GuiFlatPresets.I[0x23 ^ 0x2A] = I(")+>\t=\u0014s", "KBQdX");
        GuiFlatPresets.I[0x2 ^ 0x8] = I("> \r5\u001d<,\u0006!\u001e4-\u001c", "QChTs");
        GuiFlatPresets.I[0xA0 ^ 0xAB] = I("\f\u001f\u0001 \u0012,\u001b\b6", "CidRe");
        GuiFlatPresets.I[0x55 ^ 0x59] = I("\u001b\u001b\u0002!$\n\u0017", "mrnME");
        GuiFlatPresets.I[0x9 ^ 0x4] = I("+3\u0017\u0019\n\u0016k", "IZxto");
        GuiFlatPresets.I[0x6B ^ 0x65] = I("\u0001\u0007'*\u0004\u0004\u0016-*\u0018", "ebDEv");
        GuiFlatPresets.I[0x94 ^ 0x9B] = I("\"<5\u0016\b6 (\u0015\u0002", "QHGyf");
        GuiFlatPresets.I[0x35 ^ 0x25] = I("?\b\f'4:\u0000\u00046", "RabBG");
        GuiFlatPresets.I[0x48 ^ 0x59] = I(".\f$\u001e!%\u0017", "JyJyD");
        GuiFlatPresets.I[0x39 ^ 0x2B] = I("-$:\u0006", "AEQcf");
        GuiFlatPresets.I[0x12 ^ 0x1] = I("\u000e\u000f\u0010\u0018=\u000e\u000f\r\u001c", "bnfyb");
        GuiFlatPresets.I[0xBC ^ 0xA8] = I("\u0018&-\r0k\u0003+\u0014./'/", "KHBzI");
        GuiFlatPresets.I[0xA5 ^ 0xB0] = I("\u0018\u0007'4\u0015\t\u000b", "nnKXt");
        GuiFlatPresets.I[0xD4 ^ 0xC2] = I("%8>\u0002,\u0018`", "GQQoI");
        GuiFlatPresets.I[0x43 ^ 0x54] = I("4*\u0015\u00137\u001b)\u0004\u0014+V\u0015\b\u0013", "vEagX");
        GuiFlatPresets.I[0x78 ^ 0x60] = I("\u0006/4\u0005-\u0017#", "pFXiL");
        GuiFlatPresets.I[0xD9 ^ 0xC0] = I("\u0013\r &,.U", "qdOKI");
        GuiFlatPresets.I[0x7F ^ 0x65] = I(">'\u0006)?\u000e", "zBuLM");
        GuiFlatPresets.I[0x19 ^ 0x2] = I("#\u0004\u001c\u0000'2\b", "UmplF");
        GuiFlatPresets.I[0x21 ^ 0x3D] = I("%%\u0002\u0014<\u0018}", "GLmyY");
        GuiFlatPresets.I[0x4 ^ 0x19] = I("\u0014\u000b\r?>\u0011\u001a\u0007?\"", "pnnPL");
        GuiFlatPresets.I[0x8B ^ 0x95] = I("\t\u00166+<\u001d\n+(6", "zbDDR");
        GuiFlatPresets.I[0x13 ^ 0xC] = I("\u0006(\u001d7!\u0003 \u0015&", "kAsRR");
        GuiFlatPresets.I[0x19 ^ 0x39] = I("*\"$2/!9", "NWJUJ");
        GuiFlatPresets.I[0x7 ^ 0x26] = I(":7\f<2\u0007<\ro\u0014\r3\f6", "hRhOF");
        GuiFlatPresets.I[0xB ^ 0x29] = I("\u00101\u001c\u0003?\u0016\u0014\u0016\u0010'\u0017m\u001a\u00178\u0007,\u0014\u000b1\u0016m\t\u0010.\u0000&\r\u0011e\u0007*\r\u000e.", "sCybK");
        GuiFlatPresets.I[0x25 ^ 0x6] = I("\u0011\u0000#8\u0000\u0017%)+\u0018\u0016\\%,\u0007\u0006\u001d+0\u000e\u0017\\6+\u0011\u0001\u00172*Z\u0001\u001a'+\u0011", "rrFYt");
        GuiFlatPresets.I[0x95 ^ 0xB1] = I("\u000e\u0010\u0012\u0013\"\b5\u0018\u0000:\tL\u0014\u0007%\u0019\r\u001a\u001b,\bL\u0007\u00003\u001e\u0007\u0003\u0001x\u0001\u000b\u0004\u0006", "mbwrV");
        GuiFlatPresets.I[0xE ^ 0x2B] = I("$=\u0002&<\"\u0018\b5$#a\u00042;3 \n.2\"a\u00175-4*\u00134f4*\u000b\"+3", "GOgGH");
        GuiFlatPresets.I[0x7C ^ 0x5A] = I("\u0002\u00023V\u0015\u0004\u00199\u001d\u001a", "ewZxv");
    }
    
    private static void func_146425_a(final String s, final Item item, final BiomeGenBase biomeGenBase, final FlatLayerInfo... array) {
        func_175354_a(s, item, "".length(), biomeGenBase, null, array);
    }
    
    static GuiTextField access$1(final GuiFlatPresets guiFlatPresets) {
        return guiFlatPresets.field_146433_u;
    }
    
    @Override
    public void updateScreen() {
        this.field_146433_u.updateCursorCounter();
        super.updateScreen();
    }
    
    static class LayerItem
    {
        public String field_148232_b;
        public String field_148233_c;
        public Item field_148234_a;
        public int field_179037_b;
        
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public LayerItem(final Item field_148234_a, final int field_179037_b, final String field_148232_b, final String field_148233_c) {
            this.field_148234_a = field_148234_a;
            this.field_179037_b = field_179037_b;
            this.field_148232_b = field_148232_b;
            this.field_148233_c = field_148233_c;
        }
    }
    
    class ListSlot extends GuiSlot
    {
        public int field_148175_k;
        final GuiFlatPresets this$0;
        
        private void func_178054_a(final int n, final int n2, final Item item, final int n3) {
            this.func_148173_e(n + " ".length(), n2 + " ".length());
            GlStateManager.enableRescaleNormal();
            RenderHelper.enableGUIStandardItemLighting();
            this.this$0.itemRender.renderItemIntoGUI(new ItemStack(item, " ".length(), n3), n + "  ".length(), n2 + "  ".length());
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
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
                if (1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private void func_148171_c(final int n, final int n2, final int n3, final int n4) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0xC3 ^ 0xC4, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n + "".length(), n2 + (0xD2 ^ 0xC0), this.this$0.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + (0x46 ^ 0x54)) * 0.0078125f).endVertex();
            worldRenderer.pos(n + (0xB3 ^ 0xA1), n2 + (0x12 ^ 0x0), this.this$0.zLevel).tex((n3 + (0x7F ^ 0x6D)) * 0.0078125f, (n4 + (0x4C ^ 0x5E)) * 0.0078125f).endVertex();
            worldRenderer.pos(n + (0x8D ^ 0x9F), n2 + "".length(), this.this$0.zLevel).tex((n3 + (0x78 ^ 0x6A)) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
            worldRenderer.pos(n + "".length(), n2 + "".length(), this.this$0.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
            instance.draw();
        }
        
        public ListSlot(final GuiFlatPresets this$0) {
            this.this$0 = this$0;
            super(this$0.mc, this$0.width, this$0.height, 0x3A ^ 0x6A, this$0.height - (0x50 ^ 0x75), 0xB8 ^ 0xA0);
            this.field_148175_k = -" ".length();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final LayerItem layerItem = GuiFlatPresets.access$0().get(n);
            this.func_178054_a(n2, n3, layerItem.field_148234_a, layerItem.field_179037_b);
            this.this$0.fontRendererObj.drawString(layerItem.field_148232_b, n2 + (0x73 ^ 0x61) + (0x36 ^ 0x33), n3 + (0xC3 ^ 0xC5), 4969256 + 9828326 + 569274 + 1410359);
        }
        
        private void func_148173_e(final int n, final int n2) {
            this.func_148171_c(n, n2, "".length(), "".length());
        }
        
        @Override
        protected int getSize() {
            return GuiFlatPresets.access$0().size();
        }
        
        @Override
        protected void drawBackground() {
        }
        
        @Override
        protected boolean isSelected(final int n) {
            if (n == this.field_148175_k) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        protected void elementClicked(final int field_148175_k, final boolean b, final int n, final int n2) {
            this.field_148175_k = field_148175_k;
            this.this$0.func_146426_g();
            GuiFlatPresets.access$1(this.this$0).setText(GuiFlatPresets.access$0().get(GuiFlatPresets.access$2(this.this$0).field_148175_k).field_148233_c);
        }
    }
}
