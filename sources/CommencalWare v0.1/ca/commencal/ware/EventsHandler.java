package ca.commencal.ware;

import ca.commencal.ware.gui.click.ClickGuiScreen;
import ca.commencal.ware.managers.ModuleManager;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.modules.render.ClickGui;
import ca.commencal.ware.utils.system.Connection;
import ca.commencal.ware.utils.system.Wrapper;
import ca.commencal.ware.utils.visual.ChatUtils;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventsHandler {
	private boolean isInit = false;
	
	public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Module module : ModuleManager.getModules()) {
            if (!module.isToggled() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            suc &= module.onPacket(packet, side);
        }
        return suc;
    }
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		int key = Keyboard.getEventKey();
    		if(Keyboard.getEventKeyState()) {
    			ModuleManager.onKeyPressed(key);
    		}
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onKeyInput");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
	}
	
	
	@SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onCameraSetup(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onCameraSetup");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onItemPickup(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onItemPickup");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    } 
	
	@SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onProjectileImpact(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: ProjectileImpact");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
	}
    	
	
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onAttackEntity(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onAttackEntity");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    }    
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onPlayerTick(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onPlayerTick");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    } 
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		isInit = false;
    		return;
    	}
    	try {
    		if (!isInit) {
                new Connection(this);
                ClickGui.setColor();
                isInit = true;
            }
    		if(!(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen)) {
    			ModuleManager.getModule("ClickGui").setToggled(false);
    		}
    		ModuleManager.onClientTick(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onClientTick");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onLivingUpdate(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onLivingUpdate");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null || Wrapper.INSTANCE.mcSettings().hideGUI) {
    		return;
    	}
    	try {
    		ModuleManager.onRenderWorldLast(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onRenderWorldLast");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
    }
	
	@SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
    	if(Wrapper.INSTANCE.player() == null || Wrapper.INSTANCE.world() == null) {
    		return;
    	}
    	try {
    		ModuleManager.onRenderGameOverlay(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onRenderGameOverlay");
    		ChatUtils.error(ex.toString());
    		Wrapper.INSTANCE.copy(ex.toString());
    	}
	}
}
