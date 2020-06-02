package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Logger;
import play.db.jpa.Model;
import static controllers.Accounts.getLoggedInMember;

@Entity

public class Member extends Model
{
    public String name;
    public String gender;
    public String email;
    public String password;
    public String address;
    public float height;
    public static float startingWeight;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Stat> stats= new ArrayList<Stat>();

    /**
     * Constructor for Member class
     */
    public Member(String name, String gender, String email, String password, String address, float height, float startingWeight)
    {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.address = address;
        this.height = height;
        Member.startingWeight = startingWeight;
    }

    /**
     * Getters and setters for Member class to follow
     * @return
     */
    public float getHeight() { return this.height; }
    public void setHeight(float height)
    {
        this.height = height;
    }
    public static float getStartingWeight()
    {
        return startingWeight;
    }
    public void setStartingWeight(float startingWeight)
    {
        this.startingWeight = startingWeight;
    }
    public String getGender()
    {
        return this.gender;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }


    /**
     * finds Member via their stored email address
     * @param email
     * @return
     */
    public static Member findByEmail(String email)
    {
        return find("email", email).first();
    }


    /**
     * finds members password and cheks if their is a match
     * @param password
     * @return
     */
    public boolean checkMemberPassword(String password)
    {
        return this.password.equals(password);
    }


    /**
     * checks members height and most recent assessment weight.
     * uses this information to determine the BMI
     * returns the BMI as a float
     * @return
     */
    public static float calculateMemberBMI()
    {
        float BMI = 0;
        float roundBMI;
        Member member = getLoggedInMember();
        float calcHeight = member.getHeight();
        List<Stat> stats = member.stats;
        float memberStatWeight =0;

        if(stats.size() != 0)
        {
            int mostRecent = stats.size() -1;
            memberStatWeight = stats.get(mostRecent).getWeight();
        }
        BMI= ((memberStatWeight)/(calcHeight*calcHeight)); //calculates BMI
        roundBMI = (float) (Math.round(BMI*100)/100.0); // rounds to 2 decimals
        return roundBMI;
    }

    /**
     * determines the BMI (as above)
     * cheks this value against a recommended chart & returns this as a String
     * @return
     */
    public static String determineBMICategory()
    {
        float roundBMI = calculateMemberBMI();

        String bmiCat ="";

        if (roundBMI <16)
        {
            bmiCat =  "SEVERELY UNDERWEIGHT";
        }
        else if (roundBMI >=16 && roundBMI<18.5)
        {
            bmiCat = "UNDERWEIGHT";
        }
        else if (roundBMI >=18.5 && roundBMI<25)
        {
            bmiCat = "NORMAL";
        }
        else if (roundBMI >=25 && roundBMI <30)
        {
            bmiCat = "OVERWEIGHT";
        }
        else if (roundBMI >=30 && roundBMI <35)
        {
            bmiCat = "MODERTLY OBESE";
        }
        else
        {
            bmiCat = "SEVERLY OBESE";
        }
        return bmiCat;
    }

    /**
     * member height is taken in, in Meters, this converts it to inches
     * Checks the member height
     * returns inches as a float
     * @return
     */
    public static float heightConversion ()
    {
        float meterHeight = getLoggedInMember().getHeight();
        Logger.info ("Meter Height = " + meterHeight);
        float inchesHeight =0;
        inchesHeight = (float) (meterHeight*39.37);
        return  inchesHeight;
    }

    /**
     * takes in member height in inches (from above formula)
     * takes in most recent assessment weight (stat)
     * takes in member gender
     * checks all the above using a formula (explained in individual comments below)
     * Returns a String as to weather the member either is or isn't an ideal weight.
     * I'm super proud of how efficient I was able to make this method...it took me ages.
     * @return
     */
    public static String isIdealBodyWeight()
    {
        String weightCheck = "";
        boolean idealWeight = false;
        Member member = getLoggedInMember();
        List<Stat> stats = member.stats;
        float memberStatWeight =0;

        if(stats.size() != 0)
        {
            int mostRecent = stats.size() -1;
            memberStatWeight = stats.get(mostRecent).getWeight();
        }
        else
        {
            memberStatWeight = Member.getStartingWeight();
        }
        float hCon = heightConversion();
        float excessInches = 0;
        float calcIdealWeight = 0;
        String gender = getLoggedInMember().getGender();

        Logger.info ("hCon = " + hCon);



        if (hCon > 60) // if the member is over 5 ft
        {
            excessInches = hCon - 60; // calculate the number of excess inches
        }
        Logger.info ("Excess Inches = " + excessInches);
        if (getLoggedInMember().getGender().equals("male"))
        {
            calcIdealWeight = (float) (50 + (2.3 * excessInches)); //if excessInches has remained as 0 (person is therefore under 5ft & 50 + 0 is still 50) if not calculation are made on each inch above 5 ft
            if ((memberStatWeight >= (calcIdealWeight - 0.2)) && (memberStatWeight <= (calcIdealWeight + 0.2))) //allowing for buffer of +/- 0.2kg
            {
                idealWeight = true; //if not boolean remains false
            }
        }
        else
        {
            calcIdealWeight = (float) (45.5 + (2.3 * excessInches)); // same as above with weights changed as the person is either Female or non Specified
            if ((memberStatWeight >= (calcIdealWeight - 0.2)) && (memberStatWeight <= (calcIdealWeight + 0.2)))
            {
                idealWeight = true;
            }
            Logger.info ("Weight = " + memberStatWeight);
            Logger.info ("calcIdealWeight = " + calcIdealWeight);
        }
        if (idealWeight) //Returns String response based on the boolean value passed to it.
        {
            weightCheck += "You are an Ideal Weight";
        }
        else
        {
            weightCheck += "Your Weight is not Ideal";
        }
        return weightCheck;
    }
}