package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.modules.combat.AutoFeetPlace;
import me.finz0.osiris.module.modules.combat.Surround;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.friends.Friends;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import static org.lwjgl.opengl.GL11.*;

public class CsgoESP extends Module {
    public CsgoESP() {
        super("CsgoESP", Category.RENDER);
    }

    Setting players;
    Setting passive;
    Setting monsters;
    Setting items;
    Setting xpBottles;
    Setting crystals;
    Setting orbs;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(players = new Setting("Players", this, true, "CsGoEsPlayers"));
        AuroraMod.getInstance().settingsManager.rSetting(passive = new Setting("Passive", this, false, "CsGoEspPassive"));
        AuroraMod.getInstance().settingsManager.rSetting(monsters = new Setting("Monsters", this, false, "CsGoEspMonsters"));
        AuroraMod.getInstance().settingsManager.rSetting(items = new Setting("Items", this, false, "CsGoEspItems"));
        AuroraMod.getInstance().settingsManager.rSetting(crystals = new Setting("Crystals", this, false, "CsGoEspCrystals"));
        AuroraMod.getInstance().settingsManager.rSetting(xpBottles = new Setting("XpBottles", this, false, "CsGoEspXpBottles"));
        rSetting(orbs = new Setting("XpOrbs", this, false, "CsGoEspXpOrbs"));
    }

    public void onWorldRender(RenderEvent event) {
        if (mc.getRenderManager().options == null) return;
                boolean isThirdPersonFrontal = mc.getRenderManager().options.thirdPersonView == 2;
                float viewerYaw = mc.getRenderManager().playerViewY;

                mc.world.loadedEntityList.stream()
                        .filter(entity -> mc.player != entity)
                        .forEach(e -> {
                            OsirisTessellator.prepareGL();
                            GlStateManager.pushMatrix();
                            Vec3d pos = AutoFeetPlace.getInterpolatedPos(e, mc.getRenderPartialTicks());
                            GlStateManager.translate(pos.x-mc.getRenderManager().renderPosX, pos.y-mc.getRenderManager().renderPosY, pos.z-mc.getRenderManager().renderPosZ);
                            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
                            GlStateManager.rotate((float)(isThirdPersonFrontal ? -1 : 1), 1.0F, 0.0F, 0.0F);
                            glColor4f(1, 1, 1, 0.5f);

                            glLineWidth(3f);
                            glEnable(GL_LINE_SMOOTH);

                            if(e instanceof EntityPlayer && players.getValBoolean()) {
                                if (Friends.isFriend(e.getName())) {
                                    glColor4f(0, 1, 1, 0.5f);
                                } else {
                                    glColor4f(1f, 0f, 0f, 0.5f);
                                }
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            glColor4f(1, 1, 1, 0.5f);

                            if(GlowESP.isPassive(e) && passive.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(GlowESP.isMonster(e) && monsters.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(e instanceof EntityItem && items.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }

                            if(e instanceof EntityExpBottle && xpBottles.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, 0);
                                    glVertex2d(-e.width, e.height / 3);
                                    glVertex2d(-e.width, 0);
                                    glVertex2d((-e.width / 3) * 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d((-e.width / 3) * 2, e.height);
                                    glVertex2d(-e.width, e.height);
                                    glVertex2d(-e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, e.height);
                                    glVertex2d((e.width / 3) * 2, e.height);
                                    glVertex2d(e.width, e.height);
                                    glVertex2d(e.width, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width, 0);
                                    glVertex2d((e.width / 3) * 2, 0);
                                    glVertex2d(e.width, 0);
                                    glVertex2d(e.width, e.height / 3);
                                }
                                glEnd();
                            }


                            if(e instanceof EntityEnderCrystal && crystals.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(-e.width / 2, e.height / 3);
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(((-e.width / 3) * 2) / 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(((-e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(-e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(((e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(((e.width / 3) * 2) / 2, 0);
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(e.width / 2, e.height / 3);
                                }
                                glEnd();
                            }

                            if(e instanceof EntityXPOrb && orbs.getValBoolean()) {
                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(-e.width / 2, e.height / 3);
                                    glVertex2d(-e.width / 2, 0);
                                    glVertex2d(((-e.width / 3) * 2) / 2, 0);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(((-e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(-e.width / 2, e.height);
                                    glVertex2d(-e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(((e.width / 3) * 2) / 2, e.height);
                                    glVertex2d(e.width / 2, e.height);
                                    glVertex2d(e.width / 2, (e.height / 3) * 2);
                                }
                                glEnd();

                                glBegin(GL_LINE_LOOP);
                                {
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(((e.width / 3) * 2) / 2, 0);
                                    glVertex2d(e.width / 2, 0);
                                    glVertex2d(e.width / 2, e.height / 3);
                                }
                                glEnd();
                            }

                            OsirisTessellator.releaseGL();
                            GlStateManager.popMatrix();
                        });
                glColor4f(1,1,1, 1);
    }
}
