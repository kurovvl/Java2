package GU.Lesson5;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;


    public static void main(String[] args) {

        try
        {
            System.out.println("Test_1");
            test1();    // Сначала сделал так, потом увидел, что функция расчета включает в себя номер ячейки основного
                        // массива, а значит данные будут отличаться при использовании дробления
            System.out.println("Test_2");
            test2();    // Поэтому поэксперементировал немного.. убрал дробление по копиям т.к. массивы - ссылочный тип
        }catch (InterruptedException ex){}





    }

    public static void test1() throws InterruptedException {
        singleThread();
        doubleThread();
    }

    public static void test2() throws InterruptedException {
        new TestThreads().singleThread();
        new TestThreads().doubleThread();
    }

    public static void singleThread() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);

        var start = System.currentTimeMillis();
        new ThreadCalcArray(arr).run();
        System.out.println("Single thread: " + (System.currentTimeMillis() - start) / 1000F + " sec");
    }

    public static void doubleThread() throws InterruptedException {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);


        var start = System.currentTimeMillis();

        var a1 = new float[h];
        var a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        var t1 = new ThreadCalcArray(a1);
        var t2 = new ThreadCalcArray(a2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Double thread: " + (System.currentTimeMillis() - start) / 1000F + " sec");
    }
}

