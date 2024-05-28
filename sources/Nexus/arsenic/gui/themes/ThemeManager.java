package arsenic.gui.themes;

import arsenic.main.Nexus;
import arsenic.utils.interfaces.IConfig;
import arsenic.utils.interfaces.ISerializable;
import arsenic.utils.java.FileUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThemeManager implements IConfig<Theme>, ISerializable {
    private Theme currentTheme;
    private final List<Theme> themeList = new ArrayList<>();
    public int initialize() {
        System.out.println("initialized theme manager");
        loadConfig();
        if(getContentByJsonKey("Lilith") == null) {
            Theme lilith = new Theme("Lilith", 0xFFDD425E, new Color(0xAF0000).darker().getRGB(), 0xFFFFFE, 0xFF494949);
            lilith.setLogoPath("lilith");
            themeList.add(lilith);
        }
        if(getContentByJsonKey("Test") == null) {
            Theme test = new Theme("Test", 0xFFFFFFFF, new Color(0xFFFFFFFF, true).darker().getRGB(), 0xFF494949, 0xFFFFFFFF);
            themeList.add(test);
        }
        currentTheme = getContentByJsonKey("Lilith");
        return themeList.size();
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(Theme theme) {
        currentTheme = theme;
        Nexus.getNexus().getConfigManager().saveClientConfig();
    }

    @Override
    public JsonObject saveInfoToJson(JsonObject obj) {
        obj.addProperty("currentTheme", currentTheme.getJsonKey());
        return obj;
    }

    @Override
    public void loadFromJson(JsonObject obj) {
        JsonElement jsonElement = obj.get("currentTheme");
        if(jsonElement != null) {
            Theme theme = getContentByJsonKey(jsonElement.getAsString());
            if(theme != null) {
                setCurrentTheme(theme);
            }
        }
    }

    @Override
    public String getJsonKey() {
        return "themeManager";
    }

    @Override
    public File getDirectory() {
        return new File(FileUtils.getNexusFolderDirAsFile(), "themes.json");
    }

    @Override
    public Collection<Theme> getContents() {
        return themeList;
    }
}
