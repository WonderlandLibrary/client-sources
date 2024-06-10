 package me.vene.skilled.modules.mods.other;
 
 import java.io.IOException;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 
public class Quakecraft extends Module
{
    public Quakecraft() throws IOException {
        super(StringRegistry.register("Quakecraft"), 0, Category.O);
    }
}
