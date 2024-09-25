package none.module.modules.world;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.BlockUtil;
import none.utils.ClickType;
import none.utils.RayCastUtil;
import none.utils.TimeHelper;
import none.utils.Utils;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class Cheststealer extends Module{
	
	private NumberValue<Integer> delay = new NumberValue<>("Delay", 100, 0, 500);
	
	private BooleanValue chestaura = new BooleanValue("ChestAura", false);
	private BooleanValue ignore = new BooleanValue("Ignore", false);
	private BooleanValue drop = new BooleanValue("Drop", false);
	private BooleanValue close = new BooleanValue("Close", true);
	private BooleanValue tile = new BooleanValue("ChestTile", false);
	private BooleanValue walls = new BooleanValue("Wall", false);
	
	private TimeHelper timer = new TimeHelper();
    private TimeHelper stealTimer = new TimeHelper();
    private boolean isStealing;
    private float[] rotations = new float[2];
    private boolean rotated;

	public Cheststealer() {
		super("ChestStealer", "ChestStealer", Category.WORLD, Keyboard.KEY_K);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
		shouldChest = false;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		rotations[0] = 0;
		rotations[1] = 0;
		stealTimer.setLastMS();
		shouldChest = false;
	}
	
	private EnumFacing getFacingDirection(final BlockPos pos) {
        EnumFacing direction = null;
        if (!mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock().isFullCube() && mc.theWorld.getBlockState(pos.add(0, 1, 0)).getBlock() != Blocks.air) {
            direction = EnumFacing.UP;
        }
        final MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
        if (rayResult != null) {
            return rayResult.sideHit;
        }
        return direction;
    }
	
	private boolean rayTrace(float yaw, float pitch, BlockPos pos) {
        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 = RayCastUtil.getVectorForRotation(yaw, pitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord + 0.5, vec31.yCoord + 0.5, vec31.zCoord + 0.5);

        MovingObjectPosition result = mc.theWorld.rayTraceBlocks(vec3, vec32, false);

        return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && pos.equals(result.getBlockPos());
    }

    private boolean isBad(ItemStack item) {
        if (ignore.getObject())
            return false;
        ItemStack is = null;
        float lastDamage = -1;
//        InvCleaner cleaner = (InvCleaner)Client.instance.moduleManager.getModulesbycls(InvCleaner.class);
//        if (cleaner.shouldDrop(item)) {
//        	return true;
//        }
        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is1.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword) {
                    if (lastDamage < getDamage(is1)) {
                        lastDamage = getDamage(is1);
                        is = is1;
                    }
                }
                if (is1.getItem() instanceof ItemAxe && item.getItem() instanceof ItemAxe) {
                        is = is1;
                }
                if (is1.getItem() instanceof ItemPickaxe && item.getItem() instanceof ItemPickaxe) {
                    is = is1;
                }
//                if (cleaner.shouldDrop(item, i)) {
//                	return true;
//                }
            }
            
        }

        if (is != null && is.getItem() instanceof ItemAxe && item.getItem() instanceof ItemAxe) {
        	return false;
        }
        
        if (is != null && is.getItem() instanceof ItemPickaxe && item.getItem() instanceof ItemPickaxe) {
        	return false;
        }

        if (is != null && is.getItem() instanceof ItemSword && item.getItem() instanceof ItemSword) {
            float currentDamage = getDamage(is);
            float itemDamage = getDamage(item);
            if (itemDamage > currentDamage) {
                return false;
            }
        }
        
        return item != null &&
                ((item.getItem().getUnlocalizedName().contains("tnt")) ||
                        (item.getItem().getUnlocalizedName().contains("stick")) ||
                        (item.getItem().getUnlocalizedName().contains("egg") && !item.getItem().getUnlocalizedName().contains("leg")) ||
                        (item.getItem().getUnlocalizedName().contains("string")) ||
                        (item.getItem().getUnlocalizedName().contains("flint")) ||
                        (item.getItem().getUnlocalizedName().contains("compass")) ||
                        (item.getItem().getUnlocalizedName().contains("feather")) ||
                        (item.getItem().getUnlocalizedName().contains("bucket")) ||
                        (item.getItem().getUnlocalizedName().contains("snow")) ||
                        (item.getItem().getUnlocalizedName().contains("fish")) ||
                        (item.getItem().getUnlocalizedName().contains("enchant")) ||
                        (item.getItem().getUnlocalizedName().contains("exp")) ||
                        (item.getItem().getUnlocalizedName().contains("shears")) ||
                        (item.getItem().getUnlocalizedName().contains("anvil")) ||
                        (item.getItem().getUnlocalizedName().contains("torch")) ||
                        (item.getItem().getUnlocalizedName().contains("seeds")) ||
                        (item.getItem().getUnlocalizedName().contains("leather")) ||
                        ((item.getItem() instanceof ItemGlassBottle)) ||
                        (item.getItem().getUnlocalizedName().contains("piston")) ||
                        ((item.getItem().getUnlocalizedName().contains("potion"))
                                && (isBadPotion(item))));
    }

    private boolean isBadPotion(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
            	if (potion.getEffects(stack).isEmpty()) {
            		return true;
            	}
                for (final PotionEffect effect : potion.getEffects(stack)) {
                    if (effect.getPotion() == MobEffects.harm || effect.getPotion() == MobEffects.moveSlowdown || effect.getPotion() == MobEffects.weakness) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private float getDamage(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemSword)) {
            return 0;
        }
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + ((ItemSword) stack.getItem()).getDamageVsEntity();
    }
    
    public static boolean shouldChest = false;

	@Override
	@RegisterEvent(events = {EventTick.class, EventPreMotionUpdate.class})
	public void onEvent(Event event) {
		if (!isEnabled() || Client.instance.moduleManager.scaffold.isEnabled()) return;
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate e = (EventPreMotionUpdate) event;
			if (e.isPre() && chestaura.getObject()) {
				if (mc.currentScreen instanceof GuiChest || mc.currentScreen instanceof GuiContainer) {
					stealTimer.setLastMS();
				}
	            for (Object o : mc.theWorld.loadedTileEntityList) {
	                if (o instanceof TileEntityChest) {
	                    TileEntityChest chest = (TileEntityChest) o;
	                    float x = chest.getPos().getX();
	                    float y = chest.getPos().getY();
	                    float z = chest.getPos().getZ();
	                    boolean chestaura = Math.abs(mc.thePlayer.rotationYawHead - mc.thePlayer.rotationYaw) < 90;
	                    
	                    boolean cansee = Utils.canBlockBeSeen(new Vec3(x, y, z));
	                    if (mc.thePlayer.getDistance(x, y, z) <= 4.5) {
	                    	shouldChest = true;
	                    }else {
	                    	shouldChest = false;
	                    }
	                    if (mc.thePlayer.getDistance(x, y, z) <= 4.5 && !chest.isEmpty && chestaura) {
	                    	
	                    	rotations = BlockUtil.getRotations(chest.getPos(), getFacingDirection(chest.getPos()));
	                    	
	                    	e.setYaw(rotations[0]);
	                        e.setPitch(rotations[1]);
	                        
	                        rotated = rayTrace(rotations[0], rotations[1], chest.getPos()) || !this.walls.getObject();
	                        
	                       	
	                       	if (rotated && ((!this.walls.getObject() && cansee) || (this.walls.getObject())) && !isStealing && !chest.isEmpty && mc.thePlayer.getDistance(x, y, z) <= 3.7 && stealTimer.hasTimeReached(100) && mc.currentScreen == null) {
		                    	mc.thePlayer.swingItem();
//		                    	mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(chest.getPos(), getFacingDirection(chest.getPos()).getIndex(), mc.thePlayer.getCurrentEquippedItem(), x, y, z));
			                    mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), chest.getPos(), getFacingDirection(chest.getPos()), new Vec3(x, y, z));
//			                    mc.playerController.processRightClickBlock(mc.thePlayer, mc.theWorld, chest.getPos(), getFacingDirection(chest.getPos()), new Vec3(x,y,z), EnumHand.MAIN_HAND);
			                    mc.playerController.updateController();
			                    chest.isEmpty = true;
		                        isStealing = true;
		                        stealTimer.setLastMS();
		                    }
	                    }
	                    if (stealTimer.hasTimeReached(150) && isStealing) {
	                    	stealTimer.setLastMS();
	                    	isStealing = false;
	                    }
	                }
	            }
			}else {
				if (mc.currentScreen instanceof GuiChest) {
		            GuiChest guiChest = (GuiChest) mc.currentScreen;
		            String name = guiChest.lowerChestInventory.getDisplayName().getUnformattedText().toLowerCase();
		            String[] list = new String[]{"menu", "selector", "game", "gui", "server", "inventory", "play", "teleporter", "shop", "melee", "armor",
		                    "block", "castle", "mini", "warp", "teleport", "user", "team", "tool", "sure", "trade", "cancel", "accept", "soul", "book", "recipe",
		                    "profile", "tele", "port", "map", "kit", "select", "lobby", "vault", "lock", "anticheat", "travel", "settings", "user", "preference",
		                    "compass", "cake", "wars", "buy", "upgrade", "ranged", "potions", "utility"};
		            String[] chestname = new String[] {"chest"};
		          
		            if (!tile.getObject()) {
		            	for (String str : list) {
			                if (name.contains(str))
			                    return;
			            }
		            }else if (tile.getObject()) {
		            	for (String str : chestname) {
			                if (!name.contains(str))
			                    return;
			            }
		            }
		            isStealing = true;
		            boolean full = true;
		            int j = mc.thePlayer.inventory.mainInventory.length;
		            for (int i = 0; i < j; i++) {
		                ItemStack item = mc.thePlayer.inventory.mainInventory[i];
		                if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
		                    full = false;
		                    break;
		                }
		            }
		            boolean containsItems = false;
		            if (!full) {
		                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); index++) {
		                    ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);

		                    if (guiChest.lowerChestInventory.getStackInSlot(index) != null && !isBad(stack)) {
		                        containsItems = true;
		                        break;
		                    }
		                }
		                if (containsItems) {
		                    for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); index++) {
		                        ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
		                        int delay = this.delay.getObject();
		                        if (guiChest.lowerChestInventory.getStackInSlot(index) != null && timer.hasTimeReached(delay) && !isBad(stack)) {
		                        	mc.playerController.windowClick(guiChest.inventorySlots.windowId, index, 0, drop.getObject() ? ClickType.PICKUP : ClickType.QUICK_MOVE, mc.thePlayer);
		                        	if (mc.getCurrentServerData() != null && mc.getIntegratedServer() == null) {
		                        		if (mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {
		                        			mc.playerController.windowClick(guiChest.inventorySlots.windowId, index, 1, drop.getObject() ? ClickType.PICKUP : ClickType.QUICK_MOVE, mc.thePlayer);
		                            	}
		                        	}
		                        	if (drop.getObject()) {
		                                mc.playerController.windowClick(guiChest.inventorySlots.windowId, -999, 0, ClickType.THROW, mc.thePlayer);
		                            }
		                            timer.setLastMS();
		                        }
		                    }
		                } else if (close.getObject()) {
		                    mc.thePlayer.closeScreen();
		                    isStealing = false;
		                }
		            } else if (close.getObject()) {
		                mc.thePlayer.closeScreen();
		                isStealing = false;
		            }
		        } else {
		            isStealing = false;
		        }
			}
		}
	}
}
