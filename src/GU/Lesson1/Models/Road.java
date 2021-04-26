package GU.Lesson1.Models;

import GU.Lesson1.impl.Disqualify;
import GU.Lesson1.impl.Runable;

public class Road {
    private int length;

    public Road(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public boolean move(Object object) {
        System.out.print("Длина дороги: " + this.length + " - ");
        if (object instanceof Runable && object instanceof Disqualify) {
            if (!((Disqualify)object).isDisqualified()){
                ((Runable) object).run();
                if (getLength() <= ((Runable) object).getRunLength()) {
                    System.out.println("таки пробегает всю дистанцию");
                    return true;
                } else {
                    System.out.println("увы - спотыкается и падает");
                    ((Disqualify)object).disqualify();
                    return false;
                }
            }

        }
        return false;
    }

}
