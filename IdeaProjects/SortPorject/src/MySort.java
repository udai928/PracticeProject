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

    public MySort(int[] myArray) {
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
    index→その配列要素の配列番号
    element→その配列要素のバリュー

     */

    /*
    insertionSort
    1. 配列の0番目を固定点とする。
    2. 固定点の次の配列要素から順番に比較していく。
    3. 固定点より小さい値が出現したら、その値を固定点の左に移動する。
    4. 固定点〜左に移動した要素までを右に詰める。
    5. 繰り返す

     */
    public int[] insertionSort(int[] myArray) {
        int[] arrayTempSored = new int[myArray.length];
        //最小値が決定したら固定点(indexFixed = elementFixed)を次に小さい値にずらす。
        for (int indexFixed = 0; indexFixed < myArray.length; indexFixed++) {
            //固定点の次の要素から固定点と比較する。
            for (int indexToBeCompared = 1; indexToBeCompared < myArray.length; indexToBeCompared++) {
                //固定点と比較対象
                int elementFixed = myArray[indexFixed];
                int elementToBeCompared = myArray[indexToBeCompared];
                //比較対象が降順の場合のみ、昇順にならべかえる。
                if (elementFixed > elementToBeCompared) {
                    //固定点より小さい値が出現したら小さい値を固定点に置き、
                    arrayTempSored[indexFixed] = elementToBeCompared;
                    //元の固定点以降〜比較対象点までの数列を右にずらす。
                    for (int indexToBeShiftedFromLeft = indexFixed + 1; indexToBeShiftedFromLeft <= indexToBeCompared; indexToBeShiftedFromLeft++) {
                        //左隣の配列要素のバリューをソート済み配列に格納していく。
                        arrayTempSored[indexToBeShiftedFromLeft] = myArray[indexToBeShiftedFromLeft - 1];
                    }
                    //比較対象点以降〜配列の最後までをそのまま格納する。
                    for (int indexAfterToBeComparedToEnd = indexToBeCompared + 1; indexAfterToBeComparedToEnd < myArray.length; indexAfterToBeComparedToEnd++) {
                        arrayTempSored[indexAfterToBeComparedToEnd] = myArray[indexAfterToBeComparedToEnd];
                    }
                    //並べ替えた数列をシャローコピーではなく、ディープコピーで配列をソート後のものに入れ替える。
                    myArray = Arrays.copyOf(arrayTempSored, arrayTempSored.length);
                    //繰り返し用変数の初期化
                    indexToBeCompared = indexFixed;
                }
            }
        }
        return myArray;
    }


    /*

    heapSort
    親heap、左子heap、右子heapの値の大きさを比較し、最小値を親heapと入れ替える。

     */
    public int[] heapSort(int[] myArray) {
        for (int sortZeroPoint = 0; sortZeroPoint < myArray.length; sortZeroPoint++) {
            for (int indexParent = myArray.length - 1; indexParent >= sortZeroPoint; indexParent--) {

                //子heapのindexを生成
                int indexLeftChild = (2 * indexParent) + 1 - sortZeroPoint;
                int indexRightChild = (2 * indexParent) + 2 - sortZeroPoint;

                //子要素が2つの場合
                // →配列要素個数が左の子ヒープの配列番号より大きい、つまり左の子ヒープが配列要素内に存在する。
                // →配列要素数が右の子ヒープが配列番号より大きい、つまり右の子ヒープが配列要素内に存在する。
                if (myArray.length > indexLeftChild && myArray.length > indexRightChild) {

                    //out of indexになるケースがあるのでここでvalueの生成を行う
                    int valueParent = myArray[indexParent];
                    int valueLeftChild = myArray[indexLeftChild];
                    int valueRightChild = myArray[indexRightChild];

                    //親より子の値が小さい場合は最小の子要素を親と入れ替える
                    if (valueParent > valueLeftChild || valueParent > valueRightChild) {
                        if (valueRightChild >= valueLeftChild) {
                            swap(myArray, indexLeftChild, indexParent);
                        }
                        else {
                            swap(myArray, indexRightChild, indexParent);
                        }
                    }
                }
                //子要素が1つの場合
                // →配列要素個数が左の子ヒープの配列番号より大きい、つまり左の子ヒープが配列要素内に存在する。
                // →配列要素個数が右の子ヒープが配列要素数以上、つまり右の子ヒープが配列要素内に存在しない。
                else if (myArray.length > indexLeftChild && myArray.length <= indexRightChild) {
                    //out of indexになるケースがあるのでここでvalueの生成を行う
                    int valueParent = myArray[indexParent];
                    int valueLeftChild = myArray[indexLeftChild];

                    //親より子の値が小さい場合は子要素を親と入れ替える（左の子要素しかないので左と入れ替える。）
                    if (valueParent > valueLeftChild) {
                        swap(myArray, indexLeftChild, indexParent);
                    }
                }
            }
        }
        return myArray;
    }

    /*
    quickSort

    */

    public int[] quickSortMethod(int[] myArray, int initialIndexOfLeft, int initialIndexOfRight) {

        //これでなんでうまく動くのか理解できていない。
        int curleft = initialIndexOfLeft;
        int curright = initialIndexOfRight;
        int pivot = myArray[(curleft + curright) / 2];
        do {
            while(myArray[curleft] < pivot){curleft++;}
            while(myArray[curright] > pivot){curright--;}
            if(curleft <= curright){
                swap(myArray, curleft++, curright--);
            }
        }while(curleft <= curright);
        if(initialIndexOfLeft < curright){
            quickSortMethod(myArray, initialIndexOfLeft, curright);
        }
        if(curleft < initialIndexOfRight){
            quickSortMethod(myArray, curleft, initialIndexOfRight);
        }

        return myArray;

//        //leftChangeTargetFound・rightChangeTargetFound:pivotより大きい要素を見つけたら次の試行からスキップするためのフラグ
//        boolean leftChangeTargetFound = false;// i found the target which is bigger than value of pivot
//        boolean rightChangeTargetFound = false;// i found the target which is smaller than value of pivot
//        int elementLeftNumToChangeWithRight = -1;
//        int elementRightNumToChangeWithLeft = -1;
//        int elementNumLeft = 0;
//        int elementNumRight = myArray.length - 1;

//        //pivotの位置と左右の候補の位置がすべて同じの場合、入れ替え候補探索処理を抜ける
//        while (indexOfPivot >= elementNumLeft || indexOfPivot <= elementNumRight) {
//            int left = myArray[elementNumLeft];
//            int right = myArray[elementNumRight];
//            if (leftChangeTargetFound == false && left >= valueOfPivot) {
//                leftChangeTargetFound = true;
//                elementLeftNumToChangeWithRight = elementNumLeft;
//            }
//            //右候補が見つからなかったらpivotを右候補にする。
//            if (rightChangeTargetFound == false && right < valueOfPivot) {
//                rightChangeTargetFound = true;
//                elementRightNumToChangeWithLeft = elementNumRight;
//            } else if (rightChangeTargetFound == false && elementNumRight == indexOfPivot) {
//                rightChangeTargetFound = true;
//                elementRightNumToChangeWithLeft = elementNumRight;
//            }
//
//            //左右両方の入れ替え候補の要素が決定したら、入れ替える。
//            if (leftChangeTargetFound == true && rightChangeTargetFound == true) {
//                int temp = myArray[elementLeftNumToChangeWithRight];
//                myArray[elementLeftNumToChangeWithRight] = myArray[elementRightNumToChangeWithLeft];
//                myArray[elementRightNumToChangeWithLeft] = temp;
//                leftChangeTargetFound = false;
//                rightChangeTargetFound = false;
//                //pivotは同じ値を利用するので、配列番号を入れ直す。
//                if (indexOfPivot == elementLeftNumToChangeWithRight && indexOfPivot != elementRightNumToChangeWithLeft) {
//                    indexOfPivot = elementRightNumToChangeWithLeft;
//                    valueOfPivot = myArray[indexOfPivot];
//                } else if (indexOfPivot == elementRightNumToChangeWithLeft && indexOfPivot != elementLeftNumToChangeWithRight) {
//                    indexOfPivot = elementLeftNumToChangeWithRight;
//                    valueOfPivot = myArray[indexOfPivot];
//                } else if (indexOfPivot == elementRightNumToChangeWithLeft && indexOfPivot == elementLeftNumToChangeWithRight) {
//                    break;
//                }
//                //入れ替えたら入れ替え要素の探索をやり直す。（最下部のインクリメントの分多く足しておく）
//                elementNumLeft = 0 - 1;
//                elementNumRight = myArray.length - 1 + 1;
//            }
//
//            //インクリメント
//            elementNumLeft++;
//            elementNumRight--;
//
//        }
//
//        int[] leftHalfMyArray = null;
//        int[] rightHalfMyArray = null;
//        //左右の候補が両方ともpivotの場合はpviotで配列を分割して分割した配列でそれぞれ入れ替え処理を開始する。
//        //pivotの配列位置で配列を2分割し、それぞれの配列で探索を行う。
//        if (myArray.length > 2) {
//            leftHalfMyArray = Arrays.copyOfRange(myArray, 0, indexOfPivot + 1);
//            if (myArray.length > indexOfPivot + 1) {
//                rightHalfMyArray = Arrays.copyOfRange(myArray, indexOfPivot + 1, myArray.length);
//            }
//        } else if (myArray.length == 2 && myArray[0] <= myArray[1]) {
//            leftHalfMyArray = new int[1];
//            leftHalfMyArray[0] = myArray[0];
//            rightHalfMyArray = new int[1];
//            rightHalfMyArray[0] = myArray[1];
//        } else if (myArray.length == 2 && myArray[0] > myArray[1]) {
//            leftHalfMyArray = new int[1];
//            leftHalfMyArray[0] = myArray[1];
//            rightHalfMyArray = new int[1];
//            rightHalfMyArray[0] = myArray[0];
//        }
//
//        //分割した配列2つを順に結合した配列を生成し、返り値に渡す。
//        if (leftHalfMyArray.length > 1) {
//            leftHalfMyArray = quickSortMethod(leftHalfMyArray);
//        }
//        if (rightHalfMyArray != null && rightHalfMyArray.length > 1) {
//            rightHalfMyArray = quickSortMethod(rightHalfMyArray);
//        }
//
//        int[] joinedArray = null;
//        if (rightHalfMyArray != null) {
//            joinedArray = new int[leftHalfMyArray.length + rightHalfMyArray.length];
//            System.arraycopy(leftHalfMyArray, 0, joinedArray, 0, leftHalfMyArray.length);
//            System.arraycopy(rightHalfMyArray, 0, joinedArray, leftHalfMyArray.length, rightHalfMyArray.length);
//        } else if (rightHalfMyArray == null) {
//            joinedArray = new int[leftHalfMyArray.length];
//            System.arraycopy(leftHalfMyArray, 0, joinedArray, 0, leftHalfMyArray.length);
//        }
//        return joinedArray;
    }



    public int[] bucketSort(int[] myArray, int elementRange) {
        //前提条件に「要素の範囲<=要素の個数」が約束されている場合に高速でソートできる。
        Random rnd = new Random();
        int myHash = rnd.nextInt(elementRange);

//        int myHash = elementRange;
//        int myHash = 3;

//        printArray(myArray);
//        System.out.println("random int is " + myHash);
//        System.out.println("element range is " + elementRange);


        int bucketArrayNum = (elementRange / myHash) + 1;
        int[][] bucketArray = new int[bucketArrayNum][elementRange];
        //初期化 値未設定は「-1」とする
        for (int[] x : bucketArray) {
            Arrays.fill(x, -1);
        }
        for (int myArrayNum = 0; myArrayNum < myArray.length; myArrayNum++) {
            int hashValue = myArray[myArrayNum] / myHash;
            //該当のバケツにすでに値が入っていたら次の要素に追加する。
            for (int bucketElementNum = 0; bucketElementNum < bucketArray[hashValue].length; bucketElementNum++) {
                if (bucketArray[hashValue][bucketElementNum] == -1) {
                    bucketArray[hashValue][bucketElementNum] = myArray[myArrayNum];
                    break;
                }
            }
        }

//        printMultiArray(bucketArray);

        int[] resultArray = new int[myArray.length];
        int resultSortNum = 0;
        for (int bucketNum = 0; bucketNum < bucketArray.length; bucketNum++) {
            //そのバケツに値が入ってたら、バケツ内をinsertionSortする
            int[] arrayForSort = new int[elementRange];
            for (int bucketElementNum = 0; bucketElementNum < bucketArray[bucketNum].length; bucketElementNum++) {
                arrayForSort[bucketElementNum] = bucketArray[bucketNum][bucketElementNum];
            }
            int[] arrayBucketSorted = insertionSort(arrayForSort);
            for (int m = 0; m < arrayBucketSorted.length; m++) {
                if (arrayBucketSorted[m] == -1) {

                } else {
                    resultArray[resultSortNum] = arrayBucketSorted[m];
                    resultSortNum++;
                }
            }
        }
//        printArray(resultArray);

        return resultArray;
    }

    private static void swap(int[] array, int indexA, int indexB) {
        int indexTmp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = indexTmp;
    }

    private static void printMultiArray(int[][] myMultiArray) {
        for (int n = 0; n < myMultiArray.length; n++) {
            System.out.print("[");
            for (int m = 0; m < myMultiArray[n].length; m++) {
                System.out.print(myMultiArray[n][m] + ",");
            }
            System.out.println("]");
        }
        System.out.println("--------------------------");

    }

    private static void printArray(int[] myArray) {
        String separator = System.getProperty("line.separator");
        System.out.print("[");
        for (int n = 0; n < myArray.length - 1; n++) {
            System.out.print(myArray[n] + ",");
        }
        System.out.print(myArray[myArray.length - 1] + "]" +
                separator + "and myArray.length is " + myArray.length);
        System.out.println("");
    }

    public static String checkArray(int[] myArray) {
        String result = "Success!";
        if (myArray.length > 1) {
            //配列0番目は、-1番目は存在しないためチェックしない。
            for (int n = 1; n < myArray.length; n++) {
                if (myArray[n] < myArray[n - 1]) {
                    return "NG. arrayNum is " + n + ".";
                }
            }
        } else {
            result = "No checked.";
        }
        return result;
    }
}
