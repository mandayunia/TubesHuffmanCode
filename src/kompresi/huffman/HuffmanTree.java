/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kompresi.huffman;

/**
 *
 * @author putri
 */

//class berikut adalah class pembuatan HuffmanTree
public class HuffmanTree {

    //deklarasi variabel dan node
    int jumlah = 0;
    DaftarNode first;
    HuffmanNode root;

    //method untuk membuat node
    void buatNode(int data, int freq) {
        HuffmanNode hf = new HuffmanNode(data, freq);
        DaftarNode dn = new DaftarNode(hf);
        if (first == null) {
            first = dn;
        } else {
            dn.next = first;
            first = dn;
        }
        jumlah++;
    }

    //method untuk menghapus node 
    HuffmanNode removeNode() {
        if (first != null) {
            HuffmanNode hf;
            hf = first.data;
            first = first.next;
            return hf;
        }
        return null;
    }

    //method untuk menambahkan node
    void insertNode(HuffmanNode data) {
        DaftarNode dn = new DaftarNode(data);
        if (first == null) {
            dn.next = first;
            first = dn;
        } else {
            DaftarNode current = first;
            while (current.next != null && current.next.data.freq <= data.freq) {
                current = current.next;
            }
            dn.next = current.next;
            current.next = dn;
        }
    }

    //method untuk menggabungkan node-node menjadi sebuah tree
    void mergeNode() {
        while (first.next != null) {

            HuffmanNode nodeA = removeNode();
            HuffmanNode nodeB = removeNode();

            HuffmanNode hf = new HuffmanNode();
            hf.leftchild = nodeA;
            hf.rightchild = nodeB;
            hf.freq = nodeA.freq + nodeB.freq;
            root = hf;
            insertNode(hf);
        }
    }
    
    //method untuk menampilkan tree yg telah dibuat
    void display() {
        DaftarNode current = first;
        while (current != null) {
            System.out.print(current.data.freq + " ");
            current = current.next;
        }
    }

    //method untuk sorting 
    static void sorting(int[][] array) {
        int indexMin;
        for (int i = 0; i < array.length; i++) {
            indexMin = i;
            for (int j = i; j < array.length; j++) {
                if (array[j][1] > array[indexMin][1]) {
                    indexMin = j;
                }
            }
            int tmp = array[i][1];
            array[i][1] = array[indexMin][1];
            array[indexMin][1] = tmp;
            int temp = array[i][0];
            array[i][0] = array[indexMin][0];
            array[indexMin][0] = temp;
        }
    }
}
