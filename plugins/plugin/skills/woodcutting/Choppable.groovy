package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.google.common.collect.ImmutableSet

enum Choppable {

    LOGS(1511, 1, 0.85, 10) {
        @Override
        boolean choppable(Player player) {

        }
    },
    OAK,
    WILLOW,
    MAPLE

    static final ImmutableSet<Choppable> VALUES = ImmutableSet.copyOf(values())
    final int id
    final int level
    final double chance
    final double experience
    final int treeId

    Choppable(int id, int level, double chance, double experience) {
        this.id = id
        this.level = level
        this.chance = chance
        this.experience = experience
    }

    @Override
    String toString() {
        name().toLowerCase().replaceAll("_", " ")
    }

    boolean choppable(Player player) {
        true
    }

    static Choppable getChoppable(int id) {
        return VALUES.find {it.id == id}
    }
}