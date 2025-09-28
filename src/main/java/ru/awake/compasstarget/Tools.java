package ru.awake.compasstarget;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class Tools {

    public void targetCancel(Player player) {
        player.setCompassTarget(new Location(player.getWorld(), 0, 0, 0));
    }
    public void bukkitTaskCancel(BukkitTask bukkitTask) {
        if (bukkitTask != null && !bukkitTask.isCancelled()) {
            bukkitTask.cancel();
        }
    }

    public String getString (String path) {
        return CompassTarget.getInst().getConfig().getString(path);
    }

    public int getInt(String path) {
        return CompassTarget.getInst().getConfig().getInt(path);
    }

    public String getColorString (String path) {
        return ColorUtils.color(getString(path));
    }

    public List<String> getListString (String path) {
        return CompassTarget.getInst().getConfig().getStringList(path);
    }

    public List<String> getColorListString (String path) {
        return ColorUtils.color(getListString(path));
    }

}
