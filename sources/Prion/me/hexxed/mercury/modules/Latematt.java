package me.hexxed.mercury.modules;

import java.util.ArrayList;
import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.util.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;


public class Latematt
  extends Module
{
  private ArrayList<String> latemad = new ArrayList();
  
  private int index = 0;
  
  public Latematt() {
    super("Latematt", 0, true, ModuleCategory.MISC);
    latemad.add("Be me");
    latemad.add("Be 10 years old");
    latemad.add("I play on latematts reliant remake every day");
    latemad.add("I downloaded all of his source");
    latemad.add("One day on the reliant remake fly was glitching");
    latemad.add("All of a sudden through my headphones a faint 'yes hello'");
    latemad.add("I dismiss it because I think I'm just hearing things");
    latemad.add("Then I hear a whisper near my open window 'yes... hello'");
    latemad.add("I close minecraft because I have to go to bed");
    latemad.add("BOOM");
    latemad.add("'YES HELLO LATEMATT HERE'");
    latemad.add("He grabs me by my hair and sits me down back at my computer");
    latemad.add("I open up reliant as he slides my cargo pants to my knees");
    latemad.add("He steps back and ganders at my minecraft underwear");
    latemad.add("'This is my type of client, look at those skid marks...'");
    latemad.add("I feel a prick at my butt");
    latemad.add("It's latematt");
    latemad.add("He pushes in and out, letting a loud groan each time");
    latemad.add("As I get my tenth kill on a kitpvp server he cums into my asshole");
    latemad.add("I feel it dripping out");
    latemad.add("It's so warm");
    latemad.add("I cum");
    latemad.add("'I'd skid your source any day'");
    latemad.add("Latematt slowly moves to the door, pulling me by the shirt with him");
    latemad.add("He puts his mouth around my cock");
    latemad.add("I can feel his teeth getting tighter and tighter");
    latemad.add("He bites my dick off and leaves me there");
    latemad.add("Latematt is love");
    latemad.add("Latematt is life");
  }
  
  TimeHelper delay = new TimeHelper();
  
  public void onEnable()
  {
    index = 0;
  }
  
  public void onTick()
  {
    if (!delay.isDelayComplete(2000L)) return;
    delay.setLastMS();
    if (index < 29) {
      mc.thePlayer.sendChatMessage((String)latemad.get(index));
      index += 1;
    } else { index = 0;
    }
  }
}
