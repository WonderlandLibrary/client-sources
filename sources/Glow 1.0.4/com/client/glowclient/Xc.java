package com.client.glowclient;

import com.client.glowclient.utils.*;
import java.util.*;
import com.google.gson.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.modules.other.*;
import com.client.glowclient.modules.jewishtricks.*;
import com.client.glowclient.modules.render.*;
import com.client.glowclient.modules.movement.*;
import com.client.glowclient.modules.combat.*;
import com.client.glowclient.modules.player.*;
import com.client.glowclient.modules.server.*;
import com.client.glowclient.commands.*;

public class xc
{
    private static ArrayList<NumberValue> G;
    private static ArrayList<nB> d;
    private static Hashtable<String, Value> L;
    private static ArrayList<Value> A;
    private static ArrayList<StringValue> B;
    private static ArrayList<BooleanValue> b;
    
    public static Collection<tc> e() {
        return Collections.unmodifiableCollection((Collection<? extends tc>)xc.b);
    }
    
    public static void M(final String json) {
        try {
            JsonObject asJsonObject;
            try {
                asJsonObject = new JsonParser().parse(json).getAsJsonObject();
            }
            catch (IllegalStateException ex) {
                return;
            }
            for (final Map.Entry<String, JsonElement> entry : asJsonObject.entrySet()) {
                xc.L.getOrDefault(entry.getKey(), Value.M()).M(entry.getKey(), entry.getValue().getAsJsonObject());
            }
        }
        catch (Exception ex2) {}
    }
    
    public static String M() {
        final JsonObject jsonElement = new JsonObject();
        final Iterator<Value> iterator2;
        Iterator<Value> iterator = iterator2 = xc.L.values().iterator();
        while (iterator.hasNext()) {
            final Value value = iterator2.next();
            final JsonObject value2 = new JsonObject();
            iterator = iterator2;
            final JsonObject jsonObject = jsonElement;
            final Value value3 = value;
            value3.M(value2);
            jsonObject.add(value3.k(), value2);
        }
        return new Gson().toJson(jsonElement);
    }
    
    public xc() {
        super();
    }
    
    public static Collection<AA> k() {
        return Collections.unmodifiableCollection((Collection<? extends AA>)xc.A);
    }
    
    public static Collection<nB> A() {
        return Collections.unmodifiableCollection((Collection<? extends nB>)xc.d);
    }
    
