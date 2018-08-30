package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.Skill
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.item.Item
import com.asteria.utility.RandomGen
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

    Choppable calculate(Player player, int treeId) {
        Skill skill = player.skills[Skills.WOODCUTTING]
        for(Choppable c : Choppable.values()) {
            for(int i = 0; i < c.treeIdList.size(); i++) {
                if(c.treeIdList[i] == treeId && skill.reqLevel(c.level) ) {
                    return c
                }
            }
        }
        return null
    }

    Item[] onChop(Player player, int treeId) {
        [new Item(calculate(player, treeId).id)] as Item[]
    }
}
