package wtf.diablo.client.module.impl.misc.sessioninfo;

public final class ServerSession {
    private final long startTime;

    public ServerSession() {
        this.startTime = System.currentTimeMillis();
    }

    public String getFormattedTime() {
        final long time = System.currentTimeMillis() - this.startTime;

        final StringBuilder stringBuilder = new StringBuilder();
        final int days = (int) (time / 86400000L % 365L);
        final int hours = (int) (time / 3600000L % 24L);
        final int minutes = (int) (time / 60000L % 60L);
        final int seconds = (int) (time / 1000L % 60L);

        if (days != 0)
            stringBuilder.append(days).append("d ");
        if (hours != 0)
            stringBuilder.append(hours).append("h ");
        if (minutes != 0)
            stringBuilder.append(minutes).append("m ");
        if (seconds != 0)
            stringBuilder.append(seconds).append("s");

        return stringBuilder.toString();
    }
}
