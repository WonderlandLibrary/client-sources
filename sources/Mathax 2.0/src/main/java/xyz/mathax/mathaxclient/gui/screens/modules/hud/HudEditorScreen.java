package xyz.mathax.mathaxclient.gui.screens.modules.hud;

import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.systems.hud.HudElement;
import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class HudEditorScreen extends WidgetScreen {
    private final Color HOVER_BG_COLOR = new Color(200, 200, 200, 75);
    private final Color HOVER_OL_COLOR = new Color(200, 200, 200, 200);

    private final Color INACTIVE_BG_COLOR = new Color(200, 25, 25, 75);
    private final Color INACTIVE_OL_COLOR = new Color(200, 25, 25, 200);

    private final Hud hud;

    private final List<HudElement> selectedElements = new ArrayList<>();

    private HudElement hoveredModule;

    private boolean selecting;
    private double mouseStartX, mouseStartY;

    private boolean dragging, dragged;
    private double lastMouseX, lastMouseY;

    public HudEditorScreen(Theme theme) {
        super(theme, "Hud Editor");

        this.hud = Systems.get(Hud.class);
    }

    @Override
    public void initWidgets() {}

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (hoveredModule != null) {
            if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                if (!selectedElements.isEmpty()) {
                    selectedElements.clear();
                }

                mc.setScreen(new HudElementScreen(theme, hoveredModule));
            } else {
                dragging = true;
                dragged = false;

                if (!selectedElements.contains(hoveredModule)) {
                    selectedElements.clear();
                    selectedElements.add(hoveredModule);
                }
            }

            return true;
        }

        selecting = true;

        double scaleFactor = mc.getWindow().getScaleFactor();
        mouseStartX = mouseX * scaleFactor;
        mouseStartY = mouseY * scaleFactor;

        if (!selectedElements.isEmpty()) {
            selectedElements.clear();
            return true;
        }

        return false;
    }

    private boolean isInSelection(double mouseX, double mouseY, double x, double y) {
        double sx, sy;
        double sWidth, sHeight;
        if (mouseX >= mouseStartX) {
            sx = mouseStartX;
            sWidth = mouseX - mouseStartX;
        } else {
            sx = mouseX;
            sWidth = mouseStartX - mouseX;
        }

        if (mouseY >= mouseStartY) {
            sy = mouseStartY;
            sHeight = mouseY - mouseStartY;
        } else {
            sy = mouseY;
            sHeight = mouseStartY - mouseY;
        }

        return x >= sx && x <= sx + sWidth && y >= sy && y <= sy + sHeight;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        double scaleFactor = mc.getWindow().getScaleFactor();
        mouseX *= scaleFactor;
        mouseY *= scaleFactor;

        if (selecting) {
            selectedElements.clear();

            for (HudElement module : hud.elements) {
                double mX = module.box.getX();
                double mY = module.box.getY();
                double mWidth = module.box.width;
                double mHeight = module.box.height;
                if (isInSelection(mouseX, mouseY, mX, mY) || isInSelection(mouseX, mouseY, mX + mWidth, mY) || (isInSelection(mouseX, mouseY, mX, mY + mHeight) || isInSelection(mouseX, mouseY, mX + mWidth, mY + mHeight))) {
                    selectedElements.add(module);
                }
            }
        } else if (dragging) {
            for (HudElement element : selectedElements) {
                element.box.addPos(mouseX - lastMouseX, mouseY - lastMouseY);
            }

            double range = hud.snappingRangeSetting.get();
            if (range > 0) {
                double x = Double.MAX_VALUE;
                double y = Double.MAX_VALUE;
                for (HudElement element : selectedElements) {
                    x = Math.min(x, element.box.getX());
                    y = Math.min(y, element.box.getY());
                }

                double width = 0;
                double height = 0;
                for (HudElement element : selectedElements) {
                    width = Math.max(width, element.box.getX() - x + element.box.width);
                    height = Math.max(height, element.box.getY() - y + element.box.height);
                }

                boolean movedX = false;
                boolean movedY = false;
                for (HudElement element : hud.elements) {
                    if (selectedElements.contains(element)) {
                        continue;
                    }

                    double eX = element.box.getX();
                    double eY = element.box.getY();
                    double eWidth = element.box.width;
                    double eHeight = element.box.height;
                    boolean isHorizontallyIn = isPointBetween(x, width, eX) || isPointBetween(x, width, eX + eWidth) || isPointBetween(eX, eWidth, x) || isPointBetween(eX, eWidth, x + width);
                    boolean isVerticallyIn = isPointBetween(y, height, eY) || isPointBetween(y, height, eY + eHeight) || isPointBetween(eY, eHeight, y) || isPointBetween(eY, eHeight, y + height);

                    double moveX = 0;
                    double moveY = 0;
                    if (!movedX && isVerticallyIn) {
                        double x2 = x + width;
                        double eX2 = eX + eWidth;
                        if (Math.abs(eX - x) < range) {
                            moveX = eX - x;
                        } else if (Math.abs(eX2 - x2) <= range) {
                            moveX = eX2 - x2;
                        } else if (Math.abs(eX2 - x) <= range) {
                            moveX = eX2 - x;
                        } else if (Math.abs(eX - x2) <= range) {
                            moveX = eX - x2;
                        }
                    }

                    if (!movedY && isHorizontallyIn) {
                        double y2 = y + height;
                        double eY2 = eY + eHeight;
                        if (Math.abs(eY - y) <= range) {
                            moveY = eY - y;
                        } else if (Math.abs(eY2 - y2) <= range) {
                            moveY = eY2 - y2;
                        } else if (Math.abs(eY2 - y) <= range) {
                            moveY = eY2 - y;
                        } else if (Math.abs(eY - y2) <= range) {
                            moveY = eY - y2;
                        }
                    }

                    if (moveX != 0 || moveY != 0) {
                        for (HudElement element1 : selectedElements) {
                            element1.box.addPos(moveX, moveY);
                        }

                        if (moveX != 0) {
                            movedX = true;
                        }

                        if (moveY != 0) {
                            movedY = true;
                        }
                    }

                    if (movedX && movedY) {
                        break;
                    }
                }

                dragged = true;
            }
        }

        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    private boolean isPointBetween(double start, double size, double point) {
        return point >= start && point <= start + size;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging) {
            dragging = false;

            if (!dragged && !selectedElements.isEmpty()) {
                selectedElements.forEach(HudElement::toggle);
                selectedElements.clear();
            }

            if (selectedElements.size() <= 1) {
                selectedElements.clear();
            }

            return true;
        }

        if (selecting) {
            selecting = false;
            return true;
        }

        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        if (!Utils.canUpdate()) {
            renderBackground(matrixStack);
        }

        double scaleFactor = mc.getWindow().getScaleFactor();
        mouseX *= scaleFactor;
        mouseY *= scaleFactor;

        Utils.unscaledProjection();

        if (!Utils.canUpdate()) {
            hud.render(delta, hudElement -> true);
        }

        Renderer2D colorRenderer = Renderer2D.COLOR;
        colorRenderer.begin();

        for (HudElement element : hud.elements) {
            if (element.enabled) {
                continue;
            }

            renderElement(element, INACTIVE_BG_COLOR, INACTIVE_OL_COLOR);
        }

        for (HudElement element : selectedElements) {
            renderElement(element, HOVER_BG_COLOR, HOVER_OL_COLOR);
        }

        if (!dragging) {
            hoveredModule = null;
            for (HudElement module : hud.elements) {
                if (module.box.isOver(mouseX, mouseY)) {
                    if (!selectedElements.contains(module)) {
                        renderElement(module, HOVER_BG_COLOR, HOVER_OL_COLOR);
                    }

                    hoveredModule = module;

                    break;
                }
            }

            if (selecting) {
                renderQuad(mouseStartX, mouseStartY, mouseX - mouseStartX, mouseY - mouseStartY, HOVER_BG_COLOR, HOVER_OL_COLOR);
            }
        }

        colorRenderer.render(new MatrixStack());
        Utils.scaledProjection();

        runAfterRenderTasks();
    }

    private void renderElement(HudElement module, Color bgColor, Color olColor) {
        renderQuad(module.box.getX(), module.box.getY(), module.box.width, module.box.height, bgColor, olColor);
    }

    private void renderQuad(double x, double y, double w, double h, Color bgColor, Color olColor) {
        Renderer2D colorRenderer = Renderer2D.COLOR;
        colorRenderer.quad(x, y, w, h, bgColor);
        colorRenderer.quad(x - 1, y - 1, w + 2, 1, olColor);
        colorRenderer.quad(x - 1, y + h - 1, w + 2, 1, olColor);
        colorRenderer.quad(x - 1, y, 1, h, olColor);
        colorRenderer.quad(x + w, y, 1, h, olColor);
    }
}