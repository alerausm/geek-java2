import java.util.ArrayList;
import java.util.List;

//тот же самый Contest, но с измененными правилами - кто занял последнее место в туре выбывает
public class PlayOffContest extends Contest {
    PlayOffContest(String... args) {
        super(args);
    }
    @Override
    public void onTourEnd(Tour tour,Team team) {
       super.onTourEnd(tour,team);
        List<ContestMember> passMembers = tour.estimateTeam();
        if (passMembers.size()>3) passMembers.remove(passMembers.size()-1);
        team.getMembers().retainAll(passMembers);
        System.out.println("В следующем туре участвуют");
        for(Team.Member member:team.getMembers()) {
            System.out.println(member);
        }
    }
}
