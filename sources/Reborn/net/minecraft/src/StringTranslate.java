package net.minecraft.src;

import java.io.*;
import java.util.*;

public class StringTranslate
{
    private static StringTranslate instance;
    private Properties translateTable;
    private TreeMap languageList;
    private TreeMap field_94521_d;
    private String currentLanguage;
    private boolean isUnicode;
    
    static {
        StringTranslate.instance = new StringTranslate("en_US");
    }
    
    public StringTranslate(final String par1Str) {
        this.translateTable = new Properties();
        this.field_94521_d = new TreeMap();
        this.loadLanguageList();
        this.setLanguage(par1Str, false);
    }
    
    public static StringTranslate getInstance() {
        return StringTranslate.instance;
    }
    
    private void loadLanguageList() {
        final TreeMap var1 = new TreeMap();
        try {
            final BufferedReader var2 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/languages.txt"), "UTF-8"));
            for (String var3 = var2.readLine(); var3 != null; var3 = var2.readLine()) {
                final String[] var4 = var3.trim().split("=");
                if (var4 != null && var4.length == 2) {
                    var1.put(var4[0], var4[1]);
                }
            }
        }
        catch (IOException var5) {
            var5.printStackTrace();
            return;
        }
        (this.languageList = var1).put("en_US", "English (US)");
    }
    
    public TreeMap getLanguageList() {
        return this.languageList;
    }
    
    private void loadLanguage(final Properties par1Properties, final String par2Str) throws IOException {
        BufferedReader var3 = null;
        if (this.field_94521_d.containsKey(par2Str)) {
            var3 = new BufferedReader(new FileReader(this.field_94521_d.get(par2Str)));
        }
        else {
            var3 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + par2Str + ".lang"), "UTF-8"));
        }
        for (String var4 = var3.readLine(); var4 != null; var4 = var3.readLine()) {
            var4 = var4.trim();
            if (!var4.startsWith("#")) {
                final String[] var5 = var4.split("=");
                if (var5 != null && var5.length == 2) {
                    par1Properties.setProperty(var5[0], var5[1]);
                }
            }
        }
    }
    
    public synchronized void setLanguage(final String par1Str, final boolean par2) {
        if (par2 || !par1Str.equals(this.currentLanguage)) {
            final Properties var3 = new Properties();
            try {
                this.loadLanguage(var3, "en_US");
            }
            catch (IOException ex) {}
            this.isUnicode = false;
            if (!"en_US".equals(par1Str)) {
                try {
                    this.loadLanguage(var3, par1Str);
                    final Enumeration var4 = var3.propertyNames();
                    while (var4.hasMoreElements()) {
                        if (this.isUnicode) {
                            break;
                        }
                        final Object var5 = var4.nextElement();
                        final Object var6 = ((Hashtable<K, Object>)var3).get(var5);
                        if (var6 == null) {
                            continue;
                        }
                        final String var7 = var6.toString();
                        for (int var8 = 0; var8 < var7.length(); ++var8) {
                            if (var7.charAt(var8) >= '\u0100') {
                                this.isUnicode = true;
                                break;
                            }
                        }
                    }
                }
                catch (IOException var9) {
                    var9.printStackTrace();
                    return;
                }
            }
            this.currentLanguage = par1Str;
            this.translateTable = var3;
        }
    }
    
    public String getCurrentLanguage() {
        return this.currentLanguage;
    }
    
    public boolean isUnicode() {
        return this.isUnicode;
    }
    
    public synchronized String translateKey(final String par1Str) {
        return this.translateTable.getProperty(par1Str, par1Str);
    }
    
    public synchronized String translateKeyFormat(final String par1Str, final Object... par2ArrayOfObj) {
        final String var3 = this.translateTable.getProperty(par1Str, par1Str);
        try {
            return String.format(var3, par2ArrayOfObj);
        }
        catch (IllegalFormatException var4) {
            return "Format error: " + var3;
        }
    }
    
    public synchronized boolean containsTranslateKey(final String par1Str) {
        return this.translateTable.containsKey(par1Str);
    }
    
    public synchronized String translateNamedKey(final String par1Str) {
        return this.translateTable.getProperty(String.valueOf(par1Str) + ".name", "");
    }
    
    public static boolean isBidirectional(final String par0Str) {
        return "ar_SA".equals(par0Str) || "he_IL".equals(par0Str);
    }
    
    public synchronized void func_94519_a(String par1Str, final File par2File) {
        final int var3 = par1Str.indexOf(46);
        if (var3 > 0) {
            par1Str = par1Str.substring(0, var3);
        }
        this.field_94521_d.put(par1Str, par2File);
        if (par1Str.contains(this.currentLanguage)) {
            this.setLanguage(this.currentLanguage, true);
        }
    }
}
