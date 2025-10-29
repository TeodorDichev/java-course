package bg.sofia.uni.fmi.mjt.burnout.subject;

/**
 * @param name the name of the subject
 * @param credits number of credit hours for this subject
 * @param rating difficulty rating of the subject (1-5)
 * @param category the academic category this subject belongs to
 * @param neededStudyTime estimated study time in days required for this subject
 *
 * @throws IllegalArgumentException if the name of the subject is null or blank
 * @throws IllegalArgumentException if the credits are not positive
 * @throws IllegalArgumentException if the rating is not in its bounds
 * @throws IllegalArgumentException if the Category is null
 * @throws IllegalArgumentException if the neededStudy time is not positive
 */
public record UniversitySubject(String name, int credits, int rating, Category category, int neededStudyTime) {
    public UniversitySubject {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name in university subject cannot be null or blank");
        }

        if(credits <= 0) {
            throw new IllegalArgumentException("Credits in university subject cannot be negative");
        }

        if(rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating in university subject must be from 1 to 5");
        }

        if(category == null) {
            throw new IllegalArgumentException("Category in subject requirement cannot be null");
        }

        if(neededStudyTime <= 0) {
            throw new IllegalArgumentException("NeededStudyTime in subject cannot be negative");
        }
    }
}