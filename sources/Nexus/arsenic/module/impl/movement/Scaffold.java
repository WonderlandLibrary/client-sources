package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.*;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.minecraft.*;
import arsenic.utils.render.RenderUtils;
import arsenic.utils.rotations.RotationUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;


@ModuleInfo(name = "Scaffold", category = ModuleCategory.Movement)
public class Scaffold extends Module {
    int offGroundTicks, onGroundTicks;
    int blockCount;
    int ogslot;
    private BlockPos lastOverBlock;
    public final EnumProperty<sprintMode> mode = new EnumProperty<>("Sprint Mode: ", sprintMode.NONE);
    public final EnumProperty<rotationsMode> RotMode = new EnumProperty<>("Rotations: ", rotationsMode.EAGLE);
    public final EnumProperty<towerModes> towerMode = new EnumProperty<>("Tower: ", towerModes.NONE);
    public final DoubleProperty rps = new DoubleProperty("Rotation Speed", new DoubleValue(1, 180, 70, 1));
    public final DoubleProperty timerspeed = new DoubleProperty("Timer Max", new DoubleValue(1, 10, 1, 1));
    public final DoubleProperty timerspeed2 = new DoubleProperty("Timer Min", new DoubleValue(1, 10, 1, 1));
    public static BooleanProperty eagle = new BooleanProperty("Eagle", false);
    public static BooleanProperty fixLimit = new BooleanProperty("Fix Limit Flag", false);
    @PropertyInfo(reliesOn = "Fix Limit Flag", value = "true")
    public final EnumProperty<lMode> limitmode = new EnumProperty<>("Fix Limit: ", lMode.Sneak);
    public static BooleanProperty safewalk = new BooleanProperty("Safewalk", false);
    public static BooleanProperty snaprots = new BooleanProperty("Snap", false);
    public static BooleanProperty movefix = new BooleanProperty("MoveFix", false);
    public final EnumProperty<rMode> renderMode = new EnumProperty<>("Render Mode: ", rMode.Simple);
    float yaw, pitch;
    boolean right;
    int c08count;

    protected void onEnable() {
        c08count = 0;
        ogslot = mc.thePlayer.inventory.currentItem;
        right = false;
        mc.thePlayer.setSprinting(false);
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
        if (mc.thePlayer.isSprinting() && mode.getValue() == sprintMode.Vulcan) {
            PacketUtil.sendNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        }
    }

