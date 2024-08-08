// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin;

import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import me.perry.mcdonalds.McDonalds;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class McDonaldsMixinLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public McDonaldsMixinLoader() {
        McDonalds.LOGGER.info("\n\nLoading mixins by zPrestige_\n");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.mcdonalds.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        McDonalds.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        McDonaldsMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        McDonaldsMixinLoader.isObfuscatedEnvironment = false;
    }
}
