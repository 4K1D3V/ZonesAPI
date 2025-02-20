package pro.akii.ks.zones;

import pro.akii.ks.zones.effects.NmsZoneEffect;
import pro.akii.ks.zones.effects.PotionZoneEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.PotionEffectType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command handler for creating zones dynamically.
 */
public class ZoneCommand implements CommandExecutor {
    private final ZoneManager zoneManager;

    public ZoneCommand(ZoneManager zoneManager) {
        this.zoneManager = zoneManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("zonesapi.zone.create")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to create zones!");
            return true;
        }
        if (args.length < 7 || !args[0].equalsIgnoreCase("zone") || !args[1].equalsIgnoreCase("create")) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /zonesapi zone create <x1> <y1> <z1> <x2> <y2> <z2> [effect]");
            return true;
        }

        try {
            double x1 = Double.parseDouble(args[2]);
            double y1 = Double.parseDouble(args[3]);
            double z1 = Double.parseDouble(args[4]);
            double x2 = Double.parseDouble(args[5]);
            double y2 = Double.parseDouble(args[6]);
            double z2 = Double.parseDouble(args[7]);
            Location loc1 = new Location(player.getWorld(), x1, y1, z1);
            Location loc2 = new Location(player.getWorld(), x2, y2, z2);
            Zone zone = zoneManager.createZone(loc1, loc2);

            String effect = args.length > 8 ? args[8].toLowerCase() : "jump";
            switch (effect) {
                case "jump":
                    zone.addEffect(new PotionZoneEffect(PotionEffectType.JUMP, 2));
                    break;
                case "speed":
                    zone.addEffect(new NmsZoneEffect(0.2f, "flame", 0.5));
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Unknown effect: " + effect);
                    return true;
            }
            player.sendMessage(ChatColor.GREEN + "Created zone from " + loc1.toVector() + " to " + loc2.toVector() + " with effect: " + effect);
            return true;
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid coordinates! Use numbers.");
            return true;
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + e.getMessage());
            return true;
        }
    }
}