package me.swezedcode.client.gui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.Tea;
import me.swezedcode.client.command.commands.CommandTabGui;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.gui.tabGui.TabGui;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.more.Friend;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.module.modules.Options.UnsortedArrayList;
import me.swezedcode.client.module.modules.Visual.NameProtect;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.events.EventReadPacket;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.RenderUtils;
import me.swezedcode.client.utils.render.SpecialCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.StringUtils;

public class ClientOverlay extends GuiIngame {

	public static int mode = 0;

	private ArrayList<String> notify = new ArrayList<String>();

	public float hue = 0.0f;

	public static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

	private int size = -100;
	public static int size2 = 0;

	private HashMap<String, Integer> map = new HashMap<String, Integer>();

	public ClientOverlay(Minecraft mcIn) {
		super(mcIn);
	}

	public void drawStringWithShadow(final String text, final float x, final float y, final int color) {
		fontRenderer.drawStringWithShadow(text, x, y, color);
	}

	@Override
	public void func_175180_a(float thing) {
		super.func_175180_a(thing);
		if (mode != 1) {
			if (mc.inGameHasFocus) {
				if (!Minecraft.getMinecraft().inGameHasFocus) {
					size = -100;
				}
				if (size <= 1) {
					size += 2;
				}

				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
				String time = formatter.format(date);

				if (!Rainbow.enabled) {
					fontRenderer.drawStringWithShadow("Tea Client b" + Tea.getInstance().getVersion(), 1, 1,
							ColorPickerGui.color);
				} else {
					fontRenderer.drawStringWithShadow("Tea Client b" + Tea.getInstance().getVersion(), 1, 1,
							ColorUtils.getRainbow(0, 1).getRGB());
				}

				if (!Rainbow.enabled) {
					if (ModuleUtils.getMod(NameProtect.class).isToggled()) {
						fontRenderer.drawStringWithShadow("Username: §f" + NameProtect.name, 2, 20,
								ColorPickerGui.color);
					} else {
						fontRenderer.drawStringWithShadow("Username: §f" + mc.thePlayer.getName(), 2, 20,
								ColorPickerGui.color);
					}
					fontRenderer.drawStringWithShadow("FPS: §f" + mc.debugFPS, 2, 30, ColorPickerGui.color);
				} else {
					if (ModuleUtils.getMod(NameProtect.class).isToggled()) {
						fontRenderer.drawStringWithShadow("Username: §f" + NameProtect.name, 2, 20,
								ColorUtils.getRainbow(0, 1).getRGB());
					} else {
						fontRenderer.drawStringWithShadow("Username: §f" + mc.thePlayer.getName(), 2, 20,
								ColorUtils.getRainbow(0, 1).getRGB());
					}
					fontRenderer.drawStringWithShadow("FPS: §f" + mc.debugFPS, 2, 30,
							ColorUtils.getRainbow(0, 1).getRGB());
				}

				String version;
				String serverType = "N/A";
				final String theServer = Minecraft.getMinecraft().thePlayer.getClientBrand();
				String[] split = new String[0];
				String otherType = "";
				try {
					split = theServer.split("<-");
					otherType = ((split.length > 0) ? split[1] : "N/A");
					serverType = String.valueOf(String.valueOf(theServer.split(" ")[0])) + " - " + otherType;
					serverType = String.valueOf(String.valueOf(theServer.split(" ")[0].substring(0, 1).toUpperCase()))
							+ theServer.split(" ")[0].substring(1)
							+ ((otherType.length() > 0) ? (" - " + otherType) : "Vanilla");
					if (!Rainbow.enabled) {
						fontRenderer.drawStringWithShadow(serverType, 1, 10, ColorPickerGui.color);
					} else {
						fontRenderer.drawStringWithShadow(serverType, 1, 10, ColorUtils.getRainbow(1, 1).getRGB());
					}
				} catch (Exception ex) {
					serverType = "Vanilla";
					if (!Rainbow.enabled) {
						fontRenderer.drawStringWithShadow(serverType, 1, 10, ColorPickerGui.color);
					} else {
						fontRenderer.drawStringWithShadow(serverType, 1, 10, ColorUtils.getRainbow(1, 1).getRGB());
					}
				}

				if (CommandTabGui.on) {
					TabGui.drawTabGui(40);
				}

				double xP = mc.thePlayer.posX;
				double yP = mc.thePlayer.posY;
				double zP = mc.thePlayer.posZ;

				/*
				 * if (Rainbow.enabled) { fontRenderer.drawStringWithShadow(
				 * "X:§f " + (int) xP, 2, 97, ColorUtils.getRainbow(1,
				 * 1).getRGB()); fontRenderer.drawStringWithShadow("Y:§f " +
				 * (int) yP, 2, 107, ColorUtils.getRainbow(1, 1).getRGB());
				 * fontRenderer.drawStringWithShadow("Z:§f " + (int) zP, 2, 117,
				 * ColorUtils.getRainbow(1, 1).getRGB()); } else {
				 * fontRenderer.drawStringWithShadow("X:§f " + (int) xP, 2, 97,
				 * 0xFFFF8847); fontRenderer.drawStringWithShadow("Y:§f " +
				 * (int) yP, 2, 107, 0xFFFF8847);
				 * fontRenderer.drawStringWithShadow("Z:§f " + (int) zP, 2, 117,
				 * 0xFFFF8847); }
				 */
				int y = 3;

				List<Module> Modules = Manager.getManager().getModuleManager().getModules();
				Collections.sort(Modules, new ModuleComparator());
				int count = 0;
				List<Module> visible = new CopyOnWriteArrayList();
				for (Module m : Modules) {
					if (m.getModcategory() == ModCategory.Gui)
						continue;
					if (m.getModcategory() == ModCategory.NONE)
						continue;

					if (m.getModcategory() == ModCategory.Options)
						continue;

					if ((m.isToggled())) {
						visible.add(m);
					}
				}
				float h = hue;
				for (Module m : visible) {
					if (h > 255.0f) {
						h = 0.0f;
					}
					final Color color = Color.getHSBColor(h / 255.0f, 1.0f, 1.0f);
					final int c = Rainbow.enabled ? color.getRGB() : m.getColor();
					int right = ScaledResolution.getScaledWidth() - fontRenderer.getStringWidth(m.getDisplayName()) - 2;

					final int fuckyou = (m.displayName.length() > 0)
							? (Minecraft.getMinecraft().fontRendererObj.getStringWidth(m.displayName) + 9) : 0;
					fontRenderer.drawStringWithShadow(m.getDisplayName(), right, y + -2, c);
					y += fontRenderer.getHeight() + -1;
					h += 10f;
				}
			}
		}
	}

