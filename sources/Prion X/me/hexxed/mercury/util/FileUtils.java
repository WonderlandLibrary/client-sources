package me.hexxed.mercury.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import me.hexxed.mercury.Mercury;

public abstract class FileUtils
{
  public FileUtils() {}
  
  public static List<String> readFile(String file)
  {
    try
    {
      return Files.readAllLines(java.nio.file.Paths.get(file, new String[0]));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ArrayList();
  }
  
  public static void writeFile(String file, List<String> newcontent) {
    try {
      FileWriter fw = new FileWriter(file);
      for (String s : newcontent) {
        fw.append(s + "\r\n");
      }
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void createFile(String name) {
    try {
      File file = new File(Mercury.raidriarDir.getAbsolutePath(), name + ".txt");
      if (!file.exists()) {
        PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        printWriter.println();
        printWriter.close();
      }
      System.out.println(Mercury.raidriarDir.getAbsolutePath());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static HashMap<String, String> getFriendsFromFile() {
    List<String> friends = readFile(Mercury.raidriarDir.getAbsolutePath() + "\\friends.txt");
    HashMap<String, String> realfriends = new HashMap();
    for (String s : friends) {
      s.replaceAll(" ", "");
      if (!s.equals("")) {
        realfriends.put(s.split(":")[0], s.split(":")[1]);
      }
    }
    return realfriends;
  }
  
  public static List<String> getAlts() {
    List<String> file = readFile(Mercury.raidriarDir.getAbsolutePath() + "\\alts.txt");
    List<String> alts = new ArrayList();
    String s; label91: for (Iterator localIterator = file.iterator(); localIterator.hasNext(); 
        
        alts.add(s))
    {
      s = (String)localIterator.next();
      s.replaceAll(" ", "");
      if ((s.equals("")) || (!s.contains(":"))) break label91;
    }
    return alts;
  }
  
  public static void removeAlt(String line) {
    List<String> file = readFile(Mercury.raidriarDir.getAbsolutePath() + "\\alts.txt");
    List<String> newfile = new ArrayList();
    for (String s : file) {
      if (!s.replaceAll(" ", "").equals(line.replaceAll(" ", ""))) {
        newfile.add(s);
      }
    }
    writeFile(Mercury.raidriarDir.getAbsolutePath() + "\\alts.txt", newfile);
  }
  
  public static void enableStartupMods() {
    List<String> file = readFile(Mercury.raidriarDir.getAbsolutePath() + "\\startupmodules.txt");
    for (String s : file) {
      try {
        me.hexxed.mercury.modulebase.ModuleManager.getModByName(s).setStateSilent(true);
      } catch (NullPointerException e) {
        System.out.println("Failed to find mod " + s);
      }
    }
  }
}
