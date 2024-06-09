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
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import winter.Client;
import winter.event.events.Render3DEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Aura;
import winter.module.modules.AutoPot;
import winter.module.modules.modes.Mode;
import winter.utils.friend.FriendUtil;
import winter.utils.other.Timer;
import winter.utils.render.xd.Box;
import winter.utils.render.xd.OGLRender;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Tick
extends Mode {
    private Timer s;
    private Timer swing;
    public static Aura parent;
    public ArrayList<Entity> targetList;
    public int target = 0;
    public static boolean doDura;
    public static int swapTicksLoLE;

    public Tick(Module part, String name) {
        super(part, name);
        parent = (Aura)part;
        this.s = new Timer();
        this.swing = new Timer();
        this.targetList = new ArrayList();
    }

    @Override
    public void enable() {
        this.findTargets();
        this.s.reset();
        this.s.setDifference(520);
        this.targetList.clear();
        Aura.delay = 0;
        this.swing.reset();
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            if (++Aura.delay > 3) {
                Aura.delay = 0;
                this.findTargets();
            }
            if (this.target >= this.targetList.size()) {
                this.target = 0;
            }
            Aura.target = !this.targetList.isEmpty() ? this.targetList.get(this.target) : null;
            if (Aura.target != null && AutoPot.ticks <= 0) {
                event.setYaw(Aura.getYaw(Aura.target));
                event.setPitch(Aura.getPitch(Aura.target));
                if (this.swing.hasPassed(25.0f)) {
                    float sharpLevel = EnchantmentHelper.func_152377_a(Tick.mc.thePlayer.getHeldItem(), ((EntityLivingBase)Aura.target).getCreatureAttribute());
                    Tick.mc.thePlayer.swingItem();
                    if (Client.getManager().getMod("Criticals").isEnabled()) {
                        Tick.mc.thePlayer.onCriticalHit(Aura.target);
                    }
                    if (sharpLevel > 0.0f) {
                        Tick.mc.thePlayer.onEnchantmentCritical(Aura.target);
                    }
                    this.swing.reset();
                }
            }
        } else {
            if (Tick.shouldBlock() && Tick.mc.thePlayer.getCurrentEquippedItem() != null && Tick.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword && Aura.block.isEnabled()) {
                Tick.mc.playerController.sendUseItem(Tick.mc.thePlayer, Tick.mc.theWorld, Tick.mc.thePlayer.getCurrentEquippedItem());
            }
            if (AutoPot.timer.getDifference() < 550) {
                return;
            }
            if (Aura.target != null && (double)Tick.mc.thePlayer.getDistanceToEntity(Aura.target) <= Aura.range.getValue() && this.s.hasPassed(550.0f) && AutoPot.ticks <= 0) {
                boolean block = false;
                if (Tick.mc.thePlayer.isBlocking() && Aura.unblock.isEnabled()) {
                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    block = true;
                }
                this.dura();
                ItemStack[] items = Aura.target.getInventory();
                if (items[3] != null) {
                    Tick.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a76Winter\u00a77]: \u00a7fTarget Helmet Durability: " + String.valueOf(items[3].getMaxDamage() - items[3].getItemDamage())));
                }
                this.s.reset();
                if (block) {
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Tick.mc.thePlayer.inventory.getCurrentItem()));
                    Tick.mc.thePlayer.setItemInUse(Tick.mc.thePlayer.inventory.getCurrentItem(), Tick.mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
                }
                ++this.target;
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
                if (!(ent instanceof EntityPlayer) || !Aura.box.isEnabled() || (double)ent.getDistanceToEntity(Tick.mc.thePlayer) > Aura.range.getValue()) continue;
                mc.getRenderManager();
                float x2 = (float)(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)Tick.mc.timer.renderPartialTicks - RenderManager.renderPosX);
                double y2 = ent.posY - RenderManager.renderPosY - 0.01;
                mc.getRenderManager();
                float z2 = (float)(ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)Tick.mc.timer.renderPartialTicks - RenderManager.renderPosZ);
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
        for (Object o : Tick.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent == Tick.mc.thePlayer || (double)Tick.mc.thePlayer.getDistanceToEntity(ent) > r2 || (!(ent instanceof EntityPlayer) || !Aura.pl.isEnabled()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityWither) && !(ent instanceof EntityBat) && !(ent instanceof EntityGolem) && !(ent instanceof EntityDragon) || !Aura.mo.isEnabled()) && (!(ent instanceof EntityAnimal) || !Aura.an.isEnabled()) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || !parent.invis(ent) || !Aura.isntBot(ent) || parent.isTeamed(ent) || this.targetList.contains(ent)) continue;
            this.targetList.add(ent);
        }
        this.targetList.sort(new Comparator<Entity>(){

            @Override
            public int compare(Entity target1, Entity target2) {
                return Math.round(((EntityLivingBase)target2).getHealth() - ((EntityLivingBase)target1).getHealth());
            }
        });
    }

    public void dura() {
        if (Aura.dora.isEnabled()) {
            boolean bl2 = Tick.doDura = !doDura;
        }
        if (doDura) {
            this.swap(9, Tick.mc.thePlayer.inventory.currentItem);
            this.attack(Aura.target, false);
            if (Tick.mc.thePlayer.ticksExisted % 10 == 0) {
                this.attack(Aura.target, true);
            }
            this.attack(Aura.target, false);
            this.attack(Aura.target, true);
            this.swap(9, Tick.mc.thePlayer.inventory.currentItem);
            this.attack(Aura.target, false);
            this.attack(Aura.target, true);
        } else {
            this.attack(Aura.target, false);
            this.attack(Aura.target, false);
            this.attack(Aura.target, true);
        }
        if (!Aura.dora.isEnabled()) {
            doDura = true;
        }
    }

    private void swap(int slot, int hotbarSlot) {
        Tick.mc.playerController.windowClick(Tick.mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot, 2, Tick.mc.thePlayer);
    }

    public void doCritical() {
        Tick.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Tick.mc.thePlayer.posX, Tick.mc.thePlayer.posY + 0.0625, Tick.mc.thePlayer.posZ, false));
        Tick.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Tick.mc.thePlayer.posX, Tick.mc.thePlayer.posY, Tick.mc.thePlayer.posZ, false));
        Tick.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Tick.mc.thePlayer.posX, Tick.mc.thePlayer.posY + 1.11E-4, Tick.mc.thePlayer.posZ, false));
        Tick.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Tick.mc.thePlayer.posX, Tick.mc.thePlayer.posY, Tick.mc.thePlayer.posZ, false));
    }

    public static boolean shouldBlock() {
        if (!Aura.block.isEnabled()) {
            return false;
        }
        for (Object o : Tick.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (!(ent instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)ent;
            if (ent == Tick.mc.thePlayer || (double)Tick.mc.thePlayer.getDistanceToEntity(ent) > Aura.brange.getValue() || (!(ent instanceof EntityPlayer) || !Aura.pl.isEnabled()) && (!(ent instanceof EntityMob) && !(ent instanceof EntityWither) && !(ent instanceof EntityBat) && !(ent instanceof EntityGolem) && !(ent instanceof EntityDragon) || !Aura.an.isEnabled()) && (!(ent instanceof EntityAnimal) || !Aura.an.isEnabled()) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || !parent.invis(ent) || !Aura.isntBot(ent) || parent.isTeamed(ent)) continue;
            return true;
        }
        return false;
    }

    public void attack(Entity ent, boolean crit) {
        float sharpLevel = EnchantmentHelper.func_152377_a(Tick.mc.thePlayer.getHeldItem(), ((EntityLivingBase)Aura.target).getCreatureAttribute());
        if (crit) {
            this.doCritical();
        } else {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        }
        Tick.mc.thePlayer.swingItem();
        if (!Aura.preach.isEnabled()) {
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
        } else {
            double entx = ent.posX - 3.5 * Math.cos(Math.toRadians(Aura.getYaw(ent) + 90.0f));
            double entz = ent.posZ - 3.5 * Math.sin(Math.toRadians(Aura.getYaw(ent) + 90.0f));
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(entx, ent.posY, entz, Aura.getYaw(ent), Aura.getPitch(ent), Tick.mc.thePlayer.onGround));
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(ent, C02PacketUseEntity.Action.ATTACK));
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Tick.mc.thePlayer.posX, Tick.mc.thePlayer.posY, Tick.mc.thePlayer.posZ, Tick.mc.thePlayer.onGround));
        }
    }

}

