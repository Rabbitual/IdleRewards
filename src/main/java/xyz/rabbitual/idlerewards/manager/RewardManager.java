package xyz.rabbitual.idlerewards.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.debug.IdleRewardLogger;
import xyz.rabbitual.idlerewards.reward.IdleRewardTimers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class RewardManager {

    private final String idleRewardName;
    private final ItemStack idleReward;

    private final IdleRewardTimers timers;
    private final IdleRewardLogger rewardLogger;
    private final Map<UUID, Integer> rewardsMap;

    public RewardManager(@NotNull IdleRewardTimers timers) {
        this.timers = timers;
        this.rewardLogger = new IdleRewardLogger(this);
        this.rewardsMap = new HashMap<>();

        idleRewardName = ChatColor.DARK_PURPLE + "Lazy Shard";
        idleReward = new ItemStack(Material.PRISMARINE_SHARD);

        ItemMeta meta = Objects.requireNonNull(idleReward.getItemMeta());
        meta.setDisplayName(idleRewardName);

        idleReward.setItemMeta(meta);
    }

    /**
     * Gets the configured idle reward name
     * @return the idle reward name
     */
    @NotNull
    public String getIdleRewardName() {
        return idleRewardName;
    }

    /**
     * Gets the idle reward as an ItemStack with the provided amount as its size
     * @param amount - the amount of the idle reward to be given
     * @return the idle reward as an ItemStack with the amount as its size
     */
    @NotNull
    public ItemStack getIdleReward(int amount) {
        ItemStack amtReward = idleReward.clone();
        amtReward.setAmount(amount);
        return amtReward;
    }

    /**
     * Adds one idle reward to the provided player
     * @param player - the player to have claimable idle rewards added
     */
    public void addIdleReward(@NotNull Player player) {
        rewardsMap.compute(player.getUniqueId(), ((uuid, oldAmount) -> oldAmount == null ? 1 : ++oldAmount));
        rewardLogger.logAddedReward(player);
    }

    /**
     * Sets the claimable idle reward amount for the specified player
     * @param player - the player to have idle rewards set
     * @param amount - the amount to set the claimable idle rewards to
     */
    public void setIdleRewards(@NotNull Player player, int amount) {
        rewardsMap.put(player.getUniqueId(), amount);
    }

    /**
     * Skips the player's ongoing idle reward timer and resumes a new one
     * @param player - the player to skip the idle reward timer of
     */
    public void skip(@NotNull Player player) {
        addIdleReward(player);
        timers.scheduleTask(player);
    }

    /**
     * Gets the amount of claimable idle rewards for the specified player
     * @param player - the player to get the idle rewards amount of
     * @return the amount of claimable idle rewards
     */
    public int getClaimableIdleRewardAmount(@NotNull Player player) {
        return rewardsMap.getOrDefault(player.getUniqueId(), 0);
    }

    /**
     * Removes all claimable idle rewards for the specified player
     * @param player - the player to clear the claimable idle rewards of
     */
    public void removeClaimableRewards(@NotNull Player player) {
        rewardsMap.remove(player.getUniqueId());
        rewardLogger.logClaim(player);
    }

}
