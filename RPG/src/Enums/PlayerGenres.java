package Enums;

import java.util.Arrays;
import java.util.List;

public enum PlayerGenres {
    MASCULINO("Masculino", 20, Arrays.asList("Guerreiro", "Arqueiro")),
    FEMININO("Feminino", 20, Arrays.asList("Mago", "Bruxo"));

    private final String genderName;
    private final int powerUpPoints;
    private final List<String> powerUpClasses;

    PlayerGenres(String genderName, int powerUpPoints, List<String> powerUpClasses) {
        this.genderName = genderName;
        this.powerUpPoints = powerUpPoints;
        this.powerUpClasses = powerUpClasses;
    }

    @Override
    public String toString() {
        String saida = "%d - %s";
        return String.format(saida, ordinal() + 1, this.genderName);
    }


    public String getGenderName() {
        return genderName;
    }

    public int getPowerUpPoints() {
        return powerUpPoints;
    }

    public List<String> getPowerUpClasses() {
        return powerUpClasses;
    }
}
