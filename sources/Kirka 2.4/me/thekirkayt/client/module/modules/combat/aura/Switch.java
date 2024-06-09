/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat.aura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.Aura;
import me.thekirkayt.client.module.modules.combat.aura.AuraMode;
import me.thekirkayt.client.module.modules.movement.NoSlowdown;
import me.thekirkayt.client.module.modules.movement.Speed;
import me.thekirkayt.client.module.modules.movement.speed.Bhop;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RotationUtils;
import me.thekirkayt.utils.StateManager;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class Switch
extends AuraMode {
    private boolean setupTick;
    private boolean switchingTargets;
    public static List<EntityLivingBase> targets;
    private int index;
    private Timer timer;
    public static Timer potTimer;
    private static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$event$Event$State;

    static {
        potTimer = new Timer();
    }

    public Switch(String name, boolean value, Module module) {
        super(name, value, module);
        targets = new ArrayList<EntityLivingBase>();
        this.timer = new Timer();
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event)) {
            switch (Switch.$SWITCH_TABLE$me$thekirkayt$event$Event$State()[event.getState().ordinal()]) {
                case 1: {
                    StateManager.setOffsetLastPacketAura(false);
                    Aura auraModule = (Aura)this.getModule();
                    NoSlowdown noSlowdownModule = (NoSlowdown)new NoSlowdown().getInstance();
                    if (Timer.delay(300.0f)) {
                        targets = this.getTargets();
                    }
                    if (this.index >= targets.size()) {
                        this.index = 0;
                    }
                    if (targets.size() > 0) {
                        EntityLivingBase target = targets.get(this.index);
                        if (target != null) {
                            if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                                ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                                if (!noSlowdownModule.isEnabled() && auraModule.noslowdown) {
                                    ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                                }
                            }
                            float[] rotations = RotationUtils.getRotations(target);
                            event.setYaw(rotations[0]);
                            event.setPitch(rotations[1]);
                        }
                        if (this.setupTick) {
                            if (targets.size() > 0 && ClientUtils.player().isCollidedVertically) {
                                StateManager.setOffsetLastPacketAura(true);
                                event.setY(event.getY() + 0.07);
                                event.setGround(false);
                            }
                            if (Timer.delay(300.0f)) {
                                this.incrementIndex();
                                this.switchingTargets = true;
                                this.timer.reset();
                            }
                        } else {
                            if (targets.size() > 0 && ClientUtils.player().isCollidedVertically) {
                                event.setGround(false);
                                event.setAlwaysSend(true);
                            }
                            if (ClientUtils.player().fallDistance > 0.0f && (double)ClientUtils.player().fallDistance < 0.66) {
                                event.setGround(true);
                            }
                        }
                    }
                    this.setupTick = !this.setupTick;
                    break;
                }
                case 2: {
                    Aura auraModule = (Aura)this.getModule();
                    if (!this.setupTick || targets.size() <= 0 || targets.get(this.index) == null || targets.size() <= 0) break;
                    EntityLivingBase target = targets.get(this.index);
                    if (ClientUtils.player().isBlocking()) {
                        ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                    for (int i = 0; i < 1; ++i) {
                        if (!Timer.delay(500.0f)) continue;
                        auraModule.attack(target, true);
                    }
                }
            }
        }
        return true;
    }

    private boolean bhopCheck() {
        if (new Speed().getInstance().isEnabled() && ((Boolean)((Speed)new Speed().getInstance()).bhop.getValue()).booleanValue()) {
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                return false;
            }
            Bhop.stage = -4;
        }
        return true;
    }

    private void incrementIndex() {
        ++this.index;
        if (this.index >= targets.size()) {
            this.index = 0;
        }
    }

    private List<EntityLivingBase> getTargets() {
        ArrayList<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (Entity entity : ClientUtils.loadedEntityList()) {
            if (!((Aura)this.getModule()).isEntityValid(entity)) continue;
            targets.add((EntityLivingBase)entity);
        }
        targets.sort(new Comparator<EntityLivingBase>(){

            @Override
            public int compare(EntityLivingBase target1, EntityLivingBase target2) {
                return Math.round(target2.getHealth() - target1.getHealth());
            }
        });
        return targets;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$me$thekirkayt$event$Event$State() {
        if ($SWITCH_TABLE$me$thekirkayt$event$Event$State != null) {
            int[] arrn;
            return arrn;
        }
        int[] arrn = new int[Event.State.values().length];
        try {
            arrn[Event.State.POST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Event.State.PRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$me$thekirkayt$event$Event$State = arrn;
        return $SWITCH_TABLE$me$thekirkayt$event$Event$State;
    }

}

