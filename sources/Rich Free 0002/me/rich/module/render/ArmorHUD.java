package me.rich.module.render;


import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class ArmorHUD extends Feature {
    private static final RenderItem itemRender;

    public ArmorHUD() {
        super("ArmorHUD", 0, Category.RENDER);
    }

    @EventTarget
    public void onRender2D(Event2D event) {
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(mc);
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 65 - (mc.player.isInWater() ? 10 : 0);
        for (ItemStack is : mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.func_190926_b()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            itemRender.zLevel = 200.0f;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, y, "");
            itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.func_190916_E() > 1 ? is.func_190916_E() + "" : "";
            Fonts.neverlose500_14.drawStringWithShadow(s, x + 19 - 2 - Fonts.neverlose500_14.getStringWidth(s), y + 20, 0xFFFFFF);
            int green = Math.abs(is.getMaxDamage() - is.getItemDamage());
            Fonts.neverlose500_14.drawStringWithShadow(green + "", x + 8 - Fonts.neverlose500_14.getStringWidth(green + "") / 2, y - -18, -1);
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    static {
        itemRender = mc.getMinecraft().getRenderItem();
    }
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}