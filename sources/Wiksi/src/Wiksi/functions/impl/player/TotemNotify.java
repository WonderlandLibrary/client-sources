package src.Wiksi.functions.impl.player;


import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import java.util.Iterator;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

@FunctionRegister(
        name = "TotemCounter",
        type = Category.Player
)
public class TotemNotify extends Function {
    private static final ResourceLocation TOTEM_TEXTURE = new ResourceLocation("minecraft", "textures/item/totem_of_undying.png");
    private int totemX = 0;
    private int totemY = 0;
    private boolean isDragging = false;

    public TotemNotify() {
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.gameSettings.getPointOfView() != PointOfView.THIRD_PERSON_BACK && mc.gameSettings.getPointOfView() != PointOfView.THIRD_PERSON_FRONT) {
            int totemCount = this.countItems(Items.TOTEM_OF_UNDYING);
            totemCount += this.countItemsInOffhand(Items.TOTEM_OF_UNDYING);
            int screenWidth = mc.getMainWindow().getScaledWidth();
            int screenHeight = mc.getMainWindow().getScaledHeight();
            int hotbarHeight = 22;
            int expBarHeight = 5;
            int offset = 10;
            this.totemX = screenWidth / 2 - 8;
            this.totemY = screenHeight - hotbarHeight - expBarHeight - offset - 24 + 2;
            if (mc.currentScreen instanceof ChatScreen) {
                double mouseX = mc.mouseHelper.getMouseX();
                double mouseY = mc.mouseHelper.getMouseY();
                if (mouseX >= (double)this.totemX && mouseX <= (double)(this.totemX + 16) && mouseY >= (double)this.totemY && mouseY <= (double)(this.totemY + 16)) {
                    this.isDragging = true;
                } else {
                    this.isDragging = false;
                }

                if (this.isDragging) {
                    this.totemX = (int)mouseX;
                    this.totemY = (int)mouseY;
                }
            }

            mc.getTextureManager().bindTexture(TOTEM_TEXTURE);
            MatrixStack matrixStack = new MatrixStack();
            AbstractGui.blit(matrixStack, this.totemX, this.totemY, 0.0F, 0.0F, 16, 16, 16, 16);
            mc.fontRenderer.drawStringWithShadow(matrixStack, String.valueOf(totemCount), (float)(this.totemX + 20), (float)(this.totemY + 4), 16777215);
        }

    }

    private int countItems(Item item) {
        int count = 0;
        Iterator var3 = mc.player.inventory.mainInventory.iterator();

        while(var3.hasNext()) {
            ItemStack stack = (ItemStack)var3.next();
            if (stack.getItem() == item) {
                count += stack.getCount();
            }
        }

        return count;
    }

    private int countItemsInOffhand(Item item) {
        ItemStack offhandStack = mc.player.getHeldItemOffhand();
        return offhandStack.getItem() == item ? offhandStack.getCount() : 0;
    }
}
