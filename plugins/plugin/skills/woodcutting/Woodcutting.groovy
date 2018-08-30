package plugin.skills.woodcutting

import com.asteria.game.character.Animation
import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.Skill
import com.asteria.game.character.player.skill.SkillData
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.character.player.skill.action.HarvestingSkillAction
import com.asteria.game.character.player.skill.action.SkillAction
import com.asteria.game.item.Item
import com.asteria.game.location.Position
import com.asteria.game.plugin.PluginSignature
import com.asteria.task.Task
import plugin.skills.SkillsUtils

import java.util.concurrent.ThreadLocalRandom

import static com.asteria.game.character.player.skill.Skills.WOODCUTTING

@PluginSignature(SkillAction.class)
class Woodcutting extends HarvestingSkillAction {

    private def wcTool
    private int treeId
    private final ThreadLocalRandom random = ThreadLocalRandom.current()

    Woodcutting(Player player, WCTools tool, int treeId, Position position) {
        super(player, Optional.of(position))
        this.wcTool = tool
        this.treeId = treeId
    }

    @Override
    boolean init() {
        if (!checkWoodcutting())
            return false
        player.messages.sendMessage "You begin to cut the tree..."
        player.animation new Animation(879 )
        return true
    }

    @Override
    void onHarvest(Task t, Item item, boolean success) {
        if (success) {
            Choppable c = Choppable.getChoppable(item.id)
            Skills.experience(player, c.experience, skill().id)
        }
    }

    @Override
    boolean canExecute() {
        if (!checkWoodcutting() || random.nextInt(50) == 0)
            return false
        return true
    }

    @Override
    Item[] harvestItems() {
        wcTool.onChop(player, treeId)
    }

    @Override
    double successFactor() {
        wcTool.success
    }

    @Override
    SkillData skill() {
        SkillData.WOODCUTTING
    }

    @Override
    Optional<Animation> animation() {
        Optional.of(new Animation(879))
    }

    @Override
    boolean instant() {
        false
    }

    @Override
    double experience() {
        0 // Experience handled elsewhere.
    }

    @Override
    Optional<Item[]> removeItems() {
        return Optional.empty()
    }

    @Override
    void onStop() {
        player.animation null
    }

    private boolean checkWoodcutting() {
        if (!player.inventory.contains(wcTool.id) && !player.equipment.contains(wcTool.id)) {
            player.messages.sendMessage "You need a ${wcTool} to cut here!"
            return false
        }
        int treeLevel = getTree(player, treeId).level
        if(!player.skills[WOODCUTTING].reqLevel(treeLevel)) {
            player.messages.sendMessage "You must have a woodcutting level of ${treeLevel} to chop this."
            return false
        }

        SkillsUtils.checkInv(player)

        if (!player.skills[WOODCUTTING].reqLevel(wcTool.level)) {
            player.messages.sendMessage "You must have a Fishing level of ${wcTool.level} to use this tool."
            return false
        }

        return true
    }

    static Choppable getTree(Player player, int treeId) {
        Skill skill = player.skills[WOODCUTTING]
        for(Choppable c : Choppable.values()) {
            for(int i = 0; i < c.treeIdList.size(); i++) {
                if(c.treeIdList[i] == treeId) {
                    return c
                }
            }
        }
        return null
    }
}
