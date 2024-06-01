package best.actinium.module.impl.visual;

import best.actinium.Actinium;
import best.actinium.component.componets.SessionComponent;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.BloomEvent;
import best.actinium.event.impl.render.BlurEvent;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.render.ColorUtil;
import best.actinium.util.render.RoundedUtil;
import best.actinium.util.render.drag.DragUtil;
import best.actinium.util.render.drag.Draggable;
import best.actinium.util.render.font.CustomFontRenderer;
import best.actinium.util.render.font.FontUtil;
import org.lwjglx.util.vector.Vector2f;

import java.awt.*;

@ModuleInfo(
        name = "Session Info",
        description = "shows stuff.",
        category = ModuleCategory.VISUAL
)
public class SessionInfoModule extends Module {
    private final Draggable drag = DragUtil.createDraggable(this, "stats", 4, 30);
    private CustomFontRenderer fontBald;

    @Callback
    public void onRender(Render2DEvent event) {
        fontBald = FontUtil.productBald;
        DragUtil.setSize(140,50);

        RoundedUtil.drawRound(drag.getXPos(), drag.getYPos(), drag.getWidth(), drag.getHeight(), 4, new Color(0, 0, 0, 150));
        float charX = (drag.getXPos() + drag.getWidth() - fontBald.getStringWidth("Session Info") - 43);
        for (char i : ("Session Info".toCharArray())) {
            String string = String.valueOf(i);
            fontBald.drawStringWithShadow(
                    string,
                    charX, drag.getYPos() + fontBald.getHeight() - 5,
                    ColorUtil.getAccentColor(new Vector2f(charX * 32, 6), Actinium.INSTANCE.getModuleManager().get(HudModule.class).color1.getColor(), Actinium.INSTANCE.getModuleManager().get(HudModule.class).color2.getColor()).getRGB()
            );
            charX += this.fontBald.getStringWidth(string) + 0.25F;
        }

        fontBald.drawStringWithShadow("Kills: " + SessionComponent.getKills(),(drag.getXPos() + drag.getWidth() - fontBald.getStringWidth("Kills: " + SessionComponent.getKills()) - 105),drag.getYPos() + fontBald.getHeight() + 7,-1);
        fontBald.drawStringWithShadow("Wins: " + SessionComponent.getWins(),(drag.getXPos() + drag.getWidth() - fontBald.getStringWidth("Wins: " + SessionComponent.getWins()) - 101),drag.getYPos() + fontBald.getHeight() + 20,-1);
    }

    @Callback
    public void onBloom(BloomEvent event) {
        RoundedUtil.drawRound(drag.getXPos(), drag.getYPos(), drag.getWidth(), drag.getHeight(), 4, Color.black);
    }

    @Callback
    public void onBlur(BlurEvent event) {
        RoundedUtil.drawRound(drag.getXPos(), drag.getYPos(), drag.getWidth(), drag.getHeight(), 4, Color.white);
    }
}
