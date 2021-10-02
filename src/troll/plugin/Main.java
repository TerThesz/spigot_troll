package troll.plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public static Map<UUID, List<String>> activeTrolls = new HashMap<UUID, List<String>>();

  @Override
  public void onEnable() {
    for (String cmd : Commands.commands)
      this.getCommand(cmd).setExecutor(new Commands());

    Bukkit.getPluginManager().registerEvents(new Events(), this);
  }

  @Override
  public void onDisable() {

  }
}
