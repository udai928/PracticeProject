import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by kuzuyayuudai on 2017/01/14.
 */
public class Main {
    public static void main(String[] args){

        printMemoryInfo();

        int elementNum = 4000;
        int elementRange = 40000;
        MySort ms = new MySort(elementNum, elementRange);
        int[] myArray = ms.getMyArray();

//        int[] testArray = {5,6,3,3,9,8,4,5,3,9,1,0,6,9,0,2,6,4,5,0};
//        int elementRange = testArray.length;
//        MySort ms = new MySort(testArray);
//        int[] myArray = ms.getMyArray();


        System.out.println(ms.checkArray(myArray));

        System.out.println("\ninsertionSort start.");
        int[] insertionSortedArray = Arrays.copyOf(myArray,myArray.length);
        printTime();
        insertionSortedArray = ms.insertionSort(insertionSortedArray);
        printTime();
        System.out.println(ms.checkArray(insertionSortedArray));
        System.out.println("insertionSort end.\n\n");

        System.out.println("\nheapSort start.");
        int[] heapSortedArray = Arrays.copyOf(myArray,myArray.length);
        printTime();
        heapSortedArray = ms.heapSort(heapSortedArray);
        printTime();
        System.out.println(ms.checkArray(heapSortedArray));
        System.out.println("heapSort end.\n\n");

        System.out.println("\nquickSortMethod start.");
        int[] quickSortedArray = Arrays.copyOf(myArray,myArray.length);
        printTime();
        quickSortedArray = ms.quickSortMethod(quickSortedArray);
        printTime();
        System.out.println(ms.checkArray(quickSortedArray));
        System.out.println("quickSort end.\n\n");

        System.out.println("\nbucketSortMethod start.");
        int[] bucketSortedArray = Arrays.copyOf(myArray,myArray.length);
        printTime();
        bucketSortedArray = ms.bucketSort(bucketSortedArray,elementRange);
        printTime();
        System.out.println(ms.checkArray(bucketSortedArray));
        System.out.println("bucketSort end.\n\n");

        printMemoryInfo();

    }

    public static void printTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println("This time is ... " + sdf.format(c.getTime()));
    }
    public static void printArray(int[] myArray){
        String separator = System.getProperty("line.separator");
        System.out.print("[");
        for (int n = 0; n < myArray.length - 1; n++){
            System.out.print(myArray[n] + ",");
        }
        System.out.print(myArray[myArray.length - 1] + "]" +
                separator +  "and myArray.length is " + myArray.length);
        System.out.println("");
    }
    public static void printMemoryInfo() {
        long free = Runtime.getRuntime().freeMemory() / 1024;
        long total = Runtime.getRuntime().totalMemory() / 1024;
        long max = Runtime.getRuntime().maxMemory() / 1024;
        System.out.println("");
        System.out.println("####### Memory Info ################");
        System.out.println("total => " + total / 1024 + "KB");
        System.out.println("free  => " + free / 1024 + "KB");
        System.out.println("used  => " + (total - free) / 1024 + "KB");
        System.out.println("max   => " + max / 1024 + "KB");
        System.out.println("####################################");
        System.out.println("");
        System.out.println("");
    }
}
