import java.util.*;

public class Contest {

    public interface ContestMember {
        void doContest(Tour tour);
    }
    protected final ArrayList<Tour> tours = new ArrayList<>(); //список туров
    protected final Map<ContestMember,Integer> scores = new HashMap<>(); //список очков за места в туре

    public Contest(String... tours) {
        for (String name:tours) this.tours.add(new Tour(name)); //создаем туры по переданным названиям
    }

    public void onTourEnd(Tour tour,Team team) {
        List<ContestMember> winners = tour.getWinners();
        int i = 1;
        System.out.println("Результаты соревнования");
        for (ContestMember member:winners) {
            System.out.println("Место №"+(i++)+". "+member);
        }
    }
    //главный метод всего соревнования
    public void run(Team team) {
        for (Tour tour:tours) { //обходим все туры
            System.out.println("Начато соревнование "+tour);
            for (ContestMember member : team.getMembers()) { //заставляем каждого участника выполнить задание
                member.doContest(tour);
            }
            List<ContestMember> winners = tour.getWinners();
            int score = winners.size();
            for (ContestMember member :winners) { //сортируем команду по количеству баллов
                scores.put(member,scores.getOrDefault(member,0)+(score--)); //первым трем участникам начисляем очки за место
            }
            onTourEnd(tour,team);
        }
        System.out.println("Итоговые результаты");
        int place = 1;
        for (ContestMember member: estimateTeam (scores)) { //сортируем участников по набранному количеству баллов м выводим их
            System.out.println("Место №"+(place++)+". "+member);
        }
}

    //Возвращает список ключей (участников), отсортированный по количеству очков (от большего к меньшему)
    static List<ContestMember> estimateTeam(Map<ContestMember,Integer> scores) {
        //чем больше очков тем лучше
        List<ContestMember> result  = new ArrayList<>(scores.keySet());
        result.sort((o1, o2) -> scores.get(o2).compareTo(scores.get(o1)));
        return result;
    }

    //класс описывающий отдельный тур
    class Tour {
        final private String name;
        private final Map<ContestMember,Integer> scores = new HashMap<>();
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
        public List<ContestMember> estimateTeam() {
            return Contest.estimateTeam(scores);
        }
        //метод определяющий список победителей
        public List<ContestMember> getWinners() {
            List<ContestMember> members = estimateTeam();
            List<ContestMember> winners = new ArrayList<>();
            for (int i = 0;i<3&&i<members.size();i++) {
                winners.add(members.get(i));
            }
            return winners;
        }
    }
}
