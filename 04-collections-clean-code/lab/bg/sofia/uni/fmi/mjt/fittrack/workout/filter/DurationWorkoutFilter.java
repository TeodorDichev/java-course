package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public record DurationWorkoutFilter(int min, int max) implements WorkoutFilter {

    public DurationWorkoutFilter {
        if (min < 0 || max < 0 || max < min) {
            throw new IllegalArgumentException(
                    "Min and max are non-negative and max must be greater than min"
            );
        }
    }

    @Override
    public boolean matches(Workout workout) {
        return workout.getDuration() >= min && workout.getDuration() <= max;
    }
}
