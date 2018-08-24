package plugins.plugin.skills.mining

import com.asteria.game.character.Animation
import com.asteria.game.character.player.Player
import com.asteria.game.character.player.skill.SkillData
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.character.player.skill.action.HarvestingSkillAction
import com.asteria.game.character.player.skill.action.SkillAction
import com.asteria.game.item.Item
import com.asteria.game.location.Position
import com.asteria.game.plugin.PluginSignature
import com.asteria.task.Task
import plugins.plugin.skills.fishing.Tool

import java.util.concurrent.ThreadLocalRandom

@PluginSignature(SkillAction.class)
class Mining extends HarvestingSkillAction {

    // TODO: Better Messages/Checks for mining?

    private Tool tool
    private final ThreadLocalRandom random = ThreadLocalRandom.current()

    Mining(Player player, Tool tool, Position position) {
        super(player, Optional.of(position))
        this.tool = tool
    }

    @Override
    boolean canExecute() {
        if (!checkMining() || random.nextInt(50) == 0)
            return false
        return true
    }

    @Override
    boolean init() {
        if (!checkMining())
            return false
        player.messages.sendMessage "You swing your pick at the rock..."
        player.animation new Animation(tool.animation)
        return true
    }

    @Override
    def harvestItems() {
        tool.onCatch(player)
    }

    @Override
    void onHarvest(Task t, Item item, boolean success) {
        if (success) {
            Rocks c = Rocks.getRock(item.id)
            Skills.experience(player, c.experience, skill().id)
        }
    }

    @Override
    double successFactor() {
        tool.success
    }

    @Override
    Optional<Animation> animation() {
        Optional.of(new Animation(tool.animation))
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
        SkillData.MINING
    }

    @Override
    void onStop() {
        player.animation null
    }

    private boolean checkMining() {
        if (!player.inventory.contains(tool.id)) {
            player.messages.sendMessage "You need a pickaxe to mine the rock!"
            return false
        }
        if (player.inventory.remaining() < 1) {
            player.messages.sendMessage "You do not have any space left in your inventory."
            return false
        }
        if (!player.skills[MINING].reqLevel(tool.level)) {
            player.messages.sendMessage "You must have a Mining.java level of TODO to use this tool."
            return false
        }
        return true
    }
}
