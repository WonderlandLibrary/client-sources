package zamorozka.modules.WORLD;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class TrueSight extends Module {

	private List<Entity> entities;

	public TrueSight() {
		super("TrueSight", Keyboard.KEY_NONE, Category.WORLD);

		this.entities = new ArrayList<Entity>();

	}

	@Override
	public void onEnable() {
		for (Entity entity : this.entities) {
			entity.setInvisible(true);
		}
		this.entities.clear();
		super.onEnable();
	}

	@EventTarget
	public void onpr(EventPreMotionUpdates event) {
		for (final Entity entity : this.mc.world.loadedEntityList) {
			if (entity.isInvisible() && entity instanceof EntityPlayer) {
				entity.setInvisible(false);
				this.entities.add(entity);
			}

		}
	}
}