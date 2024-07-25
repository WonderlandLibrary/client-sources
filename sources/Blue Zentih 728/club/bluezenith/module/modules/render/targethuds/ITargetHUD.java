package club.bluezenith.module.modules.render.targethuds;

import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.render.TargetHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glColor4f;

public interface ITargetHUD {

    Minecraft mc = Minecraft.getMinecraft();

    ITargetHUD createInstance();

    void render(Render2DEvent event, EntityPlayer target, TargetHUD targetHUD);

    default void drawHead(EntityPlayer target, boolean checkHurtTime, int width, int height) {
        final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(target.getUniqueID());
        if(info == null) return;
        if(!checkHurtTime)
        GL11.glColor4f(1F, 1F, 1F, 1F);
        else glColor4f(1F, 1F - (target.hurtTime / 15f), 1F - (target.hurtTime / 15f), 1F);
        mc.getTextureManager().bindTexture(info.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(2, 2, 8F, 8F, 8, 8, width, height, 64F, 64F);
    }

    default float hoverX(TargetHUD targetHUD) {
        return targetHUD.x.get();
    }

    default float hoverY(TargetHUD targetHUD) {
        return targetHUD.y.get();
    }


}
