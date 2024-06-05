package me.rich.notifications;

import me.rich.font.CFont;
import me.rich.helpers.render.Shifting;
import me.rich.helpers.render.StopWatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public final class Notification {
	public static final int HEIGHT = 30;
	private final String title;
	public float y;
	private final String content;
	private final int time;
	private final NotificationType type;
	private final StopWatch timer;
	private final Shifting translate;
	private final CFont fontRenderer;
	public double scissorBoxWidth;

	public Notification(String title, String content, NotificationType type, CFont font) {
		this.title = title;
		this.content = content;
		this.time = 1900;
		this.type = type;
		this.timer = new StopWatch();
		this.fontRenderer = font;
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		this.translate = new Shifting(sr.getScaledWidth() - this.getWidth(), sr.getScaledHeight() - 30);
	}

	public final int getWidth() {
		return Math.max(100,
				Math.max(this.fontRenderer.getStringWidth(this.title), this.fontRenderer.getStringWidth(this.content))
						+ 10);
	}

	public final String getTitle() {
		return this.title;
	}

	public final String getContent() {
		return this.content;
	}

	public final int getTime() {
		return this.time;
	}

	public final NotificationType getType() {
		return this.type;
	}

	public final StopWatch getTimer() {
		return this.timer;
	}

	public final Shifting getTranslate() {
		return this.translate;
	}
}
