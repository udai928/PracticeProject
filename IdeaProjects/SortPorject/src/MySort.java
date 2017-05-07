import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by kuzuyayuudai on 2017/01/14.
 */
public class MySort {
    private int elementNum;
    private int elementRange;
    private int[] myArray;

    public MySort(int elementNum, int elementRange) {
        this.elementNum = elementNum;
        this.elementRange = elementRange;
        this.myArray = randomArrayGenerator(elementNum, elementRange);
    }
    public MySort(int[] myArray){
        this.myArray = myArray;
    }

    public int getElementNum() {
        return elementNum;
    }
    public int getElementRange() {
        return elementRange;
    }
    public int[] getMyArray() {
        return myArray;
    }
    public int getMyArrayElement(int elementNum) {
        return myArray[elementNum];
    }

    public int[] randomArrayGenerator(int elementNum, int elementRange) {
        int[] rndArray = new int[elementNum];
        for (int n = 0; n < elementNum; n++) {
            Random rnd = new Random();
            rndArray[n] = rnd.nextInt(elementRange);
        }
        return rndArray;
    }
    /*

    変数名は「[対象][その対象がどういう状態か]」とする。
    例）arrayTempSored
    対象：配列
    どういう状態か：一時的にソートされている状態

    array→配列
    num→その配列要素の配列番号
    element→その配列要素のバリュー

     */

    public int[] insertionSort(int[] myArray) {
        int[] arrayTempSored = new int[myArray.length];
        //最小値が決定したら固定点(numFixed = elementFixed)を次に小さい値にずらす。
        for (int numFixed = 0; numFixed < myArray.length; numFixed++) {
            //固定点の次の要素から固定点と比較する。
            for (int numToBeCompared = 1; numToBeCompared < myArray.length; numToBeCompared++) {
                //固定点と比較対象
                int elementFixed = myArray[numFixed];
                int elementToBeCompared = myArray[numToBeCompared];
                //比較対象が降順の場合のみ、昇順にならべかえる。
                if (elementFixed > elementToBeCompared) {
                    //固定点より小さい値が出現したら小さい値を固定点に置き、
                    arrayTempSored[numFixed] = elementToBeCompared;
                    //元の固定点以降〜比較対象点までの数列を右にずらす。
                    for (int numToBeShiftedFromLeft = numFixed + 1; numToBeShiftedFromLeft <= numToBeCompared; numToBeShiftedFromLeft++) {
                        //左隣の配列要素のバリューをソート済み配列に格納していく。
                        arrayTempSored[numToBeShiftedFromLeft] = myArray[numToBeShiftedFromLeft - 1];
                    }
                    //比較対象点以降〜配列の最後までをそのまま格納する。
                    for (int numAfterToBeComparedToEnd = numToBeCompared + 1; numAfterToBeComparedToEnd < myArray.length; numAfterToBeComparedToEnd++) {
                        arrayTempSored[numAfterToBeComparedToEnd] = myArray[numAfterToBeComparedToEnd];
                    }
                    //並べ替えた数列をシャローコピーではなく、ディープコピーで配列をソート後のものに入れ替える。
                    myArray = Arrays.copyOf(arrayTempSored, arrayTempSored.length);
                    //繰り返し用変数の初期化
                    numToBeCompared = numFixed;
                }
            }
        }
        return myArray;
    }

    public int[] heapSort(int[] myArray) {
        for (int sortZeroPoint = 0; sortZeroPoint < myArray.length; sortZeroPoint++) {
            for (int n = myArray.length - 1; n >= sortZeroPoint; n--) {
                int childLeftArrayNum = (2 * n) + 1 - sortZeroPoint;
                int childRightArrayNum = (2 * n) + 2 - sortZeroPoint;
                if(myArray.length > childLeftArrayNum){
                    int parentArrayNum = n;
                    if(myArray.length > childRightArrayNum) {//子要素が2つある場合
                        int parentValue = myArray[parentArrayNum];
                        int childLeftValue = myArray[childLeftArrayNum];
                        int childRightValue = myArray[childRightArrayNum];
                        if (parentValue > childLeftValue || parentValue > childRightValue) {
                            if (childRightValue >= childLeftValue) {
                                int temp = myArray[childLeftArrayNum];
                                myArray[childLeftArrayNum] = myArray[parentArrayNum];
                                myArray[parentArrayNum] = temp;
                            } else if (childRightValue < childLeftValue) {
                                int temp = myArray[childRightArrayNum];
                                myArray[childRightArrayNum] = myArray[parentArrayNum];
                                myArray[parentArrayNum] = temp;
                            } else {
                                System.out.println("error: なんかおかしい...");
                            }
                        }
                    }else{//子要素が1つの場合
                        int parentValue = myArray[parentArrayNum];
                        int childLeftValue = myArray[childLeftArrayNum];
                        if (parentValue > childLeftValue) {
                            int temp = myArray[childLeftArrayNum];
                            myArray[childLeftArrayNum] = myArray[parentArrayNum];
                            myArray[parentArrayNum] = temp;
                        }
                    }
                }
            }
        }
        return myArray;
    }

