package HORIZON-6-0-SKIDPROTECTION;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import java.util.Iterator;
import org.lwjgl.input.Mouse;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class GuiChat extends GuiScreen
{
    private static final Logger Â;
    private String Ý;
    private int Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private int à;
    private List Ø;
    protected GuiTextField HorizonCode_Horizon_È;
    private String áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000682";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public GuiChat() {
        this.Ý = "";
        this.Ø­áŒŠá = -1;
        this.Ø = Lists.newArrayList();
        this.áŒŠÆ = "";
    }
    
    public GuiChat(final String p_i1024_1_) {
        this.Ý = "";
        this.Ø­áŒŠá = -1;
        this.Ø = Lists.newArrayList();
        this.áŒŠÆ = "";
        this.áŒŠÆ = p_i1024_1_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.Ø­áŒŠá = GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Ø­áŒŠá().size();
        (this.HorizonCode_Horizon_È = new GuiTextField(0, this.É, 4, GuiChat.Ê - 12, GuiChat.Çªà¢ - 4, 12)).Ó(100);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false);
        this.HorizonCode_Horizon_È.Â(true);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.áŒŠÆ);
        this.HorizonCode_Horizon_È.Ø­áŒŠá(false);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
        GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Âµá€();
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.Â();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.Ó = false;
        if (keyCode == 15) {
            this.Ó();
        }
        else {
            this.Âµá€ = false;
        }
        if (keyCode == 1) {
            GuiChat.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
        else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.HorizonCode_Horizon_È(-1);
            }
            else if (keyCode == 208) {
                this.HorizonCode_Horizon_È(1);
            }
            else if (keyCode == 201) {
                GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Â(GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().áˆºÑ¢Õ() - 1);
            }
            else if (keyCode == 209) {
                GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Â(-GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().áˆºÑ¢Õ() + 1);
            }
            else {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(typedChar, keyCode);
            }
        }
        else {
            final String var3 = this.HorizonCode_Horizon_È.Ý().trim();
            if (var3.length() > 0) {
                this.Âµá€(var3);
            }
            GuiChat.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    @Override
    public void n_() throws IOException {
        super.n_();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0) {
            if (var1 > 1) {
                var1 = 1;
            }
            if (var1 < -1) {
                var1 = -1;
            }
            if (!GuiScreen.£à()) {
                var1 *= 7;
            }
            GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Â(var1);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            final IChatComponent var4 = GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(Mouse.getX(), Mouse.getY());
            if (this.HorizonCode_Horizon_È(var4)) {
                return;
            }
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final String p_175274_1_, final boolean p_175274_2_) {
        if (p_175274_2_) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175274_1_);
        }
        else {
            this.HorizonCode_Horizon_È.Â(p_175274_1_);
        }
    }
    
    public void Ó() {
        if (this.Âµá€) {
            this.HorizonCode_Horizon_È.Â(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1, this.HorizonCode_Horizon_È.áŒŠÆ(), false) - this.HorizonCode_Horizon_È.áŒŠÆ());
            if (this.à >= this.Ø.size()) {
                this.à = 0;
            }
        }
        else {
            final int var1 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1, this.HorizonCode_Horizon_È.áŒŠÆ(), false);
            this.Ø.clear();
            this.à = 0;
            final String var2 = this.HorizonCode_Horizon_È.Ý().substring(var1).toLowerCase();
            final String var3 = this.HorizonCode_Horizon_È.Ý().substring(0, this.HorizonCode_Horizon_È.áŒŠÆ());
            this.HorizonCode_Horizon_È(var3, var2);
            if (this.Ø.isEmpty()) {
                return;
            }
            this.Âµá€ = true;
            this.HorizonCode_Horizon_È.Â(var1 - this.HorizonCode_Horizon_È.áŒŠÆ());
        }
        if (this.Ø.size() > 1) {
            final StringBuilder var4 = new StringBuilder();
            for (final String var3 : this.Ø) {
                if (var4.length() > 0) {
                    var4.append(", ");
                }
                var4.append(var3);
            }
            GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(new ChatComponentText(var4.toString()), 1);
        }
        this.HorizonCode_Horizon_È.Â(this.Ø.get(this.à++));
    }
    
    private void HorizonCode_Horizon_È(final String p_146405_1_, final String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            BlockPos var3 = null;
            if (GuiChat.Ñ¢á.áŒŠà != null && GuiChat.Ñ¢á.áŒŠà.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
                var3 = GuiChat.Ñ¢á.áŒŠà.HorizonCode_Horizon_È();
            }
            GuiChat.Ñ¢á.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C14PacketTabComplete(p_146405_1_, var3));
            this.Ó = true;
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_146402_1_) {
        int var2 = this.Ø­áŒŠá + p_146402_1_;
        final int var3 = GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Ø­áŒŠá().size();
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0, var3);
        if (var2 != this.Ø­áŒŠá) {
            if (var2 == var3) {
                this.Ø­áŒŠá = var3;
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ý);
            }
            else {
                if (this.Ø­áŒŠá == var3) {
                    this.Ý = this.HorizonCode_Horizon_È.Ý();
                }
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().Ø­áŒŠá().get(var2));
                this.Ø­áŒŠá = var2;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        Gui_1808253012.HorizonCode_Horizon_È(2, GuiChat.Ê - 14, GuiChat.Çªà¢ - 2, GuiChat.Ê - 2, Integer.MIN_VALUE);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È();
        final IChatComponent var4 = GuiChat.Ñ¢á.Šáƒ.Ø­áŒŠá().HorizonCode_Horizon_È(Mouse.getX(), Mouse.getY());
        if (var4 != null && var4.à().áŒŠÆ() != null) {
            this.HorizonCode_Horizon_È(var4, mouseX, mouseY);
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    public void HorizonCode_Horizon_È(final String[] p_146406_1_) {
        if (this.Ó) {
            this.Âµá€ = false;
            this.Ø.clear();
            for (final String var5 : p_146406_1_) {
                if (var5.length() > 0) {
                    this.Ø.add(var5);
                }
            }
            final String var6 = this.HorizonCode_Horizon_È.Ý().substring(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1, this.HorizonCode_Horizon_È.áŒŠÆ(), false));
            final String var7 = StringUtils.getCommonPrefix(p_146406_1_);
            if (var7.length() > 0 && !var6.equalsIgnoreCase(var7)) {
                this.HorizonCode_Horizon_È.Â(this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(-1, this.HorizonCode_Horizon_È.áŒŠÆ(), false) - this.HorizonCode_Horizon_È.áŒŠÆ());
                this.HorizonCode_Horizon_È.Â(var7);
            }
            else if (this.Ø.size() > 0) {
                this.Âµá€ = true;
                this.Ó();
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
}
