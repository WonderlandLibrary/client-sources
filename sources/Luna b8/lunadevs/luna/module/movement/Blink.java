package lunadevs.luna.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.util.Timer;

public class Blink extends Module{
	
	private static ArrayList<Packet> packets;
    private EntityOtherPlayerMP fakePlayer;
    private double oldX;
    private double oldY;
    private double oldZ;
    private static long blinkTime;
    private static long lastTime;

    static {
        Blink.packets = new ArrayList<Packet>();
    }
    
	public Blink() {
		super("Blink", Keyboard.KEY_NONE, Category.MOVEMENT, true);
		this.fakePlayer = null;
	}
	
	@Override
	public void onEnable() {
		Minecraft.thePlayer.motionX = -0.009999999776482582;
        Minecraft.thePlayer.motionY = -0.009999999776482582;
        Minecraft.thePlayer.motionZ = -0.009999999776482582;
        Blink.lastTime = System.currentTimeMillis();
        this.oldX = Minecraft.thePlayer.posX;
        this.oldY = Minecraft.thePlayer.posY;
        this.oldZ = Minecraft.thePlayer.posZ;
        (this.fakePlayer = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.thePlayer.getGameProfile())).clonePlayer(Minecraft.thePlayer, true);
        this.fakePlayer.copyLocationAndAnglesFrom(Minecraft.thePlayer);
        this.fakePlayer.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
        Minecraft.theWorld.addEntityToWorld(-69, this.fakePlayer);
        super.onEnable();
	}
	
	public static void addToBlinkQueue(final Packet packet) {
        Label_0078: {
            if (Minecraft.thePlayer.posX == Minecraft.thePlayer.prevPosX) {
                final double posZ = Minecraft.thePlayer.posZ;
                Minecraft.getMinecraft();
                if (posZ == Minecraft.thePlayer.prevPosZ) {
                    final double posY = Minecraft.thePlayer.posY;
                    Minecraft.getMinecraft();
                    if (posY == Minecraft.thePlayer.prevPosY) {
                        break Label_0078;
                    }
                }
            }
            Blink.blinkTime += System.currentTimeMillis() - Blink.lastTime;
            Blink.packets.add(packet);
        }
        Blink.lastTime = System.currentTimeMillis();
    }
	
	public void cancel() {
        Blink.packets.clear();
        Minecraft.thePlayer.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch);
        this.setState(false);
    }

	@Override
	public void onDisable() {
		for (final Packet packet : Blink.packets) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
        }
        Blink.packets.clear();
        Minecraft.theWorld.removeEntityFromWorld(-69);
        this.fakePlayer = null;
        Blink.blinkTime = 0L;
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return "Smooth";
	}

}
