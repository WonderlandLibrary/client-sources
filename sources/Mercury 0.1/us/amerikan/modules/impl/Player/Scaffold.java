/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.events.MotionUpdateEvent;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.BlockUtil;
import us.amerikan.utils.TimeHelper;

public class Scaffold
extends Module {
    private static boolean cooldown = false;
    private static boolean valid = false;
    private float startPitch;
    private int time;
    private static TimeHelper scaffoldTime = new TimeHelper();
    private int rotationCooldown;
    private int aacBypass = 0;
    private int nextItem = -1;
    private int lastItem;
    private int slot;
    private int delay = 0;
    private int boost = 0;
    private double a;
    private double b;
    private double c;
    private double d;
    private List<Block> invalidBlocks;
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private float[] rotations = new float[2];

    public Scaffold() {
        super("Scaffold", "Scaffold", 0, Category.PLAYER);
        amerikan.setmgr.rSetting(new Setting("Scaffold Delay", this, 50.0, 0.0, 100.0, true));
        amerikan.setmgr.rSetting(new Setting("Slow Mode", this, false));
        amerikan.setmgr.rSetting(new Setting("Sprint", this, false));
        amerikan.setmgr.rSetting(new Setting("Silent", this, false));
        amerikan.setmgr.rSetting(new Setting("SafeWalk", this, true));
        amerikan.setmgr.rSetting(new Setting("Fastbridge", this, false));
        amerikan.setmgr.rSetting(new Setting("Boost ", this, false));
        amerikan.setmgr.rSetting(new Setting("CubeCraft", this, false));
        this.invalidBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.ender_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.furnace, Blocks.lit_furnace, Blocks.crafting_table, Blocks.acacia_fence, Blocks.acacia_fence_gate, Blocks.birch_fence, Blocks.birch_fence_gate, Blocks.dark_oak_fence, Blocks.dark_oak_fence_gate, Blocks.jungle_fence, Blocks.jungle_fence_gate, Blocks.oak_fence, Blocks.oak_fence_gate, Blocks.acacia_door, Blocks.birch_door, Blocks.dark_oak_door, Blocks.iron_door, Blocks.jungle_door, Blocks.oak_door, Blocks.spruce_door, Blocks.rail, Blocks.activator_rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.brewing_stand);
    }

    @EventTarget
    public void onAddon(EventUpdate e2) {
        this.setAddon(amerikan.setmgr.getSettingByName("Silent").getValBoolean() ? "Silent" : "ignore");
    }

    @Override
    public void onRender() {
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if ((double)this.time > amerikan.setmgr.getSettingByName("Scaffold Delay").getValDouble() / 50.0) {
            this.time = 0;
        }
        if (!Scaffold.mc.theWorld.isAirBlock(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.getEntityBoundingBox().minY, Minecraft.thePlayer.posZ).add(0, -1, 0))) {
            if (amerikan.setmgr.getSettingByName("Fastbridge").getValBoolean()) {
                Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
            }
            this.time = 0;
            TimeHelper.reset();
        } else if (amerikan.setmgr.getSettingByName("Fastbridge").getValBoolean()) {
            Scaffold.mc.gameSettings.keyBindSneak.pressed = true;
        }
        ++this.time;
        for (int i2 = 0; i2 < 9; ++i2) {
            ItemStack itemStack = Minecraft.thePlayer.inventory.getStackInSlot(i2);
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || this.invalidBlocks.contains(itemStack.getItem()) || itemStack.stackSize <= 0) continue;
            this.nextItem = i2;
            break;
        }
        amerikan.setmgr.getSettingByName("Boost ").getValBoolean();
        if (!Minecraft.thePlayer.onGround) {
            if (amerikan.setmgr.getSettingByName("CubeCraft").getValBoolean()) {
                Minecraft.thePlayer.motionX *= 0.0;
                Minecraft.thePlayer.motionZ *= 0.0;
            } else {
                Minecraft.thePlayer.motionX *= 0.9;
                Minecraft.thePlayer.motionZ *= 0.9;
            }
        }
        if (amerikan.setmgr.getSettingByName("Sprint").getValBoolean()) {
            Minecraft.thePlayer.setSprinting(true);
        } else {
            Minecraft.thePlayer.setSprinting(false);
        }
        if (Keyboard.isKeyDown(17)) {
            if (this.delay > 3) {
                Scaffold.mc.gameSettings.keyBindForward.pressed = true;
                this.delay = 0;
            } else {
                Scaffold.mc.gameSettings.keyBindForward.pressed = true;
            }
            ++this.delay;
        }
        if (Keyboard.isKeyDown(30)) {
            if (this.a > 3.0) {
                Scaffold.mc.gameSettings.keyBindLeft.pressed = false;
                this.a = 0.0;
            } else {
                Scaffold.mc.gameSettings.keyBindLeft.pressed = true;
            }
            this.a += 1.0;
        }
        if (Keyboard.isKeyDown(32)) {
            if (this.b > 3.0) {
                Scaffold.mc.gameSettings.keyBindRight.pressed = false;
                this.b = 0.0;
            } else {
                Scaffold.mc.gameSettings.keyBindRight.pressed = true;
            }
            this.b += 1.0;
        }
        if (Keyboard.isKeyDown(31)) {
            if (this.c > 3.0) {
                Scaffold.mc.gameSettings.keyBindBack.pressed = false;
                this.c = 0.0;
            } else {
                Scaffold.mc.gameSettings.keyBindBack.pressed = true;
            }
            this.c += 1.0;
        }
    }

    private void setBlockAndFacing(BlockPos var1) {
        if (Scaffold.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
        } else if (Scaffold.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        } else if (Scaffold.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        } else if (Scaffold.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        } else if (Scaffold.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        } else {
            this.currentPos = null;
            this.currentFacing = null;
        }
    }

    @EventTarget
    public void onMotion(MotionUpdateEvent e2) {
        if (e2.getState() == MotionUpdateEvent.State.PRE && this.isEnabled()) {
            this.rotated = false;
            this.currentPos = null;
            this.currentFacing = null;
            BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
            if (Scaffold.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                this.setBlockAndFacing(pos);
                if (this.currentPos != null) {
                    float[] facing = BlockUtil.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
                    float yaw = facing[0];
                    float pitch = Math.min(90.0f, facing[1] + 9.0f);
                    this.rotations[0] = yaw;
                    this.rotations[1] = pitch;
                    e2.setYaw(yaw);
                    e2.setPitch(pitch);
                }
            } else {
                e2.setYaw(this.rotations[0]);
                e2.setPitch(this.rotations[1]);
            }
            e2.setYaw(this.rotations[0]);
            e2.setPitch(this.rotations[1]);
        }
    }

    @EventTarget
    public void onPre(MotionUpdateEvent event) {
        if (event.getState() != MotionUpdateEvent.State.PRE && !this.isEnabled()) {
            return;
        }
        BlockPos playerBlock = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.getEntityBoundingBox().minY, Minecraft.thePlayer.posZ);
        BlockPos blockUnderPlayer = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
        float yaw = Minecraft.thePlayer.rotationYaw;
        boolean keyW = Keyboard.isKeyDown(17);
        boolean keyS = Keyboard.isKeyDown(31);
        boolean keyA = Keyboard.isKeyDown(30);
        boolean keyD = Keyboard.isKeyDown(32);
        if (Minecraft.thePlayer.hurtTime != 0) {
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
            if (Minecraft.thePlayer.onGround) {
                Minecraft.thePlayer.motionY = 0.42;
            }
        }
        float rotations = 70.0f;
        if (keyW) {
            yaw = Minecraft.thePlayer.rotationYaw - rotations;
        } else if (keyS) {
            yaw = Minecraft.thePlayer.rotationYaw - 180.0f - rotations;
        } else if (keyA) {
            yaw = Minecraft.thePlayer.rotationYaw - 90.0f - rotations;
        } else if (keyD) {
            yaw = Minecraft.thePlayer.rotationYaw + 90.0f - rotations;
        } else if (keyW && keyA) {
            yaw = Minecraft.thePlayer.rotationYaw - 45.0f - rotations;
        } else if (keyW && keyD) {
            yaw = Minecraft.thePlayer.rotationYaw + 45.0f - rotations;
        } else if (keyS && keyA) {
            yaw = Minecraft.thePlayer.rotationYaw + 45.0f - rotations;
        } else if (keyS && keyD) {
            yaw = Minecraft.thePlayer.rotationYaw - 45.0f - rotations;
        }
        if (!amerikan.setmgr.getSettingByName("Sprint").getValBoolean()) {
            if (Minecraft.thePlayer.isSprinting()) {
                Minecraft.thePlayer.setSprinting(false);
            }
        }
        if (amerikan.setmgr.getSettingByName("Slow Mode").getValBoolean()) {
            Minecraft.thePlayer.motionX *= 0.8;
            Minecraft.thePlayer.motionZ *= 0.8;
        }
        if (Scaffold.mc.theWorld.isAirBlock(playerBlock.add(0, -1, 0))) {
            amerikan.setmgr.getSettingByName("Sprint").getValBoolean();
            if (TimeHelper.hasReached((long)amerikan.setmgr.getSettingByName("Scaffold Delay").getValDouble())) {
                if (this.isValidBlock(playerBlock.add(0, -1, 0)) && Scaffold.isPressed()) {
                    this.place(playerBlock.add(0, -1, 0), EnumFacing.UP);
                } else if (this.isValidBlock(playerBlock.add(-1, -1, 0))) {
                    this.place(playerBlock.add(0, -1, 0), EnumFacing.EAST);
                } else if (this.isValidBlock(playerBlock.add(1, -1, 0))) {
                    this.place(playerBlock.add(0, -1, 0), EnumFacing.WEST);
                } else if (this.isValidBlock(playerBlock.add(0, -1, -1))) {
                    this.place(playerBlock.add(0, -1, 0), EnumFacing.SOUTH);
                } else if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                    this.place(playerBlock.add(0, -1, 0), EnumFacing.NORTH);
                } else if (this.isValidBlock(playerBlock.add(1, -1, 1))) {
                    if (this.isValidBlock(playerBlock.add(0, -1, 1))) {
                        this.place(playerBlock.add(0, -1, 1), EnumFacing.NORTH);
                    }
                    this.place(playerBlock.add(1, -1, 1), EnumFacing.EAST);
                } else if (this.isValidBlock(playerBlock.add(-1, -1, 1))) {
                    if (this.isValidBlock(playerBlock.add(-1, -1, 0))) {
                        this.place(playerBlock.add(0, -1, 1), EnumFacing.WEST);
                    }
                    this.place(playerBlock.add(-1, -1, 1), EnumFacing.SOUTH);
                } else if (this.isValidBlock(playerBlock.add(-1, -1, -1))) {
                    if (this.isValidBlock(playerBlock.add(0, -1, -1))) {
                        this.place(playerBlock.add(0, -1, -1), EnumFacing.SOUTH);
                    }
                    this.place(playerBlock.add(-1, -1, -1), EnumFacing.WEST);
                } else if (this.isValidBlock(playerBlock.add(1, -1, -1))) {
                    if (this.isValidBlock(playerBlock.add(1, -1, 0))) {
                        this.place(playerBlock.add(1, -1, 0), EnumFacing.EAST);
                    }
                    this.place(playerBlock.add(1, -1, -1), EnumFacing.NORTH);
                }
                if (Minecraft.thePlayer.moveForward != 0.0f) {
                    this.place(playerBlock.add(0, 0, 0), EnumFacing.DOWN);
                }
            }
        }
    }

    private BlockPos blockPosition(double x2, double z2) {
        return new BlockPos(Minecraft.thePlayer.posX + x2, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ + z2);
    }

    private boolean isInvalidBlock(BlockPos pos) {
        Block b2 = Scaffold.mc.theWorld.getBlockState(pos).getBlock();
        return b2 instanceof BlockLiquid && b2.getMaterial() == Material.air;
    }

    private boolean isValidBlock(BlockPos pos) {
        Block b2 = Scaffold.mc.theWorld.getBlockState(pos).getBlock();
        return !(b2 instanceof BlockLiquid) && b2.getMaterial() != Material.air;
    }

    private void place(BlockPos pos, EnumFacing face) {
        if (amerikan.setmgr.getSettingByName("Sprint").getValBoolean()) {
            Minecraft.thePlayer.motionX *= 0.6;
            Minecraft.thePlayer.motionZ *= 0.6;
        }
        cooldown = true;
        if (face == EnumFacing.UP) {
            pos = pos.add(0, -1, 0);
            if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
                this.lastItem = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.inventory.currentItem = this.nextItem;
            }
        } else if (face == EnumFacing.NORTH && this.isValidBlock(this.blockPosition(0.0, 1.0)) && Scaffold.isPressed()) {
            if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
                this.lastItem = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.inventory.currentItem = this.nextItem;
            }
            pos = pos.add(0, 0, 1);
        } else if (face == EnumFacing.EAST && this.isValidBlock(this.blockPosition(-1.0, 0.0)) && Scaffold.isPressed()) {
            if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
                this.lastItem = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.inventory.currentItem = this.nextItem;
            }
            pos = pos.add(-1, 0, 0);
        } else if (face == EnumFacing.SOUTH && this.isValidBlock(this.blockPosition(0.0, -1.0)) && Scaffold.isPressed()) {
            if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
                this.lastItem = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.inventory.currentItem = this.nextItem;
            }
            pos = pos.add(0, 0, -1);
        } else if (face == EnumFacing.WEST && this.isValidBlock(this.blockPosition(1.0, 0.0)) && Scaffold.isPressed()) {
            if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
                this.lastItem = Minecraft.thePlayer.inventory.currentItem;
                Minecraft.thePlayer.inventory.currentItem = this.nextItem;
            }
            pos = pos.add(1, 0, 0);
        } else {
            return;
        }
        if (Minecraft.thePlayer.hurtTime == 0) {
            Minecraft.thePlayer.swingItem();
            Scaffold.mc.playerController.func_178890_a(Minecraft.thePlayer, Scaffold.mc.theWorld, Minecraft.thePlayer.getHeldItem(), pos, face, new Vec3(0.5, 0.5, 0.5));
            double var4 = (double)pos.getX() + 0.25 - Minecraft.thePlayer.posX;
            double var6 = (double)pos.getZ() + 0.25 - Minecraft.thePlayer.posZ;
            double var8 = (double)pos.getY() + 0.25 - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
            double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
            float yaw = (float)(Math.atan2(var6, var4) * 180.0 / 3.141592653589793) - 90.0f;
            float pitch = (float)(-(Math.atan2(var8, var14) * 180.0 / 3.141592653589793));
            int ticks = 0;
            if (++ticks >= 1000) {
                ticks = 0;
            }
        }
        if ((double)this.time > amerikan.setmgr.getSettingByName("Scaffold Delay").getValDouble() / 50.0) {
            this.time = 0;
        }
        if (TimeHelper.hasReached((long)amerikan.setmgr.getSettingByName("Scaffold Delay").getValDouble())) {
            TimeHelper.reset();
        }
        ++this.aacBypass;
        if (amerikan.setmgr.getSettingByName("Silent").getValBoolean()) {
            Minecraft.thePlayer.inventory.currentItem = this.lastItem;
        }
        if (amerikan.setmgr.getSettingByName("Boost ").getValBoolean() && Scaffold.isPressed()) {
            Timer.timerSpeed = 0.6f;
            Scaffold.teleportPlayer(0.5);
        }
    }

    public static void teleportPlayer(double blockDistance) {
        Minecraft.thePlayer.setPositionAndUpdate(Minecraft.thePlayer.posX + -Math.sin(Scaffold.direction()) * blockDistance, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + Math.cos(Scaffold.direction()) * blockDistance);
    }

    public static double direction() {
        return Math.toRadians(Minecraft.thePlayer.rotationYaw);
    }

    @Override
    public void onEnable() {
        this.slot = 0;
        this.aacBypass = 0;
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        amerikan.setmgr.getSettingByName("CubeCraft").getValBoolean();
        Scaffold.mc.gameSettings.keyBindSneak.pressed = false;
        Timer.timerSpeed = 1.0f;
        EventManager.unregister(this);
    }
}

