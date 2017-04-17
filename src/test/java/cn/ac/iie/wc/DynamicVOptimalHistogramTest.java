package cn.ac.iie.wc;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.json.OptionUtil;
import com.github.abel533.echarts.series.Line;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by wangc on 2017/4/13.
 */
public class DynamicVOptimalHistogramTest {
    DynamicVOptimalHistogram dynamicVOptimalHistogram;
    public DynamicVOptimalHistogramTest(){
        dynamicVOptimalHistogram = new DynamicVOptimalHistogram(100,EpsType.VOPTIONAL);
    }
    @org.junit.Test
    public void addValue() throws Exception {
        Random random = new Random();
        for(int i =0;i<200000;i++){
            double v = random.nextGaussian() * 10;
            System.out.println(v);
            dynamicVOptimalHistogram.addValue(v);
        }
        ArrayList<HistgramJsonBeans> arrayList = dynamicVOptimalHistogram.toHistgramJsonBeans();
        String format = GsonUtil.format(arrayList);
        System.out.println(format);
    }

    @org.junit.Test
    public void printEps() throws Exception {
    }
    @Test
    public void toHtml(){
        Option option = new Option();
        option.legend("histogram");

        option.toolbox().show(true).feature(
                Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar),
                Tool.restore,
                Tool.saveAsImage);

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("x");
        option.xAxis(valueAxis);

        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("y");
        categoryAxis.boundaryGap(false);
        categoryAxis.type(AxisType.value);
        option.yAxis(categoryAxis);


        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系")
                .data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5)
                .itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        String format = GsonUtil.format(option);
        System.out.println(format);
    }
}