package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka extends ParticipatingErgenka {
    private final String favoriteDateLocation;

    public RomanticErgenka(String name, short age, int humourLevel, int romanceLevel, int rating, String favoriteDateLocation) {
        super(name, age, humourLevel, romanceLevel, rating);
        this.favoriteDateLocation = favoriteDateLocation;
        super.tooLongDateDuration = 120;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        rating += ((getRomanceLevel() * 7) / dateEvent.getTensionLevel()) + (getHumorLevel() / 3);

        int dateDuration = dateEvent.getDuration();
        if (dateDuration < super.tooShortDateDuration) {
            rating -= 3;
        } else if (dateDuration > super.tooLongDateDuration) {
            rating -= 2;
        }

        if (favoriteDateLocation.equalsIgnoreCase(dateEvent.getLocation())) {
            rating += 5;
        }
    }
}
