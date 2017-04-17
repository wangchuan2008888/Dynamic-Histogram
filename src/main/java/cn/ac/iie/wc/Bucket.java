package cn.ac.iie.wc;

/**
 * Created by andrew on 17-3-31.
 * this class using as bucket of DynamicV-OptimalHistogram
 * base on article "Dynamic Histograms: Capturing Evolving Data Sets"
 * mail: me@wangchuan.site
 */
class Bucket implements Comparable<Bucket> {
    private double leftBorder;
    private long leftCount;
    private long rightCount;

    Bucket(double leftBorder, long leftCount, long rightCount) {
        this.leftBorder = leftBorder;
        this.leftCount = leftCount;
        this.rightCount = rightCount;
    }

    Bucket(double leftBorder) {
        this(leftBorder, 1, 0);
    }

    static Bucket[] splitBucket(Bucket inBucket, double rightBorder) {
        Bucket[] splitBucket = new Bucket[2];
        long leftCount = inBucket.getLeftCount();
        long rightCount = inBucket.getRightCount();
        splitBucket[0] = new Bucket(inBucket.getLeftBorder(),
                leftCount / 2, leftCount - leftCount / 2);
        splitBucket[1] = new Bucket((inBucket.getLeftBorder() + rightBorder) * 0.5,
                rightCount / 2, rightCount - rightCount / 2);
        return splitBucket;
    }

    static Bucket mergeBucket(Bucket bucketLeft, Bucket bucketRight) {
        return new Bucket(bucketLeft.getLeftBorder(), bucketLeft.getTotal(), bucketRight.getTotal());
    }

    void incLeft() {
        leftCount++;
    }

    void incRight() {
        rightCount++;
    }

    double getLeftBorder() {
        return leftBorder;
    }

    long getLeftCount() {
        return leftCount;
    }

    long getRightCount() {
        return rightCount;
    }

    long getTotal() {
        return leftCount + rightCount;
    }

    void blanceLeftRight() {
        long sum = getTotal();
        rightCount = sum / 2;
        leftCount = sum - rightCount;
    }

    @Override
    public int compareTo(Bucket bucket) {
        if (this.getLeftBorder() > bucket.getLeftBorder()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("left border:%f,count:%d,left count:%d,right count:%d\n",
                this.leftBorder,this.getTotal(),this.getLeftCount(),this.getRightCount());
    }
}
