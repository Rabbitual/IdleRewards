package xyz.rabbitual.idlerewards.reward;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.manager.RewardManager;

public class ClaimResult {

    private final RewardManager rewardManager;
    private final Result result;
    private final int amount;

    public ClaimResult(@NotNull RewardManager rewardManager,
                       @NotNull Result result, int amount) {
        Validate.notNull(rewardManager, "null reward manager");
        Validate.notNull(result, "null result");
        this.rewardManager = rewardManager;
        this.result = result;
        this.amount = amount;
    }

    /**
     * Sends a message to the player claiming rewards. They will be informed of the amount
     * they have claimed or that there are not any rewards to claim yet, if such is the case.
     * @param player - the player to receive a message
     */
    public void notify(@NotNull Player player) {
        if (result == Result.SUCCESSFULLY_CLAIMED) {
            player.sendMessage(String.format("%sYou have claimed %sx '%s%1$s'", ChatColor.GREEN, amount, rewardManager.getIdleRewardName()));
        } else if (result == Result.NOT_READY) {
            player.sendMessage(String.format("%sYou do not have any '%s%1$s' to claim yet", ChatColor.RED, rewardManager.getIdleRewardName()));
        } else {
            player.sendMessage(ChatColor.RED + "Your inventory is too full! Make some room and try again.");
        }
    }

    public enum Result {
        NOT_READY,
        SUCCESSFULLY_CLAIMED,
        INVENTORY_TOO_FULL,
        ;
    }
}