    static {
        xc.L = new Hashtable<String, Value>();
        xc.A = new ArrayList<Value>();
        xc.B = new ArrayList<StringValue>();
        xc.b = new ArrayList<BooleanValue>();
        xc.G = new ArrayList<NumberValue>();
        xc.d = new ArrayList<nB>();
        final Iterator<NF> iterator = ModuleManager.M().iterator();
        while (iterator.hasNext()) {
            final Module module;
            if ((module = (Module)iterator.next()) instanceof ModuleContainer) {
                M(module.g);
            }
        }
        final Iterator<NF> iterator2 = ModuleManager.M().iterator();
        while (iterator2.hasNext()) {
            final Module module2;
            if ((module2 = (Module)iterator2.next()) instanceof ModuleContainer || module2 instanceof eE) {
                M(module2.c);
            }
        }
        final qe[] windows;
        final int length = (windows = iD.M().getWindows()).length;
        int n;
        int i = n = 0;
        while (i < length) {
            final qe qe2;
            final qe qe = qe2 = windows[n];
            M(qe.f);
            M(qe.k);
            M(qe.I);
            if (qe.K != null) {
                M(qe2.K);
            }
            final Iterator<gf> iterator4;
            Iterator<gf> iterator3 = iterator4 = qe2.M().iterator();
            while (iterator3.hasNext()) {
                final gf gf = iterator4.next();
                iterator3 = iterator4;
            }
            i = ++n;
        }
        M(Yaw.mode);
        M(Yaw.angle);
        M(Xray.opacity);
        M(Tracers.width);
        M(Tracers.players);
        M(Tracers.passive);
        M(Tracers.items);
        M(Tracers.hostile);
        M(Tracers.distance);
        M(Timer.tPSSync);
        M(Timer.speed);
        M(TabMod.noPing);
        M(TabMod.noNames);
        M(TabMod.noHeads);
        M(TabMod.extended);
        M(StorageESP.shulkers);
        M(StorageESP.itemFrames);
        M(StorageESP.hoppers);
        M(StorageESP.furnaces);
        M(StorageESP.eChests);
        M(StorageESP.dispensers);
        M(StorageESP.chests);
        M(Step.useTimer);
        M(Step.height);
        M(Step.infinite);
        M(Step.entityStep);
        M(Speed.useTimer);
        M(Speed.sprint);
        M(Speed.spoofCam);
        M(Speed.mode);
        M(Scaffold.tower);
        M(Reach.distance);
        M(Nuker.silent);
        M(Nuker.selective);
        M(Nuker.range);
        M(Nuker.flat);
        M(NoRender.scoreboard);
        M(NoRender.totemAnim);
        M(NoRender.skyLight);
        M(NoRender.hurtcam);
        M(NoRender.fog);
        M(NoRender.effectsHUD);
        M(NoRender.bossOverlay);
        M(NoRender.blockOverlay);
        M(NoPush.water);
        M(NoPush.entities);
        M(NoPush.blocks);
        M(NametagsImpact.players);
        M(NametagsImpact.passive);
        M(NametagsImpact.mobs);
        M(Nametags.visibility);
        M(Nametags.shulkerInv);
        M(Nametags.scaling);
        M(Nametags.scale);
        M(Nametags.players);
        M(Nametags.passive);
        M(Nametags.mobs);
        M(Nametags.renderItems);
        M(Nametags.health);
        M(LiquidSpeed.waterSpeed);
        M(LiquidSpeed.lavaSpeed);
        M(Knockback.vertical);
        M(Knockback.horizontal);
        M(KillAura.tPSsync);
        M(KillAura.throughWalls);
        M(KillAura.silent);
        M(KillAura.range);
        M(KillAura.priority);
        M(KillAura.players);
        M(KillAura.passive);
        M(KillAura.mode);
        M(KillAura.mobs);
        M(KillAura.manualClick);
        M(KillAura.friendDetect);
        M(KillAura.attackBox);
        M(Jesus.mode);
        M(IceSpeed.speed);
        M(HUD.watermark);
        M(HUD.tPS);
        M(HUD.speed);
        M(HUD.side);
        M(HUD.separation);
        M(HUD.rotation);
        M(HUD.red);
        M(HUD.order);
        M(HUD.green);
        M(HUD.direction);
        M(HUD.dimensionCoords);
        M(HUD.descriptions);
        M(HUD.customFont);
        M(HUD.coordinates);
        M(HUD.color);
        M(HUD.blue);
        M(HUD.arrayList);
        M(HUD.armor);
        M(HorseMod.speed);
        M(HorseMod.jumpPower);
        M(HorseMod.jump);
        M(GlowBot.private);
        M(GlowBot.prefix);
        M(Freecam.tracer);
        M(Freecam.speed);
        M(Freecam.nametag);
        M(Freecam.eSPbox);
        M(Flight.speed);
        M(Flight.mode);
        M(FastBreak.fast);
        M(FastBreak.delayTimer);
        M(EntitySpeed.speed);
        M(EntityESP.width);
        M(EntityESP.players);
        M(EntityESP.passive);
        M(EntityESP.mode);
        M(EntityESP.items);
        M(EntityESP.hostile);
        M(EntityESP.everything);
        M(EnchColors.red);
        M(EnchColors.mode);
        M(EnchColors.green);
        M(EnchColors.blue);
        M(ElytraControl.conSpeed);
        M(ElytraControl.mode);
        M(ElytraControl.creSpeed);
        M(CrystalAura.throughWalls);
        M(CrystalAura.range);
        M(CrystalAura.mode);
        M(SignMod.length);
        M(ColorManager.saturation);
        M(ColorManager.rendering);
        M(ColorManager.rainbowSpeed);
        M(ColorManager.brightness);
        M(ChunkFinder.red);
        M(ChunkFinder.mode);
        M(ChunkFinder.green);
        M(ChunkFinder.blue);
        M(ChunkFinder.alpha);
        M(CameraClip.zoom);
        M(BypassFly.useTimer);
        M(BypassFly.speed);
        M(BypassFly.phaseSpeed);
        M(BypassFly.groundDetect);
        M(BypassFly.glidespeed);
        M(BypassFly.glidePower);
        M(BypassFly.glide);
        M(BypassFly.fastShot);
        M(BypassFly.bowBomber);
        M(BreadCrumbs.width);
        M(BowSpam.tpsSync);
        M(BowSpam.speed);
        M(BoatTravel.speed);
        M(BoatTravel.mode);
        M(BoatJump.power);
        M(BoatFly.yaw);
        M(BoatFly.upSpeed);
        M(BoatFly.speed);
        M(BoatFly.glideSpeed);
        M(BoatAura.throughWalls);
        M(BoatAura.targetRidden);
        M(BoatAura.range);
        M(Blink.tracer);
        M(Blink.nametag);
        M(Blink.eSPbox);
        M(BedAura.range);
        M(AutoWalk.mode);
        M(AutoReconnect.delay);
        M(AutoLog.health);
        M(AutoGapple.healthAmount);
        M(AutoGapple.regen);
        M(AutoGapple.health);
        M(AntiPotion.weakness);
        M(AntiPotion.slowness);
        M(AntiPotion.nausea);
        M(AntiPotion.levitation);
        M(AntiPotion.invisibility);
        M(AntiPotion.fatigue);
        M(AntiPotion.blindness);
        M(AntiAFK.hitdelay);
        M(AntiPackets.animation);
        M(AntiPackets.chatMessage);
        M(AntiPackets.clickWindow);
        M(AntiPackets.clientStatus);
        M(AntiPackets.closeWindow);
        M(AntiPackets.confirmTeleport);
        M(AntiPackets.confirmTransaction);
        M(AntiPackets.creativeInventoryAction);
        M(AntiPackets.customPayload);
        M(AntiPackets.enchantItem);
        M(AntiPackets.entityAction);
        M(AntiPackets.heldItemChange);
        M(AntiPackets.input);
        M(AntiPackets.keepAlive);
        M(AntiPackets.placeRecipe);
        M(AntiPackets.player);
        M(AntiPackets.playerAbilities);
        M(AntiPackets.playerDigging);
        M(AntiPackets.playerTryUseItem);
        M(AntiPackets.playerTryUseItemOnBlock);
        M(AntiPackets.recipeInfo);
        M(AntiPackets.resourcePackStatus);
        M(AntiPackets.seenAdvancements);
        M(AntiPackets.spectate);
        M(AntiPackets.steerBoat);
        M(AntiPackets.tabComplete);
        M(AntiPackets.updateSign);
        M(AntiPackets.useEntity);
        M(AntiPackets.vehicleMove);
        M(AntiPackets.advancementInfo);
        M(AntiPackets.animation);
        M(AntiPackets.blockAction);
        M(AntiPackets.blockBreakAnim);
        M(AntiPackets.blockChange);
        M(AntiPackets.camera);
        M(AntiPackets.changeGameState);
        M(AntiPackets.chat);
        M(AntiPackets.chunkData);
        M(AntiPackets.closeWindow);
        M(AntiPackets.collectItem);
        M(AntiPackets.O);
        M(AntiPackets.n);
        M(AntiPackets.Oa);
        M(AntiPackets.Ea);
        M(AntiPackets.HC);
        M(AntiPackets.l);
        M(AntiPackets.sa);
        M(AntiPackets.J);
        M(AntiPackets.a);
        M(AntiPackets.o);
        M(AntiPackets.fa);
        M(AntiPackets.R);
        M(AntiPackets.y);
        M(AntiPackets.wa);
        M(AntiPackets.Ta);
        M(AntiPackets.ha);
        M(AntiPackets.La);
        M(AntiPackets.ba);
        M(AntiPackets.ra);
        M(AntiPackets.Va);
        M(AntiPackets.Qa);
        M(AntiPackets.U);
        M(AntiPackets.s);
        M(AntiPackets.Ba);
        M(AntiPackets.Hb);
        M(AntiPackets.Gc);
        M(AntiPackets.A);
        M(AntiPackets.Da);
        M(AntiPackets.ca);
        M(AntiPackets.Ia);
        M(AntiPackets.Y);
        M(AntiPackets.ia);
        M(AntiPackets.ta);
        M(AntiPackets.Za);
        M(AntiPackets.M);
        M(AntiPackets.P);
        M(AntiPackets.pa);
        M(AntiPackets.t);
        M(AntiPackets.r);
        M(AntiPackets.H);
        M(AntiPackets.Ka);
        M(AntiPackets.E);
        M(AntiPackets.j);
        M(AntiPackets.F);
        M(AntiPackets.c);
        M(AntiPackets.Wa);
        M(AntiPackets.L);
        M(AntiPackets.m);
        M(AntiPackets.Na);
        M(AntiPackets.i);
        M(AntiPackets.d);
        M(AntiPackets.C);
        M(AntiPackets.Ya);
        M(AntiPackets.b);
        M(AntiPackets.Ua);
        M(AntiPackets.S);
        M(AntiPackets.ua);
        M(AntiPackets.oa);
        M(AntiPackets.aa);
        M(AntiPackets.e);
        M(AntiPackets.w);
        M(AntiPackets.W);
        M(AntiPackets.la);
        M(AntiPackets.G);
        M(AntiPackets.x);
        M(AntiPackets.u);
        M(NameChanger.glowskiBroski);
        M(NameChanger.glowskiBroski2B);
        M(NameChanger.glowClient);
        M(NameChanger.theDarkEmperor);
        M(NameChanger.infernales);
        M(NameChanger.lagTyrant);
        M(NameChanger.furleoxnop);
        M(NameChanger.fallsGreen);
        M(AutoLog.totemCount);
        M(AutoLog.totemCount);
        M(CrystalAura.friendDetect);
        M(BlockFinder.range);
        M(BlockFinder.tracer);
        M(BlockFinder.outline);
        M(Dupe.mode);
        M(BoatTravel.phaseSpeed);
        M(Spider.mode);
        M(Spider.speed);
        M(PeekMod.rightClick);
        M(PeekMod.toolTips);
        M(NoSlow.mode);
        M(KickBow.shulkerSlot);
        M(NameChanger.phiPhi);
        M(Nametags.heldItemName);
        M(AutoIgnore.string);
        M(Scaffold.mode);
        M(SourceRemover.range);
        M(SourceRemover.source);
        M(SourceRemover.silent);
        M(BlockFinder.M);
        M(BlockFinder.B);
        M(BlockFinder.L);
        M(MappingBot.direction);
        M(MappingBot.loopVal);
        M(SignMod.colors);
        M(SignMod.data);
        M(SchematicaPrinter.range);
        M(SchematicaPrinter.silent);
        M(SchematicaPrinter.rotationalBlocks);
        M(SchematicaPrinter.delay);
        M(Command.B);
    }
    
    public static Collection<Gc> D() {
        return Collections.unmodifiableCollection((Collection<? extends Gc>)xc.G);
    }
    
    public static void M(final Value value) {
        xc.L.put(value.k(), value);
        xc.A.add(value);
        if (value instanceof BooleanValue) {
            xc.b.add((BooleanValue)value);
        }
        if (value instanceof NumberValue) {
            xc.G.add((NumberValue)value);
        }
        if (value instanceof StringValue) {
            xc.B.add((StringValue)value);
        }
        if (value instanceof nB) {
            xc.d.add((nB)value);
        }
    }
    
    public static Collection<Sc> M() {
        return Collections.unmodifiableCollection((Collection<? extends Sc>)xc.B);
    }
}
