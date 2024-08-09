package ez.h.managers;

import ez.h.*;
import ez.h.managers.altmanager.*;
import ez.h.ui.clickgui.*;
import org.json.*;
import ez.h.features.*;
import ez.h.features.another.*;
import ez.h.features.player.*;
import ez.h.features.visual.*;
import ez.h.ui.clickgui.options.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ConfigManager
{
    public String currentCFG;
    bib mc;
    public static File path;
    
    public boolean initConfig(final String s) throws IOException {
        if (!new File(ConfigManager.path.getAbsolutePath() + "\\configs\\" + s).exists()) {
            new File(ConfigManager.path.getAbsolutePath() + "\\configs\\" + s).createNewFile();
            return false;
        }
        final JSONObject jsonObject = new JSONObject(new JSONTokener((InputStream)new FileInputStream(ConfigManager.path.getAbsolutePath() + "\\configs\\" + s)));
        for (final Panel panel : ClickGuiScreen.panels) {
            if (panel == null) {
                continue;
            }
            try {
                final JSONObject jsonObject2 = jsonObject.getJSONObject(panel.category.name());
                if (jsonObject2.has("isOpen")) {
                    if (jsonObject2.getBoolean("isOpen") && !panel.isOpen) {
                        panel.isOpen = true;
                    }
                    else if (!jsonObject2.getBoolean("isOpen") && panel.isOpen) {
                        panel.isOpen = false;
                    }
                }
                if (!jsonObject2.has(panel.category.name() + "Position")) {
                    continue;
                }
                final float[] array = { Float.parseFloat(jsonObject2.getString(panel.category.name() + "Position").split(":")[0]), Float.parseFloat(jsonObject2.getString(panel.category.name() + "Position").split(":")[1]) };
                panel.x = array[0];
                panel.y = array[1];
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        for (final Feature feature : Main.features) {
            if (feature == null) {
                continue;
            }
            try {
                final JSONObject jsonObject3 = jsonObject.getJSONObject(feature.getName());
                if (jsonObject3.has("isEnabled") && !(feature instanceof Freecam) && !(feature instanceof FakePlayer) && !(feature instanceof Blink) && !(feature instanceof Perspective)) {
                    if (jsonObject3.getBoolean("isEnabled") && !feature.isEnabled()) {
                        feature.toggle();
                    }
                    else if (!jsonObject3.getBoolean("isEnabled") && feature.isEnabled()) {
                        feature.toggle();
                    }
                }
                if (jsonObject3.has("isHidden")) {
                    if (jsonObject3.getBoolean("isHidden") && !feature.isHidden()) {
                        feature.setHidden(true);
                    }
                    else if (!jsonObject3.getBoolean("isHidden") && feature.isHidden()) {
                        feature.setHidden(false);
                    }
                }
                if (jsonObject3.has("key")) {
                    feature.setKey(jsonObject3.getInt("key"));
                }
                for (final Option option : feature.options) {
                    if (option == null) {
                        continue;
                    }
                    if (!jsonObject3.has(option.getName())) {
                        continue;
                    }
                    if (option instanceof OptionBoolean) {
                        ((OptionBoolean)option).enabled = jsonObject3.getBoolean(option.getName());
                    }
                    else if (option instanceof OptionMode) {
                        final List<String> list = Arrays.asList(((OptionMode)option).values);
                        ((OptionMode)option).setMode(list.contains(jsonObject3.getString(option.getName())) ? jsonObject3.getString(option.getName()) : list.get(0));
                    }
                    else if (option instanceof OptionSlider) {
                        ((OptionSlider)option).setNum((float)jsonObject3.getDouble(option.getName()));
                    }
                    else {
                        if (!(option instanceof OptionColor)) {
                            continue;
                        }
                        ((OptionColor)option).setColor(new Color(jsonObject3.getInt(option.getName())));
                        ((OptionColor)option).alpha = jsonObject3.getInt(option.getName() + ":alpha");
                    }
                }
            }
            catch (JSONException ex2) {
                ex2.printStackTrace();
            }
        }
        return true;
    }
    
    public void saveAlts() {
        if (!new File(ConfigManager.path.getAbsolutePath()).exists()) {
            new File(ConfigManager.path.getAbsolutePath()).mkdirs();
        }
        final StringBuilder sb = new StringBuilder();
        Main.alts.forEach(alt -> sb.append(alt.getName()).append(":").append(alt.getPassword()).append(":").append(Calendar.getInstance().get(5) + "/" + Calendar.getInstance().get(2) + "/" + Calendar.getInstance().get(1)).append("\n"));
        try {
            Files.write(Paths.get(new File(ConfigManager.path.getAbsolutePath() + "\\alts.w").toURI()), sb.toString().getBytes(), StandardOpenOption.WRITE);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean initClickGUI(final String s) throws IOException {
        if (!new File(ConfigManager.path.getAbsolutePath() + "\\configs\\" + s).exists()) {
            return false;
        }
        final JSONObject jsonObject = new JSONObject(new JSONTokener((InputStream)new FileInputStream(ConfigManager.path.getAbsolutePath() + "\\configs\\" + s)));
        for (final Panel panel : ClickGuiScreen.panels) {
            if (panel == null) {
                continue;
            }
            try {
                final JSONObject jsonObject2 = jsonObject.getJSONObject(panel.category.name());
                if (jsonObject2.has("isOpen")) {
                    if (jsonObject2.getBoolean("isOpen") && !panel.isOpen) {
                        panel.isOpen = true;
                    }
                    else if (!jsonObject2.getBoolean("isOpen") && panel.isOpen) {
                        panel.isOpen = false;
                    }
                }
                if (!jsonObject2.has(panel.category.name() + "Position")) {
                    continue;
                }
                final float[] array = { Float.parseFloat(jsonObject2.getString(panel.category.name() + "Position").split(":")[0]), Float.parseFloat(jsonObject2.getString(panel.category.name() + "Position").split(":")[1]) };
                panel.x = array[0];
                panel.y = array[1];
            }
            catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }
    
    public void initAlts() {
        if (!new File(ConfigManager.path.getAbsolutePath() + "\\alts.w").exists()) {
            try {
                new File(ConfigManager.path.getAbsolutePath() + "\\alts.w").createNewFile();
            }
            catch (Exception ex2) {}
            return;
        }
        try {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            final List<Alt> alts;
            final Alt alt;
            Files.readAllLines(Paths.get(new File(ConfigManager.path.getAbsolutePath() + "\\alts.w").toURI())).stream().filter(s -> s.split(":").length == 3).forEach(s2 -> {
                alts = Main.alts;
                new Alt(s2.split(":")[0], (s2.split(":").length == 1) ? "" : s2.split(":")[1], s2.split(":")[2]);
                alts.add(alt);
            });
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void init() throws IOException {
        if (!ConfigManager.path.exists()) {
            ConfigManager.path.mkdirs();
        }
        final File file = new File(ConfigManager.path.getAbsolutePath() + "\\configs\\");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (new File(ConfigManager.path.getAbsolutePath() + "\\configs\\" + this.currentCFG).exists()) {
            this.initConfig(this.currentCFG);
        }
        else {
            new File(ConfigManager.path.getAbsolutePath() + "\\configs\\" + this.currentCFG).createNewFile();
            this.update(this.currentCFG);
        }
    }
    
    static {
        ConfigManager.path = new File(bib.z().w.getAbsolutePath() + "\\WILD");
    }
    
    public void update(final String s) throws IOException {
        if (!new File(ConfigManager.path + "\\configs\\" + this.currentCFG).exists()) {
            new File(ConfigManager.path + "\\configs\\" + this.currentCFG).createNewFile();
        }
        FriendManager.save();
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 0.1);
        for (final Panel panel : ClickGuiScreen.panels) {
            final JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("isOpen", panel.isOpen);
            jsonObject2.put(panel.category.name() + "Position", (Object)(panel.x + ":" + panel.y));
            jsonObject.put(panel.category.name(), (Object)jsonObject2);
        }
        for (final Feature feature : Main.features) {
            final JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("key", feature.getKey());
            jsonObject3.put("isEnabled", feature.isEnabled());
            jsonObject3.put("isHidden", feature.isHidden());
            for (final Option option : feature.options) {
                if (option instanceof OptionBoolean) {
                    jsonObject3.put(option.getName(), ((OptionBoolean)option).isEnabled());
                }
                else if (option instanceof OptionMode) {
                    jsonObject3.put(option.getName(), (Object)((OptionMode)option).getMode());
                }
                else if (option instanceof OptionSlider) {
                    jsonObject3.put(option.getName(), (double)((OptionSlider)option).getNum());
                }
                else {
                    if (!(option instanceof OptionColor)) {
                        continue;
                    }
                    jsonObject3.put(option.getName(), ((OptionColor)option).getColor().getRGB());
                    jsonObject3.put(option.getName() + ":alpha", ((OptionColor)option).alpha);
                }
            }
            jsonObject.put(feature.getName(), (Object)jsonObject3);
        }
        if (!new File(ConfigManager.path + "\\configs\\" + s).exists()) {
            new File(ConfigManager.path + "\\configs\\" + this.currentCFG).createNewFile();
        }
        Files.write(Paths.get(new File(ConfigManager.path + "\\configs\\" + s).toURI()), jsonObject.toString().getBytes(), StandardOpenOption.WRITE);
    }
    
    public ConfigManager() {
        this.currentCFG = "config.w";
        this.mc = bib.z();
    }
}
