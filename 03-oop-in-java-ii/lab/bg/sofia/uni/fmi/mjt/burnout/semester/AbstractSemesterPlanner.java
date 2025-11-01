package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.subject.SubjectRequirement;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI
        permits ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    private final double MATH_REST_TIME_MULTIPLIER = 0.2;
    private final double PROGRAMMING_REST_TIME_MULTIPLIER = 0.2;
    private final double THEORY_REST_TIME_MULTIPLIER = 0.2;
    private final double PRACTICAL_REST_TIME_MULTIPLIER = 0.2;

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) {
        int jarCount = 0, studyTime = 0, studyTimeCopy = 0;
        double restTime = 0.0;
        for (UniversitySubject subject : subjects) {
            studyTime += subject.neededStudyTime();
            switch (subject.category()) {
                case MATH -> restTime += subject.neededStudyTime() * MATH_REST_TIME_MULTIPLIER;
                case PROGRAMMING -> restTime += subject.neededStudyTime() * PROGRAMMING_REST_TIME_MULTIPLIER;
                case THEORY -> restTime += subject.neededStudyTime() * THEORY_REST_TIME_MULTIPLIER;
                case PRACTICAL -> restTime += subject.neededStudyTime() * PRACTICAL_REST_TIME_MULTIPLIER;
            }
        }

        if ((int) Math.ceil(restTime) > maximumSlackTime) {
            throw new DisappointmentException("You are resting too much! From Grandma!");
        }

        studyTimeCopy = studyTime;
        while (studyTimeCopy >= 5) {
            studyTimeCopy -= 5;
            jarCount++;
        }

        return studyTime > semesterDuration ? jarCount * 2 : jarCount;
    }

    protected static void containsDuplicates(SubjectRequirement[] requirements) {
        for (int i = 0; i < requirements.length; i++) {
            for (int j = i + 1; j < requirements.length; j++) {
                if (requirements[i].category() == requirements[j].category()) {
                    throw new InvalidSubjectRequirementsException("Duplicate category");
                }
            }
        }
    }

    protected static boolean contains(UniversitySubject[] arr, UniversitySubject s) {
        for (UniversitySubject universitySubject : arr) {
            if (universitySubject == s) return true;
        }
        return false;
    }

    protected static UniversitySubject[] append(UniversitySubject[] arr, UniversitySubject s) {
        UniversitySubject[] result = new UniversitySubject[arr.length + 1];

        // same as a for loop
        System.arraycopy(arr, 0, result, 0, arr.length);
        result[arr.length] = s;

        return result;
    }
}
