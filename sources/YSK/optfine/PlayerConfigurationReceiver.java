package optfine;

import com.google.gson.*;

public class PlayerConfigurationReceiver implements IFileDownloadListener
{
    private static final String[] I;
    private String player;
    
    public PlayerConfigurationReceiver(final String player) {
        this.player = null;
        this.player = player;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\t3\u0013;", "QZpZr");
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    @Override
    public void fileDownloadFinished(final String s, final byte[] array, final Throwable t) {
        if (array != null) {
            try {
                final PlayerConfiguration playerConfiguration = new PlayerConfigurationParser(this.player).parsePlayerConfiguration(new JsonParser().parse(new String(array, PlayerConfigurationReceiver.I["".length()])));
                if (playerConfiguration != null) {
                    playerConfiguration.setInitialized(" ".length() != 0);
                    PlayerConfigurations.setPlayerConfiguration(this.player, playerConfiguration);
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
