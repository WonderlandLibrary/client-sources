/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.client.gui.inventory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.Client;
import skizzle.modules.ModuleManager;
import skizzle.ui.Particle;
import skizzle.ui.elements.Button;
import skizzle.util.AnimationHelper;

public class GuiInventory
extends InventoryEffectRenderer {
    private float oldMouseX;
    private float oldMouseY;
    private static final String __OBFID = "CL_00000761";
    public static List<Particle> particles = new ArrayList<Particle>();
    private AnimationHelper animation = new AnimationHelper();

    public GuiInventory(EntityPlayer p_i1094_1_) {
        super(p_i1094_1_.inventoryContainer);
        this.allowUserInput = true;
    }

    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        this.func_175378_g();
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.currentScreen == null) {
            particles.clear();
            particles = Particle.generateNiceParticles();
        }
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        } else {
            super.initGui();
        }
        if (!Client.ghostMode) {
            Button button = new Button(String.valueOf(ModuleManager.killaura.isEnabled() ? "Disable" : "Enable") + " Killaura", 1001, this.guiLeft + this.xSize - 87, this.guiTop - 24, 90, 20);
            this.sButtonList.add(button);
            Button button2 = new Button(String.valueOf(ModuleManager.invManager.isEnabled() ? "Disable" : "Enable") + " Manager", 1002, this.guiLeft + this.xSize - 179, this.guiTop - 24, 90, 20);
            this.sButtonList.add(button2);
            Button button3 = new Button(String.valueOf(ModuleManager.chestStealer.isEnabled() ? "Disable" : "Enable") + " ChestStealer", 1003, this.guiLeft + this.xSize - 179, this.guiTop - 45, 180, 20);
            this.sButtonList.add(button3);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawStringNormal(I18n.format("container.crafting", new Object[0]), 86.0f, 16.0f, 0x404040);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        particles = Particle.normalDraw(particles);
        this.oldMouseX = mouseX;
        this.oldMouseY = mouseY;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(inventoryBackground);
        int var4 = this.guiLeft;
        int var5 = this.guiTop;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        GuiInventory.drawEntityOnScreen(var4 + 51, var5 + 75, 30, (float)(var4 + 51) - this.oldMouseX, (float)(var5 + 75 - 50) - this.oldMouseY, this.mc.thePlayer);
    }

    public static void drawEntityOnScreen(int x, int y, int size, float yaw, float pitch, EntityLivingBase entity) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 50.0f);
        GlStateManager.scale(-size, size, size);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        float var6 = entity.renderYawOffset;
        float var7 = entity.rotationYaw;
        float var8 = entity.rotationPitch;
        float var9 = entity.prevRotationYawHead;
        float var10 = entity.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-((float)Math.atan(pitch / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        entity.renderYawOffset = (float)Math.atan(yaw / 40.0f) * 20.0f;
        entity.rotationYaw = (float)Math.atan(yaw / 40.0f) * 40.0f;
        entity.rotationPitch = -((float)Math.atan(pitch / 40.0f)) * 20.0f;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.func_178631_a(180.0f);
        var11.func_178633_a(false);
        var11.renderEntityWithPosYaw(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        var11.func_178633_a(true);
        entity.renderYawOffset = var6;
        entity.rotationYaw = var7;
        entity.rotationPitch = var8;
        entity.prevRotationYawHead = var9;
        entity.rotationYawHead = var10;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
}

