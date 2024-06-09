package chaos.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import chaos.modules.Category;
import chaos.modules.Module;

public class FakeName
  extends Module
{
  public static boolean isEnabled = false;
  public static String fakename = "ChaosClient";
  
	public FakeName() {
		super("FakeName","FakeName", Keyboard.KEY_O, Category.PLAYER);
	}
  
  public void onEnable()
  {
    isEnabled = true;
  }
  
  public void onDisable()
  {
    isEnabled = false;
  }
}
