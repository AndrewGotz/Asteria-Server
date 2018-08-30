package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.google.common.collect.ImmutableSet

enum Choppable {

    LOGS(1511, 1, 0.85, 30, [1276, 1278]),
    OAK(1521, 15, 0.75, 50, [1281])

    static final ImmutableSet<Choppable> VALUES = ImmutableSet.copyOf(values())
    final int id
    final int level
    final double chance
    final double experience
    def treeIdList = []

    Choppable(int id, int level, double chance, double experience, def treeIdList) {
        this.id = id
        this.level = level
        this.chance = chance
        this.experience = experience
        this.treeIdList = treeIdList
    }

    @Override
    String toString() {
        name().toLowerCase().replaceAll("_", " ")
    }

    static Choppable getChoppable(int id) {
        return VALUES.find {it.id == id}
    }
}
