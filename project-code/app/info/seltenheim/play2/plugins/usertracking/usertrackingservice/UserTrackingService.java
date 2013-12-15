package info.seltenheim.play2.plugins.usertracking.usertrackingservice;

import play.mvc.Http.Context;

public interface UserTrackingService {
    
    void initTracking(Context context, String controller);
    void initTracking(Context context, String controller, String action);
    void ignoreContext(Context context);
    
    void track(String session, String controller);
    void track(String session, String controller, String action);
}
