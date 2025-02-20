package pro.akii.ks.zones;

import pro.akii.ks.core.ZonesAPICore;
import pro.akii.ks.zones.effects.NmsZoneEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

/**
 * Manages zones and applies effects to players based on their location.
 */
public class ZoneManager implements Listener {
    private final GridPartition gridPartition = new GridPartition();
    private final Map<Player, Set<Zone>> playerZones = new HashMap<>();
    private final Map<Player, Location> lastLocations = new HashMap<>();

    public ZoneManager() {
        Bukkit.getPluginManager().registerEvents(this, ZonesAPICore.getInstance().getPlugin());
        // Schedule particle updates every 0.5 seconds (10 ticks)
        Bukkit.getScheduler().runTaskTimer(ZonesAPICore.getInstance().getPlugin(), this::updateParticles, 0L, 10L);
    }

    /**
     * Creates a new zone between two locations.
     *
     * @param loc1 First corner.
     * @param loc2 Second corner.
     * @return The created zone.
     */
    public Zone createZone(Location loc1, Location loc2) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Zone corners must be in the same world!");
        }
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());
        Zone zone = new Zone(loc1.getWorld(), minX, minY, minZ, maxX, maxY, maxZ);
        gridPartition.addZone(zone);
        return zone;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();

        // Only process if the player moved to a new block
        Location lastLoc = lastLocations.get(player);
        if (lastLoc != null && to.getBlockX() == lastLoc.getBlockX() &&
            to.getBlockY() == lastLoc.getBlockY() && to.getBlockZ() == lastLoc.getBlockZ()) {
            return;
        }
        lastLocations.put(player, to.clone());

        Set<Zone> currentZones = new HashSet<>(gridPartition.getZonesForLocation(to));
        currentZones.removeIf(zone -> !zone.contains(to));
        Set<Zone> previousZones = playerZones.getOrDefault(player, Collections.emptySet());

        if (!currentZones.equals(previousZones)) {
            for (Zone zone : currentZones) {
                if (!previousZones.contains(zone)) {
                    zone.getEffects().forEach(effect -> effect.apply(player));
                }
            }
            for (Zone zone : previousZones) {
                if (!currentZones.contains(zone)) {
                    zone.getEffects().forEach(effect -> effect.remove(player));
                }
            }
            playerZones.put(player, currentZones);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Set<Zone> zones = playerZones.remove(player);
        lastLocations.remove(player);
        if (zones != null) {
            zones.forEach(zone -> zone.getEffects().forEach(effect -> effect.remove(player)));
        }
    }

    private void updateParticles() {
        for (Map.Entry<Player, Set<Zone>> entry : playerZones.entrySet()) {
            Player player = entry.getKey();
            if (!player.isOnline()) continue;
            for (Zone zone : entry.getValue()) {
                zone.getEffects().stream()
                    .filter(effect -> effect instanceof NmsZoneEffect)
                    .map(effect -> (NmsZoneEffect) effect)
                    .forEach(effect -> effect.spawnParticles(player));
            }
        }
    }
}