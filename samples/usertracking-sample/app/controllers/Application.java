package controllers;

import info.seltenheim.play2.usertracking.actions.TrackUser;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
}
