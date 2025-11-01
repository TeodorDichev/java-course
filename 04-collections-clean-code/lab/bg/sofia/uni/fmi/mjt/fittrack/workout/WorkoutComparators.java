package bg.sofia.uni.fmi.mjt.fittrack.workout;

import java.util.Comparator;

/**
 * Because the Workout interface is sealed and
 * I do not want to cause code duplication in
 * the concrete workout classes I decide to
 * create and use static comparators
 */
public final class WorkoutComparators {

    private WorkoutComparators() { }

    public static final Comparator<Workout> BY_CALORIES = new Comparator<Workout>() {
        @Override
        public int compare(Workout w1, Workout w2) {
            return Integer.compare(w1.getCaloriesBurned(), w2.getCaloriesBurned());
        }
    };

    public static final Comparator<Workout> BY_DIFFICULTY = new Comparator<Workout>() {
        @Override
        public int compare(Workout w1, Workout w2) {
            return Integer.compare(w1.getDifficulty(), w2.getDifficulty());
        }
    };

    public static final Comparator<Workout> BY_CALORIES_THEN_DIFFICULTY_DESC = new Comparator<Workout>() {
        @Override
        public int compare(Workout w1, Workout w2) {
            int c = Integer.compare(w2.getCaloriesBurned(), w1.getCaloriesBurned());
            if (c != 0) {
                return c;
            }
            return Integer.compare(w2.getDifficulty(), w1.getDifficulty());
        }
    };
}
