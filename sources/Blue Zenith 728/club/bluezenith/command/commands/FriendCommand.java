package club.bluezenith.command.commands;

import club.bluezenith.command.Command;

import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class FriendCommand extends Command {

    public FriendCommand() {
        super("Friend", "Manage friends.", ".friend <(name) | clear | list | remove (name) | add (name)>", "f", "friends");
    }

    @Override
    public void execute(String[] args) {
       if(args.length == 1)
           chat("Usage: " + this.syntax);
       else if(args.length == 2) {
           switch (args[1].toLowerCase()) {
               case "clear":
                   getBlueZenith().getFriendManager().clearList();
                   getBlueZenith().getNotificationPublisher().postSuccess("Friends", "Cleared your friends list.", 2500);
               break;

               case "list":
                   final List<String> friends = getBlueZenith().getFriendManager().getFriendsList();
                   if(friends.isEmpty())
                       getBlueZenith().getNotificationPublisher().postError("Friends", "You don't have any friends!", 2500);
                   else {
                       chat("List of all friends:");
                       friends.forEach(this::chat);
                   }
               break;

               case "remove":
                   getBlueZenith().getNotificationPublisher().postInfo("Friends", "Provide an IGN of a friend to remove.", 2500);
               break;

               case "add":
                   getBlueZenith().getNotificationPublisher().postInfo("Friends", "Provide an IGN of a friend to add.", 2500);
               break;

               default:
                   final boolean flag = getBlueZenith().getFriendManager().isFriend(args[1]);
                   if(flag)
                       getBlueZenith().getNotificationPublisher().postError("Friends", args[1] + " is already on your friends list!", 2500);
                   else {
                       getBlueZenith().getFriendManager().addFriend(args[1]);
                       getBlueZenith().getNotificationPublisher().postSuccess("Friends", args[1] + " is now your friend!", 2500);
                   }
               break;
           }
       } else if(args.length >= 3) {
           switch (args[1].toLowerCase()) {
               case "clear":
                   getBlueZenith().getFriendManager().clearList();
                   getBlueZenith().getNotificationPublisher().postSuccess("Friends", "Cleared your friends list.", 2500);
                   break;

               case "list":
                   final List<String> friends = getBlueZenith().getFriendManager().getFriendsList();
                   if(friends.isEmpty())
                       getBlueZenith().getNotificationPublisher().postError("Friends", "You don't have any friends!", 2500);
                   else {
                       chat("List of all friends:");
                       friends.forEach(this::chat);
                   }
                   break;

               case "remove":
                   if(getBlueZenith().getFriendManager().isFriend(args[2])) {
                       getBlueZenith().getFriendManager().removeFriend(args[2]);
                       getBlueZenith().getNotificationPublisher().postSuccess("Friends", "Removed " + args[2] + " from friends list.", 2500);
                   } else getBlueZenith().getNotificationPublisher().postError("Friends", args[2] + " is not your friend!", 2500);
                   break;

               case "add":
                   if(getBlueZenith().getFriendManager().isFriend(args[2])) {
                       getBlueZenith().getNotificationPublisher().postError("Friends", args[2] + " is already on your friends list!", 2500);
                   } else {
                       getBlueZenith().getFriendManager().addFriend(args[2]);
                       getBlueZenith().getNotificationPublisher().postSuccess("Friends", args[2] + " is now your friend.", 2500);
                   }
               break;
           }
       }
    }
}
