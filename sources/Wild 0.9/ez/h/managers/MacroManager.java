package ez.h.managers;

import java.io.*;
import java.util.*;

public class MacroManager
{
    public static HashMap<Integer, String> macroses;
    
    static {
        MacroManager.macroses = new HashMap<Integer, String>();
    }
    
    public static void removeMacros(final String s) {
        MacroManager.macroses.entrySet().forEach(entry -> {
            if (entry.getValue().equalsIgnoreCase(s)) {
                MacroManager.macroses.remove(entry.getKey());
            }
        });
    }
    
    public static void removeMacros(final int n) {
        MacroManager.macroses.remove(n);
    }
    
    public static void save() throws IOException {
    }
    
    public static void addMacros(final int n, final String s) {
        MacroManager.macroses.put(n, s);
    }
    
    public static void load() throws IOException {
    }
}
