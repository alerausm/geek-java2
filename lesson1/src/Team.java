import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//класс команды
public class Team {
    private final ArrayList<Member> members = new ArrayList<>(); //здесь хранятся участники
    public Team(String... names) {
        for (String name:names) members.add(new Member(name));
    }

    public List<Member> getMembers() {
        return this.members;
    }
    //класс участника
    class Member {
        final private String name;
        private int score = 0;
        Member(String name) {
            this.name = name;
        }
              //метод в котором участник выполняем задание
        public void doContest(Contest.Tour tour) {
            Random random = new Random();
            int myResult = random.nextInt(10)+1;
            tour.setResult(this,myResult); //после выполнения он записывает свой результат в задание
            System.out.println(name+" поучаствовал в соревновании "+tour+" с результатом "+myResult);
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
