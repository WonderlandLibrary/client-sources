package org.silvertunnel_ng.netlib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;










































public final class FileUtil
{
  private static final String FILE_CHARSET_NAME = "UTF-8";
  private static final int BUFFER_SIZE = 1024;
  
  protected FileUtil() {}
  
  public static void writeFile(File file, String content)
    throws IOException
  {
    Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
    
    try
    {
      output.write(content);
      


      output.close(); } finally { output.close();
    }
  }
  








  public static String readFile(File file)
    throws IOException
  {
    BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
    
    try
    {
      StringBuilder contents = new StringBuilder(1024);
      

      char[] charBuffer = new char['Ѐ'];
      int len;
      while ((len = input.read(charBuffer)) > 0)
      {
        contents.append(charBuffer, 0, len);
      }
      
      return contents.toString();

    }
    finally
    {
      input.close();
    }
  }
  







  public static String readFileFromClasspath(String filePath)
    throws IOException
  {
    return readFileFromInputStream(FileUtil.class.getResourceAsStream(filePath));
  }
  






  public static String readFileFromInputStream(InputStream is)
    throws IOException
  {
    BufferedReader input = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    
    try
    {
      StringBuilder contents = new StringBuilder(1024);
      

      char[] charBuffer = new char['Ѐ'];
      int len;
      while ((len = input.read(charBuffer)) > 0)
      {
        contents.append(charBuffer, 0, len);
      }
      
      return contents.toString().replaceAll("\r\n", "\n");

    }
    finally
    {
      input.close();
    }
  }
}
