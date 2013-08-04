package info.seltenheim.play2.usertracking.usertrackingservice;

import play.Logger;
import play.mvc.Http;
import play.mvc.Http.Context;

public class UserTrackingServiceConsole implements UserTrackingService {
    protected final static String TRACKING_ID_NAME = "id";

    @Override
    public void track(Context context, String controller) {
        final String session = session(context);
        Logger.debug("'"+session+"' accessed in controller '"+controller+"' an action.");
    }

    @Override
    public void track(Context context, String controller, String action) {
        final String session = session(context);
        Logger.debug("'"+session+"' accessed in controller '"+controller+"' the action '"+action+"'.");
    }

    private String session(Context context) {
        final Http.Session session = context.session();
        // Generate a unique id
        String id = session.get(TRACKING_ID_NAME);
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
            session.put(TRACKING_ID_NAME, id);
        }
        
        return id;
    }

}
