package plugin.skills.woodcutting

import com.asteria.game.character.player.Player
import com.google.common.collect.ImmutableSet

enum Choppable {

    Tree(),
    Oak(),
    Willow(),
    Maple(),
    Yew(),
    Magic()

    static final ImmutableSet<Choppable> VALUES = ImmutableSet.copyOf(values())
    final int id
    final int level
    final double chance
    final double experience

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