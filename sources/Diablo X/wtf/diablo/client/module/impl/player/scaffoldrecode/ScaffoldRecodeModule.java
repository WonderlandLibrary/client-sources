package wtf.diablo.client.module.impl.player.scaffoldrecode;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.event.impl.player.RightClickEvent;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.event.impl.player.motion.MoveEvent;
import wtf.diablo.client.event.impl.player.motion.MoveFlyingEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.module.impl.player.scaffoldrecode.rotations.RotationsHandler;
import wtf.diablo.client.module.impl.player.scaffoldrecode.helpers.ScaffoldBlockHelper;
import wtf.diablo.client.module.impl.player.scaffoldrecode.modes.EnumSprintMode;
import wtf.diablo.client.module.impl.player.scaffoldrecode.modes.EnumSwingMode;
import wtf.diablo.client.module.impl.player.scaffoldrecode.modes.EnumTowerMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.mc.player.block.BlockUtils;
import wtf.diablo.client.util.mc.player.chat.ChatUtil;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

import java.security.Key;
import java.util.Objects;

@ModuleMetaData(
        name = "Scaffold",
        description = "Automatically places blocks under you",
        category = ModuleCategoryEnum.PLAYER
)
public final class ScaffoldRecodeModule extends AbstractModule {
    private AnchorData anchorData;
    private float yaw, pitch;
    private int slot, lastSlot;

    // swing
    private final ModeSetting<EnumSwingMode> enumSwingModeModeSetting = new ModeSetting<>("Swing Type", EnumSwingMode.CLIENT);


    //tower
    private final BooleanSetting tower = new BooleanSetting("Tower", false);
    private final ModeSetting<EnumTowerMode> enumTowerModeModeSetting = new ModeSetting<>("Tower Type", EnumTowerMode.WATCHDOG);

    // sprint
    private final BooleanSetting sprint = new BooleanSetting("Sprint", false);
    private final ModeSetting<EnumSprintMode> enumSprintModeModeSetting = new ModeSetting<>("Sprint Type", EnumSprintMode.WATCHDOG);

    private final BooleanSetting spoof = new BooleanSetting("Spoof", false);

    private final BooleanSetting keepYSetting = new BooleanSetting("Keep Y", false);
    private final BooleanSetting autoJump = new BooleanSetting("Auto Jump", false);

    private int ongroundticks = 0;
    private int offgroundticks = 0;

    private final double multi = 0.96;

    private double keepY;

    @Override
    protected void onEnable() {
        super.onEnable();

        this.slot = mc.thePlayer.inventory.currentItem;

        this.keepY = mc.thePlayer.posY;

        this.lastSlot = mc.thePlayer.inventory.currentItem;
    }

    @Override
    protected void onDisable() {
        super.onDisable();

        mc.thePlayer.inventory.currentItem = this.lastSlot;
        mc.getNetHandler().addToSendQueueNoEvent(new C09PacketHeldItemChange(this.lastSlot));
    }

    public ScaffoldRecodeModule() {
        this.registerSettings(
                this.enumSwingModeModeSetting,
                this.tower,
                this.enumTowerModeModeSetting,
                this.sprint,
                this.enumSprintModeModeSetting,
                this.spoof,
                this.keepYSetting,
                this.autoJump
        );
    }

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = event -> {
        // if event state is post return
        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        this.slot = BlockUtils.getBlockSlot();

        if (this.slot == -1) {
            return;
        }
        
        // the position to place the block on.
        final BlockPos blockPos = new BlockPos(mc.thePlayer.posX, keepY - 1, mc.thePlayer.posZ);

        // set the anchor data to the block data in the block helper class.
        this.anchorData = ScaffoldBlockHelper.getBlockAnchorData(blockPos);

        // we want to apply this before placing the block.
        this.applyMovementModifier();

        //set the sprint state before we place the block.
        this.performSprint();

        this.setKeepY();

        this.performAutoJump();

        // sets the held item to blockslot
        if (!this.spoof.getValue()) {
            this.setHeldItemSlotToBlockSlot();
        }

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            MovementUtil.setMotion(0.072515f);
        }

