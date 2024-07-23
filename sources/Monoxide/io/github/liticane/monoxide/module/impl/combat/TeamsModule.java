package io.github.liticane.monoxide.module.impl.combat;

import io.github.liticane.monoxide.component.ComponentManager;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import io.github.liticane.monoxide.component.impl.EntityComponent;
import io.github.liticane.monoxide.component.impl.entity.IgnoreList;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.util.player.PlayerUtil;

import java.awt.*;

@ModuleData(name = "Teams", description = "Friendly fire will not be tolerated", category = ModuleCategory.COMBAT)
public class TeamsModule extends Module implements IgnoreList {


    public BooleanValue vanilla = new BooleanValue("Vanilla", this, false);
    public BooleanValue armorColor = new BooleanValue("Armor Color", this, false);
    public BooleanValue tabColor = new BooleanValue("Tab Color", this, true);

    public TeamsModule() {
        ComponentManager.getInstance().getByClass(EntityComponent.class).addIgnoreList(this);
    }

    @Override
    public boolean shouldSkipEntity(Entity targetPlayer) {
        if(!this.isEnabled())
            return false;

        if (!(targetPlayer instanceof EntityPlayer))
            return false;

        boolean skip = false;

        if (armorColor.getValue()) {
            int targetArmorColor = PlayerUtil.getLeatherArmorColor((EntityPlayer) targetPlayer);
            int localPlayerArmorColor = PlayerUtil.getLeatherArmorColor(mc.thePlayer);
            if(targetArmorColor != -1 && localPlayerArmorColor != -1 && targetArmorColor == localPlayerArmorColor)
                skip = true;
        }
        if(tabColor.getValue()) {
            if(getTeamColor((EntityPlayer) targetPlayer).getRGB() == getTeamColor(mc.thePlayer).getRGB())
                skip = true;
        }
        if(vanilla.getValue()) {
            if(mc.thePlayer.getTeam() != null && ((EntityPlayer) targetPlayer).getTeam() != null && mc.thePlayer.getTeam().isSameTeam(((EntityPlayer) targetPlayer).getTeam())) {
                skip = true;
            }
        }

        return skip;
    }

    public Color getTeamColor(EntityPlayer player) {
        ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) player.getTeam();
        int i = 16777215;

        if (scoreplayerteam != null && scoreplayerteam.getColorPrefix() != null) {
            String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
            if (s.length() >= 2) {
                if (mc.getRenderManager().getFontRenderer() != null && mc.getRenderManager().getFontRenderer().getColorCode(s.charAt(1)) != 0)
                    i = mc.getRenderManager().getFontRenderer().getColorCode(s.charAt(1));
            }
        }
        final float r = (float) (i >> 16 & 255) / 255.0F;
        final float g = (float) (i >> 8 & 255) / 255.0F;
        final float b = (float) (i & 255) / 255.0F;

        return new Color(r, g, b);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

}