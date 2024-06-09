/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Combat;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.mojang.authlib.GameProfile;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import us.amerikan.amerikan;
import us.amerikan.events.MotionUpdateEvent;
import us.amerikan.manager.FriendManager;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;
import us.amerikan.utils.RayCastUtilNew;
import us.amerikan.utils.Rotation;
import us.amerikan.utils.RotationHandler;
import us.amerikan.utils.TimeHelper;
import us.amerikan.utils.Utils;

public class KillAura
extends Module {
    TimeHelper delay = new TimeHelper();
    TimeHelper swtichDelay = new TimeHelper();
    float[] rotation;
    public static float Range;
    static int CPS;
    public static float AutoBlock;
    private boolean AAC;
    boolean once = false;
    private static /* synthetic */ int[] $SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State;

    public KillAura() {
        super("KillAura", "KillAura", 0, Category.COMBAT);
        amerikan.setmgr.rSetting(new Setting("Range", this, 4.0, 0.1, 7.0, false));
        amerikan.setmgr.rSetting(new Setting("MaxCPS", this, 10.0, 1.0, 20.0, true));
        amerikan.setmgr.rSetting(new Setting("MinCPS", this, 5.0, 1.0, 20.0, true));
        amerikan.setmgr.rSetting(new Setting("AutoBlock", this, false));
        amerikan.setmgr.rSetting(new Setting("Hitfail", this, false));
        amerikan.setmgr.rSetting(new Setting("AntiBot", this, false));
        amerikan.setmgr.rSetting(new Setting("VisibleOnly", this, false));
        amerikan.setmgr.rSetting(new Setting("Teams", this, false));
        amerikan.setmgr.rSetting(new Setting("EvadeBlocks", this, false));
        amerikan.setmgr.rSetting(new Setting("Precision", this, 15.0, 1.0, 20.0, true));
        amerikan.setmgr.rSetting(new Setting("InvAttack", this, false));
        amerikan.setmgr.rSetting(new Setting("Raycast", this, false));
        ArrayList<String> options = new ArrayList<String>();
        options.add("AAC");
        options.add("Cubecraft");
        amerikan.setmgr.rSetting(new Setting("Raycast Mode", this, "AAC", options));
    }

    @EventTarget
    public void onUpdate(MotionUpdateEvent event) {
        switch (KillAura.$SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State()[event.getState().ordinal()]) {
            case 1: {
                if ((KillAura.mc.currentScreen instanceof GuiInventory || KillAura.mc.currentScreen instanceof GuiChest) && !amerikan.setmgr.getSettingByName("InvAttack").getValBoolean()) {
                    return;
                }
                this.AAC = amerikan.setmgr.getSettingByName("EvadeBlocks").getValBoolean();
                Range = (float)amerikan.setmgr.getSettingByName("Range").getValDouble();
                CPS = Utils.random((int)amerikan.setmgr.getSettingByName("MinCPS").getValDouble(), (int)amerikan.setmgr.getSettingByName("MaxCPS").getValDouble());
                for (Object o2 : KillAura.mc.theWorld.loadedEntityList) {
                    if (!(o2 instanceof EntityLivingBase)) continue;
                    if (o2 == Minecraft.thePlayer) continue;
                    EntityLivingBase player = (EntityLivingBase)o2;
                    if ((double)Minecraft.thePlayer.getDistanceToEntity(player) <= (double)Range + 0.2 && !this.isValid(player) && !this.isBot(player)) {
                        float[] rot = this.AAC ? Utils.getNeededRotations(KillAura.resolveBestHitVec(player, (int)amerikan.setmgr.getSettingByName("Precision").getValDouble(), true)) : Rotation.getRotationsForEntity(player);
                        RotationHandler.setRotation(Utils.updateRotation(event.getYaw(), rot[0] + (float)this.getRandom().nextInt(4) - (float)this.getRandom().nextInt(6), 180.0f), Utils.updateRotation(event.getPitch(), rot[1] + (float)this.getRandom().nextInt(7) - (float)this.getRandom().nextInt(9), 90.0f));
                        event.setYaw(RotationHandler.getYaw());
                        event.setPitch(RotationHandler.getPitch());
                    }
                    EntityLivingBase raycast = amerikan.setmgr.getSettingByName("Raycast").getValBoolean() ? RayCastUtilNew.rayCast((double)Range + 0.2, event.getYaw(), event.getPitch()) : player;
                    this.HitEntity(raycast);
                    this.AutoBlock(player);
                }
            }
        }
    }

    private Random getRandom() {
        return new Random();
    }

    private boolean isHitfailing() {
        return amerikan.setmgr.getSettingByName("Hitfail").getValBoolean() && this.getRandom().nextBoolean() && this.getRandom().nextBoolean() && this.getRandom().nextBoolean();
    }

    public static Vec3 resolveBestHitVec(Entity enty, int precision, boolean evadeBlocks) {
        try {
            Vec3 headVec = Minecraft.thePlayer.func_174824_e(1.0f);
            Vec3 bestHitVec = new Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
            int offset = precision / 3;
            float height = enty.getEyeHeight() / (float)precision;
            float width = enty.width * 0.5f / (float)offset;
            for (int offsetY = 0; offsetY <= precision; ++offsetY) {
                for (int offsetX = -offset; offsetX <= offset; ++offsetX) {
                    for (int offsetZ = -offset; offsetZ <= offset; ++offsetZ) {
                        Vec3 possibleVec = new Vec3(enty.posX + (double)(width * (float)offsetX), enty.posY + (double)(height * (float)offsetY), enty.posZ + (double)(width * (float)offsetZ));
                        if (evadeBlocks) {
                            MovingObjectPosition movingObj = Minecraft.thePlayer.getEntityWorld().rayTraceBlocks(headVec, possibleVec);
                            if (movingObj != null) continue;
                        }
                        if (!(headVec.distanceTo(possibleVec) < headVec.distanceTo(bestHitVec))) continue;
                        bestHitVec = possibleVec;
                    }
                }
            }
            if (bestHitVec.xCoord == Double.MAX_VALUE) {
                bestHitVec = null;
            }
            return bestHitVec;
        }
        catch (Throwable t2) {
            t2.printStackTrace();
            return enty.getPositionVector();
        }
    }

    public void HitEntity(Entity e2) {
        if (e2 != null) {
            if ((double)Minecraft.thePlayer.getDistanceToEntity(e2) <= amerikan.setmgr.getSettingByName("Range").getValDouble() && !this.isValid((EntityLivingBase)e2) && !this.isBot((EntityLivingBase)e2) && !this.isHitfailing() && TimeHelper.hasReached(1000 / CPS + this.getRandom().nextInt(70) - this.getRandom().nextInt(30))) {
                Minecraft.thePlayer.swingItem();
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(e2, C02PacketUseEntity.Action.ATTACK));
                TimeHelper.reset();
            }
        }
    }

    public void AutoBlock(Entity e2) {
        if (e2 instanceof EntityLivingBase) {
            if (e2 != Minecraft.thePlayer) {
                if (Minecraft.thePlayer.getDistanceToEntity(e2) <= Range && !this.isValid((EntityLivingBase)e2) && !this.isBot((EntityLivingBase)e2) && ((EntityLivingBase)e2).deathTime <= 0 && amerikan.setmgr.getSettingByName("AutoBlock").getValBoolean()) {
                    if (Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
                        Minecraft.thePlayer.setItemInUse(Minecraft.thePlayer.getHeldItem(), 100);
                    } else {
                        KillAura.mc.gameSettings.keyBindUseItem.pressed = false;
                    }
                }
            }
        }
    }

    private static boolean isInTeam(EntityLivingBase e2) {
        String ef2;
        String pf2;
        Minecraft.getMinecraft();
        if (e2 == Minecraft.thePlayer) {
            return true;
        }
        String tname = e2.getDisplayName().getFormattedText();
        Minecraft.getMinecraft();
        String pname = Minecraft.thePlayer.getDisplayName().getFormattedText();
        boolean f1 = tname.startsWith("\u00a7");
        boolean f2 = pname.startsWith("\u00a7");
        return !f1 || !f2 || !(ef2 = KillAura.getString(tname).substring(1, 2)).startsWith(pf2 = KillAura.getString(pname).substring(1, 2));
    }

    public static String getString(String inp) {
        String re2 = inp;
        int count = 0;
        String pc2 = inp.substring(0);
        String pc22 = inp.substring(2);
        String pc3 = inp.substring(4);
        String pc4 = inp.substring(6);
        if (pc2.startsWith(pc22)) {
            ++count;
        }
        if (pc22.startsWith(pc3)) {
            ++count;
        }
        if (pc3.startsWith(pc4)) {
            ++count;
        }
        re2 = re2.substring(count * 2, re2.length());
        return re2;
    }

    public boolean checkEntityID(Entity entity) {
        boolean check = entity.getEntityId() <= 1070000000 && entity.getEntityId() > -1;
        return check;
    }

    public static boolean isInTablist(Entity player) {
        if (mc.isSingleplayer()) {
            return false;
        }
        for (Object o2 : mc.getNetHandler().func_175106_d()) {
            NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)o2;
            if (!playerInfo.func_178845_a().getName().equalsIgnoreCase(player.getName())) continue;
            return true;
        }
        return false;
    }

    private boolean isBot(EntityLivingBase e2) {
        block2 : {
            block3 : {
                if (!amerikan.setmgr.getSettingByName("AntiBot").getValBoolean()) break block2;
                if (e2.isSlient()) break block3;
                if (!((float)e2.ticksExisted <= 20.0f * Timer.timerSpeed || e2.isAirBorne && e2.motionY == 0.0 || e2 instanceof EntityPlayer && !KillAura.isInTablist(e2)) && !(e2 instanceof EntityPlayer)) break block2;
            }
            return true;
        }
        return false;
    }

    private boolean isValid(EntityLivingBase e2) {
        block5 : {
            block6 : {
                if (e2 instanceof EntityArmorStand) break block5;
                if (!amerikan.friendmgr.isFriend(e2.getName())) break block6;
                if (!ModuleManager.getModuleByName("NoFriends").isEnabled()) break block5;
            }
            if (!e2.isDead && this.checkEntityID(e2)) {
                if (!(!Minecraft.thePlayer.canEntityBeSeen(e2) && amerikan.setmgr.getSettingByName("VisibleOnly").getValBoolean() || amerikan.setmgr.getSettingByName("Teams").getValBoolean() && !KillAura.isInTeam(e2) || e2 instanceof EntityPlayer && e2.getName().equalsIgnoreCase("\u00a76Dealer"))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void messageWithoutPrefix(String msg) {
        ChatComponentText chat = new ChatComponentText(msg);
        if (msg != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chat);
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        if (!this.once) {
            KillAura.messageWithoutPrefix(String.valueOf(amerikan.instance.Client_Prefix) + "\u00a7c Killaura bypasst nicht 100% auf AAC.");
        }
        this.once = true;
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }

    static /* synthetic */ int[] $SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State() {
        if ($SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State != null) {
            int[] arrn;
            return arrn;
        }
        int[] arrn = new int[MotionUpdateEvent.State.values().length];
        try {
            arrn[MotionUpdateEvent.State.POST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[MotionUpdateEvent.State.PRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State = arrn;
        return $SWITCH_TABLE$us$amerikan$events$MotionUpdateEvent$State;
    }
}

