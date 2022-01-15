package MainSpring.Schedule;

import java.text.SimpleDateFormat;

import MainSpring.Model.AuthenticatedUsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private AuthenticatedUsers authenticatedUsers;

    private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000) // 2 minutos - 120000
    public void reportCurrentTime() {
        //log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("Lista de tokens:");
        log.info(String.valueOf(authenticatedUsers.hashMap.keySet()));
//        for(User u : lista.usersList){
//            log.info(u.getUsername());
//            log.info(u.getPassword());
//        }
    }
}
