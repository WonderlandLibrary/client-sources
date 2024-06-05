package me.rich.module.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.render.BreadcrumbHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.util.math.Vec3d;

public class Breadcrumbs extends Feature {
    
    ArrayList<BreadcrumbHelper> bcs = new ArrayList<BreadcrumbHelper>();
    
    
    public Breadcrumbs() {
        super("Breadcrumbs", 0, Category.RENDER);
        Main.settingsManager.rSetting(new Setting("LineWidth", this, 1, 1, 5, true));
        Main.settingsManager.rSetting(new Setting("RemoveTicks", this, 1, 1, 50, true));
    }

    @EventTarget
    public void bek(EventUpdate bek) {
        bcs.add(new BreadcrumbHelper(mc.player.getPositionVector()));
        
    }
    
    @EventTarget
    public void xoxol(Event3D xlol) {
        int time = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Breadcrumbs.class), "RemoveTicks").getValInt() * 500;
        
        if(isToggled()) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Breadcrumbs.class), "LineWidth").getValInt());
        RenderHelper.setColor(Main.getClientColor().getRGB());
        GL11.glBegin(3);
        for (int i = 0; i < bcs.size(); i++) {
            BreadcrumbHelper bc = bcs.get(i);
            if(bc.getTimer().hasReached(time)) {
                    bcs.remove(bc);
            }
            RenderHelper.putVertex3d(RenderHelper.getRenderPos(bc.getVector().xCoord, bc.getVector().yCoord + 0.3, bc.getVector().zCoord));
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        }
    }
    
    public void onDisable() {
        bcs.clear();
    }
    }