package xyz.rabbitual.idlerewards.command;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.manager.RewardManager;
import xyz.rabbitual.idlerewards.reward.IdleRewardTimers;

public class RewardsCommand implements CommandExecutor {

    private final RewardManager rewardManager;
    private final IdleRewardTimers timers;

    public RewardsCommand(@NotNull RewardManager rewardManager, @NotNull IdleRewardTimers timers) {
        this.rewardManager = rewardManager;
        this.timers = timers;
    }

    /**
     * {@inheritDoc}
     * Shows the player sender their claimable idle rewards and the duration of their current ongoing idle reward timer
     * @return true
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command");
            return true;
        }

        Player player = (Player)sender;

        int amount = rewardManager.getClaimableIdleRewardAmount(player);
        String idleRewardName = rewardManager.getIdleRewardName();

        long nextAddTime = timers.getNextAddTime(player);
        String formattedTimeLeft = DurationFormatUtils.formatDuration(nextAddTime - System.currentTimeMillis(), "H:mm:ss", true);

        player.sendMessage(String.format("%sYou have %sx claimable '%s%1$s'. Another will be added in %s.",
                ChatColor.YELLOW, amount, idleRewardName, formattedTimeLeft));
        return true;
    }

}
