package troll.plugin;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
  private static final Set<Material> BEDS = EnumSet.of(
    Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED,
    Material.CYAN_BED, Material.GRAY_BED, Material.GREEN_BED,
    Material.LEGACY_BED, Material.LEGACY_BED_BLOCK, Material.LIGHT_BLUE_BED,
    Material.LIGHT_GRAY_BED, Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED,
    Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED,
    Material.YELLOW_BED
    );

  private static final Set<Material> CONTAINERS = EnumSet.of(
    Material.CHEST, Material.CHEST_MINECART, Material.SHULKER_BOX,
    Material.ENDER_CHEST, Material.BARREL
  );

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player p = event.getPlayer();

    if (Main.activeTrolls.containsKey(p.getUniqueId()) && Main.activeTrolls.get(p.getUniqueId()).contains("nomove")) {
      Location from = event.getFrom();
      Location to = event.getTo();
      
      if (from.getZ() != to.getZ() && from.getX() != to.getX())
        event.setCancelled(true);
    }
  }

  @EventHandler
  public void onRightClick(PlayerInteractEvent event) {
    Player p = event.getPlayer();

    if (Main.activeTrolls.containsKey(p.getUniqueId()) && Main.activeTrolls.get(p.getUniqueId()).contains("nochest")) {
      if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && CONTAINERS.contains(event.getClickedBlock().getType()))
        event.setCancelled(true);
    } else if (Main.activeTrolls.containsKey(p.getUniqueId()) && Main.activeTrolls.get(p.getUniqueId()).contains("nobed")) {
      if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && BEDS.contains(event.getClickedBlock().getType()))
        event.setCancelled(true);
    }
  }

  @EventHandler
  public void onDisconnect(PlayerQuitEvent event) {
    Player p = event.getPlayer();

    if (Main.activeTrolls.containsKey(p.getUniqueId()))
      Main.activeTrolls.remove(p.getUniqueId());
  }
}
