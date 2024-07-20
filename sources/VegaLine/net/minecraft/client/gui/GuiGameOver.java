/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.modules.Respawn;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.ColorUtils;

public class GuiGameOver
extends GuiScreen {
    TimerHelper timer = new TimerHelper();
    private int enableButtonsTimer;
    private final ITextComponent causeOfDeath;
    private float smoothAlpha;
    private float smoothAlpha2;
    private int deathX;
    private int deathY;
    private int deathZ;

    public GuiGameOver(@Nullable ITextComponent p_i46598_1_) {
        this.causeOfDeath = p_i46598_1_;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.enableButtonsTimer = 0;
        if (this.mc.currentScreen instanceof GuiGameOver && Minecraft.player.deathTime == 0 && !Respawn.get.actived) {
            this.timer.reset();
            this.smoothAlpha = 0.0f;
            this.smoothAlpha2 = 0.0f;
            this.deathX = (int)Minecraft.player.posX;
            this.deathY = (int)Minecraft.player.posY;
            this.deathZ = (int)Minecraft.player.posZ;
        }
        if (this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
            this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 262, I18n.format("deathScreen.spectate", new Object[0])));
            this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 286, I18n.format("deathScreen." + (this.mc.isIntegratedServerRunning() ? "deleteWorld" : "leaveServer"), new Object[0])));
        } else {
            this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 262, I18n.format("deathScreen.respawn", new Object[0])));
            this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 286, I18n.format("deathScreen.titleScreen", new Object[0])));
            if (!Panic.stop) {
                this.buttonList.add(new GuiButton(228, width / 2 - 100, height / 4 + 286 + 24, I18n.format("deathScreen.spectate", new Object[0])));
            }
            if (this.mc.getSession() == null) {
                ((GuiButton)this.buttonList.get((int)1)).enabled = false;
            }
        }
        if (!this.mc.gameSettings.ofFastRender && !this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/desaturate.json"));
        }
        for (GuiButton guibutton : this.buttonList) {
            guibutton.enabled = false;
        }
    }

    private void drawDeath(ResourceLocation image, float outsize, boolean smooth) {
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, this.smoothAlpha2 < 1.0f ? this.smoothAlpha2 * 0.75f : this.smoothAlpha / 4.0f + 0.75f);
        this.mc.getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(0.0f - outsize, 0.0f - outsize, 0.0f, 0.0f, (float)width + outsize * 2.0f, (float)height + outsize * 2.0f, (float)width + outsize * 2.0f, (float)height + outsize * 2.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                Minecraft.player.respawnPlayer();
                this.mc.displayGuiScreen(null);
                break;
            }
            case 228: {
                GuiIngameMenu.respawnKey = true;
                Minecraft.player.setDead(false);
                Minecraft.player.setHealth(20.0f);
                Minecraft.player.capabilities.isFlying = true;
                Minecraft.player.closeScreen();
                this.mc.playerController.setGameType(GameType.SPECTATOR);
                Minecraft.player.noClip = true;
                break;
            }
            case 1: {
                if (this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                    break;
                }
                GuiYesNo guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
                this.mc.displayGuiScreen(guiyesno);
                guiyesno.setButtonDelay(0);
            }
        }
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (result) {
            if (this.mc.world != null) {
                this.mc.world.sendQuittingDisconnectingPacket();
            }
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(new GuiMainMenu());
        } else {
            Minecraft.player.respawnPlayer();
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.theShaderGroup = null;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ITextComponent itextcomponent;
        boolean flag = this.mc.world.getWorldInfo().isHardcoreModeEnabled();
        boolean smooth = false;
        if (this.smoothAlpha2 < 1.0f) {
            this.smoothAlpha2 = (float)((double)this.smoothAlpha2 + 0.005);
        }
        if (this.timer.hasReached(2300.0)) {
            if (this.smoothAlpha < 1.0f) {
                this.smoothAlpha = (float)((double)this.smoothAlpha + 0.015);
                smooth = true;
            }
            String coords = this.deathX + " / " + this.deathY + " / " + this.deathZ;
            float expandingY = this.smoothAlpha2 + this.smoothAlpha > 0.0f ? this.smoothAlpha2 + this.smoothAlpha : 2.0f;
            Fonts.comfortaaRegular_18.drawString(coords, width / 2 - Fonts.comfortaaRegular_18.getStringWidth(coords) / 2, (double)height * 0.7 - (double)(expandingY * 5.0f), -1);
            GuiGameOver.drawRect(0, 0.0, (double)width, (double)height, ColorUtils.getColor((int)(228.0f * this.smoothAlpha), (int)(41.0f * this.smoothAlpha), (int)(41.0f * this.smoothAlpha), (int)(80.0f * this.smoothAlpha)));
        }
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        this.drawCenteredString(this.fontRendererObj, I18n.format(flag ? "deathScreen.title.hardcore" : "deathScreen.title", new Object[0]), width / 2 / 2, 30, 0xFFFFFF);
        GlStateManager.popMatrix();
        if (this.causeOfDeath != null) {
            this.drawCenteredString(this.fontRendererObj, this.causeOfDeath.getFormattedText(), width / 2, 85, 0xFFFFFF);
        }
        this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score", new Object[0]) + ": " + TextFormatting.YELLOW + Minecraft.player.getScore(), width / 2, 100, 0xFFFFFF);
        if (this.causeOfDeath != null && mouseY > 85 && mouseY < 85 + this.fontRendererObj.FONT_HEIGHT && (itextcomponent = this.getClickedComponentAt(mouseX)) != null && itextcomponent.getStyle().getHoverEvent() != null) {
            this.handleComponentHover(itextcomponent, mouseX, mouseY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Nullable
    public ITextComponent getClickedComponentAt(int p_184870_1_) {
        if (this.causeOfDeath == null) {
            return null;
        }
        int i = this.mc.fontRendererObj.getStringWidth(this.causeOfDeath.getFormattedText());
        int j = width / 2 - i / 2;
        int k = width / 2 + i / 2;
        int l = j;
        if (p_184870_1_ >= j && p_184870_1_ <= k) {
            for (ITextComponent itextcomponent : this.causeOfDeath) {
                if ((l += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.removeTextColorsIfConfigured(itextcomponent.getUnformattedComponentText(), false))) <= p_184870_1_) continue;
                return itextcomponent;
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.enableButtonsTimer;
        if (this.enableButtonsTimer == 20) {
            for (GuiButton guibutton : this.buttonList) {
                guibutton.enabled = true;
            }
        }
    }
}

