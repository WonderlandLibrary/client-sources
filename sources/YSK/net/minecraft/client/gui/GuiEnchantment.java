package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.item.*;
import net.minecraft.client.model.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.enchantment.*;
import net.minecraft.client.resources.*;
import java.util.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class GuiEnchantment extends GuiContainer
{
    ItemStack field_147077_B;
    public int field_147073_u;
    private ContainerEnchantment container;
    private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE;
    public float field_147081_y;
    private static final ModelBook MODEL_BOOK;
    private static final String[] I;
    private final InventoryPlayer playerInventory;
    public float field_147080_z;
    public float field_147082_x;
    private final IWorldNameable field_175380_I;
    private Random random;
    public float field_147069_w;
    public float field_147076_A;
    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE;
    public float field_147071_v;
    
    static {
        I();
        ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation(GuiEnchantment.I["".length()]);
        ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation(GuiEnchantment.I[" ".length()]);
        MODEL_BOOK = new ModelBook();
    }
    
    private static void I() {
        (I = new String[0x45 ^ 0x4F])["".length()] = I("\u001a#>\";\u001c#5y)\u001b/i5!\u00002'? \u000b4i3 \r.'8:\u0007(!\t:\u000f$*3`\u001e(!", "nFFVN");
        GuiEnchantment.I[" ".length()] = I("6\u001c1\u0011\u00130\u001c:J\u0003,\r \u0011\u001fm\u001c'\u0006\u000e#\u0017=\f\b%&=\u0004\u0004.\u001c\u0016\u0007\t-\u0012g\u0015\b%", "ByIef");
        GuiEnchantment.I["  ".length()] = I(" 8)\u0003\b*9\"\u0005G&9$\u001f\b-#i\u0014\u000562", "CWGwi");
        GuiEnchantment.I["   ".length()] = I("", "qpAWH");
        GuiEnchantment.I[0xBE ^ 0xBA] = I("\u0015.\u001868y\u0019\u000b\"!09\u000b>17?Ts", "YKnST");
        GuiEnchantment.I[0x1 ^ 0x4] = I("", "LvctG");
        GuiEnchantment.I[0x4C ^ 0x4A] = I("\u0010\u0016+,(\u001a\u0017 *g\u0016\u0017&0(\u001d\rk4(\u0003\u00106v&\u001d\u001c", "syEXI");
        GuiEnchantment.I[0x52 ^ 0x55] = I("\r\u001d%\u0010\r\u0007\u001c.\u0016B\u000b\u001c(\f\r\u0000\u0006e\b\r\u001e\u001b8J\u0001\u000f\u001c2", "nrKdl");
        GuiEnchantment.I[0x82 ^ 0x8A] = I(" \u001b\u0016\u0011\f*\u001a\u001d\u0017C&\u001a\u001b\r\f-\u0000V\t\b5\u0011\u0014K\u0002-\u0011", "Ctxem");
        GuiEnchantment.I[0x2B ^ 0x22] = I("1>\u0003\u001d,;?\b\u001bc7?\u000e\u0001,<%C\u0005($4\u0001G 3?\u0014", "RQmiM");
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_147068_g();
    }
    
    public GuiEnchantment(final InventoryPlayer playerInventory, final World world, final IWorldNameable field_175380_I) {
        super(new ContainerEnchantment(playerInventory, world));
        this.random = new Random();
        this.playerInventory = playerInventory;
        this.container = (ContainerEnchantment)this.inventorySlots;
        this.field_175380_I = field_175380_I;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 0x55 ^ 0x59, 0x29 ^ 0x2C, 803795 + 2652336 + 258101 + 496520);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0x7 ^ 0xF, this.ySize - (0x7 ^ 0x67) + "  ".length(), 189426 + 2625936 - 1678497 + 3073887);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < "   ".length()) {
            final int n6 = n - (n4 + (0x8A ^ 0xB6));
            final int n7 = n2 - (n5 + (0xBD ^ 0xB3) + (0x4B ^ 0x58) * i);
            if (n6 >= 0 && n7 >= 0 && n6 < (0x78 ^ 0x14) && n7 < (0x3A ^ 0x29) && this.container.enchantItem(this.mc.thePlayer, i)) {
                this.mc.playerController.sendEnchantPacket(this.container.windowId, i);
            }
            ++i;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void func_147068_g() {
        final ItemStack stack = this.inventorySlots.getSlot("".length()).getStack();
        if (!ItemStack.areItemStacksEqual(stack, this.field_147077_B)) {
            this.field_147077_B = stack;
            do {
                this.field_147082_x += this.random.nextInt(0xA ^ 0xE) - this.random.nextInt(0xAD ^ 0xA9);
            } while (this.field_147071_v <= this.field_147082_x + 1.0f && this.field_147071_v >= this.field_147082_x - 1.0f);
        }
        this.field_147073_u += " ".length();
        this.field_147069_w = this.field_147071_v;
        this.field_147076_A = this.field_147080_z;
        int n = "".length();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < "   ".length()) {
            if (this.container.enchantLevels[i] != 0) {
                n = " ".length();
            }
            ++i;
        }
        if (n != 0) {
            this.field_147080_z += 0.2f;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            this.field_147080_z -= 0.2f;
        }
        this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0f, 1.0f);
        final float n2 = (this.field_147082_x - this.field_147071_v) * 0.4f;
        final float n3 = 0.2f;
        this.field_147081_y += (MathHelper.clamp_float(n2, -n3, n3) - this.field_147081_y) * 0.9f;
        this.field_147071_v += this.field_147081_y;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final boolean isCreativeMode = this.mc.thePlayer.capabilities.isCreativeMode;
        final int lapisAmount = this.container.getLapisAmount();
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < "   ".length()) {
            final int n4 = this.container.enchantLevels[i];
            final int n5 = this.container.field_178151_h[i];
            final int n6 = i + " ".length();
            if (this.isPointInRegion(0x3F ^ 0x3, (0x27 ^ 0x29) + (0xD1 ^ 0xC2) * i, 0x24 ^ 0x48, 0xAE ^ 0xBF, n, n2) && n4 > 0 && n5 >= 0) {
                final ArrayList arrayList = Lists.newArrayList();
                if (n5 >= 0 && Enchantment.getEnchantmentById(n5 & 60 + 54 + 123 + 18) != null) {
                    final String translatedName = Enchantment.getEnchantmentById(n5 & 49 + 232 - 214 + 188).getTranslatedName((n5 & 19935 + 48644 - 49889 + 46590) >> (0xB7 ^ 0xBF));
                    final ArrayList<String> list = (ArrayList<String>)arrayList;
                    final StringBuilder append = new StringBuilder(String.valueOf(EnumChatFormatting.WHITE.toString())).append(EnumChatFormatting.ITALIC.toString());
                    final String s = GuiEnchantment.I["  ".length()];
                    final Object[] array = new Object[" ".length()];
                    array["".length()] = translatedName;
                    list.add(append.append(I18n.format(s, array)).toString());
                }
                if (!isCreativeMode) {
                    if (n5 >= 0) {
                        arrayList.add(GuiEnchantment.I["   ".length()]);
                    }
                    if (this.mc.thePlayer.experienceLevel < n4) {
                        arrayList.add(String.valueOf(EnumChatFormatting.RED.toString()) + GuiEnchantment.I[0x25 ^ 0x21] + this.container.enchantLevels[i]);
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        final String s2 = GuiEnchantment.I[0x7D ^ 0x78];
                        String s3;
                        if (n6 == " ".length()) {
                            s3 = I18n.format(GuiEnchantment.I[0x73 ^ 0x75], new Object["".length()]);
                            "".length();
                            if (3 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            final String s4 = GuiEnchantment.I[0x38 ^ 0x3F];
                            final Object[] array2 = new Object[" ".length()];
                            array2["".length()] = n6;
                            s3 = I18n.format(s4, array2);
                        }
                        if (lapisAmount >= n6) {
                            arrayList.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s3);
                            "".length();
                            if (2 < 2) {
                                throw null;
                            }
                        }
                        else {
                            arrayList.add(String.valueOf(EnumChatFormatting.RED.toString()) + s3);
                        }
                        String s5;
                        if (n6 == " ".length()) {
                            s5 = I18n.format(GuiEnchantment.I[0x57 ^ 0x5F], new Object["".length()]);
                            "".length();
                            if (2 <= 1) {
                                throw null;
                            }
                        }
                        else {
                            final String s6 = GuiEnchantment.I[0x67 ^ 0x6E];
                            final Object[] array3 = new Object[" ".length()];
                            array3["".length()] = n6;
                            s5 = I18n.format(s6, array3);
                        }
                        arrayList.add(String.valueOf(EnumChatFormatting.GRAY.toString()) + s5);
                    }
                }
                this.drawHoveringText(arrayList, n, n2);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(1455 + 5652 - 4847 + 3629);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GlStateManager.viewport((scaledResolution.getScaledWidth() - (264 + 107 - 344 + 293)) / "  ".length() * scaledResolution.getScaleFactor(), (scaledResolution.getScaledHeight() - (224 + 107 - 118 + 27)) / "  ".length() * scaledResolution.getScaleFactor(), (179 + 66 + 65 + 10) * scaledResolution.getScaleFactor(), (157 + 44 + 35 + 4) * scaledResolution.getScaleFactor());
        GlStateManager.translate(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float n6 = 1.0f;
        GlStateManager.matrixMode(2329 + 229 - 1884 + 5214);
        GlStateManager.loadIdentity();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(0.0f, 3.3f, -16.0f);
        GlStateManager.scale(n6, n6, n6);
        final float n7 = 5.0f;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_BOOK_TEXTURE);
        GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
        final float n8 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * n;
        GlStateManager.translate((1.0f - n8) * 0.2f, (1.0f - n8) * 0.1f, (1.0f - n8) * 0.25f);
        GlStateManager.rotate(-(1.0f - n8) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        final float n9 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.25f;
        final float n10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * n + 0.75f;
        float n11 = (n9 - MathHelper.truncateDoubleToInt(n9)) * 1.6f - 0.3f;
        float n12 = (n10 - MathHelper.truncateDoubleToInt(n10)) * 1.6f - 0.3f;
        if (n11 < 0.0f) {
            n11 = 0.0f;
        }
        if (n12 < 0.0f) {
            n12 = 0.0f;
        }
        if (n11 > 1.0f) {
            n11 = 1.0f;
        }
        if (n12 > 1.0f) {
            n12 = 1.0f;
        }
        GlStateManager.enableRescaleNormal();
        GuiEnchantment.MODEL_BOOK.render(null, 0.0f, n11, n12, n8, 0.0f, 0.0625f);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.matrixMode(1618 + 805 + 1746 + 1720);
        GlStateManager.viewport("".length(), "".length(), this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(529 + 1553 + 2448 + 1358);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
        final int lapisAmount = this.container.getLapisAmount();
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < "   ".length()) {
            final int n13 = n4 + (0x4E ^ 0x72);
            final int n14 = n13 + (0x65 ^ 0x71);
            final int n15 = 0xFB ^ 0xAD;
            final String generateNewRandomName = EnchantmentNameParts.getInstance().generateNewRandomName();
            this.zLevel = 0.0f;
            this.mc.getTextureManager().bindTexture(GuiEnchantment.ENCHANTMENT_TABLE_GUI_TEXTURE);
            final int n16 = this.container.enchantLevels[i];
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (n16 == 0) {
                this.drawTexturedModalRect(n13, n5 + (0xB3 ^ 0xBD) + (0xAC ^ 0xBF) * i, "".length(), 126 + 146 - 171 + 84, 0x4A ^ 0x26, 0x2D ^ 0x3E);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                final String string = new StringBuilder().append(n16).toString();
                final FontRenderer standardGalacticFontRenderer = this.mc.standardGalacticFontRenderer;
                int n17 = 3137157 + 939099 - 3759614 + 6523240;
                int n18;
                if ((lapisAmount < i + " ".length() || this.mc.thePlayer.experienceLevel < n16) && !this.mc.thePlayer.capabilities.isCreativeMode) {
                    this.drawTexturedModalRect(n13, n5 + (0x4D ^ 0x43) + (0xE ^ 0x1D) * i, "".length(), 120 + 29 - 132 + 168, 0xFF ^ 0x93, 0xD ^ 0x1E);
                    this.drawTexturedModalRect(n13 + " ".length(), n5 + (0x99 ^ 0x96) + (0x2F ^ 0x3C) * i, (0xA ^ 0x1A) * i, 26 + 235 - 160 + 138, 0x17 ^ 0x7, 0x1E ^ 0xE);
                    standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + (0xB6 ^ 0xA6) + (0x26 ^ 0x35) * i, n15, (n17 & 5106524 + 6156758 - 10561745 + 16009885) >> " ".length());
                    n18 = 2954221 + 827445 - 1289440 + 1734606;
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                }
                else {
                    final int n19 = n2 - (n4 + (0x65 ^ 0x59));
                    final int n20 = n3 - (n5 + (0x77 ^ 0x79) + (0x3E ^ 0x2D) * i);
                    if (n19 >= 0 && n20 >= 0 && n19 < (0xEB ^ 0x87) && n20 < (0x96 ^ 0x85)) {
                        this.drawTexturedModalRect(n13, n5 + (0x6B ^ 0x65) + (0x2D ^ 0x3E) * i, "".length(), 86 + 49 - 1 + 70, 0xF2 ^ 0x9E, 0x2E ^ 0x3D);
                        n17 = 6121873 + 13225397 - 9764492 + 7194310;
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(n13, n5 + (0xA0 ^ 0xAE) + (0x93 ^ 0x80) * i, "".length(), 6 + 60 + 47 + 53, 0x66 ^ 0xA, 0x6D ^ 0x7E);
                    }
                    this.drawTexturedModalRect(n13 + " ".length(), n5 + (0x1D ^ 0x12) + (0x91 ^ 0x82) * i, (0x95 ^ 0x85) * i, 220 + 195 - 337 + 145, 0xC ^ 0x1C, 0xB1 ^ 0xA1);
                    standardGalacticFontRenderer.drawSplitString(generateNewRandomName, n14, n5 + (0xB9 ^ 0xA9) + (0xB4 ^ 0xA7) * i, n15, n17);
                    n18 = 6961631 + 4928668 - 7289004 + 3852625;
                }
                final FontRenderer fontRendererObj = this.mc.fontRendererObj;
                fontRendererObj.drawStringWithShadow(string, n14 + (0xE7 ^ 0xB1) - fontRendererObj.getStringWidth(string), n5 + (0x83 ^ 0x93) + (0xB6 ^ 0xA5) * i + (0x7A ^ 0x7D), n18);
            }
            ++i;
        }
    }
}
