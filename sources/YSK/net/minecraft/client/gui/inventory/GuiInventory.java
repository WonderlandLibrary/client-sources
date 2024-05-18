package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float oldMouseX;
    private float oldMouseY;
    private static final String[] I;
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiInventory.inventoryBackground);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawTexturedModalRect(guiLeft, guiTop, "".length(), "".length(), this.xSize, this.ySize);
        drawEntityOnScreen(guiLeft + (0xA2 ^ 0x91), guiTop + (0x26 ^ 0x6D), 0xDB ^ 0xC5, guiLeft + (0xB7 ^ 0x84) - this.oldMouseX, guiTop + (0x43 ^ 0x8) - (0x79 ^ 0x4B) - this.oldMouseY, this.mc.thePlayer);
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiInventory(final EntityPlayer entityPlayer) {
        super(entityPlayer.inventoryContainer);
        this.allowUserInput = (" ".length() != 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
    
    static {
        I();
    }
    
    public static void drawEntityOnScreen(final int n, final int n2, final int n3, final float n4, final float n5, final EntityLivingBase entityLivingBase) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(n, n2, 50.0f);
        GlStateManager.scale(-n3, n3, n3);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float renderYawOffset = entityLivingBase.renderYawOffset;
        final float rotationYaw = entityLivingBase.rotationYaw;
        final float rotationPitch = entityLivingBase.rotationPitch;
        final float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        final float rotationYawHead = entityLivingBase.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-(float)Math.atan(n5 / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        entityLivingBase.renderYawOffset = (float)Math.atan(n4 / 40.0f) * 20.0f;
        entityLivingBase.rotationYaw = (float)Math.atan(n4 / 40.0f) * 40.0f;
        entityLivingBase.rotationPitch = -(float)Math.atan(n5 / 40.0f) * 20.0f;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow("".length() != 0);
        renderManager.renderEntityWithPosYaw(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.setRenderShadow(" ".length() != 0);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        this.fontRendererObj.drawString(I18n.format(GuiInventory.I["".length()], new Object["".length()]), 0xE2 ^ 0xB4, 0xD1 ^ 0xC1, 447714 + 320706 + 504741 + 2937591);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            super.initGui();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007=\t#\u0019\r<\u0002%V\u0007 \u00061\f\r<\u0000", "dRgWx");
    }
    
    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        this.updateActivePotionEffects();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        this.oldMouseX = n;
        this.oldMouseY = n2;
    }
}
