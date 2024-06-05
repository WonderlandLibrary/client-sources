package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class TargetHUD extends Module {

    public TargetHUD() {
        super("TargetHUD", new String[]{"targethud"}, ModuleType.Render);
    }

    @EventHandler
    public void onRender(EventRender2D event) {
        ScaledResolution sr = new ScaledResolution(mc);
        final FontRenderer font2 = mc.fontRendererObj;
        if (Killaura.curTarget != null && ModuleManager.getModuleByClass(TargetHUD.class).isEnabled()
                & ModuleManager.getModuleByClass(Killaura.class).isEnabled()) {
            final String name = Killaura.curTarget.getName() + " ";
            font2.drawStringWithShadow(name, (float) (sr.getScaledWidth() / 2) - (font2.getStringWidth(name) / 2),
                    (float) (sr.getScaledHeight() / 2 - 30), -1);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
            int i = 0;
            while ((float) i < Killaura.curTarget.getMaxHealth() / 2.0f) {
                Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((float) (sr.getScaledWidth() / 2)
                                - Killaura.curTarget.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10),
                        (float) (sr.getScaledHeight() / 2 - 16), 16, 0, 9, 9);
                ++i;
            }
            i = 0;
            while ((float) i < Killaura.curTarget.getHealth() / 2.0f) {
                Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((float) (sr.getScaledWidth() / 2)
                                - Killaura.curTarget.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10),
                        (float) (sr.getScaledHeight() / 2 - 16), 52, 0, 9, 9);
                ++i;
            }
        }
//        if(Killaura.curTarget.getHealth() < 5f && Killaura.curTarget != null){
//            mc.thePlayer.sendChatMessage("[List] L " + Killaura.curTarget.getName() +" You Must Be A Novoline/Flux user");
//        }
    }
}
