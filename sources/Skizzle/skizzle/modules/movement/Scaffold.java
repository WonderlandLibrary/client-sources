/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.movement;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventRender3D;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.events.listeners.EventRenderPlayer;
import skizzle.events.listeners.EventStrafing;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.BooleanSetting;
import skizzle.settings.ModeSetting;
import skizzle.settings.NumberSetting;
import skizzle.ui.notifications.Notification;
import skizzle.util.BlockUtil;
import skizzle.util.MoveUtil;
import skizzle.util.RenderUtil;
import skizzle.util.Timer;

public class Scaffold
extends Module {
    public List<Block> noobBlocks;
    public Timer towerTimer;
    public NumberSetting boost;
    public double startY;
    public static boolean isPlaceTick = false;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u7a9f\u71c4\u41c2\ua7e1"), Qprot0.0("\u7a9c\u71e8\u41f6"), Qprot0.0("\u7a9c\u71e8\u41f6"), Qprot0.0("\u7a93\u71ea\u41e5\ua7b0\u540f\uff58"), Qprot0.0("\u7a9f\u71ca\u41d2\ua7f6\u5448\uff10"));
    public boolean hasJumped = false;
    public BlockData blockData;
    public String lastFace;
    public int switchThang;
    public float pitch;
    public float yaw;
    public BooleanSetting tower;
    public BooleanSetting towerMove;
    public BooleanSetting keepY;
    public BooleanSetting sprint;
    public ModeSetting keepRots = new ModeSetting(Qprot0.0("\u7a99\u71ce\u41c3\ua7f4\u5401\uff3a\u8c20\u16eb\u5703\uff1f\u752c\uaf03\uf82a\u725e"), Qprot0.0("\u7a97\u71c5\u41c7\ua7e6\u544d\uff0d\u8c2b"), Qprot0.0("\u7a97\u71c5\u41c7\ua7e6\u544d\uff0d\u8c2b"), Qprot0.0("\u7a96\u71c2\u41d5\ua7e5\u5443\uff04\u8c2a\u16fb"), Qprot0.0("\u7a81\u71dc\u41cf\ua7f0\u5442\uff00"));
    public float yawLast;
    public float pitchLast;
    public BooleanSetting swing;
    public BooleanSetting safeWalk;
    public BooleanSetting lockYaw;
    public BlockData lastBlock;
    public BooleanSetting hold;
    public NumberSetting placeDelay;
    public Timer notifTimer;
    public BooleanSetting down;
    public Timer placeTimer;
    public double boostSpeed = 1.5;

    public boolean shouldStop() {
        Scaffold Nigga;
        if (Nigga.mc.thePlayer != null) {
            BlockPos Nigga2 = new BlockPos(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY - 1.3, Nigga.mc.thePlayer.posZ);
            if (Nigga.isEnabled() && Nigga.safeWalk.isEnabled() && Client.booleanThingy(!Nigga.mc.thePlayer.onGround, Minecraft.theWorld.getBlockState(Nigga2).getBlock().getMaterial().equals(Material.air))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEvent(Event Nigga) {
        Object Nigga2;
        Scaffold Nigga3;
        Nigga3.setSuffix(Nigga3.mode.getMode());
        if (Nigga instanceof EventRenderGUI && Nigga3.getBlocksLeft() != 0) {
            Nigga2 = String.valueOf(Nigga3.getBlocksLeft()) + Qprot0.0("\u7af2\u71e9\u41ca\ue262\u9efb\uff03\u8c3c");
            Client.drawMessage((String)Nigga2, -1);
        }
        if (Nigga instanceof EventRenderPlayer) {
            Nigga2 = (EventRenderPlayer)Nigga;
            if (isPlaceTick) {
                ((EventRenderPlayer)Nigga2).setYaw((float)RenderUtil.interpolateValue(Nigga3.yawLast, Nigga3.yaw, ((EventRenderPlayer)Nigga2).getPartialTicks()));
                ((EventRenderPlayer)Nigga2).setPitch((float)RenderUtil.interpolateValue(Nigga3.pitchLast, Nigga3.pitch, ((EventRenderPlayer)Nigga2).getPartialTicks()));
                ((EventRenderPlayer)Nigga2).yawChange = Float.intBitsToFloat(2.13836006E9f ^ 0x7F74C8FE);
            }
        }
        if (Nigga instanceof EventUpdate && MoveUtil.isMoving()) {
            if (Nigga3.boostSpeed < 1.0) {
                Nigga3.boostSpeed = 1.0;
            }
            if (Nigga3.boostSpeed > 1.0) {
                Nigga3.boostSpeed -= (Nigga3.boost.getValue() + 1.0) / 600.0;
                Nigga3.mc.timer.timerSpeed = (float)Nigga3.boostSpeed;
            }
        }
        if (Nigga instanceof EventRender3D) {
            GL11.glEnable((int)2848);
            GlStateManager.color(Float.intBitsToFloat(2.13222221E9f ^ 0x7F172106), Float.intBitsToFloat(1.10236723E9f ^ 0x7EB806F1), Float.intBitsToFloat(1.12356762E9f ^ 0x7D8B7B27));
            if (Nigga3.getBlockCount() < 15) {
                GlStateManager.color(Float.intBitsToFloat(1.10389286E9f ^ 0x7EAA7709), Float.intBitsToFloat(1.07857126E9f ^ 0x7F2FD768), Float.intBitsToFloat(1.11488538E9f ^ 0x7F3F0019));
            }
            if (Nigga3.getBlockCount() < 1) {
                GlStateManager.color(Float.intBitsToFloat(1.08140442E9f ^ 0x7F128D9D), Float.intBitsToFloat(1.13999117E9f ^ 0x7EBE2E83), Float.intBitsToFloat(1.07612557E9f ^ 0x7D6893BF));
            }
            GL11.glLineWidth((float)Float.intBitsToFloat(1.06000115E9f ^ 0x7F6E5577));
            if (Nigga3.blockData != null && BlockData.access$0(Nigga3.blockData) != null) {
                Nigga2 = new AxisAlignedBB((double)BlockData.access$0(Nigga3.blockData).getX() - Nigga3.mc.getRenderManager().renderPosX + (double)(BlockData.access$1(Nigga3.blockData).getFrontOffsetX() * 2) + 1.0, (double)BlockData.access$0(Nigga3.blockData).getY() - Nigga3.mc.getRenderManager().renderPosY + (double)BlockData.access$1(Nigga3.blockData).getFrontOffsetY() + 1.0, (double)BlockData.access$0(Nigga3.blockData).getZ() - Nigga3.mc.getRenderManager().renderPosZ + (double)(BlockData.access$1(Nigga3.blockData).getFrontOffsetZ() * 2) + 1.0, (double)BlockData.access$0(Nigga3.blockData).getX() - Nigga3.mc.getRenderManager().renderPosX + (double)(BlockData.access$1(Nigga3.blockData).getFrontOffsetX() * 2), (double)BlockData.access$0(Nigga3.blockData).getY() - Nigga3.mc.getRenderManager().renderPosY + (double)BlockData.access$1(Nigga3.blockData).getFrontOffsetY() + 1.0, (double)BlockData.access$0(Nigga3.blockData).getZ() - Nigga3.mc.getRenderManager().renderPosZ + (double)(BlockData.access$1(Nigga3.blockData).getFrontOffsetZ() * 2));
                RenderUtil.drawBoundingBox((AxisAlignedBB)Nigga2, 3);
            }
            GL11.glLineWidth((float)Float.intBitsToFloat(1.08755827E9f ^ 0x7F52D262));
            if (Nigga3.lastBlock == null) {
                return;
            }
            GlStateManager.color(Float.intBitsToFloat(2.13747341E9f ^ 0x7F674166), Float.intBitsToFloat(1.11104614E9f ^ 0x7D35FB0F), Float.intBitsToFloat(1.07916301E9f ^ 0x7F218BA0));
            Nigga2 = new AxisAlignedBB((double)BlockData.access$0(Nigga3.lastBlock).getX() - Nigga3.mc.getRenderManager().renderPosX + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetX(), (double)BlockData.access$0(Nigga3.lastBlock).getY() - Nigga3.mc.getRenderManager().renderPosY + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetY(), (double)BlockData.access$0(Nigga3.lastBlock).getZ() - Nigga3.mc.getRenderManager().renderPosZ + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetZ(), (double)BlockData.access$0(Nigga3.lastBlock).getX() - Nigga3.mc.getRenderManager().renderPosX + 1.0 + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetX(), (double)BlockData.access$0(Nigga3.lastBlock).getY() - Nigga3.mc.getRenderManager().renderPosY + 1.0 + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetY(), (double)BlockData.access$0(Nigga3.lastBlock).getZ() - Nigga3.mc.getRenderManager().renderPosZ + 1.0 + (double)BlockData.access$1(Nigga3.lastBlock).getFrontOffsetZ());
            RenderUtil.drawBoundingBox((AxisAlignedBB)Nigga2, 3);
            RenderUtil.drawSolidBlockESP((AxisAlignedBB)Nigga2, Float.intBitsToFloat(2.13701606E9f ^ 0x7F6046FE), Float.intBitsToFloat(1.11565299E9f ^ 0x7D734FBF), Float.intBitsToFloat(1.0745673E9f ^ 0x7F7FAB75), Float.intBitsToFloat(1.07693005E9f ^ 0x7E7C692F), 7);
        }
        if (Nigga instanceof EventMotion) {
            Nigga2 = Nigga3.mode.getMode();
            if (!Nigga3.sprint.isEnabled()) {
                Nigga3.mc.thePlayer.setSprinting(false);
            }
            EventMotion Nigga4 = (EventMotion)Nigga;
            int Nigga5 = Nigga3.getSlot();
            if (Nigga4.isPre()) {
                if (Nigga3.switchThang > 3) {
                    Nigga3.switchThang = 0;
                }
                boolean bl = Nigga3.keepRots.getMode().equals(Qprot0.0("\u7a97\u71c5\u41c7\ue26f\u9ef4\uff0d\u8c2b")) || Nigga3.keepRots.getMode().equals(Qprot0.0("\u7a81\u71dc\u41cf\ue279\u9efb\uff00")) && Nigga3.switchThang < 2 ? Nigga3.blockData != null && Nigga5 != -1 : (isPlaceTick = Nigga3.blockData != null && Nigga5 != -1 && Minecraft.theWorld.getBlockState(new BlockPos(Nigga3.mc.thePlayer).add(0, -1, 0)).getBlock() == Blocks.air);
                if (Nigga5 == -1 || Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga5) != null && Nigga3.mc.thePlayer.inventory.getStackInSlot((int)Nigga5).stackSize <= 1) {
                    Nigga3.moveBlocksToHotbar();
                    if (Nigga5 == -1) {
                        return;
                    }
                }
                Nigga3.blockData = Nigga3.getBlockData();
                if (Nigga3.blockData == null) {
                    return;
                }
                if (Nigga3.tower.isEnabled() && (Nigga3.towerMove.isEnabled() || Nigga3.mc.thePlayer.moveForward == Float.intBitsToFloat(2.10713357E9f ^ 0x7D984E47) && Nigga3.mc.thePlayer.moveStrafing == Float.intBitsToFloat(2.12642688E9f ^ 0x7EBEB2E9) || Nigga3.mc.thePlayer.isCollidedHorizontally) && !Nigga3.mc.thePlayer.isPotionActive(Potion.jump)) {
                    boolean Nigga6 = Nigga3.towerTimer.hasTimeElapsed((long)1733738358 ^ 0x6756BF7CL, true);
                    if (!Minecraft.theWorld.isAirBlock(new BlockPos((double)BlockData.access$2(Nigga3.blockData).getX(), (float)BlockData.access$2(Nigga3.blockData).getY() + Float.intBitsToFloat(1.09267443E9f ^ 0x7EE0E379), (double)BlockData.access$2(Nigga3.blockData).getZ())) && Nigga6 && Nigga3.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        Nigga3.hasJumped = true;
                        Nigga3.mc.thePlayer.motionY = 0.0;
                    } else if (Nigga3.hasJumped) {
                        Nigga3.hasJumped = false;
                        Nigga3.mc.thePlayer.motionY = 0.0;
                    }
                } else {
                    Nigga3.towerTimer.reset();
                }
                if (isPlaceTick) {
                    Nigga3.yawLast = Nigga3.yaw;
                    Nigga3.pitchLast = Nigga3.pitch;
                    Nigga3.yaw = Nigga4.getYaw();
                    Nigga3.pitch = Float.intBitsToFloat(1.01935418E9f ^ 0x7E601C37);
                    if (BlockData.access$1(Nigga3.blockData).getName().equals(Qprot0.0("\u7aa7\u71db"))) {
                        Nigga3.pitch = Float.intBitsToFloat(1.07001498E9f ^ 0x7D7321EF);
                    }
                    if (Nigga3.mc.thePlayer.moveForward != Float.intBitsToFloat(2.13688576E9f ^ 0x7F5E4A1C) || Nigga3.mc.thePlayer.moveStrafing != Float.intBitsToFloat(2.12683123E9f ^ 0x7EC4DEA9)) {
                    }
                    if (((String)Nigga2).equalsIgnoreCase(Qprot0.0("\u7a93\u71ea\u41e5\ue239\u9eb6\uff58"))) {
                        Nigga3.yaw = Float.intBitsToFloat(2.12016602E9f ^ 0x7E5F2A43);
                        if (BlockData.access$1(Nigga3.blockData).getName().equalsIgnoreCase(Qprot0.0("\u7abc\u71c4\u41d4\ue279\u9ef0"))) {
                            Nigga3.yaw = Float.intBitsToFloat(2.13808358E9f ^ 0x7F70911B);
                        }
                        if (BlockData.access$1(Nigga3.blockData).getName().equalsIgnoreCase(Qprot0.0("\u7aa1\u71c4\u41d3\ue279\u9ef0"))) {
                            Nigga3.yaw = Float.intBitsToFloat(1.02783962E9f ^ 0x7E779667);
                        }
                        if (BlockData.access$1(Nigga3.blockData).getName().equalsIgnoreCase(Qprot0.0("\u7aa5\u71ce\u41d5\ue279"))) {
                            Nigga3.yaw = Float.intBitsToFloat(-1.08905165E9f ^ 0x7DA2642F);
                        }
                        if (BlockData.access$1(Nigga3.blockData).getName().equalsIgnoreCase(Qprot0.0("\u7ab7\u71ca\u41d5\ue279"))) {
                            Nigga3.yaw = Float.intBitsToFloat(1.01123264E9f ^ 0x7EF22F75);
                        }
                    } else if (((String)Nigga2).equalsIgnoreCase(Qprot0.0("\u7a9c\u71e8\u41f6")) || ((String)Nigga2).equals(Qprot0.0("\u7a9f\u71ca\u41d2\ue27f\u9ef1\uff10"))) {
                        Nigga3.yaw = BlockUtil.getRotations(BlockData.access$2(Nigga3.blockData), BlockData.access$1(Nigga3.blockData))[0];
                        Nigga3.pitch = BlockUtil.getRotations(BlockData.access$2(Nigga3.blockData), BlockData.access$1(Nigga3.blockData))[1];
                    }
                    Nigga4.setPitch(Nigga3.pitch);
                    Nigga4.setYaw(Nigga3.yaw);
                }
            } else if (Nigga5 != -1 && Nigga3.blockData != null) {
                int Nigga7 = Nigga3.mc.thePlayer.inventory.currentItem;
                Nigga3.mc.thePlayer.inventory.currentItem = Nigga5;
                if (Nigga3.getPlaceBlock(BlockData.access$2(Nigga3.blockData), BlockData.access$1(Nigga3.blockData))) {
                    Nigga3.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Nigga7));
                }
                if (Nigga3.hold.isEnabled() && !((String)Nigga2).equals(Qprot0.0("\u7a9f\u71ca\u41d2\ue27f\u9ef1\uff10"))) {
                    Nigga3.mc.thePlayer.inventory.currentItem = Nigga7;
                }
            }
        }
        if (Nigga instanceof EventStrafing && Nigga3.lockYaw.isEnabled()) {
            MoveUtil.applyStrafeToPlayer((EventStrafing)Nigga, Nigga3.yaw);
        }
    }

    public int getBlocksLeft() {
        int Nigga = 0;
        for (int Nigga2 = 0; Nigga2 < 40; ++Nigga2) {
            Scaffold Nigga3;
            if (Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga2) == null || !(Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga2).getItem() instanceof ItemBlock) || Nigga3.noobBlocks.contains(Block.getBlockFromItem(Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga2).getItem()))) continue;
            Nigga += Nigga3.mc.thePlayer.inventory.getStackInSlot((int)Nigga2).stackSize;
        }
        return Nigga;
    }

    public Scaffold() {
        super(Qprot0.0("\u7a81\u71c8\u41c7\ua7e2\u5447\uff07\u8c23\u16fb"), 0, Module.Category.MOVEMENT);
        Scaffold Nigga;
        Nigga.sprint = new BooleanSetting(Qprot0.0("\u7a81\u71db\u41d4\ua7ed\u544f\uff1c"), false);
        Nigga.down = new BooleanSetting(Qprot0.0("\u7a96\u71c4\u41d1\ua7ea\u5456\uff09\u8c3d\u16fb\u5711"), false);
        Nigga.tower = new BooleanSetting(Qprot0.0("\u7a86\u71c4\u41d1\ua7e1\u5453"), false);
        Nigga.towerMove = new BooleanSetting(Qprot0.0("\u7a86\u71c4\u41d1\ua7e1\u5453\uff48\u8c02\u16f0\u5714\uff0e"), false);
        Nigga.swing = new BooleanSetting(Qprot0.0("\u7a81\u71dc\u41cf\ua7ea\u5446"), true);
        Nigga.safeWalk = new BooleanSetting(Qprot0.0("\u7a81\u71ca\u41c0\ua7e1\u5401\uff3f\u8c2e\u16f3\u5709"), true);
        Nigga.keepY = new BooleanSetting(Qprot0.0("\u7a99\u71ce\u41c3\ua7f4\u5401\uff31"), false);
        Nigga.placeDelay = new NumberSetting(Qprot0.0("\u7a82\u71c7\u41c7\ua7e7\u5444\uff48\u8c0b\u16fa\u570e\uff0a\u753c"), 100.0, 0.0, 1000.0, 1.0);
        Nigga.boost = new NumberSetting(Qprot0.0("\u7a90\u71c4\u41c9\ua7f7\u5455\uff48\u8c1c\u16ef\u5707\uff0e\u7521"), 0.0, 0.0, 10.0, 0.0);
        Nigga.hold = new BooleanSetting(Qprot0.0("\u7a81\u71db\u41c9\ua7eb\u5447\uff48\u8c07\u16f0\u570e\uff0f"), true);
        Nigga.lockYaw = new BooleanSetting(Qprot0.0("\u7a9e\u71c4\u41c5\ua7ef\u5401\uff31\u8c2e\u16e8"), true);
        Nigga.placeTimer = new Timer();
        Nigga.notifTimer = new Timer();
        Nigga.noobBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.slime_block, Blocks.red_flower, Blocks.yellow_flower);
        Nigga.towerTimer = new Timer();
        Nigga.lastFace = Qprot0.0("\u7abc\u71c4\u41d4\ua7f0\u5449");
        Nigga.addSettings(Nigga.mode, Nigga.sprint, Nigga.keepRots, Nigga.keepY, Nigga.down, Nigga.tower, Nigga.towerMove, Nigga.swing, Nigga.safeWalk, Nigga.placeDelay, Nigga.boost, Nigga.hold, Nigga.lockYaw);
    }

    public boolean getPlaceBlock(BlockPos Nigga, EnumFacing Nigga2) {
        Scaffold Nigga3;
        new Vec3(Nigga3.mc.thePlayer.posX, Nigga3.mc.thePlayer.posY + (double)Nigga3.mc.thePlayer.getEyeHeight(), Nigga3.mc.thePlayer.posZ);
        Vec3i Nigga4 = BlockData.access$1(Nigga3.blockData).getDirectionVec();
        if (Nigga3.placeTimer.hasTimeElapsed((long)Nigga3.placeDelay.getValue(), true)) {
            Vec3 Nigga5 = new Vec3((double)BlockData.access$2(Nigga3.blockData).getX() + (double)Nigga4.getX() * 0.0, (double)BlockData.access$2(Nigga3.blockData).getY() + (double)Nigga4.getY() * 0.0, (double)BlockData.access$2(Nigga3.blockData).getZ() + (double)Nigga4.getZ() * 0.0);
            if (Nigga3.mc.playerController.onPlayerRightClick(Nigga3.mc.thePlayer, Minecraft.theWorld, Nigga3.mc.thePlayer.getHeldItem(), Nigga, Nigga2, Nigga5)) {
                Nigga3.lastBlock = Nigga3.blockData;
                ++Nigga3.switchThang;
                if (Nigga3.swing.isEnabled()) {
                    Nigga3.mc.thePlayer.swingItem();
                } else {
                    Nigga3.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }
                return true;
            }
        }
        return false;
    }

    public BlockData getBlockData() {
        boolean Nigga;
        Scaffold Nigga2;
        EnumFacing[] Nigga3 = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        double Nigga4 = 0.0;
        if (Keyboard.isKeyDown((int)Nigga2.mc.gameSettings.keyBindSneak.getKeyCode()) && !Nigga2.mc.gameSettings.keyBindJump.isPressed() && Nigga2.down.isEnabled() && Nigga2.mc.thePlayer.onGround) {
            KeyBinding.setKeyBindState(Nigga2.mc.gameSettings.keyBindSneak.getKeyCode(), false);
            Nigga4 -= 1.0;
        }
        BlockPos Nigga5 = new BlockPos(Nigga2.mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0.0, Nigga4, 0.0);
        boolean bl = Nigga = !Nigga2.towerMove.isEnabled() && Nigga2.tower.isEnabled() && (Nigga2.mc.thePlayer.moveForward == Float.intBitsToFloat(2.13228442E9f ^ 0x7F181404) && Nigga2.mc.thePlayer.moveStrafing == Float.intBitsToFloat(2.13304154E9f ^ 0x7F23A14D) || Nigga2.mc.thePlayer.isCollidedHorizontally);
        if (!Nigga2.down.isEnabled() && Nigga2.keepY.isEnabled() && !Nigga) {
            Nigga5 = new BlockPos(new Vec3(Nigga2.mc.thePlayer.getPositionVector().xCoord, Nigga2.startY, Nigga2.mc.thePlayer.getPositionVector().zCoord));
        } else {
            Nigga2.startY = Nigga2.mc.thePlayer.posY;
        }
        List<EnumFacing> Nigga6 = Arrays.asList(EnumFacing.values());
        for (int Nigga7 = 0; Nigga7 < Nigga6.size(); ++Nigga7) {
            if (Minecraft.theWorld.getBlockState(Nigga5.offset(Nigga6.get(Nigga7))).getBlock().getMaterial() == Material.air) continue;
            BlockData Nigga8 = new BlockData(Nigga2, Nigga5.offset(Nigga6.get(Nigga7)), Nigga3[Nigga6.get(Nigga7).ordinal()], null);
            return Nigga8;
        }
        BlockPos[] Nigga9 = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)};
        int Nigga10 = Nigga9.length;
        for (int Nigga11 = 0; Nigga11 < Nigga10; ++Nigga11) {
            BlockPos Nigga12 = Nigga5.add(Nigga9[Nigga11].getX(), 0, Nigga9[Nigga11].getZ());
            if (!(Minecraft.theWorld.getBlockState(Nigga12).getBlock() instanceof BlockAir)) continue;
            for (int Nigga13 = 0; Nigga13 < EnumFacing.values().length; ++Nigga13) {
                if (Minecraft.theWorld.getBlockState(Nigga12.offset(EnumFacing.values()[Nigga13])).getBlock().getMaterial() == Material.air) continue;
                return new BlockData(Nigga2, Nigga12.offset(EnumFacing.values()[Nigga13]), Nigga3[EnumFacing.values()[Nigga13].ordinal()], null);
            }
        }
        return null;
    }

    public static int getEmptyHotbarSlot() {
        for (int Nigga = 0; Nigga < 9; ++Nigga) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[Nigga] != null) continue;
            return Nigga;
        }
        return -1;
    }

    public int getSlot() {
        for (int Nigga = 0; Nigga < 9; ++Nigga) {
            Scaffold Nigga2;
            ItemStack Nigga3 = Nigga2.mc.thePlayer.inventory.mainInventory[Nigga];
            if (Nigga3 == null || !Nigga2.isValid(Nigga3) || Nigga3.stackSize <= 0) continue;
            return Nigga;
        }
        return -1;
    }

    @Override
    public void onDisable() {
        Scaffold Nigga;
        super.onDisable();
        isPlaceTick = false;
        Nigga.towerTimer.reset();
        Nigga.mc.timer.timerSpeed = Float.intBitsToFloat(1.1453975E9f ^ 0x7BC560FF);
        if (Nigga.mode.getMode().equals(Qprot0.0("\u7a9f\u71ca\u41d2\u818d\ub2e7\uff10"))) {
            Nigga.placeDelay.disabled = false;
        }
    }

    public int getBlockCount() {
        Scaffold Nigga;
        int Nigga2 = 0;
        for (int Nigga3 = 0; Nigga3 < Nigga.mc.thePlayer.inventory.mainInventory.length; ++Nigga3) {
            ItemStack Nigga4 = Nigga.mc.thePlayer.inventory.mainInventory[Nigga3];
            if (Nigga4 == null || !Nigga.isValid(Nigga4) || Nigga4.stackSize < 1) continue;
            Nigga2 += Nigga4.stackSize;
        }
        return Nigga2;
    }

    @Override
    public void onEnable() {
        Scaffold Nigga;
        super.onEnable();
        Nigga.boostSpeed = Nigga.boost.getValue() + 1.0;
        if (Nigga.mode.getMode().equals(Qprot0.0("\u7a9f\u71ca\u41d2\u59c6\u4ab8\uff10"))) {
            Nigga.placeDelay.value = 205.0;
            Nigga.placeDelay.disabled = true;
        }
        if (Nigga.mc.thePlayer != null) {
            Nigga.startY = Nigga.mc.thePlayer.posY - 1.0;
        } else {
            Nigga.toggle();
        }
    }

    public boolean isValid(ItemStack Nigga) {
        if (Nigga.getItem() instanceof ItemBlock) {
            Scaffold Nigga2;
            boolean Nigga3 = false;
            ItemBlock Nigga4 = (ItemBlock)Nigga.getItem();
            for (int Nigga5 = 0; Nigga5 < Nigga2.noobBlocks.size(); ++Nigga5) {
                if (!Nigga4.getBlock().equals(Nigga2.noobBlocks.get(Nigga5))) continue;
                Nigga3 = true;
            }
            return !Nigga3;
        }
        return false;
    }

    public void moveBlocksToHotbar() {
        Scaffold Nigga;
        boolean Nigga2 = false;
        if (Scaffold.getEmptyHotbarSlot() == -1 && Nigga.notifTimer.hasTimeElapsed((long)-1454055766 ^ 0xFFFFFFFFA954D76EL, true)) {
            Client.notifications.notifs.add(new Notification(Qprot0.0("\u7a81\u71c8\u41c7\u8948\ubbd9\uff07\u8c23\u16fb"), Qprot0.0("\u7a9c\u71c4\u4186\u897d\ubbcf\uff09\u8c2c\u16fa\u79e8\u109c\u752b\uaf4c\uf80c\u5ce8\ucff2\ue6b5\u42e8\ucccf"), Float.intBitsToFloat(1.04536339E9f ^ 0x7E4EFACF), Float.intBitsToFloat(2.13349722E9f ^ 0x7F2A9563), Notification.notificationType.WARNING));
        }
        if (Scaffold.getEmptyHotbarSlot() != -1) {
            for (int Nigga3 = 0; Nigga3 < Nigga.mc.thePlayer.inventory.mainInventory.length; ++Nigga3) {
                ItemStack Nigga4;
                if (Nigga3 <= 8 || Nigga2 || (Nigga4 = Nigga.mc.thePlayer.inventory.mainInventory[Nigga3]) == null || !Nigga.isValid(Nigga4) || !Block.getBlockFromItem(Nigga4.getItem()).isFullBlock()) continue;
                Nigga.mc.playerController.windowClick(Nigga.mc.thePlayer.inventoryContainer.windowId, Nigga3, 0, 1, Nigga.mc.thePlayer);
                Nigga2 = true;
            }
        }
    }

    private class BlockData {
        public EnumFacing enumFacing;
        public Scaffold this$0;
        public BlockPos blockPos;

        public BlockData(Scaffold scaffold, BlockPos blockPos, EnumFacing enumFacing, BlockData blockData) {
            this(scaffold, blockPos, enumFacing);
        }

        public static BlockPos access$0(BlockData blockData) {
            return blockData.blockPos;
        }

        public static EnumFacing access$1(BlockData blockData) {
            return blockData.getFacing();
        }

        public EnumFacing getFacing() {
            BlockData Nigga;
            return Nigga.enumFacing;
        }

        public BlockPos getPosition() {
            BlockData Nigga;
            return Nigga.blockPos;
        }

        public BlockData(Scaffold scaffold, BlockPos Nigga, EnumFacing Nigga2) {
            BlockData Nigga3;
            Nigga3.this$0 = scaffold;
            Nigga3.blockPos = Nigga;
            Nigga3.enumFacing = Nigga2;
        }

        public static BlockPos access$2(BlockData blockData) {
            return blockData.getPosition();
        }

        public static {
            throw throwable;
        }
    }
}

