package com.polarware.util.interfaces;

import com.polarware.Client;
import com.polarware.component.impl.hud.DragComponent;
import com.polarware.module.Module;
import com.polarware.ui.click.RiseClickGUI;
import com.polarware.ui.ingame.GuiIngameCache;
import com.polarware.ui.theme.Themes;
import com.polarware.util.font.Font;
import com.polarware.util.font.FontManager;
import com.polarware.util.profiling.Profiler;
import com.polarware.util.shader.RiseShaders;
import com.polarware.util.shader.base.ShaderRenderType;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This is an interface we can implement if we require access to the game
 * instance or our client instance if we require them in anywhere.
 *
 * @author Tecnio
 * @since 03/08/2021
 */

public interface InstanceAccess {

    Minecraft mc = Minecraft.getMinecraft();
    Client instance = Client.INSTANCE;
    List<Runnable> UI_BLOOM_RUNNABLES = new ArrayList<>();
    List<Runnable> UI_POST_BLOOM_RUNNABLES = new ArrayList<>();
    List<Runnable> UI_RENDER_RUNNABLES = new ArrayList<>();
    List<Runnable> UI_BLUR_RUNNABLES = new ArrayList<>();

    List<Runnable> NORMAL_PRE_RENDER_RUNNABLES = new ArrayList<>();
    List<Runnable> NORMAL_BLUR_RUNNABLES = new ArrayList<>();
    List<Runnable> NORMAL_POST_BLOOM_RUNNABLES = new ArrayList<>();
    List<Runnable> NORMAL_OUTLINE_RUNNABLES = new ArrayList<>();
    List<Runnable> NORMAL_RENDER_RUNNABLES = new ArrayList<>();
    List<Runnable> NORMAL_POST_RENDER_RUNNABLES = new ArrayList<>();

    List<Runnable> LIMITED_PRE_RENDER_RUNNABLES = new ArrayList<>();
    List<Runnable> LIMITED_POST_RENDER_RUNNABLES = new ArrayList<>();

    Font montserratMediumNormal = FontManager.getMontserratMedium(18);

    Font nunitoBoldMedium = FontManager.getNunitoBold(18);

    Font nunitoLarge = FontManager.getNunito(42);
    Font nunitoNormal = FontManager.getNunito(20);
    Font nunitoMedium = FontManager.getNunito(18);
    Font nunitoSmall = FontManager.getNunito(16);

    Font museoLarge = FontManager.getMuseo(42);
    Font museoNormal = FontManager.getMuseo(20);
    Font museoSmall = FontManager.getMuseo(16);

    Font nunitoLightNormal = FontManager.getNunitoLight(20);

    Font robotoNormal = FontManager.getRobotoLight(20);

    Font dreamscapeLarge = FontManager.getDreamscape(92);

    Font biko = FontManager.getBiko(18);

    Executor threadPool = Executors.newFixedThreadPool(2);

    Profiler bloomProfiler = new Profiler();
    Profiler render2dProfiler = new Profiler();
    Profiler renderLimited2dProfiler = new Profiler();
    Profiler outlineProfiler = new Profiler();
    Profiler blurProfiler = new Profiler();
    Profiler dragProfiler = new Profiler();

    default RiseClickGUI getStandardClickGUI() {
        return instance.getStandardClickGUI();
    }

    default <T extends Module> T getModule(final Class<T> clazz) {
        return instance.getModuleManager().get(clazz);
    }

    default Themes getTheme() {
        return instance.getThemeManager().getTheme();
    }

    default void setTheme(Themes theme) {
        instance.getThemeManager().setTheme(theme);
    }

    static void  render2DRunnables(float partialTicks, boolean shaders) {

        render2dProfiler.start();
        NORMAL_PRE_RENDER_RUNNABLES.forEach(Runnable::run);
        render2dProfiler.stop();

        if (shaders) {
            outlineProfiler.start();
            RiseShaders.OUTLINE_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_OUTLINE_RUNNABLES);
            outlineProfiler.stop();

            blurProfiler.start();
            RiseShaders.GAUSSIAN_BLUR_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_BLUR_RUNNABLES);
            blurProfiler.stop();
        }

        render2dProfiler.start();
        NORMAL_RENDER_RUNNABLES.forEach(Runnable::run);
        render2dProfiler.stop();

        renderLimited2dProfiler.start();
        GuiIngameCache.renderGameOverlay(partialTicks);
        renderLimited2dProfiler.start();

        render2dProfiler.start();
        NORMAL_POST_RENDER_RUNNABLES.forEach(Runnable::run);
        render2dProfiler.stop();

        if (shaders) {
            bloomProfiler.start();
            RiseShaders.POST_BLOOM_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES);
            bloomProfiler.stop();
        }

        dragProfiler.start();
        DragComponent.render(partialTicks);
        dragProfiler.stop();

//        if (mc.currentScreen != null) {
//            RiseShaders.UI_GAUSSIAN_BLUR_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.UI_BLUR_RUNNABLES);
//        }

        UI_RENDER_RUNNABLES.forEach(Runnable::run);

        if (mc.currentScreen != null) {

            RiseShaders.UI_BLOOM_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.UI_BLOOM_RUNNABLES);

            RiseShaders.UI_POST_BLOOM_SHADER.run(ShaderRenderType.OVERLAY, partialTicks, InstanceAccess.UI_POST_BLOOM_RUNNABLES);
        }

        dragProfiler.reset();
        bloomProfiler.reset();
        render2dProfiler.reset();
        outlineProfiler.reset();
        blurProfiler.reset();
        renderLimited2dProfiler.reset();
    }

    static void render3DRunnables(float partialTicks) {
        RiseShaders.OUTLINE_SHADER.run(ShaderRenderType.CAMERA, partialTicks, InstanceAccess.NORMAL_OUTLINE_RUNNABLES);
        RiseShaders.POST_BLOOM_SHADER.run(ShaderRenderType.CAMERA, partialTicks, InstanceAccess.NORMAL_POST_BLOOM_RUNNABLES);
        RiseShaders.UI_BLOOM_SHADER.run(ShaderRenderType.CAMERA, partialTicks, InstanceAccess.UI_BLOOM_RUNNABLES);
        RiseShaders.GAUSSIAN_BLUR_SHADER.run(ShaderRenderType.CAMERA, partialTicks, InstanceAccess.NORMAL_BLUR_RUNNABLES);
    }

    static void clearRunnables() {
        NORMAL_BLUR_RUNNABLES.clear();
        NORMAL_POST_BLOOM_RUNNABLES.clear();
        NORMAL_OUTLINE_RUNNABLES.clear();
        NORMAL_RENDER_RUNNABLES.clear();
        UI_BLOOM_RUNNABLES.clear();
        UI_RENDER_RUNNABLES.clear();
        NORMAL_PRE_RENDER_RUNNABLES.clear();
        NORMAL_POST_RENDER_RUNNABLES.clear();
        UI_POST_BLOOM_RUNNABLES.clear();

        LIMITED_PRE_RENDER_RUNNABLES.clear();
        LIMITED_POST_RENDER_RUNNABLES.clear();
    }
}
