 package me.vene.skilled.event;
 
 import java.io.IOException;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.ModuleManager;
 import me.vene.skilled.renderer.NiggerRenderer;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.utilities.TimerUtil;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityOtherPlayerMP;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.client.renderer.EntityRenderer;
 import net.minecraftforge.client.event.MouseEvent;
 import net.minecraftforge.client.event.RenderGameOverlayEvent;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import net.minecraftforge.event.entity.living.LivingEvent;
 import net.minecraftforge.event.entity.player.AttackEntityEvent;
 import net.minecraftforge.fml.common.eventhandler.Event;
 import net.minecraftforge.fml.common.eventhandler.IEventListener;
 import net.minecraftforge.fml.common.gameevent.InputEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 import org.lwjgl.input.Keyboard;
 
 public class EventManager implements IEventListener
 {
     private Minecraft mc;
     private long lastClick;
     private boolean selfDestructing;
     private long lastAttack;
     public static TimerUtil timerUtil;
     public static String lastAttackedEntityName;
     private String packetmeme;
     
     public EventManager() {
         this.mc = Minecraft.getMinecraft();
         this.selfDestructing = false;
         this.packetmeme = StringRegistry.register(new String(new char[] { 'p', 'a', 'c', 'k', 'e', 't', '_', 'h', 'a', 'n', 'd', 'l', 'e', 'r' }));
     }
     
     public void invoke(final Event event) {
         if (!this.selfDestructing) {
             if (event instanceof InputEvent.KeyInputEvent) {
                 this.onKeyPress((InputEvent.KeyInputEvent)event);
             }
             if (event instanceof TickEvent.ClientTickEvent) {
                 this.onClientTick((TickEvent.ClientTickEvent)event);
             }
             if (event instanceof TickEvent.RenderTickEvent) {
                 this.onRender((TickEvent.RenderTickEvent)event);
             }
             if (event instanceof LivingEvent.LivingUpdateEvent) {
                 this.onLivingUpdate((LivingEvent.LivingUpdateEvent)event);
             }
             if (event instanceof MouseEvent) {
                 this.onMouse((MouseEvent)event);
             }
             if (event instanceof AttackEntityEvent) {
                 this.onAttack((AttackEntityEvent)event);
             }
             if (event instanceof RenderGameOverlayEvent.Post) {
                 this.renderTextEvent((RenderGameOverlayEvent.Post)event);
             }
             if (event instanceof RenderWorldLastEvent) {
                 this.onRenderEvent((RenderWorldLastEvent)event);
             }
         }
     }
     
     private void onRenderEvent(final RenderWorldLastEvent e) {
         if (!this.selfDestructing && this.mc.theWorld != null) {
             for (final Module mod : ModuleManager.getModules()) {
                 if (mod.getState()) {
                     mod.onRenderEvent(e);
                 }
             }
         }
     }
     
     private void onKeyPress(final InputEvent.KeyInputEvent e) {
         try {
             if (!this.selfDestructing) {
                 if (Keyboard.getEventKey() == 0) {
                     return;
                 }
                 if (!Keyboard.getEventKeyState()) {
                     return;
                 }
                 for (final Module mod : ModuleManager.getModules()) {
                     if (mod.getKey() == Keyboard.getEventKey()) {
                         mod.setState(!mod.getState());
                     }
                 }
             }
         }
         catch (Exception ex) {}
     }
     
     private void onClientTick(final TickEvent.ClientTickEvent e) {
         if (!this.selfDestructing) {
             for (final Module mod : ModuleManager.getModules()) {
                 if (mod.getState()) {
                     mod.onClientTick(e);
                 }
             }
         }
     }
     
     private void onLivingUpdate(final LivingEvent.LivingUpdateEvent e) {
         if (!this.selfDestructing) {
             if (!(e.entity instanceof EntityPlayerSP)) {
                 return;
             }
             for (final Module mod : ModuleManager.getModules()) {
                 if (mod.getState()) {
                     mod.onLivingUpdate((EntityPlayerSP)e.entity);
                 }
             }
         }
     }
     
     private void onRender(final TickEvent.RenderTickEvent e) {
         if (!this.selfDestructing) {
             if (this.mc.theWorld != null) {
                 final EntityRenderer entityRenderer = this.mc.entityRenderer;
                 if (!(entityRenderer instanceof NiggerRenderer)) {
                     this.mc.entityRenderer = new NiggerRenderer(this.mc, this.mc.getResourceManager());
                 }
             }
             if (this.mc.theWorld != null) {
                 for (final Module mod : ModuleManager.getModules()) {
                     if (mod.getState()) {
                         mod.onRenderTick(e);
                     }
                 }
             }
         }
     }
     
     public void setSelfDestructing(final boolean selfDestructing) {
         this.selfDestructing = selfDestructing;
     }
     
     public boolean getSelfDestructing() {
         return this.selfDestructing;
     }
     
     private void onMouse(final MouseEvent e) {
         if (!this.selfDestructing && e.buttonstate && e.button == 0) {
             this.lastClick = System.currentTimeMillis();
         }
     }
     
     public long getLastClick() {
         return this.lastClick;
     }
     
     private void renderTextEvent(final RenderGameOverlayEvent.Post e) {
         if (!this.selfDestructing) {
             if (e.type != RenderGameOverlayEvent.ElementType.TEXT) {
                 return;
             }
             if (this.mc.theWorld != null) {
                 for (final Module mod : ModuleManager.getModules()) {
                     if (mod.getState()) {
                         try {
                             mod.onRenderText(e);
                         }
                         catch (IOException ex) {}
                     }
                 }
             }
         }
     }
     
     private void onAttack(final AttackEntityEvent e) {
         this.lastAttack = System.currentTimeMillis();
         if (e.target instanceof EntityOtherPlayerMP) {
             final EntityOtherPlayerMP playerMP = (EntityOtherPlayerMP)e.target;
             EventManager.lastAttackedEntityName = StringRegistry.register(playerMP.getDisplayNameString());
             EventManager.timerUtil.reset();
         }
     }
     
     public long getLastAttack() {
         return this.lastAttack;
     }
     
     public String getLastAttackedEntityName() {
         return EventManager.lastAttackedEntityName;
     }
     
     static {
         EventManager.timerUtil = new TimerUtil();
         EventManager.lastAttackedEntityName = StringRegistry.register("None.");
     }
 }
