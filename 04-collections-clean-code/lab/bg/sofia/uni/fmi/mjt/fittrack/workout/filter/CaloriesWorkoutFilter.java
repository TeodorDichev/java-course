package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public record CaloriesWorkoutFilter(int min, int max) implements WorkoutFilter {

    public CaloriesWorkoutFilter {
        if (min < 0 || max < 0 || max < min) {
            throw new IllegalArgumentException(
                    "Min and max are non-negative and max must be greater than min"
            );
        }
    }

    @Override
    public boolean matches(Workout workout) {
        return workout.getCaloriesBurned() >= min && workout.getCaloriesBurned() <= max;
    }
}
