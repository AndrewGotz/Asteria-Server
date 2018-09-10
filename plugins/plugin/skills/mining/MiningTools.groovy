package plugin.skills.mining

import com.asteria.game.character.player.Player
import com.asteria.game.item.Item
import com.asteria.utility.RandomGen

enum MiningTools {

    BPICKAXE(1265, 1, 0.10),
    IRONPICKAXE(1267, 10, 0.15),
    MITHRILPICKAXE(1273, 20, 0.25),
    ADAMANTPICKAXE(1271, 30, 0.30),
    RUNEPICKAXE(1275, 40, 0.85),

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
            System.out.print("Failure to mine")
            return new Item(0, 0)
        }
    }
}
