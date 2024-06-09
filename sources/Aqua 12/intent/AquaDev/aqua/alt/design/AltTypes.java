// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.alt.design;

public class AltTypes
{
    public String EmailName;
    public String PWName;
    
    public AltTypes(final String Email, final String PW) {
        this.EmailName = Email;
        this.PWName = PW;
    }
    
    public String getEmail() {
        return this.EmailName;
    }
    
    public String getPassword() {
        return this.PWName;
    }
    
    public void setEmail(final String Email) {
        this.EmailName = Email;
    }
    
    public void setPassword(final String PW) {
        this.PWName = PW;
    }
}
