package net.futureclient.client.modules.render;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.futureclient.client.dH;
import net.futureclient.client.af;
import net.futureclient.client.pg;
import net.futureclient.client.modules.combat.CopsAndCrims;
import org.lwjgl.opengl.EXTFramebufferObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.futureclient.client.WG;
import java.awt.Color;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.futureclient.client.WI;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import net.futureclient.loader.mixin.common.entity.living.wrapper.IEntityWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.entity.Entity;
import net.futureclient.client.ZG;
import net.minecraft.client.renderer.culling.Frustum;
import net.futureclient.client.modules.render.esp.Listener4;
import net.futureclient.client.modules.render.esp.Listener3;
import net.futureclient.client.modules.render.esp.Listener2;
import net.futureclient.client.modules.render.esp.Listener1;
import net.futureclient.client.n;
import java.util.concurrent.CopyOnWriteArrayList;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.futureclient.client.utils.NumberValue;
import java.util.List;
import net.minecraft.client.shader.Framebuffer;
import net.futureclient.client.ed;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class ESP extends Ea
{
    public static boolean F;
    private Value<Boolean> storages;
    private R<ed.bC> mode;
    private ed.Xc C;
    private Value<Boolean> players;
    private final double I;
    private Value<Boolean> items;
    private final int H;
    private Value<Boolean> monsters;
    public static boolean L;
    private Value<Boolean> vehicles;
    private Framebuffer A;
    public static boolean j;
    private Value<Boolean> animals;
    private final List<ed.QB> M;
    private Value<Boolean> sounds;
    private NumberValue linewidth;
    public static boolean D;
    private Value<Boolean> others;
    
    private void A() {
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static Minecraft getMinecraft() {
        return ESP.D;
    }
    
    public ESP() {
        super("ESP", new String[] { "ESP", "ES", "boxes", "playeresp" }, true, -58469, Category.RENDER);
        final int h = 3;
        this.I = 1.0;
        this.H = h;
        this.M = new CopyOnWriteArrayList<ed.QB>();
        this.players = new Value<Boolean>(true, new String[] { "Players", "player", "human", "P" });
        this.monsters = new Value<Boolean>(true, new String[] { "Monsters", "Hostiles", "Mobs", "monstas", "H" });
        this.animals = new Value<Boolean>(true, new String[] { "Animals", "Neutrals", "ani", "animal", "N" });
        this.vehicles = new Value<Boolean>(true, new String[] { "Vehicles", "Boat", "Boats", "Minecarts", "Minecart", "V" });
        this.others = new Value<Boolean>(true, new String[] { "Others", "Other", "Miscellaneous", "Misc", "Miscellaneus", "M" });
        this.items = new Value<Boolean>(true, new String[] { "Items", "Item", "tiems", "I" });
        this.sounds = new Value<Boolean>(false, new String[] { "Sounds", "Sound", "Walk" });
        this.storages = new Value<Boolean>(true, new String[] { "Storages", "chest", "chests", "echest", "Storage", "S", "ShulkerBoxes", "Shulkers", "ShulkerChests", "ShulkerChest", "Shulk", "Shulks", "Shulkchest" });
        this.linewidth = new NumberValue(3.0f, 0.1f, 10.0f, 1.273197475E-314, new String[] { "Linewidth", "width" });
        this.mode = new R<ed.bC>(ed.bC.k, new String[] { "Mode", "m", "type" });
        this.M(new Value[] { this.mode, this.players, this.monsters, this.animals, this.vehicles, this.others, this.items, this.sounds, this.storages, this.linewidth });
        this.M(new n[] { (n)new Listener1(this), new Listener2(this), new Listener3(this), (n)new Listener4(this) });
    }
    
    static {
        ESP.F = true;
        ESP.D = true;
        ESP.j = true;
        ESP.L = true;
    }
    
    public void B() {
        super.B();
        if (this.A != null) {
            this.A.unbindFramebuffer();
        }
        this.A = null;
        if (this.C != null) {
            this.C.e();
        }
        this.C = null;
    }
    
    public static void B(final ESP esp) {
        esp.c();
    }
    
    public static Value B(final ESP esp) {
        return esp.items;
    }
    
    public static Minecraft getMinecraft1() {
        return ESP.D;
    }
    
    private void C() {
        final Framebuffer framebuffer;
        if ((framebuffer = ESP.D.getFramebuffer()).depthBuffer > -1) {
            final int depthBuffer = -1;
            final Framebuffer framebuffer2 = framebuffer;
            this.M(framebuffer2);
            framebuffer2.depthBuffer = depthBuffer;
        }
    }
    
    public static void C(final ESP esp) {
        esp.i();
    }
    
    public static Value C(final ESP esp) {
        return esp.storages;
    }
    
    public static Minecraft getMinecraft2() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft3() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft4() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft5() {
        return ESP.D;
    }
    
    private void J() {
        try {
            final Frustum frustum = new Frustum();
            final Object o = (ESP.D.getRenderViewEntity() == null) ? ESP.D.player : ESP.D.getRenderViewEntity();
            for (final Entity entity : ZG.e()) {
                if (this.e(entity)) {
                    final double n = ((Entity)o).lastTickPosX + (((Entity)o).posX - ((Entity)o).lastTickPosX) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n2 = ((Entity)o).lastTickPosY + (((Entity)o).posY - ((Entity)o).lastTickPosY) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n3 = ((Entity)o).lastTickPosZ + (((Entity)o).posZ - ((Entity)o).lastTickPosZ) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final Entity entity2 = entity;
                    final Frustum frustum2 = frustum;
                    frustum2.setPosition(n, n2, n3);
                    if (!frustum2.isBoundingBoxInFrustum(entity2.getEntityBoundingBox())) {
                        continue;
                    }
                    final boolean b = false;
                    final Entity entity3 = entity;
                    this.M(entity3);
                    boolean isWet = b;
                    if (entity3 instanceof EntityWolf) {
                        isWet = ((IEntityWolf)entity).isIsWet();
                        ((IEntityWolf)entity).setIsWet(false);
                    }
                    ESP.D.getRenderManager().renderEntityStatic(entity, ((IMinecraft)ESP.D).getTimer().renderPartialTicks, true);
                    if (!(entity instanceof EntityWolf)) {
                        continue;
                    }
                    ((IEntityWolf)entity).setIsWet(isWet);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Minecraft getMinecraft6() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft7() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft8() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft9() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft10() {
        return ESP.D;
    }
    
    public static void c(final ESP esp) {
        esp.J();
    }
    
    private void c() {
        GL11.glStencilFunc(514, 1, 15);
        final int n = 7680;
        final int n2 = 7680;
        GL11.glStencilOp(n2, n, n2);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static Minecraft getMinecraft11() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft12() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft13() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft14() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft15() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft16() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft17() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft18() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft19() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft20() {
        return ESP.D;
    }
    
    public void b() {
        super.b();
        this.M.clear();
    }
    
    public static Value b(final ESP esp) {
        return esp.monsters;
    }
    
    private boolean b(final Entity entity) {
        return entity != null && entity != ESP.D.player && !entity.equals((Object)ESP.D.player.getRidingEntity()) && ((this.players.M() && entity instanceof EntityPlayer) || (this.monsters.M() && (WI.C(entity) || WI.e(entity))) || (this.animals.M() && (WI.i(entity) || WI.g(entity))) || (this.others.M() && WI.K(entity)) || (this.items.M() && entity instanceof EntityItem));
    }
    
    public static void b(final ESP esp) {
        esp.h();
    }
    
    public static Minecraft getMinecraft21() {
        return ESP.D;
    }
    
    private void H() {
        try {
            final Frustum frustum = new Frustum();
            final Object o = (ESP.D.getRenderViewEntity() == null) ? ESP.D.player : ESP.D.getRenderViewEntity();
            for (final Entity entity : ESP.D.world.getLoadedEntityList()) {
                if (this.M(entity)) {
                    final double n = ((Entity)o).lastTickPosX + (((Entity)o).posX - ((Entity)o).lastTickPosX) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n2 = ((Entity)o).lastTickPosY + (((Entity)o).posY - ((Entity)o).lastTickPosY) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n3 = ((Entity)o).lastTickPosZ + (((Entity)o).posZ - ((Entity)o).lastTickPosZ) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final Entity entity2 = entity;
                    final Frustum frustum2 = frustum;
                    frustum2.setPosition(n, n2, n3);
                    if (!frustum2.isBoundingBoxInFrustum(entity2.getEntityBoundingBox())) {
                        continue;
                    }
                    this.M(entity);
                    ESP.D.getRenderManager().renderEntityStatic(entity, ((IMinecraft)ESP.D).getTimer().renderPartialTicks, true);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Minecraft getMinecraft22() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft23() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft24() {
        return ESP.D;
    }
    
    public static void e(final ESP esp) {
        esp.C();
    }
    
    public static boolean e(final ESP esp, final Entity entity) {
        return esp.b(entity);
    }
    
    public static Value e(final ESP esp) {
        return esp.others;
    }
    
    public static Minecraft getMinecraft25() {
        return ESP.D;
    }
    
    private boolean e(final Entity entity) {
        return (entity != null && !entity.equals((Object)ESP.D.player.getRidingEntity()) && (WI.g(entity) || WI.i(entity)) && this.animals.M()) || ((WI.C(entity) || WI.e(entity)) && this.monsters.M()) || (entity instanceof EntityEnderCrystal && this.others.M()) || (entity instanceof EntityPlayer && entity != ESP.D.player && this.players.M());
    }
    
    public static Minecraft getMinecraft26() {
        return ESP.D;
    }
    
    private void i() {
        GL11.glStencilFunc(512, 0, 15);
        final int n = 7681;
        final int n2 = 7681;
        GL11.glStencilOp(n2, n, n2);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void i(final ESP esp) {
        esp.H();
    }
    
    public static Minecraft getMinecraft27() {
        return ESP.D;
    }
    
    public static Value i(final ESP esp) {
        return esp.animals;
    }
    
    public static Minecraft getMinecraft28() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft29() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft30() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft31() {
        return ESP.D;
    }
    
    public static void g(final ESP esp) {
        esp.g();
    }
    
    private void g() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static Minecraft getMinecraft32() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft33() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft34() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft35() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft36() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft37() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft38() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft39() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft40() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft41() {
        return ESP.D;
    }
    
    private void K() {
        GL11.glPushAttrib(1048575);
        ESP.D.entityRenderer.disableLightmap();
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        final int n = 7681;
        final int n2 = 7681;
        GL11.glStencilOp(n2, n, n2);
        GL11.glLineWidth(this.linewidth.B().floatValue());
        final int n3 = 7681;
        final int n4 = 7681;
        GL11.glStencilOp(n4, n3, n4);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static Minecraft getMinecraft42() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft43() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft44() {
        return ESP.D;
    }
    
    private boolean M(final Entity entity) {
        return (entity instanceof EntityMinecart || entity instanceof EntityBoat) && !entity.equals((Object)ESP.D.player.getRidingEntity()) && this.vehicles.M();
    }
    
    public static NumberValue M(final ESP esp) {
        return esp.linewidth;
    }
    
    public static ed.Xc M(final ESP esp, final ed.Xc c) {
        return esp.C = c;
    }
    
    public static List M(final ESP esp) {
        return esp.M;
    }
    
    public static void M(final ESP esp) {
        esp.A();
    }
    
    public static Value M(final ESP esp) {
        return esp.vehicles;
    }
    
    public static boolean M(final ESP esp, final Entity entity) {
        return esp.e(entity);
    }
    
    public static ed.Xc M(final ESP esp) {
        return esp.C;
    }
    
    public static Minecraft getMinecraft45() {
        return ESP.D;
    }
    
    private void M(final TileEntity tileEntity) {
        if (tileEntity instanceof TileEntityChest) {
            WG.M(new Color(200, 200, 101));
            return;
        }
        if (tileEntity instanceof TileEntityEnderChest) {
            WG.M(new Color(155, 0, 200, 255));
            return;
        }
        if (tileEntity instanceof TileEntityShulkerBox) {
            WG.M(new Color(200, 0, 106, 255));
        }
    }
    
    public static Framebuffer M(final ESP esp) {
        return esp.A;
    }
    
    public static void M(final ESP esp, final Entity entity, final double n, final double n2, final double n3, final int n4) {
        esp.M(entity, n, n2, n3, n4);
    }
    
    public static R M(final ESP esp) {
        return esp.mode;
    }
    
    private void M(final Entity entity, final double n, final double n2, final double n3, final int n4) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2 + entity.height + 0.5f, (float)n3);
        final float n5 = 1.0f;
        final float n6 = 0.0f;
        GL11.glNormal3f(n6, n5, n6);
        final float n7 = -ESP.D.getRenderManager().playerViewY;
        final float n8 = 1.0f;
        final float n9 = 0.0f;
        GL11.glRotatef(n7, n9, n8, n9);
        GL11.glScalef(-0.017f, -0.017f, 0.017f);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int n10 = 0;
        if (entity.isSneaking()) {
            n10 = 4;
        }
        GL11.glDisable(3553);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glEnable(2848);
        GlStateManager.disableLighting();
        WG.M(0.0, n10 + 19, 0.0, n10 + 21, -16777216);
        WG.M(0.0, n10 + 21, 0.0, n10 + 46, -16777216);
        WG.M(0.0, n10 + 21, 0.0, n10 + 25, n4);
        WG.M(0.0, n10 + 25, 0.0, n10 + 48, n4);
        WG.M(0.0, n10 + 19, 0.0, n10 + 21, -16777216);
        WG.M(0.0, n10 + 21, 0.0, n10 + 46, -16777216);
        WG.M(0.0, n10 + 21, 0.0, n10 + 25, n4);
        WG.M(0.0, n10 + 25, 0.0, n10 + 48, n4);
        WG.M(0.0, n10 + 140, 0.0, n10 + 142, -16777216);
        WG.M(0.0, n10 + 115, 0.0, n10 + 140, -16777216);
        WG.M(0.0, n10 + 136, 0.0, n10 + 140, n4);
        WG.M(0.0, n10 + 113, 0.0, n10 + 140, n4);
        WG.M(0.0, n10 + 140, 0.0, n10 + 142, -16777216);
        WG.M(0.0, n10 + 115, 0.0, n10 + 140, -16777216);
        WG.M(0.0, n10 + 136, 0.0, n10 + 140, n4);
        WG.M(0.0, n10 + 113, 0.0, n10 + 140, n4);
        GlStateManager.enableLighting();
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        final float n11 = 1.0f;
        final int n12 = 1;
        GL11.glColor4f((float)n12, (float)n12, n11, (float)n12);
        GL11.glPopMatrix();
    }
    
    private void M(final Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        final int glGenRenderbuffersEXT = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, ESP.D.displayWidth, ESP.D.displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, glGenRenderbuffersEXT);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, glGenRenderbuffersEXT);
    }
    
    private void M(final Entity entity) {
        if (WI.H(entity) && this.vehicles.M()) {
            WG.M(new Color(200, 100, 0, 255));
        }
        if ((WI.g(entity) || WI.i(entity)) && this.animals.M()) {
            final int n = 200;
            final int n2 = 0;
            WG.M(new Color(n2, n, n2, 255));
        }
        if ((WI.C(entity) || WI.e(entity)) && this.monsters.M()) {
            WG.M(new Color(200, 60, 60, 255));
        }
        if (entity instanceof EntityEnderCrystal && this.others.M()) {
            final int n3 = 100;
            final int n4 = 200;
            WG.M(new Color(n4, n3, n4, 255));
        }
        if (entity instanceof EntityPlayer && this.players.M()) {
            final EntityPlayer entityPlayer;
            if ((entityPlayer = (EntityPlayer)entity).isInvisible()) {
                WG.M(new Color(133, 200, 178, 255));
            }
            final CopsAndCrims copsAndCrims = (CopsAndCrims)pg.M().M().M((Class)af.class);
            if (pg.M().M().M(entityPlayer.getName()) || (copsAndCrims != null && copsAndCrims.M() && copsAndCrims.M(entityPlayer, (EntityPlayer)ESP.D.player))) {
                WG.M(new Color(85, 200, 200, 255));
                return;
            }
            final float distance = ESP.D.player.getDistance((Entity)entityPlayer);
            float n5;
            if (distance >= 60.0f) {
                n5 = 120.0f;
            }
            else {
                final float n6 = distance;
                n5 = n6 + n6;
            }
            WG.M(new dH(n5, 100.0f, 50.0f, 0.55f).e());
        }
    }
    
    public static Framebuffer M(final ESP esp, final Framebuffer a) {
        return esp.A = a;
    }
    
    public static Minecraft getMinecraft46() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft47() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft48() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft49() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft50() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft51() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft52() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft53() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft54() {
        return ESP.D;
    }
    
    public static Value h(final ESP esp) {
        return esp.sounds;
    }
    
    private void h() {
        try {
            final Frustum frustum = new Frustum();
            final Object o = (ESP.D.getRenderViewEntity() == null) ? ESP.D.player : ESP.D.getRenderViewEntity();
            final Iterator<TileEntity> iterator = (Iterator<TileEntity>)ESP.D.world.loadedTileEntityList.iterator();
            while (iterator.hasNext()) {
                final TileEntity tileEntity;
                if ((((tileEntity = iterator.next()) instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest) && this.storages.M()) || (tileEntity instanceof TileEntityShulkerBox && this.storages.M())) {
                    final double n = ((Entity)o).lastTickPosX + (((Entity)o).posX - ((Entity)o).lastTickPosX) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n2 = ((Entity)o).lastTickPosY + (((Entity)o).posY - ((Entity)o).lastTickPosY) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final double n3 = ((Entity)o).lastTickPosZ + (((Entity)o).posZ - ((Entity)o).lastTickPosZ) * ((IMinecraft)ESP.D).getTimer().renderPartialTicks;
                    final TileEntity tileEntity2 = tileEntity;
                    final Frustum frustum2 = frustum;
                    frustum2.setPosition(n, n2, n3);
                    if (!frustum2.isBoxInFrustum((double)tileEntity2.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ(), (double)(tileEntity.getPos().getX() + 1), (double)(tileEntity.getPos().getY() + 1), (double)(tileEntity.getPos().getZ() + 1))) {
                        continue;
                    }
                    this.M(tileEntity);
                    TileEntityRendererDispatcher.instance.render(tileEntity, ((IMinecraft)ESP.D).getTimer().renderPartialTicks, -1);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static void h(final ESP esp) {
        esp.K();
    }
    
    public static Minecraft getMinecraft55() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft56() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft57() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft58() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft59() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft60() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft61() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft62() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft63() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft64() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft65() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft66() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft67() {
        return ESP.D;
    }
    
    public static Minecraft getMinecraft68() {
        return ESP.D;
    }
}
