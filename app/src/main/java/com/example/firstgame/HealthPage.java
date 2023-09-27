package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Arrays;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HealthPage extends GeneralPage implements View.OnClickListener{

    TextView value;
    EditText input;
    Button xVal;
    LineGraphSeries<DataPoint>series;
    Boolean linear = false;
    Boolean quadratic = false;
    Boolean hyper = false;
    double b0;
    double b1;
    double b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_page);
        value = (TextView)findViewById(R.id.value);
        value.setAlpha(0);
        input = (EditText)findViewById(R.id.input);
        xVal = (Button)findViewById(R.id.gobutton);
        xVal.setOnClickListener(this);
        Intent intent = getIntent();
        String text = intent.getStringExtra(GeneralPage.EXTRA_TEXT);
        double low1 = intent.getDoubleExtra(GeneralPage.EXTRA_NUM, low);
        double max1 = intent.getDoubleExtra(GeneralPage.EXTRA_NUM1, max);
        String xsplitter = intent.getStringExtra(GeneralPage.EXTRA_TEXT1);
        String ysplitter = intent.getStringExtra(GeneralPage.EXTRA_TEXT2);
        String[] xValsString = xsplitter.split("q");
        String[] yValsString = ysplitter.split("q");
        Double[] xVals = new Double[xValsString.length];
        Double[] yVals = new Double[xValsString.length];
        for (int i = 0; i < xValsString.length; i++){
            xVals[i] = Double.parseDouble(xValsString[i]);
            yVals[i] = Double.parseDouble(yValsString[i]);
        }
        b0 = 0;
        b1 = 0;
        b2 = 0;
        System.out.println(max1 + " " + low1);
        GraphView graph = (GraphView) findViewById(R.id.graphical);
        TextView equation = (TextView) findViewById(R.id.textView2);
        equation.setText(text);
        if (text.charAt(text.length() - 1) == 'x' && text.charAt(text.length() - 2) != '/') {
            linear = true;
            text = text.substring(4);
            b0 = Double.parseDouble(text.substring(0, text.indexOf(' ')));
            text = text.substring(text.indexOf(' '));
            text = text.substring(1);
            text = text.substring(text.indexOf(' '));
            b1 = Double.parseDouble(text.substring(0, text.indexOf('x')));
        }
        if (text.charAt(text.length() - 2) == '/'){
            hyper = true;
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMaxY(1000);
            graph.getViewport().setMinY(-1000);
            text = text.substring(4);
            b0 = Double.parseDouble(text.substring(0,text.indexOf(' ')));
            System.out.println(b0);
            text = text.substring(text.indexOf('+') + 2);
            System.out.println(text);
            b1 = Double.parseDouble(text.substring(0,text.indexOf('/')));
            System.out.println(b1);

        }
        if (text.charAt(text.length() - 1) == '2'){
            quadratic = true;
            text = text.substring(4);
            b0 = Double.parseDouble(text.substring(0, text.indexOf(' ')));
            text = text.substring(text.indexOf(' '));
            text = text.substring(1);
            text = text.substring(text.indexOf(' '));
            b1 = Double.parseDouble(text.substring(0, text.indexOf('x')));
            text = text.substring(text.indexOf('+'));
            b2 = Double.parseDouble(text.substring(2, text.indexOf('x')));
        }
        double x, y;
        int distance = (((int)Math.ceil(max1-low1))*10);
        x = low1 - Math.abs(max1);
       /* graph.getViewport().setMinX(x);
        //System.out.println("min x = " + graph.getViewport().getMinX(true));
        graph.getViewport().setMaxX(max1 + distance);
        //System.out.println("max x = " + graph.getViewport().getMaxX(true));
        graph.getViewport().setMinY((b2 * x * x) + (b1 * x) + b0 - Math.abs((b2 * x * x) + (b1 * x) + b0)/5);
       // System.out.println("min y = " + graph.getViewport().getMinY(true));
        graph.getViewport().setMaxY((b2 * (x+distance) * (x+distance)) + (b1 * (x+distance)) + b0 + Math.abs(((b2 * (x+distance) * (x+distance)) + (b1 * (x+distance)) + b0)/5));
        //System.out.println("max y = " + graph.getViewport().getMaxY(true));
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);*/
        int rangepoints = (int)(Math.ceil(max1+distance)-x)*100;
        System.out.println("rangepoints = " + rangepoints);
        //DataPoint[] bruh = new DataPoint[rangepoints];
        series = new LineGraphSeries<DataPoint>();
        series.setColor(Color.BLUE);
        series.setThickness(5);
        for (int i = 0; i < rangepoints; i++) {
            series.setColor(Color.BLUE);
            series.setThickness(5);
            x += .01;
            Boolean indic = false;
            int index = 0;
            for (int j = 0; j < xVals.length; j++){
                if (xVals[j] == x) {
                    indic = true;
                    index = j;
                }
            }
            if (indic)
                y = yVals[index];
            else {
                series.setColor(Color.BLUE);
                series.setThickness(5);
                if (hyper) {
                    if (x != 0)
                        y = b0 + (b1/x);
                    else
                        y = 0;
                }
                else
                    y = (b2 * x * x) + (b1 * x) + b0;
            }
            series.appendData(new DataPoint(x,y), true, rangepoints);
        }
        series.setColor(Color.RED);
        System.out.println(xVals.length);

        /*for (int i = 0; i < xVals.length; i++) {
            for (int j = i; j < xVals.length; j++){
                if (xVals[j] < low)
                    low = xVals[j];
            }
            newxVals[i] = low;
        }
        for (int i = 0; i < xVals.length; i++) {
            x = newxVals[i];
            y = yVals[i];
            series.appendData(new DataPoint(x,y), true, xVals.length);
        }*/
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        System.out.println("max = " + graph.getViewport().getMaxXAxisSize() + " " + graph.getViewport().getMaxYAxisSize());
        graph.addSeries(series);
    }

    public void onClick(View view){
        if (view.getId() == R.id.gobutton){

            Double xstuff = Double.parseDouble(input.getText().toString());
            Double returner = 0.0;
            if (linear)
                returner = b0 + b1*xstuff;
            else if (quadratic)
                returner = b0 + b1*xstuff + b2*xstuff*xstuff;
            else
                returner = b0 + b1/xstuff;
            System.out.println(returner);
            value.setText(returner.toString());
            value.setAlpha(1f);
        }
    }
}
