package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Math.RandomUtil;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.util.Random;

public class KillSults extends Module {

    public static Mode <Enum> mode = new Mode("Mode", "Mode", KillSultsMode.values(), KillSultsMode.Mineplex);
    public static Mode <Enum> mode2 = new Mode("Message Mode", "Message Mode", KillSultsMessage.values(), KillSultsMessage.UwUWare);

    public Option<Boolean> fitterBypass = new Option ( "Fitter Bypass", "Fitter Bypass", true );

    String[] uwuwareKillSultsMessage = {
            "I'm not a hacker, I just using uwuware pvp client"
            ,"oops, i aciddently killed you with uwuware! the best \"legit\" pvp client on the platform"
            ,"uwuware on top!!",
            "report me!! please so i can sex with your mum more!!!!!!"
    };
    String[] sigmaKillSultsMessage = {
            "Download Sigma to kick ass while listening to some badass music!",
            "Why Sigma? Cause it is the addition of pure skill and incredible intellectual abilities",
            "Want some skills? Check out sigma client.Info!",
            "You have been oofed by Sigma oof oof",
            "I am not racist, but I only like Sigma users. so git gut noobs",
            "Quick Quiz: I am zeus's son, who am I? SIGMA",
            "Wow! My combo is Sigma'n!",
            "What should I choose? Sigma or Sigma?",
            "Bigmama and Sigmama",
            "I don't hack I just sigma",
            "Sigma client . Info is your new home",
            "Look a divinity! He definitely must use sigma!",
            "In need of a cute present for Christmas? Sigma is all you need!",
            "I have a good sigma config, don't blame me",
            "Don't piss me off or you will discover the true power of Sigma's inf reach",
            "Sigma never dies",
            "Maybe I will be Sigma, I am already Sigma",
            "Sigma will help you! Oops, i killed you instead.NoHaxJustSigma",
            "Do like Tenebrous, subscribe to NotThatUwU!",
            "Did I really just forget that melody? Si sig sig sig Sigma",
            "Sigma. The only client run by speakers of Breton",
            "Order free baguettes with Sigma client",
            "Another Sigma user? Awww man",
            "Sigma utility client no hax 100%",
            "Hypixel wants to know Sigma owner's location [Accept] [Deny]",
            "I am a sig-magician, thats how I am able to do all those block game tricks",
            "Stop it, get some help! Get Sigma",
            "Sigma users belike: Hit or miss I guess I never miss!",
            "I dont hack i just have Sigma Gaming Chair",
            "Stop Hackustation me cuz im just Sigma",
            "S. I. G. M. A. Hack with me today!",
            "Subscribe to NotThatUwU on youtube and discover Jello for Sigma!",
            "Beauty is not in the face; beauty is in Jello for Sigma",
            "Imagine using anything but Sigma",
            "No hax just beta testing the anti-cheat with Sigma",
            "Don't forget to report me for Sigma on the forums!",
            "Search sigmaclient , info to get the best mineman skills!",
            "don't use Sigma? ok boomer",
            "Sigma is better than Optifine",
            "It's not Scaffold it's BlockFly in Jello for Sigma!",
            "How come a noob like you not use Sigma?",
            "A mother becomes a true grandmother the day she gets Sigma 5.0",
            "Fly faster than light, only available in Sigmaâ„¢",
            "Behind every Sigma user, is an incredibly cool human being. Trust me, cooler than you.",
            "Hello Sigma my old friend...#SwitchToSigma5",
            "What? You've never downloaded Jello for Sigma? You know it's the best right?",
            "Your client sucks, just get Sigma",
            "Sigma made this world a better place, killing you with it even more"
        };

    public KillSults() {
        super("KillSults", new String[]{"KillSay", "KillSults"}, ModuleType.Misc);
        this.addValues ( mode, mode2, fitterBypass );
        this.setColor(new Color (159, 190, 192).getRGB());
    }

    @EventHandler
    public void onPacketReceive(EventPacketReceive event) {
        if (event.getPacket () instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket ();
            String message = packet.getChatComponent ().getUnformattedText ();

            setSuffix ( mode.getModeAsString () );
            switch (mode.getModeAsString ()) {
                case "Mccentral":
                    if (message.contains ( Minecraft.thePlayer.getName () ) && message.contains ( "Has Killed" )) {
                        KillSults ();
                    }

                case "Hypixel":
                    if (message.contains ( Minecraft.thePlayer.getName () ) && message.contains ( "was Killed by" )) {
                        KillSults ();
                    }
                case "Mineplex":
                    if (message.contains ( Minecraft.thePlayer.getName () ) && (message.contains ( "killed by" ) || message.contains ( "thrown into the void" ))) {
                        KillSults ();
                    }
                case "Redesky":
                    if (message.contains ( Minecraft.thePlayer.getName () ) && (message.contains ( "foi morto por" ) || message.contains ( "thrown into the void" ))) {
                        KillSults ();
                    }
            }
        }
    }

    public void KillSults() {
        int randomIndex1 = ThreadLocalRandom.current().nextInt(0, uwuwareKillSultsMessage.length);
        int randomIndex2 = ThreadLocalRandom.current().nextInt(0, sigmaKillSultsMessage.length);

        if (mode2.getModeAsString().equalsIgnoreCase("UwUWare")) {
            Minecraft.thePlayer.sendChatMessage(uwuwareKillSultsMessage[randomIndex1] + (fitterBypass.getValue() ? " ( " + randomString(10) + " ) " : ""));
        }else if(mode2.getModeAsString().equalsIgnoreCase("Sigma")){
            Minecraft.thePlayer.sendChatMessage(sigmaKillSultsMessage[randomIndex2] + (fitterBypass.getValue() ? " ( " + randomString(10) + " ) " : ""));
        }
    }

    private static final Random RANDOM = new Random();

    public static String randomString(int length) {
        String string = "";
        String abc = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        for (int i = 0; i < length; i++) {
            int randomat = random(0, abc.length());
            string = string + abc.charAt(randomat);
        }
        return string;
    }

    public static int random(int min, int max) {
        if (max <= min) return min;

        return RANDOM.nextInt(max - min) + min;
    }

    public enum KillSultsMessage{
        UwUWare, Sigma
    }
    public enum KillSultsMode{
        Mineplex,Hypixel,Mccentral , Redesky
    }
}



