package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.Render2DEvent;
import io.github.liticane.clients.feature.event.impl.render.ShaderEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.module.impl.combat.Aura;
import io.github.liticane.clients.feature.property.impl.InputProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.player.ColorUtil;
import io.github.liticane.clients.util.render.RoundedUtil;
import me.Godwhitelight.CustomFontRenderer;
import me.Godwhitelight.FontUtil;
import net.minecraft.client.Minecraft;
import org.lwjglx.util.vector.Vector2f;

import java.awt.*;

@Module.Info(name = "Watermark", category = Module.Category.VISUAL)
public class Watermark extends Module {

    public StringProperty mode = new StringProperty("Mode", this, "Basic", "Basic", "Dev");
    public InputProperty input = new InputProperty("Client name",this, "Suku");
    private CustomFontRenderer fr;

    @SubscribeEvent
    private final EventListener<ShaderEvent> onShader = e -> {
        fr = FontUtil.productWatermark;

        switch (mode.getMode()) {
            case "Basic":
                if (e.isBloom()) {
                    float charX = 6.0F;

                    for (char i : (input.getString()).toCharArray()) {
                        String string = String.valueOf(i);

                        fr.drawStringWithShadow(
                                string,
                                charX, 5,
                                Client.INSTANCE.getThemeManager().getTheme().getAccentColor(new Vector2f(charX * 32, 6)).getRGB()
                        );

                        charX += this.fr.getStringWidth(string) + 0.25F;
                    }
                }
                break;
            case "Dev":
                RoundedUtil.drawRoundOutline(3,3,fr.getStringWidth(Client.NAME  + " fps " + Minecraft.getDebugFPS()) + 5,20,10,1,Color.white,ColorUtil.interpolateColorsBackAndForth(20, 20, Client.INSTANCE.getThemeManager().getTheme().getFirstColor(), Client.INSTANCE.getThemeManager().getTheme().getSecondColor(), false));
                break;
        }
    };
    @SubscribeEvent
    private final EventListener<Render2DEvent> onRender2D = e -> {
        setSuffix(mode.getMode());
        fr = FontUtil.productWatermark;
        switch (mode.getMode()) {
            case "Basic":
                float charX = 6.0F;
                for (char i : (input.getString()).toCharArray()) {
                    String string = String.valueOf(i);

                    fr.drawStringWithShadow(
                            string,
                            charX, 5,
                            Client.INSTANCE.getThemeManager().getTheme().getAccentColor(new Vector2f(charX * 32, 6)).getRGB()
                    );

                    charX += this.fr.getStringWidth(string) + 0.25F;
                }
            break;
            case "Dev":
                RoundedUtil.drawRoundOutline(3,3,fr.getStringWidth(Client.NAME  + " fps " + Minecraft.getDebugFPS()) + 5,20,10,1,new Color(0,0,0,100),ColorUtil.interpolateColorsBackAndForth(20, 20, Client.INSTANCE.getThemeManager().getTheme().getFirstColor(), Client.INSTANCE.getThemeManager().getTheme().getSecondColor(), false));
                fr.drawStringWithShadow(input.getString() + " fps " + Minecraft.getDebugFPS(),5,8,
                        ColorUtil.interpolateColorsBackAndForth(20, 20, Client.INSTANCE.getThemeManager().getTheme().getFirstColor(), Client.INSTANCE.getThemeManager().getTheme().getSecondColor(), false).getRGB()
                );
                break;
        }
    };
}
