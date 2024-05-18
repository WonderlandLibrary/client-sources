package mods.togglesprint.me.imfr0zen.guiapi;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import mods.togglesprint.me.imfr0zen.guiapi.components.Button;
import mods.togglesprint.me.imfr0zen.guiapi.components.GuiComponent;

public class GuiFrame implements Frame {

	public static int draggingId;

	private boolean visible;
	private boolean expanded;
	private boolean dragging;

	private int id;
	public int posX;
	public int posY;
	private int prevPosX;
	private int prevPosY;

	public String title;

	private ArrayList<Button> buttons = new ArrayList<Button>();

	public GuiFrame(String title, int posX, int posY) {
		this(title, posX, posY, true, true);
	}

	public GuiFrame(String title, int posX, int posY, boolean visible, boolean expanded) {
		this.title = title;
		this.posX = posX;
		this.posY = posY;
		this.visible = visible;
		this.expanded = expanded;
		this.id = ClickGui.currentId += 1;
	}

	public void addButton(Button button) {
		if (!buttons.contains(button)) {
			buttons.add(button);
		}
	}

	/**
	 * @return The unique ID of this Frame.
	 */
	public int getButtonId() {
		return id;
	}

	@Override
	public void init() {}

	@Override
	public void render(int mouseX, int mouseY) {
		if (visible) {
			int width = Math.max(100, RenderUtil.getWidth(title) + 15);

			if (expanded) {
				for (Button button : buttons) {
					width = Math.max(width, button.getWidth() + 15);
				}
			}

			if (dragging && Mouse.isButtonDown(0)) {
				posX = mouseX - prevPosX;
				posY = mouseY - prevPosY;
				draggingId = id;
			} else {
				dragging = false;
				draggingId = -1;
			}

			RenderUtil.drawRect(posX, posY, posX + width + 1, posY + 12, Colors.OUTLINE_COLOR);
			RenderUtil.drawString(expanded ? "+" : "-", posX + width - 7, posY + 2, Colors.FONT_COLOR);

			/**
			 * Change this to false or remove the if clause, if you want the
			 * Text to be centered
			 */
			if (true) {
				RenderUtil.drawString(title, posX + 3, posY + 2, Colors.FONT_COLOR);
			} else {
				RenderUtil.drawCenteredString(title, posX + width / 2 + 2, posY + 2, Colors.FONT_COLOR);
			}

			if (expanded) {
				int height = 0;

				for (Button button : buttons) {
					button.render(posX + 1, posY + height + 12, width, mouseX, mouseY);

					
					if (button.getButtonId() == Button.extendedId) {
						ArrayList<GuiComponent> components = button.getComponents();

						if (!components.isEmpty()) {
							int width0 = 10;

							for (GuiComponent component : components) {
								width0 = Math.max(width0, component.getWidth());
							}

							int i = posX + width + 2;

							int height0 = 0;

							for (GuiComponent component : components) {
								height0 += component.getHeight();
							}

							int i0 = posY + height + height0 + 13;
							int i1 = posY + height + 12;

							RenderUtil.drawRect(i + 1, i1 + 1, i + width0, i0, Colors.BG_COLOR);

							int height1 = 0;

							for (GuiComponent component : components) {
								component.render(i, posY + height + height1 + 13, width0, mouseX, mouseY);
								height1 += component.getHeight();
							}

							RenderUtil.drawVerticalLine(i, i1, i0, Colors.OUTLINE_COLOR);
							RenderUtil.drawVerticalLine(i + width0, i1, i0, Colors.OUTLINE_COLOR);
							RenderUtil.drawHorizontalLine(i, i + width0, i1, Colors.OUTLINE_COLOR);
							RenderUtil.drawHorizontalLine(i, i + width0, i0, Colors.OUTLINE_COLOR);
						}
					}
					
					height += button.getHeight();
				}

				RenderUtil.drawVerticalLine(posX, posY + 11, posY + height + 12, Colors.OUTLINE_COLOR);
				RenderUtil.drawVerticalLine(posX + width, posY + 11, posY + height + 12, Colors.OUTLINE_COLOR);
				RenderUtil.drawHorizontalLine(posX, posX + width, posY + height + 12, Colors.OUTLINE_COLOR);
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		int width = 100;

		if (expanded) {
			for (Button button : buttons) {
				width = Math.max(width, button.getWidth());
			}
		}

		if (RenderUtil.isHovered(posX, posY, width, 13, mouseX, mouseY)) {
			if (mouseButton == 0) {
				prevPosX = mouseX - posX;
				prevPosY = mouseY - posY;
				dragging = true;
				draggingId = id;
			} else if (mouseButton == 1) {
				expanded = !expanded;
				dragging = false;
				draggingId = -1;
			}
		}

		if (expanded) {
			for (Button button : buttons) {
				button.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}

	@Override
	public void keyTyped(int keyCode, char typedChar) {
		if (expanded) {
			for (Button button : buttons) {
				button.keyTyped(keyCode, typedChar);
			}
		}
	}

	/**
	 * Can be useful for saving the frames' location
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append(posX);

		sb.append(';');
		sb.append(posY);

		sb.append(';');
		sb.append(visible);

		sb.append(';');
		sb.append(expanded);

		return sb.toString();
	}

	/**
	 * Get a new instance of GuiFrame
	 */
	public static GuiFrame fromString(String title, String info) {
		final String[] split = info.split(";");

		final int posX;
		final int posY;
		final boolean visible;
		final boolean eposXpanded;

		if (split.length != 4) {
			System.err.println("Cannot parse FrameInfo, generating a new Frame.");

			posX = 30;
			posY = 50;
			visible = true;
			eposXpanded = false;
		} else {
			posX = Integer.parseInt(split[0]);
			posY = Integer.parseInt(split[1]);
			visible = Boolean.parseBoolean(split[2]);
			eposXpanded = Boolean.parseBoolean(split[3]);
		}

		return new GuiFrame(title, posX, posY, visible, eposXpanded);
	}
}
