package me.zeroeightsix.kami.mixin;

import me.zeroeightsix.kami.KamiMod;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class MixinLoaderForge implements IFMLLoadingPlugin {

    private static boolean isObfuscatedEnvironment = false;

    public MixinLoaderForge() {
        KamiMod.LOGGER.info("KAMI mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.kami.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        KamiMod.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isObfuscatedEnvironment = (boolean) (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
