package models;

import javax.persistence.Entity;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import play.db.jpa.Model;

@Entity
public class Stat extends Model
{
    public String statDate;
    public String DateTime;
    public float Weight;
    public int Chest;
    public int Thigh;
    public int UpperArm;
    public int Waist ;
    public int Hips;
    public String Comment;
    public boolean weightTrend;

    /**
     * Stat constructor (basic)
     * @param Weight
     * @param Chest
     * @param Thigh
     * @param UpperArm
     * @param Waist
     * @param Hips
     */
    public Stat(float Weight, int Chest, int Thigh, int UpperArm, int Waist, int Hips)
    {
        this.Weight = Weight;
        this.Chest = Chest;
        this.Thigh = Thigh;
        this.UpperArm = UpperArm;
        this.Waist = Waist;
        this.Hips = Hips;
    }

    /**
     * Stat constructor (overloaded with Date, Weight Trend & Trainer Comment)
     * @param DateTime
     * @param Weight
     * @param Chest
     * @param Thigh
     * @param UpperArm
     * @param Waist
     * @param Hips
     * @param weightTrend
     * @param Comment
     */
    public Stat(String DateTime, float Weight, int Chest, int Thigh, int UpperArm, int Waist, int Hips, boolean weightTrend, String Comment)
    {
        this.DateTime = DateTime;
        this.Weight = Weight;
        this.Chest = Chest;
        this.Thigh = Thigh;
        this.UpperArm = UpperArm;
        this.Waist = Waist;
        this.Hips = Hips;
        this.weightTrend = weightTrend;
        this.Comment = Comment;
    }

    /**
     * Stat constructor (overloaded with Date & Weight Trend)
     * @param statDate
     * @param Weight
     * @param Chest
     * @param Thigh
     * @param UpperArm
     * @param Waist
     * @param Hips
     * @param weightTrend
     */
    public Stat(String statDate, float Weight, int Chest, int Thigh, int UpperArm, int Waist, int Hips, boolean weightTrend)
    {
        this.statDate = statDate;
        this.Weight = Weight;
        this.Chest = Chest;
        this.Thigh = Thigh;
        this.UpperArm = UpperArm;
        this.Waist = Waist;
        this.Hips = Hips;
        this.weightTrend = weightTrend;
    }

    /**
     * mutator for Weight (used in calculations)
     * @return
     */
    public float getWeight()
    {
        return Weight;
    }
}