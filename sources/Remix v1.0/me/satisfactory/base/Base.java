package me.satisfactory.base;

import net.minecraft.client.*;
import me.tojatta.api.utilities.value.*;
import net.minecraft.util.*;
import me.mees.remix.irc.*;
import me.satisfactory.base.module.*;
import java.io.*;
import me.satisfactory.base.hero.settings.*;
import pw.stamina.causam.*;
import me.satisfactory.base.command.*;
import me.satisfactory.base.relations.*;
import me.mees.remix.ui.font.*;
import me.satisfactory.base.gui.tabgui.*;
import pw.stamina.causam.select.*;
import pw.stamina.causam.publish.*;
import pw.stamina.causam.event.*;
import pw.stamina.causam.registry.*;
import java.awt.*;
import net.minecraft.network.*;
import me.satisfactory.base.gui.*;
import net.minecraft.network.handshake.client.*;
import java.util.*;
import me.satisfactory.base.utils.*;

public enum Base
{
    INSTANCE;
    
    static Minecraft mc;
    private final ValueManager valueManager;
    private final ResourceLocation background;
    private final ResourceLocation singleleplayer;
    private final ResourceLocation settings;
    private final ResourceLocation play;
    private final ResourceLocation multiplayer;
    private final ResourceLocation exit;
    private final ResourceLocation altmanager;
    private final ResourceLocation vMenu;
    private final ResourceLocation ModuleImg;
    public int MainColor;
    public IrcManager ircManager;
    private String CLIENT_NAME;
    private String CLIENT_VERSION;
    private ModuleManager moduleManager;
    private File raidriarDir;
    private SettingsManager setmgr;
    private EventBus eventManager;
    private CommandManager commandManager;
    private FriendManager friendManager;
    private PortscanManager portscanManager;
    private FontManager fontManager;
    
    private Base() {
        this.valueManager = new ValueManager();
        this.background = new ResourceLocation("remix/mainmenu.png");
        this.singleleplayer = new ResourceLocation("remix/singleplayer.png");
        this.settings = new ResourceLocation("remix/settings.png");
        this.play = new ResourceLocation("remix/play.png");
        this.multiplayer = new ResourceLocation("remix/multiplayer.png");
        this.exit = new ResourceLocation("remix/exit.png");
        this.altmanager = new ResourceLocation("remix/altmanager.png");
        this.vMenu = new ResourceLocation("remix/vMenu.png");
        this.ModuleImg = new ResourceLocation("remix/moduleManager.png");
        this.CLIENT_NAME = "Remix";
        this.CLIENT_VERSION = "1";
        this.fontManager = new FontManager();
        System.out.println("Loading " + this.getCLIENT_NAME() + "...");
        final long time = System.currentTimeMillis();
        this.eventManager = createEventBus();
        this.setmgr = new SettingsManager();
        this.raidriarDir = new File(Minecraft.getMinecraft().mcDataDir, this.getCLIENT_NAME());
        this.commandManager = new CommandManager();
        TabGUI.init();
        this.fontManager.loadFonts();
        System.out.println("Loaded " + this.CLIENT_NAME + " - Time spent " + (System.currentTimeMillis() - time) + "ms.");
    }
    
    private static EventBus createEventBus() {
        final SubscriptionSelectorService selectorService = CachingSubscriptionSelectorServiceDecorator.standard(SubscriptionSelectorService.simple());
        final SubscriptionRegistry registry = SetBasedSubscriptionRegistry.concurrentHash(selectorService);
        final Publisher publisher = Publisher.immediate();
        final EventEmitter emitter = EventEmitter.standard(registry, publisher);
        return EventBus.standard(registry, emitter);
    }
    
    public static int getRainbow(final double d, final int offset) {
        float hue = (float)((System.currentTimeMillis() + offset) % d);
        hue /= (float)d;
        return Color.getHSBColor(hue, 1.0f, 1.0f).getRGB();
    }
    
    public static String a(final char[] chars) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static boolean handleOutPacket(final Packet packet, final EnumConnectionState packetState) {
        if (GuiBungeeCord.mc.isUUIDHack && packet instanceof C00Handshake) {
            if (((C00Handshake)packet).getRequestedState() == EnumConnectionState.LOGIN) {
                ((C00Handshake)packet).setIp(((C00Handshake)packet).getIp() + "\u0000" + Base.mc.getFakeIp() + "\u0000" + UUID.nameUUIDFromBytes(("OfflinePlayer:" + Base.mc.getFakeNick()).getBytes()).toString().replace("-", ""));
            }
            System.out.println(((C00Handshake)packet).getIp());
        }
        return packetState == EnumConnectionState.PLAY || true;
    }
    
    public SettingsManager getSettingManager() {
        return this.setmgr;
    }
    
    public void setup() {
        this.moduleManager = new ModuleManager();
        ModuleManager.load();
        SettingsManager.load();
    }
    
    public ResourceLocation getMainMenu() {
        return this.background;
    }
    
    public ResourceLocation getSinglePlayer() {
        return this.singleleplayer;
    }
    
    public ResourceLocation getvMenu() {
        return this.vMenu;
    }
    
    public ResourceLocation getModuleImg() {
        return this.ModuleImg;
    }
    
    public ResourceLocation getSettings() {
        return this.settings;
    }
    
    public ResourceLocation getPlayer() {
        return this.play;
    }
    
    public ResourceLocation getMultiPlayer() {
        return this.multiplayer;
    }
    
    public ResourceLocation getAlt() {
        return this.altmanager;
    }
    
    public ResourceLocation getExit() {
        return this.exit;
    }
    
    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }
    
    public CommandManager getCommandManager() {
        return this.commandManager;
    }
    
    public FriendManager getFriendManager() {
        return this.friendManager;
    }
    
    public ValueManager getValueManager() {
        return this.valueManager;
    }
    
    public File getRaidriarDir() {
        return this.raidriarDir;
    }
    
    public String getCLIENT_NAME() {
        return this.CLIENT_NAME;
    }
    
    public EventBus getEventManager() {
        return this.eventManager;
    }
    
    public int GetMainColor() {
        if (this.getSettingManager().getSettingByName("Rainbow").booleanValue()) {
            return this.MainColor = getRainbow(20000.0 - this.getSettingManager().getSettingByName("RainbowSpeed").doubleValue() * 1000.0, 1);
        }
        return this.MainColor = new Color(0, 230, 60).getRGB();
    }
    
    public void addIRCMessage(final String IRCMessage) {
        final String ChatPrefix = "§2[§2IRC§2] §f";
        MiscellaneousUtil.sendInfo(ChatPrefix + IRCMessage);
    }
    
    public IrcManager getIRC() {
        return this.ircManager;
    }
    
    public PortscanManager getPortscanManager() {
        return this.portscanManager;
    }
    
    public FontManager getFontManager() {
        return this.fontManager;
    }
    
    static {
        Base.mc = Minecraft.getMinecraft();
    }
}
