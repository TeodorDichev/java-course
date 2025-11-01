package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

public class PublicVoteEliminationRule implements EliminationRule {
    private String[] votes;

    public PublicVoteEliminationRule(String[] votes) {
        this.votes = votes.clone();
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        String majorityVote = votes[0];
        int cnt = 0;
        for (String vote : votes) {
            if (cnt == 0) {
                majorityVote = vote;
                cnt = 1;
            } else if (majorityVote.equalsIgnoreCase(vote)) {
                cnt++;
            } else {
                cnt--;
            }
        }

        int realCount = 0;
        for (String vote : votes) {
            if (majorityVote.equalsIgnoreCase(vote)) {
                realCount++;
            }
        }

        int len = ergenkas.length;
        if (realCount <= len / 2) {
            return ergenkas;
        }

        Ergenka[] result = new Ergenka[len - 1];
        for (int i = 0, j = 0; i < len; i++) {
            if (!ergenkas[i].getName().equalsIgnoreCase(majorityVote)) {
                result[j++] = ergenkas[i];
            }
        }

        return result;
    }
}
