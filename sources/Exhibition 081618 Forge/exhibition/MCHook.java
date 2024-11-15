package exhibition;

import exhibition.management.keybinding.KeyHandler;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

import exhibition.event.Event;
import exhibition.event.EventSystem;
import exhibition.event.impl.EventAttack;
import exhibition.event.impl.EventChat;
import exhibition.event.impl.EventKeyPress;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventTick;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.management.notifications.user.Notifications;
import io.netty.channel.ChannelHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent.Post;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod(modid="skeet", name="skeet", version="null", acceptedMinecraftVersions="[1.8.9]")
public class MCHook {

	@EventHandler
	public void Mod(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		(new Client()).setup();
	}
	
	@SubscribeEvent
	public void onRender(TickEvent.RenderTickEvent e) {
		 if(Wrapper.getWorld() != null) {
	         EntityRenderer textGuiFrame = Wrapper.getMinecraft().entityRenderer;
	         if(!(textGuiFrame instanceof CustomEntityRenderer)) {
	            Wrapper.getMinecraft().entityRenderer = new CustomEntityRenderer(Wrapper.getMinecraft(), Wrapper.getMinecraft().getResourceManager());
	         }
	      }
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		((EventAttack)EventSystem.fire(EventSystem.getInstance(EventAttack.class))).fire(event.target, true);
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		EventChat ec = (EventChat)EventSystem.getInstance(EventChat.class);
	      ec.fire(event.message.getUnformattedText());
	}
	
	@SubscribeEvent
	public void onGui(GuiOpenEvent event) {		
		
	}
	
	@SubscribeEvent
	public void onRender2D(RenderGameOverlayEvent event) {
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			((EventRenderGui)EventSystem.getInstance(EventRenderGui.class)).fire(event.resolution , event.partialTicks);
	        Notifications.getManager().updateAndRender();
	        DevNotifications.getManager().updateAndRender();
		}
	}
	
	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if(event.entityLiving == null) {
			return;
		}
		if(event.entityLiving instanceof EntityPlayerSP) {
			EntityPlayerSP p = (EntityPlayerSP) event.entityLiving;
		      EventMotionUpdate em = (EventMotionUpdate)EventSystem.getInstance(EventMotionUpdate.class);
		      em.fire(p.getEntityBoundingBox().minY, p.rotationYaw, p.rotationPitch, p.onGround);
		}
	}
	
	@SubscribeEvent
	public void onNetHandlerHookTick(ClientTickEvent event) {
        if (Wrapper.getMinecraft().getNetHandler() != null && Wrapper.getMinecraft().getNetHandler().getNetworkManager() != null && Wrapper.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().get("exhibition") == null) {
        	Wrapper.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().addBefore("packet_handler", "exhibition", (ChannelHandler)new CustomChannelHandler(Wrapper.getMinecraft().getNetHandler().getNetworkManager()));
        }
	}
	
	@SubscribeEvent
	public void onRender3D(RenderWorldLastEvent event) {
	      ((EventRender3D)EventSystem.getInstance(EventRender3D.class)).fire(event.partialTicks, 0, 0, 0);
	}
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent event) {
		Event e = EventSystem.getInstance(EventTick.class);
	    e.fire();
	}
	
	@SubscribeEvent
	public void keyInput(KeyInputEvent event) {
		if (Wrapper.getPlayer() != null) {
			if (!Keyboard.getEventKeyState()) {
				return;
			}
			int k = Keyboard.getEventKey();
        	EventKeyPress eventKey = (EventKeyPress)EventSystem.getInstance(EventKeyPress.class);
            eventKey.fire(k);
            k = eventKey.getKey();
            if ((k == Keyboard.KEY_F12 || k == Keyboard.KEY_INSERT) && Wrapper.getMinecraft().currentScreen == null && Client.getClickGui().mainPanel.opacity == 0) {
            	Client.getClickGui().mainPanel.opacity = 255;
            	Wrapper.getMinecraft().displayGuiScreen(Client.getClickGui());
            }
            if (k == 41 && Wrapper.getMinecraft().currentScreen == null) {
            	Wrapper.getMinecraft().displayGuiScreen(Client.getSourceConsoleGUI());
            }
			KeyHandler.update(true);
		}
	}
}
