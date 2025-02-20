package pro.akii.ks;

import pro.akii.ks.core.ZonesAPICore;
import pro.akii.ks.zones.Zone;
import pro.akii.ks.zones.ZoneCommand;
import pro.akii.ks.zones.ZoneManager;
import pro.akii.ks.zones.effects.NmsZoneEffect;
import pro.akii.ks.zones.effects.PotionZoneEffect;
import org.bukkit.Location;
import org.bukkit.PotionEffectType;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main entry point for the ZonesAPI.
 */
public class ZonesAPI extends JavaPlugin {
    private ZoneManager zoneManager;

    @Override
    public void onEnable() {
        ZonesAPICore.initialize(this);
        zoneManager = new ZoneManager();

        // Register command
        getCommand("zonesapi").setExecutor(new ZoneCommand(zoneManager));

        // Example zone setup
        World world = getServer().getWorld("world");
        if (world != null) {
            Location loc1 = new Location(world, 0, 60, 0);
            Location loc2 = new Location(world, 10, 70, 10);
            Zone zone = zoneManager.createZone(loc1, loc2);
            zone.addEffect(new PotionZoneEffect(PotionEffectType.JUMP, 2));
            zone.addEffect(new NmsZoneEffect(0.2f, "flame", 0.5));
            getLogger().info("ZonesAPI enabled with example zone from " + loc1 + " to " + loc2);
        } else {
            getLogger().warning("World 'world' not found! Example zone not created.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ZonesAPI disabled.");
    }
}