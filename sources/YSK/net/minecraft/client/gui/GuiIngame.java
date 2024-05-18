package net.minecraft.client.gui;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import optfine.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.border.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.entity.boss.*;
import net.minecraft.client.renderer.texture.*;

public class GuiIngame extends Gui
{
    private final GuiSpectator spectatorGui;
    private String recordPlaying;
    private final GuiPlayerTabOverlay overlayPlayerList;
    private final GuiNewChat persistantChatGUI;
    private int field_175193_B;
    private static final String[] I;
    private String field_175201_x;
    private String field_175200_y;
    private static final ResourceLocation pumpkinBlurTexPath;
    private ItemStack highlightingItemStack;
    private final RenderItem itemRenderer;
    private int field_175192_A;
    private long lastSystemTime;
    private int recordPlayingUpFor;
    private final Minecraft mc;
    public float prevVignetteBrightness;
    private int field_175199_z;
    private static final String __OBFID;
    private static final ResourceLocation widgetsTexPath;
    private final GuiStreamIndicator streamIndicator;
    private final GuiOverlayDebug overlayDebug;
    private int field_175195_w;
    private int updateCounter;
    private final Random rand;
    private boolean recordIsPlaying;
    private static final ResourceLocation vignetteTexPath;
    private int lastPlayerHealth;
    private long healthUpdateCounter;
    private int remainingHighlightTicks;
    private int playerHealth;
    
    public GuiIngame(final Minecraft mc) {
        this.rand = new Random();
        this.recordPlaying = GuiIngame.I[0x21 ^ 0x25];
        this.prevVignetteBrightness = 1.0f;
        this.field_175201_x = GuiIngame.I[0x9A ^ 0x9F];
        this.field_175200_y = GuiIngame.I[0x49 ^ 0x4F];
        this.playerHealth = "".length();
        this.lastPlayerHealth = "".length();
        this.lastSystemTime = 0L;
        this.healthUpdateCounter = 0L;
        this.mc = mc;
        this.itemRenderer = mc.getRenderItem();
        this.overlayDebug = new GuiOverlayDebug(mc);
        this.spectatorGui = new GuiSpectator(mc);
        this.persistantChatGUI = new GuiNewChat(mc);
        this.streamIndicator = new GuiStreamIndicator(mc);
        this.overlayPlayerList = new GuiPlayerTabOverlay(mc, this);
        this.func_175177_a();
    }
    
