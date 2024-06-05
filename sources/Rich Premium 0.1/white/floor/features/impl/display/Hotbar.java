package white.floor.features.impl.display;

import com.mojang.realmsclient.gui.ChatFormatting;
import io.netty.util.internal.MathUtil;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import white.floor.event.EventTarget;
import white.floor.event.event.Event2D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.combat.CountHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Hotbar extends Feature {
    public Hotbar() {
        super("Hotbar", "beautiful hotbar", 0, Category.DISPLAY);
    }

    @EventTarget
    public void hotbar(Event2D event2D) {

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        if(!(mc.currentScreen instanceof GuiChat)) {
            String fpsandping = "FPS: " + mc.getDebugFPS() + " Ping: " + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
            String cords = "X:" + (int) mc.player.posX + " Y:" + (int) mc.player.posY + " Z:" + (int) mc.player.posZ;
            String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
            String date = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());

            Fonts.sfui15.drawStringWithShadow(fpsandping, 3, scaledResolution.getScaledHeight() - 17, -1);
            Fonts.sfui15.drawStringWithShadow(cords, 3, scaledResolution.getScaledHeight() - 8, -1);
            Fonts.sfui15.drawStringWithShadow(time, scaledResolution.getScaledWidth() - Fonts.sfui15.getStringWidth(time) - 16, scaledResolution.getScaledHeight() - 17.5,  -1);
            Fonts.sfui15.drawStringWithShadow(date, scaledResolution.getScaledWidth() - Fonts.sfui15.getStringWidth(date) - 7, scaledResolution.getScaledHeight() - 8.5,  -1);
        }
    }
}