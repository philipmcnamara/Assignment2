package controllers;

import java.util.List;

import models.Stat;
import play.mvc.Controller;

/**
 * creates an admin view
 */
public class Admin extends Controller
{
    public static void index()
    {
        List<Stat> stats = Stat.findAll();
        render("admin.html", stats);
    }
}