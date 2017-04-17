package cn.ac.iie.wc;

/**
 * Created by wangc on 2017/4/13.
 * in this class when we talk about order:
 * head <--->end
 * before<--->after
 * enqueue<--->add
 */
public class DLinkedList<T> {
    int size = 0;
    DLinkNode<T> head;
    DLinkNode<T> tail;

    int size(){
        return size;
    }
    /**
     * add to tail of double linked list
     * **/
    DLinkNode<T> add(T value){
        DLinkNode<T> newNode = new DLinkNode<>(value);
        if(size++ ==0){
            head = newNode;
        }else {
            tail.next = newNode;
            newNode.pre = tail;
        }
        tail = newNode;
        return newNode;
    }

    /**
     * add to head of double linked list
     * **/
    DLinkNode<T> enqueue(T value){
        DLinkNode<T> newNode = new DLinkNode<T>(value);
        if(size++ == 0){
            tail = newNode;
        }else{
            head.pre = newNode;
            newNode.next = head;
        }
        head = newNode;
        return newNode;
    }
    /**
     * add value before given node
     * **/
    DLinkNode<T> addBefore(DLinkNode<T> node, T value){
        DLinkNode<T> newNode = new DLinkNode<T>(value);
        DLinkNode<T>  oldBefore = node.pre;

        newNode.next = node;
        node.pre = newNode;

        if(oldBefore != null){
            oldBefore.next = newNode;
            newNode.pre = oldBefore;
        }else {
            //head node
            newNode.pre = null;
            head = newNode;
        }
        size++;
        return newNode;
    }
    DLinkNode<T> addAfter(DLinkNode<T> node, T value){
        DLinkNode<T> newNode = new DLinkNode<>(value);
        DLinkNode<T> oldAfter = node.next;

        newNode.pre = node;
        node.next = newNode;

        if(oldAfter != null){
            oldAfter.pre = newNode;
            newNode.next = oldAfter;
        }else{
            //tail node
            newNode.next = null;
            tail = newNode;
        }
        size ++;
        return newNode;
    }
     void remove(DLinkNode<T> node){
        size--;
        if(node.pre == null && node.next == null){
            //only one node
            head = null;
            tail = null;
            return;
        }
        if (node.pre == null){
            // head node
            head = node.next;
            node.next.pre = null;
            return;
        }
        if(node.next == null){
            // tail node
            tail = node.pre;
            node.pre.next = null;
            return;
        }
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public DLinkNode<T> getTail() {
         return tail;
    }
    public DLinkNode<T> getHead(){
         return head;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(head == null)
            return "empty double linked list";
        for (DLinkNode<T> node = head; node != null;node = node.next){
            sb.append(node.getValue().toString());
        }
        return sb.toString();
    }
}
