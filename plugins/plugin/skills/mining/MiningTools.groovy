package plugin.skills.mining

import com.asteria.game.character.player.Player
import com.asteria.game.item.Item
import com.asteria.utility.RandomGen
import plugin.skills.woodcutting.Woodcutting

enum MiningTools {

    BPICKAXE(1265, 1, 0.30)

    final int id
    final int level
    final double success
    static RandomGen random = new RandomGen()

    MiningTools(int id, int level, double success) {
        this.id = id
        this.level = level
        this.success = success
    }

    Item[] onMine(Player player, int oreId) {
        try {
            return [new Item(Mining.getOre(player, oreId).id)] as Item[]
        } catch (Exception e) {
            System.out.print("Failure to woodcut")
            return new Item(0, 0)
        }
    }
}
