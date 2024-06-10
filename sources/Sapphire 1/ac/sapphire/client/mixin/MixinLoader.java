package ac.sapphire.client.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public final class MixinLoader implements IFMLLoadingPlugin {
    public MixinLoader() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.sapphire.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{};
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
        // Empty block
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}