package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.Skill
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.item.Item
import com.asteria.utility.RandomGen
import plugin.skills.fishing.Catchable

enum WCTool {

    BHatchet(1351, 1, 0.35),

    final int id
    final int level
    final double success
    static RandomGen random = new RandomGen()

    WCTool(int id, int level, int success) {
        this.id = id
        this.level = level
        this.success = success
    }

    @Override
    String toString() {
        name().toLowerCase().replaceAll("_", " ")
    }

    Choppable choppable() {
        null
    }

    Item[] onHarvest(Player player) {
        [new Item(calculate(player).id)] as Item[]
    }

/*    Choppable calculate(Player player) {
        List<Catchable> success = new ArrayList<>(choppable.length)
        Skill skill = player.skills[Skills.WOODCUTTING]
        choppable().findAll {skill.reqLevel(it.level) && it.choppable(player) }.each { success.add it }
        Collections.shuffle(success, random.get())
        return success.find { random.success(it.chance) } ?: choppable()
    }*/

  /*  Item[] onCatch(Player player) {
        [new Item(calculate(player).id)] as Item[]
    }*/
}
