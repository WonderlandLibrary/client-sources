package me.finz0.osiris.module.modules.combat;

import java.util.List;

import de.Hero.settings.Setting;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.command.Command;
import me.finz0.osiris.friends.Friends;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.BlockInteractionHelper;
import me.finz0.osiris.util.EntityUtil;
import me.finz0.osiris.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class AutoWeb extends Module {
    public AutoWeb() {
        super("AutoWeb", Category.COMBAT, "Traps nearby players in webs");
    }

    Setting rotate;
    Setting debugMessages;
    Setting range;
    Setting bpt;
    Setting spoofRotations;
    Setting spoofHotbar;

    private final Vec3d[] offsetList = {  new Vec3d(0.0D, 1.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D) };
    private boolean slowModeSwitch = false;
    private int playerHotbarSlot = -1; private EntityPlayer closestTarget;
    private int lastHotbarSlot = -1;
    private int offsetStep = 0;
    int blocksPlaced;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(rotate = new Setting("Rotate", this, true, "AutoWebRotate"));
        AuroraMod.getInstance().settingsManager.rSetting(debugMessages = new Setting("DebugMessages", this, true, "AutoWebdebugMessages"));
        AuroraMod.getInstance().settingsManager.rSetting(spoofRotations = new Setting("SpoofRotations", this, true, "AutoWebSpoofRotations"));
        AuroraMod.getInstance().settingsManager.rSetting(spoofHotbar = new Setting("SpoofHotbar", this, true, "AutoWebSpoofHotbar"));

        AuroraMod.getInstance().settingsManager.rSetting(range = new Setting("Range", this, 5, 0, 10, false, "AutoWebRange"));
        AuroraMod.getInstance().settingsManager.rSetting(bpt = new Setting("BlocksPerTick", this, 8, 1, 15, true, "AutoWebBPT"));

    }

    public void onUpdate() {
        if (this.closestTarget == null) {
            return;
        }
        if (this.slowModeSwitch) {
            this.slowModeSwitch = false;
            return;
        }
        for (int i = 0; i < (int)Math.floor(((Double)this.bpt.getValDouble())); i++) {
            if (((Boolean)this.debugMessages.getValBoolean())) {
                Command.sendClientMessage("[WebAura] Loop iteration: " + this.offsetStep);
            }
            if (this.offsetStep >= this.offsetList.length) {
                endLoop();
                return;
            }
            Vec3d offset = this.offsetList[this.offsetStep];
            placeBlock((new BlockPos(this.closestTarget.getPositionVector())).down().add(offset.x, offset.y, offset.z));
            this.offsetStep++;
        }
        this.slowModeSwitch = true;
    }

    private void placeBlock(BlockPos blockPos) {
        if (!Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable()) {
            if (((Boolean)this.debugMessages.getValBoolean())) {
                Command.sendClientMessage("[WebAura] Block is already placed, skipping");
            }
            return;
        }
        if (!BlockInteractionHelper.checkForNeighbours(blockPos)) {
            return;
        }
        placeBlockExecute(blockPos);
    }

    public void placeBlockExecute(BlockPos pos) {
        Vec3d eyesPos = new Vec3d((Wrapper.getPlayer()).posX, (Wrapper.getPlayer()).posY + Wrapper.getPlayer().getEyeHeight(), (Wrapper.getPlayer()).posZ);
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!BlockInteractionHelper.canBeClicked(neighbor)) {
                if (((Boolean)this.debugMessages.getValBoolean())) {
                    Command.sendClientMessage("[WebAura] No neighbor to click at!");
                }
            } else {

                Vec3d hitVec = (new Vec3d(neighbor)).add(0.5D, 0.5D, 0.5D).add((new Vec3d(side2.getDirectionVec())).scale(0.5D));
                if (eyesPos.squareDistanceTo(hitVec) > 18.0625D) {
                    if (((Boolean)this.debugMessages.getValBoolean())) {
                        Command.sendClientMessage("[WebAura] Distance > 4.25 blocks!");
                    }
                } else {

                    if (((Boolean)this.spoofRotations.getValBoolean())) {
                        BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                    }
                    boolean needSneak = false;
                    Block blockBelow = mc.world.getBlockState(neighbor).getBlock();
                    if (BlockInteractionHelper.blackList.contains(blockBelow) || BlockInteractionHelper.shulkerList.contains(blockBelow)) {
                        if (((Boolean)this.debugMessages.getValBoolean())) {
                            Command.sendClientMessage("[WebAura] Sneak enabled!");
                        }
                        needSneak = true;
                    }
                    if (needSneak) {
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    int obiSlot = findObiInHotbar();
                    if (obiSlot == -1) {
                        if (((Boolean)this.debugMessages.getValBoolean())) {
                            Command.sendClientMessage("[WebAura] No Obi in Hotbar, disabling!");
                        }
                        disable();
                        return;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        if (((Boolean)this.debugMessages.getValBoolean())) {
                            Command.sendClientMessage("[WebAura] Setting Slot to Obi at  = " + obiSlot);
                        }
                        if (((Boolean)this.spoofHotbar.getValBoolean())) {
                            mc.player.connection.sendPacket(new CPacketHeldItemChange(obiSlot));
                        } else {

                            (Wrapper.getPlayer()).inventory.currentItem = obiSlot;
                        }
                        this.lastHotbarSlot = obiSlot;
                    }
                    mc.playerController.processRightClickBlock(Wrapper.getPlayer(), mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    if (needSneak) {
                        if (((Boolean)this.debugMessages.getValBoolean())) {
                            Command.sendClientMessage("[WebAura] Sneak disabled!");
                        }
                        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    return;
                }
            }
        }
    }

    private int findObiInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = (Wrapper.getPlayer()).inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY &&
                    stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof net.minecraft.block.BlockWeb) {
                    slot = i;

                    break;
                }
            }
        }
        return slot;
    }

    private void findTarget() {
        List<EntityPlayer> playerList = (Wrapper.getWorld()).playerEntities;
        for (EntityPlayer target : playerList) {
            if (target == mc.player) {
                continue;
            }
            if (Friends.isFriend(target.getName())) {
                continue;
            }
            if (!EntityUtil.isLiving(target)) {
                continue;
            }
            if (target.getHealth() <= 0.0F) {
                continue;
            }
            double currentDistance = Wrapper.getPlayer().getDistance(target);
            if (currentDistance > ((Double)this.range.getValDouble())) {
                continue;
            }
            if (this.closestTarget == null) {
                this.closestTarget = target;
                continue;
            }
            if (currentDistance >= Wrapper.getPlayer().getDistance(this.closestTarget)) {
                continue;
            }
            this.closestTarget = target;
        }
    }


    private void endLoop() {
        this.offsetStep = 0;
        if (((Boolean)this.debugMessages.getValBoolean())) {
            Command.sendClientMessage("[WebAura] Ending Loop");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (((Boolean)this.debugMessages.getValBoolean())) {
                Command.sendClientMessage("[WebAura] Setting Slot back to  = " + this.playerHotbarSlot);
            }
            if (((Boolean)this.debugMessages.getValBoolean())) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(this.playerHotbarSlot));
            } else {

                (Wrapper.getPlayer()).inventory.currentItem = this.playerHotbarSlot;
            }
            this.lastHotbarSlot = this.playerHotbarSlot;
        }
        findTarget();
    }


    protected void onEnable() {
        if (mc.player == null) {
            disable();
            return;
        }
        if (((Boolean)this.debugMessages.getValBoolean())) {
            Command.sendClientMessage("[WebAura] Enabling");
        }
        this.playerHotbarSlot = (Wrapper.getPlayer()).inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (((Boolean)this.debugMessages.getValBoolean())) {
            Command.sendClientMessage("[WebAura] Saving initial Slot  = " + this.playerHotbarSlot);
        }
        findTarget();
    }


    protected void onDisable() {
        if (mc.player == null) {
            return;
        }
        if (((Boolean)this.debugMessages.getValBoolean())) {
            Command.sendClientMessage("[WebAura] Disabling");
        }
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            if (((Boolean)this.debugMessages.getValBoolean())) {
                Command.sendClientMessage("[WebAura] Setting Slot to  = " + this.playerHotbarSlot);
            }
            if (((Boolean)this.spoofHotbar.getValBoolean())) {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(this.playerHotbarSlot));
            } else {

                (Wrapper.getPlayer()).inventory.currentItem = this.playerHotbarSlot;
            }
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
    }
}