package semestr_2;

// Java Program to Implement Binomial Heap

import java.util.Random;


// Class 1
// BinomialHeapNode
public class BinomialHeapNode {

    int key, degree;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;

    // Constructor of this class
    public BinomialHeapNode(int k)
    {

        key = k;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    // Method 1
    // To reverse
    public BinomialHeapNode reverse(BinomialHeapNode sibl)
    {
        BinomialHeapNode ret;
        if (sibling != null)
            ret = sibling.reverse(this);
        else
            ret = this;
        sibling = sibl;
        return ret;
    }

    // Method 2
    // To find minimum node
    public BinomialHeapNode findMinNode()
    {

        // this keyword refers to current instance itself
        BinomialHeapNode x = this, y = this;
        int min = x.key;

        int count=0;
        while (x != null) {
            count++;
            if (x.key < min) {
                y = x;
                min = x.key;
            }

            x = x.sibling;
        }
        System.out.println("Количество итераций: "+ count);

        return y;
    }

    // Method 3
    // To find node with key value
    public BinomialHeapNode findANodeWithKey(int value)
    {

        BinomialHeapNode temp = this, node = null;
        int count=0;

        while (temp != null) {
            count++;
            if (temp.key == value) {
                node = temp;
                count++;
                break;
            }

            if (temp.child == null) {
                temp = temp.sibling;
                count++;
            }

            else {
                node = temp.child.findANodeWithKey(value);
                if (node == null) {
                    temp = temp.sibling;
                    count++;
                }
                else {
                    count++;
                    break;
                }
            }
        }
        System.out.println("Количество итераций  "+count);

        return node;
    }

    // Method 4
    // To get the size
    public int getSize()
    {
        return (
                1 + ((child == null) ? 0 : child.getSize())
                        + ((sibling == null) ? 0 : sibling.getSize()));
    }

}

// Class 2
// BinomialHeap
class BinomialHeap {

    // Member variables of this class
    private BinomialHeapNode Nodes;
    private int size;

    // Constructor of this class
    public BinomialHeap()
    {
        Nodes = null;
        size = 0;
    }


    // Checking if heap is empty
    public boolean isEmpty() { return Nodes == null; }

    // Method 1
    // To get the size
    public int getSize() { return size; }

    // Method 2
    // Clear heap
    public void makeEmpty()
    {
        Nodes = null;
        size = 0;
    }

    // Method 3
    // To insert
    public void insert(int value)
    {
        int count=0;

        if (value > 0) {
            count++;
            BinomialHeapNode temp
                    = new BinomialHeapNode(value);
            if (Nodes == null) {
                count++;
                Nodes = temp;
                size = 1;
            }
            else {
                count++;
                unionNodes(temp);
                size++;
            }
        }
        System.out.println("Количество итераций: "+ count);
    }

