package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;

public class AntiKnockback extends Module {

	public static Boolean active;
	
	public AntiKnockback() {
		super("Velocity", Keyboard.KEY_NONE, Category.COMBAT, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		super.onUpdate();
	}

	@Override
    public void onEnable() {
		active = true;
        super.onEnable();
    }
 
    @Override
    public void onDisable() {
    	active = false;
        super.onDisable();
    }
	
	@Override
	public String getValue() {
		return "0.0%";
	}

}
