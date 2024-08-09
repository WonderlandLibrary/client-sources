package im.expensive.ui.ab.render.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Expensive;
import im.expensive.ui.ab.donate.DonateItems;
import im.expensive.ui.ab.model.IItem;
import im.expensive.ui.ab.render.Window;
import im.expensive.ui.ab.render.impl.Component;
import im.expensive.ui.ab.render.impl.item.EditComponent;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.Scissor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.item.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.List;


public class AllItemComponent extends Component {
    @Setter
    @Getter
    float x, y, width, height, scroll;

    public EditComponent component;
    AddedItemComponent addedItemComponents;

    public AllItemComponent(AddedItemComponent addedItemComponents) {
        this.addedItemComponents = addedItemComponents;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY) {
        scroll = MathHelper.clamp(scroll, -(Registry.ITEM.stream().count() + DonateItems.donitem.size()) + height - 75, 0);

        float currentX = 0;
        float currentY = scroll;
        Scissor.push();
        Scissor.setFromComponentCoordinates(x, y, width - 10, height - 75, (float) Window.openAnimation.getOutput());

        for (Item item : Registry.ITEM) {
            if (item instanceof AirItem) continue;
            if (MathUtil.isHovered(x + 2 + currentX, y + currentY, x, y - 20, width, height + 20)) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item.getDefaultInstance(), (int) (x + 2 + currentX), (int) (y + currentY));
            }
            currentX += 18;
            if (x + currentX >= x + width - 25) {
                currentX = 0;
                currentY += 20;
            }
        }

        for (ItemStack item : DonateItems.donitem) {
            if (MathUtil.isHovered(x + 2 + currentX, y + currentY, x, y - 20, width, height + 20)) {
                Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item, (int) (x + 2 + currentX), (int) (y + currentY));
            }
            currentX += 18;
            if (x + currentX >= x + width - 25) {
                currentX = 0;
                currentY += 20;
            }
        }

        Scissor.unset();
        Scissor.pop();

    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double delta) {
        if (MathUtil.isHovered((float) mouseX, (float) mouseY, x, y - 20, width, height + 20)) {
            scroll += delta * 10;
        }
        if (component != null)
            component.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        float currentX = 0;
        float currentY = scroll;
        if (component != null) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
        for (Item item : Registry.ITEM) {
            if (item instanceof AirItem) continue;

            if (MathUtil.isHovered(x + 2 + currentX, y + currentY, x, y - 20, width, height + 20)) {
                //Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(item.getDefaultInstance(), );
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, (int) (x + 2 + currentX), (int) (y + currentY), 16, 16)) {
                    component = new EditComponent(item.getDefaultInstance(), addedItemComponents, this);
                }
            }
            currentX += 18;
            if (x + currentX >= x + width - 25) {
                currentX = 0;
                currentY += 20;
            }
        }
        for (ItemStack item : DonateItems.donitem) {
            if (MathUtil.isHovered(x + 2 + currentX, y + currentY, x, y - 20, width, height + 20)) {
                if (MathUtil.isHovered((float) mouseX, (float) mouseY, (int) (x + 2 + currentX), (int) (y + currentY), 16, 16)) {
                    component = new EditComponent(item, addedItemComponents, this);
                }
            }
            currentX += 18;
            if (x + currentX >= x + width - 25) {
                currentX = 0;
                currentY += 20;
            }
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if (component != null) {
            component.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        if (component != null) {
            component.keyTyped(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        if (component != null) {
            component.charTyped(codePoint, modifiers);
        }
    }
}
