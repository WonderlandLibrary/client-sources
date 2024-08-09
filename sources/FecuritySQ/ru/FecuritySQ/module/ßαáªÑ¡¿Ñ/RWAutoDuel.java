package ru.FecuritySQ.module.сражение;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventMiddleClick;
import ru.FecuritySQ.event.imp.EventPacket;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.utils.Counter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RWAutoDuel extends Module {
    private static final Pattern validUserPattern = Pattern.compile("^\\w{3,16}$");

    String[] modes = { "Классик", "Тотемы", "Незеритка", "Нодебафф", "Читерский рай",  "Шипы 3", "Щит", "Лук", "Шары"};
    public OptionMode mode = new OptionMode("Режим", modes, 0);
    public List<String> sended = new ArrayList<String>();

    Counter counter = new Counter();
    Counter counter2 = new Counter();
    public RWAutoDuel() {
        super(Category.Сражение, GLFW.GLFW_KEY_0);
        addOption(mode);
    }


    @Override
    public void event(Event e) {
       if(!isEnabled()) return;

       if(e instanceof EventUpdate){
           List<String> players = getOnlinePlayers();

           if(counter2.hasTimeElapsed(800 * players.size())){
               sended.clear();
               counter2.reset();
           }
           for(String player : players){
               if(!sended.contains(player)){
                   if(counter.hasTimeElapsed(1000)) {
                       mc.player.sendChatMessage("/duel " + player);
                       sended.add(player);
                       counter.reset();
                   }
               }
           }
       }
       if(e instanceof EventPacket eventPacket){
           if(eventPacket.packet instanceof SChatPacket){
               SChatPacket chat = (SChatPacket)((EventPacket) e).packet;
               String text = chat.getChatComponent().getString().toLowerCase();
               if(text.contains("начало") && text.contains("через") && text.contains("секунд!")){
                   toggle();
               }
           }
       }
    }
    private List<String> getOnlinePlayers() {
        return mc.player.connection.getPlayerInfoMap().stream()
                .map(NetworkPlayerInfo::getGameProfile)
                .map(GameProfile::getName)
                .filter(profileName -> validUserPattern.matcher(profileName).matches())
                .collect(Collectors.toList());
    }
}
