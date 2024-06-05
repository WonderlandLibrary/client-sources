package xyz.vergoclient.util.main;

import net.minecraft.client.gui.Gui;
import xyz.vergoclient.util.datas.DataDouble5;

import java.awt.*;

public class GuiUtils {
	
	public static boolean isMouseOverDataDouble5(int mouseX, int mouseY, DataDouble5 data5d) {
		double left = data5d.x1,
			top = data5d.y1,
			right = data5d.x2,
			bottom = data5d.y2;
		
		if (left > right) {
			double temp = left;
			left = right;
			right = temp;
		}
		
		if (top > bottom) {
			double temp = top;
			top = bottom;
			bottom = temp;
		}
		
		return mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom;
		
	}
	
}
