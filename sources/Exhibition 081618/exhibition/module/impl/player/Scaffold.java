package exhibition.module.impl.player;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventTick;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.data.SettingsMap;
import exhibition.module.impl.combat.Killaura;
import exhibition.module.impl.movement.Fly;
import exhibition.module.impl.movement.LongJump;
import exhibition.module.impl.movement.Speed;
import exhibition.util.MathUtils;
import exhibition.util.NetUtil;
import exhibition.util.PlayerUtil;
import exhibition.util.Timer;
import exhibition.util.render.Colors;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class Scaffold
extends Module {
    private static final List<Block> blacklistedBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.beacon, Blocks.ladder, Blocks.sapling, Blocks.oak_fence, Blocks.red_flower, Blocks.yellow_flower, Blocks.flower_pot, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.sand, Blocks.tallgrass, Blocks.tripwire_hook, Blocks.tripwire, Blocks.gravel, Blocks.dispenser, Blocks.dropper, Blocks.crafting_table, Blocks.furnace, Blocks.redstone_torch, Blocks.standing_sign, Blocks.wall_sign);
    private BlockData blockBelowData;
    private Timer timer = new Timer();
    private Timer towerTimer = new Timer();
    private String TOWER = "TOWER";
    private String MODE = "MODE";
    private String SWING = "SWING";

    public static List<Block> getBlacklistedBlocks() {
        return blacklistedBlocks;
    }

    public Scaffold(ModuleData data) {
        super(data);
        this.settings.put(this.TOWER, new Setting(this.TOWER, true, "Helps you build up faster."));
        this.settings.put(this.MODE, new Setting(this.MODE, new Options("Mode", "Normal", new String[]{"Normal", "Watchdog", "Old"}), "Scaffold method."));
        this.settings.put(this.SWING, new Setting(this.SWING, true, "Place blocks without swinging client side."));
    }

    @Override
    public void onEnable() {
        Module[] modules;
        for (Module module : modules = new Module[]{(Module)Client.getModuleManager().get(Fly.class), (Module)Client.getModuleManager().get(LongJump.class), (Module)Client.getModuleManager().get(Speed.class)}) {
            if (!module.isEnabled()) continue;
            module.toggle();
            Notifications.getManager().post("Movement Check", "Disabled extra modules.", 250L, Notifications.Type.NOTIFY);
        }
        this.towerTimer.reset();
    }

    @Override
    public void onDisable() {
        if (Scaffold.mc.thePlayer.isSwingInProgress) {
            Scaffold.mc.thePlayer.swingProgress = 0.0f;
            Scaffold.mc.thePlayer.swingProgressInt = 0;
            Scaffold.mc.thePlayer.isSwingInProgress = false;
        }
        Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Scaffold.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
    }

    public float getDirection() {
        float var1 = Scaffold.mc.thePlayer.rotationYaw;
        if (Scaffold.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        forward = Scaffold.mc.thePlayer.moveForward < 0.0f ? -0.5f : (Scaffold.mc.thePlayer.moveForward > 0.0f ? 0.5f : 1.0f);
        if (Scaffold.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Scaffold.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= 0.017453292f;
    }

    @RegisterEvent(events={EventTick.class, EventMotionUpdate.class, EventRenderGui.class})
    public void onEvent(Event event) {
        String currentMode = ((Options)((Setting)this.settings.get(this.MODE)).getValue()).getSelected();
        if (event instanceof EventRenderGui) {
            ScaledResolution res = new ScaledResolution(mc, Scaffold.mc.displayWidth, Scaffold.mc.displayHeight);
            int color = Colors.getColor(255, 0, 0, 150);
            if (this.getBlockCount() >= 64 && 128 > this.getBlockCount()) {
                color = Colors.getColor(255, 255, 0, 150);
            } else if (this.getBlockCount() >= 128) {
                color = Colors.getColor(0, 255, 0, 150);
            }
            GlStateManager.enableBlend();
            Scaffold.mc.fontRendererObj.drawStringWithShadow(this.getBlockCount() + "", res.getScaledWidth() / 2 - Scaffold.mc.fontRendererObj.getStringWidth(this.getBlockCount() + "") / 2, res.getScaledHeight() / 2 - 25, color);
            GlStateManager.disableBlend();
        }
        if (((Module)Client.getModuleManager().get(Killaura.class)).isEnabled() && currentMode.equalsIgnoreCase("Watchdog") && (Killaura.target != null || Killaura.loaded.size() > 0)) {
            return;
        }
        if (event instanceof EventMotionUpdate) {
            this.setSuffix(currentMode);
            EventMotionUpdate em = (EventMotionUpdate)event;
            if (!currentMode.equalsIgnoreCase("Watchdog")) {
                boolean mineplex = currentMode.equalsIgnoreCase("Old");
                if (em.isPre()) {
                    BlockPos blockBelow;
                    this.blockBelowData = null;
                    double x = Scaffold.mc.thePlayer.posX;
                    double y = Scaffold.mc.thePlayer.posY - 1.0;
                    double z = Scaffold.mc.thePlayer.posZ;
                    if (!(mineplex && this.material(new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.5, Scaffold.mc.thePlayer.posZ)) != Material.air || Scaffold.mc.thePlayer.isSneaking() || Scaffold.mc.theWorld.getBlockState(blockBelow = new BlockPos(x, y, z)).getBlock() != Blocks.air && Scaffold.mc.theWorld.getBlockState(blockBelow).getBlock() != Blocks.snow_layer && Scaffold.mc.theWorld.getBlockState(blockBelow).getBlock() != Blocks.tallgrass || !this.timer.delay(100.0f))) {
                        BlockData blockData = this.blockBelowData = mineplex ? this.getBlockData(blockBelow, blacklistedBlocks) : this.getBlockData(blockBelow);
                        if (this.blockBelowData != null) {
                            float[] rotations = Scaffold.getRotationsBlock(this.blockBelowData.position, this.blockBelowData.face);
                            em.setYaw(rotations[0]);
                            em.setPitch(rotations[1]);
                        }
                    }
                } else if (em.isPost()) {
                    if (!Scaffold.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        this.towerTimer.reset();
                    }
                    if (this.blockBelowData != null && ((Boolean)((Setting)this.settings.get(this.TOWER)).getValue()).booleanValue() && this.getBlockCount() > 0 && Scaffold.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                        Scaffold.mc.thePlayer.motionX = 0.0;
                        Scaffold.mc.thePlayer.motionZ = 0.0;
                        Scaffold.mc.thePlayer.motionY = 0.42;
                        if (this.towerTimer.delay(1500.0f)) {
                            Scaffold.mc.thePlayer.motionY = -0.28;
                            this.towerTimer.reset();
                        }
                    }
                    if (!Scaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed) || this.timer.delay(100.0f)) {
                        for (int i = 36; i < 45; ++i) {
                            ItemStack is;
                            Item item;
                            if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = (is = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) instanceof ItemBlock) || blacklistedBlocks.contains(((ItemBlock)item).getBlock()) || ((ItemBlock)item).getBlock().getLocalizedName().toLowerCase().contains("chest") || this.blockBelowData == null) continue;
                            Scaffold.mc.rightClickDelayTimer = 2;
                            int currentItem = Scaffold.mc.thePlayer.inventory.currentItem;
                            Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                            Scaffold.mc.thePlayer.inventory.currentItem = i - 36;
                            Scaffold.mc.playerController.updateController();
                            try {
                                if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.inventory.getCurrentItem(), this.blockBelowData.position, this.blockBelowData.face, new Vec3((double)this.blockBelowData.position.getX() + (double)Scaffold.randomFloat(0L) + (double)((float)this.blockBelowData.face.getDirectionVec().getX() * Scaffold.randomFloat(1L)), (double)this.blockBelowData.position.getY() + (double)Scaffold.randomFloat(2L) + (double)((float)this.blockBelowData.face.getDirectionVec().getY() * Scaffold.randomFloat(3L)), (double)this.blockBelowData.position.getZ() + (double)Scaffold.randomFloat(4L) + (double)((float)this.blockBelowData.face.getDirectionVec().getZ() * Scaffold.randomFloat(5L))))) {
                                    if (((Boolean)((Setting)this.settings.get(this.SWING)).getValue()).booleanValue()) {
                                        NetUtil.sendPacket(new C0APacketAnimation());
                                    } else {
                                        Scaffold.mc.thePlayer.swingItem();
                                    }
                                    this.timer.reset();
                                }
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                            Scaffold.mc.thePlayer.inventory.currentItem = currentItem;
                            Scaffold.mc.playerController.updateController();
                            Scaffold.mc.thePlayer.setSprinting(false);
                            this.timer.reset();
                            return;
                        }
                    }
                }
            } else {
                double x = Scaffold.mc.thePlayer.posX;
                double y = Scaffold.mc.thePlayer.posY - 0.8;
                double z = Scaffold.mc.thePlayer.posZ;
                if (Scaffold.mc.thePlayer.onGround && Scaffold.mc.thePlayer.isCollidedVertically) {
                    double forward = Scaffold.mc.thePlayer.movementInput.moveForward;
                    double strafe = Scaffold.mc.thePlayer.movementInput.moveStrafe;
                    float yaw = Scaffold.mc.thePlayer.rotationYaw;
                    x += forward * 0.5 * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * 0.5 * Math.sin(Math.toRadians(yaw + 90.0f));
                    z += forward * 0.5 * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * 0.5 * Math.cos(Math.toRadians(yaw + 90.0f));
                }
                BlockPos pos = new BlockPos(x, y, z);
                if (em.isPre()) {
                    Vec3 vec;
                    if (this.invCheck()) {
                        for (int i = 9; i < 36; ++i) {
                            Item item;
                            if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) instanceof ItemBlock) || blacklistedBlocks.contains(((ItemBlock)item).getBlock()) || ((ItemBlock)item).getBlock().getLocalizedName().toLowerCase().contains("chest")) continue;
                            this.swap(i, 7);
                            break;
                        }
                    }
                    if ((vec = this.getBlockLook(pos)) != null) {
                        float[] rotations = this.look(vec);
                        em.setYaw(rotations[0]);
                        em.setPitch(rotations[1]);
                    }
                } else {
                    if (Scaffold.mc.gameSettings.keyBindJump.getIsKeyPressed() && this.getBlockCount() > 0) {
                        if (((Boolean)((Setting)this.settings.get(this.TOWER)).getValue()).booleanValue()) {
                            Scaffold.mc.thePlayer.motionZ = 0.0;
                            Scaffold.mc.thePlayer.motionX = 0.0;
                            Scaffold.mc.thePlayer.setPosition(Scaffold.mc.thePlayer.posX + 1.0, Scaffold.mc.thePlayer.posY, Scaffold.mc.thePlayer.posZ + 1.0);
                            Scaffold.mc.thePlayer.setPosition(Scaffold.mc.thePlayer.prevPosX, Scaffold.mc.thePlayer.posY, Scaffold.mc.thePlayer.prevPosZ);
                        }
                    } else {
                        this.towerTimer.reset();
                    }
                    if (!this.material(pos).isReplaceable()) {
                        return;
                    }
                    int var1 = this.getBlockSlot();
                    if (var1 == -1) {
                        return;
                    }
                    int var2 = Scaffold.mc.thePlayer.inventory.currentItem;
                    Scaffold.mc.thePlayer.inventory.currentItem = var1;
                    if (Scaffold.mc.gameSettings.keyBindJump.getIsKeyPressed() && this.getBlockCount() > 0) {
                        if (!PlayerUtil.isMoving() || ((Boolean)((Setting)this.settings.get(this.TOWER)).getValue()).booleanValue()) {
                            Scaffold.mc.thePlayer.motionZ = 0.0;
                            Scaffold.mc.thePlayer.motionX = 0.0;
                            if (Scaffold.mc.thePlayer.posY != (double)(pos.getY() + 1) && this.material(new BlockPos(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY - 1.2, Scaffold.mc.thePlayer.posZ)) != Material.air) {
                                if (((Boolean)((Setting)this.settings.get(this.TOWER)).getValue()).booleanValue()) {
                                    Scaffold.mc.thePlayer.jump();
                                    Scaffold.mc.thePlayer.motionX = -(Math.sin(this.getDirection()) * 0.0);
                                    Scaffold.mc.thePlayer.motionZ = Math.cos(this.getDirection()) * 0.0;
                                } else {
                                    Scaffold.mc.thePlayer.motionY = 0.41999998688697815;
                                }
                                if (this.towerTimer.delay(1500.0f)) {
                                    Scaffold.mc.thePlayer.motionY = -0.28;
                                    this.towerTimer.reset();
                                }
                            }
                        } else {
                            double thing = MathUtils.roundToPlace(Scaffold.mc.thePlayer.posY - (double)((int)Scaffold.mc.thePlayer.posY), 3);
                            if (thing == 0.001 && !this.towerTimer.delay(500.0f) && !Scaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                Scaffold.mc.thePlayer.motionY = -0.28;
                                Scaffold.mc.thePlayer.jumpTicks = 0;
                            }
                        }
                    }
                    if (!(!(Scaffold.mc.thePlayer.inventory.getStackInSlot(var1).getItem() instanceof ItemBlock) || blacklistedBlocks.contains(((ItemBlock)Scaffold.mc.thePlayer.inventory.getStackInSlot(var1).getItem()).getBlock()) || Scaffold.mc.thePlayer.isPotionActive(Potion.moveSpeed) && !this.timer.delay(100.0f) || this.place(pos) || this.place(pos.add(1, 0, 0)) || this.place(pos.add(0, 0, 1)) || this.place(pos.add(-1, 0, 0)))) {
                        this.place(pos.add(0, 0, -1));
                    }
                    Scaffold.mc.thePlayer.inventory.currentItem = var2;
                }
            }
        }
    }

    public float[] look(Vec3 vector) {
        double diffX = vector.xCoord - Scaffold.mc.thePlayer.posX;
        double diffY = vector.yCoord - (Scaffold.mc.thePlayer.posY + (double)Scaffold.mc.thePlayer.getEyeHeight());
        double diffZ = vector.zCoord - Scaffold.mc.thePlayer.posZ;
        double distance = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, distance)));
        return new float[]{Scaffold.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Scaffold.mc.thePlayer.rotationYaw), Scaffold.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Scaffold.mc.thePlayer.rotationPitch)};
    }

    private Vec3 checkPos(BlockPos pos) {
        Vec3 eye = new Vec3(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY + (double)Scaffold.mc.thePlayer.getEyeHeight(), Scaffold.mc.thePlayer.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos blockPos = pos.offset(side);
            EnumFacing otherSide = side.getOpposite();
            Vec3 vec3 = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            Vec3 vec32 = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (!(eye.squareDistanceTo(vec3.addVector(0.5, 0.5, 0.5)) < eye.squareDistanceTo(vec32.addVector(0.5, 0.5, 0.5))) || !this.blockPos(blockPos).canCollideCheck(this.blockState(blockPos), false)) continue;
            Vec3 vec = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()).addVector(0.5, 0.5, 0.5).add(new Vec3(otherSide.getDirectionVec().getX(), otherSide.getDirectionVec().getY(), otherSide.getDirectionVec().getZ()).multi(0.5));
            return vec;
        }
        return null;
    }

    private boolean place(BlockPos pos) {
        Vec3 eye = new Vec3(Scaffold.mc.thePlayer.posX, Scaffold.mc.thePlayer.posY + (double)Scaffold.mc.thePlayer.getEyeHeight(), Scaffold.mc.thePlayer.posZ);
        for (EnumFacing side : EnumFacing.values()) {
            Vec3 vec;
            BlockPos blockPos = pos.offset(side);
            EnumFacing otherSide = side.getOpposite();
            Vec3 vec3 = new Vec3(pos.getX(), pos.getY(), pos.getZ());
            Vec3 vec32 = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (!(eye.squareDistanceTo(vec3.addVector(0.5, 0.5, 0.5)) < eye.squareDistanceTo(vec32.addVector(0.5, 0.5, 0.5))) || !this.blockPos(blockPos).canCollideCheck(this.blockState(blockPos), false) || !(eye.squareDistanceTo(vec = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()).addVector(0.5, 0.5, 0.5).add(new Vec3(otherSide.getDirectionVec().getX(), otherSide.getDirectionVec().getY(), otherSide.getDirectionVec().getZ()).multi(0.5))) <= 18.0)) continue;
            if (Scaffold.mc.playerController.onPlayerRightClick(Scaffold.mc.thePlayer, Scaffold.mc.theWorld, Scaffold.mc.thePlayer.getCurrentEquippedItem(), blockPos, otherSide, vec)) {
                Scaffold.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                this.timer.reset();
            }
            return true;
        }
        return false;
    }

    public static boolean isValid(ItemStack item) {
        if (Scaffold.isEmpty(item)) {
            return false;
        }
        if (item.getUnlocalizedName().equalsIgnoreCase("tile.cactus")) {
            return false;
        }
        if (!(item.getItem() instanceof ItemBlock)) {
            return false;
        }
        return !blacklistedBlocks.contains(((ItemBlock)item.getItem()).getBlock());
    }

    public IBlockState blockState(BlockPos pos) {
        return Scaffold.mc.theWorld.getBlockState(pos);
    }

    public Block blockPos(BlockPos pos) {
        return this.blockState(pos).getBlock();
    }

    public Material material(BlockPos pos) {
        return this.blockPos(pos).getMaterial();
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack == null;
    }

    private Vec3 getVector(BlockData blockData) {
        int places = 13;
        double randX = MathUtils.roundToPlace(Scaffold.randomFloat(1L), places);
        double randY = MathUtils.roundToPlace(Scaffold.randomFloat(20L), places);
        double randZ = MathUtils.roundToPlace(Scaffold.randomFloat(55L), places);
        double vx = blockData.position.getX() + blockData.face.getDirectionVec().getX();
        double vy = blockData.position.getY() + blockData.face.getDirectionVec().getY();
        double vz = blockData.position.getZ() + blockData.face.getDirectionVec().getZ();
        switch (blockData.face) {
            case UP: {
                vx += randX;
                vz += randZ;
                break;
            }
            case SOUTH: 
            case NORTH: {
                vx += randX;
                vy += randY;
                break;
            }
            case EAST: 
            case WEST: {
                vy += randY;
                vz += randZ;
            }
        }
        return new Vec3(vx, vy, vz);
    }

    protected void swap(int slot, int hotbarNum) {
        Scaffold.mc.playerController.windowClick(Scaffold.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Scaffold.mc.thePlayer);
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            ItemStack item;
            if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !Scaffold.isValid(item = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack())) continue;
            return false;
        }
        return true;
    }

    private BlockData getBlockData(BlockPos pos, List list) {
        return !list.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? new BlockData(pos.add(0, -1, 0), EnumFacing.UP) : (!list.contains(Scaffold.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : (!list.contains(Scaffold.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? new BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : (!list.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : (!list.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : null))));
    }

    private Vec3 getBlockLook(BlockPos pos) {
        Vec3 output = null;
        output = this.checkPos(pos);
        if (output == null && (output = this.checkPos(pos.add(1, 0, 0))) == null && (output = this.checkPos(pos.add(0, 0, 1))) == null && (output = this.checkPos(pos.add(-1, 0, 0))) == null) {
            output = this.checkPos(pos.add(0, 0, -1));
        }
        return output;
    }

    private BlockData getBlockData(BlockPos pos) {
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock())) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock())) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock())) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock())) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add = pos.add(-1, 0, 0);
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new BlockData(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new BlockData(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new BlockData(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add2 = pos.add(1, 0, 0);
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new BlockData(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new BlockData(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new BlockData(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add3 = pos.add(0, 0, -1);
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new BlockData(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new BlockData(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new BlockData(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        BlockPos add4 = pos.add(0, 0, 1);
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new BlockData(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new BlockData(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!blacklistedBlocks.contains(Scaffold.mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new BlockData(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }

    public static float[] getRotationsBlock(BlockPos pos, EnumFacing facing) {
        double offsetX = 0.0;
        double offsetY = 0.0;
        double offsetZ = 0.0;
        switch (facing) {
            case NORTH: {
                offsetX = 0.5;
                offsetZ = 0.0;
                offsetY = 0.9;
                break;
            }
            case EAST: {
                offsetX = 1.0;
                offsetZ = 0.5;
                offsetY = 0.9;
                break;
            }
            case SOUTH: {
                offsetX = 0.5;
                offsetZ = 1.0;
                offsetY = 0.9;
                break;
            }
            case WEST: {
                offsetX = 0.0;
                offsetZ = 0.5;
                offsetY = 0.9;
                break;
            }
            case UP: {
                offsetX = 0.5;
                offsetY = 1.0;
                offsetZ = 0.5;
            }
        }
        double d0 = (double)pos.getX() + offsetX - Scaffold.mc.thePlayer.posX;
        double d1 = (double)pos.getY() + offsetY - (Scaffold.mc.thePlayer.posY + (double)Scaffold.mc.thePlayer.getEyeHeight());
        double d2 = (double)pos.getZ() + offsetZ - Scaffold.mc.thePlayer.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f = (float)(Math.atan2(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        float f1 = (float)(-(Math.atan2(d1, d3) * 180.0 / 3.141592653589793));
        return new float[]{f, f1};
    }

    public static float randomFloat(long seed) {
        seed = System.currentTimeMillis() + seed;
        return 0.3f + (float)new Random(seed).nextInt(70000000) / 1.0E8f + 1.458745E-8f;
    }

    private int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            ItemStack itemStack = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || itemStack.stackSize <= 0 || blacklistedBlocks.stream().anyMatch(e -> e.equals(((ItemBlock)itemStack.getItem()).getBlock()))) continue;
            return i - 36;
        }
        return -1;
    }

    private int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (!Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack is = Scaffold.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (!(is.getItem() instanceof ItemBlock) || is.stackSize <= 0 || blacklistedBlocks.contains(((ItemBlock)item).getBlock()) || is.getUnlocalizedName().contains("fence") || is.getUnlocalizedName().contains("pane")) continue;
            blockCount += is.stackSize;
        }
        return blockCount;
    }

    private class BlockData {
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

}

