package com.client.glowclient.sponge.mixin;

import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.audio.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer
{
    @Shadow
    protected Minecraft field_71159_c;
    @Shadow
    public int field_71157_e;
    @Shadow
    protected int field_71156_d;
    @Shadow
    public float field_71086_bY;
    @Shadow
    public float field_71080_cy;
    @Shadow
    private boolean field_184842_cm;
    @Shadow
    private EnumHand field_184843_cn;
    @Shadow
    private boolean field_184844_co;
    @Shadow
    private boolean field_189811_cr;
    @Shadow
    private int field_189812_cs;
    @Shadow
    private boolean field_189813_ct;
    @Shadow
    public MovementInput field_71158_b;
    @Shadow
    public NetHandlerPlayClient field_71174_a;
    @Shadow
    public float field_71154_f;
    @Shadow
    public float field_71155_g;
    @Shadow
    public float field_71163_h;
    @Shadow
    public float field_71164_i;
    @Shadow
    private int field_110320_a;
    @Shadow
    private float field_110321_bQ;
    @Shadow
    private boolean field_175171_bO;
    @Shadow
    private boolean field_175170_bN;
    @Shadow
    private double field_175172_bI;
    @Shadow
    private double field_175166_bJ;
    @Shadow
    private double field_175167_bK;
    @Shadow
    private float field_175164_bL;
    @Shadow
    private float field_175165_bM;
    @Shadow
    private int field_175168_bP;
    @Shadow
    private boolean field_184841_cd;
    
    public MixinEntityPlayerSP() {
        super((World)null, (GameProfile)null);
        this.autoJumpEnabled = true;
    }
    
    @Shadow
    public boolean isRidingHorse() {
        return false;
    }
    
    @Shadow
    protected void sendHorseJump() {
    }
    
    @Shadow
    public float getHorseJumpPower() {
        return 1.0f;
    }
    
    @Shadow
    protected boolean isCurrentViewEntity() {
        return true;
    }
    
    @Shadow
    private boolean isHeadspaceFree(final BlockPos blockPos, final int n) {
        return true;
    }
    
    @Shadow
    private boolean isOpenBlockSpace(final BlockPos blockPos) {
        return false;
    }
    
    @Overwrite
    public boolean isRowingBoat() {
        return false;
    }
    
    @Inject(method = { "pushOutOfBlocks" }, at = { @At("HEAD") }, cancellable = true)
    public void prePushOutOfBlocks(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v14) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Overwrite
    public void onLivingUpdate() {
        ++this.sprintingTicksLeft;
        if (this.sprintToggleTimer > 0) {
            --this.sprintToggleTimer;
        }
        this.prevTimeInPortal = this.timeInPortal;
        if (this.inPortal) {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
                if (this.mc.currentScreen instanceof GuiContainer) {
                    this.closeScreen();
                }
                this.mc.displayGuiScreen((GuiScreen)null);
            }
            if (this.timeInPortal == 0.0f) {
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRIGGER, this.rand.nextFloat() * 0.4f + 0.8f));
            }
            this.timeInPortal += 0.0125f;
            if (this.timeInPortal >= 1.0f) {
                this.timeInPortal = 1.0f;
            }
            this.inPortal = false;
        }
        else if (this.isPotionActive(MobEffects.NAUSEA) && this.getActivePotionEffect(MobEffects.NAUSEA).getDuration() > 60) {
            this.timeInPortal += 0.006666667f;
            if (this.timeInPortal > 1.0f) {
                this.timeInPortal = 1.0f;
            }
        }
        else {
            if (this.timeInPortal > 0.0f) {
                this.timeInPortal -= 0.05f;
            }
            if (this.timeInPortal < 0.0f) {
                this.timeInPortal = 0.0f;
            }
        }
        if (this.timeUntilPortal > 0) {
            --this.timeUntilPortal;
        }
        final boolean jump = this.movementInput.jump;
        final boolean sneak = this.movementInput.sneak;
        final boolean b = this.movementInput.moveForward >= 0.8f;
        this.movementInput.updatePlayerMoveState();
        this.mc.getTutorial().handleMovement(this.movementInput);
        if (this.isHandActive() && !this.isRiding()) {
            if (!HookTranslator.v18) {
                final MovementInput movementInput = this.movementInput;
                movementInput.moveStrafe *= 0.2f;
                final MovementInput movementInput2 = this.movementInput;
                movementInput2.moveForward *= 0.2f;
                this.sprintToggleTimer = 0;
            }
            else if (HookTranslator.m8()) {
                this.sprintToggleTimer = 0;
            }
        }
        boolean b2 = false;
        if (this.autoJumpTime > 0) {
            --this.autoJumpTime;
            b2 = true;
            this.movementInput.jump = true;
        }
        final PlayerSPPushOutOfBlocksEvent playerSPPushOutOfBlocksEvent = new PlayerSPPushOutOfBlocksEvent((EntityPlayer)this, this.getEntityBoundingBox());
        if (!MinecraftForge.EVENT_BUS.post((Event)playerSPPushOutOfBlocksEvent)) {
            final AxisAlignedBB entityBoundingBox = playerSPPushOutOfBlocksEvent.getEntityBoundingBox();
            this.pushOutOfBlocks(this.posX - this.width * 0.35, entityBoundingBox.minY + 0.5, this.posZ + this.width * 0.35);
            this.pushOutOfBlocks(this.posX - this.width * 0.35, entityBoundingBox.minY + 0.5, this.posZ - this.width * 0.35);
            this.pushOutOfBlocks(this.posX + this.width * 0.35, entityBoundingBox.minY + 0.5, this.posZ - this.width * 0.35);
            this.pushOutOfBlocks(this.posX + this.width * 0.35, entityBoundingBox.minY + 0.5, this.posZ + this.width * 0.35);
        }
        final boolean b3 = this.getFoodStats().getFoodLevel() > 6.0f || this.capabilities.allowFlying;
        if (this.onGround && !sneak && !b && this.movementInput.moveForward >= 0.8f && !this.isSprinting() && b3 && !this.isHandActive() && !this.isPotionActive(MobEffects.BLINDNESS)) {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.sprintToggleTimer = 7;
            }
            else {
                this.setSprinting(true);
            }
        }
        if (!this.isSprinting() && this.movementInput.moveForward >= 0.8f && b3 && this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.setSprinting(true);
        }
        if ((!HookTranslator.v18 || HookTranslator.m8()) && this.isHandActive()) {
            this.setSprinting(false);
        }
        if (this.isSprinting() && (this.movementInput.moveForward < 0.8f || this.collidedHorizontally || !b3)) {
            this.setSprinting(false);
        }
        if (this.capabilities.allowFlying) {
            if (this.mc.playerController.isSpectatorMode()) {
                if (!this.capabilities.isFlying) {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            }
            else if (!jump && this.movementInput.jump && !b2) {
                if (this.flyToggleTimer == 0) {
                    this.flyToggleTimer = 7;
                }
                else {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }
        if (this.movementInput.jump && !jump && !this.onGround && this.motionY < 0.0 && !this.isElytraFlying() && !this.capabilities.isFlying) {
            final ItemStack itemStackFromSlot = this.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            if (itemStackFromSlot.getItem() == Items.ELYTRA && ItemElytra.isUsable(itemStackFromSlot)) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        }
        this.wasFallFlying = this.isElytraFlying();
        if (this.capabilities.isFlying && this.isCurrentViewEntity()) {
            if (this.movementInput.sneak) {
                this.movementInput.moveStrafe /= (float)0.3;
                this.movementInput.moveForward /= (float)0.3;
                this.motionY -= this.capabilities.getFlySpeed() * 3.0f;
            }
            if (this.movementInput.jump) {
                this.motionY += this.capabilities.getFlySpeed() * 3.0f;
            }
        }
        if (this.isRidingHorse()) {
            final IJumpingMount jumpingMount = (IJumpingMount)this.getRidingEntity();
            if (this.horseJumpPowerCounter < 0) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter == 0) {
                    this.horseJumpPower = 0.0f;
                }
            }
            if (jump && !this.movementInput.jump) {
                this.horseJumpPowerCounter = -10;
                jumpingMount.setJumpPower(MathHelper.floor(this.getHorseJumpPower() * 100.0f));
                this.sendHorseJump();
            }
            else if (!jump && this.movementInput.jump) {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0f;
            }
            else if (jump) {
                ++this.horseJumpPowerCounter;
                if (this.horseJumpPowerCounter < 10) {
                    this.horseJumpPower = this.horseJumpPowerCounter * 0.1f;
                }
                else {
                    this.horseJumpPower = 0.8f + 2.0f / (this.horseJumpPowerCounter - 9) * 0.1f;
                }
            }
        }
        else {
            this.horseJumpPower = 0.0f;
        }
        super.onLivingUpdate();
        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
        if (HookTranslator.v22) {
            this.sprintToggleTimer = 0;
        }
    }
    
    @Overwrite
    private void onUpdateWalkingPlayer() {
        HookTranslator.m6();
        final boolean sprinting = this.isSprinting();
        if (sprinting != this.serverSprintState) {
            if (sprinting) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = sprinting;
        }
        final boolean sneaking = this.isSneaking();
        if (sneaking != this.serverSneakState) {
            if (sneaking) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = sneaking;
        }
        if (this.isCurrentViewEntity()) {
            final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
            final double n = this.posX - this.lastReportedPosX;
            final double n2 = entityBoundingBox.minY - this.lastReportedPosY;
            final double n3 = this.posZ - this.lastReportedPosZ;
            final double n4 = this.rotationYaw - this.lastReportedYaw;
            final double n5 = this.rotationPitch - this.lastReportedPitch;
            ++this.positionUpdateTicks;
            boolean b = n * n + n2 * n2 + n3 * n3 > 9.0E-4 || this.positionUpdateTicks >= 20;
            final boolean b2 = n4 != 0.0 || n5 != 0.0;
            if (this.isRiding()) {
                this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.motionX, -999.0, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                b = false;
            }
            else if (b && b2) {
                this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.posX, entityBoundingBox.minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
            }
            else if (b) {
                this.connection.sendPacket((Packet)new CPacketPlayer.Position(this.posX, entityBoundingBox.minY, this.posZ, this.onGround));
            }
            else if (b2) {
                this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, this.onGround));
            }
            else if (this.prevOnGround != this.onGround) {
                this.connection.sendPacket((Packet)new CPacketPlayer(this.onGround));
            }
            if (b) {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = entityBoundingBox.minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }
            if (b2) {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
            this.prevOnGround = this.onGround;
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
        }
        HookTranslator.m7();
    }
}
