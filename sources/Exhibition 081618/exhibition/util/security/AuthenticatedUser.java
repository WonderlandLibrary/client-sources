package exhibition.util.security;

import exhibition.Client;
import exhibition.util.security.AuthenticationUtil;
import exhibition.util.security.Crypto;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

public class AuthenticatedUser {
    private transient String decryptedUsername;
    private transient String decryptedPassword;
    private transient String encryptedUsername;
    private transient String encryptedPassword;
    private transient String inputUsername;
    private transient String inputPassword;
    private transient String entirePastbin;
    private transient List<String> jvmArguments;

    public AuthenticatedUser(String[] args) {
        this.decryptedUsername = args[0];
        this.decryptedPassword = args[1];
        this.encryptedUsername = args[2];
        this.encryptedPassword = args[3];
        try {
            this.inputUsername = Crypto.decrypt(AuthenticationUtil.getSecretNew(), args[2]);
            this.inputPassword = Crypto.decrypt(AuthenticationUtil.getSecretNew(), args[3]);
        }
        catch (Exception exception) {}
        this.jvmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
    }

    public boolean testEncryption() {
        try {
           String t1 = Crypto.decrypt(AuthenticationUtil.getSecretNew(), this.encryptedUsername);
           String t2 = Crypto.decrypt(AuthenticationUtil.getSecretNew(), this.encryptedPassword);
           if (t1.equals(this.inputUsername) && t2.equals(this.inputPassword)) {
              return t1.equals(this.inputUsername) && t2.equals(this.inputPassword);
           }
        } catch (Exception var3) {
           System.out.println("BAD");
           throw new ReportedException((CrashReport)null);
        }

        System.out.println("FALSE");
        return false;
    }

    public boolean doAuth() {
        return false;
    }

    public String getDecryptedUsername() {
        return this.decryptedUsername;
    }

    public String getDecryptedPassword() {
        return this.decryptedPassword;
    }

    public String getEncryptedUsername() {
        return this.encryptedUsername;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public List<String> getJvmArguments() {
        return this.jvmArguments;
    }
}

