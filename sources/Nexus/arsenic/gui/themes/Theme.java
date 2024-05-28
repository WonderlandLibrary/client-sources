package arsenic.gui.themes;

import arsenic.utils.interfaces.ISerializable;
import arsenic.utils.render.RenderUtils;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

public class Theme implements ISerializable {

    private final String name;
    private String logoName = "arsenic";
    private ResourceLocation logoPath = RenderUtils.getResourcePath("/assets/arsenic/logos/" + logoName + "logo.png");
    private int mainColor;
    private int darkerColor;
    private int white;
    private int black;

    public Theme(String name) {
        this.name = name;
    }

    public Theme(String name, int mainColor, int darkerColor, int white, int black) {
        this.name = name;
        this.mainColor = mainColor;
        this.darkerColor = darkerColor;
        this.white = white;
        this.black = black;
    }

    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
    }

    public int getDarkerColor() {
        return darkerColor;
    }

    public void setDarkerColor(int darkerColor) {
        this.darkerColor = darkerColor;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public ResourceLocation getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoName) {
        logoName = logoName.toLowerCase();
        this.logoName = logoName;
        logoPath = RenderUtils.getResourcePath("/assets/arsenic/logos/" + logoName + "logo.png");
    }


    //configs could be done with reflection
    // but this would look strange with obfuscation and honestly not much of a point
    @Override
    public void loadFromJson(JsonObject obj) {
        setLogoPath(obj.get("resourcelocation").getAsString());
        white = obj.get("white").getAsInt();
        black = obj.get("black").getAsInt();
        darkerColor = obj.get("dark").getAsInt();
        mainColor = obj.get("main").getAsInt();
    }

    @Override
    public JsonObject saveInfoToJson(JsonObject obj) {
        obj.addProperty("resourcelocation", logoName);
        obj.addProperty("white", getWhite());
        obj.addProperty("black", getBlack());
        obj.addProperty("dark", getDarkerColor());
        obj.addProperty("main", getMainColor());
        return obj;
    }

    @Override
    public String getJsonKey() {
        return name;
    }
}
