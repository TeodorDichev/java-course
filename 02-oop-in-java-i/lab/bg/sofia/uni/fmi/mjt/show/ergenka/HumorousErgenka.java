package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class HumorousErgenka extends ParticipatingErgenka {

    public HumorousErgenka(String name, short age, int humourLevel, int romanceLevel, int rating) {
        super(name, age, humourLevel, romanceLevel, rating);
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        rating += ((getHumorLevel() * 5) / dateEvent.getTensionLevel()) + (getRomanceLevel() / 3);

        int dateDuration = dateEvent.getDuration();
        if(dateDuration < super.tooShortDateDuration) {
            rating -= 2;
        } else if (dateDuration > super.tooLongDateDuration) {
            rating -= 3;
        } else {
            rating += 4;
        }
    }
}
