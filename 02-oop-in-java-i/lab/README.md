# Обектно-ориентирано програмиране с Java (част I)

## 🌹The Ergen(less)

След края на лятото, докато всички се чудят дали да започнат фитнес или просто да рестартират Netflix абонамента си, телевизионният сезон започва с пълна сила 📺.

Един от най-гледаните риалити формати е "Ергенът". Тази година обаче продуцентите са решили да се възползват от факта, че хората са склонни да гледат каквото и да е вечерно време между 20 и 21 часа, докато хапват и пийват със семейството.
За да сведат разходите си до минимум, продукцията е решила вместо да дават хонорари за участие на неизвестни хора със съмнителни личностни качества, да симулира изцяло предаването: зрителите ще виждат резултатите в края на епизода, а през останалото време ще текат реклами на прах за пране 🧺. 

И понеже явно вие сте единствените, които имате желанието (*нямате избор*) да помагат на продукцията, вие трябва да имплементирате симулатора 💻.


## src.bg.sofia.uni.fmi.mjt.show.ShowAPI

---
Това е основният интерфейс на играта. През него ще можете да получавате информация за оставащите участнички, да изигравате един кръг от играта, да елиминирате участнички посредством `elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule` и да организирате среща между Ергена и една от тях:

```java
package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.ergenka.src.ergenka.Ergenka;
import bg.sofia.uni.fmi.mjt.show.date.bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule;

/**
 * Public API for controlling the show.
 */
public interface src.

ShowAPI {

    /**
     * Returns the current ergenkas participating in the show.
     *
     * @return an array of ergenkas, never {@code null}; may be empty
     */
    ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka[] getErgenkas ();

    /**
     * Plays a full round using the provided {@link bg.sofia.uni.fmi.mjt.show.date.DateEvent}.
     *
     * @param dateEvent the event to play during the round, never {@code null}
     */
    void playRound (bg.sofia.uni.fmi.mjt.show.date.DateEvent dateEvent);

    /**
     * Applies a sequence of elimination rules to the current ergenkas.
     *
     * @param eliminationRules the rules to apply
     */
    void eliminateErgenkas (elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule[]eliminationRules);

    /**
     * Performs a single date with the ergenkas {@link bg.sofia.uni.fmi.mjt.show.date.DateEvent}.
     *
     * @param ergenka the ergenka participating in the date, never {@code null}
     * @param dateEvent the date event to organize, never {@code null}
     */
    void organizeDate (ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka ergenka, bg.sofia.uni.fmi.mjt.show.date.DateEvent dateEvent);

}
```

Очакваме от вас да създадете имплементация `bg.sofia.uni.fmi.mjt.show.ShowAPIImpl` на интерфейса `src.bg.sofia.uni.fmi.mjt.show.ShowAPI` в пакета `bg.sofia.uni.fmi.mjt.show`, със следния конструктор:

```
public bg.sofia.uni.fmi.mjt.show.ShowAPIImpl(ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka[] ergenkas, elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule[] defaultEliminationRules)
```

В тази имплементация един рунд се провежда, като Ергенът се среща с всяка от участничките в играта при едни и същи условия - т.е. използвайки един и същи `bg.sofia.uni.fmi.mjt.show.date.DateEvent`.

Класът `bg.sofia.uni.fmi.mjt.show.date.DateEvent` го имате наготово и не е необходимо да го променяте. Той изглежда така:

```java
package bg.sofia.uni.fmi.mjt.show.date;

public class bg.sofia.uni.fmi.mjt.show.date.DateEvent {
    private static final int TENSION_LEVEL_MIN = 0;
    private static final int TENSION_LEVEL_MAX = 10;

    private final String location;
    private final int tensionLevel;
    private final int duration;

    public bg.sofia.uni.fmi.mjt.show.date.DateEvent(String location, int tensionLevel, int duration) {
        this.location = location;
        this.duration = duration;
        if (tensionLevel < TENSION_LEVEL_MIN) {
            this.tensionLevel = TENSION_LEVEL_MIN;
        } else {
            this.tensionLevel = Math.min(tensionLevel, TENSION_LEVEL_MAX);
        }
    }

    public String getLocation() {
        return location;
    }

    public int getTensionLevel() {
        return tensionLevel;
    }

    public int getDuration() {
        return duration;
    }
}
```

