package com.example.firstgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class GeneralPage extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_TEXT = "com.example.application.FirstGame.EXTRA_TEXT";
    public static final String EXTRA_NUM = "com.example.application.FirstGame.EXTRA_NUM";
    public static final String EXTRA_NUM1 = "com.example.application.FirstGame.EXTRA_NUM1";
    public static final String EXTRA_TEXT1 = "com.example.application.FirstGame.EXTRA_TEXT1";
    public static final String EXTRA_TEXT2 = "com.example.application.FirstGame.EXTRA_TEXT2";
    Button one;
    Button two;
    Button next;
    Button submit;
    TextView toptext;
    TextView eqindic;
    TextView equation;
    TextView MeanLine;
    EditText xValues;
    EditText yValues;
    ArrayList<Double> xValuesAr;
    ArrayList<Double> yValuesAr;
    Boolean indic = false;
    String xsplitter = "";
    String ysplitter = "";
    String x = "";
    String y = "";
    int xsub = 0;
    public double low = 0;
    public double max = 0;
    double centroidx = 0;
    double centroidy = 0;
    double bsub0 = 0;
    double bsub1 = 0;
    double bsub1numsum = 0;
    double bsub1denomsum = 0;
    double quaddenom = 0;
    double quadb0;
    double quadb1;
    double quadb2;
    /*double powb0;
    double powb1;
    double expb0;
    double expb1;*/
    double hypb0;
    double hypb1;
    /*double logb0;
    double logb1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        xValuesAr = new ArrayList<Double>();
        yValuesAr = new ArrayList<Double>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_page);
        one = (Button)findViewById(R.id.buttonone);
        two = (Button)findViewById(R.id.buttontwo);
        two = (Button)findViewById(R.id.buttontwo);
        next = (Button)findViewById(R.id.next);
        submit = (Button)findViewById(R.id.submit);
        toptext = (TextView)findViewById(R.id.toptext);
        eqindic = (TextView)findViewById(R.id.eqindic);
        equation = (TextView)findViewById(R.id.equation);
        MeanLine = (TextView)findViewById(R.id.MeanLineIndicator);
        xValues = (EditText)findViewById(R.id.x_values);
        yValues = (EditText)findViewById(R.id.y_values);
        xValues.setAlpha(0f);
        next.setAlpha(0f);
        yValues.setAlpha(0f);
        submit.setAlpha(0f);
        MeanLine.setAlpha(0f);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        next.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.buttonone) {
            indic = true;
            toptext.setText("Enter your values");
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            next.setAlpha(1f);
            submit.setAlpha(1f);
            yValues.setAlpha(1f);
            yValues.setHint("Value");
        }
        if (view.getId() == R.id.buttontwo) {
            toptext.setText("Enter your coordinates");
            one.setVisibility(View.GONE);
            two.setVisibility(View.GONE);
            next.setAlpha(1);
            submit.setAlpha(1);
            xValues.setAlpha(1f);
            yValues.setAlpha(1f);
        }
        if (view.getId() == R.id.next) {
            x = xValues.getText().toString();
            y = yValues.getText().toString();
            xValues.setText("");
            yValues.setText("");
            xsub++;
            yValuesAr.add(Double.parseDouble(y));
            if(!indic)
                xValuesAr.add(Double.parseDouble(x));
            else
            {   double ok = (xsub-1);
                xValuesAr.add(ok);}
            if (xValues.getAlpha()==0){
                double sum = 0;
                for (int i = 0; i < yValuesAr.size(); i++)
                    sum += yValuesAr.get(i);
                String setter = "y = " + (sum/yValuesAr.size());
                equation.setText(setter);
            }
            else {
                LinReg();
                QuadReg();
                //PowReg();
                //ExpReg();
                HypReg();
                //LogReg();
                Analyze();
            }
        }
        if (view.getId() == R.id.submit) {
            if (yValuesAr.size() == 0){
                toptext.setText("Please enter values or select next.");
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (indic)
                            toptext.setText("Enter your values");
                        else
                            toptext.setText("Enter your coordinates");
                    }
                }, 3000);
            }
            else if (indic)
                MeanLine.setAlpha(1f);
            else
                openGraph();
        }
    }
    public void LinReg(){
        centroidx = 0;
        centroidy = 0;
        for (int i = 0; i < yValuesAr.size(); i++){
            centroidx += xValuesAr.get(i);
            centroidy += yValuesAr.get(i);
        }
        centroidx /= xValuesAr.size();
        centroidy /= yValuesAr.size();
        bsub1numsum += (Math.round(((xValuesAr.get(xsub-1)-centroidx) *  (yValuesAr.get(xsub-1)-centroidy))*100)/100);
        bsub1denomsum += (Math.round(((xValuesAr.get(xsub-1)-centroidx)*(xValuesAr.get(xsub-1)-centroidx))*100)/100);
        bsub1 = Math.round((bsub1numsum/bsub1denomsum)*100)/100;
        System.out.println("b1 = " + bsub1);
        bsub0 = centroidy - Math.round((bsub1numsum*centroidx/bsub1denomsum)*100)/100;
        System.out.println("b0 = " + bsub0);
        //String eq = "y = "+ Math.round(bsub0*100.0)/100.0 + " + " +  Math.round((bsub1numsum/bsub1denomsum)*100.0)/100.0 + "x";
        //equation.setText(eq);
    }

    public void QuadReg(){
        double quad_sumxx = 0;
        double quad_sumxy = 0;
        double quad_sumxx2 = 0;
        double quad_sumx2x2 = 0;
        double quad_sumx2y = 0;
        double quadcentroidx = 0;
        double quadcentroidy = 0;
        double quadcentroidx2 = 0;
        quadb0 = 0;
        quadb1 = 0;
        quadb2 = 0;

        for (int i = 0; i < yValuesAr.size(); i++){
            quadcentroidx += xValuesAr.get(i);
            quadcentroidy += yValuesAr.get(i);
            quadcentroidx2 += xValuesAr.get(i)*xValuesAr.get(i);
        }
        quadcentroidx /= xValuesAr.size();
        quadcentroidy /= yValuesAr.size();
        quadcentroidx2 /= xValuesAr.size();
        for (int i = 0; i < xValuesAr.size(); i++){
            quad_sumxx += (xValuesAr.get(i)-quadcentroidx)*(xValuesAr.get(i)-quadcentroidx);
            quad_sumxy += (xValuesAr.get(i)-quadcentroidx)*(yValuesAr.get(i)-quadcentroidy);
            quad_sumxx2 += (xValuesAr.get(i)-quadcentroidx)*((xValuesAr.get(i)*xValuesAr.get(i))-(quadcentroidx2));
            quad_sumx2x2 += ((xValuesAr.get(i)*xValuesAr.get(i))-(quadcentroidx2))*((xValuesAr.get(i)*xValuesAr.get(i))-(quadcentroidx2));
            quad_sumx2y += ((xValuesAr.get(i)*xValuesAr.get(i))-(quadcentroidx2))*(yValuesAr.get(i)-centroidy);
        }
        quaddenom = (quad_sumxx*quad_sumx2x2) - (quad_sumxx2*quad_sumxx2);
        System.out.println("denom = " + quaddenom);
        System.out.println("b0 = " + quadb0);
        quadb1 = (quad_sumxy*quad_sumx2x2-quad_sumx2y*quad_sumxx2)/quaddenom;
        quadb1 = Math.round(quadb1*100)/100;
        System.out.println("b1 = " + quadb1);
        quadb2 = (quad_sumx2y*quad_sumxx-quad_sumxy*quad_sumxx2)/quaddenom;
        quadb2 = Math.round(quadb2*100)/100;
        quadb0 = quadcentroidy - quadb1*quadcentroidx - quadb2*quadcentroidx2;
        quadb0 = Math.round(quadb0*100)/100;
        System.out.println("b2 = " + quadb2);

    }

    /*public void PowReg(){
        double lnxlny_sum = 0;
        double lnx_sum = 0;
        double lny_sum = 0;
        double two_lnx_sum = 0;
        powb0 = 0;
        powb1 = 0;
        for (int i = 0; i < xValuesAr.size(); i++){
            lnxlny_sum += Math.log(xValuesAr.get(i))*Math.log(yValuesAr.get(i));
            lnx_sum += Math.log(xValuesAr.get(i));
            lny_sum += Math.log(yValuesAr.get(i));
            two_lnx_sum += 2*Math.log(xValuesAr.get(i));
        }
        if (!((Double.isInfinite(lnxlny_sum)||Double.isNaN(lnxlny_sum))||(Double.isInfinite(lnx_sum)||Double.isNaN(lnx_sum))||(Double.isInfinite(lny_sum)||Double.isNaN(lny_sum))||(Double.isInfinite(two_lnx_sum)||Double.isNaN(two_lnx_sum)))) {
            powb1 = ((xValuesAr.size() * lnxlny_sum) - (lnx_sum * lny_sum)) / ((xValuesAr.size() * two_lnx_sum) - (lnx_sum * lnx_sum));
            powb1 = Math.round(100 * powb1) / 100;
            powb0 = Math.pow(2.71828, (lny_sum - powb1 * lnx_sum) / xValuesAr.size());
            powb0 = Math.round(100 * powb0) / 100;
        }

    }

    public void ExpReg(){
        double xlny_sum = 0;
        double x_sum = 0;
        double lny_sum = 0;
        double xsquare_sum = 0;
        expb0 = 0;
        expb1 = 0;
        for (int i = 0; i < xValuesAr.size(); i++){
            xlny_sum += xValuesAr.get(i)*Math.log(yValuesAr.get(i));
            x_sum += xValuesAr.get(i);
            lny_sum += Math.log(yValuesAr.get(i));
            xsquare_sum += xValuesAr.get(i)*xValuesAr.get(i);
        }
        if (!((Double.isInfinite(xlny_sum)||Double.isNaN(xlny_sum))||(Double.isInfinite(lny_sum)||Double.isNaN(lny_sum)))) {
            expb1 = Math.pow(2.71828, (xValuesAr.size()*xlny_sum-x_sum*lny_sum)/(xValuesAr.size()*xsquare_sum-x_sum*x_sum));
            expb1 = Math.round(100 * expb1) / 100;
            expb0 = Math.pow(2.71828, (lny_sum-Math.log(expb1)*x_sum));
            expb0 = Math.round(100 * expb0) / 100;
        }
    }

  */public void HypReg(){
        double yoverx_sum = 0;
        double invx_sum = 0;
        double y_sum = 0;
        double invx2_sum = 0;
        double x_sum = 0;
        hypb0 = 0;
        hypb1 = 0;
        for (int i = 0; i < xValuesAr.size(); i++){
            yoverx_sum += (yValuesAr.get(i)/xValuesAr.get(i));
            invx_sum += (1/xValuesAr.get(i));
            y_sum += yValuesAr.get(i);
            invx2_sum += (1/(xValuesAr.get(i)*xValuesAr.get(i)));
            x_sum += xValuesAr.get(i);
        }
        hypb1 = ((xValuesAr.size()*yoverx_sum)-(invx_sum*y_sum))/((xValuesAr.size()*invx2_sum)-(invx_sum*invx_sum));
        hypb1 = Math.round(hypb1*100)/100;
        hypb0 = ((y_sum - hypb1*invx_sum)/xValuesAr.size());
        hypb0 = Math.round(hypb0*100)/100;
    }/*

    public void LogReg(){
        double ylnx_sum = 0;
        double lnx_sum = 0;
        double y_sum = 0;
        double twolnx_sum = 0;
        logb1 = 0;
        logb0 = 0;
        for (int i = 0; i < xValuesAr.size(); i++){
            ylnx_sum += yValuesAr.get(i)*Math.log(xValuesAr.get(i));
            lnx_sum += Math.log(xValuesAr.get(i));
            y_sum += yValuesAr.get(i);
            twolnx_sum += 2*Math.log(xValuesAr.get(i));
        }
        if (!((Double.isInfinite(ylnx_sum)||Double.isNaN(ylnx_sum))||(Double.isInfinite(lnx_sum)||Double.isNaN(lnx_sum))||(Double.isInfinite(twolnx_sum)||Double.isNaN(twolnx_sum)))) {
            logb1 = (xValuesAr.size()*ylnx_sum-lnx_sum*y_sum)/(xValuesAr.size()*twolnx_sum-(lnx_sum*lnx_sum));
            logb1 = Math.round(100 * logb1) / 100;
            logb0 = (y_sum-logb1*lnx_sum)/xValuesAr.size();
            logb0 = Math.round(100 * logb0) / 100;
        }
    }*/

    public void Analyze(){
        double linear_dis_sum = 0;
        double quad_dis_sum = 0;
        double hyp_dis_sum = 0;
        for (int i = 0; i < xValuesAr.size(); i++){
            linear_dis_sum += Math.abs((bsub0 + bsub1*xValuesAr.get(i)) - yValuesAr.get(i));
            quad_dis_sum += Math.abs((quadb0 + quadb1*xValuesAr.get(i) + quadb2*xValuesAr.get(i)*xValuesAr.get(i)) - yValuesAr.get(i));
            hyp_dis_sum += Math.abs((hypb0 + (hypb1/xValuesAr.get(i)))-yValuesAr.get(i));
        }
        String sumname = "linear_dis_sum";
        double min = linear_dis_sum;
        if (linear_dis_sum < min){
            min = linear_dis_sum;
            sumname = "linear_dis_sum";
        }
        if (quad_dis_sum < min){
            min = quad_dis_sum;
            sumname = "quad_dis_sum";
        }
        if (hyp_dis_sum < min){
            min = hyp_dis_sum;
            sumname = "hyp_dis_sum";
        }
        String eq = "error";
        if (sumname == "linear_dis_sum")
            eq = "y = "+ Math.round(bsub0*100.0)/100.0 + " + " +  Math.round((bsub1numsum/bsub1denomsum)*100.0)/100.0 + "x";
        else if (sumname == "quad_dis_sum")
            eq = "y = "+ quadb0 + " + " +  quadb1 + "x" + " + " + quadb2 + "x^2";
        /*else if (sumname == "pow_dis_sum")
            eq = "y ="+ powb0 + " * x^" + powb1;
        else if (sumname == "exp_dis_sum")
            eq = "y ="+ expb0 + " * " + expb1 + "^x";*/
        else if (sumname == "hyp_dis_sum")
            eq = "y ="+ hypb0 + " + " + hypb1 + "/x";
        /*else if (sumname == "log_dis_sum")
            eq = "y ="+ logb0 + " + " + logb1 + "ln(x)";*/
        System.out.println("eq" + eq);
        equation.setText(eq);
    }

    public void openGraph(){

        TextView equation = (TextView) findViewById(R.id.equation);
        String text = equation.getText().toString();
        xsplitter = "";
        ysplitter = "";
        low = xValuesAr.get(0);
        for (int i = 0; i < xValuesAr.size(); i++) {
            if (i != xValuesAr.size()-1){
                xsplitter += xValuesAr.get(i) + "q";
                ysplitter += yValuesAr.get(i) + "q";
            }
            else{
                xsplitter += xValuesAr.get(i);
                ysplitter += yValuesAr.get(i);
            }
            if (xValuesAr.get(i) < low)
                low = xValuesAr.get(i);
        }
        max = xValuesAr.get(0);
        for (int i = 0; i < xValuesAr.size(); i++) {
            if (xValuesAr.get(i) > max)
                max = xValuesAr.get(i);
        }
        Intent intent = new Intent(this, HealthPage.class);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_NUM, low);
        intent.putExtra(EXTRA_NUM1, max);
        intent.putExtra(EXTRA_TEXT1, xsplitter);
        intent.putExtra(EXTRA_TEXT2, ysplitter);
        startActivity(intent);
    }
}
