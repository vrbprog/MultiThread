import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        final int size = 10000000;
        float[] arr = new float[size];
        float[] arrm = new float[size];

        for (int i = 0; i < size; i++) {
            arr[i] = 1.0f;
        }
        long start = System.currentTimeMillis();
        arr = mathOneThread(arr);
        long end = System.currentTimeMillis();
        System.out.println("OneThread function execution time is: " + (end-start) + "ms");

        for (int i = 0; i < size; i++) {
            arrm[i] = 1.0f;
        }
        start = System.currentTimeMillis();
        arrm = mathMultiThread(arrm);
        end = System.currentTimeMillis();
        System.out.println("MultiThread function execution time is: " + (end-start) + "ms");

        boolean status = true;
        for (int i = 0; i < size; i++) {
            if(arr[i] != arrm[i]) {
                status = false;
                continue;
            }
        }

        System.out.printf("Arrays \"arr\" and \"arrm\" %s identical", status ? "is" : "is not");


    }

    public static float[] mathOneThread(float[] array){
        int len = array.length;
        for (int i = 0; i < len; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return array;
    }

    public static float[] mathMultiThread(float[] array){
        final int numberOfProc = Runtime.getRuntime().availableProcessors();
        float[][] multiArr = new float[numberOfProc][array.length/numberOfProc];
        Thread[] masThread = new Thread[numberOfProc];

        for (int i = 0; i < numberOfProc; i++) {
            multiArr[i] = Arrays.copyOfRange(array, i*array.length/numberOfProc, (i+1)*array.length/numberOfProc);
            masThread[i] = new Thread(new MyRunnable(multiArr[i],i*array.length/numberOfProc));
            masThread[i].start();
        }

        try {
            masThread[numberOfProc - 1].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfProc; i++) {
            System.arraycopy(multiArr[i], 0, array, i*array.length/numberOfProc, array.length/numberOfProc);
        }
        return array;

    }

}