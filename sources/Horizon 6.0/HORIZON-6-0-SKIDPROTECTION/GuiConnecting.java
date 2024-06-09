package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiConnecting extends GuiScreen
{
    private static final AtomicInteger Â;
    private NetworkManager Ý;
    private boolean Ø­áŒŠá;
    private final GuiScreen Âµá€;
    private static final String Ó = "CL_00000685";
    private static final ResourceLocation_1975012498 à;
    private int Ø;
    public static String HorizonCode_Horizon_È;
    
    static {
        Â = new AtomicInteger(0);
        à = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
        GuiConnecting.HorizonCode_Horizon_È = "";
    }
    
    public GuiConnecting(final GuiScreen p_i1181_1_, final Minecraft mcIn, final ServerData p_i1181_3_) {
        this.Ø = 0;
        GuiConnecting.Ñ¢á = mcIn;
        GuiMultiplayer.Â = p_i1181_3_;
        this.Âµá€ = p_i1181_1_;
        final ServerAddress var4 = ServerAddress.HorizonCode_Horizon_È(p_i1181_3_.Â);
        mcIn.HorizonCode_Horizon_È((WorldClient)null);
        mcIn.HorizonCode_Horizon_È(p_i1181_3_);
        this.HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È(), var4.Â());
    }
    
    public GuiConnecting(final GuiScreen p_i1182_1_, final Minecraft mcIn, final String p_i1182_3_, final int p_i1182_4_) {
        this.Ø = 0;
        GuiConnecting.HorizonCode_Horizon_È = p_i1182_3_;
        GuiConnecting.Ñ¢á = mcIn;
        this.Âµá€ = p_i1182_1_;
        mcIn.HorizonCode_Horizon_È((WorldClient)null);
        this.HorizonCode_Horizon_È(p_i1182_3_, p_i1182_4_);
    }
    
    private void HorizonCode_Horizon_È(final String ip, final int port) {
        if (Horizon.¥à) {
            return;
        }
        GuiConnecting.HorizonCode_Horizon_È = ip;
        Horizon.¥à = true;
        new Thread("Server Connector O" + GuiConnecting.Â.incrementAndGet()) {
            private static final String Â = "CL_00000686";
            
            @Override
            public void run() {
                InetAddress var1 = null;
                try {
                    if (GuiConnecting.this.Ø­áŒŠá) {
                        return;
                    }
                    var1 = InetAddress.getByName(ip);
                    GuiConnecting.HorizonCode_Horizon_È(GuiConnecting.this, NetworkManager.HorizonCode_Horizon_È(var1, port));
                    GuiConnecting.this.Ý.HorizonCode_Horizon_È(new NetHandlerLoginClient(GuiConnecting.this.Ý, GuiConnecting.Ñ¢á, GuiConnecting.this.Âµá€));
                    GuiConnecting.this.Ý.HorizonCode_Horizon_È(new C00Handshake(47, ip, port, EnumConnectionState.Ø­áŒŠá));
                    GuiConnecting.this.Ý.HorizonCode_Horizon_È(new C00PacketLoginStart(GuiConnecting.Ñ¢á.Õ().Âµá€()));
                }
                catch (UnknownHostException var4) {
                    if (GuiConnecting.this.Ø­áŒŠá) {
                        return;
                    }
                    GuiConnecting.Ñ¢á.HorizonCode_Horizon_È(new GuiDisconnected(GuiConnecting.this.Âµá€, "connect.failed", new ChatComponentText("Error while connecting to " + ip + "! (Unknown Hots)")));
                }
                catch (Exception var3) {
                    if (GuiConnecting.this.Ø­áŒŠá) {
                        return;
                    }
                    final String var2 = var3.toString();
                    GuiConnecting.Ñ¢á.HorizonCode_Horizon_È(new GuiDisconnected(GuiConnecting.this.Âµá€, "connect.failed", new ChatComponentText(var2)));
                }
                Horizon.¥à = false;
            }
        }.start();
    }
    
    @Override
    public void Ý() {
        if (this.Ý != null) {
            if (this.Ý.Âµá€()) {
                this.Ý.HorizonCode_Horizon_È();
            }
            else {
                this.Ý.áˆºÑ¢Õ();
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiMainMenuButton(0, GuiConnecting.Çªà¢ / 2 - 100, GuiConnecting.Ê / 2 + 70, 200, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0) {
            Horizon.¥à = false;
            this.Ø­áŒŠá = true;
            if (this.Ý != null) {
                this.Ý.HorizonCode_Horizon_È(new ChatComponentText("Aborted"));
            }
            GuiConnecting.Ñ¢á.HorizonCode_Horizon_È(new GuiMultiplayer(null));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledRes = new ScaledResolution(GuiConnecting.Ñ¢á, GuiConnecting.Ñ¢á.Ó, GuiConnecting.Ñ¢á.à);
        GuiConnecting.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiConnecting.à);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiConnecting.Çªà¢, GuiConnecting.Ê, 1325400064);
        switch (++this.Ø) {
            case 1: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888888888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0888888888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0088888888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0008888888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000888888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 6: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000088888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 7: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000008888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 8: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000000888]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 9: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000000088]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 10: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000000008]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 11: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[0000000000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 12: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8000000000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 13: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8800000000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 14: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8880000000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 15: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888000000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 16: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888800000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 17: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888880000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 18: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888888000]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 19: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888888800]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                break;
            }
            case 20: {
                this.HorizonCode_Horizon_È(UIFonts.Ý, "[8888888880]", GuiConnecting.Çªà¢ / 2, 10, 11184810);
                this.Ø = 0;
                break;
            }
        }
        if (this.Ý == null) {
            this.HorizonCode_Horizon_È(UIFonts.Ý, "Connecting to [Connecting]", GuiConnecting.Çªà¢ / 2, GuiConnecting.Ê / 2 + 30, -1249039);
        }
        else {
            this.HorizonCode_Horizon_È(UIFonts.Ý, "Connecting to [Logging in]", GuiConnecting.Çªà¢ / 2, GuiConnecting.Ê / 2 + 30, -1249039);
        }
        this.HorizonCode_Horizon_È(UIFonts.Ý, GuiConnecting.HorizonCode_Horizon_È, GuiConnecting.Çªà¢ / 2, GuiConnecting.Ê / 2 + 42, -13330213);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final GuiConnecting guiConnecting, final NetworkManager ý) {
        guiConnecting.Ý = ý;
    }
}
