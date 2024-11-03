package net.augustus.ScriptingAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.augustus.Augustus;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class APIImpl implements ScriptingAPI{
	
	Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void log(String message) {
		System.out.println(message);
	}
	
	@Override
	public String getContentFromURL(String urlStr) {
		String toReturn = "";
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder content = new StringBuilder();
				while ((inputLine = in.readLine()) != null) {
					content.append(inputLine).append("\n");
				}
				in.close();
				toReturn = content.toString();
			} else {
				System.out.println("GET request failed");
			}
			connection.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	@Override
	public void setBinding(String binding) {
		Augustus.getInstance().setApiBinding(binding);
	}
	
	@Override
	public String getLoadedScriptByName(String name) {
		String result = "";
		for(Script s : apiFileManager.scriptsFound) {
			if(s.getName().equals(name))
				result = s.getScript();
		}
		if(result == "")
			this.log("No scripts found!");
		return result;
	}
	
	@Override
	public void executeScript(String script) {
		Augustus.getInstance().getExecutor().execute(script);
	}

	@Override
	public void jump() {
		mc.thePlayer.jump();
	}

	@Override
	public void setMotionX(double motion) {
		mc.thePlayer.motionX = motion;
	}

	@Override
	public void setMotionY(double motion) {
		mc.thePlayer.motionY = motion;
	}

	@Override
	public void setMotionZ(double motion) {
		mc.thePlayer.motionZ = motion;
	}
	
	@Override
	public void addMotionX(double motion) {
		mc.thePlayer.motionX += motion;
	}

	@Override
	public void addMotionY(double motion) {
		mc.thePlayer.motionY += motion;
	}

	@Override
	public void addMotionZ(double motion) {
		mc.thePlayer.motionZ += motion;
	}

	@Override
	public void setSprinting(boolean state) {
		mc.thePlayer.setSprinting(state);
	}

	@Override
	public void setPosition(double x, double y, double z) {
		mc.thePlayer.setPosition(x, y, z);
	}

	@Override
	public void addX(double pos) {
		mc.thePlayer.setPosition(pos, mc.thePlayer.posY, mc.thePlayer.posZ);
	}

	@Override
	public void addY(double pos) {
		mc.thePlayer.setPosition(mc.thePlayer.posX, pos, mc.thePlayer.posZ);
	}

	@Override
	public void addZ(double pos) {
		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, pos);
	}

	@Override
	public void setSneaking(boolean state) {
		mc.thePlayer.setSneaking(state);
	}

	@Override
	public void leftClickMouse() {
		mc.clickMouse();
	}

	@Override
	public void rightClickMouse() {
		mc.rightClickMouse();
	}

	@Override
	public void midClickMouse() {
		mc.middleClickMouse();
	}
	
	@Override
	public void attackEntity(Entity e) {
		mc.playerController.attackEntity(mc.thePlayer, e);
	}

	@Override
	public Entity getNearestEntity(double range) {
		Entity result = mc.thePlayer;
	    double closestDist = Double.MAX_VALUE;
	    Entity closestEnt = null;
	    for (Entity e : mc.theWorld.loadedEntityList) {
	        if (e != mc.thePlayer) {
	            double distance = e.getDistanceToEntity(mc.thePlayer);
	            if (distance <= range && distance < closestDist) {
	                closestDist = distance;
	                closestEnt = e;
	            }
	        }
	    }
	    if (closestEnt != null) {
	        result = closestEnt;
	    }
	    return result;
	}

	@Override
	public Vec3 getMotion() {
		return new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
	}

	@Override
	public Vec3 getPosition() {
		return new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
	}

	@Override
	public Vec3 getPrevPosition() {
		return new Vec3(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ);
	}

	@Override
	public double getEntityHealth(Entity e) {
		return ((EntityLivingBase) e).getHealth();
	}

	@Override
	public double getMaxEntityHealth(Entity e) {
		return ((EntityLivingBase) e).getMaxHealth();
	}

	@Override
	public String getName(Entity e) {
		return e.getName();
	}

	@Override
	public String getCustomName(Entity e) {
		return e.getCustomNameTag().isEmpty() ? e.getName() : e.getCustomNameTag();
	}

	@Override
	public Vec3 getPosition(Entity e) {
		return new Vec3(e.posX, e.posY, e.posZ);
	}

	@Override
	public Vec3 getPrevPosition(Entity e) {
		return new Vec3(e.lastTickPosX, e.lastTickPosY, e.lastTickPosZ);
	}

	@Override
	public Vec3 getMotion(Entity e) {
		return new Vec3(e.motionX, e.motionY, e.motionZ);
	}

	@Override
	public float getRotationYaw(Entity e) {
		return e.rotationYaw;
	}

	@Override
	public float getRotationPitch(Entity e) {
		return e.rotationPitch;
	}

	@Override
	public float getPrevRotationYaw(Entity e) {
		return e.prevRotationYaw;
	}

	@Override
	public float getPrevRotationPitch(Entity e) {
		return e.prevRotationPitch;
	}

	@Override
	public boolean isCollided(Entity e) {
		return e.isCollided;
	}

	@Override
	public boolean isOnGround(Entity e) {
		return e.onGround;
	}

	@Override
	public boolean isCollidedHorizontally(Entity e) {
		return e.isCollidedHorizontally;
	}

	@Override
	public boolean isCollidedVertically(Entity e) {
		return e.isCollidedVertically;
	}

	@Override
	public boolean isInWeb(Entity e) {
		return e.isInWeb;
	}

	@Override
	public boolean noClip(Entity e) {
		return e.noClip;
	}

	@Override
	public int getTickExisted(Entity e) {
		return e.ticksExisted;
	}

	@Override
	public boolean isInWater(Entity e) {
		return e.inWater;
	}

	@Override
	public Vec3i getChunkCoords(Entity e) {
		return new Vec3i(e.chunkCoordX, e.chunkCoordY, e.chunkCoordZ);
	}

	@Override
	public Vec3 getServerPos(Entity e) {
		return new Vec3(e.serverPosX, e.serverPosY, e.serverPosZ);
	}

	@Override
	public int getDimension(Entity e) {
		return e.dimension;
	}

}
