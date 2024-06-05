package me.vaziak.sensation.client.api;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import me.vaziak.sensation.client.impl.visual.Notifications;
import me.vaziak.sensation.client.impl.visual.notifications.NotificationData;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.property.impl.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public abstract class Module {
	protected String mode;
	private final String id;
	private int bind;
	private final Category category;
	protected final LinkedHashMap<String, Value> propertyRegistry;
	protected boolean state;
	protected Minecraft mc = Minecraft.getMinecraft();
	public int animation;
	public Color color;
	private long enableTime;
	private TimerUtil stopwatch = new TimerUtil();

	public Module(String id, Category category) {
		this.id = id;
		bind = Keyboard.KEY_NONE;
		this.category = category;
		propertyRegistry = new LinkedHashMap<>();
		state = false;
	}
	public Module(String id,int bind) {
		this.id = id;
		this.bind = bind;
		category = Category.MISC;
		propertyRegistry = new LinkedHashMap<>();
		state = false;
	}

	public Module(String id, int bind, Category category) {
		this.id = id;
		this.bind = bind;
		this.category = category;
		propertyRegistry = new LinkedHashMap<>();
		state = false;
	}
	public void forceDisable(String reason) {
		state = false;
		Sensation.instance.eventBus.unregister(this);
	}
	public int getBind() {
		return bind;
	}

	public Category getCategory() {
		return category;
	}
	
	public String getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public HashMap<String, Value> getPropertyRegistry() {
		return propertyRegistry;
	}

	public boolean getState() {
		return state;
	}

	protected void onDisable() {
		if (mc.thePlayer == null) return;

	}

	protected void onEnable() {
		if (mc.thePlayer == null) return;
		Random random = new Random();
		float r = random.nextFloat() / 2f + .5f;
		float g = random.nextFloat() / 2f + .5f;
		float b = random.nextFloat() / 2f + .5f;
		color = new Color(r, g, b);
	}

	protected void registerValue(Value... properties) {
		for (Value property : properties) {
			propertyRegistry.put(property.getId(), property);
		}
	}

	protected void sendPacket(Packet packet) {
		if (mc.thePlayer != null) {
			mc.thePlayer.sendQueue.addToSendQueue(packet);
		}
	}

	protected void sendPacketNoEvent(Packet packet) {
		if (mc.thePlayer != null) {
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(packet);
		}
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public void setMode(String themode) {
		mode = themode;
	}

	public void setState(boolean state, boolean notification) {
		if (this.state == state) {
			return;
		}

		Random random = new Random();

		float r = random.nextFloat() / 2f + .5f;
		float g = random.nextFloat() / 2f + .5f;
		float b = random.nextFloat() / 2f + .5f;

		color = new Color(r, g, b);

		this.state = state;

		if (notification) {
			Notifications notifications = (Notifications) Sensation.instance.cheatManager.getCheatRegistry().get("Notifications");

			if (state) {
				notifications.notificationData.add(new NotificationData("Module Status", "Module Enabled: " + EnumChatFormatting.GREEN + getId(), System.currentTimeMillis(), NotificationData.NotificationType.INFO));
			} else {
				notifications.notificationData.add(new NotificationData("Module Status", "Module Disabled: " + EnumChatFormatting.RED + getId(), System.currentTimeMillis(), NotificationData.NotificationType.INFO));
			}
		}
		if (mc.theWorld != null) {
			mc.theWorld.playSoundEffect(mc.thePlayer.posX + 0.5D, mc.thePlayer.posY + 0.5D, mc.thePlayer.posZ+ 0.5D, "random.click", 0.3F, state ? 0.6F : 0.5F);
		}
		if (state) {
			Sensation.eventBus.register(this);
			onEnable();
			animation = 0;
			enableTime = System.currentTimeMillis();
		} else {
			Sensation.eventBus.unregister(this);
			onDisable();
			animation = 0;
		}
	}
	
	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}
	
	public double getBaseMoveSpeed(double baseSpeed) {
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}
	
	
    public void setPosition(double value) {
    	double x = mc.thePlayer.posX;
    	double y = mc.thePlayer.posY;
    	double z = mc.thePlayer.posZ;
    	mc.thePlayer.setPosition(x, y + value, z);
    }
    
    public void setPositionSpecific(double value) {
    	double x = mc.thePlayer.posX;
    	double y = mc.thePlayer.posY;
    	double z = mc.thePlayer.posZ;
    	mc.thePlayer.setPosition(x, value, z);
    }
    
    public void sendC04(double value, boolean ground) {
    	double x = mc.thePlayer.posX;
    	double y = mc.thePlayer.posY;
    	double z = mc.thePlayer.posZ;
    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + value, z, ground));
    }
    
    public void sendC04Specific(double value, boolean ground) {
    	double x = mc.thePlayer.posX;
    	double y = mc.thePlayer.posY;
    	double z = mc.thePlayer.posZ;
    	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, value, z, ground));
    }
    
	public double getGroundLevel() {
		for (int i = (int) Math.round(mc.thePlayer.posY); i > 0; --i) {
			AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().addCoord(0, 0, 0);
			box.minY = i - 1;
			box.maxY = i;
			if (isColliding(box) && box.minY <= mc.thePlayer.posY)
				return i;
		}
		return -999; // Just making sure that I can check for a value that will never happen. 0 is possible.
	}
	
	private boolean isColliding(AxisAlignedBB box) {
		return mc.theWorld.checkBlockCollision(box);
	}

	public boolean onServer(String server) {
		return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains(server);
	}

	public long getEnableTime() {
		return enableTime;
	}
}
