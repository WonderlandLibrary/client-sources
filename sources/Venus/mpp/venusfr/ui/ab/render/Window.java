/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.ui.ab.donate.DonateItems;
import mpp.venusfr.ui.ab.model.ItemStorage;
import mpp.venusfr.ui.ab.render.impl.AddedItemComponent;
import mpp.venusfr.ui.ab.render.impl.AllItemComponent;
import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;
import mpp.venusfr.utils.animations.impl.DecelerateAnimation;
import mpp.venusfr.utils.animations.impl.EaseBackIn;
import mpp.venusfr.utils.client.ClientUtil;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class Window
extends Screen
implements IMinecraft {
    private float x;
    private float y;
    private float width;
    private float height;
    private final Animation hoveredAnimation = new DecelerateAnimation(400, 1.0);
    public static final Animation openAnimation = new EaseBackIn(400, 1.0, 1.0f);
    public final AddedItemComponent addedItemComponents;
    public final AllItemComponent allItemComponent;
    boolean itemMenuOpened;

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (!this.itemMenuOpened) {
            this.addedItemComponents.mouseScrolled(d, d2, d3);
        }
        this.allItemComponent.mouseScrolled(d, d2, d3);
        return super.mouseScrolled(d, d2, d3);
    }

    public Window(ITextComponent iTextComponent, ItemStorage itemStorage) {
        super(iTextComponent);
        DonateItems.add();
        this.addedItemComponents = new AddedItemComponent();
        this.allItemComponent = new AllItemComponent(this.addedItemComponents);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (openAnimation.getOutput() == 0.0 && openAnimation.isDone()) {
            this.minecraft.displayGuiScreen(null);
        }
        int n3 = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int n4 = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        this.width = 400.0f;
        this.height = 300.0f;
        this.x = (float)n3 / 2.0f - this.width / 2.0f;
        this.y = (float)n4 / 2.0f - this.height / 2.0f;
        float f2 = 10.0f;
        GlStateManager.pushMatrix();
        Window.sizeAnimation(this.x + this.width / 2.0f, this.y + this.height / 2.0f, openAnimation.getOutput());
        DisplayUtils.drawShadow(this.x, this.y, this.width, this.height, 10, ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 7.0f, ColorUtils.rgba(17, 17, 17, 255));
        float f3 = this.x + f2;
        float f4 = this.y + this.height - f2 * 6.0f;
        float f5 = this.width - f2 * 2.0f;
        float f6 = 50.0f;
        this.renderAddingPanel(matrixStack, f3, f4, f5, f6, n, n2);
        this.allItemComponent.setX(this.x + f2);
        this.allItemComponent.setY(this.y + f2);
        this.allItemComponent.setWidth(this.width);
        this.allItemComponent.setHeight(this.height);
        if (this.itemMenuOpened) {
            this.allItemComponent.render(matrixStack, n, n2);
        } else {
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y, this.width, this.height);
            this.addedItemComponents.x = this.x + f2;
            this.addedItemComponents.y = this.y + f2;
            this.addedItemComponents.width = this.width;
            this.addedItemComponents.height = this.height;
            this.addedItemComponents.render(matrixStack, n, n2);
            Scissor.unset();
            Scissor.pop();
        }
        GlStateManager.popMatrix();
        if (this.allItemComponent.component != null) {
            if (this.itemMenuOpened && openAnimation.getOutput() >= 1.0) {
                this.allItemComponent.component.setX(this.x - 150.0f);
                this.allItemComponent.component.setY(this.y);
                this.allItemComponent.component.render(matrixStack, n, n2);
            } else {
                this.allItemComponent.component = null;
            }
        }
        super.render(matrixStack, n, n2, f);
    }

    @Override
    public void init(Minecraft minecraft, int n, int n2) {
        openAnimation.setDirection(Direction.FORWARDS);
        super.init(minecraft, n, n2);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        this.addedItemComponents.keyTyped(n, n2, n3);
        if (this.itemMenuOpened) {
            this.allItemComponent.keyTyped(n, n2, n3);
        }
        if (n == 256) {
            openAnimation.setDirection(Direction.BACKWARDS);
            return true;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (this.itemMenuOpened) {
            this.allItemComponent.mouseClicked(d, d2, n);
        } else {
            this.addedItemComponents.mouseClicked(d, d2, n);
        }
        float f = 10.0f;
        float f2 = this.x + f;
        float f3 = this.y + this.height - f * 6.0f;
        float f4 = this.width - f * 2.0f;
        float f5 = 50.0f;
        if (MathUtil.isHovered((float)d, (float)d2, f2, f3, f4, f5) && n == 0) {
            this.itemMenuOpened = !this.itemMenuOpened;
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        this.addedItemComponents.mouseReleased(d, d2, n);
        this.allItemComponent.mouseReleased(d, d2, n);
        return super.mouseReleased(d, d2, n);
    }

    @Override
    public boolean charTyped(char c, int n) {
        this.addedItemComponents.charTyped(c, n);
        if (this.itemMenuOpened) {
            this.allItemComponent.charTyped(c, n);
        }
        return super.charTyped(c, n);
    }

    private void renderAddingPanel(MatrixStack matrixStack, float f, float f2, float f3, float f4, int n, int n2) {
        boolean bl = MathUtil.isHovered(n, n2, f, f2, f3, f4);
        this.hoveredAnimation.setDirection(bl ? Direction.FORWARDS : Direction.BACKWARDS);
        int n3 = (int)(5.0 * this.hoveredAnimation.getOutput());
        DisplayUtils.drawShadow(f, f2, f3, f4, 16, ColorUtils.rgba(28, 28, 28, (int)(255.0 * this.hoveredAnimation.getOutput())));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, 4.0f, ColorUtils.rgba(23 + n3, 23 + n3, 23 + n3, 255));
        float f5 = Fonts.montserrat.getHeight(12.0f);
        Fonts.montserrat.drawCenteredText(matrixStack, this.itemMenuOpened ? "\u0417\u0430\u043a\u0440\u044b\u0442\u044c" : "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043f\u0440\u0435\u0434\u043c\u0435\u0442", f + f3 / 2.0f, f2 + f4 / 2.0f - f5 / 2.0f, ColorUtils.rgba(255, 255, 255, 128), 12.0f);
    }

    public static void sizeAnimation(double d, double d2, double d3) {
        GlStateManager.translated(d, d2, 0.0);
        GlStateManager.scaled(d3, d3, d3);
        GlStateManager.translated(-d, -d2, 0.0);
    }
}

