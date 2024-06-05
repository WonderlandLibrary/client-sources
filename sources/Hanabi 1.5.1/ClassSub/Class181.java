package ClassSub;

import java.io.*;
import java.util.*;
import java.net.*;

class Class181 extends Thread
{
    PrintStream ps;
    
    
    public Class181(final PrintStream ps) {
        this.ps = ps;
    }
    
    public static String getRandomString(final int n) {
        final String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final Random random = new Random();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            sb.append(s.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }
    
    @Override
    public void run() {
        Class334.active = false;
        while (true) {
            this.ps.println("AntiSkidderCrack Protecting");
            try {
                final URL url = new URL(new URI(new String("https://hanabi.alphaantileak.cn:1337/" + getRandomString(new Random().nextInt(9) + 1))).toString());
            }
            catch (MalformedURLException | URISyntaxException ex2) {
                final Throwable t;
                t.printStackTrace();
            }
            try {
                Thread.sleep(3000L);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
