// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient;

import de.liquiddev.ircclient.client.IrcPlayer;
import java.nio.ByteBuffer;
import de.liquiddev.ircclient.api.IrcClient;
import java.util.Scanner;
import de.liquiddev.ircclient.api.IrcApi;
import de.liquiddev.ircclient.client.IrcClientFactory;
import de.liquiddev.ircclient.client.ClientType;

public class Main
{
    public static void main(final String[] args) {
        if (args.length != 2) {
            System.out.println(new _ircIllIlIlIII().toString());
            return;
        }
        ClientType value;
        try {
            value = ClientType.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            System.out.println(new _ircIIIIllIlll().toString());
            return;
        }
        System.out.println(String.valueOf(new _ircIllIIIlIlI().toString()) + value.getName() + new _ircIIIIlIlIlI().toString());
        final IrcClient ircClient = IrcClientFactory.getDefault().createIrcClient(value, args[1], null, new _ircIllIlIIIII().toString());
        System.out.println(ircClient.getNickname());
        ircClient.getApiManager().registerApi(new _ircIlIIIIlIlI().setPrefix(new _irclIIlIllIII().toString()));
        ircClient.getApiManager().registerCustomDataListener((ircPlayer, str, p2) -> System.out.println(String.valueOf(ircPlayer.getIrcNick()) + new _irclllIllIIII().toString() + str));
        final Scanner scanner = new Scanner(System.in);
        while (!ircClient.isForcedDisconnect()) {
            final String nextLine;
            if ((nextLine = scanner.nextLine()).equalsIgnoreCase(new _irclIIIIlIIII().toString())) {
                ircClient.disconnect();
            }
            else if (nextLine.startsWith(new _irclIIlIlllll().toString())) {
                ircClient.executeCommand(nextLine);
            }
            else if (nextLine.startsWith(new _irclllIIlIlIl().toString())) {
                ircClient.sendCustomData(new _ircIIlIIIlIlI().toString(), new byte[0]);
            }
            else {
                ircClient.sendChatMessage(nextLine);
            }
        }
        scanner.close();
    }
    
    private static void _ircIllIllIIlI(final IrcClient ircClient) {
        final ByteBuffer byteBuffer;
        ircClient.getApiManager().registerCustomDataListener((ircPlayer, s, array) -> {
            if (!s.equalsIgnoreCase(new _ircIIIlIlllII().toString())) {
                return;
            }
            else {
                ByteBuffer.wrap(array);
                System.out.println(String.valueOf(ircPlayer.getIrcNick()) + new _ircIllIIIIllI().toString() + byteBuffer.getInt());
                return;
            }
        });
        final ByteBuffer allocate;
        (allocate = ByteBuffer.allocate(64)).putInt(4);
        allocate.flip();
        final byte[] dst = new byte[allocate.remaining()];
        allocate.get(dst);
        ircClient.sendCustomData(new _ircIIIIIlIIll().toString(), dst);
    }
}
