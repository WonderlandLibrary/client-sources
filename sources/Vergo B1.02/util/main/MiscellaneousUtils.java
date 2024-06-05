package xyz.vergoclient.util.main;

import java.awt.AWTException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.json.JSONException;
import org.json.JSONObject;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.security.ApiResponse;
import xyz.vergoclient.security.account.Account;
import xyz.vergoclient.ui.guis.GuiAltManager;
import xyz.vergoclient.ui.guis.LogInGui;
import xyz.vergoclient.util.datas.DataDouble3;
import xyz.vergoclient.util.datas.DataDouble6;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import xyz.vergoclient.files.impl.FileAlts;

public class MiscellaneousUtils {

	public static Minecraft mc = Minecraft.getMinecraft();

	public static float getMaxFallDist() {
		PotionEffect potioneffect = mc.thePlayer.getActivePotionEffect(Potion.jump);
		int f = potioneffect != null ? (potioneffect.getAmplifier() + 1) : 0;
		return mc.thePlayer.getMaxFallHeight() + f;
	}

	public static void damage() {
		double damageOffset = 0, damageY = 0, damageYTwo = 0;
		damageOffset = 0.06011F;
		damageY = 0.000495765F;
		damageYTwo = 0.0049575F;
		NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getNetHandler();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		for (int i = 0; i < (getMaxFallDist() / (damageOffset - 0.005F)) + 1; i++) {
			netHandler.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + damageOffset, z, false));
			netHandler.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + damageY, z, false));
			netHandler.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + damageYTwo + damageOffset * 0.000001, z, false));
		}
		netHandler.getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
	}

	public static float clampValue(final float value, final float floor, final float cap) {
		if (value < floor) {
			return floor;
		}
		return Math.min(value, cap);
	}

	public static int clampValue(final int value, final int floor, final int cap) {
		if (value < floor) {
			return floor;
		}
		return Math.min(value, cap);
	}
	
    public static boolean isPosSolid(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        if ((block.getMaterial().isSolid() || !block.isTranslucent() || block instanceof BlockLadder || block instanceof BlockCarpet
                || block instanceof BlockSnow || block instanceof BlockSkull)
                && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer)) {
            return true;
        }
        return false;
    }
    
    public static boolean shouldRenderCapeOnPlayer(AbstractClientPlayer player) {
    	
    	if (player.getName() == Minecraft.getMinecraft().thePlayer.getName()) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }

	public static ApiResponse parseApiResponse(String json) {
		JSONObject obj = new JSONObject(json);
		ApiResponse apiResponse = new ApiResponse();
		for (ApiResponse.ResponseStatus responseStatus : ApiResponse.ResponseStatus.values()) {
			if (obj.getString("authenticated").equals(responseStatus.toString())) {
				apiResponse.status = responseStatus;
				break;
			}
		}
		return apiResponse;
	}

	public static Account parseAccount(String json) {
		JSONObject obj = new JSONObject(json);
		return new Account(obj.getInt("uid"), obj.getString("username"), obj.getString("hwid"), obj.getInt("banned"));
	}
    
	public static String getTeamName(int num, Scoreboard board) {
		ScoreObjective objective = board.getObjectiveInDisplaySlot(1);
        Collection collection = board.getSortedScores(objective);
        ArrayList arraylist = Lists.newArrayList(Iterables.filter(collection, new Predicate()
        {
            public static final String __OBFID = "CL_00001958";
            public boolean apply(Score p_apply_1_)
            {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.apply((Score)p_apply_1_);
            }
        }));
        
        try {
			Score score = (Score) arraylist.get(num);
			ScorePlayerTeam scoreplayerteam = board.getPlayersTeam((score).getPlayerName());
			//String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, ((Score) score).getPlayerName()) + ": " + EnumChatFormatting.RED + ((Score) score).getScorePoints();
			String s = ScorePlayerTeam.formatPlayerName(scoreplayerteam, ((Score) score).getPlayerName());
			return s;
		} catch (Exception e) {
			//e.printStackTrace();
		}
        
        return "ERROR";
        
	}
	
	public static String getFormattedTime() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		
		String message = dtf.format(now);
		
		String[] times = message.split(":");
		
		if (Integer.valueOf(times[0]) >= 12 && Integer.valueOf(times[0]) < 24) {
			message = message.replaceAll("13:", "01:").replaceAll("14:", "02:").replaceAll("15:", "03:").replaceAll("16:", "04:").replaceAll("17:", "05:").replaceAll("18:", "06:").replaceAll("19:", "07:").replaceAll("20:", "08:").replaceAll("21:", "09:").replaceAll("22:", "10:").replaceAll("23:", "11:").replaceAll("24:", "12:");
			message += " PM";
		}
		else if (Integer.valueOf(times[0]) <= 0) {
			message = message.replaceAll("00:", "12:");
			message += " AM";
		}
		else if (Integer.valueOf(times[0]) <= 12) {
			message += " AM";
		}
		
		return message;
		
	}
	
	public static String getFormattedDate() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

		String formattedDate = formatter.format(LocalDate.now());
		return formattedDate;
		
	}
	
	public static String getFormattedSessionTime() {
		String sessionTimeFormatted = "";
		long sessionTime = System.currentTimeMillis() - ModuleManager.sessionTime;
		if (((sessionTime - (sessionTime % 86400000)) / 86400000) > 0)
			sessionTimeFormatted += ((sessionTime - (sessionTime % 86400000)) / 86400000) + "d ";
		sessionTime = sessionTime % 86400000;
		if (((sessionTime - (sessionTime % 3600000)) / 3600000) > 0)
			sessionTimeFormatted += ((sessionTime - (sessionTime % 3600000)) / 3600000) + "h ";
		sessionTime = sessionTime % 3600000;
		if (((sessionTime - (sessionTime % 60000)) / 60000) > 0)
			sessionTimeFormatted += ((sessionTime - (sessionTime % 60000)) / 60000) + "m ";
		sessionTime = sessionTime % 60000;
		sessionTimeFormatted += ((sessionTime - (sessionTime % 1000)) / 1000) + "s ";
		sessionTime = sessionTime % 1000;
		return sessionTimeFormatted.substring(0, sessionTimeFormatted.length() - 1);
	}

	public static void connectToServer(String ip, int port, Minecraft mc) {
		ServerData connect = new ServerData("temp", ip + ":" + port, false);
		mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, connect));
	}

	public static void setPosAndMotionWithDataDouble6(DataDouble6 posAndMotion) {
		
		Minecraft.getMinecraft().thePlayer.setPosition(posAndMotion.x, posAndMotion.y, posAndMotion.z);
		Minecraft.getMinecraft().thePlayer.motionX = posAndMotion.motionX;
		Minecraft.getMinecraft().thePlayer.motionY = posAndMotion.motionY;
		Minecraft.getMinecraft().thePlayer.motionZ = posAndMotion.motionZ;
		
	}
	
	public static String getRandomName() {
		return "https://E621.net";
	}
	
	public static String getRandomString(int length) {
//		String chars = "1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./<>?\":|}{)(*&^%$#@!";
		String chars = "QWERTYUIOPASDFGHJKLZXCVBNM";
		String rand = "";
		for (int i = 0; i < length; i++) {
			rand += chars.toCharArray()[new Random().nextInt(chars.length())];
		}
		return rand;
	}
	
	public static String getHypixelMode() {
		try {
			ScoreObjective scoreobjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			String scoreTitle = scoreobjective.getDisplayName();
			String mode = "";
			boolean removeNext = false;
			for (char c : scoreTitle.toCharArray()) {
				if (c == "\247".toCharArray()[0]) {
					removeNext = true;
				}
				else if (!removeNext) {
					mode += c;
				}else {
					removeNext = false;
				}
			}
			mode = mode.toLowerCase();
			return mode;
		} catch (Exception e) {}
		return "";
	}
	
	public static boolean canAccessInternet() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void setAltBanStatusHypixel(IChatComponent message) {
		new Thread("Ban time updater") {
        	public void run() {
        		
                try {
                	long dateUnbanned = System.currentTimeMillis();
                	String time = message.getSiblings().get(1).getFormattedText().substring(2, message.getSiblings().get(1).getFormattedText().length() - 2);
                	if (time.equalsIgnoreCase("reason: ")) {
                		for (int e = 0; e < GuiAltManager.altsFile.alts.size(); e++) {
                    		try {
                    			FileAlts.Alt a = GuiAltManager.altsFile.alts.get(e);
                    			if (a.username.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                        			a.banTime = Long.MAX_VALUE;
                        			return;
                        		}
        					} catch (Exception e2) {
        						
        					}
                    	}
                		return;
                	}
                	String[] times = time.split(" ");
                	for (String date : times) {
                		try {
                			String numbers = date.substring(0, date.length() - 1);
                    		if (date.endsWith("d")) {
                    			dateUnbanned += TimeUnit.DAYS.toMillis(Integer.valueOf(numbers));
                    		}
                    		else if (date.endsWith("h")) {
                    			dateUnbanned += TimeUnit.HOURS.toMillis(Integer.valueOf(numbers));
                    		}
                    		else if (date.endsWith("m")) {
                    			dateUnbanned += TimeUnit.MINUTES.toMillis(Integer.valueOf(numbers));
                    		}
                    		else if (date.endsWith("s")) {
                    			dateUnbanned += TimeUnit.SECONDS.toMillis(Integer.valueOf(numbers));
                    		}
        				} catch (Exception e) {
        					e.printStackTrace();
        				}
                	}
                	
                	for (int e = 0; e < GuiAltManager.altsFile.alts.size(); e++) {
                		try {
                			FileAlts.Alt a = GuiAltManager.altsFile.alts.get(e);
                			if (a.username.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                    			a.banTime = dateUnbanned;
                    		}
        				} catch (Exception e2) {
        					
        				}
                	}
        		} catch (Exception e) {
        			
        		}
        		
        	}
        }.start();
	}
	
	public static void setAltUnbannedHypixel() {
		new Thread("Ban time updater") {
        	public void run() {
        		for (int e = 0; e < GuiAltManager.altsFile.alts.size(); e++) {
            		try {
            			FileAlts.Alt a = GuiAltManager.altsFile.alts.get(e);
//            			ChatUtils.addChatMessage(Minecraft.getMinecraft().getSession().getUsername() + " " + a.username);
            			if (a.username.equals(Minecraft.getMinecraft().getSession().getUsername())) {
                			a.banTime = System.currentTimeMillis();
//                			ChatUtils.addChatMessage("testing");
                		}
    				} catch (Exception e2) {
    					e2.printStackTrace();
    				}
            	}
        	}
        }.start();
	}
	
	public static List<EntityPlayer> getTabPlayerList() {
        final NetHandlerPlayClient var4 = Minecraft.getMinecraft().thePlayer.sendQueue;
        final List<EntityPlayer> list = new ArrayList<>();
        final List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {
                continue;
            }
            list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }
	
	// These numbers are used for smooth render positions
	public static Vec3 getRenderEntityPos(EntityPlayer player) {
        float timer = Minecraft.getMinecraft().timer.renderPartialTicks;
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * timer;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * timer;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * timer;
        return new Vec3(x, y, z);
	}
	
	public static double get2dDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}
	
	public static void simulateMouseClick() throws AWTException{
		Minecraft.getMinecraft().clickMouse();
	}
	
	public static DataDouble3 getClosestPointFromBoundingBox(AxisAlignedBB boundingBox, double x, double y, double z) {
		
		if (x > boundingBox.maxX) {
			x = boundingBox.maxX;
		}
		else if (x < boundingBox.minX) {
			x = boundingBox.minX;
		}
		
		if (y > boundingBox.maxY) {
			y = boundingBox.maxY;
		}
		else if (y < boundingBox.minY) {
			y = boundingBox.minY;
		}
		
		if (z > boundingBox.maxZ) {
			z = boundingBox.maxZ;
		}
		else if (z < boundingBox.minZ) {
			z = boundingBox.minZ;
		}
		
		return new DataDouble3(x, y, z);
		
	}
	
	public static String removeFormattingCodes(String input) {
		try {
			String output = "";
			boolean removeNext = false;
			for (char c : input.toCharArray()) {
				if (c == "\247".toCharArray()[0]) {
					removeNext = true;
				}
				else if (!removeNext) {
					output += c;
				}else {
					removeNext = false;
				}
			}
			return output;
		} catch (Exception e) {}
		return "";
	}
	
}
