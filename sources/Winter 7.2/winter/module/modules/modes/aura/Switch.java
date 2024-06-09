/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.aura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import winter.event.events.Render3DEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Aura;
import winter.module.modules.AutoPot;
import winter.module.modules.modes.Mode;
import winter.module.modules.modes.aura.Tick;
import winter.utils.friend.FriendUtil;
import winter.utils.other.Timer;
import winter.utils.render.xd.Box;
import winter.utils.render.xd.OGLRender;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Switch
extends Mode {
    private Timer s;
    public Aura parent;
    public ArrayList<Entity> targetList;
    public int target = 0;

    public Switch(Module part, String name) {
        super(part, name);
        this.parent = (Aura)part;
        this.s = new Timer();
        this.targetList = new ArrayList();
    }

    @Override
    public void enable() {
        this.targetList.clear();
        Aura.delay = 0;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            ++Aura.delay;
            this.findTargets();
            if (this.target >= this.targetList.size()) {
                this.target = 0;
            }
            Aura.target = !this.targetList.isEmpty() ? this.targetList.get(this.target) : null;
            if (Aura.target != null && Aura.potTimer.hasPassed(AutoPot.jump.isEnabled() ? 500 : 100)) {
                event.setYaw(Aura.getYaw(Aura.target));
                event.setPitch(Aura.getPitch(Aura.target));
            }
            if (this.s.hasPassed(300.0f)) {
                ++this.target;
                this.s.reset();
            }
        } else {
            if (Tick.shouldBlock() && Switch.mc.thePlayer.getCurrentEquippedItem() != null && Switch.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && Aura.block.isEnabled()) {
                Switch.mc.playerController.sendUseItem(Switch.mc.thePlayer, Switch.mc.theWorld, Switch.mc.thePlayer.getCurrentEquippedItem());
            }
            if (Aura.target != null && (double)Switch.mc.thePlayer.getDistanceToEntity(Aura.target) <= Aura.range.getValue() && (double)Aura.delay >= Aura.ticks.getValue() && Aura.potTimer.hasPassed(AutoPot.jump.isEnabled() ? 500 : 100)) {
                Aura.attack(Aura.target, true);
                Aura.delay = 0;
            }
        }
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (!this.targetList.isEmpty()) {
            for (Entity ent : this.targetList) {
                float blue;
                float green;
                float red;
                if (!(ent instanceof EntityPlayer) || !Aura.box.isEnabled() || (double)ent.getDistanceToEntity(Switch.mc.thePlayer) > Aura.range.getValue()) continue;
                mc.getRenderManager();
                float x2 = (float)(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)Switch.mc.timer.renderPartialTicks - RenderManager.renderPosX);
                double y2 = ent.posY - RenderManager.renderPosY - 0.01;
                mc.getRenderManager();
                float z2 = (float)(ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)Switch.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
                Box box = ent.isSneaking() ? new Box((double)x2 - 0.45, y2, (double)z2 - 0.45, (double)x2 + 0.45, y2 + 1.62, (double)z2 + 0.45) : new Box((double)x2 - 0.45, y2, (double)z2 - 0.45, (double)x2 + 0.45, y2 + 1.9, (double)z2 + 0.45);
                if (((EntityLivingBase)ent).hurtTime != 0) {
                    red = 0.9f;
                    green = 0.0f;
                    blue = 0.0f;
                } else {
                    red = 0.0f;
                    green = 0.9f;
                    blue = 0.0f;
                }
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glTranslated(x2, y2, z2);
                GL11.glRotated(ent.rotationYaw, 0.0, y2, 0.0);
                GL11.glTranslated(- x2, - y2, - z2);
                GL11.glColor4f(red, green, blue, 0.1f);
                OGLRender.drawBox(box);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
    }

    public void findTargets() {
        this.targetList.clear();
        double r2 = Aura.range.getValue();
        for (Object o : Switch.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent == Switch.mc.thePlayer || (double)Switch.mc.thePlayer.getDistanceToEntity(ent) > r2 || (!(ent instanceof EntityPlayer) || !Aura.pl.isEnabled()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityWither) && !(ent instanceof EntityBat) && !(ent instanceof EntityGolem) && !(ent instanceof EntityDragon) || !Aura.mo.isEnabled()) && (!(ent instanceof EntityAnimal) || !Aura.an.isEnabled()) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || !this.parent.invis(ent) || !Aura.isntBot(ent) || this.parent.isTeamed(ent) || this.targetList.contains(ent)) continue;
            this.targetList.add(ent);
        }
        this.targetList.sort(new Comparator<Entity>(){

            @Override
            public int compare(Entity target1, Entity target2) {
                return Math.round(((EntityLivingBase)target2).getHealth() - ((EntityLivingBase)target1).getHealth());
            }
        });
    }

}

