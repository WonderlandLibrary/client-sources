/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.friends.Friends;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.AutoRobot;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.List;
import java.util.Random;

public final class AutoEz extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public AutoEz() {
        super(Categories.Misc, "auto-ez", "Sends EZ messages lmfao");
    }

    private final Setting<MessageMode> killMsgMode = sgGeneral.add(new EnumSetting.Builder<MessageMode>()
        .name("Kill Message Mode")
        .description("What kind of messages to send.")
        .defaultValue(MessageMode.Custom)
        .build()
    );

    private final Setting<Integer> distance = sgGeneral.add(new IntSetting.Builder()
        .name("distance")
        .description("The distance the people need to be.")
        .defaultValue(25)
        .min(0)
        .sliderMax(400)
        .build()
    );


    private final Setting<Boolean> startMark = sgGeneral.add(new BoolSetting.Builder()
        .name("StartMark")
        .description("Adds an exclamation mark to the start of the messages")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> msg = sgGeneral.add(new BoolSetting.Builder()
        .name("msg")
        .description("Message the player directly instead of sending it in public chat")
        .defaultValue(false)
        .visible(() -> !startMark.get())
        .build()
    );

    private final Setting<List<String>> killMessages = sgGeneral.add(new StringListSetting.Builder()
        .name("Kill Messages")
        .description("Messages to send when killing an enemy with Custom message mode on")
        .defaultValue(List.of("Fucked by NullPointerWare!", "NullPointerWare on top", "NullPointerWare strong", "NullPointerWare gayming"))
        .visible(() -> killMsgMode.get() == MessageMode.Custom)
        .build()
    );

    private final Random r = new Random();
    private int lastNum;

    // credits to exhibition for these messages
    private final String[] exhibobo = new String[]{
        "Wow, you just died in a block game %s",
        "%s died in a block game lmfao.",
        "%s died for using an android device. LOL",
        "%s, your mother is of the homophobic type",
        "That's a #VictoryRoyale!, better luck next time, %s!",
        "%s, used Flux then got backhanded by the face of hypixel",
        "even loolitsalex has more wins then you %s",
        "my grandma plays minecraft better than you %s",
        "%s, you should look into purchasing vape",
        "oops %s I'm so sorry",
        "%s, What's worse your skin or the fact your a casual f3ckin normie",
        "you know what %s, blind gamers deserve a chance too. I support you.",
        "that was a pretty bad move %s",
        "how does it feel to get stomped on %s",
        "%s, do you really like dying this much?",
        "if i had a choice between %s and jake paul, id choose jake paul",
        "hey %s, what does your IQ and kills have in common? They are both low af",
        "Hey %s, want some PvP advice?",
        "wow, you just died in a game about legos",
        "i'm surprised that you were able hit the 'Install' button %s",
        "%s I speak English not your gibberish.",
        "%s Take the L, kid",
        "%s got memed",
        "%s is a default skin!!!1!1!1!1!!1!1",
        "%s You died in a fucking block game",
        "%s likes anime",
        "%s Trash dawg, you barely even hit me.",
        "%s I just fucked him so hard he left the game",
        "%s get bent over and fucked kid",
        "%s couldn't even beat 4 block",
        "Someone get this kid a tissue, %s is about to cry!",
        "%s's dad is bald",
        "%s Your family tree must be a cactus because everybody on it is a prick.",
        "%s You're so fucking trash that the binman mistook you for garbage and collected you in the morning",
        "%s some kids were dropped at birth but you were clearly thrown at a wall",
        "%s go back to your mother's womb you retarded piece of shit",
        "Thanks for the free kill %s !",
        "Benjamin's forehead is bigger than your future Minecraft PvP career %s",
        "%s are you even trying?",
        "%s You. Are. Terrible.",
        "%s my mom is better at this game then you",
        "%s lololololol mad? lololololol",
        "%s /friend me so we can talk about how useless you are",
        "%s: \"Staff! Staff! Help me! I am dogcrap at this game and i am getting rekt!\"",
        "%s Is it really that hard to trace me while i'm hopping around you?",
        "%s, Vape is a cool thing you should look into!",
        "%s I'm not using reach, you just need to click faster.",
        "%s I hope you recorded that, so that you can watch how trash you really are.",
        "%s You have to use the left and right mouse button in this game, in case you forgot.",
        "%s I think that the amount of ping you have equates to your braincells dumbfuck asshat",
        "%s ALT+F4 to remove the problem",
        "%s alt+f4 for hidden perk window",
        "%s You'll eventually switch back to Fortnite again, so why not do it now?",
        "%s go back to fortnite where you belong, you degenerate 5 year old",
        "%s I'll be sure to Orange Justice the fucck out of your corpse",
        "%s Exhibob better than you!1",
        "%s I'm a real gamer, and you just got owned!!",
        "%s Take a taste of your own medicine you clapped closet cheater",
        "%s go drown in your own salt",
        "%s go and suck off prestonplayz, you 7 yr old fanboy",
        "%s how are you so bad. I'm losing brain cells just watching you play",
        "%s Jump down from your school building with a rope around your neck.",
        "%s dominated, monkey :dab:",
        "%s Please add me as a friend so that you can shout at me. I live for it.",
        "%s i fvcked your dad",
        "%s Yeah, I dare you, rage quit. Come on, make us both happy.",
        "%s No, you are not blind! I DID own you!",
        "%s easy 10 hearted L",
        "%s It's almost as if i can hear you squeal from the other side!",
        "%s If you read this, you are confirmed homosexual",
        "%s have you taken a dump lately? Because I just beat the shit of out you.",
        "%s 6 block woman beater",
        "%s feminist demolisher",
        "%s chromosome count doubles the size of this game",
        "a million years of evolution and we get %s",
        "if the body is 70 percent water how is %s 100 percent salt???",
        "%s L",
        "%s got rekt",
        "%s you're so fat that when you had a fire in your house you dialled 999 on the microwave",
        "LMAO %s is a Fluxuser",
        "LMAO %s is a Sigmauser",
        "%s I suffer from these fukking kicks, grow brain lol",
        "LMAO %s a crack user",
        "%s Hypixel thought could stop us from cheating, huh, you are just as delusional as him",
        "%s GET FUCKED IM ON BADLION CLIENT WHORE",
        "%s should ask tene if i was hacking or not",
        "%s check out ARITHMOS CHANNEL",
        "%s gay",
        "%s, please stop",
        "%s, I play fortnite duos with your mom",
        "%s acts hard but %s's dad beats him harder",
        "Lol commit not alive %s",
        "How'd you hit the DOWNLOAD button with that aim? %s",
        "I'd say your aim is cancer, but at least cancer kills people. %s",
        "%s is about as useful as pedals on a wheelchair",
        "%s's aim is now sponsored by Parkinson's!",
        "%s, I'd say uninstall but you'd probably miss that too.",
        "%s, I bet you edate.",
        "%s, you probably watch tenebrous videos and are intruiged",
        "%s Please could you not commit not die kind sir thanks",
        "%s gay",
        "%s you probably suck on door knobs",
        "%s go commit stop breathing u dumb idot",
        "%s go commit to sucking on door knobs",
        "the only way you can improve at pvp %s is by taking a long walk off a short pier",
        "L %s",
        "%s Does not have a good client",
        "%s's client refused to work",
        "%s Stop hacking idiot",
        "%s :potato:",
        "%s go hunt kangaroos fucking aussie ping",
        "%s Super Mario Bros. deathsound",
        "Hey everyone, do /friend add %s , and tell them how trash they are",
        "%s Just do a France 1940, thank you",
        "Hey %s , would you like to hear a joke? Yeah, you ain't getting any",
        "%s got OOFed",
        "You mum your dad the ones you never had %s",
        "%s please be toxic to me, I enjoy it",
        "oof %s",
        "%s knock knock, FBI open up, we saw you searched for cracked vape.",
        "%s plez commit jump out of window for free rank",
        "%s you didn't even stand a chance!",
        "%s keep trying!",
        "%s, you're the type of player to get 3rd place in a 1v1",
        "%s, I'm not saying you're worthless, but I would unplug your life support to charge my phone",
        "I didn't know dying was a special ability %s",
        "%s, Stephen Hawking had better hand-eye coordination than you",
        "%s, kids like you were the inspiration for birth control",
        "%s you're the definition of bane",
        "%s lol GG!!!",
        "%s lol bad client what is it exhibition?",
        "%s L what are you lolitsalex?",
        "%s gg e z kid",
        "%s tene is my favorite youtuber and i bought his badlion client clock so i'm legit",
        "Don't forget to report me %s",
        "Your IQ is that of a Steve %s",
        "%s have you taken a dump lately? Because I just beat the shit of out you.",
        "%s dont ever put bean in my donut again.",
        "%s 2 plus 2 is 4, minus 1 that's your IQ",
        "I think you need vape %s !",
        "%s You just got oneTapped LUL",
        "%s You're the inspiration for birth control",
        "%s I don't understand why condoms weren't named by you.",
        "%s, My blind grandpa has better aim than you.",
        "%s, Exhibob better then you!",
        "%s, u r So E.Z",
        "NullPointerWare > %s",
        "%s, NMSL",
        "%s, your parents abandoned you, then the orphanage did the same",
        "%s,stop using trash client like sigma.",
        "%s, your client is worse than sigma, and that's an achievement",
        "%s, ur fatter than Napoleon",
        "%s please consider not alive",
        "%s, probably bought sigma premium",
        "%s, probably asks for sigma premium keys",
        "%s the type of person to murder someone and apologize saying it was a accident",
        "%s you're the type of person who would quickdrop irl",
        "%s, got an F on the iq test.",
        "Don't forget to report me %s",
        "%s even viv is better than you LMAO",
        "%s your mom gaye",
        "%s I Just Sneezed On Your Forehead",
        "%s your teeth are like stars - golden, and apart.",
        "%s Rose are blue, stars are red, you just got hacked on and now you're dead",
        "%s i don't hack because watchdog is watching so it would ban me anyway.",
        "%s, chill out on the paint bro",
        "%s You got died from the best client in the game, now with Infinite Sprint bypass",
        "%s you're so fat, that your bellybutton reaches your house 20 minutes before you do",
        "%s your dick is so small, that you bang cheerios"
    };


    private final String[] allah= new String[]{
        "Allah is a false \"god\"",
        "Allah commits shirk all day every day",
        "Allah is 3 gods in one",
        "Allah died when mohammad died",
        "Mohammed is burning in Gehenna, and %s will be too",
        "Mohammad married a 6 year old",
        "Mohammad married a 6 year old like you %s",
        "unbelievers go to heaven",
        "Mohammad committed Shirk (Q. 17:18)",
    };

    private final String[] GG = new String[] {
        "GG %s",
        "Well Played %S",
        "You'll get em next time %s",
        "GG WP",
        "Good Game",
        "Sometimes you can't win; unfortunate...",
        "%s Have you hugged your mom today?",
        "Close, close",
        "That was a nail-biter, GG!",
        "Nice try, %s!",
        "Good effort, keep it up!",
        "GG, well-fought %s!",
        "You're pretty good %s",
        "%s you can do better... maybe",
        "I'm disappointed, I now you can do better",
        "If you wish to defeat me, you'll have to train for another 100 years - Technoblade",
        "GG"
    };

    private final String[] GGPT = new String[] {
        "GG %s",
        "Well Played %s",
        "You'll get 'em next time, %s",
        "GG WP",
        "Good Game!",
        "Sometimes you can't win; unfortunate...",
        "%s, have you hugged your mom today?",
        "A valiant effort, %s!",
        "That was a nail-biter, GG!",
        "Nice try, %s!",
        "Good effort, keep it up!",
        "GG, well-fought %s!",
        "In the game of life, you're a winner, %s!",
        "Excellent match, %s!",
        "Victory or defeat, always a good game with you, %s.",
        "Better luck next time, %s!",
        "You're a gaming legend, %s!",
        "Top-notch gameplay, GG!",
        "Skillful moves, %s!",
        "Good sportsmanship, %s!",
        "Keep the positive vibes, GG!",
        "GG, you're a gaming maestro, %s!",
        "Fortune favors the bold, %s!",
        "Epic battle, %s! GG!",
        "You're a force to be reckoned with, GG %s!",
        "The game may end, but the memories of this match will last, %s.",
        "Win or lose, always a pleasure playing with you, %s!",
        "GG, the journey is just as important as the destination, %s.",
        "Great match, %s! Let's play again soon.",
        "You make every game exciting, GG %s!",
        "Winning or learning? Either way, GG %s!",
        "That was a rollercoaster of emotions, GG!",
        "Skill, strategy, and sportsmanship – you've got it all, %s!",
        "Thanks for the game, %s! GG!",
        "You're a gaming virtuoso, %s!",
        "Your gameplay is a work of art, GG!",
        "No matter the outcome, your effort is commendable, %s!",
        "The battlefield is your canvas, %s! GG!",
        "Victory dances or defeat reflections? GG %s!",
        "Every game with you is a masterpiece, GG!",
        "May your next game be as thrilling as this one, %s!",
        "In the grand tapestry of gaming, your moves stand out, GG %s!"
    };


    private final String[] noclue = new String[]{
        "Yeah, these niggas say, I’ll catch a foul, what do they know?",
        "Just tryna score a point in the end zone",
        "Didn’t ask your opinion, nigga who the fuck are you?",
        "Hand on my choppa, I'll turn you to pasta linguini, Yeah okay",
        "Niggas be hostile, I know that they just wanna be me, in my lane",
        "Vibin' with the gang, smoking gas in the coupe",
        "Had to drop her and she had no clue",
        "Run up, you done up, your ass gon' get toast",
        "All you niggas on lame shit",
        "Your bitch come back to my place, I do the most",
        "Break their net, with the rolex, that two-tone",
        "Mobbin' with a thick bitch, might be a redbone",
    };


    private final String[] lattiaClientOG = {
        "Get good %s",
        "Lattia owns you, %s",
        "Please learn to play %s",
        "Lattia Client on TOP! %s got REKT!",
        "Lattia Client just rekt %s!",
        "Lattia Client is the best client, %s didn't get the memo",
        "Have you considered using Lattia Client?"
    };

    private final String[] lattiaClient = new String[]{
        "Get good %s",
        "Please learn to play %s",
        "Lattia owns you, %s",
        "If %s had a dollar for every time Lattia Client owned them, they'd be broke",
        "%s tried to outsmart Lattia Client. Key word: tried.",
        "Rumor has it, Lattia Client is the reason %s uninstalled the game.",
        "Lattia Client: because %s needed a reality check!",
        "Roses are red, violets are blue, Lattia Client wrecked you too",
        "Did you hear about %s? Lattia Client just turned them into a red spot on the ground!",
        "In a parallel universe, %s won against Lattia Client. But we don't live there.",
        "Scientists are studying how %s's ego survived the encounter with Lattia Client.",
        "They say laughter is the best medicine. Too bad it can't heal %s from the trauma of facing Lattia Client.",
        "It's not %s's fault; it's just that Lattia Client is operating on a different level of being.",
        "%s thought they could outrun Lattia Client. They were wrong. Very, very wrong.",
        "They say practice makes perfect. Lattia Client must practice 24/7, judging by %s's record.",
        "Quick question, %s: Ever wonder what winning feels like? Lattia Client doesn't.",
        "%s was floored by Lattia Client",
        "%s's game plan: Lose to Lattia Client and repeat.",
        "%s thought they were the protagonist. Then Lattia Client showed up.",
        "If %s's gameplay was a movie, Lattia Client would be the unexpected plot twist.",
        "They say lightning never strikes the same place twice. Lattia Client does, though.",
        "When it comes to gaming, Lattia Client has the competition floored",
        "%s thought they were a gaming prodigy, then Lattia Client flew in.",
        "Have you considered using Lattia Client?",
        "Fun fact: Lattia Client has a black belt in beating %s.",
        "Lattia Client is the best client, %s didn't get the memo",
        "Lattia Client: 1, %s: 0.",
        "Lattia Client: Killing Kids since 2021",
        "Lattia Client: Making players question their life choices since 2021",
        "Lattia Client on TOP! %s got REKT!",
        "Lattia Client just rekt %s!",
        "Lattia Client: Making %s question whether respawning is worth the emotional toll.",
        "Lattia Client: %s's reality check specialist, available 24/7.",
        "Lattia Client: Where %s's dreams of victory go to retire.",
        "Lattia Client: Because beating %s once just wasn't enough.",
        "Lattia Client just gave %s a VIP ticket to the respawn show.",
        "Lattia Client's favorite game? Collecting victories against %s.",
        "Lattia Client: Where victory is not just a possibility; it's a guarantee.",
        "Lattia Client doesn't break a sweat; it breaks %s's winstreaks.",
        "Lattia Client: Making kids feel grounded since 2021."
    };



    @Override
    public void onActivate() {
        super.onActivate();
        lastNum = -1;
    }

    @Override
    public String getInfoString() {
        return killMsgMode.get().name();
    }

    @EventHandler
    private void onReceive(final PacketEvent.Receive event) {
        if (event.packet instanceof EntityStatusS2CPacket packet) {
            // Pop
            if (packet.getStatus() == 3 && packet.getEntity(mc.world) != null) {
                Entity entity = packet.getEntity(mc.world);

                if (entity.distanceTo(mc.player) > distance.get())
                    return;

                if (mc.player != null && mc.world != null && entity instanceof PlayerEntity) {
                    if (entity != mc.player && !Friends.get().isFriend((PlayerEntity) entity)) {
                        sendKillMessage(entity.getEntityName());
                        if (Modules.get().get(AutoRobot.class).isActive()) Modules.get().get(AutoRobot.class).ing = false;
                    }
                }
            }
        }
    }

    private void sendKillMessage(String name) {
        if (isNull())
            return;
        switch (killMsgMode.get()) {
            case Exhibition -> {
                int num = r.nextInt(0, exhibobo.length);
                if (num == lastNum) {
                    num = num < exhibobo.length - 1 ? num + 1 : 0;
                }
                lastNum = num;

                String message = exhibobo[num].replace("%s", name == null ? "You" : name);

                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);

            }

            case LattiaClient -> {
                int num = r.nextInt(0, lattiaClient.length);
                if (num == lastNum) {
                    num = num < lattiaClient.length - 1 ? num + 1 : 0;
                }
                lastNum = num;
                String message = lattiaClient[num].replace("%s", name == null ? "You" : name);

                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);
            }

            case Custom -> {
                if (!killMessages.get().isEmpty()) {
                    int num = r.nextInt(0, killMessages.get().size());
                    if (num == lastNum) num = num < killMessages.get().size() - 1 ? num + 1 : 0;

                    lastNum = num;
                    String message = killMessages.get().get(num).replace("<PLAYER>", name == null ? "You" : name);

                    if (startMark.get())
                        message = "!" + message;

                    if (msg.get())
                        message = "/msg " + name + " " + message;

                    ChatUtils.sendPlayerMsg(message);

                }
            }
            case NoClue -> {
                int num = r.nextInt(0, noclue.length);
                if (num == lastNum) {
                    num = num < noclue.length - 1 ? num + 1 : 0;
                }
                lastNum = num;
                String message = noclue[num].replace("%s", name == null ? "You" : name);
                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);
            }
            case Allah -> {
                int num = r.nextInt(0, allah.length);
                if (num == lastNum) {
                    num = num < noclue.length - 1 ? num + 1 : 0;
                }
                lastNum = num;
                String message = allah[num].replace("%s", name == null ? "You" : name);
                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);
            }
            case GG -> {
                int num = r.nextInt(0, GG.length);
                if (num == lastNum) {
                    num = num < noclue.length - 1 ? num + 1 : 0;
                }
                lastNum = num;
                String message = (GG[num].replace("%s", name == null ? "You" : name));
                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);

            }
            case GGPT -> {
                int num = r.nextInt(0, GGPT.length);
                if (num == lastNum) {
                    num = num < noclue.length - 1 ? num + 1 : 0;
                }
                lastNum = num;
                String message = (GGPT[num].replace("%s", name == null ? "You" : name));
                if (startMark.get())
                    message = "!" + message;

                if (msg.get())
                    message = "/msg " + name + " " + message;

                ChatUtils.sendPlayerMsg(message);

            }
        }
    }

    public enum MessageMode {
        Custom,
        LattiaClient,
        Exhibition,
        NoClue,
        Allah,
        GG,
        GGPT
    }
}

