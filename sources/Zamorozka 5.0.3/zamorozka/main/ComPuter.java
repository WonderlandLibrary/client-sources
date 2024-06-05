package zamorozka.main;

public class ComPuter {
    public static boolean isWindows(){

        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "win" ) >= 0);1

    }

    public static boolean isMac(){

        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "mac" ) >= 0); 

    }

    public static boolean isUnix (){

        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);

    }

    public static String getOSVerion() {
        String os = System.getProperty("os.version");
        return os;
    }
}
