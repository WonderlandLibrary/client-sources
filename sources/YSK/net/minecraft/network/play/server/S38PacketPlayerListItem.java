package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class S38PacketPlayerListItem implements Packet<INetHandlerPlayClient>
{
    private Action action;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
    private final List<AddPlayerData> players;
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.action = packetBuffer.readEnumValue(Action.class);
        final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
        int i = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (i < varIntFromBuffer) {
            GameProfile gameProfile = null;
            int n = "".length();
            WorldSettings.GameType gameType = null;
            IChatComponent chatComponent = null;
            switch ($SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action()[this.action.ordinal()]) {
                case 1: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), packetBuffer.readStringFromBuffer(0x25 ^ 0x35));
                    final int varIntFromBuffer2 = packetBuffer.readVarIntFromBuffer();
                    int j = "".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    while (j < varIntFromBuffer2) {
                        final String stringFromBuffer = packetBuffer.readStringFromBuffer(21476 + 21009 - 34967 + 25249);
                        final String stringFromBuffer2 = packetBuffer.readStringFromBuffer(23949 + 25321 - 45207 + 28704);
                        if (packetBuffer.readBoolean()) {
                            gameProfile.getProperties().put((Object)stringFromBuffer, (Object)new Property(stringFromBuffer, stringFromBuffer2, packetBuffer.readStringFromBuffer(18173 + 10679 - 19257 + 23172)));
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            gameProfile.getProperties().put((Object)stringFromBuffer, (Object)new Property(stringFromBuffer, stringFromBuffer2));
                        }
                        ++j;
                    }
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    n = packetBuffer.readVarIntFromBuffer();
                    if (!packetBuffer.readBoolean()) {
                        break;
                    }
                    chatComponent = packetBuffer.readChatComponent();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    n = packetBuffer.readVarIntFromBuffer();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    if (!packetBuffer.readBoolean()) {
                        break;
                    }
                    chatComponent = packetBuffer.readChatComponent();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    break;
                }
            }
            this.players.add(new AddPlayerData(gameProfile, n, gameType, chatComponent));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\r-\u0007\u0019\n\u0002", "lNspe");
        S38PacketPlayerListItem.I[" ".length()] = I("?\"\r3 ??", "ZLyAI");
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add(S38PacketPlayerListItem.I["".length()], (Object)this.action).add(S38PacketPlayerListItem.I[" ".length()], (Object)this.players).toString();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Action func_179768_b() {
        return this.action;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.players.size());
        final Iterator<AddPlayerData> iterator = this.players.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final AddPlayerData addPlayerData = iterator.next();
            switch ($SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action()[this.action.ordinal()]) {
                case 1: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeString(addPlayerData.getProfile().getName());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getProfile().getProperties().size());
                    final Iterator iterator2 = addPlayerData.getProfile().getProperties().values().iterator();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final Property property = iterator2.next();
                        packetBuffer.writeString(property.getName());
                        packetBuffer.writeString(property.getValue());
                        if (property.hasSignature()) {
                            packetBuffer.writeBoolean(" ".length() != 0);
                            packetBuffer.writeString(property.getSignature());
                            "".length();
                            if (4 < 1) {
                                throw null;
                            }
                            continue;
                        }
                        else {
                            packetBuffer.writeBoolean("".length() != 0);
                        }
                    }
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getGameMode().getID());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getPing());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean("".length() != 0);
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        packetBuffer.writeBoolean(" ".length() != 0);
                        packetBuffer.writeChatComponent(addPlayerData.getDisplayName());
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                        continue;
                    }
                    break;
                }
                case 2: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getGameMode().getID());
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    continue;
                }
                case 3: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getPing());
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    continue;
                }
                case 4: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean("".length() != 0);
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        packetBuffer.writeBoolean(" ".length() != 0);
                        packetBuffer.writeChatComponent(addPlayerData.getDisplayName());
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                        continue;
                    }
                    break;
                }
                case 5: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                }
                default: {
                    continue;
                }
            }
        }
    }
    
    static {
        I();
    }
    
    public S38PacketPlayerListItem() {
        this.players = (List<AddPlayerData>)Lists.newArrayList();
    }
    
    public List<AddPlayerData> func_179767_a() {
        return this.players;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerListItem(this);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action() {
        final int[] $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = S38PacketPlayerListItem.$SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
        if ($switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action != null) {
            return $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action;
        }
        final int[] $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2 = new int[Action.values().length];
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[Action.ADD_PLAYER.ordinal()] = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[Action.REMOVE_PLAYER.ordinal()] = (0x6F ^ 0x6A);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[Action.UPDATE_DISPLAY_NAME.ordinal()] = (0x76 ^ 0x72);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[Action.UPDATE_GAME_MODE.ordinal()] = "  ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2[Action.UPDATE_LATENCY.ordinal()] = "   ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return S38PacketPlayerListItem.$SWITCH_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action = $switch_TABLE$net$minecraft$network$play$server$S38PacketPlayerListItem$Action2;
    }
    
    public S38PacketPlayerListItem(final Action action, final Iterable<EntityPlayerMP> iterable) {
        this.players = (List<AddPlayerData>)Lists.newArrayList();
        this.action = action;
        final Iterator<EntityPlayerMP> iterator = iterable.iterator();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayerMP entityPlayerMP = iterator.next();
            this.players.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.getTabListDisplayName()));
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S38PacketPlayerListItem(final Action action, final EntityPlayerMP... array) {
        this.players = (List<AddPlayerData>)Lists.newArrayList();
        this.action = action;
        final int length = array.length;
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < length) {
            final EntityPlayerMP entityPlayerMP = array[i];
            this.players.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.getTabListDisplayName()));
            ++i;
        }
    }
    
    public enum Action
    {
        UPDATE_GAME_MODE(Action.I[" ".length()], " ".length());
        
        private static final Action[] ENUM$VALUES;
        
        UPDATE_DISPLAY_NAME(Action.I["   ".length()], "   ".length()), 
        UPDATE_LATENCY(Action.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
        ADD_PLAYER(Action.I["".length()], "".length()), 
        REMOVE_PLAYER(Action.I[0x2A ^ 0x2E], 0xB4 ^ 0xB0);
        
        private Action(final String s, final int n) {
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final Action[] enum$VALUES = new Action[0x32 ^ 0x37];
            enum$VALUES["".length()] = Action.ADD_PLAYER;
            enum$VALUES[" ".length()] = Action.UPDATE_GAME_MODE;
            enum$VALUES["  ".length()] = Action.UPDATE_LATENCY;
            enum$VALUES["   ".length()] = Action.UPDATE_DISPLAY_NAME;
            enum$VALUES[0x80 ^ 0x84] = Action.REMOVE_PLAYER;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x4D ^ 0x48])["".length()] = I("\u001251\b\u0012\u001f0,\u0012\u0010", "SquWB");
            Action.I[" ".length()] = I("\u001d3#\u000e\u0005\r< \u000e\u001c\r<*\u0000\u0015\r", "HcgOQ");
            Action.I["  ".length()] = I("\u001d\u0011% ;\r\u001e- ;\r\u000f\"8", "HAaao");
            Action.I["   ".length()] = I("\u0007\u0013\u0010,,\u0017\u001c\u0010$+\u0002\u000f\u00154'\u001c\u0002\u0019(", "RCTmx");
            Action.I[0x66 ^ 0x62] = I("#\u000e/*\u001c4\u00142)\u000b(\u000e0", "qKbeJ");
        }
    }
    
    public class AddPlayerData
    {
        final S38PacketPlayerListItem this$0;
        private final IChatComponent displayName;
        private static final String[] I;
        private final GameProfile profile;
        private final WorldSettings.GameType gamemode;
        private final int ping;
        
        public IChatComponent getDisplayName() {
            return this.displayName;
        }
        
        public AddPlayerData(final S38PacketPlayerListItem this$0, final GameProfile profile, final int ping, final WorldSettings.GameType gamemode, final IChatComponent displayName) {
            this.this$0 = this$0;
            this.profile = profile;
            this.ping = ping;
            this.gamemode = gamemode;
            this.displayName = displayName;
        }
        
        public GameProfile getProfile() {
            return this.profile;
        }
        
        @Override
        public String toString() {
            final Objects.ToStringHelper add = Objects.toStringHelper((Object)this).add(AddPlayerData.I["".length()], this.ping).add(AddPlayerData.I[" ".length()], (Object)this.gamemode).add(AddPlayerData.I["  ".length()], (Object)this.profile);
            final String s = AddPlayerData.I["   ".length()];
            String componentToJson;
            if (this.displayName == null) {
                componentToJson = null;
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else {
                componentToJson = IChatComponent.Serializer.componentToJson(this.displayName);
            }
            return add.add(s, (Object)componentToJson).toString();
        }
        
        static {
            I();
        }
        
        public int getPing() {
            return this.ping;
        }
        
        private static void I() {
            (I = new String[0x7 ^ 0x3])["".length()] = I("\u0005\r<\u0003?\n\u0015", "ilHfQ");
            AddPlayerData.I[" ".length()] = I("$\u000f:\u0016%,\n2", "CnWsh");
            AddPlayerData.I["  ".length()] = I("\n\u001c\f\u0003\u001f\u0016\u000b", "zncev");
            AddPlayerData.I["   ".length()] = I("\u0015/\u001a(\u0005\u0010?'9\u0004\u0014", "qFiXi");
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (false) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public WorldSettings.GameType getGameMode() {
            return this.gamemode;
        }
    }
}
