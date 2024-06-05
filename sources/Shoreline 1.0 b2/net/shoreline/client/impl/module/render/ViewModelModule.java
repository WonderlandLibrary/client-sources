package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 */
public class ViewModelModule extends ToggleModule
{
    //
    Config<Integer> fovConfig = new NumberConfig<>("FOV", "Field of view", 0,
            180, 180);
    Config<Float> xConfig = new NumberConfig<>("X", "Translation in " +
            "x-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> yConfig = new NumberConfig<>("Y", "Translation in " +
            "y-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> zConfig = new NumberConfig<>("Z", "Translation in " +
            "z-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> scaleXConfig = new NumberConfig<>("ScaleX", "Scaling in" +
            "x-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> scaleYConfig = new NumberConfig<>("ScaleY", "Scaling in" +
            "y-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> scaleZConfig = new NumberConfig<>("ScaleZ", "Scaling in" +
            "z-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> rotateXConfig = new NumberConfig<>("RotateX", "Rotation in" +
            "x-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> rotateYConfig = new NumberConfig<>("RotateY", "Rotation in" +
            "y-direction", 0.0f, 0.0f, 5.0f);
    Config<Float> rotateZConfig = new NumberConfig<>("RotateZ", "Rotation in" +
            "z-direction", 0.0f, 0.0f, 5.0f);
    Config<AspectRatio> aspectRatioConfig = new EnumConfig<>("AspectRatio",
            "", AspectRatio.DEFAULT, AspectRatio.values());

    /**
     *
     */
    public ViewModelModule()
    {
        super("ViewModel", "Renders for the first-person viewmodel",
                ModuleCategory.RENDER);
    }

    public enum AspectRatio
    {
        DEFAULT
    }
}
