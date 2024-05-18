package net.minecraft.client.gui.achievement;

import java.io.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import org.lwjgl.input.*;
import net.minecraft.stats.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.entity.*;

public class GuiStats extends GuiScreen implements IProgressMeter
{
    private GuiSlot displaySlot;
    protected GuiScreen parentScreen;
    private StatFileWriter field_146546_t;
    private StatsGeneral generalStats;
    private static final String[] I;
    protected String screenTitle;
    private StatsMobsList mobStats;
    private boolean doesGuiPauseGame;
    private StatsBlock blockStats;
    private StatsItem itemStats;
    
    static StatFileWriter access$1(final GuiStats guiStats) {
        return guiStats.field_146546_t;
    }
    
    private void drawButtonBackground(final int n, final int n2) {
        this.drawSprite(n, n2, "".length(), "".length());
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (this.displaySlot != null) {
            this.displaySlot.handleMouseInput();
        }
    }
    
    public void createButtons() {
        this.buttonList.add(new GuiButton("".length(), this.width / "  ".length() + (0x5F ^ 0x5B), this.height - (0xBB ^ 0xA7), 36 + 132 - 147 + 129, 0x6 ^ 0x12, I18n.format(GuiStats.I["  ".length()], new Object["".length()])));
        this.buttonList.add(new GuiButton(" ".length(), this.width / "  ".length() - (116 + 135 - 246 + 155), this.height - (0x95 ^ 0xA1), 0x90 ^ 0xC0, 0x9E ^ 0x8A, I18n.format(GuiStats.I["   ".length()], new Object["".length()])));
        final GuiButton guiButton;
        this.buttonList.add(guiButton = new GuiButton("  ".length(), this.width / "  ".length() - (0x68 ^ 0x38), this.height - (0x89 ^ 0xBD), 0x51 ^ 0x1, 0x51 ^ 0x45, I18n.format(GuiStats.I[0x28 ^ 0x2C], new Object["".length()])));
        final GuiButton guiButton2;
        this.buttonList.add(guiButton2 = new GuiButton("   ".length(), this.width / "  ".length(), this.height - (0x65 ^ 0x51), 0x12 ^ 0x42, 0x42 ^ 0x56, I18n.format(GuiStats.I[0x88 ^ 0x8D], new Object["".length()])));
        final GuiButton guiButton3;
        this.buttonList.add(guiButton3 = new GuiButton(0xB1 ^ 0xB5, this.width / "  ".length() + (0xD4 ^ 0x84), this.height - (0xF5 ^ 0xC1), 0x19 ^ 0x49, 0xBA ^ 0xAE, I18n.format(GuiStats.I[0xC ^ 0xA], new Object["".length()])));
        if (this.blockStats.getSize() == 0) {
            guiButton.enabled = ("".length() != 0);
        }
        if (this.itemStats.getSize() == 0) {
            guiButton2.enabled = ("".length() != 0);
        }
        if (this.mobStats.getSize() == 0) {
            guiButton3.enabled = ("".length() != 0);
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    static FontRenderer access$2(final GuiStats guiStats) {
        return guiStats.fontRendererObj;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format(GuiStats.I[" ".length()], new Object["".length()]);
        this.doesGuiPauseGame = (" ".length() != 0);
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
    }
    
    public void func_175366_f() {
        (this.generalStats = new StatsGeneral(this.mc)).registerScrollButtons(" ".length(), " ".length());
        (this.itemStats = new StatsItem(this.mc)).registerScrollButtons(" ".length(), " ".length());
        (this.blockStats = new StatsBlock(this.mc)).registerScrollButtons(" ".length(), " ".length());
        (this.mobStats = new StatsMobsList(this.mc)).registerScrollButtons(" ".length(), " ".length());
    }
    
    static void access$3(final GuiStats guiStats, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        guiStats.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    private static void I() {
        (I = new String[0x5 ^ 0xD])["".length()] = I("?2\u0018(\u001b\u0018w\u0003\"\n\u00003", "lWtMx");
        GuiStats.I[" ".length()] = I("%\u0016\"v\u00026\u0002?+", "BcKXq");
        GuiStats.I["  ".length()] = I("\u0014\u0000\u0018@\u000e\u001c\u001b\u0014", "suqnj");
        GuiStats.I["   ".length()] = I("4\"\u0014,} 3\u001b=!&:7-'39\u001b", "GVuXS");
        GuiStats.I[0x2D ^ 0x29] = I("48\u0016\u0002X% \u0018\u0015\u001d4\u000e\u0002\u0002\u0002(\"", "GLwvv");
        GuiStats.I[0x21 ^ 0x24] = I("\u0010\u0003'1G\n\u0003#(\u001a!\u000221\u0006\r", "cwFEi");
        GuiStats.I[0x5D ^ 0x5B] = I("\u00150\u000b\fY\u000b+\b\u000b5\u00130\u001e\u0017\u0019", "fDjxw");
        GuiStats.I[0x3E ^ 0x39] = I("#\u0005\u000b>\u0010>\u001c\u00063\u001c<^\u0003%\u000e \u001c\b+\u001d'\u001e\u0000\u0019\r/\u0004\u0014", "NpgJy");
    }
    
    private void drawStatsScreen(final int n, final int n2, final Item item) {
        this.drawButtonBackground(n + " ".length(), n2 + " ".length());
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        this.itemRender.renderItemIntoGUI(new ItemStack(item, " ".length(), "".length()), n + "  ".length(), n2 + "  ".length());
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }
    
    static void access$4(final GuiStats guiStats, final int n, final int n2, final Item item) {
        guiStats.drawStatsScreen(n, n2, item);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentScreen);
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else if (guiButton.id == " ".length()) {
                this.displaySlot = this.generalStats;
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else if (guiButton.id == "   ".length()) {
                this.displaySlot = this.itemStats;
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else if (guiButton.id == "  ".length()) {
                this.displaySlot = this.blockStats;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (guiButton.id == (0x65 ^ 0x61)) {
                this.displaySlot = this.mobStats;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                this.displaySlot.actionPerformed(guiButton);
            }
        }
    }
    
    private void drawSprite(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiStats.statIcons);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x1A ^ 0x1D, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + "".length(), n2 + (0xAF ^ 0xBD), this.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + (0x5A ^ 0x48)) * 0.0078125f).endVertex();
        worldRenderer.pos(n + (0x4B ^ 0x59), n2 + (0x98 ^ 0x8A), this.zLevel).tex((n3 + (0x3F ^ 0x2D)) * 0.0078125f, (n4 + (0x2A ^ 0x38)) * 0.0078125f).endVertex();
        worldRenderer.pos(n + (0xAC ^ 0xBE), n2 + "".length(), this.zLevel).tex((n3 + (0xD7 ^ 0xC5)) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
        worldRenderer.pos(n + "".length(), n2 + "".length(), this.zLevel).tex((n3 + "".length()) * 0.0078125f, (n4 + "".length()) * 0.0078125f).endVertex();
        instance.draw();
    }
    
    @Override
    public void doneLoading() {
        if (this.doesGuiPauseGame) {
            this.func_175366_f();
            this.createButtons();
            this.displaySlot = this.generalStats;
            this.doesGuiPauseGame = ("".length() != 0);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        int n;
        if (this.doesGuiPauseGame) {
            n = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    static void access$0(final GuiStats guiStats, final int n, final int n2, final int n3, final int n4) {
        guiStats.drawSprite(n, n2, n3, n4);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.doesGuiPauseGame) {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, I18n.format(GuiStats.I[0x54 ^ 0x53], new Object["".length()]), this.width / "  ".length(), this.height / "  ".length(), 637157 + 13036986 - 4223466 + 7326538);
            this.drawCenteredString(this.fontRendererObj, GuiStats.lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % GuiStats.lanSearchStates.length)], this.width / "  ".length(), this.height / "  ".length() + this.fontRendererObj.FONT_HEIGHT * "  ".length(), 1347751 + 11648992 - 6749947 + 10530419);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            this.displaySlot.drawScreen(n, n2, n3);
            this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / "  ".length(), 0x2C ^ 0x38, 3080299 + 7735579 + 5546072 + 415265);
            super.drawScreen(n, n2, n3);
        }
    }
    
