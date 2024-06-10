package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.gui.utils.*;
import java.util.*;
import me.kaimson.melonclient.utils.*;

public class HotbarModule extends Module
{
    public static HotbarModule INSTANCE;
    private final Setting showAttackDamage;
    private final Setting showEnchantments;
    
    public HotbarModule() {
        super("Hotbar");
        new Setting(this, "General Options");
        this.showAttackDamage = new Setting(this, "Show held item attack damage").setDefault(false);
        this.showEnchantments = new Setting(this, "Show held item enchantments").setDefault(false);
        HotbarModule.INSTANCE = this;
    }
    
    public void onTick() {
        this.renderAttackDamage();
        this.renderEnchantments();
    }
    
    private void renderAttackDamage() {
        if (!this.showAttackDamage.getBoolean()) {
            return;
        }
        final zx heldItemStack = ave.A().h.bi.h();
        if (heldItemStack != null) {
            bfl.E();
            final float scale = 0.5f;
            bfl.a(scale, scale, 1.0f);
            final avr sr = new avr(ave.A());
            FontUtils.drawCenteredString(this.getAttackDamageString(heldItemStack), sr.a() / 2.0f / scale, (float)((sr.b() - 59 + (ave.A().c.b() ? -1 : 14) + ave.A().k.a) * 2 + ave.A().k.a), 13421772);
            bfl.F();
        }
    }
    
    private void renderEnchantments() {
        if (!this.showEnchantments.getBoolean()) {
            return;
        }
        final zx heldItemStack = ave.A().h.bi.h();
        if (heldItemStack != null) {
            String toDraw = "";
            toDraw = this.getEnchantmentString(heldItemStack);
            bfl.E();
            final float scale = 0.5f;
            bfl.a(scale, scale, 1.0f);
            final avr sr = new avr(ave.A());
            FontUtils.drawString(toDraw, sr.a() - ave.A().k.a(toDraw) / 2 + 0.1f, (float)((sr.b() - 59 + (ave.A().c.b() ? -2 : 14) + ave.A().k.a) * 2), 13421772);
            bfl.F();
        }
    }
    
    private String getAttackDamageString(final zx stack) {
        for (final String entry : stack.a((wn)ave.A().h, true)) {
            if (entry.endsWith("Attack Damage")) {
                return entry.split(" ", 2)[0].substring(2);
            }
        }
        return "";
    }
    
    private String getEnchantmentString(final zx heldItemStack) {
        final StringBuilder enchantBuilder = new StringBuilder();
        final Map<Integer, Integer> en = (Map<Integer, Integer>)ack.a(heldItemStack);
        for (final Map.Entry<Integer, Integer> entry : en.entrySet()) {
            enchantBuilder.append(a.r.toString());
            enchantBuilder.append(Maps.ENCHANTMENT_SHORT_NAME.get(entry.getKey()));
            enchantBuilder.append(" ");
            enchantBuilder.append(entry.getValue());
            enchantBuilder.append(" ");
        }
        return enchantBuilder.toString().trim();
    }
}
