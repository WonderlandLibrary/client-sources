package net.minecraft.client.gui;

import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.gen.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;

public class GuiCreateFlatWorld extends GuiScreen
{
    private Details createFlatWorldListSlotGui;
    private final GuiCreateWorld createWorldGui;
    private GuiButton field_146388_u;
    private String field_146394_i;
    private String field_146391_r;
    private FlatGeneratorInfo theFlatGeneratorInfo;
    private GuiButton field_146389_t;
    private String flatWorldTitle;
    private static final String[] I;
    private GuiButton field_146386_v;
    
    static FlatGeneratorInfo access$0(final GuiCreateFlatWorld guiCreateFlatWorld) {
        return guiCreateFlatWorld.theFlatGeneratorInfo;
    }
    
    public String func_146384_e() {
        return this.theFlatGeneratorInfo.toString();
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
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.createFlatWorldListSlotGui.handleMouseInput();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.createFlatWorldListSlotGui.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.flatWorldTitle, this.width / "  ".length(), 0x40 ^ 0x48, 10315238 + 15900871 - 12839998 + 3401104);
        final int n4 = this.width / "  ".length() - (0xD1 ^ 0x8D) - (0x36 ^ 0x26);
        this.drawString(this.fontRendererObj, this.field_146394_i, n4, 0xBD ^ 0x9D, 11482064 + 15388920 - 17230968 + 7137199);
        this.drawString(this.fontRendererObj, this.field_146391_r, n4 + "  ".length() + (130 + 22 - 130 + 191) - this.fontRendererObj.getStringWidth(this.field_146391_r), 0x2A ^ 0xA, 1990104 + 12408846 - 10483200 + 12861465);
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        final int n = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_148228_k - " ".length();
        if (guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(this.createWorldGui);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (guiButton.id == 0) {
            this.createWorldGui.chunkProviderSettingsJson = this.func_146384_e();
            this.mc.displayGuiScreen(this.createWorldGui);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (guiButton.id == (0xB2 ^ 0xB7)) {
            this.mc.displayGuiScreen(new GuiFlatPresets(this));
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (guiButton.id == (0xC ^ 0x8) && this.func_146382_i()) {
            this.theFlatGeneratorInfo.getFlatLayers().remove(n);
            this.createFlatWorldListSlotGui.field_148228_k = Math.min(this.createFlatWorldListSlotGui.field_148228_k, this.theFlatGeneratorInfo.getFlatLayers().size() - " ".length());
        }
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_146375_g();
    }
    
    private static void I() {
        (I = new String[0x9B ^ 0x90])["".length()] = I("\f\u0003\u0012'\u0013\n&\u00184\u000b\u000b_\u00143\u0014\u001b\u001e\u001a/\u001d\n_\u0011*\u0006\u001b_\u0003/\u0013\u0003\u0014", "oqwFg");
        GuiCreateFlatWorld.I[" ".length()] = I("&'\t\t, \u0002\u0003\u001a4!{\u000f\u001d+1:\u0001\u0001\" {\n\u000491{\u0018\u00014 ", "EUlhX");
        GuiCreateFlatWorld.I["  ".length()] = I("3\u001e\u0014\u001125;\u001e\u0002*4B\u0012\u00055$\u0003\u001c\u0019<5B\u0017\u001c'$B\u0019\u0015/7\u0004\u0005", "PlqpF");
        GuiCreateFlatWorld.I["   ".length()] = I(")=!51/\u0018+&).a'!6> )=?/a\"8$>a%0!\u0006.=17", "JODTE");
        GuiCreateFlatWorld.I[0x9A ^ 0x9E] = I("lq\n\n;e", "LYDSr");
        GuiCreateFlatWorld.I[0x6E ^ 0x6B] = I("$\u0014\u0016&<\"1\u001c5$#H\u00102;3\t\u001e.2\"H\u0015+)3H\u0016#!3*\u0012>-5", "GfsGH");
        GuiCreateFlatWorld.I[0x21 ^ 0x27] = I("Dy\u0005?\u0010M", "dQKfY");
        GuiCreateFlatWorld.I[0x69 ^ 0x6E] = I(":\u00186\u0011'<=<\u0002?=D0\u0005 -\u0005>\u0019)<D5\u001c2-D!\u0015>6\u001c6<2 \u000f!", "YjSpS");
        GuiCreateFlatWorld.I[0x74 ^ 0x7C] = I("\u0005\u0014'O\u0005\r\u000f+", "baNaa");
        GuiCreateFlatWorld.I[0x84 ^ 0x8D] = I(":!0\u000e\u001b<\u0004:\u001d\u0003=}6\u001a\u001c-<8\u0006\u0015<}%\u001d\n*6!\u001c", "YSUoo");
        GuiCreateFlatWorld.I[0xBE ^ 0xB4] = I(",8\u0006y\u0001*#\f2\u000e", "KMoWb");
    }
    
    public GuiCreateFlatWorld(final GuiCreateWorld createWorldGui, final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
        this.createWorldGui = createWorldGui;
        this.func_146383_a(s);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        this.flatWorldTitle = I18n.format(GuiCreateFlatWorld.I["".length()], new Object["".length()]);
        this.field_146394_i = I18n.format(GuiCreateFlatWorld.I[" ".length()], new Object["".length()]);
        this.field_146391_r = I18n.format(GuiCreateFlatWorld.I["  ".length()], new Object["".length()]);
        this.createFlatWorldListSlotGui = new Details();
        this.buttonList.add(this.field_146389_t = new GuiButton("  ".length(), this.width / "  ".length() - (48 + 101 - 58 + 63), this.height - (0x95 ^ 0xA1), 0xEB ^ 0x8F, 0x5 ^ 0x11, String.valueOf(I18n.format(GuiCreateFlatWorld.I["   ".length()], new Object["".length()])) + GuiCreateFlatWorld.I[0x4F ^ 0x4B]));
        this.buttonList.add(this.field_146388_u = new GuiButton("   ".length(), this.width / "  ".length() - (0x48 ^ 0x7A), this.height - (0x10 ^ 0x24), 0xC2 ^ 0xA6, 0x7B ^ 0x6F, String.valueOf(I18n.format(GuiCreateFlatWorld.I[0xAB ^ 0xAE], new Object["".length()])) + GuiCreateFlatWorld.I[0x3C ^ 0x3A]));
        this.buttonList.add(this.field_146386_v = new GuiButton(0x36 ^ 0x32, this.width / "  ".length() - (97 + 18 - 31 + 71), this.height - (0xAE ^ 0x9A), 99 + 148 - 142 + 45, 0x38 ^ 0x2C, I18n.format(GuiCreateFlatWorld.I[0x62 ^ 0x65], new Object["".length()])));
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() - (50 + 114 - 59 + 50), this.height - (0xA5 ^ 0xB9), 147 + 95 - 187 + 95, 0x77 ^ 0x63, I18n.format(GuiCreateFlatWorld.I[0xB5 ^ 0xBD], new Object["".length()])));
        this.buttonList.add(new GuiButton(0x70 ^ 0x75, this.width / "  ".length() + (0x85 ^ 0x80), this.height - (0x51 ^ 0x65), 113 + 50 - 98 + 85, 0x39 ^ 0x2D, I18n.format(GuiCreateFlatWorld.I[0xCD ^ 0xC4], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() + (0x9D ^ 0x98), this.height - (0x48 ^ 0x54), 133 + 11 - 138 + 144, 0x9C ^ 0x88, I18n.format(GuiCreateFlatWorld.I[0x75 ^ 0x7F], new Object["".length()])));
        final GuiButton field_146389_t = this.field_146389_t;
        final GuiButton field_146388_u = this.field_146388_u;
        final int length = "".length();
        field_146388_u.visible = (length != 0);
        field_146389_t.visible = (length != 0);
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_146375_g();
    }
    
    static {
        I();
    }
    
    public void func_146383_a(final String s) {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(s);
    }
    
    public void func_146375_g() {
        final boolean func_146382_i = this.func_146382_i();
        this.field_146386_v.enabled = func_146382_i;
        this.field_146388_u.enabled = func_146382_i;
        this.field_146388_u.enabled = ("".length() != 0);
        this.field_146389_t.enabled = ("".length() != 0);
    }
    
    private boolean func_146382_i() {
        if (this.createFlatWorldListSlotGui.field_148228_k > -" ".length() && this.createFlatWorldListSlotGui.field_148228_k < this.theFlatGeneratorInfo.getFlatLayers().size()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    class Details extends GuiSlot
    {
        public int field_148228_k;
        final GuiCreateFlatWorld this$0;
        private static final String[] I;
        
        public Details(final GuiCreateFlatWorld this$0) {
            this.this$0 = this$0;
            super(this$0.mc, this$0.width, this$0.height, 0xB8 ^ 0x93, this$0.height - (0x88 ^ 0xB4), 0xAB ^ 0xB3);
            this.field_148228_k = -" ".length();
        }
        
        @Override
        protected void elementClicked(final int field_148228_k, final boolean b, final int n, final int n2) {
            this.field_148228_k = field_148228_k;
            this.this$0.func_146375_g();
        }
        
        private void func_148226_e(final int n, final int n2) {
            this.func_148224_c(n, n2, "".length(), "".length());
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
                if (3 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void drawBackground() {
        }
        
        private static void I() {
            (I = new String[0x52 ^ 0x56])["".length()] = I("\u0013\u0019\u0005", "RpwJP");
            Details.I[" ".length()] = I("60\u001d#>0\u0015\u00170&1l\u001b79!-\u0015+00l\u001e.+!l\u0014#300V6%%", "UBxBJ");
            Details.I["  ".length()] = I("\";3),$\u001e9:4%g5=+5&;!\"$g0$95g:)!$;x*75=9%", "AIVHX");
            Details.I["   ".length()] = I("\u001a;\u000b\u00180\u001c\u001e\u0001\u000b(\u001dg\r\f7\r&\u0003\u0010>\u001cg\b\u0015%\rg\u0002\u0018=\u001c;", "yInyD");
        }
        
        private void func_148224_c(final int n, final int n2, final int n3, final int n4) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(Gui.statIcons);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0x59 ^ 0x5E, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(n + "".length(), n2 + (0x92 ^ 0x80), this.this$0.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + (0x78 ^ 0x6A)) * 0.0078125f).endVertex();
            worldRenderer.pos(n + (0x88 ^ 0x9A), n2 + (0x38 ^ 0x2A), this.this$0.zLevel).tex((n3 + (0x39 ^ 0x2B)) * 0.0078125f, (n4 + (0x37 ^ 0x25)) * 0.0078125f).endVertex();
            worldRenderer.pos(n + (0x3C ^ 0x2E), n2 + "".length(), this.this$0.zLevel).tex((n3 + (0xD4 ^ 0xC6)) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
            worldRenderer.pos(n + "".length(), n2 + "".length(), this.this$0.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
            instance.draw();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            if (n == this.field_148228_k) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        protected int getSize() {
            return GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final FlatLayerInfo flatLayerInfo = GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().get(GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size() - n - " ".length());
            final IBlockState func_175900_c = flatLayerInfo.func_175900_c();
            final Block block = func_175900_c.getBlock();
            Item item = Item.getItemFromBlock(block);
            ItemStack itemStack;
            if (block != Blocks.air && item != null) {
                itemStack = new ItemStack(item, " ".length(), block.getMetaFromState(func_175900_c));
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                itemStack = null;
            }
            ItemStack itemStack2 = itemStack;
            String itemStackDisplayName;
            if (itemStack2 == null) {
                itemStackDisplayName = Details.I["".length()];
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                itemStackDisplayName = item.getItemStackDisplayName(itemStack2);
            }
            String localizedName = itemStackDisplayName;
            if (item == null) {
                if (block != Blocks.water && block != Blocks.flowing_water) {
                    if (block == Blocks.lava || block == Blocks.flowing_lava) {
                        item = Items.lava_bucket;
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                    }
                }
                else {
                    item = Items.water_bucket;
                }
                if (item != null) {
                    itemStack2 = new ItemStack(item, " ".length(), block.getMetaFromState(func_175900_c));
                    localizedName = block.getLocalizedName();
                }
            }
            this.func_148225_a(n2, n3, itemStack2);
            this.this$0.fontRendererObj.drawString(localizedName, n2 + (0x7E ^ 0x6C) + (0x8E ^ 0x8B), n3 + "   ".length(), 6869115 + 15019367 - 11382256 + 6270989);
            String s2;
            if (n == 0) {
                final String s = Details.I[" ".length()];
                final Object[] array = new Object[" ".length()];
                array["".length()] = flatLayerInfo.getLayerCount();
                s2 = I18n.format(s, array);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (n == GuiCreateFlatWorld.access$0(this.this$0).getFlatLayers().size() - " ".length()) {
                final String s3 = Details.I["  ".length()];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = flatLayerInfo.getLayerCount();
                s2 = I18n.format(s3, array2);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                final String s4 = Details.I["   ".length()];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = flatLayerInfo.getLayerCount();
                s2 = I18n.format(s4, array3);
            }
            this.this$0.fontRendererObj.drawString(s2, n2 + "  ".length() + (38 + 104 - 61 + 132) - this.this$0.fontRendererObj.getStringWidth(s2), n3 + "   ".length(), 947952 + 16098804 - 16960252 + 16690711);
        }
        
        private void func_148225_a(final int n, final int n2, final ItemStack itemStack) {
            this.func_148226_e(n + " ".length(), n2 + " ".length());
            GlStateManager.enableRescaleNormal();
            if (itemStack != null && itemStack.getItem() != null) {
                RenderHelper.enableGUIStandardItemLighting();
                this.this$0.itemRender.renderItemIntoGUI(itemStack, n + "  ".length(), n2 + "  ".length());
                RenderHelper.disableStandardItemLighting();
            }
            GlStateManager.disableRescaleNormal();
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - (0x67 ^ 0x21);
        }
    }
}
