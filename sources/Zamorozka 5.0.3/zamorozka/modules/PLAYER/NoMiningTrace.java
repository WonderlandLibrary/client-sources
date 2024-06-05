package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.TraceEntityEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoMiningTrace extends Module {
	
	public NoMiningTrace() {
		super("NoEntityTrace", 0, Category.PLAYER);
	}
}