package cn.ac.iie.wc;

import java.util.ArrayList;

/**
 * Created by andrew on 17-3-31.
 */
public class DynamicVOptimalHistogram {
    EpsType epsComputer;
    private DLinkedList<Bucket> bucketList = new DLinkedList<>();
    private double maxValue = -Double.MAX_VALUE;
    private int bucketLimte = 0;

    public DynamicVOptimalHistogram(int maxBuckets, EpsType epsComputer) {
        this.bucketLimte = maxBuckets;
        this.epsComputer = epsComputer;
    }

    public void addValue(double value) {
        if (bucketList.size() < bucketLimte) {
            addValueBeforeFull(value);
            return;
        }
        if(addAheadList(value)||addTailList(value)){
            DLinkNode<Bucket> node = findBestToMerge();
            mergeBucket(node);
            return;
        }
        addToList(value);
    }
    private void addToList(double value){
        for (DLinkNode<Bucket> node = bucketList.getHead();
                node != null; node = node.next){
            if(node.getValue().getLeftBorder()> value){
                double nextLeft;
                if(node.next != null)
                    nextLeft = node.getValue().getLeftBorder();
                else
                    nextLeft = maxValue;
                addToBucket(node.getPre().getValue(),nextLeft,value);
                compress();
                break;
            }
        }
    }

    private void compress() {
        DLinkNode<Bucket> bestToMerge = findBestToMerge();
        DLinkNode<Bucket> bestToSplit = findBestToSplit();
        if(computeEps(bestToMerge) > (computeEps(bestToSplit)+computeEps(bestToMerge.getNext()))){

            spliteBucket(bestToSplit);
            mergeBucket(bestToMerge);
        }
    }
    private double getRightBorder(DLinkNode<Bucket> node){
        return node.getNext() != null ? node.getNext().getValue().getLeftBorder():maxValue;
    }
    private static void addToBucket(Bucket bucket, double right ,double value){
        double left = bucket.getLeftBorder();
        double middle = (left+right)*0.5;
        if(value<left || value > right)
            throw new IllegalArgumentException("value is not between bucket");
        if(middle < value)
            bucket.incRight();
        else
            bucket.incLeft();
    }
    private void addValueBeforeFull(double value) {
//        System.out.println(value);
        if(!(addAheadList(value) || addTailList(value))){
            DLinkNode<Bucket> node = bucketList.getHead();
            while(node != null){
                if(node.getValue().getLeftBorder()>value){
                    if(node.getPre().getValue().getLeftBorder() == value){
                        node.getPre().getValue().incLeft();
                    }else {
                        bucketList.addBefore(node,new Bucket(value));
                    }
                    break;
                }
                node = node.next;
            }
        }
    }

    /**
     * if value little than smallest value
     * add value as frist bucket and return true
     * else return false
     * */
    private boolean addAheadList(double value) {
        if(bucketList.getHead()==null){
            bucketList.add(new Bucket(value));
            this.maxValue = value;
            return true;
        }
        if (value < bucketList.getHead().getValue().getLeftBorder()) {
            bucketList.enqueue(new Bucket(value));
            return true;
        }
        return false;
    }

    /**
     * if value bigger than biggest value
     * add value as frist bucket and return true
     * else return false
     * */
    private boolean addTailList(double value) {
        if (value > this.maxValue) {
            this.maxValue = value;
            bucketList.add(new Bucket(value));
            return true;
        } else
            return false;
    }

    private double computeEps(DLinkNode<Bucket> node){
        double left = node.getValue().getLeftBorder();
        double right = getRightBorder(node);
        return epsComputer.computeEps(left,node.getValue().getLeftCount(),node.getValue().getRightCount(),right,false);
    }

    private DLinkNode<Bucket> findBestToMerge() {
        DLinkNode<Bucket> node = bucketList.getHead();
        DLinkNode<Bucket> minNode = null;
        double minDeltaEps = Double.MAX_VALUE;
        for(;node.getNext() != null ;node = node.getNext()){
            double org1 = epsComputer.computeEps(node.getValue().getLeftBorder(),node.getValue().getLeftCount(),
                    node.getValue().getRightCount(),getRightBorder(node),true);
            double org2 = epsComputer.computeEps(node.getNext().getValue().getLeftBorder(),node.getNext().getValue().getLeftCount(),
                    node.getNext().getValue().getRightCount(),
                    getRightBorder(node.getNext()),true);
            double after = epsComputer.computeEps(node.getValue().getLeftBorder(),
                    node.getValue().getTotal(),
                    node.next.getValue().getTotal(),
                    getRightBorder(node.getNext()),true);
            double deltaEps = after - (org1 - org2);
            if(deltaEps < minDeltaEps){
                minNode = node;
                minDeltaEps = deltaEps;
            }
        }
        if(minNode == null){
            throw new NullPointerException();
        }
        return minNode;
    }
    private DLinkNode<Bucket> findBestToSplit(){
        //find max eps node
        double maxEps = -Double.MAX_VALUE;
        DLinkNode<Bucket> maxEpsNode =null;
        DLinkNode<Bucket> node = bucketList.getHead();
        for(;node != null;node = node.getNext()){
            double eps = computeEps(node);
            if (eps > maxEps){
                maxEps = eps;
                maxEpsNode = node;
            }
        }
        return maxEpsNode;
    }
    private void spliteBucket(DLinkNode<Bucket> maxNode) {
        double leftBorder = maxNode.getValue().getLeftBorder();
        double rightBorder = getRightBorder(maxNode);
        long leftCount = maxNode.getValue().getLeftCount();
        long rightCount = maxNode.getValue().getRightCount();
        Bucket bucket1 = new Bucket(leftBorder,leftCount/2,leftCount-leftCount/2);
        Bucket bucket2 = new Bucket((leftBorder+ rightBorder)*0.5,rightCount/2,rightCount-rightCount/2);
        bucketList.addBefore(maxNode,bucket1);
        bucketList.addBefore(maxNode,bucket2);
        bucketList.remove(maxNode);

    }

    private void mergeBucket(DLinkNode<Bucket> node) {
        Bucket newBucket = new Bucket(node.getValue().getLeftBorder(),
                node.getValue().getTotal(),node.getNext().getValue().getTotal());
        bucketList.addBefore(node,newBucket);
        bucketList.remove(node.getNext());
        bucketList.remove(node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bucketList.toString());
        return sb.toString();
    }
    public ArrayList<HistgramJsonBeans> toHistgramJsonBeans(){
        ArrayList<HistgramJsonBeans> arrayList = new ArrayList<HistgramJsonBeans>();
        DLinkNode<Bucket> node = bucketList.getHead();
        for(; node != null;node = node.getNext()){
            double left = node.getValue().getLeftBorder();
            double right = getRightBorder(node);
            long total = node.getValue().getTotal();
            double y = total / (right-left);
            arrayList.add(new HistgramJsonBeans(left,y));
        }
        return arrayList;
    }
}
