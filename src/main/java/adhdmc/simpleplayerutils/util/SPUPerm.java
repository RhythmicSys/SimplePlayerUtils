package adhdmc.simpleplayerutils.util;

import org.bukkit.permissions.Permission;

public enum SPUPerm {
    //Commands
    AFK(new Permission("spu.command.afk")),
    AFK_OTHERS(new Permission("spu.command.other.afk")),
    HAT(new Permission("spu.command.hat")),
    FLY(new Permission("spu.command.fly")),
    FLY_OTHERS(new Permission("spu.command.other.fly")),
    FLYSPEED(new Permission("spu.command.flyspeed")),
    FLYSPEED_OTHERS(new Permission("spu.command.other.flyspeed")),
    WALKSPEED(new Permission("spu.command.walkspeed")),
    WALKSPEED_OTHERS(new Permission("spu.command.other.walkspeed")),
    ANVIL(new Permission("spu.command.anvil")),
    ANVIL_OTHER(new Permission("spu.command.other.anvil")),
    CARTOGRAPHY(new Permission("spu.command.cartography")),
    CARTOGRAPHY_OTHER(new Permission("spu.command.other.cartography")),
    CRAFTING(new Permission("spu.command.crafting")),
    CRAFTING_OTHER(new Permission("spu.command.other.crafting")),
    ENDERCHEST(new Permission("spu.command.enderchest")),
    ENDERCHEST_OTHER(new Permission("spu.command.other.enderchest")),
    LOOM(new Permission("spu.command.loom")),
    LOOM_OTHER(new Permission("spu.command.other.loom")),
    SMITHING(new Permission("spu.command.smithing")),
    SMITHING_OTHER(new Permission("spu.command.other.smithing")),
    STONECUTTER(new Permission("spu.command.stonecutter")),
    STONECUTTER_OTHER(new Permission("spu.command.other.stonecutter")),
    GRINDSTONE(new Permission("spu.command.grindstone")),
    GRINDSTONE_OTHER(new Permission("spu.command.other.grindstone")),
    TRASH(new Permission("spu.command.trash")),
    TRASH_OTHER(new Permission("spu.command.other.trash")),
    REPAIR_HELD(new Permission("spu.command.repair.held")),
    REPAIR_HELD_OTHER(new Permission("spu.command.other.repair.held")),
    REPAIR_HOTBAR(new Permission("spu.command.repair.hotbar")),
    REPAIR_HOTBAR_OTHER(new Permission("spu.command.other.repair.hotbar")),
    REPAIR_ALL(new Permission("spu.command.repair.all")),
    REPAIR_ALL_OTHER(new Permission("spu.command.other.repair.all")),
    RENAME_BASIC(new Permission("spu.command.rename.basic")),
    RENAME_MINIMESSAGE(new Permission("spu.command.rename.minimessage")),
    RENAME_MAX_CHAR_BYPASS(new Permission("spu.command.rename.character.max.bypass")),
    RENAME_REGEX_BYPASS(new Permission("spu.command.rename.regex.bypass")),
    LORE_BASIC(new Permission("spu.command.lore.basic")),
    LORE_MINIMESSAGE(new Permission("spu.command.lore.minimessage")),
    RELOAD(new Permission("spu.reload"));
    
    final Permission perm;
    
    SPUPerm(Permission perm) {
        this.perm = perm;
    }
    
    public Permission getPerm() {
        return perm;
    }
}
