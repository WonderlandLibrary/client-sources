package best.actinium.util.render;

import best.actinium.util.IAccess;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Map;

public class ChangeLogUtil implements IAccess {
    public static void drawText(JsonElement jsonElement, int xOffset, int initialYOffset, int color) {
        if (jsonElement != null) {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int y = initialYOffset;

                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    String text = entry.getValue().getAsString();
                    mc.fontRendererObj.drawStringWithShadow(text, xOffset, y, color);
                    y += 10;
                }
            } else {
                String text = jsonElement.getAsString();
                mc.fontRendererObj.drawStringWithShadow(text, xOffset, initialYOffset, color);
            }
        } else {
            System.out.println("Failed to read JSON file.");
        }
    }
}
