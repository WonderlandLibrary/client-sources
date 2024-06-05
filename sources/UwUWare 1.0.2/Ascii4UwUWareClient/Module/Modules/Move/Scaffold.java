package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Events.World.EventPostUpdate;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.RotationUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.setBlockAndFacing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.*;

public class Scaffold extends Module {

    private final float[] rotations = new float[2];
    private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
    private final List<Block> badBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence);
    private BlockData blockData;
    public Option <Boolean> safewalk = new Option("safewalk", "safewalk", true);
    //public Option<Boolean> blockfly = new Option("Down", "Down", false);
    public Option<Boolean> tower = new Option("Tower", "Tower", true);
    public Option<Boolean> keeprots = new Option("Keep Rotation", "Keep Rotation", true);
    public Option<Boolean> swing = new Option("swing", "swing", true);
    public Option<Boolean> keepy = new Option("keepy", "keepy", true);
    public Option<Boolean> eagle = new Option("eagle", "eagle", false);
    public Option<Boolean> edge = new Option("eage", "eage", true);
    public Option<Boolean> raycast = new Option("raycast", "raycast", true);
    private final Numbers <Double> expand = new Numbers<Double>("expand", "expand", 1.00, 0.00, 5.00, 0.01);
    private final Numbers <Double> delay = new Numbers<Double>("Delay", "Delay", 50.0, 0.0, 1000.0, 10.0);
    private final Numbers <Double> eageOffset = new Numbers<Double>("Edge Offset", "Edge Offset", 0.13, 0.0, 5D, 0.01);
    public Option<Boolean> keepsprint = new Option("keepsprint", "keepsprint", true);
    public Option<Boolean> silent = new Option("Silent", "Silent", false);
    int stage = 0;
    public static boolean isPlaceTick = false;
    public static boolean stopWalk = false;
    private double startY;
    public TimerUtil towerTimer = new TimerUtil();
    private int count;
    private static BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private final TimerUtil timer = new TimerUtil();


    //public Setting boost;

    float oldPitch = 0;
    private RotationUtils RayCastUtil;

    public Scaffold() {
        super("Scaffold", new String[]{"Scaffold", "BlockFly"}, ModuleType.Move);
        addValues ( ScafMode );
        addValues ( TowerMode );
        addValues ( this.expand );
        addValues ( this.delay );
        addValues(this.eageOffset);
        addValues(this.silent);
        addValues ( this.keepy );
        addValues(edge);
        //addValues ( this.blockfly );
        addValues ( this.eagle );
        addValues ( this.raycast );
        addValues ( this.tower );
        addValues ( this.keepsprint );
        addValues ( this.keeprots );
        addValues ( this.safewalk );
        addValues ( this.swing );

    }
    public Mode<Enum> ScafMode = new Mode("SC-Mode", "SC-Mode", ScaffoldMode.values(), ScaffoldMode.Normal);
    public Mode<Enum> TowerMode = new Mode("TowerMode", "TowerMode", TowerModeXD.values(), TowerModeXD.Hypixel);

    float yaw = 0;
    float pitch = 0;

    private boolean isBlockUnder() {
        for (int i = (int) (Minecraft.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos( Minecraft.thePlayer.posX, i, Minecraft.thePlayer.posZ);
            if (Minecraft.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        slotTimer.reset();
        ticks = 0;
        if (Minecraft.thePlayer !=null) {
            startY = Minecraft.thePlayer.posY;
        }
    }

    float lastYaw = 0;

    public void fakeJump() {
        Minecraft.thePlayer.isAirBorne = true;
        Minecraft.thePlayer.triggerAchievement(StatList.jumpStat);

    }

    int ticks = 0;

    @EventHandler
    public void onMotionUpdate(EventPreUpdate event) {
        setSuffix ( ScafMode.getModeAsString () );
        switch (ScafMode.getModeAsString ()) {
            case "Normal":
                int slot = this.getSlot ();
                stopWalk = (getBlockCount () == 0 || slot == -1) && safewalk.getValue ().booleanValue ();
                isPlaceTick = keeprots.getValue ().booleanValue () ? blockData != null && slot != -1 : blockData != null && slot != -1 && Minecraft.theWorld.getBlockState ( new BlockPos ( Minecraft.thePlayer ).add ( 0, -1, 0 ) ).getBlock () == Blocks.air;
                if (slot == -1) {
                    moveBlocksToHotbar ();

                    return;
                }
                if (!keepsprint.getValue ().booleanValue ()) {
                    Minecraft.thePlayer.setSprinting ( false );
                    mc.gameSettings.keyBindSprint.pressed = false;
                    Minecraft.thePlayer.sendQueue.addToSendQueueSilent ( new C0BPacketEntityAction ( Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING ) );
                }
                this.blockData = getBlockData ();
                if (this.blockData == null) {
                    return;
                }

                // tower and towermove
                if (mc.gameSettings.keyBindJump.isKeyDown () && tower.getValue ().booleanValue () && !Minecraft.thePlayer.isMoving () && !Minecraft.thePlayer.isPotionActive ( Potion.jump )) {
                    setSuffix ( ScafMode.getModeAsString () );
                    switch (TowerMode.getModeAsString ()) {
                        case "Hypixel":
                            EntityPlayerSP player = Minecraft.thePlayer;
                            if (!MoveUtils.isOnGround ( 0.79 ) || Minecraft.thePlayer.onGround) {
                                player.motionY = 0.41985;
                                stage = 1;
                            }
                            if (towerTimer.hasReached ( 1500 )) {
                                towerTimer.reset ();
                                player.motionY = -1;
                            }


                        case "Packet":
                            if (Minecraft.thePlayer.onGround) {
                                Minecraft.thePlayer.setPosition ( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.99, Minecraft.thePlayer.posZ );
                                Minecraft.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.41999998688698, Minecraft.thePlayer.posZ, false ) );
                                Minecraft.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.7531999805212, Minecraft.thePlayer.posZ, false ) );
                            }
                        case "Cubecraft":
                            count++;
                            Minecraft.thePlayer.motionX = 0;
                            Minecraft.thePlayer.motionZ = 0;
                            Minecraft.thePlayer.jumpMovementFactor = 0;
                            if (MoveUtils.isOnGround ( 2 ))
                                if (count == 1) {
                                    Minecraft.thePlayer.motionY = 0.41;
                                } else {

                                    Minecraft.thePlayer.motionY = 0.47;
                                    count = 0;
                                }
                    }

                } else {
                    towerTimer.reset ();
                }

                if (isPlaceTick) {
                    /*Rotation targetRotation = new Rotation ( setBlockAndFacing.BlockUtil.getDirectionToBlock ( blockData.getPosition ().getX (), blockData.getPosition ().getY (), blockData.getPosition ().getZ (), blockData.getFacing () )[0], 79.44f );
                    Rotation limitedRotation = setBlockAndFacing.BlockUtil.limitAngleChange ( new Rotation ( yaw, event.getPitch () ), targetRotation, (float) ThreadLocalRandom.current ().nextDouble ( 20, 30 ) );
                    yaw = getRotations(blockData.getPosition(), blockData.getFacing())[0];
                    pitch = limitedRotation.getPitch ();*/
                    event.setYaw(Minecraft.thePlayer.rotationYaw - 180);
                    event.setPitch(79);
                    Minecraft.thePlayer.rotationPitchHead = (event.getPitch());

                }
            case "cubecraft":
                if (eagle.getValue ().booleanValue ()) {
                    BlockPos pos = new BlockPos ( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1, Minecraft.thePlayer.posZ );
                    boolean air = Minecraft.theWorld.getBlockState ( pos ).getBlock () instanceof BlockAir;
                    setSneaking ( air );

                }

                rotated = false;
                currentPos = null;
                currentFacing = null;

                BlockPos pos = new BlockPos ( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0D, Minecraft.thePlayer.posZ );
                if (Minecraft.theWorld.getBlockState ( pos ).getBlock () instanceof BlockAir) {
                    setBlockAndFacing ( pos );

                    if (currentPos != null) {
                        float[] facing = setBlockAndFacing.BlockUtil.getDirectionToBlock ( currentPos.getX (), currentPos.getY (), currentPos.getZ (), currentFacing );

                        float yaw = facing[0] + randomNumber ( 3, -3 );
                        float pitch = Math.min ( 90, facing[1] + 9 + randomNumber ( 3, -3 ) );

                        rotations[0] = yaw;
                        rotations[1] = pitch;

                        rotated = !raycast.getValue ().booleanValue () || rayTrace ( yaw, pitch );


                    }
                } else {
                    if (keeprots.getValue ().booleanValue ()) {
                        event.setYaw(Minecraft.thePlayer.rotationYaw - 180);
                        event.setPitch(79);
                    }
                }
        }
        Minecraft.thePlayer.rotationYawHead = event.getYaw ();
        //mc.thePlayer.rotationPitchHead = event.getPitch();
        Minecraft.thePlayer.renderYawOffset = event.getYaw ();

    }



    @EventHandler
    public void onPostUpdate(EventPostUpdate event) {
        //setSuffix ( TowerMode.getModeAsString () );

        if (!this.keepsprint.getValue ().booleanValue ()) {
                Minecraft.thePlayer.setSprinting(false);
                Minecraft.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));

                mc.gameSettings.keyBindSprint.pressed = false;

        }

        int slot = this.getSlot ();
        double x = Minecraft.thePlayer.posX;
        double z = Minecraft.thePlayer.posZ;
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float YAW = Minecraft.thePlayer.rotationYaw;
        if (!Minecraft.thePlayer.isCollidedHorizontally){
            double[] coords = getExpandCoords(x,z,forward,strafe,YAW);
            x = coords[0];
            z = coords[1];
        }
        BlockPos pos = new BlockPos ( x, Minecraft.thePlayer.posY - 1, z );
        if (slot != -1 && this.blockData != null) {
            final int currentSlot = Minecraft.thePlayer.inventory.currentItem;
            if (pos.getBlock () instanceof BlockAir) {
                    Minecraft.thePlayer.inventory.currentItem = slot;
                if (this.getPlaceBlock ( this.blockData.getPosition (), this.blockData.getFacing () )) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue ( new C09PacketHeldItemChange ( currentSlot ) );
                }
            } else {
                mc.timer.timerSpeed = 1.0f;
            }
            if (silent.getValue()) {
                Minecraft.thePlayer.inventory.currentItem = currentSlot;
            }
            switch (TowerMode.getModeAsString ()) {
                case "Packet":
                    for (int i = 0; i < 9; i++) {
                        if (Minecraft.thePlayer.inventory.getStackInSlot ( i ) == null)
                            continue;
                        if (Minecraft.thePlayer.inventory.getStackInSlot ( i ).getItem () instanceof ItemBlock) {
                            Minecraft.thePlayer.sendQueue.addToSendQueueSilent ( new C09PacketHeldItemChange ( Minecraft.thePlayer.inventory.currentItem = i ) );
                        }
                    }
                    if (currentPos != null) {
                        if (timer.hasReached ( this.delay.getValue () ) && rotated) {
                            if (Minecraft.thePlayer.getCurrentEquippedItem () != null && Minecraft.thePlayer.getCurrentEquippedItem ().getItem () instanceof ItemBlock) {
                                if (Minecraft.playerController.onPlayerRightClick ( Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getCurrentEquippedItem (), currentPos, currentFacing, new Vec3 ( currentPos.getX () * 0.5, currentPos.getY () * 0.5, currentPos.getZ () * 0.5 ) )) {
                                    timer.reset ();
                                    if (swing.getValue ().booleanValue ()) {
                                        Minecraft.thePlayer.swingItem ();
                                    } else {
                                        mc.getNetHandler ().addToSendQueueSilent ( new C0APacketAnimation () );
                                    }

                                }
                            }
                        }
                    }
            }
        }
    }

    private boolean getPlaceBlock(final BlockPos pos, final EnumFacing facing) {
        final Vec3 eyesPos = new Vec3( Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.posZ);
        Vec3i data = this.blockData.getFacing().getDirectionVec();
        if (timer.hasReached( this.delay.getValue() )){
            if (edge.getValue() ? Minecraft.playerController.onPlayerRightClick( Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing))) && isOnEdgeWithOffset(eageOffset.getValue()) : Minecraft.playerController.onPlayerRightClick( Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing)))) {
                if (this.swing.getValue ().booleanValue ()) {
                    Minecraft.thePlayer.swingItem();
                } else {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }

                timer.reset();
                return true;
            }


        }
        return false;
    }

    private Vec3 getVec3(BlockData data) {
        BlockPos pos = data.getPosition();
        EnumFacing face = data.getFacing();
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 0.5D;
        double z = (double) pos.getZ() + 0.5D;
        x += (double) face.getFrontOffsetX() / 2.0D;
        z += (double) face.getFrontOffsetZ() / 2.0D;
        y += (double) face.getFrontOffsetY() / 2.0D;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            y += this.randomNumber(0.49D, 0.5D);
        } else {
            x += this.randomNumber(0.3D, -0.3D);
            z += this.randomNumber(0.3D, -0.3D);
        }

        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3D, -0.3D);
        }

        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3D, -0.3D);
        }

        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static boolean rayTrace(float yaw, float pitch) {
        Vec3 vec3 = Minecraft.thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 = RotationUtils.getVectorForRotation (new float[]{yaw, pitch});
        Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);


        MovingObjectPosition result = Minecraft.theWorld.rayTraceBlocks(vec3, vec32, false);


        return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && currentPos.equals(result.getBlockPos());
    }

    static Random rng = new Random();

    public static int getRandom(final int floor, final int cap) {
        return floor + rng.nextInt(cap - floor + 1);
    }

    public static Color rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }

    public static float[] getRotations(BlockPos block, EnumFacing face) {
        double x = block.getX() + 0.5 - Minecraft.thePlayer.posX +  (double) face.getFrontOffsetX()/2;
        double z = block.getZ() + 0.5 - Minecraft.thePlayer.posZ +  (double) face.getFrontOffsetZ()/2;
        double y = (block.getY() + 0.5);
        double d1 = Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
        if (yaw < 0.0F) {
            yaw += 360f;
        }
        return new float[]{yaw, pitch};
    }

    public void setBlockAndFacing(BlockPos var1) {

        //if(!shouldDownwards()) {
        if (Minecraft.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;

        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;

        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, -2);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, 2);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, -2);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 2);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, -2);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 2);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 0);
            currentFacing = EnumFacing.DOWN;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 1);
            currentFacing = EnumFacing.WEST;
        }
    }

    public void getExpandBlock(BlockPos var1) {

        //if(!shouldDownwards()) {
        if (Minecraft.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;

        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;

        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, -2);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, 2);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, -2);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 2);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, -2);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 2);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 0);
            currentFacing = EnumFacing.DOWN;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 1);
            currentFacing = EnumFacing.WEST;
        }
    }

    private float[] aimAtLocation(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
        double d1 = paramBlockPos.getX() + 0.5D - Minecraft.thePlayer.posX + paramEnumFacing.getFrontOffsetX() / 2.0D;
        double d2 = paramBlockPos.getZ() + 0.5D - Minecraft.thePlayer.posZ + paramEnumFacing.getFrontOffsetZ() / 2.0D;
        double d3 = Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight() - (paramBlockPos.getY() + 0.5D);
        double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
        float f1 = (float) (Math.atan2(d2, d1) * 180.0D / 3.141592653589793D) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0D / 3.141592653589793D);
        if (f1 < 0.0F) {
            f1 += 360.0F;
        }
        return new float[]{f1, f2};
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.setSneaking(false);
    }

    private void setSneaking(boolean b) {
        KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
        mc.gameSettings.keyBindSneak.pressed = b;
    }

    public BlockData getBlockData() {
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        double yValue = 0;
        /*if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockfly.getValue ().booleanValue ()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
            yValue -= 0.6;
        }*/
        BlockPos aa = new BlockPos( Minecraft.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0, yValue, 0);
        BlockPos playerpos = aa;

        if(expand.getValue() > 0 && Minecraft.thePlayer.isMoving()){
            playerpos = aa.offset( Minecraft.thePlayer.getHorizontalFacing());
        }
        boolean tower = !this.tower.getValue ().booleanValue () && !Minecraft.thePlayer.isMoving();
        if (/*!this.blockfly.getValue ().booleanValue () &&*/ this.keepy.getValue ().booleanValue () && !tower && Minecraft.thePlayer.isMoving()) {
            playerpos = new BlockPos(new Vec3( Minecraft.thePlayer.getPositionVector().xCoord, this.startY, Minecraft.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
        } else {
            this.startY = Minecraft.thePlayer.posY;
        }

        for (EnumFacing facing : EnumFacing.values()) {
            if (playerpos.offset(facing).getBlock().getMaterial() != Material.air) {
                return new BlockData(playerpos.offset(facing), invert[facing.ordinal()]);
            }
        }
        final BlockPos[] addons = {
                new BlockPos(-1, 0, 0),
                new BlockPos(1, 0, 0),
                new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};

        for (int length2 = addons.length, j = 0; j < length2; ++j) {
            final BlockPos offsetPos = playerpos.add(addons[j].getX(), 0, addons[j].getZ());
            if (Minecraft.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                for (int k = 0; k < EnumFacing.values().length; ++k) {
                    if (Minecraft.theWorld.getBlockState(offsetPos.offset(EnumFacing.values()[k])).getBlock().getMaterial() != Material.air) {

                        return new BlockData(offsetPos.offset(EnumFacing.values()[k]), invert[EnumFacing.values()[k].ordinal()]);
                    }
                }
            }
        }

        return null;
    }

    int slotIndex = 0;
    TimerUtil slotTimer = new TimerUtil();

    private int getSlot() {
        ArrayList<Integer> slots = new ArrayList<>();
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                slots.add(k);
            }
        }
        if (slots.isEmpty()) {
            return -1;
        }
        /*if (slotTimer.hasReached(150)) {
            if (slotIndex >= slots.size() || slotIndex == slots.size() - 1) {
                slotIndex = 0;
            } else {
                slotIndex++;
            }
            slotTimer.reset();
        }*/
        return slots.get(slotIndex);
    }

    @EventHandler
    public void on2D(EventRender2D event) {
        if (this.getSlot() != -1) {
            ItemStack stack = Minecraft.thePlayer.inventory.getStackInSlot(getSlot());
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, GuiScreen.width / 2 - 10, GuiScreen.height + 20);
            GL11.glPopMatrix();
            FontLoaders.roboto14.drawCenteredString(getBlockCount() + " Blocks left", GuiScreen.width / 2, GuiScreen.height / 2 +5, new Color(185, 230, 255).getRGB());
        }
    }


    private boolean isValid(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBlock) {
            boolean isBad = false;

            ItemBlock block = (ItemBlock) itemStack.getItem();
            for (int i = 0; i < this.badBlocks.size(); i++) {
                if (block.getBlock().equals(this.badBlocks.get(i))) {
                    isBad = true;
                }
            }

            return !isBad;
        }
        return false;
    }

    public int getBlockCount() {
        int count = 0;
        for (int k = 0; k < Minecraft.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                count += itemStack.stackSize;
            }
        }
        return count;
    }

    public class BlockData {
        private final BlockPos blockPos;
        private final EnumFacing enumFacing;

        public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }

        public EnumFacing getFacing() {
            return this.enumFacing;
        }

        public BlockPos getPosition() {
            return this.blockPos;
        }
    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (!isHotbarFull()) {
            for (int k = 0; k < Minecraft.thePlayer.inventory.mainInventory.length; ++k) {
                if (k > 8 && !added) {
                    final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
                    if (itemStack != null && this.isValid(itemStack)) {
                        shiftClick(k);
                        added = true;
                    }
                }
            }
        }
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[k];
            if (itemStack != null) {
                count++;
            }
        }
        return count == 8;
    }

    public float setSmooth(float current, float target, float speed) {
        if (target - current > 0) {
            current -= speed;
        } else {
            current += speed;
        }
        return current;
    }

    public static void shiftClick(int slot) {
        Minecraft.playerController.windowClick( Minecraft.thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.thePlayer );
        Minecraft.playerController.windowClick( Minecraft.thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.thePlayer );
    }

    public double[] getExpandCoords(double x, double z, double forward, double strafe, float YAW){
        BlockPos underPos = new BlockPos(x, Minecraft.thePlayer.posY - 1, z);
        Block underBlock = Minecraft.theWorld.getBlockState(underPos).getBlock();
        double xCalc = -999, zCalc = -999;
        double dist = 0;
        double expandDist = expand.getValue()*2;
        while(!isAirBlock(underBlock)){
            xCalc = x;
            zCalc = z;
            dist ++;
            if(dist > expandDist){
                dist = expandDist;
            }
            xCalc += (forward * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f)) + strafe * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f))) * dist;
            zCalc += (forward * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f)) - strafe * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f))) * dist;
            if(dist == expandDist){
                break;
            }
            underPos = new BlockPos(xCalc, Minecraft.thePlayer.posY - 1, zCalc);
            underBlock = Minecraft.theWorld.getBlockState(underPos).getBlock();
        }
        return new double[]{xCalc,zCalc};
    }

    public boolean isAirBlock(Block block) {
        if (block.getMaterial().isReplaceable()) {
            return !(block instanceof BlockSnow) || !(block.getBlockBoundsMaxY() > 0.125);
        }

        return false;
    }

    public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }

    //Thx To domi
    private boolean isOnEdgeWithOffset(double paramDouble) {
        double d1 = Minecraft.thePlayer.posX;
        double d2 = Minecraft.thePlayer.posY;
        double d3 = Minecraft.thePlayer.posZ;
        BlockPos blockPos1 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 - paramDouble);
        BlockPos blockPos2 = new BlockPos(d1 - paramDouble, d2 - 0.5D, d3 + paramDouble);
        BlockPos blockPos3 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 + paramDouble);
        BlockPos blockPos4 = new BlockPos(d1 + paramDouble, d2 - 0.5D, d3 - paramDouble);
        return (Minecraft.thePlayer.worldObj.getBlockState(blockPos1).getBlock() == Blocks.air && Minecraft.thePlayer.worldObj.getBlockState(blockPos2).getBlock() == Blocks.air && Minecraft.thePlayer.worldObj.getBlockState(blockPos3).getBlock() == Blocks.air && Minecraft.thePlayer.worldObj.getBlockState(blockPos4).getBlock() == Blocks.air);
    }

    public enum ScaffoldMode{
        Normal
    }
    public enum TowerModeXD{
        Hypixel , Packet
    }

}