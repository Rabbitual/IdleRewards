package xyz.rabbitual.idlerewards.command;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.manager.RewardManager;
import xyz.rabbitual.idlerewards.reward.ClaimResult;

import java.util.Map;

public class ClaimCommand implements CommandExecutor {

    private final RewardManager rewardManager;

    public ClaimCommand(@NotNull RewardManager rewardManager) {
        Validate.notNull(rewardManager, "null reward manager");
        this.rewardManager = rewardManager;
    }

    /**
     * {@inheritDoc}
     * Gives the sender any pending idle rewards and informs them of their earnings
     * @return true
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to claim a reward.");
            return true;
        }

        claimReward((Player)sender);
        return true;
    }

    /**
     * Gives a player their pending idle rewards and updates the reward manager
     * @param player - the player to be rewarded
     */
    private void claimReward(@NotNull Player player) {
        int amount = rewardManager.getClaimableIdleRewardAmount(player);

        ClaimResult.Result result;
        if (amount == 0) {
            result = ClaimResult.Result.NOT_READY;
        } else {
            Map<Integer, ItemStack> leftovers = player.getInventory().addItem(rewardManager.getIdleReward(amount));

            if (leftovers.isEmpty()) {
                result = ClaimResult.Result.SUCCESSFULLY_CLAIMED;
                rewardManager.removeClaimableRewards(player);
            } else {
                result = ClaimResult.Result.INVENTORY_TOO_FULL;
                rewardManager.setIdleRewards(player, leftovers.get(0).getAmount());
            }

        }

        new ClaimResult(rewardManager, result, amount).notify(player);
    }

}
