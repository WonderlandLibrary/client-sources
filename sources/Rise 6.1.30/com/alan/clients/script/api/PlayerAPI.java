package com.alan.clients.script.api;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.ItemDamageComponent;
import com.alan.clients.component.impl.player.PacketlessDamageComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.script.api.wrapper.impl.ScriptEntity;
import com.alan.clients.script.api.wrapper.impl.ScriptEntityLiving;
import com.alan.clients.script.api.wrapper.impl.ScriptInventory;
import com.alan.clients.script.api.wrapper.impl.ScriptItemStack;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector2f;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector3d;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.player.DamageUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class PlayerAPI extends ScriptEntityLiving {

    public PlayerAPI() {
        super(MC.thePlayer);

        Client.INSTANCE.getEventBus().register(this);
    }

    public String getName() {
        return MC.getSession().getUsername();
    }

    public String getPlayerID() {
        return MC.getSession().getPlayerID();
    }

    public boolean isOnGround() {
        return MC.thePlayer.onGround;
    }

    public boolean isMoving() {
        return MoveUtil.isMoving();
    }

    public void jump() {
        MC.thePlayer.jump();
    }

    public void strafe() {
        MoveUtil.strafe();
    }

    public void strafe(final double speed) {
        MoveUtil.strafe(speed);
    }

    public float getForward() {
        return MC.thePlayer.moveForward;
    }

    public float getStrafe() {
        return MC.thePlayer.moveStrafing;
    }

    public double getSpeed(){
        return MoveUtil.speed();
    }

    public void stop() {
        MoveUtil.stop();
    }

    public void setPosition(final double posX, final double posY, final double posZ) {
        MC.thePlayer.setPosition(posX, posY, posZ);
    }

    public void setPosition(final ScriptVector3d vector) {
        this.setPosition(vector.getX(), vector.getY(), vector.getZ());
    }

    public void setMotion(final double motionX, final double motionY, final double motionZ) {
        MC.thePlayer.motionX = motionX;
        MC.thePlayer.motionY = motionY;
        MC.thePlayer.motionZ = motionZ;
    }

    public int getUseItemProgress() {
        return MC.thePlayer.getItemInUseDuration();
    }

    public void setMotionY(final double motionY) {
        MC.thePlayer.motionY = motionY;
    }

    public void setMotionX(final double motionX) {
        MC.thePlayer.motionX = motionX;
    }

    public void setMotionZ(final double motionZ) {
        MC.thePlayer.motionZ = motionZ;
    }

    public void setMotion(final ScriptVector3d vector) {
        this.setMotion(vector.getX(), vector.getY(), vector.getZ());
    }

    public void leftClick() {
        MC.clickMouse();
    }

    public void rightClick() {
        MC.rightClickMouse();
    }

    public void attackEntity(final ScriptEntityLiving target) {
        MC.playerController.attackEntity(MC.thePlayer, MC.theWorld.getEntityByID(target.getEntityId()));
    }

    public void swingItem() {
        MC.thePlayer.swingItem();
    }

    public void message(String message) {
        if (MC.thePlayer != null && MC.theWorld != null) MC.thePlayer.sendChatMessage(message);
    }

    public void setRotation(ScriptVector2f rotations, double rotationSpeed, boolean movementFix) {
        RotationComponent.setRotations(new Vector2f(rotations.getX(), rotations.getY()), rotationSpeed, movementFix ? MovementFix.NORMAL : MovementFix.OFF);
    }

    public void setHeldItem(int slot, boolean render) {
        Client.INSTANCE.getComponentManager().get(Slot.class).setSlot(slot);
    }

    public double[] getLerpedPosition() {
        return new double[] {MC.thePlayer.lastTickPosX + (MC.thePlayer.posX - MC.thePlayer.lastTickPosX) * MC.timer.renderPartialTicks, MC.thePlayer.lastTickPosY + (MC.thePlayer.posY - MC.thePlayer.lastTickPosY) * MC.timer.renderPartialTicks, MC.thePlayer.lastTickPosZ + (MC.thePlayer.posZ - MC.thePlayer.lastTickPosZ) * MC.timer.renderPartialTicks};
    }

    public void setSlot(int slot) {
        MC.thePlayer.inventory.currentItem = slot;
    }

    public void setHeldItem(int slot) {
        Client.INSTANCE.getComponentManager().get(Slot.class).setSlot(slot);
    }

    public ScriptItemStack getHeldItemStack() {
        return new ScriptItemStack(Client.INSTANCE.getComponentManager().get(Slot.class).getItemStack());
    }

    public ScriptItemStack getClientHeldItemStack() {
        return new ScriptItemStack(MC.thePlayer.getHeldItem());
    }

    public int getClientHeldItemSlot() {
        return MC.thePlayer.inventory.currentItem;
    }

    public void itemDamage() {
        ItemDamageComponent.damage(true);
    }

    public void damage(boolean packet, float timer) {
        if (!packet) PacketlessDamageComponent.setActive(timer);
        else DamageUtil.damagePlayer(0.5);
    }

    public int getHurtTime() {
        return MC.thePlayer.hurtTime;
    }

    public void damage(boolean packet) {
        damage(packet, 1);
    }

    public void fakeDamage() {
        PlayerUtil.fakeDamage();
    }

    public boolean isUsingItem() {
        return Minecraft.getMinecraft().thePlayer.isUsingItem();
    }

    public boolean isHoldingSword() {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        return itemStack != null && itemStack.getItem() instanceof ItemSword;
    }

    public boolean isHoldingTool() {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        return itemStack != null && itemStack.getItem() instanceof ItemTool;
    }

    public boolean isHoldingBlock() {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        return itemStack != null && itemStack.getItem() instanceof ItemBlock;
    }

    public boolean isHoldingFood() {
        ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        return itemStack != null && itemStack.getItem() instanceof ItemFood;
    }

    public ScriptVector2f calculateRotations(ScriptVector3d to) {
        Vector2f calculated = RotationUtil.calculate(new Vector3d(to.getX(), to.getY(), to.getZ()));
        return new ScriptVector2f(calculated.x, calculated.y);
    }

    public ScriptVector2f calculateRotations(ScriptEntity to) {
        ScriptVector3d toVec = to.getPosition();
        toVec.add(new ScriptVector3d(0, 1.8, 0));
        return calculateRotations(toVec);
    }

    public boolean mouseOverEntity(ScriptEntity entity, int range) {
        MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationComponent.rotations, range);

        if (movingObjectPosition == null || movingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY) {
            return false;
        }

        return movingObjectPosition.entityHit != null && movingObjectPosition.entityHit.getEntityId() == entity.getEntityId();
    }

    public ScriptInventory getInventory() {
        return new ScriptInventory(MC.thePlayer.inventory);
    }

    public double getBPS(){
        return MC.thePlayer.getDistance(MC.thePlayer.lastTickPosX,MC.thePlayer.posY,MC.thePlayer.lastTickPosZ) * 20;
    }

    public void setSprinting(boolean sprinting) {
        MC.thePlayer.setSprinting(sprinting);
    }

    public void setClientRotation(float yaw, float pitch) {
        MC.thePlayer.rotationYaw = yaw;
        MC.thePlayer.rotationPitch = pitch;
    }

    public float getFallDistance() {
        return MC.thePlayer.fallDistance;
    }

    public float getHunger() {
        return MC.thePlayer.getFoodStats().getFoodLevel();
    }

    public float getAbsorption() {
        return MC.thePlayer.getAbsorptionAmount();
    }

    public int getFacing() {
        return MC.thePlayer.getHorizontalFacing().getIndex();
    }

    public float getEyeHeight() {
        return MC.thePlayer.getEyeHeight();
    }

    public boolean isInWater() {
        return MC.thePlayer.isInWater();
    }

    public boolean isInLava() {
        return MC.thePlayer.isInLava();
    }

    public void setSneaking(boolean sneaking) {
        MC.thePlayer.setSneaking(sneaking);
    }

    public boolean isInWeb() {
        return MC.thePlayer.isInWeb;
    }

    public boolean isOnLadder() {
        return MC.thePlayer.isOnLadder();
    }

    public boolean isCollided() {
        return MC.thePlayer.isCollided;
    }

    public boolean isCollidedHorizontally() {
        return MC.thePlayer.isCollidedHorizontally;
    }

    public boolean isCollidedVertically() {
        return MC.thePlayer.isCollidedVertically;
    }

    public boolean isPotionActive(int potionid) {
        return MC.thePlayer.isPotionActive(potionid);
    }

    public void placeBlock(ScriptItemStack heldStack, ScriptVector3d blockPos, int side, ScriptVector3d hitVec){
        MC.playerController.onPlayerRightClick(MC.thePlayer, MC.theWorld, heldStack.getWrapped(),
                new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), EnumFacing.getFront(side),
                new Vec3(hitVec.getX(),hitVec.getY(),hitVec.getZ()));
    }

    public String getGUI() {
        if (MC.currentScreen == null) {
            return "none";
        } else if (MC.currentScreen instanceof GuiChest) {
            return "chest";
        } else if (MC.currentScreen instanceof RiseClickGUI) {
            return "clickgui";
        } else if (MC.currentScreen instanceof GuiChat) {
            return "chat";
        } else if (MC.currentScreen instanceof GuiInventory) {
            return "inventory";
        }

        return "undefined";
    }

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        if (this.wrapped == null || this.wrapped != MC.thePlayer) {
            this.wrapped = this.wrappedLiving = MC.thePlayer;
        }
    };
}
