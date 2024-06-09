package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class ChatComponentProcessor
{
    private static final String HorizonCode_Horizon_È = "CL_00002310";
    
    public static IChatComponent HorizonCode_Horizon_È(final ICommandSender p_179985_0_, final IChatComponent p_179985_1_, final Entity p_179985_2_) throws CommandException {
        Object var3 = null;
        if (p_179985_1_ instanceof ChatComponentScore) {
            final ChatComponentScore var4 = (ChatComponentScore)p_179985_1_;
            String var5 = var4.HorizonCode_Horizon_È();
            if (PlayerSelector.Â(var5)) {
                final List var6 = PlayerSelector.Â(p_179985_0_, var5, Entity.class);
                if (var6.size() != 1) {
                    throw new EntityNotFoundException();
                }
                var5 = var6.get(0).v_();
            }
            var3 = ((p_179985_2_ != null && var5.equals("*")) ? new ChatComponentScore(p_179985_2_.v_(), var4.Â()) : new ChatComponentScore(var5, var4.Â()));
            ((ChatComponentScore)var3).HorizonCode_Horizon_È(var4.Ý());
        }
        else if (p_179985_1_ instanceof ChatComponentSelector) {
            final String var7 = ((ChatComponentSelector)p_179985_1_).HorizonCode_Horizon_È();
            var3 = PlayerSelector.Â(p_179985_0_, var7);
            if (var3 == null) {
                var3 = new ChatComponentText("");
            }
        }
        else if (p_179985_1_ instanceof ChatComponentText) {
            var3 = new ChatComponentText(((ChatComponentText)p_179985_1_).HorizonCode_Horizon_È());
        }
        else {
            if (!(p_179985_1_ instanceof ChatComponentTranslation)) {
                return p_179985_1_;
            }
            final Object[] var8 = ((ChatComponentTranslation)p_179985_1_).áˆºÑ¢Õ();
            for (int var9 = 0; var9 < var8.length; ++var9) {
                final Object var10 = var8[var9];
                if (var10 instanceof IChatComponent) {
                    var8[var9] = HorizonCode_Horizon_È(p_179985_0_, (IChatComponent)var10, p_179985_2_);
                }
            }
            var3 = new ChatComponentTranslation(((ChatComponentTranslation)p_179985_1_).Ø­áŒŠá(), var8);
        }
        final ChatStyle var11 = p_179985_1_.à();
        if (var11 != null) {
            ((IChatComponent)var3).HorizonCode_Horizon_È(var11.á());
        }
        for (final IChatComponent var13 : p_179985_1_.Ó()) {
            ((IChatComponent)var3).HorizonCode_Horizon_È(HorizonCode_Horizon_È(p_179985_0_, var13, p_179985_2_));
        }
        return (IChatComponent)var3;
    }
}
