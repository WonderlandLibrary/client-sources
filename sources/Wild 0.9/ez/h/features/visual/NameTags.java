package ez.h.features.visual;

import org.lwjgl.opengl.*;
import ez.h.event.events.*;
import ez.h.features.another.*;
import ez.h.*;
import java.awt.*;
import ez.h.utils.*;
import ez.h.ui.fonts.*;
import ez.h.event.*;
import java.util.*;
import java.util.stream.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class NameTags extends Feature
{
    OptionSlider size;
    static HashMap<alk, String> shortNames;
    OptionBoolean renderPotions;
    OptionBoolean renderArmor;
    static OptionBoolean renderEnchant;
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    public static void renderItem(final aip aip, final int n, int n2, final boolean b) {
        if (aip.isEmpty()) {
            return;
        }
        final bip k = NameTags.mc.k;
        final bzw ad = NameTags.mc.ad();
        final EnchantEntry[] array = new EnchantEntry[0xD0 ^ 0xC3];
        array[0] = new EnchantEntry(alo.a, "Pr");
        array[1] = new EnchantEntry(alo.h, "Th");
        array[2] = new EnchantEntry(alo.l, "Sh");
        array[3] = new EnchantEntry(alo.p, "Fr");
        array[4] = new EnchantEntry(alo.o, "Kb");
        array[5] = new EnchantEntry(alo.u, "Un");
        array[6] = new EnchantEntry(alo.w, "Pw");
        array[7] = new EnchantEntry(alo.z, "Inf");
        array[8] = new EnchantEntry(alo.i, "Dph");
        array[9] = new EnchantEntry(alo.C, "Men");
        array[0xBD ^ 0xB7] = new EnchantEntry(alo.b, "FPr");
        array[0x8D ^ 0x86] = new EnchantEntry(alo.j, "Fro");
        array[0x65 ^ 0x69] = new EnchantEntry(alo.s, "Eff");
        array[0xB8 ^ 0xB5] = new EnchantEntry(alo.q, "Lt");
        array[0x40 ^ 0x4E] = new EnchantEntry(alo.v, "For");
        array[0x54 ^ 0x5B] = new EnchantEntry(alo.B, "Lur");
        array[0x4A ^ 0x5A] = new EnchantEntry(alo.c, "Fea");
        array[0x9A ^ 0x8B] = new EnchantEntry(alo.y, "Fl");
        array[0x8E ^ 0x9C] = new EnchantEntry(alo.m, "Smi");
        final EnchantEntry[] array2 = array;
        GL11.glPushMatrix();
        bus.c((float)n, (float)(n2 + (b ? (0x35 ^ 0x3F) : 0)), 0.0f);
        GL11.glPopMatrix();
        bhz.c();
        ad.a = -147.0f;
        if (!b) {
            bus.b(0.8f, 0.8f, 0.8f);
        }
        else {
            bus.b(1.0f, 1.0f, 1.0f);
        }
        bus.m();
        bus.a(343 + 715 - 551 + 263, 499 + 564 - 415 + 123, 1, 0);
        GL11.glEnable(1652 + 1518 - 921 + 759);
        GL11.glEnable(2298 + 904 - 1859 + 1586);
        bhz.c();
        GL11.glEnable(7133 + 14399 - 16099 + 27390);
        GL11.glPolygonOffset(1.0f, -1.1E7f);
        ad.b(aip, n, n2);
        ad.a(k, aip, n, n2, (String)null);
        GL11.glPolygonOffset(1.0f, 1100000.0f);
        GL11.glDisable(16344 + 10049 - 6231 + 12661);
        bhz.a();
        GL11.glEnable(160 + 2859 - 2574 + 2484);
        ad.a = 0.0f;
        GL11.glPushMatrix();
        GL11.glEnable(2577 + 694 - 2348 + 2085);
        GL11.glDisable(339 + 186 + 1739 + 665);
        if (b && NameTags.renderEnchant.enabled) {
            EnchantEntry[] array3;
            for (int length = (array3 = array2).length, i = 0; i < length; ++i) {
                final EnchantEntry enchantEntry = array3[i];
                final int a = alm.a(enchantEntry.getEnchant(), aip);
                String string = "" + a;
                if (a > 8) {
                    string = "10+";
                }
                if (a > 0) {
                    GL11.glDisable(1142 + 127 + 812 + 815);
                    CFontManager.manropesmall.drawString(enchantEntry.getName() + (enchantEntry.getName().equals("Men") ? "" : string), n + 2.5f, (float)(n2 - (0x9C ^ 0x96)), -1, false);
                    n2 -= (int)((k.a - 1) * 1.0f);
                }
            }
        }
        GL11.glEnable(733 + 805 - 309 + 1700);
        bhz.a();
        bus.e();
        bus.l();
        bus.g();
        GL11.glPopMatrix();
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        for (final aed aed : NameTags.mc.f.i) {
            if (!aed.F) {
                if (aed == NameTags.mc.h) {
                    continue;
                }
                if (aed.i_().d().contains("-")) {
                    continue;
                }
                final double n = aed.M + (aed.p - aed.M) * eventRender3D.getPartialTicks() - NameTags.mc.ac().o;
                final double n2 = aed.N + (aed.q - aed.N) * eventRender3D.getPartialTicks() - NameTags.mc.ac().p;
                final double n3 = aed.O + (aed.r - aed.O) * eventRender3D.getPartialTicks() - NameTags.mc.ac().q;
                GL11.glPushMatrix();
                final float n4 = Math.min(Math.max(1.6f * (NameTags.mc.h.getDistance((vg)aed) * 0.15f), 1.25f), 6.0f) * 0.014f;
                GL11.glTranslatef((float)n, (float)n2 + aed.by() + 0.8f + n4 * 12.0f, (float)n3);
                GL11.glEnable(1551 + 1342 - 1571 + 1720);
                GL11.glBlendFunc(588 + 0 + 33 + 149, 211 + 538 - 0 + 22);
                GL11.glNormal3f(1.0f, 1.0f, 1.0f);
                if (NameTags.mc.t.aw == 2) {
                    GL11.glRotatef(-NameTags.mc.ac().e, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-NameTags.mc.ac().f, 1.0f, 0.0f, 0.0f);
                }
                else {
                    GL11.glRotatef(-NameTags.mc.h.v, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(NameTags.mc.h.w, 1.0f, 0.0f, 0.0f);
                }
                GL11.glScalef(-n4 * this.size.getNum(), -n4 * this.size.getNum(), n4 * this.size.getNum());
                final String s = ((AntiBot)Main.getFeatureByName("AntiBot")).isBot(aed) ? "Bot" : aed.i_().c();
                final boolean cyrillic = Utils.isCyrillic(s);
                final CFontRenderer acrombold = CFontManager.acrombold;
                final float n5 = cyrillic ? ((float)NameTags.mc.k.a(s)) : ((float)acrombold.getStringWidth(s));
                GL11.glDisable(2717 + 437 - 1843 + 1618);
                RenderUtils.drawBlurredShadow(-3.5f - n5 / 2.0f, 0.0f, n5 + 7.0f, acrombold.FONT_HEIGHT + 19.0f, 4, new Color(-1375731702, true));
                if (cyrillic) {
                    NameTags.mc.k.drawString(s, 0.0f - NameTags.mc.k.a(s) / 2.0f, 4.5f, -1);
                }
                else {
                    CFontManager.rany.drawCenteredStringNoShadow(s, 0.0f, 4.5f, -1);
                }
                CFontManager.rany.drawCenteredStringNoShadow(Utils.format("##", aed.cd()), -3.5f, 16.0f, -1);
                bus.b(1.2f, 1.2f, 0.0f);
                NameTags.mc.N().a(new nf("textures/gui/icons.png"));
                NameTags.mc.q.a(2.9166665f, 13.0f, 0x6C ^ 0x7C, 0, 9, 9);
                NameTags.mc.q.a(2.9166665f, 13.0f, 0xB1 ^ 0x85, 0, 9, 9);
                GL11.glDisable(2850 + 1995 - 2291 + 375);
                if (this.renderArmor.enabled) {
                    int n6 = -45;
                    for (final aip aip : aed.getArmorInventoryList()) {
                        n6 += 15;
                        renderItem(aip, n6, -17, true);
                    }
                }
                GL11.glEnable(990 + 900 + 962 + 77);
                renderItem(aed.cp(), -44, -17, true);
                renderItem(aed.co(), 0xB2 ^ 0xAC, -17, true);
                if (aed.ca().size() > 0 && this.renderPotions.enabled) {
                    float n7 = -20.0f + aed.ca().size() * (0x3A ^ 0x2E) / 2.0f;
                    for (final va va : aed.ca()) {
                        GL11.glDisable(2514 + 1410 - 1026 + 31);
                        NameTags.mc.N().a(new nf("textures/gui/container/inventory.png"));
                        final int d = va.a().d();
                        NameTags.mc.q.a(-((0x85 ^ 0x91) * aed.ca().size()) + (n7 += 20.0f), this.getMaxHeight(aed, -17.0f), d % 8 * (0x45 ^ 0x57), 46 + 44 + 38 + 70 + d / 8 * (0x60 ^ 0x72), 0x2A ^ 0x38, 0x90 ^ 0x82);
                        CFontManager.acrom.drawStringWithShadow(Utils.getRimNum(va), -((0x69 ^ 0x7B) * aed.ca().size()) + n7 + 3.0f, this.getMaxHeight(aed, -15.0f) - 9.0f, -1);
                        GL11.glEnable(252 + 2343 - 1682 + 2016);
                    }
                }
                GL11.glDisable(1460 + 564 - 290 + 1308);
                GL11.glEnable(2433 + 736 - 996 + 756);
                bus.I();
                GL11.glPopMatrix();
            }
        }
    }
    
    float getMaxHeight(final aed aed, final float n) {
        return -6.0f + n * alm.a((aip)((List)aed.getArmorInventoryList().stream().sorted(Comparator.comparingDouble(aip -> -alm.a(aip).size())).collect(Collectors.toList())).get(0)).size() + n;
    }
    
    public NameTags() {
        super("NameTags", "\u041e\u0442\u043e\u0431\u0440\u0430\u0436\u0430\u0435\u0442 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e \u043e \u0438\u0433\u0440\u043e\u043a\u0430\u0445.", Category.VISUAL);
        this.renderPotions = new OptionBoolean(this, "Potions", true);
        this.renderArmor = new OptionBoolean(this, "Armor", true);
        NameTags.renderEnchant = new OptionBoolean(this, "Enchants", true);
        this.addOptions(this.size = new OptionSlider(this, "Size", 1.0f, 0.01f, 3.0f, OptionSlider.SliderType.NULL), this.renderPotions, this.renderArmor, NameTags.renderEnchant);
    }
    
    public static class EnchantEntry
    {
        private final alk enchant;
        private final String name;
        
        public String getName() {
            return this.name;
        }
        
        public alk getEnchant() {
            return this.enchant;
        }
        
        public EnchantEntry(final alk enchant, final String name) {
            this.enchant = enchant;
            this.name = name;
        }
    }
}
