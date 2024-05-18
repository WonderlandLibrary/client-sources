package de.labystudio.account;

public class MojangAccount
{
    private String userName;
    private String password;
    private String email;

    public MojangAccount(String userName, String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
