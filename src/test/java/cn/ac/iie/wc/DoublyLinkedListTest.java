package cn.ac.iie.wc;

import com.clearspring.analytics.util.DoublyLinkedList;
import com.clearspring.analytics.util.ListNode2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wangc on 2017/4/13.
 */
public class DoublyLinkedListTest {
    DoublyLinkedList<Integer> doubleDoublyLinkedList;
    @Before
    public void before(){
        doubleDoublyLinkedList = new DoublyLinkedList<>();
    }
    @Test
    public void test(){

        for(int i=0;i <10;i++){
            doubleDoublyLinkedList.enqueue(i);
        }
        ListNode2<Integer> node = doubleDoublyLinkedList.tail();
        while(node != null){

            System.out.println(node.getValue());
            node = node.getNext();
        }
    }
    @After
    public void after(){
        for(Integer i : doubleDoublyLinkedList){
            System.out.println(i);
        }
    }
}