След това участничките преминават през *елиминация*, като за целта се прилагат едно или повече `elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule`, подадени на метода 

```
void eliminateErgenkas(elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule[] eliminationRules)
```

Ако масивът с правила е празен, да се приложат правилата по подразбиране.

## ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka

---
В играта могат да участват различни видове участнички - забавни, романтични и т.н. Всички те имплементират интерфейса `ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka`, който изглежда по следния начин:

```java
package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.date.bg.sofia.uni.fmi.mjt.show.DateEvent;

/**
 * Represents an ergenka participating in the show.
 *
 * Implementations must provide the ergenka's basic attributes (name, age)
 * and performance metrics (romance, humor, rating). The {@link #reactToDate(bg.sofia.uni.fmi.mjt.show.date.DateEvent)}
 * method allows an ergenka to update her internal state when participating in a {@link bg.sofia.uni.fmi.mjt.show.date.DateEvent}.
 */
public interface ergenka.bg.sofia.uni.fmi.mjt.show.

Ergenka {

    /**
     * Returns the ergenka's full name. The name of each ergenka is a unique identifier.
     *
     * @return the name of the ergenka, never {@code null}
     */
    String getName ();

    /**
     * Returns the ergenka's age in years.
     *
     * @return the age as a short
     */
    short getAge ();

    /**
     * Returns the ergenka's romance level.
     *
     * @return an integer representing the romance level
     */
    int getRomanceLevel ();

    /**
     * Returns the ergenka's humor level.
     *
     * @return an integer representing the humor level
     */
    int getHumorLevel ();

    /**
     * Returns the ergenka's current rating.
     *
     * @return the rating as an integer
     */
    int getRating ();

    /**
     * Reacts to a {@link bg.sofia.uni.fmi.mjt.show.date.DateEvent} and updates internal state accordingly.
     *
     * @param dateEvent the date event the ergenka participates in, never {@code null}
     */
    void reactToDate (bg.sofia.uni.fmi.mjt.show.date.DateEvent dateEvent);

}

```
Методът `reactToDate(bg.sofia.uni.fmi.mjt.show.date.DateEvent dateEvent)` променя рейтинга на участничката по различен начин в зависимост от вида ѝ.

Трябва да създадете два вида участнички с посочените по-долу публични конструктори:

```java
public ergenka.bg.sofia.uni.fmi.mjt.show.RomanticErgenka(String name, short age, int romanceLevel, int humorLevel, int rating, String favoriteDateLocation)
```

За `ergenka.bg.sofia.uni.fmi.mjt.show.RomanticErgenka` **промяната** на рейтинга се изчислява така:

$`\frac{romance\_level * 7}{date\_tension\_level} + \lfloor humor\_level / 3 \rfloor + bonuses`$

**Бонуси за `ergenka.bg.sofia.uni.fmi.mjt.show.RomanticErgenka`**:
- +5 точки, ако локацията на срещата е любима на участника (case insensitive)
- -3 точки, ако срещата е прекалено кратка (<30 мин)
- -2 точки, ако срещата е прекалено дълга (>120 мин)

```java
public ergenka.bg.sofia.uni.fmi.mjt.show.HumorousErgenka(String name, short age, int romanceLevel, int humorLevel, int rating)
```

За `ergenka.bg.sofia.uni.fmi.mjt.show.HumorousErgenka` **промяната** на рейтинга се изчислява така:

$`\frac{humor\_level * 5}{date\_tension\_level} + \lfloor romance\_level / 3 \rfloor + bonuses`$

**Бонуси за `ergenka.bg.sofia.uni.fmi.mjt.show.HumorousErgenka`**:
- +4 точки, ако срещата е разумно дълга (>= 30 мин и <= 90 мин)
- -2 точки, ако срещата е прекалено кратка (<30 мин)
- -3 точки, ако срещата е прекалено дълга (>90 мин)

