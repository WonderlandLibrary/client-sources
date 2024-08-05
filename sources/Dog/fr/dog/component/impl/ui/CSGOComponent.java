package fr.dog.component.impl.ui;

import fr.dog.Dog;
import fr.dog.component.Component;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.render.Render3DEvent;
import fr.dog.event.impl.world.TickEvent;
import fr.dog.module.impl.combat.KillAuraModule;
import fr.dog.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CSGOComponent extends Component {
    public static boolean enabled = false;

    public int haram = 0;

    protected static ArrayList<Vec3> sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj = new ArrayList<>();

    @SubscribeEvent
    public void commit9_11(Render3DEvent nigger) {
        if (!enabled)
            return;


        EntityPlayerSP liticane = mc.thePlayer;

        EntityPlayer piss = Dog.getInstance().getModuleManager().getModule(KillAuraModule.class).target;
        if (liticane != null && !sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj.isEmpty()) {
            drawPathLine(sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj, 2, Color.WHITE);
        }
        if (piss != null && liticane != null) {
            final double asipfnkaasg = liticane.lastTickPosX + (mc.thePlayer.posX - liticane.lastTickPosX) * mc.timer.renderPartialTicks;
            final double ioasnfkasf = mc.thePlayer.lastTickPosY + (liticane.posY - liticane.lastTickPosY) * mc.timer.renderPartialTicks;
            final double IllIllIlIIIllIIllIIlllIIIIlIIIlIIIIllllllIlIlIllIlllII = mc.thePlayer.lastTickPosZ + (liticane.posZ - liticane.lastTickPosZ) * mc.timer.renderPartialTicks;

            final double ibangedyourmama = piss.lastTickPosX + (piss.posX - piss.lastTickPosX) * mc.timer.renderPartialTicks;
            final double theretard = piss.lastTickPosY + (piss.posY - piss.lastTickPosY) * mc.timer.renderPartialTicks;
            final double balls = piss.lastTickPosZ + (piss.posZ - piss.lastTickPosZ) * mc.timer.renderPartialTicks;


            drawPathLine(new ArrayList<>(Arrays.asList(new Vec3(asipfnkaasg, ioasnfkasf, IllIllIlIIIllIIllIIlllIIIIlIIIlIIIIllllllIlIlIllIlllII), new Vec3(ibangedyourmama, theretard, balls))), 2, Color.RED);
        }
    }

    @SubscribeEvent
    public void hnjgmbnjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj(PlayerTickEvent e) {
        if (!enabled)
            return;

        EntityPlayer slavehalalnoharam = Dog.getInstance().getModuleManager().getModule(KillAuraModule.class).target;
        if (slavehalalnoharam != null) {
            if (slavehalalnoharam.hurtTime == 0) {
                sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj.clear();
            } else {
                sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj.add(new Vec3(slavehalalnoharam.posX, slavehalalnoharam.posY, slavehalalnoharam.posZ));
            }
        } else {
            sdefgcvhbjkjhgfdrsethujiklomjhbgfvthvj.clear();
        }
    }

    @SubscribeEvent
    public void lastTickPosX(TickEvent aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa) {
        if (!enabled)
            return;

    }

    public static void drawPathLine(ArrayList<Vec3> positions, float lineWidth, Color color) {
        Minecraft mc = Minecraft.getMinecraft();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(GL11.GL_LINE_STRIP);


        for (Vec3 vec3 : positions) {
            GL11.glColor4f(color.getRed() / 255.F,
                    color.getGreen() / 255.F,
                    color.getBlue() / 255.F, 1f);
            GL11.glVertex3d(vec3.xCoord - RenderManager.renderPosX, vec3.yCoord - RenderManager.renderPosY, vec3.zCoord - RenderManager.renderPosZ);
        }
        GL11.glEnd();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }
}
