package host.kix.uzi.admin;

import host.kix.uzi.Uzi;
import host.kix.uzi.file.CustomFile;
import host.kix.uzi.friends.Friend;
import host.kix.uzi.management.ListManager;
import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.*;
import java.util.Optional;

/**
 * Created by Kix on 5/31/2017.
 * Made for the eclipse project.
 */
public class AdminManager extends ListManager<Admin> {

    public AdminManager() {
    }

    public Admin findAdminThroughIdentifier(String identifier) {
        for (Admin admin : getContents()) {
            if (admin.getUsername().equalsIgnoreCase(identifier)) {
                return admin;
            }
        }
        return null;
    }

    public Optional<Admin> get(String username) {
        for (Admin admin : getContents())
            if (admin.getUsername().equals(username)) {
                return Optional.of(admin);
            }
        return Optional.empty();
    }

}
