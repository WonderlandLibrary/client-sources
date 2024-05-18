package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.gui.spectator.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final String[] I;
    private final List<ISpectatorMenuObject> field_178672_a;
    
    public TeleportToTeam() {
        this.field_178672_a = (List<ISpectatorMenuObject>)Lists.newArrayList();
        final Iterator<ScorePlayerTeam> iterator = Minecraft.getMinecraft().theWorld.getScoreboard().getTeams().iterator();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.field_178672_a.add(new TeamSelectionObject(iterator.next()));
        }
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText(TeleportToTeam.I["".length()]);
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture("".length(), "".length(), 16.0f, 0.0f, 0x10 ^ 0x0, 0x73 ^ 0x63, 256.0f, 256.0f);
    }
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(TeleportToTeam.I[" ".length()]);
    }
    
    @Override
    public boolean func_178662_A_() {
        final Iterator<ISpectatorMenuObject> iterator = this.field_178672_a.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().func_178662_A_()) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("7\u0001#\u000f.\u0010D.J9\u0001\u0005\"J9\u000bD;\u000f!\u0001\u0014 \u00189D\u0010 ", "ddOjM");
        TeleportToTeam.I[" ".length()] = I("=\u0004\u0014$!\u0006\u0013\fa%\u0006A\f$0\u0004A\u0015$<\u000b\u0004\n", "iaxAQ");
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        spectatorMenu.func_178647_a(this);
    }
    
    @Override
    public List<ISpectatorMenuObject> func_178669_a() {
        return this.field_178672_a;
    }
    
    class TeamSelectionObject implements ISpectatorMenuObject
    {
        final TeleportToTeam this$0;
        private final ResourceLocation field_178677_c;
        private final List<NetworkPlayerInfo> field_178675_d;
        private final ScorePlayerTeam field_178676_b;
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            int colorCode = -" ".length();
            final String formatFromString = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
            if (formatFromString.length() >= "  ".length()) {
                colorCode = Minecraft.getMinecraft().fontRendererObj.getColorCode(formatFromString.charAt(" ".length()));
            }
            if (colorCode >= 0) {
                Gui.drawRect(" ".length(), " ".length(), 0x46 ^ 0x49, 0x46 ^ 0x49, MathHelper.func_180183_b((colorCode >> (0x91 ^ 0x81) & 78 + 124 + 36 + 17) / 255.0f * n, (colorCode >> (0x35 ^ 0x3D) & 80 + 83 - 117 + 209) / 255.0f * n, (colorCode & 212 + 205 - 276 + 114) / 255.0f * n) | n2 << (0x48 ^ 0x50));
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
            GlStateManager.color(n, n, n, n2 / 255.0f);
            Gui.drawScaledCustomSizeModalRect("  ".length(), "  ".length(), 8.0f, 8.0f, 0xBC ^ 0xB4, 0x80 ^ 0x88, 0x3A ^ 0x36, 0x4E ^ 0x42, 64.0f, 64.0f);
            Gui.drawScaledCustomSizeModalRect("  ".length(), "  ".length(), 40.0f, 8.0f, 0x3A ^ 0x32, 0xAF ^ 0xA7, 0x50 ^ 0x5C, 0x94 ^ 0x98, 64.0f, 64.0f);
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
                if (2 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public IChatComponent getSpectatorName() {
            return new ChatComponentText(this.field_178676_b.getTeamName());
        }
        
        @Override
        public boolean func_178662_A_() {
            int n;
            if (this.field_178675_d.isEmpty()) {
                n = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            return n != 0;
        }
        
        public TeamSelectionObject(final TeleportToTeam this$0, final ScorePlayerTeam field_178676_b) {
            this.this$0 = this$0;
            this.field_178676_b = field_178676_b;
            this.field_178675_d = (List<NetworkPlayerInfo>)Lists.newArrayList();
            final Iterator<String> iterator = field_178676_b.getMembershipCollection().iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(iterator.next());
                if (playerInfo != null) {
                    this.field_178675_d.add(playerInfo);
                }
            }
            if (!this.field_178675_d.isEmpty()) {
                final String name = this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size())).getGameProfile().getName();
                AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c = AbstractClientPlayer.getLocationSkin(name), name);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
            }
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            spectatorMenu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
        }
    }
}
