package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class About extends Controller //basic point of the website screen, link to html site with info
{
  public static void index() {
    Logger.info("Rendering about");
    render ("about.html");
  }
}
