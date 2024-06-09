/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"modules", "mods", "hacks", "hax"})
public class Modules
extends Command {
    @Override
    public void runCommand(String[] args) {
        ClientUtils.sendMessage("COMBAT: Aura, AutoPotion, AutoSoup, NoVelocity, Criticals, Reach, AutoClicker, BowAimbot, Regen, Triggerbot. MOVEMENT: HypixelFly, Blink, FastLadder, LongJump, Jesus, Noclip, Nofall, NoSlowdown, Speed, Sprint, Glide, Step, Fly, JanitorBhop, JanitorSpeed, BunnyHop, NCPhase, Scaffold, Safewalk, NoWeb, AntiWater, VanillaPhase. PLAYER: AntiBot, BedFucker, ChestStealer, FastPlace, FastUse, Damage, Tower, Phase. RENDER: AntiEffects, ESP, Freecam, Brightness, Gui, Hud, NameProtect, NameTags, NoRender, StorageEsp, Tracers, ViewClip, NoFov, SowordTransition, Xray, Troll, ESP 2D, HideBlocking, Tags. MISC: Commands, InventoryCleaner, ScreenWalk, Middle Click Friendl, NoRotate, Respawn, NewChat, MoreInventory, Derp, ban, WorldTime.");
    }
}

