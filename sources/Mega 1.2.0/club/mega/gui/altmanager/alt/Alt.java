package club.mega.gui.altmanager.alt;

public class Alt {

    private final String name;
    private final String email;
    private final String password;
    private final AltType altType;

    public Alt(final String name, final String email, final String password, final AltType altType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.altType = altType;
    }

    public final String getName() {
        return name;
    }
    public final String getEmail() {
        return email;
    }
    public final String getPassword() {
        return password;
    }
    public final AltType getAltType() {
        return altType;
    }

}
