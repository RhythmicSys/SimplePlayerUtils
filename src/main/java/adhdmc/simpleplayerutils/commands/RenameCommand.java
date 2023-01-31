package adhdmc.simpleplayerutils.commands;

import adhdmc.simpleplayerutils.SimplePlayerUtils;
import adhdmc.simpleplayerutils.util.SPUMessage;
import adhdmc.simpleplayerutils.util.SPUPerm;
import adhdmc.simpleplayerutils.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RenameCommand implements CommandExecutor, TabCompleter {
    MiniMessage miniMessage = SimplePlayerUtils.getMiniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Console cannot run this
        if (!(sender instanceof Player player)) {
            sender.sendMessage(miniMessage.deserialize(SPUMessage.ERROR_ONLY_PLAYER.getMessage()));
            return false;
        }
        //Check perms
        if (!(player.hasPermission(SPUPerm.RENAME_BASIC.getPerm()) || player.hasPermission(SPUPerm.RENAME_MINIMESSAGE.getPerm()))) {
            sender.sendMessage(Util.messageParsing(SPUMessage.ERROR_NO_PERMISSION.getMessage(),
                    null, null, null, null, null, null, null));
            return false;
        }
        String renameString = String.join(" ", Arrays.stream(args).skip(0).collect(Collectors.joining(" ")));
        String strippedInput = miniMessage.stripTags(renameString);
        int maxChars = SimplePlayerUtils.getInstance().getConfig().getInt("rename-max-characters");
        if ((strippedInput.length() > maxChars) &&
                !player.hasPermission(SPUPerm.RENAME_MAX_CHAR_BYPASS.getPerm())) {
            player.sendMessage(Util.messageParsing(SPUMessage.RENAME_ERROR_INPUT_TOO_LONG.getMessage(),
                    null, null, (double) maxChars, null, null, null, null));
            return false;
        }
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        ItemMeta heldItemMeta = heldItem.getItemMeta();
        Component newItemName;
        if (player.hasPermission(SPUPerm.RENAME_MINIMESSAGE.getPerm())) {
            newItemName = miniMessage.deserialize(renameString).decoration(TextDecoration.ITALIC, false);
            heldItemMeta.displayName(newItemName);
            heldItem.setItemMeta(heldItemMeta);
            player.sendMessage(Util.messageParsing(SPUMessage.RENAME_COMMAND_FEEDBACK.getMessage(),
                    null, null, null, null, null, null, renameString));
            return true;
        }
        if (player.hasPermission(SPUPerm.RENAME_BASIC.getPerm())) {
            newItemName = miniMessage.deserialize(strippedInput);
            heldItemMeta.displayName(newItemName);
            heldItem.setItemMeta(heldItemMeta);
            player.sendMessage(Util.messageParsing(SPUMessage.RENAME_COMMAND_FEEDBACK.getMessage(),
                    null, null, null, null, null, null, strippedInput));
            return true;
        }
        player.sendMessage(Util.messageParsing(SPUMessage.ERROR_GENERAL.getMessage(),
                null, null, null, null, null, null, null));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
