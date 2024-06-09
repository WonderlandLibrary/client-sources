package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;
import tv.twitch.chat.ChatUserSubscription;
import tv.twitch.chat.ChatUserMode;
import java.util.Collection;
import java.util.Set;
import com.google.common.collect.Lists;
import java.util.List;
import tv.twitch.chat.ChatUserInfo;

public class GuiTwitchUserMode extends GuiScreen
{
    private static final EnumChatFormatting HorizonCode_Horizon_È;
    private static final EnumChatFormatting Â;
    private static final EnumChatFormatting Ý;
    private final ChatUserInfo Ø­áŒŠá;
    private final IChatComponent Âµá€;
    private final List Ó;
    private final IStream à;
    private int Ø;
    private static final String áŒŠÆ = "CL_00001837";
    
    static {
        HorizonCode_Horizon_È = EnumChatFormatting.Ý;
        Â = EnumChatFormatting.ˆÏ­;
        Ý = EnumChatFormatting.Ó;
    }
    
    public GuiTwitchUserMode(final IStream p_i1064_1_, final ChatUserInfo p_i1064_2_) {
        this.Ó = Lists.newArrayList();
        this.à = p_i1064_1_;
        this.Ø­áŒŠá = p_i1064_2_;
        this.Âµá€ = new ChatComponentText(p_i1064_2_.displayName);
        this.Ó.addAll(HorizonCode_Horizon_È(p_i1064_2_.modes, p_i1064_2_.subscriptions, p_i1064_1_));
    }
    
