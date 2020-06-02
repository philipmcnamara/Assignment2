package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import models.Member;
import models.Stat;
import play.Logger;
import play.mvc.Controller;

public class MemberCtrl extends Controller
{
    /**
     * Sends member to the correct member view html
     * @param id
     */
    public static void index(Long id)
    {
        Member member = Member.findById(id);
        session.put("logged_in_Memberid", member.id);
        Logger.info ("Member id = " + id);
        render("member.html", member);
    }

    /**
     * Allows a member to delete a line of stats (An Asssessment)
     * @param id
     * @param statid
     */
    public static void deleteStat (Long id, Long statid)
    {
        Member member = Member.findById(id);
        Stat stat = Stat.findById(statid);
        Logger.info ("Removing" + stat.Weight);
        member.stats.remove(stat);
        member.save();
        stat.delete();
        render("member.html", member);
    }

    /**
     * Allows a member to creat a new line of Stats
     * Checks and adds the Date to the list of stats
     * Checks the previous weight and returns a true or false value as to weather it was above or below the previous value (Used to create an icon later on)
     * Saves Changes
     * @param id
     * @param Weight
     * @param Chest
     * @param Thigh
     * @param UpperArm
     * @param Waist
     * @param Hips
     */
    public static void addStat (Long id, float Weight, int Chest, int Thigh, int UpperArm, int Waist, int Hips)
    {
        boolean weightTrend = calculateTrend(id,Weight);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String statDate = dateFormat.format(date);
        Stat stat = new Stat(statDate,Weight,Chest,Thigh,UpperArm,Waist,Hips,weightTrend);
        Member member = Member.findById(id);
        member.stats.add(stat);
        member.save();
        redirect("/members/" + id);
    }

    /**
     * called by the above method to check if the Member has lost/gained any weight
     * @param id
     * @param weight
     * @return
     */
    public static boolean calculateTrend(Long id, float weight)
    {
        Member member = Member.findById(id);
        List<Stat> stats = member.stats;
        boolean lostWeight = false;

        if(stats.size() > 1)
        {
            int previousPosition = stats.size() -1;
            float previousWeight = stats.get(previousPosition).getWeight();

            if(weight < previousWeight)
            {
                lostWeight = true;
            }
        }
        return lostWeight;
    }
}