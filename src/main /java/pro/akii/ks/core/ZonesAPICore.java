package pro.akii.ks.core;

import pro.akii.ks.nms.NmsAdapter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core class for ZonesAPI, managing initialization and NMS integration.
 */
public class ZonesAPICore {
    private static ZonesAPICore instance;
    private final JavaPlugin plugin;
    private NmsAdapter nmsAdapter;

    private ZonesAPICore(JavaPlugin plugin) {
        this.plugin = plugin;
        loadNmsAdapter();
    }

    /**
     * Initializes the ZonesAPI singleton.
     *
     * @param plugin The plugin instance.
     */
    public static void initialize(JavaPlugin plugin) {
        if (instance == null) {
            instance = new ZonesAPICore(plugin);
            plugin.getLogger().info("ZonesAPI initialized.");
        }
    }

    /**
     * Gets the ZonesAPI instance.
     *
     * @return The instance.
     * @throws IllegalStateException if not initialized.
     */
    public static ZonesAPICore getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ZonesAPI not initialized!");
        }
        return instance;
    }

    private void loadNmsAdapter() {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            Class<?> adapterClass = Class.forName("pro.akii.ks.nms.adapters." + version);
            nmsAdapter = (NmsAdapter) adapterClass.getConstructor().newInstance();
            plugin.getLogger().info("Loaded NMS adapter for version: " + version);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load NMS adapter for " + version + ". NMS features disabled.");
            nmsAdapter = null;
        }
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public NmsAdapter getNmsAdapter() {
        return nmsAdapter;
    }
}