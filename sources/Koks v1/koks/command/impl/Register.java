package koks.command.impl;

import koks.Koks;
import koks.command.Command;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 20:50
 */
public class Register extends Command {

    public Register() {
        super("Register", "reg");
    }

    @Override
    public void execute(String[] args) {
        sendmsg("Trying to sign up using the password: '" + Koks.getKoks().CLIENT_NAME + "2020'", true);
        sendServerMessage("/register " + Koks.getKoks().CLIENT_NAME + "2020 " + Koks.getKoks().CLIENT_NAME + "2020");
    }

}