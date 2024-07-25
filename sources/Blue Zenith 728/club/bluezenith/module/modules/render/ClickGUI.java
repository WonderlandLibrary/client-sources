package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.*;
import club.bluezenith.ui.clickguis.novo.AncientNovoGUI;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.math.Range;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.lavache.anime.Animate;
import fr.lavache.anime.Easing;

import java.awt.*;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.core.data.config.ConfigManager.gson;
import static club.bluezenith.module.value.builders.AbstractBuilder.*;

public class ClickGUI extends Module {
    public static ClickGui oldDropdownUI = null;
    private AncientNovoGUI ancientNovoDropdown;

    public final Color oldDropdownBackground = new Color(35, 35, 35, 255);

    private final ModeValue guiMode = createMode("Mode")
            .range("Ancient Novo", "Dropdown")
            .index(1)
            .defaultOf("Dropdown")
            .whenChanged(() -> {
                if(mc.currentScreen instanceof AncientNovoGUI
                         || mc.currentScreen instanceof ClickGui)
                    getBlueZenith().getNotificationPublisher().postWarning(
                            this.getName(),
                            "Re-open the GUI for the changes to take effect.",
                            2500
                            );

            })
            .build();

    public final ModeValue toggleAnimation = createMode("Toggle effect")
            .range("Fill, Scale, Simple, None")
            .index(2)
            .showIf(() -> guiMode.is("Dropdown"))
            .build();

    public final IntegerValue backgroundAlpha = createInteger("Background alpha")
            .index(3)
            .increment(1)
            .range(Range.of(0, 200))
            .build();

    @SuppressWarnings("unused")
    public final FloatValue oldDropdownHeight = createFloat("Height")
            .index(4)
            .increment(1F)
            .range(Range.of(-3F, 3F))
            .defaultOf(0F)
            .showIf(() -> guiMode.is("Dropdown"))
            .whenChanged((before, after) ->
                    updatePanelHeight(after)
            )
            .build();

    public final BooleanValue animateSliders = createBoolean("Smooth sliders")
            .index(4)
            .showIf(() -> guiMode.is("Dropdown"))
            .build();

    public final BooleanValue closePrevious = createBoolean("Close previous")
            .index(5)
            .showIf(() -> guiMode.is("Dropdown") || guiMode.is("Ancient Novo"))
            .build();

    public final BooleanValue blurBackground = createBoolean("Background blur")
            .index(6)
            .build();

    public final ModeValue colorMode = createMode("Color")
            .range("Match HUD, Custom, Rainbow, Gradient")
            .index(7)
            .defaultOf("Match HUD")
            .whenChanged((before, after) -> {
                if(guiMode.is("Ancient Novo")) {
                    if(!after.equals("Match HUD")) {
                        if(before.equals("Match HUD"))
                            return "Custom";
                        else return "Match HUD";
                    }
                }

                return after;
            })
            .build();

    public final ModeValue colorType = createMode("Color type")
            .range("Static, Dynamic")
            .index(8)
            .showIf(() -> !colorMode.is("Custom") && !guiMode.is("Ancient Novo"))
            .build();

    public final ColorValue primaryColor = createColor("Primary")
            .index(9)
            .showIf(() -> !colorMode.is("Match HUD") && !colorMode.is("Rainbow"))
            .build();

    public final ColorValue secondaryColor = createColor("Secondary")
            .index(10)
            .showIf(() -> colorMode.is("Gradient"))
            .build();

    public final Animate scaleAnimation = new Animate()
            .setEase(Easing.CUBIC_OUT)
            .setMin(0f)
            .setMax(1f)
            .setSpeed(5)
            .setValue(0);

    public ClickGUI() {
        super("ClickGUI", ModuleCategory.RENDER);

        getBlueZenith().registerStartupTask((bz) -> {
            ancientNovoDropdown = new AncientNovoGUI(getNovoDropdownConfig(false));
            oldDropdownUI = new ClickGui();
        });
    }

    @Override
    public void onEnable() {
        if(player == null) return;

        switch (guiMode.get()) {
            case "Ancient Novo":
                mc.displayGuiScreen(ancientNovoDropdown);
            break;

            case "Dropdown":
                mc.displayGuiScreen(oldDropdownUI);
            break;
        }

        scaleAnimation.setValue(0).reset();
        super.setState(false);
    }

    @Override
    protected void addDataToConfig(JsonObject configObject) {
        getNovoDropdownConfig(true);
    }

    //updates height for old dropdown panels
    private float updatePanelHeight(float height) {
        final Runnable updateFunction = () -> oldDropdownUI.getPanels().forEach(panel -> panel.updateHeight(height));
        if(oldDropdownUI == null)
            getBlueZenith().registerStartupTask((bz) -> updateFunction.run());
        else updateFunction.run();
        return height;
    }

    private JsonObject getNovoDropdownConfig(boolean save) {
        try {
            if(!save) {
                final String result = getBlueZenith().getResourceRepository().readFromFile("novodropdown.json");
                return new JsonParser().parse(result).getAsJsonObject();
            } else {
                getBlueZenith().getResourceRepository().writeToFile("novodropdown.json", gson.toJson(ancientNovoDropdown.getConfig()));
                return null;
            }
        } catch (Exception exception) {
            return new JsonObject();
        }
    }
}
