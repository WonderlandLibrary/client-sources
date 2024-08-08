package net.futureclient.client.modules.combat;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.smoothaim.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.UE;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class SmoothAim extends Ea
{
    private Value<Boolean> friendCheck;
    private NumberValue innerAngle;
    private NumberValue distance;
    private R<UE.sD> aimCheck;
    private Value<Boolean> invisibleCheck;
    private NumberValue outerAngle;
    private Value<Boolean> proximityCheck;
    private Value<Boolean> rayTrace;
    private NumberValue speed;
    private Timer d;
    private Value<Boolean> weaponCheck;
    private Value<Boolean> teamCheck;
    private R<UE.SD> aimType;
    
    public SmoothAim() {
        super("SmoothAim", new String[] { "SmoothAim" }, true, -4615980, Category.COMBAT);
        this.aimType = new R<UE.SD>(UE.SD.k, new String[] { "AimType", "Aiming", "AimMethod", "at", "bone" });
        this.aimCheck = new R<UE.sD>(UE.sD.k, new String[] { "AimCheck", "Check", "ac" });
        this.invisibleCheck = new Value<Boolean>(true, new String[] { "InvisibleCheck", "Invisibles", "Invis", "ic", "i" });
        this.teamCheck = new Value<Boolean>(true, new String[] { "TeamCheck", "Team", "AttackTeammates", "t", "tc" });
        this.friendCheck = new Value<Boolean>(true, new String[] { "FriendCheck", "Friends", "AttackFriends", "Betray", "f" });
        this.rayTrace = new Value<Boolean>(true, new String[] { "RayTrace", "Trace", "Ray", "r", "rt" });
        this.proximityCheck = new Value<Boolean>(true, new String[] { "ProximityCheck", "Proximity", "pc", "p" });
        this.weaponCheck = new Value<Boolean>(true, new String[] { "WeaponCheck", "Weapon", "w" });
        this.speed = new NumberValue(0.0, 1.0, 0.0, 1.0, new String[] { "Speed", "Sped", "AimSpeed", "S" });
        this.distance = new NumberValue(0.0, 1.273197475E-314, 0.0, 1.273197475E-314, new String[] { "Distance", "Dist", "Far", "Close", "Length", "d", "Range" });
        this.innerAngle = new NumberValue(5.941588215E-315, 0.0, 0.0, 1.0, new String[] { "InnerAngle", "Inner", "In", "ia" });
        this.outerAngle = new NumberValue(0.0, 0.0, 0.0, 1.0, new String[] { "OuterAngle", "Outer", "Out", "oa" });
        final int n = 12;
        this.d = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.aimType;
        array[1] = this.aimCheck;
        array[2] = this.invisibleCheck;
        array[3] = this.teamCheck;
        array[4] = this.friendCheck;
        array[5] = this.rayTrace;
        array[6] = this.proximityCheck;
        array[7] = this.weaponCheck;
        array[8] = this.speed;
        array[9] = this.distance;
        array[10] = this.innerAngle;
        array[11] = this.outerAngle;
        this.M(array);
        this.M(new n[] { (n)new Listener1(this) });
    }
    
    public static NumberValue B(final SmoothAim smoothAim) {
        return smoothAim.innerAngle;
    }
    
    public static Value B(final SmoothAim smoothAim) {
        return smoothAim.proximityCheck;
    }
    
    public static Minecraft getMinecraft() {
        return SmoothAim.D;
    }
    
    public static Minecraft getMinecraft1() {
        return SmoothAim.D;
    }
    
    public static Value C(final SmoothAim smoothAim) {
        return smoothAim.friendCheck;
    }
    
    public static Minecraft getMinecraft2() {
        return SmoothAim.D;
    }
    
    public static NumberValue b(final SmoothAim smoothAim) {
        return smoothAim.distance;
    }
    
    public static Minecraft getMinecraft3() {
        return SmoothAim.D;
    }
    
    public static Value b(final SmoothAim smoothAim) {
        return smoothAim.rayTrace;
    }
    
    public static Minecraft getMinecraft4() {
        return SmoothAim.D;
    }
    
    public static R e(final SmoothAim smoothAim) {
        return smoothAim.aimType;
    }
    
    public static NumberValue e(final SmoothAim smoothAim) {
        return smoothAim.outerAngle;
    }
    
    public static Value e(final SmoothAim smoothAim) {
        return smoothAim.weaponCheck;
    }
    
    public static Value i(final SmoothAim smoothAim) {
        return smoothAim.teamCheck;
    }
    
    public static Minecraft getMinecraft5() {
        return SmoothAim.D;
    }
    
    public static Minecraft getMinecraft6() {
        return SmoothAim.D;
    }
    
    public static Minecraft getMinecraft7() {
        return SmoothAim.D;
    }
    
    public static R M(final SmoothAim smoothAim) {
        return smoothAim.aimCheck;
    }
    
    public static Value M(final SmoothAim smoothAim) {
        return smoothAim.invisibleCheck;
    }
    
    public static Timer M(final SmoothAim smoothAim) {
        return smoothAim.d;
    }
    
    public static NumberValue M(final SmoothAim smoothAim) {
        return smoothAim.speed;
    }
    
    public static Minecraft getMinecraft8() {
        return SmoothAim.D;
    }
}
