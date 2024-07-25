package club.bluezenith.module.modules.fun;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.preferences.DataHandler;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AuraTargetKilledEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.module.value.types.StringValue;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;

import java.io.BufferedReader;
import java.util.List;

import static club.bluezenith.util.math.MathUtil.getRandomInt;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class KillSults extends Module implements DataHandler { //todo recode thx

    public static List<String> insults = Lists.newArrayList();
    ModeValue mode = new ModeValue("Mode", "Custom", "Custom", "Random IP").setIndex(-1);
    StringValue additionalString = new StringValue("Message", "").setIndex(1).showIf(() -> mode.is("Random IP"));
    BooleanValue value = new BooleanValue("Anti-Spam", false).setIndex(2);
    IntegerValue delay = new IntegerValue("Char amount", 4, 1, 10, 1).showIf(() -> value.get()).setIndex(3);
    public KillSults() {
        super("Killsults", ModuleCategory.FUN);
    }

    @Listener
    public void onKill(AuraTargetKilledEvent event) {
        if(event.target == null || insults.isEmpty() || !(event.target instanceof EntityPlayer)) return;
        switch (mode.get()) {
            case "Custom":
            final int random = getRandomInt(-1, insults.size());
            String the = insults.get(random);
            if (value.get())
                the = "[" + randomAlphanumeric(delay.get()) + "] " + the;
            mc.thePlayer.sendChatMessage(the.replaceAll("\\$d", ((EntityPlayer) event.target).getGameProfile().getName()));
            break;

            case "Random IP":
                final StringBuilder builder = new StringBuilder();
                for(int i = 0; i < 4; i++) {
                     builder.append(getRandomInt(50, 255));
                     if(i != 3) builder.append("\u037A");
                }
                String killsult = "";
                if(value.get()) {
                    killsult = "[" + randomAlphanumeric(delay.get()) + "] ";
                }
                if(!additionalString.get().isEmpty())
                    killsult += additionalString.get() + " ";
                killsult += builder.toString();
                killsult = killsult.replaceAll("\\$d", ((EntityPlayer) event.target).getGameProfile().getName());
                mc.thePlayer.sendChatMessage(killsult);
            break;
        }
    }

    @Override
    public void deserialize() {
        if(!BlueZenith.getBlueZenith().getResourceRepository().createFileInDirectory("killsults.txt", false)) return;
        String line;
        BufferedReader reader = BlueZenith.getBlueZenith().getResourceRepository().getReaderForFile("killsults.txt");
        try {
            while ((line = reader.readLine()) != null)
                insults.add(line);
        } catch (Exception exception){}
    }

    @Override
    public void serialize() {
        if(!BlueZenith.getBlueZenith().getResourceRepository().fileExists("killsults.txt")) return;
        BlueZenith.getBlueZenith().getResourceRepository().deleteFile("killsults.txt");
        final StringBuilder builder = new StringBuilder();
        insults.forEach(l -> builder.append(l).append("\n"));
        BlueZenith.getBlueZenith().getResourceRepository().writeToFile("killsults.txt", builder.toString());
    }
}
