package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowAttributeSumEliminationRule implements EliminationRule {
    private final int threshold;

    public LowAttributeSumEliminationRule(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {

        int countToKeep = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka.getHumorLevel() + ergenka.getRomanceLevel() >= threshold) {
                countToKeep++;
            }
        }

        Ergenka[] result = new Ergenka[countToKeep];
        for (int i = 0, j = 0; i < ergenkas.length; i++) {
            if (ergenkas[i].getHumorLevel() + ergenkas[i].getRomanceLevel() >= threshold) {
                result[j++] = ergenkas[i];
            }
        }

        return result;
    }
}
