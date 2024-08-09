package ez.h.managers.altmanager;

public class Alt
{
    String name;
    String password;
    String created;
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public Alt(final String name, final String password, final String created) {
        this.name = name;
        this.password = password;
        this.created = created;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
