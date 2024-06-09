//package us.dev.direkt.module.internal.core.protocol;
//
//import com.google.common.collect.ImmutableMap;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiDisconnected;
//import net.minecraft.client.gui.GuiLabel;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.multiplayer.GuiConnecting;
//import net.minecraft.client.multiplayer.ServerAddress;
//import net.minecraft.client.network.NetHandlerLoginClient;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.network.EnumConnectionState;
//import net.minecraft.network.NetworkManager;
//import net.minecraft.network.handshake.client.C00Handshake;
//import net.minecraft.network.login.client.CPacketLoginStart;
//import net.minecraft.network.login.server.SPacketDisconnect;
//import net.minecraft.network.login.server.SPacketEncryptionRequest;
//import net.minecraft.util.text.TextComponentString;
//import net.minecraft.util.text.TextComponentTranslation;
//import org.apache.commons.lang3.StringUtils;
//import us.dev.direkt.Direkt;
//import us.dev.direkt.Wrapper;
//import us.dev.direkt.event.internal.events.game.network.EventDecodePacket;
//import us.dev.direkt.event.internal.events.game.server.EventServerDisconnect;
//import us.dev.direkt.module.ModCategory;
//import us.dev.direkt.module.annotations.ModData;
//import us.dev.direkt.module.Module;
//import us.dev.direkt.module.internal.core.protocol.adapter.ProtocolAdapter;
//import us.dev.direkt.module.internal.core.protocol.adapter.adapters.AdapterVersion108;
//import us.dev.direkt.module.internal.core.protocol.adapter.adapters.AdapterVersion109;
//import us.dev.direkt.module.internal.core.protocol.adapter.adapters.AdapterVersion110;
//import us.dev.direkt.module.internal.core.protocol.adapter.adapters.AdapterVersion210;
//import us.dev.direkt.module.internal.core.protocol.adapter.adapters.AdapterVersion47;
//import us.dev.dvent.Listener;
//import us.dev.dvent.Link;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Map;
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
///**
// * @author Foundry
// */
//@ModData(label = "Protocol Hack", category = ModCategory.CORE)
//public class ProtocolHack extends Module {
//
//    private static final Map<String, ? extends ProtocolAdapter> protocolMap = new ImmutableMap.Builder<String, ProtocolAdapter>()
//            /* Forwards Compatibility */
//    		.put("Outdated client! Please use 1.9.1", new AdapterVersion108())
//            .put("Outdated client! Please use 1.9.2", new AdapterVersion109())
//            .put("Outdated client! Please use 1.9.4", new AdapterVersion110())
//            .put("OUTDATED CLIENT. USE 1.9.4", new AdapterVersion110())
//            .put("Outdated client! Please use 1.10", new AdapterVersion210())
//            /* Backwards Compatibility */
//            .put("Outdated server! I'm still on 1.8.8", new AdapterVersion47())
//            .build();
//
//    private static final String GENERIC_OUTDATED_MESSAGE = "Outdated";
//
//    private static final String GENERIC_EXCEPTION_MESSAGE = "Internal Exception";
//
//    private final Executor adapterExecutor = Executors.newSingleThreadExecutor();
//    private final Object mutex = new Object();
//    private volatile boolean foundAdapter, isSearchingForAdapter;
//
//    private volatile ProtocolAdapter currentAdapter;
//    
//    private static int time;
//
//    @Listener
//    protected Link<EventDecodePacket> onDecodePacket = new Link<>(event -> {
//        if (isSearchingForAdapter) {
//            if (event.getPacket() instanceof SPacketDisconnect) {
//                synchronized (mutex) {
//                    mutex.notify();
//                }
//                event.setCancelled(true);
//            }
//            else if (event.getPacket() instanceof SPacketEncryptionRequest) {
//                foundAdapter = true;
//                synchronized (mutex) {
//                    mutex.notify();
//                }
//                event.setCancelled(true);
//            }
//        }
//    });
//
//    @Listener
//    protected Link<EventServerDisconnect> onServerDisconnect = new Link<>(event -> {
//        if (currentAdapter != null) {
//            Direkt.getInstance().getEventManager().unregister(currentAdapter);
//            currentAdapter = null;
//        }
//
//        ProtocolAdapter lookupAdapter;
//        if (event.getMessage().isPresent()) {
//            String[] messageSplits;
//            if ((lookupAdapter = protocolMap.get(event.getMessage().get())) != null) {
//                currentAdapter = lookupAdapter;
//                tryAdaptedConnection(this.currentAdapter);
//            } else if ((messageSplits = event.getMessage().get().split(": ")).length > 1) {
//                for (String s : messageSplits) {
//                    if ((lookupAdapter = protocolMap.get(s)) != null) {
//                        currentAdapter = lookupAdapter;
//                        tryAdaptedConnection(this.currentAdapter);
//                    }
//                }
//            } else if (!isSearchingForAdapter && StringUtils.containsIgnoreCase(event.getMessage().get(), GENERIC_OUTDATED_MESSAGE)) {
//                isSearchingForAdapter = true;
//
//                Wrapper.getMinecraft().displayGuiScreen(new GuiScreen() {
//                    @Override
//                    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//                        super.drawScreen(mouseX, mouseY, partialTicks);
//                        this.drawDefaultBackground();
//
//                        this.drawCenteredString(this.fontRendererObj, "Adapting connection for protocol", this.width / 2, this.height / 2 - 35, 16777215);
//                        this.drawCenteredString(this.fontRendererObj, "Determining best adapter...", this.width / 2, this.height / 2 - this.fontRendererObj.FONT_HEIGHT / 2 - this.fontRendererObj.FONT_HEIGHT * 2 + 10, 11184810);
//
//                        if (ProtocolHack.this.currentAdapter != null) {
//                            tryAdaptedConnection(currentAdapter);
//                        }
//                    }
//                });
//
//                adapterExecutor.execute(() -> {
//                    try {
//                        final ServerAddress serveraddress = ServerAddress.fromString(Wrapper.getMinecraft().getCurrentServerData().serverIP);
//                        for (ProtocolAdapter adapter : protocolMap.values()) {
//                            final NetworkManager networkManager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort(), Wrapper.getGameSettings().isUsingNativeTransport());
//
//                            networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, Wrapper.getMinecraft(), null));
//                            networkManager.sendPacket(new C00Handshake(adapter.getProtocolVersion(), serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.LOGIN));
//                            networkManager.sendPacket(new CPacketLoginStart(Wrapper.getMinecraft().getSession().getProfile()));
//
//                            synchronized (mutex) {
//                                mutex.wait();
//                            }
//
//                            networkManager.closeChannel(new TextComponentString("Quitting"));
//                            if (foundAdapter) {
//                                foundAdapter = isSearchingForAdapter = false;
//                                currentAdapter = adapter;
//                                return;
//                            }
//                        }
//
//                        if (currentAdapter == null) {
//                            foundAdapter = isSearchingForAdapter = false;
//                            Wrapper.getMinecraft().displayGuiScreen(new GuiDisconnected(null, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Failed to adapt connection to server protocol")) {
//                                @Override
//                                public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//                                    this.drawDefaultBackground();
//
//                                    this.drawCenteredString(this.fontRendererObj, this.message.getFormattedText(), this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
//                                    int i = this.height / 2 - this.textHeight / 2;
//
//                                    if (this.multilineMessage != null) {
//                                        for (String s : this.multilineMessage) {
//                                            this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
//                                            i += this.fontRendererObj.FONT_HEIGHT;
//                                        }
//                                    }
//
//                                    for (GuiButton button : this.buttonList) {
//                                        button.drawButton(this.mc, mouseX, mouseY);
//                                    }
//
//                                    for (GuiLabel label : this.labelList) {
//                                        label.drawLabel(this.mc, mouseX, mouseY);
//                                    }
//                                }
//
//                                @Override
//                                public void initGui() {
//                                    this.buttonList.clear();
//                                    this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(event.getMessage().get(), this.width - 50);
//                                    this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
//                                    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu")));
//                                }
//                            });
//                        }
//                    } catch (UnknownHostException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//            } else if (StringUtils.containsIgnoreCase(event.getMessage().get(), GENERIC_EXCEPTION_MESSAGE)) {
//                Wrapper.getMinecraft().displayGuiScreen(new GuiDisconnected(null, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Failed to adapt connection to server protocol")) {
//                    @Override
//                    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//                        this.drawDefaultBackground();
//
//                        this.drawCenteredString(this.fontRendererObj, this.message.getFormattedText(), this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
//                        int i = this.height / 2 - this.textHeight / 2;
//
//                        if (this.multilineMessage != null) {
//                            for (String s : this.multilineMessage) {
//                                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
//                                i += this.fontRendererObj.FONT_HEIGHT;
//                            }
//                        }
//
//                        for (GuiButton button : this.buttonList) {
//                            button.drawButton(this.mc, mouseX, mouseY);
//                        }
//
//                        for (GuiLabel label : this.labelList) {
//                            label.drawLabel(this.mc, mouseX, mouseY);
//                        }
//                    }
//
//                    @Override
//                    public void initGui() {
//                        this.buttonList.clear();
//                        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(event.getMessage().get(), this.width - 50);
//                        this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
//                        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu")));
//                    }
//                });
//            }
//        }
//    });
//    
//    private static void tryAdaptedConnection(ProtocolAdapter adapter) {
//        Direkt.getInstance().getEventManager().register(adapter);
//        
//        time = 0;
//        
//        Wrapper.getMinecraft().displayGuiScreen(new GuiConnecting(null, Wrapper.getMinecraft(), Wrapper.getMinecraft().getCurrentServerData()) {
//        	
//            @Override
//            protected void connect(final String ip, final int port) {
//            	if(time == 1){
//                    new Thread(() -> {
//                    	//This is here becuase "Connection throttled!"
//                    	try {
//    						Thread.sleep(4000);
//    					} catch (Exception e1) {
//    						// TODO Auto-generated catch block
//    						e1.printStackTrace();
//    					}
//                        InetAddress address = null;
//                        try {
//                            final ServerAddress serveraddress = ServerAddress.fromString(Wrapper.getMinecraft().getCurrentServerData().serverIP);
//                            networkManager = NetworkManager.createNetworkManagerAndConnect((address = InetAddress.getByName(serveraddress.getIP())), serveraddress.getPort(), Wrapper.getGameSettings().isUsingNativeTransport());
//                            networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, Wrapper.getMinecraft(), null));
//                            networkManager.sendPacket(new C00Handshake(adapter.getProtocolVersion(), serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.LOGIN));
//                            networkManager.sendPacket(new CPacketLoginStart(Wrapper.getMinecraft().getSession().getProfile()));
//
//                        } catch (UnknownHostException e){
//                            Wrapper.getMinecraft().displayGuiScreen(new GuiDisconnected(null, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Unknown host")));
//
//                        } catch (Exception e) {
//                            String s = e.toString();
//
//                            if (address != null) {
//                                String s1 = address.toString() + ":" + port;
//                                s = s.replaceAll(s1, "");
//                            }
//
//                            Wrapper.getMinecraft().displayGuiScreen(new GuiDisconnected(null, "connect.failed", new TextComponentTranslation("disconnect.genericReason", s)));
//                        }
//                    }).run();
//            	}
//            }
//        	
//            @Override
//            public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//            	if(time == 1){
//            		final ServerAddress serveraddress = ServerAddress.fromString(Wrapper.getMinecraft().getCurrentServerData().serverIP);
//            		this.connect(serveraddress.getIP(), serveraddress.getPort());
//            	}
//            	else{
//                    this.drawDefaultBackground();
//
//                    this.drawCenteredString(this.fontRendererObj, "Adapting connection for protocol " + adapter.getProtocolVersion() + " (" + adapter.getGameVersion() + ")...", this.width / 2, this.height / 2 - 50, 16777215);
//
//                    for (GuiButton button : this.buttonList) {
//                        button.drawButton(this.mc, mouseX, mouseY);
//                    }
//
//                    for (GuiLabel label : this.labelList) {
//                        label.drawLabel(this.mc, mouseX, mouseY);
//                    }
//            	}
//                time++;
//            }
//        });
//    }
//}
