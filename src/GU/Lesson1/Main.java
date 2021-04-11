package GU.Lesson1;

import GU.Lesson1.Models.*;
import GU.Lesson1.impl.Runable;

public class Main {


    public static void main(String[] args) {
        Object cat = new Cat("Барсик",100,50 );
        Object human = new Human("Борис",10,10 );
        Object robot = new Robot("Бендер",300,20 );
        Object[] objList = {cat, human, robot};

        var wall = new Wall(35);
        var road = new Road(120);

        for (Object o : objList) {
            wall.move(o);
            road.move(o);
        }



    }

}
