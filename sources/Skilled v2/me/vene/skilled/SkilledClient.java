 package me.vene.skilled;

 import java.io.IOException;
 import java.lang.reflect.Field;
 import java.util.List;
 import java.util.Map;
 import java.util.Vector;
 import me.vene.skilled.autogg.AutoGG;
 import me.vene.skilled.command.CommandManager;
 import me.vene.skilled.event.EventManager;
 import me.vene.skilled.friend.FriendManager;
 import me.vene.skilled.gui.GUI;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.ModuleManager;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.utilities.TextUtil;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.EntityRenderer;
 import net.minecraft.launchwrapper.Launch;
 import net.minecraft.launchwrapper.LaunchClassLoader;
 import net.minecraftforge.client.event.MouseEvent;
 import net.minecraftforge.client.event.RenderGameOverlayEvent;
 import net.minecraftforge.client.event.RenderWorldLastEvent;
 import net.minecraftforge.common.MinecraftForge;
 import net.minecraftforge.event.entity.living.LivingEvent;
 import net.minecraftforge.event.entity.player.AttackEntityEvent;
 import net.minecraftforge.fml.common.FMLCommonHandler;
 import net.minecraftforge.fml.common.Mod;
 import net.minecraftforge.fml.common.Mod.EventHandler;
 import net.minecraftforge.fml.common.event.FMLInitializationEvent;
 import net.minecraftforge.fml.common.eventhandler.Event;
 import net.minecraftforge.fml.common.eventhandler.EventBus;
 import net.minecraftforge.fml.common.eventhandler.EventPriority;
 import net.minecraftforge.fml.common.eventhandler.IEventListener;
 import net.minecraftforge.fml.common.gameevent.InputEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;

 @Mod(name = "autogg", modid = "autogg", version = "1.0", acceptedMinecraftVersions = "*")
 public class SkilledClient
 {
   public TextUtil bebas_font;
   private static GUI clickGUI;
   private static SkilledClient instance;
   private EventManager eventManager;
   private CommandManager commandManager;
   private FriendManager friendManager;
   public boolean nigger = false;
   
   public SkilledClient() {
     registerMemes();
     registerEvents();
  }
   
   @EventHandler
   public void init(FMLInitializationEvent e) throws IOException {
     instance = this;
     this.bebas_font = new TextUtil("bebas", 50);
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new AutoGG());
     
     FMLCommonHandler.instance().bus().register(this);
     clickGUI = new GUI();
   }
    public static SkilledClient getInstance() {
     return instance;
   }
   
   public static GUI getClickGUI() {
     return clickGUI;
   }
   
   public EventManager getEventManager() {
     return this.eventManager;
  }
  
   private void registerMemes() {
     this.eventManager = new EventManager();
     this.commandManager = new CommandManager();
     this.friendManager = new FriendManager();
   }
   
   public CommandManager getCommandManager() {
     return this.commandManager;
   }
   
   public FriendManager getFriendManager() {
     return this.friendManager;
   }
   
   private void registerEvents() {
     try {
       Field field = EventBus.class.getDeclaredField(new String(new char[] { 'b', 'u', 's', 'I', 'D' }));
       field.setAccessible(true);
       registerEvent((Class)InputEvent.KeyInputEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)TickEvent.RenderTickEvent.class, (IEventListener)this.eventManager, EventPriority.HIGH, field);
       registerEvent((Class)TickEvent.ClientTickEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)AttackEntityEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)LivingEvent.LivingUpdateEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)MouseEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)RenderGameOverlayEvent.Post.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
       registerEvent((Class)RenderWorldLastEvent.class, (IEventListener)this.eventManager, EventPriority.NORMAL, field);
    }
        catch (NoSuchFieldException ex) {}
    }
    
    private void registerEvent(final Class<? extends Event> classEvent, final IEventListener listener, final EventPriority priority, final Field field) {
        try {
            ((Event)classEvent.newInstance()).getListenerList().register(field.getInt(FMLCommonHandler.instance().bus()), priority, listener);
        }
        catch (InstantiationException ex) {}
        catch (IllegalAccessException ex2) {}
    }
    
    private void unregisterEvent(final Class<? extends Event> classEvent, final IEventListener listener, final Field field) {
        try {
            ((Event)classEvent.newInstance()).getListenerList().unregister(field.getInt(FMLCommonHandler.instance().bus()), listener);
            ((Event)classEvent.newInstance()).getListenerList().unregister(field.getInt(MinecraftForge.EVENT_BUS), listener);
        }
        catch (InstantiationException ex) {}
        catch (IllegalAccessException ex2) {}
    }
  
   public void selfDestruct() {
     this.eventManager.setSelfDestructing(true);
    if ((Minecraft.getMinecraft()).currentScreen instanceof GUI) {
       Minecraft.getMinecraft().displayGuiScreen(null);
     }
     Field field = null;
     try {
      String busMEME = new String(new char[] { 'b', 'u', 's', 'I', 'D' });
       field = EventBus.class.getDeclaredField(busMEME);
       field.setAccessible(true);
       unregisterEvent((Class)InputEvent.KeyInputEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)TickEvent.ClientTickEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)TickEvent.RenderTickEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)LivingEvent.LivingUpdateEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)MouseEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)AttackEntityEvent.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)RenderGameOverlayEvent.Post.class, (IEventListener)this.eventManager, field);
       unregisterEvent((Class)RenderWorldLastEvent.class, (IEventListener)this.eventManager, field);
     } 
     catch (NoSuchFieldException noSuchFieldException) {
     }
     for (Module mod : ModuleManager.getModules()) {
       mod.setKey(-1);
       mod.setState(false);
       mod.setName(null);
       for (NumberValue value : mod.getValues()) {
         value.setName(null);
         value.setValue(0.0D);
         value = null;
       } 
       for (BooleanValue booleanValue : mod.getOptions()) {
         booleanValue.setName(null);
         booleanValue = null;
       } 
       mod.getOptions().clear();
       mod.getValues().clear();
       mod = null;
     }
     for (final Category category : Category.values()) {
         category.setName(null);
     }
     Minecraft.getMinecraft().entityRenderer = new EntityRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().getResourceManager());
     try {
         final String cachedClassesString = StringRegistry.register(new String(new char[] { 'c', 'a', 'c', 'h', 'e', 'd', 'C', 'l', 'a', 's', 's', 'e', 's' }));
         final Field cachedClasses = LaunchClassLoader.class.getDeclaredField(StringRegistry.register(cachedClassesString));
         cachedClasses.setAccessible(true);
         ((Map)cachedClasses.get(Launch.classLoader)).clear();
         final String classesString = StringRegistry.register(new String(new char[] { 'c', 'l', 'a', 's', 's', 'e', 's' }));
         final Field classes = ClassLoader.class.getDeclaredField(StringRegistry.register(classesString));
         classes.setAccessible(true);
         ((Vector)classes.get(Launch.classLoader)).clear();
         final String sourcesString = StringRegistry.register(new String(new char[] { 's', 'o', 'u', 'r', 'c', 'e', 's' }));
         final Field sources = LaunchClassLoader.class.getDeclaredField(StringRegistry.register(sourcesString));
         sources.setAccessible(true);
         ((List)sources.get(Launch.classLoader)).clear();
         ((Map)cachedClasses.get(Minecraft.class.getClassLoader())).clear();
         ((Vector)classes.get(Minecraft.class.getClassLoader())).clear();
         ((List)sources.get(Minecraft.class.getClassLoader())).clear();
     }
     catch (Exception ex2) {}
     try {
         StringRegistry.cleanup();
         Thread.sleep(1000L);
         StringRegistry.cleanup();
         Thread.sleep(5000L);
     }
     catch (Exception ex3) {}
     try {
         System.gc();
         System.runFinalization();
         System.gc();
         Thread.sleep(100L);
         System.gc();
         System.runFinalization();
         Thread.sleep(200L);
         System.gc();
         System.runFinalization();
         Thread.sleep(300L);
         System.gc();
         System.runFinalization();
     }
     catch (InterruptedException ex4) {}
 }
}
