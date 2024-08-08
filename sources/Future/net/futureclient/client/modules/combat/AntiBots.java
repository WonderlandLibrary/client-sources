package net.futureclient.client.modules.combat;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.modules.combat.antibots.Listener4;
import net.futureclient.client.modules.combat.antibots.Listener3;
import net.futureclient.client.modules.combat.antibots.Listener2;
import net.futureclient.client.modules.combat.antibots.Listener1;
import net.futureclient.client.n;
import java.util.HashMap;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import java.util.UUID;
import java.util.Map;
import net.futureclient.client.Ea;

public class AntiBots extends Ea
{
    private boolean j;
    public Map<Integer, UUID> K;
    public Value<Boolean> uuid;
    private boolean d;
    public Value<Boolean> invisible;
    public Value<Boolean> ping;
    public Value<Boolean> name;
    
    public static Minecraft getMinecraft() {
        return AntiBots.D;
    }
    
    public AntiBots() {
        super("AntiBots", new String[] { "AntiBots", "AntiBot", "AntiEntity", "Antiboat" }, true, -8374971, Category.COMBAT);
        this.ping = new Value<Boolean>(true, new String[] { "Ping", "Pin", "Pong" });
        this.invisible = new Value<Boolean>(true, new String[] { "Invisible", "Invis" });
        this.name = new Value<Boolean>(true, new String[] { "Name", "Nam" });
        this.uuid = new Value<Boolean>(true, new String[] { "UUID", "UserID", "UID" });
        final int n = 4;
        this.K = new HashMap<Integer, UUID>();
        final Value[] array = new Value[n];
        array[0] = this.ping;
        array[1] = this.invisible;
        array[2] = this.name;
        array[3] = this.uuid;
        this.M(array);
        this.M(new n[] { (n)new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this) });
    }
    
    public static boolean B(final AntiBots antiBots, final EntityPlayer entityPlayer) {
        return antiBots.M(entityPlayer);
    }
    
    private boolean B(final EntityPlayer entityPlayer) {
        return this.invisible.M() && entityPlayer.isInvisible() && !entityPlayer.isPotionActive(MobEffects.INVISIBILITY);
    }
    
    public void B() {
        super.B();
        if (!AntiBots.D.isSingleplayer() && AntiBots.D.getCurrentServerData() != null) {
            this.j = AntiBots.D.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
            this.d = AntiBots.D.getCurrentServerData().serverIP.toLowerCase().contains("mineplex");
        }
    }
    
    public static Minecraft getMinecraft1() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AntiBots.D;
    }
    
    private boolean b(final EntityPlayer entityPlayer) {
        return this.name.M() && entityPlayer.getDisplayName().getFormattedText().equalsIgnoreCase(new StringBuilder().insert(0, entityPlayer.getName()).append("§r").toString()) && !AntiBots.D.player.getDisplayName().getFormattedText().equalsIgnoreCase(new StringBuilder().insert(0, AntiBots.D.player.getName()).append("§r").toString());
    }
    
    private List<EntityPlayer> b() {
        final ArrayList list = new ArrayList<EntityPlayer>(AntiBots.D.world.playerEntities);
        list.remove(AntiBots.D.player);
        return (List<EntityPlayer>)list;
    }
    
    public static boolean b(final AntiBots antiBots, final EntityPlayer entityPlayer) {
        return antiBots.b(entityPlayer);
    }
    
    public void b() {
        super.b();
        this.K.clear();
    }
    
    public static Minecraft getMinecraft9() {
        return AntiBots.D;
    }
    
    public static boolean e(final AntiBots antiBots, final boolean j) {
        return antiBots.j = j;
    }
    
    public static Minecraft getMinecraft10() {
        return AntiBots.D;
    }
    
    private boolean e(final EntityPlayer entityPlayer) {
        return this.uuid.M() && AntiBots.D.getConnection() != null && AntiBots.D.getConnection().getPlayerInfo(entityPlayer.getUniqueID()) == null;
    }
    
    public static boolean e(final AntiBots antiBots, final EntityPlayer entityPlayer) {
        return antiBots.e(entityPlayer);
    }
    
    public static Minecraft getMinecraft11() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft12() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AntiBots.D;
    }
    
    private boolean M(final EntityPlayer entityPlayer) {
        return this.j && (int)AntiBots.D.player.posX == (int)entityPlayer.posX && (int)AntiBots.D.player.posZ == (int)entityPlayer.posZ && (int)entityPlayer.posY - (int)AntiBots.D.player.posY > 8;
    }
    
    public static Minecraft getMinecraft15() {
        return AntiBots.D;
    }
    
    public static boolean M(final AntiBots antiBots, final EntityPlayer entityPlayer) {
        return antiBots.B(entityPlayer);
    }
    
    public static boolean M(final AntiBots antiBots) {
        return antiBots.d;
    }
    
    public static List M(final AntiBots antiBots) {
        return antiBots.b();
    }
    
    public static boolean M(final AntiBots antiBots, final boolean d) {
        return antiBots.d = d;
    }
    
    public static Minecraft getMinecraft16() {
        return AntiBots.D;
    }
    
    public static Minecraft getMinecraft17() {
        return AntiBots.D;
    }
}
