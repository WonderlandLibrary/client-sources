package cn.Hanabi.injection.mixins;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import net.minecraft.client.renderer.*;
import cn.Hanabi.modules.*;
import org.spongepowered.asm.mixin.injection.*;
import cn.Hanabi.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import ClassSub.*;
import net.minecraft.scoreboard.*;
import java.util.*;

@SideOnly(Side.CLIENT)
@Mixin({ GuiIngame.class })
public class MixinGuiIngame
{
    @Inject(method = { "renderTooltip" }, at = { @At("HEAD") }, cancellable = true)
    private void renderTooltip(final ScaledResolution sr, final float partialTicks, final CallbackInfo ci) {
        EventManager.call(new EventRender2D(partialTicks));
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        if (ModManager.getModule("HUD").isEnabled() && Class118.hotbar.getValueState()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderScoreboard" }, at = { @At("HEAD") }, cancellable = true)
    public void customBoard(final ScoreObjective so, final ScaledResolution sr, final CallbackInfo info) {
        if (Hanabi.INSTANCE.sbm == null) {
            final Scoreboard scoreboard = so.getScoreboard();
            Collection<Score> collection = (Collection<Score>)scoreboard.getSortedScores(so);
            final List<Score> list = (List<Score>)Lists.newArrayList(Iterables.filter((Iterable)collection, (Predicate)new Predicate<Score>(this) {
                final MixinGuiIngame this$0;
                
                public boolean apply(final Score a) {
                    return a.getPlayerName() != null && !a.getPlayerName().startsWith("#");
                }
                
                public boolean apply(final Object o) {
                    return this.apply((Score)o);
                }
            }));
            if (list.size() > 15) {
                collection = (Collection<Score>)Lists.newArrayList(Iterables.skip((Iterable)list, collection.size() - 15));
            }
            else {
                collection = list;
            }
            int maxLength = Minecraft.getMinecraft().fontRendererObj.getStringWidth(so.getDisplayName());
            for (final Score score : collection) {
                final ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
                final String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
                maxLength = Math.max(maxLength, Minecraft.getMinecraft().fontRendererObj.getStringWidth(s));
            }
            final int i1 = collection.size() * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
            final int startY = sr.getScaledHeight() / 2 + i1 / 3;
            Hanabi.INSTANCE.sbm = new Class287(sr.getScaledWidth() - maxLength - 4, startY);
        }
        if (Hanabi.INSTANCE.sbm != null) {
            Hanabi.INSTANCE.sbm.passValue();
        }
        info.cancel();
    }
}