    public int[] quickSortMethod(int[] myArray){

        //pivotは数列のランダム番目の値とする。
        Random rnd = new Random();
        int pivotNum = rnd.nextInt(myArray.length);
        int pivot = myArray[pivotNum];

        //leftChangeTargetFound・rightChangeTargetFound:pivotより大きい要素を見つけたら次の試行からスキップするためのフラグ
        boolean leftChangeTargetFound = false;
        boolean rightChangeTargetFound = false;
        int elementLeftNumToChangeWithRight = -1;
        int elementRightNumToChangeWithLeft = -1;
        int elementNumLeft = 0;
        int elementNumRight = myArray.length - 1;

        //pivotの位置と左右の候補の位置がすべて同じの場合、入れ替え候補探索処理を抜ける
        while (pivotNum >= elementNumLeft || pivotNum <= elementNumRight){
            int left = myArray[elementNumLeft];
            int right = myArray[elementNumRight];
            if(leftChangeTargetFound == false && left >= pivot){
                leftChangeTargetFound = true;
                elementLeftNumToChangeWithRight = elementNumLeft;
            }
            //右候補が見つからなかったらpivotを右候補にする。
            if(rightChangeTargetFound == false && right < pivot){
                rightChangeTargetFound = true;
                elementRightNumToChangeWithLeft = elementNumRight;
            }else if(rightChangeTargetFound == false &&elementNumRight == pivotNum){
                rightChangeTargetFound = true;
                elementRightNumToChangeWithLeft = elementNumRight;
            }

            //左右両方の入れ替え候補の要素が決定したら、入れ替える。
            if(leftChangeTargetFound == true && rightChangeTargetFound == true){
                int temp = myArray[elementLeftNumToChangeWithRight];
                myArray[elementLeftNumToChangeWithRight] = myArray[elementRightNumToChangeWithLeft];
                myArray[elementRightNumToChangeWithLeft] = temp;
                leftChangeTargetFound = false;
                rightChangeTargetFound = false;
                //pivotは同じ値を利用するので、配列番号を入れ直す。
                if(pivotNum == elementLeftNumToChangeWithRight && pivotNum != elementRightNumToChangeWithLeft){
                    pivotNum = elementRightNumToChangeWithLeft;
                    pivot = myArray[pivotNum];
                }else if(pivotNum == elementRightNumToChangeWithLeft && pivotNum != elementLeftNumToChangeWithRight){
                    pivotNum = elementLeftNumToChangeWithRight;
                    pivot = myArray[pivotNum];
                }else if(pivotNum == elementRightNumToChangeWithLeft && pivotNum == elementLeftNumToChangeWithRight){
                    break;
                }
                //入れ替えたら入れ替え要素の探索をやり直す。（最下部のインクリメントの分多く足しておく）
                elementNumLeft = 0 - 1;
                elementNumRight = myArray.length - 1 + 1;
            }

            //インクリメント
            elementNumLeft++;
            elementNumRight--;

        }

        int[] leftHalfMyArray = null;
        int[] rightHalfMyArray = null;
        //左右の候補が両方ともpivotの場合はpviotで配列を分割して分割した配列でそれぞれ入れ替え処理を開始する。
        //pivotの配列位置で配列を2分割し、それぞれの配列で探索を行う。
        if(myArray.length > 2) {
            leftHalfMyArray = Arrays.copyOfRange(myArray, 0, pivotNum + 1);
            if(myArray.length > pivotNum + 1) {
                rightHalfMyArray = Arrays.copyOfRange(myArray, pivotNum + 1, myArray.length);
            }
        }else if(myArray.length == 2 && myArray[0] <= myArray[1]) {
            leftHalfMyArray = new int[1];
            leftHalfMyArray[0] = myArray[0];
            rightHalfMyArray = new int[1];
            rightHalfMyArray[0] = myArray[1];
        }else if(myArray.length == 2 && myArray[0] > myArray[1]){
            leftHalfMyArray = new int[1];
            leftHalfMyArray[0] = myArray[1];
            rightHalfMyArray = new int[1];
            rightHalfMyArray[0] = myArray[0];
        }

        //分割した配列2つを順に結合した配列を生成し、返り値に渡す。
        if(leftHalfMyArray.length > 1){
            leftHalfMyArray = quickSortMethod(leftHalfMyArray);
        }
        if(rightHalfMyArray != null && rightHalfMyArray.length > 1){
            rightHalfMyArray = quickSortMethod(rightHalfMyArray);
        }

        int[] joinedArray = null;
        if(rightHalfMyArray != null) {
            joinedArray = new int[leftHalfMyArray.length + rightHalfMyArray.length];
            System.arraycopy(leftHalfMyArray, 0, joinedArray, 0, leftHalfMyArray.length);
            System.arraycopy(rightHalfMyArray, 0, joinedArray, leftHalfMyArray.length, rightHalfMyArray.length);
        }else if(rightHalfMyArray == null){
            joinedArray = new int[leftHalfMyArray.length];
            System.arraycopy(leftHalfMyArray, 0, joinedArray, 0, leftHalfMyArray.length);
        }
        return joinedArray;
    }

