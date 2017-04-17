package cn.ac.iie.wc;

/**
 * Created by wangc on 2017/4/13.
 */
public class DLinkNode<T> {
    T value;
    DLinkNode<T> pre;
    DLinkNode<T> next;
    public DLinkNode(T value){
        pre = null;
        next = null;
        this.value = value;
    }
    public T getValue() {
        return value;
    }

    public DLinkNode<T> getNext() {
        return next;
    }

    public DLinkNode<T> getPre() {
        return pre;
    }
    public void setValue(T value){
        this.value = value;
    }
}
