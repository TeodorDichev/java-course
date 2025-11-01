package bg.sofia.uni.fmi.mjt.fittrack.workout;

import bg.sofia.uni.fmi.mjt.fittrack.exception.InvalidWorkoutException;

import java.util.Objects;

public final class StrengthWorkout implements Workout {
    private final String name;
    private final int duration;
    private final int calories;
    private final int difficulty;
    private final int minDifficulty = 1;
    private final int maxDifficulty = 5;

    public StrengthWorkout(String name, int duration, int calories, int difficulty) {
        this.name = name;

        if (duration < 0) {
            throw new InvalidWorkoutException("Duration must be non-negative");
        }
        this.duration = duration;

        if (calories < 0) {
            throw new InvalidWorkoutException("Calories must be non-negative");
        }
        this.calories = calories;

        if (difficulty < minDifficulty || difficulty > maxDifficulty) {
            throw new InvalidWorkoutException("Workout difficulty must be between 1 and 5");
        }
        this.difficulty = difficulty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getCaloriesBurned() {
        return calories;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public WorkoutType getType() {
        return WorkoutType.STRENGTH;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StrengthWorkout that = (StrengthWorkout) o;
        return duration == that.duration && calories == that.calories && difficulty == that.difficulty && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, duration, calories, difficulty);
    }

    @Override
    public String toString() {
        return "StrengthWorkout{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", calories=" + calories +
                ", difficulty=" + difficulty +
                '}';
    }
}
