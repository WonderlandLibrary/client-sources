package ez.h.features;

import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;
import ez.h.ui.*;
import ez.h.*;
import java.util.*;
import ez.h.features.another.*;
import java.awt.*;

public class Feature
{
    public List<Option> options;
    String description;
    final Category category;
    int key;
    protected static bib mc;
    String suffix;
    boolean hidden;
    public Counter counter;
    boolean enabled;
    final String name;
    
    static {
        Feature.mc = bib.z();
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSuffix() {
        if (this.suffix.equals("") || this.suffix == null) {
            return this.name;
        }
        a a = a.v;
        final String mode = HUD.suffixColor.getMode();
        switch (mode) {
            case "Gray": {
                a = a.h;
                break;
            }
            case "White": {
                a = a.p;
                break;
            }
        }
        return this.name + " " + a + this.suffix;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public void deltaTickEvent() {
    }
    
    public Feature(final String name, final String description, final Category category) {
        this.counter = new Counter();
        this.options = new ArrayList<Option>();
        this.name = name;
        this.description = description;
        this.category = category;
        this.setSuffix("");
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void toggle() {
        this.enabled = !this.enabled;
        final String string = "&1&lINFO&8: &f\u0424\u0443\u043d\u043a\u0446\u0438\u044f &5" + this.name + " ";
        String s;
        if (this.enabled) {
            s = string + "&a&l\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d\u0430";
            this.onEnable();
        }
        else {
            s = string + "&c&l\u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d\u0430";
            this.onDisable();
        }
        if (Main.getFeatureByName("Debug").isEnabled()) {
            Feature.mc.h.a((hh)new ho(s.replace("&", "§")));
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void updateElements() {
    }
    
    public void addOptions(final Option... array) {
        this.options.addAll(Arrays.asList(array));
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void onDisable() {
        Main.instance.eventManager.unregister(this);
        if (this instanceof KeyPearl) {
            return;
        }
        Notifications.addNotification("Disabled", this.getName(), Notifications.rectColor.getColor(), new Color(-1));
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void onEnable() {
        Main.instance.eventManager.register(this);
        if (this instanceof KeyPearl) {
            return;
        }
        Notifications.addNotification("Enabled", this.getName(), Notifications.rectColor.getColor(), new Color(-1));
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
}
