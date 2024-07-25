package club.bluezenith.ui.button;

import club.bluezenith.ui.button.elements.CollidableTriangle;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.function.Consumer;

import static club.bluezenith.util.render.RenderUtil.animate;
import static net.minecraft.util.MathHelper.abs;

public class ScrollingSelector extends GuiButton {
    private long timeSinceCreated = System.currentTimeMillis();
    private Consumer<Integer> valueChangeConsumer;
    private CollidableTriangle triangleDown;
    private CollidableTriangle triangleUp;
    private String[] values;
    private int currentIndex, prevIndex;
    private float progress, targetProgress;
    private boolean progressDone, consumerCalled, firstCall;

    public ScrollingSelector(int buttonId, int x, int y, String buttonText, String... values) {
        super(buttonId, x, y, buttonText);
        this.values = values;
    }

    public ScrollingSelector(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String... values) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.values = values;
    }

    public ScrollingSelector setIndexConsumer(Consumer<Integer> consumer) {
        this.valueChangeConsumer = consumer;
        return this;
    }

    public ScrollingSelector setValue(String value) {
        for (int i = 0; i < this.values.length; i++) {
            if (values[i].equals(value)) {
                if (i > currentIndex) {
                    targetProgress = progress - 19 * (i - currentIndex);
                } else {
                    targetProgress = progress + 19 * (currentIndex - i);
                }
                currentIndex = i;
                prevIndex = i;
                break;
            }
        }
        return this;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        /*if(triangleDown == null) {
            triangleDown = new CollidableTriangle(1, false,
                    getEquilateralPoints(xPosition, yPosition + (height/2D + 7) + 2, 20, 12));
        }
        if(triangleUp == null) {
            triangleUp = new CollidableTriangle(1, false,
                    getEquilateralPoints(xPosition, yPosition - (height/2D - 7) - 2, 20, -12));
        }*/
        GL11.glLineWidth(1.5F);
        this.determineFontRenderer();
     /*   triangleDown.draw(mouseX, mouseY);
        triangleUp.draw(mouseX, mouseY);*/

        progressDone = true;
        scrollHandling:
        {
            if(System.currentTimeMillis() - timeSinceCreated < 1000) break scrollHandling;
            if(!club.bluezenith.ui.clickgui.ClickGui.i(mouseX, mouseY, xPosition - 60, yPosition - 2.5F, xPosition + 89, yPosition + 15 + 2.5F)) break scrollHandling;
            if (Mouse.hasWheel()) {
                int wheel = Mouse.getDWheel();
                if (wheel != 0) {
                    if(!progressDone) break scrollHandling;

                    int index = prevIndex;
                    if(wheel > 0) {
                        index++;
                    }
                    else index--;

                    if(index >= values.length || index < 0) {
                        break scrollHandling;
                    }
                    currentIndex = index;

                    final int increment = 19;
                    if(currentIndex > prevIndex) targetProgress = targetProgress - increment;
                    else if(currentIndex < prevIndex) targetProgress = targetProgress + increment;
                    consumerCalled = false;

                }
            }
        }

        progress = animate(targetProgress, progress, 0.13F);

        Color c = new Color(0, 0, 8 / 255f, 110 / 255f);
        Color c1 = new Color(0, 0, 16 / 255f, 110 / 255f);

        float y = 0, y2 = 0;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.crop(xPosition - 60, yPosition - 2.5F, xPosition + 89, yPosition + 15 + 2.5F);
     //  RenderUtil.hollowRect(xPosition - 60, yPosition - 2.5F, xPosition + 89, yPosition + 14 + 2.5F, 1, -1);
        this.drawGradientRect(xPosition - 60, y = yPosition - 2.5F, xPosition + 89, y2 = yPosition + 15 + 2.5F, c.getRGB(), c1.getRGB());
      //  RenderUtil.rect(xPosition - 60, yPosition - 2.5F, xPosition + 89, yPosition + 14 + 2.5F, new Color(0, 0, 0, 150));

        if(progressDone) {
            if(!consumerCalled && valueChangeConsumer != null) {
                consumerCalled = true;
                if(!firstCall) {
                    firstCall = true;
                    return;
                }
                valueChangeConsumer.accept(currentIndex);
            }
            prevIndex = currentIndex;
        }

        GlStateManager.translate(0, progress, 0);
        float position = yPosition + 2;
        for (String value : this.values) {
            this.fontRenderer.drawString(value, xPosition + 30/2F - this.fontRenderer.getStringWidthF(value)/2F + 0.01F, position, -1);
            position += 19;
        }
        GlStateManager.translate(0, -progress, 0);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        fontRenderer.drawString(displayString, xPosition + 17/2F - fontRenderer.getStringWidthF(displayString)/2F + 0.01F, yPosition - 30, new Color(200, 200, 200).getRGB());
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public String getCurrentValue() {
        return values[currentIndex];
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if(!progressDone || true) return false;
        if(triangleUp.isMouseOver()) {
            int index = prevIndex;
            index++;

            if(index >= values.length || index < 0) {
                return true;
            }
            currentIndex = index;

            final int increment = 19;
            if(currentIndex > prevIndex) targetProgress = progress - increment;
            else if(currentIndex < prevIndex) targetProgress = progress + increment;
        } else if(triangleDown.isMouseOver()) {
            int index = prevIndex;
            index--;

            if(index >= values.length || index < 0) {
                return true;
            }
            currentIndex = index;

            final int increment = 19;
            if(currentIndex > prevIndex) targetProgress = progress - increment;
            else if(currentIndex < prevIndex) targetProgress = progress + increment;
        }

        return false;
    }
}
