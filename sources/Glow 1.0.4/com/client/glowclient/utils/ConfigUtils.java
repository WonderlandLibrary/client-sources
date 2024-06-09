package com.client.glowclient.utils;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import com.client.glowclient.*;
import java.nio.file.*;

public class ConfigUtils
{
    public final File L;
    private static final ConfigUtils A;
    public final File B;
    private final File b;
    
    public void d() {
        final String m = xc.M();
        try {
            final File file;
            if ((file = new File(new StringBuilder().insert(0, this.L.getPath()).append(File.separatorChar).append("Config.json").toString())).exists()) {
                file.delete();
            }
            file.createNewFile();
            IOUtils.write(m.getBytes(), new FileOutputStream(file));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void B() {
        try {
            final File file;
            if (!(file = new File(this.L, "Enemies.json")).exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
            final InputStreamReader inputStreamReader;
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            inputStreamReader = new InputStreamReader(dataInputStream);
            final BufferedReader bufferedReader2 = bufferedReader;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                wa.M().D(line.trim().split(":")[0]);
            }
            bufferedReader2.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void E() {
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(this.L, "Enemies.json")));
            wa.M();
            Iterator<ya> iterator2;
            final Iterator<ya> iterator = iterator2 = wa.b.iterator();
            while (iterator2.hasNext()) {
                final ya ya = iterator.next();
                iterator2 = iterator;
                final String s = "@{";
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(ya.M());
                bufferedWriter2.write(SA.M(s));
            }
            bufferedWriter.close();
        }
        catch (Exception ex) {}
    }
    
    public void e() {
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(this.L, "Friends.json")));
            Va.M();
            Iterator<xa> iterator2;
            final Iterator<xa> iterator = iterator2 = Va.b.iterator();
            while (iterator2.hasNext()) {
                final xa xa = iterator.next();
                iterator2 = iterator;
                final String s = "@{";
                final BufferedWriter bufferedWriter2 = bufferedWriter;
                bufferedWriter2.write(xa.M());
                bufferedWriter2.write(SA.M(s));
            }
            bufferedWriter.close();
        }
        catch (Exception ex) {}
    }
    
    public void k() {
        String s = null;
        final File file = new File(new StringBuilder().insert(0, this.L.getPath()).append(File.separatorChar).append("Config.json").toString());
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                final String s2 = "6\f";
                this.d();
                s = SA.M(s2);
            }
            else {
                s = IOUtils.toString(new FileInputStream(file));
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        xc.M(s);
    }
    
    public void A() {
        this.k();
        this.D();
        this.B();
    }
    
    public static File M() {
        return Paths.get("", new String[0]).toAbsolutePath().toFile();
    }
    
    public void D() {
        try {
            final File file;
            if (!(file = new File(this.L, "Friends.json")).exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
            final InputStreamReader inputStreamReader;
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            inputStreamReader = new InputStreamReader(dataInputStream);
            final BufferedReader bufferedReader2 = bufferedReader;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Va.M().D(line.trim().split(":")[0]);
            }
            bufferedReader2.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private ConfigUtils() {
        super();
        (this.b = new File(M(), "config/glowclient")).mkdirs();
        this.L = new File(M(), "config/glowclient/settings");
        this.b.mkdirs();
        this.B = new File(M(), "mods/1.12.2");
    }
    
    static {
        A = new ConfigUtils();
    }
    
    public static ConfigUtils M() {
        return ConfigUtils.A;
    }
    
    public void M() {
        this.d();
        this.e();
        this.E();
    }
}