> 🔍 Синтаксисът $`\lfloor x \rfloor`$ означава закръгляне надолу до най-близкото цяло число.

## elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule

---
Участничките в играта могат да отпадат според различни правила за елиминация. Всички правила имплементират интерфейса `elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule`, който изглежда така:

```java
package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;
import bg.sofia.uni.fmi.mjt.show.ergenka.src.ergenka.Ergenka;

/**
 * Defines a rule used to eliminate ergenkas from the show.
 */
public interface src.elimination.

EliminationRule {

    /**
     * Applies the elimination rule to the provided ergenkas.
     *
     * @param ergenkas the current ergenkas, never {@code null}
     * @return an array with ergenkas that remain after elimination, never {@code null}
     */
    ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka[] eliminateErgenkas (ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka[]ergenkas);

}

```

От вас се очаква да имплементирате три правила за елиминация.

```java
public elimination.bg.sofia.uni.fmi.mjt.show.LowestRatingEliminationRule()
```
`elimination.bg.sofia.uni.fmi.mjt.show.LowestRatingEliminationRule` - елиминира участничката (или участничките) с най-нисък рейтинг (при равен рейтинг си отиват всички със съответния такъв)

```java
public elimination.bg.sofia.uni.fmi.mjt.show.LowAttributeSumEliminationRule(int threshold)
```
`elimination.bg.sofia.uni.fmi.mjt.show.LowAttributeSumEliminationRule` - елиминира участничките, чийто сбор на `humorLevel` и `romanceLevel` е по-малък от `threshold`

```java
public elimination.bg.sofia.uni.fmi.mjt.show.PublicVoteEliminationRule(String[] votes)
```
`elimination.bg.sofia.uni.fmi.mjt.show.PublicVoteEliminationRule` - елиминира участничката, която има 50% + 1 гласа от публиката (ако няма такава, не елиминира никого)

### Бележка

>За намирането на участничката с 50% + 1 гласа в `elimination.bg.sofia.uni.fmi.mjt.show.PublicVoteEliminationRule` има тривиален алгоритъм със сложност по време $O(n^2)$
и малко по-малко тривиален алгоритъм със сложност по време, а и по памет, $O(n)$. Бързодействието на вашето решение няма да бъде тествано, така че сте свободни да направите каквото смятате за добре. Хубавият алгоритъм се нарича *Алгоритъм на Boyer–Moore*. За него може да прочетете повече [тук](https://en.wikipedia.org/wiki/Boyer%E2%80%93Moore_majority_vote_algorithm).

## Структура

---
Придържате се към следната структура на пакетите, интерфейсите и конструктурите на класовете, иначе няма как да ви тестваме решенията:

```
src
└── bg.sofia.uni.fmi.mjt.show
    ├── src.bg.sofia.uni.fmi.mjt.show.ShowAPI.java
    ├── bg.sofia.uni.fmi.mjt.show.ShowAPIImpl.java
    ├── ergenka
    │   ├── ergenka.bg.sofia.uni.fmi.mjt.show.Ergenka.java
    │   ├── ergenka.bg.sofia.uni.fmi.mjt.show.HumorousErgenka.java
    │   ├── ergenka.bg.sofia.uni.fmi.mjt.show.RomanticErgenka.java
    │   └── (...)
    ├── elimination
    │   ├── elimination.bg.sofia.uni.fmi.mjt.show.EliminationRule.java
    │   ├── elimination.bg.sofia.uni.fmi.mjt.show.LowestRatingEliminationRule.java
    │   ├── elimination.bg.sofia.uni.fmi.mjt.show.LowAttributeSumEliminationRule.java
    │   ├── elimination.bg.sofia.uni.fmi.mjt.show.PublicVoteEliminationRule.java
    │   └── (...)
    └── date
        └── bg.sofia.uni.fmi.mjt.show.date.DateEvent.java
```

---
## ⚠️ Забележки
> Използването на структури от данни, различни от масив, **не е позволено**. Задачата трябва да се реши с помощта на
> знанията от курса до момента и допълнителните Java APIs, указани в условието.
