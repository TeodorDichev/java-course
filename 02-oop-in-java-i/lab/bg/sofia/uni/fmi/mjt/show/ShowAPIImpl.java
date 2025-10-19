package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class ShowAPIImpl implements ShowAPI {
    private Ergenka[] ergenkas;
    private EliminationRule[] defaultEliminationRules;

    public ShowAPIImpl(Ergenka[] ergenkas, EliminationRule[] defaultEliminationRules) {
        this.ergenkas = ergenkas.clone();
        this.defaultEliminationRules = defaultEliminationRules.clone();
    }

    @Override
    public Ergenka[] getErgenkas() {
        return ergenkas.clone();
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        for(Ergenka ergenka : ergenkas) {
            organizeDate(ergenka, dateEvent);
        }
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        if(eliminationRules.length == 0) {
            for(EliminationRule rule : defaultEliminationRules) {
                rule.eliminateErgenkas(ergenkas);
            }
        }

        for(EliminationRule rule : eliminationRules) {
            rule.eliminateErgenkas(ergenkas);
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        ergenka.reactToDate(dateEvent);
    }
}
