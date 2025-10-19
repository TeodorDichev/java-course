package bg.sofia.uni.fmi.mjt.show.ergenka;

public abstract class ParticipatingErgenka implements Ergenka {
    private final String name;
    private final short age;
    private final int humourLevel;
    private final int romanceLevel;
    protected int rating;
    protected int tooShortDateDuration = 30;
    protected int tooLongDateDuration = 90;

    public ParticipatingErgenka(String name, short age, int humourLevel, int romanceLevel, int rating) {
        this.name = name;
        this.age = age;
        this.humourLevel = humourLevel;
        this.romanceLevel = romanceLevel;
        this.rating = rating;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public short getAge() {
        return this.age;
    }

    @Override
    public int getRomanceLevel() {
        return this.romanceLevel;
    }

    @Override
    public int getHumorLevel() {
        return this.humourLevel;
    }

    @Override
    public int getRating() {
        return this.rating;
    }
}
