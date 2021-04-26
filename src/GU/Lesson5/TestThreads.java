package GU.Lesson5;

import java.util.Arrays;

public class TestThreads {
    static final int size = 10000000;
    static final int h = size / 2;

    public void singleThread() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);

        long start = System.currentTimeMillis();
        calcArray(0, arr.length, arr);
        System.out.println("Single thread: " + (System.currentTimeMillis() - start) / 1000F + " sec");
        //System.out.println(arr[5]); System.out.println(arr[h+5]);
    }

    public void doubleThread() throws InterruptedException {
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);

        long start = System.currentTimeMillis();
        Thread[] pool = new Thread[2];
        for (int i = 0; i < 2; i++) {
            int finalI = i;                 /// Почему при создании Треда он не захотел испольтзовать i из фора, а захотел новую временную переменную?

            pool[i] = new Thread(() -> {
                calcArray(finalI * h, h + h * finalI, arr);
                //System.out.println("st: " + finalI * h + ", en: " + (h + h * finalI));
            });
        }
        for (Thread thread : pool) thread.start();
        for (Thread thread : pool) thread.join();
        System.out.println("Double thread: " + (System.currentTimeMillis() - start) / 1000F + " sec");
        //System.out.println(arr[5]); System.out.println(arr[h+5]);
    }

    public void calcArray(int st, int en, float[] arr) {
        for (int i = st; i < en; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }


}
