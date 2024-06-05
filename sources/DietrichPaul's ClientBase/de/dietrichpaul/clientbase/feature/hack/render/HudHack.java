/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack.render;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.Render2DListener;
import de.dietrichpaul.clientbase.feature.gui.font.FontAtlas;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.util.render.ColorUtil;
import de.dietrichpaul.clientbase.util.render.api.Renderer2D;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HudHack extends Hack implements Render2DListener {

    public HudHack() {
        super("HUD", HackCategory.RENDER);
    }

    private void renderActiveHacks(MatrixStack matrices) {
        FontAtlas logo = ClientBase.INSTANCE.getFontList().getVerdana();
        logo.renderWithShadow(matrices, ClientBase.NAME, 4 / 2F, 4 / 2F, 18, -1);

        FontAtlas font = ClientBase.INSTANCE.getFontList().getVerdana();

        List<Hack> hacks = new LinkedList<>();
        for (Hack hack : ClientBase.INSTANCE.getHackList().getHacks()) {
            if (hack.isToggled()) hacks.add(hack);
        }
        hacks.sort(Comparator.comparingDouble(h -> -font.getWidth(h.getName())));

        int width = mc.getWindow().getScaledWidth();
        float y = 0;
        for (Hack hack : hacks) {
            float tw = font.getWidth(hack.getName());
            Renderer2D.fill(matrices, width - tw - 4, y, width, y + font.getLineHeight() + 4, Integer.MIN_VALUE);
            font.renderWithShadow(matrices, hack.getName(), width - tw - 2, y + 2, ColorUtil.getRainbow(-y, 0.6F, 1F));
            y += font.getLineHeight() + 4;
        }
    }

    @Override
    public void onRender2D(MatrixStack matrices, float tickDelta) {
        renderActiveHacks(matrices);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(Render2DListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(Render2DListener.class, this);
    }
}
