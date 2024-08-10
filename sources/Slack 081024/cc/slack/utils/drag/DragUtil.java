package cc.slack.utils.drag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@Setter
@AllArgsConstructor
public class DragUtil {
	
	/*
	 * Only for render 2d for now..
	 */
	
	private double x, y, width, height;
	private float scale;

	public boolean isInside(int x, int y) {

		return x > getX() && y > getY() && x < getX() + getWidth() && y < getY() + getHeight();
	}

	public static double[] setScaledPosition(double x, double y) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		return new double[] { x,  y };
	}

	public static double[] setPosition(double x, double y) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

		return new double[] { x, y  };
	}
}
