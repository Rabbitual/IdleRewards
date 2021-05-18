package xyz.rabbitual.idlerewards;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.rabbitual.idlerewards.command.ClaimCommand;
import xyz.rabbitual.idlerewards.command.RewardsCommand;
import xyz.rabbitual.idlerewards.command.SkipCommand;
import xyz.rabbitual.idlerewards.listener.PlayerJoinListener;
import xyz.rabbitual.idlerewards.manager.RewardManager;
import xyz.rabbitual.idlerewards.reward.IdleRewardTimers;

import java.util.Objects;

public class IdleRewardsPlugin extends JavaPlugin {

    private final IdleRewardTimers rewardTimers = new IdleRewardTimers(this);
    private final RewardManager rewardManager = new RewardManager(rewardTimers);

    /**
     * Gets the this plugin's reward manager instance
     * @return the sole reward manager instance
     */
    @NotNull
    public RewardManager getRewardManager() {
        return rewardManager;
    }

    /**
     * Gets this plugin's idle reward timers
     * @return the sole IdleRewardTimers instance
     */
    @NotNull
    public IdleRewardTimers getTimers() {
        return rewardTimers;
    }

    /**
     * Register event listeners and sets command executors
     */
    @Override
    @SuppressWarnings("unused")
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        registerCommand("claim", new ClaimCommand(rewardManager));
        registerCommand("rewards", new RewardsCommand(rewardManager, rewardTimers));
        registerCommand("skip", new SkipCommand(rewardManager));
    }

    /**
     * Registers a plugin command with a command executor
     * @param commandName - the name of the command to be registered
     * @param commandExecutor - the command executor to execute the command with the specified name
     */
    private void registerCommand(@NotNull String commandName, @NotNull CommandExecutor commandExecutor) {
        Objects.requireNonNull(getCommand(commandName)).setExecutor(commandExecutor);
    }

}
