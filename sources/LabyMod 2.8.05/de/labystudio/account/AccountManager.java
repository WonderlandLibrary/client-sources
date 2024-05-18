package de.labystudio.account;

import com.mojang.authlib.Agent;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import de.labystudio.labymod.LabyMod;
import java.lang.reflect.Field;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.apache.logging.log4j.LogManager;

public class AccountManager
{
    private static Session tempSession = null;
    public static String accountManagerUsername = Minecraft.getMinecraft().getSession().getProfile().getName();
    public static String accountManagerPassword = "";

    public static String login(String username, String password)
    {
        try
        {
            tempSession = authenticate(username, password);
        }
        catch (AuthenticationException authenticationexception)
        {
            if (!password.startsWith("@"))
            {
                return authenticationexception.getMessage();
            }

            tempSession = new Session(username, password.replace("@", "").replace("-", ""), "", Session.Type.MOJANG.toString());
        }

        return tempSession != null ? setSession(tempSession) : "Error?";
    }

    public static String setSession(Session tempSession)
    {
        try
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            Field field1 = minecraft.getClass().getDeclaredField("ae");
            field1.setAccessible(true);
            field1.set(minecraft, tempSession);
        }
        catch (Exception exception1)
        {
            try
            {
                Minecraft minecraft1 = Minecraft.getMinecraft();
                Field field = minecraft1.getClass().getDeclaredField("session");
                field.setAccessible(true);
                field.set(minecraft1, tempSession);
            }
            catch (Exception exception)
            {
                tempSession = null;
                exception.printStackTrace();
                return exception1.getMessage();
            }
        }

        LabyMod.getInstance().client.newAccount();
        LogManager.getLogger().info("AccountManager: You are now playing with " + tempSession.getProfile().getName() + ".");
        return "";
    }

    public static void removeFinal(Field field) throws Exception
    {
        field.setAccessible(true);
        int i = field.getModifiers();
        Field field1 = field.getClass().getDeclaredField("modifiers");
        i = i & -17;
        field1.setAccessible(true);
        field1.setInt(field1, i);
    }

    public static Session authenticate(String username, String password) throws AuthenticationException
    {
        HttpAuthenticationService httpauthenticationservice = new YggdrasilAuthenticationService(Proxy.NO_PROXY, Minecraft.getMinecraft().getSession().getToken());
        UserAuthentication userauthentication = httpauthenticationservice.createUserAuthentication(Agent.MINECRAFT);
        userauthentication.setUsername(username);
        userauthentication.setPassword(password);
        userauthentication.logIn();

        if (userauthentication.canPlayOnline())
        {
            Session session = new Session(userauthentication.getSelectedProfile().getName(), userauthentication.getSelectedProfile().getId().toString(), userauthentication.getAuthenticatedToken(), Session.Type.MOJANG.toString());
            return session;
        }
        else
        {
            LogManager.getLogger().info("AccountManager: Could not log in!");
            return null;
        }
    }
}
