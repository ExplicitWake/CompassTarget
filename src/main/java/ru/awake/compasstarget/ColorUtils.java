package ru.awake.compasstarget;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorUtils {

    public static String SUB_VERSION = Bukkit.getBukkitVersion().split("\\.")[1];
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F\\d]{6})");
    public static String color(String message) {
        if (SUB_VERSION.contains("-")) {
            SUB_VERSION = SUB_VERSION.split("-")[0];
        }
        if (Integer.parseInt(SUB_VERSION) >= 16) {
            Matcher matcher = HEX_PATTERN.matcher(message);
            StringBuilder builder = new StringBuilder(message.length() + 32);
            while (matcher.find()) {
                String group = matcher.group(1);
                matcher.appendReplacement(builder, "§x§" +
                        group.charAt(0) + "§" + group.charAt(1) + "§" +
                        group.charAt(2) + "§" + group.charAt(3) + "§" + group.charAt(4) + "§" +
                        group.charAt(5));
            }
            message = matcher.appendTail(builder).toString();
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> text) {
        return (List<String>) text.stream().map(ColorUtils::color).collect(Collectors.toList());
    }
}
