package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.Skill
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.item.Item
import com.asteria.utility.RandomGen
import plugin.skills.SkillsUtils
import plugin.skills.fishing.Catchable

enum WCTools {

    BHATCHET(1351, 1, 0.30)

    final int id
    final int level
    final double success
    static RandomGen random = new RandomGen()

    WCTools(int id, int level, double success) {
        this.id = id
        this.level = level
        this.success = success
    }

    Item[] onChop(Player player, int treeId) {
        try {
            return [new Item(Woodcutting.getTree(player, treeId).id)] as Item[]
        } catch (Exception e) {
            System.out.print("Failure to woodcut")
            return new Item(0, 0)
        }
    }
}
