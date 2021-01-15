public class MyRunnable implements Runnable{
    private float[] arr;
    private int offset;

    public MyRunnable(float[] array, int offset) {
        this.arr = array;
        this.offset = offset;
    }

    @Override
    public void run() {
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + offset) / 5) * Math.cos(0.2f + (i + offset) / 5) * Math.cos(0.4f + (i + offset) / 2));
        }
    }
}
