package lunadevs.luna.module.combat;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiKnockbackGomme extends Module {

	public static Boolean active;
	
	public AntiKnockbackGomme() {
		super("VelocityGomme", Keyboard.KEY_NONE, Category.COMBAT, true);
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
		return "40.0%";
	}

}
