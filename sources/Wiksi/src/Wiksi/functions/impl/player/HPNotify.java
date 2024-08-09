package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.SliderSetting;
import net.minecraft.client.Minecraft;

@FunctionRegister(name = "HPDetected", type = Category.Player)
public class HPNotify extends Function {
    private SliderSetting hp = new SliderSetting("Здоровье", 5, 1, 20, 0.5f);
    private static final int DISPLAY_DURATION_TICKS = 180;
    private int displayTicks = 0;
    private boolean isDisplaying = false;

    public HPNotify() {
        addSettings(hp);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            float health = mc.player.getHealth();
            if (health <= hp.get()) {
                this.isDisplaying = true;
                this.displayTicks = 180;
            }
        }

    }

    @Subscribe
    private void onDisplay(EventDisplay e) {
        if (this.isDisplaying) {
            MatrixStack matrixStack = e.getMatrixStack();
            Minecraft mc = Minecraft.getInstance();
            int screenWidth = mc.getMainWindow().getScaledWidth();
            int screenHeight = mc.getMainWindow().getScaledHeight();
            String message = "У вас мало ХП!";
            int color = 16777215;
            int alpha = (int)(255.0F * ((float)this.displayTicks / 180.0F));
            float scale = 1.5F;
            int messageWidth = mc.fontRenderer.getStringWidth(message);
            int x = (screenWidth - (int)((float)messageWidth * scale)) / 2;
            int y = screenHeight / 2 - 4;
            matrixStack.push();
            matrixStack.scale(scale, scale, scale);
            mc.fontRenderer.drawStringWithShadow(matrixStack, message, (float)x / scale, (float)y / scale, color | alpha << 24);
            matrixStack.pop();
            if (--this.displayTicks <= 0) {
                this.isDisplaying = false;
            }
        }

    }
}
