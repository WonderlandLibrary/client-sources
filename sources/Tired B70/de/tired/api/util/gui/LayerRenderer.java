package de.tired.api.util.gui;

import de.tired.api.extension.Extension;
import de.tired.api.util.render.Translate;
import de.tired.interfaces.IHook;
import de.tired.module.impl.list.visual.*;
import de.tired.notification.newnotifications.NotifyManager;
import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.shaderloader.list.OutlineShader;
import de.tired.tired.Tired;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;


public class LayerRenderer extends ShaderRenderLayer implements IHook {
    public float float2;
    private float animationX;
    private final Translate translate;


    public LayerRenderer() {
        this.translate = new Translate(0, 0);
    }

    @Override
    public void renderLayerWBlur() {


        if (Tired.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
            NotifyManager.drawNotifications(false, true);
        }

        if (!Shader.getInstance().scoreBoardBlur.getValue() && !Shader.getInstance().scoreBoardBlur.getValue()) {
            renderScoreBoard(!Shader.getInstance().isState());
        }

        if (!Shader.getInstance().chatBlur.getValue()) {
            MC.ingameGUI.renderChatFinal(true);
        }

        ShaderManager.shaderBy(OutlineShader.class).startESP();
        if (Tired.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
            if (Shader.getInstance().hotbarBlur.getValue()) {
                Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), true);
            }
        }

        ShaderManager.shaderBy(OutlineShader.class).stopESP();

        if (Shader.getInstance().isState()) {
            ShaderRenderer.startBlur();

            if (Tired.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
                if (Shader.getInstance().hotbarBlur.getValue()) {
                    Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), true);
                }
            }

            if (Shader.getInstance().chatBlur.getValue()) {
                MC.ingameGUI.renderChatFinal(true);
            }

            targetHUD2();


            if (Shader.getInstance().scoreBoardBlur.getValue()) {
                renderScoreBoard(true);
            }

            if (Tired.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
                NotifyManager.drawNotifications(true, false);
            }


            if (ArrowESP.getInstance().state) {
                ArrowESP.getInstance().renderArrow2();
            }
            targetHUD(true);

            if (Shader.getInstance().arraylistBlur.getValue()) {

            }
            ShaderRenderer.stopBlur(12);
        }
    }

    public void renderScoreBoard(boolean rect) {
        Scoreboard scoreboard = MC.theWorld.getScoreboard();
        ScoreObjective scoreobjective = null;
        ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(MC.thePlayer.getName());

        if (scoreplayerteam != null) {
            int i1 = scoreplayerteam.getChatFormat().getColorIndex();

            if (i1 >= 0) {
                scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + i1);
            }
        }

        final ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective : scoreboard.getObjectiveInDisplaySlot(1);
        if (scoreobjective1 != null) {
            final ScaledResolution scaledresolution = new ScaledResolution(MC);
            MC.ingameGUI.renderScoreboard(scoreobjective1, scaledresolution, rect);
        }

    }

    private void targetHUD2() {
     /*   final ScaledResolution sr = new ScaledResolution(MC);

        if (KillAura.getInstance().isState() && KillAura.getInstance().getCurrentEntity() != null && KillAura.getInstance().getCurrentEntity() instanceof EntityPlayer) {
            if (float2 < 0.5F) {
                translate.interpolate((sr.getScaledWidth() / 2f) , sr.getScaledHeight() / 2, 12);

            }
        } else {

            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        if ((KillAura.getInstance().isState() && KillAura.getInstance().tickEntity == null)) {
            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        GL11.glPushMatrix();
        GL11.glTranslatef(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0);
        GL11.glScaled(0.5 + translate.getX() / sr.getScaledWidth() - float2, 0.5 + translate.getY() / sr.getScaledHeight() - float2, 0);
        GL11.glTranslatef(-sr.getScaledWidth() / 2f, -sr.getScaledHeight() / 2f, 0);

        if (KillAura.getInstance().tickEntity != null) {
            if (translate.getX() != 0.0) {
                KillAura.getInstance().renderOnly();
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
*/
    }


    private void targetHUD(boolean rect) {
    /*    final ScaledResolution sr = new ScaledResolution(MC);

        if (KillAura.getInstance().isState() && KillAura.getInstance().getCurrentEntity() != null && KillAura.getInstance().getCurrentEntity() instanceof EntityPlayer) {
            if (float2 < 0.5F) {
                translate.interpolate((sr.getScaledWidth() / 2f) , sr.getScaledHeight() / 2, 12);

            }
        } else {

            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        if ((KillAura.getInstance().isState() && KillAura.getInstance().tickEntity == null)) {
            translate.interpolate(0, 0, 5);
            float2 = 0;
        }


        GL11.glPushMatrix();
        GL11.glTranslatef(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, 0);
        GL11.glScaled(0.5 + translate.getX() / sr.getScaledWidth() - float2, 0.5 + translate.getY() / sr.getScaledHeight() - float2, 0);
        GL11.glTranslatef(-sr.getScaledWidth() / 2f, -sr.getScaledHeight() / 2f, 0);

        if (KillAura.getInstance().tickEntity != null) {
            if (translate.getX() != 0.0) {
                KillAura.getInstance().renderTargetHUD(rect);
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
*/
    }

    @Override
    public void renderNormalLayer() {

        if (Tired.INSTANCE.moduleManager.moduleBy(Notifications.class).isState()) {
            NotifyManager.drawNotifications(false, true);
        }


        targetHUD(false);
        MC.ingameGUI.renderChatFinal(false);


        if (Tired.INSTANCE.moduleManager.findModuleByClass(HotBar.class).isState()) {
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.renderHotbar(HotBar.getInstance().smooth.getValue(), !Shader.getInstance().hotbarBlur.getValue() || !Shader.getInstance().isState());
        }

        if (NameTags.getInstance().isState()) {
            NameTags.getInstance().doRenderFinal(true);
        }

        if (Shader.getInstance().scoreBoardBlur.getValue()) {
            renderScoreBoard(false);
        }
    }


}
