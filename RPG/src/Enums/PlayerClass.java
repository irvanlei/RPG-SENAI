package Enums;

import java.util.Map;

public enum PlayerClass {
    GUERREIRO("Guerreiro", 60, 15, 900, Map.of("Espada", 25, "Machado", 23, "Cutelo", 25, "Bastão", 18), "com seu/sua %s"),
    ARQUEIRO("Arqueiro", 90, 8, 600, Map.of("Arco e flecha", 30, "Besta", 35), "com seu/sua %s, a/o %s atingiu"),
    MAGO("Mago", 120, 9, 500, Map.of("Cajado", 30), "com seu %s, lançando uma bola de mágia"),
    BRUXO("Bruxo", 100, 6, 1000, Map.of("Vasoura", 50), "Sobe na %s e solta uma rajada de ar");

    private final String className;
    private final int attackPoints;
    private final int defensePoints;
    private final int maxHealthPoints;
    private final Map<String, Integer> weaponsMap;
    private final String attackText;

    PlayerClass(String className, int attackPoints, int defensePoints, int maxHealthPoints, Map<String, Integer> weaponsMap, String attackText) {
        this.className = className;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.maxHealthPoints = maxHealthPoints;
        this.weaponsMap = weaponsMap;
        this.attackText = attackText;
    }

    public String getClassName() {
        return className;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public Map<String, Integer> getWeaponsMap() {
        return weaponsMap;
    }



    public String getAttackText() {
        return attackText;
    }


}
