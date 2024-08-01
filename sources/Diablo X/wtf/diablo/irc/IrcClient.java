package wtf.diablo.irc;

import best.azura.eventbus.core.EventBus;
import wtf.diablo.auth.DiabloRank;
import wtf.diablo.irc.event.IrcConnectedMessageEvent;
import wtf.diablo.irc.event.IrcIncomingMessageEvent;
import wtf.diablo.irc.event.IrcQueryOnlineEvent;
import wtf.diablo.irc.event.IrcReceiveMinecraftEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class IrcClient {
    private final Builder builder;
    private final Socket socket;
    private Thread recieverThread;

    private IrcClient(final Builder builder) {
        this.builder = builder;
        try {
            this.socket = new Socket(builder.host, builder.port);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        this.recieverThread = createRecieverThread();
    }

    private Thread createRecieverThread() {
        return new Thread(() -> {
            try {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("RCVMSG ")) {
                        line = line.replace("RCVMSG IRC ", "");

                        final String[] split = line.replace("RCVMSG ", "").split(" ");

                        if (split.length < 2) {
                            continue;
                        }

                        final String sender = split[0];

                        System.out.println("Sender: " + sender);

                        final String message = line.replace("RCVMSG " + sender + " ", "");

                        builder.eventBus.call(new IrcIncomingMessageEvent(message, sender));
                    } else if (line.startsWith("CONNECTED ")) {
                        final String message = line.replace("CONNECTED ", "");

                        builder.eventBus.call(new IrcConnectedMessageEvent(message));
                    } else if (line.startsWith("ERROR ")) {
                        final String message = line.replace("ERROR ", "");

                        builder.eventBus.call(new IrcConnectedMessageEvent(message));
                    } else if (line.startsWith("ONLINE ")) {
                        final int online = Integer.parseInt(line.replace("ONLINE ", ""));

                        builder.eventBus.call(new IrcQueryOnlineEvent(online));
                    } else if (line.startsWith("RCVMINECRAFT")) {
                        final String[] split = line.replace("RCVMINECRAFT ", "").split(" ");

                        if (split.length != 4) {
                            System.out.println("Invalid length: " + split.length);
                            continue;
                        }

                        final String username = split[0];
                        final DiabloRank rank = DiabloRank.getRank(Integer.valueOf(split[1]));
                        final String minecraftUsername = split[2];
                        final boolean shouldRemove = Boolean.parseBoolean(split[3]);

                        builder.eventBus.call(new IrcReceiveMinecraftEvent(username, rank, minecraftUsername, shouldRemove));
                    }
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateMinecraft(final String message, final int rank, final String minecraftUsername, final boolean shouldRemove) {
        try {
            final String formattedMessage = String.format("PUBMINECRAFT %s %s %s %s %s", builder.sessionID, message, rank, minecraftUsername, shouldRemove);

            socket.getOutputStream().write(formattedMessage.getBytes());
            socket.getOutputStream().write('\n'); // Add a newline to separate messages
            socket.getOutputStream().flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void postMessage(final String message) {
        try {
            final String formattedMessage = String.format("PUBMSG %s %s", builder.sessionID, message);

            socket.getOutputStream().write(formattedMessage.getBytes());
            socket.getOutputStream().write('\n'); // Add a newline to separate messages
            socket.getOutputStream().flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        recieverThread.start();
    }

    public void queryUsersOnline() {
        try {
            final String formattedMessage = String.format("QUERYONLINE %s", builder.sessionID);

            socket.getOutputStream().write(formattedMessage.getBytes());
            socket.getOutputStream().write('\n'); // Add a newline to separate messages
            socket.getOutputStream().flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            recieverThread.interrupt();
            socket.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void publishMinecraftUsername(final String username) {
        try {
            final String formattedMessage = String.format("PUBMINECRAFT %s %s", builder.sessionID, username);

            socket.getOutputStream().write(formattedMessage.getBytes());
            socket.getOutputStream().write('\n'); // Add a newline to separate messages
            socket.getOutputStream().flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private EventBus eventBus;
        private String sessionID;
        private String host;
        private int port;

        public Builder eventBus(EventBus eventBus) {
            this.eventBus = eventBus;
            return this;
        }

        public Builder sessionID(String sessionID) {
            this.sessionID = sessionID;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public IrcClient build() {
            return new IrcClient(this);
        }
    }
}
