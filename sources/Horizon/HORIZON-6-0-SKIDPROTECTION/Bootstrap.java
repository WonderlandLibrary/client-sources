package HORIZON-6-0-SKIDPROTECTION;

public class Bootstrap
{
    public static void HorizonCode_Horizon_È(final Game game, final int width, final int height, final boolean fullscreen) {
        try {
            final AppGameContainer container = new AppGameContainer(game, width, height, fullscreen);
            container.Ø­áŒŠá();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
