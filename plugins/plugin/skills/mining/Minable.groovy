package plugin.skills.mining

import com.google.common.collect.ImmutableSet

enum Minable {

    COPPER(436, 1, 0.85, 45,2091),
    TIN(438, 15, 0.75, 50,1 ),
    IRON(440, 15, 0.75, 50, 1),
    MITHRIL(447, 15, 0.75, 50, 1),
    ADAMANT(450, 15, 0.75, 50, 1),
    RUNE(451, 15, 0.75, 50, 1)


    static final ImmutableSet<Minable> VALUES = ImmutableSet.copyOf(values())
    final int id
    final int level
    final double chance
    final double experience
    def oreId

    Minable(int id, int level, double chance, double experience, def oreId) {
        this.id = id
        this.level = level
        this.chance = chance
        this.experience = experience
        this.oreId = oreId
    }

    @Override
    String toString() {
        name().toLowerCase().replaceAll("_", " ")
    }

    static Minable getMinable(int id) {
        return VALUES.find {it.id == id}
    }
}
