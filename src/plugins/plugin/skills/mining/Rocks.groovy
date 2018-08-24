package plugins.plugin.skills.mining

import com.google.common.collect.ImmutableSet

enum Rocks {

    BRONZE(317, 1, 0.85, 10)

    static final ImmutableSet<Rocks> VALUES = ImmutableSet.copyOf(values())
    final int id
    final int level
    final double chance
    final double experience

   Rocks(int id, int level, double chance, double experience) {
        this.id = id
        this.level = level
        this.chance = chance
        this.experience = experience
    }

    static Rocks getRock(int id) {
        return VALUES.find {it.id == id}
    }
}