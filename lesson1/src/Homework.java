/**
 Java 2. Homework 1
 @author A.Usmanov
 @version dated 2018-08-13
*/
public class Homework {
    public static void main(String[] args) {

        Team team = new Team("Вася","Петя","Маша","Света","Федя","Фрося");
        Contest contest = new Contest("Огонь","Вода","Медные трубы");
        contest.run(team);
        Contest playoff = new PlayOffContest("Огонь","Вода","Медные трубы");
        playoff.run(team);
    }
}
