package com.client.glowclient;

import java.util.concurrent.atomic.*;
import joptsimple.internal.*;
import com.client.glowclient.utils.*;
import java.util.*;
import net.minecraft.util.text.*;

public class qd
{
    private static final int B = 1;
    private static final ThreadLocal<AtomicInteger> b;
    
    private static AtomicInteger M() {
        AtomicInteger atomicInteger;
        if ((atomicInteger = qd.b.get()) == null) {
            atomicInteger = new AtomicInteger(1);
            qd.b.set(atomicInteger);
        }
        return atomicInteger;
    }
    
    public static void D(final String s) {
        M(s, (Style)null);
    }
    
    private static void D(final String s, final Style style) {
        final String string = new StringBuilder().insert(0, Strings.repeat('\uf802', Math.max(M().get(), 1))).append("§f").toString();
        if (style == null) {
            M(string, s);
            return;
        }
        M(string, s, style);
    }
    
    private static void M(final String s, final String s2, final Style style) {
        M(s, s2, style, style);
    }
    
    public static void k() {
        qd.b.remove();
    }
    
    public static void A() {
        M().decrementAndGet();
    }
    
    private static void M(final String s, final String string, final Style style, Style style2) {
        if (Wrapper.mc.player != null && !com.google.common.base.Strings.isNullOrEmpty(string)) {
            if (string.contains("\n")) {
                Scanner scanner2;
                final Scanner scanner;
                (scanner = (scanner2 = new Scanner(string))).useDelimiter("\n");
                Style style3 = style;
                style2 = style2;
                while (scanner2.hasNext()) {
                    final Style style4 = style3;
                    final Style style5 = style2;
                    M(s, scanner.next(), style3, style2);
                    style3 = style5;
                    style2 = style4;
                    scanner2 = scanner;
                }
            }
            else {
                final TextComponentString textComponentString;
                (textComponentString = new TextComponentString(new StringBuilder().insert(0, s).append(string.replaceAll("\r", "")).toString())).setStyle(style);
                Wrapper.mc.player.sendMessage((ITextComponent)textComponentString);
            }
        }
    }
    
    public static void M(final String s) {
        D(s, null);
    }
    
    public static void D() {
        M().set(1);
    }
    
    public static int M() {
        return M().get();
    }
    
    public static void M(final int n) {
        M().set(n);
    }
    
    public static void M(final String s, final Style style) {
        final String string = new StringBuilder().insert(0, Strings.repeat('\uf802', Math.max(M().get(), 1))).append("§9[GlowClient]§7 ").toString();
        if (style == null) {
            M(string, s);
            return;
        }
        M(string, s, style);
    }
    
    private static void M(final String s, final String s2) {
        M(s, s2, new Style().setColor(TextFormatting.GRAY), new Style().setColor(TextFormatting.GRAY));
    }
    
    public static void M() {
        M().incrementAndGet();
    }
    
    static {
        b = new ThreadLocal<AtomicInteger>();
    }
    
    public qd() {
        super();
    }
}
