package com.client.glowclient.modules.server;

import com.client.glowclient.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.client.glowclient.*;
import java.util.regex.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class FontChat extends ModuleContainer
{
    private String c;
    private Boolean k;
    private static final String H = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int[][] f;
    private String M;
    private Boolean G;
    private String d;
    public String L;
    private Boolean A;
    private int B;
    private static final String[] b;
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        if (eventClientPacket.getPacket() instanceof CPacketChatMessage && !pc.M((Packet)eventClientPacket.getPacket())) {
            this.M = ((CPacketChatMessage)eventClientPacket.getPacket()).getMessage();
            int b;
            int i = b = 0;
            while (i < FontChat.b.length) {
                if (this.L.toUpperCase().equals(FontChat.b[b])) {
                    this.B = b;
                }
                i = ++b;
            }
            FontChat fontChat;
            if (this.M.startsWith("/r ")) {
                final boolean b2 = false;
                fontChat = this;
                final boolean b3 = true;
                this.d = this.M.substring(3);
                this.k = b3;
                this.G = b2;
            }
            else if (this.M.startsWith("/pm ")) {
                final Matcher matcher;
                if ((matcher = Pattern.compile("\\s\\w*\\s").matcher(this.M)).find()) {
                    this.c = this.M.substring(matcher.start(), matcher.end()).trim();
                }
                fontChat = this;
                final boolean b4 = false;
                final boolean b5 = false;
                final boolean b6 = true;
                this.d = this.M.replace(new StringBuilder().insert(0, "/pm ").append(this.c).append(" ").toString(), "");
                this.G = b6;
                this.k = b5;
                this.A = b4;
            }
            else if (this.M.startsWith("/ignore")) {
                this.A = true;
                final boolean b7 = false;
                fontChat = this;
                this.k = false;
                this.G = b7;
            }
            else {
                this.d = this.M;
                final boolean b8 = false;
                fontChat = this;
                final boolean b9 = false;
                this.k = b9;
                this.G = b9;
                this.A = b8;
            }
            final char[] charArray = fontChat.d.toCharArray();
            int n;
            int j = n = 0;
            while (j < this.d.toCharArray().length) {
                if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".indexOf(this.d.charAt(n)) != -1 && this.d.toCharArray()[n] != '>') {
                    charArray[n] = (char)FontChat.f[this.B]["ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".indexOf(this.d.charAt(n))];
                }
                else {
                    final char[] array = charArray;
                    final char[] charArray2 = this.d.toCharArray();
                    final int n2 = n;
                    array[n2] = charArray2[n2];
                }
                j = ++n;
            }
            String s = new String(charArray);
            if (this.k) {
                s = new StringBuilder().insert(0, "/r ").append(s).toString();
            }
            else if (this.G) {
                s = new StringBuilder().insert(0, "/pm ").append(this.c).append(" ").append(s).toString();
            }
            else if (this.A) {
                s = this.M;
            }
            final CPacketChatMessage cPacketChatMessage = new CPacketChatMessage(s);
            final boolean canceled = true;
            pc.M((Packet)cPacketChatMessage);
            Ob.M().sendPacket((Packet)cPacketChatMessage);
            eventClientPacket.setCanceled(canceled);
        }
    }
    
    static {
        b = new String[] { "FULL", "CIRCLE", "PARENTHESES", "SMALL" };
        final int[][] f2 = new int[4][];
        final int[] array = new int[62];
        final int n = 0;
        array[n] = 65313;
        array[1] = 65314;
        array[2] = 65315;
        array[3] = 65316;
        array[4] = 65317;
        array[5] = 65318;
        array[6] = 65319;
        array[7] = 65320;
        array[8] = 65321;
        array[9] = 65322;
        array[10] = 65323;
        array[11] = 65324;
        array[12] = 65325;
        array[13] = 65326;
        array[14] = 65327;
        array[15] = 65328;
        array[16] = 65329;
        array[17] = 65330;
        array[18] = 65331;
        array[19] = 65332;
        array[20] = 65333;
        array[21] = 65334;
        array[22] = 65335;
        array[23] = 65336;
        array[24] = 65337;
        array[25] = 65338;
        array[26] = 65345;
        array[27] = 65346;
        array[28] = 65347;
        array[29] = 65348;
        array[30] = 65349;
        array[31] = 65350;
        array[32] = 65351;
        array[33] = 65352;
        array[34] = 65353;
        array[35] = 65354;
        array[36] = 65355;
        array[37] = 65356;
        array[38] = 65357;
        array[39] = 65358;
        array[40] = 65359;
        array[41] = 65360;
        array[42] = 65361;
        array[43] = 65362;
        array[44] = 65363;
        array[45] = 65364;
        array[46] = 65365;
        array[47] = 65366;
        array[48] = 65367;
        array[49] = 65368;
        array[50] = 65369;
        array[51] = 65370;
        array[52] = 65296;
        array[53] = 65297;
        array[54] = 65298;
        array[55] = 65299;
        array[56] = 65300;
        array[57] = 65301;
        array[58] = 65302;
        array[59] = 65303;
        array[60] = 65304;
        array[61] = 65305;
        f2[n] = array;
        final int[] array2 = new int[62];
        array2[0] = 9398;
        final int n2 = 1;
        array2[n2] = 9399;
        array2[2] = 9400;
        array2[3] = 9401;
        array2[4] = 9402;
        array2[5] = 9403;
        array2[6] = 9404;
        array2[7] = 9405;
        array2[8] = 9406;
        array2[9] = 9407;
        array2[10] = 9408;
        array2[11] = 9409;
        array2[12] = 9410;
        array2[13] = 9411;
        array2[14] = 9412;
        array2[15] = 9413;
        array2[16] = 9414;
        array2[17] = 9415;
        array2[18] = 9416;
        array2[19] = 9417;
        array2[20] = 9418;
        array2[21] = 9419;
        array2[22] = 9420;
        array2[23] = 9421;
        array2[24] = 9422;
        array2[25] = 9423;
        array2[26] = 9424;
        array2[27] = 9425;
        array2[28] = 9426;
        array2[29] = 9427;
        array2[30] = 9428;
        array2[31] = 9429;
        array2[32] = 9430;
        array2[33] = 9431;
        array2[34] = 9432;
        array2[35] = 9433;
        array2[36] = 9434;
        array2[37] = 9435;
        array2[38] = 9436;
        array2[39] = 9437;
        array2[40] = 9438;
        array2[41] = 9439;
        array2[42] = 9440;
        array2[43] = 9441;
        array2[44] = 9442;
        array2[45] = 9443;
        array2[46] = 9444;
        array2[47] = 9445;
        array2[48] = 9446;
        array2[49] = 9447;
        array2[50] = 9448;
        array2[51] = 9449;
        array2[52] = 9450;
        array2[53] = 9312;
        array2[54] = 9313;
        array2[55] = 9314;
        array2[56] = 9315;
        array2[57] = 9316;
        array2[58] = 9317;
        array2[59] = 9318;
        array2[60] = 9319;
        array2[61] = 9320;
        f2[n2] = array2;
        final int[] array3 = new int[62];
        array3[0] = 9372;
        array3[1] = 9373;
        final int n3 = 2;
        array3[n3] = 9374;
        array3[3] = 9375;
        array3[4] = 9376;
        array3[5] = 9377;
        array3[6] = 9378;
        array3[7] = 9379;
        array3[8] = 9380;
        array3[9] = 9381;
        array3[10] = 9382;
        array3[11] = 9383;
        array3[12] = 9384;
        array3[13] = 9385;
        array3[14] = 9386;
        array3[15] = 9387;
        array3[16] = 9388;
        array3[17] = 9389;
        array3[18] = 9390;
        array3[19] = 9391;
        array3[20] = 9392;
        array3[21] = 9393;
        array3[22] = 9394;
        array3[23] = 9395;
        array3[24] = 9396;
        array3[25] = 9397;
        array3[26] = 9372;
        array3[27] = 9373;
        array3[28] = 9374;
        array3[29] = 9375;
        array3[30] = 9376;
        array3[31] = 9377;
        array3[32] = 9378;
        array3[33] = 9379;
        array3[34] = 9380;
        array3[35] = 9381;
        array3[36] = 9382;
        array3[37] = 9383;
        array3[38] = 9384;
        array3[39] = 9385;
        array3[40] = 9386;
        array3[41] = 9387;
        array3[42] = 9388;
        array3[43] = 9389;
        array3[44] = 9390;
        array3[45] = 9391;
        array3[46] = 9392;
        array3[47] = 9393;
        array3[48] = 9394;
        array3[49] = 9395;
        array3[50] = 9396;
        array3[51] = 9397;
        array3[52] = 48;
        array3[53] = 49;
        array3[54] = 50;
        array3[55] = 51;
        array3[56] = 52;
        array3[57] = 53;
        array3[58] = 54;
        array3[59] = 55;
        array3[60] = 56;
        array3[61] = 57;
        f2[n3] = array3;
        final int[] array4 = new int[62];
        array4[0] = 7491;
        array4[1] = 7495;
        array4[2] = 7580;
        final int n4 = 3;
        array4[n4] = 7496;
        array4[4] = 7497;
        array4[5] = 7584;
        array4[6] = 7501;
        array4[7] = 688;
        array4[8] = 7588;
        array4[9] = 690;
        array4[10] = 7503;
        array4[11] = 737;
        array4[12] = 7504;
        array4[13] = 7599;
        array4[14] = 7506;
        array4[15] = 7510;
        array4[16] = 7587;
        array4[17] = 691;
        array4[18] = 738;
        array4[19] = 7511;
        array4[20] = 7512;
        array4[21] = 7515;
        array4[22] = 695;
        array4[23] = 739;
        array4[24] = 696;
        array4[25] = 7611;
        array4[26] = 7491;
        array4[27] = 7495;
        array4[28] = 7580;
        array4[29] = 7496;
        array4[30] = 7497;
        array4[31] = 7584;
        array4[32] = 7501;
        array4[33] = 688;
        array4[34] = 7588;
        array4[35] = 690;
        array4[36] = 7503;
        array4[37] = 737;
        array4[38] = 7504;
        array4[39] = 7599;
        array4[40] = 7506;
        array4[41] = 7510;
        array4[42] = 7587;
        array4[43] = 691;
        array4[44] = 738;
        array4[45] = 7511;
        array4[46] = 7512;
        array4[47] = 7515;
        array4[48] = 695;
        array4[49] = 739;
        array4[50] = 696;
        array4[51] = 7611;
        array4[52] = 48;
        array4[53] = 49;
        array4[54] = 50;
        array4[55] = 51;
        array4[56] = 52;
        array4[57] = 53;
        array4[58] = 54;
        array4[59] = 55;
        array4[60] = 56;
        array4[61] = 57;
        f2[n4] = array4;
        f = f2;
    }
    
    public FontChat() {
        final boolean b = false;
        final boolean b2 = false;
        final String s = "";
        final String m = "";
        super(Category.SERVER, "FontChat", false, -1, "Type in unicode fonts in chat");
        this.L = FontChat.b[0];
        this.M = m;
        this.d = s;
        this.c = s;
        this.k = b2;
        this.G = b2;
        this.A = b;
    }
}
