package com.client.glowclient.modules.server;

import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import java.util.*;
import com.client.glowclient.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.text.*;

public class SignMod extends ModuleContainer
{
    public static BooleanValue data;
    public static BooleanValue colors;
    public static final NumberValue length;
    
    private static String M(final int n) {
        return String.valueOf((char)n);
    }
    
    @Override
    public void E() {
        if (SignMod.colors.M()) {
            HookTranslator.v4 = false;
        }
    }
    
    @Override
    public void D() {
        if (SignMod.colors.M()) {
            HookTranslator.v4 = true;
        }
    }
    
    private static int M(final int n) {
        if (n < 55296) {
            return n;
        }
        return n + 2048;
    }
    
    public SignMod() {
        super(Category.SERVER, "SignMod", false, -1, "Multiple options for sign edits");
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (SignMod.colors.M()) {
            HookTranslator.v4 = true;
            return;
        }
        HookTranslator.v4 = false;
    }
    
    static {
        final String s = "SignMod";
        final String s2 = "Length";
        final String s3 = "Amount of room to type on a sign while writing";
        final double n = 5.0;
        final double n2 = 90.0;
        length = ValueFactory.M(s, s2, s3, n2, n, n2, 180.0);
        SignMod.colors = ValueFactory.M("SignMod", "Colors", "Use color codes on signs", true);
        SignMod.data = ValueFactory.M("SignMod", "Data", "Forces the sign to have as much data as possible", false);
    }
    
    @SubscribeEvent
    public void M(final Cd cd) {
        final int n = 384;
        if (SignMod.data.M()) {
            final String s = new Random().ints(128, 1112063).map(Hf::M).limit(10500L).mapToObj((IntFunction<?>)Hf::M).collect((Collector<? super Object, ?, String>)Collectors.joining());
            if (cd.getPacket() instanceof CPacketUpdateSign) {
                final ITextComponent[] array = { (ITextComponent)new TextComponentString(s.substring(1 * n, 2 * n)), (ITextComponent)new TextComponentString(s.substring(2 * n, 3 * n)), (ITextComponent)new TextComponentString(s.substring(3 * n, 4 * n)), (ITextComponent)new TextComponentString(s.substring(4 * n, 5 * n)) };
                final CPacketUpdateSign cPacketUpdateSign = (CPacketUpdateSign)cd.getPacket();
                final String[] lines = new String[4];
                final ITextComponent[] array2 = array;
                final int n2 = 0;
                lines[n2] = array2[n2].getUnformattedText();
                final ITextComponent[] array3 = array;
                final int n3 = 1;
                lines[n3] = array3[n3].getUnformattedText();
                final ITextComponent[] array4 = array;
                final int n4 = 2;
                lines[n4] = array4[n4].getUnformattedText();
                final ITextComponent[] array5 = array;
                final int n5 = 3;
                lines[n5] = array5[n5].getUnformattedText();
                cPacketUpdateSign.lines = lines;
            }
        }
    }
}
