package xyz.rabbitual.idlerewards.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.IdleRewardsPlugin;

public class PlayerJoinListener implements Listener {

    private final IdleRewardsPlugin plugin;

    public PlayerJoinListener(@NotNull IdleRewardsPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Starts the idle reward timer on player join
     * @param event - the PlayerJoinEvent
     */
    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getTimers().scheduleTask(player);
    }

}
