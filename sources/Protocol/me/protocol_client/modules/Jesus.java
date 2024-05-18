package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import me.protocol_client.utils.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPacketSent;
import events.EventPreMotionUpdates;

public class Jesus extends Module {

	private TimerUtil timer = new TimerUtil();
	
	public final Value<Boolean> solid = new Value<>("liquids_solid", true);
	public final Value<Boolean> aac = new Value<>("liquids_aac", false);
	public Jesus() {
		super("Liquids", "liquids", Keyboard.KEY_J, Category.PLAYER, new String[]{"jesus", "liquids", "liquid"});
	}
	
	
	private int delay = 0;
	  @EventTarget
	  public void onPreUpdate(EventPreMotionUpdates event)
	  {
		  if(!solid.getValue() || aac.getValue()){
			  setDisplayName("Liquids [Wet]");
	    Block block = Wrapper.getBlock((int)Wrapper.getPlayer().posX, (int)(Wrapper.getPlayer().boundingBox.maxY + 0.1D), (int)Wrapper.getPlayer().posZ);
	    if (((isInLiquid()) || (Wrapper.getPlayer().isInWater())) && (!Wrapper.getPlayer().isSneaking()) && 
	      (!(block instanceof BlockLiquid))) {
	    	if(aac.getValue()){
	    		Wrapper.getPlayer().jump();
	    		if(Wrapper.getPlayer().moveForward > 0){
	    			Wrapper.getPlayer().motionX *= 0.7f;
	    			Wrapper.getPlayer().motionZ *= 0.7f;
	    		}
	    	}else{
	      Wrapper.getPlayer().motionY = 0.015D;
	      Wrapper.getPlayer().jumpMovementFactor *= 1.2f;
	    	}
	    }
	    if (((isInLiquid()) || (Wrapper.getPlayer().isInWater())) && (!Wrapper.getPlayer().isSneaking()) && 
	  	      (!(block instanceof BlockLiquid)) && Wrapper.getPlayer().isCollidedHorizontally) {
	    	Wrapper.getPlayer().motionY = 0.045D;
	    	
	    }
	  }if(solid.getValue()){
		  setDisplayName("Liquids [Solid]");
	      if (isInLiquid()) {
	    	  if(!Wrapper.mc().gameSettings.keyBindSneak.pressed){
	        Wrapper.getPlayer().motionY = 0.085D;
	      }
	      }
	  }
	  }
	  @EventTarget
	  public void onPacketSend(EventPacketSent event)
	  {
		  if(solid.getValue()){
	    if ((event.getPacket() instanceof C03PacketPlayer))
	    {
	      C03PacketPlayer player = (C03PacketPlayer)event.getPacket();
	      if ((isOnLiquid())) {
	    	  this.delay += 1;
		        if (this.delay >= 4)
		        {
	        player.y -= 0.001;
	        delay = 0;
		        }
	      }
	    }
		  }
	  }
	  
	  public static boolean isOnLiquid()
	  {
		  AxisAlignedBB boundingBox = Wrapper.getPlayer().getEntityBoundingBox();
		  boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
		    boolean onLiquid = false;
		    int y = (int)boundingBox.minY;
		    for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0D); x++) {
		      for (int z = MathHelper.floor_double(boundingBox.minZ); z < 
		            MathHelper.floor_double(boundingBox.maxZ + 1.0D); z++)
		      {
		        Block block = Wrapper.getBlock(new BlockPos(x, y, z));
		        if (block != Blocks.air)
		        {
		          if (!(block instanceof BlockLiquid)) {
		            return false;
		          }
		          onLiquid = true;
		        }
		      }
		    }
		    return onLiquid;
	  }
	  
	  public static boolean isInLiquid()
	  {
		  AxisAlignedBB par1AxisAlignedBB = Wrapper.getPlayer().getEntityBoundingBox();
		  par1AxisAlignedBB = par1AxisAlignedBB.contract(0.001D, 0.001D, 0.001D);
		    int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		    int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
		    int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		    int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
		    int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		    int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
		    if (Wrapper.getWorld().getChunkFromBlockCoords(
		      new BlockPos(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ)) == null) {
		      return false;
		    }
		    new Vec3(0.0D, 0.0D, 0.0D);
		    for (int var12 = var4; var12 < var5; var12++) {
		      for (int var13 = var6; var13 < var7; var13++) {
		        for (int var14 = var8; var14 < var9; var14++)
		        {
		          Block var15 = Wrapper.getBlock(new BlockPos(var12, var13, var14));
		          if ((var15 instanceof BlockLiquid)) {
		            return true;
		          }
		        }
		      }
		    }
		    return false;
	  }
	  
	  private boolean isStandingStill()
	  {
	    return (Math.abs(Wrapper.getPlayer().motionX) <= 0.01D) && (Math.abs(Wrapper.getPlayer().motionZ) <= 0.01D);
	  }
	  public void onEnable(){
		  EventManager.register(this);
		  if(aac.getValue() && solid.getValue()){
			  Wrapper.tellPlayer(Protocol.primColor + "WARNING\2477: Using solid mode and aac mode will be buggy on acc servers.");
		  }
	  }
	  public void onDisable(){
		  EventManager.unregister(this);
	  }
	  public void runCmd(String s){
		  try{
		  if(s.startsWith("mode")){
			  String[] args = s.split(" ");
			  if(args[1].equalsIgnoreCase("solid")){
				  this.solid.setValue(true);
				  Wrapper.tellPlayer("\2477Jesus mode " + Protocol.primColor + "solid §set to true");
				  return;
			  }
			  if(args[1].equalsIgnoreCase("wet")){
				  this.solid.setValue(false);
				  Wrapper.tellPlayer("\2477Jesus mode " + Protocol.primColor + "solid §set to false");
				  return;
			  }
			  if(args[1].equalsIgnoreCase("aac")){
				  this.solid.setValue(false);
				  this.aac.setValue(true);
				  Wrapper.tellPlayer("\2477Jesus mode " + Protocol.primColor + "AAC §set to true");
				  return;
			  }
			  Wrapper.invalidCommand("Liquids");
			  Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Liquids\2477 <mode> <solid/wet/aac>");
			  return;
		  }
		  }catch(Exception e){
			  Wrapper.invalidCommand("Liquids");
			  Wrapper.tellPlayer("\2477-" + Protocol.primColor + "Liquids\2477 <mode> <solid/wet/aac>");
		  }
	  }
}