    ItemStack blockslot = null;
    @EventLink
    public final Listener<EventTick> eventTickListener = event -> {
        mc.timer.timerSpeed = (float) MathUtils.getRandomInRange(timerspeed.getValue().getInput(), timerspeed2.getValue().getInput());
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), PlayerUtils.isPlayerHoldingBlocks());
        // towers
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !MoveUtil.overAir(2)) {
            if (towerMode.getValue() == towerModes.NCP) {
                if (mc.thePlayer.posY % 1 <= 0.00153598) {
                    mc.thePlayer.setPosition(
                            mc.thePlayer.posX,
                            Math.floor(mc.thePlayer.posY),
                            mc.thePlayer.posZ
                    );
                    mc.thePlayer.motionY = 0.41998;
                } else if (mc.thePlayer.posY % 1 < 0.1 && offGroundTicks != 0) {
                    mc.thePlayer.setPosition(
                            mc.thePlayer.posX,
                            Math.floor(mc.thePlayer.posY),
                            mc.thePlayer.posZ
                    );
                }
            }
            if (towerMode.getValue() == towerModes.TP) {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = 0.41999998688698;
                } else if (mc.thePlayer.motionY < 0.23) {
                    mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
                }
            }
            if (towerMode.getValue() == towerModes.VULCAN) {
                if (offGroundTicks > 3) {
                    PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(null));
                    mc.thePlayer.onGround = true;
                    mc.thePlayer.motionY = MathUtils.getRandomInRange(0.47F, 0.50F);
                }
            }
        }
        // misc
        if (!PlayerUtils.isPlayerHoldingBlocks()) {
            for (int slot = 0; slot <= 8; slot++) {
                ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
                if (itemInSlot != null && itemInSlot.getItem() instanceof ItemBlock && !itemInSlot.getItem().getRegistryName().equalsIgnoreCase("minecraft:tnt") && !itemInSlot.getItem().getRegistryName().equalsIgnoreCase("minecraft:barrier") && (((ItemBlock) itemInSlot.getItem()).getBlock().isFullBlock() || ((ItemBlock) itemInSlot.getItem()).getBlock().isFullCube())) {
                    blockslot = mc.thePlayer.inventory.getStackInSlot(slot);
                    if (mc.thePlayer.inventory.currentItem != slot) {
                        mc.thePlayer.inventory.currentItem = slot;
                    }
                }
            }
        }
        if (eagle.getValue()) {
            setShift(PlayerUtils.playerOverAir() && mc.thePlayer.onGround || PlayerUtils.playerOverAir()); //shift on jump
        }
    };
    @EventLink
    public final Listener<EventSilentRotation> eventSilentRotationListener = event -> {
        if (!PlayerUtils.isPlayerInGame()) {
            return;
        }
        yaw = event.getYaw();
        pitch = event.getPitch();
        if (snaprots.getValue()) {
            if (!PlayerUtils.playerOverAir()) {
                event.setPitch(90);
                return;
            }
        }

        event.setSpeed((float) rps.getValue().getInput());
        event.setJumpFix(movefix.getValue());
        event.setDoMovementFix(false);

        if (RotMode.getValue() == rotationsMode.EAGLE) {
            //tower
            event.setYaw(mc.thePlayer.rotationYaw + 180F);
            if (mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0 && !PlayerUtils.playerOverAir()) {
                event.setPitch(90);
                return;
            }

            float pitchd = ((mc.thePlayer.rotationYaw % 90) / 45) * 4f;
            if (pitchd > 4) {
                pitchd = 8 - pitchd;
            }
            //Fix for negative values
            if (pitchd < 0) { //horrid code imo but atleast it works :p
                pitchd = Math.abs(pitchd);
                pitchd = 8 - pitchd;
                if (Math.abs(pitchd) > 5) {
                    pitchd = 0;
                }
            }

            event.setPitch(82 - pitchd);
        }
        if (RotMode.getValue() == rotationsMode.Simple) {
            if (lastOverBlock == null) {
                return;
            }
            final float[] rotations = RotationUtils.getDirectionToBlock(lastOverBlock.getX(), lastOverBlock.getY(), lastOverBlock.getZ(), EnumFacing.DOWN);
            event.setYaw(mc.thePlayer.rotationYaw + 180F);
            event.setPitch(rotations[1]);
        }

        if (RotMode.getValue() == rotationsMode.GODBRIDGE) {
            EnumFacing enumfacing = mc.thePlayer.getHorizontalFacing();
            //tower
            if (mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0 && !PlayerUtils.playerOverAir()) {
                event.setYaw(mc.thePlayer.rotationYaw + 180F);
                event.setPitch(90);
                return;
            }
            if (MoveUtil.isMoving()) {
                event.setPitch(79.6F);
                if (enumfacing.equals(EnumFacing.SOUTH)) {
                    event.setYaw(180);
                }
                if (enumfacing.equals(EnumFacing.NORTH)) {
                    event.setYaw(0);
                }
                if (enumfacing.equals(EnumFacing.EAST)) {
                    event.setYaw(90);
                }
                if (enumfacing.equals(EnumFacing.WEST)) {
                    event.setYaw(-90);
                }
            }
        }
    };

    @EventLink
    public final Listener<EventUpdate.Pre> eventUpdateListener = event -> {
        //sprint
        if (mode.getValue() == sprintMode.VANILLA || mode.getValue() == sprintMode.Vulcan || mode.getValue() == sprintMode.Hypixel) {
            mc.thePlayer.setSprinting(true);
        }
        if (mode.getValue() == sprintMode.Hypixel && MoveUtil.isMoving() && mc.thePlayer.isSprinting()) {
            mc.thePlayer.motionX *= 0.8;
            mc.thePlayer.motionZ *= 0.8;
        }
        // here
        if (fixLimit.getValue()) {
            if (PlayerUtils.playerOverAir()) {
                c08count++;
            }
            if (c08count == 10 && mc.thePlayer.onGround) {
                if (limitmode.getValue() == lMode.Sneak) {
                    setShift(true);
                }
                if (limitmode.getValue() == lMode.SneakPacket) {
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                    PacketUtil.send(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (limitmode.getValue() == lMode.Move) {

                }
                c08count = 0;
            } else {
                if (limitmode.getValue() == lMode.Sneak) {
                    setShift(false);
                }
            }
        }
        if (PlayerUtils.isPlayerInGame()) {
            if (mc.thePlayer.onGround) {
                offGroundTicks = 0;
                onGroundTicks++;
            } else {
                onGroundTicks = 0;
                offGroundTicks++;
            }
        }
    };


    @EventLink
    public final Listener<EventPacket.OutGoing> outGoingListener = e -> {
        if (mode.getValue() == sprintMode.Vulcan) {
            if (e.getPacket() instanceof C0BPacketEntityAction && ((C0BPacketEntityAction) e.getPacket()).getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
                e.setCancelled(true);
            }
        }
    };

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = e -> {
        FontRendererExtension<?> fr = Nexus.getNexus().getClickGuiScreen().getFontRenderer();
        final ScaledResolution scaledResolution = e.getSr();
        final int width = scaledResolution.getScaledWidth(), height = scaledResolution.getScaledHeight();
        if (renderMode.getValue() == rMode.Simple) {
            updateBlockCount();
            if (fr != null) {
                fr.drawString(
                        blockCount + " Blocks",
                        width / 1.95f,
                        (height / 2 + 20),
                        -1
                );
                mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.inventory.getCurrentItem(), (int) (scaledResolution.getScaledWidth() / 2F - 17F), height + 4);
            }
        }
    };
    @EventLink
    public final Listener<EventRenderWorldLast> renderWorldLast = event -> {
        if (lastOverBlock == null) {
            return;
        }
        RenderUtils.BlockESP(lastOverBlock, Nexus.getNexus().getThemeManager().getCurrentTheme().getMainColor(), true);
    };
    @EventLink
    public final Listener<EventMove> moveListener = event -> {
        lastOverBlock = PlayerUtils.getBestBlockPos();
        if (movefix.getValue()) {
            event.setYaw(yaw);
            float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(yaw) - MathHelper.wrapAngleTo180_float(MoveUtil.getStrafe())) + 22.5F;

            if (diff < 0) {
                diff = 360 + diff;
            }

            int a = (int) (diff / 45.0);

            //LogUtil.addChatMessage("Diff " + diff + " : Test " + a);

            float value = event.getForward() != 0 ? Math.abs(event.getForward()) : Math.abs(event.getStrafe());

            float forward = value;
            float strafe = 0;

            for (int i = 0; i < 8 - a; i++) {
                float dirs[] = MoveUtil.incrementMoveDirection(forward, strafe);

                forward = dirs[0];
                strafe = dirs[1];
            }

            event.setForward(forward);
            event.setStrafe(strafe);
        }
    };

    private void updateBlockCount() {
        blockCount = 0;
        for (int i = InventoryUtils.EXCLUDE_ARMOR_BEGIN; i < InventoryUtils.END; i++) {
            final ItemStack stack = InventoryUtils.getStackInSlot(i);

            if (stack != null && stack.getItem() instanceof ItemBlock &&
                    InventoryUtils.isGoodBlockStack(stack)) {
                blockCount += stack.stackSize;
            }
        }
    }

    protected void onDisable() {
        mc.thePlayer.inventory.currentItem = ogslot;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        setShift(false);
        mc.thePlayer.setSprinting(false);
        mc.timer.timerSpeed = 1.0F;
    }

    private void setShift(boolean sh) {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }

    public enum rotationsMode {
        EAGLE,
        GODBRIDGE,
        Simple
    }

    public enum towerModes {
        NONE,
        NCP,
        TP,
        VULCAN
    }

    public enum sprintMode {
        NONE,
        Vulcan,
        VANILLA,
        Hypixel
    }

    public enum rMode {
        Simple,
        NONE
    }

    public enum lMode {
        Sneak,
        SneakPacket,
        Move
    }

    public boolean mixinResult(boolean flag) {
        if (flag) {
            return true;
        }
        return mc.thePlayer.onGround && safewalk.getValue();
    }
}
