package adhdmc.simpleplayerutils.commands;

import adhdmc.simpleplayerutils.SimplePlayerUtils;
import adhdmc.simpleplayerutils.util.SPUMessage;
import adhdmc.simpleplayerutils.util.SPUPerm;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HatCommand implements CommandExecutor, TabCompleter {
    MiniMessage miniMessage = SimplePlayerUtils.getMiniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(miniMessage.deserialize(SPUMessage.ERROR_ONLY_PLAYER.getMessage()));
            return false;
        }
        if (!player.hasPermission(SPUPerm.HAT.getPerm())) {
            sender.sendMessage(miniMessage.deserialize(SPUMessage.ERROR_NO_PERMISSION.getMessage()));
            return false;
        }
        ItemStack handItem = player.getInventory().getItemInMainHand();
        ItemStack helmetItem = player.getInventory().getHelmet();
        if (helmetItem != null) {
            ItemMeta helmetMeta = helmetItem.getItemMeta();
            if (helmetMeta.hasEnchant(Enchantment.BINDING_CURSE)) {
                player.sendMessage(miniMessage.deserialize(SPUMessage.HAT_ERROR_BINDING.getMessage(),
                        Placeholder.parsed("plugin_prefix", SPUMessage.PLUGIN_PREFIX.getMessage())));
                return false;
            }
        }
        if (handItem.getType() != Material.AIR) {
            player.getInventory().setHelmet(handItem);
            player.getInventory().setItemInMainHand(helmetItem);
            player.sendMessage(miniMessage.deserialize(SPUMessage.HAT_OUTPUT.getMessage(),
                    Placeholder.parsed("plugin_prefix", SPUMessage.PLUGIN_PREFIX.getMessage())));
            return true;
        } else {
            player.sendMessage(miniMessage.deserialize(SPUMessage.HAT_ERROR_HAND_EMPTY.getMessage(),
                    Placeholder.parsed("plugin_prefix", SPUMessage.PLUGIN_PREFIX.getMessage())));
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}