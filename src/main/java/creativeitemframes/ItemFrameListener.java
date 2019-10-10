package creativeitemframes;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

/**
 * Listen for changes to item frames
 */
public class ItemFrameListener implements Listener {

    private final Plugin plugin;

    ItemFrameListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof ItemFrame) {
            onItemFrameDamageByPlayer((ItemFrame) event.getEntity(), (Player) event.getDamager());
        }
    }

    private void onItemFrameDamageByPlayer(ItemFrame itemFrame, Player player) {
        if (player.getGameMode() == GameMode.CREATIVE && itemFrame.getItem().getAmount() > 0) {
            plugin.getLogger().info(String.format("" +
                            "Player %s " +
                            "destroyed item %s " +
                            "in item frame at %s " +
                            "while in creative. Putting item in their inventory",
                    player.getName(),
                    itemFrame.getItem(),
                    itemFrame.getLocation()));
            itemFrame.getWorld().dropItem(
                    new Location(
                            itemFrame.getLocation().getWorld(),
                            itemFrame.getLocation().getBlockX() + 0.5d,
                            itemFrame.getLocation().getBlockY() + 0.5d,
                            itemFrame.getLocation().getBlockZ() + 0.5d
                    ),
                    itemFrame.getItem());
        }
    }

}
