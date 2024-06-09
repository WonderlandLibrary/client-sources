package com.client.glowclient.modules.other;

import java.util.concurrent.atomic.*;
import java.awt.*;
import java.util.function.*;
import com.client.glowclient.modules.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.*;

public class HUD extends ModuleContainer
{
    public static BooleanValue descriptions;
    public static BooleanValue speed;
    public static BooleanValue dimensionCoords;
    public static NumberValue green;
    public static BooleanValue direction;
    public static BooleanValue watermark;
    public static BooleanValue rotation;
    public static ea F;
    public static BooleanValue coordinates;
    private int e;
    public static BooleanValue customFont;
    public static BooleanValue arrayList;
    public static nB color;
    public static NumberValue blue;
    public static NumberValue red;
    private int k;
    public static nB side;
    private int f;
    public static BooleanValue armor;
    private static final String G;
    public static BooleanValue tPS;
    private int L;
    public static NumberValue separation;
    public static nB order;
    
    private void A(final AtomicInteger atomicInteger, final int n, final ScaledResolution scaledResolution, final ca ca, final AtomicInteger atomicInteger2, final String s) {
        int n2;
        if (HUD.color.e().equals("Rainbow")) {
            final Color m = aa.M(atomicInteger.intValue() * 100000000, 1.0f);
            n2 = n;
            final int f = 200;
            final Color color = m;
            this.e = m.getRed();
            this.L = color.getGreen();
            this.k = color.getBlue();
            this.f = f;
        }
        else {
            this.M(s);
            n2 = n;
        }
        double n3 = n2 + scaledResolution.getScaledWidth() - Ia.M(HUD.F, s, 1.0) - 2.0;
        if (HUD.side.e().equals("Left")) {
            n3 = 2.0;
        }
        final ca i = ca.K().M(HUD.F).D(ga.M(this.e, this.L, this.k, this.f)).M(s, n3 + 1.0, atomicInteger2.intValue() + 1, true);
        final HUD hud = this;
        i.D(ga.M(hud.e, hud.L, this.k, this.f)).M(s, n3, atomicInteger2.intValue());
        atomicInteger2.addAndGet(HUD.separation.M());
        atomicInteger.addAndGet(1);
    }
    
    private void D(final AtomicInteger atomicInteger, final int n, final ScaledResolution scaledResolution, final ca ca, final AtomicInteger atomicInteger2, final String s) {
        int n2;
        if (HUD.color.e().equals("Rainbow")) {
            final Color m = aa.M(atomicInteger.intValue() * 100000000, 1.0f);
            n2 = n;
            final int f = 200;
            final Color color = m;
            this.e = m.getRed();
            this.L = color.getGreen();
            this.k = color.getBlue();
            this.f = f;
        }
        else {
            this.M(s);
            n2 = n;
        }
        double n3 = n2 + scaledResolution.getScaledWidth() - Ia.M(HUD.F, s, 1.0) - 2.0;
        if (HUD.side.e().equals("Left")) {
            n3 = 2.0;
        }
        AtomicInteger atomicInteger3;
        if (HUD.F != null) {
            atomicInteger3 = atomicInteger2;
            final ca i = ca.K().M(HUD.F).D(ga.M(this.e, this.L, this.k, this.f)).M(s, n3 + 1.0, atomicInteger2.intValue() + 1, true);
            final HUD hud = this;
            i.D(ga.M(hud.e, hud.L, this.k, this.f)).M(s, n3, atomicInteger2.intValue());
        }
        else {
            ca.K().M(HUD.F).D(ga.M(this.e, this.L, this.k, this.f)).M(s, n3, atomicInteger2.doubleValue(), true);
            atomicInteger3 = atomicInteger2;
        }
        atomicInteger3.addAndGet(HUD.separation.M());
        atomicInteger.addAndGet(1);
    }
    
    private static boolean A(final Module module) {
        return module.A();
    }
    
    private void M(final AtomicInteger atomicInteger, final int n, final ScaledResolution scaledResolution, final ca ca, final AtomicInteger atomicInteger2, final String s) {
        int n2;
        if (HUD.color.e().equals("Rainbow")) {
            final Color m = aa.M(atomicInteger.intValue() * 100000000, 1.0f);
            n2 = n;
            final int f = 200;
            final Color color = m;
            this.e = m.getRed();
            this.L = color.getGreen();
            this.k = color.getBlue();
            this.f = f;
        }
        else {
            this.M(s);
            n2 = n;
        }
        double n3 = n2 + scaledResolution.getScaledWidth() - Ia.M(HUD.F, s, 1.0) - 2.0;
        if (HUD.side.e().equals("Left")) {
            n3 = 2.0;
        }
        ca.K().M(HUD.F).D(ga.M(this.e, this.L, this.k, this.f)).M(s, n3, atomicInteger2.doubleValue(), true);
        atomicInteger2.addAndGet(HUD.separation.M());
        atomicInteger.addAndGet(1);
    }
    
