package xyz.gucciclient.modules;

import net.minecraft.client.*;
import xyz.gucciclient.values.*;
import xyz.gucciclient.utils.*;
import java.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;

public abstract class Module
{
    protected Minecraft mc;
    private String name;
    private int key;
    private boolean state;
    private Category category;
    private ArrayList<BooleanValue> booleans;
    private ArrayList<NumberValue> values;
    
    public Module(final String name, final int key, final Category category) {
        this.mc = Wrapper.getMinecraft();
        this.booleans = new ArrayList<BooleanValue>();
        this.values = new ArrayList<NumberValue>();
        this.name = name;
        this.key = key;
        this.state = false;
        this.category = category;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setKey(final int key) {
        this.key = key;
    }
    
    public boolean setToggled(final boolean toggled) {
        return this.state = toggled;
    }
    
    public boolean getState() {
        return this.state;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public ArrayList<BooleanValue> getBooleans() {
        return this.booleans;
    }
    
    public ArrayList<NumberValue> getValues() {
        return this.values;
    }
    
    public void toggle() {
        this.setState(!this.state);
    }
    
    public void addBoolean(final BooleanValue booleans) {
        this.booleans.add(booleans);
    }
    
    public void addValue(final NumberValue values) {
        this.values.add(values);
    }
    
    public static ArrayList<Module> getCategoryModules(final Category cat) {
        final ArrayList<Module> modsInCategory = new ArrayList<Module>();
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getCategory() == cat) {
                modsInCategory.add(mod);
            }
        }
        return modsInCategory;
    }
    
    public static Module getModule(final Class<? extends Module> clazz) {
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }
    
    public void setState(final boolean enabled) {
        if (this.state == enabled) {
            return;
        }
        this.state = enabled;
        if (enabled) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            FMLCommonHandler.instance().bus().register((Object)this);
            this.onEnable();
        }
        else {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            FMLCommonHandler.instance().bus().unregister((Object)this);
            this.onDisable();
        }
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public enum Modules
    {
        Clicker, 
        Heal, 
        SmoothAim, 
        KillAura, 
        Velocity, 
        Speed, 
        FastPlace, 
        Fullbright, 
        ClickGUI, 
        Hud, 
        AgroPearl, 
        Debuff, 
        SelfDestruct;
    }
    
    public enum Category
    {
        COMBAT, 
        RENDER, 
        OTHER, 
        UTILITY, 
        MOVEMENT;
    }
}
