import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String wybor;
        do {
            System.out.println();
            System.out.println("Please input operation (encode/decode/exit):");
            wybor = scanner.nextLine();
            if(wybor.equals("encode")){
                String s = encode();
            }else if(wybor.equals("decode")) {
                decode();
            }else if(wybor.equals("exit")) {
                System.out.println("Bye!");
            }else {
                System.out.printf("There is no '%s' operation\n", wybor);
            }
        }while(!wybor.equals("exit"));
    }

    public static void decode(){
        int n, m;
        boolean flag = false;
        String wpis;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Input encoded string: (q=exit)" );
            n = 0; m = 0;
            do {
                flag = false;
                int count = 0;
                wpis = scanner.nextLine();
                if(wpis.charAt(0) == 'q') break;
                for (int i = 0; i < wpis.length(); i++) {
                    if (wpis.charAt(i) != '0' && wpis.charAt(i) != ' ') {
                        flag = true;
                        System.out.println("The encoded message includes characters other than 0 or spaces");
                        break;
                    }
                }
                if(flag) continue;
                if(wpis.length() < 3){
                    System.out.println("The number of blocks is odd");
                    flag = true;
                }
                if(flag) continue;
                if (wpis.charAt(0) != '0' || (wpis.charAt(0) == '0' && wpis.charAt(1) == 0 && wpis.charAt(2) == '0')) {
                    flag = true;
                    System.out.println("The first block of each sequence is not 0 or 00");
                }
                if(flag) continue;
                for (int i = 0; i < wpis.length(); i++) {
                    if (i < wpis.length() - 1 && wpis.charAt(i) == '0' && wpis.charAt(i + 1) == ' ') {
                        count++;
                    }
                }
                if(wpis.charAt(wpis.length() - 1) == '0') count++;
                if (count % 2 == 1) {
                    flag = true;
                    System.out.println("The number of blocks is odd");
                }

            } while (flag);
            if(wpis.charAt(0) == 'q') break;

            int[][] tab = new int[7][wpis.length() / 3];
            for (int i = 0; i < wpis.length(); i++) {
                if (i < wpis.length() - 1 && wpis.charAt(i) == '0' && wpis.charAt(i + 1) == ' ') {
                    i += 2;
                    while (i < wpis.length() && wpis.charAt(i) == '0') {
                        if (n == 7) {
                            n = 0;
                            m++;
                        }
                        tab[n][m] = 1;
                        n++;
                        i++;
                    }
                } else {
                    i += 3;
                    if (i > wpis.length() - 1) break;
                    while (i < wpis.length() && wpis.charAt(i) == '0') {
                        if (n == 7) {
                            n = 0;
                            m++;
                        }
                        tab[n][m] = 0;
                        n++;
                        i++;
                    }
                }
            }
            if(n != 7) {
                System.out.println("The length of the decoded binary string is not a multiple of 7");
                continue;
            }
            System.out.println("Decoded string:");
            char[] znaki = new char[m + 1];
            for(int i = 0 ; i < m + 1 ; i++){
                znaki[i] = (char)(tab[0][i] * 64 +  tab[1][i] * 32 +tab[2][i] * 16 +tab[3][i] * 8 +tab[4][i] * 4 +tab[5][i] * 2 + tab[6][i]);
                System.out.printf("%c",znaki[i]);
            }
            System.out.println();
        }while(n != 7);

    }

    public static String encode(){
        String s = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String wpis = scanner.nextLine();
        System.out.println("Encoded string:");
        char[] znakBinarnie = new char[7 * wpis.length()];
        for(int i = 0 ; i < wpis.length() ; i++){
            int k = 0;
            if(wpis.charAt(i) < 64) {
                znakBinarnie[0 + i * 7] = '0';
                k++;
            }
            if(wpis.charAt(i) < 32) {
                znakBinarnie[1 + i * 7] = '0';
                k++;
            }
            if(wpis.charAt(i) < 16) {
                znakBinarnie[2 + i * 7] = '0';
                k++;
            }
            for(int j = 0 ; j < 7 - k ; j++){
                znakBinarnie[j + k + i * 7] = Integer.toBinaryString(wpis.charAt(i)).charAt(j);
            }
            //  we have got now an array znakBinarnie containing string in binary string

        }

        int m = 0;
        while(m < 7 * wpis.length()){
            if (znakBinarnie[m] == '1') {
                s+= "0 ";
                while (m < 7 * wpis.length() && znakBinarnie[m] == '1') {
                    s+= "0";
                    m++;
                }
                s+= " ";
            } else {
                s+= "00 ";
                while (m < 7 * wpis.length() && znakBinarnie[m] == '0') {
                    s+= "0";
                    m++;
                }
                s+= " ";
            }
        }
        System.out.println(s);
        return s;
    }
}