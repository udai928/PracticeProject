package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        //String input = "10 30 20";
        //String[] input_split = input.split(" ",0);
        //double[] input_double = Stream.of(input_split).mapToDouble(Integer::parseInt).toArray();

        Scanner sc = new Scanner(System.in);
        try {
            String line = sc.next();
            double[] input_double = new double[3];
            input_double[0] = sc.nextInt();
            input_double[1] = sc.nextInt();
            input_double[2] = sc.nextInt();

            canYouShareCandysForTwoBoys(input_double);
        }
        catch (InputMismatchException e){
            String line = sc.next();
            System.out.print(line);
            howManyPutTheStone(line);

        }


    }
    private static void howManyPutTheStone(String line){

        String[] prepared_stone_board = line.split("");
        ArrayList<String> stone_board = new ArrayList(Arrays.asList(prepared_stone_board));
        int jiro_put_count = 0;

        //原点を決めて、原点次の石の色を見る。

        int a = 0;
        while(a < 100) {

            String zero_point_stone_color = stone_board.get(0);
            System.out.println("¥n list is " + stone_board + ".¥n");
            String stone_change_or_not_change = "not_change";

            for (int i = 1; i < stone_board.size(); i++) {
                //配列番号0は基準位置のためスキップし、配列番号1からの繰り返し処理とする。
                String this_stone_color = stone_board.get(i);

                if (this_stone_color.equals(zero_point_stone_color)) {
                    //原点と同じ色なら、さらにその次の石の色を見る。繰り返す。
                    System.out.print("stone is same color.");
                    stone_change_or_not_change = "not_change";
                } else {

                    System.out.println("this_stone_color " + this_stone_color + ".");
                    System.out.println("zero_point_stone_color " + zero_point_stone_color + ".");

                    // 原点と違う色の石があったら、その時点の一番左の地点（原点の手前）に
                    // 原点と違う色の石を置き、原点〜違う色までの石をすべて原点と違う色に変える。
                    String color_to_change = "";//初期化
                    if (zero_point_stone_color.equals("W")) {
                        color_to_change = "B";
                    } else if (zero_point_stone_color.equals("B")) {
                        color_to_change = "W";
                    } else {
                        System.out.print("Error : detected unexpected color.");
                    }
                    stone_board.add(0,color_to_change);
                    for (int change_section = 1; change_section < i+1; change_section++) {
                        stone_board.set(change_section, color_to_change);
                    }

                    System.out.println("¥n VVVVV list is " + stone_board + ".¥n");

                    //変えたら試行回数を1加算し、一番左に置いた石を原点として最初に戻る。
                    jiro_put_count++;
                    stone_change_or_not_change = "change";
                    System.out.println("stone color is changed");
                    break;
                }
                //原点と違う色の石が最後まで無い場合、石はすべて同じ色→処理終了。
            }

            System.out.println("checking stone color...");
            if (stone_change_or_not_change.equals("not_change")) {
                System.out.println("stone color is all same.");
                break;
            } else {
                a++;
                System.out.println("stone color is not same at all.");
            }
        }

        for(String now_stone_is : stone_board){
            System.out.println("now_stone_is" + now_stone_is + ".");
        }
        System.out.println("jiro put count is " + jiro_put_count + ".");

    }

    private static void canYouShareCandysForTwoBoys(double[] input_double){
        double half_of_all_candy = (input_double[0] + input_double[1] + input_double[2]) / 2;
        Boolean result_flag = false;

        for (Double input_element : input_double){
            if(input_element == half_of_all_candy)
            {
                result_flag = true;
                break;
            }
            else
            {
                //なにもしない
            }
        }

        if(result_flag == true) {
            System.out.println("Yes");
        }
        else{
            System.out.println("No");
        }
    }
}