	private List<Module> getRenderMods() {
		final List<Module> psuedoMods = new ArrayList<Module>();
		for (final Module m : Manager.getManager().getModuleManager().getModules()) {
			if ((m.isToggled()) && (m.getName() != "SortedArrayList")) {
				psuedoMods.add(m);
			}
		}
		psuedoMods.sort((o1, o2) -> {
			final int o1l = ((o1.displayName.length() > 0)
					? (fontRenderer.getStringWidth(o1.displayName) + fontRenderer.getStringWidth("12")) : 0);
			final int o2l = ((o2.displayName.length() > 0)
					? (Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.displayName)
							+ fontRenderer.getStringWidth("12"))
					: 0);
			if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.getName())
					+ o1l > fontRenderer.getStringWidth(o2.getName()) + o2l) {
				return -1;
			} else if (fontRenderer.getStringWidth(o1.getName()) + o2l == fontRenderer.getStringWidth(o2.getName())
					+ o2l) {
				return 0;
			} else {
				return 1;
			}
		});
		return psuedoMods;
	}

	class ModuleComparator implements Comparator<Module> {

		ModuleComparator() {
		}

		public int compare(Module o1, Module o2) {
			final List<Module> psuedoMods = new ArrayList<Module>();

			if (fontRenderer.getStringWidth(o1.getDisplayName()) < fontRenderer.getStringWidth(o2.getDisplayName())) {
				return 1;
			} else if (fontRenderer.getStringWidth(o1.getDisplayName()) > fontRenderer
					.getStringWidth(o2.getDisplayName())) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
