package net.futureclient.client;

import net.minecraft.world.DimensionType;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import net.futureclient.client.modules.render.Waypoints;

public class gB extends q
{
    public final Waypoints k;
    
    public gB(final Waypoints k, final String s) {
        this.k = k;
        super(s);
    }
    
    @Override
    public void e(final Object... array) {
        try {
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.e()));
            final Iterator<Xa> iterator2;
            Iterator<Xa> iterator = iterator2 = this.k.k.iterator();
            while (iterator.hasNext()) {
                final Xa xa = iterator2.next();
                iterator = iterator2;
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(xa.M() + ":" + xa.b() + ":" + Waypoints.M(this.k).format(xa.b()) + ":" + Waypoints.M(this.k).format(xa.e()) + ":" + Waypoints.M(this.k).format(xa.M()) + ":" + xa.e());
                bufferedWriter2.newLine();
            }
            bufferedWriter.close();
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    @Override
    public void M(final Object... array) {
        this.k.k.clear();
        try {
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!this.e().exists()) {
            return;
        }
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.e()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] split = line.split(":");
                try {
                    String s = "overworld";
                    if (split.length >= 6 && (split[5].equalsIgnoreCase(DimensionType.OVERWORLD.getName()) || split[5].equalsIgnoreCase(DimensionType.NETHER.getName()) || split[5].equalsIgnoreCase(DimensionType.THE_END.getName()))) {
                        s = split[5];
                    }
                    if (this.k.k.contains(new Xa(split[0], split[1], Double.parseDouble(split[2].replaceAll(",", ".")), Double.parseDouble(split[3].replaceAll(",", ".")), Double.parseDouble(split[4].replaceAll(",", ".")), s))) {
                        continue;
                    }
                    this.k.k.add(new Xa(split[0], split[1], Double.parseDouble(split[2].replaceAll(",", ".")), Double.parseDouble(split[3].replaceAll(",", ".")), Double.parseDouble(split[4].replaceAll(",", ".")), s));
                }
                catch (Exception ex2) {
                    ex2.printStackTrace();
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
        }
    }
}
