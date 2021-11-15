package Features;

public class Enemy extends Character {

    private final int weaponDamage;

    public Enemy(String name, int maxHealthPoints, int attackPoints, int defensePoints, int weaponDamage) {
        super(name, maxHealthPoints, attackPoints, defensePoints);
        this.weaponDamage = weaponDamage;
    }

    @Override
    public int attack(int playerDefensePoints, int playerHealthPoints) {
        int diceDamage = Game.rollDice(20);
        int enemyAttack = this.getAttackPoints() + this.getWeaponDamage() + diceDamage;

        if (Game.gameMode.equals("Fácil"))
            enemyAttack = (int) ((double) enemyAttack * Game.easy);

        if (diceDamage == 1) {
            Text.printText("O inimigo errou o ataque! Você não sofreu dano.");
            return 0;
        } else if (diceDamage == 20)
            Text.printText("O inimigo acertou um ataque crítico!");
        else
            enemyAttack -= playerDefensePoints;
        enemyAttack = (playerHealthPoints - enemyAttack < 0) ? playerHealthPoints : enemyAttack;

        playerHealthPoints -= enemyAttack;
        Text.printText("O inimigo atacou! Você sofreu " + enemyAttack + " de dano e agora possui " + playerHealthPoints + " pontos de vida.");

        return enemyAttack;
    }

    @Override
    public void setPlayerClassName(String playerClassName) {

    }


    public int getWeaponDamage() {
        return weaponDamage;
    }

}
