package plugin.objects

import plugin.minigames.fightcaves.FightCavesHandler

import com.asteria.game.character.Animation
import com.asteria.game.character.player.Player
import com.asteria.game.character.player.content.Spellbook
import com.asteria.game.character.player.content.ViewingOrb
import com.asteria.game.character.player.skill.Skills
import com.asteria.game.location.Position
import com.asteria.game.plugin.PluginListener
import com.asteria.game.plugin.PluginSignature
import com.asteria.game.plugin.context.ObjectFirstClickPlugin
import plugin.skills.SkillsUtils
import plugin.skills.mining.Mining
import plugin.skills.mining.MiningTools
import plugin.skills.woodcutting.WCTools
import plugin.skills.woodcutting.Woodcutting

@PluginSignature(ObjectFirstClickPlugin.class)
final class ObjectFirstClick implements PluginListener<ObjectFirstClickPlugin> {


    @Override
    void execute(Player player, ObjectFirstClickPlugin context) {
        int id = context.id
        Position position = context.position.copy()
        switch (context.id) {
            case 9391:
                player.viewingOrb = new ViewingOrb(player, new Position(2398, 5150),
                new Position(2384, 5157), new Position(2409, 5158), new Position(2388, 5138),
                new Position(2411, 5137))
                player.viewingOrb.open()
                break
            case 9368:
                if (player.position.y <= 5167 && FightCavesHandler.players.remove(player)) {
                    player.move new Position(2399, 5169)
                    player.combatBuilder.reset()
                    FightCavesHandler.awaiting.add player
                    player.messages.sendMessage "You forfeit the fight pits minigame!"
                    player.messages.sendWalkable 2804
                    FightCavesHandler.display player
                    player.messages.sendContextMenu(1, "null")
                    FightCavesHandler.end false
                }
                break
            case 9369:
                if (FightCavesHandler.awaiting.contains(player)) {
                    FightCavesHandler.awaiting.remove player
                    player.messages.sendMessage "You exit the fight pits minigame waiting room!"
                    player.move new Position(2399, 5177)
                    player.messages.sendWalkable(-1)
                } else if (!FightCavesHandler.awaiting.contains(player)) {
                    int minutes = FightCavesHandler.GAME_CYCLE_MINUTES - FightCavesHandler.gameCounter
                    FightCavesHandler.awaiting.add player
                    player.messages.sendMessage "You enter the fight pits minigame waiting room!"
                    player.move new Position(2399, 5175)
                    player.messages.sendWalkable 2804
                    FightCavesHandler.display player
                    FightCavesHandler.awaiting.each { FightCavesHandler.display it }
                }
                break
            case 11758:
                player.bank.open()
                break
            case 3193:
            case 2213:
                player.bank.open()
                break
            case 409:
                int level = player.skills[Skills.PRAYER].realLevel
                if (player.skills[Skills.PRAYER].level < level) {
                    player.animation new Animation(645)
                    player.skills[Skills.PRAYER].setLevel(level, true)
                    player.messages.sendMessage "You recharge your prayer points."
                    Skills.refresh(player, Skills.PRAYER)
                } else {
                    player.messages.sendMessage "You already have full prayer points."
                }
                break
            case 6552:
                if (player.spellbook == Spellbook.ANCIENT) {
                    Spellbook.convert(player, Spellbook.NORMAL)
                } else if (player.spellbook == Spellbook.NORMAL) {
                    Spellbook.convert(player, Spellbook.ANCIENT)
                }
                break
            //Woodcutting junk.
            case 1276:
                Woodcutting wood = new Woodcutting(player, WCTools.BHATCHET, 1276, position)
                wood.start()
                break
            case 1281:
                Woodcutting wood = new Woodcutting(player, WCTools.BHATCHET, 1281, position)
                wood.start()
                break
            case 1278:
                Woodcutting wood = new Woodcutting(player, WCTools.BHATCHET, id, position)
                wood.start()
                break
            //Mining junk
            case 2091:
                Mining mine = new Mining(player, SkillsUtils.checkMiningTools(player), id, position)
                mine.start()
                break
            case 2095:
                Mining mine = new Mining(player, SkillsUtils.checkMiningTools(player), id, position)
                mine.start()
                break

        }
    }
}
