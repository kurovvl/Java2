package GU.Lesson1.Models;

import GU.Lesson1.impl.Disqualify;
import GU.Lesson1.impl.Jumpable;
import GU.Lesson1.impl.Runable;

public class Wall {
    private int heigth = 0;

    public int getHeigth() {
        return heigth;
    }

    public Wall(int heigth) {
        this.heigth = heigth;
    }

    public boolean move(Object object) {
        System.out.print("Высота стены: " + this.heigth + " - ");
        if (object instanceof Runable && object instanceof Disqualify) {
            if (!((Disqualify) object).isDisqualified()) {
                ((Jumpable) object).jump();
                if (getHeigth() <= ((Jumpable) object).getJumpHeight()) {
                    System.out.println("таки перепрыгивает стену");
                    return true;
                } else {
                    System.out.println("становится частью стены");
                    ((Disqualify) object).disqualify();
                    return false;
                }
            }
        }
        return false;
    }
}
