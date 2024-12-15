package com.alan.clients.component.impl.hud;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.component.impl.hud.dragcomponent.api.Orientation;
import com.alan.clients.component.impl.hud.dragcomponent.api.Snap;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.GuiClickEvent;
import com.alan.clients.event.impl.input.GuiMouseReleaseEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.util.MouseUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.DragValue;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import static com.alan.clients.util.animation.Easing.LINEAR;

public class DragComponent extends Component {

    private static DragValue selectedValue = null;
    private static Vector2d offset;
    private static final ArrayList<Module> modules = new ArrayList<>();
    private static final Animation animationAlpha = new Animation(LINEAR, 600);
    public static final StopWatch closeStopWatch = new StopWatch(), stopWatch = new StopWatch();

    public static ArrayList<Snap> snaps = new ArrayList<>();
    public static Snap selected;

    @EventLink(value = -2) // Must be rendered after the LayerManager
    public final Listener<Render2DEvent> onRender2D = event -> {
        try {
            final ScaledResolution scaledResolution = mc.scaledResolution;
            final int width = scaledResolution.getScaledWidth();
            final int height = scaledResolution.getScaledHeight();

            boolean shouldRender = mc.currentScreen instanceof GuiChat;

            if (!shouldRender) {
                selectedValue = null;
            } else {
                closeStopWatch.reset();
            }

            animationAlpha.setEasing(LINEAR);
            animationAlpha.setDuration(300);
            animationAlpha.run(shouldRender ? 100 : 0);

            if (animationAlpha.getValue() <= 0 && closeStopWatch.finished(0)) {
                selectedValue = null;
                return;
            }

            modules.clear();
            Client.INSTANCE.getModuleManager().getAll().stream().filter(module ->
                            module.isEnabled() && module.getValues().stream().
                                    anyMatch(value -> value instanceof DragValue)).
                    forEach(modules::add);

            if (selectedValue != null) {
                Vector2d mouse = MouseUtil.mouse();
                final double positionX = mouse.x + offset.x;
                final double positionY = mouse.y + offset.y;

                selectedValue.targetPosition = new Vector2d(positionX, positionY);

                // Setup snapping
                snaps.clear();

                double edgeSnap = Client.INSTANCE.getThemeManager().getTheme().getPadding();

                // Permanent snaps
                snaps.add(new Snap(width / 2f, 5, Orientation.HORIZONTAL, true, true, true));
                snaps.add(new Snap(height / 2f, 5, Orientation.VERTICAL, true, true, true));

                snaps.add(new Snap(height - edgeSnap, 5, Orientation.VERTICAL, false, false, true));
                snaps.add(new Snap(edgeSnap, 5, Orientation.VERTICAL, false, true, false));
                snaps.add(new Snap(width - edgeSnap, 5, Orientation.HORIZONTAL, false, false, true));
                snaps.add(new Snap(edgeSnap, 5, Orientation.HORIZONTAL, false, true, false));

                for (Module module : modules) {
                    // Getting Position Value
                    Optional<Value<?>> positionValues = module.getValues().stream().filter(value ->
                            value instanceof DragValue).findFirst();
                    DragValue positionValue = ((DragValue) positionValues.get());

                    if (positionValue == selectedValue) continue;

                    snaps.add(new Snap(positionValue.position.x + positionValue.scale.x + edgeSnap, 5, Orientation.HORIZONTAL, false, true, false));
                    snaps.add(new Snap(positionValue.position.x - edgeSnap, 5, Orientation.HORIZONTAL, false, false, true));

                    snaps.add(new Snap(positionValue.position.y, 5, Orientation.VERTICAL, false, false, true));
                    snaps.add(new Snap(positionValue.position.y + positionValue.scale.y, 5, Orientation.VERTICAL, false, true, false));
                }

                double closest;
                selected = null;
                Color color = ColorUtil.withAlpha(Color.WHITE, 60);

                for (Snap snap : snaps) {
                    switch (snap.orientation) {
                        case VERTICAL:
                            closest = Double.MAX_VALUE;

                            for (double y = -selectedValue.scale.y; y <= 0; y += selectedValue.scale.y / 2f) {
                                if ((y == -selectedValue.scale.y / 2 && !snap.center) || (y == -selectedValue.scale.y && !snap.left) || (y == 0 && !snap.right)) {
                                    continue;
                                }

                                double distance = Math.abs(selectedValue.targetPosition.y - (snap.position + y));

                                if (distance < snap.distance && distance < closest) {
                                    closest = distance;
                                    selectedValue.targetPosition.y = snap.position + y;
                                    selected = snap;
                                    RenderUtil.rectangle(0, selected.position, scaledResolution.getScaledWidth(), 0.5, color);
                                }
                            }
                            break;

                        case HORIZONTAL:
                            closest = Double.MAX_VALUE;
                            for (double x = -selectedValue.scale.x; x <= 0; x += selectedValue.scale.x / 2f) {
                                if ((x == -selectedValue.scale.x / 2 && !snap.center) || (x == -selectedValue.scale.x && !snap.left) || (x == 0 && !snap.right)) {
                                    continue;
                                }

                                double distance = Math.abs(selectedValue.targetPosition.x - (snap.position + x));

                                if (distance < snap.distance && distance < closest) {
                                    closest = distance;
                                    selectedValue.targetPosition.x = snap.position + x;
                                    selected = snap;
                                    RenderUtil.rectangle(selected.position, 0, 0.5, scaledResolution.getScaledHeight(), color);
                                }
                            }
                            break;
                    }
                }
            }

            // Validating position
            for (Module module : modules) {
                // Getting Position Value
                Optional<Value<?>> positionValues = module.getValues().stream().filter(value ->
                        value instanceof DragValue).findFirst();
                DragValue positionValue = ((DragValue) positionValues.get());

                float offset = Client.INSTANCE.getThemeManager().getTheme().getPadding();

                positionValue.position.x = Math.max(offset, positionValue.position.x);
                positionValue.position.x = Math.min(width - positionValue.scale.x - offset, positionValue.position.x);

                positionValue.position.y = Math.max(offset, positionValue.position.y);
                positionValue.position.y = Math.min(height - positionValue.scale.y - offset, positionValue.position.y);

                positionValue.targetPosition.x = Math.max(offset, positionValue.targetPosition.x);
                positionValue.targetPosition.x = Math.min(width - positionValue.scale.x - offset, positionValue.targetPosition.x);

                positionValue.targetPosition.y = Math.max(offset, positionValue.targetPosition.y);
                positionValue.targetPosition.y = Math.min(height - positionValue.scale.y - offset, positionValue.targetPosition.y);

                positionValue.position = new Vector2d(Math.min(width - positionValue.scale.x - offset, positionValue.targetPosition.x), Math.min(height - positionValue.scale.y - offset, positionValue.targetPosition.y));
            }

//            for (Module module : modules) {
//                // Init stencil
//                GlStateManager.pushMatrix();
//                StencilUtil.initStencil();
//                StencilUtil.bindWriteStencilBuffer();
//
//                // Gets shape of draggable element
//                Field field = module.getClass().getField("onRender2D");
//                System.out.println(field);
//                ((Listener)field.get(module))
//                        .call(new Render2DEvent(mc.scaledResolution, 1));
//
////                .setClickGUIrender2DRunnables(1, false);
//                .setClickGUIclearRunnables();
//
//                // Ends stencil
//                StencilUtil.bindReadStencilBuffer(1);
//
//                // Render contents within cut out area
//                RenderUtil.rectangle(0, 0, scaledResolution.getScaledWidth(),
//                        scaledResolution.getScaledHeight(), ColorUtil.withAlpha(Color.BLACK, (int) animationAlpha.getValue()));
//
//                // Getting Position Value
//                Optional<Value<?>> positionValues = module.getValues().stream().filter(value ->
//                        value instanceof DragValue).findFirst();
//                DragValue positionValue = ((DragValue) positionValues.get());
//
//                // Getting width
//                double maxScale = Math.min(positionValue.scale.y, positionValue.scale.x);
//
//                positionValue.animationPosition.setEasing(shouldRender || selectedValue == positionValue ? EASE_OUT_QUINT : EASE_IN_QUINT);
//                positionValue.animationPosition.run(shouldRender ? (selectedValue != positionValue ? maxScale : maxScale * 0.8) : 0);
//
//                double widthPercentage = 360f / 895;
//                double heightPercentage = 535f / 895;
//
//                double imageWidth = positionValue.animationPosition.getValue() * widthPercentage;
//                double imageHeight = positionValue.animationPosition.getValue() * heightPercentage;
//
//                double imageX = positionValue.position.x + positionValue.scale.x / 2 - imageWidth / 2f;
//                double imageY = positionValue.position.y + positionValue.scale.y / 2 - imageHeight / 2f;
//
//                RenderUtil.image(image, imageX, imageY, imageWidth, imageHeight, ColorUtil.withAlpha(Color.WHITE, (int) animationAlpha.getValue()));
//
//                // Ends Rendering
//                StencilUtil.uninitStencilBuffer();
//                GlStateManager.popMatrix();
//            }

            stopWatch.reset();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("exception");
        }
    };

    @EventLink
    public final Listener<GuiClickEvent> onGuiClick = event -> {
        if (event.getMouseButton() != 0) {
            return;
        }

        if (mc.currentScreen instanceof GuiChat) {
            for (final Module module : modules) {
                for (final Value<?> value : module.getValues()) {
                    if (value instanceof DragValue) {
                        final DragValue positionValue = (DragValue) value;
                        final Vector2d position = positionValue.position;
                        final Vector2d scale = positionValue.scale;
                        final float mouseX = event.getMouseX();
                        final float mouseY = event.getMouseY();

                        if (!positionValue.structure && GUIUtil.mouseOver(position, scale, mouseX, mouseY)) {
                            selectedValue = positionValue;

                            offset = new Vector2d(position.x - mouseX, position.y - mouseY);
                        }
                    }
                }
            }
        }
    };

    @EventLink
    public final Listener<GuiMouseReleaseEvent> onGuiMouseRelease = event -> {
        selectedValue = null;
    };
}