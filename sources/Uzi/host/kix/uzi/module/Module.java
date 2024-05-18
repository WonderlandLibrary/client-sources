package host.kix.uzi.module;

import com.darkmagician6.eventapi.EventManager;
import host.kix.uzi.Uzi;
import host.kix.uzi.utilities.value.Value;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by myche on 2/3/2017.
 */
public class Module {

    public static Minecraft mc = Minecraft.getMinecraft();
    private String name;
    private String nameWithSuffixAndStuff;
    private int bind;
    private Category category;
    private boolean hidden;
    private boolean enabled;
    private ArrayList values = new ArrayList();
    private Color color;

    public Module(String name, int bind, Category category) {
        this.name = name;
        this.bind = bind;
        this.category = category;
        this.nameWithSuffixAndStuff = name;
        Random random = new Random();
        this.color = Color.getHSBColor(random.nextFloat(), 0.5F, 1F);
    }

    public ArrayList<Value> getValues() {
        return values;
    }

    public void add(Value value) {
        getValues().add(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBind() {
        return bind;
    }

    public void setBind(int bind) {
        this.bind = bind;
        if (Uzi.getInstance().getFileManager().find("modinfo") != null) {
            Uzi.getInstance().getFileManager().find("modinfo").saveFile();
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Color getColor() {
        return color;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }

        if (Uzi.getInstance().getFileManager().find("modinfo") != null) {
            Uzi.getInstance().getFileManager().find("modinfo").saveFile();
        }
    }

    public Value findGivenValue(String identifier) {
        for (Value val : this.getValues()) {
            if (val.getName().equalsIgnoreCase(identifier)) {
                return val;
            }
        }
        return null;
    }

    public void onEnable() {
        EventManager.register(this);
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    public enum Category {
        COMBAT, EXPLOITS, MISC, MOVEMENT, RENDER, WORLD
    }
}
