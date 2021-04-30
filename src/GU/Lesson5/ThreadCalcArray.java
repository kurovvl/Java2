package GU.Lesson5;

public class ThreadCalcArray extends Thread {

    private float[] arr;
    public ThreadCalcArray(float[] arr) {
        this.arr = arr;
    }
    @Override
    public void run() {
        reCalcArray();
    }

    private void reCalcArray() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
}
