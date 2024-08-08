package net.futureclient.client;

import java.util.List;
import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;

public class ge extends dg<rD>
{
    public ge() {
        super();
        this.k = (List<T>)new ArrayList<Object>();
        new NF(this, "friends.json");
    }
    
    public String e(String replace) {
        final ChatFormatting aqua = ChatFormatting.AQUA;
        for (final rD rd : this.k) {
            if (replace.contains(rd.M())) {
                replace = replace.replace(rd.M(), String.format("%s%s§r", aqua, rd.e()));
            }
        }
        return replace;
    }
    
    public boolean M(final String s) {
        for (final rD rd : this.k) {
            if (s.equalsIgnoreCase(rd.M()) || s.equalsIgnoreCase(rd.e())) {
                return true;
            }
        }
        return false;
    }
    
    public rD M(final String s) {
        for (final rD rd : this.M()) {
            if (s.equalsIgnoreCase(rd.M()) || s.equalsIgnoreCase(rd.e())) {
                return rd;
            }
        }
        return null;
    }
}
