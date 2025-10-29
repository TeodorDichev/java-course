package bg.sofia.uni.fmi.mjt.burnout.semester;

import bg.sofia.uni.fmi.mjt.burnout.exception.DisappointmentException;
import bg.sofia.uni.fmi.mjt.burnout.subject.UniversitySubject;
import bg.sofia.uni.fmi.mjt.burnout.plan.SemesterPlan;
import bg.sofia.uni.fmi.mjt.burnout.exception.InvalidSubjectRequirementsException;

public abstract sealed class AbstractSemesterPlanner implements SemesterPlannerAPI
    permits  ComputerScienceSemesterPlanner, SoftwareEngineeringSemesterPlanner {

    @Override
    public int calculateJarCount(UniversitySubject[] subjects, int maximumSlackTime, int semesterDuration) {
        int jarCount = 0, studyTime = 0, studyTimeCopy = 0;
        double restTime = 0.0;
        for(UniversitySubject subject : subjects) {
            studyTime += subject.neededStudyTime();
            switch (subject.category()) {
                case MATH -> restTime += subject.neededStudyTime() * 0.2;
                case PROGRAMMING -> restTime += subject.neededStudyTime() * 0.1;
                case THEORY -> restTime += subject.neededStudyTime() * 0.15;
                case PRACTICAL -> restTime += subject.neededStudyTime() * 0.05;
            }
        }

        if((int) Math.ceil(restTime) > maximumSlackTime) {
            throw new DisappointmentException("You are resting too much! From Grandma!");
        }

        studyTimeCopy = studyTime;
        while(studyTimeCopy >= 5) {
            studyTimeCopy -= 5;
            jarCount++;
        }

        return studyTime > semesterDuration ? jarCount * 2 : jarCount;
    }
}
