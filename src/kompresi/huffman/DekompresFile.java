/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kompresi.huffman;

/**
 *
 * @author putri
 */


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class DekompresFile {

    static HuffmanTree ht = new HuffmanTree();
    static int buffer;
    static int N;
    static ObjectInput input;

    public static void begindekompress(String inputPath, String outputPath) throws IOException {
        System.out.println(inputPath);
        System.out.println(outputPath);
        
        input = new ObjectInputStream(new FileInputStream(inputPath));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
        int[][] in = new int[256][2];
        int j = 0;
        for (int i = 0; i < in.length; i++) {
            in[i][1] = input.readInt();
            if (in[i][1] != 0) {
                in[i][0] = j;
            }
            j++;
        }
        HuffmanTree.sorting(in);
        for (int i = 0; i < 256; i++) {
            if (in[i][1] != 0) {
                ht.buatNode(in[i][0], in[i][1]);
            }
        }
        ht.mergeNode();
        isibuffer();
        while (buffer != -1) {
            HuffmanNode root = ht.root;
            while (root.leftchild != null && root.rightchild != null) {
                boolean bit = bacaBit();
                if (bit) {
                    root = root.rightchild;
                } else {
                    root = root.leftchild;
                }
            }
            bw.append((char) root.data);
        }
        input.close();
        bw.close();
    }

    static void isibuffer() throws IOException {
        buffer = input.read();
        N = 8;
    }

    static boolean bacaBit() throws IOException {
        boolean bits = false;
        if (buffer != -1) {
            N--;
            bits = ((buffer >> N) & 1) == 1;
            if (N == 0) {
                isibuffer();
            }
        }
        return bits;
    }
}