    public GuiStats(final GuiScreen parentScreen, final StatFileWriter field_146546_t) {
        this.screenTitle = GuiStats.I["".length()];
        this.doesGuiPauseGame = (" ".length() != 0);
        this.parentScreen = parentScreen;
        this.field_146546_t = field_146546_t;
    }
    
    abstract class Stats extends GuiSlot
    {
        private static final String[] I;
        protected int field_148215_p;
        protected List<StatCrafting> statsHolder;
        protected int field_148218_l;
        protected int field_148217_o;
        protected Comparator<StatCrafting> statSorter;
        final GuiStats this$0;
        
        @Override
        protected boolean isSelected(final int n) {
            return "".length() != 0;
        }
        
        protected void func_148212_h(final int field_148217_o) {
            if (field_148217_o != this.field_148217_o) {
                this.field_148217_o = field_148217_o;
                this.field_148215_p = -" ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if (this.field_148215_p == -" ".length()) {
                this.field_148215_p = " ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                this.field_148217_o = -" ".length();
                this.field_148215_p = "".length();
            }
            Collections.sort(this.statsHolder, this.statSorter);
        }
        
        protected Stats(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0x5 ^ 0x25, this$0.height - (0xD8 ^ 0x98), 0x55 ^ 0x41);
            this.field_148218_l = -" ".length();
            this.field_148217_o = -" ".length();
            this.setShowSelectionBox("".length() != 0);
            this.setHasListHeader(" ".length() != 0, 0xD3 ^ 0xC7);
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected void func_148132_a(final int n, final int n2) {
            this.field_148218_l = -" ".length();
            if (n >= (0x31 ^ 0x7E) && n < (0xC ^ 0x7F)) {
                this.field_148218_l = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else if (n >= 72 + 63 - 98 + 92 && n < 14 + 33 - 26 + 144) {
                this.field_148218_l = " ".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else if (n >= 37 + 72 + 16 + 54 && n < 103 + 62 - 36 + 86) {
                this.field_148218_l = "  ".length();
            }
            if (this.field_148218_l >= 0) {
                this.func_148212_h(this.field_148218_l);
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(Stats.I["".length()]), 1.0f));
            }
        }
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            if (!Mouse.isButtonDown("".length())) {
                this.field_148218_l = -" ".length();
            }
            if (this.field_148218_l == 0) {
                GuiStats.access$0(this.this$0, n + (0x48 ^ 0x3B) - (0x54 ^ 0x46), n2 + " ".length(), "".length(), "".length());
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (0xEE ^ 0x9D) - (0xAA ^ 0xB8), n2 + " ".length(), "".length(), 0xB3 ^ 0xA1);
            }
            if (this.field_148218_l == " ".length()) {
                GuiStats.access$0(this.this$0, n + (148 + 114 - 148 + 51) - (0x7F ^ 0x6D), n2 + " ".length(), "".length(), "".length());
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (20 + 152 - 152 + 145) - (0x27 ^ 0x35), n2 + " ".length(), "".length(), 0x63 ^ 0x71);
            }
            if (this.field_148218_l == "  ".length()) {
                GuiStats.access$0(this.this$0, n + (93 + 50 - 85 + 157) - (0x37 ^ 0x25), n2 + " ".length(), "".length(), "".length());
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (142 + 161 - 131 + 43) - (0x28 ^ 0x3A), n2 + " ".length(), "".length(), 0x22 ^ 0x30);
            }
            if (this.field_148217_o != -" ".length()) {
                int n3 = 0x2 ^ 0x4D;
                int n4 = 0xAA ^ 0xB8;
                if (this.field_148217_o == " ".length()) {
                    n3 = 22 + 26 + 16 + 65;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (this.field_148217_o == "  ".length()) {
                    n3 = 131 + 126 - 166 + 88;
                }
                if (this.field_148215_p == " ".length()) {
                    n4 = (0x45 ^ 0x61);
                }
                GuiStats.access$0(this.this$0, n + n3, n2 + " ".length(), n4, "".length());
            }
        }
        
        protected final StatCrafting func_148211_c(final int n) {
            return this.statsHolder.get(n);
        }
        
        protected void func_148213_a(final StatCrafting statCrafting, final int n, final int n2) {
            if (statCrafting != null) {
                final String trim = new StringBuilder().append(I18n.format(String.valueOf(new ItemStack(statCrafting.func_150959_a()).getUnlocalizedName()) + Stats.I["   ".length()], new Object["".length()])).toString().trim();
                if (trim.length() > 0) {
                    final int n3 = n + (0x23 ^ 0x2F);
                    final int n4 = n2 - (0x2E ^ 0x22);
                    GuiStats.access$3(this.this$0, n3 - "   ".length(), n4 - "   ".length(), n3 + GuiStats.access$2(this.this$0).getStringWidth(trim) + "   ".length(), n4 + (0x56 ^ 0x5E) + "   ".length(), -(854463981 + 309118731 - 1130926743 + 1041085855), -(201429545 + 1032864037 - 831222824 + 670671066));
                    GuiStats.access$2(this.this$0).drawStringWithShadow(trim, n3, n4, -" ".length());
                }
            }
        }
        
        @Override
        protected void func_148142_b(final int n, final int n2) {
            if (n2 >= this.top && n2 <= this.bottom) {
                final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
                final int n3 = this.width / "  ".length() - (0x33 ^ 0x6F) - (0x5F ^ 0x4F);
                if (slotIndexFromScreenCoords >= 0) {
                    if (n < n3 + (0x3C ^ 0x14) || n > n3 + (0x82 ^ 0xAA) + (0x2A ^ 0x3E)) {
                        return;
                    }
                    this.func_148213_a(this.func_148211_c(slotIndexFromScreenCoords), n, n2);
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    final String s = Stats.I["  ".length()];
                    String s2;
                    if (n >= n3 + (0x2C ^ 0x5F) - (0x8 ^ 0x1A) && n <= n3 + (0x40 ^ 0x33)) {
                        s2 = this.func_148210_b("".length());
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    else if (n >= n3 + (74 + 53 + 13 + 25) - (0xD4 ^ 0xC6) && n <= n3 + (159 + 27 - 157 + 136)) {
                        s2 = this.func_148210_b(" ".length());
                        "".length();
                        if (4 < -1) {
                            throw null;
                        }
                    }
                    else {
                        if (n < n3 + (163 + 204 - 192 + 40) - (0x50 ^ 0x42) || n > n3 + (144 + 174 - 308 + 205)) {
                            return;
                        }
                        s2 = this.func_148210_b("  ".length());
                    }
                    final String trim = new StringBuilder().append(I18n.format(s2, new Object["".length()])).toString().trim();
                    if (trim.length() > 0) {
                        final int n4 = n + (0xAD ^ 0xA1);
                        final int n5 = n2 - (0x7D ^ 0x71);
                        GuiStats.access$3(this.this$0, n4 - "   ".length(), n5 - "   ".length(), n4 + GuiStats.access$2(this.this$0).getStringWidth(trim) + "   ".length(), n5 + (0x98 ^ 0x90) + "   ".length(), -(348523321 + 45557352 - 268530740 + 948191891), -(440271805 + 858938348 - 725334068 + 499865739));
                        GuiStats.access$2(this.this$0).drawStringWithShadow(trim, n4, n5, -" ".length());
                    }
                }
            }
        }
        
        protected void func_148209_a(final StatBase statBase, final int n, final int n2, final boolean b) {
            if (statBase != null) {
                final String format = statBase.format(GuiStats.access$1(this.this$0).readStat(statBase));
                final GuiStats this$0 = this.this$0;
                final FontRenderer access$2 = GuiStats.access$2(this.this$0);
                final String s = format;
                final int n3 = n - GuiStats.access$2(this.this$0).getStringWidth(format);
                final int n4 = n2 + (0x24 ^ 0x21);
                int n5;
                if (b) {
                    n5 = 16117229 + 11268941 - 21654278 + 11045323;
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else {
                    n5 = 3253119 + 8987310 - 10665663 + 7899426;
                }
                this$0.drawString(access$2, s, n3, n4, n5);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                final String s2 = Stats.I[" ".length()];
                final GuiStats this$2 = this.this$0;
                final FontRenderer access$3 = GuiStats.access$2(this.this$0);
                final String s3 = s2;
                final int n6 = n - GuiStats.access$2(this.this$0).getStringWidth(s2);
                final int n7 = n2 + (0x15 ^ 0x10);
                int n8;
                if (b) {
                    n8 = 3676004 + 3305976 + 9089993 + 705242;
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    n8 = 3329878 + 6062600 - 2127712 + 2209426;
                }
                this$2.drawString(access$3, s3, n6, n7, n8);
            }
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[0x49 ^ 0x4D])["".length()] = I(".\u0017.m'<\u00163,+g\u00125&6:", "IbGCE");
            Stats.I[" ".length()] = I("\\", "qplaK");
            Stats.I["  ".length()] = I("", "zkMON");
            Stats.I["   ".length()] = I("I>#.\r", "gPBCh");
        }
        
        @Override
        protected final int getSize() {
            return this.statsHolder.size();
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
                if (4 < 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        protected abstract String func_148210_b(final int p0);
    }
    
    class StatsItem extends Stats
    {
        private static final String[] I;
        final GuiStats this$0;
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatCrafting func_148211_c = this.func_148211_c(n);
            final Item func_150959_a = func_148211_c.func_150959_a();
            GuiStats.access$4(this.this$0, n2 + (0x3E ^ 0x16), n3, func_150959_a);
            final int idFromItem = Item.getIdFromItem(func_150959_a);
            final StatBase statBase = StatList.objectBreakStats[idFromItem];
            final int n7 = n2 + (0x2F ^ 0x5C);
            int n8;
            if (n % "  ".length() == 0) {
                n8 = " ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
            }
            this.func_148209_a(statBase, n7, n3, n8 != 0);
            final StatBase statBase2 = StatList.objectCraftStats[idFromItem];
            final int n9 = n2 + (70 + 91 - 100 + 104);
            int n10;
            if (n % "  ".length() == 0) {
                n10 = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                n10 = "".length();
            }
            this.func_148209_a(statBase2, n9, n3, n10 != 0);
            final StatCrafting statCrafting = func_148211_c;
            final int n11 = n2 + (137 + 56 - 189 + 211);
            int n12;
            if (n % "  ".length() == 0) {
                n12 = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n12 = "".length();
            }
            this.func_148209_a(statCrafting, n11, n3, n12 != 0);
        }
        
        static GuiStats access$0(final StatsItem statsItem) {
            return statsItem.this$0;
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            super.drawListHeader(n, n2, tessellator);
            if (this.field_148218_l == 0) {
                GuiStats.access$0(this.this$0, n + (0xF9 ^ 0x8A) - (0x23 ^ 0x31) + " ".length(), n2 + " ".length() + " ".length(), 0x5E ^ 0x16, 0x69 ^ 0x7B);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (0x26 ^ 0x55) - (0x54 ^ 0x46), n2 + " ".length(), 0xFF ^ 0xB7, 0x89 ^ 0x9B);
            }
            if (this.field_148218_l == " ".length()) {
                GuiStats.access$0(this.this$0, n + (136 + 161 - 260 + 128) - (0x4F ^ 0x5D) + " ".length(), n2 + " ".length() + " ".length(), 0x26 ^ 0x34, 0xB5 ^ 0xA7);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (53 + 116 - 56 + 52) - (0xA5 ^ 0xB7), n2 + " ".length(), 0xAE ^ 0xBC, 0x74 ^ 0x66);
            }
            if (this.field_148218_l == "  ".length()) {
                GuiStats.access$0(this.this$0, n + (130 + 65 - 159 + 179) - (0x46 ^ 0x54) + " ".length(), n2 + " ".length() + " ".length(), 0x41 ^ 0x65, 0x83 ^ 0x91);
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (148 + 0 + 58 + 9) - (0x82 ^ 0x90), n2 + " ".length(), 0x95 ^ 0xB1, 0xBB ^ 0xA9);
            }
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("=\u00158 J-\u001382\u0010+\u0005", "NaYTd");
            StatsItem.I[" ".length()] = I("$6\u0006 Y\"1\u00020", "WBgTw");
            StatsItem.I["  ".length()] = I(" \"\u0017\u0001C73\u0006\u0019\b'3\u0012", "SVvum");
        }
        
        public StatsItem(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0.super(minecraft);
            this.statsHolder = (List<StatCrafting>)Lists.newArrayList();
            final Iterator<StatCrafting> iterator = StatList.itemStats.iterator();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final StatCrafting statCrafting = iterator.next();
                int n = "".length();
                final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                if (GuiStats.access$1(this$0).readStat(statCrafting) > 0) {
                    n = " ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (StatList.objectBreakStats[idFromItem] != null && GuiStats.access$1(this$0).readStat(StatList.objectBreakStats[idFromItem]) > 0) {
                    n = " ".length();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else if (StatList.objectCraftStats[idFromItem] != null && GuiStats.access$1(this$0).readStat(StatList.objectCraftStats[idFromItem]) > 0) {
                    n = " ".length();
                }
                if (n != 0) {
                    this.statsHolder.add(statCrafting);
                }
            }
            this.statSorter = new Comparator<StatCrafting>(this) {
                final StatsItem this$1;
                
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
                        if (4 == 3) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public int compare(final StatCrafting statCrafting, final StatCrafting statCrafting2) {
                    final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                    final int idFromItem2 = Item.getIdFromItem(statCrafting2.func_150959_a());
                    StatBase statBase = null;
                    StatBase statBase2 = null;
                    if (this.this$1.field_148217_o == 0) {
                        statBase = StatList.objectBreakStats[idFromItem];
                        statBase2 = StatList.objectBreakStats[idFromItem2];
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                    }
                    else if (this.this$1.field_148217_o == " ".length()) {
                        statBase = StatList.objectCraftStats[idFromItem];
                        statBase2 = StatList.objectCraftStats[idFromItem2];
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    else if (this.this$1.field_148217_o == "  ".length()) {
                        statBase = StatList.objectUseStats[idFromItem];
                        statBase2 = StatList.objectUseStats[idFromItem2];
                    }
                    if (statBase != null || statBase2 != null) {
                        if (statBase == null) {
                            return " ".length();
                        }
                        if (statBase2 == null) {
                            return -" ".length();
                        }
                        final int stat = GuiStats.access$1(StatsItem.access$0(this.this$1)).readStat(statBase);
                        final int stat2 = GuiStats.access$1(StatsItem.access$0(this.this$1)).readStat(statBase2);
                        if (stat != stat2) {
                            return (stat - stat2) * this.this$1.field_148215_p;
                        }
                    }
                    return idFromItem - idFromItem2;
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((StatCrafting)o, (StatCrafting)o2);
                }
            };
        }
        
        @Override
        protected String func_148210_b(final int n) {
            String s;
            if (n == " ".length()) {
                s = StatsItem.I["".length()];
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else if (n == "  ".length()) {
                s = StatsItem.I[" ".length()];
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                s = StatsItem.I["  ".length()];
            }
            return s;
        }
    }
    
    class StatsGeneral extends GuiSlot
    {
        final GuiStats this$0;
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected int getSize() {
            return StatList.generalStats.size();
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return "".length() != 0;
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
                if (false == true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * (0x17 ^ 0x1D);
        }
        
        public StatsGeneral(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0xAF ^ 0x8F, this$0.height - (0xCD ^ 0x8D), 0x32 ^ 0x38);
            this.setShowSelectionBox("".length() != 0);
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatBase statBase = StatList.generalStats.get(n);
            final GuiStats this$0 = this.this$0;
            final FontRenderer access$2 = GuiStats.access$2(this.this$0);
            final String unformattedText = statBase.getStatName().getUnformattedText();
            final int n7 = n2 + "  ".length();
            final int n8 = n3 + " ".length();
            int n9;
            if (n % "  ".length() == 0) {
                n9 = 16575108 + 4417815 - 6315472 + 2099764;
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                n9 = 2044070 + 1603001 + 3066105 + 2761016;
            }
            this$0.drawString(access$2, unformattedText, n7, n8, n9);
            final String format = statBase.format(GuiStats.access$1(this.this$0).readStat(statBase));
            final GuiStats this$2 = this.this$0;
            final FontRenderer access$3 = GuiStats.access$2(this.this$0);
            final String s = format;
            final int n10 = n2 + "  ".length() + (68 + 196 - 98 + 47) - GuiStats.access$2(this.this$0).getStringWidth(format);
            final int n11 = n3 + " ".length();
            int n12;
            if (n % "  ".length() == 0) {
                n12 = 3006404 + 12188417 - 12515529 + 14097923;
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            else {
                n12 = 2091175 + 1271068 + 73507 + 6038442;
            }
            this$2.drawString(access$3, s, n10, n11, n12);
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
    }
    
    class StatsBlock extends Stats
    {
        private static final String[] I;
        final GuiStats this$0;
        
        static GuiStats access$0(final StatsBlock statsBlock) {
            return statsBlock.this$0;
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
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final StatCrafting func_148211_c = this.func_148211_c(n);
            final Item func_150959_a = func_148211_c.func_150959_a();
            GuiStats.access$4(this.this$0, n2 + (0x14 ^ 0x3C), n3, func_150959_a);
            final int idFromItem = Item.getIdFromItem(func_150959_a);
            final StatBase statBase = StatList.objectCraftStats[idFromItem];
            final int n7 = n2 + (0x23 ^ 0x50);
            int n8;
            if (n % "  ".length() == 0) {
                n8 = " ".length();
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
            }
            this.func_148209_a(statBase, n7, n3, n8 != 0);
            final StatBase statBase2 = StatList.objectUseStats[idFromItem];
            final int n9 = n2 + (103 + 31 - 69 + 100);
            int n10;
            if (n % "  ".length() == 0) {
                n10 = " ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                n10 = "".length();
            }
            this.func_148209_a(statBase2, n9, n3, n10 != 0);
            final StatCrafting statCrafting = func_148211_c;
            final int n11 = n2 + (59 + 1 + 88 + 67);
            int n12;
            if (n % "  ".length() == 0) {
                n12 = " ".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                n12 = "".length();
            }
            this.func_148209_a(statCrafting, n11, n3, n12 != 0);
        }
        
        @Override
        protected String func_148210_b(final int n) {
            String s;
            if (n == 0) {
                s = StatsBlock.I["".length()];
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (n == " ".length()) {
                s = StatsBlock.I[" ".length()];
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                s = StatsBlock.I["  ".length()];
            }
            return s;
        }
        
        static {
            I();
        }
        
        @Override
        protected void drawListHeader(final int n, final int n2, final Tessellator tessellator) {
            super.drawListHeader(n, n2, tessellator);
            if (this.field_148218_l == 0) {
                GuiStats.access$0(this.this$0, n + (0x49 ^ 0x3A) - (0x99 ^ 0x8B) + " ".length(), n2 + " ".length() + " ".length(), 0x81 ^ 0x93, 0xE ^ 0x1C);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (0x68 ^ 0x1B) - (0x58 ^ 0x4A), n2 + " ".length(), 0xBF ^ 0xAD, 0x8D ^ 0x9F);
            }
            if (this.field_148218_l == " ".length()) {
                GuiStats.access$0(this.this$0, n + (55 + 4 + 36 + 70) - (0xF ^ 0x1D) + " ".length(), n2 + " ".length() + " ".length(), 0xB8 ^ 0x9C, 0x49 ^ 0x5B);
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (144 + 157 - 163 + 27) - (0x43 ^ 0x51), n2 + " ".length(), 0x3 ^ 0x27, 0x4A ^ 0x58);
            }
            if (this.field_148218_l == "  ".length()) {
                GuiStats.access$0(this.this$0, n + (77 + 64 - 17 + 91) - (0x36 ^ 0x24) + " ".length(), n2 + " ".length() + " ".length(), 0x59 ^ 0x6F, 0x4B ^ 0x59);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                GuiStats.access$0(this.this$0, n + (202 + 202 - 312 + 123) - (0x6 ^ 0x14), n2 + " ".length(), 0x28 ^ 0x1E, 0x57 ^ 0x45);
            }
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("%-\u0017=K5+\u0017/\u00113=", "VYvIe");
            StatsBlock.I[" ".length()] = I(":\u001b\u0007\u0007O<\u001c\u0003\u0017", "Iofsa");
            StatsBlock.I["  ".length()] = I("40\b\u001cl*-\u0007\r&", "GDihB");
        }
        
        public StatsBlock(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0.super(minecraft);
            this.statsHolder = (List<StatCrafting>)Lists.newArrayList();
            final Iterator<StatCrafting> iterator = StatList.objectMineStats.iterator();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final StatCrafting statCrafting = iterator.next();
                int n = "".length();
                final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                if (GuiStats.access$1(this$0).readStat(statCrafting) > 0) {
                    n = " ".length();
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else if (StatList.objectUseStats[idFromItem] != null && GuiStats.access$1(this$0).readStat(StatList.objectUseStats[idFromItem]) > 0) {
                    n = " ".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                }
                else if (StatList.objectCraftStats[idFromItem] != null && GuiStats.access$1(this$0).readStat(StatList.objectCraftStats[idFromItem]) > 0) {
                    n = " ".length();
                }
                if (n != 0) {
                    this.statsHolder.add(statCrafting);
                }
            }
            this.statSorter = new Comparator<StatCrafting>(this) {
                final StatsBlock this$1;
                
                @Override
                public int compare(final StatCrafting statCrafting, final StatCrafting statCrafting2) {
                    final int idFromItem = Item.getIdFromItem(statCrafting.func_150959_a());
                    final int idFromItem2 = Item.getIdFromItem(statCrafting2.func_150959_a());
                    StatBase statBase = null;
                    StatBase statBase2 = null;
                    if (this.this$1.field_148217_o == "  ".length()) {
                        statBase = StatList.mineBlockStatArray[idFromItem];
                        statBase2 = StatList.mineBlockStatArray[idFromItem2];
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    else if (this.this$1.field_148217_o == 0) {
                        statBase = StatList.objectCraftStats[idFromItem];
                        statBase2 = StatList.objectCraftStats[idFromItem2];
                        "".length();
                        if (-1 == 3) {
                            throw null;
                        }
                    }
                    else if (this.this$1.field_148217_o == " ".length()) {
                        statBase = StatList.objectUseStats[idFromItem];
                        statBase2 = StatList.objectUseStats[idFromItem2];
                    }
                    if (statBase != null || statBase2 != null) {
                        if (statBase == null) {
                            return " ".length();
                        }
                        if (statBase2 == null) {
                            return -" ".length();
                        }
                        final int stat = GuiStats.access$1(StatsBlock.access$0(this.this$1)).readStat(statBase);
                        final int stat2 = GuiStats.access$1(StatsBlock.access$0(this.this$1)).readStat(statBase2);
                        if (stat != stat2) {
                            return (stat - stat2) * this.this$1.field_148215_p;
                        }
                    }
                    return idFromItem - idFromItem2;
                }
                
                @Override
                public int compare(final Object o, final Object o2) {
                    return this.compare((StatCrafting)o, (StatCrafting)o2);
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
            };
        }
    }
    
    class StatsMobsList extends GuiSlot
    {
        final GuiStats this$0;
        private final List<EntityList.EntityEggInfo> field_148222_l;
        private static final String[] I;
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final EntityList.EntityEggInfo entityEggInfo = this.field_148222_l.get(n);
            final String format = I18n.format(StatsMobsList.I["".length()] + EntityList.getStringFromID(entityEggInfo.spawnedID) + StatsMobsList.I[" ".length()], new Object["".length()]);
            final int stat = GuiStats.access$1(this.this$0).readStat(entityEggInfo.field_151512_d);
            final int stat2 = GuiStats.access$1(this.this$0).readStat(entityEggInfo.field_151513_e);
            final String s = StatsMobsList.I["  ".length()];
            final Object[] array = new Object["  ".length()];
            array["".length()] = stat;
            array[" ".length()] = format;
            String s2 = I18n.format(s, array);
            final String s3 = StatsMobsList.I["   ".length()];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = format;
            array2[" ".length()] = stat2;
            String s4 = I18n.format(s3, array2);
            if (stat == 0) {
                final String s5 = StatsMobsList.I[0x29 ^ 0x2D];
                final Object[] array3 = new Object[" ".length()];
                array3["".length()] = format;
                s2 = I18n.format(s5, array3);
            }
            if (stat2 == 0) {
                final String s6 = StatsMobsList.I[0x46 ^ 0x43];
                final Object[] array4 = new Object[" ".length()];
                array4["".length()] = format;
                s4 = I18n.format(s6, array4);
            }
            this.this$0.drawString(GuiStats.access$2(this.this$0), format, n2 + "  ".length() - (0x48 ^ 0x42), n3 + " ".length(), 3671071 + 13725638 - 1528953 + 909459);
            final GuiStats this$0 = this.this$0;
            final FontRenderer access$2 = GuiStats.access$2(this.this$0);
            final String s7 = s2;
            final int n7 = n2 + "  ".length();
            final int n8 = n3 + " ".length() + GuiStats.access$2(this.this$0).FONT_HEIGHT;
            int n9;
            if (stat == 0) {
                n9 = 5888594 + 4797729 - 8409652 + 4039457;
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                n9 = 8296404 + 1911582 - 7918187 + 7184393;
            }
            this$0.drawString(access$2, s7, n7, n8, n9);
            final GuiStats this$2 = this.this$0;
            final FontRenderer access$3 = GuiStats.access$2(this.this$0);
            final String s8 = s4;
            final int n10 = n2 + "  ".length();
            final int n11 = n3 + " ".length() + GuiStats.access$2(this.this$0).FONT_HEIGHT * "  ".length();
            int n12;
            if (stat2 == 0) {
                n12 = 4024347 + 1499458 - 1054197 + 1846520;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n12 = 4807634 + 6578910 - 4116289 + 2203937;
            }
            this$2.drawString(access$3, s8, n10, n11, n12);
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
                if (1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected int getSize() {
            return this.field_148222_l.size();
        }
        
        private static void I() {
            (I = new String[0x14 ^ 0x12])["".length()] = I("0'3,',g", "UIGES");
            StatsMobsList.I[" ".length()] = I("a?\u0011<\b", "OQpQm");
            StatsMobsList.I["  ".length()] = I("<9\n,t*#\u001f1.6\u0006\u000246<", "OMkXZ");
            StatsMobsList.I["   ".length()] = I("\u001d=\b.Y\u000b'\u001d3\u0003\u0017\u0002\u00006\u001b\u000b-+#", "nIiZw");
            StatsMobsList.I[0x78 ^ 0x7C] = I("\u001f6%'B\t,0:\u0018\u0015\t-?\u0000\u001fl*<\u0002\t", "lBDSl");
            StatsMobsList.I[0x27 ^ 0x22] = I("\u0002\u0002\u00100E\u0014\u0018\u0005-\u001f\b=\u0018(\u0007\u0014\u00123=E\u001f\u0019\u001f!", "qvqDk");
        }
        
        @Override
        protected void drawBackground() {
            this.this$0.drawDefaultBackground();
        }
        
        @Override
        protected int getContentHeight() {
            return this.getSize() * GuiStats.access$2(this.this$0).FONT_HEIGHT * (0x78 ^ 0x7C);
        }
        
        @Override
        protected boolean isSelected(final int n) {
            return "".length() != 0;
        }
        
        public StatsMobsList(final GuiStats this$0, final Minecraft minecraft) {
            this.this$0 = this$0;
            super(minecraft, this$0.width, this$0.height, 0xA3 ^ 0x83, this$0.height - (0xFF ^ 0xBF), GuiStats.access$2(this$0).FONT_HEIGHT * (0x21 ^ 0x25));
            this.field_148222_l = (List<EntityList.EntityEggInfo>)Lists.newArrayList();
            this.setShowSelectionBox("".length() != 0);
            final Iterator<EntityList.EntityEggInfo> iterator = EntityList.entityEggs.values().iterator();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityList.EntityEggInfo entityEggInfo = iterator.next();
                if (GuiStats.access$1(this$0).readStat(entityEggInfo.field_151512_d) > 0 || GuiStats.access$1(this$0).readStat(entityEggInfo.field_151513_e) > 0) {
                    this.field_148222_l.add(entityEggInfo);
                }
            }
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
    }
}
