package MainSpring.Schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import MainSpring.Model.AuthenticatedUsers;
import MainSpring.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        checkTwoMinutesPassed(authenticatedUsers.hashMap);
    }

    private void checkTwoMinutesPassed(HashMap<String, User> hashMap) {
        Calendar time;
        for (Map.Entry<String, User> entry : hashMap.entrySet()) {
            time = GregorianCalendar.getInstance();
            String token = entry.getKey();
            User user = entry.getValue();
            if(time.getTimeInMillis() - user.getLoggedIn().getTimeInMillis() > 120000) {
                hashMap.remove(token);
                System.out.println("removi um token");
            }
        }
    }
}
