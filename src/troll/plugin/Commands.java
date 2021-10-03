package troll.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
  public static String[] commands = { "troll" };
  private List<String> availableTrolls = Arrays.asList(new String[] { "nosleep", "nomove", "nochest" });

  private String help =
    "§c§lTroll §cplugin commands\n\n" +
    "§b§l/troll help or /troll §b- Displays this help menu.\n" +
    "§b§l/troll list <username> §b- Displays all trolls that are active for this player.\n" +
    "§b§l/troll trolls §b- Displays all available trolls.\n" +
    "§b§l/troll <troll name> <username> [enable/disable] §b- Enables or disables a troll for this player.\n";

  private String trolls = 
    "§c§lTroll §cList of all available trolls\n\n" + 
    "§b§lnosleep §b- the \"There are monsters nearby\" troll.\n" +
    "§b§lnomove §b- player will be glitched and unable to move.\n" +
    "§b§lnochest §b- player wont be able to open any containers.\n" +
    "§b§lkick §b- Kicks player with an error message.";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    if (cmd.getName().equalsIgnoreCase("troll")) {
      if (args.length == 0) {
        sender.sendMessage(help);
        return true;
      }

      if (availableTrolls.contains(args[0].toLowerCase())) {
        if (args.length < 3) {
          argsError(sender);
          return true;
        }

        String trollName = args[0];
        Player p = Bukkit.getPlayer(args[1]);
        Boolean enable = false;
        List<String> activeTrolls = Main.activeTrolls.get(p.getUniqueId());

        if (p == null || !p.isOnline()) {
          sender.sendMessage("§cPlayer doesn't exist or isn't online.");
          return true;
        }

        if (!args[2].equalsIgnoreCase("enable") && !args[2].equalsIgnoreCase("disable") ) {
          sender.sendMessage("§cWrong keyword. Use \"enable\" or \"disable\".");
          return true;
        }

        if (args[2].equalsIgnoreCase("enable"))
          enable = true;
        
        if (!enable) {
          if (activeTrolls == null || !activeTrolls.contains(trollName)) {
            sender.sendMessage("§cThis troll isn't enabled for this player.");
            return true;
          }

          Main.activeTrolls.get(p.getUniqueId()).remove(trollName);

          sender.sendMessage("§aTroll successfully disabled.");
        } else {
          if (activeTrolls == null) {
            Main.activeTrolls.put(p.getUniqueId(), new ArrayList<String>());
            activeTrolls = Main.activeTrolls.get(p.getUniqueId());
          }

          if (activeTrolls.contains(trollName)) {
            sender.sendMessage("§cThis troll is already enabled for this player.");
            return true;
          }

          Main.activeTrolls.get(p.getUniqueId()).add(trollName);

          sender.sendMessage("§aTroll successfully enabled.");
        }

        return true;
      }

      switch (args[0].toLowerCase()) {
        case "help":
          sender.sendMessage(help);
          break;
        case "trolls":
          sender.sendMessage(trolls);
          break;
        case "list":
          if (args.length < 2) {
            argsError(sender);
            break;
          }

          Player p = Bukkit.getPlayer(args[1]);

          if (p == null || !p.isOnline()) {
            sender.sendMessage("§cPlayer doesn't exist or isn't online.");
            break;
          }

          if (!Main.activeTrolls.containsKey(p.getUniqueId())) {
            sender.sendMessage("§cPlayer doesn't have any active trolls.");
            break;
          }

          sender.sendMessage("§b§lActive trolls: §b" + String.join(", ", Main.activeTrolls.get(p.getUniqueId())));
          break;
        case "kick":
          if (args.length < 2) {
            argsError(sender);
            break;
          }

          Player player = Bukkit.getPlayer(args[1]);

          if (player == null || !player.isOnline()) {
            sender.sendMessage("§cPlayer doesn't exist or isn't online.");
            break;
          }

          player.kickPlayer("could not pass event SpawnEntityEvent to class java.mojang.generation.handler.GenerationHandler error at java.mojang.events.handler.SpawnEntityEvent [java:135] at java.mojang.handler.GenerationHandler [java:19]");
          break;
        default:
          sender.sendMessage(help);
          break;
      }
    }

    return true;
  }

  private void argsError(CommandSender sender) {
    sender.sendMessage("§cIf you don't know how to use this command type: /troll or /troll help.\nI'm too lazy to add a proper error message with usage.");  
  }
}
