package troll.plugin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
  public static String[] commands = { "troll" };

  private String help =
    "§c§lTroll §4cplugin commands\n\n" +
    "§3§l/troll help or /troll §3- Displays this help menu.\n" +
    "§3§l/troll list <username> §3- Displays all trolls that are active for this player.\n" +
    "§3§l/troll trolls §3- Displays all available trolls.\n" +
    "§3§l/troll <troll name> <username> enable/disable §3- Enables or disables a troll for this player.\n";

  private String trolls = 
    "§c§lTroll §4List of all available trolls\n\n" + 
    "§3§lnosleep §3- the \"There are monsters nearby\" troll.\n" +
    "§3§lnomove §3- player will be glitched and unable to move.\n" +
    "§3§lnochest §3- player wont be able to open any containers.\n";

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
    if (cmd.getName().equalsIgnoreCase("troll")) {
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

          sender.sendMessage("§3§lActive trolls: §3" + String.join(",", (String[]) Main.activeTrolls.get(p.getUniqueId())));
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
