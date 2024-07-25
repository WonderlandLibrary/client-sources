package club.bluezenith.module.modules.render.targethud;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.ExtendedModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glColor4f;

public interface TargetHudMode extends ExtendedModeValue.Mode {
    Minecraft mc = Minecraft.getMinecraft();

    void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD);

    void startDisappearing();

    void stopDisappearing();

    boolean doneDisappearing();

    default void drawHead(EntityPlayer target, boolean checkHurtTime) {
        final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
        if(info == null) return;
        if(!checkHurtTime)
            GL11.glColor4f(1F, 1F, 1F, 1F);
        else glColor4f(1F, 1F - (target.hurtTime / 15f), 1F - (target.hurtTime / 15f), 1F);
        mc.getTextureManager().bindTexture(info.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(2, 2, 8F, 8F, 8, 8, 30, 30, 64F, 64F);
    }

    default void scaleFromCenter(float width, float height, float scale) {
        width /= 2F;
        height /= 2F;

        GlStateManager.translate(width, height, 0);
        GlStateManager.scale(scale, scale, 0);
        GlStateManager.translate(-width, -height, 0);
    }

    default boolean translateToPos() {
        return true;
    }

    default Value<?>[] addSettings() {
        return null;
    }
}
