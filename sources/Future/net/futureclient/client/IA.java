package net.futureclient.client;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import net.futureclient.client.modules.miscellaneous.InvCleaner;

public class IA extends q
{
    public final InvCleaner k;
    
    public IA(final InvCleaner k, final String s) {
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
            final Iterator<String> iterator2;
            Iterator<String> iterator = iterator2 = this.k.d.iterator();
            while (iterator.hasNext()) {
                final String s = iterator2.next();
                iterator = iterator2;
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(s.toLowerCase());
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
        this.k.d.clear();
        try {
            if (this.e().length() > 6000L && !this.e().delete()) {
                this.e().deleteOnExit();
            }
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
        Label_0099:
            while (true) {
                BufferedReader bufferedReader2 = bufferedReader;
                String line;
                while ((line = bufferedReader2.readLine()) != null) {
                    try {
                        if (!this.k.d.contains(line.toLowerCase())) {
                            this.k.d.add(line.toLowerCase());
                            continue Label_0099;
                        }
                        continue Label_0099;
                    }
                    catch (Exception ex2) {
                        bufferedReader2 = bufferedReader;
                        ex2.printStackTrace();
                        continue;
                    }
                    break;
                }
                break;
            }
            bufferedReader.close();
        }
        catch (Exception ex3) {
            ex3.printStackTrace();
        }
    }
}
