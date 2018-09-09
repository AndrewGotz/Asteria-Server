package plugin.skills

import com.asteria.game.character.player.Player
import plugin.skills.mining.MiningTools

class SkillsUtils {

    static boolean checkInv(Player player) {
        if (player.inventory.remaining() < 1) {
            player.messages.sendMessage "You do not have any space left in your inventory."
            return false
        }
        return true
    }

    private static ArrayList getMiningtools() {
        ArrayList<MiningTools> list = new ArrayList<>()
        for(MiningTools t : MiningTools.values()) {
            list.add(t)
        }
        return list
    }

    static MiningTools checkMiningTools(Player player) {
        ArrayList<MiningTools> list = getMiningtools()
        for(MiningTools t : list) {
            if(player.equipment.contains(t.id)) {
                return t
            }
            if(player.inventory.contains(t.id)) {
                return t
            }
        }
        return null
    }

}
