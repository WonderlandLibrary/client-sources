package com.client.glowclient.modules.jewishtricks;

import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class NameChanger extends ModuleContainer
{
    public static StringValue phiPhi;
    public static StringValue infernales;
    public static StringValue glowskiBroski;
    public static StringValue glowskiBroski2B;
    public static StringValue furleoxnop;
    public static StringValue fallsGreen;
    public static StringValue theDarkEmperor;
    public static StringValue glowClient;
    public static StringValue lagTyrant;
    
    public NameChanger() {
        super(Category.JEWISH TRICKS, "NameChanger", false, -1, "Give Special people nicknames");
    }
    
    static {
        NameChanger.glowskiBroski = ValueFactory.M("NameChanger", "GlowskiBroski", "What a fucking faggot", "GlowskiBroski");
        NameChanger.glowskiBroski2B = ValueFactory.M("NameChanger", "GlowskiBroski2B", "What a fucking faggot", "GlowskiBroski2B");
        NameChanger.glowClient = ValueFactory.M("NameChanger", "GlowClient", "What a fucking faggot", "GlowClient");
        NameChanger.theDarkEmperor = ValueFactory.M("NameChanger", "TheDark_Emperor", "What a fucking faggot", "TheDark_Emperor");
        NameChanger.lagTyrant = ValueFactory.M("NameChanger", "Lag_Tyrant", "What a fucking faggot", "Lag_Tyrant");
        NameChanger.infernales = ValueFactory.M("NameChanger", "Infernales_", "What a fucking faggot", "Infernales_");
        NameChanger.furleoxnop = ValueFactory.M("NameChanger", "furleoxnop", "What a fucking faggot", "furleoxnop");
        NameChanger.fallsGreen = ValueFactory.M("NameChanger", "FallsGreen", "What a fucking faggot", "FallsGreen");
        NameChanger.phiPhi = ValueFactory.M("NameChanger", "Phi_Phi", "What a fucking faggot", "Feg");
    }
}
