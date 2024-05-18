package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import java.util.ArrayList;
import java.util.List;

public class GuiIRCChat extends GuiScreen
{
    public GuiTextField HorizonCode_Horizon_È;
    public static List<IRCMessage> Â;
    
    static {
        GuiIRCChat.Â = new ArrayList<IRCMessage>();
    }
    
    public GuiIRCChat() {
        if (Horizon.à¢.£ÂµÄ == null) {
            Horizon.à¢.£ÂµÄ = new IRCClient(1337);
            new Thread("Say Hello!") {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500L);
                    }
                    catch (InterruptedException ex) {}
                    Horizon.à¢.£ÂµÄ.HorizonCode_Horizon_È();
                }
            }.start();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiIrcInvisibleButton(88, GuiIRCChat.Çªà¢ - 59, GuiIRCChat.Ê - 44, 23, 8, "SEND"));
        this.ÇŽÉ.add(new GuiMenuButton(1888, 2, GuiIRCChat.Ê - 22, 60, 20, "Connect"));
        this.ÇŽÉ.add(new GuiMenuButton(1889, 72, GuiIRCChat.Ê - 22, 60, 20, "Disconnect"));
        (this.HorizonCode_Horizon_È = new GuiTextField(1, GuiIRCChat.Ñ¢á.µà, 38, GuiIRCChat.Ê - 46, GuiIRCChat.Çªà¢ - 35 - 35, 20)).HorizonCode_Horizon_È(false);
        this.HorizonCode_Horizon_È.Ó(50);
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        Gui_1808253012.HorizonCode_Horizon_È(30, 60, GuiIRCChat.Çªà¢ - 30, GuiIRCChat.Ê - 30, -14210000);
        Gui_1808253012.HorizonCode_Horizon_È(30, 30, GuiIRCChat.Çªà¢ - 30, 60, -13222335);
        Gui_1808253012.HorizonCode_Horizon_È(30, 30, 35, 60, -14210000);
        RenderHelper_1118140819.HorizonCode_Horizon_È(GuiIRCChat.Çªà¢ - 35, GuiIRCChat.Ê - 35, 35.0f, GuiIRCChat.Ê - 50, 1.0f, -13815242, -14670552);
        GL11.glPushMatrix();
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È("IRC", 20, 19, -1);
        GL11.glPopMatrix();
        int a = 46;
        for (int i = 0; i < GuiIRCChat.Â.size(); ++i) {
            final IRCMessage msg = GuiIRCChat.Â.get(i);
            a += 20;
            if (msg.Ø­áŒŠá()) {
                Gui_1808253012.HorizonCode_Horizon_È(GuiIRCChat.Çªà¢ / 2 - GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È(msg.Ý()) / 2, a, GuiIRCChat.Çªà¢ - 37, a + 18, msg.Â());
                GL11.glPushMatrix();
                GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È(msg.Ý(), GuiIRCChat.Çªà¢ / 2 - GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È(msg.Ý()) / 2 + 4, a + 5, -1);
                GL11.glPopMatrix();
            }
            else {
                Gui_1808253012.HorizonCode_Horizon_È(32, a, GuiIRCChat.Çªà¢ / 2 + GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È(msg.Ý()) / 2 + 8, a + 18, msg.Â());
                GL11.glPushMatrix();
                if (msg.Ý().contains("@" + Horizon.É)) {
                    final String formatted = msg.Ý().replaceAll("@" + Horizon.É, "§a§o@" + Horizon.É + "§f");
                    GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È("§8" + msg.HorizonCode_Horizon_È() + "§7> §f" + formatted, 34, a + 5, -1);
                }
                else {
                    GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È("§8" + msg.HorizonCode_Horizon_È() + "§7> §f" + msg.Ý(), 34, a + 5, -1);
                }
                GL11.glPopMatrix();
            }
        }
        if (a > GuiIRCChat.Ê - 80) {
            GuiIRCChat.Â.remove(0);
        }
        if (this.HorizonCode_Horizon_È.Ý().length() >= 50) {
            GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È("§c§o" + this.HorizonCode_Horizon_È.Ý().length() + "/50", GuiIRCChat.Çªà¢ - 60, GuiIRCChat.Ê - 60, -1);
        }
        else {
            GuiIRCChat.Ñ¢á.µà.HorizonCode_Horizon_È("§7§o" + this.HorizonCode_Horizon_È.Ý().length() + "/50", GuiIRCChat.Çªà¢ - 60, GuiIRCChat.Ê - 60, -1);
        }
        this.HorizonCode_Horizon_È.Â(true);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.Â();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 88: {
                if (this.HorizonCode_Horizon_È.Ý() == "") {
                    return;
                }
                Horizon.à¢.£ÂµÄ.HorizonCode_Horizon_È(("/m/" + Horizon.à¢.£ÂµÄ.Ø­áŒŠá() + "/o/" + this.HorizonCode_Horizon_È.Ý()).getBytes());
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("");
                break;
            }
            case 1888: {
                if (Horizon.à¢.£ÂµÄ == null) {
                    (Horizon.à¢.£ÂµÄ = new IRCClient(1337)).HorizonCode_Horizon_È();
                    break;
                }
                break;
            }
            case 1889: {
                if (Horizon.à¢.£ÂµÄ != null) {
                    Horizon.à¢.£ÂµÄ.Â();
                    Horizon.à¢.£ÂµÄ = null;
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(typedChar, keyCode);
        if (typedChar == '\r') {
            if (this.HorizonCode_Horizon_È.Ý() == "") {
                return;
            }
            Horizon.à¢.£ÂµÄ.HorizonCode_Horizon_È(("/m/" + Horizon.à¢.£ÂµÄ.Ø­áŒŠá() + "/o/" + this.HorizonCode_Horizon_È.Ý()).getBytes());
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("");
        }
        super.HorizonCode_Horizon_È(typedChar, keyCode);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
}
