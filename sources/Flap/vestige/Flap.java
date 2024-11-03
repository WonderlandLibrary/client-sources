package vestige;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import vestige.anticheat.Anticheat;
import vestige.command.CommandManager;
import vestige.event.EventManager;
import vestige.extensions.DiscordRP;
import vestige.filesystem.FileSystem;
import vestige.font.FontManager;
import vestige.handler.client.BalanceHandler;
import vestige.handler.client.CameraHandler;
import vestige.handler.client.KeybindHandler;
import vestige.handler.client.SlotSpoofHandler;
import vestige.handler.packet.PacketBlinkHandler;
import vestige.handler.packet.PacketDelayHandler;
import vestige.module.ModuleManager;
import vestige.ui.menu.VestigeMainMenu;
import vestige.util.IMinecraft;
import vestige.util.render.FontUtil;

public class Flap implements IMinecraft {
   public static final Flap instance = new Flap();
   public final String name = "Flop";
   public final String version = "69";
   private EventManager eventManager;
   private ModuleManager moduleManager;
   private CommandManager commandManager;
   private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(4);
   private PacketDelayHandler packetDelayHandler;
   private PacketBlinkHandler packetBlinkHandler;
   private KeybindHandler keybindHandler;
   private BalanceHandler balanceHandler;
   private CameraHandler cameraHandler;
   private SlotSpoofHandler slotSpoofHandler;
   public static DiscordRP discordRP = new DiscordRP();
   private Anticheat anticheat;
   private FileSystem fileSystem;
   private FontManager fontManager;
   private boolean destructed;

   public void start() throws Exception {
      discordRP.start();
      Display.setTitle("Flop Client");
      this.eventManager = new EventManager();
      this.moduleManager = new ModuleManager();
      this.commandManager = new CommandManager();
      this.packetDelayHandler = new PacketDelayHandler();
      this.packetBlinkHandler = new PacketBlinkHandler();
      this.keybindHandler = new KeybindHandler();
      this.balanceHandler = new BalanceHandler();
      this.slotSpoofHandler = new SlotSpoofHandler();
      this.cameraHandler = new CameraHandler();
      this.anticheat = new Anticheat();
      this.fileSystem = new FileSystem();
      this.fontManager = new FontManager();
      this.fileSystem.loadDefaultConfig();
      this.fileSystem.loadKeybinds();
      this.moduleManager.modules.forEach((m) -> {
         m.onClientStarted();
      });
      FontUtil.initFonts();
      if (mc.gameSettings.ofFastRender) {
         mc.gameSettings.ofFastRender = false;
      }

      Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft", "opening"), 1.0F));
   }

   public void shutdown() {
      if (!this.destructed) {
         instance.fileSystem.saveKeybinds();
      }

   }

   public static ScheduledExecutorService getExecutor() {
      return ex;
   }

   public GuiScreen getMainMenu() {
      return (GuiScreen)(this.destructed ? new GuiMainMenu() : new VestigeMainMenu());
   }

   public static DiscordRP getDiscordRP() {
      return discordRP;
   }

   public String getName() {
      Objects.requireNonNull(this);
      return "Flop";
   }

   public String getVersion() {
      Objects.requireNonNull(this);
      return "69";
   }

   public EventManager getEventManager() {
      return this.eventManager;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public PacketDelayHandler getPacketDelayHandler() {
      return this.packetDelayHandler;
   }

   public PacketBlinkHandler getPacketBlinkHandler() {
      return this.packetBlinkHandler;
   }

   public KeybindHandler getKeybindHandler() {
      return this.keybindHandler;
   }

   public BalanceHandler getBalanceHandler() {
      return this.balanceHandler;
   }

   public CameraHandler getCameraHandler() {
      return this.cameraHandler;
   }

   public SlotSpoofHandler getSlotSpoofHandler() {
      return this.slotSpoofHandler;
   }

   public Anticheat getAnticheat() {
      return this.anticheat;
   }

   public FileSystem getFileSystem() {
      return this.fileSystem;
   }

   public FontManager getFontManager() {
      return this.fontManager;
   }

   public boolean isDestructed() {
      return this.destructed;
   }

   public void setDestructed(boolean destructed) {
      this.destructed = destructed;
   }
}
