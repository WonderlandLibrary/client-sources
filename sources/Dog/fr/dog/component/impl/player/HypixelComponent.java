package fr.dog.component.impl.player;

import fr.dog.component.Component;
import fr.dog.util.packet.RequestUtil;
import lombok.SneakyThrows;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.HypixelPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPartyInfoPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPingPacket;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPlayerInfoPacket;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.function.Predicate;

public class HypixelComponent extends Component {



    public static ArrayList<UUID> known_cheaters_or_alts = new ArrayList<>();
    public static String HYPIXEL_APIKEY = "";
    public static HashMap<UUID, String> namecache = new HashMap<>();

    public static Map<UUID, ClientboundPartyInfoPacket.PartyMember> partyHashMap = null;

    @SneakyThrows
    public static void loadCheaters(){
        known_cheaters_or_alts.clear();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL("https://legitclient.com/getCheaterList").openConnection().getInputStream()));

        String inputLine;
        StringBuilder requestResult = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null)
            requestResult.append(inputLine);

        bufferedReader.close();

        System.out.println(requestResult.toString());
        for(String a : requestResult.toString().split(" ")){
            try {
                // stackoverflow code
                String bg =  a.replaceFirst( "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5" );
                known_cheaters_or_alts.add(UUID.fromString(bg));
            }catch (Exception ignored){
            }
        }
    }



    public static void initHypixelAPI(){

        HypixelModAPI.getInstance().registerHandler(ClientboundPartyInfoPacket.class, packet -> {
            partyHashMap = packet.getMemberMap();
        });
        HypixelModAPI.getInstance().registerHandler(ClientboundLocationPacket.class, packet -> {
            ;
        });
    }




}
