package fr.dog.module.impl.render;

import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class CustomCape extends Module {

    public final ModeProperty mode = ModeProperty.newInstance("Cape mode", new String[]{"Dog", "DogToprak", "Dog2","BadAiim","Ouuu","2011","2012","2013","2015","2016","CherryBlossom","Vanilla","Cobalt","Sky","Hypixel","FaenTeeth", "Nat"}, "Dog");
    private final HashMap<String, ResourceLocation> capes = new HashMap<>();

    public CustomCape() {
        super("CustomCape", ModuleCategory.RENDER);
        this.setEnabled(false);

        for(String name : mode.getValues()) {
            capes.put(name, new ResourceLocation("dogclient/cape/" + name + ".png"));

        }
        this.registerProperties(mode);
    }

    public ResourceLocation getLocation(){
        return capes.get(mode.getValue());
    }

}
