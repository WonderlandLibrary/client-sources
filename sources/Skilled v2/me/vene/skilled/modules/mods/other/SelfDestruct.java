 package me.vene.skilled.modules.mods.other;
 
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;


public class SelfDestruct extends Module
{
    public SelfDestruct() {
        super(StringRegistry.register(new String(new char[] { 'S', 'e', 'l', 'f', 'D', 'e', 's', 't', 'r', 'u', 'c', 't' })), 0, Category.O);
    }
    
    @Override
    public void onEnable() {
        SkilledClient.getInstance().nigger = true;
        SkilledClient.getInstance().selfDestruct();
    }
}
