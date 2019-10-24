package ru.shurinovlev.saos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;


public class UIDraw extends View {

    static View v2;

    float proportion, difference;

    int gen_quantity, filter_name, type_signal_1, type_signal_2,type_signal_3, operation;
    boolean fil, add_multi = false;
    float width, height, width_line;
    double freq1, ampl1, freq2, ampl2, freq3, ampl3, ampl_noise, from_freq, before_freq, offset1, offset2, offset3;
    RectF btn1, btn2, btn3, btn4, btn4_1, add_1, add_2, gen1_1, gen1_2, gen1_3, gen1_4, gen1_5, gen1_6, gen2_3, gen2_4, gen2_5, gen2_6, gen3_5, gen3_6, fil1, fil2;

    int XtoU(double x, float w) {
        return (int) ( width * 6 / 108 * x / 25.12 + w - width * 15 / 108);
    }
    int YtoV(double y, float h) {
        return (int) ( height * 4 / 64 * (y + 1) / 2 + h - height * 15 / 64);
    }

    Paint mPaint = new Paint();

    public UIDraw(Context context) {
        super(context);
    }

    public UIDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public UIDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public UIDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();
        width_line = height / 200;
        proportion = width/height/2;
        mPaint.setStrokeWidth(width_line);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));

        //заливка фона
        mPaint.setColor(getResources().getColor(R.color.mycolor));
        canvas.drawRect(0,0,width,getHeight(),mPaint);
        if (gen_quantity == 0){
            mPaint.setColor(getResources().getColor(R.color.mycolor));
            canvas.drawRect(0,0,width,getHeight(),mPaint);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(width / 9, 0, width / 9, height, mPaint);


        difference = (height - height * proportion)/2;
        height = height * proportion;
        /*
        //данные о экране
        mPaint.setTextSize(width / 20);
        mPaint.setColor(Color.RED);
        canvas.drawText("Ширина "+String.valueOf(width)+ " Высота "+ String.valueOf(height),x,height / 2, mPaint);
        */


        //координаты элементов панели
        btn1 = new RectF(width / 54, height * 8 / 100 + difference, width * 5 / 54, height * 23 / 100 + difference);
        btn2 = new RectF(width / 54, height * 31 / 100 + difference,width * 5 / 54, height * 46 / 100 + difference);
        btn3 = new RectF(width / 54, height * 54 / 100 + difference, width * 5 / 54, height * 69 / 100 + difference);
        btn4 = new RectF(width / 54, height * 77 / 100 + difference, width * 5 / 54, height * 92 / 100 + difference);
        btn4_1 = new RectF(width / 54, height * 77 / 100 + difference, width * 5 / 54, height * 92 / 100 + difference);


        //координаты генераторов
        gen1_6 = new RectF(width * 8 / 54, height / 16 + difference, width * 16 / 54, height * 5 / 16 + difference);
        gen2_6 = new RectF(width * 8 / 54, height * 6 / 16 + difference, width * 16 / 54, height * 10 / 16 + difference);
        gen3_6 = new RectF(width * 8 / 54, height * 11 / 16 + difference, width * 16 / 54, height * 15 / 16 + difference);
        gen1_5 = new RectF(width * 15 / 54, height / 16 + difference, width * 23 / 54, height * 5 / 16 + difference);
        gen2_5 = new RectF(width * 15 / 54, height * 6 / 16 + difference, width * 23 / 54, height * 10 / 16 + difference);
        gen3_5 = new RectF(width * 15 / 54, height * 11 / 16 + difference, width * 23 / 54, height * 15 / 16 + difference);
        gen1_4 = new RectF(width * 8 / 54, height * 3 / 16 + difference, width * 16 / 54, height * 7 / 16 + difference);
        gen2_4 = new RectF(width * 8 / 54, height * 9 / 16 + difference, width * 16 / 54, height * 13 / 16 + difference);
        gen1_3 = new RectF(width * 15 / 54, height * 3 / 16 + difference, width * 23 / 54, height * 7 / 16 + difference);
        gen2_3 = new RectF(width * 15 / 54, height * 9 / 16 + difference, width * 23 / 54, height * 13 / 16 + difference);
        gen1_2 = new RectF(width * 15 / 54, height * 6 / 16 + difference, width * 23 / 54, height * 10 / 16 + difference);
        gen1_1 = new RectF(width * 22 / 54, height * 6 / 16 + difference, width * 30 / 54, height * 10 / 16 + difference);
        //координаты сумматора/перемножителя
        add_2 = new RectF(width * 25 / 54, height * 13 / 32 + difference, width * 30 / 54 , height * 19 / 32 + difference);
        add_1 = new RectF(width * 32 / 54, height * 13 / 32 + difference, width * 37 / 54 , height * 19 / 32 + difference);
        //координаты фильтра
        fil2 = new RectF(width * 39 / 54, height * 13 / 32 + difference, width * 45 / 54 , height * 19 / 32 + difference);
        fil1 = new RectF(width * 32 / 54, height * 13 / 32 + difference, width * 38 / 54 , height * 19 / 32 + difference);





        //отрисовка панели с кномпками
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(width / 9, difference, width / 9, height + difference, mPaint);
        canvas.drawRect(btn1, mPaint);
        canvas.drawRect(btn2, mPaint);
        canvas.drawRect(btn3, mPaint);
        if (ampl_noise != 0){
            mPaint.setColor(Color.GREEN);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(btn4_1, mPaint);
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        canvas.drawRect(btn4, mPaint);
        //текст для кнопок
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(height / 25);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("+FGEN",width * 11 / 432 ,height * 33 / 200 + difference, mPaint);
        canvas.drawText("+filter",width * 7 / 216 ,height * 79 / 200 + difference, mPaint);
        canvas.drawText("clear",width * 15 / 432 ,height * 125 / 200 + difference, mPaint);
        canvas.drawText("noise",width * 7 / 216 ,height * 171 / 200 + difference, mPaint);
        mPaint.setStrokeWidth(width_line);
        mPaint.setStyle(Paint.Style.STROKE);



        if ( (gen_quantity == 1) && (!fil) ) {
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_1, mPaint);
            //рисование точек наблюдения сигнала и спектра 1
            canvas.drawCircle(width * 69 / 108, height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 69 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }


            //провода
            canvas.drawLine(width * 30 / 54, height / 2 + difference, width * 33 / 54, height / 2 + difference, mPaint);
            //обозначение типа сигнала на генераторе 1
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1) {
                    //синус
                    canvas.drawLine(XtoU(j / 25, width * 30 / 54), YtoV(Math.sin(j / 25), height * 10 / 16 + difference), XtoU((j + 1) / 25, width * 30 / 54), YtoV(Math.sin((j + 1) / 25), height * 10 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2) {
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25, width * 30 / 54), YtoV(0.7, height * 10 / 16 + difference), XtoU((j + 1) / 25, width * 30 / 54), YtoV(0.7, height * 10 / 16 + difference), mPaint);
                    } else {
                        canvas.drawLine(XtoU(j / 25, width * 30 / 54), YtoV(-0.7, height * 10 / 16 + difference), XtoU((j + 1) / 25, width * 30 / 54), YtoV(-0.7, height * 10 / 16 + difference), mPaint);
                    }
                    if (j == 314) {
                        canvas.drawLine(XtoU(j / 25, width * 30 / 54), YtoV(0.7, height * 10 / 16 + difference), XtoU((j + 1) / 25, width * 30 / 54), YtoV(-0.7, height * 10 / 16 + difference), mPaint);
                    }

                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(height / 20);
            canvas.drawText("FGEN",width * 52 / 108 ,height * 7 / 16 + difference, mPaint);
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 177 / 432 ,height * 31 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 177 / 432 ,height * 35 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 177 / 432 ,height * 39 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        if ( (gen_quantity == 1) && (fil) ){
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_2, mPaint);
            //рисование фильтров
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(fil1,mPaint);
            //рисование точек наблюдения сигнала и спектра 2
            canvas.drawCircle(width * 55 / 108, height / 2 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 85 / 108, height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height / 2 + difference, width / 40, mPaint);
                canvas.drawCircle(width * 85 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            //провода
            canvas.drawLine(width * 23 / 54, height / 2 + difference,width * 26 / 54 ,height / 2 + difference,mPaint);
            canvas.drawLine(width * 29 / 54, height / 2 + difference,width * 32 / 54 ,height / 2 + difference,mPaint);
            canvas.drawLine(width * 38 / 54, height / 2 + difference,width * 41 / 54 ,height / 2 + difference,mPaint);
            //обозначение типа сигнала на генераторе 2
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 10 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 10 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }

                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setTextSize(height / 20);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 7 / 16 + difference, mPaint);
            canvas.drawText("filter",width * 135 / 216 ,height * 36 / 64 + difference, mPaint);
            if (filter_name == 0){
                canvas.drawText("LP",width * 137 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 1){
                canvas.drawText("HP",width * 137 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 2){
                canvas.drawText("BP",width * 137 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 3){
                canvas.drawText("notch",width * 267 /432 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 4){
                canvas.drawText("matched",width * 519 / 864 ,height * 31 / 64 + difference, mPaint);
            }
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 121 / 432 ,height * 31 /64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 121 / 432 ,height * 35 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 121 / 432 ,height * 39 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        if ( (gen_quantity == 2) && (!fil) ){
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_3, mPaint);
            canvas.drawRect(gen2_3, mPaint);
            //рисование элемента перемножения/сложения сигналов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(add_1,mPaint);
            //рисование точек наблюдения сигнала и спектра 3
            canvas.drawCircle(width * 55 / 108, height * 5 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 55 / 108, height * 11 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 83 / 108, height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height * 5 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height * 11 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_1 != 0 && type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 83 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            //провода
            canvas.drawLine(width * 23 / 54, height * 5 / 16 + difference,width * 26 / 54 ,height * 5 / 16 + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 11 / 16 + difference,width * 26 / 54 ,height * 11 / 16 + difference,mPaint);
            canvas.drawLine(width * 29 / 54, height * 5 / 16 + difference,width * 30 / 54 ,height * 5 / 16 + difference,mPaint);
            canvas.drawLine(width * 29 / 54, height * 11 / 16 + difference,width * 30 / 54 ,height * 11 / 16 + difference,mPaint);
            canvas.drawLine(width * 30 / 54, height * 5 / 16 + difference,width * 30 / 54 ,height * 15 / 32 + difference,mPaint);
            canvas.drawLine(width * 30 / 54, height * 11 / 16 + difference,width * 30 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 30 / 54, height * 15 / 32 + difference,width * 32 / 54 ,height * 15 / 32 + difference,mPaint);
            canvas.drawLine(width * 30 / 54, height * 17 / 32 + difference,width * 32 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 37 / 54, height / 2 + difference,width * 40 / 54 ,height / 2 + difference,mPaint);
            //обозначение типа сигнала на генераторе 3
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 7 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 7 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 7 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 7 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 7 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 7 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 7 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 7 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_2 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 13 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 13 / 16 + difference), mPaint);
                }
                if (type_signal_2 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 13 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 13 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 13 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 13 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 13 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 13 / 16 + difference), mPaint);
                    }

                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(height / 20);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 4 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 10 / 16 + difference, mPaint);
            if (add_multi){
                canvas.drawText("MULTI",width * 131 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            else {
                canvas.drawText("ADD",width * 133 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 121 / 432 ,height * 19 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 121 / 432 ,height * 23 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 121 / 432 ,height * 27 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl2 + " V",width * 121 / 432 ,height * 43 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq2 + " kHz",width * 121 / 432 ,height * 47 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset2 + " V",width * 121 / 432 ,height * 51 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        if ( (gen_quantity == 2) && (fil) ){
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_4, mPaint);
            canvas.drawRect(gen2_4, mPaint);
            //рисование элемента перемножения/сложения сигналов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(add_2,mPaint);
            //рисование фильтров
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(fil2,mPaint);
            //рисование точек наблюдения сигнала и спектра 4
            canvas.drawCircle(width * 41 / 108, height * 5 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 41 / 108, height * 11 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 69 / 108, height / 2 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 99 / 108, height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 41 / 108, height * 5 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 41 / 108, height * 11 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_1 != 0 && type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 69 / 108, height / 2 + difference, width / 40, mPaint);
                canvas.drawCircle(width * 99 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            //провода
            canvas.drawLine(width * 16 / 54, height * 5 / 16 + difference,width * 19 / 54 ,height * 5 / 16 + difference,mPaint);
            canvas.drawLine(width * 16 / 54, height * 11 / 16 + difference,width * 19 / 54 ,height * 11 / 16 + difference,mPaint);
            canvas.drawLine(width * 22 / 54, height * 5 / 16 + difference,width * 23 / 54 ,height * 5 / 16 + difference,mPaint);
            canvas.drawLine(width * 22 / 54, height * 11 / 16 + difference,width * 23 / 54 ,height * 11 / 16 + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 5 / 16 + difference,width * 23 / 54 ,height * 15 / 32 + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 11 / 16 + difference,width * 23 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 15 / 32 + difference,width * 25 / 54 ,height * 15 / 32 + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 17 / 32 + difference,width * 25 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 30 / 54, height / 2 + difference,width * 33 / 54 ,height / 2 + difference,mPaint);
            canvas.drawLine(width * 36 / 54, height / 2 + difference,width * 39 / 54 ,height / 2 + difference,mPaint);
            canvas.drawLine(width * 45 / 54, height / 2 + difference,width * 48 / 54 ,height / 2 + difference,mPaint);
            //обозначение типа сигнала на генераторе 4
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 16 / 54), YtoV(Math.sin(j/25),height * 7 / 16 + difference),XtoU((j+1)/25,width * 16 / 54),YtoV(Math.sin((j+1)/25),height * 7 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 7 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(0.7,height * 7 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(-0.7,height * 7 / 16 + difference), XtoU((j+1) / 25,width * 16 / 54), YtoV(-0.7,height * 7 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 7 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(-0.7,height * 7 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_2 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 16 / 54), YtoV(Math.sin(j/25),height * 13 / 16 + difference),XtoU((j+1)/25,width * 16 / 54),YtoV(Math.sin((j+1)/25),height * 13 / 16 + difference), mPaint);
                }
                if (type_signal_2 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 13 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(0.7,height * 13 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(-0.7,height * 13 / 16 + difference), XtoU((j+1) / 25,width * 16 / 54), YtoV(-0.7,height * 13 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 13 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(-0.7,height * 13 / 16 + difference), mPaint);
                    }

                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(height / 20);
            canvas.drawText("FGEN",width * 24 / 108 ,height * 4 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 24 / 108 ,height * 10 / 16 + difference, mPaint);
            if (add_multi){
                canvas.drawText("MULTI",width * 103 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            else {
                canvas.drawText("ADD",width * 105 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            canvas.drawText("filter",width * 163 / 216 ,height * 36 / 64 + difference, mPaint);
            if (filter_name == 0){
                canvas.drawText("LP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 1){
                canvas.drawText("HP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 2){
                canvas.drawText("BP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 3){
                canvas.drawText("notch",width * 323 / 432 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 4){
                canvas.drawText("matched",width * 631 / 864 ,height * 31 / 64 + difference, mPaint);
            }
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 65 / 432 ,height * 19 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 65 / 432 ,height * 23 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 65 / 432 ,height * 27 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl2 + " V",width * 65 / 432 ,height * 43 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq2 + " kHz",width * 65 / 432 ,height * 47 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset2 + " V",width * 65 / 432 ,height * 51 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        if ( (gen_quantity == 3) && (!fil) ){
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_5, mPaint);
            canvas.drawRect(gen2_5, mPaint);
            canvas.drawRect(gen3_5, mPaint);
            //рисование элемента перемножения/сложения сигналов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(add_1,mPaint);
            //рисование точек наблюдения сигнала и спектра 5
            canvas.drawCircle(width * 55 / 108, height * 3 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 55 / 108, height / 2 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 55 / 108, height * 13 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 83 / 108,height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height * 3 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_3 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 55 / 108, height * 13 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_1 != 0 && type_signal_2 != 0 && type_signal_3 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 83 / 108,height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            //соединительные провода
            canvas.drawLine(width * 23 / 54, height * 3 / 16 + difference,width * 26 / 54 ,height * 3 / 16  + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height / 2 + difference,width * 26 / 54 ,height / 2  + difference,mPaint);
            canvas.drawLine(width * 23 / 54, height * 13 / 16 + difference,width * 26 / 54 ,height * 13 / 16 + difference ,mPaint);
            canvas.drawLine(width * 29 / 54,height * 3 / 16 + difference,width * 30 / 54 ,height * 3 / 16 + difference ,mPaint);
            canvas.drawLine(width * 29 / 54,height / 2  + difference,width * 32 / 54 ,height / 2 + difference ,mPaint);
            canvas.drawLine(width * 29 / 54,height * 13 / 16 + difference ,width * 30 / 54,height * 13 / 16  + difference,mPaint);
            canvas.drawLine(width * 30 / 54 ,height * 3 / 16 + difference ,width * 30 / 54  ,height * 15 / 32 + difference ,mPaint);
            canvas.drawLine(width * 30 / 54 ,height * 13 / 16 + difference ,width * 30 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 30 / 54,height * 15 / 32 + difference,width * 32 / 54 ,height * 15 / 32  + difference,mPaint);
            canvas.drawLine(width * 30 / 54,height * 17 / 32 + difference ,width * 32 / 54 ,height * 17 / 32 + difference ,mPaint);
            canvas.drawLine(width * 37 / 54,height / 2  + difference,width * 40 / 54 ,height / 2  + difference,mPaint);
            //обозначение типа сигнала на генераторе 5
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 5 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 5 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 5 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 5 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 5 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 5 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 5 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 5 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_2 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 10 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 10 / 16 + difference), mPaint);
                }
                if (type_signal_2 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_3 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 23 / 54), YtoV(Math.sin(j/25),height * 15 / 16 + difference),XtoU((j+1)/25,width * 23 / 54),YtoV(Math.sin((j+1)/25),height * 15 / 16 + difference), mPaint);
                }
                if (type_signal_3 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 15 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(0.7,height * 15 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(-0.7,height * 15 / 16 + difference), XtoU((j+1) / 25,width * 23 / 54), YtoV(-0.7,height * 15 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 23 / 54), YtoV(0.7,height * 15 / 16 + difference), XtoU((j + 1) / 25,width * 23 / 54), YtoV(-0.7,height * 15 / 16 + difference), mPaint);
                    }
                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setTextSize(height / 20);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 2 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 7 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 38 / 108 ,height * 12 / 16 + difference, mPaint);
            if (add_multi){
                canvas.drawText("MULTI",width * 131 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            else {
                canvas.drawText("ADD",width * 133 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 121 / 432 ,height * 11 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 121 / 432 ,height * 15 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 121 / 432 ,height * 19 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl2 + " V",width * 121 / 432 ,height * 31 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq2 + " kHz",width * 121 / 432 ,height * 35 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset2 + " V",width * 121 / 432 ,height * 39 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl3 + " V",width * 121 / 432 ,height * 51 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq3 + " kHz",width * 121 / 432 ,height * 55 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset3 + " V",width * 121 / 432 ,height * 59 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        if ( (gen_quantity == 3) && (fil) ){
            //рисование генераторов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(gen1_6, mPaint);
            canvas.drawRect(gen2_6, mPaint);
            canvas.drawRect(gen3_6, mPaint);
            //рисование элемента перемножения/сложения сигналов
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(add_2,mPaint);
            //рисование фильтров
            mPaint.setColor(Color.BLACK);
            canvas.drawRect(fil2,mPaint);
            //рисование точек наблюдения сигнала и спектра 6
            canvas.drawCircle(width * 41 / 108, height * 3 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 41 / 108, height / 2 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 41 / 108, height * 13 / 16 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 69 / 108,height / 2 + difference, width / 36, mPaint);
            canvas.drawCircle(width * 99 / 108,height / 2 + difference, width / 36, mPaint);
            if (type_signal_1 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 41 / 108, height * 3 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_2 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 41 / 108, height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_3 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 41 / 108, height * 13 / 16 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            if (type_signal_1 != 0 && type_signal_2 != 0 && type_signal_3 != 0){
                mPaint.setColor(Color.GREEN);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width * 69 / 108,height / 2 + difference, width / 40, mPaint);
                canvas.drawCircle(width * 99 / 108,height / 2 + difference, width / 40, mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setStyle(Paint.Style.STROKE);
            }
            //соединительные провода 6
            canvas.drawLine(width * 16 / 54, height * 3 / 16 + difference,width * 19 / 54 ,height * 3 / 16  + difference,mPaint);
            canvas.drawLine(width * 16 / 54, height / 2 + difference,width * 19 / 54 ,height / 2 + difference ,mPaint);
            canvas.drawLine(width * 16 / 54, height * 13 / 16 + difference,width * 19 / 54 ,height * 13 / 16 + difference ,mPaint);
            canvas.drawLine(width * 22 / 54,height * 3 / 16  + difference,width * 23 / 54 ,height * 3 / 16 + difference ,mPaint);
            canvas.drawLine(width * 22 / 54,height / 2  + difference,width * 25 / 54 ,height / 2  + difference,mPaint);
            canvas.drawLine(width * 22 / 54,height * 13 / 16 + difference ,width * 23 / 54,height * 13 / 16 + difference ,mPaint);
            canvas.drawLine(width * 23 / 54 ,height * 3 / 16  + difference,width * 23 / 54  ,height * 15 / 32 + difference ,mPaint);
            canvas.drawLine(width * 23 / 54 ,height * 13 / 16  + difference,width * 23 / 54 ,height * 17 / 32 + difference,mPaint);
            canvas.drawLine(width * 23 / 54,height * 15 / 32 + difference,width * 25 / 54 ,height * 15 / 32  + difference,mPaint);
            canvas.drawLine(width * 23 / 54,height * 17 / 32 + difference ,width * 25 / 54 ,height * 17 / 32  + difference,mPaint);
            canvas.drawLine(width * 30 / 54,height / 2  + difference,width * 33 / 54 ,height / 2  + difference,mPaint);
            canvas.drawLine(width * 36 / 54,height / 2  + difference,width * 39 / 54 ,height / 2  + difference,mPaint);
            canvas.drawLine(width * 45 / 54,height / 2  + difference,width * 48 / 54 ,height / 2  + difference,mPaint);
            //обозначение типа сигнала на генераторе 6
            for (int j = 0; j < 628; j++) {
                if (type_signal_1 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 16 / 54), YtoV(Math.sin(j/25),height * 5 / 16 + difference),XtoU((j+1)/25,width * 16 / 54),YtoV(Math.sin((j+1)/25),height * 5 / 16 + difference), mPaint);
                }
                if (type_signal_1 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 5 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(0.7,height * 5 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(-0.7,height * 5 / 16 + difference), XtoU((j+1) / 25,width * 16 / 54), YtoV(-0.7,height * 5 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 5 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(-0.7,height * 5 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_2 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 16 / 54), YtoV(Math.sin(j/25),height * 10 / 16 + difference),XtoU((j+1)/25,width * 16 / 54),YtoV(Math.sin((j+1)/25),height * 10 / 16 + difference), mPaint);
                }
                if (type_signal_2 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(0.7,height * 10 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(-0.7,height * 10 / 16 + difference), XtoU((j+1) / 25,width * 16 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 10 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(-0.7,height * 10 / 16 + difference), mPaint);
                    }

                }
                if (type_signal_3 == 1){
                    //синус
                    canvas.drawLine(XtoU(j/25,width * 16 / 54), YtoV(Math.sin(j/25),height * 15 / 16 + difference),XtoU((j+1)/25,width * 16 / 54),YtoV(Math.sin((j+1)/25),height * 15 / 16 + difference), mPaint);
                }
                if (type_signal_3 == 2){
                    //прямоугольный
                    if (j < 314) {
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 15 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(0.7,height * 15 / 16 + difference), mPaint);
                    }
                    else{
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(-0.7,height * 15 / 16 + difference), XtoU((j+1) / 25,width * 16 / 54), YtoV(-0.7,height * 15 / 16 + difference), mPaint);
                    }
                    if (j== 314){
                        canvas.drawLine(XtoU(j / 25,width * 16 / 54), YtoV(0.7,height * 15 / 16 + difference), XtoU((j + 1) / 25,width * 16 / 54), YtoV(-0.7,height * 15 / 16 + difference), mPaint);
                    }
                }
            }
            //текст
            mPaint.setStrokeWidth(0);
            mPaint.setTextSize(height / 20);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText("FGEN",width * 24 / 108 ,height * 2 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 24 / 108 ,height * 7 / 16 + difference, mPaint);
            canvas.drawText("FGEN",width * 24 / 108 ,height * 12 / 16 + difference, mPaint);
            if (add_multi){
                canvas.drawText("MULTI",width * 103 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            else {
                canvas.drawText("ADD",width * 105 / 216 ,height * 33 / 64 + difference, mPaint);
            }
            canvas.drawText("filter",width * 163 / 216 ,height * 36 / 64 + difference, mPaint);
            if (filter_name == 0){
                canvas.drawText("LP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 1){
                canvas.drawText("HP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 2){
                canvas.drawText("BP",width * 165 / 216 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 3){
                canvas.drawText("notch",width * 323 / 432 ,height * 31 / 64 + difference, mPaint);
            }
            if (filter_name == 4){
                canvas.drawText("matched",width * 631 / 864 ,height * 31 / 64 + difference, mPaint);
            }
            mPaint.setTextSize(height / 33);
            canvas.drawText("Amplitude: "+ ampl1 + " V",width * 65 / 432 ,height * 11 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq1 + " kHz",width * 65 / 432 ,height * 15 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset1 + " V",width * 65 / 432 ,height * 19 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl2 + " V",width * 65 / 432 ,height * 31 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq2 + " kHz",width * 65 / 432 ,height * 35 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset2 + " V",width * 65 / 432 ,height * 39 / 64 + difference, mPaint);
            canvas.drawText("Amplitude: "+ ampl3 + " V",width * 65 / 432 ,height * 51 / 64 + difference, mPaint);
            canvas.drawText("Frequency: "+ freq3 + " kHz",width * 65 / 432 ,height * 55 / 64 + difference, mPaint);
            canvas.drawText("Offset: "+ offset3 + " V",width * 65 / 432 ,height * 59 / 64 + difference, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        v2 = findViewById(R.id.v2);

    }

    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX(), y = event.getY();
        int actionMask = event.getActionMasked();
        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
                //добавить генератор
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 8 / 100 + difference && y<= height * 23 / 100 + difference && gen_quantity < 3){
                    gen_quantity = gen_quantity + 1;
                    fil = false;
                    ampl1 = 0; ampl2 = 0; ampl3 = 0; freq1 = 0; freq2 = 0; freq3 = 0; from_freq = 0; before_freq = 0;
                    type_signal_1 = 0; type_signal_2 = 0; type_signal_3 = 0; filter_name = 0;
                }
                //добавить фильтр
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 31 / 100 + difference && y<= height * 46 / 100 + difference){
                    if (gen_quantity != 0){
                        fil = true;
                    }
                }
                //очистить экран
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 54 / 100 + difference && y<= height * 69 / 100 + difference){
                    fil = false;
                    gen_quantity = 0; offset1 = 0; offset2 = 0; offset3 = 0;
                }
                //добавить шум
                LayoutInflater inflater_noise = LayoutInflater.from(getContext());
                View view_noise = inflater_noise.inflate(R.layout.layout_noise, null);
                final EditText noise = (EditText) view_noise.findViewById(R.id.noise);
                final AlertDialog alertDialog_noise = new AlertDialog.Builder(getContext()).setView(view_noise).create();

                if (x >= width / 54 && x <= width * 5 / 54 && y >= height * 77 / 100  + difference&& y <= height * 92 / 100 + difference){
                    noise.setText(String.valueOf(ampl_noise));
                    alertDialog_noise.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_noise = noise.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_noise.equals("")){get_noise = "0.0";}
                            ampl_noise = Double.parseDouble(get_noise);
                            alertDialog_noise.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog_noise.show();
                }


                //переключение между сумматором и перемножителем
                //add1
                if (gen_quantity > 1 && !fil && x >= width * 32 / 54 && x <= width * 37 / 54 && y >= height * 13 / 32 + difference && y <= height * 19 / 32 + difference){
                    add_multi = !add_multi;
                }
                //add2
                if ( gen_quantity > 1 && fil && x >= width * 25 / 54 && x <= width * 30 / 54 && y >= height * 13 / 32 + difference && y <= height * 19 / 32 + difference ){
                    add_multi = !add_multi;
                }
                //настройка генератора
                LayoutInflater inflater_gen = LayoutInflater.from(getContext());
                View view_gen = inflater_gen.inflate(R.layout.layout_fgen,null);
                final EditText Amplitude = (EditText) view_gen.findViewById(R.id.amplitude);
                final EditText Frequency = (EditText) view_gen.findViewById(R.id.frequency);
                final EditText Offset = (EditText) view_gen.findViewById(R.id.offset);
                final Spinner spinner = (Spinner) view_gen.findViewById(R.id.spinner_signal);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Amplitude.setEnabled(false);
                            Frequency.setEnabled(false);
                            Offset.setEnabled(false);
                        }
                        else{
                            Amplitude.setEnabled(true);
                            Frequency.setEnabled(true);
                            Offset.setEnabled(true);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view_gen).create();

                //gen1
                if ( ((gen_quantity == 1) && (!fil) && x >= width * 22 / 54 && x <= width * 30 / 54 && y >= height * 6 / 16 + difference && y <= height * 10 / 16 + difference) || (gen_quantity == 1 && fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height * 6 / 16  + difference&& y <= height * 10 / 16 + difference) || (gen_quantity == 2 && !fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height * 3 / 16 + difference && y <= height * 7 / 16 + difference) || (gen_quantity == 2 && fil && x >= width * 8 / 54 && x <= width * 16 / 54 && y >= height * 3 / 16 + difference && y <= height * 7 / 16 + difference) || (gen_quantity == 3 && !fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height / 16  + difference && y <= height * 5 / 16 + difference) || (gen_quantity == 3 && fil && x >= width * 8 / 54 && x <= width * 16 / 54 && y >= height / 16  + difference && y <= height * 5 / 16 + difference) ){

                    spinner.setSelection(type_signal_1);
                    Amplitude.setText(String.valueOf(ampl1));
                    Frequency.setText(String.valueOf(freq1));
                    Offset.setText(String.valueOf(offset1));

                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_ampl = Amplitude.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_freq = Frequency.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_offset = Offset.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_offset.equals("")){get_offset = "0.0";}
                            if (get_ampl.equals("")){get_ampl = "0.0";}
                            if (get_freq.equals("")){get_freq = "0.0";}
                            ampl1 = Double.parseDouble(get_ampl);
                            freq1 = Double.parseDouble(get_freq);
                            offset1 = Double.parseDouble(get_offset);
                            type_signal_1 = spinner.getSelectedItemPosition();
                            if (ampl1 == 0 || freq1 == 0 || type_signal_1 == 0){
                                type_signal_1 = 0;
                                ampl1 = 0;
                                freq1 = 0;
                                offset1 = 0;
                            }
                            alertDialog.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog.show();
                }
                //gen2
                if( (gen_quantity == 2 && !fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height * 9 / 16 + difference && y <= height * 13 / 16 + difference) || (gen_quantity == 2 && fil && x >= width * 8 / 54 && x <= width * 16 / 54 && y >= height * 9 / 16 + difference && y <= height * 13 / 16 + difference) || (gen_quantity == 3 && !fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height * 6 / 16  + difference&& y <= height * 10 / 16 + difference) || (gen_quantity == 3 && fil && x >= width * 8 / 54 && x <= width * 16 / 54 && y >= height * 6 / 16  + difference&& y <= height * 10 / 16 + difference) ){
                    spinner.setSelection(type_signal_2);
                    Amplitude.setText(String.valueOf(ampl2));
                    Frequency.setText(String.valueOf(freq2));
                    Offset.setText(String.valueOf(offset2));

                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_ampl = Amplitude.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_freq = Frequency.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_offset = Offset.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_offset.equals("")){get_offset = "0.0";}
                            if (get_ampl.equals("")){get_ampl = "0.0";}
                            if (get_freq.equals("")){get_freq = "0.0";}
                            ampl2 = Double.parseDouble(get_ampl);
                            freq2 = Double.parseDouble(get_freq);
                            offset2 = Double.parseDouble(get_offset);
                            type_signal_2 = spinner.getSelectedItemPosition();
                            if (ampl2 == 0 || freq2 == 0 || type_signal_2 == 0){
                                type_signal_2 = 0;
                                ampl2 = 0;
                                freq2 = 0;
                                offset2 = 0;
                            }
                            alertDialog.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog.show();
                }
                //gen3
                if( (gen_quantity == 3 && !fil && x >= width * 15 / 54 && x <= width * 23 / 54 && y >= height * 11 / 16  + difference&& y <= height * 15 / 16 + difference) || (gen_quantity == 3 && fil && x >= width * 8 / 54 && x <= width * 16 / 54 && y >= height * 11 / 16  + difference&& y <= height * 15 / 16 + difference) ){
                    spinner.setSelection(type_signal_3);
                    Amplitude.setText(String.valueOf(ampl3));
                    Frequency.setText(String.valueOf(freq3));
                    Offset.setText(String.valueOf(offset3));

                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_ampl = Amplitude.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_freq = Frequency.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_offset = Offset.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_offset.equals("")){get_offset = "0.0";}
                            if (get_ampl.equals("")){get_ampl = "0.0";}
                            if (get_freq.equals("")){get_freq = "0.0";}
                            ampl3 = Double.parseDouble(get_ampl);
                            freq3 = Double.parseDouble(get_freq);
                            offset3 = Double.parseDouble(get_offset);
                            type_signal_3 = spinner.getSelectedItemPosition();
                            if (ampl3 == 0 || freq3 == 0 || type_signal_3 == 0){
                                type_signal_3 = 0;
                                ampl3 = 0;
                                freq3 = 0;
                                offset3 = 0;
                            }
                            alertDialog.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog.show();
                }
                //выбор типа фильтра и его настройка
                LayoutInflater inflater_fil = LayoutInflater.from(getContext());
                View view_fil = inflater_fil.inflate(R.layout.layout_filter,null);
                final EditText from = (EditText) view_fil.findViewById(R.id.from);
                final EditText before = (EditText) view_fil.findViewById(R.id.before);
                final Spinner spinner_fil = (Spinner) view_fil.findViewById(R.id.spinner_filter);
                spinner_fil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            from.setEnabled(false);
                            before.setEnabled(true);
                            from.setText("");
                        }
                        if (position == 1) {
                            from.setEnabled(true);
                            before.setEnabled(false);
                            before.setText("");
                        }
                        if (position == 2 || position == 3) {
                            from.setEnabled(true);
                            before.setEnabled(true);
                        }
                        if (position == 4){
                            from.setEnabled(false);
                            before.setEnabled(false);
                            from.setText("");
                            before.setText("");
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                final AlertDialog alertDialog_filter = new AlertDialog.Builder(getContext()).setView(view_fil).create();

                if ( (gen_quantity == 1 && fil && x >= width * 32 / 54 && x <= width * 38 / 54 && y >= height * 13 / 32 && y <= height * 19 / 32) || (gen_quantity > 1 && fil && x >= width * 39 / 54 && x <= width * 45 / 54 && y >= height * 13 / 32 && y <= height * 19 / 32) ){
                    spinner_fil.setSelection(filter_name);
                    from.setText(String.valueOf(from_freq));
                    before.setText(String.valueOf(before_freq));

                    alertDialog_filter.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_from = from.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_before = before.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_from.equals("")){get_from = "0.0";}
                            if (get_before.equals("")){get_before = "0.0";}

                            from_freq = Double.parseDouble(get_from);
                            before_freq = Double.parseDouble(get_before);
                            filter_name = spinner_fil.getSelectedItemPosition();
                            alertDialog.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog_filter.show();
                }

//точки просмотра сигнала (здесь переход на активность с построением графиков) + до одного условия возможно?
// с генераторов
                // передать частоту, амплитуду, тип сигнала, шум, количество генераторов
                //gen1
                if( (freq1!=0 && gen_quantity == 1 && !fil && x >= width * 66 / 108  && x <= width * 72 / 108 && y >= height / 2 - width / 36  + difference&& y <= height / 2 + width / 36 + difference) || (freq1!=0 && gen_quantity == 1 && fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) || (freq1!=0 && gen_quantity == 2 && !fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height * 5 / 16 - width / 36 + difference && y <= height * 5 / 16 + width / 36 + difference) || (freq1!=0 && gen_quantity == 2 && fil && x >= width * 38 / 108 && x <= width * 44 / 108 && y >= height * 5 / 16 - width / 36 + difference && y <= height * 5 / 16 + width / 36 + difference) || (freq1!=0 && gen_quantity == 3 && !fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height * 3 / 16 - width / 36  + difference&& y <= height * 3 / 16 + width / 36 + difference) || (freq1!=0 && gen_quantity == 3 && fil && x >= width * 38 / 108 && x <= width * 44 / 108 && y >= height * 3 / 16 - width / 36 + difference && y <= height * 3 / 16 + width / 36 + difference) ){
                    /*frequency_1 = freq1;
                    amplitude_1 = ampl1;
                    type_fgen1 = type_signal_1;
                    amplitude_noise = ampl_noise;
                    where_to_see = 0;*/
                    Intent intent = new Intent(getContext(), Plots.class);
                    intent.putExtra("frequency", freq1);
                    intent.putExtra("amplitude", ampl1);
                    intent.putExtra("type_signal", type_signal_1);
                    intent.putExtra("ampl_noise", ampl_noise);
                    intent.putExtra("where_to_see", 0);
                    intent.putExtra("fgen_quantity", gen_quantity);
                    intent.putExtra("offset", offset1);
                    getContext().startActivity(intent);
                }
                //gen2
                if( (freq2!=0 && gen_quantity == 2 && !fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height * 11 / 16 - width / 36 + difference && y <= height * 11 / 16 + width / 36 + difference) || (freq2!=0 && gen_quantity == 2 && fil && x >= width * 38 / 108 && x <= width * 44 / 108 && y >= height * 11 / 16 - width / 36  + difference&& y <= height * 11 / 16 + width / 36 + difference) || (freq2!=0 && gen_quantity == 3 && !fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height / 2 - width / 36  + difference && y <= height / 2 + width / 36 + difference) || (freq2!=0 && gen_quantity == 3 && fil && x >= width * 38 / 108 && x <= width * 44 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) ){
                    /*frequency_2 = freq2;
                    amplitude_2 = ampl2;
                    type_fgen2 = type_signal_2;
                    amplitude_noise = ampl_noise;
                    where_to_see = 0;*/
                    Intent intent = new Intent(getContext(), Plots.class);
                    intent.putExtra("frequency", freq2);
                    intent.putExtra("amplitude", ampl2);
                    intent.putExtra("type_signal", type_signal_2);
                    intent.putExtra("ampl_noise", ampl_noise);
                    intent.putExtra("where_to_see", 0);
                    intent.putExtra("fgen_quantity", gen_quantity);
                    intent.putExtra("offset", offset2);
                    getContext().startActivity(intent);
                }
                //gen3
                if( (freq3!=0 && gen_quantity == 3 && !fil && x >= width * 52 / 108 && x <= width * 58 / 108 && y >= height * 13 / 16 - width / 36 + difference && y <= height * 13 / 16 + width / 36 + difference) || (freq3!=0 && gen_quantity == 3 && fil && x >= width * 38 / 108 && x <= width * 44 / 108 && y >= height * 13 / 16 - width / 36  + difference&& y <= height * 13 / 16 + width / 36 + difference) ){
                    /*frequency_3 = freq3;
                    amplitude_3 = ampl3;
                    type_fgen3 = type_signal_3;
                    amplitude_noise = ampl_noise;
                    where_to_see = 0;*/
                    Intent intent = new Intent(getContext(), Plots.class);
                    intent.putExtra("frequency", freq3);
                    intent.putExtra("amplitude", ampl3);
                    intent.putExtra("type_signal", type_signal_3);
                    intent.putExtra("ampl_noise", ampl_noise);
                    intent.putExtra("where_to_see", 0);
                    intent.putExtra("fgen_quantity", gen_quantity);
                    intent.putExtra("offset", offset3);
                    getContext().startActivity(intent);
                }
//c сумматоров / перемножителей
                //передать частоту, амплитуду, тип сигнала, шум, кол-во генераторов, сумматор или перемножитель
                if( (freq1!=0 && freq2!=0 && gen_quantity == 2 && !fil && x >= width * 80 / 108 && x <= width * 86 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) || (freq1!=0 && freq2!=0 && gen_quantity == 2 && fil && x >= width * 66 / 108 && x <= width * 72 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) || (freq1!=0 && freq2!=0 && freq3!=0 && gen_quantity == 3 && !fil && x >= width * 80 / 108 && x <= width * 86 / 108 && y >= height / 2 - width / 36  + difference&& y <= height / 2 + width / 36 + difference) || (freq1!=0 && freq2!=0 && freq3!=0 && gen_quantity == 3 && fil && x >= width * 66 / 108 && x <= width * 72 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) ){
                    /*frequency_1 = freq1;
                    amplitude_1 = ampl1;
                    type_fgen1 = type_signal_1;
                    frequency_2 = freq2;
                    amplitude_2 = ampl2;
                    type_fgen2 = type_signal_2;
                    frequency_3 = freq3;
                    amplitude_3 = ampl3;
                    type_fgen3 = type_signal_3;
                    amplitude_noise = ampl_noise;
                    fgen_quantity = gen_quantity;
                    where_to_see = 1;*/
                    if (add_multi){
                        operation = 1;
                    }
                    else {
                        operation = 0;
                    }

                    Intent intent = new Intent(getContext(), Plots.class);
                    intent.putExtra("freq1", freq1);
                    intent.putExtra("freq2", freq2);
                    intent.putExtra("freq3", freq3);
                    intent.putExtra("ampl1", ampl1);
                    intent.putExtra("ampl2", ampl2);
                    intent.putExtra("ampl3", ampl3);
                    intent.putExtra("offset1", offset1);
                    intent.putExtra("offset2", offset2);
                    intent.putExtra("offset3", offset3);
                    intent.putExtra("type_signal1", type_signal_1);
                    intent.putExtra("type_signal2", type_signal_2);
                    intent.putExtra("type_signal3", type_signal_3);
                    intent.putExtra("fgen_quantity", gen_quantity);
                    intent.putExtra("ampl_noise", ampl_noise);
                    intent.putExtra("where_to_see", 1);
                    intent.putExtra("operation", operation);
                    getContext().startActivity(intent);
                }
//с фильтров
                //передать частоту, амплитуду, тип сигнала, шум, кол-во генераторов, сумматор или перемножитель, фильтра
                if( (freq1!=0 && gen_quantity == 1 && fil && x >= width * 82 / 108 && x <= width * 88 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) || (freq1!=0 && freq2!=0 && gen_quantity == 2 && fil && x >= width * 96 / 108 && x <= width * 102 / 108 && y >= height / 2 - width / 36  + difference && y <= height / 2 + width / 36 + difference) || (freq1!=0 && freq2!=0 && freq3!=0 && gen_quantity == 3 && fil && x >= width * 96 / 108 && x <= width * 102 / 108 && y >= height / 2 - width / 36 + difference && y <= height / 2 + width / 36 + difference) ){
                    /*frequency_1 = freq1;
                    amplitude_1 = ampl1;
                    type_fgen1 = type_signal_1;
                    frequency_2 = freq2;
                    amplitude_2 = ampl2;
                    type_fgen2 = type_signal_2;
                    frequency_3 = freq3;
                    amplitude_3 = ampl3;
                    type_fgen3 = type_signal_3;
                    amplitude_noise = ampl_noise;
                    fgen_quantity = gen_quantity;
                    type_filter = filter_name;
                    from_frequency = from_freq;
                    before_frequency = before_freq;
                    where_to_see = 2;*/
                    if (add_multi){
                        operation = 1;
                    }
                    else {
                        operation = 0;
                    }
                    Intent intent = new Intent(getContext(), Plots.class);
                    intent.putExtra("freq1", freq1);
                    intent.putExtra("freq2", freq2);
                    intent.putExtra("freq3", freq3);
                    intent.putExtra("ampl1", ampl1);
                    intent.putExtra("ampl2", ampl2);
                    intent.putExtra("ampl3", ampl3);
                    intent.putExtra("offset1", offset1);
                    intent.putExtra("offset2", offset2);
                    intent.putExtra("offset3", offset3);
                    intent.putExtra("type_signal1", type_signal_1);
                    intent.putExtra("type_signal2", type_signal_2);
                    intent.putExtra("type_signal3", type_signal_3);
                    intent.putExtra("fgen_quantity", gen_quantity);
                    intent.putExtra("ampl_noise", ampl_noise);
                    intent.putExtra("type_filter", filter_name);
                    intent.putExtra("from_frequency", from_freq);
                    intent.putExtra("before_frequency", before_freq);
                    intent.putExtra("where_to_see", 2);
                    intent.putExtra("operation", operation);
                    getContext().startActivity(intent);
                }
        }

        invalidate();
        return true;
    }
}
