package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.settings.*;
import me.kaimson.melonclient.gui.utils.*;
import java.util.*;

public class PotionHUDModule extends IModuleRenderer
{
    private final jy inventoryBackground;
    private int width;
    private int height;
    private final Setting shadow;
    private final Setting blink;
    
    public PotionHUDModule() {
        super("Potion HUD", 20);
        this.inventoryBackground = new jy("textures/gui/container/inventory.png");
        new Setting(this, "General Options");
        this.shadow = new Setting(this, "Text Shadow").setDefault(true);
        new Setting(this, "Blink Options");
        this.blink = new Setting(this, "Blink").setDefault(true);
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public void render(final float x, final float y) {
        if (this.mc.h.bl() != null && this.mc.h.bl().size() > 0) {
            this.render(this.mc.h.bl(), x, y, false);
        }
        else if (this.mc.m instanceof GuiHUDEditor) {
            this.renderDummy(x, y);
        }
    }
    
    @Override
    public void renderDummy(final float x, final float y) {
        final Collection<pf> potionEffects = new ArrayList<pf>((Collection<? extends pf>)Collections.emptySet());
        potionEffects.add(new pf(pe.x.H, 9));
        potionEffects.add(new pf(pe.c.H, 9));
        this.width = 90;
        this.height = 44;
        this.render(potionEffects, x, y, true);
    }
    
    private void render(final Collection<pf> potionEffects, final float x, final float y, final boolean isDummy) {
        if (potionEffects == null) {
            return;
        }
        bfl.E();
        bfl.w();
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        int j = (int)y;
        final int tileHeight = 22;
        if (!isDummy) {
            this.width = 0;
            this.height = 22 * potionEffects.size();
        }
        for (final pf potionEffect : potionEffects) {
            final pe potion = pe.a[potionEffect.a()];
            if (potion.e()) {
                bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
                this.mc.P().a(this.inventoryBackground);
                GuiUtils.INSTANCE.a(x + 6.0f, (float)(j + 2), potion.f() % 8 * 18, 198 + potion.f() / 8 * 18, 18, 18);
            }
            FontUtils.drawString(bnq.a(potion.a(), new Object[0]), x + 10.0f + 18.0f, (float)(j + 3), 16777215, this.shadow.getBoolean());
            Label_0284: {
                if (isDummy) {
                    if (this.blink.getBoolean()) {
                        if (ave.J() / 50L % 20L >= 10L) {
                            break Label_0284;
                        }
                    }
                }
                else if (!this.shouldRender(potionEffect.b(), 10)) {
                    break Label_0284;
                }
                FontUtils.drawString(pe.a(potionEffect), x + 10.0f + 18.0f, (float)(j + 3 + 10), 8355711, this.shadow.getBoolean());
            }
            if (!isDummy) {
                this.width = Math.max(this.width, 30 + this.mc.k.a(bnq.a(potion.a(), new Object[0])));
            }
            j += tileHeight;
        }
        bfl.F();
    }
    
    private boolean shouldRender(final int ticksLeft, final int thresholdSeconds) {
        return !this.blink.getBoolean() || ticksLeft / 20 > thresholdSeconds || ticksLeft % 20 < 10;
    }
}
