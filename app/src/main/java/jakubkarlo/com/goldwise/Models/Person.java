package jakubkarlo.com.goldwise.Models;

/**
 * Created by Jakub on 20.11.2017.
 */

public class Person {

    private String name;
    private double share;


    public Person(String name, double share) {
        this.name = name;
        this.share = share;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
