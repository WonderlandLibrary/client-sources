package net.futureclient.client.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.futureclient.client.Uh;
import net.futureclient.client.zh;
import net.futureclient.client.xG;
import net.futureclient.client.Jb;
import net.futureclient.client.gD;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Iterator;
import java.util.Collection;
import net.futureclient.client.gB;
import net.futureclient.client.modules.render.waypoints.Listener6;
import net.futureclient.client.modules.render.waypoints.Listener5;
import net.futureclient.client.modules.render.waypoints.Listener4;
import net.futureclient.client.modules.render.waypoints.Listener3;
import net.futureclient.client.modules.render.waypoints.Listener2;
import net.futureclient.client.modules.render.waypoints.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.aA;
import net.futureclient.client.mA;
import net.futureclient.client.MC;
import net.futureclient.client.GC;
import net.futureclient.client.vB;
import net.futureclient.client.pg;
import java.util.ArrayList;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.RC;
import net.futureclient.client.R;
import java.util.List;
import java.text.DecimalFormat;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Xa;
import net.futureclient.client.Ea;

public class Waypoints extends Ea
{
    private Xa L;
    private Value<Boolean> deathPoints;
    private NumberValue scaling;
    private Value<Boolean> logoutPoints;
    private DecimalFormat K;
    private NumberValue width;
    public List<Xa> d;
    private Value<Boolean> lines;
    private R<RC.sc> mode;
    public List<Xa> k;
    
    public static Minecraft getMinecraft() {
        return Waypoints.D;
    }
    
