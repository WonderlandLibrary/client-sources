package xyz.cucumber.base.module.feat.other;

import java.util.Collection;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.events.ext.EventRenderScoreboard;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

@ModuleInfo(
   category = Category.OTHER,
   description = "",
   name = "Scoreboard",
   priority = ArrayPriority.LOW
)
public class ScoreboardModule extends Mod implements Dragable {
   private PositionUtils position;
   private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
   private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
   private BooleanSettings rounded = new BooleanSettings("Rounded", true);
   private BooleanSettings blur = new BooleanSettings("Blur", true);
   private BooleanSettings bloom = new BooleanSettings("Bloom", true);
   private ColorSettings bloomColor = new ColorSettings("Bloom Color", "Static", -16777216, -1, 100);
   private ColorSettings backgroundColor = new ColorSettings("Background Color", "Static", -16777216, -1, 50);
   private BooleanSettings outline = new BooleanSettings("Outline", true);
   private ColorSettings outlineColor = new ColorSettings("Outline Color", "Static", -16777216, -1, 50);

   public ScoreboardModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.positionX, this.positionY, this.rounded, this.blur, this.bloom, this.bloomColor, this.backgroundColor, this.outline, this.outlineColor
         }
      );
   }

   @EventListener
   public void onRenderScore(EventRenderScoreboard e) {
      e.setCancelled(true);
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (this.blur.isEnabled()) {
         new ScaledResolution(this.mc);
         Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
         ScoreObjective scoreobjective = null;
         ScoreObjective objective = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
         if (objective != null) {
            double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
            scoreboard = objective.getScoreboard();
            Collection<Score> collection = scoreboard.getSortedScores(objective);
            double i = 70.0;
            double width = i;

            for (Score score2 : collection) {
               ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
               String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam2, score2.getPlayerName());
               if (width < Fonts.getFont("rb-r").getWidth(s1)) {
                  width = Fonts.getFont("rb-r").getWidth(s1);
               }
            }

            int height = collection.size() * 9 + 16;
            if (this.blur.isEnabled()) {
               if (e.getType() == EventType.PRE) {
                  e.setCancelled(true);
                  return;
               }

               if (this.rounded.isEnabled()) {
                  RenderUtils.drawRoundedRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, -16777216, 2.0F);
               } else {
                  RenderUtils.drawRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, -16777216);
               }
            }
         }
      }
   }

   @EventListener
   public void onBloom(EventBloom e) {
      if (this.bloom.isEnabled()) {
         new ScaledResolution(this.mc);
         Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
         ScoreObjective scoreobjective = null;
         ScoreObjective objective = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
         if (objective != null) {
            double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
            scoreboard = objective.getScoreboard();
            Collection<Score> collection = scoreboard.getSortedScores(objective);
            double i = 70.0;
            double width = i;

            for (Score score2 : collection) {
               ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
               String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam2, score2.getPlayerName());
               if (width < Fonts.getFont("rb-r").getWidth(s1)) {
                  width = Fonts.getFont("rb-r").getWidth(s1);
               }
            }

            int height = collection.size() * 9 + 16;
            if (this.bloom.isEnabled()) {
               if (e.getType() == EventType.PRE) {
                  e.setCancelled(true);
                  return;
               }

               int color = ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
               if (this.rounded.isEnabled()) {
                  RenderUtils.drawRoundedRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color, 2.0F);
               } else {
                  RenderUtils.drawRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color);
               }
            }
         }
      }
   }

   @EventListener
   public void onRenderGui(EventRenderGui e) {
      new ScaledResolution(this.mc);
      Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
      ScoreObjective scoreobjective = null;
      ScoreObjective objective = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
      if (objective != null) {
         double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
         scoreboard = objective.getScoreboard();
         Collection<Score> collection = scoreboard.getSortedScores(objective);
         double i = 70.0;
         double width = i;

         for (Score score2 : collection) {
            ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam2, score2.getPlayerName());
            if (width < Fonts.getFont("rb-r").getWidth(s1)) {
               width = Fonts.getFont("rb-r").getWidth(s1);
            }
         }

         int height = collection.size() * 9 + 16;
         int color = ColorUtils.getAlphaColor(
            ColorUtils.getColor(this.backgroundColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0), this.backgroundColor.getAlpha()
         );
         if (this.rounded.isEnabled()) {
            RenderUtils.drawRoundedRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color, 2.0F);
         } else {
            RenderUtils.drawRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color);
         }

         if (this.outline.isEnabled()) {
            color = ColorUtils.getAlphaColor(
               ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0), this.outlineColor.getAlpha()
            );
            if (this.rounded.isEnabled()) {
               RenderUtils.drawOutlinedRoundedRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color, 2.0, 0.1);
            } else {
               RenderUtils.drawOutlinedRect(pos[0], pos[1], pos[0] + width, pos[1] + (double)height, color, 1.0F);
            }
         }

         Fonts.getFont("rb-r")
            .drawString(objective.getDisplayName(), pos[0] + width / 2.0 - Fonts.getFont("rb-r").getWidth(objective.getDisplayName()) / 2.0, pos[1] + 4.0, -1);
         int j = 0;

         for (Score score1 : collection) {
            j++;
            ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam1, score1.getPlayerName());
            Fonts.getFont("rb-r").drawString(s1, pos[0] + 3.0, pos[1] + (double)height - (double)(9 * j), -1);
         }
      }
   }

   @Override
   public PositionUtils getPosition() {
      Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
      ScoreObjective scoreobjective = null;
      ScoreObjective objective = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
      if (objective == null) {
         return new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F);
      } else {
         scoreboard = objective.getScoreboard();
         Collection<Score> collection = scoreboard.getSortedScores(objective);
         double i = 70.0;
         double width = i;

         for (Score score2 : collection) {
            ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
            String s1 = ScorePlayerTeam.formatPlayerName(scoreplayerteam2, score2.getPlayerName());
            if (width < Fonts.getFont("rb-r").getWidth(s1)) {
               width = Fonts.getFont("rb-r").getWidth(s1);
            }
         }

         double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
         int height = collection.size() * 9 + 16;
         return new PositionUtils(pos[0], pos[1], width, (double)height, 1.0F);
      }
   }

   @Override
   public void setXYPosition(double x, double y) {
      this.positionX.setValue(x);
      this.positionY.setValue(y);
   }
}
