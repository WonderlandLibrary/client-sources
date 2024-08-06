package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.TargetUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;

@RegisterModule(
    name = "Pointers",
    uniqueId = "pointers",
    description = "Renders Arrows To Players Outside Of FOV",
    category = ModuleCategory.Render
)
public class Pointers extends Module {

    @ConfigOption(
        name = "Only If Out Of FOV",
        description = "Only Shows Arrows To People Who Aren't In front Of You",
        order = 1
    )
    public static Boolean onlyIfOutOfFov = false;

    @ConfigOption(
        name = "Constant Circle",
        description = "Makes The Pointers Stay Within A Constant Radius",
        order = 2
    )
    public static Boolean constantCircle = false;

    @ConfigOption(
        name = "Constant Scale",
        description = "Makes The Pointers A Constant Size",
        order = 3
    )
    public static Boolean constantScale = false;

    @ConfigOption(
        name = "Circle Radius",
        description = "Sets The Pointer Radius",
        order = 4,
        min = 5,
        max = 100
    )
    public static Float radius = 30f;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onClientTick(RenderTickEvent event) {
        if (C.w() != null) {
            RenderUtil.setContext(event.drawContext);
            for (AbstractClientPlayerEntity player : C.w().getPlayers()) {
                if (player != C.p() && TargetUtil.shouldTarget(player)) {
                    float radius2 = radius;
                    if (!constantCircle) radius2 *= 5;

                    RenderUtil.drawArrow(
                        C.mc.getWindow().getScaledWidth() / 2f,
                        C.mc.getWindow().getScaledHeight() / 2f,
                        radius2,
                        player,
                        ThemeUtil.themeColors()[0]
                    );
                }
            }
        }
    }
}
