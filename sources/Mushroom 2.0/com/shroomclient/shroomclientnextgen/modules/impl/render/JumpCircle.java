package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Jump Circle",
    uniqueId = "jumpcircle",
    description = "Renders A Circle When You Land",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class JumpCircle extends Module {

    @ConfigOption(
        name = "Grow Size",
        description = "Max Size Of Circle",
        min = 0.5f,
        max = 5f,
        precision = 2,
        order = 1
    )
    public Float growSizeMax = 1f;

    @ConfigOption(
        name = "Grow Time",
        description = "Circle Grow Time, In MS",
        min = 100,
        max = 5000,
        precision = 2,
        order = 2
    )
    public Integer growSpeedMs = 1000;

    boolean lastOnGround = false;
    ArrayList<jumpLocations> jumpSpots = new ArrayList<>();

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void draw3dEvent(Render3dEvent e) {
        if (C.p().isOnGround() && !lastOnGround) {
            jumpSpots.add(
                new jumpLocations(C.p().getPos(), System.currentTimeMillis())
            );
        }

        lastOnGround = C.p().isOnGround();

        for (int i = 0; i < jumpSpots.size(); i++) {
            int growSize = (int) ((System.currentTimeMillis() -
                    jumpSpots.get(i).time));
            if (growSize >= growSpeedMs) {
                jumpSpots.remove(i);
            } else {
                Vec3d jumpSpot = jumpSpots.get(i).spot;
                Color color = ThemeUtil.themeColors(
                    (int) jumpSpot.x,
                    (int) jumpSpot.z,
                    50,
                    0.001f
                )[0];
                Color colorWithOpac = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    (int) (255 -
                        (((float) growSize / (float) growSpeedMs) * 255))
                );

                RenderUtil.draw3dCircle(
                    jumpSpot.x,
                    jumpSpot.y,
                    jumpSpot.z,
                    ((double) growSize / growSpeedMs) * growSizeMax,
                    e.partialTicks,
                    e.matrixStack,
                    colorWithOpac
                );
            }
        }
    }

    public record jumpLocations(Vec3d spot, long time) {}
}
