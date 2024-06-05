/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.CollideEvent;
import cc.diablo.event.impl.OverlayEvent;
import cc.diablo.event.impl.SafeWalkEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.MathHelper;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.helpers.module.ModuleData;
import cc.diablo.helpers.player.EntityHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

@ModuleData(name="Scaffold", category=Category.Player, description="become bob the builder")
public class Scaffold
extends Module {
    public ModeSetting mode = new ModeSetting("Rotations", "Watchdog", "Smooth", "Watchdog", "Legit", "Snap", "None");
    public ModeSetting placeMode = new ModeSetting("Place Mode", "Packet", "Packet", "Right Click");
    public BooleanSetting safeWalk = new BooleanSetting("Safe Walk", true);
    public NumberSetting timer = new NumberSetting("Timer", 1.0, 1.0, 5.0, 0.05);
    public BooleanSetting sprint = new BooleanSetting("Sprint", false);
    public NumberSetting delayMin = new NumberSetting("Delay Min", 75.0, 0.0, 500.0, 1.0);
    public NumberSetting delayMax = new NumberSetting("Delay Max", 100.0, 0.0, 500.0, 1.0);
    public NumberSetting minRot = new NumberSetting("Minimum Rotation", 5.0, 0.0, 15.0, 0.2);
    public NumberSetting maxRot = new NumberSetting("Maximum Rotation", 5.0, 0.0, 15.0, 0.2);
    public BooleanSetting noAura = new BooleanSetting("No Aura", false);
    public BooleanSetting autoJump = new BooleanSetting("Auto Jump", false);
    public BooleanSetting collide = new BooleanSetting("Collide", false);
    public static BlockDataOld data = null;
    private final List<Block> validBlocks;
    private final List<Block> invalidBlocks;
    private final BlockPos[] blockPositions;
    private final EnumFacing[] facings;
    private int slot = -1;
    public float yaw;
    public float pitch;
    private Stopwatch stopwatch = new Stopwatch();
    double keepY;

    public Scaffold() {
        this.invalidBlocks = Arrays.asList(Blocks.redstone_wire, Blocks.tallgrass, Blocks.redstone_torch, Blocks.enchanting_table, Blocks.furnace, Blocks.carpet, Blocks.crafting_table, Blocks.trapped_chest, Blocks.chest, Blocks.dispenser, Blocks.air, Blocks.water, Blocks.lava, Blocks.flowing_water, Blocks.flowing_lava, Blocks.sand, Blocks.snow_layer, Blocks.torch, Blocks.anvil, Blocks.jukebox, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.noteblock, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.wooden_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.yellow_flower, Blocks.red_flower, Blocks.anvil, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.cactus, Blocks.ladder, Blocks.web, Blocks.gravel, Blocks.tnt);
        this.validBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);
        this.blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)};
        this.facings = new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH};
        this.addSettings(this.mode, this.placeMode, this.safeWalk, this.timer, this.sprint, this.delayMin, this.delayMax, this.minRot, this.maxRot, this.autoJump, this.collide);
    }

    @Override
    public void onEnable() {
        this.keepY = Scaffold.mc.thePlayer.posY - 1.0;
        this.stopwatch.reset();
        this.slot = Scaffold.mc.thePlayer.inventory.currentItem;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Scaffold.mc.gameSettings.keyBindUseItem.pressed = false;
        if (Timer.timerSpeed != 1.0f) {
            Timer.timerSpeed = 1.0f;
        }
        Scaffold.mc.thePlayer.inventory.currentItem = this.slot;
        super.onDisable();
    }

    @Subscribe
    public void onSafewalk(SafeWalkEvent e) {
        if (Scaffold.mc.thePlayer.onGround && this.safeWalk.isChecked()) {
            e.setWalkSafely(true);
        }
    }

    @Subscribe
    public void onOverlay(OverlayEvent event) {
        boolean render = false;
        if (Scaffold.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
            ScaledResolution sr = new ScaledResolution(mc);
            int width = 60;
            int height = 15;
            int x = (int)((double)(sr.getScaledWidth() / 8 * 5) - (double)width * 1.4);
            int y = sr.getScaledHeight() / 6 * 4 - height;
            int blocks = 0;
            for (int index = 36; index < 45; ++index) {
                ItemStack itemStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
                if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || ((ItemBlock)itemStack.getItem()).getBlock() instanceof BlockFalling) continue;
                blocks += itemStack.stackSize;
                render = true;
            }
            if (render) {
                RenderUtils.drawRect(x - 1, y - 3, x + 51 + width, y + height + 1, RenderUtils.transparency(new Color(37, 37, 37).getRGB(), 0.6f));
                RenderUtils.drawRect(x, y, x + 50 + width, y + height, RenderUtils.transparency(new Color(45, 45, 45, 255).getRGB(), 0.8f));
                RenderUtils.drawRect(x, y - 2, x + 50 + width, y, RenderUtils.transparency(ColorHelper.getColor(0), 0.8f));
                Scaffold.mc.fontRendererObj.drawStringWithShadow(Scaffold.mc.thePlayer.getHeldItem().getDisplayName(), x + 3, y + 3, -1);
                Scaffold.mc.fontRendererObj.drawStringWithShadow(String.valueOf(blocks), x + 47 + width - Scaffold.mc.fontRendererObj.getStringWidth(String.valueOf(blocks)), y + 3, -1);
            }
        }
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (Scaffold.mc.gameSettings.keyBindJump.pressed && !Scaffold.mc.thePlayer.isMoving()) {
            this.keepY = Scaffold.mc.thePlayer.posY - 1.0;
        }
        data = this.getBlockDataOld2(new BlockPos(Scaffold.mc.thePlayer.posX, this.keepY, Scaffold.mc.thePlayer.posZ));
        this.setDisplayName(this.getName() + "\u00a77 " + this.mode.getMode());
        WorldClient world = Minecraft.theWorld;
        EntityPlayerSP player = Scaffold.mc.thePlayer;
        Timer.timerSpeed = (float)this.timer.getVal();
        Vec3 hitVec = this.getVec3(data);
        Scaffold.mc.thePlayer.setSprinting(this.sprint.isChecked());
        double yDif = 1.0;
        if (Scaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed) && Scaffold.mc.thePlayer.onGround) {
            EntityHelper.setMotion(0.08f);
        }
        int slot = -1;
        int blockCount = 0;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (itemStack == null) continue;
            int stackSize = itemStack.stackSize;
            if (!this.isValidItem(itemStack.getItem()) || stackSize <= blockCount) continue;
            blockCount = stackSize;
            slot = i;
        }
        if (Scaffold.mc.thePlayer.onGround && this.autoJump.isChecked() && Scaffold.mc.thePlayer.isMoving()) {
            Scaffold.mc.thePlayer.jump();
            Scaffold.mc.thePlayer.motionY = 0.4199996888637543;
        }
        int randomInt = MathHelper.getRandInt((int)this.delayMin.getVal(), (int)this.delayMax.getVal());
        if (e.isPre()) {
            float[] rotations = this.getBlockRotations(Scaffold.data.pos, Scaffold.data.face);
            switch (this.mode.getMode()) {
                case "Watchdog": {
                    KillAuraHelper.setRotations(e, (float)MathHelper.round(rotations[0], 39), rotations[1] - 0.7f - MathHelper.getRandomInRange((int)this.minRot.getVal(), (int)this.maxRot.getVal()));
                    break;
                }
                case "Smooth": {
                    KillAuraHelper.setRotations(e, rotations[0], rotations[1]);
                    break;
                }
                case "Legit": {
                    KillAuraHelper.setRotations(e, rotations[0], rotations[1] - 0.7f - MathHelper.getRandomInRange((int)this.minRot.getVal(), (int)this.maxRot.getVal()));
                    break;
                }
                case "Snap": {
                    KillAuraHelper.setRotations(e, this.yaw, this.pitch);
                }
            }
            if (data != null && slot != -1 && this.stopwatch.hasReached(randomInt)) {
                randomInt = MathHelper.getRandInt((int)this.delayMin.getVal(), (int)this.delayMax.getVal());
                player.inventory.currentItem = slot;
                if (this.placeMode.isMode("Right Click")) {
                    if (Scaffold.mc.playerController.onPlayerRightClick(player, world, player.getCurrentEquippedItem(), Scaffold.data.pos, Scaffold.data.face, hitVec)) {
                        this.yaw = (float)MathHelper.round(rotations[0], 37);
                        this.pitch = rotations[1] - 0.7f;
                        player.swingItem();
                    }
                } else if (this.placeMode.isMode("Packet")) {
                    // empty if block
                }
                this.stopwatch.reset();
            }
        }
    }

    @Subscribe
    public void onCollide(CollideEvent e) {
        double x = e.getX();
        double y = e.getY();
        double z = e.getZ();
        if (this.collide.isChecked() && y < Scaffold.mc.thePlayer.posY) {
            e.setBoundingBox(AxisAlignedBB.fromBounds(15.0, 1.0, 15.0, -15.0, -1.0, -15.0).offset(x, y, z));
        }
    }

    public static double getDirection() {
        float rotationYaw = Scaffold.mc.thePlayer.rotationYaw;
        if (Scaffold.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (Scaffold.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Scaffold.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Scaffold.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (Scaffold.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return rotationYaw;
    }

    private float[] getBlockRotations(BlockPos blockPos, EnumFacing enumFacing) {
        if (blockPos == null && enumFacing == null) {
            return null;
        }
        Vec3 positionEyes = Scaffold.mc.thePlayer.getPositionEyes(2.0f);
        Vec3 add = new Vec3((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
        double n = add.xCoord - positionEyes.xCoord;
        double n2 = add.yCoord - positionEyes.yCoord;
        double n3 = add.zCoord - positionEyes.zCoord;
        return new float[]{(float)(Math.atan2(n3, n) * 180.0 / Math.PI - 90.0), -((float)(Math.atan2(n2, (float)Math.hypot(n, n3)) * 180.0 / Math.PI))};
    }

    private Vec3 getVec3(BlockDataOld data) {
        BlockPos pos = data.pos;
        EnumFacing face = data.face;
        double x = (float)pos.getX() + 0.5f;
        double y = (float)pos.getY() + 0.5f;
        double z = (float)pos.getZ() + 0.5f;
        x += (double)face.getFrontOffsetX() / 2.0;
        z += (double)face.getFrontOffsetZ() / 2.0;
        y += (double)face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += this.randomNumber(0.3, -0.3);
            z += this.randomNumber(0.3, -0.3);
        } else {
            y += this.randomNumber(0.49, 0.5);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private BlockDataOld getBlockDataOld2(BlockPos pos) {
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos1 = pos.add(-1, 0, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos2 = pos.add(1, 0, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos3 = pos.add(0, 0, 1);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos4 = pos.add(0, 0, -1);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos19 = pos.add(-2, 0, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos1.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos2.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos3.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos4.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos5 = pos.add(0, -1, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos5.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos5.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos5.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos5.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos5.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos6 = pos5.add(1, 0, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos6.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos6.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos6.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos6.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos6.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos7 = pos5.add(-1, 0, 0);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos7.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos7.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos7.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos7.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos7.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos8 = pos5.add(0, 0, 1);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos8.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos8.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos8.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos8.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos8.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos pos9 = pos5.add(0, 0, -1);
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos9.add(0, -1, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos9.add(-1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos9.add(1, 0, 0)).getBlock())) {
            return new BlockDataOld(pos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos9.add(0, 0, 1)).getBlock())) {
            return new BlockDataOld(pos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalidBlocks.contains(Minecraft.theWorld.getBlockState(pos9.add(0, 0, -1)).getBlock())) {
            return new BlockDataOld(pos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    private boolean isValidItem(Item item) {
        if (item instanceof ItemBlock) {
            ItemBlock iBlock = (ItemBlock)item;
            Block block = iBlock.getBlock();
            return !this.invalidBlocks.contains(block);
        }
        return false;
    }

    private static class BlockDataOld {
        public final BlockPos pos;
        public final EnumFacing face;

        private BlockDataOld(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
}

