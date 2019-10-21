/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Ola Glal //K = 133457799BBCDFF1 //M = 0123456789ABCDEF //C =
 * 85E813540F0AB405
 */
public class GUIController implements Initializable {

    @FXML
    private TextArea lblResult;
    @FXML
    private TextArea tMsg;
    @FXML
    private RadioButton rEn;
    @FXML
    private RadioButton rDe;
    @FXML
    private TextField tKey;

    final ToggleGroup group = new ToggleGroup();
    static int choose;
    static String m, k, FINAL, OUT;
    @FXML
    private TextArea lblHex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rEn.setToggleGroup(group);
        rEn.setSelected(true);

        rDe.setToggleGroup(group);
        lblResult.setText("Result will be shown here");
    }

    @FXML
    private void toExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void toRun(ActionEvent event) {
        lblResult.setText("");
        String msgHex = "", msgString="";
        m = tMsg.getText();
        k = tKey.getText();
        if (rEn.isSelected()) {
            choose = 1;
            msgHex = toHex(m);
            String result = "";
            int msgLength = msgHex.length();
            if (!(msgLength % 16 == 0)) {
                result = msgPadding(msgHex);
            }
            lblHex.setText(result);
            System.out.println(result + " " + result.length());

            int x = 0, y = 16;
            OUT = "";
            for (int i = 0; i < (msgLength / 16); i++) {
                OUT += algorithm(msgHex.substring(x, y));
                x += 16;
                y += 16;
            }
            OUT += algorithm(msgHex.substring(msgLength - 16, msgLength));
            
        } else if (rDe.isSelected()) {
            choose = 2;
            /* input in Hexadecimal*/
            //OUT = algorithm(m);
            /* input is message in hexadecimal */
            int msgLength=m.length();
            int x = 0, y = 16;
            OUT = "";
            for (int i = 0; i < (msgLength / 16); i++) {
                OUT += algorithm(m.substring(x, y));
                x += 16;
                y += 16;
            }
            //OUT += algorithm(m.substring(msgLength - 16, msgLength));
            
        }

        
        if (rEn.isSelected()) {
            lblResult.setText(OUT);
        } else if (rDe.isSelected()) {
            lblResult.setText(hexToString(OUT));
            lblHex.setText(OUT);
        }

    }

    @FXML
    private void toCopy(ActionEvent event) {
        String copy = lblResult.getText();
        StringSelection myString = new StringSelection(copy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(myString, null);
    }

    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes()));
    }

    public static String msgPadding(String x) {
        String result = x;
        int pad = x.length() % 16;
        System.out.println(x.length());
        System.out.println(pad);

        for (int i = 0; i < pad; i++) {
            result += "0";
        }

        return result;
    }

    public static String hexToString(String hex) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static int[] hexToDecimal(String s) {
        String digits = "0123456789ABCDEF";
        int[] m = new int[s.length()];
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            m[i] = d;
        }
        return m;
    }

    private static int toBin(int num) {
        if (num == 0) {
            return 0;
        } else {
            return (num % 2 + 10 * toBin(num / 2));
        }
    }

    private static int toDec(int num) {
        int decimal = 0;
        int n = 0;
        while (true) {
            if (num == 0) {
                break;
            } else {
                int temp = num % 10;
                decimal += temp * Math.pow(2, n);
                num /= 10;
                n++;
            }
        }
        return decimal;
    }

    public static int[] decimalToBinary(int[] k) {
        int[] m = new int[16];
        for (int i = 0; i < k.length; i++) {
            m[i] = toBin(k[i]);
        }
        return m;
    }

    public static String[] padding(int[] m) {
        String[] result = new String[16];
        for (int i = 0; i < m.length; i++) {
            result[i] = String.format("%04d", m[i]);
        }
        return result;
    }

    public static String[] key64(String[] m) {
        String[] result = new String[8];
        int j = 0;
        for (int i = 0; i < m.length; i += 2) {
            result[j] = m[i] + m[i + 1];
            j++;
        }
        return result;
    }

    public static String[] PC1(String[] m) {
        String all = "";
        String result[] = new String[8];
        for (int i = 0; i < m.length; i++) {
            all += m[i];
        }

        //indexs -1 because the index of array strat from 0 and PC-1 from 1
        result[0] = new StringBuilder().append(all.charAt(57 - 1)).append(all.charAt(49 - 1)).append(all.charAt(41 - 1)).append(all.charAt(33 - 1)).append(all.charAt(25 - 1)).append(all.charAt(17 - 1)).append(all.charAt(9 - 1)).toString();
        result[1] = new StringBuilder().append(all.charAt(1 - 1)).append(all.charAt(58 - 1)).append(all.charAt(50 - 1)).append(all.charAt(42 - 1)).append(all.charAt(34 - 1)).append(all.charAt(26 - 1)).append(all.charAt(18 - 1)).toString();
        result[2] = new StringBuilder().append(all.charAt(10 - 1)).append(all.charAt(2 - 1)).append(all.charAt(59 - 1)).append(all.charAt(51 - 1)).append(all.charAt(43 - 1)).append(all.charAt(35 - 1)).append(all.charAt(27 - 1)).toString();
        result[3] = new StringBuilder().append(all.charAt(19 - 1)).append(all.charAt(11 - 1)).append(all.charAt(3 - 1)).append(all.charAt(60 - 1)).append(all.charAt(52 - 1)).append(all.charAt(44 - 1)).append(all.charAt(36 - 1)).toString();
        result[4] = new StringBuilder().append(all.charAt(63 - 1)).append(all.charAt(55 - 1)).append(all.charAt(47 - 1)).append(all.charAt(39 - 1)).append(all.charAt(31 - 1)).append(all.charAt(23 - 1)).append(all.charAt(15 - 1)).toString();
        result[5] = new StringBuilder().append(all.charAt(7 - 1)).append(all.charAt(62 - 1)).append(all.charAt(54 - 1)).append(all.charAt(46 - 1)).append(all.charAt(38 - 1)).append(all.charAt(30 - 1)).append(all.charAt(22 - 1)).toString();
        result[6] = new StringBuilder().append(all.charAt(14 - 1)).append(all.charAt(6 - 1)).append(all.charAt(61 - 1)).append(all.charAt(53 - 1)).append(all.charAt(45 - 1)).append(all.charAt(37 - 1)).append(all.charAt(29 - 1)).toString();
        result[7] = new StringBuilder().append(all.charAt(21 - 1)).append(all.charAt(13 - 1)).append(all.charAt(5 - 1)).append(all.charAt(28 - 1)).append(all.charAt(20 - 1)).append(all.charAt(12 - 1)).append(all.charAt(4 - 1)).toString();
        return result;
    }

    public static String shiftKey(String k, int n) {
        char[] tempK = k.toCharArray();
        while (n != 0) {
            char temp = tempK[0];
            for (int i = 0; i < tempK.length - 1; i++) {
                tempK[i] = tempK[i + 1];
            }
            tempK[tempK.length - 1] = temp;
            n--;
        }
        String result = new String(tempK);
        return result;
    }

    public static String[] PC2(String c, String d) {
        String all = c.concat(d);
        String[] result = new String[8];
        result[0] = new StringBuilder().append(all.charAt(14 - 1)).append(all.charAt(17 - 1)).append(all.charAt(11 - 1)).append(all.charAt(24 - 1)).append(all.charAt(1 - 1)).append(all.charAt(5 - 1)).toString();
        result[1] = new StringBuilder().append(all.charAt(3 - 1)).append(all.charAt(28 - 1)).append(all.charAt(15 - 1)).append(all.charAt(6 - 1)).append(all.charAt(21 - 1)).append(all.charAt(10 - 1)).toString();
        result[2] = new StringBuilder().append(all.charAt(23 - 1)).append(all.charAt(19 - 1)).append(all.charAt(12 - 1)).append(all.charAt(4 - 1)).append(all.charAt(26 - 1)).append(all.charAt(8 - 1)).toString();
        result[3] = new StringBuilder().append(all.charAt(16 - 1)).append(all.charAt(7 - 1)).append(all.charAt(27 - 1)).append(all.charAt(20 - 1)).append(all.charAt(13 - 1)).append(all.charAt(2 - 1)).toString();
        result[4] = new StringBuilder().append(all.charAt(41 - 1)).append(all.charAt(52 - 1)).append(all.charAt(31 - 1)).append(all.charAt(37 - 1)).append(all.charAt(47 - 1)).append(all.charAt(55 - 1)).toString();
        result[5] = new StringBuilder().append(all.charAt(30 - 1)).append(all.charAt(40 - 1)).append(all.charAt(51 - 1)).append(all.charAt(45 - 1)).append(all.charAt(33 - 1)).append(all.charAt(48 - 1)).toString();
        result[6] = new StringBuilder().append(all.charAt(44 - 1)).append(all.charAt(49 - 1)).append(all.charAt(39 - 1)).append(all.charAt(56 - 1)).append(all.charAt(34 - 1)).append(all.charAt(53 - 1)).toString();
        result[7] = new StringBuilder().append(all.charAt(46 - 1)).append(all.charAt(42 - 1)).append(all.charAt(50 - 1)).append(all.charAt(36 - 1)).append(all.charAt(29 - 1)).append(all.charAt(32 - 1)).toString();
        return result;
    }

    public static String[] initailP(String[] x) {
        String[] result = new String[16];
        String all = "";
        for (int i = 0; i < x.length; i++) {
            all += x[i];
        }
        result[0] = new StringBuilder().append(all.charAt(58 - 1)).append(all.charAt(50 - 1)).append(all.charAt(42 - 1)).append(all.charAt(34 - 1)).toString();
        result[1] = new StringBuilder().append(all.charAt(26 - 1)).append(all.charAt(18 - 1)).append(all.charAt(10 - 1)).append(all.charAt(2 - 1)).toString();
        result[2] = new StringBuilder().append(all.charAt(60 - 1)).append(all.charAt(52 - 1)).append(all.charAt(44 - 1)).append(all.charAt(36 - 1)).toString();
        result[3] = new StringBuilder().append(all.charAt(28 - 1)).append(all.charAt(20 - 1)).append(all.charAt(12 - 1)).append(all.charAt(4 - 1)).toString();
        result[4] = new StringBuilder().append(all.charAt(62 - 1)).append(all.charAt(54 - 1)).append(all.charAt(46 - 1)).append(all.charAt(38 - 1)).toString();
        result[5] = new StringBuilder().append(all.charAt(30 - 1)).append(all.charAt(22 - 1)).append(all.charAt(14 - 1)).append(all.charAt(6 - 1)).toString();
        result[6] = new StringBuilder().append(all.charAt(64 - 1)).append(all.charAt(56 - 1)).append(all.charAt(48 - 1)).append(all.charAt(40 - 1)).toString();
        result[7] = new StringBuilder().append(all.charAt(32 - 1)).append(all.charAt(24 - 1)).append(all.charAt(16 - 1)).append(all.charAt(8 - 1)).toString();
        result[8] = new StringBuilder().append(all.charAt(57 - 1)).append(all.charAt(49 - 1)).append(all.charAt(41 - 1)).append(all.charAt(33 - 1)).toString();
        result[9] = new StringBuilder().append(all.charAt(25 - 1)).append(all.charAt(17 - 1)).append(all.charAt(9 - 1)).append(all.charAt(1 - 1)).toString();
        result[10] = new StringBuilder().append(all.charAt(59 - 1)).append(all.charAt(51 - 1)).append(all.charAt(43 - 1)).append(all.charAt(35 - 1)).toString();
        result[11] = new StringBuilder().append(all.charAt(27 - 1)).append(all.charAt(19 - 1)).append(all.charAt(11 - 1)).append(all.charAt(3 - 1)).toString();
        result[12] = new StringBuilder().append(all.charAt(61 - 1)).append(all.charAt(53 - 1)).append(all.charAt(45 - 1)).append(all.charAt(37 - 1)).toString();
        result[13] = new StringBuilder().append(all.charAt(29 - 1)).append(all.charAt(21 - 1)).append(all.charAt(13 - 1)).append(all.charAt(5 - 1)).toString();
        result[14] = new StringBuilder().append(all.charAt(63 - 1)).append(all.charAt(55 - 1)).append(all.charAt(47 - 1)).append(all.charAt(39 - 1)).toString();
        result[15] = new StringBuilder().append(all.charAt(31 - 1)).append(all.charAt(23 - 1)).append(all.charAt(15 - 1)).append(all.charAt(7 - 1)).toString();
        return result;
    }

    public static String[] XOR(String[] x, String[] y) {
        String[] result = {"", "", "", "", "", "", "", ""};
        for (int i = 0; i < x[0].length(); i++) {
            for (int j = 0; j < result.length; j++) {
                if (x[j].charAt(i) == y[j].charAt(i)) {
                    result[j] += '0';
                } else {
                    result[j] += '1';
                }
            }
        }
        return result;
    }

    public static String[] expand(String[] x) {
        String[] result = {"", "", "", "", "", "", "", ""};
        result[0] = (x[x.length - 1].charAt(3)) + x[0] + (x[1].charAt(0));
        for (int i = 1; i < x.length - 1; i++) {
            result[i] = (x[i - 1].charAt(3)) + x[i] + (x[i + 1].charAt(0));
        }
        result[x.length - 1] = (x[x.length - 2].charAt(3)) + x[x.length - 1] + (x[0].charAt(0));
        return result;
    }

    static int[][] sBox = null;

    public static String sFunction(String x, int y) {
        switch (y) {
            case 1:
                sBox = new int[][]{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};
                break;
            case 2:
                sBox = new int[][]{{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};
                break;
            case 3:
                sBox = new int[][]{{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};
                break;
            case 4:
                sBox = new int[][]{{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};
                break;
            case 5:
                sBox = new int[][]{{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};
                break;
            case 6:
                sBox = new int[][]{{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};
                break;
            case 7:
                sBox = new int[][]{{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};
                break;
            case 8:
                sBox = new int[][]{{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
                break;
        }

        String r = new StringBuilder().append(x.charAt(0)).append(x.charAt(5)).toString();
        int xAxis = toDec(Integer.parseInt(r));
        String c = new StringBuilder().append(x.charAt(1)).append(x.charAt(2)).append(x.charAt(3)).append(x.charAt(4)).toString();
        int yAxis = toDec(Integer.parseInt(c));
        int sResult = sBox[xAxis][yAxis];
        int z = toBin(sResult);
        String result = String.format("%04d", z);
        return result;
    }

    public static String[] sbox(String[] x) {
        String[] result = {"", "", "", "", "", "", "", ""};
        result[0] = sFunction(x[0], 1);
        result[1] = sFunction(x[1], 2);
        result[2] = sFunction(x[2], 3);
        result[3] = sFunction(x[3], 4);
        result[4] = sFunction(x[4], 5);
        result[5] = sFunction(x[5], 6);
        result[6] = sFunction(x[6], 7);
        result[7] = sFunction(x[7], 8);
        return result;
    }

    public static String[] permutation(String[] x) {
        String[] result = new String[8];
        String all = "";
        for (int i = 0; i < x.length; i++) {
            all += x[i];
        }
        result[0] = new StringBuilder().append(all.charAt(16 - 1)).append(all.charAt(7 - 1)).append(all.charAt(20 - 1)).append(all.charAt(21 - 1)).toString();
        result[1] = new StringBuilder().append(all.charAt(29 - 1)).append(all.charAt(12 - 1)).append(all.charAt(28 - 1)).append(all.charAt(17 - 1)).toString();
        result[2] = new StringBuilder().append(all.charAt(1 - 1)).append(all.charAt(15 - 1)).append(all.charAt(23 - 1)).append(all.charAt(26 - 1)).toString();
        result[3] = new StringBuilder().append(all.charAt(5 - 1)).append(all.charAt(18 - 1)).append(all.charAt(31 - 1)).append(all.charAt(10 - 1)).toString();
        result[4] = new StringBuilder().append(all.charAt(2 - 1)).append(all.charAt(8 - 1)).append(all.charAt(24 - 1)).append(all.charAt(14 - 1)).toString();
        result[5] = new StringBuilder().append(all.charAt(32 - 1)).append(all.charAt(27 - 1)).append(all.charAt(3 - 1)).append(all.charAt(9 - 1)).toString();
        result[6] = new StringBuilder().append(all.charAt(19 - 1)).append(all.charAt(13 - 1)).append(all.charAt(30 - 1)).append(all.charAt(6 - 1)).toString();
        result[7] = new StringBuilder().append(all.charAt(22 - 1)).append(all.charAt(11 - 1)).append(all.charAt(4 - 1)).append(all.charAt(25 - 1)).toString();
        return result;
    }

    public static String[] f(String[] x, String[] k) {
        String[] result = {"", "", "", "", "", "", "", ""};
        //from 32bit to 48bit
        String[] ER = expand(x);
        //XOR
        String[] KplusER = XOR(ER, k);
        //from 48bit to 32bit
        String[] S = sbox(KplusER);
        //permution
        result = permutation(S);
        return result;
    }

    public static String[] reverseP(String[] x) {
        String[] result = new String[16];
        String all = "";
        for (int i = 0; i < x.length; i++) {
            all += x[i];
        }
        result[0] = new StringBuilder().append(all.charAt(40 - 1)).append(all.charAt(8 - 1)).append(all.charAt(48 - 1)).append(all.charAt(16 - 1)).toString();
        result[1] = new StringBuilder().append(all.charAt(56 - 1)).append(all.charAt(24 - 1)).append(all.charAt(64 - 1)).append(all.charAt(32 - 1)).toString();
        result[2] = new StringBuilder().append(all.charAt(39 - 1)).append(all.charAt(7 - 1)).append(all.charAt(47 - 1)).append(all.charAt(15 - 1)).toString();
        result[3] = new StringBuilder().append(all.charAt(55 - 1)).append(all.charAt(23 - 1)).append(all.charAt(63 - 1)).append(all.charAt(31 - 1)).toString();
        result[4] = new StringBuilder().append(all.charAt(38 - 1)).append(all.charAt(6 - 1)).append(all.charAt(46 - 1)).append(all.charAt(14 - 1)).toString();
        result[5] = new StringBuilder().append(all.charAt(54 - 1)).append(all.charAt(22 - 1)).append(all.charAt(62 - 1)).append(all.charAt(30 - 1)).toString();
        result[6] = new StringBuilder().append(all.charAt(37 - 1)).append(all.charAt(5 - 1)).append(all.charAt(45 - 1)).append(all.charAt(13 - 1)).toString();
        result[7] = new StringBuilder().append(all.charAt(53 - 1)).append(all.charAt(21 - 1)).append(all.charAt(61 - 1)).append(all.charAt(29 - 1)).toString();
        result[8] = new StringBuilder().append(all.charAt(36 - 1)).append(all.charAt(4 - 1)).append(all.charAt(44 - 1)).append(all.charAt(12 - 1)).toString();
        result[9] = new StringBuilder().append(all.charAt(52 - 1)).append(all.charAt(20 - 1)).append(all.charAt(60 - 1)).append(all.charAt(28 - 1)).toString();
        result[10] = new StringBuilder().append(all.charAt(35 - 1)).append(all.charAt(3 - 1)).append(all.charAt(43 - 1)).append(all.charAt(11 - 1)).toString();
        result[11] = new StringBuilder().append(all.charAt(51 - 1)).append(all.charAt(19 - 1)).append(all.charAt(59 - 1)).append(all.charAt(27 - 1)).toString();
        result[12] = new StringBuilder().append(all.charAt(34 - 1)).append(all.charAt(2 - 1)).append(all.charAt(42 - 1)).append(all.charAt(10 - 1)).toString();
        result[13] = new StringBuilder().append(all.charAt(50 - 1)).append(all.charAt(18 - 1)).append(all.charAt(58 - 1)).append(all.charAt(26 - 1)).toString();
        result[14] = new StringBuilder().append(all.charAt(33 - 1)).append(all.charAt(1 - 1)).append(all.charAt(41 - 1)).append(all.charAt(9 - 1)).toString();
        result[15] = new StringBuilder().append(all.charAt(49 - 1)).append(all.charAt(17 - 1)).append(all.charAt(57 - 1)).append(all.charAt(25 - 1)).toString();
        return result;
    }

    public static String decToHex(String[] x) {
        int decimal;
        String result = "";
        for (int i = 0; i < x.length; i++) {
            decimal = Integer.parseInt(x[i], 2);
            result += Integer.toString(decimal, 16);
        }
        return result;
    }

    public static String algorithm(String msgX) {
        //convert Hex input m to binary
        int[] decnum, binnum;
        decnum = hexToDecimal(msgX);
        binnum = decimalToBinary(decnum);

        //padding to 16block/4bits String
        String[] M = new String[16];
        M = padding(binnum);

        //convert Hex input k to binary
        int[] decnumK, binnumK;
        decnumK = hexToDecimal(k);
        binnumK = decimalToBinary(decnumK);

        //padding to 8block/8bits String
        String[] K = new String[16];
        K = padding(binnumK);
        K = key64(K);

        //permutation
        String[] Kplus = new String[8];
        Kplus = PC1(K);

        //Create C0 and D0        
        String[] C0 = new String[4];
        for (int i = 0; i < C0.length; i++) {
            C0[i] = Kplus[i];
        }

        String[] D0 = new String[4];
        for (int i = 0; i < D0.length; i++) {
            D0[i] = Kplus[i + 4];
        }

        //Create 16subkey
        String tempC = "";
        for (int i = 0; i < C0.length; i++) {
            tempC += C0[i];
        }

        String tempD = "";
        for (int i = 0; i < D0.length; i++) {
            tempD += D0[i];
        }

        String C1 = shiftKey(tempC, 1);
        String D1 = shiftKey(tempD, 1);
        String C2 = shiftKey(C1, 1);
        String D2 = shiftKey(D1, 1);
        String C3 = shiftKey(C2, 2);
        String D3 = shiftKey(D2, 2);
        String C4 = shiftKey(C3, 2);
        String D4 = shiftKey(D3, 2);
        String C5 = shiftKey(C4, 2);
        String D5 = shiftKey(D4, 2);
        String C6 = shiftKey(C5, 2);
        String D6 = shiftKey(D5, 2);
        String C7 = shiftKey(C6, 2);
        String D7 = shiftKey(D6, 2);
        String C8 = shiftKey(C7, 2);
        String D8 = shiftKey(D7, 2);
        String C9 = shiftKey(C8, 1);
        String D9 = shiftKey(D8, 1);
        String C10 = shiftKey(C9, 2);
        String D10 = shiftKey(D9, 2);
        String C11 = shiftKey(C10, 2);
        String D11 = shiftKey(D10, 2);
        String C12 = shiftKey(C11, 2);
        String D12 = shiftKey(D11, 2);
        String C13 = shiftKey(C12, 2);
        String D13 = shiftKey(D12, 2);
        String C14 = shiftKey(C13, 2);
        String D14 = shiftKey(D13, 2);
        String C15 = shiftKey(C14, 2);
        String D15 = shiftKey(D14, 2);
        String C16 = shiftKey(C15, 1);
        String D16 = shiftKey(D15, 1);

        //Apply PC-2
        String[] K0 = PC2(tempC, tempD);
        String[] K1 = PC2(C1, D1);
        String[] K2 = PC2(C2, D2);
        String[] K3 = PC2(C3, D3);
        String[] K4 = PC2(C4, D4);
        String[] K5 = PC2(C5, D5);
        String[] K6 = PC2(C6, D6);
        String[] K7 = PC2(C7, D7);
        String[] K8 = PC2(C8, D8);
        String[] K9 = PC2(C9, D9);
        String[] K10 = PC2(C10, D10);
        String[] K11 = PC2(C11, D11);
        String[] K12 = PC2(C12, D12);
        String[] K13 = PC2(C13, D13);
        String[] K14 = PC2(C14, D14);
        String[] K15 = PC2(C15, D15);
        String[] K16 = PC2(C16, D16);

        //Initial Perumtation
        String[] IP = new String[16];
        IP = initailP(M);

        //L0 and R0
        String[] L0 = new String[8];
        for (int i = 0; i < L0.length; i++) {
            L0[i] = IP[i];
        }

        String[] R0 = new String[8];
        for (int i = 0; i < R0.length; i++) {
            R0[i] = IP[i + 8];
        }

        if (choose == 1) {
            //Round 1
            String[] X1 = new String[8];
            String[] L1 = new String[8];
            String[] R1 = new String[8];
            X1 = f(R0, K1);
            L1 = R0;
            R1 = XOR(L0, X1);

            //Round 2
            String[] X2 = new String[8];
            String[] L2 = new String[8];
            String[] R2 = new String[8];
            X2 = f(R1, K2);
            L2 = R1;
            R2 = XOR(L1, X2);

            //Round 3
            String[] X3 = new String[8];
            String[] L3 = new String[8];
            String[] R3 = new String[8];
            X3 = f(R2, K3);
            L3 = R2;
            R3 = XOR(L2, X3);

            //Round 4
            String[] X4 = new String[8];
            String[] L4 = new String[8];
            String[] R4 = new String[8];
            X4 = f(R3, K4);
            L4 = R3;
            R4 = XOR(L3, X4);

            //Round 5
            String[] X5 = new String[8];
            String[] L5 = new String[8];
            String[] R5 = new String[8];
            X5 = f(R4, K5);
            L5 = R4;
            R5 = XOR(L4, X5);

            //Round 6
            String[] X6 = new String[8];
            String[] L6 = new String[8];
            String[] R6 = new String[8];
            X6 = f(R5, K6);
            L6 = R5;
            R6 = XOR(L5, X6);

            //Round 7
            String[] X7 = new String[8];
            String[] L7 = new String[8];
            String[] R7 = new String[8];
            X7 = f(R6, K7);
            L7 = R6;
            R7 = XOR(L6, X7);

            //Round 8
            String[] X8 = new String[8];
            String[] L8 = new String[8];
            String[] R8 = new String[8];
            X8 = f(R7, K8);
            L8 = R7;
            R8 = XOR(L7, X8);

            //Round 9
            String[] X9 = new String[8];
            String[] L9 = new String[8];
            String[] R9 = new String[8];
            X9 = f(R8, K9);
            L9 = R8;
            R9 = XOR(L8, X9);

            //Round 10
            String[] X10 = new String[8];
            String[] L10 = new String[8];
            String[] R10 = new String[8];
            X10 = f(R9, K10);
            L10 = R9;
            R10 = XOR(L9, X10);

            //Round 11
            String[] X11 = new String[8];
            String[] L11 = new String[8];
            String[] R11 = new String[8];
            X11 = f(R10, K11);
            L11 = R10;
            R11 = XOR(L10, X11);

            //Round 12
            String[] X12 = new String[8];
            String[] L12 = new String[8];
            String[] R12 = new String[8];
            X12 = f(R11, K12);
            L12 = R11;
            R12 = XOR(L11, X12);

            //Round 13
            String[] X13 = new String[8];
            String[] L13 = new String[8];
            String[] R13 = new String[8];
            X13 = f(R12, K13);
            L13 = R12;
            R13 = XOR(L12, X13);

            //Round 14
            String[] X14 = new String[8];
            String[] L14 = new String[8];
            String[] R14 = new String[8];
            X14 = f(R13, K14);
            L14 = R13;
            R14 = XOR(L13, X14);

            //Round 15
            String[] X15 = new String[8];
            String[] L15 = new String[8];
            String[] R15 = new String[8];
            X15 = f(R14, K15);
            L15 = R14;
            R15 = XOR(L14, X15);

            //Round 16
            String[] X16 = new String[8];
            String[] L16 = new String[8];
            String[] R16 = new String[8];
            X16 = f(R15, K16);
            L16 = R15;
            R16 = XOR(L15, X16);

            String[] R16L16 = new String[8];
            R16L16[0] = R16[0] + R16[1];
            R16L16[1] = R16[2] + R16[3];
            R16L16[2] = R16[4] + R16[5];
            R16L16[3] = R16[6] + R16[7];
            R16L16[4] = L16[0] + L16[1];
            R16L16[5] = L16[2] + L16[3];
            R16L16[6] = L16[4] + L16[5];
            R16L16[7] = L16[6] + L16[7];

            //Final permutation
            String[] IPmins = reverseP(R16L16);

            //To hexadecimal
            FINAL = decToHex(IPmins).toUpperCase();

        } else if (choose == 2) {
            //Round 1
            String[] X1 = new String[8];
            String[] L1 = new String[8];
            String[] R1 = new String[8];
            X1 = f(R0, K16);
            L1 = R0;
            R1 = XOR(L0, X1);

            //Round 2
            String[] X2 = new String[8];
            String[] L2 = new String[8];
            String[] R2 = new String[8];
            X2 = f(R1, K15);
            L2 = R1;
            R2 = XOR(L1, X2);

            //Round 3
            String[] X3 = new String[8];
            String[] L3 = new String[8];
            String[] R3 = new String[8];
            X3 = f(R2, K14);
            L3 = R2;
            R3 = XOR(L2, X3);

            //Round 4
            String[] X4 = new String[8];
            String[] L4 = new String[8];
            String[] R4 = new String[8];
            X4 = f(R3, K13);
            L4 = R3;
            R4 = XOR(L3, X4);

            //Round 5
            String[] X5 = new String[8];
            String[] L5 = new String[8];
            String[] R5 = new String[8];
            X5 = f(R4, K12);
            L5 = R4;
            R5 = XOR(L4, X5);

            //Round 6
            String[] X6 = new String[8];
            String[] L6 = new String[8];
            String[] R6 = new String[8];
            X6 = f(R5, K11);
            L6 = R5;
            R6 = XOR(L5, X6);

            //Round 7
            String[] X7 = new String[8];
            String[] L7 = new String[8];
            String[] R7 = new String[8];
            X7 = f(R6, K10);
            L7 = R6;
            R7 = XOR(L6, X7);

            //Round 8
            String[] X8 = new String[8];
            String[] L8 = new String[8];
            String[] R8 = new String[8];
            X8 = f(R7, K9);
            L8 = R7;
            R8 = XOR(L7, X8);

            //Round 9
            String[] X9 = new String[8];
            String[] L9 = new String[8];
            String[] R9 = new String[8];
            X9 = f(R8, K8);
            L9 = R8;
            R9 = XOR(L8, X9);

            //Round 10
            String[] X10 = new String[8];
            String[] L10 = new String[8];
            String[] R10 = new String[8];
            X10 = f(R9, K7);
            L10 = R9;
            R10 = XOR(L9, X10);

            //Round 11
            String[] X11 = new String[8];
            String[] L11 = new String[8];
            String[] R11 = new String[8];
            X11 = f(R10, K6);
            L11 = R10;
            R11 = XOR(L10, X11);

            //Round 12
            String[] X12 = new String[8];
            String[] L12 = new String[8];
            String[] R12 = new String[8];
            X12 = f(R11, K5);
            L12 = R11;
            R12 = XOR(L11, X12);

            //Round 13
            String[] X13 = new String[8];
            String[] L13 = new String[8];
            String[] R13 = new String[8];
            X13 = f(R12, K4);
            L13 = R12;
            R13 = XOR(L12, X13);

            //Round 14
            String[] X14 = new String[8];
            String[] L14 = new String[8];
            String[] R14 = new String[8];
            X14 = f(R13, K3);
            L14 = R13;
            R14 = XOR(L13, X14);

            //Round 15
            String[] X15 = new String[8];
            String[] L15 = new String[8];
            String[] R15 = new String[8];
            X15 = f(R14, K2);
            L15 = R14;
            R15 = XOR(L14, X15);

            //Round 16
            String[] X16 = new String[8];
            String[] L16 = new String[8];
            String[] R16 = new String[8];
            X16 = f(R15, K1);
            L16 = R15;
            R16 = XOR(L15, X16);

            String[] R16L16 = new String[8];
            R16L16[0] = R16[0] + R16[1];
            R16L16[1] = R16[2] + R16[3];
            R16L16[2] = R16[4] + R16[5];
            R16L16[3] = R16[6] + R16[7];
            R16L16[4] = L16[0] + L16[1];
            R16L16[5] = L16[2] + L16[3];
            R16L16[6] = L16[4] + L16[5];
            R16L16[7] = L16[6] + L16[7];

            //Final permutation
            String[] IPmins = reverseP(R16L16);

            //To hexadecimal
            FINAL = decToHex(IPmins).toUpperCase();

        }
        return FINAL;
    }
}
