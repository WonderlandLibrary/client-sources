package com.client.glowclient.sponge;

import net.minecraftforge.fml.relauncher.*;
import java.util.*;
import net.minecraftforge.fml.common.*;
import org.spongepowered.asm.launch.*;
import com.client.glowclient.utils.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;

public class GlowClientCore implements IFMLLoadingPlugin
{
    public String getModContainerClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> map) {
    }
    
    public GlowClientCore() {
        Message.message();
        final String s = "k\u001f~\u001fh\u0005(\u0011j\u0019q\u0015j\u001fc\u0018rXl\u0005i\u0018";
        super();
        FMLLog.log.info("GlowClient SpongeForge ASM");
        MixinBootstrap.init();
        Mixins.addConfiguration(Value.M(s));
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
