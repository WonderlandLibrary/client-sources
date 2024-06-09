package HORIZON-6-0-SKIDPROTECTION;

import tv.twitch.broadcast.IngestServer;
import java.io.IOException;

public class GuiIngestServers extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private String Â;
    private HorizonCode_Horizon_È Ý;
    private static final String Ø­áŒŠá = "CL_00001843";
    
    public GuiIngestServers(final GuiScreen p_i46312_1_) {
        this.HorizonCode_Horizon_È = p_i46312_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Â = I18n.HorizonCode_Horizon_È("options.stream.ingest.title", new Object[0]);
        this.Ý = new HorizonCode_Horizon_È(GuiIngestServers.Ñ¢á);
        if (!GuiIngestServers.Ñ¢á.Ä().Æ()) {
            GuiIngestServers.Ñ¢á.Ä().Ø­à();
        }
        this.ÇŽÉ.add(new GuiButton(1, GuiIngestServers.Çªà¢ / 2 - 155, GuiIngestServers.Ê - 24 - 6, 150, 20, I18n.HorizonCode_Horizon_È("gui.done", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(2, GuiIngestServers.Çªà¢ / 2 + 5, GuiIngestServers.Ê - 24 - 6, 150, 20, I18n.HorizonCode_Horizon_È("options.stream.ingest.reset", new Object[0])));
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        this.Ý.Ø();
    }
    
    @Override
    public void q_() {
        if (GuiIngestServers.Ñ¢á.Ä().Æ()) {
            GuiIngestServers.Ñ¢á.Ä().µÕ().à();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                GuiIngestServers.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
            else {
                GuiIngestServers.Ñ¢á.ŠÄ.Çª = "";
                GuiIngestServers.Ñ¢á.ŠÄ.Â();
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiIngestServers.Çªà¢ / 2, 20, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    class HorizonCode_Horizon_È extends GuiSlot
    {
        private static final String Â = "CL_00001842";
        
        public HorizonCode_Horizon_È(final Minecraft mcIn) {
            super(mcIn, GuiIngestServers.Çªà¢, GuiIngestServers.Ê, 32, GuiIngestServers.Ê - 35, (int)(mcIn.µà.HorizonCode_Horizon_È * 3.5));
            this.HorizonCode_Horizon_È(false);
        }
        
        @Override
        protected int HorizonCode_Horizon_È() {
            return this.Ý.Ä().¥Æ().length;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
            this.Ý.ŠÄ.Çª = this.Ý.Ä().¥Æ()[slotIndex].serverUrl;
            this.Ý.ŠÄ.Â();
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final int slotIndex) {
            return this.Ý.Ä().¥Æ()[slotIndex].serverUrl.equals(this.Ý.ŠÄ.Çª);
        }
        
        @Override
        protected void Â() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final int p_180791_1_, int p_180791_2_, final int p_180791_3_, final int p_180791_4_, final int p_180791_5_, final int p_180791_6_) {
            final IngestServer var7 = this.Ý.Ä().¥Æ()[p_180791_1_];
            String var8 = var7.serverUrl.replaceAll("\\{stream_key\\}", "");
            String var9 = String.valueOf((int)var7.bitrateKbps) + " kbps";
            String var10 = null;
            final IngestServerTester var11 = this.Ý.Ä().µÕ();
            if (var11 != null) {
                if (var7 == var11.HorizonCode_Horizon_È()) {
                    var8 = EnumChatFormatting.ÂµÈ + var8;
                    var9 = String.valueOf((int)(var11.Ø­áŒŠá() * 100.0f)) + "%";
                }
                else if (p_180791_1_ < var11.Â()) {
                    if (var7.bitrateKbps == 0.0f) {
                        var9 = EnumChatFormatting.ˆÏ­ + "Down!";
                    }
                }
                else {
                    var9 = EnumChatFormatting.µà + "1234" + EnumChatFormatting.Æ + " kbps";
                }
            }
            else if (var7.bitrateKbps == 0.0f) {
                var9 = EnumChatFormatting.ˆÏ­ + "Down!";
            }
            p_180791_2_ -= 15;
            if (this.HorizonCode_Horizon_È(p_180791_1_)) {
                var10 = EnumChatFormatting.áˆºÑ¢Õ + "(Preferred)";
            }
            else if (var7.defaultServer) {
                var10 = EnumChatFormatting.ÂµÈ + "(Default)";
            }
            Gui_1808253012.Â(GuiIngestServers.this.É, var7.serverName, p_180791_2_ + 2, p_180791_3_ + 5, 16777215);
            Gui_1808253012.Â(GuiIngestServers.this.É, var8, p_180791_2_ + 2, p_180791_3_ + GuiIngestServers.this.É.HorizonCode_Horizon_È + 5 + 3, 3158064);
            Gui_1808253012.Â(GuiIngestServers.this.É, var9, this.à() - 5 - GuiIngestServers.this.É.HorizonCode_Horizon_È(var9), p_180791_3_ + 5, 8421504);
            if (var10 != null) {
                Gui_1808253012.Â(GuiIngestServers.this.É, var10, this.à() - 5 - GuiIngestServers.this.É.HorizonCode_Horizon_È(var10), p_180791_3_ + 5 + 3 + GuiIngestServers.this.É.HorizonCode_Horizon_È, 8421504);
            }
        }
        
        @Override
        protected int à() {
            return super.à() + 15;
        }
    }
}
