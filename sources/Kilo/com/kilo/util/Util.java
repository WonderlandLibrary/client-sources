package com.kilo.util;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Session;
import net.minecraft.util.Vec3;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class Util {
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static final float doubleClickTimer = 0.3f;

	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float)1/255)*c.getRed();
		float g = ((float)1/255)*c.getGreen();
		float b = ((float)1/255)*c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}
	
	public static int blendColor(int color1, int color2, float perc){
		Color x = new Color(color1);
		Color y = new Color(color2);

		float inverse_blending = 1 - perc;

		float red =   x.getRed()   * perc   +   y.getRed()   * inverse_blending;
		float green = x.getGreen() * perc   +   y.getGreen() * inverse_blending;
		float blue =  x.getBlue()  * perc   +   y.getBlue()  * inverse_blending;

		Color blended;
		try{
			blended = new Color (red / 255, green / 255, blue / 255);
		} catch (Exception e){
			blended = new Color(-1); 
		}
		
		return blended.getRGB();
	}
	
	public static String replaceFormat(String s) {
		return s.replaceAll("(?i)&([a-f0-9])", "\u00a7$1");
	}
	
	public boolean isASCII(char c) {
		return "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c) != -1;
	}
	
	public static void openWeb(String s) {
		try {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		        try {
		            desktop.browse(new URI(s));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
        } catch (Exception e) {}
	}
	
	public static List<String> usernameHistory(String username) {
		String uuid = null;
		List<String> names = new ArrayList<String>();
		try {
			uuid = getUUIDFromName(username);
		} catch (Exception e) {}
		
		if (uuid == null) {
			return names;
		}
		
		try {
			names = getNamesFromUUID(uuid);
		} catch (Exception e) {}

		return names;
	}
	
	public static String getUUIDFromName(String username) throws Exception {
		URL getUUID = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
		BufferedReader in = new BufferedReader(new InputStreamReader(getUUID.openStream()));

		String inputLine;
		String line = "";
		while ((inputLine = in.readLine()) != null) {
			line = inputLine;
		}
		in.close();

		JSONObject jo = new JSONObject(line);
		return jo.getString("id");
	}
	
	public static List<String> getNamesFromUUID(String uuid) throws Exception {
		List<String> names = new ArrayList<String>();
		URL getNames = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
		BufferedReader in = new BufferedReader(new InputStreamReader(getNames.openStream()));

		String inputLine = "";
		String line = "";
		while ((inputLine = in.readLine()) != null) {
			line = inputLine;
		}
		in.close();
		
		JSONArray ja = new JSONArray(line);
		
		for(int i = 0; i < ja.length(); i++) {
			JSONObject jao = new JSONObject(ja.get(i).toString());
			names.add(jao.getString("name"));
		}
		
		return names;
	}
	
	public static double safeDiv(double x, double y) {
		try {
			return x/y;
		} catch (Exception e) {
			return 1;
		}
	}
	public static String prettyFloat(double f) {
		if (f == (long) f) {
			return String.format("%d", (long)f);
		}
		return String.format("%s", f);
	}
	
	public static float angleDifference(float a, float b){
		return ((((a - b) % 360) + 540f) % 360) - 180f;
	}

	public static float makeFloat(Object o) {
		try {
			return Float.parseFloat(String.valueOf(o));
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static int makeInteger(Object o) {
		try {
			return Integer.parseInt(String.valueOf(prettyFloat(makeFloat(o))));
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static boolean makeBoolean(Object o) {
		try {
			return Boolean.parseBoolean(String.valueOf(o));
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean canSeeEntity(EntityLivingBase from, EntityLivingBase to) {
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.posX, to.posY + (double)to.getEyeHeight(), to.posZ)) == null) {
			return true;
		}

		if (to.getEntityBoundingBox() == null) {
			return false;
		}
		
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().minX, to.getEntityBoundingBox().maxY, to.getEntityBoundingBox().minZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().minX, to.getEntityBoundingBox().maxY, to.getEntityBoundingBox().maxZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().maxX, to.getEntityBoundingBox().maxY, to.getEntityBoundingBox().maxZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().maxX, to.getEntityBoundingBox().maxY, to.getEntityBoundingBox().minZ)) == null) {
			return true;
		}

		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().minX, to.getEntityBoundingBox().minY, to.getEntityBoundingBox().minZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().minX, to.getEntityBoundingBox().minY, to.getEntityBoundingBox().maxZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().maxX, to.getEntityBoundingBox().minY, to.getEntityBoundingBox().maxZ)) == null) {
			return true;
		}
		if (from.worldObj.rayTraceBlocks(new Vec3(from.posX, from.posY + (double)from.getEyeHeight(), from.posZ), new Vec3(to.getEntityBoundingBox().maxX, to.getEntityBoundingBox().minY, to.getEntityBoundingBox().minZ)) == null) {
			return true;
		}
		
		return false;
	}
	
	public static float[] getRotationToBlockPos(BlockPos bp) {
		double pX = mc.thePlayer.posX;
		double pY = mc.thePlayer.posY+mc.thePlayer.getEyeHeight();
		double pZ = mc.thePlayer.posZ;

		double eX = bp.getX()+0.5f;
		double eY = bp.getY()+0.5f;
		double eZ = bp.getZ()+0.5f;
		
		double dX = pX-eX;
		double dY = pY-eY;
		double dZ = pZ-eZ;
		double dH = Math.sqrt(Math.pow(dX, 2)+Math.pow(dZ, 2));

		float yaw = 0;
		float pitch = 0;
		
		yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX))+90);
		pitch = (float)(Math.toDegrees(Math.atan2(dH, dY)));
		
		return new float[] {yaw, 90-pitch};
	}
	
	public static float[] getRotationToPos(double x, double y, double z) {
		double pX = mc.thePlayer.posX;
		double pY = mc.thePlayer.posY+mc.thePlayer.getEyeHeight();
		double pZ = mc.thePlayer.posZ;

		double eX = x;
		double eY = y;
		double eZ = z;
		
		double dX = pX-eX;
		double dY = pY-eY;
		double dZ = pZ-eZ;
		double dH = Math.sqrt(Math.pow(dX, 2)+Math.pow(dZ, 2));

		float yaw = 0;
		float pitch = 0;
		
		yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX))+90);
		pitch = (float)(Math.toDegrees(Math.atan2(dH, dY)));
		
		return new float[] {yaw, 90-pitch};
	}
}
