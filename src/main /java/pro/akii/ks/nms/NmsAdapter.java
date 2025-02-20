package pro.akii.ks.nms;

import org.bukkit.entity.Player;

/**
 * Interface for NMS operations, abstracting version-specific functionality.
 */
public interface NmsAdapter {
    void setPlayerSpeed(Player player, float speed);
    void resetPlayerSpeed(Player player);
    void sendParticle(Player player, String type, double x, double y, double z, float offsetX, float offsetY, float offsetZ, float speed, int count);
}