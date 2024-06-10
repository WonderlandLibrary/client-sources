package Snipeware.module.impl.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.events.render.EventRender2D;
import Snipeware.gui.click.util.MathUtil;
import Snipeware.module.Module;
import Snipeware.module.impl.combat.KillAura;
import Snipeware.module.impl.visuals.DMGParticles;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.visual.RenderUtil;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import font.FontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S40PacketDisconnect;

public class SessionInfo extends Module {

	public int rar = -50;

	public TimeHelper timer = new TimeHelper();
	public NumberValue<Integer> transperency = new NumberValue<>("Transperency", 250, 1, 253, 1);

	private int hour = 0;
	private int min = 0;
	private int sec = 0;
	private int Kills = 0;


	
	public SessionInfo() {
		super("SessionInfo", Keyboard.KEY_NONE, ModuleCategory.VISUALS);
		this.addValues(transperency);

	}

	public void ResetTime() {
		MinTimer.reset();
		SecTimer.reset();
	}

	
  
	private TimeHelper MinTimer = new TimeHelper();
	private TimeHelper SecTimer = new TimeHelper();
	private TimeHelper TpsTimer = new TimeHelper();

/*
	private double calculateTPS(int nSamples) {
        final int samples = this.secondRecords.size();

        if (samples < 2) {
            // Too few to calculate
            return 20.0;
        }

        nSamples = Math.min(nSamples, samples);

        long total = 0;

        for (int i = 0; i < nSamples; i++) {
            total += this.secondRecords.get(samples - 1 - i);
        }

        final double avg = (double) total / nSamples;

        return avg / 50;
    }
    */
	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (Client.getInstance().getModuleManager().getModule("SessionInfo").isEnabled()) {
		/*
	        if (event.getPacket() instanceof S03PacketTimeUpdate) {
	            final long current = System.currentTimeMillis();

	            if (this.lastTimeUpdate != 0) {
	                this.secondRecords.add(current - this.lastTimeUpdate);
	            }

	            this.lastTimeUpdate = System.currentTimeMillis();
	        }
			*/
			if (event.getPacket() instanceof S02PacketChat) {
				final S02PacketChat packet = (S02PacketChat) event.getPacket();
				String text = packet.getChatComponent().getUnformattedText();
				if (text.contains("foi morto por " + mc.thePlayer.getGameProfile().getName())) {
					Kills += 1;
				} else if (text.contains("by " + mc.thePlayer.getGameProfile().getName())) {
					Kills += 1;
				}
			}
		}
	}

	@Handler
	public void onRender2D(EventRender2D event) {

	
		if (GuiConnecting.isconnected == true) {
		
		
			
			if (MinTimer.isDelayComplete(60000)) {
				min += 1;
				if (min >= 60) {
					hour += 1;
					min = 0;
				}
				MinTimer.reset();
			}
			if (SecTimer.isDelayComplete(1000)) {
				if (sec >= 60) {
					sec = 0;
				}
				sec += 1;
				SecTimer.reset();
			}

		} else {
			ResetTime();
			hour = 0;
			min = 0;
			sec = 0;
		}
		if (mc.thePlayer != null && Client.getInstance().getModuleManager().getModule("SessionInfo").isEnabled()) {

			final ScaledResolution scaledResolution2;
			final ScaledResolution scaledResolution = scaledResolution2 = new ScaledResolution(mc);
			double x = scaledResolution2.getScaledWidth() / 2;
			double y = scaledResolution2.getScaledHeight() / 2;
			Color darkgary2 = new Color(65, 65, 65, 210);
			double rectwidth = 120;
			double rectheight = 65;

			int rectX = 5;
			int rectY = 125;

			EntityPlayerSP player = mc.thePlayer;
			double xDist = player.posX - player.lastTickPosX;
			double zDist = player.posZ - player.lastTickPosZ;
			float d = (float) StrictMath.sqrt(xDist * xDist + zDist * zDist);
			String Text = String.format("%.2f", d * 20 * mc.timer.timerSpeed);
			String Text2 = String.valueOf(mc.getCurrentServerData().pingToServer);
			String Text3 = String.valueOf(hour) + "h " + String.valueOf(min) + "m " + String.valueOf(sec) + "s";
			RenderUtil.drawRect(rectX - 2.5f, rectY - 2.5f, rectwidth + 5, rectheight + 5, 
					new Color(44, 44, 44, transperency.getValue()).getRGB());
			
			RenderUtil.drawRect(rectX, rectY, rectwidth, rectheight, 
					new Color(55, 55, 55, transperency.getValue()).getRGB());
			
			RenderUtil.drawRoundedRect(rectX + 5, rectY + 14, rectwidth - 10, rectheight / 16, 5,
					new Color(65, 65, 65, transperency.getValue()).getRGB());
		
				
			final FontRenderer font2 = Client.INSTANCE.getFontManager().getFont("Display 20", false);
			final FontRenderer font = Client.INSTANCE.getFontManager().getFont("Display 17", false);
			font2.drawStringWithShadow("Session Info", rectX / 2 + 37, rectY + 3,
					new Color(255, 255, 255, transperency.getValue()).getRGB());
			font.drawString("Time Played: " + Text3, rectX / 2 + 9, rectY + 23,
					new Color(255, 255, 255, transperency.getValue()).getRGB());
			font.drawString("Sync Speed: " + Text, rectX / 2 + 9, rectY + 33,
					new Color(255, 255, 255, transperency.getValue()).getRGB());
			font.drawString("Ping: " + Text2, rectX / 2 + 9, rectY + 43,
					new Color(255, 255, 255, transperency.getValue()).getRGB());
			font.drawString("Kills: " + String.valueOf(Kills), rectX / 2 + 9, rectY + 53,
					new Color(255, 255, 255, transperency.getValue()).getRGB());

		}
	}

	public void onEnable() {
		ResetTime();
		super.onEnable();
	}

	public void onDisable() {
		ResetTime();
		super.onDisable();
	}

}