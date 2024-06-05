package zamorozka.modules.POTION;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NightVision extends Module
{
public NightVision()
{
  super("NightVision", 0, Category.POTION);
}

public void onUpdate() {
		if (this.getState()) {
  Zamorozka.player().addPotionEffect(new PotionEffect(Potion.getPotionById(16), 9999999, 1));
}

		else
{
			Zamorozka.player().removePotionEffect(Potion.getPotionById(16));
}
}
}
