package net.minecraft.client.gui.achievement;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import java.io.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;

public class GuiAchievements extends GuiScreen implements IProgressMeter
{
    protected int field_146563_h;
    protected double field_146566_v;
    private static final String[] I;
    private static final int field_146572_y;
    protected float field_146570_r;
    private static final int field_146560_B;
    protected double field_146569_s;
    protected double field_146567_u;
    protected double field_146568_t;
    protected GuiScreen parentScreen;
    private static final int field_146559_A;
    protected double field_146565_w;
    protected double field_146573_x;
    private int field_146554_D;
    private boolean loadingAchievements;
    private static final ResourceLocation ACHIEVEMENT_BACKGROUND;
    private StatFileWriter statFileWriter;
    protected int field_146564_i;
    protected int field_146557_g;
    protected int field_146555_f;
    private static final int field_146571_z;
    
    @Override
    public void initGui() {
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(" ".length(), this.width / "  ".length() + (0x86 ^ 0x9E), this.height / "  ".length() + (0x1C ^ 0x56), 0x75 ^ 0x25, 0x6F ^ 0x7B, I18n.format(GuiAchievements.I[" ".length()], new Object["".length()])));
    }
    
    @Override
    public void updateScreen() {
        if (!this.loadingAchievements) {
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            final double n = this.field_146565_w - this.field_146567_u;
            final double n2 = this.field_146573_x - this.field_146566_v;
            if (n * n + n2 * n2 < 4.0) {
                this.field_146567_u += n;
                this.field_146566_v += n2;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                this.field_146567_u += n * 0.85;
                this.field_146566_v += n2 * 0.85;
            }
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void drawTitle() {
        this.fontRendererObj.drawString(I18n.format(GuiAchievements.I["   ".length()], new Object["".length()]), (this.width - this.field_146555_f) / "  ".length() + (0x99 ^ 0x96), (this.height - this.field_146557_g) / "  ".length() + (0x4B ^ 0x4E), 3016071 + 2697821 - 3736316 + 2233176);
    }
    
    @Override
    public void doneLoading() {
        if (this.loadingAchievements) {
            this.loadingAchievements = ("".length() != 0);
        }
    }
    
    private TextureAtlasSprite func_175371_a(final Block block) {
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState());
    }
    
    static {
        I();
        field_146572_y = AchievementList.minDisplayColumn * (0x6B ^ 0x73) - (0x6A ^ 0x1A);
        field_146571_z = AchievementList.minDisplayRow * (0x75 ^ 0x6D) - (0x75 ^ 0x5);
        field_146559_A = AchievementList.maxDisplayColumn * (0x67 ^ 0x7F) - (0xD9 ^ 0x94);
        field_146560_B = AchievementList.maxDisplayRow * (0x11 ^ 0x9) - (0x56 ^ 0x1B);
        ACHIEVEMENT_BACKGROUND = new ResourceLocation(GuiAchievements.I["".length()]);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (!this.loadingAchievements && guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    private static void I() {
        (I = new String[0x5 ^ 0xD])["".length()] = I(":&\u000e\u00176<&\u0005L$;*Y\u0002 &*\u0013\u0015&#&\u0018\u0017l/ \u001e\n&8&\u001b\u0006-:\u001c\u0014\u0002 %$\u0004\f6 'X\u0013-)", "NCvcC");
        GuiAchievements.I[" ".length()] = I("\u0017\u001d.O\u0010\u001f\u0006\"", "phGat");
        GuiAchievements.I["  ".length()] = I("!8\u0014\u0000$<!\u0019\r(>c\u001c\u001b:\"!\u0017\u0015)%#\u001f'9-9\u000b", "LMxtM");
        GuiAchievements.I["   ".length()] = I("\u001e7\u001ft\u001b\u001a*\u001f?\f\u001c/\u00134\u000e\n", "yBvZz");
        GuiAchievements.I[0xB ^ 0xF] = I("5\u000623(\"\u00007?# K.;&1\u000b", "TeZZM");
        GuiAchievements.I[0xC1 ^ 0xC4] = I(".!\u001d,=9'\u0018 6;l\u0000+3!-\u0002+", "OBuEX");
        GuiAchievements.I[0x10 ^ 0x16] = I("+!<?\u001d<'93\u0016>l&3\t?+&3\u000b", "JBTVx");
        GuiAchievements.I[0x78 ^ 0x7F] = I("\u000e\u0016\u00079.\u0019\u0010\u00025%\u001b[\u001d5:\u001a\u001c\u001d58", "ouoPK");
    }
    
    protected void drawAchievementScreen(final int n, final int n2, final float n3) {
        int n4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * n3);
        int n5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * n3);
        if (n4 < GuiAchievements.field_146572_y) {
            n4 = GuiAchievements.field_146572_y;
        }
        if (n5 < GuiAchievements.field_146571_z) {
            n5 = GuiAchievements.field_146571_z;
        }
        if (n4 >= GuiAchievements.field_146559_A) {
            n4 = GuiAchievements.field_146559_A - " ".length();
        }
        if (n5 >= GuiAchievements.field_146560_B) {
            n5 = GuiAchievements.field_146560_B - " ".length();
        }
        final int n6 = (this.width - this.field_146555_f) / "  ".length();
        final int n7 = (this.height - this.field_146557_g) / "  ".length();
        final int n8 = n6 + (0xA6 ^ 0xB6);
        final int n9 = n7 + (0x4 ^ 0x15);
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(51 + 142 + 299 + 26);
        GlStateManager.pushMatrix();
        GlStateManager.translate(n8, n9, -200.0f);
        GlStateManager.scale(1.0f / this.field_146570_r, 1.0f / this.field_146570_r, 0.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        final int n10 = n4 + (267 + 228 - 271 + 64) >> (0x41 ^ 0x45);
        final int n11 = n5 + (248 + 35 - 151 + 156) >> (0x22 ^ 0x26);
        final int n12 = (n4 + (81 + 263 - 162 + 106)) % (0xB6 ^ 0xA6);
        final int n13 = (n5 + (147 + 251 - 247 + 137)) % (0xA3 ^ 0xB3);
        final Random random = new Random();
        final float n14 = 16.0f / this.field_146570_r;
        final float n15 = 16.0f / this.field_146570_r;
        int length = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (length * n14 - n13 < 155.0f) {
            final float n16 = 0.6f - (n11 + length) / 25.0f * 0.3f;
            GlStateManager.color(n16, n16, n16, 1.0f);
            int length2 = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
            while (length2 * n15 - n12 < 224.0f) {
                random.setSeed(this.mc.getSession().getPlayerID().hashCode() + n10 + length2 + (n11 + length) * (0xA5 ^ 0xB5));
                final int n17 = random.nextInt(" ".length() + n11 + length) + (n11 + length) / "  ".length();
                TextureAtlasSprite textureAtlasSprite = this.func_175371_a(Blocks.sand);
                if (n17 <= (0xA ^ 0x2F) && n11 + length != (0xC ^ 0x2F)) {
                    if (n17 == (0x47 ^ 0x51)) {
                        if (random.nextInt("  ".length()) == 0) {
                            textureAtlasSprite = this.func_175371_a(Blocks.diamond_ore);
                            "".length();
                            if (2 == 3) {
                                throw null;
                            }
                        }
                        else {
                            textureAtlasSprite = this.func_175371_a(Blocks.redstone_ore);
                            "".length();
                            if (2 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else if (n17 == (0x67 ^ 0x6D)) {
                        textureAtlasSprite = this.func_175371_a(Blocks.iron_ore);
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                    else if (n17 == (0x71 ^ 0x79)) {
                        textureAtlasSprite = this.func_175371_a(Blocks.coal_ore);
                        "".length();
                        if (3 == 1) {
                            throw null;
                        }
                    }
                    else if (n17 > (0x7F ^ 0x7B)) {
                        textureAtlasSprite = this.func_175371_a(Blocks.stone);
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                    else if (n17 > 0) {
                        textureAtlasSprite = this.func_175371_a(Blocks.dirt);
                        "".length();
                        if (-1 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    textureAtlasSprite = this.func_175371_a(Blocks.bedrock);
                }
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModalRect(length2 * (0x2E ^ 0x3E) - n12, length * (0x61 ^ 0x71) - n13, textureAtlasSprite, 0x4F ^ 0x5F, 0xB ^ 0x1B);
                ++length2;
            }
            ++length;
        }
        GlStateManager.enableDepth();
        GlStateManager.depthFunc(379 + 438 - 523 + 221);
        this.mc.getTextureManager().bindTexture(GuiAchievements.ACHIEVEMENT_BACKGROUND);
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < AchievementList.achievementList.size()) {
            final Achievement achievement = AchievementList.achievementList.get(i);
            if (achievement.parentAchievement != null) {
                final int n18 = achievement.displayColumn * (0x7A ^ 0x62) - n4 + (0x6B ^ 0x60);
                final int n19 = achievement.displayRow * (0x93 ^ 0x8B) - n5 + (0x23 ^ 0x28);
                final int n20 = achievement.parentAchievement.displayColumn * (0xA9 ^ 0xB1) - n4 + (0x88 ^ 0x83);
                final int n21 = achievement.parentAchievement.displayRow * (0xA ^ 0x12) - n5 + (0x3 ^ 0x8);
                final boolean hasAchievementUnlocked = this.statFileWriter.hasAchievementUnlocked(achievement);
                final boolean canUnlockAchievement = this.statFileWriter.canUnlockAchievement(achievement);
                if (this.statFileWriter.func_150874_c(achievement) <= (0x37 ^ 0x33)) {
                    int n22 = -(9248940 + 6566965 - 10225011 + 11186322);
                    if (hasAchievementUnlocked) {
                        n22 = -(1897138 + 934169 - 9977 + 3429006);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else if (canUnlockAchievement) {
                        n22 = -(2152771 + 5054432 + 4193868 + 5310865);
                    }
                    this.drawHorizontalLine(n18, n20, n19, n22);
                    this.drawVerticalLine(n20, n19, n21, n22);
                    if (n18 > n20) {
                        this.drawTexturedModalRect(n18 - (0xB2 ^ 0xB9) - (0x10 ^ 0x17), n19 - (0x1E ^ 0x1B), 0x61 ^ 0x13, 71 + 206 - 212 + 169, 0x37 ^ 0x30, 0x75 ^ 0x7E);
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                    }
                    else if (n18 < n20) {
                        this.drawTexturedModalRect(n18 + (0x42 ^ 0x49), n19 - (0x72 ^ 0x77), 0x1F ^ 0x74, 205 + 54 - 107 + 82, 0x5 ^ 0x2, 0x71 ^ 0x7A);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else if (n19 > n21) {
                        this.drawTexturedModalRect(n18 - (0x88 ^ 0x8D), n19 - (0x81 ^ 0x8A) - (0x81 ^ 0x86), 0x33 ^ 0x53, 162 + 230 - 216 + 58, 0xB8 ^ 0xB3, 0x78 ^ 0x7F);
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else if (n19 < n21) {
                        this.drawTexturedModalRect(n18 - (0x57 ^ 0x52), n19 + (0x8A ^ 0x81), 0x5D ^ 0x3D, 45 + 11 + 4 + 181, 0x3C ^ 0x37, 0x1D ^ 0x1A);
                    }
                }
            }
            ++i;
        }
        Achievement achievement2 = null;
        final float n23 = (n - n8) * this.field_146570_r;
        final float n24 = (n2 - n9) * this.field_146570_r;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int j = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (j < AchievementList.achievementList.size()) {
            final Achievement achievement3 = AchievementList.achievementList.get(j);
            final int n25 = achievement3.displayColumn * (0x7E ^ 0x66) - n4;
            final int n26 = achievement3.displayRow * (0x9 ^ 0x11) - n5;
            Label_2166: {
                if (n25 >= -(0x4 ^ 0x1C) && n26 >= -(0x5A ^ 0x42) && n25 <= 224.0f * this.field_146570_r && n26 <= 155.0f * this.field_146570_r) {
                    final int func_150874_c = this.statFileWriter.func_150874_c(achievement3);
                    if (this.statFileWriter.hasAchievementUnlocked(achievement3)) {
                        final float n27 = 0.75f;
                        GlStateManager.color(n27, n27, n27, 1.0f);
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    else if (this.statFileWriter.canUnlockAchievement(achievement3)) {
                        final float n28 = 1.0f;
                        GlStateManager.color(n28, n28, n28, 1.0f);
                        "".length();
                        if (4 < 0) {
                            throw null;
                        }
                    }
                    else if (func_150874_c < "   ".length()) {
                        final float n29 = 0.3f;
                        GlStateManager.color(n29, n29, n29, 1.0f);
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else if (func_150874_c == "   ".length()) {
                        final float n30 = 0.2f;
                        GlStateManager.color(n30, n30, n30, 1.0f);
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else if (func_150874_c != (0xBD ^ 0xB9)) {
                        "".length();
                        if (false) {
                            throw null;
                        }
                        break Label_2166;
                    }
                    else {
                        final float n31 = 0.1f;
                        GlStateManager.color(n31, n31, n31, 1.0f);
                    }
                    this.mc.getTextureManager().bindTexture(GuiAchievements.ACHIEVEMENT_BACKGROUND);
                    if (achievement3.getSpecial()) {
                        this.drawTexturedModalRect(n25 - "  ".length(), n26 - "  ".length(), 0x5C ^ 0x46, 123 + 3 - 38 + 114, 0x81 ^ 0x9B, 0x24 ^ 0x3E);
                        "".length();
                        if (0 < -1) {
                            throw null;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(n25 - "  ".length(), n26 - "  ".length(), "".length(), 35 + 157 - 41 + 51, 0x6 ^ 0x1C, 0x2D ^ 0x37);
                    }
                    if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                        final float n32 = 0.1f;
                        GlStateManager.color(n32, n32, n32, 1.0f);
                        this.itemRender.func_175039_a("".length() != 0);
                    }
                    GlStateManager.enableLighting();
                    GlStateManager.enableCull();
                    this.itemRender.renderItemAndEffectIntoGUI(achievement3.theItemStack, n25 + "   ".length(), n26 + "   ".length());
                    GlStateManager.blendFunc(667 + 55 - 597 + 645, 401 + 166 - 555 + 759);
                    GlStateManager.disableLighting();
                    if (!this.statFileWriter.canUnlockAchievement(achievement3)) {
                        this.itemRender.func_175039_a(" ".length() != 0);
                    }
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    if (n23 >= n25 && n23 <= n25 + (0x9A ^ 0x8C) && n24 >= n26 && n24 <= n26 + (0x2A ^ 0x3C)) {
                        achievement2 = achievement3;
                    }
                }
            }
            ++j;
        }
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiAchievements.ACHIEVEMENT_BACKGROUND);
        this.drawTexturedModalRect(n6, n7, "".length(), "".length(), this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0f;
        GlStateManager.depthFunc(391 + 175 - 411 + 360);
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        super.drawScreen(n, n2, n3);
        if (achievement2 != null) {
            String s = achievement2.getStatName().getUnformattedText();
            final String description = achievement2.getDescription();
            final int n33 = n + (0x7D ^ 0x71);
            final int n34 = n2 - (0x46 ^ 0x42);
            final int func_150874_c2 = this.statFileWriter.func_150874_c(achievement2);
            if (this.statFileWriter.canUnlockAchievement(achievement2)) {
                final int max = Math.max(this.fontRendererObj.getStringWidth(s), 0x9 ^ 0x71);
                int splitStringWidth = this.fontRendererObj.splitStringWidth(description, max);
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    splitStringWidth += 12;
                }
                this.drawGradientRect(n33 - "   ".length(), n34 - "   ".length(), n33 + max + "   ".length(), n34 + splitStringWidth + "   ".length() + (0x6 ^ 0xA), -(500949430 + 105045518 - 165196091 + 632942967), -(563807097 + 894913528 - 799074474 + 414095673));
                this.fontRendererObj.drawSplitString(description, n33, n34 + (0x21 ^ 0x2D), max, -(1487950 + 2454613 - 2223780 + 4531553));
                if (this.statFileWriter.hasAchievementUnlocked(achievement2)) {
                    this.fontRendererObj.drawStringWithShadow(I18n.format(GuiAchievements.I[0x1E ^ 0x1A], new Object["".length()]), n33, n34 + splitStringWidth + (0x8F ^ 0x8B), -(6580181 + 4036735 - 5425967 + 2111964));
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
            }
            else if (func_150874_c2 == "   ".length()) {
                s = I18n.format(GuiAchievements.I[0x29 ^ 0x2C], new Object["".length()]);
                final int max2 = Math.max(this.fontRendererObj.getStringWidth(s), 0x22 ^ 0x5A);
                final String s2 = GuiAchievements.I[0xB2 ^ 0xB4];
                final Object[] array = new Object[" ".length()];
                array["".length()] = achievement2.parentAchievement.getStatName();
                final String unformattedText = new ChatComponentTranslation(s2, array).getUnformattedText();
                this.drawGradientRect(n33 - "   ".length(), n34 - "   ".length(), n33 + max2 + "   ".length(), n34 + this.fontRendererObj.splitStringWidth(unformattedText, max2) + (0x3E ^ 0x32) + "   ".length(), -(860466274 + 56754098 - 618840480 + 775361932), -(625794915 + 346051645 + 82361131 + 19534133));
                this.fontRendererObj.drawSplitString(unformattedText, n33, n34 + (0x6D ^ 0x61), max2, -(4314713 + 320954 + 2865017 + 1915940));
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else if (func_150874_c2 < "   ".length()) {
                final int max3 = Math.max(this.fontRendererObj.getStringWidth(s), 0x30 ^ 0x48);
                final String s3 = GuiAchievements.I[0x10 ^ 0x17];
                final Object[] array2 = new Object[" ".length()];
                array2["".length()] = achievement2.parentAchievement.getStatName();
                final String unformattedText2 = new ChatComponentTranslation(s3, array2).getUnformattedText();
                this.drawGradientRect(n33 - "   ".length(), n34 - "   ".length(), n33 + max3 + "   ".length(), n34 + this.fontRendererObj.splitStringWidth(unformattedText2, max3) + (0x89 ^ 0x85) + "   ".length(), -(916814336 + 350510777 - 288315873 + 94732584), -(148646735 + 932581280 - 339058246 + 331572055));
                this.fontRendererObj.drawSplitString(unformattedText2, n33, n34 + (0xC ^ 0x0), max3, -(2027321 + 8423167 - 4931807 + 3897943));
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                s = null;
            }
            if (s != null) {
                final FontRenderer fontRendererObj = this.fontRendererObj;
                final String s4 = s;
                final float n35 = n33;
                final float n36 = n34;
                int n37;
                if (this.statFileWriter.canUnlockAchievement(achievement2)) {
                    if (achievement2.getSpecial()) {
                        n37 = -(28 + 126 - 44 + 18);
                        "".length();
                        if (-1 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n37 = -" ".length();
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                }
                else if (achievement2.getSpecial()) {
                    n37 = -(5849898 + 7965663 - 6993809 + 1534024);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    n37 = -(7612777 + 5404644 - 4958633 + 296924);
                }
                fontRendererObj.drawStringWithShadow(s4, n35, n36, n37);
            }
        }
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        int n;
        if (this.loadingAchievements) {
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
    
    @Override
    public void drawScreen(final int field_146563_h, final int field_146564_i, final float n) {
        if (this.loadingAchievements) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiAchievements.I["  ".length()], new Object["".length()]), this.width / "  ".length(), this.height / "  ".length(), 8963237 + 11388481 - 13355557 + 9781054);
            this.drawCenteredString(this.fontRendererObj, GuiAchievements.lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % GuiAchievements.lanSearchStates.length)], this.width / "  ".length(), this.height / "  ".length() + this.fontRendererObj.FONT_HEIGHT * "  ".length(), 8179488 + 7169717 - 2837879 + 4265889);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            if (Mouse.isButtonDown("".length())) {
                final int n2 = (this.width - this.field_146555_f) / "  ".length();
                final int n3 = (this.height - this.field_146557_g) / "  ".length();
                final int n4 = n2 + (0x8 ^ 0x0);
                final int n5 = n3 + (0x8A ^ 0x9B);
                if ((this.field_146554_D == 0 || this.field_146554_D == " ".length()) && field_146563_h >= n4 && field_146563_h < n4 + (128 + 59 - 178 + 215) && field_146564_i >= n5 && field_146564_i < n5 + (89 + 40 - 72 + 98)) {
                    if (this.field_146554_D == 0) {
                        this.field_146554_D = " ".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        this.field_146567_u -= (field_146563_h - this.field_146563_h) * this.field_146570_r;
                        this.field_146566_v -= (field_146564_i - this.field_146564_i) * this.field_146570_r;
                        final double field_146567_u = this.field_146567_u;
                        this.field_146569_s = field_146567_u;
                        this.field_146565_w = field_146567_u;
                        final double field_146566_v = this.field_146566_v;
                        this.field_146568_t = field_146566_v;
                        this.field_146573_x = field_146566_v;
                    }
                    this.field_146563_h = field_146563_h;
                    this.field_146564_i = field_146564_i;
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
            }
            else {
                this.field_146554_D = "".length();
            }
            final int dWheel = Mouse.getDWheel();
            final float field_146570_r = this.field_146570_r;
            if (dWheel < 0) {
                this.field_146570_r += 0.25f;
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else if (dWheel > 0) {
                this.field_146570_r -= 0.25f;
            }
            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0f, 2.0f);
            if (this.field_146570_r != field_146570_r) {
                final float n6 = field_146570_r - this.field_146570_r;
                final float n7 = field_146570_r * this.field_146555_f;
                final float n8 = field_146570_r * this.field_146557_g;
                final float n9 = this.field_146570_r * this.field_146555_f;
                final float n10 = this.field_146570_r * this.field_146557_g;
                this.field_146567_u -= (n9 - n7) * 0.5f;
                this.field_146566_v -= (n10 - n8) * 0.5f;
                final double field_146567_u2 = this.field_146567_u;
                this.field_146569_s = field_146567_u2;
                this.field_146565_w = field_146567_u2;
                final double field_146566_v2 = this.field_146566_v;
                this.field_146568_t = field_146566_v2;
                this.field_146573_x = field_146566_v2;
            }
            if (this.field_146565_w < GuiAchievements.field_146572_y) {
                this.field_146565_w = GuiAchievements.field_146572_y;
            }
            if (this.field_146573_x < GuiAchievements.field_146571_z) {
                this.field_146573_x = GuiAchievements.field_146571_z;
            }
            if (this.field_146565_w >= GuiAchievements.field_146559_A) {
                this.field_146565_w = GuiAchievements.field_146559_A - " ".length();
            }
            if (this.field_146573_x >= GuiAchievements.field_146560_B) {
                this.field_146573_x = GuiAchievements.field_146560_B - " ".length();
            }
            this.drawDefaultBackground();
            this.drawAchievementScreen(field_146563_h, field_146564_i, n);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.drawTitle();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }
    
    public GuiAchievements(final GuiScreen parentScreen, final StatFileWriter statFileWriter) {
        this.field_146555_f = 24 + 78 + 31 + 123;
        this.field_146557_g = 160 + 193 - 249 + 98;
        this.field_146570_r = 1.0f;
        this.loadingAchievements = (" ".length() != 0);
        this.parentScreen = parentScreen;
        this.statFileWriter = statFileWriter;
        final int n = 11 + 12 + 108 + 10;
        final int n2 = 120 + 120 - 205 + 106;
        final double field_146569_s = AchievementList.openInventory.displayColumn * (0x12 ^ 0xA) - n / "  ".length() - (0xB7 ^ 0xBB);
        this.field_146565_w = field_146569_s;
        this.field_146567_u = field_146569_s;
        this.field_146569_s = field_146569_s;
        final double field_146568_t = AchievementList.openInventory.displayRow * (0x96 ^ 0x8E) - n2 / "  ".length();
        this.field_146573_x = field_146568_t;
        this.field_146566_v = field_146568_t;
        this.field_146568_t = field_146568_t;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            super.keyTyped(c, n);
        }
    }
}
