package net.futureclient.client;

import java.util.Iterator;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import org.lwjgl.input.Keyboard;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class dD extends q
{
    public final Ce k;
    
    public dD(final Ce k, final String s) {
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
            final Object iterator;
            Object o = iterator = this.k.k.iterator();
            while (((Iterator)o).hasNext()) {
                final LF lf = ((Iterator<LF>)iterator).next();
                o = iterator;
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(Keyboard.getKeyName(lf.M()) + ":" + lf.M());
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
                Label_0109: {
                    int n;
                    try {
                        n = Integer.parseInt(split[0]);
                        final dD dd = this;
                        break Label_0109;
                    }
                    catch (NumberFormatException ex4) {
                        n = Keyboard.getKeyIndex(split[0]);
                        final dD dd = this;
                    }
                    try {
                        final dD dd = this;
                        dd.k.k.add((T)new LF(n, split[1]));
                    }
                    catch (Exception ex2) {
                        ex2.printStackTrace();
                    }
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
        }
    }
}
