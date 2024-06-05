package client.metaware.impl.module.combat;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.event.impl.render.Render2DEvent;
import client.metaware.impl.event.impl.render.Render3DEvent;
import client.metaware.impl.module.movmeent.Flight;
import client.metaware.impl.module.movmeent.Speed;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

@ModuleInfo(name = "TargetStrafe", renderName = "Target Strafe", category = Category.COMBAT)
public final class TargetStrafe extends Module {

    public final DoubleProperty radius = new DoubleProperty("Radius", 2.0D, 0.1D, 4.0D, 0.1D);
    public final DoubleProperty pointz = new DoubleProperty("Points", 50, 3, 100, 1);
    public final Property<Boolean> holdspace = new Property<Boolean>("Hold Space", true);
    private final Property<Boolean> render = new Property<Boolean>("Render", true);

    private final List<Vector3f> points = new ArrayList<>();

    public byte direction;

    @EventHandler
    private Listener<UpdatePlayerEvent> eventListener = event -> {
        if (event.isPre()) {
            if (mc.thePlayer.isCollidedHorizontally || MovementUtils.isOverVoid()) {
                direction = (byte) -direction;
                return;
            }

            if (mc.gameSettings.keyBindLeft.isKeyDown()) {
                direction = 1;
                return;
            }

            if (mc.gameSettings.keyBindRight.isKeyDown())
                direction = -1;
        }
    };

    @EventHandler
    private Listener<Render2DEvent> eventListener1 = event ->{

    };

    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    public static void drawLinesAroundPlayer(Entity entity, RenderManager renderManager, double radius, float partialTicks, int points, float width, int color) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glDisable(GL_DEPTH_TEST);
        glLineWidth(width);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glBegin(GL_LINE_STRIP);
        final double x = RenderUtil.interpolate(entity.prevPosX, entity.posX, partialTicks) - renderManager.viewerPosX;
        final double y = RenderUtil.interpolate(entity.prevPosY, entity.posY, partialTicks) - renderManager.viewerPosY;
        final double z = RenderUtil.interpolate(entity.prevPosZ, entity.posZ, partialTicks) - renderManager.viewerPosZ;
        RenderUtil.glColor(color);
        for (int i = 0; i <= points; i++)
            glVertex3d(x + radius * Math.cos(i * Math.PI * 2 / points), y, z + radius * Math.sin(i * Math.PI * 2 / points));
        glEnd();
        glDepthMask(true);
        glDisable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    @EventHandler
    private Listener<Render3DEvent> render3DEventListener = event ->{
        final KillAura killAura = Metaware.INSTANCE.getModuleManager().getModuleByClass(KillAura.class);
        final Speed speed = Metaware.INSTANCE.getModuleManager().getModuleByClass(Speed.class);
        final Flight flight = Metaware.INSTANCE.getModuleManager().getModuleByClass(Flight.class);
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            boolean colorchange = speed.isToggled() || flight.isToggled();
            int color = 0;
            if (killAura.target == entity && colorchange && !this.holdspace.getValue()) {
                color = Color.green.getRGB();
            }
            else if (killAura.target == entity && colorchange && this.holdspace.getValue() && mc.gameSettings.keyBindJump.isKeyDown()) {
                color = Color.green.getRGB();
            } else {
                color = Color.white.getRGB();
            }
            if (render.getValue() && entity != null && killAura.target == entity) {
                drawLinesAroundPlayer(entity, mc.getRenderManager(), radius.getValue(), event.getPartialTicks(), pointz.getValue().intValue(), 3f, Color.black.getRGB());
                drawLinesAroundPlayer(entity, mc.getRenderManager(), radius.getValue(), event.getPartialTicks(), pointz.getValue().intValue(), 2, color);
            }
        }
    };
}