// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.altmanager;

import java.util.Iterator;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.andrewsnetwork.icarus.utilities.Alt;
import net.andrewsnetwork.icarus.Icarus;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import net.andrewsnetwork.icarus.file.BasicFile;

public class Alts extends BasicFile
{
    public Alts() {
        super("alts");
    }
    
    @Override
    public void loadFile() {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.getFile()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                if (s.contains("\t")) {
                    s = s.replace("\t", "    ");
                }
                if (s.contains("    ")) {
                    final String[] parts = s.split("    ");
                    final String[] account = parts[1].split(":");
                    if (account.length == 2) {
                        Icarus.getAltManager().getAlts().add(new Alt(account[0], account[1], parts[0]));
                    }
                    else {
                        String pw = account[1];
                        for (int i = 2; i < account.length; ++i) {
                            pw = String.valueOf(pw) + ":" + account[i];
                        }
                        Icarus.getAltManager().getAlts().add(new Alt(account[0], pw, parts[0]));
                    }
                }
                else {
                    final String[] account2 = s.split(":");
                    if (account2.length == 1) {
                        Icarus.getAltManager().getAlts().add(new Alt(account2[0], ""));
                    }
                    else if (account2.length == 2) {
                        try {
                            Icarus.getAltManager().getAlts().add(new Alt(account2[0], account2[1]));
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        String pw2 = account2[1];
                        for (int j = 2; j < account2.length; ++j) {
                            pw2 = String.valueOf(pw2) + ":" + account2[j];
                        }
                        Icarus.getAltManager().getAlts().add(new Alt(account2[0], pw2));
                    }
                }
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
    
    @Override
    public void saveFile() {
        try {
            final PrintWriter printWriter = new PrintWriter(this.getFile());
            for (final Alt alt : Icarus.getAltManager().getAlts()) {
                if (alt.getMask().equals("")) {
                    printWriter.println(String.valueOf(alt.getUsername()) + ":" + alt.getPassword());
                }
                else {
                    printWriter.println(String.valueOf(alt.getMask()) + "    " + alt.getUsername() + ":" + alt.getPassword());
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
