package net.futureclient.client;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Fh extends q
{
    public final aj k;
    
    public Fh(final aj k, final String s) {
        this.k = k;
        super(s);
    }
    
    @Override
    public void e(final Object... array) {
        try {
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.e()));
            for (final ci ci : this.k.M()) {
                switch (Og.k[ci.M().ordinal()]) {
                    case 1:
                        bufferedWriter.write(String.format("%s", ci.i()));
                        bufferedWriter.newLine();
                        continue;
                    case 2:
                        bufferedWriter.write(String.format("%s:%s", ci.i(), ci.b()));
                        bufferedWriter.newLine();
                        continue;
                    case 3:
                        bufferedWriter.write(String.format("%s:%s:%s", ci.i(), ci.M(), ci.C()));
                        bufferedWriter.newLine();
                        continue;
                    case 4:
                        bufferedWriter.write(String.format("%s:%s:%s:%s:%s", ci.B(), ci.e(), ci.M(), ci.i(), ci.b()));
                        bufferedWriter.newLine();
                        continue;
                }
            }
            bufferedWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void M(final Object... array) {
        this.k.k.clear();
        try {
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.e()));
            this.k.M().clear();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final String[] split;
                switch ((split = line.split(":")).length) {
                    case 1:
                        this.k.e((Object)new ci(line));
                        continue;
                    case 2:
                        this.k.e((Object)new ci(split[0], split[1]));
                        continue;
                    case 3:
                        this.k.e((Object)new ci(split[0], split[1], split[2]));
                        continue;
                    case 5:
                        this.k.e((Object)new ci(split[0], split[1], split[2], split[3], split[4]));
                        continue;
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
