package info.seltenheim.play2.usertracking.usertrackingservice;

import info.seltenheim.play2.plugins.usertracking.usertrackingservice.UserTrackingService;
import info.seltenheim.play2.plugins.usertracking.usertrackingservice.UserTrackingServiceConsole;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTrackingServiceConsoleTests {
    private static UserTrackingService service;
    
    
    @BeforeClass
    public static void setUpClass() {
        service = new UserTrackingServiceConsole();
    }

    @Test  
    public void testTrack() {
        service.track("foo", "bar");
        service.track("foo", "bar","action");
    }
 
}
