package lunadevs.luna.module.movement;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventMotion;
import lunadevs.luna.events.EventPlayerUpdate;
import lunadevs.luna.events.EventType;
import lunadevs.luna.events.KeyPressEvent;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Scaffold extends Module{

	public Scaffold() {
		super("Scaffold", Keyboard.KEY_PERIOD, Category.MOVEMENT, true);
	}
	
	 private List<Block> invalid = Arrays.asList(new Block[] { Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava });
	  private TimeHelper timer = new TimeHelper();
	  private BlockData blockData;
	  boolean placing;
	  private boolean count = true;
	  private int slot;
	  public static int blockcount;
	    private int original;
	  public static boolean active;
	  
	  @Override
	  public void onRender(){
		  ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Display.getWidth(), Display.getHeight());
          Luna.fontRenderer.drawStringWithShadow(String.valueOf(blockcount), sr.getScaledWidth() / 2 - (Luna.fontRenderer.getStringWidth(String.valueOf(blockcount))) / 2, sr.getScaledHeight() / 2 - 15, 0xFF690096);
  }
	  
	  
	  @Override
	public void onEnable() {
		  active=true;
		super.onEnable();
	}
	  @Override
	public void onDisable() {
		  active=false;
		  super.onDisable();
	}
	  
	  public static float[] getBlockRotations(int x, int y, int z)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    Entity temp = new EntitySnowball(Minecraft.theWorld);
	    temp.posX = (x + 0.5D);
	    temp.posY = (y + 0.5D);
	    temp.posZ = (z + 0.5D);
	    return getAngles(temp);
	  }
	  public static float[] getAngles(Entity e)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    return new float[] { getYawChangeToEntity(e) + Minecraft.thePlayer.rotationYaw, getPitchChangeToEntity(e) + Minecraft.thePlayer.rotationPitch };
	  }
	  public static float getYawChangeToEntity(Entity entity)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    double deltaX = entity.posX - Minecraft.thePlayer.posX;
	    double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
	    double yawToEntity;
	    if ((deltaZ < 0.0D) && (deltaX < 0.0D))
	    {
	      yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
	    }
	    else
	    {
	      if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
	        yawToEntity = -90.0D + 
	          Math.toDegrees(Math.atan(deltaZ / deltaX));
	      } else {
	        yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
	      }
	    }
	    return 
	      MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float)yawToEntity));
	  }

	  public static float getPitchChangeToEntity(Entity entity)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    double deltaX = entity.posX - Minecraft.thePlayer.posX;
	    double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
	    double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4D - 
	      Minecraft.thePlayer.posY;
	    double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * 
	      deltaZ);
	    
	    double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
	    
	    return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - 
	      (float)pitchToEntity);
	  }
	  private void swap(final int slot, final int hotbarNum) {
	        z.mc().playerController.windowClick(z.player().inventoryContainer.windowId, slot, hotbarNum, 2, z.player());
	    }
	  
	  private boolean grabBlock() {
	        for (int i = 0; i < 36; i++) {
	            ItemStack stack = z.player().inventoryContainer.getSlot(i).getStack();
	            if (stack != null && stack.getItem() instanceof ItemBlock) {
	                for (int x = 36; x < 45; x++) {
	                    try {
	                        Item stackkk = z.player().inventoryContainer.getSlot(x).getStack().getItem();
	                    } catch (NullPointerException ex) {
	                        System.out.println(x-36);
	                        swap(i, x - 36);
	                        return true;
	                    }
	                }
	                swap(i, 1);
	                return true;
	            }
	        }
	        return false;
	    }

	    private int blockInHotbar() {
	        for (int i = 36; i < 45; i++) {
	            ItemStack stack = z.player().inventoryContainer.getSlot(i).getStack();
	            if (stack != null && stack.getItem() instanceof ItemBlock) {
	                return i;
	            }
	        }
	        return 0;
	    }
	  
	  @EventTarget
	  public void onPre(EventMotion event)
	  {
//			  if (this.blockData != null) {
//		            if (this.timer.hasReached(65L)) {
//		                mc.rightClickDelayTimer = 0;
//		                if (mc.gameSettings.keyBindJump.getIsKeyPressed() && !mc.thePlayer.isMoving()) {
//		                    mc.thePlayer.motionY = 0.42;
//		                    if (timer.hasReached(1500)) {
//		                        mc.thePlayer.motionY = -0.28;
//		                        timer.reset();
//		                        if (timer.hasReached(2L)) {
//		                            mc.thePlayer.motionY = 0.42;
//		                        }}		                    }
//		                }
//		            }
	        blockcount = 0;
	        for (int i = 0; i < 45; i++) {
	            ItemStack stack = z.player().inventoryContainer.getSlot(i).getStack();
	            if (stack != null && stack.getItem() instanceof ItemBlock) {
	                blockcount += stack.stackSize;
	            }
	        }
	        int block = blockInHotbar();

	        if (block == 0) {
	            if ((grabBlock())) {
	                block = blockInHotbar();
	                if (block == 0) return;
	            }
	        }
	    if (event.getType() == EventType.PRE)
	    {
	    	
	      int tempSlot = getBlockSlot();
	      this.blockData = null;
	      this.slot = -1;
	      if ((tempSlot != -1))
	      {
	        double x2 = Math.cos(Math.toRadians(Minecraft.thePlayer.rotationYaw + 90.0F));
	        double z2 = Math.sin(Math.toRadians(Minecraft.thePlayer.rotationYaw + 90.0F));
	        double xOffset = Minecraft.thePlayer.movementInput.moveForward * 0.4D * x2 + Minecraft.thePlayer.movementInput.moveStrafe * 0.4D * z2;
	        double zOffset = Minecraft.thePlayer.movementInput.moveForward * 0.4D * z2 - Minecraft.thePlayer.movementInput.moveStrafe * 0.4D * x2;
	        double x = Minecraft.thePlayer.posX + xOffset;double y = Minecraft.thePlayer.posY - 1.0D;double z = Minecraft.thePlayer.posZ + zOffset;
	        BlockPos blockBelow1 = new BlockPos(x+0.1, y, z+0.1);
	        if (Minecraft.theWorld.getBlockState(blockBelow1).getBlock() == Blocks.air)
	        {
	          this.blockData = getBlockData(blockBelow1, this.invalid);
	          this.slot = tempSlot;
	          if (this.blockData != null)
	          {
	            event.getLocation().setYaw(getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0]);
	            event.getLocation().setPitch(getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1]);
	          }
	        }
	      }
	    }
	  }
	
	  @EventTarget
	  public void onPost(EventPlayerUpdate post)
	  {
	    post.getType();
	    if ((post.getType() == EventType.POST) && (this.blockData != null) && (this.timer.hasReached(75L)) && (this.slot != -1))
	    {
	      mc.rightClickDelayTimer = 3;
	      boolean dohax = mc.thePlayer.inventory.currentItem != this.slot;
	      if (dohax) {
	    	  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
	      }
	      if (mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.blockData.position, this.blockData.face, new Vec3(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ()))) {
	        mc.thePlayer.swingItem();
	      }
	      if (dohax) {
	    	  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
	      }
	    }
	  }
	  
	  @EventTarget
	  public void onPre(EventPlayerUpdate e5)
	  {
	  }
	  
	  @EventTarget
	  public void onPress(KeyPressEvent e)
	  {
	    e.getKey();
	    mc.gameSettings.keyBindJump.getKeyCode();
	  }
	
	@Override
	public String getValue() {
		return String.valueOf(new DecimalFormat("#.#").format(blockcount));
	}
	  public static float[] getBlockRotations(int x, int y, int z, EnumFacing facing)
	  {
	    Minecraft mc = Minecraft.getMinecraft();
	    Entity temp = new EntitySnowball(Minecraft.theWorld);
	    temp.posX = (x + 0.5D);
	    temp.posY = (y + 0.5D);
	    temp.posZ = (z + 0.5D);
	    return getAngles(temp);         
	  }
	  public static BlockData getBlockData(BlockPos pos, List list)
	  {
		  Minecraft.getMinecraft();return 
	    
	      !list.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? 
	      new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH) : !list.contains(Minecraft.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH) : !list.contains(Minecraft.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? new BlockData(pos.add(1, 0, 0), EnumFacing.WEST) : !list.contains(Minecraft.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST) : !list.contains(Minecraft.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? new BlockData(pos.add(0, -1, 0), EnumFacing.UP) : null;
	  }
	  
	  public static class BlockData
	  {
	    public BlockPos position;
	    public EnumFacing face;
	    
	    public BlockData(BlockPos position, EnumFacing face)
	    {
	      this.position = position;
	      this.face = face;
	    }
	  }
	  
	  public static Block getBlock(int x, int y, int z)
	  {
	    return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	  }
	  
	  private int getBlockSlot()
	  {
	    for (int i = 36; i < 45; i++)
	    {
	      ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	      if ((itemStack != null) && ((itemStack.getItem() instanceof ItemBlock))) {
	        return i - 36;
	      }
	    }
	    return -1;
	  }

}
