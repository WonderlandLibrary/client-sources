package me.protocol_client.files.allfiles;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Protocol;
import me.protocol_client.files.FileUtils;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import me.protocol_client.thanks_slicky.properties.Value;

public class ValuesFile
{
  public static void load()
  {
    List<String> file = FileUtils.readFile(Protocol.protocolDir + "\\options.txt");
    for (String s : file)
    {
    	try{
    	if(s == null){
    		return;
    	}
      String valueName = s.split(":")[0];
      String stringBool = s.split(":")[1];
      boolean bool = Boolean.parseBoolean(stringBool);
      Value val = Protocol.getValueManager().find(valueName);
      float slide = 0;
      if(val instanceof ClampedValue){
    	  slide = Float.parseFloat(s.split(":")[2]);
      }
      float fl = 0;
      if(isFloat(stringBool)){
    	  fl = Float.parseFloat(stringBool);
      }
      if (val != null && val.getDefault() instanceof Boolean)
      {
        val.setValue(bool);
      }
      if (val != null && val.getDefault() instanceof Float)
      {
        val.setValue(fl);
      }
      if (val != null && val instanceof ClampedValue)
      {
        ((ClampedValue) val).setSliderX(slide);
      }
    	}catch(Exception e){
    		System.out.println("Looks like the values file is empty. Don't worry, this usually means this is the first time starting the game.");
    	}
    }
  }
  
  public static void save()
  {
    List<String> newfile = new ArrayList();
    for (Value val : Protocol.getValueManager().getContents()) {
    	String name = val.getName();
    	String option = val.getValue().toString();
    	if(val instanceof ClampedValue){
    		float s = ((ClampedValue)val).getSliderX();
    		newfile.add(name + ":" + option + ":" + s);
    	}else{
    	newfile.add(name + ":" + option);
    }
    }
    FileUtils.writeFile(Protocol.protocolDir + "\\options.txt", newfile);
  }
  public static boolean isFloat(String s)
	{
		try
		{
			Float.parseFloat(s);
		}catch(NumberFormatException e)
		{
			return false;
		}
		return true;
	}
	
}
