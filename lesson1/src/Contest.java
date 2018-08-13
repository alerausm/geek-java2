import java.util.*;

public class Contest {
    private final ArrayList<Tour> tours = new ArrayList<>(); //список туров
    private final Map<Team.Member,Integer> scores = new HashMap<>(); //список очков за места в туре

    public Contest(String... tours) {
        for (String name:tours) this.tours.add(new Tour(name)); //создаем туры по переданным названиям
    }

    //главный метод всего соревнования
    public void run(Team team) {
        for (Tour tour:tours) { //обходим все туры
            System.out.println("Начато соревнование "+tour);
            for (Team.Member member : team.getMembers()) { //заставляем каждого участника выполнить задание
                member.doContest(tour);
            }
            int score = 3; //3 балла за первое место
            System.out.println("Результаты соревнования");
            for (Team.Member member :tour.estimateTeam()) { //сортируем команду по количеству баллов
                System.out.println("Место №"+(4-score)+". "+member);
                scores.put(member,scores.getOrDefault(member,0)+score); //первым трем участникам начисляем очки за место
                score--;
                if (score<1) break;
            }
        }
        System.out.println("Итоговые результаты");
        int place = 1;
        for (Team.Member member: estimateTeam (scores)) { //сортируем участников по набранному количеству баллов м выводим их
            System.out.println("Место №"+(place++)+". "+member);
        }
}

    //Возвращает список ключей (участников), отсортированный по количеству очков (от большего к меньшему)
    static List<Team.Member> estimateTeam(Map<Team.Member,Integer> scores) {
        //чем больше очков тем лучше
        List<Team.Member> result  = new ArrayList<>(scores.keySet());
        result.sort((o1, o2) -> scores.get(o2).compareTo(scores.get(o1)));
        return result;
    }

    //класс описывающий отдельный тур
    class Tour {
        final private String name;
        private final Map<Team.Member,Integer> scores = new HashMap<>();
        public Tour(String name) {
            this.name = name;
        }
        //использует участник для записи своих результатов
        public void setResult(Team.Member member, int result) {
            scores.put(member,result);
        }

        @Override
        public String toString() {
            return this.name;
        }
        //сортирует участников по количеству набранных очков (от большего к меньшему)
        public List<Team.Member> estimateTeam() {
            return Contest.estimateTeam(scores);
        }
    }
}
