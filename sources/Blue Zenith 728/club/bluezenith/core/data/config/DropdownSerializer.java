package club.bluezenith.core.data.config;

import club.bluezenith.core.data.preferences.DataHandler;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.ui.clickgui.components.Panel;
import com.google.gson.*;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class DropdownSerializer implements DataHandler {
    private static final String fileName = "clickgui", extension = ".json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void serialize() {
        final JsonObject panels = new JsonObject();
        ClickGUI.oldDropdownUI.getPanels().forEach(panel -> {
            JsonObject panelInfo = new JsonObject();
            panelInfo.add("x", new JsonPrimitive(panel.x));
            panelInfo.add("y", new JsonPrimitive(panel.y));
            panelInfo.add("shown", new JsonPrimitive(panel.showContent));
            panels.add(panel.id, panelInfo);
        });

        getBlueZenith().getResourceRepository().writeToFile(fileName + extension, gson.toJson(panels));
    }

    @Override
    public void deserialize() {
        final String contents = getBlueZenith().getResourceRepository().readFromFile(fileName + extension);

        if(contents == null)
            return;

        new JsonParser().parse(contents).getAsJsonObject().entrySet().forEach(entry -> {
            final Panel panel = ClickGUI.oldDropdownUI.getPanel(entry.getKey()); assert panel != null;

            entry.getValue().getAsJsonObject().entrySet().forEach(panelEntry -> {
                switch (panelEntry.getKey()) {
                    case "x": panel.x = panelEntry.getValue().getAsFloat(); break;
                    case "y": panel.y = panelEntry.getValue().getAsFloat(); break;
                    case "shown": panel.showContent = panelEntry.getValue().getAsBoolean(); break;
                }
            });
        });
    }
}
