package ru.awake.compasstarget;

import org.bukkit.plugin.java.JavaPlugin;

public final class CompassTarget extends JavaPlugin {

    public static CompassTarget inst;

    public static CompassTarget getInst() {
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        getCommand("gps").setExecutor(new CommandClass());
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
