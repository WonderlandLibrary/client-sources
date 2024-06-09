// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.msauth;

public class MicrosoftAuthentication
{
    private static MicrosoftAuthentication instance;
    
    public MicrosoftAuthentication() {
        MicrosoftAuthentication.instance = this;
    }
    
    public static MicrosoftAuthentication getInstance() {
        return (MicrosoftAuthentication.instance == null) ? new MicrosoftAuthentication() : MicrosoftAuthentication.instance;
    }
    
    public void loginWithPopUpWindow() {
        if (MicrosoftLoginWindow.getInstance() == null) {
            new MicrosoftLoginWindow();
        }
        else if (MicrosoftLoginWindow.getInstance().isVisible()) {
            System.out.println("Login window already open");
        }
        else {
            MicrosoftLoginWindow.getInstance().start();
        }
    }
}
