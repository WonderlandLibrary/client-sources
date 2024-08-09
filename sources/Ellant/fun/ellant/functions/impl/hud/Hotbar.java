package fun.ellant.functions.impl.hud;

import com.google.common.eventbus.Subscribe;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector4f;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;

@FunctionRegister(name = "Hotbar", type = Category.RENDER, desc = "Делает хотбар красивым")
public class Hotbar extends Function {
    private final Minecraft mc = Minecraft.getInstance();

    private final FontRenderer fontRenderer = this.mc.fontRenderer;

    private static Hotbar instance;

    private boolean state;

    public static Hotbar getInstance() {
        if (instance == null)
            instance = new Hotbar();
        return instance;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return this.state;
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (e.getType() != EventDisplay.Type.POST)
            return;
        int hotbarX = (this.mc.getMainWindow().getScaledWidth() - 182) / 2;
        int hotbarY = this.mc.getMainWindow().getScaledHeight() - 22;
        drawStyledRect((hotbarX - 1), (hotbarY - 1), 183.2F, 25.0F, 3.5F, 1.0F, 2130706432);
        DisplayUtils.drawShadow((hotbarX - 1), (hotbarY - 1), 183.2F, 22.0F, 25, ColorUtils.rgba(0, 0, 0, 255), ColorUtils.rgba(0, 0, 0, 0));
        NonNullList<ItemStack> hotbarItems = this.mc.player.inventory.mainInventory;
        for (int i = 0; i < 9; i++) {
            int slotX = hotbarX + i * 20;
            int slotY = hotbarY;
            int slotColor = 1426063360;
            if (i == this.mc.player.inventory.currentItem)
                slotColor = 1439485132;
            DisplayUtils.drawRoundedRect(slotX, slotY, 22.0F, 20.0F, 3.0F, slotColor);
            ItemStack itemStack = (ItemStack)hotbarItems.get(i);
            if (!itemStack.isEmpty()) {
                this.mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, slotX + 2, slotY + 2);
                this.mc.getItemRenderer().renderItemOverlayIntoGUI(this.fontRenderer, itemStack, slotX + 2, slotY + 2, null);
            }
        }
        NonNullList<ItemStack> offhandItems = this.mc.player.inventory.offHandInventory;
        ItemStack offhandItem = (ItemStack)offhandItems.get(0);
        if (!offhandItem.isEmpty())
            for (int j = 0; j < 1; j++) {
                int slotX = hotbarX - 28 - j * 20;
                int slotY = hotbarY;
                int slotColor = 1426063360;
                DisplayUtils.drawRoundedRect(slotX, slotY, 20.0F, 20.0F, 3.0F, slotColor);
                drawStyledRect(slotX, slotY, 20.0F, 20.0F, 3.0F, 1.0F, 2130706432);
                ItemStack itemStack = (ItemStack)offhandItems.get(j);
                if (!itemStack.isEmpty()) {
                    this.mc.getItemRenderer().renderItemAndEffectIntoGUI(itemStack, slotX + 2, slotY + 2);
                    this.mc.getItemRenderer().renderItemOverlayIntoGUI(this.fontRenderer, itemStack, slotX + 2, slotY + 2, null);
                }
            }
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius, float borderWidth, int color) {
        Vector4i colors = new Vector4i(HUD.getColor(0, 1.0F), HUD.getColor(90, 1.0F), HUD.getColor(180, 1.0F), HUD.getColor(270, 1.0F));
        DisplayUtils.drawRoundedRect(x - borderWidth, y - borderWidth, width + borderWidth * 2.0F, height + borderWidth * 2.0F, new Vector4f(7.0F, 7.0F, 7.0F, 7.0F), colors);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, color);
    }
}