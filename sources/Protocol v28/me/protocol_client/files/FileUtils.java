package me.protocol_client.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;

public abstract class FileUtils
{
  public static List<String> readFile(String file)
  {
    try
    {
      return Files.readAllLines(Paths.get(file, new String[0]));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return new ArrayList();
  }
  public static void writeFile(String file, List<String> newcontent)
  {
    try
    {
      FileWriter fw = new FileWriter(file);
      for (String s : newcontent) {
        fw.append(s + "\r\n");
      }
      fw.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  public static void createFile(String name)
  {
    try
    {
      File file = new File(Protocol.protocolDir, name + ".txt");
      if (!file.exists())
      {
        PrintWriter printWriter = new PrintWriter(new FileWriter(file));
        printWriter.println();
        printWriter.close();
      }
      System.out.println(Protocol.protocolDir);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
}
