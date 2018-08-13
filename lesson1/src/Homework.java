/**
 Java 2. Homework 1
 @author A.Usmanov
 @version dated 2018-08-13
*/
public class Homework {
    public static void main(String[] args) {
        Contest contest = new Contest("Огонь","Вода","Медные трубы");
        Team team = new Team("Вася","Петя","Маша","Света");
        contest.run(team);
    }
}
