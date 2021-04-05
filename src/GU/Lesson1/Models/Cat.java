package GU.Lesson1.Models;

import GU.Lesson1.impl.Disqualify;
import GU.Lesson1.impl.Jumpable;
import GU.Lesson1.impl.Runable;

public class Cat implements Jumpable, Runable, Disqualify {
    private String name;
    private int runDistance;
    private int jumpHeight;
    private boolean disqualified = false;

    public void setDisqualified(boolean disqualified) {
        this.disqualified = disqualified;
    }


    public Cat(String name, int distance, int jumpHeight) {
        this.name = name;
        this.runDistance = distance;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean isDisqualified() {
        return checkDisq();
    }

    @Override
    public void jump() {
        System.out.print("Кот " + this.name + " прыгает и... ");
    }

    @Override
    public int getJumpHeight() {
        return this.jumpHeight;
    }

    @Override
    public void run() {
        System.out.print("Кот " + this.name + " бежит и... ");
    }

    @Override
    public int getRunLength() {
        return this.runDistance;
    }

    private boolean checkDisq() {
        if (disqualified) System.out.println("Кот " + this.name + " дисквалифицирован");
        return disqualified;
    }

    @Override
    public void disqualify() {
        setDisqualified(true);
    }
}
