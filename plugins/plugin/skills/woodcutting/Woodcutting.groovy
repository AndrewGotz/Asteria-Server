package plugin.skills.woodcutting

import com.asteria.game.character.Animation
import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.SkillData
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.character.player.skill.action.HarvestingSkillAction
import com.asteria.game.item.Item
import com.asteria.game.location.Position
import com.asteria.task.Task
import plugin.skills.fishing.Catchable
import plugin.skills.fishing.Tool

import java.util.concurrent.ThreadLocalRandom

import static com.asteria.game.character.player.skill.Skills.WOODCUTTING

class Woodcutting extends HarvestingSkillAction {

    private final ThreadLocalRandom random = ThreadLocalRandom.current()
    private WCTool tool
    private int treeId

    Woodcutting(Player player, int treeId) {
        super(player)
        this.treeId = treeId
    }

    @Override
    boolean canExecute() {
        if (!checkWoodcutting() || random.nextInt(50) == 0)
            return false
        return true
    }

    @Override
    void onHarvest(Task t, Item item, boolean success) {
        if (success) {
            Catchable c = Choppable.getChoppable(item.id)
            Skills.experience(player, c.experience, skill().id)
        }
    }

    @Override
    boolean init() {
        if (!checkWoodcutting())
            return false
        player.messages.sendMessage "You begin to chop..."
        player.animation new Animation(875 )
        return true
    }

    @Override
    Item[] harvestItems() {
        tool.onHarvest(player)
    }

    @Override
    double successFactor() {
        tool.success
    }

    @Override
    Optional<Animation> animation() {
        return new Animation(875)
    }

    @Override
    Optional<Item[]> removeItems() {
        if (tool.needed <= 0)
            return Optional.empty()
        return Optional.of([new Item(tool.needed, 1)] as Item[])
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
    SkillData skill() {
        SkillData.WOODCUTTING
    }

    @Override
    void onStop() {
        player.animation null
    }

    private boolean checkWoodcutting() {
        if (!player.inventory.contains(tool.id) || !player.weapon) {
            player.messages.sendMessage "You need a hatchet to cut a tree!"
            return false
        }
        if (player.inventory.remaining() < 1) {
            player.messages.sendMessage "You do not have any space left in your inventory."
            return false
        }
        if (!player.skills[WOODCUTTING].reqLevel(tool.level) || !player.skills[]) {
            player.messages.sendMessage "You must have a woodcutting level of ${tool.level} to use this tool."
            return false
        }
        return true
    }
}
