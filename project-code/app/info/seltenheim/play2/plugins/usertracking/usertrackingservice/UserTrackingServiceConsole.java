package info.seltenheim.play2.plugins.usertracking.usertrackingservice;

import play.Logger;

public class UserTrackingServiceConsole extends UserTrackingServiceBase implements UserTrackingService {

    public void track(String session, String controller) {
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' an action.");
    }

    public void track(String session, String controller, String action) {
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' the action '" + action + "'.");
    }
}
