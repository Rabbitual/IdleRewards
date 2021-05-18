package xyz.rabbitual.idlerewards.debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.manager.RewardManager;

public class IdleRewardLogger {

    private final RewardManager rewardManager;

    public IdleRewardLogger(@NotNull RewardManager rewardManager) {
        this.rewardManager = rewardManager;
    }

    /**
     * Logs a new addition to the specified player's idle rewards
     * @param player - the player receiving an additional claimable idle reward
     */
    public void logAddedReward(@NotNull Player player) {
        log("Added 1x '%s%s' to %s's claimable idle rewards", rewardManager.getIdleRewardName(), ChatColor.LIGHT_PURPLE, player.getName());
    }

    /**
     * Logs to the console that the provided player has claimed their idle rewards
     * @param player - the player whose claimed their item rewards
     */
    public void logClaim(@NotNull Player player) {
        log("%s claimed %sx '%s%s'", player.getName(),
                rewardManager.getClaimableIdleRewardAmount(player), rewardManager.getIdleRewardName(), ChatColor.LIGHT_PURPLE);
    }

    /**
     * Logs a message to the console with a light purple debug prefix. Note that the color is not reset between
     * the debug prefix and the message to be logged to the console.
     * @param message - the message to log to console
     * @param args - formatting arguments
     */
    private void log(@NotNull String message, Object... args) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[Debug] " + String.format(message, args));
    }

}
