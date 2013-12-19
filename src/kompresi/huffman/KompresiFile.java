/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kompresi.huffman;

/**
 *
 * @author putri
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;


//berikut adalah class untuk kompresi file
public class KompresiFile {

    
    static HuffmanTree ht = new HuffmanTree();
    static int[] nodeDaun;
    static String[] encode;
    static int index = 0;
    static int buffer = 0;
    static int N = 8;

    //method kompress
    public static void beginKompress(String inputPath, String outputPath) throws FileNotFoundException, IOException {
        System.out.println(inputPath);
        System.out.println(outputPath);
        FileInputStream in = new FileInputStream(new File(inputPath));
        ObjectOutput ob = new ObjectOutputStream(new FileOutputStream(outputPath));
        int data;
        int[][] array = new int[256][2];
        while ((data = in.read()) >= 0) {
            array[data][0] = data;
            array[data][1]++;
        }
        for (int i = 0; i < array.length; i++) {
            ob.writeInt(array[i][1]);
        }

        HuffmanTree.sorting(array);
        for (int i = 0; i < 256; i++) {
            if (array[i][1] != 0) {
                ht.buatNode(array[i][0], array[i][1]);
            }
        }
//        ht.display();
        in.close();
        FileInputStream output = new FileInputStream(new File(inputPath));
        ht.mergeNode();
        nodeDaun = new int[ht.jumlah];
        encode = new String[ht.jumlah];
        buatCode(ht.root, "");
        while ((data = output.read()) >= 0) {
            for (int i = 0; i < ht.jumlah; i++) {
                if (nodeDaun[i] == data) {
                    String value = encode[i];
                    for(int j = 0; j < value.length(); j++){
                        buffer <<= 1;
                        buffer |= Integer.parseInt(value.substring(j, j+1));
                        N--;
                        if(N == 0){
                            ob.write(buffer);
                            buffer = 0;
                            N = 8;
                        }
                    }
                    break;
                }
            }
        }
        if(N < 8){
            buffer <<= N;
            ob.write(buffer);
            buffer = 0;
            N = 8;
        }
        ob.close();
        output.close();
    }

    //method untuk menambahkan angka 1 dan 0 pada tree
    static void buatCode(HuffmanNode root, String temp) {
        String tmpCode = temp;
        if (root.leftchild == null && root.rightchild == null) {
            nodeDaun[index] = root.data;
            encode[index] = tmpCode;
            index++;
        } else {
            buatCode(root.leftchild, tmpCode + "0");
            buatCode(root.rightchild, tmpCode + "1");
        }
    }
}
