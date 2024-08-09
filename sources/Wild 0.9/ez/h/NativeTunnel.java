package ez.h;

public class NativeTunnel
{
    public static int getPlayerUid() {
        try {
            return getUid0();
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            return 0;
        }
    }
    
    private static native int getUid0();
}
