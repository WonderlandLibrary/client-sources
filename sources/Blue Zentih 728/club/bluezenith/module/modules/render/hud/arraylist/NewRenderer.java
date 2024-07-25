package club.bluezenith.module.modules.render.hud.arraylist;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.util.render.RenderUtil.end2D;
import static club.bluezenith.util.render.RenderUtil.start2D;
import static java.lang.Float.compare;
import static java.lang.Math.max;
import static org.lwjgl.opengl.GL11.*;

public class NewRenderer implements Draggable {
    private float hoverX, x = Float.MIN_VALUE, y = Float.MIN_VALUE, x2, y2;

    private final List<Module> drawnModules = new ArrayList<>(),
                               allModules = new ArrayList<>();

    private Alignment alignment = Alignment.TOP_RIGHT;
    private FontRenderer fontRenderer;
    private HUD hud;

    private ScaledResolution sc;

    private float relativeLeft, relativeRight;
    private double lastScaledWidth;
    private boolean isSlaying;

    //todo dragging and support for other alignments
    public float render(FontRenderer fontRenderer, ScaledResolution sc, HUD hud) {
        this.hud = hud;
        this.fontRenderer = fontRenderer;
        this.sc = sc;
        return 0;
    }

    @Override
    public void draw(Render2DEvent event) {
        float height;

                    //screen width
        final float sWidth = (float) sc.getScaledWidth_double(),
                    width = sWidth / 2F;

        //todo save relative positions so after restart the position is fine
        if(lastScaledWidth != sc.getScaledWidth_double()) {
            this.x = (alignment.isRight() ? sWidth - relativeRight : relativeLeft);
            lastScaledWidth = sc.getScaledWidth_double();
        }

        if(x >= width) {
            if(y >= this.y2)
                alignment = Alignment.BOTTOM_RIGHT;
            else alignment = Alignment.TOP_RIGHT;
        }
        else {
            if(y >= this.y2)
               alignment = Alignment.BOTTOM_LEFT;
            else alignment = Alignment.TOP_LEFT;
        }

        if(allModules.size() == 0) {
            allModules.addAll(BlueZenith.getBlueZenith().getModuleManager().getModules());
            drawnModules.addAll(allModules);
        }

        final boolean leftToRight = alignment.isLeft();

        final boolean reset = this.x == Float.MIN_VALUE || this.y == Float.MIN_VALUE;

        float   startX = reset ? this.x = (sWidth - hud.marginX.get()) : this.x - hud.marginX.get(),
                startY = reset ? this.y = hud.marginY.get() : this.y + hud.marginY.get();

        final float extraRectWidth = hud.extraRectWidth.get(),
                outlineWidth = hud.outlineWidth.get();

        height = startY;

        if(height < outlineWidth && shouldOutline("Top"))
            height = outlineWidth;

        drawnModules.clear();

        for (Module module : allModules) {
            if(module.getState() && !module.hidden || module.arrayListProgress > 0F)
                drawnModules.add(module);
        }

        final boolean doGradient = shouldOutline("Bar") && !hud.colorMode.is("Type");

        //vertex data (x, y coords) for outline, it's drawn after the modules, so I can achieve a gradient effect
        final float[][] outlineRight = doGradient ? new float[drawnModules.size()][2] : null;
        //color data for that outline
        final float[][] outlineRightColors = doGradient ? new float[drawnModules.size()][4] : null;

        //last module height for that outline (kind of bypass value)
        float lastHeight = 0;

        for (int i = 0; i < drawnModules.size(); i++) {
            Module module = drawnModules.get(i);
            final String displayString = hud.getDisplayString(module);

            if(hud.arrayListAnimationMode.is("Smooth") || (isSlaying = hud.arrayListAnimationMode.is("Slay"))) {
                module.arrayListProgress = RenderUtil.animate(
                        module.getState() && !module.hidden ? 1 : -0.1F,
                        module.arrayListProgress,
                        module.getState() && !module.hidden ? isSlaying ? 0.15F : 0.09F : isSlaying ? 0.09F : 0.05F
                );
            } else if(hud.arrayListAnimationMode.is("Linear")) {
                final int direction = module.getState() && !module.hidden ? 1 : -1;
                module.arrayListProgress = MathHelper.clamp(module.arrayListProgress + (0.003F * RenderUtil.delta) * direction, -.1F, 1F);
            } else module.arrayListProgress = module.getState() && !module.hidden ? 1 : -0.1F;

            final int color = BlueZenith.isVirtueTheme ? hud.getVirtueColor(module).getRGB() : hud.colorMode.is("Type") ? hud.getTypeColor(module).getRGB(): hud.getColor(i);

            float rectX1, rectY1, rectX2, rectY2;

            if(leftToRight) {
                RenderUtil.rect(
                        rectX1 = startX - extraRectWidth,
                        rectY1 = height,
                        rectX2 = startX + extraRectWidth + fontRenderer.getStringWidthF(displayString),
                        rectY2 = height + (fontRenderer.FONT_HEIGHT + hud.height.get()) * module.arrayListProgress,
                        ColorUtil.get(hud.backgroundBrightness.get(), hud.backgroundBrightness.get(), hud.backgroundBrightness.get(), hud.backgroundOpacity.get())
                );
            } else {
                RenderUtil.rect(
                        rectX1 = startX - fontRenderer.getStringWidthF(displayString) - extraRectWidth,
                        rectY1 = height,
                        rectX2 = startX + extraRectWidth,
                        rectY2 = height + (fontRenderer.FONT_HEIGHT + hud.height.get()) * module.arrayListProgress,
                        ColorUtil.get(hud.backgroundBrightness.get(), hud.backgroundBrightness.get(), hud.backgroundBrightness.get(), hud.backgroundOpacity.get())
                );
            }

            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            if(leftToRight) {
                RenderUtil.crop(rectX1 - (shouldOutline("Bar") ? outlineWidth : 0), this.y - outlineWidth, rectX2 + .5F + outlineWidth, rectY2 + outlineWidth + .5F);
            } else {
                RenderUtil.crop(rectX1 - outlineWidth - extraRectWidth, this.y - outlineWidth, rectX2 + .5F + (shouldOutline("Bar") ? outlineWidth : 0), rectY2 + 20);
            }

            float stringX;

            if(leftToRight) {
                stringX = -hud.textX.get() + (startX - (fontRenderer.getStringWidthF(displayString) * (1 - module.arrayListProgress)));
            } else {
                stringX = hud.textX.get() + startX - fontRenderer.getStringWidthF(displayString) * module.arrayListProgress;
            }

            if(i == 0) {
                if (!leftToRight) {
                    this.hoverX = stringX;
                } else this.hoverX = stringX - extraRectWidth - outlineWidth;
            }

            float stringY;

            if(hud.arrayListAnimationMode.is("Slay")) {
                stringY = (hud.textY.get() + rectY1 + ((rectY2 - rectY1) / 2F) - (fontRenderer.FONT_HEIGHT / 2F)) - (fontRenderer.FONT_HEIGHT * 1.5F) * (1 - module.arrayListProgress);
            } else {
                stringY = (hud.textY.get() + rectY1 + ((rectY2 - rectY1) / 2F) - (fontRenderer.FONT_HEIGHT / 2F) * module.arrayListProgress);
            }

            if(module.getState() || !hud.arrayListAnimationMode.is("Slay")) {
                fontRenderer.drawString(
                        displayString,
                        stringX,
                        stringY,
                        color,
                        true
                );
            }

            boolean hasNext = i != drawnModules.size() - 1;

            if(i == 0 && shouldOutline("Top")) {
                topAnimation(module);

                final float x1 = rectX1 - (shouldOutline("Bar") && leftToRight || shouldOutline("Split") && !leftToRight ? outlineWidth : 0);
                final float extra = shouldOutline("Split") && leftToRight ? outlineWidth : 0;
                final float lineWidth = ((rectX2 + extra) - x1);
                final float x2 = x1 + lineWidth;

                if(leftToRight) {
                    RenderUtil.rect(x2 - (x2 - x1), rectY1 - outlineWidth, x1 + ((x2 - x1) + (shouldOutline("Split") && false ? outlineWidth : 0)) * module.lineProgress, rectY1, color);
                } else {
                    RenderUtil.rect(x2 - (x2 - x1) * module.lineProgress, rectY1 - outlineWidth, x2 + (shouldOutline("Bar") ? outlineWidth : -outlineWidth), rectY1, color);
                }
            } else if(i != 0) {
                resetTopAnimation(module);
            }

            if(i == 0 && leftToRight) {
                this.x2 = rectX2;
            }

            if(doGradient) {
                final float[] outlineData = outlineRight[i];
                outlineData[0] = leftToRight ? rectX1 - (shouldOutline("Top") ? outlineWidth : 0): rectX2;

                final float widthMultiplier = shouldOutline("Bottom") ? 0.5F : 0.7F;

                outlineData[1] = rectY1 - ((shouldOutline("Top") || !hasNext) && (!leftToRight || i != drawnModules.size() - 1) ? outlineWidth * widthMultiplier : 0);

                outlineRightColors[i] = ColorUtil.get(color);
            } else if(shouldOutline("Bar") && hud.colorMode.is("Type"))
                RenderUtil.rect(rectX2, rectY1 - outlineWidth, rectX2 + outlineWidth, rectY2, color);

            if(shouldOutline("Split")) {
                final boolean connect = shouldOutline("Connect split") || shouldOutline("Bottom") && !hasNext;

                if(leftToRight) {
                    RenderUtil.rect(rectX2, rectY1, rectX2 + outlineWidth, rectY2 + (connect ? outlineWidth : 0), color);
                } else {
                    RenderUtil.rect(rectX1 - outlineWidth, rectY1, rectX1, rectY2 + (connect ? outlineWidth : 0), color);
                }
            }

            if(!hasNext && shouldOutline("Bottom")) {
                if(leftToRight) {
                    RenderUtil.rect(rectX1 + (shouldOutline("Bar") ? -outlineWidth : outlineWidth), rectY2, rectX2 - (shouldOutline("Split") ? -outlineWidth : outlineWidth), rectY2 + outlineWidth, color);
                } else {
                    RenderUtil.rect(rectX1 + (shouldOutline("Split") ? 0 : outlineWidth), rectY2, rectX2 - (shouldOutline("Bar") ? -outlineWidth : outlineWidth), rectY2 + outlineWidth, color);
                }
                this.y2 = rectY2 + outlineWidth;
            }

            else if(hasNext && shouldOutline("Connect split") && shouldOutline("Split")) {
                final Module nextModule = drawnModules.get(i + 1);

                if(leftToRight) {
                    final float nextX = rectX1 + fontRenderer.getStringWidthF(hud.getDisplayString(nextModule));

                    RenderUtil.rect(rectX2 - (rectX2 - nextX - extraRectWidth - outlineWidth - outlineWidth), rectY2, rectX2, rectY2 + outlineWidth, color);
                } else {
                    final float nextX1 = startX - (fontRenderer.getStringWidthF(hud.getDisplayString(nextModule)));

                    RenderUtil.rect(rectX1 - outlineWidth, rectY2, nextX1 - outlineWidth - extraRectWidth, rectY2 + outlineWidth, color);
                }
            }

            height += lastHeight = (fontRenderer.FONT_HEIGHT + hud.height.get()) * module.arrayListProgress;
            GL11.glDisable(GL11.GL_SCISSOR_TEST);

            if(!leftToRight)
              this.x2 = rectX2;
        }

        if(doGradient) {
            drawRightOutline(outlineRight, outlineRightColors, lastHeight);
        }

        this.y2 = height;

        if(leftToRight) {
            relativeLeft = this.x;
        } else relativeRight = sWidth - this.x;
    }

