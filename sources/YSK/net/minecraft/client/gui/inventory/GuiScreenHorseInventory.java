package net.minecraft.client.gui.inventory;

import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class GuiScreenHorseInventory extends GuiContainer
{
    private static final ResourceLocation horseGuiTextures;
    private IInventory playerInventory;
    private IInventory horseInventory;
    private float mousePosY;
    private float mousePosx;
    private EntityHorse horseEntity;
    private static final String[] I;
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(this.horseInventory.getDisplayName().getUnformattedText(), 0x80 ^ 0x88, 0xA8 ^ 0xAE, 4197195 + 3304887 - 6235483 + 2944153);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 0x56 ^ 0x5E, this.ySize - (0x5B ^ 0x3B) + "  ".length(), 2190915 + 3096689 - 1252314 + 175462);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("-\u001d\u0012;%+\u001d\u0019`7,\u0011E,?7\f\u000b&><\nE'?+\u000b\u000fa 7\u001f", "YxjOP");
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenHorseInventory.horseGuiTextures);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        if (this.horseEntity.isChested()) {
            this.drawTexturedModalRect(n4 + (0x67 ^ 0x28), n5 + (0xD3 ^ 0xC2), "".length(), this.ySize, 0x73 ^ 0x29, 0x59 ^ 0x6F);
        }
        if (this.horseEntity.canWearArmor()) {
            this.drawTexturedModalRect(n4 + (0x8D ^ 0x8A), n5 + (0xB ^ 0x28), "".length(), this.ySize + (0x17 ^ 0x21), 0x65 ^ 0x77, 0x88 ^ 0x9A);
        }
        GuiInventory.drawEntityOnScreen(n4 + (0xB6 ^ 0x85), n5 + (0x4D ^ 0x71), 0x7A ^ 0x6B, n4 + (0x63 ^ 0x50) - this.mousePosx, n5 + (0xE6 ^ 0xAD) - (0x4E ^ 0x7C) - this.mousePosY, this.horseEntity);
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        horseGuiTextures = new ResourceLocation(GuiScreenHorseInventory.I["".length()]);
    }
    
    public GuiScreenHorseInventory(final IInventory playerInventory, final IInventory horseInventory, final EntityHorse horseEntity) {
        super(new ContainerHorseInventory(playerInventory, horseInventory, horseEntity, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.horseInventory = horseInventory;
        this.horseEntity = horseEntity;
        this.allowUserInput = ("".length() != 0);
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.mousePosx = n;
        this.mousePosY = n2;
        super.drawScreen(n, n2, n3);
    }
}
