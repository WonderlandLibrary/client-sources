package cn.Hanabi.injection;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.mixin.*;
import org.jetbrains.annotations.*;
import java.util.*;

public class MixinLoader implements IFMLLoadingPlugin
{
    public MixinLoader() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.hanabi.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }
    
    @NotNull
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    @Nullable
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    @Nullable
    public String getAccessTransformerClass() {
        return null;
    }
}
