package de.tired.notification.newnotifications;

import de.tired.api.extension.Extension;
import de.tired.api.util.math.TimerUtil;
import de.tired.api.util.font.FontManager;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import de.tired.interfaces.FHook;
import de.tired.interfaces.IHook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

@AllArgsConstructor
public class Notify implements IHook {

	private String notifyMessage;
	private final TimerUtil timerUtil;
	@Getter
	private double width, height, yAxis, lastYaxis;

	private long stayTime;
	private double animation;

	private String title;

	public Notify(String title, String notifyMessage) {
		this.notifyMessage = notifyMessage;
		this.title = title;
		(this.timerUtil = new TimerUtil()).doReset();
		this.width = 	FHook.fontRenderer3.getStringWidth(notifyMessage) + 29;
		this.height = 	23.0;
		this.animation = this.width;
		this.stayTime = 2000L;
		this.yAxis = -1.0;
	}

	public void renderNotification(double gettingY, double lastYaxis, boolean rect, boolean text) {
		this.lastYaxis = lastYaxis;
		float width = (float) (this.finished() ? this.width : 0.0);
		this.animation = AnimationUtil.getAnimationState(this.animation, this.finished() ? this.width : 0.0, Math.max(3.6D, Math.abs((double) animation - width)) * 2);
		if (yAxis == -1) {
			this.yAxis = gettingY;
		} else {
			this.yAxis = AnimationUtil.getAnimationState(yAxis, gettingY, 120);
		}
		final ScaledResolution res = new ScaledResolution(MC);

		final int x1 = (int) (res.getScaledWidth() - this.width + this.animation);
		final int x2 = (int) (res.getScaledWidth() + this.animation);
		final int y1 = (int) this.yAxis;
		final int y2 = (int) (y1 + this.height);

		//RenderProcessor.drawGradientSideways(x1, y1 - 1, x2, y2 , ClickGUI.getInstance().colorPickerSetting.ColorPickerC.darker().getRGB(), ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRGB());

		if (rect) {
			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawRoundedRectangle(x1, y1, x2, y2, 4,  new Color(20, 20, 20, 120).getRGB());
		}

		if (text) {
			FHook.fontRenderer3.drawString(this.title, (float) (x1) + 17, (float) (y1 + this.height / 3.5), -1);
			FHook.fontRenderer3.drawString(this.notifyMessage, (float) (x1) + 17, (float) (y1 + this.height / 3.5) + 9, -1);

			//RenderProcessor.drawRoundedRectangle(x1, y1, x2 - 150, y2, 7, new Color(10, 10, 10, 50).getRGB());

			GlStateManager.disableBlend();

			Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawCheckMark((float) (x1) + 12.0f, (float) (y1 + this.height / 3.5) - 2.5f, 7,new Color(9, 190, 107).getRGB() );

		}

	}

	public boolean finished() {
		return timerUtil.reachedTime(stayTime) && yAxis == lastYaxis;
	}

	public boolean shouldDelete() {
		return finished() && animation >= width;
	}

}
