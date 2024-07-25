package club.bluezenith.module.modules.misc;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.DevOnly;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import net.minecraft.client.gui.FontRenderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static club.bluezenith.util.render.RenderUtil.animate;
import static java.lang.System.currentTimeMillis;
import static net.minecraft.client.renderer.GlStateManager.*;

@DevOnly
public class Debug extends Module {
    private static Debug module;
    private final BooleanValue noRepeating = new BooleanValue("Don't repeat", false).setIndex(2);
    private final FloatValue livingTime = AbstractBuilder.createFloat("Living time").increment(0.1F).max(5F).min(0.5F).defaultOf(2F).index(3).build();
    private static final List<Entry> entries = new ArrayList<>();
    private static final List<Entry> toDelete = new ArrayList<>();

    public Debug() {
        super("Debug", ModuleCategory.MISC);
        module = this;
    }

    public static void debug(String text) {
        if(module != null && module.getState()) {
            final Entry toAdd = new Entry(text);
            int index = entries.indexOf(toAdd);
            if(!module.noRepeating.get() || index == -1) {
                entries.add(toAdd);
            } else if(index > 0) {
                final Entry existingEntry = entries.get(index);
                existingEntry.timesFound++;
                existingEntry.spawnedOn += 400 / existingEntry.timesFound;
            }
        }
    }

    @Listener
    public void render(Render2DEvent event) {

        if(!toDelete.isEmpty()) {
            for (Entry entry : toDelete) {
                entries.remove(entry);
            }
            toDelete.clear();
        }

        final FontRenderer font = mc.fontRendererObj;
        float currentY = 2;

        final float scale = 0.5f;
        final long time = currentTimeMillis();
        float posX = (HUD.module.font.get().getStringWidthF(HUD.module.hudName.get()) + 10)/scale;

        pushMatrix();
        scale(scale, scale, scale);
        translate(1 / scale, 1 / scale, 1 / scale);

        final Entry[] entriez = entries.toArray(new Entry[0]);

        for (Entry entry : entriez) {
            final boolean shouldIncreaseAlpha = time - entry.spawnedOn < livingTime.get() * 1000;
            if(!shouldIncreaseAlpha) {
                entry.alpha = animate(0.001f, entry.alpha, 0.08f);
                if(entry.alpha <= 0.03f) {
                    toDelete.add(entry);
                }
            }
            final float target = currentY + font.FONT_HEIGHT + 2;
            entry.y = (entry.y > target) ? animate(target, entry.y, 0.25f) : target;

            if(shouldIncreaseAlpha)
             entry.alpha = animate(1, entry.alpha, 0.1f);

            enableAlpha();
            enableBlend();

            String text = entry.text;
            if(entry.timesFound > 1)
                text += " [" + entry.timesFound + "x]";

            font.drawString(text, shouldIncreaseAlpha ? posX * entry.alpha : posX / entry.alpha, shouldIncreaseAlpha ? entry.y/entry.alpha : entry.y, new Color(1F, 1F, 1F, entry.alpha).getRGB(), true);
            disableBlend();
            disableAlpha();

            currentY = entry.y;
            if(currentY > (event.getHeight() / scale) - font.FONT_HEIGHT / scale) {
                posX += font.getStringWidthF(entry.text) + 10;
                currentY = -font.FONT_HEIGHT;
            }

        }

        popMatrix();
    }

    private static class Entry {

        public Entry(String text) {
            this.text = text;
            spawnedOn = currentTimeMillis();
        }

        String text;
        float y;
        float alpha;
        int timesFound = 1;
        long spawnedOn;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(text, entry.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }
    }
}
