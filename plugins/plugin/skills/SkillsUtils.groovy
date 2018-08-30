package plugin.skills

import com.asteria.game.character.player.Player

class SkillsUtils {

    static boolean checkInv(Player player) {
        if (player.inventory.remaining() < 1) {
            player.messages.sendMessage "You do not have any space left in your inventory."
            return false
        }
    }

}
