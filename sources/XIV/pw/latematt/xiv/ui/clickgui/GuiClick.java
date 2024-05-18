package pw.latematt.xiv.ui.clickgui;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.gui.GuiScreen;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.ui.clickgui.panel.Panel;
import pw.latematt.xiv.ui.clickgui.panel.panels.*;
import pw.latematt.xiv.ui.clickgui.theme.ClickTheme;
import pw.latematt.xiv.ui.clickgui.theme.themes.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GuiClick extends GuiScreen {
    private List<Panel> panels;
    private List<ClickTheme> themes;
    private ClickTheme theme;

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
        themes.add(new AvidTheme(this));
        themes.add(theme = new DarculaTheme(this));
        themes.add(new DebugTheme(this));
        themes.add(new IridiumTheme(this));
        themes.add(new IXTheme(this));
        themes.add(new NorthStarTheme(this));
        themes.add(new PringlesTheme(this));
        themes.add(new XenonTheme(this));

        int x = 4;
        int y = 4;

        addPanel(new ThemePanel(x, y), x, y);
        addPanel(new AuraPanel(x, y), x, y);
        addPanel(new ESPPanel(x, y), x, y);
        addPanel(new StorageESPPanel(x, y), x, y);
        addPanel(new WaypointsPanel(x, y), x, y);
        addPanel(new FastUsePanel(x, y), x, y);
        addPanel(new NameProtectPanel(x, y), x, y);
        addPanel(new TriggerbotPanel(x, y), x, y);

        for (ModType type : ModType.values()) {
            addPanel(new ModulePanel(type, x, y), x, y);
        }

        //addPanel(new ValuePanel(x, y), x, y);
        addPanel(new HUBPanel(x, y), x, y);

        new XIVFile("gui", "json") {
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

        new XIVFile("guiTheme", "cfg") {
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

        XIV.getInstance().getFileManager().loadFile("gui");
        XIV.getInstance().getFileManager().loadFile("guiTheme");
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        panels.stream().filter(Panel::isShowing).forEach(panel -> panel.mouseClicked(mouseX, mouseY, mouseButton));
        XIV.getInstance().getFileManager().saveFile("gui");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();

        panels.stream().filter(Panel::isShowing).forEach(panel -> panel.drawPanel(mouseX, mouseY));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        panels.stream().filter(Panel::isShowing).forEach(panel -> panel.keyPressed(keyCode));
    }

    @Override
    public void onGuiClosed() {
        panels.stream().filter(Panel::isShowing).forEach(Panel::onGuiClosed);
        XIV.getInstance().getFileManager().saveFile("gui");
    }

    public class PanelConfig {
        public final String name;
        public final float x, y;
        public final boolean open, showing;

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
