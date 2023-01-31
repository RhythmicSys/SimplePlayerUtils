package adhdmc.simpleplayerutils.commands;

import adhdmc.simpleplayerutils.SimplePlayerUtils;
import adhdmc.simpleplayerutils.util.SPUMessage;
import adhdmc.simpleplayerutils.util.SPUPerm;
import adhdmc.simpleplayerutils.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WalkspeedCommand implements CommandExecutor, TabCompleter {
    MiniMessage miniMessage = SimplePlayerUtils.getMiniMessage();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //If the user has neither the permission to set their own Walkspeed, or others', return after sending an error
        if (!(sender.hasPermission(SPUPerm.WALKSPEED.getPerm())||sender.hasPermission(SPUPerm.WALKSPEED_OTHERS.getPerm()))) {
            sender.sendMessage(miniMessage.deserialize(SPUMessage.ERROR_NO_PERMISSION.getMessage(),
                    Placeholder.parsed("plugin_prefix", SPUMessage.PLUGIN_PREFIX.getMessage())));
            return false;
        }
        FileConfiguration config = SimplePlayerUtils.getInstance().getConfig();
        float maxForMessage = config.getInt("max-walkspeed");
        float minForMessage = config.getInt("min-walkspeed");
        float maxSpeed = config.getInt("max-walkspeed");
        float minSpeed = config.getInt("min-walkspeed");
        maxSpeed = maxSpeed/10;
        minSpeed = minSpeed/10;
        //If the user has the permission to set others' Walkspeed, and there are 2 arguments, go through this
        if (sender.hasPermission(SPUPerm.WALKSPEED_OTHERS.getPerm()) && args.length == 2){
            //Name to use in messages, if it's a player, use the player's displayname, if it's not, use the console format from the message file
            Component senderName;
            if (sender instanceof Player player) {
                senderName = player.displayName();
            } else {
                senderName = miniMessage.deserialize(SPUMessage.CONSOLE_FORMAT.getMessage());
            }
            //Match a player to the first argument, if there is no player, error and return
            Player player = SimplePlayerUtils.getInstance().getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(Util.messageParsing(SPUMessage.ERROR_NO_VALID_PLAYER_SUPPLIED.getMessage(),
                        miniMessage.deserialize(args[0]), null, null, null, null, null, null));
                return false;
            }
            //If the argument after the player name is 'reset', set their Walk speed to the default, and let both the sender and player know, and return
            if (args[1].equalsIgnoreCase("reset")) {
                player.setWalkSpeed(0.2f);
                sender.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_RESET_OTHER.getMessage(),
                        player.displayName(), null, null, null, null, null, null));
                player.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_RESET_BY_OTHER.getMessage(),
                        null, senderName, null, null, null, null, null));
                return true;
            }
            //If the argument after the player name is 'get', inform the sender of the player's current WALKspeed, *10, as that's the numbers that are used, instead of the floats, and return
            if (args[1].equalsIgnoreCase("get")) {
                sender.sendMessage(Util.messageParsing(SPUMessage.OTHER_CURRENT_WALKSPEED.getMessage(),
                        player.displayName(), null, (double) (player.getWalkSpeed() * 10), null, null, null, null));
                return true;
            }
            //Supposing neither of those options were gone down, try to cast the second argument to a float. If it doesn't cast, error and let the sender know, and return
            try {
                float speed = Float.parseFloat(args[1]);
            } catch (NumberFormatException formatException) {
                sender.sendMessage(Util.messageParsing(SPUMessage.SPEED_NUMBER_ERROR.getMessage(),
                        null, null, null, minForMessage, maxForMessage, null, null));
                return false;
            }
            float speed = Float.parseFloat(args[1]);
            speed = speed/10;
            //Divide the number by 10, so it actually fits in the -1 to 1 range, if it still doesn't fit, error and return
            if (!((speed >= -1) && (speed >= minSpeed) && (speed <= maxSpeed) && (speed <= 1))) {
                sender.sendMessage(Util.messageParsing(SPUMessage.SPEED_NUMBER_ERROR.getMessage(),
                        null, null, null, minForMessage, maxForMessage, null, null));
                return false;
            }
            //Set the WALK speed, send a message to both the sender and player informing them of the successful speed change, and return
            player.setWalkSpeed(speed);
            sender.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_SET_OTHER.getMessage(),
                    player.displayName(), null, (double) (speed * 10), null, null, null, null));
            player.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_SET_BY_OTHER.getMessage(),
                    null, senderName, (double) (speed * 10), null, null, null, null));
            return true;
        }
        //If player doesn't have permission to set their own speed, error and return
        if (!sender.hasPermission(SPUPerm.WALKSPEED.getPerm())) {
            sender.sendMessage(Util.messageParsing(SPUMessage.ERROR_NO_PERMISSION.getMessage(),
                    null, null, null, null, null, null, null));
            return false;
        }
        //If there are any console command senders at this point, they are running a command wrong, error and return
        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage(Util.messageParsing(SPUMessage.ERROR_ONLY_PLAYER.getMessage(),
                    null, null, null, null, null, null, null));
            return false;
        }
        //If the argument after the player name is 'get', inform the player of their current WALKspeed, *10, as that's the numbers that are used, instead of the floats, and return
        if (args.length == 0 ||args[0].equalsIgnoreCase("get")) {
            playerSender.sendMessage(Util.messageParsing(SPUMessage.OWN_CURRENT_WALKSPEED.getMessage(),
                    null, null, (double) (playerSender.getWalkSpeed() * 10), null, null, null, null));
            return true;
        }
        //If the is 'reset', set their WALK speed to the default, and let both the player know, and return
        if (args[0].equalsIgnoreCase("reset")) {
            playerSender.setWalkSpeed(0.2f);
            playerSender.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_RESET.getMessage(),
                    null, null, null, null, null, null, null));
            return true;
        }
        //Supposing neither of those options were gone down, try to cast the second argument to a float. If it doesn't cast, error and let the player know, and return
        try {
            float speed = Float.parseFloat(args[0]);
        } catch (NumberFormatException formatException) {
            playerSender.sendMessage(Util.messageParsing(SPUMessage.SPEED_NUMBER_ERROR.getMessage(),
                    null, null, null, minForMessage, maxForMessage, null, null));
            return false;
        }
        float speed = Float.parseFloat(args[0]);
        speed = speed/10;
        //Divide the number by 10, so it actually fits in the -1 to 1 range, if it still doesn't fit, error and return
        if (!((speed >= -1) && (speed >= minSpeed) && (speed <= maxSpeed) && (speed <= 1))) {
            playerSender.sendMessage(Util.messageParsing(SPUMessage.SPEED_NUMBER_ERROR.getMessage(),
                    null, null, null, minForMessage, maxForMessage, null, null));
            return false;
        }
        //Set the WALK speed, send a message to the player informing them of the successful speed change, and return
        playerSender.setWalkSpeed(speed);
        sender.sendMessage(Util.messageParsing(SPUMessage.WALKSPEED_SET.getMessage(),
                null, null, (double) (speed * 10), minForMessage, maxForMessage, null, null));
        return true;
    }



    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
