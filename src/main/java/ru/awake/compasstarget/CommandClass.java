package ru.awake.compasstarget;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CommandClass implements CommandExecutor {

    public BukkitTask updateDistance;
    Tools tools = new Tools();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(tools.getColorString("messages.only-player"));
            return true;
        }
        if (strings.length == 0) {
            for (String string : tools.getColorListString("messages.help")) {
                sender.sendMessage(string);
            }
            return true;
        }



        Player player = (Player) sender;

        if (strings.length == 1 && strings[0].equalsIgnoreCase("cancel")) {
            tools.targetCancel(player);
            player.sendMessage(tools.getColorString("messages.cancel"));
            tools.bukkitTaskCancel(updateDistance);
            return true;
        }

        if (strings.length == 3) {
            if (strings[0].matches("-?\\d+(\\.\\d+)?") &&
                    strings[1].matches("-?\\d+(\\.\\d+)?") &&
                    strings[2].matches("-?\\d+(\\.\\d+)?")) {
                double x = Double.parseDouble(strings[0]);
                double y = Double.parseDouble(strings[1]);
                double z = Double.parseDouble(strings[2]);

                Location location = new Location(player.getWorld(), x, y, z);
                player.setCompassTarget(location);
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ROOT);
                DecimalFormat format = new DecimalFormat("#.#", symbols);
                for (String string : tools.getColorListString("messages.successful-input")) {
                    String formatted = string.replace("{x}", String.valueOf(x));
                    formatted = formatted.replace("{y}", String.valueOf(y));
                    formatted = formatted.replace("{z}", String.valueOf(z));
                    sender.sendMessage(formatted);
                }

                tools.bukkitTaskCancel(updateDistance);

                updateDistance = Bukkit.getScheduler().runTaskTimer(CompassTarget.getInst(), () -> {

                    double distance = player.getLocation().distance(location);
                    String distanceS = format.format(distance);

                    if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) {
                        if (distance > tools.getInt("distance")) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(tools.getColorString("messages.actionbar.way")
                                    .replace("{blocks}", distanceS)));
                        } else {
                            tools.targetCancel(player);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(tools.getColorString("messages.actionbar.place")));
                            tools.bukkitTaskCancel(updateDistance);
                        }
                    }
                }, 0, 20);
            } else {
                sender.sendMessage(tools.getColorString("messages.only-numbers"));
            }
        } else {
            for (String string : tools.getColorListString("messages.help")) {
                sender.sendMessage(string);
            }
        }
        return true;
    }
}
