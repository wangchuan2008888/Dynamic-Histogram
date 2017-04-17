package cn.ac.iie.wc;

/**
 * Created by andrew on 17-3-31.
 */
public enum EpsType {
    VOPTIONAL_E {
        @Override
        double computeEps(double leftBorder, long leftCount, long rightCount, double rightBorder,boolean border) {
            long total = leftCount + rightCount;
            double leftCountF = 1.0 * leftCount/total;
            double rightCountF = 1.0 * rightCount/total;
            double averageF = (leftCountF + rightCountF ) * 0.5;
            return (Math.pow((leftCountF-averageF),2) +
                    Math.pow(rightCountF-averageF,2)) *
                    Math.pow((rightBorder-leftBorder)*0.25,2);
        }
    },
    VOPTIONAL{
        @Override
        double computeEps(double leftBorder, long leftCount, long rightCount, double rightBorder,boolean border) {
            if(border)
                return VOPTIONAL_E.computeEps(leftBorder,leftCount,rightCount,rightBorder,false);
            long total = leftCount + rightCount;
            double leftCountF = 1.0 * leftCount/total;
            double rightCountF = 1.0 * rightCount/total;
            double averageF = (leftCountF + rightCountF ) * 0.5;
            return (Math.pow((leftCountF-averageF),2) +
                    Math.pow(rightCountF-averageF,2));
        }
    };
//    AverageDeviationOptimal {
//        @Override
//        double computeEps(Bucket bucket) {
//            long total = bucket.getTotal();
//            double leftCountF = 1.0*bucket.getLeftCount();
//            double rightCountF = 1.0 * bucket.getRightCount();
//            double averageF = (leftCountF + rightCountF ) * 0.5;
//            return Math.abs(leftCountF - averageF)
//                    + Math.abs(rightCountF - averageF);
//        }
//    };

    abstract double computeEps(double leftBorder, long leftCount, long rightCount, double rightBorder,boolean border);
}
