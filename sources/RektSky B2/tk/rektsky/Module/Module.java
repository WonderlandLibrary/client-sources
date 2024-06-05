package tk.rektsky.Module;

import net.minecraft.client.*;
import java.util.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Event.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import net.minecraft.util.*;

public class Module
{
    public String name;
    public String description;
    public int keyCode;
    public Category category;
    private boolean toggled;
    public Minecraft mc;
    public ArrayList<Setting> settings;
    public int enabledTicks;
    
    public Module(final String name, final String description, final int key, final Category cat) {
        this.toggled = false;
        this.mc = Minecraft.getMinecraft();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keyCode = key;
        this.category = cat;
        this.description = description;
    }
    
    public Module(final String name, final String description, final Category cat) {
        this.toggled = false;
        this.mc = Minecraft.getMinecraft();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keyCode = 0;
        this.category = cat;
        this.description = description;
    }
    
    public void toggle() {
        if (this.toggled) {
            final ModuleTogglePreEvent event = new ModuleTogglePreEvent(this, false);
            EventManager.callEvent(event);
            if (event.isCanceled()) {
                return;
            }
            this.toggled = false;
            if (ModulesManager.getModuleByClass(ToggleNotifications.class).toggled) {
                Notification.displayNotification(new Notification.PopupMessage(this.name, "Disabled " + this.name, ColorUtil.NotificationColors.RED, 40));
            }
            this.onDisable();
        }
        else {
            final ModuleTogglePreEvent event = new ModuleTogglePreEvent(this, true);
            EventManager.callEvent(event);
            if (event.isCanceled()) {
                return;
            }
            this.toggled = true;
            this.enabledTicks = 0;
            if (ModulesManager.getModuleByClass(ToggleNotifications.class).toggled) {
                Notification.displayNotification(new Notification.PopupMessage(this.name, "Enabled " + this.name, ColorUtil.NotificationColors.GREEN, 40));
            }
            this.onEnable();
        }
    }
    
    public void setToggled(final boolean value) {
        if (!value) {
            final ModuleTogglePreEvent event = new ModuleTogglePreEvent(this, false);
            EventManager.callEvent(event);
            if (event.isCanceled()) {
                return;
            }
            this.toggled = false;
            if (ModulesManager.getModuleByClass(ToggleNotifications.class).toggled) {
                Notification.displayNotification(new Notification.PopupMessage(this.name, "Disabled " + this.name, ColorUtil.NotificationColors.RED, 40));
            }
            this.onDisable();
        }
        else {
            final ModuleTogglePreEvent event = new ModuleTogglePreEvent(this, true);
            EventManager.callEvent(event);
            if (event.isCanceled()) {
                return;
            }
            this.toggled = true;
            this.enabledTicks = 0;
            if (ModulesManager.getModuleByClass(ToggleNotifications.class).toggled) {
                Notification.displayNotification(new Notification.PopupMessage(this.name, "Enabled " + this.name, ColorUtil.NotificationColors.GREEN, 40));
            }
            this.onEnable();
        }
    }
    
    public void rawSetToggled(final boolean value) {
        if (!value) {
            this.toggled = false;
        }
        else {
            this.toggled = true;
        }
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void onEvent(final Event event) {
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public String getSuffix() {
        return "";
    }
    
    public void registerSetting(final Setting setting) {
    }
    
    public enum Category
    {
        COMBAT("Combat", "Combat Hacks, e.g. Killaura", new ResourceLocation("rektsky/clickgui/combat.png")), 
        MOVEMENT("Movement", "Some modules about movement e.g. Fly or Speed", new ResourceLocation("rektsky/clickgui/movement.png")), 
        RENDER("Render", "Render more info on your screen e.g. Tracers", new ResourceLocation("rektsky/clickgui/render.png")), 
        PLAYER("Player", "Player Hacks e.g. ChestStealer", new ResourceLocation("rektsky/clickgui/player.png")), 
        EXPLOIT("Exploits", "Exploit the server : )", new ResourceLocation("rektsky/clickgui/exploit.png")), 
        REKTSKY("RektSky", "AKA Misc : )", new ResourceLocation("rektsky/clickgui/rektsky.png")), 
        WORLD("World", "World Hacks e.g. BedRekter", new ResourceLocation("rektsky/clickgui/world.png"));
        
        private String name;
        private String description;
        private ResourceLocation icon;
        
        private Category(final String name, final String descripton, final ResourceLocation icon) {
            this.name = name;
            this.description = this.description;
            this.icon = icon;
        }
        
        public ResourceLocation getIcon() {
            return this.icon;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getDescription() {
            return this.description;
        }
    }
}
