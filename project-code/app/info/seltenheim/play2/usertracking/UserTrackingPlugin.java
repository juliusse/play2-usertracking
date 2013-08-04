package info.seltenheim.play2.usertracking;

import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingService;
import info.seltenheim.play2.usertracking.usertrackingservice.UserTrackingServiceConsole;
import play.Application;
import play.Configuration;
import play.Logger;
import play.Plugin;
import play.mvc.Http;
import play.mvc.Http.Context;
import akka.actor.ActorSystem;

public class UserTrackingPlugin extends Plugin {    
    private static UserTrackingService service = null;
    private static ActorSystem system = ActorSystem.apply();
    private final Application application;
    
    public UserTrackingPlugin(Application application) {
        this.application = application;
    }
    
    @Override
    public void onStart() {        
        final Configuration config = application.configuration();
        final String implClass = config.getString("info.seltenheim.play2.usertracking.serviceImpl");
        if(implClass == null) {
            Logger.info("no service implementation found, using Console. To define a service implementation use 'info.seltenheim.play2.usertracking.serviceImpl'.");
            service = new UserTrackingServiceConsole();
        } else {
            try {
                Class<?> clazz = Class.forName(implClass);
                service = (UserTrackingService)clazz.getConstructor().newInstance();
            } catch (Exception e) {
                Logger.error("Problem instanciating service implementation, Using Console! ",e);
                service = new UserTrackingServiceConsole();
            }
        }
        
    }
    
    public static UserTrackingService getUserTrackingService() {
        return service;
    }
    
    public static ActorSystem getActorSystem() {
        return system;
    }
   
}
