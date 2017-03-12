package com.example.mysql;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.Timer;  
import java.util.TimerTask;  
  
import org.achartengine.ChartFactory;  
import org.achartengine.GraphicalView;  
import org.achartengine.chart.PointStyle;  
import org.achartengine.model.TimeSeries;  
import org.achartengine.model.XYMultipleSeriesDataset;  
import org.achartengine.renderer.XYMultipleSeriesRenderer;  
import org.achartengine.renderer.XYSeriesRenderer;  
  
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;  
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Message;  
import android.view.ViewGroup.LayoutParams;  
import android.widget.LinearLayout;  
  
public class RtChartsActivity extends Activity {  
      
    int constNum = 100;  
    private Timer timer = new Timer();  
    private GraphicalView chart;  
    private TimerTask task;  
    private int addY = -1;  
    private long addX;  
    private TimeSeries series;  
    private XYMultipleSeriesDataset dataset;  
    private Handler handler;  
    private Random random=new Random();  
    
    Date[] xcache = new Date[constNum];  
    int[] ycache = new int[constNum];  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.rtchart);  
        LinearLayout layout1 = (LinearLayout)findViewById(R.id.linearLayout1);//生成图表  
        chart = ChartFactory.getTimeChartView(this, getDateDemoDataset(), getDemoRenderer(), "mm:ss");  
        layout1.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));  
          
          
        handler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                //刷新图表  
                updateChart();  
                super.handleMessage(msg);  
            }  
        };  
        task = new TimerTask() {  
            @Override  
            public void run() {  
                Message message = new Message();  
                message.what = 200;  
                handler.sendMessage(message);  
            }  
        };  
        timer.schedule(task, 2*1000,1000);  
    }  
    private void updateChart() {
    	// * 1.删除数据集中的点集合
        // * 2.限制点显示的范围
        // * 3.将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
        // * 4.将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
        // * 5.给数据集重新添加新的点集合
        // * 6.视图更新

            //设定长度为20  
            int length = series.getItemCount();  
            if(length>=constNum) length = constNum;  
            addY=random.nextInt()%5+50;  
            addX=new Date().getTime();  
              
            //将前面的点放入缓存  
            for (int i = 0; i < length; i++) {  
                xcache[i] =  new Date((long)series.getX(i));  
                ycache[i] = (int) series.getY(i);  
            }  
              
            series.clear();  
            //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中  
            series.add(new Date(addX), addY);  
            for (int k = 0; k < length; k++) {  
                series.add(xcache[k], ycache[k]);  
            }  
            //在数据集中添加新的点集  
            dataset.removeSeries(series);  
            dataset.addSeries(series);  
            //曲线更新  
            chart.invalidate();  
        } 
    private XYMultipleSeriesRenderer getDemoRenderer() {  
            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();  
            renderer.setChartTitle("过程温度图示");//标题  
            renderer.setChartTitleTextSize(20);  
            renderer.setXTitle("时间");    //x轴说明  
            renderer.setYTitle("温度（℃）");  
            renderer.setAxisTitleTextSize(20);  
            renderer.setAxesColor(Color.BLACK);  
            renderer.setLabelsTextSize(20);    //数轴刻度字体大小  
            renderer.setLabelsColor(Color.BLACK);  
            renderer.setLegendTextSize(20);    //曲线说明  
            renderer.setXLabelsColor(Color.BLACK);  
            renderer.setYLabelsColor(0,Color.BLACK);  
            renderer.setShowLegend(false);
            renderer.setGridColor(Color.GREEN);// 曲线颜色
            renderer.setMargins(new int[] {5, 30, 15, 2});//上左下右{ 20, 30, 100, 0 })  
            XYSeriesRenderer r = new XYSeriesRenderer();  
            r.setColor(Color.RED);  
            r.setChartValuesTextSize(18);  
            r.setChartValuesSpacing(3);  
            r.setPointStyle(PointStyle.CIRCLE);                        
            r.setFillPoints(false);
            //r.setDisplayChartValues(true);
            //r.setDisplayChartValuesDistance(30);
            renderer.addSeriesRenderer(r);  
            renderer.setMarginsColor(Color.WHITE);  
            renderer.setPanEnabled(true,false);  
            renderer.setShowGrid(true);  
            renderer.setYAxisMax(110);//纵坐标最大值  
            renderer.setYAxisMin(0);//纵坐标最小值  
            renderer.setInScroll(true);    
            return renderer;  
          }  
    private XYMultipleSeriesDataset getDateDemoDataset() {//初始化的数据  
            dataset = new XYMultipleSeriesDataset(); 
            final int nr = 5;  
            long value = new Date().getTime();  
            double r=0 ;
            int i=0;
            double R[]=new double[5];
              for (HashMap<String, String> map : AllPvsActivity.pvsList) {
                Set<String> set = map.keySet();
                for (String key : set) {
                    String v = map.get(key);
                    if (key=="tem"){
                    	r=Double.parseDouble(v);                    	
                    }
                    if(key=="id"){
                    	 i=Integer.parseInt(v);
                    }
                    System.out.println(r);
                    R[i]=r; 
                    break;
                }
               
                  

                System.out.println(R[i]);
                i++;
            }
            for(int k=0;k<nr;k++){
            	System.out.println(R[k]);
            }
            series = new TimeSeries("Demo series " +  1);  
            for (int k = 0; k < 5; k++) {  
               series.add(new Date(value+k*500), R[k]);//初值Y轴以20为中心，X轴初值范围再次定义  
                   if(R[k]>60){
            	   final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            	   builder.setTitle("警告").setMessage("温度达到"+R[k]+"度了！").setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮        		   
               	         @Override
          	  
            	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
            	  
            	             // TODO Auto-generated method stub  
            	  
            	            // finish();  
            	  
            	         } 
            	         }) .show();
               }
            }  
            dataset.addSeries(series);  
            return dataset;  
          }  
    @Override  
    public void onDestroy() {  
            //当结束程序时关掉Timer  
            timer.cancel();  
            super.onDestroy();  
        };  
}  