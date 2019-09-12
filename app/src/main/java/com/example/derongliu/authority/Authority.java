package com.example.derongliu.authority;

public class Authority {

    public static void main(String[] argv) {

        int[] array = {2, 5, 7, 1, 6, 9, 8, 0, 3};

//        冒泡排序
//        bSort(array);


//        快速排序
//        qSort(array, 0, array.length - 1);
//
//        for (int a : array) {
//            System.out.print(a);
//            System.out.print(",");
//        }

//        链表任意两个元素交换
//        Node head = new Node(10);
//        Node currentNode = head;
//
//        int index1 = 0;
//        int index2 = 8;
//
//        Node node1 = null;
//        Node node2 = null;
//
//        for (int i = 0; i < array.length; i++) {
//            Node next = new Node(array[i]);
//            if (i == index1) {
//                node1 = next;
//            }
//
//            if (i == index2) {
//                node2 = next;
//            }
//
//            currentNode.next = next;
//            currentNode = next;
//        }
//
//        currentNode = head;
//        while (currentNode.next != null) {
//            System.out.print(currentNode.next.value);
//            currentNode = currentNode.next;
//        }
//        System.out.print("\n");
//
//        swapTwoNode(head, node1, node2);
//        currentNode = head;
//        while (currentNode.next != null) {
//            System.out.print(currentNode.next.value);
//            currentNode = currentNode.next;
//        }
//        System.out.print("\n");

        Node head = new Node(10);
        Node currentNode = head;
        for (int a : array) {
            Node next = new Node(a);
            currentNode.next = next;
            currentNode = next;
        }

        currentNode = head;
        while (currentNode != null) {
            System.out.print(currentNode.value);
            currentNode = currentNode.next;
        }
        System.out.print("\n");


//         相邻节点交换
//         值交换
//        swapAdjacentNodeByValue(head);
//        指针交换
//        currentNode = swapAdjacentNodeByPointer(head);


//        reverseList
//        currentNode = reverseList(head);
//        reverseListByRecursive
        currentNode = reverseListByRecursive(head);
        while (currentNode != null) {
            System.out.print(currentNode.value);
            currentNode = currentNode.next;
        }
        System.out.print("\n");
    }

    private static Node reverseListByRecursive(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node node = reverseListByRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    private static Node reverseList(Node head) {

        Node preNode = null;
        Node nextNode = null;
        Node currentNode = head;

        while (currentNode != null) {
            nextNode = currentNode.next;
            currentNode.next = preNode;
            preNode = currentNode;
            currentNode = nextNode;
        }
        return preNode;
    }

    private static void bSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

    private static void qSort(int[] array, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int index = array[i];

        while (i < j) {
            while (i < j && array[j] >= index) {
                j--;
            }

            if (i < j) {
                array[i++] = array[j];
            }

            while (i < j && array[i] < index) {
                i++;
            }

            if (i < j) {
                array[j--] = array[i];
            }
        }
        array[i] = index;
        qSort(array, low, i - 1);
        qSort(array, i + 1, high);
    }


    private static void swapTwoNode(Node head, Node node1, Node node2) {
        if (node1 == null || node2 == null || node1 == node2) {
            return;
        }

        if (node1.next == node2) {
            Node currentNode = head;
            while (currentNode.next != null) {
                if (currentNode.next == node1) {
                    node1.next = node2.next;
                    node2.next = node1;
                    currentNode.next = node2;
                    return;
                }
                currentNode = currentNode.next;
            }
            return;
        }

        if (node2.next == node1) {
            Node currentNode = head;
            while (currentNode.next != null) {
                if (currentNode.next == node2) {
                    node2.next = node1.next;
                    node1.next = node2;
                    currentNode.next = node1;
                    return;
                }
                currentNode = currentNode.next;
            }
            return;
        }

        Node currentNode = head;

        Node preNode1 = null;
        Node preNode2 = null;

        while (currentNode.next != null) {

            if (preNode1 != null && preNode2 != null) {
                break;
            }

            if (currentNode.next == node1) {
                preNode1 = currentNode;
            }

            if (currentNode.next == node2) {
                preNode2 = currentNode;
            }

            currentNode = currentNode.next;
        }

        if (preNode1 != null && preNode2 != null) {
            Node postNode2 = node2.next;
            preNode1.next = node2;
            node2.next = node1.next;
            preNode2.next = node1;
            node1.next = postNode2;
        }
    }

    private static void swapAdjacentNodeByValue(Node head) {
        Node currentNode = head;
        while (currentNode != null) {
            Node q = currentNode.next;
            if (q != null) {
                int tmp = q.value;
                q.value = currentNode.value;
                currentNode.value = tmp;
                currentNode = currentNode.next;
            }
            currentNode = currentNode.next;
        }
    }

    private static Node swapAdjacentNodeByPointer(Node head) {
        if (head != null && head.next != null) {
            Node currentNode = head;
            Node pre = head;
            head = head.next;
            Node p;
            Node q;
            while (currentNode != null && currentNode.next != null) {
                p = currentNode.next;
                q = p.next;

                pre.next = p;
                p.next = currentNode;
                currentNode.next = q;

                pre = currentNode;
                currentNode = q;
            }
        }
        return head;
    }

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
}
