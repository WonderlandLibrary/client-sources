package im.expensive.ui.ab.render;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Expensive;
import im.expensive.ui.ab.donate.DonateItems;
import im.expensive.ui.ab.factory.ItemFactory;
import im.expensive.ui.ab.factory.ItemFactoryImpl;
import im.expensive.ui.ab.model.IItem;
import im.expensive.ui.ab.model.ItemStorage;
import im.expensive.ui.ab.render.impl.AddedItemComponent;
import im.expensive.ui.ab.render.impl.AllItemComponent;
import im.expensive.ui.dropdown.Panel;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.Direction;
import im.expensive.utils.animations.impl.DecelerateAnimation;
import im.expensive.utils.animations.impl.EaseBackIn;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.nbt.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.optifine.render.RenderUtils;
import org.lwjgl.glfw.GLFW;
import ru.hogoshi.util.Easings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Window extends Screen implements IMinecraft {
    private float x, y, width, height;
    private final Animation hoveredAnimation = new DecelerateAnimation(400, 1);

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (!itemMenuOpened) {

            addedItemComponents.mouseScrolled(mouseX, mouseY, delta);

        }
        allItemComponent.mouseScrolled(mouseX, mouseY, delta);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public static final Animation openAnimation = new EaseBackIn(400, 1, 1);

    public final AddedItemComponent addedItemComponents;
    public final AllItemComponent allItemComponent;

    public Window(ITextComponent titleIn, ItemStorage itemStorage) {
        super(titleIn);
        DonateItems.add();
        addedItemComponents = new AddedItemComponent();
        allItemComponent = new AllItemComponent(addedItemComponents);
    }

    boolean itemMenuOpened;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (openAnimation.getOutput() == 0.0f && openAnimation.isDone()) {
            minecraft.displayGuiScreen(null);
        }
        int windowWidth = ClientUtil.calc(mc.getMainWindow().getScaledWidth());
        int windowHeight = ClientUtil.calc(mc.getMainWindow().getScaledHeight());
        this.width = 400;
        this.height = 300;
        this.x = ((float) windowWidth / 2) - (width / 2);
        this.y = ((float) windowHeight / 2) - (height / 2);
        float padding = 10;
        GlStateManager.pushMatrix();
        sizeAnimation(x + (width / 2), y + (height / 2), openAnimation.getOutput());
        // background
        DisplayUtils.drawShadow(x, y, width, height, 10, ColorUtils.rgba(17, 17, 17, 128));
        DisplayUtils.drawRoundedRect(x, y, width, height, 7, ColorUtils.rgba(17, 17, 17, 255));

        float addingPanelX = x + padding;
        float addingPanelY = y + height - (padding * 6);
        float addingPanelWidth = width - (padding * 2);
        float addingPanelHeight = 50;
        renderAddingPanel(matrixStack, addingPanelX, addingPanelY, addingPanelWidth, addingPanelHeight, mouseX, mouseY);

        allItemComponent.setX(x + padding);
        allItemComponent.setY(y + padding);
        allItemComponent.setWidth(width);
        allItemComponent.setHeight(height);
        if (itemMenuOpened) {
            allItemComponent.render(matrixStack, mouseX, mouseY);
        } else {
            Scissor.push();
            Scissor.setFromComponentCoordinates(x, y, width, height);

            addedItemComponents.x = x + padding;
            addedItemComponents.y = y + padding;
            addedItemComponents.width = width;
            addedItemComponents.height = height;
            addedItemComponents.render(matrixStack, mouseX, mouseY);

            Scissor.unset();
            Scissor.pop();
        }

        GlStateManager.popMatrix();

        if (allItemComponent.component != null) {


            if (itemMenuOpened && openAnimation.getOutput() >= 1) {

                allItemComponent.component.setX(x - 150);
                allItemComponent.component.setY(y);
                allItemComponent.component.render(matrixStack, mouseX, mouseY);
            } else {
                allItemComponent.component = null;
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

        addedItemComponents.keyTyped(keyCode, scanCode, modifiers);

        if (itemMenuOpened)
            allItemComponent.keyTyped(keyCode, scanCode, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            openAnimation.setDirection(Direction.BACKWARDS);
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {


        if (itemMenuOpened)
            allItemComponent.mouseClicked(mouseX, mouseY, button);
        else
            addedItemComponents.mouseClicked(mouseX, mouseY, button);

        float padding = 10;

        float addingPanelX = x + padding;
        float addingPanelY = y + height - (padding * 6);
        float addingPanelWidth = width - (padding * 2);
        float addingPanelHeight = 50;

        if (MathUtil.isHovered((float) mouseX, (float) mouseY, addingPanelX, addingPanelY, addingPanelWidth, addingPanelHeight) && button == 0) {
            itemMenuOpened = !itemMenuOpened;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        addedItemComponents.mouseReleased(mouseX, mouseY, button);


        allItemComponent.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {

        addedItemComponents.charTyped(codePoint, modifiers);

        if (itemMenuOpened)
            allItemComponent.charTyped(codePoint, modifiers);
        return super.charTyped(codePoint, modifiers);
    }

    private void renderAddingPanel(MatrixStack matrixStack, float x, float y, float width, float height, int mouseX, int mouseY) {
        boolean hovered = MathUtil.isHovered(mouseX, mouseY, x, y, width, height);

        hoveredAnimation.setDirection(hovered ? Direction.FORWARDS : Direction.BACKWARDS);

        int fade = (int) (5 * hoveredAnimation.getOutput());

        DisplayUtils.drawShadow(x, y, width, height, 16, ColorUtils.rgba(28, 28, 28, (int) (255 * hoveredAnimation.getOutput())));
        DisplayUtils.drawRoundedRect(x, y, width, height, 4,
                ColorUtils.rgba(23 + fade, 23 + fade, 23 + fade, 255));

        float fontHeight = Fonts.montserrat.getHeight(12);
        Fonts.montserrat.drawCenteredText(matrixStack, itemMenuOpened ? "Закрыть" : "Добавить предмет", x + (width / 2),
                y + (height / 2) - (fontHeight / 2), ColorUtils.rgba(255, 255, 255, 128), 12);
    }

    public static void sizeAnimation(double width, double height, double scale) {
        GlStateManager.translated(width, height, 0);
        GlStateManager.scaled(scale, scale, scale);
        GlStateManager.translated(-width, -height, 0);
    }
}
