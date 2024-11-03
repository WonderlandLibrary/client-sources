package net.silentclient.client.mods.hud;

import java.text.DecimalFormat;

import net.minecraft.util.MathHelper;
import net.silentclient.client.mods.HudMod;
import net.silentclient.client.mods.ModCategory;

public class SpeedometerMod extends HudMod {
	
	private static final int SPEED_COUNT = 200;
	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");
	private double[] speeds = new double[SPEED_COUNT];
	
	public SpeedometerMod() {
		super("Speedometer", ModCategory.MODS, "silentclient/icons/mods/speedometer.png");
	}
	
	public void addSpeed(double speed) {
		System.arraycopy(speeds, 1, speeds, 0, SPEED_COUNT - 1);
		speeds[SPEED_COUNT - 1] = speed;
	}
	
	private double getSpeed() {
		double xTraveled = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zTraveled = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		return MathHelper.sqrt_double(xTraveled * xTraveled + zTraveled * zTraveled);
	}
	
	@Override
	public String getText() {
		return "00,00 " + getPostText();
	}
	
	@Override
	public String getTextForRender() {
		return FORMAT.format(getSpeed() / 0.05F) + " " + getPostText();
	}
	
	@Override
	public String getDefautPostText() {
		return "m/s";
	}
}