    public static List HorizonCode_Horizon_È(final Set p_152328_0_, final Set p_152328_1_, final IStream p_152328_2_) {
        final String var3 = (p_152328_2_ == null) ? null : p_152328_2_.áŒŠà();
        final boolean var4 = p_152328_2_ != null && p_152328_2_.Ï­Ðƒà();
        final ArrayList var5 = Lists.newArrayList();
        for (final ChatUserMode var7 : p_152328_0_) {
            final IChatComponent var8 = HorizonCode_Horizon_È(var7, var3, var4);
            if (var8 != null) {
                final ChatComponentText var9 = new ChatComponentText("- ");
                var9.HorizonCode_Horizon_È(var8);
                var5.add(var9);
            }
        }
        for (final ChatUserSubscription var10 : p_152328_1_) {
            final IChatComponent var8 = HorizonCode_Horizon_È(var10, var3, var4);
            if (var8 != null) {
                final ChatComponentText var9 = new ChatComponentText("- ");
                var9.HorizonCode_Horizon_È(var8);
                var5.add(var9);
            }
        }
        return var5;
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final ChatUserSubscription p_152330_0_, final String p_152330_1_, final boolean p_152330_2_) {
        ChatComponentTranslation var3 = null;
        if (p_152330_0_ == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
            if (p_152330_1_ == null) {
                var3 = new ChatComponentTranslation("stream.user.subscription.subscriber", new Object[0]);
            }
            else if (p_152330_2_) {
                var3 = new ChatComponentTranslation("stream.user.subscription.subscriber.self", new Object[0]);
            }
            else {
                var3 = new ChatComponentTranslation("stream.user.subscription.subscriber.other", new Object[] { p_152330_1_ });
            }
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.HorizonCode_Horizon_È);
        }
        else if (p_152330_0_ == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
            var3 = new ChatComponentTranslation("stream.user.subscription.turbo", new Object[0]);
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.Ý);
        }
        return var3;
    }
    
    public static IChatComponent HorizonCode_Horizon_È(final ChatUserMode p_152329_0_, final String p_152329_1_, final boolean p_152329_2_) {
        ChatComponentTranslation var3 = null;
        if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
            var3 = new ChatComponentTranslation("stream.user.mode.administrator", new Object[0]);
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.Ý);
        }
        else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
            if (p_152329_1_ == null) {
                var3 = new ChatComponentTranslation("stream.user.mode.banned", new Object[0]);
            }
            else if (p_152329_2_) {
                var3 = new ChatComponentTranslation("stream.user.mode.banned.self", new Object[0]);
            }
            else {
                var3 = new ChatComponentTranslation("stream.user.mode.banned.other", new Object[] { p_152329_1_ });
            }
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.Â);
        }
        else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
            if (p_152329_1_ == null) {
                var3 = new ChatComponentTranslation("stream.user.mode.broadcaster", new Object[0]);
            }
            else if (p_152329_2_) {
                var3 = new ChatComponentTranslation("stream.user.mode.broadcaster.self", new Object[0]);
            }
            else {
                var3 = new ChatComponentTranslation("stream.user.mode.broadcaster.other", new Object[0]);
            }
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.HorizonCode_Horizon_È);
        }
        else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
            if (p_152329_1_ == null) {
                var3 = new ChatComponentTranslation("stream.user.mode.moderator", new Object[0]);
            }
            else if (p_152329_2_) {
                var3 = new ChatComponentTranslation("stream.user.mode.moderator.self", new Object[0]);
            }
            else {
                var3 = new ChatComponentTranslation("stream.user.mode.moderator.other", new Object[] { p_152329_1_ });
            }
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.HorizonCode_Horizon_È);
        }
        else if (p_152329_0_ == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
            var3 = new ChatComponentTranslation("stream.user.mode.staff", new Object[0]);
            var3.à().HorizonCode_Horizon_È(GuiTwitchUserMode.Ý);
        }
        return var3;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        final int var1 = GuiTwitchUserMode.Çªà¢ / 3;
        final int var2 = var1 - 130;
        this.ÇŽÉ.add(new GuiButton(1, var1 * 0 + var2 / 2, GuiTwitchUserMode.Ê - 70, 130, 20, I18n.HorizonCode_Horizon_È("stream.userinfo.timeout", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(0, var1 * 1 + var2 / 2, GuiTwitchUserMode.Ê - 70, 130, 20, I18n.HorizonCode_Horizon_È("stream.userinfo.ban", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(2, var1 * 2 + var2 / 2, GuiTwitchUserMode.Ê - 70, 130, 20, I18n.HorizonCode_Horizon_È("stream.userinfo.mod", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(5, var1 * 0 + var2 / 2, GuiTwitchUserMode.Ê - 45, 130, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(3, var1 * 1 + var2 / 2, GuiTwitchUserMode.Ê - 45, 130, 20, I18n.HorizonCode_Horizon_È("stream.userinfo.unban", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(4, var1 * 2 + var2 / 2, GuiTwitchUserMode.Ê - 45, 130, 20, I18n.HorizonCode_Horizon_È("stream.userinfo.unmod", new Object[0])));
        int var3 = 0;
        for (final IChatComponent var5 : this.Ó) {
            var3 = Math.max(var3, this.É.HorizonCode_Horizon_È(var5.áŒŠÆ()));
        }
        this.Ø = GuiTwitchUserMode.Çªà¢ / 2 - var3 / 2;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 0) {
                this.à.Ó("/ban " + this.Ø­áŒŠá.displayName);
            }
            else if (button.£à == 3) {
                this.à.Ó("/unban " + this.Ø­áŒŠá.displayName);
            }
            else if (button.£à == 2) {
                this.à.Ó("/mod " + this.Ø­áŒŠá.displayName);
            }
            else if (button.£à == 4) {
                this.à.Ó("/unmod " + this.Ø­áŒŠá.displayName);
            }
            else if (button.£à == 1) {
                this.à.Ó("/timeout " + this.Ø­áŒŠá.displayName);
            }
            GuiTwitchUserMode.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, this.Âµá€.Ø(), GuiTwitchUserMode.Çªà¢ / 2, 70, 16777215);
        int var4 = 80;
        for (final IChatComponent var6 : this.Ó) {
            Gui_1808253012.Â(this.É, var6.áŒŠÆ(), this.Ø, var4, 16777215);
            var4 += this.É.HorizonCode_Horizon_È;
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
