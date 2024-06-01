package best.actinium.module.impl.combat;

import best.actinium.component.componets.PingSpoofComponent;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.render.Render3DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.ColorProperty;
import best.actinium.property.impl.InfoStringProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;

/**
 * @author Nyghtfull
 * @since 2/7/2024
 */
@ModuleInfo(
        name = "Lag Range",
        description = "make you tp in front of enemy's.",
        category = ModuleCategory.COMBAT
)
public class LagRangeModule extends Module {
    private NumberProperty everyMS = new NumberProperty("Every MS", this, 1, 200, 1000, 1);
    private NumberProperty delayMS = new NumberProperty("Delay MS", this, 1, 200, 1000, 1);
    private InfoStringProperty packetStuff = new InfoStringProperty("Packet Stuff", this, false);
    private BooleanProperty teleport = new BooleanProperty("Teleport",this,true),
    velocity = new BooleanProperty("Velocity",this,true);
    private InfoStringProperty renderStuff = new InfoStringProperty("Render Stuff", this, false);
    public BooleanProperty esp = new BooleanProperty("Render ESP", this, true);
    private ColorProperty outlineColor = new ColorProperty("Outline Color", this, Color.red);
    private ColorProperty fillColor = new ColorProperty("Fill Color", this, Color.white);
    private double x, y, z;
    private boolean blinking = false, picked = false;
    private TimerUtil delay = new TimerUtil(), ever = new TimerUtil();
    private EntityLivingBase target;

    @Override
    public void onEnable() {
        x = mc.thePlayer.posX;
        y = mc.thePlayer.posY;
        z = mc.thePlayer.posZ;
        blinking = false;
        super.onEnable();
    }

    @Callback
    public void onUpdate(UpdateEvent event) {
        //todo: finish the target stuff btw
        this.target = mc.theWorld.getLoadedEntityList().stream()
                .filter(entity -> entity instanceof EntityPlayer && entity != mc.thePlayer).map(entity -> (EntityPlayer) entity)
                .min(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity))).orElse(null);

        if (ever.finished(everyMS.getValue().longValue())) {
            blinking = true;
        }

        if (delay.finished(delayMS.getValue().longValue()) && blinking) {
            blinking = false;
            delay.reset();
        }

        if (blinking) {
            if (!picked) {
                x = mc.thePlayer.posX;
                y = mc.thePlayer.posY;
                z = mc.thePlayer.posZ;
                picked = true;
            }

            PingSpoofComponent.setSpoofing(999999999, true, teleport.isEnabled(), velocity.isEnabled(), true, true, true);
            ever.reset();
        } else {
            //todo: test if workie and component is fine
            //i think i need to do this too :skull:
            PingSpoofComponent.dispatch();
            picked = false;
        }
    }

    @Callback
    public void onRender(Render3DEvent event) {
        if (!esp.isEnabled() || !blinking || mc.gameSettings.thirdPersonView == 0) {
            return;
        }

        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GlStateManager.disableCull();
        GL11.glDepthMask(false);

        RenderUtil.drawPosESP(x, y, z, 0.23f, outlineColor.getColor(), fillColor.getColor());

        GL11.glDepthMask(true);
        GlStateManager.enableCull();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2848);
    }
}
