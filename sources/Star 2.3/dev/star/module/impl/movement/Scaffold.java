package dev.star.module.impl.movement;

import dev.star.event.impl.game.TickEvent;
import dev.star.event.impl.network.PacketSendEvent;
import dev.star.event.impl.player.*;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.ParentAttribute;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.utils.animations.Animation;
import dev.star.utils.animations.Direction;
import dev.star.utils.animations.impl.DecelerateAnimation;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.player.*;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.render.RoundedUtil;
import dev.star.utils.server.PacketUtils;
import dev.star.utils.time.TimerUtil;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.IFontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Scaffold extends Module {

    private final ModeSetting Scaff_moode = new ModeSetting("Mode", "Star", "None", "Star", "Hypixel Semi", "Normal", "Jump");
    private final ModeSetting countMode = new ModeSetting("Block Counter", "Star", "None", "Star", "Basic");
    private final BooleanSetting rotations = new BooleanSetting("Rotations", true);
    private final NumberSetting minspeed = new NumberSetting("Min Rot Speed", 5, 20, 1, 1);
    private final NumberSetting maxspeed = new NumberSetting("Max Rot Speed", 7, 20, 1, 1);

    private final BooleanSetting Tower = new BooleanSetting("Tower", true);

    private final BooleanSetting autoSwap = new BooleanSetting("autoSwap", false);

    //public static NumberSetting extend = new NumberSetting("Extend", 0, 6, 0, 0.05);
    public static final BooleanSetting auto3rdPerson = new BooleanSetting("Auto 3rd Person", false);
    private ScaffoldUtils.BlockCache blockCache, lastBlockCache;
    private float y;
    private float speed;
    private final MouseFilter pitchMouseFilter = new MouseFilter();
    private final TimerUtil delayTimer = new TimerUtil();
    private final TimerUtil timerUtil = new TimerUtil();
    public static double keepYCoord;
    private boolean shouldSendPacket;
    private boolean shouldTower;
    private boolean firstJump;
    private boolean pre;
    private int jumpTimer;
    private int slot;
    private int prevSlot;
    private float[] cachedRots = new float[2];
    private float[] testrots = new float[2];

    private boolean forceStrict;
    public float placeYaw;
    public float placePitch;
    private double startPos = -1;
    private boolean down;
    private int add;
    private boolean delay;
    private boolean place;
    private boolean placedUp;
    public static double original;
    private int lastSlot;
    private MovingObjectPosition placeBlock;
    private boolean startedSprint;
    private int sprintTicks;
    private boolean pendingMovementStop;
    private double spoofedX, spoofedY, spoofedZ, oldY;
    private boolean wasHovering;
    private int ticksHovering;
    private int tickkstower;
    private boolean place_extra;
    private int inAirTicks;
    private int towerTicks;
    private boolean towering;
    private String oldMode;


    @Getter
    private float renderedYaw, renderedPitch;


    private final Animation anim = new DecelerateAnimation(250, 1);

    public Scaffold() {
        super("Scaffold", Category.MOVEMENT, "Automatically places blocks under you");

        this.addSettings(Scaff_moode,  countMode, rotations,  Tower, autoSwap, auto3rdPerson, maxspeed, minspeed );
    }

    public static boolean keysDown() {
        return Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
    }

    public static  boolean ShouldKeepY() {
        return keysDown();
    }
    public double groundDistance() {
        for (int i = 1; i <= 20; i++) {
            if (!mc.thePlayer.onGround && !(BlockUtils.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - (i / 10), mc.thePlayer.posZ)) instanceof BlockAir)) {
                return (i / 10);
            }
        }
        return -1;
    }



    public static boolean overPlaceable(double yOffset) {
        BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + yOffset, mc.thePlayer.posZ);
        return BlockUtils.replaceable(playerPos) || BlockUtils.isFluid(BlockUtils.getBlockAtPos(playerPos));
    }

    public boolean inBetween(float min, float max, float value) {
        return value >= min && value <= max;
    }


    private boolean forceStrict(float value) {
        return (inBetween(-170, -105, value) || inBetween(-80, 80, value) || inBetween(98, 170, value)) && !inBetween(-10, 10, value);
    }

    public static float getYaw() {
        float n = 0.0f;
        final double n2 = mc.thePlayer.movementInput.moveForward;
        final double n3 = mc.thePlayer.movementInput.moveStrafe;
        if (n2 == 0.0) {
            if (n3 == 0.0) {
                n = 180.0f;
            }
            else if (n3 > 0.0) {
                n = 90.0f;
            }
            else if (n3 < 0.0) {
                n = -90.0f;
            }
        }
        else if (n2 > 0.0) {
            if (n3 == 0.0) {
                n = 180.0f;
            }
            else if (n3 > 0.0) {
                n = 135.0f;
            }
            else if (n3 < 0.0) {
                n = -135.0f;
            }
        }
        else if (n2 < 0.0) {
            if (n3 == 0.0) {
                n = 0.0f;
            }
            else if (n3 > 0.0) {
                n = 45.0f;
            }
            else if (n3 < 0.0) {
                n = -45.0f;
            }
        }
        return mc.thePlayer.rotationYaw + n;
    }

    private int getSlot() {
        int slot = -1;
        int highestStack = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && !InventoryUtils.isBlockBlacklisted((ItemBlock) itemStack.getItem()) && itemStack.stackSize > 0) {
                if (mc.thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                    highestStack = mc.thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                }
            }
        }
        return slot;
    }

    private boolean place() {
        int slot = ScaffoldUtils.getBlockSlot();
        if (blockCache == null || lastBlockCache == null || slot == -1) return false;

        if (this.slot != slot) {
            this.slot = slot;
            PacketUtils.sendPacketNoEvent(new C09PacketHeldItemChange(this.slot));
        }

        boolean placed = false;
        if (delayTimer.hasTimeElapsed(0 * 1000)) {
            firstJump = false;
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(this.slot), lastBlockCache.getPosition(), lastBlockCache.getFacing(), ScaffoldUtils.getHypixelVec3(lastBlockCache))) {
                placed = true;


                y = 79;




                PacketUtils.sendPacket(new C0APacketAnimation());

            }
            delayTimer.reset();
            blockCache = null;
        }
        return placed;
    }

    private void place(MovingObjectPosition block, boolean extra) {
        ItemStack heldItem = mc.thePlayer.getHeldItem();
        if (heldItem == null || !(heldItem.getItem() instanceof ItemBlock)) {
            return;
        }
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, heldItem, block.getBlockPos(), block.sideHit, block.hitVec)) {

                mc.thePlayer.swingItem();
                mc.getItemRenderer().resetEquippedProgress();

        }
    }
    private void place1(BlockPos block, boolean extra) {
        ItemStack heldItem = mc.thePlayer.getHeldItem();
        if (heldItem == null || !(heldItem.getItem() instanceof ItemBlock)) {
            return;
        }
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, heldItem, block, EnumFacing.DOWN, new Vec3(block.getX(), block.getY(), block.getZ()))) {

            mc.thePlayer.swingItem();
            mc.getItemRenderer().resetEquippedProgress();
        }
    }



    void HypixelScaff()
    {
        blockCache = ScaffoldUtils.getBlockInfo();
        if (blockCache != null) {
            lastBlockCache = ScaffoldUtils.getBlockInfo();
        } else {
            return;
        }
        BlockPos test  = blockCache.getPosition();
        testrots = RotationUtils.getRotationsToPosition(test.getX(), test.getY() - 3,test.getZ() );


    }


    public Block blockRelativeToPlayer(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(offsetX, offsetY, offsetZ)).getBlock();
    }


    public static boolean jumpDown() {
        return Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
    }

    private boolean keepYPosition() {
        return this.isEnabled() && keysDown();
    }

    public Vec3 getPlacePossibility(double offsetY, double original) { // rise
        List<Vec3> possibilities = new ArrayList<>();
        int range = 5;
        for (int x = -range; x <= range; ++x) {
            for (int y = -range; y <= range; ++y) {
                for (int z = -range; z <= range; ++z) {
                    final Block block = blockRelativeToPlayer(x, y, z);
                    if (!block.getMaterial().isReplaceable()) {
                        for (int x2 = -1; x2 <= 1; x2 += 2) {
                            possibilities.add(new Vec3(mc.thePlayer.posX + x + x2, mc.thePlayer.posY + y, mc.thePlayer.posZ + z));
                        }
                        for (int y2 = -1; y2 <= 1; y2 += 2) {
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y + y2, mc.thePlayer.posZ + z));
                        }
                        for (int z2 = -1; z2 <= 1; z2 += 2) {
                            possibilities.add(new Vec3(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z + z2));
                        }
                    }
                }
            }
        }

        possibilities.removeIf(vec3 -> mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord) > 5);

        if (possibilities.isEmpty()) {
            return null;
        }
        possibilities.sort(Comparator.comparingDouble(vec3 -> {
            final double d0 = (mc.thePlayer.posX) - vec3.xCoord;
            final double d1 = ((keepYPosition() ? original : mc.thePlayer.posY) - 1 + offsetY) - vec3.yCoord;
            final double d2 = (mc.thePlayer.posZ) - vec3.zCoord;
            return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
        }));

        return possibilities.get(0);
    }

    static class EnumFacingOffset {
        EnumFacing enumFacing;
        Vec3 offset;

        EnumFacingOffset(EnumFacing enumFacing, Vec3 offset) {
            this.enumFacing = enumFacing;
            this.offset = offset;
        }

        EnumFacing getEnumFacing() {
            return enumFacing;
        }

        Vec3 getOffset() {
            return offset;
        }
    }

    public EnumFacingOffset getEnumFacing(final Vec3 position) {
        for (int x2 = -1; x2 <= 1; x2 += 2) {
            if (!BlockUtils.getBlock(new BlockPos(position.xCoord + x2, position.yCoord, position.zCoord)).getMaterial().isReplaceable()) {
                if (x2 > 0) {
                    return new EnumFacingOffset(EnumFacing.WEST, new Vec3(x2, 0, 0));
                } else {
                    return new EnumFacingOffset(EnumFacing.EAST, new Vec3(x2, 0, 0));
                }
            }
        }

        for (int y2 = -1; y2 <= 1; y2 += 2) {
            if (!BlockUtils.getBlock(new BlockPos(position.xCoord, position.yCoord + y2, position.zCoord)).getMaterial().isReplaceable()) {
                if (y2 < 0) {
                    return new EnumFacingOffset(EnumFacing.UP, new Vec3(0, y2, 0));
                }
            }
        }

        for (int z2 = -1; z2 <= 1; z2 += 2) {
            if (!BlockUtils.getBlock(new BlockPos(position.xCoord, position.yCoord, position.zCoord + z2)).getMaterial().isReplaceable()) {
                if (z2 < 0) {
                    return new EnumFacingOffset(EnumFacing.SOUTH, new Vec3(0, 0, z2));
                } else {
                    return new EnumFacingOffset(EnumFacing.NORTH, new Vec3(0, 0, z2));
                }
            }
        }

        return null;
    }


    public float[] generateSearchSequence(float value) {
        int length = (int) value * 2;
        float[] sequence = new float[length + 1];

        int index = 0;
        sequence[index++] = 0;

        for (int i = 1; i <= value; i++) {
            sequence[index++] = i;
            sequence[index++] = -i;
        }

        return sequence;
    }

    private static final Random rand = new Random();

    private boolean isDiagonal() {
        float yaw = ((mc.thePlayer.rotationYaw % 360) + 360) % 360 > 180 ? ((mc.thePlayer.rotationYaw % 360) + 360) % 360 - 360 : ((mc.thePlayer.rotationYaw % 360) + 360) % 360;
        return (yaw >= -170 && yaw <= 170) && !(yaw >= -10 && yaw <= 10) && !(yaw >= 80 && yaw <= 100) && !(yaw >= -100 && yaw <= -80) || Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()) || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
    }

    public static int randomizeInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    private double getRandom() {
        return randomizeInt(-90, 90) / 100.0;
    }

    void Starscaff() {
        if (!towering) {
            mc.thePlayer.setSprinting(true);


            final ItemStack heldItem = mc.thePlayer.getHeldItem();
            if (!autoSwap.isEnabled() || getSlot() == -1) {
                if (heldItem == null || !(heldItem.getItem() instanceof ItemBlock)) {
                    return;
                }
            }
            if (keepYPosition() && !down) {
                startPos = Math.floor(mc.thePlayer.posY);
                down = true;
            } else if (!keepYPosition()) {
                down = false;
                placedUp = false;
            }
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                add = 0;
            }
            double original = startPos;
            if (groundDistance() > 0 && mc.thePlayer.posY >= Math.floor(mc.thePlayer.posY) && mc.thePlayer.fallDistance > 0) {
                original++;
            }
            Vec3 targetVec3 = getPlacePossibility(0, original);
            if (targetVec3 == null) {
                return;
            }
            BlockPos targetPos = new BlockPos(targetVec3.xCoord, targetVec3.yCoord, targetVec3.zCoord);

            if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
                MovementUtils.setSpeed(MovementUtils.getHorizontalSpeed());
            }
            int slot = getSlot();
            if (slot == -1) {
                return;
            }
            if (lastSlot == -1) {
                lastSlot = mc.thePlayer.inventory.currentItem;
            }
            mc.thePlayer.inventory.currentItem = slot;
            if (heldItem == null || !(heldItem.getItem() instanceof ItemBlock)) {
                return;
            }
            MovingObjectPosition rayCasted = null;
            float searchYaw = 25;
            EnumFacingOffset enumFacing = getEnumFacing(targetVec3);
            if (enumFacing == null) {
                return;
            }
            targetPos = targetPos.add(enumFacing.getOffset().xCoord, enumFacing.getOffset().yCoord, enumFacing.getOffset().zCoord);
            float[] targetRotation = RotationUtils.getRotations(targetPos);
            float searchPitch[] = new float[]{78, 12};
            for (int i = 0; i < 2; i++) {
                if (i == 1 && rayCasted == null && overPlaceable(-1)) {
                    searchYaw = 180;
                    searchPitch = new float[]{65, 25};
                } else if (i == 1) {
                    break;
                }
                for (float checkYaw : generateSearchSequence(searchYaw)) {
                    float playerYaw = isDiagonal() ? getYaw() : targetRotation[0];
                    float fixedYaw = (float) (playerYaw - checkYaw + getRandom());
                    double deltaYaw = Math.abs(playerYaw - fixedYaw);
                    if (i == 1 && (inBetween(75, 95, (float) deltaYaw)) || deltaYaw > 500) {
                        continue;
                    }
                    for (float checkPitch : generateSearchSequence(searchPitch[1])) {
                        float fixedPitch = RotationUtils.clampTo90((float) (targetRotation[1] + checkPitch + getRandom()));
                        MovingObjectPosition raycast = RotationUtils.rayTraceCustom(mc.playerController.getBlockReachDistance(), fixedYaw, fixedPitch);
                        if (raycast != null) {
                            if (raycast.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                                if (raycast.getBlockPos().equals(targetPos) && raycast.sideHit == enumFacing.getEnumFacing()) {
                                    if (rayCasted == null || !BlockUtils.isSamePos(raycast.getBlockPos(), rayCasted.getBlockPos())) {
                                        if (((ItemBlock) heldItem.getItem()).canPlaceBlockOnSide(mc.theWorld, raycast.getBlockPos(), raycast.sideHit, mc.thePlayer, heldItem)) {
                                            if (rayCasted == null) {
                                                if ((forceStrict(checkYaw)) && i == 1) {
                                                    forceStrict = true;
                                                } else {
                                                    forceStrict = false;
                                                }
                                                rayCasted = raycast;
                                                placeYaw = fixedYaw;
                                                placePitch = fixedPitch;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (rayCasted != null) {
                    break;
                }
            }
            if (rayCasted != null) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                placeBlock = rayCasted;
                place(placeBlock, false);
                place = false;
                if (placeBlock.sideHit == EnumFacing.UP && keepYPosition()) {
                    placedUp = true;

                }
            }
        }
    }

    @Override
    public void onTickEvent(TickEvent event)
    {
        if (Scaff_moode.is("Hypixel Semi")) {
            HypixelScaff();
        }
        if (Scaff_moode.is("Normal")) {
            HypixelScaff();
        }
        if (Scaff_moode.is("Jump")) {
            HypixelScaff();
        }
    }



    @Override
    public void onUpdateEvent(UpdateEvent e) {

   if (Scaff_moode.is("Star"))
    {
        if (!towering) {
            oldMode = "";
            Starscaff();
        }
        else
        {
            oldMode = "Star";
            Scaff_moode.setCurrentMode("Normal");
        }

    }
    }

    @Override
    public void onJumpEvent(JumpEvent event) {

        if (Scaff_moode.is("Hypixel Semi") && startedSprint && wasHovering) {

            event.cancel();

        }


    }

    @Override
    public void onMoveEvent(MoveEvent e) {

        if  (Tower.isEnabled()) {

            if (MovementUtils.isMoving()  && !mc.thePlayer.isPotionActive(Potion.jump)) {
                if (mc.thePlayer.onGround) {
                    towering = mc.gameSettings.keyBindJump.pressed;

                    if (towering) {
                        towerTicks = 0;
                        mc.thePlayer.jumpTicks = 0;
                        if (e.getY() > 0) {
                          e.setY(mc.thePlayer.motionY = 0.4198);
                            mc.thePlayer.motionX *= 1.0f;
                            mc.thePlayer.motionZ *= 1.0f;

                        }
                    }
                } else
                {
                    if (towerTicks == 2) {
                        e.setY(Math.floor(mc.thePlayer.posY + 1) - mc.thePlayer.posY);
                        mc.thePlayer.motionX *= 1.0f;
                        mc.thePlayer.motionZ *= 1.0f;
                    }
                    else if (towerTicks == 3) {
                        towering = mc.gameSettings.keyBindJump.pressed;

                        if (towering) {
                           e.setY(mc.thePlayer.motionY = 0.4198);
                            mc.thePlayer.motionX *= 1.0f;
                            mc.thePlayer.motionZ *= 1.0f;
                            towerTicks = 0;
                        }
                    }
                }

                towerTicks++;
            } else {
                towering = false;
            }
        }
        if (Scaff_moode.is("Hypixel Semi")) {


            if (!startedSprint) {
                MovementUtils.strafe(e, 0.185);

                if (mc.thePlayer.onGround) {
                    if (sprintTicks == 0) {
                        MovementUtils.jump(e);
                        sprintTicks++;
                    } else {
                        startedSprint = true;
                        sprintTicks = 0;
                    }
                }

            } else {
                boolean diagonal = MovementUtils.isGoingDiagonally(0.12);


                if (mc.thePlayer.onGround && pendingMovementStop) {
                    MovementUtils.strafe(e, 0);
                    pendingMovementStop = false;
                } else {
                    ++sprintTicks;

                    double speed = 0.2728 - 1E-5 - Math.random() * 1E-5;

                    if (mc.thePlayer.onGround) {
                        //MovementUtil.strafe(speed - 0.1517);
                        MovementUtils.strafe(e, speed - 0.15);

                        MovementUtils.strafe(e, speed);
                    } else {
                        sprintTicks = 0;
                    }
                }
            }


        }
    }


    @Override
    public void onMotionEvent(MotionEvent e) {

        float[] rotationss = new float[]{0, 0};
        final float[] lastAngles = new float[]{mc.thePlayer.prevRotationYawHead, mc.thePlayer.prevRotationYawHead};

        if (rotations.isEnabled()) {
                float[] rotations = new float[]{0, 0};
                switch (Scaff_moode.getMode()) {
                    case "Star":
                        rotationss = RotationUtils.dosmoothrots(MovementUtils.getPlayerDirection() - 175, placePitch,minspeed.getValue().intValue(), maxspeed.getValue().intValue() );
                        RotationUtils.applyGCD(rotationss, lastAngles);
                        e.setRotations(rotationss[0], rotationss[1]);
                        RotationUtils.setVisualRotations(rotationss[0], rotationss[1]);
                        break;
                }

        }


        if (Scaff_moode.is("Normal")) {

            if (!towering) {
                if (oldMode.equals("Star")) {
                    Scaff_moode.setCurrentMode("Star");
                }
            }

            if (rotations.isEnabled()) {

                if (mc.gameSettings.keyBindJump.pressed)
                {
                    testrots[1] = 85;
                }
                rotationss = RotationUtils.dosmoothrots(MovementUtils.getPlayerDirection() - 170, testrots[1], minspeed.getValue().intValue(), maxspeed.getValue().intValue());
                RotationUtils.applyGCD(rotationss, lastAngles);
                e.setRotations(rotationss[0], rotationss[1]);
                RotationUtils.setVisualRotations(rotationss[0], rotationss[1]);
            }

            if (!towering) {
                mc.thePlayer.setSprinting(true);
                mc.thePlayer.motionX *= 0.86f;
                mc.thePlayer.motionZ *= 0.86f;
            }
            else
            {
                mc.thePlayer.setSprinting(true);
                mc.thePlayer.motionX *= 1.0f;
                mc.thePlayer.motionZ *= 1.0f;

            }
        }

        if (Scaff_moode.is("Jump")) {

            if (rotations.isEnabled()) {

                if (mc.gameSettings.keyBindJump.pressed)
                {
                    testrots[1] = 88;
                }
                rotationss = RotationUtils.dosmoothrots(MovementUtils.getPlayerDirection() - 170, 84, minspeed.getValue().intValue(), maxspeed.getValue().intValue() );
                RotationUtils.applyGCD(rotationss, lastAngles);
                e.setRotations(rotationss[0], rotationss[1]);
                RotationUtils.setVisualRotations(rotationss[0], rotationss[1]);
            }
            if (!towering) {
                if (e.isPre()) {
                    if (mc.thePlayer.onGround) {
                        if (MovementUtils.isMoving()) {
                            mc.thePlayer.jump();
                            MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.2);
                            inAirTicks = 0;
                        }
                    } else {


                        inAirTicks++;
                        if (inAirTicks == 8)
                            mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);

                    }
                }
            }
        }



        if (Scaff_moode.is("Hypixel Semi")) {

            if (rotations.isEnabled()) {

                rotationss = RotationUtils.dosmoothrots(MovementUtils.getPlayerDirection() - 180, 82,minspeed.getValue().intValue(), maxspeed.getValue().intValue() );
                RotationUtils.applyGCD(rotationss, lastAngles);
                e.setRotations(rotationss[0], rotationss[1]);
                RotationUtils.setVisualRotations(rotationss[0], rotationss[1]);
            }

            if (mc.thePlayer.onGround) {
                if (ticksHovering != 0 || !mc.gameSettings.keyBindJump.pressed) {
                    switch (++ticksHovering) {
                        case 1:
                            if (MovementUtils.isGoingDiagonally(0.1) && mc.thePlayer.isAirBorne) {
                                //LogUtil.addChatMessage("Test1");
                                ticksHovering = 0;
                                wasHovering = false;
                            } else {
                                e.setY(e.getY() + 0.0005);
                                e.setOnGround(false);
                                wasHovering = true;
                            }
                            break;
                        case 2:
                            //event.setX(spoofedX);
                            //event.setZ(spoofedZ);

                            ticksHovering = 0;
                            wasHovering = false;
                            break;
                    }
                }
            } else {
                ticksHovering = 0;
            }
        }



        // Timer Stuff
//        if (!mc.gameSettings.keyBindJump.isKeyDown()) {
//            mc.timer.timerSpeed = timer.getValue().floatValue();
//        } else {
//            mc.timer.timerSpeed = tower.isEnabled() ? towerTimer.getValue().floatValue() : 1;
//        }
//
//        if (e.isPre()) {
//            // Auto Jump
//            if (baseSpeed.isEnabled()) {
//                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 0.7);
//            }
//            if (autoJump.isEnabled() && mc.thePlayer.onGround && MovementUtils.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown()) {
//                mc.thePlayer.jump();
//            }
//
//            if (sprint.isEnabled() && sprintMode.is("Watchdog") && mc.thePlayer.onGround && MovementUtils.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown() && !isDownwards() && mc.thePlayer.isSprinting()) {
//                final double[] offset = MathUtils.yawPos(mc.thePlayer.getDirection(), MovementUtils.getSpeed() / 2);
//                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX - offset[0], mc.thePlayer.posY, mc.thePlayer.posZ - offset[1], true));
//            }
//
//            // Rotations
//            if (rotations.isEnabled()) {
//                float[] rotations = new float[]{0, 0};
//                switch (rotationMode.getMode()) {
//                    case "Watchdog":
//                        rotations = new float[]{MovementUtils.getMoveYaw(e.getYaw()) - 180, y};
//                        e.setRotations(rotations[0], rotations[1]);
//                        break;
//                    case "NCP":
//                        float prevYaw = cachedRots[0];
//                        if ((blockCache = ScaffoldUtils.getBlockInfo()) == null) {
//                            blockCache = lastBlockCache;
//                        }
//                        if (blockCache != null && (mc.thePlayer.ticksExisted % 3 == 0
//                                || mc.theWorld.getBlockState(new BlockPos(e.getX(), ScaffoldUtils.getYLevel(), e.getZ())).getBlock() == Blocks.air)) {
//                            cachedRots = RotationUtils.getRotations(blockCache.getPosition(), blockCache.getFacing());
//                        }
//                        if ((mc.thePlayer.onGround || (MovementUtils.isMoving() && tower.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown())) && Math.abs(cachedRots[0] - prevYaw) >= 90) {
//                            cachedRots[0] = MovementUtils.getMoveYaw(e.getYaw()) - 180;
//                        }
//                        rotations = cachedRots;
//                        e.setRotations(rotations[0], rotations[1]);
//                        break;
//                    case "Back":
//                        rotations = new float[]{MovementUtils.getMoveYaw(e.getYaw()) - 180, 77};
//                        e.setRotations(rotations[0], rotations[1]);
//                        break;
//                    case "Down":
//                        e.setPitch(90);
//                        break;
//                    case "45":
//                        float val;
//                        if (MovementUtils.isMoving()) {
//                            float f = MovementUtils.getMoveYaw(e.getYaw()) - 180;
//                            float[] numbers = new float[]{-135, -90, -45, 0, 45, 90, 135, 180};
//                            float lastDiff = 999;
//                            val = f;
//                            for (float v : numbers) {
//                                float diff = Math.abs(v - f);
//                                if (diff < lastDiff) {
//                                    lastDiff = diff;
//                                    val = v;
//                                }
//                            }
//                        } else {
//                            val = rotations[0];
//                        }
//                        rotations = new float[]{
//                                (val + MathHelper.wrapAngleTo180_float(mc.thePlayer.prevRotationYawHead)) / 2.0F,
//                                (77 + MathHelper.wrapAngleTo180_float(mc.thePlayer.prevRotationPitchHead)) / 2.0F};
//                        e.setRotations(rotations[0], rotations[1]);
//                        break;
//                    case "Enum":
//                        if (lastBlockCache != null) {
//                            float yaw = RotationUtils.getEnumRotations(lastBlockCache.getFacing());
//                            e.setRotations(yaw, 77);
//                        } else {
//                            e.setRotations(mc.thePlayer.rotationYaw + 180, 77);
//                        }
//                        break;
//                    case "0":
//                        e.setRotations(0, 0);
//                        break;
//                }
//                RotationUtils.setVisualRotations(e);
//            }
//
//            // Speed 2 Slowdown
//            if (speedSlowdown.isEnabled() && mc.thePlayer.isPotionActive(Potion.moveSpeed) && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround) {
//                MovementUtils.setSpeed(speedSlowdownAmount.getValue());
//            }
//
//            if (sneak.isEnabled()) KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
//
//            // Save ground Y level for keep Y
//            if (mc.thePlayer.onGround) {
//                keepYCoord = Math.floor(mc.thePlayer.posY - 1.0);
//            }
//
//            if (tower.isEnabled() && mc.gameSettings.keyBindJump.isKeyDown()) {
//                double centerX = Math.floor(e.getX()) + 0.5, centerZ = Math.floor(e.getZ()) + 0.5;
//                switch (towerMode.getMode()) {
//                    case "Vanilla":
//                        mc.thePlayer.motionY = 0.42f;
//                        break;
//                    case "Verus":
//                        if (mc.thePlayer.ticksExisted % 2 == 0)
//                            mc.thePlayer.motionY = 0.42f;
//                        break;
//                    case "Watchdog":
//                        if (!MovementUtils.isMoving() && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() != Blocks.air && lastBlockCache != null) {
//                            if (mc.thePlayer.ticksExisted % 6 == 0) {
//                                e.setX(centerX + 0.1);
//                                e.setZ(centerZ + 0.1);
//                            } else {
//                                e.setX(centerX - 0.1);
//                                e.setZ(centerZ - 0.1);
//                            }
//                            MovementUtils.setSpeed(0);
//                        }
//
//                        mc.thePlayer.motionY = 0.3;
//                        e.setOnGround(true);
//                        break;
//                    case "NCP":
//                        if (!MovementUtils.isMoving() || MovementUtils.getSpeed() < 0.16) {
//                            if (mc.thePlayer.onGround) {
//                                mc.thePlayer.motionY = 0.42;
//                            } else if (mc.thePlayer.motionY < 0.23) {
//                                mc.thePlayer.setPosition(mc.thePlayer.posX, (int) mc.thePlayer.posY, mc.thePlayer.posZ);
//                                mc.thePlayer.motionY = 0.42;
//                            }
//                        }
//                        break;
//                }
//            }
//
//            // Setting Block Cache
//            blockCache = ScaffoldUtils.getBlockInfo();
//            if (blockCache != null) {
//                lastBlockCache = ScaffoldUtils.getBlockInfo();
//            } else {
//                return;
//            }
//
//            if (mc.thePlayer.ticksExisted % 4 == 0) {
//                pre = true;
//            }
//
//            // Placing Blocks (Pre)
//            if (placeType.is("Pre") || (placeType.is("Dynamic") && pre)) {
//                if (place()) {
//                    pre = false;
//                }
//            }
//        } else {
//            // Setting Item Slot
//            if (!itemSpoof.isEnabled()) {
//                mc.thePlayer.inventory.currentItem = slot;
//            }
//
//            // Placing Blocks (Post)
//            if (placeType.is("Post") || (placeType.is("Dynamic") && !pre)) {
//                place();
//            }
//
//            pre = false;
//        }
    }


    @Override
    public void onBlockPlaceable(BlockPlaceableEvent event) {
        if (Scaff_moode.is("Hypixel semi") || Scaff_moode.is("Jump")|| Scaff_moode.is("Normal") ) {
            place();
        }

        if (Scaff_moode.is("Star"))
        {
            if (mc.gameSettings.keyBindJump.pressed)
            {
                place();
            }
        }
    }



    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.inventory.currentItem = prevSlot;


            if (auto3rdPerson.isEnabled()) {
                mc.gameSettings.thirdPersonView = 0;
            }
        }
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }

    @Override
    public void onEnable() {
        lastBlockCache = null;
        if (mc.thePlayer != null) {
            prevSlot = mc.thePlayer.inventory.currentItem;
            if (getSlot() != -1) {
                mc.thePlayer.inventory.currentItem = getSlot();
            }

            if (auto3rdPerson.isEnabled()) {
                mc.gameSettings.thirdPersonView = 1;
            }
        }
        wasHovering = false;
        oldMode = "";
        ticksHovering = 0;
        sprintTicks = 0;
        startedSprint = false;
        pendingMovementStop = false;
        firstJump = true;
        speed = 1.1f;
        timerUtil.reset();
        jumpTimer = 0;
        y = 80;
        super.onEnable();
    }

    public void renderCounterBlur() {
        if (!enabled && anim.isDone()) return;
        int slot = ScaffoldUtils.getBlockSlot();
        ItemStack heldItem = slot == -1 ? null : mc.thePlayer.inventory.mainInventory[slot];
        int count = slot == -1 ? 0 : ScaffoldUtils.getBlockCount();
        String countStr = String.valueOf(count);
        IFontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);
        int color;
        float x, y;
        String str = countStr + " block" + (count != 1 ? "s" : "");
        float output = anim.getOutput().floatValue();
        switch (countMode.getMode()) {
            case "Star":
                float blockWH = heldItem != null ? 15 : -2;
                int spacing = 3;
                String text = "§l" + countStr + "§r block" + (count != 1 ? "s" : "");
                float textWidth = Font18.getStringWidth(text);

                float totalWidth = ((textWidth + blockWH + spacing) + 6) * output;
                x = sr.getScaledWidth() / 2f - (totalWidth / 2f);
                y = sr.getScaledHeight() - (sr.getScaledHeight() / 2f - 20);
                float height = 20;
                RenderUtil.scissorStart(x - 1.5, y - 1.5, totalWidth + 3, height + 3);

                RoundedUtil.drawRound(x, y, totalWidth, height, 5, Color.BLACK);
                RenderUtil.scissorEnd();
                break;
            case "Basic":
                int blockCount = count; // Assuming `count` is your block count variable.
                String color1;

                if (blockCount >= 127) {
                    color1 = "§a"; // Minecraft color code for green
                } else if (blockCount >= 64) {
                    color1 = "§e"; // Minecraft color code for yellow
                } else {
                    color1 = "§c"; // Minecraft color code for red
                }

                // Construct the string with color
                String str1 = color1 + count;

                x = sr.getScaledWidth() / 2F - Font18.getStringWidth(str1) / 2F + 1;
                y = sr.getScaledHeight() / 2F + 10;
                RenderUtil.scaleStart(sr.getScaledWidth() / 2.0F, y + Font18.getHeight() / 2.0F, output);

                if (slot != -1) {
                    ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int) (x) - 17, (int) y - 4);
                }

                Font18.drawStringWithShadow(str1, x, y, -1);
                RenderUtil.scaleEnd();
                break;
        }
    }

    public void renderCounter() {
        anim.setDirection(enabled ? Direction.FORWARDS : Direction.BACKWARDS);
        if (!enabled && anim.isDone()) return;
        int slot = ScaffoldUtils.getBlockSlot();
        ItemStack heldItem = slot == -1 ? null : mc.thePlayer.inventory.mainInventory[slot];
        int count = slot == -1 ? 0 : ScaffoldUtils.getBlockCount();
        String countStr = String.valueOf(count);
        IFontRenderer fr = mc.fontRendererObj;
        ScaledResolution sr = new ScaledResolution(mc);
        int color;
        float x, y;
        String str = countStr + " block" + (count != 1 ? "s" : "");
        float output = anim.getOutput().floatValue();
        switch (countMode.getMode()) {
            case "Star":
                float blockWH = heldItem != null ? 15 : -2;
                int spacing = 3;
                String text = "§l" + countStr + "§r block" + (count != 1 ? "s" : "");
                float textWidth = Font18.getStringWidth(text);

                float totalWidth = ((textWidth + blockWH + spacing) + 6) * output;
                x = sr.getScaledWidth() / 2f - (totalWidth / 2f);
                y = sr.getScaledHeight() - (sr.getScaledHeight() / 2f - 20);
                float height = 20;
                RenderUtil.scissorStart(x - 1.5, y - 1.5, totalWidth + 3, height + 3);

                RoundedUtil.drawRound(x, y, totalWidth, height, 5, ColorUtil.tripleColor(20, .45f));

                Font18.drawString(text, x + 3 + blockWH + spacing, y + Font18.getMiddleOfBox(height) + .5f, -1);

                if (heldItem != null) {
                    RenderHelper.enableGUIStandardItemLighting();
                    mc.getRenderItem().renderItemAndEffectIntoGUI(heldItem, (int) x + 3, (int) (y + 10 - (blockWH / 2)));
                    RenderHelper.disableStandardItemLighting();
                }
                RenderUtil.scissorEnd();
                break;
            case "Basic":
                int blockCount = count; // Assuming `count` is your block count variable.
                String color1;

                if (blockCount >= 127) {
                    color1 = "§a"; // Minecraft color code for green
                } else if (blockCount >= 64) {
                    color1 = "§e"; // Minecraft color code for yellow
                } else {
                    color1 = "§c"; // Minecraft color code for red
                }

                // Construct the string with color
                String str1 = color1 + count;

                x = sr.getScaledWidth() / 2F - Font18.getStringWidth(str1) / 2F + 1;
                y = sr.getScaledHeight() / 2F + 10;
                RenderUtil.scaleStart(sr.getScaledWidth() / 2.0F, y + Font18.getHeight() / 2.0F, output);

                if (slot != -1) {
                    ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                    mc.getRenderItem().renderItemAndEffectIntoGUI(stack, (int) (x) - 17, (int) y - 4);
                }

                Font18.drawStringWithShadow(str1, x, y, -1);
                RenderUtil.scaleEnd();
                break;

        }
    }



}
