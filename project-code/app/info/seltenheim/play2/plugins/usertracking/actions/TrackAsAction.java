package info.seltenheim.play2.plugins.usertracking.actions;

import info.seltenheim.play2.plugins.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.plugins.usertracking.usertrackingservice.UserTrackingService;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.SimpleResult;

public class TrackAsAction extends Action<TrackAs> {
    @Override
    public Promise<SimpleResult> call(Context context) throws Throwable {
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