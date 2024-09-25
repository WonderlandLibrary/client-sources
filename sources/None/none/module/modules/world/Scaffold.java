package none.module.modules.world;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.EventSystem;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.Event3D;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventPushBlock;
import none.event.events.EventTick;
import none.event.events.StrafeEvent;
import none.module.Category;
import none.module.Module;
import none.module.modules.player.InvCleaner;
import none.module.modules.render.ClientColor;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.BlockUtil;
import none.utils.ClickType;
import none.utils.MoveUtils;
import none.utils.PlayerUtil;
import none.utils.RayCastUtil;
import none.utils.RenderingUtil;
import none.utils.RotationUtils;
import none.utils.TimeHelper;
import none.utils.render.Colors;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class Scaffold extends Module{
	public static BooleanValue safewalk = new BooleanValue("SafeWalk", true);
	public static BooleanValue downside = new BooleanValue("DownSide", false);
	private static List<Block> blacklistedBlocks;
	
	public static List<Block> getBlacklistedBlocks() {
        return blacklistedBlocks;
    }
	
	private NumberValue<Integer> delay = new NumberValue<>("Delay", 0, 0, 1000);
	
	private static String[] modes = {"Normal", "AAC", "Cubecraft"};
	public static ModeValue scaffoldmode = new ModeValue("Scaffold-Mode", "Normal", modes);
	
	private BooleanValue autoblock = new BooleanValue("AutoBlock", true);
	private BooleanValue noswing = new BooleanValue("NoSwing", false);
	private BooleanValue esp = new BooleanValue("ESP", true);
	private BooleanValue eagle = new BooleanValue("Eagle", false);
	private BooleanValue keepRotations = new BooleanValue("KeepRotations", true);
	private BooleanValue sprint = new BooleanValue("KeepSprint", false);
	private BooleanValue tower = new BooleanValue("Tower", true);
	private BooleanValue towermove = new BooleanValue("TowerMove", false);
	private static String[] towermodes = {"Normal", "AAC", "AAC2", "AAC364"};
	public static ModeValue towermode = new ModeValue("Tower-Mode", "Normal", towermodes);
	private NumberValue<Float> aactower = new NumberValue<>("AACTower", 0.4F, 0.05F, 1.0F);
	private static String[] rotationsmode = {"Basic", "Basic2", "Smart"};
    public static ModeValue rotationsmodes = new ModeValue("Rotation", "Basic", rotationsmode);
    private BooleanValue whilecleaning = new BooleanValue("While-InvCleaner", false);
    public static BooleanValue ItemBlock = new BooleanValue("ItemBlockingRender", true);
	private BooleanValue placeable = new BooleanValue("Placeable", false);
	public static BooleanValue sameY = new BooleanValue("SameY", false);
	private String[] placeevents = {"PRE", "NOW", "POST"};
	public ModeValue placemode = new ModeValue("PlaceEvent", "Post", placeevents);
	public BooleanValue rotationStrafeValue = new BooleanValue("RotationStrafe",false);
	public BooleanValue swapMove = new BooleanValue("SwapMovement", true);
	public BooleanValue newpick = new BooleanValue("NewPicker", false);
    
    private boolean rotated = false;
    private TimeHelper timer = new TimeHelper();
    
    private float[] rotations = new float[2];
    private int currentitem, cubespoof = -1;
    BlockData blockData;
    int count;
    public int posY;
    
    private static boolean blocking;

	public Scaffold() {
		super("Scaffold", "Scaffold", Category.WORLD, Keyboard.KEY_Z);
		blacklistedBlocks = Arrays.asList(
				Blocks.crafting_table,
                Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava,
                Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars,
                Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore,
                Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt,
                Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore,
                Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate,
                Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily,
                Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, 
                Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.ice);
	}

	@Override
	protected void onEnable() {
		super.onEnable();
		if (mc.thePlayer == null) return;
		posY = (int)mc.thePlayer.posY;
		rotations[0] = mc.thePlayer.rotationYaw;
		rotations[1] = mc.thePlayer.rotationPitch;
		count = 0;
		if (scaffoldmode.getSelected().equalsIgnoreCase("Cubecraft") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
		if (scaffoldmode.getSelected().equalsIgnoreCase("Cubecraft")) {
			currentitem = mc.thePlayer.inventory.currentItem;
		}
		blocking = false;
		cubespoof = -1;
		blockData = null;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		rotations[0] = 0;
		rotations[1] = 0;
		count = 0;
		blocking = false;
		cubespoof = -1;
		blockData = null;
		if (mc.thePlayer == null) return;
		mc.timer.timerSpeed = 1f;
		if (scaffoldmode.getSelected().equalsIgnoreCase("Cubecraft")) {
			mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(currentitem));
    		mc.thePlayer.inventory.currentItem = currentitem;
		}else {
			mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(currentitem));
    		mc.thePlayer.inventory.currentItem = currentitem;
		}
		
		if (mc.thePlayer.isSwingInProgress) {
            mc.thePlayer.swingProgress = 0;
            mc.thePlayer.swingProgressInt = 0;
            mc.thePlayer.isSwingInProgress = false;
        }
	}
	
	private boolean rayTrace(float yaw, float pitch) {
        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 = RayCastUtil.getVectorForRotation(yaw, pitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord + 0.25, vec31.yCoord + 0.25, vec31.zCoord + 0.25);


        MovingObjectPosition result = mc.theWorld.rayTraceBlocks(vec3, vec32, false);


        return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockData.position.equals(result.getBlockPos());
    }
	
	private void setSneaking(boolean b) {
        KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
        try {
            Field field = sneakBinding.getClass().getDeclaredField("pressed");
            field.setAccessible(true);
            field.setBoolean(sneakBinding, b);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
	
	public static boolean getBlocking() {
		return blocking;
	}
	
	private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if ((block.getMaterial().isSolid() || !block.isTranslucent() || block.isFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet
                || block instanceof BlockSnow || block instanceof BlockSkull)
                && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer) && !(block instanceof BlockAir)) {
            return true;
        }
        return false;
	}
	
	private BlockData getBlockData(BlockPos pos) {
			if (downside.getObject() && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (isPosSolid(pos.add(0, 1, 0))) {
		            return new BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
		        }
			
				BlockPos pos111 = pos.add(0, 0, 0);
//				if (isPosSolid(pos111.add(0, 1, 0))) {
//		            return new BlockData(pos111.add(0, 1, 0), EnumFacing.DOWN);
//		        }
	            if (isPosSolid(pos111.add(-1, 0, 0))) {
	                return new BlockData(pos111.add(-1, 0, 0), EnumFacing.EAST);
	            }
	            if (isPosSolid(pos111.add(1, 0, 0))) {
	                return new BlockData(pos111.add(1, 0, 0), EnumFacing.WEST);
	            }
	            if (isPosSolid(pos111.add(0, 0, 1))) {
	                return new BlockData(pos111.add(0, 0, 1), EnumFacing.NORTH);
	            }
	            if (isPosSolid(pos111.add(0, 0, -1))) {
	                return new BlockData(pos111.add(0, 0, -1), EnumFacing.SOUTH);
	            }
	            
	            BlockPos pos112 = pos.add(1, 0, 0);
//				if (isPosSolid(pos111.add(0, 1, 0))) {
//		            return new BlockData(pos111.add(0, 1, 0), EnumFacing.DOWN);
//		        }
	            if (isPosSolid(pos112.add(-1, 0, 0))) {
	                return new BlockData(pos112.add(-1, 0, 0), EnumFacing.EAST);
	            }
	            if (isPosSolid(pos112.add(1, 0, 0))) {
	                return new BlockData(pos112.add(1, 0, 0), EnumFacing.WEST);
	            }
	            if (isPosSolid(pos112.add(0, 0, 1))) {
	                return new BlockData(pos112.add(0, 0, 1), EnumFacing.NORTH);
	            }
	            if (isPosSolid(pos112.add(0, 0, -1))) {
	                return new BlockData(pos112.add(0, 0, -1), EnumFacing.SOUTH);
	            }
	            
	            BlockPos pos113 = pos.add(-1, 0, 0);
//				if (isPosSolid(pos111.add(0, 1, 0))) {
//		            return new BlockData(pos111.add(0, 1, 0), EnumFacing.DOWN);
//		        }
	            if (isPosSolid(pos113.add(-1, 0, 0))) {
	                return new BlockData(pos113.add(-1, 0, 0), EnumFacing.EAST);
	            }
	            if (isPosSolid(pos113.add(1, 0, 0))) {
	                return new BlockData(pos113.add(1, 0, 0), EnumFacing.WEST);
	            }
	            if (isPosSolid(pos113.add(0, 0, 1))) {
	                return new BlockData(pos113.add(0, 0, 1), EnumFacing.NORTH);
	            }
	            if (isPosSolid(pos113.add(0, 0, -1))) {
	                return new BlockData(pos113.add(0, 0, -1), EnumFacing.SOUTH);
	            }
	            
	            BlockPos pos114 = pos.add(0, 0, 1);
//				if (isPosSolid(pos111.add(0, 1, 0))) {
//		            return new BlockData(pos111.add(0, 1, 0), EnumFacing.DOWN);
//		        }
	            if (isPosSolid(pos114.add(-1, 0, 0))) {
	                return new BlockData(pos114.add(-1, 0, 0), EnumFacing.EAST);
	            }
	            if (isPosSolid(pos114.add(1, 0, 0))) {
	                return new BlockData(pos114.add(1, 0, 0), EnumFacing.WEST);
	            }
	            if (isPosSolid(pos114.add(0, 0, 1))) {
	                return new BlockData(pos114.add(0, 0, 1), EnumFacing.NORTH);
	            }
	            if (isPosSolid(pos114.add(0, 0, -1))) {
	                return new BlockData(pos114.add(0, 0, -1), EnumFacing.SOUTH);
	            }
	            
	            BlockPos pos115 = pos.add(0, 0, -1);
//				if (isPosSolid(pos111.add(0, 1, 0))) {
//		            return new BlockData(pos111.add(0, 1, 0), EnumFacing.DOWN);
//		        }
	            if (isPosSolid(pos115.add(-1, 0, 0))) {
	                return new BlockData(pos115.add(-1, 0, 0), EnumFacing.EAST);
	            }
	            if (isPosSolid(pos115.add(1, 0, 0))) {
	                return new BlockData(pos115.add(1, 0, 0), EnumFacing.WEST);
	            }
	            if (isPosSolid(pos115.add(0, 0, 1))) {
	                return new BlockData(pos115.add(0, 0, 1), EnumFacing.NORTH);
	            }
	            if (isPosSolid(pos115.add(0, 0, -1))) {
	                return new BlockData(pos115.add(0, 0, -1), EnumFacing.SOUTH);
	            }
			}else {
			
        	if (isPosSolid(pos.add(0, -1, 0))) {
                return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
            }
        	if (isPosSolid(pos.add(-1, 0, 0))) {
                return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            }
        	if (isPosSolid(pos.add(1, 0, 0))) {
                return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
            }
        	if (isPosSolid(pos.add(0, 0, 1))) {
                return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
            }
        	if (isPosSolid(pos.add(0, 0, -1))) {
                return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
            }
        	
            BlockPos pos1 = pos.add(-1, 0, 0);
            if (isPosSolid(pos1.add(0, -1, 0))) {
                return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos1.add(-1, 0, 0))) {
                return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos1.add(1, 0, 0))) {
                return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos1.add(0, 0, 1))) {
                return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos1.add(0, 0, -1))) {
                return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
            }
            
            BlockPos pos2 = pos.add(1, 0, 0);
            if (isPosSolid(pos2.add(0, -1, 0))) {
                return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos2.add(-1, 0, 0))) {
                return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos2.add(1, 0, 0))) {
                return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos2.add(0, 0, 1))) {
                return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos2.add(0, 0, -1))) {
                return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
            }
            
            BlockPos pos3 = pos.add(0, 0, 1);
            if (isPosSolid(pos3.add(0, -1, 0))) {
                return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos3.add(-1, 0, 0))) {
                return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos3.add(1, 0, 0))) {
                return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos3.add(0, 0, 1))) {
                return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos3.add(0, 0, -1))) {
                return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
            }
            
            BlockPos pos4 = pos.add(0, 0, -1);
            if (isPosSolid(pos4.add(0, -1, 0))) {
                return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos4.add(-1, 0, 0))) {
                return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos4.add(1, 0, 0))) {
                return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos4.add(0, 0, 1))) {
                return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos4.add(0, 0, -1))) {
                return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
            }
            
            BlockPos pos5 = pos.add(0, -1, 0);
            if (isPosSolid(pos5.add(0, -1, 0))) {
                return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos5.add(-1, 0, 0))) {
                return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos5.add(1, 0, 0))) {
                return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos5.add(0, 0, 1))) {
                return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos5.add(0, 0, -1))) {
                return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos19 = pos.add(-2, 0, 0);
            if (isPosSolid(pos19.add(0, -1, 0))) {
                return new BlockData(pos19.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos19.add(-1, 0, 0))) {
                return new BlockData(pos19.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos19.add(1, 0, 0))) {
                return new BlockData(pos19.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos19.add(0, 0, 1))) {
                return new BlockData(pos19.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos19.add(0, 0, -1))) {
                return new BlockData(pos19.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos29 = pos.add(2, 0, 0);
            if (isPosSolid(pos29.add(0, -1, 0))) {
                return new BlockData(pos29.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos29.add(-1, 0, 0))) {
                return new BlockData(pos29.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos29.add(1, 0, 0))) {
                return new BlockData(pos29.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos29.add(0, 0, 1))) {
                return new BlockData(pos29.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos29.add(0, 0, -1))) {
                return new BlockData(pos29.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos39 = pos.add(0, 0, 2);
            if (isPosSolid(pos39.add(0, -1, 0))) {
                return new BlockData(pos39.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos39.add(-1, 0, 0))) {
                return new BlockData(pos39.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos39.add(1, 0, 0))) {
                return new BlockData(pos39.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos39.add(0, 0, 1))) {
                return new BlockData(pos39.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos39.add(0, 0, -1))) {
                return new BlockData(pos39.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos49 = pos.add(0, 0, -2);
            if (isPosSolid(pos49.add(0, -1, 0))) {
                return new BlockData(pos49.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos49.add(-1, 0, 0))) {
                return new BlockData(pos49.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos49.add(1, 0, 0))) {
                return new BlockData(pos49.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos49.add(0, 0, 1))) {
                return new BlockData(pos49.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos49.add(0, 0, -1))) {
                return new BlockData(pos49.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos59 = pos.add(0, -2, 0);
            if (isPosSolid(pos59.add(0, -1, 0))) {
                return new BlockData(pos59.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos59.add(-1, 0, 0))) {
                return new BlockData(pos59.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos59.add(1, 0, 0))) {
                return new BlockData(pos59.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos59.add(0, 0, 1))) {
                return new BlockData(pos59.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos59.add(0, 0, -1))) {
                return new BlockData(pos59.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos69 = pos5.add(1, 0, 0);
            if (isPosSolid(pos69.add(0, -1, 0))) {
                return new BlockData(pos69.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos69.add(-1, 0, 0))) {
                return new BlockData(pos69.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos69.add(1, 0, 0))) {
                return new BlockData(pos69.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos69.add(0, 0, 1))) {
                return new BlockData(pos69.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos69.add(0, 0, -1))) {
                return new BlockData(pos69.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos79 = pos5.add(-1, 0, 0);
            if (isPosSolid(pos79.add(0, -1, 0))) {
                return new BlockData(pos79.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos79.add(-1, 0, 0))) {
                return new BlockData(pos79.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos79.add(1, 0, 0))) {
                return new BlockData(pos79.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos79.add(0, 0, 1))) {
                return new BlockData(pos79.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos79.add(0, 0, -1))) {
                return new BlockData(pos79.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos89 = pos5.add(0, 0, 1);
            if (isPosSolid(pos89.add(0, -1, 0))) {
                return new BlockData(pos89.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos89.add(-1, 0, 0))) {
                return new BlockData(pos89.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos89.add(1, 0, 0))) {
                return new BlockData(pos89.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos89.add(0, 0, 1))) {
                return new BlockData(pos89.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos89.add(0, 0, -1))) {
                return new BlockData(pos89.add(0, 0, -1), EnumFacing.SOUTH);
            }
            BlockPos pos99 = pos5.add(0, 0, -1);
            if (isPosSolid(pos99.add(0, -1, 0))) {
                return new BlockData(pos99.add(0, -1, 0), EnumFacing.UP);
            }
            if (isPosSolid(pos99.add(-1, 0, 0))) {
                return new BlockData(pos99.add(-1, 0, 0), EnumFacing.EAST);
            }
            if (isPosSolid(pos99.add(1, 0, 0))) {
                return new BlockData(pos99.add(1, 0, 0), EnumFacing.WEST);
            }
            if (isPosSolid(pos99.add(0, 0, 1))) {
                return new BlockData(pos99.add(0, 0, 1), EnumFacing.NORTH);
            }
            if (isPosSolid(pos99.add(0, 0, -1))) {
                return new BlockData(pos99.add(0, 0, -1), EnumFacing.SOUTH);
            }
			}
            return null;
    }
	
	public static void drawESP(EntityLivingBase entity, int color) {
		double x = entity.lastTickPosX
				+ (entity.posX - entity.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double y = entity.lastTickPosY
				+ (entity.posY - entity.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks;

		double z = entity.lastTickPosZ
				+ (entity.posZ - entity.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks;
		double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.8;
		double height = 0.1;
		Vec3 vec = new Vec3(x - width/2, y, z - width/2);
        Vec3 vec2 = new Vec3(x + width/2, y + height, z + width/2);
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderingUtil.enableGL2D();
        RenderingUtil.pre3D();
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 2);
        RenderingUtil.glColor(color);
        RenderingUtil.drawOutlinedBoundingBox(new AxisAlignedBB(
        		vec.xCoord - renderManager.renderPosX, vec.yCoord - renderManager.renderPosY, vec.zCoord - renderManager.renderPosZ,
        		vec2.xCoord - renderManager.renderPosX, vec2.yCoord - renderManager.renderPosY, vec2.zCoord - renderManager.renderPosZ));
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        RenderingUtil.disableGL2D();
    }
	
	private void ItemSpoof() {
        ItemStack is = new ItemStack(Item.getItemById(261));
        try {
        	if (newpick.getObject()) {
	        	int theSlot = -1;
	        	int lastmoreslot = theSlot;
	        	int lastmoresize = 1;
	            for (int i = 36; i < 45; i++) {
	                theSlot = i - 36;
	                if (!mc.thePlayer.inventoryContainer.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(i), is, true)
	                        && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
	                	if (isValid(mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize > 0) {
	                		if (mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize > lastmoresize) {
	                			lastmoreslot = theSlot;
	                			lastmoresize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
	                		}
	                    }
	                }
	            }
	            theSlot = lastmoreslot;
	            if (mc.thePlayer.inventory.currentItem != theSlot && theSlot != -1) {
	            	C09PacketHeldItemChange p = new C09PacketHeldItemChange(theSlot);
	            	cubespoof = theSlot;
	            	mc.thePlayer.inventory.currentItem = theSlot;
	            	mc.thePlayer.connection.getNetworkManager().sendPacketNoEvent(p);
	            	mc.playerController.updateController();
	            }
        	}else {
        		int theSlot = -1;
	        	int lastmoreslot = theSlot;
	        	int lastmoresize = 1;
	            for (int i = 36; i < 45; i++) {
	                theSlot = i - 36;
	                if (!mc.thePlayer.inventoryContainer.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(i), is, true)
	                        && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
	                	if (isValid(mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()) && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize > 0) {
	                		if (mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize > 0) {
	                			lastmoreslot = theSlot;
	                			lastmoresize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
	                		}
	                    }
	                }
	            }
	            theSlot = lastmoreslot;
	            if (mc.thePlayer.inventory.currentItem != theSlot && theSlot != -1) {
	            	C09PacketHeldItemChange p = new C09PacketHeldItemChange(theSlot);
	            	cubespoof = theSlot;
	            	mc.thePlayer.connection.getNetworkManager().sendPacketNoEvent(p);
	            	mc.thePlayer.inventory.currentItem = theSlot;
	            	mc.playerController.updateController();
	            }
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	private boolean hotbarContainBlock() {
        int i = 36;

        while (i < 45) {
            try {
                ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if ((stack == null) || (stack.getItem() == null) || !(stack.getItem() instanceof ItemBlock) || !isValid(stack.getItem())) {
                    i++;
                    continue;
                }
                return true;
            } catch (Exception e) {

            }
        }

        return false;

    }
	
	public int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && isValid(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
	
	protected void swap(int slot, int hotbarNum) {
//        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, ClickType.SWAP, mc.thePlayer);
    }

    private boolean invCheck() {
        for (int i = 36; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                Item item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                if (item instanceof ItemBlock && isValid(item)) {
                    return false;
                }
            }
        }
        return true;
    }
	
	public void getBestBlocks(){
    	
 	 	if(getBlockCount() == 0)
    		return;
 	 	if (invCheck()) {

            ItemStack is = new ItemStack(Item.getItemById(261));
            for (int i = 9; i < 36; i++) {

                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    Item item = mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                    int count = 0;
                    if (item instanceof ItemBlock && isValid(item)) {
                        for (int a = 36; a < 45; a++) {
                            if (mc.thePlayer.inventoryContainer.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(a), is, true)) {
                            	
                            	swap(i, a - 36);
                                count++;
                                break;
                            }
                        }

                        if (count == 0) {
                        	
                            swap(i, 7);
                        }
                        break;

                    }
                }
            }
        }
    }
	
	private boolean isValid(Item item) {
        if (!(item instanceof ItemBlock)) {
            return false;
        } else {
            ItemBlock iBlock = (ItemBlock) item;
            Block block = iBlock.getBlock();
            if (blacklistedBlocks.contains(block)) {
                return false;
            }
        }
        return true;
    }
	
	public void tower(String mode, EventPreMotionUpdate em) {
        BlockPos underPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
        BlockData data = getBlockData(underPos);
        if(!mc.gameSettings.keyBindJump.isKeyDown()){
        	if((towermove.getObject() && PlayerUtil.isMoving2()) && (mode.equalsIgnoreCase("AAC"))){
        		if (MoveUtils.isOnGround(0.76) && !MoveUtils.isOnGround(0.75) && mc.thePlayer.motionY > 0.23 && mc.thePlayer.motionY < 0.25) {
                    mc.thePlayer.motionY = (Math.round(mc.thePlayer.posY) - mc.thePlayer.posY);
                }
                if (MoveUtils.isOnGround(0.0001)) {   
                	if(mode.equalsIgnoreCase("AAC")){
                        mc.thePlayer.motionX *= 0.95;
                        mc.thePlayer.motionZ *= 0.95;   
                	}
                }else if(mc.thePlayer.motionY > 0.1 && mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 0.0001 && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 0.0001){
                   
                	mc.thePlayer.motionY = 0;
                }
        	}
        	return;
        }
        if (mode.equalsIgnoreCase("AAC")) {
        	//AAC
        	mc.thePlayer.onGround = false;
        	em.setOnGround(false);
            if (MoveUtils.isOnGround(0.76) && !MoveUtils.isOnGround(0.75) && mc.thePlayer.motionY > 0.23 && mc.thePlayer.motionY < 0.25) {
                mc.thePlayer.motionY = (Math.round(mc.thePlayer.posY) - mc.thePlayer.posY);
            }
            if(!PlayerUtil.isMoving2()){
            	mc.thePlayer.motionX = 0;
        		mc.thePlayer.motionZ = 0;
        		mc.thePlayer.jumpMovementFactor = 0;
            }
            if (MoveUtils.isOnGround(0.0001)) {            	
                mc.thePlayer.motionY = 0.42;
                mc.thePlayer.motionX *= 0.9;
                mc.thePlayer.motionZ *= 0.9;          	
            }else if(mc.thePlayer.posY >= Math.round(mc.thePlayer.posY) - 0.0001 && mc.thePlayer.posY <= Math.round(mc.thePlayer.posY) + 0.0001){
                mc.thePlayer.motionY = 0;
                mc.timer.timerSpeed = (float)aactower.getObject();
            }
            if (mc.timer.timerSpeed == aactower.getObject()) {
                mc.timer.timerSpeed = 1;
            }
        } else if(mode.equalsIgnoreCase("Normal")){
        	//Normal
            if (isPosSolid(underPos) && data != null) {
            	mc.thePlayer.motionY = 0.42;      		
                mc.thePlayer.motionX *= 0.75;
                mc.thePlayer.motionZ *= 0.75;
            }
            if(!towermove.getObject() && !PlayerUtil.isMoving2()){
        		mc.thePlayer.motionX = 0;
        		mc.thePlayer.motionZ = 0;
            }
        }
        else if(mode.equalsIgnoreCase("AAC2")){
        	//AAC2
        	double X = mc.thePlayer.posX; double Y = mc.thePlayer.posY; double Z = mc.thePlayer.posZ;
        	em.setPitch(90);
        	if(!PlayerUtil.isMoving2()){
           		mc.thePlayer.motionX = 0;
        		mc.thePlayer.motionZ = 0;
        		mc.thePlayer.jumpMovementFactor = 0;
        	}

        	int slot = mc.thePlayer.inventory.currentItem;
        	ItemSpoof();
        	for(int i = 0; i < 2; i++){
        		BlockPos underp = new BlockPos(X,mc.thePlayer.isCollidedVertically ? Y+i:Y-1.4+i, Z);
            	net.minecraft.block.Block block = mc.theWorld.getBlockState(underp).getBlock();
            	if(block.isTranslucent()){
            		PlayerControllerMP.canPlace = true;
            		mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), underp.add(0, 1, 0), EnumFacing.UP, new Vec3(0, 0, 0));
//            		mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, underp.add(0, 1, 0), EnumFacing.UP, new Vec3(0, 0, 0), EnumHand.MAIN_HAND);
            		PlayerControllerMP.canPlace = false;
            		mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
            		break;
            	}	
        	}
        	mc.thePlayer.inventory.currentItem = slot;
        }else if (mode.equalsIgnoreCase("AAC364")) {
        	//AAC364
        	if (mc.thePlayer.ticksExisted % 4 == 1) {
                mc.thePlayer.motionY = 0.42;
            } else if (mc.thePlayer.ticksExisted % 4 == 0) {
                mc.thePlayer.motionY = -0.22;
            }
        	MoveUtils.forward(mc.thePlayer.ticksExisted % 4 == 0 ? -0.035 : mc.thePlayer.ticksExisted % 4 == 1 ? 0.035 : 0);
        }
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, Event2D.class, Event3D.class, EventTick.class, EventPushBlock.class, EventPacket.class, EventTick.class, StrafeEvent.class})
	public void onEvent(Event event) {
		if (!isEnabled() || mc.currentScreen instanceof GuiMultiplayer) {
			rotations[0] = mc.thePlayer.rotationYawHead;
			rotations[1] = mc.thePlayer.rotationPitchHead;
			return;
		}
		
		if (scaffoldmode.getSelected().equalsIgnoreCase("Cubecraft") && !HWID.isHWID()) {
			return;
		}
        
		
		if ((!mc.thePlayer.isEntityAlive() || (mc.currentScreen != null && mc.currentScreen instanceof GuiGameOver))) {
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by death.", 3));
			evc("Scaffold disabled by death.");
			this.toggle();
			return;
		}
		if(mc.thePlayer.ticksExisted <= 1){
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by respawn.", 3));
			evc("Scaffold disabled by respawn.");
			this.toggle();

			rotations[0] = mc.thePlayer.rotationYawHead;
        	rotations[1] = mc.thePlayer.rotationPitchHead;
			return;
		}
		
		setDisplayName(this.getName() + ChatFormatting.WHITE + " " + scaffoldmode.getSelected() + " " + rotationsmodes.getSelected());
		
    	boolean shouldTower = tower.getObject();
		
		if(event instanceof EventPushBlock) {
        	EventPushBlock epb = (EventPushBlock)event;
        	if(towermode.getSelected().equalsIgnoreCase("AAC2")) {
                if (mc.gameSettings.keyBindJump.isKeyDown() && shouldTower && (towermove.getObject() || !PlayerUtil.isMoving2())) {
                    if(!PlayerUtil.isMoving2()) {
                    	epb.setCancelled(true);
                    }
                }
			}
        }
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (shouldTower) {
				if (towermode.getSelected().equalsIgnoreCase("AAC") && mc.gameSettings.keyBindJump.isKeyDown()) {
					if (ep.isIncoming() && (p instanceof S08PacketPlayerPosLook)) {
						S08PacketPlayerPosLook in = (S08PacketPlayerPosLook) ep.getPacket();
						in.yaw = mc.thePlayer.rotationYaw;
						in.pitch = mc.thePlayer.rotationPitch;
	//					ep.setCancelled(true);
					}
				}
			}
			
			if(p instanceof C09PacketHeldItemChange){
        		C09PacketHeldItemChange packet = (C09PacketHeldItemChange)p;
        		int slot = packet.getSlotId();
        		if(ep.isPre())
        		if(cubespoof == -1 || cubespoof != slot){
//        			ep.setCancelled(true);
        		}
        	}
        	if(cubespoof != -1){
                if(p instanceof C08PacketPlayerBlockPlacement){
                	C08PacketPlayerBlockPlacement pa = (C08PacketPlayerBlockPlacement)p;
                	ItemStack stack = pa.getStack();
                	if(!ItemStack.areItemsEqual(mc.thePlayer.inventory.getStackInSlot(cubespoof), stack)){
                		ep.setCancelled(true);
                	}
                }
                if(p instanceof C07PacketPlayerDigging){
                	C07PacketPlayerDigging pa = (C07PacketPlayerDigging)p;
                	net.minecraft.network.play.client.C07PacketPlayerDigging.Action act  = pa.getStatus();
                	if(act == net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM){
                		if(!ItemStack.areItemsEqual(mc.thePlayer.inventory.getStackInSlot(cubespoof), mc.thePlayer.inventory.getCurrentItem())){
                    		ep.setCancelled(true);
                    	}
                	}
                }
        	}
		}
		
		if (event instanceof StrafeEvent) {
			StrafeEvent strafeevent = (StrafeEvent) event;
			EventPacket packet = (EventPacket) EventSystem.getInstance(EventPacket.class);
			if (!rotationStrafeValue.getObject())
				return;
			
			if (!MoveUtils.isMoveKeyPressed())
				return;
			if (packet.getPacket() instanceof S08PacketPlayerPosLook) {
				S08PacketPlayerPosLook pos = (S08PacketPlayerPosLook) packet.getPacket();
				rotations[0] = pos.yaw;
				rotations[1] = pos.pitch;
			}
			float yaw = rotations[0];
			float strafe = strafeevent.getStrafe();
			float forward = strafeevent.getForward();
			float friction = strafeevent.getFriction();
			float f = strafe * strafe + forward * forward;
			
			if (f >= 1.0E-4F) {
				f = MathHelper.sqrt_float(f);
				
				if (f < 1.0F) {
					f = 1.0F;
				}
				
				f = friction / f;
				strafe = strafe * f;
				forward = forward * f;
				float f1 = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
				float f2 = MathHelper.cos(yaw * (float) Math.PI / 180.0F);
				mc.thePlayer.motionX += (double) (strafe * f2 - forward * f1);
				mc.thePlayer.motionZ += (double) (forward * f2 + strafe * f1);
			}
            
			strafeevent.setCancelled(true);
		}
		
		if (event instanceof EventTick) {
			if (!placemode.getSelected().equalsIgnoreCase("NOW")) return;
			if (scaffoldmode.getObject() != 2) {
				currentitem = mc.thePlayer.inventory.currentItem;
//				cubespoof = mc.thePlayer.inventory.currentItem;
			}
			if (blockData != null) {
				blocking = true;
				if (autoblock.getObject()) {
					ItemSpoof();
				}
                if (((placeable.getObject() && mc.thePlayer.swingProgress <= 1F) || !placeable.getObject()) && rotated && ((whilecleaning.getObject()) || (!whilecleaning.getObject() && (!InvCleaner.drop || !Client.instance.moduleManager.invCleaner.isEnabled())))) {
//                	if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
//                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
                		if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
                		}else if (cubespoof != -1) {
                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(cubespoof), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
                		}
                		timer.setLastMS();
                        if (eagle.getObject()) {
                                mc.thePlayer.motionX *= 0.2;
                                mc.thePlayer.motionZ *= 0.2;
                        }
//                    }
                	
                	if (noswing.getObject()) {
                		mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
                	}else {
                		mc.thePlayer.swingItem();
                	}
                }
                if (scaffoldmode.getObject() != 2) {
                	mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(currentitem));
	        		mc.thePlayer.inventory.currentItem = currentitem;
                }
                mc.playerController.updateController();
            }
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			
			if (e.isPre()) {
				if (!sameY.getObject() || mc.thePlayer.fallDistance > 1.25 || mc.thePlayer.onGround || mc.thePlayer.isCollidedHorizontally || !MoveUtils.isMoveKeyPressed()) {
					posY = (int)mc.thePlayer.posY;
				}
				getBestBlocks();
				if (scaffoldmode.getSelected().equalsIgnoreCase("Cubecraft")) {
					mc.thePlayer.setSprinting(false);
				}else {
					if (sprint.getObject() && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())) {
	                	mc.thePlayer.setSprinting(true);
	                }else {
	                	mc.thePlayer.setSprinting(false);
	                }
				}
				
				if (eagle.getObject()) {
					if (rotated) {
						setSneaking(true);
					} else {
						setSneaking(false);
					}
				}
	            rotated = false;
	            if (!hotbarContainBlock()) {
	            	blockData = null;
	            	return;
	            }
	            
	            if (downside.getObject() && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
	            	posY = (int)mc.thePlayer.posY;
	            	if (mc.gameSettings.keyBindJump.isKeyDown()) {
	            		posY -= 2.0;
	            	}else {
	            		posY -= 1.0;
	            	}
	            }
	            
	            BlockPos pos = new BlockPos(mc.thePlayer.posX, posY - 1, mc.thePlayer.posZ);
	            if (shouldTower && mc.gameSettings.keyBindJump.isKeyDown()) {
            		if (!towermove.getObject()) {
            			mc.thePlayer.motionX = 0;
            			mc.thePlayer.motionZ = 0;
            		}
	                if ((towermove.getObject() || (!towermove.getObject() && mc.thePlayer.motionX == 0 && mc.thePlayer.motionZ == 0))) {
	                    tower(towermode.getSelected(), e);
	                    posY = (int)mc.thePlayer.posY;
	                }
    			}else {
    				mc.timer.timerSpeed = 1F;
    			}
	            
	            if (blockData == null) {
	            	if (rotationsmodes.getSelected().equalsIgnoreCase("Smart")) {
	            		if (mc.thePlayer.getHorizontalFacing().equals(EnumFacing.NORTH)) {
	        				rotations[0] = 0;
	        			}else if (mc.thePlayer.getHorizontalFacing().equals(EnumFacing.SOUTH)) {
	        				rotations[0] = 180;
	        			}else if (mc.thePlayer.getHorizontalFacing().equals(EnumFacing.EAST)) {
	        				rotations[0] = 90;
	        			}else if (mc.thePlayer.getHorizontalFacing().equals(EnumFacing.WEST)) {
	        				rotations[0] = 270;
	        			}
	            	}
	            }
	            
	            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir && mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockAir) {
            		BlockData blockData = getBlockData(pos);
            		this.blockData = blockData;
	                if (blockData != null) {
	                    float facing[] = BlockUtil.getRotations(blockData.position, blockData.face);
	                    
	                    if (rotationsmodes.getSelected().equalsIgnoreCase("Smart")) {
	                    	facing = smart(blockData.position, blockData.face, e);
	                    }else if (rotationsmodes.getSelected().equalsIgnoreCase("Basic2")) {
	                    	facing = getRotationsAAC(blockData.position, blockData.face);
	                    }

	                    float yaw = facing[0];
	                    float pitch = facing[1];
	                    
	                    int delayed = delay.getInteger();
	                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
	                    	delayed = 50;
	                    }
	                    rotated = timer.hasTimeReached(delayed);
	                    if (timer.hasTimeReached(delayed) && scaffoldmode.getSelected().equalsIgnoreCase("AAC") && rayTrace(yaw, pitch)) {
	                    	rotated = true;
	                    }
	                    e.setYaw(yaw);
	                    e.setPitch(pitch);
	                    
	                    rotations[0] = yaw;
	                    rotations[1] = pitch;	                    
	                }
            	}
	            else 
	            	if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
            		BlockData blockData = getBlockData(pos);
            		this.blockData = blockData;
	                if (blockData != null) {
	                    float facing[] = BlockUtil.getRotations(blockData.position, blockData.face);
	                    
	                    if (rotationsmodes.getSelected().equalsIgnoreCase("Smart")) {
	                    	facing = smart(blockData.position, blockData.face, e);
	                    }else if (rotationsmodes.getSelected().equalsIgnoreCase("Basic2")) {
	                    	facing = getRotationsAAC(blockData.position, blockData.face);
	                    }

	                    float yaw = facing[0];
	                    float pitch = facing[1];
	                    
	                    int delayed = delay.getInteger();
	                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
	                    	delayed = 50;
	                    }
	                    e.setYaw(yaw);
	                    e.setPitch(pitch);
	                    
	                    rotations[0] = yaw;
	                    rotations[1] = pitch;
//	                    if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
	                    	rotated = timer.hasTimeReached(delayed);
	                    	if (timer.hasTimeReached(delayed) && scaffoldmode.getSelected().equalsIgnoreCase("AAC") && rayTrace(yaw, pitch)) {
	                    		rotated = true;
	                    	}	                    	
//	                    }
	                }
            	}
	            
	            if (keepRotations.getObject()) {
	            	if (shouldTower && towermode.getSelected().equalsIgnoreCase("AAC") && mc.gameSettings.keyBindJump.isKeyDown() && towermove.getObject()) {
	            		if (mc.thePlayer.motionX != 0 && mc.thePlayer.motionZ != 0) {
	            			e.setYaw(rotations[0]);
		            		e.setPitch(rotations[1]);
	            		}
	            	}else if (shouldTower && towermode.getSelected().equalsIgnoreCase("AAC") && mc.gameSettings.keyBindJump.isKeyDown()) {
	            		
	            	}else {
	            		e.setYaw(rotations[0]);
	            		e.setPitch(rotations[1]);
	            	}
	            }
	            
	            if (!placemode.getSelected().equalsIgnoreCase("PRE")) return;
	            
	            if (scaffoldmode.getObject() != 2) {
					currentitem = mc.thePlayer.inventory.currentItem;
//					cubespoof = mc.thePlayer.inventory.currentItem;
				}
				if (blockData != null) {
					blocking = true;
					if (autoblock.getObject()) {
						ItemSpoof();
					}
	                if (((placeable.getObject() && mc.thePlayer.swingProgress <= 1F) || !placeable.getObject()) && rotated && ((whilecleaning.getObject()) || (!whilecleaning.getObject() && (!InvCleaner.drop || !Client.instance.moduleManager.invCleaner.isEnabled())))) {
//	                	if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
//	                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
	                		if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
	                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//	                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
	                		}else if (cubespoof != -1) {
	                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(cubespoof), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//	                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
	                		}
	                		timer.setLastMS();
	                        if (eagle.getObject()) {
	                                mc.thePlayer.motionX *= 0.2;
	                                mc.thePlayer.motionZ *= 0.2;
	                        }
//	                    }
	                	
	                	if (noswing.getObject()) {
	                		mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
	                	}else {
	                		mc.thePlayer.swingItem();
	                	}
	                }
	                if (scaffoldmode.getObject() != 2) {
	                	mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(currentitem));
		        		mc.thePlayer.inventory.currentItem = currentitem;
	                }
	                mc.playerController.updateController();
	            }
			}else if (e.isPost()) {
				if (!placemode.getSelected().equalsIgnoreCase("POST")) return;
				
				if (scaffoldmode.getObject() != 2) {
					currentitem = mc.thePlayer.inventory.currentItem;
//					cubespoof = mc.thePlayer.inventory.currentItem;
				}
				if (blockData != null) {
					blocking = true;
					if (autoblock.getObject()) {
						ItemSpoof();
					}
	                if (((placeable.getObject() && mc.thePlayer.swingProgress <= 1F) || !placeable.getObject()) && rotated && ((whilecleaning.getObject()) || (!whilecleaning.getObject() && (!InvCleaner.drop || !Client.instance.moduleManager.invCleaner.isEnabled())))) {
//	                	if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
//	                        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
	                		if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock) {
	                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//	                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
	                		}else if (cubespoof != -1) {
	                			if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getStackInSlot(cubespoof), blockData.position, blockData.face, getVec3(blockData.position, blockData.face)));
//	                			mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, blockData.position, blockData.face, getVec3(blockData.position, blockData.face), EnumHand.MAIN_HAND);
	                		}
	                		timer.setLastMS();
	                        if (eagle.getObject()) {
	                                mc.thePlayer.motionX *= 0.2;
	                                mc.thePlayer.motionZ *= 0.2;
	                        }
//	                    }
	                	
	                	if (noswing.getObject()) {
	                		mc.thePlayer.connection.sendPacket(new C0APacketAnimation());
	                	}else {
	                		mc.thePlayer.swingItem();
	                	}
	                }
	                if (scaffoldmode.getObject() != 2) {
	                	mc.thePlayer.connection.sendPacket(new C09PacketHeldItemChange(currentitem));
		        		mc.thePlayer.inventory.currentItem = currentitem;
	                }
	                mc.playerController.updateController();
	            }
			}
		}
		
		if (event instanceof Event3D) {
			int renderColor = ClientColor.getColor();
			 if (esp.getObject()) {
				 drawESP(mc.thePlayer, !ClientColor.rainbow.getObject() ? renderColor : ClientColor.rainbow(1000));
				 if (blockData != null) {
					 //ABBox
					 RenderingUtil.pre3D();
					 mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
					 RenderingUtil.glColor(!ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(10000));
					 BlockPos place = blockData.position;
					 EnumFacing face = blockData.face;
					 double x1 = place.getX() - RenderManager.renderPosX;
					 double x2 = place.getX() - RenderManager.renderPosX + 1;
					 double y1 = place.getY() - RenderManager.renderPosY;
					 double y2 = place.getY() - RenderManager.renderPosY + 1;
					 double z1 = place.getZ() - RenderManager.renderPosZ;
					 double z2 = place.getZ() - RenderManager.renderPosZ + 1;
					 y1 += face.getFrontOffsetY();
					 if(face.getFrontOffsetX() < 0){
						 x2 += face.getFrontOffsetX();
					 }else{
						 x1 += face.getFrontOffsetX();
					 }
					 if(face.getFrontOffsetZ() < 0){
						 z2 += face.getFrontOffsetZ();
					 }else{
						 z1 += face.getFrontOffsetZ();
					 }
					 
					 RenderingUtil.drawBoundingBox(new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
					 GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
					 RenderingUtil.post3D();
				 }
			 }
		}
		
		if (event instanceof Event2D) {

            ScaledResolution res = new ScaledResolution(mc);
            int blockCount = getBlockCount();
            int color = Colors.getColor(255, 0, 0);
            if (blockCount >= 64 && 128 > blockCount) {
                color = Colors.getColor(255, 255, 0);
            } else if (blockCount >= 128) {
                color = Colors.getColor(0, 255, 0);
            }
//            Client.fm.getFont("BebasNeue").drawStringWithShadow(blockCount + "", res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(blockCount + "") / 2, res.getScaledHeight() / 2 - 25, color);
            mc.fontRendererObj.drawStringWithShadow(blockCount + "", res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(blockCount + "") / 2, res.getScaledHeight() / 2 - 25, color);
		}
	}
	
	public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.5;
        double z = pos.getZ() + 0.5;
        x += (double) face.getFrontOffsetX() / 2;
        z += (double) face.getFrontOffsetZ() / 2;
        y += (double) face.getFrontOffsetY() / 2;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += randomNumber(0.3, -0.3);
            z += randomNumber(0.3, -0.3);
        } else {
            y += randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += randomNumber(0.3, -0.3);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += randomNumber(0.3, -0.3);
        }
        return new Vec3(x, y, z);
    }
	
	public static double randomNumber(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }
	
	public static int randomNumber(int max, int min) {
		return Math.round(min + (float)Math.random() * ((max - min)));
	}
	
	private float[] smoothAim(BlockPos position, EnumFacing face){
		float[] facing = BlockUtil.getDirectionToBlock2(position, face, rotations[0], rotations[1]);
		float targetYaw = facing[0];
//		float yawFactor = targetYaw / 6F;
		float yawFactor = targetYaw / 12F;
		if (Math.abs(yawFactor) < 5) {
			yawFactor = targetYaw / 5F;
		}else if (Math.abs(yawFactor) > 5 && Math.abs(yawFactor) < 10) {
			yawFactor = targetYaw / 4.2F;
		}else if (Math.abs(yawFactor) > 10 && Math.abs(yawFactor) < 14) {
			yawFactor = targetYaw / 3.7F;
		}else if (Math.abs(yawFactor) > 14) {
			yawFactor = targetYaw / 2F;
		}
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			yawFactor = targetYaw/2F;
		}
		rotations[0] += yawFactor;
		float targetPitch = facing[1];
		float pitchFactor = targetPitch;
		rotations[1] += pitchFactor;
		return new float[]{rotations[0], rotations[1]};
	}
	
	float[] smart(BlockPos position, EnumFacing face, EventPreMotionUpdate e) {
		float facing[] = BlockUtil.getRotations(blockData.position, blockData.face);
		boolean up = mc.gameSettings.keyBindForward.isKeyDown(), left = mc.gameSettings.keyBindLeft.isKeyDown(), down = mc.gameSettings.keyBindBack.isKeyDown(), right = mc.gameSettings.keyBindRight.isKeyDown();
//		if (face.equals(EnumFacing.NORTH)) {
//			rotations[0] = 360;
//		}else if (face.equals(EnumFacing.SOUTH)) {
//			rotations[0] = 180;
//		}else if (face.equals(EnumFacing.EAST)) {
//			rotations[0] = 90;
//		}else if (face.equals(EnumFacing.WEST)) {
//			rotations[0] = 270;
//		}
		if ((up && left) || (up && right) || (down && left) || (down && right)) {
			if (up && left) {
				rotations[0] = e.getYaw() + 90 + 45;
			}else if (up && right) {
				rotations[0] = e.getYaw() + 180 + 45;
			}else if (down && left) {
				rotations[0] = e.getYaw() + 45;
			}else if (down && right) {
				rotations[0] = e.getYaw() - 90 + 45;
			}
			rotations[0] = RotationUtils.getNewAngle(rotations[0]);
			if (Math.abs(rotations[0]) >= 0 && Math.abs(rotations[0]) <= 45) {
				if (rotations[0] > 0) {
					rotations[0] = 45;
				}else if (rotations[0] < 0) {
					rotations[0] = -45;
				}
			}else if (Math.abs(rotations[0]) >= 45 && Math.abs(rotations[0]) <= 90) {
				if (rotations[0] > 0) {
					rotations[0] = 45;
				}else if (rotations[0] < 0) {
					rotations[0] = -45;
				}
			}else if (Math.abs(rotations[0]) >= 90 && Math.abs(rotations[0]) <= 135) {
				if (rotations[0] > 0) {
					rotations[0] = 135;
				}else if (rotations[0] < 0) {
					rotations[0] = -135;
				}
			}else if (Math.abs(rotations[0]) >= 135 && Math.abs(rotations[0]) <= 180) {
				if (rotations[0] > 0) {
					rotations[0] = 135;
				}else if (rotations[0] < 0) {
					rotations[0] = -135;
				}
			}
		}else {
			if (face.equals(EnumFacing.NORTH)) {
				rotations[0] = 360;
			}else if (face.equals(EnumFacing.SOUTH)) {
				rotations[0] = 180;
			}else if (face.equals(EnumFacing.EAST)) {
				rotations[0] = 90;
			}else if (face.equals(EnumFacing.WEST)) {
				rotations[0] = 270;
			}
		}
		rotations[1] = facing[1];
		return new float[] {rotations[0], rotations[1]};
	}
	
	public float[] getRotationsAAC(BlockPos block, EnumFacing face) {
        double x = block.getX() + 0.5 - mc.thePlayer.posX +  (double) face.getFrontOffsetX()/2;
        double z = block.getZ() + 0.5 - mc.thePlayer.posZ +  (double) face.getFrontOffsetZ()/2;
        double y = (block.getY() + 0.5 + face.getFrontOffsetZ()/2);
        y+=0.5;
        double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - y;
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
        if (yaw < 0.0F) {
            yaw += 360f;
        }
        return new float[]{yaw, pitch};
    }
}

class BlockData {

    public BlockPos position;
    public EnumFacing face;

    public BlockData(BlockPos position, EnumFacing face) {
        this.position = position;
        this.face = face;
    }
}
