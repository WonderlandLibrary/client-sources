package Squad.Modules.Combat;


import org.lwjgl.input.Keyboard;

import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class Teams extends Module {

	public Teams() {
		super("Teams", Keyboard.KEY_NONE, 0xf44146, Category.Combat);
	}
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public void onTick(EntityLivingBase e) {	
			boolean col = mc.thePlayer.getDisplayName().getUnformattedTextForChat().startsWith("§") 
					&& e.getDisplayName().getUnformattedTextForChat().startsWith("§");
			if(col) {
				String myCol = mc.thePlayer.getDisplayName().getUnformattedTextForChat().substring(1, 2);
				String eCol = e.getDisplayName().getUnformattedTextForChat().substring(1, 2);
				
				if(myCol.equals(eCol)) {
					
		
				}
			}
		
	
	}

}
