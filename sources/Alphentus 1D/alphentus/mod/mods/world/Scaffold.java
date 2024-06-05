package alphentus.mod.mods.world;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.RandomUtil;
import alphentus.utils.RotationUtil;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 02.08.2020.
 */
public class Scaffold extends Mod {

    public Setting minDelay = new Setting("Minimal Delay", 0, 200, 0, true, this);
    public Setting maxDelay = new Setting("Maximal Delay", 0, 200, 0, true, this);
    public Setting sprint = new Setting("Sprint", true, this);
    public Setting sneak = new Setting("Sneak", false, this);
    public Setting safeWalk = new Setting("SafeWalk", false, this);
    public Setting intave = new Setting("Intave", false, this);
    public Setting otherRots = new Setting("Other Rotations", false, this);

    public float pitch;
    public float yaw;
    RandomUtil randomUtil = new RandomUtil();
    TimeUtil placeTimer = new TimeUtil();
    TimeUtil intaveTimer = new TimeUtil();

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_NONE, true, ModCategory.WORLD);

        Init.getInstance().settingManager.addSetting(minDelay);
        Init.getInstance().settingManager.addSetting(maxDelay);
        Init.getInstance().settingManager.addSetting(sneak);
        Init.getInstance().settingManager.addSetting(sprint);
        Init.getInstance().settingManager.addSetting(safeWalk);
        Init.getInstance().settingManager.addSetting(intave);
        Init.getInstance().settingManager.addSetting(otherRots);

    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;

        if (event.getType() == Type.PRE) {
            event.setYaw(yaw);
            event.setPitch(82);
        }

        if (event.getType() == Type.RENDER2D) {
            int blocksCount = 0;
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && isPlacableBlock(mc.thePlayer.inventory.getStackInSlot(i))) {
                    blocksCount += mc.thePlayer.inventory.getStackInSlot(i).stackSize;
                }
            }

            String text = "" + blocksCount;
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, scaledResolution.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2, scaledResolution.getScaledHeight() / 2 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * 2, Color.GREEN.darker().getRGB());
            blocksCount = 0;
        }

        if (event.getType() == Type.TICKUPDATE) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY - 1, mc.thePlayer.posZ);
            if (maxDelay.getCurrent() <= minDelay.getCurrent())
                maxDelay.setCurrent(minDelay.getCurrent() + 1);
            if (intave.isState()) {
                if (intaveTimer.isDelayComplete(200)) {
                    if (!otherRots.isState())
                        getYaw(pos);
                }
                if (!intaveTimer.isDelayComplete(500)) {
                    mc.thePlayer.setSprinting(false);
                }
            } else {
                if (!otherRots.isState())
                    getYaw(pos);
            }

            if (!sprint.isState()) {
                mc.gameSettings.keyBindSprint.pressed = false;
                mc.thePlayer.setSprinting(false);
            }
            if (mc.thePlayer.isMoving() || mc.thePlayer.motionY != 0)
                getBlockPosToPlaceOn(pos);
        }
    }

    public void placeBlock(BlockPos pos, EnumFacing face) {
        ItemStack silentItemStack = null;
        if (mc.thePlayer.getCurrentEquippedItem() == null || !(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock || !isPlacableBlock(mc.thePlayer.getCurrentEquippedItem()))) {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.getStackInSlot(i) != null && mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && isPlacableBlock(mc.thePlayer.inventory.getStackInSlot(i))) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
                    silentItemStack = mc.thePlayer.inventory.getStackInSlot(i);
                    if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)
                        mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    break;
                }
            }
        } else {
            if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)
                mc.thePlayer.swingItem();
            silentItemStack = (mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) ? mc.thePlayer.getCurrentEquippedItem() : null;
        }

        getPitch(pos);

        if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() instanceof BlockAir) {
            if (otherRots.isState())
                getYaw(pos);
            if (sneak.isState())
                mc.gameSettings.keyBindSneak.pressed = true;
            double finalDelay = mc.thePlayer.onGround ? randomUtil.randomDouble(minDelay.getCurrent() + 1, maxDelay.getCurrent() + 2) + 5 : 20;
            if (placeTimer.isDelayComplete(finalDelay) && (intaveTimer.isDelayComplete(750) || !intave.isState())) {
                mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, silentItemStack, pos, face, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
                placeTimer.reset();
            }
        } else {
            mc.gameSettings.keyBindSneak.pressed = false;
            placeTimer.reset();
        }

    }

    public void getBlockPosToPlaceOn(BlockPos pos) {

        if (mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(-1, 0, 0), EnumFacing.EAST);
        } else if (mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(1, 0, 0), EnumFacing.WEST);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0, -1), EnumFacing.SOUTH);
        } else if (mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, 0, 1), EnumFacing.NORTH);
        }

        // Fixing the placing while jumping (interact flags)
        else if (mc.theWorld.getBlockState(pos.add(-1, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(-1, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(1, -1, 0)).getBlock() != Blocks.air) {
            placeBlock(pos.add(1, -1, 0), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(0, -1, -1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, -1, -1), EnumFacing.UP);
        } else if (mc.theWorld.getBlockState(pos.add(0, -1, 1)).getBlock() != Blocks.air) {
            placeBlock(pos.add(0, -1, 1), EnumFacing.UP);
        }

    }

    public boolean isPlacableBlock(ItemStack is) {
        if (is == null || is.getItem().getUnlocalizedName().equals("tnt")
                || is.getItem().getUnlocalizedName().equals("web")
                || is.getItem().getUnlocalizedName().equals("chest")
                || is.getItem().getUnlocalizedName().equals("fence")
                || is.getItem().getUnlocalizedName().equals("ice"))
            return false;
        return true;
    }

    public void getYaw(BlockPos pos) {
        float[] rotations = RotationUtil.getFaceDirectionToBlockPos(pos, this.yaw, this.pitch);
        float yaw = 0;
        if (otherRots.isState()) {
            yaw = rotations[0];
            pitch = rotations[1];
        } else {
            if (mc.gameSettings.keyBindForward.isKeyDown())
                yaw = mc.thePlayer.rotationYaw + 180;
            if (mc.gameSettings.keyBindLeft.isKeyDown())
                yaw = mc.thePlayer.rotationYaw + 90;
            if (mc.gameSettings.keyBindRight.isKeyDown())
                yaw = mc.thePlayer.rotationYaw - 90;
            if (mc.gameSettings.keyBindBack.isKeyDown())
                yaw = mc.thePlayer.rotationYaw;

            if (mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindLeft.isKeyDown())
                yaw = mc.thePlayer.rotationYaw + 90 + (180 - 90) / 2;
            if (mc.gameSettings.keyBindForward.isKeyDown() && mc.gameSettings.keyBindRight.isKeyDown())
                yaw = mc.thePlayer.rotationYaw - 90 - (180 - 90) / 2;

            if (mc.gameSettings.keyBindBack.isKeyDown() && mc.gameSettings.keyBindLeft.isKeyDown())
                yaw = mc.thePlayer.rotationYaw + 90 - (180 - 90) / 2;
            if (mc.gameSettings.keyBindBack.isKeyDown() && mc.gameSettings.keyBindRight.isKeyDown())
                yaw = mc.thePlayer.rotationYaw - 90 + (180 - 90) / 2;

            if (!mc.thePlayer.isMoving())
                yaw = mc.thePlayer.rotationYaw + 180;
        }

        this.yaw = yaw;
    }

    public void getPitch(BlockPos pos) {
        float pitch;
        float[] rotations = RotationUtil.getFaceDirectionToBlockPos(pos, this.yaw, this.pitch);
        pitch = rotations[1];

        this.pitch = pitch;
    }

    @Override
    public void onEnable() {
        intaveTimer.reset();
        yaw = mc.thePlayer.rotationYaw;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        intaveTimer.reset();
        mc.gameSettings.keyBindSneak.pressed = false;
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        super.onDisable();
    }
}