package info.seltenheim.play2.usertracking.actions;

import info.seltenheim.play2.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingService;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public class TrackUserAction extends Action<TrackUser> {
    @Override
    public Result call(Context context) throws Throwable {
        String controller = configuration.controller();
        String action = configuration.action();

        call(context, controller, action);

        return delegate.call(context);
    }

    public static void call(Context context, String controller, String action) {
        final UserTrackingService service = UserTrackingPlugin.getUserTrackingService();
        service.initTracking(context, controller, action);
    }


}
