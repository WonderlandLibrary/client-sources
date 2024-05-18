package me.hexxed.mercury.overlay;

import java.time.Clock;
import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.modulebase.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public enum Placeholders
{
  FPS("%fps%", Integer.valueOf(Minecraft.debugFPS)), 
  PING("%ping%", Integer.valueOf(getValuesping)), 
  POSX("%posx%", Double.valueOf(getMinecraftthePlayer.posX)), 
  POSY("%posy%", Double.valueOf(getMinecraftthePlayer.posY)), 
  POSZ("%posz%", Double.valueOf(getMinecraftthePlayer.posZ)), 
  IGN("%ign%", ""), 
  SERVERSTRING("%serverstring%", ""), 
  SERVERVERSION("%serverversion%", ""), 
  CLIENT_NAME("%name%", Mercury.getInstance().getName()), 
  CLIENT_VERSION("%version%", Mercury.getInstance().getVersion()), 
  CLIENT_NAME_SHORT("%nameshort%", Mercury.getInstance().getShortName()), 
  CLIENT_NAME_REST("%namerest%", Mercury.getInstance().getName().substring(1)), 
  SCREEN_HEIGHT("%height%", Integer.valueOf(getValuesheight)), 
  SCREEN_WIDTH("%width%", Integer.valueOf(getValueswidth)), 
  TIME("%time%", "");
  
  private String replace;
  private Object output;
  
  static
  {
    Minecraft.getMinecraft();
  }
  















  private Placeholders(String replace, Object output)
  {
    this.output = output;
    this.replace = replace;
  }
  
  public String getReplace() {
    return replace;
  }
  
  public Object getOutput() {
    return output;
  }
  

  public void setOutput(Object newoutput)
  {
    output = newoutput;
  }
  
  public static void updatePlaceHolders() {
    for (Placeholders p : ) { String str1;
      switch ((str1 = p.getReplace()).hashCode()) {case -1934551563:  if (str1.equals("%serverversion%")) {} break; case -1905588391:  if (str1.equals("%nameshort%")) {} break; case 37320193:  if (str1.equals("%fps%")) break; break; case 37400762:  if (str1.equals("%ign%")) {} break; case 198079884:  if (str1.equals("%serverstring%")) {} break; case 862261273:  if (str1.equals("%height%")) {} break; case 1163863541:  if (str1.equals("%name%")) {} break; case 1165949934:  if (str1.equals("%ping%")) {} break; case 1166134012:  if (str1.equals("%posx%")) {} break; case 1166134043:  if (str1.equals("%posy%")) {} break; case 1166134074:  if (str1.equals("%posz%")) {} break; case 1169642995:  if (str1.equals("%time%")) {} break; case 1984830340:  if (!str1.equals("%width%"))
        {

          continue;Minecraft.getMinecraft();p.setOutput(Integer.valueOf(Minecraft.debugFPS));
          continue;
          

          p.setOutput(Integer.valueOf(getValuesping));
        }
        else
        {
          p.setOutput(Integer.valueOf(getValueswidth));
          continue;
          

          p.setOutput(Integer.valueOf(getValuesheight));
          continue;
          

          p.setOutput(Mercury.getInstance().getName());
          continue;
          

          p.setOutput(Mercury.getInstance().getShortName());
          continue;
          

          p.setOutput(Long.valueOf(Math.round(getMinecraftthePlayer.posX)));
          continue;
          

          p.setOutput(Long.valueOf(Math.round(getMinecraftthePlayer.posY)));
          continue;
          

          p.setOutput(Long.valueOf(Math.round(getMinecraftthePlayer.posZ)));
          continue;
          

          p.setOutput(Minecraft.getMinecraft().getSession().getUsername());
          continue;
          

          p.setOutput(getMinecraftthePlayer.getClientBrand());
          continue;
          

          p.setOutput(getMinecraftgetCurrentServerDatagameVersion);
          continue;
          

          String time = String.valueOf(Clock.systemDefaultZone().instant()).substring(11);
          boolean pm = Integer.valueOf(time.split(":")[0]).intValue() + 2 > 12;
          String simpletime = (pm ? 
            String.valueOf(Integer.valueOf(time.split(":")[0]).intValue() - 10) : 
            String.valueOf(Integer.valueOf(time.split(":")[0]).intValue() + 2)) + 
            ":" + time.split(":")[1] + " " + (pm ? "PM" : "AM");
          p.setOutput(simpletime);
        }
        break;
      }
    }
  }
}
