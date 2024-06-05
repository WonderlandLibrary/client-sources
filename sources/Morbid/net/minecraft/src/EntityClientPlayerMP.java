package net.minecraft.src;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import me.enrythebest.reborn.cracked.util.*;

public class EntityClientPlayerMP extends EntityPlayerSP
{
    public NetClientHandler sendQueue;
    private double oldPosX;
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    public float oldRotationPitch;
    private boolean wasOnGround;
    private boolean shouldStopSneaking;
    private boolean wasSneaking;
    private int field_71168_co;
    private boolean hasSetHealth;
    
    public EntityClientPlayerMP(final Minecraft par1Minecraft, final World par2World, final Session par3Session, final NetClientHandler par4NetClientHandler) {
        super(par1Minecraft, par2World, par3Session, 0);
        this.wasOnGround = false;
        this.shouldStopSneaking = false;
        this.wasSneaking = false;
        this.field_71168_co = 0;
        this.hasSetHealth = false;
        this.sendQueue = par4NetClientHandler;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        return false;
    }
    
    @Override
    public void heal(final int par1) {
    }
    
    @Override
    public void onUpdate() {
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ))) {
            Morbid.getHookManager().onPreUpdate();
            super.onUpdate();
            this.sendMotionUpdates();
        }
    }
    
    public void sendMotionUpdates() {
        Morbid.getRotationManager().preUpdate();
        Morbid.getHookManager().onPreMotionUpdate();
        Morbid.getManager();
        if (!ModManager.getMod("freecam").isEnabled()) {
            final boolean var1 = this.isSprinting();
            if (var1 != this.wasSneaking) {
                if (var1) {
                    this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
                }
                else {
                    this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
                }
                this.wasSneaking = var1;
            }
            final boolean var2 = this.isSneaking();
            if (var2 != this.shouldStopSneaking) {
                if (var2) {
                    this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
                }
                else {
                    this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
                }
                this.shouldStopSneaking = var2;
            }
            final double var3 = this.posX - this.oldPosX;
            final double var4 = this.boundingBox.minY - this.oldMinY;
            final double var5 = this.posZ - this.oldPosZ;
            final double var6 = Morbid.getRotationManager().getYaw() - Morbid.getRotationManager().getOldYaw();
            final double var7 = Morbid.getRotationManager().getPitch() - Morbid.getRotationManager().getOldPitch();
            boolean var8 = var3 * var3 + var4 * var4 + var5 * var5 > 9.0E-4 || this.field_71168_co >= 20;
            final boolean var9 = var6 != 0.0 || var7 != 0.0;
            if (this.ridingEntity != null) {
                this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0, -999.0, this.motionZ, Morbid.getRotationManager().getYaw(), Morbid.getRotationManager().getPitch(), this.onGround));
                var8 = false;
            }
            else if (var8 && var9) {
                this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, Morbid.getRotationManager().getYaw(), Morbid.getRotationManager().getPitch(), this.onGround));
            }
            else if (var8) {
                this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
            }
            else if (var9) {
                this.sendQueue.addToSendQueue(new Packet12PlayerLook(Morbid.getRotationManager().getYaw(), Morbid.getRotationManager().getPitch(), this.onGround));
            }
            else {
                this.sendQueue.addToSendQueue(new Packet10Flying(this.onGround));
            }
            ++this.field_71168_co;
            this.wasOnGround = this.onGround;
            if (var8) {
                this.oldPosX = this.posX;
                this.oldMinY = this.boundingBox.minY;
                this.oldPosY = this.posY;
                this.oldPosZ = this.posZ;
                this.field_71168_co = 0;
            }
            if (var9) {
                Morbid.getRotationManager().postUpdate();
            }
            Morbid.getHookManager().onPostMotionUpdate();
        }
    }
    
    @Override
    public EntityItem dropOneItem(final boolean par1) {
        final int var2 = par1 ? 3 : 4;
        this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
        return null;
    }
    
    @Override
    protected void joinEntityItemWithWorld(final EntityItem par1EntityItem) {
    }
    
    public void sendChatMessage(final String par1Str) {
        Morbid.getManager();
        if (ModManager.getMod("vanilla").isEnabled()) {
            if (par1Str.equals("novanilla")) {
                Morbid.getManager();
                ModManager.getMod("vanilla").setEnabled(false);
            }
            else {
                MorbidHelper.sendPacket(new Packet3Chat(par1Str));
            }
        }
        else if (par1Str.equals(".say .")) {
            MorbidHelper.sendPacket(new Packet3Chat(".say ."));
        }
        else {
            Morbid.getHookManager().onCommandPased(par1Str);
        }
    }
    
    @Override
    public void swingItem() {
        super.swingItem();
        this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
    }
    
    @Override
    public void respawnPlayer() {
        this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
    }
    
    @Override
    protected void damageEntity(final DamageSource par1DamageSource, final int par2) {
        if (!this.isEntityInvulnerable()) {
            this.setEntityHealth(this.getHealth() - par2);
        }
    }
    
    @Override
    public void closeScreen() {
        this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
        this.func_92015_f();
    }
    
    public void func_92015_f() {
        this.inventory.setItemStack(null);
        super.closeScreen();
    }
    
    @Override
    public void setHealth(final int par1) {
        if (this.hasSetHealth) {
            super.setHealth(par1);
        }
        else {
            this.setEntityHealth(par1);
            this.hasSetHealth = true;
        }
    }
    
    @Override
    public void addStat(final StatBase par1StatBase, final int par2) {
        if (par1StatBase != null && par1StatBase.isIndependent) {
            super.addStat(par1StatBase, par2);
        }
    }
    
    public void incrementStat(final StatBase par1StatBase, final int par2) {
        if (par1StatBase != null && !par1StatBase.isIndependent) {
            super.addStat(par1StatBase, par2);
        }
    }
    
    @Override
    public void sendPlayerAbilities() {
        this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
    }
    
    @Override
    public boolean func_71066_bF() {
        return true;
    }
}
