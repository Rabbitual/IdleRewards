package xyz.rabbitual.idlerewards.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.manager.RewardManager;

public class SkipCommand implements CommandExecutor {

    private final RewardManager rewardManager;

    public SkipCommand(@NotNull RewardManager rewardManager) {
        this.rewardManager = rewardManager;
    }

    /**
     * {@inheritDoc}
     * Skips the player sender's currently ongoing idle reward timer and starts a new one
     * @return true
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to skip your idle reward timer");
            return true;
        }

        rewardManager.skip((Player)sender);
        sender.sendMessage(String.format("%sSkipped your idle reward timer and added 1x '%s%1$s' to your claimable idle rewards. "
                + "You couldn't wait, could ya? Tsk, tsk.", ChatColor.GOLD, rewardManager.getIdleRewardName()));

        return true;
    }

}
