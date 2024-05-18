/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat.aura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.Aura;
import me.thekirkayt.client.module.modules.combat.aura.AuraMode;
import me.thekirkayt.client.module.modules.movement.NoSlowdown;
import me.thekirkayt.client.module.modules.movement.Speed;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventManager;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RotationUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Tick
extends AuraMode {
    private EntityLivingBase target;
    public static EntityLivingBase focusTarget;
    private boolean reBlock;
    private boolean unblocked;
    private Timer timer = new Timer();
    private Timer swapTargetTimer = new Timer();
    private Timer testTimer = new Timer();
    private UpdateEvent prevEvent;

    public Tick(String name, boolean value, Module module) {
        super(name, value, module);
        EventManager.register(this);
    }

    @Override
    public boolean enable() {
        if (super.enable() && ClientUtils.player() != null) {
            this.prevEvent = null;
            this.target = this.getTarget(true);
            this.reBlock = false;
        }
        return super.enable();
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event)) {
            Event.State state = event.getState();
            event.getState();
            if (state == Event.State.PRE) {
                Aura auraModule = (Aura)this.getModule();
                this.lowerTicks();
                if (this.shouldBlock(auraModule)) {
                    ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                    if (!new NoSlowdown().getInstance().isEnabled() && auraModule.noslowdown) {
                        ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                }
                if (this.target != null) {
                    float[] rotations = RotationUtils.getRotations(this.target);
                    event.setYaw(rotations[0]);
                    event.setPitch(rotations[1]);
                    this.prevEvent = event;
                }
                if (Timer.delay(auraModule.dura ? 440 : 240) && (this.getTarget(false) != null || this.isInvalid(this.target) || this.target.auraTicks <= 9)) {
                    this.target = this.getTarget(false);
                    this.swapTargetTimer.reset();
                }
            }
            Event.State state2 = event.getState();
            event.getState();
            if (state2 == Event.State.POST) {
                Aura auraModule2 = (Aura)this.getModule();
                if (this.reBlock) {
                    ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                    this.reBlock = false;
                }
                if (auraModule2.dura && !new Speed().getInstance().isEnabled() && this.prevEvent.getYaw() == RotationUtils.getRotations(this.target)[0]) {
                    if (this.target.auraTicks <= 10) {
                        if (ClientUtils.player().isBlocking()) {
                            C07PacketPlayerDigging dedicatedToTickDotJava = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
                            ClientUtils.packet(dedicatedToTickDotJava);
                            this.reBlock = true;
                        }
                        if ((double)this.target.getDistanceToEntity(ClientUtils.player()) <= 3.0) {
                            this.target.auraTicks = 100;
                            if (auraModule2.dura) {
                                this.testTimer.reset();
                                this.swap(9, ClientUtils.player().inventory.currentItem);
                                this.attack(this.target, false);
                                auraModule2.attack(this.target, false);
                                this.attack(this.target, true);
                                this.swap(9, ClientUtils.player().inventory.currentItem);
                                this.attack(this.target, false);
                                this.attack(this.target, true);
                            }
                        } else if ((double)this.target.getDistanceToEntity(ClientUtils.player()) <= 3.0) {
                            this.attack(this.target, false);
                            auraModule2.attack(this.target, false);
                            this.attack(this.target, true);
                        }
                        this.swapTargetTimer.reset();
                    }
                } else if (this.prevEvent != null && this.target.auraTicks <= 10 && this.prevEvent.getYaw() == RotationUtils.getRotations(this.target)[0]) {
                    if (ClientUtils.player().isBlocking()) {
                        C07PacketPlayerDigging dedicatedToTickDotJava = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
                        ClientUtils.packet(dedicatedToTickDotJava);
                        this.reBlock = true;
                    }
                    this.target.auraTicks = 20;
                    auraModule2.attack(this.target, false);
                    auraModule2.attack(this.target, false);
                    auraModule2.attack(this.target, false);
                    this.swapTargetTimer.reset();
                }
            }
        }
        return true;
    }

    private boolean shouldBlock(Aura auraModule) {
        if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
            double oldRange = auraModule.range;
            boolean nearbyEntitiesBlock = false;
            for (Entity entity : ClientUtils.loadedEntityList()) {
                if (!auraModule.isEntityValid(entity)) continue;
                auraModule.range = oldRange;
                return true;
            }
        }
        return false;
    }

    private boolean isInvalid(EntityLivingBase entity) {
        return entity == null || !((Aura)new Aura().getInstance()).isEntityValid(entity);
    }

    private void attack(EntityLivingBase ent, boolean crit) {
        Aura auraModule = (Aura)this.getModule();
        auraModule.swingItem();
        if (crit) {
            this.crit();
        }
        float sharpLevel = EnchantmentHelper.func_152377_a(ClientUtils.player().getHeldItem(), ent.getCreatureAttribute());
        boolean vanillaCrit = ClientUtils.player().fallDistance > 0.0f && !ClientUtils.player().onGround && !ClientUtils.player().isOnLadder() && !ClientUtils.player().isInWater() && !ClientUtils.player().isPotionActive(Potion.blindness) && ClientUtils.player().ridingEntity == null;
        ClientUtils.packet(new C02PacketUseEntity((Entity)ent, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            ClientUtils.player().onCriticalHit(ent);
        }
        if (sharpLevel > 0.0f) {
            ClientUtils.player().onEnchantmentCritical(ent);
        }
    }

    public void crit() {
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + 0.05, ClientUtils.player().posZ, false));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, false));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY + 0.012511, ClientUtils.player().posZ, false));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX, ClientUtils.player().posY, ClientUtils.player().posZ, false));
    }

    protected void swap(int slot, int hotbarNum) {
        ClientUtils.playerController().windowClick(ClientUtils.player().inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.player());
    }

    private EntityLivingBase getTarget(boolean startup) {
        ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        Aura auraModule = (Aura)this.getModule();
        if (startup) {
            for (int i = 0; i < 10; ++i) {
                this.lowerTicks();
            }
        }
        for (Entity ent : ClientUtils.loadedEntityList()) {
            if (!(ent instanceof EntityLivingBase)) continue;
            EntityLivingBase entity = (EntityLivingBase)ent;
            if (entity.auraTicks > 12 && entity.auraTicks > 13 || !auraModule.isEntityValid(entity)) continue;
            targets.add(entity);
        }
        Collections.sort(targets, new Comparator(){

            public int compare(EntityLivingBase ent1, EntityLivingBase ent2) {
                return ent1.auraTicks - ent2.auraTicks;
            }

            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
        if (targets.isEmpty()) {
            return null;
        }
        EntityLivingBase ent2 = (EntityLivingBase)targets.get(0);
        if (this.target != ent2) {
            Math.min(ent2.auraTicks, 12);
        }
        return ent2;
    }

    private void lowerTicks() {
        for (Entity ent : ClientUtils.loadedEntityList()) {
            EntityLivingBase entity;
            if (!(ent instanceof EntityLivingBase)) continue;
            EntityLivingBase entityLivingBase3 = entity = (EntityLivingBase)ent;
            EntityLivingBase entityLivingBase4 = entity;
            EntityLivingBase entityLivingBase2 = entity;
            --entityLivingBase4.auraTicks;
        }
    }

}

