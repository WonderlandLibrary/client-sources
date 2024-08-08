// 
// Decompiled by Procyon v0.5.36
// 

package me.zeroeightsix.kami.module.modules.render;

import me.zeroeightsix.kami.gui.font.CFontRenderer;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.util.ColourUtils;
import me.zeroeightsix.kami.util.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

@Module.Info(name = "Crystals", category = Module.Category.GUI)
public class Crystals extends Module
{
    private Setting<Float> x;
    private Setting<Float> y;
    private Setting<Boolean> rainbow;
    private Setting<Boolean> smooth;
    private Setting<Integer> red;
    private Setting<Integer> green;
    private Setting<Integer> blue;
    CFontRenderer cFontRenderer;

    public Crystals() {
        this.x = this.register(Settings.f("InfoX", 0.0f));
        this.y = this.register(Settings.f("InfoY", 200.0f));
        this.rainbow = this.register(Settings.b("Rainbow", false));
        this.smooth = this.register(Settings.b("Smooth Font", false));
        this.red = this.register((Setting<Integer>)Settings.integerBuilder("Red").withRange(0, 255).withValue(255).build());
        this.green = this.register((Setting<Integer>)Settings.integerBuilder("Green").withRange(0, 255).withValue(255).build());
        this.blue = this.register((Setting<Integer>)Settings.integerBuilder("Blue").withRange(0, 255).withValue(255).build());
        this.cFontRenderer = new CFontRenderer(new Font("Arial", 0, 18), true, false);
    }

    @Override
    public void onRender() {
        float yCount = this.y.getValue();
        final int ared = this.red.getValue();
        final int bgreen = this.green.getValue();
        final int cblue = this.blue.getValue();
        int color;
        final int drgb = color = ColourUtils.toRGBA(ared, bgreen, cblue, 255);
        int crystals = Crystals.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
        if (Crystals.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            ++crystals;
        }
        if (this.rainbow.getValue()) {
            final float[] hue = { System.currentTimeMillis() % 11520L / 11520.0f };
            final int rgb = Color.HSBtoRGB(hue[0], 1.0f, 1.0f);
            final int red = rgb >> 16 & 0xFF;
            final int green = rgb >> 8 & 0xFF;
            final int blue = rgb & 0xFF;
            final int argb = color = ColourUtils.toRGBA(red, green, blue, 255);
        }
        if (this.smooth.getValue()) {
            this.cFontRenderer.drawStringWithShadow("CRYSTAL: " + crystals, this.x.getValue(), (yCount += 10.0f) - this.cFontRenderer.getHeight() - 1.0f, color);
        }
        else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow("CRYSTAL: " + crystals, (float)this.x.getValue(), (yCount += 10.0f) - Wrapper.getMinecraft().fontRenderer.FONT_HEIGHT, color);

        }
    }



}