        if (!this.sprint.getValue())
            mc.thePlayer.setSprinting(false);

        // perform the tower
        this.performTower(event);

        if (mc.thePlayer.onGround) {
            this.ongroundticks++;
            this.offgroundticks = 0;
        } else {
            this.offgroundticks++;
            this.ongroundticks = 0;
        }

        // rotate.
        this.performRotations(event);

        if (event.getEventType() == EventTypeEnum.PRE) {
            // finally we place the block.
            this.performPlacingBlock();
        }
    };

    private boolean performedFirstPart;

    @EventHandler
    private final Listener<MoveFlyingEvent> moveFlyingEventListener = event -> {
        switch (enumTowerModeModeSetting.getValue()) {
            case WATCHDOG:
                break;
        }
    };

    private void performFirstPartOfTower() {
        double mx = mc.thePlayer.motionX;
        double mz = mc.thePlayer.motionZ;

        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;

        if (mc.thePlayer.onGround) {
            ongroundticks++;
            offgroundticks = 0;
        } else {
            offgroundticks++;
            ongroundticks = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.isKeyDown(Keyboard.KEY_W)) {
            mc.thePlayer.motionX = mx * multi;
            mc.thePlayer.motionZ = mz * multi;
            if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
                MovementUtil.setMotion(MovementUtil.getBaseMoveSpeed() * 0.2);
                mc.thePlayer.motionX = mx * multi;
                mc.thePlayer.motionZ = mz * multi;
                if (offgroundticks == 3) {
                    mc.thePlayer.motionX = mx * multi;
                    mc.thePlayer.motionY = -7.600;
                    mc.thePlayer.motionZ = mz * multi;

                    mc.thePlayer.setPosition(x, Math.floor(y), z);
                }

                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = MovementUtil.getJumpMotion();
                    mc.thePlayer.setPosition(x, Math.floor(y), z);
                }
            }
        }
    }

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = e -> {
        final ItemStack currentItem = mc.thePlayer.getHeldItem();

        int totalBlocks = 0;
        for (int i = 0; i < 9; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemBlock) {
                totalBlocks += stack.stackSize;
            }
        }

        if (totalBlocks == 0)
            return;

        final String text = totalBlocks + " blocks";

        final int x = e.getScaledResolution().getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(text) / 2 - 8;
        final int y = e.getScaledResolution().getScaledHeight() / 2 + 20;

        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableDepth();
        mc.getRenderItem().renderItemAndEffectIntoGUI(currentItem, x, y);
        GlStateManager.disableDepth();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();

        mc.fontRendererObj.drawStringWithShadow(text, x + 20, y + 4, -1);
    };

    private void performPlacingBlock() {
        if (this.slot == -1) {
            return;
        }

        if (this.slot != BlockUtils.getBlockSlot() && this.spoof.getValue()) {
            mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(BlockUtils.getBlockSlot()));
        }

        final ItemStack itemIn = this.spoof.getValue() ? mc.thePlayer.inventory.getStackInSlot(this.slot) : mc.thePlayer.getHeldItem();

        // if possible to place swing arm.
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemIn, this.anchorData.getBlockPos(), this.anchorData.getEnumFacing(), ScaffoldBlockHelper.getCorrectHitVector(this.anchorData))) {
            this.performSwinging();
        }
    }

    private void performSwinging() {
        // switch between the swing mode type's
        switch (this.enumSwingModeModeSetting.getValue()) {
            case CLIENT:
                // regularly swing
                mc.thePlayer.swingItem();
                break;
            case SERVER:
                // swing server sided.
                mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                break;
        }
    }

    private void performSprint() {
        if (this.sprint.getValue()) {
            switch (this.enumSprintModeModeSetting.getValue()) {
                case WATCHDOG:
                    if (MovementUtil.isMoving() && mc.thePlayer.onGround) {
                        mc.thePlayer.setSprinting(true);
                        mc.thePlayer.motionX *= MathUtil.getRandomFloat(0.85F, 0.875F);
                        mc.thePlayer.motionZ *= MathUtil.getRandomFloat(0.85F, 0.875F);
                    }
                    break;
                case VANILLA:
                    mc.thePlayer.setSprinting(true);
                    break;
            }
        }
    }

    private float[] performRotations(final MotionEvent event) {
        // these are spiny's but they are temporary for now.
        this.yaw = RotationsHandler.getMovementDirection(mc.thePlayer.rotationYaw) - 180;
        this.pitch = 84.0F;


        // finally set the yaw and pitch with event
        event.setYaw(yaw);
        event.setPitch(pitch);
        // set the player's rotation
        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.rotationPitchHead = pitch;
        mc.thePlayer.renderYawOffset = yaw;

        return new float[] {yaw, MathHelper.clamp_float(pitch, -90, 90)};
    }

    private float[] getBlockRotations(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos == null && enumFacing == null) {
            return null;
        } else {
            final Vec3 positionEyes = mc.thePlayer.getPositionEyes(2.0F);
            final Vec3 add = (new Vec3((double) Objects.requireNonNull(blockPos).getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D));
            final double n = add.xCoord - positionEyes.xCoord;
            final double n2 = add.yCoord - positionEyes.yCoord;
            final double n3 = add.zCoord - positionEyes.zCoord;
            return new float[]{(float) (Math.atan2(n3, n) * 180.0D / Math.PI - 90.0D), -((float) (Math.atan2(n2, (float) Math.hypot(n, n3)) * 180.0D / Math.PI))};
        }
    }

    private void performTower(final MotionEvent motionEvent) {
        if (motionEvent.getEventType() != EventTypeEnum.PRE)
            return;


        if (tower.getValue()) {
            switch (this.enumTowerModeModeSetting.getValue()) {
                case VANILLA:
                    // if space is held down make motion y 0.42f;
                    if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                        mc.thePlayer.motionY = 0.42f;
                    }
                    break;
                case WATCHDOG:
                    // perform watchdog tower.
                    this.performWatchdogTower();
                    break;
            }
        }
    }

    // vince made this
    private void performWatchdogTower() {
        double mx = mc.thePlayer.motionX;
        double mz = mc.thePlayer.motionZ;

        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;

        if (mc.thePlayer.onGround) {
            ongroundticks++;
            offgroundticks = 0;
        } else {
            offgroundticks++;
            ongroundticks = 0;
        }

        final double multi = 0.96;

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.isKeyDown(Keyboard.KEY_W)) {
            mc.thePlayer.motionX = mx * multi;
            mc.thePlayer.motionZ = mz * multi;
            if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
                mc.thePlayer.motionX = mx * multi;
                mc.thePlayer.motionZ = mz * multi;
                if (offgroundticks == 3) {
                    mc.thePlayer.motionX = mx * multi;
                    mc.thePlayer.motionY = -7.800;
                    mc.thePlayer.motionZ = mz * multi;

                    mc.thePlayer.setPosition(x, Math.floor(y), z);
                }

                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionY = MovementUtil.getJumpMotion();
                    mc.thePlayer.setPosition(x, Math.floor(y), z);
                }
            }
        }
    }

    private void applyMovementModifier() {
        mc.thePlayer.motionX *= 0.85;
        mc.thePlayer.motionZ *= 0.85;
    }

    private void setHeldItemSlotToBlockSlot() {
        mc.thePlayer.inventory.currentItem = this.slot;
    }

    private void setKeepY() {
        if (mc.thePlayer.fallDistance > 1 || mc.thePlayer.movementInput.jump || !this.keepYSetting.getValue()) {
            keepY = mc.thePlayer.posY;
        }
    }

    private void performAutoJump() {
        if (this.autoJump.getValue()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W) && mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
            MovementUtil.setMotion(0.18);
        }
    }

    public static final class AnchorData {
        private final BlockPos blockPos;
        private final EnumFacing enumFacing;

        public AnchorData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }

        public BlockPos getBlockPos() {
            return this.blockPos;
        }

        public EnumFacing getEnumFacing() {
            return this.enumFacing;
        }
    }
}