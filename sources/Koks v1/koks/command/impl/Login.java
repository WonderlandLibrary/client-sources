package koks.command.impl;

import koks.Koks;
import koks.command.Command;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 20:50
 */
public class Login extends Command {

    public Login() {
        super("Login", "log");
    }

    @Override
    public void execute(String[] args) {
        sendmsg("Trying to login using the password: '" + Koks.getKoks().CLIENT_NAME + "2020'", true);
        sendServerMessage("/login " + Koks.getKoks().CLIENT_NAME + "2020");
    }

}