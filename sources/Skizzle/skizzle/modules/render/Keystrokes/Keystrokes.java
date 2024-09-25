/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package skizzle.modules.render.Keystrokes;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import skizzle.events.Event;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.modules.Module;
import skizzle.modules.render.Keystrokes.Keystroke;
import skizzle.newFont.FontUtil;
import skizzle.newFont.MinecraftFontRenderer;
import skizzle.util.RenderUtil;

public class Keystrokes
extends Module {
    public List<Keystroke> keystrokes = new ArrayList<Keystroke>();

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventRenderGUI) {
            Keystrokes Nigga2;
            MinecraftFontRenderer Nigga3 = FontUtil.cleanmediumish;
            for (Keystroke Nigga4 : Nigga2.keystrokes) {
                int Nigga5 = Nigga4.x;
                int Nigga6 = Nigga4.y;
                Gui.drawRect(Nigga5 - Nigga4.width, Nigga6 - Nigga4.height, Nigga5 + Nigga4.width, Nigga6 + Nigga4.height, -1876942816);
                double Nigga7 = Nigga4.animation.getDelay();
                Nigga7 += 1.0;
                boolean Nigga8 = Nigga4.animation.hasTimeElapsed((long)-403756695 ^ 0xFFFFFFFFE7EF2968L, true);
                if (Keyboard.isKeyDown((int)Nigga4.key)) {
                    if (Nigga8 && Nigga4.animation.stage < (double)(Nigga4.width + Nigga4.height)) {
                        Nigga4.animation.stage += (0.0 + Nigga7 / 30.0) * 2.0;
                    }
                    GL11.glEnable((int)3089);
                    RenderUtil.scissor((double)(Nigga5 - Nigga4.width) + 0.0, (double)(Nigga6 - Nigga4.height) + 0.0, (double)(Nigga4.width * 2) - 0.0, (double)(Nigga4.height * 2) - 0.0);
                    RenderUtil.initMask();
                    RenderUtil.draw2DCircle(Nigga5, Nigga6, Nigga4.animation.stage, 0.0, true, -1, 360);
                    RenderUtil.useMask();
                    Gui.drawRect((double)(Nigga5 - Nigga4.width) + 0.0, (double)(Nigga6 - Nigga4.height) + 0.0, (double)(Nigga5 + Nigga4.width) - 0.0, (double)(Nigga6 + Nigga4.height) - 0.0, -1433366384);
                    RenderUtil.disableMask();
                    GL11.glDisable((int)3089);
                } else {
                    Nigga4.animation.stage = 0.0;
                }
                String Nigga9 = Keyboard.getKeyName((int)Nigga4.key);
                Nigga3.drawString(Nigga9, (float)((double)Nigga5 - (double)Nigga3.getStringWidth(Nigga9) / 1.9), Nigga4.y - Nigga3.FONT_HEIGHT / 2, -1);
            }
        }
    }

    public Keystrokes() {
        super(Qprot0.0("\ue8b2\u71ce\ud3f4\ua7f7\uc2ba\u6d31\u8c20\u84df\u5707\u69f7"), 0, Module.Category.RENDER);
        Keystrokes Nigga;
        Nigga.keystrokes.add(new Keystroke(17, 43, 92, 13, 13));
        Nigga.keystrokes.add(new Keystroke(31, 43, 120, 13, 13));
        Nigga.keystrokes.add(new Keystroke(30, 15, 120, 13, 13));
        Nigga.keystrokes.add(new Keystroke(32, 71, 120, 13, 13));
        Nigga.keystrokes.add(new Keystroke(57, 43, 148, 41, 13));
    }

    public static {
        throw throwable;
    }
}

