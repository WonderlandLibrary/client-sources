package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import java.util.*;

public class GuiIngame extends Gui
{
    private static final RenderItem itemRenderer;
    private final Random rand;
    private final Minecraft mc;
    public GuiNewChat persistantChatGUI;
    private int updateCounter;
    private String recordPlaying;
    private int recordPlayingUpFor;
    private boolean recordIsPlaying;
    public float prevVignetteBrightness;
    private int remainingHighlightTicks;
    private ItemStack highlightingItemStack;
    
    static {
        itemRenderer = new RenderItem();
    }
    
    public GuiIngame(final Minecraft par1Minecraft) {
        this.rand = new Random();
        this.updateCounter = 0;
        this.recordPlaying = "";
        this.recordPlayingUpFor = 0;
        this.recordIsPlaying = false;
        this.prevVignetteBrightness = 1.0f;
        this.mc = par1Minecraft;
        this.persistantChatGUI = new GuiNewChat(par1Minecraft);
    }
    
    public void renderGameOverlay(final float par1, final boolean par2, final int par3, final int par4) {
        final ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        final int var6 = ScaledResolution.getScaledWidth();
        final int var7 = ScaledResolution.getScaledHeight();
        final FontRenderer var8 = this.mc.fontRenderer;
        this.mc.entityRenderer.setupOverlayRendering();
        GL11.glEnable(3042);
        if (Minecraft.isFancyGraphicsEnabled()) {
            this.renderVignette(Minecraft.thePlayer.getBrightness(par1), var6, var7);
        }
        else {
            GL11.glBlendFunc(770, 771);
        }
        final ItemStack var9 = Minecraft.thePlayer.inventory.armorItemInSlot(3);
        if (this.mc.gameSettings.thirdPersonView == 0 && var9 != null && var9.itemID == Block.pumpkin.blockID) {
            this.renderPumpkinBlur(var6, var7);
        }
        if (!Minecraft.thePlayer.isPotionActive(Potion.confusion)) {
            final float var10 = Minecraft.thePlayer.prevTimeInPortal + (Minecraft.thePlayer.timeInPortal - Minecraft.thePlayer.prevTimeInPortal) * par1;
            if (var10 > 0.0f) {
                this.renderPortalOverlay(var10, var6, var7);
            }
        }
        if (!this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.renderEngine.bindTexture("/gui/gui.png");
            final InventoryPlayer var11 = Minecraft.thePlayer.inventory;
            this.zLevel = -90.0f;
            this.drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var6 / 2 - 91 - 1 + var11.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
            GL11.glEnable(3042);
            GL11.glBlendFunc(775, 769);
            this.drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(3042);
            boolean var12 = Minecraft.thePlayer.hurtResistantTime / 3 % 2 == 1;
            if (Minecraft.thePlayer.hurtResistantTime < 10) {
                var12 = false;
            }
            final int var13 = Minecraft.thePlayer.getHealth();
            final int var14 = Minecraft.thePlayer.prevHealth;
            this.rand.setSeed(this.updateCounter * 312871);
            final boolean var15 = false;
            final FoodStats var16 = Minecraft.thePlayer.getFoodStats();
            final int var17 = var16.getFoodLevel();
            final int var18 = var16.getPrevFoodLevel();
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
            if (this.mc.playerController.shouldDrawHUD()) {
                final int var19 = var6 / 2 - 91;
                final int var20 = var6 / 2 + 91;
                this.mc.mcProfiler.startSection("expBar");
                final int var21 = Minecraft.thePlayer.xpBarCap();
                if (var21 > 0) {
                    final short var22 = 182;
                    final int var23 = (int)(Minecraft.thePlayer.experience * (var22 + 1));
                    final int var24 = var7 - 32 + 3;
                    this.drawTexturedModalRect(var19, var24, 0, 64, var22, 5);
                    if (var23 > 0) {
                        this.drawTexturedModalRect(var19, var24, 0, 69, var23, 5);
                    }
                }
                final int var25 = var7 - 39;
                final int var23 = var25 - 10;
                final int var24 = Minecraft.thePlayer.getTotalArmorValue();
                int var26 = -1;
                if (Minecraft.thePlayer.isPotionActive(Potion.regeneration)) {
                    var26 = this.updateCounter % 25;
                }
                this.mc.mcProfiler.endStartSection("healthArmor");
                for (int var27 = 0; var27 < 10; ++var27) {
                    if (var24 > 0) {
                        final int var28 = var19 + var27 * 8;
                        if (var27 * 2 + 1 < var24) {
                            this.drawTexturedModalRect(var28, var23, 34, 9, 9, 9);
                        }
                        if (var27 * 2 + 1 == var24) {
                            this.drawTexturedModalRect(var28, var23, 25, 9, 9, 9);
                        }
                        if (var27 * 2 + 1 > var24) {
                            this.drawTexturedModalRect(var28, var23, 16, 9, 9, 9);
                        }
                    }
                    int var28 = 16;
                    if (Minecraft.thePlayer.isPotionActive(Potion.poison)) {
                        var28 += 36;
                    }
                    else if (Minecraft.thePlayer.isPotionActive(Potion.wither)) {
                        var28 += 72;
                    }
                    byte var29 = 0;
                    if (var12) {
                        var29 = 1;
                    }
                    final int var30 = var19 + var27 * 8;
                    int var31 = var25;
                    if (var13 <= 4) {
                        var31 = var25 + this.rand.nextInt(2);
                    }
                    if (var27 == var26) {
                        var31 -= 2;
                    }
                    byte var32 = 0;
                    if (Minecraft.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
                        var32 = 5;
                    }
                    this.drawTexturedModalRect(var30, var31, 16 + var29 * 9, 9 * var32, 9, 9);
                    if (var12) {
                        if (var27 * 2 + 1 < var14) {
                            this.drawTexturedModalRect(var30, var31, var28 + 54, 9 * var32, 9, 9);
                        }
                        if (var27 * 2 + 1 == var14) {
                            this.drawTexturedModalRect(var30, var31, var28 + 63, 9 * var32, 9, 9);
                        }
                    }
                    if (var27 * 2 + 1 < var13) {
                        this.drawTexturedModalRect(var30, var31, var28 + 36, 9 * var32, 9, 9);
                    }
                    if (var27 * 2 + 1 == var13) {
                        this.drawTexturedModalRect(var30, var31, var28 + 45, 9 * var32, 9, 9);
                    }
                }
                this.mc.mcProfiler.endStartSection("food");
                for (int var27 = 0; var27 < 10; ++var27) {
                    int var28 = var25;
                    int var33 = 16;
                    byte var32 = 0;
                    if (Minecraft.thePlayer.isPotionActive(Potion.hunger)) {
                        var33 += 36;
                        var32 = 13;
                    }
                    if (Minecraft.thePlayer.getFoodStats().getSaturationLevel() <= 0.0f && this.updateCounter % (var17 * 3 + 1) == 0) {
                        var28 = var25 + (this.rand.nextInt(3) - 1);
                    }
                    if (var15) {
                        var32 = 1;
                    }
                    final int var31 = var20 - var27 * 8 - 9;
                    this.drawTexturedModalRect(var31, var28, 16 + var32 * 9, 27, 9, 9);
                    if (var15) {
                        if (var27 * 2 + 1 < var18) {
                            this.drawTexturedModalRect(var31, var28, var33 + 54, 27, 9, 9);
                        }
                        if (var27 * 2 + 1 == var18) {
                            this.drawTexturedModalRect(var31, var28, var33 + 63, 27, 9, 9);
                        }
                    }
                    if (var27 * 2 + 1 < var17) {
                        this.drawTexturedModalRect(var31, var28, var33 + 36, 27, 9, 9);
                    }
                    if (var27 * 2 + 1 == var17) {
                        this.drawTexturedModalRect(var31, var28, var33 + 45, 27, 9, 9);
                    }
                }
                this.mc.mcProfiler.endStartSection("air");
                if (Minecraft.thePlayer.isInsideOfMaterial(Material.water)) {
                    final int var27 = Minecraft.thePlayer.getAir();
                    for (int var28 = MathHelper.ceiling_double_int((var27 - 2) * 10.0 / 300.0), var33 = MathHelper.ceiling_double_int(var27 * 10.0 / 300.0) - var28, var30 = 0; var30 < var28 + var33; ++var30) {
                        if (var30 < var28) {
                            this.drawTexturedModalRect(var20 - var30 * 8 - 9, var23, 16, 18, 9, 9);
                        }
                        else {
                            this.drawTexturedModalRect(var20 - var30 * 8 - 9, var23, 25, 18, 9, 9);
                        }
                    }
                }
                this.mc.mcProfiler.endSection();
            }
            GL11.glDisable(3042);
            this.mc.mcProfiler.startSection("actionBar");
            GL11.glEnable(32826);
            RenderHelper.enableGUIStandardItemLighting();
            for (int var19 = 0; var19 < 9; ++var19) {
                final int var20 = var6 / 2 - 90 + var19 * 20 + 2;
                final int var21 = var7 - 16 - 3;
                this.renderInventorySlot(var19, var20, var21, par1);
            }
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(32826);
            this.mc.mcProfiler.endSection();
        }
        if (Minecraft.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GL11.glDisable(2929);
            GL11.glDisable(3008);
            final int var34 = Minecraft.thePlayer.getSleepTimer();
            float var35 = var34 / 100.0f;
            if (var35 > 1.0f) {
                var35 = 1.0f - (var34 - 100) / 10.0f;
            }
            final int var13 = (int)(220.0f * var35) << 24 | 0x101020;
            Gui.drawRect(0, 0, var6, var7, var13);
            GL11.glEnable(3008);
            GL11.glEnable(2929);
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.playerController.func_78763_f() && Minecraft.thePlayer.experienceLevel > 0) {
            this.mc.mcProfiler.startSection("expLevel");
            final boolean var12 = false;
            final int var13 = var12 ? 16777215 : 8453920;
            final String var36 = new StringBuilder().append(Minecraft.thePlayer.experienceLevel).toString();
            final int var34 = (var6 - var8.getStringWidth(var36)) / 2;
            final int var37 = var7 - 31 - 4;
            var8.drawString(var36, var34 + 1, var37, 0);
            var8.drawString(var36, var34 - 1, var37, 0);
            var8.drawString(var36, var34, var37 + 1, 0);
            var8.drawString(var36, var34, var37 - 1, 0);
            var8.drawString(var36, var34, var37, var13);
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.gameSettings.heldItemTooltips) {
            this.mc.mcProfiler.startSection("toolHighlight");
            if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
                final String var36 = this.highlightingItemStack.getDisplayName();
                final int var13 = (var6 - var8.getStringWidth(var36)) / 2;
                int var14 = var7 - 59;
                if (!this.mc.playerController.shouldDrawHUD()) {
                    var14 += 14;
                }
                int var34 = (int)(this.remainingHighlightTicks * 256.0f / 10.0f);
                if (var34 > 255) {
                    var34 = 255;
                }
                if (var34 > 0) {
                    GL11.glPushMatrix();
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    var8.drawStringWithShadow(var36, var13, var14, 16777215 + (var34 << 24));
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                }
            }
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.isDemo()) {
            this.mc.mcProfiler.startSection("demo");
            String var36 = "";
            if (Minecraft.theWorld.getTotalWorldTime() >= 120500L) {
                var36 = StatCollector.translateToLocal("demo.demoExpired");
            }
            else {
                var36 = String.format(StatCollector.translateToLocal("demo.remainingTime"), StringUtils.ticksToElapsedTime((int)(120500L - Minecraft.theWorld.getTotalWorldTime())));
            }
            final int var13 = var8.getStringWidth(var36);
            var8.drawStringWithShadow(var36, var6 - var13 - 10, 5, 16777215);
            this.mc.mcProfiler.endSection();
        }
        if (this.mc.gameSettings.showDebugInfo) {
            this.mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.5.2 (" + this.mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
            final long var38 = Runtime.getRuntime().maxMemory();
            final long var39 = Runtime.getRuntime().totalMemory();
            final long var40 = Runtime.getRuntime().freeMemory();
            final long var41 = var39 - var40;
            String var42 = "Used memory: " + var41 * 100L / var38 + "% (" + var41 / 1024L / 1024L + "MB) of " + var38 / 1024L / 1024L + "MB";
            this.drawString(var8, var42, var6 - var8.getStringWidth(var42) - 2, 2, 14737632);
            var42 = "Allocated memory: " + var39 * 100L / var38 + "% (" + var39 / 1024L / 1024L + "MB)";
            this.drawString(var8, var42, var6 - var8.getStringWidth(var42) - 2, 12, 14737632);
            final int var25 = MathHelper.floor_double(Minecraft.thePlayer.posX);
            final int var23 = MathHelper.floor_double(Minecraft.thePlayer.posY);
            final int var24 = MathHelper.floor_double(Minecraft.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", Minecraft.thePlayer.posX, var25, var25 >> 4, var25 & 0xF), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", Minecraft.thePlayer.boundingBox.minY, Minecraft.thePlayer.posY), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", Minecraft.thePlayer.posZ, var24, var24 >> 4, var24 & 0xF), 2, 80, 14737632);
            final int var26 = MathHelper.floor_double(Minecraft.thePlayer.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
            this.drawString(var8, "f: " + var26 + " (" + Direction.directions[var26] + ") / " + MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationYaw), 2, 88, 14737632);
            if (Minecraft.theWorld != null && Minecraft.theWorld.blockExists(var25, var23, var24)) {
                final Chunk var43 = Minecraft.theWorld.getChunkFromBlockCoords(var25, var24);
                this.drawString(var8, "lc: " + (var43.getTopFilledSegment() + 15) + " b: " + var43.getBiomeGenForWorldCoords(var25 & 0xF, var24 & 0xF, Minecraft.theWorld.getWorldChunkManager()).biomeName + " bl: " + var43.getSavedLightValue(EnumSkyBlock.Block, var25 & 0xF, var23, var24 & 0xF) + " sl: " + var43.getSavedLightValue(EnumSkyBlock.Sky, var25 & 0xF, var23, var24 & 0xF) + " rl: " + var43.getBlockLightValue(var25 & 0xF, var23, var24 & 0xF, 0), 2, 96, 14737632);
            }
            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", Minecraft.thePlayer.capabilities.getWalkSpeed(), Minecraft.thePlayer.capabilities.getFlySpeed(), Minecraft.thePlayer.onGround, Minecraft.theWorld.getHeightValue(var25, var24)), 2, 104, 14737632);
            GL11.glPopMatrix();
            this.mc.mcProfiler.endSection();
        }
        if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            final float var35 = this.recordPlayingUpFor - par1;
            int var13 = (int)(var35 * 256.0f / 20.0f);
            if (var13 > 255) {
                var13 = 255;
            }
            if (var13 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef(var6 / 2, var7 - 48, 0.0f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                int var14 = 16777215;
                if (this.recordIsPlaying) {
                    var14 = (Color.HSBtoRGB(var35 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF);
                }
                var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var14 + (var13 << 24));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
            this.mc.mcProfiler.endSection();
        }
        ScoreObjective var44 = Minecraft.theWorld.getScoreboard().func_96539_a(1);
        if (var44 != null) {
            this.func_96136_a(var44, var7, var6, var8);
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, var7 - 48, 0.0f);
        this.mc.mcProfiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.mcProfiler.endSection();
        GL11.glPopMatrix();
        var44 = Minecraft.theWorld.getScoreboard().func_96539_a(0);
        if (this.mc.gameSettings.keyBindPlayerList.pressed && (!this.mc.isIntegratedServerRunning() || Minecraft.thePlayer.sendQueue.playerInfoList.size() > 1 || var44 != null)) {
            this.mc.mcProfiler.startSection("playerList");
            final NetClientHandler var45 = Minecraft.thePlayer.sendQueue;
            final List var46 = var45.playerInfoList;
            int var17;
            int var34;
            int var37;
            for (var34 = (var37 = var45.currentServerMaxPlayers), var17 = 1; var37 > 20; var37 = (var34 + var17 - 1) / var17) {
                ++var17;
            }
            int var18 = 300 / var17;
            if (var18 > 150) {
                var18 = 150;
            }
            final int var19 = (var6 - var17 * var18) / 2;
            final byte var32 = 10;
            Gui.drawRect(var19 - 1, var32 - 1, var19 + var18 * var17, var32 + 9 * var37, Integer.MIN_VALUE);
            for (int var21 = 0; var21 < var34; ++var21) {
                final int var25 = var19 + var21 % var17 * var18;
                final int var23 = var32 + var21 / var17 * 9;
                Gui.drawRect(var25, var23, var25 + var18 - 1, var23 + 8, 553648127);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glEnable(3008);
                if (var21 < var46.size()) {
                    final GuiPlayerInfo var47 = var46.get(var21);
                    final ScorePlayerTeam var48 = Minecraft.theWorld.getScoreboard().getPlayersTeam(var47.name);
                    final String var49 = ScorePlayerTeam.func_96667_a(var48, var47.name);
                    var8.drawStringWithShadow(var49, var25, var23, 16777215);
                    if (var44 != null) {
                        final int var28 = var25 + var8.getStringWidth(var49) + 5;
                        final int var33 = var25 + var18 - 12 - 5;
                        if (var33 - var28 > 5) {
                            final Score var50 = var44.getScoreboard().func_96529_a(var47.name, var44);
                            final String var42 = new StringBuilder().append(EnumChatFormatting.YELLOW).append(var50.func_96652_c()).toString();
                            var8.drawStringWithShadow(var42, var33 - var8.getStringWidth(var42), var23, 16777215);
                        }
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.mc.renderEngine.bindTexture("/gui/icons.png");
                    final byte var51 = 0;
                    final boolean var52 = false;
                    byte var29;
                    if (var47.responseTime < 0) {
                        var29 = 5;
                    }
                    else if (var47.responseTime < 150) {
                        var29 = 0;
                    }
                    else if (var47.responseTime < 300) {
                        var29 = 1;
                    }
                    else if (var47.responseTime < 600) {
                        var29 = 2;
                    }
                    else if (var47.responseTime < 1000) {
                        var29 = 3;
                    }
                    else {
                        var29 = 4;
                    }
                    this.zLevel += 100.0f;
                    this.drawTexturedModalRect(var25 + var18 - 12, var23, 0 + var51 * 10, 176 + var29 * 8, 10, 8);
                    this.zLevel -= 100.0f;
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glEnable(3008);
    }
    
    private void func_96136_a(final ScoreObjective par1ScoreObjective, final int par2, final int par3, final FontRenderer par4FontRenderer) {
        final Scoreboard var5 = par1ScoreObjective.getScoreboard();
        final Collection var6 = var5.func_96534_i(par1ScoreObjective);
        if (var6.size() <= 15) {
            int var7 = par4FontRenderer.getStringWidth(par1ScoreObjective.getDisplayName());
            for (final Score var9 : var6) {
                final ScorePlayerTeam var10 = var5.getPlayersTeam(var9.func_96653_e());
                final String var11 = String.valueOf(ScorePlayerTeam.func_96667_a(var10, var9.func_96653_e())) + ": " + EnumChatFormatting.RED + var9.func_96652_c();
                var7 = Math.max(var7, par4FontRenderer.getStringWidth(var11));
            }
            final int var12 = var6.size() * par4FontRenderer.FONT_HEIGHT;
            final int var13 = par2 / 2 + var12 / 3;
            final byte var14 = 3;
            final int var15 = par3 - var7 - var14;
            int var16 = 0;
            for (final Score var18 : var6) {
                ++var16;
                final ScorePlayerTeam var19 = var5.getPlayersTeam(var18.func_96653_e());
                final String var20 = ScorePlayerTeam.func_96667_a(var19, var18.func_96653_e());
                final String var21 = new StringBuilder().append(EnumChatFormatting.RED).append(var18.func_96652_c()).toString();
                final int var22 = var13 - var16 * par4FontRenderer.FONT_HEIGHT;
                final int var23 = par3 - var14 + 2;
                Gui.drawRect(var15 - 2, var22, var23, var22 + par4FontRenderer.FONT_HEIGHT, 1342177280);
                par4FontRenderer.drawString(var20, var15, var22, 553648127);
                par4FontRenderer.drawString(var21, var23 - par4FontRenderer.getStringWidth(var21), var22, 553648127);
                if (var16 == var6.size()) {
                    final String var24 = par1ScoreObjective.getDisplayName();
                    Gui.drawRect(var15 - 2, var22 - par4FontRenderer.FONT_HEIGHT - 1, var23, var22 - 1, 1610612736);
                    Gui.drawRect(var15 - 2, var22 - 1, var23, var22, 1342177280);
                    par4FontRenderer.drawString(var24, var15 + var7 / 2 - par4FontRenderer.getStringWidth(var24) / 2, var22 - par4FontRenderer.FONT_HEIGHT, 553648127);
                }
            }
        }
    }
    
    private void renderBossHealth() {
        if (BossStatus.bossName != null && BossStatus.statusBarLength > 0) {
            --BossStatus.statusBarLength;
            final FontRenderer var1 = this.mc.fontRenderer;
            final ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            final int var3 = ScaledResolution.getScaledWidth();
            final short var4 = 182;
            final int var5 = var3 / 2 - var4 / 2;
            final int var6 = (int)(BossStatus.healthScale * (var4 + 1));
            final byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            if (var6 > 0) {
                this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            }
            final String var8 = BossStatus.bossName;
            var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.renderEngine.bindTexture("/gui/icons.png");
        }
    }
    
    private void renderPumpkinBlur(final int par1, final int par2) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        this.mc.renderEngine.bindTexture("%blur%/misc/pumpkinblur.png");
        final Tessellator var3 = Tessellator.instance;
        var3.startDrawingQuads();
        var3.addVertexWithUV(0.0, par2, -90.0, 0.0, 1.0);
        var3.addVertexWithUV(par1, par2, -90.0, 1.0, 1.0);
        var3.addVertexWithUV(par1, 0.0, -90.0, 1.0, 0.0);
        var3.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var3.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderVignette(float par1, final int par2, final int par3) {
        par1 = 1.0f - par1;
        if (par1 < 0.0f) {
            par1 = 0.0f;
        }
        if (par1 > 1.0f) {
            par1 = 1.0f;
        }
        this.prevVignetteBrightness += (float)((par1 - this.prevVignetteBrightness) * 0.01);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0f);
        this.mc.renderEngine.bindTexture("%blur%/misc/vignette.png");
        final Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        var4.addVertexWithUV(0.0, par3, -90.0, 0.0, 1.0);
        var4.addVertexWithUV(par2, par3, -90.0, 1.0, 1.0);
        var4.addVertexWithUV(par2, 0.0, -90.0, 1.0, 0.0);
        var4.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
        var4.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
    }
    
    private void renderPortalOverlay(float par1, final int par2, final int par3) {
        if (par1 < 1.0f) {
            par1 *= par1;
            par1 *= par1;
            par1 = par1 * 0.8f + 0.2f;
        }
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, par1);
        this.mc.renderEngine.bindTexture("/terrain.png");
        final Icon var4 = Block.portal.getBlockTextureFromSide(1);
        final float var5 = var4.getMinU();
        final float var6 = var4.getMinV();
        final float var7 = var4.getMaxU();
        final float var8 = var4.getMaxV();
        final Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV(0.0, par3, -90.0, var5, var8);
        var9.addVertexWithUV(par2, par3, -90.0, var7, var8);
        var9.addVertexWithUV(par2, 0.0, -90.0, var7, var6);
        var9.addVertexWithUV(0.0, 0.0, -90.0, var5, var6);
        var9.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderInventorySlot(final int par1, final int par2, final int par3, final float par4) {
        final ItemStack var5 = Minecraft.thePlayer.inventory.mainInventory[par1];
        if (var5 != null) {
            final float var6 = var5.animationsToGo - par4;
            if (var6 > 0.0f) {
                GL11.glPushMatrix();
                final float var7 = 1.0f + var6 / 5.0f;
                GL11.glTranslatef(par2 + 8, par3 + 12, 0.0f);
                GL11.glScalef(1.0f / var7, (var7 + 1.0f) / 2.0f, 1.0f);
                GL11.glTranslatef(-(par2 + 8), -(par3 + 12), 0.0f);
            }
            RenderItem.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
            if (var6 > 0.0f) {
                GL11.glPopMatrix();
            }
            RenderItem.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, var5, par2, par3);
        }
    }
    
    public void updateTick() {
        if (this.recordPlayingUpFor > 0) {
            --this.recordPlayingUpFor;
        }
        ++this.updateCounter;
        if (Minecraft.thePlayer != null) {
            final ItemStack var1 = Minecraft.thePlayer.inventory.getCurrentItem();
            if (var1 == null) {
                this.remainingHighlightTicks = 0;
            }
            else if (this.highlightingItemStack != null && var1.itemID == this.highlightingItemStack.itemID && ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack) && (var1.isItemStackDamageable() || var1.getItemDamage() == this.highlightingItemStack.getItemDamage())) {
                if (this.remainingHighlightTicks > 0) {
                    --this.remainingHighlightTicks;
                }
            }
            else {
                this.remainingHighlightTicks = 40;
            }
            this.highlightingItemStack = var1;
        }
    }
    
    public void setRecordPlayingMessage(final String par1Str) {
        this.recordPlaying = "Now playing: " + par1Str;
        this.recordPlayingUpFor = 60;
        this.recordIsPlaying = true;
    }
    
    public GuiNewChat getChatGUI() {
        return this.persistantChatGUI;
    }
    
    public int getUpdateCounter() {
        return this.updateCounter;
    }
}
