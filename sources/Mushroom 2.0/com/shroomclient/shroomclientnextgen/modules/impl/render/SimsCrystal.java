package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.Iterator;
import net.minecraft.client.network.AbstractClientPlayerEntity;

@RegisterModule(
    name = "Sims Crystal",
    uniqueId = "simsc",
    description = "Draws A Sims Crystal Above Your Head",
    category = ModuleCategory.Render,
    enabledByDefault = false
)
public class SimsCrystal extends Module {

    @ConfigOption(
        name = "Theme Color",
        description = "Makes The Sims Crystal Theme Colored",
        order = 1
    )
    public static Boolean themeColor = true;

    @ConfigOption(
        name = "Only Self",
        description = "Only Draws Sims Crystal Over Yourself",
        order = 2
    )
    public static Boolean onlySelf = true;

    @ConfigOption(
        name = "Only In F5",
        description = "Only Draws Sims Crystal Over Yourself While In F5",
        order = 3
    )
    public static Boolean onlyInf5 = true;

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        Iterator<AbstractClientPlayerEntity> players = C.w()
            .getPlayers()
            .iterator();

        while (players.hasNext()) {
            AbstractClientPlayerEntity player = players.next();

            if (player.getId() == C.p().getId() || !onlySelf) {
                if (
                    player.getId() != C.p().getId() ||
                    !C.mc.options.getPerspective().isFirstPerson() ||
                    !onlyInf5
                ) {
                    if (!TargetUtil.isBot(player)) {
                        double playX =
                            player.lastRenderX +
                            (player.getX() - player.lastRenderX) *
                                e.partialTicks;
                        double playY =
                            player.lastRenderY +
                            (player.getY() - player.lastRenderY) *
                                e.partialTicks;
                        double playZ =
                            player.lastRenderZ +
                            (player.getZ() - player.lastRenderZ) *
                                e.partialTicks;

                        if (themeColor) RenderUtil.drawSimsCrystal(
                            playX,
                            playY + 2.4,
                            playZ,
                            0.4,
                            1.2,
                            e.partialTicks,
                            e.matrixStack,
                            ThemeUtil.themeColors(
                                (int) playX,
                                (int) playZ,
                                50,
                                0.002f
                            )[0]
                        );
                        else RenderUtil.drawSimsCrystal(
                            playX,
                            playY + 2.4,
                            playZ,
                            0.4,
                            1.2,
                            e.partialTicks,
                            e.matrixStack,
                            new Color(0, 200, 100, 150)
                        );
                    }
                }
            }
        }
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
