// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat.aura;

import java.util.Iterator;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemSword;
import me.chrest.client.module.modules.movement.NoSlowdown;
import me.chrest.utils.RotationUtils;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.modules.combat.Aura;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.module.Module;
import me.chrest.utils.Timer;
import net.minecraft.entity.EntityLivingBase;

public class Single extends AuraMode
{
    private EntityLivingBase target;
    private Timer timer;
    
    public Single(final String name, final boolean value, final Module module) {
        super(name, value, module);
        this.timer = new Timer();
    }
    
    @Override
    public boolean enable() {
        this.target = null;
        return super.enable();
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event) && event.getState().equals(Event.State.PRE)) {
            final Aura auraModule = (Aura)this.getModule();
            if (ClientUtils.player().isEntityAlive()) {
                this.loadEntity();
                if (ClientUtils.mc().objectMouseOver.entityHit != null) {
                    final Entity hit = ClientUtils.mc().objectMouseOver.entityHit;
                    if (auraModule.isEntityValid(hit)) {
                        this.target = (EntityLivingBase)hit;
                    }
                }
                if (this.target != null) {
                    final double x = this.target.posX - ClientUtils.player().posX;
                    final double z = this.target.posZ - ClientUtils.player().posZ;
                    final double h = ClientUtils.y() + ClientUtils.player().getEyeHeight() - (this.target.posY + this.target.getEyeHeight());
                    final double h2 = Math.sqrt(x * x + z * z);
                    final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                    final float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
                    final double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
                    final double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
                    final double dist = Math.sqrt(xDist * xDist + yDist * yDist);
                    if (dist > auraModule.degrees) {
                        this.target = null;
                    }
                }
            }
            final NoSlowdown noSlowdownModule = (NoSlowdown)new NoSlowdown().getInstance();
            if (auraModule.autoblock && ClientUtils.player().getCurrentEquippedItem() != null && ClientUtils.player().getCurrentEquippedItem().getItem() instanceof ItemSword && ClientUtils.player().getDistanceToEntity(this.target) <= auraModule.range) {
                ClientUtils.playerController().sendUseItem(ClientUtils.player(), ClientUtils.world(), ClientUtils.player().getCurrentEquippedItem());
                if (!noSlowdownModule.isEnabled() && auraModule.noslowdown) {
                    ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
            }
            if (this.target != null) {
                final float[] rotations = RotationUtils.getRotations(this.target);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
        }
        if (super.onUpdate(event)) {
            final Event.State state = event.getState();
            event.getState();
            if (state == Event.State.POST) {
                final NoSlowdown noSlowdownModule2 = (NoSlowdown)new NoSlowdown().getInstance();
                final Aura auraModule2 = (Aura)this.getModule();
                if (ClientUtils.player().isBlocking()) {
                    ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                }
                if (this.target != null && this.timer.delay((float)(1000.0 / auraModule2.speed))) {
                    auraModule2.attack(this.target);
                    this.timer.reset();
                }
                if (ClientUtils.player().isBlocking()) {
                    ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                }
            }
        }
        return true;
    }
    
    private void loadEntity() {
        final Aura auraModule = (Aura)this.getModule();
        EntityLivingBase loadEntity = null;
        double distance = 2.147483647E9;
        for (final Entity e : ClientUtils.loadedEntityList()) {
            if (e instanceof EntityLivingBase) {
                if (!auraModule.isEntityValid(e)) {
                    continue;
                }
                final double x = e.posX - ClientUtils.player().posX;
                final double z = e.posZ - ClientUtils.player().posZ;
                final double h = ClientUtils.player().posY + ClientUtils.player().getEyeHeight() - (e.posY + e.getEyeHeight());
                final double h2 = Math.sqrt(x * x + z * z);
                final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                final float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
                final double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
                final double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
                final double angleDistance = Math.sqrt(xDist * xDist + yDist * yDist);
                if (angleDistance > auraModule.degrees) {
                    continue;
                }
                if (ClientUtils.player().getDistanceToEntity(e) >= distance) {
                    continue;
                }
                loadEntity = (EntityLivingBase)e;
                distance = ClientUtils.player().getDistanceToEntity(e);
            }
        }
        this.target = loadEntity;
    }
}
