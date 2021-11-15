package Features;

import Enums.PlayerClass;
import Enums.PlayerGenres;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Game {

    public static String gameMode;
    public static double easy = 0.80;
    public static double hard = 0.90;

    private static boolean playGame = true;

    public static Player player;

    public static void setGameMode() {
        String gameModeTitle = "Escolha sua proficiência:";
        List<String> gameModeMenu = Arrays.asList("Fácil", "Normal", "Difícil");
        Text gameModeChoices = new Text(gameModeTitle, gameModeMenu);
        int gameModeChoice = gameModeChoices.playerChoice();
        gameMode = gameModeMenu.get(gameModeChoice-1);
    }

    public static int rollDice(Integer sides) {
        Random dice = new Random();
        return dice.nextInt(sides + 1) + 1;
    }

    public static void playerDied() {
        Text.clearConsole();
        Text.printTitle("Você não estava preparado para a força do inimigo...");
        if (player.getMotivation().equals("Vingança"))
            Text.printTitle("Não foi possível concluir sua vingança, e agora resta saber se alguém se vingará por você.");
        else {
            if (player.getGender().equals("Masculino"))
                Text.printTitle("A glória que buscavas não será sua, e a cidade aguarda por seu próximo herói.");
            else
                Text.printTitle("A glória que buscavas não será sua, e a cidade aguarda por sua próxima heroína.");
        }
    }

    public static boolean runAway() {
        int willRun = rollDice(10);
        if (willRun > 5) {
            Text.clearConsole();
            Text.printTitle("Você fugiu...");
            Text.printText("Você não estava preparado para a força do inimigo, e decide fugir para que possa tentar novamente em uma próxima vez.");
            return true;
        }
        Text.printTitle("Você não consegue fugir e continua na batalha!");
        return false;
    }

    public static Enemy createEnemy(String enemyName, int playerMaxDefensePoints, int playerAttackPoints, int playerDefensePoints, int playerWeaponDamage) {
        int random = rollDice(20) + 90;
        double percentage = (double) random / 100;
        int enemyMaxHealthPoints = (int) ((double) playerMaxDefensePoints * percentage);
        int enemyAttackPoints = (int) ((double) playerAttackPoints * percentage);
        int enemyDefensePoints = (int) ((double) playerDefensePoints * percentage);
        int enemyWeaponDamage = (int) ((double) playerWeaponDamage * percentage);
        return new Enemy(enemyName, enemyMaxHealthPoints, enemyAttackPoints, enemyDefensePoints, enemyWeaponDamage);
    }

    public static Player createPlayer() {

        Text.clearConsole();

        Player player = new Player();

        player.setName(player.readName());

        Text.clearConsole();

        List<String> genderMenu = new ArrayList<>();
        List<String> genderMenuValues = new ArrayList<>();
        for (PlayerGenres item : PlayerGenres.values()) {
            genderMenu.add(item.getGenderName() + " (+" + item.getPowerUpPoints() + " pontos de ataque para as classes: " + item.getPowerUpClasses() + ")");
            genderMenuValues.add(item.getGenderName());
        }
        String genderMenuTitle = "Escolha o gênero do seu personagem:";
        Text playerGenderMenu = new Text(genderMenuTitle, genderMenu, genderMenuValues);
        int playerGenderChoice = playerGenderMenu.playerChoice();
        String playerGenderName = PlayerGenres.values()[playerGenderChoice - 1].getGenderName();

        player.setGender(playerGenderName);
        Text.clearConsole();

        List<String> classMenu = new ArrayList<>();
        List<String> classMenuValues = new ArrayList<>();
        for (PlayerClass item : PlayerClass.values()) {
            classMenu.add(item.getClassName() + " (Ataque: "+ item.getAttackPoints() + " | Defesa: " + item.getDefensePoints() + " | Vida: " + item.getMaxHealthPoints() + ")");
            classMenuValues.add(item.getClassName());
        }
        String classMenuTitle = "Escolha uma classe de jornada: ";
        Text playerClassMenu = new Text(classMenuTitle, classMenu, classMenuValues);
        int playerClassChoice = playerClassMenu.playerChoice();
        String playerClassName = PlayerClass.values()[playerClassChoice - 1].getClassName();

        int attackPoints = PlayerClass.values()[playerClassChoice - 1].getAttackPoints();
        int maxHealthPoints = PlayerClass.values()[playerClassChoice - 1].getMaxHealthPoints();
        int defensePoints = PlayerClass.values()[playerClassChoice - 1].getDefensePoints();
        Map<String, Integer> availableWeapons = PlayerClass.values()[playerClassChoice - 1].getWeaponsMap();

        if (PlayerGenres.values()[playerGenderChoice - 1].getPowerUpClasses().contains(playerClassName))
            attackPoints += PlayerGenres.values()[playerGenderChoice - 1].getPowerUpPoints();

        player.setPlayerClassName(playerClassName);
        player.setMaxHealthPoints(maxHealthPoints);
        player.setHealthPoints(maxHealthPoints);
        player.setDefensePoints(defensePoints);
        player.setAttackPoints(attackPoints);
        Text.clearConsole();

        List<String> weaponsMenu = new ArrayList<>();
        List<String> weaponsMenuValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : availableWeapons.entrySet()) {
            String weaponName = entry.getKey();
            Integer weaponDamage = entry.getValue();
            weaponsMenu.add(weaponName + " (Dano " + weaponDamage + ")");
            weaponsMenuValues.add(weaponName);
        }

        String weaponMenuTitle = "Escolha uma arma: ";
        Text playerWeaponMenu = new Text(weaponMenuTitle, weaponsMenu, weaponsMenuValues);
        int playerWeaponChoice = playerWeaponMenu.playerChoice();
        String playerWeaponName = weaponsMenuValues.get(playerWeaponChoice - 1);
        int weaponDamage = availableWeapons.get(playerWeaponName);

        player.setWeapon(playerWeaponName);
        player.setWeaponDamage(weaponDamage);

        String classAttackText = PlayerClass.values()[playerClassChoice - 1].getAttackText();
        player.setAttackText(classAttackText);

        Text.clearConsole();

        // Imprimindo o resumo das escolhas
        Text.printTitle("A aventura vai começar!");
        System.out.println("Modo: " + gameMode
                + "\nNome: " + player.getName()
                + "\nGênero: " + playerGenderName
                + "\nClasse: " + playerClassName
                + "\nVida: " + maxHealthPoints
                + "\nArma: " + playerWeaponName
                + "\nDano da Arma: " + weaponDamage
                + "\nAtaque: " + attackPoints
                + "\nDefesa: " + defensePoints
        );
        Text.enterToContinue();

        return player;
    }


    public static void startGame() {

        while (playGame) {
            Text.clearConsole();
            System.out.println("\n\n");

            System.out.println("Seja bem vindo(a) à BATALHA FINAL!\n");

            setGameMode();

            player = createPlayer();

            Story.intro(player);

            if (!Story.corridor())
                break;

            if (!Story.mainRoom(player))
                break;

            Enemy comum = createEnemy("Orc", player.getHealthPoints(), player.getAttackPoints(), player.getDefensePoints(), player.getWeaponDamage());
            if (!Story.rightDoor(player, comum))
                break;

            Story.changeArmor(player);

            Enemy evilMage = createEnemy("Ryze", player.getHealthPoints(), player.getAttackPoints(), player.getDefensePoints(), player.getWeaponDamage());
            if (!Story.leftDoor(player, evilMage))
                break;

            Story.drinkPotion(player);

            Enemy boss = createEnemy("Junior", player.getHealthPoints(), player.getAttackPoints(), player.getDefensePoints(), player.getWeaponDamage());
            if (!Story.finalRoom(player, boss))
                break;

            Story.theEnd(player);

            String playAgainTitle = "Deseja jogar novamente?";
            List<String> playAgainMenu = Arrays.asList("Sim", "Não");
            Text playAgainChoices = new Text(playAgainTitle, playAgainMenu);

            if (playAgainChoices.playerChoice() == 2)
                playGame = false;
        }
    }

    public static boolean battle(@NotNull Player player, @NotNull Enemy enemy) {
        Text.clearConsole();

        String playerName = player.getName();
        String enemyName = enemy.getName();

        Text.printTitle(String.format("Batalha entre %s e %s", playerName, enemyName));
        System.out.printf("%s tem %d pontos de vida.%n", playerName, player.getHealthPoints());
        System.out.printf("%s tem %d pontos de vida.%n", enemyName, enemy.getHealthPoints());

        String battleTitle = "Escolha sua ação";
        List<String> battleMenu = Arrays.asList("Lutar", "Fugir");
        Text battleChoices = new Text(battleTitle, battleMenu);

        int playerBattleChoice = 1;

        while ((playerBattleChoice == 1) && (player.getHealthPoints() > 0) && (enemy.getHealthPoints() > 0)) {

            playerBattleChoice = battleChoices.playerChoice();

            Text.clearConsole();

            if (playerBattleChoice == 1) {
                int playerAttack = player.attack(enemy.getDefensePoints(), enemy.getHealthPoints());
                enemy.setHealthPoints(enemy.getHealthPoints() - playerAttack);
                if (enemy.getHealthPoints() > 0) {
                    int enemyAttack = enemy.attack(player.getDefensePoints(), player.getHealthPoints());
                    player.setHealthPoints(player.getHealthPoints() - enemyAttack);
                } else {
                    Text.printTitle(String.format("%s venceu a batalha!", playerName));
                    Text.printText("O inimigo não é páreo para o seu heroísmo, e jaz imóvel aos seus pés.");
                    Text.enterToContinue();
                    return true;
                }
                if (player.getHealthPoints() <= 0) {
                    Text.printTitle(String.format("%s venceu a batalha!", enemyName));
                    Text.enterToContinue();
                    Game.playerDied();
                    return false;
                }
                System.out.printf("%s tem %d pontos de vida e %s tem %d pontos de vida!%n", playerName, player.getHealthPoints(), enemyName, enemy.getHealthPoints());
            } else if (playerBattleChoice == 2) {
                if (Game.runAway())
                    return false;
                playerBattleChoice = 1;
            }
        }
        return true;
    }

    public static void printPreBattle(Player player, Enemy enemy) {
        System.out.println("\n" + player.getName() + "\t  VS  \t" + enemy.getName()
                + "\n" + player.getHealthPoints() + "\t\t Vida\t\t" + enemy.getHealthPoints()
                + "\n" + player.getAttackPoints() + "\t\tAtaque\t\t" + enemy.getAttackPoints()
                + "\n" + player.getDefensePoints() + "\t\tDefesa\t\t" + enemy.getDefensePoints()
                + "\n" + player.getWeaponDamage() + "\t\t Arma\t\t" + enemy.getWeaponDamage()
        );
    }


}