    protected boolean showCrosshair() {
        if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            return "".length() != 0;
        }
        if (!this.mc.playerController.isSpectator()) {
            return " ".length() != 0;
        }
        if (this.mc.pointedEntity != null) {
            return " ".length() != 0;
        }
        if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.mc.theWorld.getTileEntity(this.mc.objectMouseOver.getBlockPos()) instanceof IInventory) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void displayTitle(final String field_175201_x, final String field_175200_y, final int field_175199_z, final int field_175192_A, final int field_175193_B) {
        if (field_175201_x == null && field_175200_y == null && field_175199_z < 0 && field_175192_A < 0 && field_175193_B < 0) {
            this.field_175201_x = GuiIngame.I[0xBE ^ 0xA3];
            this.field_175200_y = GuiIngame.I[0x58 ^ 0x46];
            this.field_175195_w = "".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (field_175201_x != null) {
            this.field_175201_x = field_175201_x;
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (field_175200_y != null) {
            this.field_175200_y = field_175200_y;
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            if (field_175199_z >= 0) {
                this.field_175199_z = field_175199_z;
            }
            if (field_175192_A >= 0) {
                this.field_175192_A = field_175192_A;
            }
            if (field_175193_B >= 0) {
                this.field_175193_B = field_175193_B;
            }
            if (this.field_175195_w > 0) {
                this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
            }
        }
    }
    
    public int getUpdateCounter() {
        return this.updateCounter;
    }
    
    public void renderExpBar(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection(GuiIngame.I[0x1C ^ 0x11]);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        if (this.mc.thePlayer.xpBarCap() > 0) {
            final int n2 = 126 + 126 - 124 + 54;
            final int n3 = (int)(this.mc.thePlayer.experience * (n2 + " ".length()));
            final int n4 = scaledResolution.getScaledHeight() - (0x69 ^ 0x49) + "   ".length();
            this.drawTexturedModalRect(n, n4, "".length(), 0x28 ^ 0x68, n2, 0xA ^ 0xF);
            if (n3 > 0) {
                this.drawTexturedModalRect(n, n4, "".length(), 0x21 ^ 0x64, n3, 0xB0 ^ 0xB5);
            }
        }
        this.mc.mcProfiler.endSection();
        if (this.mc.thePlayer.experienceLevel > 0) {
            this.mc.mcProfiler.startSection(GuiIngame.I[0x29 ^ 0x27]);
            final int n5 = 2927654 + 2626216 - 3258810 + 6158860;
            final String string = new StringBuilder().append(this.mc.thePlayer.experienceLevel).toString();
            final int n6 = (scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(string)) / "  ".length();
            final int n7 = scaledResolution.getScaledHeight() - (0xBF ^ 0xA0) - (0x53 ^ 0x57);
            "".length();
            this.getFontRenderer().drawString(string, n6 + " ".length(), n7, "".length());
            this.getFontRenderer().drawString(string, n6 - " ".length(), n7, "".length());
            this.getFontRenderer().drawString(string, n6, n7 + " ".length(), "".length());
            this.getFontRenderer().drawString(string, n6, n7 - " ".length(), "".length());
            this.getFontRenderer().drawString(string, n6, n7, n5);
            this.mc.mcProfiler.endSection();
        }
    }
    
    protected void renderTooltip(final ScaledResolution scaledResolution, final float n) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.widgetsTexPath);
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
            final int n2 = scaledResolution.getScaledWidth() / "  ".length();
            final float zLevel = this.zLevel;
            this.zLevel = -90.0f;
            this.drawTexturedModalRect(n2 - (0x74 ^ 0x2F), scaledResolution.getScaledHeight() - (0x70 ^ 0x66), "".length(), "".length(), 166 + 55 - 172 + 133, 0x41 ^ 0x57);
            this.drawTexturedModalRect(n2 - (0xEE ^ 0xB5) - " ".length() + entityPlayer.inventory.currentItem * (0xA5 ^ 0xB1), scaledResolution.getScaledHeight() - (0xB6 ^ 0xA0) - " ".length(), "".length(), 0x1A ^ 0xC, 0x4C ^ 0x54, 0x15 ^ 0x3);
            this.zLevel = zLevel;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(274 + 61 - 194 + 629, 670 + 48 - 462 + 515, " ".length(), "".length());
            RenderHelper.enableGUIStandardItemLighting();
            int i = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
            while (i < (0xB0 ^ 0xB9)) {
                this.renderHotbarItem(i, scaledResolution.getScaledWidth() / "  ".length() - (0x5B ^ 0x1) + i * (0x8C ^ 0x98) + "  ".length(), scaledResolution.getScaledHeight() - (0xD2 ^ 0xC2) - "   ".length(), n, entityPlayer);
                ++i;
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
    
    private void renderVignette(float clamp_float, final ScaledResolution scaledResolution) {
        if (Config.isVignetteEnabled()) {
            clamp_float = 1.0f - clamp_float;
            clamp_float = MathHelper.clamp_float(clamp_float, 0.0f, 1.0f);
            final WorldBorder worldBorder = this.mc.theWorld.getWorldBorder();
            final float n = (float)worldBorder.getClosestDistance(this.mc.thePlayer);
            final double max = Math.max(worldBorder.getWarningDistance(), Math.min(worldBorder.getResizeSpeed() * worldBorder.getWarningTime() * 1000.0, Math.abs(worldBorder.getTargetSize() - worldBorder.getDiameter())));
            float n2;
            if (n < max) {
                n2 = 1.0f - (float)(n / max);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else {
                n2 = 0.0f;
            }
            this.prevVignetteBrightness += (float)((clamp_float - this.prevVignetteBrightness) * 0.01);
            GlStateManager.disableDepth();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.tryBlendFuncSeparate("".length(), 588 + 496 - 389 + 74, " ".length(), "".length());
            if (n2 > 0.0f) {
                GlStateManager.color(0.0f, n2, n2, 1.0f);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
            }
            this.mc.getTextureManager().bindTexture(GuiIngame.vignetteTexPath);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0x22 ^ 0x25, DefaultVertexFormats.POSITION_TEX);
            worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
            worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
            worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
            worldRenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
            instance.draw();
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.enableDepth();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(696 + 421 - 863 + 516, 453 + 596 - 335 + 57, " ".length(), "".length());
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
    
    public void func_181029_i() {
        this.overlayPlayerList.func_181030_a();
    }
    
    public void setRecordPlayingMessage(final String s) {
        final String s2 = GuiIngame.I[0x52 ^ 0x4E];
        final Object[] array = new Object[" ".length()];
        array["".length()] = s;
        this.setRecordPlaying(I18n.format(s2, array), " ".length() != 0);
    }
    
    static {
        I();
        __OBFID = GuiIngame.I["".length()];
        vignetteTexPath = new ResourceLocation(GuiIngame.I[" ".length()]);
        widgetsTexPath = new ResourceLocation(GuiIngame.I["  ".length()]);
        pumpkinBlurTexPath = new ResourceLocation(GuiIngame.I["   ".length()]);
    }
    
    public void renderStreamIndicator(final ScaledResolution scaledResolution) {
        this.streamIndicator.render(scaledResolution.getScaledWidth() - (0x5D ^ 0x57), 0x91 ^ 0x9B);
    }
    
    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            this.recordPlayingUpFor -= " ".length();
        }
        if (this.field_175195_w > 0) {
            this.field_175195_w -= " ".length();
            if (this.field_175195_w <= 0) {
                this.field_175201_x = GuiIngame.I[0xDA ^ 0xC0];
                this.field_175200_y = GuiIngame.I[0x9E ^ 0x85];
            }
        }
        this.updateCounter += " ".length();
        this.streamIndicator.func_152439_a();
        if (this.mc.thePlayer != null) {
            final ItemStack currentItem = this.mc.thePlayer.inventory.getCurrentItem();
            if (currentItem == null) {
                this.remainingHighlightTicks = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (this.highlightingItemStack != null && currentItem.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(currentItem, this.highlightingItemStack) && (currentItem.isItemStackDamageable() || currentItem.getMetadata() == this.highlightingItemStack.getMetadata())) {
                if (this.remainingHighlightTicks > 0) {
                    this.remainingHighlightTicks -= " ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
            else {
                this.remainingHighlightTicks = (0x98 ^ 0xB0);
            }
            this.highlightingItemStack = currentItem;
        }
    }
    
    public void renderGameOverlay(final float n) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int scaledWidth = scaledResolution.getScaledWidth();
        final int scaledHeight = scaledResolution.getScaledHeight();
        this.mc.entityRenderer.setupOverlayRendering();
        GlStateManager.enableBlend();
        if (Config.isVignetteEnabled()) {
            this.renderVignette(this.mc.thePlayer.getBrightness(n), scaledResolution);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            GlStateManager.tryBlendFuncSeparate(157 + 464 - 238 + 387, 514 + 324 - 481 + 414, " ".length(), "".length());
        }
        final ItemStack armorItemInSlot = this.mc.thePlayer.inventory.armorItemInSlot("   ".length());
        if (this.mc.gameSettings.thirdPersonView == 0 && armorItemInSlot != null && armorItemInSlot.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(scaledResolution);
        }
        if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            final float n2 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * n;
            if (n2 > 0.0f) {
                this.func_180474_b(n2, scaledResolution);
            }
        }
        if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(scaledResolution, n);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            this.renderTooltip(scaledResolution, n);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        GlStateManager.enableBlend();
        if (this.showCrosshair()) {
            GlStateManager.tryBlendFuncSeparate(115 + 48 - 30 + 642, 43 + 621 + 99 + 6, " ".length(), "".length());
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(scaledWidth / "  ".length() - (0xC2 ^ 0xC5), scaledHeight / "  ".length() - (0x7A ^ 0x7D), "".length(), "".length(), 0x55 ^ 0x45, 0x7F ^ 0x6F);
        }
        GlStateManager.tryBlendFuncSeparate(707 + 628 - 1164 + 599, 261 + 619 - 472 + 363, " ".length(), "".length());
        this.mc.mcProfiler.startSection(GuiIngame.I[0x46 ^ 0x41]);
        this.renderBossHealth();
        this.mc.mcProfiler.endSection();
        if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(scaledResolution);
        }
        GlStateManager.disableBlend();
        if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection(GuiIngame.I[0x40 ^ 0x48]);
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            final int sleepTimer = this.mc.thePlayer.getSleepTimer();
            float n3 = sleepTimer / 100.0f;
            if (n3 > 1.0f) {
                n3 = 1.0f - (sleepTimer - (0xC6 ^ 0xA2)) / 10.0f;
            }
            Gui.drawRect("".length(), "".length(), scaledWidth, scaledHeight, (int)(220.0f * n3) << (0x66 ^ 0x7E) | 898744 + 54034 - 781540 + 881466);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n4 = scaledWidth / "  ".length() - (0xDE ^ 0x85);
        if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(scaledResolution, n4);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(scaledResolution, n4);
        }
        if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
            this.func_181551_a(scaledResolution);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else if (this.mc.thePlayer.isSpectator()) {
            this.spectatorGui.func_175263_a(scaledResolution);
        }
        if (this.mc.isDemo()) {
            this.renderDemo(scaledResolution);
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(scaledResolution);
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection(GuiIngame.I[0x95 ^ 0x9C]);
            final float n5 = this.recordPlayingUpFor - n;
            int n6 = (int)(n5 * 255.0f / 20.0f);
            if (n6 > 114 + 235 - 299 + 205) {
                n6 = 169 + 117 - 197 + 166;
            }
            if (n6 > (0x53 ^ 0x5B)) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(scaledWidth / "  ".length(), scaledHeight - (0xDA ^ 0x9E), 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(169 + 746 - 672 + 527, 571 + 2 + 17 + 181, " ".length(), "".length());
                int n7 = 1625413 + 879475 - 926127 + 15198454;
                if (this.recordIsPlaying) {
                    n7 = (MathHelper.func_181758_c(n5 / 50.0f, 0.7f, 0.6f) & 4998951 + 3777262 + 4339792 + 3661210);
                }
                this.getFontRenderer().drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / "  ".length(), -(0x3D ^ 0x39), n7 + (n6 << (0x3 ^ 0x1B) & -(15746992 + 12169857 - 20325499 + 9185866)));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        if (this.field_175195_w > 0) {
            this.mc.mcProfiler.startSection(GuiIngame.I[0x53 ^ 0x59]);
            final float n8 = this.field_175195_w - n;
            int n9 = 210 + 204 - 350 + 191;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
                n9 = (int)((this.field_175199_z + this.field_175192_A + this.field_175193_B - n8) * 255.0f / this.field_175199_z);
            }
            if (this.field_175195_w <= this.field_175193_B) {
                n9 = (int)(n8 * 255.0f / this.field_175193_B);
            }
            final int clamp_int = MathHelper.clamp_int(n9, "".length(), 151 + 104 - 183 + 183);
            if (clamp_int > (0xAC ^ 0xA4)) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(scaledWidth / "  ".length(), scaledHeight / "  ".length(), 0.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(40 + 654 - 668 + 744, 593 + 354 - 366 + 190, " ".length(), "".length());
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0f, 4.0f, 4.0f);
                final int n10 = clamp_int << (0x4D ^ 0x55) & -(13773258 + 5539837 - 8232231 + 5696352);
                this.getFontRenderer().drawString(this.field_175201_x, -this.getFontRenderer().getStringWidth(this.field_175201_x) / "  ".length(), -10.0f, 13726587 + 10785911 - 11807497 + 4072214 | n10, " ".length() != 0);
                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                this.getFontRenderer().drawString(this.field_175200_y, -this.getFontRenderer().getStringWidth(this.field_175200_y) / "  ".length(), 5.0f, 5817627 + 8956350 - 12580411 + 14583649 | n10, " ".length() != 0);
                GlStateManager.popMatrix();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        final Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
        ScoreObjective objectiveInDisplaySlot = null;
        final ScorePlayerTeam playersTeam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
        if (playersTeam != null) {
            final int colorIndex = playersTeam.getChatFormat().getColorIndex();
            if (colorIndex >= 0) {
                objectiveInDisplaySlot = scoreboard.getObjectiveInDisplaySlot("   ".length() + colorIndex);
            }
        }
        ScoreObjective objectiveInDisplaySlot2;
        if (objectiveInDisplaySlot != null) {
            objectiveInDisplaySlot2 = objectiveInDisplaySlot;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            objectiveInDisplaySlot2 = scoreboard.getObjectiveInDisplaySlot(" ".length());
        }
        final ScoreObjective scoreObjective = objectiveInDisplaySlot2;
        if (scoreObjective != null) {
            this.renderScoreboard(scoreObjective, scaledResolution);
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(415 + 63 + 92 + 200, 29 + 494 - 194 + 442, " ".length(), "".length());
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, scaledHeight - (0xAE ^ 0x9E), 0.0f);
        this.mc.mcProfiler.startSection(GuiIngame.I[0x2B ^ 0x20]);
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GlStateManager.popMatrix();
        final ScoreObjective objectiveInDisplaySlot3 = scoreboard.getObjectiveInDisplaySlot("".length());
        if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || (this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= " ".length() && objectiveInDisplaySlot3 == null)) {
            this.overlayPlayerList.updatePlayerList("".length() != 0);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.overlayPlayerList.updatePlayerList(" ".length() != 0);
            this.overlayPlayerList.renderPlayerlist(scaledWidth, scoreboard, objectiveInDisplaySlot3);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableAlpha();
    }
    
    private void renderScoreboard(final ScoreObjective scoreObjective, final ScaledResolution scaledResolution) {
        final Scoreboard scoreboard = scoreObjective.getScoreboard();
        final Collection<Score> sortedScores = scoreboard.getSortedScores(scoreObjective);
        final ArrayList arrayList = Lists.newArrayList(Iterables.filter((Iterable)sortedScores, (Predicate)new Predicate(this) {
            final GuiIngame this$0;
            private static final String[] I;
            private static final String __OBFID;
            
            static {
                I();
                __OBFID = GuiIngame$1.I[" ".length()];
            }
            
            public boolean apply(final Score score) {
                if (score.getPlayerName() != null && !score.getPlayerName().startsWith(GuiIngame$1.I["".length()])) {
                    return " ".length() != 0;
                }
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
                    if (3 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("h", "KKPqn");
                GuiIngame$1.I[" ".length()] = I(":\u0007\u0016CSI{xJVA", "yKIsc");
            }
            
            public boolean apply(final Object o) {
                return this.apply((Score)o);
            }
        }));
        ArrayList<Score> arrayList2;
        if (arrayList.size() > (0xA0 ^ 0xAF)) {
            arrayList2 = (ArrayList<Score>)Lists.newArrayList(Iterables.skip((Iterable)arrayList, sortedScores.size() - (0x5 ^ 0xA)));
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            arrayList2 = (ArrayList<Score>)arrayList;
        }
        int n = this.getFontRenderer().getStringWidth(scoreObjective.getDisplayName());
        final Iterator<Score> iterator = arrayList2.iterator();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Score score = iterator.next();
            n = Math.max(n, this.getFontRenderer().getStringWidth(String.valueOf(ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score.getPlayerName()), score.getPlayerName())) + GuiIngame.I[0x8 ^ 0x1C] + EnumChatFormatting.RED + score.getScorePoints()));
        }
        final int n2 = scaledResolution.getScaledHeight() / "  ".length() + arrayList2.size() * this.getFontRenderer().FONT_HEIGHT / "   ".length();
        final int length = "   ".length();
        final int n3 = scaledResolution.getScaledWidth() - n - length;
        int length2 = "".length();
        final Iterator<Score> iterator2 = arrayList2.iterator();
        "".length();
        if (4 < 1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Score score2 = iterator2.next();
            ++length2;
            final String formatPlayerName = ScorePlayerTeam.formatPlayerName(scoreboard.getPlayersTeam(score2.getPlayerName()), score2.getPlayerName());
            final String string = new StringBuilder().append(EnumChatFormatting.RED).append(score2.getScorePoints()).toString();
            final int n4 = n2 - length2 * this.getFontRenderer().FONT_HEIGHT;
            final int n5 = scaledResolution.getScaledWidth() - length + "  ".length();
            Gui.drawRect(n3 - "  ".length(), n4, n5, n4 + this.getFontRenderer().FONT_HEIGHT, 862654671 + 990634720 - 1596278547 + 1085166436);
            this.getFontRenderer().drawString(formatPlayerName, n3, n4, 107249980 + 11367731 + 348795961 + 86234455);
            this.getFontRenderer().drawString(string, n5 - this.getFontRenderer().getStringWidth(string), n4, 482246187 + 303861230 - 252389422 + 19930132);
            if (length2 == arrayList2.size()) {
                final String displayName = scoreObjective.getDisplayName();
                Gui.drawRect(n3 - "  ".length(), n4 - this.getFontRenderer().FONT_HEIGHT - " ".length(), n5, n4 - " ".length(), 287114301 + 275110309 - 504603088 + 1552991214);
                Gui.drawRect(n3 - "  ".length(), n4 - " ".length(), n5, n4, 1203249365 + 956088216 - 1850841388 + 1033681087);
                this.getFontRenderer().drawString(displayName, n3 + n / "  ".length() - this.getFontRenderer().getStringWidth(displayName) / "  ".length(), n4 - this.getFontRenderer().FONT_HEIGHT, 112515443 + 65327325 - 154324752 + 530130111);
            }
        }
    }
    
    public void func_175177_a() {
        this.field_175199_z = (0x61 ^ 0x6B);
        this.field_175192_A = (0x74 ^ 0x32);
        this.field_175193_B = (0x84 ^ 0x90);
    }
    
    private void renderPumpkinOverlay(final ScaledResolution scaledResolution) {
        GlStateManager.disableDepth();
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.tryBlendFuncSeparate(706 + 679 - 1291 + 676, 539 + 769 - 1259 + 722, " ".length(), "".length());
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableAlpha();
        this.mc.getTextureManager().bindTexture(GuiIngame.pumpkinBlurTexPath);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x5A ^ 0x5D, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        worldRenderer.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        instance.draw();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void renderDemo(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection(GuiIngame.I[0x98 ^ 0x88]);
        final String s = GuiIngame.I[0x28 ^ 0x39];
        String s2;
        if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
            s2 = I18n.format(GuiIngame.I[0x18 ^ 0xA], new Object["".length()]);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            final String s3 = GuiIngame.I[0x6C ^ 0x7F];
            final Object[] array = new Object[" ".length()];
            array["".length()] = StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()));
            s2 = I18n.format(s3, array);
        }
        this.getFontRenderer().drawStringWithShadow(s2, scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(s2) - (0x93 ^ 0x99), 5.0f, 12640053 + 8411439 - 20877979 + 16603702);
        this.mc.mcProfiler.endSection();
    }
    
    private static void I() {
        (I = new String[0x3B ^ 0x24])["".length()] = I("\u000f%\f\\]|YcZ[}", "LiSlm");
        GuiIngame.I[" ".length()] = I("\u0000\u0007\u000f\u0003\u0007\u0006\u0007\u0004X\u001f\u001d\u0011\u0014X\u0004\u001d\u0005\u0019\u0012\u0006\u0000\u0007Y\u0007\u001c\u0013", "tbwwr");
        GuiIngame.I["  ".length()] = I("3\u001d45\u00185\u001d?n\n2\u0011c6\u0004#\u001f)5\u001ei\b\"&", "GxLAm");
        GuiIngame.I["   ".length()] = I("!3.\u00157'3%N/<%5N2 ;&\n+;4:\u00140{&8\u0006", "UVVaB");
        GuiIngame.I[0x18 ^ 0x1C] = I("", "iSnlh");
        GuiIngame.I[0xC0 ^ 0xC5] = I("", "EXmqM");
        GuiIngame.I[0xA0 ^ 0xA6] = I("", "FXCDb");
        GuiIngame.I[0x14 ^ 0x13] = I("\f\u0004\u001d\u0011\u0006\u000b\n\u0002\u0016&", "nknbN");
        GuiIngame.I[0x6 ^ 0xE] = I("\u00004\u000b.$", "sXnKT");
        GuiIngame.I[0xCA ^ 0xC3] = I("\"3\u001f%8,<72'>$\u001d2", "MEzWT");
        GuiIngame.I[0x5C ^ 0x56] = I("-\u0018\u001f$$\u0018\u001f\u000f\u001b4;\u0005\u0002<-<", "YqkHA");
        GuiIngame.I[0x58 ^ 0x53] = I("\f\u000e\u001b\u0018", "ofzll");
        GuiIngame.I[0x3C ^ 0x30] = I("&37$.-4", "LFZTl");
        GuiIngame.I[0x10 ^ 0x1D] = I("\u000e\u001d\u001f\u0012(\u0019", "keoPI");
        GuiIngame.I[0x15 ^ 0x1B] = I("\u0014)\u0015?\"\u00074\t", "qQesG");
        GuiIngame.I[0xCE ^ 0xC1] = I("\u001c\u0000$\u0011\u0016\u001b\u0000,=\u0001\n\b\u0006\u0015\u0018\n", "oeHtu");
        GuiIngame.I[0x34 ^ 0x24] = I("\u0011 :=", "uEWRW");
        GuiIngame.I[0x49 ^ 0x58] = I("", "AjUxi");
        GuiIngame.I[0x79 ^ 0x6B] = I(")5\u0001\u0001})5\u0001\u0001\u00165 \u0005\u001c6)", "MPlnS");
        GuiIngame.I[0x8C ^ 0x9F] = I("*-\u0002\u0018}<-\u0002\u0016: !\u0001\u0010\u0007'%\n", "NHowS");
        GuiIngame.I[0xD4 ^ 0xC0] = I("RQ", "hqpVe");
        GuiIngame.I[0x53 ^ 0x46] = I("'!\u000b\u00184", "FSfwF");
        GuiIngame.I[0x57 ^ 0x41] = I(" 4\r\u0000& ", "HQllR");
        GuiIngame.I[0x42 ^ 0x55] = I("\u0003.)\t", "eAFmA");
        GuiIngame.I[0x65 ^ 0x7D] = I("<\r\u0005\b\u0012\u0019\u0007\u0011\n\u00129", "Qbpff");
        GuiIngame.I[0x6D ^ 0x74] = I("\"\u0011\u0002", "CxptF");
        GuiIngame.I[0x86 ^ 0x9C] = I("", "lvwzm");
        GuiIngame.I[0x6 ^ 0x1D] = I("", "hcPtG");
        GuiIngame.I[0x11 ^ 0xD] = I("\u001d\u00105\u0002#\u000b[8\u0002&?\u00197\u00148\u0001\u0012", "ouVmQ");
        GuiIngame.I[0x9 ^ 0x14] = I("", "xExOa");
        GuiIngame.I[0x1B ^ 0x5] = I("", "mQvfP");
    }
    
    public void func_181551_a(final ScaledResolution scaledResolution) {
        this.mc.mcProfiler.startSection(GuiIngame.I[0xBA ^ 0xB5]);
        if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
            String s = this.highlightingItemStack.getDisplayName();
            if (this.highlightingItemStack.hasDisplayName()) {
                s = EnumChatFormatting.ITALIC + s;
            }
            final int n = (scaledResolution.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / "  ".length();
            int n2 = scaledResolution.getScaledHeight() - (0x87 ^ 0xBC);
            if (!this.mc.playerController.shouldDrawHUD()) {
                n2 += 14;
            }
            int n3 = (int)(this.remainingHighlightTicks * 256.0f / 10.0f);
            if (n3 > 192 + 242 - 354 + 175) {
                n3 = 67 + 43 - 85 + 230;
            }
            if (n3 > 0) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(318 + 167 + 182 + 103, 196 + 218 - 55 + 412, " ".length(), "".length());
                this.getFontRenderer().drawStringWithShadow(s, n, n2, 2744619 + 9457899 - 4154854 + 8729551 + (n3 << (0x11 ^ 0x9)));
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void renderPlayerStats(final ScaledResolution scaledResolution) {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)this.mc.getRenderViewEntity();
            final int ceiling_float_int = MathHelper.ceiling_float_int(entityPlayer.getHealth());
            int n;
            if (this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L) {
                n = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            if (ceiling_float_int < this.playerHealth && entityPlayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + (0xB0 ^ 0xA4);
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else if (ceiling_float_int > this.playerHealth && entityPlayer.hurtResistantTime > 0) {
                this.lastSystemTime = Minecraft.getSystemTime();
                this.healthUpdateCounter = this.updateCounter + (0x88 ^ 0x82);
            }
            if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
                this.playerHealth = ceiling_float_int;
                this.lastPlayerHealth = ceiling_float_int;
                this.lastSystemTime = Minecraft.getSystemTime();
            }
            this.playerHealth = ceiling_float_int;
            final int lastPlayerHealth = this.lastPlayerHealth;
            this.rand.setSeed(this.updateCounter * (307583 + 95041 - 137054 + 47301));
            final int length = "".length();
            final FoodStats foodStats = entityPlayer.getFoodStats();
            final int foodLevel = foodStats.getFoodLevel();
            final int prevFoodLevel = foodStats.getPrevFoodLevel();
            final IAttributeInstance entityAttribute = entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            final int n3 = scaledResolution.getScaledWidth() / "  ".length() - (0x79 ^ 0x22);
            final int n4 = scaledResolution.getScaledWidth() / "  ".length() + (0xF9 ^ 0xA2);
            final int n5 = scaledResolution.getScaledHeight() - (0x80 ^ 0xA7);
            final float n6 = (float)entityAttribute.getAttributeValue();
            final float absorptionAmount = entityPlayer.getAbsorptionAmount();
            final int ceiling_float_int2 = MathHelper.ceiling_float_int((n6 + absorptionAmount) / 2.0f / 10.0f);
            final int max = Math.max((0xCF ^ 0xC5) - (ceiling_float_int2 - "  ".length()), "   ".length());
            final int n7 = n5 - (ceiling_float_int2 - " ".length()) * max - (0x71 ^ 0x7B);
            float n8 = absorptionAmount;
            final int totalArmorValue = entityPlayer.getTotalArmorValue();
            int n9 = -" ".length();
            if (entityPlayer.isPotionActive(Potion.regeneration)) {
                n9 = this.updateCounter % MathHelper.ceiling_float_int(n6 + 5.0f);
            }
            this.mc.mcProfiler.startSection(GuiIngame.I[0xA8 ^ 0xBD]);
            int i = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (i < (0x6A ^ 0x60)) {
                if (totalArmorValue > 0) {
                    final int n10 = n3 + i * (0x44 ^ 0x4C);
                    if (i * "  ".length() + " ".length() < totalArmorValue) {
                        this.drawTexturedModalRect(n10, n7, 0x6E ^ 0x4C, 0x4D ^ 0x44, 0x2F ^ 0x26, 0x75 ^ 0x7C);
                    }
                    if (i * "  ".length() + " ".length() == totalArmorValue) {
                        this.drawTexturedModalRect(n10, n7, 0x20 ^ 0x39, 0x17 ^ 0x1E, 0x80 ^ 0x89, 0x66 ^ 0x6F);
                    }
                    if (i * "  ".length() + " ".length() > totalArmorValue) {
                        this.drawTexturedModalRect(n10, n7, 0x80 ^ 0x90, 0x74 ^ 0x7D, 0x3E ^ 0x37, 0x4D ^ 0x44);
                    }
                }
                ++i;
            }
            this.mc.mcProfiler.endStartSection(GuiIngame.I[0x80 ^ 0x96]);
            int j = MathHelper.ceiling_float_int((n6 + absorptionAmount) / 2.0f) - " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
            while (j >= 0) {
                int n11 = 0x42 ^ 0x52;
                if (entityPlayer.isPotionActive(Potion.poison)) {
                    n11 += 36;
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else if (entityPlayer.isPotionActive(Potion.wither)) {
                    n11 += 72;
                }
                int n12 = "".length();
                if (n2 != 0) {
                    n12 = " ".length();
                }
                final int n13 = MathHelper.ceiling_float_int((j + " ".length()) / 10.0f) - " ".length();
                final int n14 = n3 + j % (0x6A ^ 0x60) * (0x77 ^ 0x7F);
                int n15 = n5 - n13 * max;
                if (ceiling_float_int <= (0x76 ^ 0x72)) {
                    n15 += this.rand.nextInt("  ".length());
                }
                if (j == n9) {
                    n15 -= 2;
                }
                int length2 = "".length();
                if (entityPlayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
                    length2 = (0x3 ^ 0x6);
                }
                this.drawTexturedModalRect(n14, n15, (0x4C ^ 0x5C) + n12 * (0x79 ^ 0x70), (0x2F ^ 0x26) * length2, 0x83 ^ 0x8A, 0x17 ^ 0x1E);
                if (n2 != 0) {
                    if (j * "  ".length() + " ".length() < lastPlayerHealth) {
                        this.drawTexturedModalRect(n14, n15, n11 + (0x86 ^ 0xB0), (0x2 ^ 0xB) * length2, 0xCF ^ 0xC6, 0xA6 ^ 0xAF);
                    }
                    if (j * "  ".length() + " ".length() == lastPlayerHealth) {
                        this.drawTexturedModalRect(n14, n15, n11 + (0x90 ^ 0xAF), (0x21 ^ 0x28) * length2, 0x56 ^ 0x5F, 0xBD ^ 0xB4);
                    }
                }
                if (n8 <= 0.0f) {
                    if (j * "  ".length() + " ".length() < ceiling_float_int) {
                        this.drawTexturedModalRect(n14, n15, n11 + (0xB5 ^ 0x91), (0x9B ^ 0x92) * length2, 0x67 ^ 0x6E, 0xA ^ 0x3);
                    }
                    if (j * "  ".length() + " ".length() == ceiling_float_int) {
                        this.drawTexturedModalRect(n14, n15, n11 + (0xE9 ^ 0xC4), (0x4F ^ 0x46) * length2, 0x67 ^ 0x6E, 0x85 ^ 0x8C);
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                }
                else {
                    if (n8 == absorptionAmount && absorptionAmount % 2.0f == 1.0f) {
                        this.drawTexturedModalRect(n14, n15, n11 + (71 + 23 - 39 + 98), (0x88 ^ 0x81) * length2, 0x80 ^ 0x89, 0x7E ^ 0x77);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(n14, n15, n11 + (97 + 44 - 87 + 90), (0x75 ^ 0x7C) * length2, 0xB2 ^ 0xBB, 0x9D ^ 0x94);
                    }
                    n8 -= 2.0f;
                }
                --j;
            }
            final Entity ridingEntity = entityPlayer.ridingEntity;
            if (ridingEntity == null) {
                this.mc.mcProfiler.endStartSection(GuiIngame.I[0xAC ^ 0xBB]);
                int k = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (k < (0xCD ^ 0xC7)) {
                    int n16 = n5;
                    int n17 = 0x3E ^ 0x2E;
                    int n18 = "".length();
                    if (entityPlayer.isPotionActive(Potion.hunger)) {
                        n17 += 36;
                        n18 = (0x17 ^ 0x1A);
                    }
                    if (entityPlayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (foodLevel * "   ".length() + " ".length()) == 0) {
                        n16 = n5 + (this.rand.nextInt("   ".length()) - " ".length());
                    }
                    if (length != 0) {
                        n18 = " ".length();
                    }
                    final int n19 = n4 - k * (0x63 ^ 0x6B) - (0x6B ^ 0x62);
                    this.drawTexturedModalRect(n19, n16, (0x6B ^ 0x7B) + n18 * (0x8F ^ 0x86), 0xAD ^ 0xB6, 0x91 ^ 0x98, 0x51 ^ 0x58);
                    if (length != 0) {
                        if (k * "  ".length() + " ".length() < prevFoodLevel) {
                            this.drawTexturedModalRect(n19, n16, n17 + (0x2B ^ 0x1D), 0x69 ^ 0x72, 0x73 ^ 0x7A, 0x84 ^ 0x8D);
                        }
                        if (k * "  ".length() + " ".length() == prevFoodLevel) {
                            this.drawTexturedModalRect(n19, n16, n17 + (0xD ^ 0x32), 0x24 ^ 0x3F, 0x9E ^ 0x97, 0xB5 ^ 0xBC);
                        }
                    }
                    if (k * "  ".length() + " ".length() < foodLevel) {
                        this.drawTexturedModalRect(n19, n16, n17 + (0x25 ^ 0x1), 0x47 ^ 0x5C, 0x95 ^ 0x9C, 0xA ^ 0x3);
                    }
                    if (k * "  ".length() + " ".length() == foodLevel) {
                        this.drawTexturedModalRect(n19, n16, n17 + (0x3A ^ 0x17), 0x43 ^ 0x58, 0x6A ^ 0x63, 0x63 ^ 0x6A);
                    }
                    ++k;
                }
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else if (ridingEntity instanceof EntityLivingBase) {
                this.mc.mcProfiler.endStartSection(GuiIngame.I[0xB0 ^ 0xA8]);
                final EntityLivingBase entityLivingBase = (EntityLivingBase)ridingEntity;
                final int n20 = (int)Math.ceil(entityLivingBase.getHealth());
                int l = (int)(entityLivingBase.getMaxHealth() + 0.5f) / "  ".length();
                if (l > (0x85 ^ 0x9B)) {
                    l = (0x5 ^ 0x1B);
                }
                int n21 = n5;
                int length3 = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (l > 0) {
                    final int min = Math.min(l, 0x5D ^ 0x57);
                    l -= min;
                    int length4 = "".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                    while (length4 < min) {
                        final int n22 = 0x7D ^ 0x49;
                        int n23 = "".length();
                        if (length != 0) {
                            n23 = " ".length();
                        }
                        final int n24 = n4 - length4 * (0x30 ^ 0x38) - (0x6 ^ 0xF);
                        this.drawTexturedModalRect(n24, n21, n22 + n23 * (0x90 ^ 0x99), 0x35 ^ 0x3C, 0xBA ^ 0xB3, 0xA6 ^ 0xAF);
                        if (length4 * "  ".length() + " ".length() + length3 < n20) {
                            this.drawTexturedModalRect(n24, n21, n22 + (0xA9 ^ 0x8D), 0x10 ^ 0x19, 0x8D ^ 0x84, 0x7C ^ 0x75);
                        }
                        if (length4 * "  ".length() + " ".length() + length3 == n20) {
                            this.drawTexturedModalRect(n24, n21, n22 + (0x31 ^ 0x1C), 0x0 ^ 0x9, 0x92 ^ 0x9B, 0x96 ^ 0x9F);
                        }
                        ++length4;
                    }
                    n21 -= 10;
                    length3 += 20;
                }
            }
            this.mc.mcProfiler.endStartSection(GuiIngame.I[0x6E ^ 0x77]);
            if (entityPlayer.isInsideOfMaterial(Material.water)) {
                final int air = this.mc.thePlayer.getAir();
                final int ceiling_double_int = MathHelper.ceiling_double_int((air - "  ".length()) * 10.0 / 300.0);
                final int n25 = MathHelper.ceiling_double_int(air * 10.0 / 300.0) - ceiling_double_int;
                int length5 = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (length5 < ceiling_double_int + n25) {
                    if (length5 < ceiling_double_int) {
                        this.drawTexturedModalRect(n4 - length5 * (0x74 ^ 0x7C) - (0x76 ^ 0x7F), n7, 0xAC ^ 0xBC, 0x84 ^ 0x96, 0x42 ^ 0x4B, 0xCF ^ 0xC6);
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(n4 - length5 * (0x27 ^ 0x2F) - (0x7D ^ 0x74), n7, 0xD ^ 0x14, 0x71 ^ 0x63, 0x27 ^ 0x2E, 0x31 ^ 0x38);
                    }
                    ++length5;
                }
            }
            this.mc.mcProfiler.endSection();
        }
    }
    
    public void setRecordPlaying(final IChatComponent chatComponent, final boolean b) {
        this.setRecordPlaying(chatComponent.getUnformattedText(), b);
    }
    
    public FontRenderer getFontRenderer() {
        return this.mc.fontRendererObj;
    }
    
    private void renderHotbarItem(final int n, final int n2, final int n3, final float n4, final EntityPlayer entityPlayer) {
        final ItemStack itemStack = entityPlayer.inventory.mainInventory[n];
        if (itemStack != null) {
            final float n5 = itemStack.animationsToGo - n4;
            if (n5 > 0.0f) {
                GlStateManager.pushMatrix();
                final float n6 = 1.0f + n5 / 5.0f;
                GlStateManager.translate(n2 + (0xAA ^ 0xA2), n3 + (0xB2 ^ 0xBE), 0.0f);
                GlStateManager.scale(1.0f / n6, (n6 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.translate(-(n2 + (0x44 ^ 0x4C)), -(n3 + (0x1C ^ 0x10)), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(itemStack, n2, n3);
            if (n5 > 0.0f) {
                GlStateManager.popMatrix();
            }
            this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemStack, n2, n3);
        }
    }
    
    public void renderHorseJumpBar(final ScaledResolution scaledResolution, final int n) {
        this.mc.mcProfiler.startSection(GuiIngame.I[0x25 ^ 0x29]);
        this.mc.getTextureManager().bindTexture(Gui.icons);
        final float horseJumpPower = this.mc.thePlayer.getHorseJumpPower();
        final int n2 = 69 + 118 - 181 + 176;
        final int n3 = (int)(horseJumpPower * (n2 + " ".length()));
        final int n4 = scaledResolution.getScaledHeight() - (0xE ^ 0x2E) + "   ".length();
        this.drawTexturedModalRect(n, n4, "".length(), 0x58 ^ 0xC, n2, 0x56 ^ 0x53);
        if (n3 > 0) {
            this.drawTexturedModalRect(n, n4, "".length(), 0x9A ^ 0xC3, n3, 0x67 ^ 0x62);
        }
        this.mc.mcProfiler.endSection();
    }
    
    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            BossStatus.statusBarTime -= " ".length();
            final FontRenderer fontRendererObj = this.mc.fontRendererObj;
            final int scaledWidth = new ScaledResolution(this.mc).getScaledWidth();
            final int n = 134 + 57 - 161 + 152;
            final int n2 = scaledWidth / "  ".length() - n / "  ".length();
            final int n3 = (int)(BossStatus.healthScale * (n + " ".length()));
            final int n4 = 0x52 ^ 0x5E;
            this.drawTexturedModalRect(n2, n4, "".length(), 0x28 ^ 0x62, n, 0xC6 ^ 0xC3);
            this.drawTexturedModalRect(n2, n4, "".length(), 0x1E ^ 0x54, n, 0x31 ^ 0x34);
            if (n3 > 0) {
                this.drawTexturedModalRect(n2, n4, "".length(), 0x69 ^ 0x26, n3, 0x1B ^ 0x1E);
            }
            final String bossName = BossStatus.bossName;
            this.getFontRenderer().drawStringWithShadow(bossName, scaledWidth / "  ".length() - this.getFontRenderer().getStringWidth(bossName) / "  ".length(), n4 - (0x8 ^ 0x2), 8931043 + 1572109 - 4747546 + 11021609);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(GuiIngame.icons);
        }
    }
    
    public GuiSpectator getSpectatorGui() {
        return this.spectatorGui;
    }
    
    public void setRecordPlaying(final String recordPlaying, final boolean recordIsPlaying) {
        this.recordPlaying = recordPlaying;
        this.recordPlayingUpFor = (0x8C ^ 0xB0);
        this.recordIsPlaying = recordIsPlaying;
    }
    
    public GuiPlayerTabOverlay getTabList() {
        return this.overlayPlayerList;
    }
    
    private void func_180474_b(float n, final ScaledResolution scaledResolution) {
        if (n < 1.0f) {
            n *= n;
            n *= n;
            n = n * 0.8f + 0.2f;
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask("".length() != 0);
        GlStateManager.tryBlendFuncSeparate(239 + 47 - 215 + 699, 590 + 693 - 1029 + 517, " ".length(), "".length());
        GlStateManager.color(1.0f, 1.0f, 1.0f, n);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final TextureAtlasSprite texture = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
        final float minU = texture.getMinU();
        final float minV = texture.getMinV();
        final float maxU = texture.getMaxU();
        final float maxV = texture.getMaxV();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x96 ^ 0x91, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(0.0, scaledResolution.getScaledHeight(), -90.0).tex(minU, maxV).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), -90.0).tex(maxU, maxV).endVertex();
        worldRenderer.pos(scaledResolution.getScaledWidth(), 0.0, -90.0).tex(maxU, minV).endVertex();
        worldRenderer.pos(0.0, 0.0, -90.0).tex(minU, minV).endVertex();
        instance.draw();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }
}