    private void resetTopAnimation(Module module) {
        if(hud.arrayListAnimationMode.is("Smooth") || hud.arrayListAnimationMode.is("Slay")) {
            module.lineProgress = RenderUtil.animate(
                    0,
                    module.lineProgress,
                    0.05F
            );
        } else if(hud.arrayListAnimationMode.is("Linear")) {
            module.lineProgress = MathHelper.clamp(module.lineProgress + (0.003F * RenderUtil.delta) * -1, -.1F, 1F);
        } else {
            module.lineProgress = 0;
        }
    }

    private void topAnimation(Module module) {
        if(hud.arrayListAnimationMode.is("Smooth") || hud.arrayListAnimationMode.is("Slay")) {
            module.lineProgress = RenderUtil.animate(
                    module.getState() && !module.hidden ? 1 : 0,
                    module.lineProgress,
                    module.getState() && !module.hidden ? 0.07F : 0.05F
            );
        } else if(hud.arrayListAnimationMode.is("Linear"))  {
            final int direction = module.getState() && !module.hidden ? 1 : -1;
            module.lineProgress = MathHelper.clamp(module.lineProgress + (0.003F * RenderUtil.delta) * direction, -.1F, 1F);
        } else module.lineProgress = module.getState() && !module.hidden ? 1 : 0;
    }

