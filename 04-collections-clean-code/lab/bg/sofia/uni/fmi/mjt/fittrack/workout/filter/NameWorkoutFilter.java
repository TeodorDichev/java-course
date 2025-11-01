package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public record NameWorkoutFilter(String keyword, boolean caseSensitive) implements WorkoutFilter {

    public NameWorkoutFilter {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be blank or null");
        }
    }

    @Override
    public boolean matches(Workout workout) {
        if (caseSensitive) {
            return keyword.equals(workout.getName());
        }
        return keyword.equalsIgnoreCase(workout.getName());
    }
}
