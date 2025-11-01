package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;
import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutComparators;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.WorkoutFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FitPlanner implements FitPlannerAPI {

    private final List<Workout> availableWorkouts;

    FitPlanner(Collection<Workout> availableWorkouts) {

        if (availableWorkouts == null) {
            throw new IllegalArgumentException("Available workouts are null");
        }

        this.availableWorkouts = List.copyOf(availableWorkouts);
    }

    @Override
    public List<Workout> findWorkoutsByFilters(List<WorkoutFilter> filters) {

        if (filters == null || filters.isEmpty()) {
            return List.of();
        }

        List<Workout> result = new ArrayList<>();
        for (var workout : availableWorkouts) {
            for (var filter : filters) {
                if (filter.matches(workout)) {
                    result.add(workout);
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public List<Workout> generateOptimalWeeklyPlan(int totalMinutes) throws OptimalPlanImpossibleException {
        if (totalMinutes < 0) {
            throw new IllegalArgumentException("Total minutes must be non-negative");
        }

        if (totalMinutes == 0 || availableWorkouts.isEmpty()) {
            return List.of();
        }

        int[] dp = new int[totalMinutes + 1];
        boolean[][] chosen = new boolean[availableWorkouts.size()][totalMinutes + 1];

        computeDpTable(totalMinutes, dp, chosen);
        List<Workout> result = backtrackSelection(totalMinutes, chosen);

        if (result.isEmpty()) {
            throw new OptimalPlanImpossibleException("No combination fits within total minutes");
        }

        result.sort(WorkoutComparators.BY_CALORIES_THEN_DIFFICULTY_DESC);
        return result;
    }

    private void computeDpTable(int totalMinutes, int[] dp, boolean[][] chosen) {
        int n = availableWorkouts.size();

        for (int i = 0; i < n; i++) {
            int time = availableWorkouts.get(i).getDuration();
            int value = availableWorkouts.get(i).getCaloriesBurned();

            for (int j = totalMinutes; j >= time; j--) {
                if (dp[j - time] + value > dp[j]) {
                    dp[j] = dp[j - time] + value;
                    chosen[i][j] = true;
                }
            }
        }
    }

    private List<Workout> backtrackSelection(int totalMinutes, boolean[][] chosen) {
        List<Workout> result = new ArrayList<>();
        int remaining = totalMinutes;

        for (int i = availableWorkouts.size() - 1; i >= 0; i--) {
            int time = availableWorkouts.get(i).getDuration();
            if (remaining >= time && chosen[i][remaining]) {
                result.add(availableWorkouts.get(i));
                remaining -= time;
            }
        }
        return result;
    }

    @Override
    public Map<WorkoutType, List<Workout>> getWorkoutsGroupedByType() {
        Map<WorkoutType, List<Workout>> result = new HashMap<>();

        for (Workout workout : availableWorkouts) {
            WorkoutType type = workout.getType();
            if (!result.containsKey(type)) {
                result.put(type, new ArrayList<Workout>());
            }
            result.get(type).add(workout);
        }

        return result;
    }

    @Override
    public List<Workout> getWorkoutsSortedByCalories() {
        List<Workout> result = new ArrayList<>(availableWorkouts);
        result.sort(WorkoutComparators.BY_CALORIES);
        return result;
    }

    @Override
    public List<Workout> getWorkoutsSortedByDifficulty() {
        List<Workout> result = new ArrayList<>(availableWorkouts);
        result.sort(WorkoutComparators.BY_DIFFICULTY);
        return result;
    }

    @Override
    public Set<Workout> getUnmodifiableWorkoutSet() {
        return Set.copyOf(availableWorkouts);
    }
}
