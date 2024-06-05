/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.TickEvent;
import cc.diablo.event.impl.WorldLoadEvent;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class StaffDetector
extends Module {
    private final List<String> verusKnownStaff = Arrays.asList("0ayt", "0hHaze", "0hMustafa", "0StefanSalvatore", "0Taha", "0YH_", "1A7mad1", "1A7MD_PvP", "1Ab0oDx", "1Ahmd", "1ASSASSINATION_", "1ayt", "1Brhom", "1Cloud_", "1Daykel", "1ELY_", "1F5aMH___3oo", "1HeyImHasson_", "1Hussain", "1LaB", "1Levaai", "1Lweez", "1M0ha", "1M7mdz", "1M7mmD", "1mAhMeeD", "1MasterOogway", "1Mshari", "1Neres", "1RealFadi", "1Selver", "1Sinqx", "1Sweet", "1Terix", "1Tz3bo", "1xM7moD_", "1_Nr", "2gfs", "38eD", "39be", "3AmOdi_", "3Mmr", "420m2tt", "420syr1a", "502x", "502z", "5aQxx", "7bx_", "7sO", "90fa", "a4lf", "a7madx7craft", "a7mm", "ABBGEEN", "AbduIlah", "Abdulaziz187", "Aboz3bl", "AbuA7md506", "AdamViP_", "AG_99", "Ahmed_1b", "Ahmmd", "Aiiden", "AlmondMilky", "AngelRana", "Aparamillos", "arbawii", "AssassinTime", "aXav", "AYm13579", "Ayrinea", "b3ed", "Ba6ee5man", "baderr", "BaSiL_123", "Bastic", "BinRashood", "Bl2ck", "Bl2q", "BlackOurs", "Blood_Artz", "bodi66699", "ByNEK", "cKoro", "comsterr", "Creegam", "Crlexs", "dazaiscatgirl", "DetectiveDnxx", "DetectiveFoxTY", "DetectiveMuhamed", "Dizibre", "Dqrkfall", "ebararh", "Eissaa", "EmirhanBoss", "Enorm1ty", "Enormities", "EthqnInMC", "Ev2n", "EyesO_Diamond", "Faisaal", "FANOR_SY", "FaRidok", "FastRank", "FernandoEscobar", "FireLigtning", "FlyMeToMoon", "Foxy__Boy", "Frqosh", "GzazhMan", "HeWantMeee", "hlla", "i7_e", "IamCsO", "iDhoom", "idpc", "iEvlect", "iikimo", "iikuya", "ilybb0", "iMehdi_", "ImM7MAD", "ImortalM3x", "iS3od_", "iSlo0oMx2", "Its_Qassem", "ItzFHD", "iTz_AbODe2", "Ix20", "ixBander", "Jinaaan", "judeh_gamer", "JustGetOutDude", "JustKreem", "KaaReeeM", "KingHOYT", "Laeria", "Le3b0ody", "leeleeeleeeleee", "lt1x", "Lunching", "Lwzi", "M4rwaan", "m6m6_", "m7mdxjw", "M7mmd", "M7_m", "martadella", "MastersLouis__", "MayBe1ForAGer", "MeAndOnlyMee", "MezzBek", "Mieeteer", "MightyM7MD", "MightySaeed", "MK_F16", "Mlazm_", "mokgii", "MrProfessor_T", "MShkLJe", "Mt2b", "Muhameed", "Mvhammed", "mzh", "N15_", "N29r", "NaRqC", "nejem", "nickdimos", "NotMissYou_", "Nwwf", "oBIXT", "OnlyMoHqMeD__", "oq_ba", "pauseflow", "PerfectRod_", "policewomen", "PotatoSoublaki", "puffingstardust", "qMoha2nd", "Raceth", "RADVN", "Rayleiigh", "resci", "rjassassin", "rkqx", "rr2ot", "Rvgeraz", "S3mm", "S3rvox", "Sarout", "SenpaiAhmed", "Severity_", "SheWantMeee", "SKZ96", "Sp0tzy_", "SpecialAmeer", "Stay1High", "StrengthSimp", "TheBlackTime", "TheDaddyRank", "Tibbz_BGamer", "Tostiebramkaas", "UmmDrRep", "UmmIvy", "Violeet", "Vrqq", "VTomas", "vxom", "V_Death_V", "w7r", "WalriderTime", "wl3d", "xA7md_7rb", "xBeshoo", "xIBerryPlayz", "xiiNinja", "xiiRadi", "xImTaiG_", "xL2d", "xMz7", "xx1k", "xZomik", "Y2serr", "yff3", "yosife_7Y", "YouwantMeThis", "yQuack", "ZANAD", "zFlokenzVEVO", "_Cignus_", "_JustMix", "_N3", "_Skofy", "_Surfers_");
    private final ArrayList<String> staffInMatch = new ArrayList();
    private int amount;

    public StaffDetector() {
        super("Staff Detector", "Detects when staff are in your game (BlocksMC)", 0, Category.Misc);
    }

    @Subscribe
    public void onWorldChange(WorldLoadEvent e) {
        this.staffInMatch.clear();
        this.amount = 0;
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (StaffDetector.mc.thePlayer.ticksExisted > 5) {
            for (EntityPlayer player : Minecraft.theWorld.playerEntities) {
                if (this.staffInMatch.contains(player.getName())) continue;
                for (String staff : this.verusKnownStaff) {
                    if (!player.getName().contains(staff)) continue;
                    this.staffInMatch.add(player.getName());
                    this.amount = this.staffInMatch.size();
                    ChatHelper.addChat(staff + " \u00a7fis in your game!");
                }
            }
        } else {
            this.staffInMatch.clear();
            this.amount = 0;
        }
    }
}

