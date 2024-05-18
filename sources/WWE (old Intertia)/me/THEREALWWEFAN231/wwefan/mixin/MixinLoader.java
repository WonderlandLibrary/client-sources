package me.THEREALWWEFAN231.wwefan.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import java.util.*;

public class MixinLoader implements IFMLLoadingPlugin
{
    public MixinLoader() {
        super();
        MixinBootstrap.init();
        if (Boolean.parseBoolean(System.getProperty("disablebaritone"))) {
            Mixins.addConfiguration("mixins.wwefan.json");
        }
        else {
            Mixins.addConfigurations("mixins.wwefan.json", "mixins.baritone.json");
        }
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> map) {
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
