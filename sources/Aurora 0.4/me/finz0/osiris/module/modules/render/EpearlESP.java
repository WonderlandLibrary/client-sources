package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.GeometryMasks;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class EpearlESP extends Module {
    public EpearlESP() {
        super("PearlESP", Category.RENDER, "Draws a box around entities");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "BoxEspRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(r = new Setting("Red", this, 255, 1, 255, true, "BoxEspRed"));
        AuroraMod.getInstance().settingsManager.rSetting(g = new Setting("Green", this, 255, 1, 255, true, "BoxEspGreen"));
        AuroraMod.getInstance().settingsManager.rSetting(b = new Setting("Blue", this, 255, 1, 255, true, "BoxEspBlue"));
        AuroraMod.getInstance().settingsManager.rSetting(a = new Setting("Alpha", this, 50, 1, 255, true, "BoxEspAlpha"));
        AuroraMod.getInstance().settingsManager.rSetting(r2 = new Setting("RedOutline", this, 255, 1, 255, true, "BoxEspRed"));
        AuroraMod.getInstance().settingsManager.rSetting(g2 = new Setting("GreenOutline", this, 255, 1, 255, true, "BoxEspGreenOutline"));
        AuroraMod.getInstance().settingsManager.rSetting(b2 = new Setting("BlueOutline", this, 255, 1, 255, true, "BoxEspBlueOutline"));
        AuroraMod.getInstance().settingsManager.rSetting(a2 = new Setting("AlphaOutline", this, 50, 1, 255, true, "BoxEspAlphaOutline"));
    }


    Setting rainbow;
    Setting r;
    Setting g;
    Setting b;
    Setting a;
    Setting r2;
    Setting g2;
    Setting b2;
    Setting a2;

    public void onWorldRender(RenderEvent event) {
        Color c = new Color(r.getValInt(), g.getValInt(), b.getValInt(), a.getValInt());
        if (rainbow.getValBoolean())
            c = new Color(Rainbow.getColor().getRed(), Rainbow.getColor().getGreen(), Rainbow.getColor().getBlue(), a.getValInt());
        Color finalC = c;
        Color finalC1 = c;
        mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .forEach(e -> {
                    OsirisTessellator.prepare(GL11.GL_QUADS);
                    {
                        OsirisTessellator.drawBox(e.getRenderBoundingBox(), finalC.getRGB(), GeometryMasks.Quad.ALL);
                        OsirisTessellator.drawBoundingBox(e.getRenderBoundingBox(), 1, finalC1.getRGB());
                    }
                });
    }
}


