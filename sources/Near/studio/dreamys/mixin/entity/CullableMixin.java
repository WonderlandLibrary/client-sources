package studio.dreamys.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import studio.dreamys.entityculling.access.Cullable;
import studio.dreamys.near;

@Mixin({Entity.class, TileEntity.class})
public class CullableMixin implements Cullable {
	private long lastTime;
	private boolean culled;

	private void setTimeout() {
		lastTime = System.currentTimeMillis() + 1000;
	}

	@Override
	public boolean isForcedVisible() {
		return lastTime <= System.currentTimeMillis();
	}

	@Override
	public void setCulled(boolean value) {
		culled = value;
		if(!value) {
			setTimeout();
		}
	}

	@Override
	public boolean isCulled() {
		if(!near.moduleManager.getModule("Optimization").isToggled() || !near.settingsManager.getSettingByName(near.moduleManager.getModule("Optimization"), "Culling").getValBoolean())return false;
		return culled;
	}
}
