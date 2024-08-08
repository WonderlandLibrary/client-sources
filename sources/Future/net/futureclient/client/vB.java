package net.futureclient.client;

import net.minecraft.world.DimensionType;
import java.util.Arrays;
import net.futureclient.client.modules.render.Waypoints;

public class vB extends XB
{
    public final Waypoints k;
    
    public vB(final Waypoints k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length >= 2 && array[0].equalsIgnoreCase("add")) {
            final String s = array[1];
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
            vB vb;
            double n;
            double n2;
            double n3;
            if (array2.length >= 5) {
                vb = this;
                n = Double.parseDouble(array[2]);
                n2 = Double.parseDouble(array[3]);
                n3 = Double.parseDouble(array[4]);
            }
            else {
                vb = this;
                n = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posX).replaceAll(",", "."));
                n2 = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posY).replaceAll(",", "."));
                n3 = Double.parseDouble(Waypoints.M(this.k).format(this.k.player.posZ).replaceAll(",", "."));
            }
            String s2 = vb.k.world.provider.getDimensionType().getName();
            if (array.length == 6) {
                final String lowerCase = array[5].toLowerCase();
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
        if (array.length != 2 || (!array[0].equalsIgnoreCase("del") && !array[0].equalsIgnoreCase("remove"))) {
            return null;
        }
        final Xa e;
        if ((e = this.k.e(array[1])) == null) {
            return "Invalid waypoint entered.";
        }
        if (Waypoints.M(this.k, e)) {
            this.k.k.remove(e);
        }
        return String.format("Removed waypoint &e%s&7.", e.M());
    }
    
    @Override
    public String M() {
        return "&e[add|del] [name] | [x] [y] [z] [dimension]";
    }
}
