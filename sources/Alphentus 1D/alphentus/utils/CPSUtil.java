package alphentus.utils;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class CPSUtil {

    public static double getRandomCPS(int minCPS, int maxCPS) {
        return (long) ((Math.random() * (1000 / minCPS - 1000 / maxCPS + 1)) + 1000 / maxCPS);
    }

    public static double getCPS(int cps) {
        return 1000 / cps;
    }

}