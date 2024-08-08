package net.futureclient.client.modules.combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.futureclient.client.modules.combat.copsandcrims.Listener3;
import net.futureclient.client.modules.combat.copsandcrims.Listener2;
import net.futureclient.client.modules.combat.copsandcrims.Listener1;
import net.futureclient.client.n;
import java.util.HashMap;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import net.futureclient.client.af;
import net.futureclient.client.utils.Value;
import net.futureclient.client.ZH;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class CopsAndCrims extends Ea
{
    private NumberValue vRecoil;
    private R<ZH.zi> bone;
    private int C;
    public Value<Boolean> teamProtect;
    private R<af.Pe> clickType;
    private int B;
    private Value<Boolean> rcs;
    private Map<EntityPlayer, List<Vec3d>> l;
    private NumberValue hRecoil;
    private float E;
    private float A;
    private NumberValue delay;
    private Value<Boolean> noSpread;
    private Value<Boolean> autoShoot;
    private Value<Boolean> silentAim;
    private NumberValue fov;
    private int D;
    private EntityPlayer k;
    
    public static Minecraft getMinecraft() {
        return CopsAndCrims.D;
    }
    
    public CopsAndCrims() {
        super("CopsAndCrims", new String[] { "CopsAndCrims", "CAC", "Aimware" }, true, -3128823, Category.COMBAT);
        this.B = 10;
        this.delay = new NumberValue(7.0f, 0.0f, 35.0f, 1.273197475E-314, new String[] { "Delay", "Del" });
        this.fov = new NumberValue(180.0f, 1.0f, 180.0f, 1, new String[] { "FOV", "Fieldofview" });
        this.hRecoil = new NumberValue(0.1f, 0.1f, 2.0f, 1.273197475E-314, new String[] { "HRecoil", "rcsHorizontal", "Horizontal" });
        this.vRecoil = new NumberValue(0.5f, 0.1f, 2.0f, 1.273197475E-314, new String[] { "VRecoil", "rcsVertical", "Vertical" });
        this.bone = new R<ZH.zi>(ZH.zi.k, new String[] { "Bone", "Targeting", "AimPos" });
        this.clickType = new R<af.Pe>(af.Pe.a, new String[] { "ClickType", "Type", "Hand" });
        this.teamProtect = new Value<Boolean>(true, new String[] { "TeamProtect", "TeamKill", "AttackTeam", "teamdamage" });
        this.noSpread = new Value<Boolean>(true, new String[] { "NoSpread" });
        this.rcs = new Value<Boolean>(false, new String[] { "RCS", "RecoilControlSystem", "Recoil", "RecoilControl", "RC" });
        this.silentAim = new Value<Boolean>(true, new String[] { "SilentAim", "Silent", "Sailent" });
        this.autoShoot = new Value<Boolean>(true, new String[] { "AutoShoot", "AS", "Autofire", "Autoshot" });
        final int n = 11;
        this.l = new HashMap<EntityPlayer, List<Vec3d>>();
        final Value[] array = new Value[n];
        array[0] = this.noSpread;
        array[1] = this.rcs;
        array[2] = this.autoShoot;
        array[3] = this.silentAim;
        array[4] = this.clickType;
        array[5] = this.delay;
        array[6] = this.fov;
        array[7] = this.bone;
        array[8] = this.hRecoil;
        array[9] = this.vRecoil;
        array[10] = this.teamProtect;
        this.M(array);
        this.M(new n[] { new Listener1(this), (n)new Listener2(this), new Listener3(this) });
    }
    
    public static int B(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.C;
    }
    
    public static Value B(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.autoShoot;
    }
    
    public static NumberValue B(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.vRecoil;
    }
    
    public static Minecraft getMinecraft1() {
        return CopsAndCrims.D;
    }
    
    public static int C(final CopsAndCrims copsAndCrims) {
        return ++copsAndCrims.C;
    }
    
    public static Minecraft getMinecraft2() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft3() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft4() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft5() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft6() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft7() {
        return CopsAndCrims.D;
    }
    
    public static Value b(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.rcs;
    }
    
    public static int b(final CopsAndCrims copsAndCrims) {
        return ++copsAndCrims.D;
    }
    
    public static Minecraft getMinecraft8() {
        return CopsAndCrims.D;
    }
    
    public static NumberValue b(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.delay;
    }
    
    public static Minecraft getMinecraft9() {
        return CopsAndCrims.D;
    }
    
    public static int e(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.B;
    }
    
    public static NumberValue e(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.fov;
    }
    
    public static float e(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.E;
    }
    
    public static Value e(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.noSpread;
    }
    
    public static R e(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.bone;
    }
    
    public static Minecraft getMinecraft10() {
        return CopsAndCrims.D;
    }
    
    public static int e(final CopsAndCrims copsAndCrims, final int d) {
        return copsAndCrims.D = d;
    }
    
    public static float e(final CopsAndCrims copsAndCrims, final float e) {
        return copsAndCrims.E = e;
    }
    
    public static Minecraft getMinecraft11() {
        return CopsAndCrims.D;
    }
    
    public static int i(final CopsAndCrims copsAndCrims) {
        return --copsAndCrims.D;
    }
    
    public static Minecraft getMinecraft12() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft13() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft14() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft15() {
        return CopsAndCrims.D;
    }
    
    public static Value M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.silentAim;
    }
    
    public static NumberValue M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.hRecoil;
    }
    
    public static Minecraft getMinecraft16() {
        return CopsAndCrims.D;
    }
    
    public static float M(final CopsAndCrims copsAndCrims, final float a) {
        return copsAndCrims.A = a;
    }
    
    private double M(final EntityPlayer entityPlayer) {
        double n = -CopsAndCrims.D.player.getDistance((Entity)entityPlayer);
        if (entityPlayer.lastTickPosX == entityPlayer.posX && entityPlayer.lastTickPosY == entityPlayer.posY && entityPlayer.lastTickPosZ == entityPlayer.posZ) {
            n += 0.0;
        }
        return n - entityPlayer.getDistance((Entity)CopsAndCrims.D.player) / 5.0f;
    }
    
    public static EntityPlayer M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.k;
    }
    
    public static double M(final CopsAndCrims copsAndCrims, final EntityPlayer entityPlayer) {
        return copsAndCrims.M(entityPlayer);
    }
    
    public static int M(final CopsAndCrims copsAndCrims, final int c) {
        return copsAndCrims.C = c;
    }
    
    private String M(final Entity entity) {
        final Matcher matcher;
        if ((matcher = Pattern.compile("§(.).*§r").matcher(entity.getDisplayName().getFormattedText())).find()) {
            return matcher.group(1);
        }
        return "f";
    }
    
    public static Map M(final CopsAndCrims copsAndCrims, final Map l) {
        return copsAndCrims.l = (Map<EntityPlayer, List<Vec3d>>)l;
    }
    
    public boolean M(final EntityPlayer entityPlayer, final EntityPlayer entityPlayer2) {
        return entityPlayer.getDisplayName().getFormattedText().contains(new StringBuilder().insert(0, "§").append(this.M((Entity)entityPlayer)).toString()) && entityPlayer2.getDisplayName().getFormattedText().contains(new StringBuilder().insert(0, "§").append(this.M((Entity)entityPlayer)).toString());
    }
    
    public static Map M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.l;
    }
    
    public static R M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.clickType;
    }
    
    public static float M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.A;
    }
    
    public static EntityPlayer M(final CopsAndCrims copsAndCrims, final EntityPlayer k) {
        return copsAndCrims.k = k;
    }
    
    public static int M(final CopsAndCrims copsAndCrims) {
        return copsAndCrims.D;
    }
    
    public static Minecraft getMinecraft17() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft18() {
        return CopsAndCrims.D;
    }
    
    public static Minecraft getMinecraft19() {
        return CopsAndCrims.D;
    }
}
