package de.Hero.clickgui;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.VISUALLY.HUD;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ClickGUI extends GuiScreen {
	public static ArrayList<Panel> panels;
	public static ArrayList<Panel> rpanels;
	public static GuiTextField Search;
	public static double maxOffset = 0;
	public SettingsManager setmgr;
	public Setting set;
	int delay;
	private ModuleButton mb = null;
	private int scroll;
	private float curAlpha;

	/*
	 * Konstrukor sollte nur einmal aufgerufen werden => in der MainMethode des
	 * eigenen Codes hier Client.startClient() das GUI wird dann so geffnet:
	 * mc.displayGuiScreen(Client.clickgui); this.setToggled(false); das Module wird
	 * sofort wieder beendet damit nchstes mal nicht 2mal der z.B. 'RSHIFT' Knopf
	 * gedrckt werden muss
	 */
	public ClickGUI() throws IOException {
		setmgr = Zamorozka.settingsManager;

		FontUtil.setupFontUtils();
		panels = new ArrayList<>();
		double pwidth = 85;
		double pheight = 20;
		double px = 20;
		double py = 20;
		double pyplus = pheight + 2;

		/*
		 * Zum Sortieren der Panels einfach die Reihenfolge im Enum ndern ;)
		 */

		for (Category c : Category.values()) {
			String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
			ClickGUI.panels.add(new Panel(title, px, py, pwidth, pheight, false, this) {
				@Override
				public void setup() {
					for (Module m : ModuleManager.getModules()) {
						if (!m.getCategory().equals(c))
							continue;
						this.Elements.add(new ModuleButton(m, this));

					}
				}
			});

			py += pyplus;
		}

		rpanels = new ArrayList<Panel>();
		for (Panel p : panels) {
			rpanels.add(p);
		}
		Collections.reverse(rpanels);

	}

	public static Color setRainbow(long offset, float fade) {
		float hue = (float) (System.nanoTime() * -5L + offset) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade);

	}

	public void updateScreen() {

	}

	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		maxOffset = 0;

		GL11.glPushMatrix();
		GL11.glScalef(5.0F, 5.0F, 1.0F);
		GL11.glPopMatrix();
		ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());

		// clickgui gradient rect

		if ((ModuleManager.getModule(HUD.class).getState() && Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean())) {
			String mode2 = Zamorozka.settingsManager.getSettingByName("GradientColor Mode").getValString();
			String mode = Zamorozka.settingsManager.getSettingByName("Gradient Mode").getValString();
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			float alpha = (float) Zamorozka.settingsManager.getSettingByName("GradientAlpha").getValDouble();
			int step = (int) (alpha / 100);
			if (this.curAlpha < alpha - step) {
				this.curAlpha += step;
			} else if (this.curAlpha > alpha - step && this.curAlpha != alpha) {
				this.curAlpha = (int) alpha;

			} else if (this.curAlpha != alpha) {
				this.curAlpha = (int) alpha;
			}

			if (mode2.equalsIgnoreCase("Client")) {
				Color c = new Color(Zamorozka.getClientColors().getRed(), Zamorozka.getClientColors().getGreen(), Zamorozka.getClientColors().getBlue(), (int) curAlpha);
				Color none = new Color(0, 0, 0, 0);
				if (Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
					if (mode.equalsIgnoreCase("Top")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), c.getRGB(), none.getRGB());
					} else if (mode.equalsIgnoreCase("Bottom")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), c.getRGB());
					} else if (mode.equalsIgnoreCase("Everywhere")) {
						this.drawGradientRect(0, 0, (int) sr.getScaledWidth(), (int) sr.getScaledHeight(), c.getRGB(), c.getRGB());
					}
				}
			}
			if (mode2.equalsIgnoreCase("Custom")) {
				int red = (int) Zamorozka.settingsManager.getSettingByName("GradientRed").getValDouble();
				int green = (int) Zamorozka.settingsManager.getSettingByName("GradientGreen").getValDouble();
				int blue = (int) Zamorozka.settingsManager.getSettingByName("GradientBlue").getValDouble();
				Color c = new Color(red, green, blue, (int) curAlpha);
				Color none = new Color(0, 0, 0, 0);
				if (Zamorozka.settingsManager.getSettingByName("GradientBackground").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
					if (mode.equalsIgnoreCase("Top")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), c.getRGB(), none.getRGB());
					} else if (mode.equalsIgnoreCase("Bottom")) {
						this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), none.getRGB(), c.getRGB());
					} else if (mode.equalsIgnoreCase("Everywhere")) {
						this.drawGradientRect(0, 0, (int) sr.getScaledWidth(), (int) sr.getScaledHeight(), c.getRGB(), c.getRGB());
					}
				}
			}
		}

		// Category name background

		this.drawRect(0, 1000, 156, 0, Integer.MIN_VALUE);

		/*
		 * Panels und damit auch Buttons rendern. panels wird NUR hier im Code
		 * verwendet, da das zuletzt gerenderte Panel ganz oben ist Auch wenn es
		 * manchmal egal wre ob panels/rpanels benutzt wird habe ich mich einfach mal
		 * dazu entschieden, einfach weil es einfacher ist nur einmal panels zu benutzen
		 */

		if (mouseX > width / 2 * 1.899 && mouseX < width / 2 * 1.899 + width && mouseY > height / 1.05 && mouseY < height && Mouse.isInsideWindow()) {
			File file = new File((mc).mcDataDir + File.separator + "Zamorozka", "clickgui.cfg");
			FileInputStream fstream = null;
			try {
				fstream = new FileInputStream(file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		}
		/**
		 * Gui.drawRect(width /6, height/6, width/2*1.69, height/1.2, 0xff101010);
		 * Gui.drawRect(width /6, height/4.2, width/2*1.69, height/4.4, 0xFF00F2FF);
		 *
		 * Gui.drawRect(width /6, height/6.1, width/2*1.69, height/4.1, 0xFF043792);
		 * Gui.drawRect(width /6, height/4.1, width/2*1.69, height/4.14, 0xFF4D6692);
		 * //Gui.drawRect(width /6, height/6, width/5.93, height/1.2, 0xFF00F2FF);
		 * //Gui.drawRect(width /2*1.69, height/6, width/2*1.697, height/1.2,
		 * 0xFF00F2FF);
		 *
		 * //Gui.drawRect(width /6, height/6, width/2*1.69, height/5.9, 0xFF00F2FF);
		 * //Gui.drawRect(width /6, height/1.2099, width/2*1.69, height/1.2,
		 * 0xFF00F2FF);
		 *
		 * Gui.drawRect(width /6, height/4.1, width/3.27, height/1.2, 0xff043792);
		 *
		 * //RenderUtils.drawPic(width /4.9, height/5.7, width/15.9, height/15.9, new
		 * ResourceLocation("sword.png")); //RenderUtils.drawPic(width /4.9, height/5.9,
		 * width/15.9, height/15.9, new ResourceLocation("sword.png"));
		 * mc.fontRendererObj.drawStringWithShadow("§3"+mc.debugFPS, (float) (width
		 * /1.22), height/4, 0xFF0091D7);
		 **/
		for (Panel p : panels) {
			p.drawScreen(mouseX, mouseY, partialTicks);
		}
		/* https://www.youtube.com/channel/UCJum3PIbnYvIfIEu05GL_yQ */
		/*															*/

		mb = null;
		/*
		 * berprfen ob ein Button listening == true hat, wenn ja, dann soll nicht mehr
		 * gesucht werden, nicht dass 1+ auf listening steht...
		 */
		listen: for (Panel p : panels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					if (e.listening) {
						mb = e;
						break listen;
					}
				}
			}
		}
		if (mb != null) {

			FontUtil.drawTotalCenteredStringWithShadow("", 0, -10, 0xffffffff);
			ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
			float pY = (mc.currentScreen != null && (mc.currentScreen instanceof GuiChat)) ? -12 : -2;
//			FontUtil.drawTotalCenteredStringWithShadow("�ESC�, ����� ������ ���� " + mb.mod.getName() + (mb.mod.getKey() > -1 ? " (" + Keyboard.getKeyName(mb.mod.getKey())+ ")" : ""), 80,  scaledResolution.getScaledHeight() - 9 + pY, 0xFFFFFFFD);
			FontUtil.drawTotalCenteredStringWithShadow(mb.mod.getName() + (mb.mod.getKey() > -1 ? " (" + Keyboard.getKeyName(mb.mod.getKey()) + ")" : ""), scaledResolution.getScaledWidth() - width / 2, scaledResolution.getScaledHeight() - 48 + pY,
					0xFFFFFFFD);
			FontUtil.drawTotalCenteredStringWithShadow("", 0, 20, 0xffffffff);

		}
		/*
		 * Settings rendern. Da Settings ber alles gerendert werden soll, abgesehen vom
		 * ListeningOverlay werden die Elements von hier aus fast am Schluss gerendert
		 */
		for (Panel panel : panels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended && b.menuelements != null && !b.menuelements.isEmpty()) {
						double off = 0;
						Color temp = ColorUtil.getClickGUIColor().darker();
						int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
						int y2 = 0;
						for (Element e : b.menuelements) {

							e.offset = off;
							e.update();

							if (maxOffset < Panel.height + panel.y) {
								maxOffset = Panel.height + panel.y;
							}

							e.drawScreen(mouseX + 4, mouseY, partialTicks);
							off += e.height;
						}
					}
				}
			}

		}

		/*
		 * Nicht bentigt, aber es ist so einfach sauberer ;) Und ohne diesen call knnen
		 * keine GUIButtons/andere Elemente gerendert werden
		 */

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		/*
		 * Damit man nicht nochmal den Listeningmode aktivieren kann, wenn er schon
		 * aktiviert ist
		 */
		if (mb != null)
			return;

		/*
		 * Bentigt damit auch mit Elements interagiert werden kann besonders zu beachten
		 * ist dabei, dass zum einen rpanels aufgerufen wird welche eine Eigenstndige
		 * Kopie von panels ist, genauer oben erklrt Also rpanels damit zuerst das panel
		 * 'untersucht' wird, dass als letztes gerendert wurde => Ganz oben ist! sodass
		 * der Nutzer nicht mit dem Unteren interagiern kann, weil er es wohl nicht
		 * will. Und damit nicht einfach mit Panels anstatt Elements interagiert wird
		 * werden hier nur die Settings untersucht. Und wenn wirklich interagiert wurde,
		 * dann endet diese Methode hier. Das ist auch in anderen Loops zu beobachten
		 */
		for (Panel panel : rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							if (e.mouseClicked(mouseX, mouseY, mouseButton))
								return;
						}
					}
				}
			}
		}

		/*
		 * Bentigt damit mit ModuleButtons interagiert werden kann und Panels
		 * 'gegriffen' werden knnen
		 */
		for (Panel p : rpanels) {
			if (p.mouseClicked(mouseX, mouseY, mouseButton))
				return;
		}

		/*
		 * Nicht bentigt, aber es ist so einfach sauberer ;)
		 */
		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		/*
		 * Damit man nicht nochmal den Listeningmode aktivieren kann, wenn er schon
		 * aktiviert ist
		 */
		if (mb != null)
			return;

		/*
		 * Eigentlich nur fr die Slider bentigt, aber durch diesen Call erfhrt jedes
		 * Element, wenn z.B. Rechtsklick losgelassen wurde
		 */
		for (Panel panel : rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							e.mouseReleased(mouseX, mouseY, state);
						}
					}
				}
			}
		}

		/*
		 * Bentigt damit Slider auch losgelassen werden knnen und nicht immer an der
		 * Maus 'festkleben' :>
		 */
		for (Panel p : rpanels) {
			p.mouseReleased(mouseX, mouseY, state);
		}

		/*
		 * Nicht bentigt, aber es ist so einfach sauberer ;)
		 */
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		/*
		 * Bentigt fr die Keybindfunktion
		 */

		for (Panel p : rpanels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					try {
						if (e.keyTyped(typedChar, keyCode))
							return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		/*
		 * keyTyped in GuiScreen MUSS aufgerufen werden, damit man mit z.B. ESCAPE aus
		 * dem GUI gehen kann
		 */
		try {
			super.keyTyped(typedChar, keyCode);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	@Override
	public void initGui() {
		loadGui();
		if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
			if (mc.entityRenderer.theShaderGroup != null) {
				mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			}
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

		}

	}

	@Override
	public void onGuiClosed() {
		/*
		 * End blur
		 */

		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}

		/*
		 * Sliderfix
		 */
		for (Panel panel : ClickGUI.rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							if (e instanceof ElementSlider) {
								((ElementSlider) e).dragging = false;
							}
						}
					}
				}
			}
		}
		saveGui();
	}

	public List getPanels() {
		return panels;
	}

	public void saveGui() {
		try {
			File file1 = new File((mc).mcDataDir + File.separator + "Zamorozka/cfgs");
			File file = new File(file1, "clickgui.cfg");
			if (!file1.exists()) {
				file1.mkdirs();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			String line;

			out.write("Config:Default");
			out.write("\r\n");
			for (Setting s : SettingsManager.getSettings()) {
				if (!s.getName().equals("Configs")) {
					out.write(s.getName() + ":" + s.getValString() + ":" + s.getValBoolean() + ":" + s.getValDouble());
					out.write("\r\n");
				}
			}

			out.close();
		} catch (Exception e) {
			Minecraft.player.sendChatMessage("Failed to save configs!");
		}
	}

	public void loadGui() {
		try {
			File file1 = new File((mc).mcDataDir + File.separator + "Zamorozka/cfgs");
			File file = new File(file1, "clickgui.cfg");
			if (!file1.exists()) {
				file1.mkdirs();
			}
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				String readString = line.trim();
				String[] split = readString.split(":");
				for (Setting s : SettingsManager.getSettings()) {
					if (s.getName().equals(split[0])) {
						s.setValString(split[1]);
						s.setValBoolean(Boolean.valueOf(split[2]).booleanValue());
						s.setValDouble(Float.valueOf(split[3]));
					}
				}
			}
			br.close();
		} catch (Exception e) {

		}

	}

	public void closeAllSettings() {
		for (Panel p : rpanels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					// e.extended = false;
				}
			}
		}
	}

}