    public int[] bucketSort(int[] myArray, int elementRange){
        //前提条件に「要素の範囲<=要素の個数」が約束されている場合に高速でソートできる。
        Random rnd = new Random();
        int myHash = rnd.nextInt(elementRange);

//        int myHash = elementRange;
//        int myHash = 3;

//        printArray(myArray);
//        System.out.println("random int is " + myHash);
//        System.out.println("element range is " + elementRange);


        int bucketArrayNum  = (elementRange / myHash) + 1;
        int[][] bucketArray = new int[bucketArrayNum][elementRange];
        //初期化 値未設定は「-1」とする
        for(int[] x : bucketArray){
            Arrays.fill(x,-1);
        }
        for(int myArrayNum = 0; myArrayNum < myArray.length; myArrayNum++) {
            int hashValue = myArray[myArrayNum] / myHash;
            //該当のバケツにすでに値が入っていたら次の要素に追加する。
            for(int bucketElementNum = 0; bucketElementNum < bucketArray[hashValue].length; bucketElementNum++){
                if(bucketArray[hashValue][bucketElementNum] == -1){
                    bucketArray[hashValue][bucketElementNum] = myArray[myArrayNum];
                    break;
                }
            }
        }

//        printMultiArray(bucketArray);

        int[] resultArray = new int[myArray.length];
        int resultSortNum = 0;
        for(int bucketNum = 0; bucketNum < bucketArray.length; bucketNum++){
            //そのバケツに値が入ってたら、バケツ内をinsertionSortする
            int[] arrayForSort = new int[elementRange];
            for(int bucketElementNum = 0; bucketElementNum < bucketArray[bucketNum].length; bucketElementNum++) {
                arrayForSort[bucketElementNum] = bucketArray[bucketNum][bucketElementNum];
            }
            int[] arrayBucketSorted = insertionSort(arrayForSort);
            for(int m = 0; m < arrayBucketSorted.length ; m++) {
                if(arrayBucketSorted[m] == -1) {

                }else{
                    resultArray[resultSortNum] = arrayBucketSorted[m];
                    resultSortNum++;
                }
            }
        }
//        printArray(resultArray);

        return resultArray;
    }

    public static void printMultiArray(int[][] myMultiArray){
        for (int n = 0; n < myMultiArray.length; n++){
            System.out.print("[");
            for(int m = 0; m < myMultiArray[n].length; m++){
                System.out.print(myMultiArray[n][m] + ",");
            }
            System.out.println("]");
        }
        System.out.println("--------------------------");

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

    public static String checkArray(int[] myArray){
        String result = "Success!";
        if(myArray.length >1){
            //配列0番目は、-1番目は存在しないためチェックしない。
            for(int n = 1; n < myArray.length; n++){
                if(myArray[n] < myArray[n - 1]){
                        return "NG. arrayNum is " + n + ".";
                }
            }
        }else {
            result = "No checked.";
        }
        return result;
    }
}
