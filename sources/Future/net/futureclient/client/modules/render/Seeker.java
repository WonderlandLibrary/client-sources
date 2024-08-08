package net.futureclient.client.modules.render;

import net.futureclient.client.ZG;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.math.Vec3d;
import net.futureclient.client.gD;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.futureclient.client.Ec;
import net.futureclient.client.pg;
import net.futureclient.client.xG;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.seeker.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Seeker extends Ea
{
    private NumberValue width;
    private Value<Boolean> fill;
    private Value<Boolean> tracers;
    private Value<Boolean> boundingBox;
    
    public Seeker() {
        super("Seeker", new String[] { "Seeker", "seek", "hide" }, true, -7285564, Category.RENDER);
        this.boundingBox = new Value<Boolean>(true, new String[] { "BoundingBox", "Bound" });
        this.tracers = new Value<Boolean>(false, new String[] { "Tracers", "Tracer" });
        this.fill = new Value<Boolean>(false, new String[] { "Fill", "filling", "fillings" });
        this.width = new NumberValue(0.6f, 0.1f, 10.0f, 1.273197475E-314, new String[] { "Width", "With", "Radius", "raidus" });
        this.M(new Value[] { this.boundingBox, this.fill, this.tracers, this.width });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Seeker.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Seeker.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Seeker.D;
    }
    
    public static Value b(final Seeker seeker) {
        return seeker.tracers;
    }
    
    public static void e(final Seeker seeker, final Entity entity, final String s, final double n, final double n2, final double n3, final float n4) {
        seeker.e(entity, s, n, n2, n3, n4);
    }
    
    public static Minecraft getMinecraft3() {
        return Seeker.D;
    }
    
    private void e(final Entity entity, final String s, final double n, double distance, final double n2, final float n3) {
        final double n4 = distance + (entity.isSneaking() ? 0.0 : 8.48798316E-315);
        final Object o2;
        final Object o = o2 = ((Seeker.D.getRenderViewEntity() == null) ? Seeker.D.player : Seeker.D.getRenderViewEntity());
        final double posX = ((Entity)o2).posX;
        final double posY = ((Entity)o2).posY;
        final double posZ = ((Entity)o2).posZ;
        final Vec3d m;
        ((Entity)o2).posX = (m = xG.M((Entity)o2)).x;
        ((Entity)o2).posY = m.y;
        ((Entity)o2).posZ = m.z;
        distance = ((Entity)o2).getDistance(n + Seeker.D.getRenderManager().viewerPosX, distance + Seeker.D.getRenderManager().viewerPosY, n2 + Seeker.D.getRenderManager().viewerPosZ);
        double n5 = 6.00949208E-315 + ((Nametags)pg.M().M().M((Class)Ec.class)).scaling.B().floatValue() * distance;
        if (distance <= 0.0) {
            n5 = 3.56495293E-315;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)n4 + 1.4f, (float)n2);
        final float n6 = -Seeker.D.getRenderManager().playerViewY;
        final float n7 = 1.0f;
        final float n8 = 0.0f;
        GlStateManager.rotate(n6, n8, n7, n8);
        GlStateManager.rotate(Seeker.D.getRenderManager().playerViewX, (Seeker.D.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, (float)0);
        GlStateManager.scale(-n5, -n5, n5);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        final gD gd;
        Object o3;
        if ((gd = (gD)pg.M().M().M((Class)gD.class)) != null && (boolean)gd.E.M()) {
            o3 = o;
            final int n9 = gd.p.M(s) / 2;
            GlStateManager.enableBlend();
            final float n10 = (float)(-n9 - 1);
            final gD gd2 = gd;
            xG.M(n10, (float)(-gd2.p.M()), (float)(n9 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            gd2.p.M(s, -n9, (double)(-(gd.p.M() - 1)), -1);
        }
        else {
            final int n11 = Seeker.D.fontRenderer.getStringWidth(s) / 2;
            GlStateManager.enableBlend();
            xG.M((float)(-n11 - 1), (float)(-Seeker.D.fontRenderer.FONT_HEIGHT), (float)(n11 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            Seeker.D.fontRenderer.drawStringWithShadow(s, (float)(-n11), (float)(-(Seeker.D.fontRenderer.FONT_HEIGHT - 1)), -1);
            o3 = o;
        }
        ((Entity)o3).posX = posX;
        final float n12 = 1.0f;
        final double posZ2 = posZ;
        final Object o4 = o;
        ((Entity)o4).posY = posY;
        ((Entity)o4).posZ = posZ2;
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(n12, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static Value e(final Seeker seeker) {
        return seeker.fill;
    }
    
    public static Minecraft getMinecraft4() {
        return Seeker.D;
    }
    
    private boolean M(final EntityAnimal entityAnimal) {
        return entityAnimal != null && ZG.b((Entity)entityAnimal) && entityAnimal.getMaxHealth() == 20.0f;
    }
    
    public static Minecraft getMinecraft5() {
        return Seeker.D;
    }
    
    public static void M(final Seeker seeker, final Entity entity, final String s, final double n, final double n2, final double n3, final float n4) {
        seeker.M(entity, s, n, n2, n3, n4);
    }
    
    public static boolean M(final Seeker seeker, final EntityAnimal entityAnimal) {
        return seeker.M(entityAnimal);
    }
    
    public static String M(final Seeker seeker, final EntityAnimal entityAnimal) {
        return seeker.M(entityAnimal);
    }
    
    private String M(final EntityAnimal entityAnimal) {
        final double ceil;
        String s;
        if ((ceil = Math.ceil(entityAnimal.getHealth())) > 0.0) {
            s = "§a";
        }
        else if (ceil > 0.0) {
            s = "§2";
        }
        else if (ceil > 0.0) {
            s = "§e";
        }
        else if (ceil > 0.0) {
            s = "§6";
        }
        else if (ceil > 0.0) {
            s = "§c";
        }
        else {
            s = "§4";
        }
        return new StringBuilder().insert(0, s).append((ceil > 0.0) ? Integer.valueOf((int)ceil) : "dead").toString();
    }
    
    private void M(final Entity entity, final String s, final double n, double distance, final double n2, final float n3) {
        final double n4 = distance + (entity.isSneaking() ? 0.0 : 8.48798316E-315);
        final Entity entity3;
        final Entity entity2 = entity3 = (Entity)((Seeker.D.getRenderViewEntity() == null) ? Seeker.D.player : Seeker.D.getRenderViewEntity());
        final double posX = entity3.posX;
        final double posY = entity3.posY;
        final double posZ = entity3.posZ;
        final Vec3d m;
        entity3.posX = (m = xG.M(entity3)).x;
        entity3.posY = m.y;
        entity3.posZ = m.z;
        final gD gd2;
        final gD gd = gd2 = (gD)pg.M().M().M((Class)gD.class);
        distance = entity2.getDistance(n + Seeker.D.getRenderManager().viewerPosX, distance + Seeker.D.getRenderManager().viewerPosY, n2 + Seeker.D.getRenderManager().viewerPosZ);
        final int n5 = Seeker.D.fontRenderer.getStringWidth(s) / 2;
        final int n6 = gd.p.M(s) / 2;
        final Nametags nametags = (Nametags)pg.M().M().M((Class)Ec.class);
        double n7 = 6.00949208E-315 + ((nametags == null) ? 0.003f : nametags.scaling.B().floatValue()) * distance;
        if (distance <= 0.0) {
            n7 = 3.56495293E-315;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)n4 + 1.4f, (float)n2);
        final float n8 = -Seeker.D.getRenderManager().playerViewY;
        final float n9 = 1.0f;
        final float n10 = 0.0f;
        GlStateManager.rotate(n8, n10, n9, n10);
        GlStateManager.rotate(Seeker.D.getRenderManager().playerViewX, (Seeker.D.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, (float)0);
        GlStateManager.scale(-n7, -n7, n7);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        Entity entity4;
        if (gd2.E.M()) {
            GlStateManager.enableBlend();
            xG.M((float)(-n6 - 1), (float)(-gd2.p.M()), (float)(n6 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            entity4 = entity2;
            gd2.p.M(s, -n6, (double)(-(gd2.p.M() - 1)), -1);
        }
        else {
            GlStateManager.enableBlend();
            xG.M((float)(-n5 - 1), (float)(-Seeker.D.fontRenderer.FONT_HEIGHT), (float)(n5 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            Seeker.D.fontRenderer.drawStringWithShadow(s, (float)(-n5), (float)(-(Seeker.D.fontRenderer.FONT_HEIGHT - 1)), -1);
            entity4 = entity2;
        }
        entity4.posX = posX;
        final float n11 = 1.0f;
        final double posZ2 = posZ;
        final Entity entity5 = entity2;
        entity5.posY = posY;
        entity5.posZ = posZ2;
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(n11, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static NumberValue M(final Seeker seeker) {
        return seeker.width;
    }
    
    public static Value M(final Seeker seeker) {
        return seeker.boundingBox;
    }
    
    public static Minecraft getMinecraft6() {
        return Seeker.D;
    }
}
