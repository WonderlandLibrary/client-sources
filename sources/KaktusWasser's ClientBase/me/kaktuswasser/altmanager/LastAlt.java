// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.altmanager;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.file.BasicFile;
import me.kaktuswasser.client.utilities.Alt;

import java.io.BufferedReader;
import java.io.FileReader;

public class LastAlt extends BasicFile
{
    public LastAlt() {
        super("lastalt");
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
                        Client.getAltManager().setLastAlt(new Alt(account[0], account[1], parts[0]));
                    }
                    else {
                        String pw = account[1];
                        for (int i = 2; i < account.length; ++i) {
                            pw = String.valueOf(pw) + ":" + account[i];
                        }
                        Client.getAltManager().setLastAlt(new Alt(account[0], pw, parts[0]));
                    }
                }
                else {
                    final String[] account2 = s.split(":");
                    if (account2.length == 1) {
                        Client.getAltManager().setLastAlt(new Alt(account2[0], ""));
                    }
                    else if (account2.length == 2) {
                        Client.getAltManager().setLastAlt(new Alt(account2[0], account2[1]));
                    }
                    else {
                        String pw2 = account2[1];
                        for (int j = 2; j < account2.length; ++j) {
                            pw2 = String.valueOf(pw2) + ":" + account2[j];
                        }
                        Client.getAltManager().setLastAlt(new Alt(account2[0], pw2));
                    }
                }
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    @Override
    public void saveFile() {
        try {
            final PrintWriter printWriter = new PrintWriter(this.getFile());
            final Alt alt = Client.getAltManager().getLastAlt();
            if (alt != null) {
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