    private static boolean D(final Module module) {
        return module.A();
    }
    
    private String e() {
        final StringBuilder sb = new StringBuilder("");
        final te m;
        StringBuilder sb2;
        if ((m = me.M()).M() <= 0) {
            (sb2 = sb).append("Loading...");
        }
        else {
            final int n = 100;
            final int n2;
            if ((n2 = m.M() / n) * n < m.M()) {
                sb.append(String.format("%.2f", m.M().M()));
                if (n2 > 0) {
                    sb.append(", ");
                }
            }
            if (n2 > 0) {
                int n3;
                int i = n3 = n2;
                while (i > 0) {
                    sb.append(String.format("%.2f", m.M(n3 * n).M()));
                    if (n3 - 1 != 0) {
                        sb.append(", ");
                    }
                    i = --n3;
                }
            }
            sb2 = sb;
        }
        return sb2.toString();
    }
    
    public void M(final int n, final ScaledResolution scaledResolution, final ca ca) {
        if (HUD.arrayList.M()) {
            final AtomicInteger atomicInteger = new AtomicInteger(1);
            final AtomicInteger atomicInteger2 = new AtomicInteger(-100);
            if (HUD.order.e().equals("ABC")) {
                ModuleManager.M().stream().filter(NF::k).filter(WF::A).map((Function<? super NF, ?>)WF::M).forEach((Consumer<? super Object>)this::D);
            }
            if (HUD.order.e().equals("Length")) {
                if (HUD.customFont.M()) {
                    ModuleManager.M().stream().filter(NF::k).filter(WF::D).map((Function<? super NF, ?>)WF::D).sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)HUD.F::M).reversed()).forEach((Consumer<? super Object>)this::A);
                    return;
                }
                ModuleManager.M().stream().filter(NF::k).filter(WF::M).map((Function<? super NF, ?>)WF::A).sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)Wrapper.mc.fontRenderer::func_78256_a).reversed()).forEach((Consumer<? super Object>)this::M);
            }
        }
    }
    
    private void M(final String s, final String s2, final String s3, final String s4, final double n, final double n2, final double n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        Ia.M(HUD.F, s2, Ia.M(HUD.F, s3, 1.0) + Ia.M(HUD.F, s, 1.0) + n, scaledResolution.getScaledHeight() - n3, true, ga.M(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 255), 1.0);
        Ia.M(HUD.F, s4, Ia.M(HUD.F, s3, 1.0) + Ia.M(HUD.F, s2, 1.0) + Ia.M(HUD.F, s, 1.0) + n, scaledResolution.getScaledHeight() - n3, true, ga.G, 1.0);
    }
    
    private static boolean M(final Module module) {
        return module.A();
    }
    
    private void M(final String s, final String s2, final double n, final double n2, final int n3, final ScaledResolution scaledResolution) {
        Ia.M(HUD.F, s, n + scaledResolution.getScaledWidth() - Ia.M(HUD.F, s, 1.0) - Ia.M(HUD.F, s2, 1.0) - 2.0 + 1.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUD.F) - 13.0, true, n3, 1.0);
        Ia.M(HUD.F, s2, n + scaledResolution.getScaledWidth() - Ia.M(HUD.F, s2, 1.0) - 2.0, n2 + scaledResolution.getScaledHeight() - Ia.M(HUD.F) - 13.0, true, ga.G, 1.0);
    }
    
    public HUD() {
        super(Category.OTHER, "HUD", true, -1, "Shows all HUD features");
    }
    
    private static String A(final Module module) {
        return module.D();
    }
    
    private static String D(final Module module) {
        return module.D();
    }
    
    private void M(final String s, final double n, final double n2, final int n3) {
        Ia.M(HUD.F, s, n, n2, true, n3, 1.0);
    }
    
    private void M(final String s, final String s2, final double n, final double n2, final double n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        Ia.M(HUD.F, s, n, scaledResolution.getScaledHeight() - n3, true, ga.M(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 255), 1.0);
        Ia.M(HUD.F, s2, n + n2, scaledResolution.getScaledHeight() - n3, true, ga.G, 1.0);
    }
    
    @SubscribeEvent
    public void M(final RenderGameOverlayEvent$Text renderGameOverlayEvent$Text) {
        final ca ca = new ca();
        final Minecraft client = FMLClientHandler.instance().getClient();
        final ScaledResolution scaledResolution = new ScaledResolution(client);
        final int n = 1;
        int n2 = 1;
        final String format = String.format("%.2f", client.player.rotationPitch);
        final String format2 = String.format("%.2f", MathHelper.wrapDegrees(client.player.rotationYaw));
        final String format3 = String.format("%.3f", client.player.posY);
        final String format4 = String.format("%.3f", client.player.posX);
        final String format5 = String.format("%.3f", client.player.posZ);
        final String format6 = String.format("%.3f", client.player.posX / 8.0);
        final String format7 = String.format("%.3f", client.player.posZ / 8.0);
        String format8 = "";
        if (client.player.dimension == -1 && client.player.posX * 8.0 < 3.0E7 && client.player.posX * 8.0 > -3.0E7) {
            format8 = String.format("%.3f", client.player.posX * 8.0);
        }
        else if (client.player.posX * 8.0 > 3.0E7) {
            format8 = "30000000.000";
        }
        else if (client.player.posX * 8.0 < -3.0E7) {
            format8 = "-30000000";
        }
        String format9 = "";
        if (client.player.dimension == -1 && client.player.posZ * 8.0 < 3.0E7 && client.player.posZ * 8.0 > -3.0E7) {
            format9 = String.format("%.3f", client.player.posZ * 8.0);
        }
        else if (client.player.posZ * 8.0 > 3.0E7) {
            format9 = "30000000.000";
        }
        else if (client.player.posZ * 8.0 < -3.0E7) {
            format9 = "-30000000";
        }
        if (HUD.customFont.M()) {
            HUD.F = com.client.glowclient.g.L;
        }
        else {
            HUD.F = null;
        }
        if (HUD.armor.M()) {
            int n3 = 0;
            final ScaledResolution scaledResolution2 = scaledResolution;
            int n4 = scaledResolution2.getScaledWidth() / 2 + 129;
            final int scaledHeight = scaledResolution2.getScaledHeight();
            n4 -= 103;
            int n5 = scaledHeight - 55;
            if (Ob.M() instanceof EntityBoat) {
                final boolean k = ModuleManager.M("EntityHunger").k();
                final ScaledResolution scaledResolution3 = scaledResolution;
                if (k) {
                    n5 = scaledResolution3.getScaledHeight() - 55;
                }
                else {
                    n5 = scaledResolution3.getScaledHeight() - 45;
                }
            }
            Minecraft minecraft = null;
            Label_0709: {
                if (Ob.M() instanceof AbstractHorse) {
                    if (ModuleManager.M("EntityHunger").k()) {
                        final float n6 = fcmpl(((AbstractHorse)Ob.M()).getMaxHealth(), 21.0f);
                        final ScaledResolution scaledResolution4 = scaledResolution;
                        if (n6 > 0) {
                            n5 = scaledResolution4.getScaledHeight() - 75;
                            minecraft = client;
                            break Label_0709;
                        }
                        n5 = scaledResolution4.getScaledHeight() - 65;
                        minecraft = client;
                        break Label_0709;
                    }
                    else {
                        final float n7 = fcmpl(((AbstractHorse)Ob.M()).getMaxHealth(), 21.0f);
                        final ScaledResolution scaledResolution5 = scaledResolution;
                        if (n7 > 0) {
                            n5 = scaledResolution5.getScaledHeight() - 65;
                            minecraft = client;
                            break Label_0709;
                        }
                        n5 = scaledResolution5.getScaledHeight() - 55;
                    }
                }
                minecraft = client;
            }
            if (minecraft.player.isCreative()) {
                n5 = scaledResolution.getScaledHeight() - 40;
            }
            int n8 = 0;
            int n9 = 0;
            String value = "";
            int n10;
            int i = n10 = 0;
            while (i < client.player.inventory.getSizeInventory()) {
                final ItemStack stackInSlot;
                if ((stackInSlot = client.player.inventory.getStackInSlot(n10)) != null && stackInSlot.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                    value = String.valueOf(n9 = (n8 += stackInSlot.getCount()));
                }
                i = ++n10;
            }
            this.M(value, n4 - Ia.M(HUD.F, value, 1.0) - 11.0, n5 + 8, ga.G);
            int n11;
            int j = n11 = 0;
            while (j < 4) {
                final ItemStack m = UB.M(n11);
                final ca l = ca.K().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e);
                final ItemStack itemStack = m;
                final double n12 = n4 + 3 + 7.5 * n3;
                ++n3;
                final ca m2 = l.M(itemStack, n12, n5);
                final ItemStack itemStack2 = m;
                final double n13 = n4 - 5 + 7.5 * n3;
                ++n3;
                m2.D(itemStack2, n13, n5).M(com.client.glowclient.ca::M);
                if (n9 >= 1) {
                    ca.K().M(com.client.glowclient.ca::B).M(com.client.glowclient.ca::e).M(Items.TOTEM_OF_UNDYING.getDefaultInstance(), n4 - 14, n5).M(com.client.glowclient.ca::M);
                }
                final double n14 = n4 + 4 + n11 * 16;
                double n15 = n5 - 2;
                final List<X> m3;
                if ((m3 = Ea.M(m.getEnchantmentTagList())) != null) {
                    final Iterator<X> iterator = m3.iterator();
                Label_1036:
                    while (true) {
                        Iterator<X> iterator2 = iterator;
                        while (iterator2.hasNext()) {
                            Ia.M(HUD.F, iterator.next().M(), n14, n15 - 1.0, true, ga.G, 0.5);
                            final double n16 = n15 -= 4.5;
                            if (n16 > n16) {
                                continue Label_1036;
                            }
                            iterator2 = iterator;
                        }
                        break;
                    }
                }
                j = ++n11;
            }
        }
        if (HUD.watermark.M()) {
            double n17 = n;
            final double n18 = n2;
            final String s = "GlowClient";
            if (HUD.side.e().equals("Left")) {
                n17 = scaledResolution.getScaledWidth() - 1 - Ia.M(HUD.F, s, 1.0);
            }
            final String s2 = s;
            final double n19 = n17;
            final double n20 = n18;
            final int n21 = 0;
            final int n22 = 150;
            final int n23 = 255;
            this.M(s2, n19, n20, ga.M(n21, n22, n23, n23));
            if (HUD.side.e().equals("Right")) {
                Ia.M(HUD.F, HUD.G, n17 + Ia.M(HUD.F, s, 1.0) + 1.0, n18, true, ga.G, 0.7);
            }
            if (HUD.side.e().equals("Left")) {
                Ia.M(HUD.F, HUD.G, n17 - Ia.M(HUD.F, s, 1.25) - 1.0, n18, true, ga.G, 0.7);
            }
        }
        n2 += 9;
        if (client.currentScreen instanceof GuiChat) {
            n2 -= 13;
        }
        final String format10 = String.format("%.3f", u.M((Entity)client.player));
        final EnumFacing horizontalFacing = client.getRenderViewEntity().getHorizontalFacing();
        String m4 = "Loading...";
        switch (mF.b[horizontalFacing.ordinal()]) {
            case 1: {
                final String s3 = "6\u00157e";
                while (false) {}
                m4 = ja.M(s3);
                break;
            }
            case 2: {
                m4 = "[+Z]";
                break;
            }
            case 3: {
                m4 = "[-X]";
                break;
            }
            case 4: {
                m4 = "[+X]";
                break;
            }
        }
        String m5 = "Loading...";
        switch (mF.b[horizontalFacing.ordinal()]) {
            case 1: {
                final String s4 = "a\u0002]\u0019G";
                while (false) {}
                m5 = Ta.M(s4);
                break;
            }
            case 2: {
                m5 = "South";
                break;
            }
            case 3: {
                m5 = "West";
                break;
            }
            case 4: {
                m5 = "East";
                break;
            }
        }
        if (HUD.speed.M()) {
            final String s5 = "Speed: ";
            final String string = new StringBuilder().insert(0, format10).append("m/s").toString();
            final double n24 = n;
            final double n25 = n2;
            final int m6 = HUD.red.M();
            final int m7 = HUD.green.M();
            final int m8 = HUD.blue.M();
            final int n26 = 255;
            n2 -= 9;
            this.M(s5, string, n24, n25, ga.M(m6, m7, m8, n26), scaledResolution);
        }
        if (HUD.tPS.M()) {
            final String s6 = "TPS: ";
            final String e = this.e();
            final double n27 = n;
            final double n28 = n2;
            final int m9 = HUD.red.M();
            final int m10 = HUD.green.M();
            final int m11 = HUD.blue.M();
            final int n29 = 255;
            n2 -= 9;
            this.M(s6, e, n27, n28, ga.M(m9, m10, m11, n29), scaledResolution);
        }
        if (HUD.rotation.M()) {
            final String s7 = "Pitch: ";
            final String s8 = format;
            final double n30 = n;
            final double n31 = n2;
            final int m12 = HUD.red.M();
            final int m13 = HUD.green.M();
            final int m14 = HUD.blue.M();
            final int n32 = 255;
            n2 -= 9;
            this.M(s7, s8, n30, n31, ga.M(m12, m13, m14, n32), scaledResolution);
            final String s9 = "Yaw: ";
            final String s10 = format2;
            final double n33 = n;
            final double n34 = n2;
            final int m15 = HUD.red.M();
            final int m16 = HUD.green.M();
            final int m17 = HUD.blue.M();
            final int n35 = 255;
            n2 -= 9;
            this.M(s9, s10, n33, n34, ga.M(m15, m16, m17, n35), scaledResolution);
        }
        if (HUD.direction.M()) {
            final String string2 = m5 + " ";
            final String s11 = m4;
            final double n36 = n;
            final double n37 = n2;
            final int m18 = HUD.red.M();
            final int m19 = HUD.green.M();
            final int m20 = HUD.blue.M();
            final int n38 = 255;
            n2 -= 9;
            this.M(string2, s11, n36, n37, ga.M(m18, m19, m20, n38), scaledResolution);
        }
        if (HUD.coordinates.M()) {
            int n39 = 28;
            if (client.currentScreen instanceof GuiChat) {
                n39 += 12;
            }
            this.M("X: ", format4, n, 10.0, n39);
            final String s12 = "Y: ";
            final String s13 = format3;
            n39 -= 9;
            this.M(s12, s13, n, 10.0, n39);
            final String s14 = "Z: ";
            final String s15 = format5;
            n39 -= 9;
            this.M(s14, s15, n, 10.0, n39);
            if (HUD.dimensionCoords.M()) {
                if (client.player.dimension == 0) {
                    int n40 = 28;
                    if (client.currentScreen instanceof GuiChat) {
                        n40 += 12;
                    }
                    this.M("X: ", "NetherX: ", format4, format6, n, 1.0, n40);
                    final String s16 = "Z: ";
                    final String s17 = "a\b[\u0005J\u001fuW\u000f";
                    n40 -= 18;
                    this.M(s16, Ta.M(s17), format5, format7, n, 1.0, n40);
                }
                if (client.player.dimension == -1) {
                    int n41 = 28;
                    if (client.currentScreen instanceof GuiChat) {
                        n41 += 12;
                    }
                    this.M("X: ", "OwX: ", format4, format8, n, 1.0, n41);
                    final String s18 = "Z: ";
                    final String s19 = "`\u001auW\u000f";
                    n41 -= 18;
                    this.M(s18, Ta.M(s19), format5, format9, n, 1.0, n41);
                }
            }
        }
        this.M(n, scaledResolution, ca);
        final qe[] windows;
        final int length = (windows = iD.M().getWindows()).length;
        int n43;
        int n42 = n43 = 0;
        while (n42 < length) {
            final qe qe = windows[n43];
            if (!(HUD.B.currentScreen instanceof iD) && !qe.B && qe.K.M()) {
                final int d = qe.D();
                final qe qe2 = qe;
                qe2.M(d, qe2.E(), false);
            }
            n42 = ++n43;
        }
    }
    
    static {
        HUD.separation = ValueFactory.M("HUD", "Separation", "Amount of Separation", 10.0, 1.0, 0.0, 15.0);
        HUD.coordinates = ValueFactory.M("HUD", "Coordinates", "Displays coordinates", true);
        HUD.rotation = ValueFactory.M("HUD", "Rotation", "Displays rotation data", true);
        HUD.watermark = ValueFactory.M("HUD", "Watermark", "Displays watermark", true);
        HUD.tPS = ValueFactory.M("HUD", "TPS", "Displays ticks per second", true);
        HUD.dimensionCoords = ValueFactory.M("HUD", "DimensionCoords", "Ow/Nether Coordinates", true);
        HUD.arrayList = ValueFactory.M("HUD", "ArrayList", "Shows Mods", true);
        HUD.armor = ValueFactory.M("HUD", "Armor", "Displays armor being worn", true);
        HUD.order = ValueFactory.M("HUD", "Order", "Order of arraylist", "Length", "Length", "ABC");
        HUD.side = ValueFactory.M("HUD", "Side", "Side of the arraylist", "Right", "Right", "Left");
        HUD.speed = ValueFactory.M("HUD", "Speed", "Speed in m/s", true);
        HUD.direction = ValueFactory.M("HUD", "Direction", "Shows NSEW, XZ direction", true);
        HUD.customFont = ValueFactory.M("HUD", "CustomFont", "Enables the sexy font", true);
        HUD.descriptions = ValueFactory.M("HUD", "Descriptions", "Shows GUI Descriptions for arraylist", true);
        HUD.color = ValueFactory.M("HUD", "Color", "Mode of color", "HUD", "HUD", "Category", "Random", "Rainbow");
        final String s = "HUD";
        final String s2 = "Red";
        final String s3 = "Red";
        final double n = 1.0;
        final double n2 = 0.0;
        HUD.red = ValueFactory.M(s, s2, s3, n2, n, n2, 255.0);
        HUD.green = ValueFactory.M("HUD", "Green", "Green", 140.0, 1.0, 0.0, 255.0);
        HUD.blue = ValueFactory.M("HUD", "Blue", "Blue", 255.0, 1.0, 0.0, 255.0);
        HUD.F = g.L;
        G = Glow.D();
    }
    
    private void M(final String s) {
        if (HUD.color.e().equals("HUD")) {
            final int f = 255;
            this.e = HUD.red.M();
            this.L = HUD.green.M();
            this.k = HUD.blue.M();
            this.f = f;
        }
        if (HUD.color.e().equals("Random")) {
            for (final Module module : ModuleManager.M()) {
                if (!s.equals(module.k())) {
                    if (s.contains(module.k())) {
                        final Module module2 = module;
                        final Module module3 = module;
                        this.e = module3.A;
                        this.L = module3.l;
                        this.k = module2.b;
                    }
                    this.f = 200;
                }
                else {
                    final int f2 = 200;
                    final Module module4 = module;
                    final Module module5 = module;
                    this.e = module5.A;
                    this.L = module5.l;
                    this.k = module4.b;
                    this.f = f2;
                }
            }
        }
        if (HUD.color.e().equals("Category")) {
            for (final Module module6 : ModuleManager.M()) {
                if (!s.equals(module6.k())) {
                    if (!s.contains(module6.k())) {
                        continue;
                    }
                    if (module6.M().D().equals("Combat")) {
                        final int k = 0;
                        final int l = 50;
                        this.e = 255;
                        this.L = l;
                        this.k = k;
                    }
                    if (module6.M().D().equals("Player")) {
                        final int i = 0;
                        final int j = 150;
                        this.e = 255;
                        this.L = j;
                        this.k = i;
                    }
                    if (module6.M().D().equals("Render")) {
                        final int m = 255;
                        final int l2 = 50;
                        this.e = 150;
                        this.L = l2;
                        this.k = m;
                    }
                    if (module6.M().D().equals("Server")) {
                        final int k2 = 50;
                        final int l3 = 200;
                        this.e = 0;
                        this.L = l3;
                        this.k = k2;
                    }
                    if (module6.M().D().equals("Movement")) {
                        final int k3 = 255;
                        final int l4 = 140;
                        this.e = 50;
                        this.L = l4;
                        this.k = k3;
                    }
                    this.f = 200;
                }
                else {
                    if (module6.M().D().equals("Combat")) {
                        final int k4 = 0;
                        final int l5 = 50;
                        this.e = 255;
                        this.L = l5;
                        this.k = k4;
                    }
                    if (module6.M().D().equals("Player")) {
                        final int k5 = 0;
                        final int l6 = 150;
                        this.e = 255;
                        this.L = l6;
                        this.k = k5;
                    }
                    if (module6.M().D().equals("Render")) {
                        final int k6 = 255;
                        final int l7 = 50;
                        this.e = 150;
                        this.L = l7;
                        this.k = k6;
                    }
                    if (module6.M().D().equals("Server")) {
                        final int k7 = 50;
                        final int l8 = 200;
                        this.e = 0;
                        this.L = l8;
                        this.k = k7;
                    }
                    if (module6.M().D().equals("Movement")) {
                        final int k8 = 255;
                        final int l9 = 140;
                        this.e = 50;
                        this.L = l9;
                        this.k = k8;
                    }
                    this.f = 200;
                }
            }
        }
    }
    
    private static String M(final Module module) {
        return module.D();
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
