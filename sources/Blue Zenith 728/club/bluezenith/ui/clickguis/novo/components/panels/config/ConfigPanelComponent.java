package club.bluezenith.ui.clickguis.novo.components.panels.config;

import club.bluezenith.module.value.types.StringValue;
import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickguis.novo.components.modules.values.StringComponent;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.clickguis.novo.AncientNovoGUI.TITLE_RECT_HEIGHT;
import static club.bluezenith.util.render.RenderUtil.rect;
import static net.minecraft.client.gui.GuiScreen.isCtrlKeyDown;
import static net.minecraft.client.gui.GuiScreen.isShiftKeyDown;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class ConfigPanelComponent extends Component {
    private static final TFontRenderer title = FontUtil.createFont("posaytightposaycleanposayfresh2", 38);

    private boolean isDragging, setPrevXY, requestEntriesUpdate;
    private float prevX, prevY;

    private final List<Component> configComponentList = new ArrayList<>();

    private final AncientNovoGUI ancientNovoGUI;

    //the component (config name) that the user has clicked on
    ConfigEntryComponent selectedComponent;

    StringComponent configNameField = new StringComponent(new StringValue("Config name", ""), x, y);;

    public ConfigPanelComponent(AncientNovoGUI ancientNovoGUI, float x, float y) {
        super(x, y);
        this.prevX = x;
        this.prevY = y;

        updateEntries();
        this.ancientNovoGUI = ancientNovoGUI;
    }

    public void updateEntries() {
        configComponentList.clear();

        for (String config : getBlueZenith().getResourceRepository().getAllFilesInFolder("config")) {
            configComponentList.add(new ConfigEntryComponent(ancientNovoGUI, removeExtension(config), x, y));
        }

        configComponentList.add(configNameField);

        configComponentList.add(new ConfigActionComponent(ancientNovoGUI, x, y, () -> {
            if(selectedComponent == null) {
                getBlueZenith().getNotificationPublisher().postError(
                        "Config Manager",
                        "Select a config to load.",
                        2500
                );
                return;
            }
            getBlueZenith().getConfigManager().loadConfigFromName(selectedComponent.getConfigName(), isShiftKeyDown(), isCtrlKeyDown(), true);
            requestEntriesUpdate = true;
        }).setIdentifier("Load"));

        configComponentList.add(new ConfigActionComponent(ancientNovoGUI, x, y, () -> {
            if(selectedComponent != null) {
                getBlueZenith().getConfigManager().saveConfig(selectedComponent.getConfigName(), true);
                requestEntriesUpdate = true;
            }
            else if(!configNameField.getText().isEmpty()) {
                getBlueZenith().getConfigManager().saveConfig(configNameField.getText(), true);
                requestEntriesUpdate = true;
                configNameField.resetText();
            }
            else getBlueZenith().getNotificationPublisher().postError(
                        "Config Manager",
                        "Select a config to save, \n or type in a new name.",
                        3000
                );
        }).setIdentifier("Save"));

        configComponentList.add(new ConfigActionComponent(ancientNovoGUI, x, y, () -> {
            if(selectedComponent == null) {
                getBlueZenith().getNotificationPublisher().postError(
                        "Config Manager",
                        "Select a config to delete.",
                        2500
                );
                return;
            }
            getBlueZenith().getResourceRepository().deleteFile("config/" + selectedComponent.getConfigName() + ".json");
            getBlueZenith().getNotificationPublisher().postSuccess(
                    "Config Manager",
                    "Deleted config " + selectedComponent.getConfigName(),
                    2500
            );
            requestEntriesUpdate = true;
        }).setIdentifier("Delete"));

        configComponentList.forEach(component -> {
            component.setWidth(100);
            component.setHeight(component instanceof ConfigEntryComponent ? 15 : 14);
        });

        configNameField.setHeight(15);
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {

        if(this.isDragging)
            mouseDragging(mouseX, mouseY);

        rect(x, y, x + (width = 100), y + (height = TITLE_RECT_HEIGHT), ancientNovoGUI.getGuiAccentColor());
        title.drawString("Configs", x + 2, y + 2, -1, true);

        updateSize();

        if(this.shown) {
            float configHeight = y + TITLE_RECT_HEIGHT;

            for (Component component : configComponentList) {
                final boolean isStringComponent = component instanceof StringComponent;

                component.moveTo(getX(), isStringComponent ? configHeight + 3 : configHeight);

                if(isStringComponent)
                    rect(component.getX(), component.getY() - 3, component.getX() + component.getWidth(), component.getY() + component.getHeight() - 2, new Color(0, 0, 0, 200).getRGB());

                component.draw(mouseX, mouseY, scaledResolution);
                configHeight += isStringComponent ? component.getHeight() + 1 : component.getHeight();
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean hasSetSelectedComponent = false;

        if(ClickGui.i(mouseX, mouseY, x, y, x + width, y + TITLE_RECT_HEIGHT)) {
            if(mouseButton == 0)
              isDragging = true;
            else if(mouseButton == 1)
                this.shown = !this.shown;
        } else if(shown) for (Component component : this.configComponentList) {
            if(component.isMouseOver(mouseX, mouseY)) {
                if(component instanceof ConfigEntryComponent) {
                    missclick();

                    selectedComponent = (ConfigEntryComponent) component;
                    selectedComponent.isSelected = hasSetSelectedComponent = true;
                }
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

        if(!hasSetSelectedComponent)
            missclick();

        if(requestEntriesUpdate) {
            requestEntriesUpdate = false;
            updateEntries();
        }
    }

    public void missclick() {
        if(selectedComponent != null) selectedComponent.isSelected = false;
        selectedComponent = null;
    }

    @Override
    public void keyTyped(char key, int keyCode) {
        if(shown && configNameField.isAcceptingKeypresses())
            configNameField.keyTyped(key, keyCode);
    }

    @Override
    public boolean isAcceptingKeypresses() {
        return configNameField.isAcceptingKeypresses();
    }

    @Override
    public float getWidth() {
        return width;
    }

    public void updateSize() {
        final AtomicReference<Float> height = new AtomicReference<>(y + TITLE_RECT_HEIGHT);

        this.configComponentList.forEach(config ->
                height.set(height.get() + config.getHeight())
        );

        this.height = height.get();
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        isDragging = false;
    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return isMouseInBounds(mouseX, mouseY);
    }

    private void mouseDragging(int mouseX, int mouseY) {
        if (!setPrevXY) {
            this.prevX = mouseX - x;
            this.prevY = mouseY - y;
            this.setPrevXY = true;
        } else {
            this.x = mouseX - this.prevX;
            this.y = mouseY - this.prevY;
        }
    }


}
