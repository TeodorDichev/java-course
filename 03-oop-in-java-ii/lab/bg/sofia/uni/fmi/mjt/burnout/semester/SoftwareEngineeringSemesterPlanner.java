package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.CryToStudentsDepartmentException;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.subject.Category;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;

public final class SoftwareEngineeringSemesterPlanner extends AbstractSemesterPlanner {

    @Override
    public UniversitySubject[] calculateSubjectList(SemesterPlan semesterPlan)
            throws InvalidSubjectRequirementsException {

        if (semesterPlan == null) {
            throw new IllegalArgumentException("Semester plan is null");
        }

        UniversitySubject[] subjects = semesterPlan.subjects();
        SubjectRequirement[] requirements = semesterPlan.subjectRequirements();
        int minCredits = semesterPlan.minimalAmountOfCredits();

        // check duplicate categories
        containsDuplicates(semesterPlan.subjectRequirements());

        // sort by easiest (lowest rating), then most credits
        bubbleSortByCreditsThenRating(subjects);

        UniversitySubject[] selected = new UniversitySubject[0];
        int totalCredits = 0;

        // satisfy category minimums
        for (SubjectRequirement requirement : requirements) {
            Category cat = requirement.category();
            int needed = requirement.minAmountEnrolled();
            int added = 0;

            for (int j = 0; j < subjects.length && added < needed; j++) {
                if (subjects[j].category() == cat && !contains(selected, subjects[j])) {
                    selected = append(selected, subjects[j]);
                    totalCredits += subjects[j].credits();
                    added++;
                }
            }
        }

        // fill until credits requirement is met
        for (int i = 0; i < subjects.length && totalCredits < minCredits; i++) {
            if (!contains(selected, subjects[i])) {
                selected = append(selected, subjects[i]);
                totalCredits += subjects[i].credits();
            }
        }

        if(totalCredits < minCredits) {
            throw new CryToStudentsDepartmentException("Software engineering student unable to get minimal credits");
        }

        return selected;
    }

    private static void bubbleSortByCreditsThenRating(UniversitySubject[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].credits() < arr[j + 1].credits()
                        || (arr[j].credits() == arr[j + 1].credits()
                        && arr[j].rating() > arr[j + 1].rating())) {
                    UniversitySubject tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    private static void containsDuplicates(SubjectRequirement[] requirements) {
        for (int i = 0; i < requirements.length; i++) {
            for (int j = i + 1; j < requirements.length; j++) {
                if (requirements[i].category() == requirements[j].category()) {
                    throw new InvalidSubjectRequirementsException("Duplicate category");
                }
            }
        }
    }

    private static boolean contains(UniversitySubject[] arr, UniversitySubject s) {
        for (UniversitySubject universitySubject : arr) {
            if (universitySubject == s) return true;
        }
        return false;
    }

    private static UniversitySubject[] append(UniversitySubject[] arr, UniversitySubject s) {
        UniversitySubject[] result = new UniversitySubject[arr.length + 1];

        // same as a for loop
        System.arraycopy(arr, 0, result, 0, arr.length);
        result[arr.length] = s;

        return result;
    }
}
