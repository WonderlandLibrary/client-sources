package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.LivingLabelEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.player.PlayerUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector4d;
import java.awt.*;


@ModuleInfo(
        name = "NameTags",
        category = Category.RENDER
)
public class NameTags extends Module {

    private final BooleanValue drawArmor = new BooleanValue("Draw Armor", true);
    private final BooleanValue drawHealth = new BooleanValue("Draw Health", true);

    public NameTags() {
        addSettings(drawArmor, drawHealth);
    }

    @Listen
    public void onRender (RenderEvent e) {
        if (e.getState() == RenderEvent.State.RENDER_2D) {
            mc.theWorld.loadedEntityList.forEach(entity -> {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer ent = (EntityPlayer) entity;
                    if (AttackUtil.isTarget(entity) && RenderUtil.isInViewFrustrum(ent.getEntityBoundingBox())) {
                        Vector4d position = RenderUtil.getProjectedEntity(ent, e.getPartialTicks());
                        mc.getEntityRenderer().setupOverlayRendering();
                        if (position != null) {
                            GL11.glPushMatrix();
                            float size = .5f;
                            if (drawArmor.getValue()) { RenderUtil.drawArmor(ent, (int) (position.x + ((position.z - position.x) / 2)), (int) position.y - 4 - mc.getFontRenderer().FONT_HEIGHT * 2, size); }
                            GlStateManager.scale(size, size, size);
                            float x = (float) position.x / size;
                            float x2 = (float) position.z / size;
                            float y = (float) position.y / size;
                            final String nametext = entity.getDisplayName().getFormattedText() + (drawHealth.getValue() ? " §7(§f" + MathUtil.roundToPlace(((EntityPlayer) entity).getHealth(), 1) + " §c❤§7)" : "");
                            RenderUtil.drawRoundedRect((x + (x2 - x) / 2) - (mc.getFontRenderer().getStringWidth(nametext) >> 1) - 2, y - mc.getFontRenderer().FONT_HEIGHT - 6, (x + (x2 - x) / 2) + (mc.getFontRenderer().getStringWidth(nametext) >> 1) + 2, y + 4, 3F, new Color(0, 0, 0, 120).getRGB());

                            mc.getFontRenderer().drawStringWithShadow(nametext, (x + ((x2 - x) / 2)) - (mc.getFontRenderer().getStringWidth(nametext) / 2F), y - mc.getFontRenderer().FONT_HEIGHT - 2, PlayerUtil.getNameColor(ent));
                            GL11.glPopMatrix();
                        }
                    }
                }
            });
        }

    }

    @Listen
    public void onLivingLabel (LivingLabelEvent event) {
        if(event.getEntity() instanceof EntityPlayer && AttackUtil.isTarget(event.getEntity())) {
            event.cancel();
        }
    }


}
