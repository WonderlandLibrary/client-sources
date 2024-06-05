package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.UI.ClientNotification;
import gq.vapu.czfclient.Util.ClientUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

import java.awt.*;


public class Health
        extends Module {
    int fuck = 0;
    private int width;

    public Health() {
        super("Health", new String[]{"Healthy"}, ModuleType.Render);
    }

    @EventHandler
    private void renderHud(EventRender2D event) {
        if (mc.thePlayer.getHealth() >= 0.0f && mc.thePlayer.getHealth() < 10.0f) {
            this.width = 3;
        }
        if (mc.thePlayer.getHealth() >= 10.0f && mc.thePlayer.getHealth() < 100.0f) {
            this.width = 5;
        }
        mc.fontRendererObj.drawStringWithShadow("" + MathHelper.ceiling_float_int(mc.thePlayer.getHealth()), (float) (new ScaledResolution(mc).getScaledWidth() / 2 - this.width), (float) (new ScaledResolution(mc).getScaledHeight() / 2 - 5) - (float) Crosshair.SIZE.getValue().doubleValue() - (float) Crosshair.GAP.getValue().doubleValue(), mc.thePlayer.getHealth() <= 10.0f ? new Color(255, 0, 0).getRGB() : new Color(0, 255, 0).getRGB());
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        if (mc.thePlayer.getHealth() >= 0.0f && mc.thePlayer.getHealth() < 6.0f && fuck == 0) {
            fuck = 1;
            ClientUtil.sendClientMessage("Your HP is low!", ClientNotification.Type.WARNING);
        } else if (mc.thePlayer.getHealth() >= 0.0f && mc.thePlayer.getHealth() > 6.0f && fuck == 1) {
            fuck = 0;
        }
    }
}
