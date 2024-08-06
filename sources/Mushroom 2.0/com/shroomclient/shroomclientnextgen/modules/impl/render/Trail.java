package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.ArrayList;
import me.x150.renderer.render.Renderer3d;
import net.minecraft.util.math.Vec3d;

@RegisterModule(
    name = "Trail",
    uniqueId = "trail",
    description = "Draws A Trail Under You",
    category = ModuleCategory.Render,
    enabledByDefault = false
)
public class Trail extends Module {

    @ConfigMode
    @ConfigParentId("mode")
    @ConfigOption(name = "Mode", description = "", order = 3)
    public static Mode mode = Mode.Line;

    @ConfigOption(
        name = "Trail Points Ticks",
        description = "How Fast The Trail Goes Away, In Ticks",
        min = 10,
        max = 200,
        order = 1
    )
    public Integer trailLength = 20;

    @ConfigChild(value = "mode", parentEnumOrdinal = 1)
    @ConfigOption(
        name = "Trail Circles Size",
        description = "Size Of Circle",
        min = 0.5f,
        max = 5f,
        precision = 2,
        order = 2
    )
    public Float growSizeMax = 1f;

    ArrayList<Vec3d> spots = new ArrayList<>();
    int ticks = 0;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (mode == Mode.Square) {
            Vec3d size = new Vec3d(0.2d, 0.2d, 0.2d);
            Renderer3d.renderFadingBlock(
                ThemeUtil.themeColors(
                    (int) e.getX(),
                    (int) e.getZ(),
                    5,
                    0.005f
                )[0],
                ThemeUtil.themeColors(
                    (int) e.getX(),
                    (int) e.getZ(),
                    5,
                    0.005f
                )[0],
                new Vec3d(e.getX(), e.getY() + 0.25, e.getZ()).add(
                    size.multiply(0.5).multiply(-1)
                ),
                size,
                5000
            );
        }

        if (C.p() != null) spots.add(C.p().getPos());
    }

    @SubscribeEvent
    public void draw3dEvent(Render3dEvent e) {
        ticks++;

        while (spots.size() > trailLength) {
            spots.remove(0);
        }

        if (spots.size() > 1) {
            Vec3d lastPos = spots.get(0);
            for (int i = 0; i < spots.size(); i++) {
                switch (mode) {
                    case Circle -> {
                        int growSize = trailLength - i;

                        Vec3d spot = spots.get(i);
                        Color color = ThemeUtil.themeColors(
                            (int) spot.x,
                            (int) spot.z,
                            10,
                            0.005f
                        )[0];
                        Color colorWithOpac = new Color(
                            color.getRed(),
                            color.getGreen(),
                            color.getBlue(),
                            Math.max(
                                Math.min(
                                    (int) (255 -
                                        (((float) growSize /
                                                (float) spots.size()) *
                                            255)),
                                    255
                                ),
                                0
                            )
                        );

                        RenderUtil.draw3dCircle(
                            spot.x,
                            spot.y,
                            spot.z,
                            ((double) growSize / spots.size()) * growSizeMax,
                            e.partialTicks,
                            e.matrixStack,
                            colorWithOpac
                        );
                    }
                    case Line -> {
                        if (i > 0) {
                            Vec3d spot = spots.get(i);
                            RenderUtil.drawLine3d(
                                lastPos,
                                spot,
                                e.partialTicks,
                                e.matrixStack,
                                ThemeUtil.themeColors(
                                    (int) spot.x,
                                    (int) spot.z,
                                    5,
                                    0.005f
                                )[0]
                            );
                            lastPos = spot;
                        }
                    }
                }
            }
        }
    }

    public enum Mode {
        Line,
        Circle,
        Square,
    }
}
