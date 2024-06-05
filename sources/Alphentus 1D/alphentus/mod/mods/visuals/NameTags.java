package alphentus.mod.mods.visuals;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.mod.mods.combat.Teams;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class NameTags extends Mod {

    public NameTags() {
        super("NameTags", Keyboard.KEY_NONE, true, ModCategory.VISUALS);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER3D)
            return;
        if (!getState())
            return;

        for (Object o : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) o;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase e = (EntityLivingBase) o;
                if (isValid(e)) {
                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
                    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;

                    boolean teamMate = Init.getInstance().modManager.getModuleByClass(Teams.class).getState() && (e.getDisplayName().getFormattedText().startsWith("§" + mc.thePlayer.getDisplayName().getFormattedText().charAt(1)) || e.getName().equalsIgnoreCase(""));
                    String name = e.getName();
                    String health = "Health: " + (e.getHealth() > mc.thePlayer.getHealth() ? "§c" : e.getHealth() == mc.thePlayer.getHealth() ? "§b" : "§a") + (Float.isNaN(e.getHealth()) ? "" : String.valueOf(Math.round(Float.parseFloat(String.valueOf(e.getHealth())))));

                    double size = 0;
                    if (mc.thePlayer.getDistanceToEntity(e) <= 6.5) {
                        size = -0.013;
                    } else {
                        size = -0.03 * (mc.thePlayer.getDistanceToEntity(e) / 15);
                    }

                    int textLength;
                    double heightY = y + 2.25F + size * 65;

                    if (mc.fontRendererObj.getStringWidth(name) > mc.fontRendererObj.getStringWidth(health))
                        textLength = mc.fontRendererObj.getStringWidth(name);
                    else
                        textLength = mc.fontRendererObj.getStringWidth(health);

                    GL11.glPushMatrix();
                    GL11.glTranslated(x, heightY, z);
                    GL11.glScaled(size, size, size);
                    GL11.glRotated(mc.getRenderManager().playerViewY, 0, -1, 0);
                    GlStateManager.disableDepth();

                    Gui.drawRect(-textLength / 2 - 1, -92 - (teamMate ? 10 : 0), textLength / 2 + 2, -71, 0x99000000);
                    if (teamMate)
                        mc.fontRendererObj.drawString("-TEAM-", mc.fontRendererObj.getStringWidth("-TEAM-") / 2 - mc.fontRendererObj.getStringWidth("-TEAM-") + 1, -100, Color.GREEN.hashCode());
                    mc.fontRendererObj.drawString(name, mc.fontRendererObj.getStringWidth(name) / 2 - mc.fontRendererObj.getStringWidth(name) + 1, -90, Init.getInstance().CLIENT_COLOR.hashCode());
                    mc.fontRendererObj.drawString(health, mc.fontRendererObj.getStringWidth(health) / 2 - mc.fontRendererObj.getStringWidth(health) + 1, -80, 0xFFFFFFFF);

                    GlStateManager.enableDepth();
                    GL11.glPopMatrix();
                }
            }
        }
    }

    public boolean isInTabList(Entity entity) {
        if (mc.isSingleplayer()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            for (Object o : mc.getNetHandler().getPlayerInfoMap()) {
                NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
                if (playerInfo.getGameProfile().getName().equalsIgnoreCase(entity.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid(EntityLivingBase e) {
        if (!isInTabList(e))
            return false;
        if (e == mc.thePlayer)
            return false;
        if (e.isInvisible())
            return false;
        return true;
    }
}
