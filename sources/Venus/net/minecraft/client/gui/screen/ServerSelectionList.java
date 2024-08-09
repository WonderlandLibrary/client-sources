/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerSelectionList
extends ExtendedList<Entry> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ThreadPoolExecutor field_214358_b = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(false).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER)).build());
    private static final ResourceLocation field_214359_c = new ResourceLocation("textures/misc/unknown_server.png");
    private static final ResourceLocation field_214360_d = new ResourceLocation("textures/gui/server_selection.png");
    private static final ITextComponent field_243365_r = new TranslationTextComponent("lanServer.scanning");
    private static final ITextComponent field_243366_s = new TranslationTextComponent("multiplayer.status.cannot_resolve").mergeStyle(TextFormatting.DARK_RED);
    private static final ITextComponent field_243367_t = new TranslationTextComponent("multiplayer.status.cannot_connect").mergeStyle(TextFormatting.DARK_RED);
    private static final ITextComponent field_244607_u = new TranslationTextComponent("multiplayer.status.incompatible");
    private static final ITextComponent field_243370_w = new TranslationTextComponent("multiplayer.status.no_connection");
    private static final ITextComponent field_243371_x = new TranslationTextComponent("multiplayer.status.pinging");
    private final MultiplayerScreen owner;
    private final List<NormalEntry> serverListInternet = Lists.newArrayList();
    private final Entry lanScanEntry = new LanScanEntry();
    private final List<LanDetectedEntry> serverListLan = Lists.newArrayList();

    public ServerSelectionList(MultiplayerScreen multiplayerScreen, Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.owner = multiplayerScreen;
    }

    private void setList() {
        this.clearEntries();
        this.serverListInternet.forEach(arg_0 -> ServerSelectionList.lambda$setList$0(this, arg_0));
        this.addEntry(this.lanScanEntry);
        this.serverListLan.forEach(arg_0 -> ServerSelectionList.lambda$setList$1(this, arg_0));
    }

    @Override
    public void setSelected(@Nullable Entry entry) {
        super.setSelected(entry);
        if (this.getSelected() instanceof NormalEntry) {
            NarratorChatListener.INSTANCE.say(new TranslationTextComponent("narrator.select", ((NormalEntry)this.getSelected()).server.serverName).getString());
        }
        this.owner.func_214295_b();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        Entry entry = (Entry)this.getSelected();
        return entry != null && entry.keyPressed(n, n2, n3) || super.keyPressed(n, n2, n3);
    }

    @Override
    protected void moveSelection(AbstractList.Ordering ordering) {
        this.func_241572_a_(ordering, ServerSelectionList::lambda$moveSelection$2);
    }

    public void updateOnlineServers(ServerList serverList) {
        this.serverListInternet.clear();
        for (int i = 0; i < serverList.countServers(); ++i) {
            this.serverListInternet.add(new NormalEntry(this, this.owner, serverList.getServerData(i)));
        }
        this.setList();
    }

    public void updateNetworkServers(List<LanServerInfo> list) {
        this.serverListLan.clear();
        for (LanServerInfo lanServerInfo : list) {
            this.serverListLan.add(new LanDetectedEntry(this.owner, lanServerInfo));
        }
        this.setList();
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 30;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 85;
    }

    @Override
    protected boolean isFocused() {
        return this.owner.getListener() == this;
    }

    @Override
    public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
        this.setSelected((Entry)abstractListEntry);
    }

    private static boolean lambda$moveSelection$2(Entry entry) {
        return !(entry instanceof LanScanEntry);
    }

    private static void lambda$setList$1(ServerSelectionList serverSelectionList, AbstractList.AbstractListEntry abstractListEntry) {
        serverSelectionList.addEntry(abstractListEntry);
    }

    private static void lambda$setList$0(ServerSelectionList serverSelectionList, AbstractList.AbstractListEntry abstractListEntry) {
        serverSelectionList.addEntry(abstractListEntry);
    }

    static void access$000(ServerSelectionList serverSelectionList, AbstractList.AbstractListEntry abstractListEntry) {
        serverSelectionList.ensureVisible(abstractListEntry);
    }

    static int access$100(ServerSelectionList serverSelectionList, int n) {
        return serverSelectionList.getRowTop(n);
    }

    public static class LanScanEntry
    extends Entry {
        private final Minecraft mc = Minecraft.getInstance();

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            int n8 = n2 + n5 / 2 - 4;
            this.mc.fontRenderer.func_243248_b(matrixStack, field_243365_r, this.mc.currentScreen.width / 2 - this.mc.fontRenderer.getStringPropertyWidth(field_243365_r) / 2, n8, 0xFFFFFF);
            String string = switch ((int)(Util.milliTime() / 300L % 4L)) {
                default -> "O o o";
                case 1, 3 -> "o O o";
                case 2 -> "o o O";
            };
            this.mc.fontRenderer.drawString(matrixStack, string, this.mc.currentScreen.width / 2 - this.mc.fontRenderer.getStringWidth(string) / 2, n8 + 9, 0x808080);
        }
    }

    public static abstract class Entry
    extends AbstractList.AbstractListEntry<Entry> {
    }

    public class NormalEntry
    extends Entry {
        private final MultiplayerScreen owner;
        private final Minecraft mc;
        private final ServerData server;
        private final ResourceLocation serverIcon;
        private String lastIconB64;
        private DynamicTexture icon;
        private long lastClickTime;
        final ServerSelectionList this$0;

        protected NormalEntry(ServerSelectionList serverSelectionList, MultiplayerScreen multiplayerScreen, ServerData serverData) {
            this.this$0 = serverSelectionList;
            this.owner = multiplayerScreen;
            this.server = serverData;
            this.mc = Minecraft.getInstance();
            this.serverIcon = new ResourceLocation("servers/" + Hashing.sha1().hashUnencodedChars(serverData.serverIP) + "/icon");
            this.icon = (DynamicTexture)this.mc.getTextureManager().getTexture(this.serverIcon);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            List<ITextComponent> list;
            ITextComponent iTextComponent;
            int n8;
            if (!this.server.pinged) {
                this.server.pinged = true;
                this.server.pingToServer = -2L;
                this.server.serverMOTD = StringTextComponent.EMPTY;
                this.server.populationInfo = StringTextComponent.EMPTY;
                field_214358_b.submit(this::lambda$render$1);
            }
            boolean bl2 = this.server.version != SharedConstants.getVersion().getProtocolVersion();
            this.mc.fontRenderer.drawString(matrixStack, this.server.serverName, n3 + 32 + 3, n2 + 1, 0xFFFFFF);
            List<IReorderingProcessor> list2 = this.mc.fontRenderer.trimStringToWidth(this.server.serverMOTD, n4 - 32 - 2);
            for (int i = 0; i < Math.min(list2.size(), 2); ++i) {
                this.mc.fontRenderer.func_238422_b_(matrixStack, list2.get(i), n3 + 32 + 3, n2 + 12 + 9 * i, 0x808080);
            }
            ITextComponent iTextComponent2 = bl2 ? this.server.gameVersion.deepCopy().mergeStyle(TextFormatting.RED) : this.server.populationInfo;
            int n9 = this.mc.fontRenderer.getStringPropertyWidth(iTextComponent2);
            this.mc.fontRenderer.func_243248_b(matrixStack, iTextComponent2, n3 + n4 - n9 - 15 - 2, n2 + 1, 0x808080);
            int n10 = 0;
            if (bl2) {
                n8 = 5;
                iTextComponent = field_244607_u;
                list = this.server.playerList;
            } else if (this.server.pinged && this.server.pingToServer != -2L) {
                n8 = this.server.pingToServer < 0L ? 5 : (this.server.pingToServer < 150L ? 0 : (this.server.pingToServer < 300L ? 1 : (this.server.pingToServer < 600L ? 2 : (this.server.pingToServer < 1000L ? 3 : 4))));
                if (this.server.pingToServer < 0L) {
                    iTextComponent = field_243370_w;
                    list = Collections.emptyList();
                } else {
                    iTextComponent = new TranslationTextComponent("multiplayer.status.ping", this.server.pingToServer);
                    list = this.server.playerList;
                }
            } else {
                n10 = 1;
                n8 = (int)(Util.milliTime() / 100L + (long)(n * 2) & 7L);
                if (n8 > 4) {
                    n8 = 8 - n8;
                }
                iTextComponent = field_243371_x;
                list = Collections.emptyList();
            }
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
            AbstractGui.blit(matrixStack, n3 + n4 - 15, n2, n10 * 10, 176 + n8 * 8, 10, 8, 256, 256);
            String string = this.server.getBase64EncodedIconData();
            if (!Objects.equals(string, this.lastIconB64)) {
                if (this.func_241614_a_(string)) {
                    this.lastIconB64 = string;
                } else {
                    this.server.setBase64EncodedIconData(null);
                    this.func_241613_a_();
                }
            }
            if (this.icon != null) {
                this.func_238859_a_(matrixStack, n3, n2, this.serverIcon);
            } else {
                this.func_238859_a_(matrixStack, n3, n2, field_214359_c);
            }
            int n11 = n6 - n3;
            int n12 = n7 - n2;
            if (n11 >= n4 - 15 && n11 <= n4 - 5 && n12 >= 0 && n12 <= 8) {
                this.owner.func_238854_b_(Collections.singletonList(iTextComponent));
            } else if (n11 >= n4 - n9 - 15 - 2 && n11 <= n4 - 15 - 2 && n12 >= 0 && n12 <= 8) {
                this.owner.func_238854_b_(list);
            }
            if (this.mc.gameSettings.touchscreen || bl) {
                this.mc.getTextureManager().bindTexture(field_214360_d);
                AbstractGui.fill(matrixStack, n3, n2, n3 + 32, n2 + 32, -1601138544);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                int n13 = n6 - n3;
                int n14 = n7 - n2;
                if (this.canJoin()) {
                    if (n13 < 32 && n13 > 16) {
                        AbstractGui.blit(matrixStack, n3, n2, 0.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        AbstractGui.blit(matrixStack, n3, n2, 0.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (n > 0) {
                    if (n13 < 16 && n14 < 16) {
                        AbstractGui.blit(matrixStack, n3, n2, 96.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        AbstractGui.blit(matrixStack, n3, n2, 96.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (n < this.owner.getServerList().countServers() - 1) {
                    if (n13 < 16 && n14 > 16) {
                        AbstractGui.blit(matrixStack, n3, n2, 64.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        AbstractGui.blit(matrixStack, n3, n2, 64.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
            }
        }

        public void func_241613_a_() {
            this.owner.getServerList().saveServerList();
        }

        protected void func_238859_a_(MatrixStack matrixStack, int n, int n2, ResourceLocation resourceLocation) {
            this.mc.getTextureManager().bindTexture(resourceLocation);
            RenderSystem.enableBlend();
            AbstractGui.blit(matrixStack, n, n2, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.disableBlend();
        }

        private boolean canJoin() {
            return false;
        }

        private boolean func_241614_a_(@Nullable String string) {
            if (string == null) {
                this.mc.getTextureManager().deleteTexture(this.serverIcon);
                if (this.icon != null && this.icon.getTextureData() != null) {
                    this.icon.getTextureData().close();
                }
                this.icon = null;
            } else {
                try {
                    NativeImage nativeImage = NativeImage.readBase64(string);
                    Validate.validState(nativeImage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                    Validate.validState(nativeImage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                    if (this.icon == null) {
                        this.icon = new DynamicTexture(nativeImage);
                    } else {
                        this.icon.setTextureData(nativeImage);
                        this.icon.updateDynamicTexture();
                    }
                    this.mc.getTextureManager().loadTexture(this.serverIcon, this.icon);
                } catch (Throwable throwable) {
                    LOGGER.error("Invalid icon for server {} ({})", (Object)this.server.serverName, (Object)this.server.serverIP, (Object)throwable);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean keyPressed(int n, int n2, int n3) {
            if (Screen.hasShiftDown()) {
                ServerSelectionList serverSelectionList = this.owner.serverListSelector;
                int n4 = serverSelectionList.getEventListeners().indexOf(this);
                if (n == 264 && n4 < this.owner.getServerList().countServers() - 1 || n == 265 && n4 > 0) {
                    this.func_228196_a_(n4, n == 264 ? n4 + 1 : n4 - 1);
                    return false;
                }
            }
            return super.keyPressed(n, n2, n3);
        }

        private void func_228196_a_(int n, int n2) {
            this.owner.getServerList().swapServers(n, n2);
            this.owner.serverListSelector.updateOnlineServers(this.owner.getServerList());
            Entry entry = (Entry)this.owner.serverListSelector.getEventListeners().get(n2);
            this.owner.serverListSelector.setSelected(entry);
            ServerSelectionList.access$000(this.this$0, entry);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            double d3 = d - (double)this.this$0.getRowLeft();
            double d4 = d2 - (double)ServerSelectionList.access$100(this.this$0, this.this$0.getEventListeners().indexOf(this));
            if (d3 <= 32.0) {
                if (d3 < 32.0 && d3 > 16.0 && this.canJoin()) {
                    this.owner.func_214287_a(this);
                    this.owner.connectToSelected();
                    return false;
                }
                int n2 = this.owner.serverListSelector.getEventListeners().indexOf(this);
                if (d3 < 16.0 && d4 < 16.0 && n2 > 0) {
                    this.func_228196_a_(n2, n2 - 1);
                    return false;
                }
                if (d3 < 16.0 && d4 > 16.0 && n2 < this.owner.getServerList().countServers() - 1) {
                    this.func_228196_a_(n2, n2 + 1);
                    return false;
                }
            }
            this.owner.func_214287_a(this);
            if (Util.milliTime() - this.lastClickTime < 250L) {
                this.owner.connectToSelected();
            }
            this.lastClickTime = Util.milliTime();
            return true;
        }

        public ServerData getServerData() {
            return this.server;
        }

        private void lambda$render$1() {
            try {
                this.owner.getOldServerPinger().ping(this.server, this::lambda$render$0);
            } catch (UnknownHostException unknownHostException) {
                this.server.pingToServer = -1L;
                this.server.serverMOTD = field_243366_s;
            } catch (Exception exception) {
                this.server.pingToServer = -1L;
                this.server.serverMOTD = field_243367_t;
            }
        }

        private void lambda$render$0() {
            this.mc.execute(this::func_241613_a_);
        }
    }

    public static class LanDetectedEntry
    extends Entry {
        private static final ITextComponent field_243386_c = new TranslationTextComponent("lanServer.title");
        private static final ITextComponent field_243387_d = new TranslationTextComponent("selectServer.hiddenAddress");
        private final MultiplayerScreen screen;
        protected final Minecraft mc;
        protected final LanServerInfo serverData;
        private long lastClickTime;

        protected LanDetectedEntry(MultiplayerScreen multiplayerScreen, LanServerInfo lanServerInfo) {
            this.screen = multiplayerScreen;
            this.serverData = lanServerInfo;
            this.mc = Minecraft.getInstance();
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.mc.fontRenderer.func_243248_b(matrixStack, field_243386_c, n3 + 32 + 3, n2 + 1, 0xFFFFFF);
            this.mc.fontRenderer.drawString(matrixStack, this.serverData.getServerMotd(), n3 + 32 + 3, n2 + 12, 0x808080);
            if (this.mc.gameSettings.hideServerAddress) {
                this.mc.fontRenderer.func_243248_b(matrixStack, field_243387_d, n3 + 32 + 3, n2 + 12 + 11, 0x303030);
            } else {
                this.mc.fontRenderer.drawString(matrixStack, this.serverData.getServerIpPort(), n3 + 32 + 3, n2 + 12 + 11, 0x303030);
            }
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            this.screen.func_214287_a(this);
            if (Util.milliTime() - this.lastClickTime < 250L) {
                this.screen.connectToSelected();
            }
            this.lastClickTime = Util.milliTime();
            return true;
        }

        public LanServerInfo getServerData() {
            return this.serverData;
        }
    }
}

