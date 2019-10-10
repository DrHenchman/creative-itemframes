package creativeitemframes;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
        Entity source = damageSource(event);
        if (event.getEntity() instanceof ItemFrame && source instanceof Player) {
            onItemFrameDamageByPlayer((ItemFrame) event.getEntity(), (Player) source);
        }
    }

    private Entity damageSource(EntityDamageByEntityEvent event) {
        Entity source = event.getDamager();
        if (source instanceof Projectile) {
            Projectile projectile = (Projectile) source;
            if (projectile.getShooter() instanceof Entity) {
                source = (Entity) projectile.getShooter();

            }
        }
        return source;
    }

    private void onItemFrameDamageByPlayer(ItemFrame itemFrame, Player player) {
        if (player.getGameMode() == GameMode.CREATIVE && itemFrame.getItem().getAmount() > 0) {
            player.sendMessage("" +
                    "You damaged an item frame whilst in creative mode. " +
                    "This normally would delete the item. " +
                    "Dropping item instead.");
            Location dropLocation = new Location(
                    itemFrame.getLocation().getWorld(),
                    itemFrame.getLocation().getBlockX() + 0.5d,
                    itemFrame.getLocation().getBlockY() + 0.5d,
                    itemFrame.getLocation().getBlockZ() + 0.5d
            );
            itemFrame.getWorld().dropItem(dropLocation, itemFrame.getItem());
        }
    }

}