    private void drawRightOutline(float[][] _outlineData, float[][] _outlineColors, float lastHeight) {
        final float previousLineWidth = glGetFloat(GL_LINE_WIDTH);
        final float outlineWidth = hud.outlineWidth.get() / 2F; //bypass value
        final int previousShadeModel = glGetInteger(GL_SHADE_MODEL);

        glLineWidth(hud.outlineWidth.get() * 2);
        glShadeModel(GL_SMOOTH);
        start2D(GL_LINE_STRIP);

        for (int i = 0; i < _outlineData.length; i++) {
            final float[] outlineData = _outlineData[i],
                          outlineColors = _outlineColors[max(i - 1, 0)];
            glColor4f(outlineColors[0], outlineColors[1], outlineColors[2], outlineColors[3]);
            glVertex2d(outlineData[0] + outlineWidth, outlineData[1]);
        }

        //drawing the outline for the last module (it's not included in _outlineData for some reason I don't want to figure out)
        final float[] lastOutlineData = _outlineData[max(_outlineData.length - 1, 0)];
        glVertex2d(lastOutlineData[0] + outlineWidth, lastOutlineData[1] + lastHeight + outlineWidth);

        end2D();
        glLineWidth(previousLineWidth);
        glShadeModel(previousShadeModel);
    }

    private boolean shouldOutline(String optionName) {
        return hud.outlineOptions.getOptionState(optionName);
    }

    public void onTick() {
        allModules.sort((first, second) -> compare(
                -fontRenderer.getStringWidthF(hud.getDisplayString(first)),
                -fontRenderer.getStringWidthF(hud.getDisplayString(second))
        ));
    }

    @Override
    public boolean shouldBeRendered() {
        return hud != null && hud.getState();
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return checkMouseBounds(mouseX, mouseY, hoverX, y, x2, y2);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public String getIdentifier() {
        return "array the list";
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void resetPosition() {
        this.x = this.y = Float.MIN_VALUE;
    }

    @Override
    public int hashCode() {
        return "arraylist".hashCode();
    }
}
