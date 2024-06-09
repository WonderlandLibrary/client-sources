package Squad.Modules.Combat;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventPreMotion;
import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySpellParticleFX.MobFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;

public class ModKillAura extends Module {

	private float delay;
	private float range;
	private int random;
	Entity e1;
	public boolean rotated = false;
	private TimeHelper auraHitDelay = new TimeHelper();
	private TimeHelper heuristicsBypassDelay = new TimeHelper();
	private Random r = new Random();

	public ModKillAura() {
		super("Aura", Keyboard.KEY_R, 0xf44146, Category.Combat);

	}

	public void setup() {
		ArrayList<String> options2 = new ArrayList<>();
		options2.add("NCP-Aura1");
		options2.add("AAC-Aura1");
		Squad.instance.setmgr.rSetting(new Setting("Mode", this, "NCP-Aura1", options2));

		Squad.instance.setmgr.rSetting(new Setting("Delay", this, 4.33, 1, 70.0, false));
		Squad.instance.setmgr.rSetting(new Setting("Range", this, 4.0, 1.0, 6.0, false));
		Squad.instance.setmgr.rSetting(new Setting("EntityID", this, false));
		Squad.instance.setmgr.rSetting(new Setting("Ping", this, false));

	}

	@EventTarget
	public void onEvent(EventUpdate e) {
		delay = ((float) Squad.instance.setmgr.getSettingByName("Delay").getValDouble());
		range = ((float) Squad.instance.setmgr.getSettingByName("Range").getValDouble());
	}

	@EventTarget
	public void onTick(EventUpdate event) {
		for (Object o : mc.theWorld.loadedEntityList) {
			if (o instanceof EntityLivingBase) {
				if (o != mc.thePlayer) {
					EntityLivingBase e = (EntityLivingBase) o;
					// random = (1 + r.nextInt(60 - 1));
					if (check(e) && (auraHitDelay.isDelayComplete(1000 / delay))) {
						attack(e);
						e1 = e;
						System.out.println(e + "");
					}
				}
			}
		}
	}
	
	public boolean ping(EntityLivingBase e){
		if(Squad.instance.setmgr.getSettingByName("Ping").getValBoolean()){
			if(mc.getNetHandler().getPlayerInfo(e.getUniqueID()).getResponseTime() > 5)
			{
				return true;
			}
		} else {
			return true;
		}
		
		return false;
		
	}

	private boolean check(EntityLivingBase e) {
		if (e.isInvisible())
			return false;
		if (e.getHealth() <= 0)
			return false;
		if (e instanceof Entity
				&& e.getDisplayName().getUnformattedText().equals("§6Dealer")) {
			return false;
		}
		if (Squad.instance.setmgr.getSettingByName("Mode").getValString().equalsIgnoreCase("NCP-Aura1")
				&& e.ticksExisted < 250)

			return false;
		if (Squad.instance.setmgr.getSettingByName("EntityID").getValBoolean() && e.getEntityId() > 1070000000)
			return false;
		
		return true;

	}

	private void attack(EntityLivingBase e) {
		if (e.getDistanceToEntity(mc.thePlayer) <= range) {
			// mc.thePlayer.sendQueue.addToSendQueue(new
			// C0BPacketEntityAction(mc.thePlayer.posX, mc.thePlayer.posY,
			// mc.thePlayer.posZ, getYaw(e), getPitch(e), true));
			System.out.println(e + "");
			if (rotated) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
				auraHitDelay.setLastMS();
				if (Squad.instance.setmgr.getSettingByName("Mode").getValString().equalsIgnoreCase("AAC-Aura1")) {
					if (rotated) {
						mc.thePlayer.sendQueue
								.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
						mc.thePlayer.swingItem();

					}

				}
				e1 = null;
				rotated = false;
			}

		}
	}

	@EventTarget
	public void onPreMotion(EventPreMotion e) {
		float rot[] = getRotationsNeeded(e1);
		e.setPitch(rot[1]);
		e.setYaw(rot[0]);
		this.rotated = true;
	}

	public float[] getRotationsNeeded(Entity entity) {
		float yaw;
		float pitch;
		double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double diffY;
		if ((entity instanceof EntityLivingBase)) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9D
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
		}
		double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
		return new float[] {
				Minecraft.getMinecraft().thePlayer.rotationYaw
						+ MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
				Minecraft.getMinecraft().thePlayer.rotationPitch
						+ MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
	}

	@EventTarget
	public void onevent(EventUpdate e) {
		if (Squad.instance.setmgr.getSettingByName("Mode").getValString().equalsIgnoreCase("NCP-Aura1")) {
			setDisplayname("Aura §7NCP");
		} else {
			if (Squad.instance.setmgr.getSettingByName("Mode").getValString().equalsIgnoreCase("AAC-Aura1")) {
				setDisplayname("Aura §7AAC");

			}
		}
	}
	
	public void onEnable(){
		EventManager.register(this);
	}
	
	public void onDisable(){
		e1 = null;
		EventManager.unregister(this);
	}

}
