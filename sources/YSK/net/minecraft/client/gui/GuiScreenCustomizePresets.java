package net.minecraft.client.gui;

import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiScreenCustomizePresets extends GuiScreen
{
    protected String field_175315_a;
    private GuiButton field_175316_h;
    private String field_175313_s;
    private String field_175312_t;
    private ListPreset field_175311_g;
    private GuiCustomizeWorldScreen field_175314_r;
    private static final List<Info> field_175310_f;
    private static final String[] I;
    private GuiTextField field_175317_i;
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.field_175315_a = I18n.format(GuiScreenCustomizePresets.I[0x2E ^ 0x21], new Object["".length()]);
        this.field_175313_s = I18n.format(GuiScreenCustomizePresets.I[0x2C ^ 0x3C], new Object["".length()]);
        this.field_175312_t = I18n.format(GuiScreenCustomizePresets.I[0x79 ^ 0x68], new Object["".length()]);
        this.field_175317_i = new GuiTextField("  ".length(), this.fontRendererObj, 0x29 ^ 0x1B, 0x14 ^ 0x3C, this.width - (0xF ^ 0x6B), 0x3B ^ 0x2F);
        this.field_175311_g = new ListPreset();
        this.field_175317_i.setMaxStringLength(1218 + 1981 - 1961 + 762);
        this.field_175317_i.setText(this.field_175314_r.func_175323_a());
        this.buttonList.add(this.field_175316_h = new GuiButton("".length(), this.width / "  ".length() - (0xF ^ 0x69), this.height - (0x5A ^ 0x41), 0xD2 ^ 0xB6, 0x66 ^ 0x72, I18n.format(GuiScreenCustomizePresets.I[0x60 ^ 0x72], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() + "   ".length(), this.height - (0x56 ^ 0x4D), 0xD9 ^ 0xBD, 0x7D ^ 0x69, I18n.format(GuiScreenCustomizePresets.I[0x9E ^ 0x8D], new Object["".length()])));
        this.func_175304_a();
    }
    
    private boolean func_175305_g() {
        if ((this.field_175311_g.field_178053_u <= -" ".length() || this.field_175311_g.field_178053_u >= GuiScreenCustomizePresets.field_175310_f.size()) && this.field_175317_i.getText().length() <= " ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_175311_g.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_175315_a, this.width / "  ".length(), 0x9 ^ 0x1, 3824037 + 14648451 - 1715653 + 20380);
        this.drawString(this.fontRendererObj, this.field_175313_s, 0x52 ^ 0x60, 0x29 ^ 0x37, 2649287 + 10370619 - 10852405 + 8359379);
        this.drawString(this.fontRendererObj, this.field_175312_t, 0x42 ^ 0x70, 0x33 ^ 0x75, 1184611 + 8190792 - 1582244 + 2733721);
        this.field_175317_i.drawTextBox();
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        this.field_175317_i.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (!this.field_175317_i.textboxKeyTyped(c, n)) {
            super.keyTyped(c, n);
        }
    }
    
    private static void I() {
        (I = new String[0x87 ^ 0x93])["".length()] = I("\r')8\u0013\u000b'\"c\u0001\f+~<\u0014\u001c148\u0015V508\u0003\u000bl!\"\u0001", "yBQLf");
        GuiScreenCustomizePresets.I[" ".length()] = I("\r'(\u0013\u0013\u000b\u0002\"\u0000\u000b\n{.\u0007\u0014\u001a: \u001b\u001d\u000b{.\u0007\u0014\u001a: \\\u0017\u001c0>\u0017\u0013@\",\u0006\u0002\u001c\u0002\"\u0000\u000b\n", "nUMrg");
        GuiScreenCustomizePresets.I["  ".length()] = I("\u0016\u0000\t\u001f9\u0010\u0000\u0002D+\u0017\f^\u001b>\u0007\u0016\u0014\u001f?M\f\u0002\u0007)\u0011K\u0001\u0005+", "beqkL");
        GuiScreenCustomizePresets.I["   ".length()] = I("*(\u0017\u0003\u0010,\r\u001d\u0010\b-t\u0011\u0017\u0017=5\u001f\u000b\u001e,t\u0011\u0017\u0017=5\u001fL\u0014;?\u0001\u0007\u0010g3\u0001\u000e\u0001\u0005;\u001c\u0006", "IZrbd");
        GuiScreenCustomizePresets.I[0x2B ^ 0x2F] = I("\u0000?-\u0002\u0005\u0006?&Y\u0017\u00013z\u0006\u0002\u0011)0\u0002\u0003[>0\u001a\u0019\u00132!X\u0000\u001a=", "tZUvp");
        GuiScreenCustomizePresets.I[0x55 ^ 0x50] = I(":\u001a?\u0014\u0012<?5\u0007\n=F9\u0000\u0015-\u00077\u001c\u001c<F9\u0000\u0015-\u00077[\u0016+\r)\u0010\u0012w\u000b;\u0003\u0003\u001d\r6\u001c\u00011\u001c", "YhZuf");
        GuiScreenCustomizePresets.I[0x51 ^ 0x57] = I("\u00047;\u0002\u001d\u000270Y\u000f\u0005;l\u0006\u001a\u0015!&\u0002\u001b_?\"\u0012\u0006\u0015!0X\u0018\u001e5", "pRCvh");
        GuiScreenCustomizePresets.I[0x7 ^ 0x0] = I("\u001b\u0018\t,\u0018\u001d=\u0003?\u0000\u001cD\u000f8\u001f\f\u0005\u0001$\u0016\u001dD\u000f8\u001f\f\u0005\u0001c\u001c\n\u000f\u001f(\u0018V\u0007\u00038\u0002\f\u000b\u0005#\u001f", "xjlMl");
        GuiScreenCustomizePresets.I[0x2D ^ 0x25] = I("3.\u000f$\r5.\u0004\u007f\u001f2\"X \n\"8\u0012$\u000bh/\u0005?\r #\u0003~\b),", "GKwPx");
        GuiScreenCustomizePresets.I[0x92 ^ 0x9B] = I("\u00167\u0012\u0015\u0017\u0010\u0012\u0018\u0006\u000f\u0011k\u0014\u0001\u0010\u0001*\u001a\u001d\u0019\u0010k\u0014\u0001\u0010\u0001*\u001aZ\u0013\u0007 \u0004\u0011\u0017[!\u0005\u001b\u0016\u0012-\u0003", "uEwtc");
        GuiScreenCustomizePresets.I[0x34 ^ 0x3E] = I("\u0016&)\u001b;\u0010&\"@)\u0017*~\u001f<\u000704\u001b=M 9\u000e!\u0011m!\u0001)", "bCQoN");
        GuiScreenCustomizePresets.I[0x7A ^ 0x71] = I(":\u0013.):<6$:\"=O(==-\u000e&!4<O(==-\u000e&f>+\u00048-:w\u0002*>+\u001a\t*'=", "YaKHN");
        GuiScreenCustomizePresets.I[0xF ^ 0x3] = I("$!\u001c6\u000f\"!\u0017m\u001d%-K2\b57\u00016\t\u007f(\u0011!\u0011~4\n%", "PDdBz");
        GuiScreenCustomizePresets.I[0x7E ^ 0x73] = I("74\f\u0000\u00051\u0011\u0006\u0013\u001d0h\n\u0014\u0002 )\u0004\b\u000b1h\n\u0014\u0002 )\u0004O\u0001&#\u001a\u0004\u0005z!\u0006\u000e\u0015\u00183\n\n", "TFiaq");
        GuiScreenCustomizePresets.I[0xC ^ 0x2] = I("1\u001f+&9\u001f\u0003\"7v%\u0005*>2R:*7%\u0017\u001e+", "rjXRV");
        GuiScreenCustomizePresets.I[0x63 ^ 0x6C] = I("\b\u00150\n>\u000e0:\u0019&\u000fI6\u001e9\u001f\b8\u00020\u000eI6\u001e9\u001f\b8E:\u0019\u0002&\u000e>\u0018I!\u0002>\u0007\u0002", "kgUkJ");
        GuiScreenCustomizePresets.I[0x8 ^ 0x18] = I("\r\u001e\u000e':\u000b;\u00044\"\nB\b3=\u001a\u0003\u0006/4\u000bB\u001b4+\u001d\t\u001f5`\u001d\u0004\n4+", "nlkFN");
        GuiScreenCustomizePresets.I[0x38 ^ 0x29] = I("2\n\u0007\n;4/\r\u0019#5V\u0001\u001e<%\u0017\u000f\u000254V\u0012\u0019*\"\u001d\u0016\u0018a=\u0011\u0011\u001f", "QxbkO");
        GuiScreenCustomizePresets.I[0xA0 ^ 0xB2] = I("\b?2#\u0019\u000e\u001a80\u0001\u000fc47\u001e\u001f\":+\u0017\u000ec'0\b\u0018(#1C\u0018(;'\u000e\u001f", "kMWBm");
        GuiScreenCustomizePresets.I[0x56 ^ 0x45] = I("\u000f'\"A2\t<(\n=", "hRKoQ");
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_175311_g.handleMouseInput();
    }
    
    static {
        I();
        (field_175310_f = Lists.newArrayList()).add(new Info(I18n.format(GuiScreenCustomizePresets.I[" ".length()], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I["".length()]), ChunkProviderSettings.Factory.jsonToFactory("{ \"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":8.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":0.5, \"biomeScaleWeight\":2.0, \"biomeScaleOffset\":0.375, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":255 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I["   ".length()], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I["  ".length()]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":3000.0, \"heightScale\":6000.0, \"upperLimitScale\":250.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I[0x8D ^ 0x88], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I[0x7F ^ 0x7B]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":5000.0, \"mainNoiseScaleY\":1000.0, \"mainNoiseScaleZ\":5000.0, \"baseSize\":8.5, \"stretchY\":5.0, \"biomeDepthWeight\":2.0, \"biomeDepthOffset\":1.0, \"biomeScaleWeight\":4.0, \"biomeScaleOffset\":1.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I[0x9A ^ 0x9D], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I[0xBB ^ 0xBD]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":738.41864, \"heightScale\":157.69133, \"upperLimitScale\":801.4267, \"lowerLimitScale\":1254.1643, \"depthNoiseScaleX\":374.93652, \"depthNoiseScaleZ\":288.65228, \"depthNoiseScaleExponent\":1.2092624, \"mainNoiseScaleX\":1355.9908, \"mainNoiseScaleY\":745.5343, \"mainNoiseScaleZ\":1183.464, \"baseSize\":1.8758626, \"stretchY\":1.7137525, \"biomeDepthWeight\":1.7553768, \"biomeDepthOffset\":3.4701107, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":2.535211, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":63 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I[0x32 ^ 0x3B], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I[0x2A ^ 0x22]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":1000.0, \"mainNoiseScaleY\":3000.0, \"mainNoiseScaleZ\":1000.0, \"baseSize\":8.5, \"stretchY\":10.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":20 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I[0x63 ^ 0x68], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I[0x2E ^ 0x24]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":2.0, \"lowerLimitScale\":64.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":false, \"seaLevel\":6 }")));
        GuiScreenCustomizePresets.field_175310_f.add(new Info(I18n.format(GuiScreenCustomizePresets.I[0x70 ^ 0x7D], new Object["".length()]), new ResourceLocation(GuiScreenCustomizePresets.I[0xCF ^ 0xC3]), ChunkProviderSettings.Factory.jsonToFactory("{\"coordinateScale\":684.412, \"heightScale\":684.412, \"upperLimitScale\":512.0, \"lowerLimitScale\":512.0, \"depthNoiseScaleX\":200.0, \"depthNoiseScaleZ\":200.0, \"depthNoiseScaleExponent\":0.5, \"mainNoiseScaleX\":80.0, \"mainNoiseScaleY\":160.0, \"mainNoiseScaleZ\":80.0, \"baseSize\":8.5, \"stretchY\":12.0, \"biomeDepthWeight\":1.0, \"biomeDepthOffset\":0.0, \"biomeScaleWeight\":1.0, \"biomeScaleOffset\":0.0, \"useCaves\":true, \"useDungeons\":true, \"dungeonChance\":8, \"useStrongholds\":true, \"useVillages\":true, \"useMineShafts\":true, \"useTemples\":true, \"useRavines\":true, \"useWaterLakes\":true, \"waterLakeChance\":4, \"useLavaLakes\":true, \"lavaLakeChance\":80, \"useLavaOceans\":true, \"seaLevel\":40 }")));
    }
    
    public void func_175304_a() {
        this.field_175316_h.enabled = this.func_175305_g();
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiScreenCustomizePresets(final GuiCustomizeWorldScreen field_175314_r) {
        this.field_175315_a = GuiScreenCustomizePresets.I[0xCE ^ 0xC0];
        this.field_175314_r = field_175314_r;
    }
    
    static List access$0() {
        return GuiScreenCustomizePresets.field_175310_f;
    }
    
    @Override
    public void updateScreen() {
        this.field_175317_i.updateCursorCounter();
        super.updateScreen();
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        switch (guiButton.id) {
            case 0: {
                this.field_175314_r.func_175324_a(this.field_175317_i.getText());
                this.mc.displayGuiScreen(this.field_175314_r);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.field_175314_r);
                break;
            }
        }
    }
    
    static GuiTextField access$1(final GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.field_175317_i;
    }
    
    static ListPreset access$2(final GuiScreenCustomizePresets guiScreenCustomizePresets) {
        return guiScreenCustomizePresets.field_175311_g;
    }
    
    static class Info
    {
        public ResourceLocation field_178953_b;
        public ChunkProviderSettings.Factory field_178954_c;
        public String field_178955_a;
        
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
                if (1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Info(final String field_178955_a, final ResourceLocation field_178953_b, final ChunkProviderSettings.Factory field_178954_c) {
            this.field_178955_a = field_178955_a;
            this.field_178953_b = field_178953_b;
            this.field_178954_c = field_178954_c;
        }
    }
    
    class ListPreset extends GuiSlot
    {
        final GuiScreenCustomizePresets this$0;
        public int field_178053_u;
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final Info info = GuiScreenCustomizePresets.access$0().get(n);
            this.func_178051_a(n2, n3, info.field_178953_b);
            this.this$0.fontRendererObj.drawString(info.field_178955_a, n2 + (0x3 ^ 0x23) + (0x22 ^ 0x28), n3 + (0xA7 ^ 0xA9), 12871393 + 3553683 - 14632767 + 14984906);
        }
        
        public ListPreset(final GuiScreenCustomizePresets this$0) {
            this.this$0 = this$0;
            super(this$0.mc, this$0.width, this$0.height, 0x48 ^ 0x18, this$0.height - (0xA2 ^ 0x82), 0xE6 ^ 0xC0);
            this.field_178053_u = -" ".length();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            if (n == this.field_178053_u) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        private void func_178051_a(final int n, final int n2, final ResourceLocation resourceLocation) {
            final int n3 = n + (0x6 ^ 0x3);
            this.this$0.drawHorizontalLine(n3 - " ".length(), n3 + (0x36 ^ 0x16), n2 - " ".length(), -(1933933 + 230425 - 888791 + 764017));
            this.this$0.drawHorizontalLine(n3 - " ".length(), n3 + (0x2B ^ 0xB), n2 + (0x1C ^ 0x3C), -(3519621 + 247669 - 2139567 + 4622613));
            this.this$0.drawVerticalLine(n3 - " ".length(), n2 - " ".length(), n2 + (0x90 ^ 0xB0), -(1315047 + 1166518 - 2456879 + 2014898));
            this.this$0.drawVerticalLine(n3 + (0x6A ^ 0x4A), n2 - " ".length(), n2 + (0xAF ^ 0x8F), -(2151166 + 1059136 + 2568922 + 471112));
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(resourceLocation);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0x56 ^ 0x51, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n3 + "".length(), n2 + (0x78 ^ 0x58), 0.0).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(n3 + (0x61 ^ 0x41), n2 + (0x3D ^ 0x1D), 0.0).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(n3 + (0x74 ^ 0x54), n2 + "".length(), 0.0).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(n3 + "".length(), n2 + "".length(), 0.0).tex(0.0, 0.0).endVertex();
            instance.draw();
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected int getSize() {
            return GuiScreenCustomizePresets.access$0().size();
        }
        
        @Override
        protected void elementClicked(final int field_178053_u, final boolean b, final int n, final int n2) {
            this.field_178053_u = field_178053_u;
            this.this$0.func_175304_a();
            GuiScreenCustomizePresets.access$1(this.this$0).setText(GuiScreenCustomizePresets.access$0().get(GuiScreenCustomizePresets.access$2(this.this$0).field_178053_u).field_178954_c.toString());
        }
        
        @Override
        protected void drawBackground() {
        }
    }
}
