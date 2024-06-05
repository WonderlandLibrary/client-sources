package koks.modules.impl.utilities;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender2D;
import koks.event.impl.KeyPressEvent;
import koks.hud.CrossHair;
import koks.hud.ModuleList;
import koks.hud.Watermark;
import koks.modules.Module;
import koks.theme.Theme;
import koks.utilities.CustomFont;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 22:23
 */
public class HUD extends Module {

    public ModuleList moduleList = new ModuleList();
    public Watermark watermark = new Watermark();

    private BooleanValue<Boolean> tabGUI = new BooleanValue<>("Enabled", true, this);
    private BooleanValue<Boolean> tabGUI_shadow = new BooleanValue<>("Shadow", true, this);
    private BooleanValue<Boolean> tabGUI_client_color = new BooleanValue<>("Color", true, this);
    private BooleanValue<Boolean> tabGUICenteredString = new BooleanValue<>("Centered String", true, this);

    private BooleanValue<Boolean> shadowArrayList = new BooleanValue<>("Shadow", true, this);
    public ModeValue<String> watermarkStyle = new ModeValue<>("Watermark Style", "Simple", new String[]{"Simple", "Custom with Shadow", "Mario Kart"}, this);
    public BooleanValue<Boolean> customCrossHair = new BooleanValue<>("CrossHair", false, this);

    public ModeValue<String> tabGuiSettings = new ModeValue<>("TabGui Settings", new BooleanValue[]{tabGUI, tabGUI_shadow, tabGUI_client_color, tabGUICenteredString}, this);
    public ModeValue<String> arrayListSettings = new ModeValue<>("Arraylist Settings", new BooleanValue[]{shadowArrayList}, this);

    public HUD() {
        super("HUD", "Its render the HUD", Category.UTILITIES);
        addValue(tabGuiSettings);
        addValue(arrayListSettings);
        addValue(watermarkStyle);
        addValue(customCrossHair);
        this.setToggled(true);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof EventRender2D) {

            if (tabGUI.isToggled()) {
                tabGUI_shadow.setVisible(true);
                tabGUI_client_color.setVisible(true);
                tabGUICenteredString.setVisible(true);
            } else {
                tabGUI_shadow.setVisible(false);
                tabGUI_client_color.setVisible(false);
                tabGUICenteredString.setVisible(false);
            }

            switch (Koks.getKoks().getThemeCategory()) {
                case NONE:
                    moduleList.drawList(shadowArrayList.isToggled());
                    watermark.drawWatermark();
                    if (Koks.getKoks().tabGUI != null && tabGUI.isToggled()) {
                        if (watermarkStyle.getSelectedMode().equals("Custom with Shadow"))
                            Koks.getKoks().tabGUI.drawScreen(2, 70, 80, 15, this.tabGUI_shadow.isToggled(), tabGUI_client_color.isToggled(), tabGUICenteredString.isToggled());
                        else
                            Koks.getKoks().tabGUI.drawScreen(2, 20, 80, 15, this.tabGUI_shadow.isToggled(), tabGUI_client_color.isToggled(), tabGUICenteredString.isToggled());
                    }
                    if (customCrossHair.isToggled())
                        new CrossHair().drawCrosshair();
                    break;
                default:
                    Koks.getKoks().themeManager.getThemeList().forEach(theme -> {
                        if (theme.getThemeCategory()==Koks.getKoks().getThemeCategory()) {
                            theme.drawIngameTheme();
                        }
                    });
                    break;
            }
        }

        if (event instanceof KeyPressEvent) {
            switch (Koks.getKoks().getThemeCategory()) {
                case NONE:
                    if (Koks.getKoks().tabGUI != null && tabGUI.isToggled())
                        Koks.getKoks().tabGUI.keyPress(((KeyPressEvent) event).getKey());
                    break;
                default:
                    Koks.getKoks().themeManager.getThemeList().forEach(theme -> {
                        if (theme.getThemeCategory().equals(Koks.getKoks().getThemeCategory())) {
                            if (theme.drawTabGUI()) {
                                if (Koks.getKoks().tabGUI != null)
                                    Koks.getKoks().tabGUI.keyPress(((KeyPressEvent) event).getKey());
                            }
                        }
                    });
                    break;
            }
        }

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
