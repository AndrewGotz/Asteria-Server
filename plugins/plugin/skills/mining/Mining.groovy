package plugin.skills.mining

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
import plugin.skills.woodcutting.Choppable

import java.util.concurrent.ThreadLocalRandom

import static com.asteria.game.character.player.skill.Skills.MINING

@PluginSignature(SkillAction.class)
class Mining extends HarvestingSkillAction {
    private def miningtool
    private int oreId
    private final ThreadLocalRandom random = ThreadLocalRandom.current()

    Mining(Player player, MiningTools tool, int oreId, Position position) {
        super(player, Optional.of(position))
        this.miningtool = tool
        this.oreId = oreId
    }

    @Override
    boolean init() {
        if (!checkMining())
            return false
        player.messages.sendMessage "You begin to mine the ore..."
        player.animation new Animation(625  )
        return true
    }

    @Override
    void onHarvest(Task t, Item item, boolean success) {
        if (success) {
            Minable c = Minable.getMinable(item.id)
            Skills.experience(player, c.experience, skill().id)
        }
    }

    @Override
    boolean canExecute() {
        if (!checkMining() || random.nextInt(50) == 0)
            return false
        return true
    }

    @Override
    Item[] harvestItems() {
        miningtool.onMine(player, oreId)
    }

    @Override
    double successFactor() {
        miningtool.success
    }

    @Override
    SkillData skill() {
        SkillData.MINING
    }

    @Override
    Optional<Animation> animation() {
        Optional.of(new Animation(625))
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

    private boolean checkMining() {
        if (miningtool == null) {
            player.messages.sendMessage "You need a pickaxe to mine here!"
            return false
        }
        int oreLevel = getOre(player, oreId).level
        if(!player.skills[MINING].reqLevel(oreLevel)) {
            player.messages.sendMessage "You must have a woodcutting level of ${oreLevel} to chop this."
            return false
        }

        SkillsUtils.checkInv(player)

        if (!player.skills[MINING].reqLevel(miningtool.level)) {
            player.messages.sendMessage "You must have a Mining level of ${miningtool.level} to use this tool."
            return false
        }

        return true
    }

    static Minable getOre(Player player, int oreId) {
        Skill skill = player.skills[MINING]
        for(Minable c : Minable.values()) {
            if(c.oreId == oreId) {
                return c
            }
        }
        return null
    }
}
