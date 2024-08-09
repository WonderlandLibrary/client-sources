/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.VideoMode;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FullscreenResolutionOption
extends SliderPercentageOption {
    public FullscreenResolutionOption(MainWindow mainWindow) {
        this(mainWindow, mainWindow.getMonitor());
    }

    private FullscreenResolutionOption(MainWindow mainWindow, @Nullable Monitor monitor) {
        super("options.fullscreen.resolution", -1.0, monitor != null ? (double)(monitor.getVideoModeCount() - 1) : -1.0, 1.0f, arg_0 -> FullscreenResolutionOption.lambda$new$1(monitor, mainWindow, arg_0), (arg_0, arg_1) -> FullscreenResolutionOption.lambda$new$2(monitor, mainWindow, arg_0, arg_1), (arg_0, arg_1) -> FullscreenResolutionOption.lambda$new$3(monitor, arg_0, arg_1));
    }

    private static ITextComponent lambda$new$3(Monitor monitor, GameSettings gameSettings, SliderPercentageOption sliderPercentageOption) {
        if (monitor == null) {
            return new TranslationTextComponent("options.fullscreen.unavailable");
        }
        double d = sliderPercentageOption.get(gameSettings);
        return d == -1.0 ? sliderPercentageOption.getGenericValueComponent(new TranslationTextComponent("options.fullscreen.current")) : sliderPercentageOption.getGenericValueComponent(new StringTextComponent(monitor.getVideoModeFromIndex((int)d).toString()));
    }

    private static void lambda$new$2(Monitor monitor, MainWindow mainWindow, GameSettings gameSettings, Double d) {
        if (monitor != null) {
            if (d == -1.0) {
                mainWindow.setVideoMode(Optional.empty());
            } else {
                mainWindow.setVideoMode(Optional.of(monitor.getVideoModeFromIndex(d.intValue())));
            }
        }
    }

    private static Double lambda$new$1(Monitor monitor, MainWindow mainWindow, GameSettings gameSettings) {
        if (monitor == null) {
            return -1.0;
        }
        Optional<VideoMode> optional = mainWindow.getVideoMode();
        return optional.map(arg_0 -> FullscreenResolutionOption.lambda$new$0(monitor, arg_0)).orElse(-1.0);
    }

    private static Double lambda$new$0(Monitor monitor, VideoMode videoMode) {
        return monitor.getVideoModeIndex(videoMode);
    }
}