    public Waypoints() {
        super("Waypoints", new String[] { "Waypoints", "points", "wp", "Waypoint", "wpoint" }, false, -16742264, Category.RENDER);
        this.deathPoints = new Value<Boolean>(true, new String[] { "DeathPoints", "DeathPoint", "DP", "KillSpot", "DiePoints" });
        this.logoutPoints = new Value<Boolean>(true, new String[] { "LogoutPoints", "LogoutSpots", "LoginSpots", "LogPoints", "LogSpts" });
        this.lines = new Value<Boolean>(true, new String[] { "Lines", "Line", "DrawLiens", "DrawLiens", "Liens" });
        this.width = new NumberValue(1.8f, 0.1f, 5.0f, 1.273197475E-314, new String[] { "Width", "w" });
        this.scaling = new NumberValue(1.561788902E-314, 1.748524532E-314, 5.941588215E-315, 1.748524532E-314, new String[] { "Scaling", "scale", "s" });
        this.mode = new R<RC.sc>(RC.sc.a, new String[] { "Mode", "Type", "Method" });
        final int n = 6;
        this.k = new ArrayList<Xa>();
        this.d = new ArrayList<Xa>();
        this.K = new DecimalFormat("0.#");
        final Value[] array = new Value[n];
        array[0] = this.deathPoints;
        array[1] = this.logoutPoints;
        array[2] = this.lines;
        array[3] = this.width;
        array[4] = this.scaling;
        array[5] = this.mode;
        this.M(array);
        pg.M().M().e((Object)new vB(this, new String[] { "Waypoints", "points", "wp", "Waypoint", "wpoint" }));
        pg.M().M().e((Object)new GC(this, new String[] { "WaypointsAdd", "waypointadd", "pointadd", "pointsadd", "wpadd", "wadd", "padd" }));
        pg.M().M().e((Object)new MC(this, new String[] { "WaypointsRemove", "waypointremove", "pointremove", "pointsremove", "wpremove", "wrem", "wremove", "premove" }));
        pg.M().M().e((Object)new mA(this, new String[] { "Destination", "WaypointDestination", "WaypointsDestination", "Dest" }));
        pg.M().M().e((Object)new aA(this, new String[] { "RemoveDestination", "DestinationRemove", "RemoveDest", "DRemove", "DeleteDestination", "DelDestination", "DelDest" }));
        this.M(new n[] { (n)new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this) });
        new gB(this, "waypoints.txt");
    }
    
    public static Minecraft getMinecraft1() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Waypoints.D;
    }
    
    public void b() {
        final ArrayList<Xa> list = new ArrayList<Xa>();
        final Iterator<Xa> iterator = this.k.iterator();
        while (iterator.hasNext()) {
            final Xa xa;
            if ((xa = iterator.next()) != null && xa.M().contains("_logout_spot")) {
                list.add(xa);
            }
        }
        this.k.removeAll(list);
        super.b();
    }
    
    public static Value b(final Waypoints waypoints) {
        return waypoints.logoutPoints;
    }
    
    public static Minecraft getMinecraft18() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Waypoints.D;
    }
    
    public Xa e(final String s) {
        final Iterator<Xa> iterator = this.k.iterator();
        while (iterator.hasNext()) {
            final Xa xa;
            if ((xa = iterator.next()).M().equalsIgnoreCase(s)) {
                return xa;
            }
        }
        return null;
    }
    
    private void e(final Xa xa, final double n, final double n2, final double n3, final float n4, double m, final double n5) {
        final double distance = ((Entity)((Waypoints.D.getRenderViewEntity() == null) ? Waypoints.D.player : Waypoints.D.getRenderViewEntity())).getDistance(m, xa.e(), n5);
        final String format = String.format("%s XYZ %.1f %.1f %.1f", xa.M(), m, xa.e(), n5);
        m = this.M(6.00949208E-315 + this.scaling.B().doubleValue() * distance, 1.273197475E-314, this.scaling.B().doubleValue() * 0.0);
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)n2 + 1.4f, (float)n3);
        final float n6 = -Waypoints.D.getRenderManager().playerViewY;
        final float n7 = 1.0f;
        final float n8 = 0.0f;
        GlStateManager.rotate(n6, n8, n7, n8);
        GlStateManager.rotate(Waypoints.D.getRenderManager().playerViewX, (Waypoints.D.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, (float)0);
        GlStateManager.scale(-m, -m, m);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        final String string = new StringBuilder().insert(0, xa.M()).append(" ").append(Math.round(distance)).append("m").toString();
        int n9;
        int n10;
        int n11;
        if (gd != null && (boolean)gd.E.M()) {
            final gD gd2 = gd;
            n9 = gd2.p.M(format) / 2;
            n10 = -gd2.p.M();
            n11 = gd2.p.M(string) / 2;
        }
        else {
            n9 = Waypoints.D.fontRenderer.getStringWidth(format) / 2;
            n10 = -Waypoints.D.fontRenderer.FONT_HEIGHT;
            n11 = Waypoints.D.fontRenderer.getStringWidth(string) / 2;
        }
        switch (Jb.k[this.mode.M().ordinal()]) {
            case 1:
                GlStateManager.enableBlend();
                xG.M((float)(-n9 - 1), (float)n10, n9 + 1.5f, 1.0f, 1.8f, 1426064384, 855638016);
                GlStateManager.disableBlend();
                if (gd != null && (boolean)gd.E.M()) {
                    gd.p.M(format, -n9, (double)(-(gd.p.M() - 1)), -5592406);
                    break;
                }
                Waypoints.D.fontRenderer.drawStringWithShadow(format, (float)(-n9), (float)(-(Waypoints.D.fontRenderer.FONT_HEIGHT - 1)), -5592406);
                break;
            case 2:
                GlStateManager.enableBlend();
                xG.M((float)(-n11 - 1), (float)n10, n11 + 1.5f, 1.0f, 1.8f, 1426064384, 855638016);
                GlStateManager.disableBlend();
                if (gd != null && (boolean)gd.E.M()) {
                    gd.p.M(string, -n11, (double)(-(gd.p.M() - 1)), -5592406);
                    break;
                }
                Waypoints.D.fontRenderer.drawStringWithShadow(string, (float)(-n11), (float)(-(Waypoints.D.fontRenderer.FONT_HEIGHT - 1)), -5592406);
                break;
        }
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static void e(final Waypoints waypoints, final Xa xa, final double n, final double n2, final double n3, final float n4, final double n5, final double n6) {
        waypoints.e(xa, n, n2, n3, n4, n5, n6);
    }
    
    public static Minecraft getMinecraft21() {
        return Waypoints.D;
    }
    
    public static Value e(final Waypoints waypoints) {
        return waypoints.deathPoints;
    }
    
    public static Minecraft getMinecraft22() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft23() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft24() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft25() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft27() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft28() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft30() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft33() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft34() {
        return Waypoints.D;
    }
    
    private Xa M(final String s) {
        final Iterator<Xa> iterator = this.k.iterator();
        while (iterator.hasNext()) {
            final Xa xa;
            if ((xa = iterator.next()).M().equalsIgnoreCase(s) && ((xa.b().equals("singleplayer") && Waypoints.D.isSingleplayer()) || (Waypoints.D.getCurrentServerData() != null && xa.b().equalsIgnoreCase(Waypoints.D.getCurrentServerData().serverIP.replaceAll(":", "_"))) || (Waypoints.D.isConnectedToRealms() && xa.b().equals("realms")))) {
                return xa;
            }
        }
        return null;
    }
    
    private void M(final Xa xa, final double n, final double n2, final double n3, final float n4, final double n5, final double n6) {
        final double distance = ((Entity)((Waypoints.D.getRenderViewEntity() == null) ? Waypoints.D.player : Waypoints.D.getRenderViewEntity())).getDistance(n + Waypoints.D.getRenderManager().viewerPosX, n2 + Waypoints.D.getRenderManager().viewerPosY, n3 + Waypoints.D.getRenderManager().viewerPosZ);
        double n7 = 6.00949208E-315 + this.scaling.B().floatValue() * distance;
        if (distance <= 0.0) {
            n7 = 3.56495293E-315;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)n, (float)n2 + 1.4f, (float)n3);
        final float n8 = -Waypoints.D.getRenderManager().playerViewY;
        final float n9 = 1.0f;
        final float n10 = 0.0f;
        GlStateManager.rotate(n8, n10, n9, n10);
        GlStateManager.rotate(Waypoints.D.getRenderManager().playerViewX, (Waypoints.D.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, (float)0);
        GlStateManager.scale(-n7, -n7, n7);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        String s = null;
        gD gd2 = null;
        switch (Jb.k[this.mode.M().ordinal()]) {
            case 1:
                s = String.format(zh.M("\u001dY\u0018rap\u0018\u000f\u0016\u001b^\n\u001d\u0004\tL\u0018\u000f\u0016\u001b^"), xa.M(), n5, xa.e(), n6);
                gd2 = gd;
                break;
            case 2:
                s = new StringBuilder().insert(0, xa.M()).append(" ").append(Math.round(distance)).append("m").toString();
                gd2 = gd;
                break;
            default:
                s = "";
                gd2 = gd;
                break;
        }
        if (gd2.E.M()) {
            final int n11 = gd.p.M(s) / 2;
            GlStateManager.enableBlend();
            final float n12 = (float)(-n11 - 1);
            final gD gd3 = gd;
            xG.M(n12, (float)(-gd3.p.M()), (float)(n11 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            GlStateManager.disableBlend();
            gd3.p.M(s, -n11, (double)(-(gd.p.M() - 1)), -5592406);
        }
        else {
            final int n13;
            xG.M((float)(-(n13 = Waypoints.D.fontRenderer.getStringWidth(s) / 2) - 1), (float)(-Waypoints.D.fontRenderer.FONT_HEIGHT), (float)(n13 + 2), 1.0f, 1.8f, 1426064384, 855638016);
            Waypoints.D.fontRenderer.drawStringWithShadow(s, (float)(-n13), (float)(-(Waypoints.D.fontRenderer.FONT_HEIGHT - 1)), -5592406);
        }
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    public static NumberValue M(final Waypoints waypoints) {
        return waypoints.width;
    }
    
    public static Xa M(final Waypoints waypoints) {
        return waypoints.L;
    }
    
    public static Minecraft getMinecraft35() {
        return Waypoints.D;
    }
    
    public static Xa M(final Waypoints waypoints, final Xa l) {
        return waypoints.L = l;
    }
    
    public static DecimalFormat M(final Waypoints waypoints) {
        return waypoints.K;
    }
    
    private float M(final Xa xa) {
        final float n = (float)(xa.b() - Waypoints.D.player.posX);
        final float n2 = (float)(xa.M() - Waypoints.D.player.posZ);
        final float n3 = n;
        final float n4 = n3 * n3;
        final float n5 = n2;
        return Uh.M(n4 + n5 * n5);
    }
    
    public static boolean M(final Waypoints waypoints, final Xa xa) {
        return waypoints.M(xa);
    }
    
    public Xa M() {
        return this.L;
    }
    
    private boolean M(final Xa xa) {
        final Iterator<Xa> iterator = this.k.iterator();
        while (iterator.hasNext()) {
            final Xa xa2;
            if ((xa2 = iterator.next()).b().equalsIgnoreCase(xa.b()) && xa2.b() == xa.b() && xa2.e() == xa.e() && xa2.M() == xa.M() && xa2.e().equalsIgnoreCase(xa.e())) {
                return true;
            }
        }
        return false;
    }
    
    public static Value M(final Waypoints waypoints) {
        return waypoints.lines;
    }
    
    private double M(final double n, final double n2, final double n3) {
        return Math.max(n2, Math.min(n3, n));
    }
    
    public static Xa M(final Waypoints waypoints, final String s) {
        return waypoints.M(s);
    }
    
    public static void M(final Waypoints waypoints, final Xa xa, final double n, final double n2, final double n3, final float n4, final double n5, final double n6) {
        waypoints.M(xa, n, n2, n3, n4, n5, n6);
    }
    
    private Vec3d M(double n, double n2, double n3) {
        final Object o = (Waypoints.D.getRenderViewEntity() == null) ? Waypoints.D.player : Waypoints.D.getRenderViewEntity();
        final Vec3d vec3d = new Vec3d(((Entity)o).posX, ((Entity)o).posY, ((Entity)o).posZ);
        final double distance = ((Entity)o).getDistance(n, n2, n3);
        final Vec3d vec3d2 = vec3d;
        n = vec3d2.x - 0.0 * (vec3d.x - n) / distance;
        n2 = vec3d2.y - 0.0 * (vec3d.y - n2) / distance;
        n3 = vec3d2.z - 0.0 * (vec3d.z - n3) / distance;
        return new Vec3d(n, n2, n3);
    }
    
    public static float M(final Waypoints waypoints, final Xa xa) {
        return waypoints.M(xa);
    }
    
    public static Vec3d M(final Waypoints waypoints, final double n, final double n2, final double n3) {
        return waypoints.M(n, n2, n3);
    }
    
    public static Minecraft getMinecraft36() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft37() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft38() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft39() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft40() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft41() {
        return Waypoints.D;
    }
    
    public static Minecraft getMinecraft42() {
        return Waypoints.D;
    }
}
