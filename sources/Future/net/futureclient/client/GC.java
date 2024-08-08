package net.futureclient.client;

import net.minecraft.world.DimensionType;
import java.util.Arrays;
import net.futureclient.client.modules.render.Waypoints;

public class GC extends XB
{
    public final Waypoints k;
    
    public GC(final Waypoints k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M() {
        return "&e[name] | [x] [y] [z] [dimension]";
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length >= 1) {
            final String s = array[0];
            String replaceAll = "";
            String[] array2;
            if (this.k.isSingleplayer()) {
                replaceAll = "singleplayer";
                array2 = array;
            }
            else if (this.k.getCurrentServerData() != null) {
                replaceAll = this.k.getCurrentServerData().serverIP.replaceAll(":", "_");
                array2 = array;
            }
            else {
                if (this.k.isConnectedToRealms()) {
                    replaceAll = "realms";
                }
                array2 = array;
            }
            GC gc;
            double n;
            double n2;
            double n3;
            if (array2.length >= 4) {
                gc = this;
                n = Double.parseDouble(array[1]);
                n2 = Double.parseDouble(array[2]);
                n3 = Double.parseDouble(array[3]);
            }
            else {
                gc = this;
                n = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posX).replaceAll(",", "."));
                n2 = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posY).replaceAll(",", "."));
                n3 = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posZ).replaceAll(",", "."));
            }
            String s2 = gc.k.world.provider.getDimensionType().getName();
            if (array.length == 5) {
                final String lowerCase = array[4].toLowerCase();
                final String[] array3 = { "overworld", "world", "ow" };
                final String[] array4 = { "nether", "the_nether", "nethe", "n" };
                final String[] array5 = { "end", "the_end", "en", "e" };
                if (Arrays.<String>asList(array3).contains(lowerCase)) {
                    s2 = DimensionType.OVERWORLD.getName();
                }
                else if (Arrays.<String>asList(array4).contains(lowerCase)) {
                    s2 = DimensionType.NETHER.getName();
                }
                else {
                    if (!Arrays.<String>asList(array5).contains(lowerCase)) {
                        return "Invalid dimension type entered.";
                    }
                    s2 = DimensionType.THE_END.getName();
                }
            }
            final Xa xa = new Xa(s, replaceAll, n, n2, n3, s2);
            if (!Waypoints.M(this.k, xa)) {
                this.k.k.add(xa);
            }
            return String.format("Added waypoint &e%s&7.", xa.M());
        }
        return null;
    }
}
