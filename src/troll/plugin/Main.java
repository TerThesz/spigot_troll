package troll.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public static Map<UUID, String[]> activeTrolls = new HashMap<UUID, String[]>();

  @Override
  public void onEnable() {
    for (String cmd : Commands.commands)
      this.getCommand(cmd).setExecutor(new Commands());

    Bukkit.getPluginManager().registerEvents(new Events(), this);

    activeTrolls.put(Bukkit.getPlayer("TerThesz").getUniqueId(), new String[] { "dfdsfs", "dsdsgdsg" });
  }

  @Override
  public void onDisable() {

  }
}
