package me.protocol_client.ui.click.Protocol;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import me.protocol_client.Protocol;
import me.protocol_client.files.allfiles.GuiFile;
import me.protocol_client.files.allfiles.ValuesFile;
import me.protocol_client.module.Category;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.ui.click.Protocol.Panel.panels.ModulePanel;
import me.protocol_client.ui.click.Protocol.Panel.panels.ThemePanel;
import me.protocol_client.ui.click.Protocol.theme.ClickTheme;
import me.protocol_client.ui.click.Protocol.theme.themes.NodusTheme;
import me.protocol_client.ui.click.Protocol.theme.themes.ProtocolTheme;
import me.protocol_client.ui.click.Protocol.theme.themes.ReliantThemeNew;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GuiClick extends GuiScreen {
	private List<Panel>			panels;
	private List<ClickTheme>	themes;
	private ClickTheme			theme;

	public ClickTheme getTheme() {
		return theme;
	}

	public void setTheme(ClickTheme theme) {
		this.theme = theme;
	}

	public List<ClickTheme> getThemes() {
		return themes;
	}

	public List<Panel> getPanels() {
		return panels;
	}

	public void addPanel(Panel panel, int x, int y) {
		for (int i = 0; i < panels.size(); i++) {
			if (x > 4 + (102 * 3)) {
				x = 4;
				y += 15;
			} else {
				x += 102;
			}
		}

		panel.setX(x);
		panel.setY(y);

		panels.add(panel);
	}

	@Override
	public void initGui() {
		panels = new CopyOnWriteArrayList<>();
		themes = new ArrayList<>();
		themes.add(new ProtocolTheme(this));
		themes.add(new ReliantThemeNew(this));
		themes.add(new NodusTheme(this));
		if (theme == null) {
			theme = new ProtocolTheme(this);
		}
		int x = 4;
		int y = 4;

		for (Category type : Category.values()) {
			if (type != Category.OTHER) {
				addPanel(new ModulePanel(type, x, y), x, y);
			}
		}
		addPanel(new ThemePanel(x, y), x, y);
		for (Panel panel : this.getPanels()) {
			if (panel.getTempX() != 0 && panel.getTempY() != 0) {
				panel.setX(panel.getTempX());
				panel.setY(panel.getTempY());
			}
		}
		ValuesFile.load();
		new GuiFile("gui", "json") {
			@Override
			public void load() throws IOException {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				List<PanelConfig> panelConfigs = gson.fromJson(reader, new TypeToken<List<PanelConfig>>() {
				}.getType());
				for (PanelConfig panelConfig : panelConfigs) {
					panels.stream().filter(panel -> panelConfig.getName().equals(panel.getName())).forEach(panel -> {
						panel.setX(panelConfig.getX());
						panel.setY(panelConfig.getY());
						panel.setOpen(panelConfig.isOpen());
						panel.setShowing(panelConfig.isShowing());
					});
				}
			}

			@Override
			public void save() throws IOException {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				List<PanelConfig> panelConfigs = panels.stream().map(panel -> new PanelConfig(panel.getName(), panel.getX(), panel.getY(), panel.isOpen(), panel.isShowing())).collect(Collectors.toList());
				Files.write(gson.toJson(panelConfigs).getBytes("UTF-8"), file);
			}
		};

		new GuiFile("guiTheme", "cfg") {
			@Override
			public void load() throws IOException {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = reader.readLine()) != null) {
					final String finalizedLine = line;
					Optional<ClickTheme> newTheme = themes.stream().filter(theme -> finalizedLine.equals(theme.getName())).findFirst();
					if (newTheme.isPresent()) {
						theme = newTheme.get();
					}
				}
			}

			@Override
			public void save() throws IOException {
				Files.write(theme.getName().getBytes("UTF-8"), file);
			}
		};

		Protocol.getOtherFileManager().loadFile("gui");
		Protocol.getOtherFileManager().loadFile("guiTheme");
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		panels.stream().filter(Panel::isShowing).forEach(panel -> panel.mouseClicked(mouseX, mouseY, mouseButton));
		Protocol.getOtherFileManager().saveFile("gui");
		Protocol.getOtherFileManager().saveFile("guiTheme");
		ValuesFile.save();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(!(this.getTheme() instanceof NodusTheme)){
		this.drawDefaultBackground();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
		panels.stream().filter(Panel::isShowing).forEach(panel -> panel.drawPanel(mouseX, mouseY));
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		for (int i = 0; i < 24; i++) {
			Gui.drawRect(0, 0, RenderUtils2D.newScaledResolution().getScaledHeight(), RenderUtils2D.newScaledResolution().getScaledWidth(), 0x50000000);
		}
		panels.stream().filter(Panel::isShowing).forEach(panel -> panel.keyPressed(keyCode));
	}

	@Override
	public void onGuiClosed() {
		panels.stream().filter(Panel::isShowing).forEach(Panel::onGuiClosed);
		Protocol.getOtherFileManager().saveFile("gui");
		ValuesFile.save();
	}

	public class PanelConfig {
		public final String	name;
		public final float	x, y;
		public final boolean	open, showing;

		public PanelConfig(String name, float x, float y, boolean open, boolean showing) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.open = open;
			this.showing = showing;
		}

		public String getName() {
			return name;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public boolean isOpen() {
			return open;
		}

		public boolean isShowing() {
			return showing;
		}
	}
}
