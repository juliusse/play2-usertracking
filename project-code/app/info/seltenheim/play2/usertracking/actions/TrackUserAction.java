package info.seltenheim.play2.usertracking.actions;

import info.seltenheim.play2.usertracking.ExpiringMap;
import info.seltenheim.play2.usertracking.UserTrackingPlugin;
import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Scheduler;

public class TrackUserAction extends Action<TrackUser> {

    private static Map<Context, Cancellable> contextLoggingMap = new ExpiringMap<Context, Cancellable>();

    @Override
    public Result call(Context context) throws Throwable {
        final String controller = configuration.controller();
        final String action = configuration.action();
        final ExecuteTracking executeTracking = new ExecuteTracking(context, controller, action);

        final ActorSystem system = UserTrackingPlugin.getActorSystem();
        final Scheduler scheduler = system.scheduler();

        Cancellable cancellable = scheduler.scheduleOnce(Duration.apply(1, TimeUnit.SECONDS), executeTracking, system.dispatcher());
        contextLoggingMap.put(context, cancellable);

        return delegate.call(context);
    }

    private static final class ExecuteTracking implements Runnable {
        private final Context context;
        private final String controller;
        private final String action;

        public ExecuteTracking(Context context, String controller, String action) {
            super();
            this.context = context;
            this.controller = controller;
            this.action = action;
        }

        @Override
        public void run() {
            final UserTrackingService service = UserTrackingPlugin.getUserTrackingService();
            if (action == null) {
                service.track(context, controller);
            } else {
                service.track(context, controller, action);
            }
        }
    }
}
