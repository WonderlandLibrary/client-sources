package net.futureclient.client;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Ih extends q
{
    public final aj k;
    
    public Ih(final aj k, final String s) {
        this.k = k;
        super(s);
    }
    
    @Override
    public void e(final Object... array) {
        try {
            if (aj.M(this.k).isEmpty()) {
                return;
            }
            if (!this.e().exists()) {
                this.e().createNewFile();
            }
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.e()));
            bufferedWriter.write(aj.M(this.k));
            bufferedWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void M(final Object... array) {
        try {
            if (!this.e().exists()) {
                return;
            }
            final String line;
            if ((line = new BufferedReader(new FileReader(this.e())).readLine()) != null) {
                aj.M(this.k, line);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
