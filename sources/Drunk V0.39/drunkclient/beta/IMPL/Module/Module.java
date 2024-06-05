/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package drunkclient.beta.IMPL.Module;

import drunkclient.beta.API.EventBus;
import drunkclient.beta.Client;
import drunkclient.beta.IMPL.Module.Module1;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.render.HUD;
import drunkclient.beta.IMPL.managers.FileManager;
import drunkclient.beta.IMPL.managers.ModuleManager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Value;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Module {
    public String name;
    public String description;
    private String suffix;
    private int color;
    private String[] alias;
    private boolean enabled;
    public boolean enabledOnStartup = false;
    private int key;
    public List<Value> values;
    public Type type;
    private boolean removed;
    public static Minecraft mc = Minecraft.getMinecraft();
    public static Random random = new Random();
    boolean hasClicked = false;

    public Module(String name, String[] alias, Type type, String description) {
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.type = type;
        this.suffix = "";
        this.key = 0;
        this.removed = false;
        this.enabled = false;
        this.values = new ArrayList<Value>();
    }

    public Module() {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getAlias() {
        return this.alias;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean wasRemoved() {
        return this.removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(Object obj) {
        String suffix = obj.toString();
        if (suffix.isEmpty()) {
            this.suffix = suffix;
            return;
        }
        this.suffix = String.format("%s\u00a77", (Object)((Object)EnumChatFormatting.GRAY) + suffix);
    }

    public void setEnabled(boolean enabled) {
        HUD hud = (HUD)ModuleManager.getModuleByName("Hud");
        this.enabled = enabled;
        if (enabled) {
            EventBus.getInstance().register(this);
            this.onEnable();
            return;
        }
        EventBus.getInstance().unregister(this);
        this.onDisable();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    protected void addValues(Value ... values) {
        Value[] var5 = values;
        int var4 = values.length;
        int var3 = 0;
        while (var3 < var4) {
            Value value = var5[var3];
            this.values.add(value);
            ++var3;
        }
    }

    public List<Value> getValues() {
        return this.values;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
        String content = "";
        Iterator<Module> var4 = Client.instance.getModuleManager().getModules().iterator();
        while (true) {
            if (!var4.hasNext()) {
                FileManager.save("Binds.txt", content, false);
                return;
            }
            Module m = var4.next();
            content = content + String.format("%s:%s%s", m.getName(), Keyboard.getKeyName((int)m.getKey()), System.lineSeparator());
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    /*
     * Unable to fully structure code
     */
    public void makeCommand() {
        if (this.values.size() <= 0) return;
        options = "";
        other = "";
        for (Value v : this.values) {
            if (v instanceof Mode) continue;
            if (options.isEmpty()) {
                options = options + v.getName();
                continue;
            }
            options = options + String.format(", %s", new Object[]{v.getName()});
        }
        var4 = this.values.iterator();
        block1: while (true) {
            if (!var4.hasNext()) {
                Client.instance.getCommandManager().add(new Module1(this, this.name, this.alias, String.format("%s%s", new Object[]{options.isEmpty() != false ? "" : String.format("%s,", new Object[]{options}), other.isEmpty() != false ? "" : String.format("%s", new Object[]{other})}), "Setup this module"));
                return;
            }
            v = var4.next();
            if (!(v instanceof Mode)) continue;
            mode = (Mode)v;
            modes = mode.getModes();
            length = modes.length;
            i = 0;
            while (true) {
                if (i < length) ** break;
                continue block1;
                e = modes[i];
                other = other.isEmpty() != false ? other + e.name().toLowerCase() : other + String.format(", %s", new Object[]{e.name().toLowerCase()});
                ++i;
            }
            break;
        }
    }
}

