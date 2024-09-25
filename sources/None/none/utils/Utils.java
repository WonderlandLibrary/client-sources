/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package none.utils;

import java.awt.Point;
import java.net.Proxy;
import java.util.Random;

import org.lwjgl.input.Mouse;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Vec3;

public class Utils {
    private static final Random RANDOM = new Random();
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * This function returns a random value between min and max
     * If <code>min >= max</code> the function will return min
     *
     * @param min Minimal
     * @param max Maximal
     * @return The value
     */
    public static int random(int min, int max) {
        if (max <= min) return min;

        return RANDOM.nextInt(max - min) + min;
    }
    
    public static double randomNumber(double max, double min) {
        return (Math.random() * (max - min)) + min;
    }
    
    public static boolean randomBoolean() {
    	return random(0 , 1) == 0;
    }
    
    public static boolean randomPercent(int percent) {
    	return random(0, 100) <= percent;
    }
    
    public static String randomString(int length) {
    	String string = "";
    	String abc = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
    	for (int i = 0; i < length; i++) {
    		int randomat = Utils.random(0, abc.length());
    		string = string + abc.charAt(randomat);
    	}
    	return string;
    }

    public static Session createSession(String username, String password, Proxy proxy) throws Exception {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(proxy, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service
                .createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(username);
        auth.setPassword(password);

        auth.logIn();
        return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                auth.getAuthenticatedToken(), "mojang");
    }

    /**
     * @return The direction of the player's movement in radians
     */
    public static double getDirection() {
        Minecraft mc = Minecraft.getMinecraft();

        float yaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0.0F) yaw += 180.0F;

        float forward = 1.0F;

        if (mc.thePlayer.moveForward < 0.0F) forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0.0F) forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0.0F) yaw -= 90.0F * forward;

        if (mc.thePlayer.moveStrafing < 0.0F) yaw += 90.0F * forward;

        return Math.toRadians(yaw);
    }

    /*
     * By DarkStorm
     */
    public static Point calculateMouseLocation() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int scale = minecraft.gameSettings.guiScale;
        if (scale == 0)
            scale = 1000;
        int scaleFactor = 0;
        while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240)
            scaleFactor++;
        return new Point(Mouse.getX() / scaleFactor, minecraft.displayHeight / scaleFactor - Mouse.getY() / scaleFactor - 1);
    }
    
    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 1 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }

    public static float[] getNeededRotations(final Vec3 vec) {
        final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }
    
    public static float[] getNeededRotationsForEntity(EntityLivingBase eyesPos) {
//        final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final double diffX = eyesPos.posX - mc.thePlayer.posX;
        final double diffY = (eyesPos.posY + eyesPos.getEyeHeight() / 2.0) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double diffZ = eyesPos.posZ - mc.thePlayer.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
    }
    
    public static float[] getNeededRotationsForEntity(Entity eyesPos) {
//      final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
      final double diffX = eyesPos.posX - mc.thePlayer.posX;
      final double diffY = (eyesPos.posY + eyesPos.getEyeHeight() / 2.0) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
      final double diffZ = eyesPos.posZ - mc.thePlayer.posZ;
      final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
      final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
      return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
  }
    
    public static float[] getNeededRotationsForAAC(EntityLivingBase eyesPos) {
      final double diffX = eyesPos.posX - mc.thePlayer.posX;
      final double diffY = (eyesPos.posY + (double) eyesPos.getEyeHeight()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
      final double diffZ = eyesPos.posZ - mc.thePlayer.posZ;
      final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
      final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
      return new float[]{MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch)};
  }
    
    public static boolean canEntityBeSeen(EntityLivingBase e){
    	Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),mc.thePlayer.posZ);

    	AxisAlignedBB box = e.getEntityBoundingBox();
        Vec3 vec2 = new Vec3(e.posX, e.posY + (e.getEyeHeight()/1.32F),e.posZ);	
        double minx = e.posX - 0.25;
        double maxx = e.posX + 0.25;
        double miny = e.posY;
        double maxy = e.posY + Math.abs(e.posY - box.maxY) ;
        double minz = e.posZ - 0.25;
        double maxz = e.posZ + 0.25;
        boolean see =  mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx,miny,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx,miny,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx,miny,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx,miny,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	    vec2 = new Vec3(maxx, maxy,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx, maxy,minz);	
	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx, maxy,maxz - 0.1);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx, maxy,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	
    	return false;
    }
    
    public static boolean canBlockBeSeen(Vec3 e){
    	Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),mc.thePlayer.posZ);

        Vec3 vec2 = new Vec3(e.xCoord, e.yCoord,e.zCoord);	
        double minx = e.xCoord - 0.25;
        double maxx = e.xCoord + 0.25;
        double miny = e.yCoord - 0.25;
        double maxy = e.yCoord + 0.25;
        double minz = e.zCoord - 0.25;
        double maxz = e.zCoord + 0.25;
        boolean see =  mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx,miny,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx,miny,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx,miny,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx,miny,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	    vec2 = new Vec3(maxx, maxy,minz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	  
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx, maxy,minz);	
	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(minx, maxy,maxz - 0.1);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    vec2 = new Vec3(maxx, maxy,maxz);	
	    see = mc.theWorld.rayTraceBlocks(vec1, vec2) == null? true:false;
	    if(see)
	    	return true;
	    
	
    	return false;
    }
    
    public static boolean isOnLiquid() {
        if (mc.thePlayer == null) return false;
        boolean onLiquid = false;
        final int y = (int) mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D).minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                final Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if (block != null && block.getMaterial() != Material.AIR) {
                    if (!(block instanceof BlockLiquid)) return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
}
