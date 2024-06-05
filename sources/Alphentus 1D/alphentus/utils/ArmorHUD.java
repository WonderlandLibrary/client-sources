package alphentus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class ArmorHUD {

    Minecraft mc = Minecraft.getMinecraft();

    Translate translate;
    int x, y, slot = 0;


    ItemStack itemStack = null;

    public ArmorHUD(int slot) {
        this.translate = new Translate(0, 0);
        this.slot = slot;
    }

    public void tickUpdate() {
        this.itemStack = mc.thePlayer.inventory.armorItemInSlot(3 - slot);
    }

    public void drawScreen() {

        if (itemStack != null) {
            translate.interpolate(16, 0, 1);
        } else {
            translate.setX(-15);
        }
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int) (x + translate.getX()), y);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(this.mc.fontRendererObj, itemStack, (int) (x + translate.getX()), y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public void setInformations(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
