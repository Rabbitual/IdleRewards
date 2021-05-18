package xyz.rabbitual.idlerewards.reward;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.IdleRewardsPlugin;
import xyz.rabbitual.idlerewards.manager.RewardManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdleRewardTimers {

    private final IdleRewardsPlugin plugin;
    private final Map<UUID, BukkitTask> timerTasks;
    private final Map<UUID, Long> nextAddTimes;

    public IdleRewardTimers(@NotNull IdleRewardsPlugin plugin) {
        this.plugin = plugin;
        this.timerTasks = new HashMap<>();
        this.nextAddTimes = new HashMap<>();
    }

    /**
     * Gets the time, as system millis, that the specified player should next receive additional idle rewards
     * @param player - the player to get the idle reward addition time of
     * @return the time that the specified player will receive their next idle reward
     */
    public long getNextAddTime(@NotNull Player player) {
        return nextAddTimes.get(player.getUniqueId());
    }

    /**
     * Schedules an idle reward timer task for the specified player
     * @param player - the player to start an idle reward timer for
     */
    public void scheduleTask(@NotNull Player player) {
        UUID playerUUID = player.getUniqueId();
        if (timerTasks.containsKey(playerUUID)) {
            timerTasks.get(playerUUID).cancel();
        }

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        RewardManager rewardManager = plugin.getRewardManager();

                        if (!player.isOnline()) {
                            timerTasks.remove(playerUUID);
                            nextAddTimes.remove(playerUUID);
                            cancel();
                            return;
                        }

                        rewardManager.addIdleReward(player);
                        nextAddTimes.put(playerUUID, System.currentTimeMillis() + 300000L);
                    }
                }.runTask(plugin);
            }
        }.runTaskTimerAsynchronously(plugin, 6000L, 6000L);

        timerTasks.put(playerUUID, task);
        nextAddTimes.put(playerUUID, System.currentTimeMillis() + 300000L);
    }


}
