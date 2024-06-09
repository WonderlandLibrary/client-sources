// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.RenderHelper;
import intent.AquaDev.aqua.utils.RenderUtil;
import java.awt.Color;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.gui.GuiNewChat;
import intent.AquaDev.aqua.fr.lavache.anime.Easing;
import net.minecraft.client.gui.ScaledResolution;
import events.listeners.EventPostRender2D;
import events.listeners.EventRender2D;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.client.renderer.entity.RenderItem;
import intent.AquaDev.aqua.fr.lavache.anime.Animate;
import intent.AquaDev.aqua.modules.Module;

public class CustomHotbar extends Module
{
    Animate anim;
    private final RenderItem itemRenderer;
    
    public CustomHotbar() {
        super("CustomHotbar", Type.Visual, "CustomHotbar", 0, Category.Visual);
        this.anim = new Animate();
        this.itemRenderer = CustomHotbar.mc.getRenderItem();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRender2D) {
            this.drawShaders();
        }
        if (e instanceof EventPostRender2D) {
            this.drawRects();
        }
    }
    
    private void drawShaders() {
        final ScaledResolution sr = new ScaledResolution(CustomHotbar.mc);
        final int i = sr.getScaledWidth() / 2;
        this.anim.setEase(Easing.LINEAR).setMin(11.0f).setMax(40.0f).setSpeed(15.0f).setReversed(!GuiNewChat.animatedChatOpen).update();
        if (Aqua.moduleManager.getModuleByName("Blur").isToggled()) {
            Blur.drawBlurred(() -> RenderUtil.drawRoundedRect(i - 91, sr.getScaledHeight() - this.anim.getValue(), 182.0, 22.0, 3.0, new Color(0, 0, 0, 1).getRGB()), false);
        }
    }
    
    private void drawRects() {
        final ScaledResolution sr = new ScaledResolution(CustomHotbar.mc);
        final int i = sr.getScaledWidth() / 2;
        this.anim.setEase(Easing.LINEAR).setMin(11.0f).setMax(40.0f).setSpeed(GuiNewChat.animatedChatOpen ? 15.0f : 40.0f).setReversed(!GuiNewChat.animatedChatOpen).update();
        RenderUtil.drawRoundedRect2Alpha(i - 91, sr.getScaledHeight() - this.anim.getValue(), 182.0, 22.0, 3.0, new Color(0, 0, 0, 50));
        RenderUtil.drawRoundedRect2Alpha(sr.getScaledWidth() / 2.0f - 91.0f + CustomHotbar.mc.thePlayer.inventory.currentItem * 20, sr.getScaledHeight() - this.anim.getValue(), 22.0, 22.0, 3.0, RenderUtil.getColorAlpha(new Color(Aqua.setmgr.getSetting("HUDColor").getColor()).getRGB(), 100));
        for (int j = 0; j < 9; ++j) {
            final int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
            final int l = (int)(sr.getScaledHeight() - this.anim.getValue() + 2.0f);
            RenderHelper.enableStandardItemLighting();
            this.renderHotbarItem(j, k, l, CustomHotbar.mc.timer.renderPartialTicks, CustomHotbar.mc.thePlayer);
            RenderHelper.disableStandardItemLighting();
        }
    }
    
    private void renderHotbarItem(final int index, final int xPos, final int yPos, final float partialTicks, final EntityPlayer player) {
        final ItemStack itemstack = player.inventory.mainInventory[index];
        if (itemstack != null) {
            final float f = itemstack.animationsToGo - partialTicks;
            if (f > 0.0f) {
                GlStateManager.pushMatrix();
                final float f2 = 1.0f + f / 5.0f;
                GlStateManager.translate((float)(xPos + 8), (float)(yPos + 12), 0.0f);
                GlStateManager.scale(1.0f / f2, (f2 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.translate((float)(-(xPos + 8)), (float)(-(yPos + 12)), 0.0f);
            }
            this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
            if (f > 0.0f) {
                GlStateManager.popMatrix();
            }
            RenderHelper.enableStandardItemLighting();
            this.itemRenderer.renderItemOverlays(CustomHotbar.mc.fontRendererObj, itemstack, xPos, yPos);
            GlStateManager.resetColor();
        }
    }
}
