package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class LowestRatingEliminationRule implements EliminationRule{
    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        int lowestRating = Integer.MAX_VALUE;
        for (Ergenka ergenka : ergenkas) {
            lowestRating = Math.min(ergenka.getRating(), lowestRating);
        }

        int countToKeep = 0;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka.getRating() > lowestRating) {
                countToKeep++;
            }
        }

        Ergenka[] result = new Ergenka[countToKeep];
        for (int i = 0, j = 0; i < ergenkas.length; i++) {
            if (ergenkas[i].getRating() > lowestRating) {
                result[j++] = ergenkas[i];
            }
        }

        return result;
    }
}