    // Method 4
    // To unite two binomial heaps
    private void merge(BinomialHeapNode binHeap)
    {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;

        while ((temp1 != null) && (temp2 != null)) {

            if (temp1.degree == temp2.degree) {

                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            }

            else {

                if (temp1.degree < temp2.degree) {

                    if ((temp1.sibling == null)
                            || (temp1.sibling.degree
                            > temp2.degree)) {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    }

                    else {
                        temp1 = temp1.sibling;
                    }
                }

                else {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;

                    if (tmp == Nodes) {
                        Nodes = temp1;
                    }

                    else {
                    }
                }
            }
        }

        if (temp1 == null) {
            temp1 = Nodes;

            while (temp1.sibling != null) {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        }


    }

    // Method 5
    // For union of nodes
    private void unionNodes(BinomialHeapNode binHeap)
    {
        merge(binHeap);

        BinomialHeapNode prevTemp = null, temp = Nodes,
                nextTemp = Nodes.sibling;

        while (nextTemp != null) {

            if ((temp.degree != nextTemp.degree)
                    || ((nextTemp.sibling != null)
                    && (nextTemp.sibling.degree
                    == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            }

            else {

                if (temp.key <= nextTemp.key) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                }

                else {

                    if (prevTemp == null) {
                        Nodes = nextTemp;
                    }

                    else {
                        prevTemp.sibling = nextTemp;
                    }

                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }

    // Method 6
    // To return minimum key
    public int findMinimum()
    {
        int count=0;
        count++;
        return Nodes.findMinNode().key;
    }

    // Method 7
    // To delete a particular element */
    public void delete(int value)
    {
        int count=0;

        if ((Nodes != null)
                && (Nodes.findANodeWithKey(value) != null)) {
            count++;
            decreaseKeyValue(value, findMinimum() - 1);
            count++;
            extractMin();
            count++;
        }
        System.out.println("Количество итераций: "+ count);
    }

    // Method 8
    // To decrease key with a given value */
    public void decreaseKeyValue(int old_value,
                                 int new_value)
    {
        int count=0;
        BinomialHeapNode temp
                = Nodes.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.key = new_value;
        BinomialHeapNode tempParent = temp.parent;

        while ((tempParent != null)
                && (temp.key < tempParent.key)) {
            count++;
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;

            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }

    // Method 9
    // To extract the node with the minimum key
    public int extractMin()
    {
        int count=0;
        if (Nodes == null)
            return -1;

        BinomialHeapNode temp = Nodes, prevTemp = null;
        BinomialHeapNode minNode = Nodes.findMinNode();

        while (temp.key != minNode.key) {
            count++;
            prevTemp = temp;
            temp = temp.sibling;
        }

        if (prevTemp == null) {
            Nodes = temp.sibling;
        }
        else {
            prevTemp.sibling = temp.sibling;
        }

        temp = temp.child;
        BinomialHeapNode fakeNode = temp;

        while (temp != null) {
            count++;
            temp.parent = null;
            temp = temp.sibling;
        }

        if ((Nodes == null) && (fakeNode == null)) {
            size = 0;
        }
        else {
            if ((Nodes == null) && (fakeNode != null)) {
                Nodes = fakeNode.reverse(null);
                size = Nodes.getSize();
            }
            else {
                if ((Nodes != null) && (fakeNode == null)) {
                    size = Nodes.getSize();
                }
                else {
                    unionNodes(fakeNode.reverse(null));
                    size = Nodes.getSize();
                }
            }
        }

        return minNode.key;
    }

    // Method 10
    // To display heap
    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(Nodes);
        System.out.println("\n");
    }

    private void displayHeap(BinomialHeapNode r)
    {
        if (r != null) {
            displayHeap(r.child);
            System.out.print(r.key + " ");
            displayHeap(r.sibling);
        }
    }

    public static void main(String[] args)  {
        int[] randomInt = new int[10001];
        for (int i = 0; i < randomInt.length; i++) {
            randomInt[i] = (int) (Math.random()*1000);
        }

        // Make object of BinomialHeap
        BinomialHeap binHeap = new BinomialHeap();

        System.out.println("Insert");
        int timeInsert=0;
        for (int i = 0; i < randomInt.length; i++) {
            long start =System.nanoTime();

            binHeap.insert(randomInt[i]);
            long finish= System.nanoTime();
            long time=finish-start;
            timeInsert+=time/1000;
            System.out.println("Время работы "+ i+":"+ time/1000);


        }

        int sr=timeInsert/10000;
        System.out.println("Среднее время "+ sr);

        System.out.println("Poisk");

        int timeFind=0;
        Random randPoick = new Random();
        int [] poisk=new int[100];
        for (int i = 0; i < 100; i++) {
            poisk[i]=randomInt[randPoick.nextInt(randomInt.length)];

        }
        //заолняем
        BinomialHeapNode heapNode=new BinomialHeapNode(poisk.length);
        for (int i = 0; i < poisk.length; i++) {
            heapNode.key=poisk[i];

        }



        for (int i = 0; i < poisk.length; i++) {


            long start =System.nanoTime();
            heapNode.findANodeWithKey(poisk[i]);
            long finish= System.nanoTime();
            long time=finish-start;
            timeFind+=time/1000;
            System.out.println("Время работы "+ i+": "+ time/1000);

        }
        // поиск минимума

        long start = System.nanoTime();
        binHeap.findMinimum();
        long finish = System.nanoTime();
        long time = finish - start;
        System.out.println("Время работы: " + time / 1000);







        int srFind=timeFind/100;
        System.out.println("Среднее время: "+ srFind);
        System.out.println("Del");
        Random randDel = new Random();
        int [] del=new int[1000];
        for (int i = 0; i < 1000; i++) {
            del[i]=randomInt[randDel.nextInt(randomInt.length)];

        }

        int timeDel=0;
        for (int i = 0; i < del.length; i++) {

            long startD =System.nanoTime();
            binHeap.delete(del[i]);
            long finishD= System.nanoTime();
            long timeD=finish-start;
            timeDel+=timeD/1000;
            System.out.println("Время работы "+ i+": "+ timeD/1000);
        }
        int srDel=timeDel/1000;
        System.out.println("Среднее время: "+srDel);


    }
}