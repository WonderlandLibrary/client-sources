package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.WorldLoadEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.Identifier;

/* // funny ;3
@RegisterModule(
        name = "Blur Testing",
        uniqueId = "blurring",
        description = "autism rainbow",
        category = ModuleCategory.Render
)
 */

public class BlurTesting extends Module {

    boolean loadBlur = false;

    @Override
    protected void onEnable() {
        loadBlur = true;
    }

    @Override
    protected void onDisable() {
        if (
            C.isInGame() && C.mc.gameRenderer.getPostProcessor() != null
        ) C.mc.gameRenderer.disablePostProcessor();

        loaded = false;
    }

    boolean loaded = false;

    @SubscribeEvent
    public void onRender2d(RenderTickEvent e) {
        if (loadBlur || C.mc.options.getPerspective() != lastPers) {
            if (
                C.mc.gameRenderer.getPostProcessor() != null
            ) C.mc.gameRenderer.disablePostProcessor();
            C.mc.gameRenderer.loadPostProcessor(
                new Identifier("shaders/post/blur.json")
            );

            loadBlur = false;

            lastPers = C.mc.options.getPerspective();

            loaded = true;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldLoadEvent e) {
        loadBlur = true;
    }

    Perspective lastPers = null;
}
