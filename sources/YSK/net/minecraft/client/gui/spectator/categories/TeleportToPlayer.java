package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.network.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.client.gui.spectator.*;
import java.util.*;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private final List<ISpectatorMenuObject> field_178673_b;
    private static final Ordering<NetworkPlayerInfo> field_178674_a;
    private static final String[] I;
    
    static {
        I();
        field_178674_a = Ordering.from((Comparator)new Comparator<NetworkPlayerInfo>() {
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
            }
            
            @Override
            public int compare(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
                return ComparisonChain.start().compare((Comparable)networkPlayerInfo.getGameProfile().getId(), (Comparable)networkPlayerInfo2.getGameProfile().getId()).result();
            }
        });
    }
    
    public TeleportToPlayer() {
        this(TeleportToPlayer.field_178674_a.sortedCopy((Iterable)Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()));
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText(TeleportToPlayer.I["".length()]);
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        spectatorMenu.func_178647_a(this);
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture("".length(), "".length(), 0.0f, 0.0f, 0x77 ^ 0x67, 0x30 ^ 0x20, 256.0f, 256.0f);
    }
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(TeleportToPlayer.I[" ".length()]);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0007-#&( h.c;8)6&9t< c?1$*3$&<o7$", "THOCK");
        TeleportToPlayer.I[" ".length()] = I("!'\u001f=)\u001a0\u0007x-\u001ab\u000348\f'\u0001", "uBsXY");
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean func_178662_A_() {
        int n;
        if (this.field_178673_b.isEmpty()) {
            n = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public List<ISpectatorMenuObject> func_178669_a() {
        return this.field_178673_b;
    }
    
    public TeleportToPlayer(final Collection<NetworkPlayerInfo> collection) {
        this.field_178673_b = (List<ISpectatorMenuObject>)Lists.newArrayList();
        final Iterator<NetworkPlayerInfo> iterator = TeleportToPlayer.field_178674_a.sortedCopy((Iterable)collection).iterator();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final NetworkPlayerInfo networkPlayerInfo = iterator.next();
            if (networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                this.field_178673_b.add(new PlayerMenuObject(networkPlayerInfo.getGameProfile()));
            }
        }
    }
}
