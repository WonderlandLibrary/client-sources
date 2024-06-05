package me.superskidder.lune.modules.movement;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventMove;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.utils.player.MoveUtils;
import me.superskidder.lune.utils.entity.EntityValidator;
import me.superskidder.lune.utils.entity.impl.VoidCheck;
import me.superskidder.lune.utils.entity.impl.WallCheck;
import me.superskidder.lune.utils.gl.GLUtils;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Num;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public final class TargetStrafe
        extends Mod {
    private final Num radius = new Num("Radius", 2.0, 0.1, 4.0);
    private final Bool render = new Bool("Render", true);
    private final Bool directionKeys = new Bool("Direction Keys", true);
    public final Bool space = new Bool("Hold Space", false);
    private final EntityValidator targetValidator;
    private KillAura aura;
    private int direction = -1;
    private int wait;
    private List<Entity> targets = new ArrayList<>();

    public TargetStrafe() {
        super("TargetStrafe", ModCategory.Movement,"Make other players hit you hard");
        this.addValues(this.radius, this.render, this.directionKeys, this.space);
        this.targetValidator = new EntityValidator();
        this.targetValidator.add(new VoidCheck());
        this.targetValidator.add(new WallCheck());
    }

    @Override
    public void onEnabled() {
        if (this.aura == null) {
            this.aura = (KillAura) Lune.INSTANCE.moduleManager.getModByClass(KillAura.class);
        }
    }

    @EventTarget
    public final void onUpdate(EventPreUpdate event) {

    }

    private void switchDirection() {
        this.direction = this.direction == 1 ? -1 : 1;
    }


    @EventTarget
    public void onRender(EventRender3D event) 
    {
    	Method method;
		try {
			method = Lune.moduleManager.getModByClass(KillAura.class).getClass().getMethod("getTargets",Double.class);
	        for (Entity target : (List<Entity>)method.invoke(Lune.moduleManager.getModByClass(KillAura.class), 10.0d)) {
	            this.drawCircle(target, event.getPartialTicks(), (Double) this.radius.getValue());
	        }
		} catch (NoSuchMethodException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glPushMatrix();
        GL11.glDisable((int) 3553);
        GLUtils.startSmooth();
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glLineWidth((float) 1.0f);
        GL11.glBegin((int) 3);

        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - mc.getRenderManager().viewerPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - mc.getRenderManager().viewerPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - mc.getRenderManager().viewerPosZ;
        float r, g, b;
        r = 0.003921569f * (float) Color.RED.getRed();
        g = 0.003921569f * (float) Color.RED.getGreen();
        b = 0.003921569f * (float) Color.RED.getBlue();

        double pix2 = 6.283185307179586;
        for (int i = 0; i <= 90; ++i) {
            GL11.glColor3f((float) r, (float) g, (float) b);
            GL11.glVertex3d((double) (x + rad * Math.cos((double) i * 6.283185307179586 / 8.0)), (double) y, (double) (z + rad * Math.sin((double) i * 6.283185307179586 / 8.0)));
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean) true);
        GL11.glEnable((int) 2929);
        GLUtils.endSmooth();
        GL11.glEnable((int) 3553);
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);

    }

    public void strafe(EventMove event, double moveSpeed) {
        if (KillAura.target == null || mc.thePlayer.isInWater() || mc.thePlayer.isInLava()) {
            return;
        }
        if(mc.gameSettings.keyBindSprint.isKeyDown()){
            return;
        }

        if (wait > 0) {
            wait--;
        }
        if (mc.thePlayer.isCollidedHorizontally && wait == 0) {
            this.switchDirection();
            wait = 5;
        }

        if (mc.gameSettings.keyBindLeft.isPressed()) {
            this.direction = 1;
        }
        if (mc.gameSettings.keyBindRight.isPressed()) {
            this.direction = -1;
        }
        EntityLivingBase target = KillAura.target;
        float[] rotations = KillAura.getRotation(target);
        if ((double) TargetStrafe.mc.thePlayer.getDistanceToEntity(target) <= (Double) this.radius.getValue()) {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], this.direction, 0.0);
        } else {
            MoveUtils.setSpeed(event, moveSpeed, rotations[0], this.direction, 1.0);
        }
    }


    public boolean canStrafe() {
        if (this.aura == null) {
            this.aura = (KillAura) Lune.INSTANCE.moduleManager.getModByClass(KillAura.class);
        }
        return this.aura.getState() && this.aura.target != null && this.getState() && this.targetValidator.validate(this.aura.target) && ((Boolean) this.space.getValue() == false || TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown());
    }
}

