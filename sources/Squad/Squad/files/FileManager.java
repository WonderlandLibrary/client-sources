package Squad.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class FileManager
{
  protected Minecraft mc;
  private String fileName;
  private File path;
  
  public FileManager(String fileName, String clientName)
  {
    this.mc = Minecraft.getMinecraft();
    fileName = fileName + "." + Squad.Squad.ClientName.toLowerCase();
    this.fileName = fileName;
    this.path = new File(this.mc.mcDataDir.getAbsolutePath() + File.separator + clientName + File.separator);
    if (!this.path.exists()) {
      try
      {
        this.path.mkdir();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    File file = new File(this.path + fileName);
    if (!file.exists()) {
      try
      {
        file.createNewFile();
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public final ArrayList<String> read()
  {
    ArrayList<String> list = new ArrayList();
    try
    {
      BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(new File(this.path, this.fileName).getAbsolutePath()))));
      for (;;)
      {
        String text = br.readLine();
        if (text == null) {
          break;
        }
        list.add(text.trim());
      }
      br.close();
    }
    catch (Throwable e)
    {
      e.printStackTrace();
    }
    return list;
  }
  
  public void write(String text)
  {
    write(new String[] { text });
  }
  
  public void write(String[] text)
  {
    if ((text == null) || (text.length == 0) || (text[0].trim().length() == 0)) {
      return;
    }
    try
    {
      BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.path, this.fileName), true));
      String[] arrayOfString;
      int j = (arrayOfString = text).length;
      for (int i = 0; i < j; i++)
      {
        String line = arrayOfString[i];
        
        bw.write(line);
        bw.write("\r\n");
      }
      bw.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void remove(int line)
  {
    ArrayList<String> file = read();
    if (file.size() < line) {
      return;
    }
    clear();
    int loop = 1;
    for (String text : file)
    {
      if (loop != line) {
        write(text);
      }
      loop++;
    }
  }
  
  public void clear()
  {
    try
    {
      BufferedWriter bw = new BufferedWriter(new FileWriter(new File(this.path, this.fileName)));
      bw.write("");
      bw.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
