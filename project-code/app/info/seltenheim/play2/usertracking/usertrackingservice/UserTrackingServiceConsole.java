package info.seltenheim.play2.usertracking.usertrackingservice;

import play.Logger;
import play.mvc.Http.Context;

public class UserTrackingServiceConsole extends UserTrackingServiceBase implements UserTrackingService {
    protected final static String TRACKING_ID_NAME = "id";


    public void track(Context context, String controller) {
        final String session = session(context);
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' an action.");
    }

    public void track(Context context, String controller, String action) {
        final String session = session(context);
        Logger.debug("'" + session + "' accessed in controller '" + controller + "' the action '" + action + "'.");
    }
}
