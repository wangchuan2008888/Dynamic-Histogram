package cn.ac.iie.wc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by wangc on 2017/4/14.
 */
public class DLinkedListTest {
    DLinkedList<Integer> dLinkedList;
    public DLinkedListTest(){
        dLinkedList = new DLinkedList<>();
    }
    @Test
    public void size() throws Exception {
        assertEquals(dLinkedList.size(),0);
        for(int i = 0;i <100;i++){
            dLinkedList.add(i);
            assertEquals(dLinkedList.size(),i+1);
        }
    }

    @Test
    public void add() throws Exception {
        for (int i =0;i<100;i++){
            dLinkedList.add(i);
            assertEquals(dLinkedList.tail.getValue().intValue(),i);
        }
        DLinkNode<Integer> node = dLinkedList.head;
        while (node != null){
            System.out.println(node.getValue());
            node = node.next;
        }
    }

    @Test
    public void enqueue() throws Exception {
        for (int i =0;i<100;i++){
            dLinkedList.enqueue(i);
            assertEquals(dLinkedList.head.getValue().intValue(),i);
        }
        printList(dLinkedList);
    }
    static <T> void  printList(DLinkedList<T> linkedList){
        DLinkNode<T> node = linkedList.head;
        while (node!=null){
            System.out.println(node.getValue());
            node = node.next;
        }
    }
    @Test
    public void addBefore() throws Exception {
        DLinkNode<Integer> node1 = dLinkedList.add(1);
        dLinkedList.add(2);
        DLinkNode<Integer> node = dLinkedList.add(3);
        dLinkedList.add(4);
        dLinkedList.add(5);
        dLinkedList.add(6);
        assertEquals(dLinkedList.size(),6);
        dLinkedList.addBefore(node,25);
        assertEquals(dLinkedList.size(),7);
        dLinkedList.addBefore(node1,0);
        assertEquals(dLinkedList.size(),8);
        printList(dLinkedList);
    }

    @Test
    public void addAfter() throws Exception {
        ArrayList<DLinkNode<Integer>> arrayList = new ArrayList<>();
        for(int i=0; i < 10;i++){
            arrayList.add(dLinkedList.add(i));
        }
        for(int i = 10;i < 20;i++){
            dLinkedList.addBefore(arrayList.get(i-10),i);
            assertEquals(dLinkedList.size(),i+1);
        }
        printList(dLinkedList);
    }

    @Test
    public void remove() throws Exception {
        ArrayList<DLinkNode<Integer>> arrayList = new ArrayList<>();
        for(int i=0; i < 10;i++){
            arrayList.add(dLinkedList.add(i));
        }
        int i = dLinkedList.size();
        for(int k = arrayList.size()-1; k >= 0;k--){
            dLinkedList.remove(arrayList.get(k));
            System.out.println(String.format("actual node count: %d, i :%d",dLinkedList.size(),i));
            assertEquals(dLinkedList.size(),--i);
        }
    }

}