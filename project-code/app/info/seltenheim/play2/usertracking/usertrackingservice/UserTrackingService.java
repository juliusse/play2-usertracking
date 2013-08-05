package info.seltenheim.play2.usertracking.usertrackingservice;

import play.mvc.Http.Context;

public interface UserTrackingService {
    
    void initTracking(Context context, String controller);
    void initTracking(Context context, String controller, String action);
    void ignoreContext(Context context);
    
    void track(Context context, String controller);
    void track(Context context, String controller, String action);
}
