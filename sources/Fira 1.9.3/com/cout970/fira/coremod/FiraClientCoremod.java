package com.cout970.fira.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@MCVersion("1.12.2")
@Name("FiraClient CoreMod")
@TransformerExclusions({"com.cout970.fira.coremod", "com.cout970.fira.coremod.mixin"})
@SortingIndex(1002)
@Keep
public class FiraClientCoremod implements IFMLLoadingPlugin {

    public FiraClientCoremod() {
        CoreModLog.LOG.info("Using encoding: " + System.getProperty("file.encoding"));
//        System.setProperty("mixin.debug.verbose", "true");
//        System.setProperty("mixin.debug.export", "true");

        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.fira_client.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        CoreModLog.LOG.info("Fira mixins initialized");
        CoreModLog.LOG.info("ObfuscationContext: " + MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
