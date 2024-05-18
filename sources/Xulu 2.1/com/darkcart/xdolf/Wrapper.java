package com.darkcart.xdolf;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.clickgui.XuluGuiClick;
import com.darkcart.xdolf.fonts.Fonts;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.FileManager;
import com.darkcart.xdolf.util.FriendManager;
import com.darkcart.xdolf.util.Rainbow;
import com.darkcart.xdolf.util.RotationUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.Util.EnumOS;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class Wrapper {
	
	public static Hacks hacks;
	public static FileManager fileManager;
	public static FriendManager friendManager;
	public static XuluGuiClick clickGui;
	public static Rainbow rainbow;
	
	public static final Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	public static final EntityPlayerSP getPlayer() {
		return getMinecraft().player;
	}
	
	public static final WorldClient getWorld() {
		return getMinecraft().world;
	}
	private double x;
	  private double y;
	  private double z;
	  {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	  }
	  
	  public double getX()
	  {
	    return this.x;
	  }
	  
	  public double getY()
	  {
	    return this.y;
	  }
	  
	  public double getZ()
	  {
	    return this.z;
	  }
	  
	  public void setX(double x)
	  {
	    this.x = x;
	  }
	  
	  public void setY(double y)
	  {
	    this.y = y;
	  }
	  
	  public void setZ(double z)
	  {
	    this.z = z;
	}
	
	public static void sendPacket(Packet packet)
	{
		Minecraft.getMinecraft().getConnection().sendPacket(packet);
	}
	
	public static void sendPacketBypass(Packet packet)
	{
		Minecraft.getMinecraft().getConnection().sendPacket(packet);
}

	public static final File getMinecraftDir() {
		return getMinecraft().mcDataDir;
	}
	
	public static void addChatMessage(String s) {
		getPlayer().addChatMessage(new TextComponentString(Client.wrap(String.format("§8[§7%s%s%s§8] %s", "§7", Client.CLIENT_NAME, "§f", s), 100)));
	}
	
	public static void addAntiCheatMessage(String s) {
		getPlayer().addChatMessage(new TextComponentString(Client.wrap(String.format("§8[§7%s%s%s§8] %s", "§7", Client.ANTICHEAT_PREFIX, "§f", s), 100)));
	}
	
	public static float getCooldown()
	{
		return getPlayer().getCooledAttackStrength(0);
	}
	
	public static Hacks getHacks()
	{
		if(hacks == null) hacks = new Hacks();
		
		return hacks;
	}
	
	public static FileManager getFileManager()
	{
		if(fileManager == null) fileManager = new FileManager();
		
		return fileManager;
	}
	
	public static void faceEntity(EntityLivingBase entity) {
	      float[] rotations = RotationUtils.getRotations(entity);
	      if(rotations != null) {
	         Minecraft.getMinecraft();
			Minecraft.player.rotationYaw = rotations[0];
	         Minecraft.getMinecraft();
			Minecraft.player.rotationPitch = rotations[1] - 8.0F;
	      }

	   }
	
	public static FriendManager getFriends()
	{
		if(friendManager == null) friendManager = new FriendManager();
		
		return friendManager;
	}
	
	public static XuluGuiClick getClickGui()
	{
		if(clickGui == null) clickGui = new XuluGuiClick();
		
		return clickGui;
	}
	
	public static void drawCenteredTTFString(String s, int x, int y, int color)
    {
		Fonts.roboto18.drawCenteredString(s, x, y, color, true);
    }
	
	public static Rainbow getRainbowClass() {
		return rainbow;
	}
	
    /**
     * gets the working dir (OS specific) for the specific application (which is always minecraft)
     */
    public static File getAppDir(String par0Str)
    {
    	String var1 = System.getProperty("user.home", ".");
        File var2;

        switch (getOs()) {
            case LINUX:
            case SOLARIS:
                var2 = new File(var1, '.' + par0Str + '/');
                break;

            case WINDOWS:
                String var3 = System.getenv("APPDATA");
                
                if (var3 != null) {
                    var2 = new File(var3, "." + par0Str + '/');
                } else {
                    var2 = new File(var1, '.' + par0Str + '/');
                }

                break;

            case OSX:
                var2 = new File(var1, "Library/Application Support/" + par0Str);
                break;

            default:
                var2 = new File(var1, par0Str + '/');
         }

         if (!var2.exists() && !var2.mkdirs()) {
        	 throw new RuntimeException("The working directory could not be created: " + var2);
         } else {
             return var2;
         }
     }
    
    public static EnumOS getOs() {
        String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? EnumOS.WINDOWS : (var0.contains("mac") ? EnumOS.OSX : (var0.contains("solaris") ? EnumOS.SOLARIS : (var0.contains("sunos") ? EnumOS.SOLARIS : (var0.contains("linux") ? EnumOS.LINUX : (var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }
    
	public static void onKeyPressed() {
		int key = Keyboard.getEventKey();
		for(Module mod : Hacks.hackList) {
			mod.onKeyPressed(key);
		}
	}
	
	public static Block getBlock(BlockPos pos)
	  {
	    Minecraft.getMinecraft();return Minecraft.world.getBlockState(pos).getBlock();
	  }
	  
	  public static Block getBlockAbovePlayer(EntityPlayer inPlayer, double blocks)
	  {
	    blocks += inPlayer.height;
	    return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY + blocks, inPlayer.posZ));
	  }
	  
	  public static Block getBlockAtPos(EntityPlayer inPlayer, double x, double y, double z)
	  {
	    return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
	  }

	public static float getDistanceToGround(Entity e)
	  {
	    if (getPlayer().isCollidedVertically) {
	      return 0.0F;
	    }
	    for (float a = (float)e.posY; a > 0.0F; a -= 1.0F)
	    {
	      int[] stairs = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
	      int[] exemptIds = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 
	        76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 
	        176, 177 };
	      
	      Block block = getBlock(new BlockPos(e.posX, a - 1.0F, e.posZ));
	      if (!(block instanceof BlockAir))
	      {
	        if ((Block.getIdFromBlock(block) == 44) || 
	          (Block.getIdFromBlock(block) == 126)) {
	          return (float)(e.posY - a - 0.5D) < 0.0F ? 0.0F : (float)(e.posY - a - 0.5D);
	        }
	        int[] arrayOfInt1;
	        int j = (arrayOfInt1 = stairs).length;
	        for (int i = 0; i < 1337; i++) {
	        {
	          int id = arrayOfInt1[i];
	          if (Block.getIdFromBlock(block) == id) {
	            return (float)(e.posY - a - 1.0D) < 0.0F ? 0.0F : (float)(e.posY - a - 1.0D);
	          }
	        }
	        j = (arrayOfInt1 = exemptIds).length;
	        for (j = 0; j < j; j++)
	        {
	          int id = arrayOfInt1[j];
	          if (Block.getIdFromBlock(block) == id) {
	            return (float)(e.posY - a) < 0.0F ? 0.0F : (float)(e.posY - a);
	          }
	        }
	        return (float)(e.posY - a + block.getBlockBoundsMaxY() - 1.0D);
	      }
	    }
	  }
		return 0.0F;
	
}}
