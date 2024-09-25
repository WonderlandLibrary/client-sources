/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.client.gui.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import skizzle.Client;
import skizzle.modules.ModuleManager;
import skizzle.ui.Particle;
import skizzle.ui.elements.Button;
import skizzle.util.AnimationHelper;
import skizzle.util.Timer;

public class GuiChest
extends GuiContainer {
    private static final ResourceLocation field_147017_u = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private Timer timer = new Timer();
    private List<Particle> particles = new ArrayList<Particle>();
    private AnimationHelper animation = new AnimationHelper();
    private int inventoryRows;
    private static final String __OBFID = "CL_00000749";

    public GuiChest(IInventory upper, IInventory lower) {
        super(new ContainerChest(upper, lower, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = upper;
        this.lowerChestInventory = lower;
        this.allowUserInput = false;
        int var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = lower.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.particles = Particle.generateNiceParticles();
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
        this.fontRendererObj.drawStringNormal(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8.0f, 6.0f, 0x404040);
        this.fontRendererObj.drawStringNormal(this.upperChestInventory.getDisplayName().getUnformattedText(), 8.0f, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(field_147017_u);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        this.particles = Particle.normalDraw(this.particles);
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}

