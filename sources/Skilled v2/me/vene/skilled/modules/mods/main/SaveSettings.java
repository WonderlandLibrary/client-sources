 package me.vene.skilled.modules.mods.main;
 
 import com.google.gson.JsonObject;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;

public class SaveSettings extends Module
{
    public SaveSettings() {
        super("Save Settings", 0, Category.G);
    }
    
    @Override
    public void onEnable() {
        final JsonObject obj = new JsonObject();
        obj.addProperty("Reach Min", (Number)3.0);
        obj.addProperty("Reach Max", (Number)3.0);
        this.setState(false);
    }
}
