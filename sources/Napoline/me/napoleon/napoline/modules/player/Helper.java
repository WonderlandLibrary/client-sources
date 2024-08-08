package me.napoleon.napoline.modules.player;

import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import me.napoleon.napoline.events.EventChat;
import me.napoleon.napoline.events.EventRender2D;
import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.font.FontLoaders;
import me.napoleon.napoline.junk.values.type.Bool;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.render.RenderUtils;
import me.napoleon.napoline.utils.timer.TimerUtil;

public class Helper extends Mod {
    public Bool AutoGG = new Bool("AutoGG", true);
    public Bool AutoPlay = new Bool("AutoPlay", true);
    public Mode AutoPlayMode = new Mode("AutoPlayMode", Mod.values(), Mod.Normal);
    private boolean gameend = false;

    private int x;
    private TimerUtil th = new TimerUtil();
    int tick = 4;
    enum Mod {
        Normal,
        Insane
    }

    public Helper() {
        super("AutoPlay", ModCategory.Render,"Auto start game & send GG");
        this.addValues(AutoGG, AutoPlay, AutoPlayMode);
        th.reset();
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        if((Boolean) AutoPlay.getValue()) {
            if (gameend) {
                String s1 = "The game will start in ";
                String s2 = " seconds.";
                String text = s1 + tick + s2;
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                RenderUtils.drawRect(0, 20, x, 40, new Color(38, 43, 51).getRGB());
                FontLoaders.F18.drawString(text, x - FontLoaders.F18.getStringWidth(text) - 5, 28, -1);
                if (x < FontLoaders.F18.getStringWidth(text) / 2) {
                    x += 3;
                } else if (x < FontLoaders.F18.getStringWidth(text) + 18) {
                    x += 4;
                }


                RenderUtils.drawRect(0, 20, 2, 40, new Color(59, 135, 197).getRGB());

                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
            }else{
                String s1 = "The game will start in ";
                String s2 = " seconds.";
                String text = s1 + "0" + s2;
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                RenderUtils.drawRect(0, 20, x, 40, new Color(38, 43, 51).getRGB());
                FontLoaders.F18.drawString(text, x - FontLoaders.F18.getStringWidth(text) - 5, 28, -1);
                if (x > 0) {
                    x -= 3;
                }

                if(x>2) {
                    RenderUtils.drawRect(0, 20, 2, 40, new Color(59, 135, 197).getRGB());
                }

                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
            }
        }
    }
    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(gameend) {
            if (th.delay(1000)) {
                tick--;
                th.reset();
            }
            if (tick < 0) {
                tick = 4;
                if((Boolean) AutoPlay.getValue()) {
                    if (AutoPlayMode.getValue() == Mod.Normal) {
                        mc.thePlayer.sendChatMessage("/play solo_normal");
                    } else if (AutoPlayMode.getValue() == Mod.Insane) {
                        mc.thePlayer.sendChatMessage("/play solo_insane");
                    }
                }
                gameend=false;
            }
        }
    }
    @EventTarget
    public void onChat(EventChat e) {
        System.out.println(e.getMessage());
        if(e.getMessage().contains("Winner - ")){
            gameend=true;
            if((Boolean) AutoGG.getValue()) {
                mc.thePlayer.sendChatMessage("Napoline £6/$6 Client->napoline[dot]uk");
            }
            x = 0;
        }
    }


}
