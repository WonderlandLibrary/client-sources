/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.ui.ab.donate.DonateItems;
import fun.ellant.ui.ab.model.ItemStorage;
import fun.ellant.ui.ab.render.impl.AddedItemComponent;
import fun.ellant.ui.ab.render.impl.AllItemComponent;
import fun.ellant.utils.animations.Animation;
import fun.ellant.utils.animations.Direction;
import fun.ellant.utils.animations.impl.DecelerateAnimation;
import fun.ellant.utils.animations.impl.EaseBackIn;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import fun.ellant.utils.render.font.Fonts;
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
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (!this.itemMenuOpened) {
            this.addedItemComponents.mouseScrolled(mouseX, mouseY, delta);
        }
        this.allItemComponent.mouseScrolled(mouseX, mouseY, delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public Window(ITextComponent titleIn, ItemStorage itemStorage) {
        super(titleIn);
        DonateItems.add();
        this.addedItemComponents = new AddedItemComponent();
        this.allItemComponent = new AllItemComponent(this.addedItemComponents);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (openAnimation.getOutput() == 0.0 && openAnimation.isDone()) {
            this.minecraft.displayGuiScreen(null);
        }
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        this.width = 400.0f;
        this.height = 300.0f;
        this.x = (float)windowWidth / 2.0f - this.width / 2.0f;
        this.y = (float)windowHeight / 2.0f - this.height / 2.0f;
        float padding = 10.0f;
        GlStateManager.pushMatrix();
        Window.sizeAnimation(this.x + this.width / 2.0f, this.y + this.height / 2.0f, openAnimation.getOutput());
        DisplayUtils.drawShadow(this.x, this.y, this.width, this.height, 10, ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(this.x, this.y, this.width, this.height, 7.0f, ColorUtils.rgba(17, 17, 17, 205));
        float addingPanelX = this.x + padding;
        float addingPanelY = this.y + this.height - padding * 6.0f;
        float addingPanelWidth = this.width - padding * 2.0f;
        float addingPanelHeight = 50.0f;
        this.renderAddingPanel(matrixStack, addingPanelX, addingPanelY, addingPanelWidth, addingPanelHeight, mouseX, mouseY);
        this.allItemComponent.setX(this.x + padding);
        this.allItemComponent.setY(this.y + padding);
        this.allItemComponent.setWidth(this.width);
        this.allItemComponent.setHeight(this.height);
        if (this.itemMenuOpened) {
            this.allItemComponent.render(matrixStack, mouseX, mouseY);
        } else {
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y, this.width, this.height);
            this.addedItemComponents.x = this.x + padding;
            this.addedItemComponents.y = this.y + padding;
            this.addedItemComponents.width = this.width;
            this.addedItemComponents.height = this.height;
            this.addedItemComponents.render(matrixStack, mouseX, mouseY);
            Scissor.unset();
            Scissor.pop();
        }
        GlStateManager.popMatrix();
        if (this.allItemComponent.component != null) {
            if (this.itemMenuOpened && openAnimation.getOutput() >= 1.0) {
                this.allItemComponent.component.setX(this.x - 150.0f);
                this.allItemComponent.component.setY(this.y);
                this.allItemComponent.component.render(matrixStack, mouseX, mouseY);
            } else {
                this.allItemComponent.component = null;
            }
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        openAnimation.setDirection(Direction.FORWARDS);
        super.init(minecraft, width, height);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.addedItemComponents.keyTyped(keyCode, scanCode, modifiers);
        if (this.itemMenuOpened) {
            this.allItemComponent.keyTyped(keyCode, scanCode, modifiers);
        }
        if (keyCode == 256) {
            openAnimation.setDirection(Direction.BACKWARDS);
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.itemMenuOpened) {
            this.allItemComponent.mouseClicked(mouseX, mouseY, button);
        } else {
            this.addedItemComponents.mouseClicked(mouseX, mouseY, button);
        }
        float padding = 10.0f;
        float addingPanelX = this.x + padding;
        float addingPanelY = this.y + this.height - padding * 6.0f;
        float addingPanelWidth = this.width - padding * 2.0f;
        float addingPanelHeight = 50.0f;
        if (MathUtil.isHovered((float)mouseX, (float)mouseY, addingPanelX, addingPanelY, addingPanelWidth, addingPanelHeight) && button == 0) {
            this.itemMenuOpened = !this.itemMenuOpened;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.addedItemComponents.mouseReleased(mouseX, mouseY, button);
        this.allItemComponent.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        this.addedItemComponents.charTyped(codePoint, modifiers);
        if (this.itemMenuOpened) {
            this.allItemComponent.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    private void renderAddingPanel(MatrixStack matrixStack, float x, float y, float width, float height, int mouseX, int mouseY) {
        boolean hovered = MathUtil.isHovered(mouseX, mouseY, x, y, width, height);
        this.hoveredAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        int fade = (int)(5.0 * this.hoveredAnimation.getOutput());
        DisplayUtils.drawShadow(x, y, width, height, 16, ColorUtils.rgba(28, 28, 28, (int)(178 * this.hoveredAnimation.getOutput())));
        DisplayUtils.drawRoundedRect(x, y, width, height, 4.0f, ColorUtils.rgba(23 + fade, 23 + fade, 23 + fade, 205));
        float fontHeight = Fonts.montserrat.getHeight(12.0f);
        Fonts.montserrat.drawCenteredText(matrixStack, this.itemMenuOpened ? "Закрыть" : "Добавить предмет", x + width / 2.0f, y + height / 2.0f - fontHeight / 2.0f, ColorUtils.rgba(255, 255, 255, 255), 12.0f);
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0.0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0.0);
    }
}

