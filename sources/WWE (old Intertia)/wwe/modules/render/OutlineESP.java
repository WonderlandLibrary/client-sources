package wwe.modules.render;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.passive.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import wwe.*;

public class OutlineESP extends qMIe
{
    public static boolean feQE;
    public static boolean krOE;
    public ModeValue mode;
    public BooleanValue players;
    public BooleanValue animals;
    public BooleanValue monsters;
    public BooleanValue crystals;
    public BooleanValue everyThing;
    public BooleanValue color;
    
    public OutlineESP() {
        super("Outline ESP", "Makes a kwel outline on entities", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Shader");
        list.add("Outline");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Outline", list);
        this.players = new BooleanValue("Players", this, "ESP around players", true);
        this.animals = new BooleanValue("Animals", this, "ESP around animals", false);
        this.monsters = new BooleanValue("Monsters", this, "ESP around monsters", false);
        this.crystals = new BooleanValue("Crystals", this, "ESP around crystals", false);
        this.everyThing = new BooleanValue("Every Thing", this, "ESP around all entities", false);
        this.color = new BooleanValue("Color", this, "Change color of esp", true);
    }
    
    public static void uuKQ(final float n) {
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity == Wrapper.mc.getRenderViewEntity()) {
                if (OutlineESP.\u2008\u200f\u2004) {
                    throw null;
                }
                continue;
            }
            else {
                if (entity instanceof EntityLivingBase || entity instanceof EntityEnderCrystal) {
                    RenderHelper.enableStandardItemLighting();
                    final int combinedLight = Wrapper.mc.world.getCombinedLight(entity.getPosition(), 0);
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(combinedLight % 65536), (float)(combinedLight / 65536));
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    final Render entityRenderObject = Wrapper.mc.getRenderManager().getEntityRenderObject(entity);
                    final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
                    final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
                    final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
                    OutlineESP.feQE = true;
                    OutlineESP.krOE = true;
                    entityRenderObject.doRender(entity, n2 - yYGD.onRX(), n3 - yYGD.UuJK(), n4 - yYGD.LkVR(), entity.rotationYaw, n);
                    OutlineESP.krOE = false;
                    OutlineESP.feQE = false;
                }
                if (OutlineESP.\u2008\u200f\u2004) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    public static void mmPS(final float n) {
        for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity == Wrapper.mc.getRenderViewEntity()) {
                if (OutlineESP.\u2008\u200f\u2004) {
                    throw null;
                }
                continue;
            }
            else {
                if (entity instanceof EntityLivingBase || entity instanceof EntityEnderCrystal) {
                    RenderHelper.enableStandardItemLighting();
                    final int combinedLight = Wrapper.mc.world.getCombinedLight(entity.getPosition(), 0);
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)(combinedLight % 65536), (float)(combinedLight / 65536));
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    final Render entityRenderObject = Wrapper.mc.getRenderManager().getEntityRenderObject(entity);
                    final double n2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * n;
                    final double n3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * n;
                    final double n4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * n;
                    if (entity instanceof EntityAnimal || entity instanceof EntityVillager) {
                        GL11.glColor3f(0.0f, 230.0f, 255.0f);
                        if (OutlineESP.\u2008\u200f\u2004) {
                            throw null;
                        }
                    }
                    else if (entity instanceof EntityMob || entity instanceof EntityWither) {
                        Tracers.oisD(new Color(-14124001));
                        if (OutlineESP.\u2008\u200f\u2004) {
                            throw null;
                        }
                    }
                    else if (entity instanceof EntityPlayer) {
                        if (vlwv.Zqnd().UsVJ(entity.getName())) {
                            Tracers.oisD(JIfG.JzFV(0x3A9F7B9E704B419DL ^ 0x3A9F7B9E704B419CL, 1.0f));
                            if (OutlineESP.\u2008\u200f\u2004) {
                                throw null;
                            }
                        }
                        else {
                            GL11.glColor3f(1.0f, 0.0f, 0.0f);
                            if (OutlineESP.\u2008\u200f\u2004) {
                                throw null;
                            }
                        }
                    }
                    else if (entity instanceof EntityEnderCrystal) {
                        Tracers.oisD(new Color(-65340));
                        if (OutlineESP.\u2008\u200f\u2004) {
                            throw null;
                        }
                    }
                    else {
                        GL11.glColor3f(1.0f, 1.0f, 0.0f);
                    }
                    OutlineESP.feQE = true;
                    OutlineESP.krOE = true;
                    entityRenderObject.doRender(entity, n2 - yYGD.onRX(), n3 - yYGD.UuJK(), n4 - yYGD.LkVR(), entity.rotationYaw, n);
                    OutlineESP.krOE = false;
                    OutlineESP.feQE = false;
                }
                if (OutlineESP.\u2008\u200f\u2004) {
                    throw null;
                }
                continue;
            }
        }
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Shader")) {
            this.owgW("Outline ESP " + TextFormatting.DARK_GRAY + "Shader");
            for (final EntityPlayer next : Wrapper.mc.world.loadedEntityList) {
                if (this.players.OnGi() && next instanceof EntityPlayer) {
                    next.setGlowing(true);
                }
                if (this.crystals.OnGi() && next instanceof EntityEnderCrystal) {
                    ((EntityEnderCrystal)next).setGlowing(true);
                }
                if (this.animals.OnGi()) {
                    if (next instanceof EntityAnimal) {
                        ((EntityAnimal)next).setGlowing(true);
                    }
                    if (next instanceof EntityVillager) {
                        ((EntityVillager)next).setGlowing(true);
                    }
                }
                if (this.monsters.OnGi()) {
                    if (next instanceof EntityMob) {
                        ((EntityMob)next).setGlowing(true);
                    }
                    if (next instanceof EntityWither) {
                        ((EntityWither)next).setGlowing(true);
                    }
                }
                if (this.everyThing.OnGi() && next instanceof Entity) {
                    ((Entity)next).setGlowing(true);
                }
                if (OutlineESP.\u2008\u200f\u2004) {
                    throw null;
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Outline")) {
            this.owgW("Outline ESP " + TextFormatting.DARK_GRAY + "Outline");
        }
    }
    
    @Override
    public void qHNm() {
        if (!this.IuiN()) {
            return;
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        for (final Entity next : Wrapper.mc.world.loadedEntityList) {
            if (next instanceof Entity) {
                next.setGlowing(false);
            }
            if (OutlineESP.\u2008\u200f\u2004) {
                throw null;
            }
        }
        super.qBmy();
    }
    
    @oqrr
    public void KgSM(final hnaB hnaB) {
        if (this.mode.OnGi().equalsIgnoreCase("Outline") && OutlineESP.feQE) {
            hnaB.Ggez(true);
        }
    }
    
    @oqrr
    public void pVgV(final dfEB dfEB) {
        if (this.mode.OnGi().equalsIgnoreCase("Outline") && OutlineESP.feQE) {
            dfEB.Ggez(true);
        }
    }
    
    @oqrr
    public void oHvZ(final ZONL zonl) {
        if (this.mode.OnGi().equalsIgnoreCase("Shader")) {
            if (zonl.flPf instanceof EntityEnderCrystal) {
                zonl.YDEv = -65340;
                zonl.Ggez(true);
            }
            if (zonl.flPf instanceof EntityPlayer) {
                zonl.YDEv = -65536;
                zonl.Ggez(true);
            }
            if (zonl.flPf instanceof EntityAnimal || zonl.flPf instanceof EntityVillager) {
                zonl.YDEv = -16718081;
                zonl.Ggez(true);
            }
            if (zonl.flPf instanceof EntityMob || zonl.flPf instanceof EntityWither) {
                zonl.YDEv = -14124001;
                zonl.Ggez(true);
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Outline") && OutlineESP.feQE) {
            zonl.YDEv = -65536;
            zonl.Ggez(true);
        }
    }
    
    static {
        OutlineESP.krOE = true;
    }
}
