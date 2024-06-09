/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.S02PacketChat;
import us.amerikan.modules.Category;

public class Module {
    public String name;
    private String displayname;
    public int key;
    public Category category;
    public boolean toggled;
    public boolean visible;
    boolean enabled;
    public boolean isEnabled;
    private int anim;
    private int keybind;
    private int keyBind;
    private String addon;
    public static Boolean colormode = false;
    public static Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String displayname, int keybind, Category category) {
        this.name = name;
        this.displayname = displayname;
        this.category = category;
        this.keybind = keybind;
        this.visible = true;
        this.setup();
    }

    public String getName() {
        return this.name;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isEnabled() {
        return this.toggled;
    }

    public void onToggled() {
    }

    public void onEnabled() {
    }

    public void onDisabled() {
    }

    public void onToggle() {
    }

    public void setup() {
    }

    public void toggle() {
        if (this.toggled) {
            this.toggled = false;
            this.onDisable();
        } else {
            this.toggled = true;
            this.onEnable();
        }
    }

    public void onEnable() {
        if (this.anim == -1) {
            this.anim = 0;
        }
    }

    public void onDisable() {
    }

    public boolean isCategory(Category category) {
        return this.category == category;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean onSendChatMessage(String s2) {
        return true;
    }

    public boolean onRecieveChatMessage(S02PacketChat packet) {
        return true;
    }

    public boolean getToggle() {
        return false;
    }

    public Minecraft getMC() {
        return Minecraft.getMinecraft();
    }

    public boolean getState() {
        return this.isEnabled;
    }

    public void setState(boolean state) {
        this.onToggle();
        if (state) {
            this.onEnable();
            this.isEnabled = true;
        } else {
            this.onDisable();
            this.isEnabled = false;
        }
    }

    public void toggleModule() {
        this.setState(!this.getState());
    }

    public void onRender() {
    }

    public void onPreMotionUpdate() {
    }

    public void onTick() {
    }

    public void onEvent(Event event) {
    }

    public String getAddon() {
        if (this.addon != null) {
            return this.addon;
        }
        return "ignore";
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public static boolean isPressed() {
        return Module.mc.gameSettings.keyBindForward.pressed || Module.mc.gameSettings.keyBindBack.pressed || Module.mc.gameSettings.keyBindRight.pressed || Module.mc.gameSettings.keyBindLeft.pressed;
    }

    public void onUpdate() {
    }

    public int getAnim() {
        return this.anim;
    }

    public void setAnim(int anim) {
        this.anim = anim;
    }
}

