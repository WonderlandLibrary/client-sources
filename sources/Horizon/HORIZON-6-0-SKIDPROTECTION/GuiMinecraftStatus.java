package HORIZON-6-0-SKIDPROTECTION;

public class GuiMinecraftStatus extends GuiScreen
{
    private static final ResourceLocation_1975012498 Â;
    private String Ý;
    private String Ø­áŒŠá;
    private String Âµá€;
    private String Ó;
    public TimeHelper HorizonCode_Horizon_È;
    private int à;
    
    static {
        Â = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiMinecraftStatus() {
        this.Ý = "LOADING";
        this.Ø­áŒŠá = "LOADING";
        this.Âµá€ = "LOADING";
        this.Ó = "LOADING";
        this.HorizonCode_Horizon_È = new TimeHelper();
        this.à = 61;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.HorizonCode_Horizon_È.Â(1000L)) {
            --this.à;
            this.HorizonCode_Horizon_È.Ø­áŒŠá();
        }
        final ScaledResolution scaledRes = new ScaledResolution(GuiMinecraftStatus.Ñ¢á, GuiMinecraftStatus.Ñ¢á.Ó, GuiMinecraftStatus.Ñ¢á.à);
        GuiMinecraftStatus.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiMinecraftStatus.Â);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiMinecraftStatus.Çªà¢, GuiMinecraftStatus.Ê, -1627389951, -1610612736);
        try {
            if (this.Âµá€.contains("OK")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 205, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 115, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -14176672, 1613679729);
            }
            else if (this.Âµá€.contains("LOADING")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 205, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 115, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -932849, 1626577938);
                Gui_1808253012.Â(UIFonts.Â, "LOADING", GuiMinecraftStatus.Çªà¢ / 2 - 196, GuiMinecraftStatus.Ê / 2 + 8, -1);
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 205, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 115, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -1618884, 1623210283);
            }
            if (this.Ý.contains("OK")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 105, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 15, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -14176672, 1613679729);
            }
            else if (this.Ý.contains("LOADING")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 105, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 15, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -932849, 1626577938);
                Gui_1808253012.Â(UIFonts.Â, "LOADING", GuiMinecraftStatus.Çªà¢ / 2 - 95, GuiMinecraftStatus.Ê / 2 + 8, -1);
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - 105, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 - 15, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -1618884, 1623210283);
            }
            if (this.Ø­áŒŠá.contains("OK")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 15, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 105, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -14176672, 1613679729);
            }
            else if (this.Ø­áŒŠá.contains("LOADING")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 15, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 105, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -932849, 1626577938);
                Gui_1808253012.Â(UIFonts.Â, "LOADING", GuiMinecraftStatus.Çªà¢ / 2 + 25, GuiMinecraftStatus.Ê / 2 + 8, -1);
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 15, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 105, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -1618884, 1623210283);
            }
            if (this.Ó.contains("OK")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 115, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 205, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -14176672, 1613679729);
            }
            else if (this.Ó.contains("LOADING")) {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 115, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 205, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -932849, 1626577938);
                Gui_1808253012.Â(UIFonts.Â, "LOADING", GuiMinecraftStatus.Çªà¢ / 2 + 125, GuiMinecraftStatus.Ê / 2 + 8, -1);
            }
            else {
                RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 + 115, GuiMinecraftStatus.Ê / 2 - 25, GuiMinecraftStatus.Çªà¢ / 2 + 205, GuiMinecraftStatus.Ê / 2 + 65, 2.0f, -1618884, 1623210283);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        UIFonts.Â.HorizonCode_Horizon_È("SESSION", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SESSION") / 2 + 162), GuiMinecraftStatus.Ê / 2 - 55, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("SERVER", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SERVER") / 2 + 162), GuiMinecraftStatus.Ê / 2 - 42, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("AUTH", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("AUTH") / 2 + 62), GuiMinecraftStatus.Ê / 2 - 55, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("SERVER", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SERVER") / 2 + 62), GuiMinecraftStatus.Ê / 2 - 42, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("SKIN", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SKIN") / 2 - 58), GuiMinecraftStatus.Ê / 2 - 55, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("SERVER", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SERVER") / 2 - 58), GuiMinecraftStatus.Ê / 2 - 42, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("TEXTURE", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("TEXTURE") / 2 - 158), GuiMinecraftStatus.Ê / 2 - 55, -1249039);
        UIFonts.Â.HorizonCode_Horizon_È("SERVER", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SERVER") / 2 - 158), GuiMinecraftStatus.Ê / 2 - 42, -1249039);
        this.HorizonCode_Horizon_È(UIFonts.áˆºÑ¢Õ, "Status refreshes in " + this.à + " Seconds.", GuiScreen.Çªà¢ / 2 + 3, GuiMinecraftStatus.Ê - 18, -13330213);
        RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - this.à * 2, 48.0f, GuiMinecraftStatus.Çªà¢ / 2 + this.à * 2, 50.0f, -1);
        RenderHelper_1118140819.HorizonCode_Horizon_È(GuiMinecraftStatus.Çªà¢ / 2 - this.à * 2, 20.0f, GuiMinecraftStatus.Çªà¢ / 2 + this.à * 2, 22.0f, -1);
        UIFonts.Ó.HorizonCode_Horizon_È("SERVER STATUS", GuiScreen.Çªà¢ / 2 - (UIFonts.Â.HorizonCode_Horizon_È("SERVER STATUS") / 2 + 40), 20, -4340793);
        if (this.à == 0) {
            GuiMinecraftStatus.Ñ¢á.HorizonCode_Horizon_È(new GuiMinecraftStatus());
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (!Horizon.à¢.Ï.HorizonCode_Horizon_È.isRunning() && Horizon.Âµà && GuiMinecraftStatus.Ñ¢á.á == null) {
            Horizon.à¢.Ï.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menusong.wav");
            Horizon.à¢.Ï.HorizonCode_Horizon_È(-28.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        new Thread("Updater") {
            @Override
            public void run() {
                try {
                    GuiMinecraftStatus.HorizonCode_Horizon_È(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("https://sessionserver.mojang.com"));
                    GuiMinecraftStatus.Â(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("https://authserver.mojang.com"));
                    GuiMinecraftStatus.Ý(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("http://skins.minecraft.net"));
                    GuiMinecraftStatus.Ø­áŒŠá(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("http://textures.minecraft.net"));
                }
                catch (Exception e) {
                    try {
                        if (!Utils_1442407170.HorizonCode_Horizon_È("https://sessionserver.mojang.com").contains("OK")) {
                            GuiMinecraftStatus.HorizonCode_Horizon_È(GuiMinecraftStatus.this, "DOWN");
                        }
                        else {
                            GuiMinecraftStatus.HorizonCode_Horizon_È(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("https://sessionserver.mojang.com"));
                        }
                    }
                    catch (Exception e2) {
                        GuiMinecraftStatus.HorizonCode_Horizon_È(GuiMinecraftStatus.this, "DOWN");
                    }
                    try {
                        if (!Utils_1442407170.HorizonCode_Horizon_È("https://authserver.mojang.com").contains("OK")) {
                            GuiMinecraftStatus.Â(GuiMinecraftStatus.this, "DOWN");
                        }
                        else {
                            GuiMinecraftStatus.Â(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("https://authserver.mojang.com"));
                        }
                    }
                    catch (Exception e2) {
                        GuiMinecraftStatus.Â(GuiMinecraftStatus.this, "DOWN");
                    }
                    try {
                        if (!Utils_1442407170.HorizonCode_Horizon_È("http://skins.minecraft.net").contains("OK")) {
                            GuiMinecraftStatus.Ý(GuiMinecraftStatus.this, "DOWN");
                        }
                        else {
                            GuiMinecraftStatus.Ý(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("http://skins.minecraft.net"));
                        }
                    }
                    catch (Exception e2) {
                        GuiMinecraftStatus.Ý(GuiMinecraftStatus.this, "DOWN");
                    }
                    try {
                        if (!Utils_1442407170.HorizonCode_Horizon_È("http://textures.minecraft.net").contains("OK")) {
                            GuiMinecraftStatus.Ø­áŒŠá(GuiMinecraftStatus.this, "DOWN");
                            return;
                        }
                        GuiMinecraftStatus.Ø­áŒŠá(GuiMinecraftStatus.this, Utils_1442407170.HorizonCode_Horizon_È("http://textures.minecraft.net"));
                    }
                    catch (Exception e2) {
                        GuiMinecraftStatus.Ø­áŒŠá(GuiMinecraftStatus.this, "DOWN");
                    }
                }
            }
        }.start();
        final int var3 = GuiMinecraftStatus.Ê / 4 + 24;
        this.ÇŽÉ.add(new GuiMenuButton(1, 2, GuiMinecraftStatus.Ê - 20, 98, 20, "Back"));
        this.ÇŽÉ.add(new GuiMenuButton(2, GuiMinecraftStatus.Çªà¢ - 98, GuiMinecraftStatus.Ê - 20, 98, 20, "Update"));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) {
        switch (button.£à) {
            case 1: {
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/mainmenu/menuback.wav");
                Horizon.à¢.áˆºáˆºÈ.HorizonCode_Horizon_È(-18.0f);
                GuiMinecraftStatus.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                break;
            }
            case 2: {
                GuiMinecraftStatus.Ñ¢á.HorizonCode_Horizon_È(new GuiMinecraftStatus());
                break;
            }
        }
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final GuiMinecraftStatus guiMinecraftStatus, final String âµá€) {
        guiMinecraftStatus.Âµá€ = âµá€;
    }
    
    static /* synthetic */ void Â(final GuiMinecraftStatus guiMinecraftStatus, final String ý) {
        guiMinecraftStatus.Ý = ý;
    }
    
    static /* synthetic */ void Ý(final GuiMinecraftStatus guiMinecraftStatus, final String ø­áŒŠá) {
        guiMinecraftStatus.Ø­áŒŠá = ø­áŒŠá;
    }
    
    static /* synthetic */ void Ø­áŒŠá(final GuiMinecraftStatus guiMinecraftStatus, final String ó) {
        guiMinecraftStatus.Ó = ó;
    }
}
