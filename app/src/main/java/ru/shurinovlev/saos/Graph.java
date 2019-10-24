package ru.shurinovlev.saos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class Graph extends View {

    float difference, proportion, height, width;

    Paint mPaint = new Paint();
    RectF btn1, btn2, btn3, btn4;
    float us1, vs1, us2, vs2, uf1, vf1, uf2, vf2;
    int N = 8192;

    //диапазон отображения сигнала от -1 до 20,
    double xs1 = -1.0, ys1 = 0, xs2 = 21.0, ys2 = 0, t_limit = 20.0;
    //диапазон отображения энергетического спектра
    double xf1 = -10.0, yf1 = 0, xf2 = 204.8, yf2 = 0;



    double frequency = Plots.frequency;
    double amplitude = Plots.amplitude;
    double ampl_noise = Plots.ampl_noise;
    double from_freq = Plots.from_freq;
    double before_freq = Plots.before_freq;
    double offset = Plots.offset;

    double o[] = Plots.o;
    double a[] = Plots.a;
    double f[] = Plots.f;

    int type_signal = Plots.type_signal;
    int where_to_see = Plots.where_to_see;
    int operation = Plots.operation;
    int type_s[] = Plots.type_s;
    int fgen_quantity = Plots.fgen_quantity;
    int type_filter = Plots.type_filter;
    Bitmap export= Plots.export;
    Bitmap help = Plots.help;

    double dt = t_limit / N;
    double s[] = new double[N];
    double s_for_k[] = new double[N];
    double fr[] = new double[N];
    double ampl[] = new double[N];
    Complex[] ampl_f, ampl_f_clear;

    public Graph(Context context) {
        super(context);

    }
    public Graph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public Graph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public Graph(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // перевод мировой ск в экранную ск (сигнал)
    int XtoU_s(double x) {
        return (int) ((us2 - us1) * (x - xs1) / (xs2 - xs1) + us1);
    }
    int YtoV_s(double y) {
        return (int) ((vs2 - vs1) * (y - ys1) / (ys2 - ys1) + vs1);
    }
    // перевод мировой ск в экранную ск (спектр)
    int XtoU_f(double x) {
        return (int) ((uf2 - uf1) * (x - xf1) / (xf2 - xf1) + uf1);
    }
    int YtoV_f(double y) {
        return (int) ((vf2 - vf1) * (y - yf1) / (yf2 - yf1) + vf1);
    }
    // 0  - это фаза
    double signal(int type , int n, double ampl, double freq, double s_offset){
        if (type == 1){
            return ampl * Math.sin(2 * Math.PI * freq * (n * dt) + 0) + s_offset;
        }
        else {
            return ampl * Math.signum(Math.sin( 2 * Math.PI * freq * n * dt + 0)) + s_offset;
        }
    }
    double noise(int n) {return ampl_noise * Math.random() * Math.sin(2 * Math.PI * 15 * Math.random() + 1.0);}
    //double cor_funq (int n, double func, double noise) {return (func + noise) * Math.sin(20 * n * dt - t_limit);}

    @SuppressLint("DefaultLocale")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));

        mPaint.setColor(getResources().getColor(R.color.mycolor));
        canvas.drawRect(0,0, width, height, mPaint);
        mPaint.setTextSize(height/40);
        float width_line = height / 200;
        us1 = width / 9; vs1 = 0; us2 = width * 10 / 18; vs2 = height;
        uf1 = width * 10 / 18 ; vf1 = 0; uf2 = width; vf2 = height;
        float strokeWidth = mPaint.getStrokeWidth();
        mPaint.setStrokeWidth(width_line);
        mPaint.setColor(Color.BLACK);
        canvas.drawLine(width / 9, 0, width / 9, height, mPaint);
        proportion = width/height/2;
        difference = (height - height * proportion)/2;
        height = height * proportion;


        //координаты элементов панели
        btn1 = new RectF(width / 54, height * 8 / 100 + difference, width * 5 / 54, height * 23 / 100 + difference);
        btn2 = new RectF(width / 54, height * 31 / 100 + difference,width * 5 / 54, height * 46 / 100 + difference);
        btn3 = new RectF(width / 54, height * 54 / 100 + difference, width * 5 / 54, height * 69 / 100 + difference);
        btn4 = new RectF(width / 54, height * 77 / 100 + difference, width * 5 / 54, height * 92 / 100 + difference);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setAntiAlias(true);
        //отрисовка панели с кномпками
        mPaint.setColor(Color.BLACK);

        canvas.drawRect(btn1, mPaint);
        canvas.drawRect(btn2, mPaint);
        canvas.drawRect(btn3, mPaint);
        canvas.drawRect(btn4, mPaint);
        canvas.drawBitmap(export,null,btn3, mPaint);
        canvas.drawBitmap(help,null,btn4, mPaint);


        //текст для кнопок
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(height / 25);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("range t",width * 11 / 432 ,height * 33 / 200 + difference, mPaint);
        canvas.drawText("range f",width * 11 / 432 ,height * 79 / 200 + difference, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);




        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setTextSize(height/40);


        //сигнал и спектр с генератора
        Complex complex_s[] = new Complex[N];
        Complex complex_s_for_k[] = new Complex[N];

        if (where_to_see == 0){
            if (ampl_noise != 0){
                for (int i = 0; i < N; i++){
                    s[i] = signal(type_signal, i, amplitude, frequency, offset) + noise(i);
                    complex_s[i] = new Complex(s[i]);
                }
                Complex[] Ampl_f = FFT.fft(complex_s);
                for (int i = 0; i < N ; i++){
                    ampl[i] =  Ampl_f[i].abs()/N;
                    fr[i] = i / t_limit;
                }
            }
            else {
                for (int i = 0; i < N; i++){
                    s[i] = signal(type_signal, i, amplitude, frequency, offset);
                    complex_s[i] = new Complex(s[i]);
                }
                Complex[] Ampl_f = FFT.fft(complex_s);
                for (int i = 0; i < N ; i++){
                    ampl[i] =  Ampl_f[i].abs()/N;
                    fr[i] = i / t_limit;
                }
            }
        }
        //сигнал и спектр с сумматора/перемножителя
        if (where_to_see == 1){
            if (fgen_quantity == 2){
                if (operation == 0){
                    if(ampl_noise != 0){
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + noise(i) ;
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                }
                else {
                    if(ampl_noise != 0){
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) + noise(i) ;
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                }
            }
            else {
                if (operation == 0){
                    if(ampl_noise != 0){
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]) + noise(i) ;
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                }
                else {
                    if(ampl_noise != 0){
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]) + noise(i) ;
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        Complex[] Ampl_f = FFT.fft(complex_s);
                        for (int i = 0; i < N ; i++){
                            ampl[i] =  Ampl_f[i].abs()/N;
                            fr[i] = i / t_limit;
                        }
                    }
                }
            }
        }
        //сигнал и спектр с фильтра
        if (where_to_see == 2){
            if (fgen_quantity == 1){
                if (ampl_noise != 0){
                    if (type_filter != 4){
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_signal, i, amplitude, frequency, offset) + noise(i);
                            complex_s[i] = new Complex(s[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                    }
                    else {
                        for (int i = 0; i < N; i++){

                            s[i] = signal(type_signal, i, amplitude, frequency, offset) + noise(i);
                            s_for_k[i] = signal(type_signal, i, amplitude, frequency, offset);
                            complex_s[i] = new Complex(s[i]);
                            complex_s_for_k[i] = new Complex(s_for_k[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                        ampl_f_clear = FFT.fft(complex_s_for_k);
                    }
                }
                else {
                    for (int i = 0; i < N; i++){
                        s[i] = signal(type_signal, i, amplitude, frequency, offset);
                        complex_s[i] = new Complex(s[i]);
                    }
                    ampl_f = FFT.fft(complex_s);
                    ampl_f_clear = ampl_f;
                }
            }
            if (fgen_quantity == 2){
                if (operation == 0){
                    if (ampl_noise != 0){
                        if (type_filter != 4){
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + noise(i);
                                complex_s[i] = new Complex(s[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                        }
                        else {
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + noise(i);
                                s_for_k[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]);
                                complex_s[i] = new Complex(s[i]);
                                complex_s_for_k[i] = new Complex(s_for_k[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                            ampl_f_clear = FFT.fft(complex_s_for_k);
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                        ampl_f_clear = ampl_f;
                    }
                }
                else {
                    if (ampl_noise != 0){
                        if (type_filter != 4){
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) + noise(i);
                                complex_s[i] = new Complex(s[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                        }
                        else {
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) + noise(i);
                                s_for_k[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]);
                                complex_s[i] = new Complex(s[i]);
                                complex_s_for_k[i] = new Complex(s_for_k[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                            ampl_f_clear = FFT.fft(complex_s_for_k);
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                        ampl_f_clear = ampl_f;
                    }
                }
            }
            if (fgen_quantity == 3){
                if (operation == 0){
                    if (ampl_noise != 0){
                        if (type_filter != 4){
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]) + noise(i);
                                complex_s[i] = new Complex(s[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                        }
                        else {
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]) + noise(i);
                                s_for_k[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]);
                                complex_s[i] = new Complex(s[i]);
                                complex_s_for_k[i] = new Complex(s_for_k[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                            ampl_f_clear = FFT.fft(complex_s_for_k);
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) + signal(type_s[1], i, a[1], f[1], o[1]) + signal(type_s[2], i, a[2], f[2], o[2]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                        ampl_f_clear = ampl_f;
                    }
                }
                else {
                    if (ampl_noise != 0){
                        if (type_filter != 4){
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]) + noise(i);
                                complex_s[i] = new Complex(s[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                        }
                        else {
                            for (int i = 0; i < N; i++){
                                s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]) + noise(i);
                                s_for_k[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]);
                                complex_s[i] = new Complex(s[i]);
                                complex_s_for_k[i] = new Complex(s_for_k[i]);
                            }
                            ampl_f = FFT.fft(complex_s);
                            ampl_f_clear = FFT.fft(complex_s_for_k);
                        }
                    }
                    else {
                        for (int i = 0; i < N; i++){
                            s[i] = signal(type_s[0], i, a[0], f[0], o[0]) * signal(type_s[1], i, a[1], f[1], o[1]) * signal(type_s[2], i, a[2], f[2], o[2]);
                            complex_s[i] = new Complex(s[i]);
                        }
                        ampl_f = FFT.fft(complex_s);
                        ampl_f_clear = ampl_f;
                    }
                }
            }

            //фнч
            if (type_filter == 0) {
                int k = 0;
                while ((int) (before_freq * 20 + k) <= N / 2) {
                    ampl_f[(int) (before_freq * 20 + k)] = new Complex (0 , 0);
                    ampl_f[(int) (N - before_freq * 20 - k)] = new Complex (0 , 0);
                    k++;
                }
                Complex[] ampl_s = FFT.inverse_fft(ampl_f);
                for (int n = 0; n < N; n++) {
                    s[n] = ampl_s[n].div(new Complex(N, 0)).Re();
                    ampl[n] =  ampl_f[n].abs() / N;
                    fr[n] = n / t_limit;
                }
            }
            //фвч
            if (type_filter == 1) {
                int k = 0;
                while (k < (int) (from_freq  * 20  + 1) && (int) (from_freq  * 20  + 1) <= N/2 ) {
                    ampl_f[k] = new Complex(0,0);
                    ampl_f[N - k - 1] = new Complex(0,0);
                    k++;
                }
                Complex[] ampl_s = FFT.inverse_fft(ampl_f);
                for (int n = 0; n < N; n++) {
                    s[n] = ampl_s[n].div(new Complex(N, 0)).Re();
                    ampl[n] =  ampl_f[n].abs() / N;
                    fr[n] = n / t_limit;
                }
            }
            //режекторный
            if (type_filter == 3) {
                int k = (int) (from_freq  * 20);
                while (k < (int) (before_freq  * 20  + 1) && (int) (before_freq  * 20  + 1) <= N / 2) {
                    ampl_f[k] = new Complex(0,0);
                    ampl_f[N - k] = new Complex(0,0);
                    k++;
                }
                Complex[] ampl_s = FFT.inverse_fft(ampl_f);
                for (int n = 0; n < N; n++) {
                    s[n] = ampl_s[n].div(new Complex(N, 0)).Re();
                    ampl[n] =  ampl_f[n].abs() / N;
                    fr[n] = n / t_limit;
                }
            }
            //полосовой
            if (type_filter == 2) {

                int k = 0;
                while (k < (int) (from_freq  * 20 + 1) && (int) (from_freq  * 20 + 1) <= N/2 ) {
                    ampl_f[k] = new Complex(0,0);
                    ampl_f[N - k - 1] = new Complex(0,0);
                    k++;
                }
                k = 0;
                while ((int) (before_freq  * 20 + k) <= N / 2) {
                    ampl_f[(int) (before_freq  * 20 + k)] = new Complex(0,0);
                    ampl_f[(int) (N - before_freq  * 20 - k)] = new Complex(0,0);
                    k++;
                }
                Complex[] ampl_s = FFT.inverse_fft(ampl_f);
                for (int n = 0; n < N; n++) {
                    s[n] = ampl_s[n].div(new Complex(N, 0)).Re();
                    ampl[n] =  ampl_f[n].abs() / N;
                    fr[n] = n / t_limit;
                }

            }
            //согласованный
            if (type_filter == 4) {
                Complex Ampl_f_before_filter[] = new Complex[N];
                for (int n = 0; n < N; n++) {

                    Ampl_f_before_filter[n] = new Complex(Math.cos(-2 * Math.PI * n * dt), Math.sin(-2 * Math.PI * n * dt)).mul(ampl_f[n]).mul(ampl_f_clear[n].conj());
                }
                Complex[] Ampl_func_before_filter = FFT.inverse_fft(Ampl_f_before_filter);
                for (int n = 0; n < N; n++) {
                    s[n] = Ampl_func_before_filter[n].Re()/N/N;
                    ampl[n] = Ampl_f_before_filter[n].abs()/N/N;
                    fr[n] = n / t_limit;
                }
            }
        }


        //автомасштабирование по у
        for (int i = 0; i < N; i++){
            if (ys1 < Math.abs(s[i])){
                ys1 = 1.1 * Math.abs(s[i]);
                ys2 = -ys1;
            }
            if (yf1 < ampl[i]){
                yf1 = 1.1 * ampl[i];
                yf2 = - yf1 / 10;
            }
        }




        //рисуем сигнал и спектр
        for (int i = 0; i < N-1; i++){

            if (XtoU_s(i * t_limit / N) > us1 && XtoU_s((i+1) * t_limit / N) < us2){
                mPaint.setColor(Color.BLUE);
                canvas.drawLine(XtoU_s(i * t_limit / N), YtoV_s(s[i]),XtoU_s((i+1) * t_limit / N),YtoV_s(s[i+1]),mPaint);
            }
            if (XtoU_f(fr[i]) > uf1 && fr[i] < uf2){
                mPaint.setColor(Color.RED);
                canvas.drawLine(XtoU_f(fr[i]), YtoV_f(ampl[i]), XtoU_f(fr[i]), YtoV_f(0), mPaint);
            }

        }


        //ниже все связанное с рисованием и подписью осей

        //сигнал
        mPaint.setColor(Color.BLACK);
        //ось х
        if( ys1 * ys2 <0){
            canvas.drawLine(us1,YtoV_s(0.0),us2,YtoV_s(0.0),mPaint);
        }
        //ось у
        if (xs1 * xs2 <0){
            canvas.drawLine(XtoU_s(0.0),vs2,XtoU_s(0.0),vs1, mPaint);
        }

        //подпись осей
        mPaint.setStyle(Paint.Style.FILL);
        if (ys1 < 1 || type_filter == 4 || ys1 > 10){
            canvas.drawText(String.format("%.1f", ys1/1.1 ), XtoU_s(-(xs2 - xs1) / 40), YtoV_s(ys1/1.1), mPaint);
            canvas.drawLine(XtoU_s(-(xs2 - xs1) / 70), YtoV_s(ys1/1.1), XtoU_s((xs2 - xs1) / 70), YtoV_s(ys1/1.1), mPaint);
            canvas.drawText(String.format("%.1f", ys2/1.1 ), XtoU_s(-(xs2 - xs1) / 40), YtoV_s(ys2/1.1), mPaint);
            canvas.drawLine(XtoU_s(-(xs2 - xs1) / 70), YtoV_s(ys2/1.1), XtoU_s((xs2 - xs1) / 70), YtoV_s(ys2/1.1), mPaint);
        }
        else {
            for (int i= (int) ys2; i<ys1;i++){
                if(i == 0){}
                else {
                    canvas.drawText(String.valueOf(i), XtoU_s(-(xs2 - xs1) / 40), YtoV_s(i), mPaint);
                    canvas.drawLine(XtoU_s(-(xs2 - xs1) / 70), YtoV_s(i), XtoU_s((xs2 - xs1) / 70), YtoV_s(i), mPaint);
                }
            }
        }

        for (int i= (int) xs1; i < xs2; i++){
            canvas.drawText(String.valueOf(i), XtoU_s(i), YtoV_s((ys2-ys1)/20), mPaint);
            canvas.drawLine(XtoU_s(i), YtoV_s(-(ys2-ys1)/70), XtoU_s(i), YtoV_s((ys2-ys1)/70), mPaint);
        }

        //энергетический спектр
        //ось х
        if( yf1 * yf2 <0){
            canvas.drawLine(uf1,YtoV_f(0.0),uf2,YtoV_f(0.0),mPaint);
        }
        //ось у
        canvas.drawLine(XtoU_f(xf1+0.01),vf2,XtoU_f(xf1+0.01),vf1, mPaint);

        double x11=(xf2-xf1)/70;
        double y11=(yf2-yf1)/70;
        // подпись осей
        for (int i = 1; i < 10; i++){
            canvas.drawText(String.valueOf( String.format("%.1f", i * yf1 / 10 ) ), XtoU_f(xf1+ x11), YtoV_f(i * yf1 / 10), mPaint);
            canvas.drawLine(XtoU_f(xf1), YtoV_f(i * yf1 / 10), XtoU_f(xf1 + x11), YtoV_f(i * yf1 / 10), mPaint);
        }
        for (int i = (int) xf1; i < xf2; ) {
            canvas.drawText(String.valueOf(i), XtoU_f(i), YtoV_f((yf2 - yf1) / 20), mPaint);
            canvas.drawLine(XtoU_f(i), YtoV_f(-y11), XtoU_f(i), YtoV_f(y11), mPaint);
            if ((int) (xf2-xf1)/10 == 0){
                i++;
            }
            else i+=(int) (xf2-xf1)/10;

        }
        if (xf1 < 0) {
            canvas.drawText(String.valueOf(0), XtoU_f(0), YtoV_f((yf2 - yf1) / 20), mPaint);
            canvas.drawLine(XtoU_f(0), YtoV_f(-y11), XtoU_f(0), YtoV_f(y11), mPaint);
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        float  first_x, first_y, second_x, second_y;
        float w = getWidth(), h = getHeight();
        float x = event.getX(), y = event.getY();
        int upPI = 0;
        int downPI = 0;
        boolean inTouch = false;

        int actionMask = event.getActionMasked();
        // индекс касания
        int pointerIndex = event.getActionIndex();
        // число касаний
        int pointerCount = event.getPointerCount();

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN: // первое касание
                LayoutInflater scale_t = LayoutInflater.from(getContext());
                View view_t = scale_t.inflate(R.layout.scale_t, null);
                final EditText from_t = (EditText) view_t.findViewById(R.id.t_from);
                final EditText before_t = (EditText) view_t.findViewById(R.id.t_before);
                final AlertDialog alertDialog_t = new AlertDialog.Builder(getContext()).setView(view_t).create();

                LayoutInflater scale_f = LayoutInflater.from(getContext());
                View view_f = scale_f.inflate(R.layout.scale_f, null);
                final EditText from_f = (EditText) view_f.findViewById(R.id.fr_from);
                final EditText before_f = (EditText) view_f.findViewById(R.id.fr_before);
                final AlertDialog alertDialog_f = new AlertDialog.Builder(getContext()).setView(view_f).create();

                LayoutInflater help = LayoutInflater.from(getContext());
                View view_help = help.inflate(R.layout.help, null);
                final AlertDialog alertDialog_help = new AlertDialog.Builder(getContext()).setView(view_help).create();

                //диапазон t
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 8 / 100 + difference && y <= height * 23 / 100 + difference){
                    final Toast[] error = new Toast[1];
                    from_t.setText(String.valueOf(xs1));
                    before_t.setText(String.valueOf(xs2));
                    alertDialog_t.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_from = from_t.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_before = before_t.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_from.equals("")|| Double.parseDouble(get_from) > Double.parseDouble(get_before) ){get_from = String.valueOf(xs1);}
                            if (get_before.equals("")|| Double.parseDouble(get_from) > Double.parseDouble(get_before)){get_before = String.valueOf(xs2);}
                            xs2 = Double.parseDouble(get_before);
                            xs1 = Double.parseDouble(get_from);
                            if (xs2 > 21 || xs1 < -1 ){
                                xs2 = 21;
                                xs1 = -1;
                                error[0] = Toast.makeText(getContext(),"Отображаемый диапазон до 22 мкс!", Toast.LENGTH_SHORT);

                            }
                            alertDialog_t.dismiss();
                            invalidate();
                            if (error[0] != null){
                                error[0].show();
                            }
                        }
                    });
                    alertDialog_t.show();
                }
                //диапазон f
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 31 / 100 + difference && y<= height * 46 / 100 + difference){
                    from_f.setText(String.valueOf(xf1));
                    before_f.setText(String.valueOf(xf2));
                    alertDialog_f.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String get_from = from_f.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            String get_before = before_f.getText().toString().replaceAll("\\s", "").replaceAll(",",".");
                            if (get_from.equals("") || Double.parseDouble(get_from) > Double.parseDouble(get_before)){get_from = String.valueOf(xf1);}
                            if (get_before.equals("") || Double.parseDouble(get_from) > Double.parseDouble(get_before)){get_before = String.valueOf(xf2);}
                            xf2 = Double.parseDouble(get_before);
                            xf1 = Double.parseDouble(get_from);
                            alertDialog_f.dismiss();
                            invalidate();
                        }
                    });
                    alertDialog_f.show();
                }
                //сохранить скриншот
                if ( x >= width / 54 && x <= width * 5 / 54 && y >= height * 54 / 100 + difference && y<= height * 69 / 100 + difference){
                    java.util.Date now = new Date();
                    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
                    try {
                        String mPath1 = Environment.getExternalStorageDirectory().toString() + "/SAoS/" +"plots - " + now + ".jpg";
                        String mPath2 = Environment.getExternalStorageDirectory().toString() + "/SAoS/" +"circuit - " + now + ".jpg";

                        View v1 = findViewById(R.id.v1);
                        View v2 = UIDraw.v2;


                        File imageFile1 = new File(mPath1);
                        File imageFile2 = new File(mPath2);
                        FileOutputStream outputStream1 = new FileOutputStream(imageFile1);
                        FileOutputStream outputStream2 = new FileOutputStream(imageFile2);
                        takeScreenshot(v1, Bitmap.Config.ARGB_8888).compress(Bitmap.CompressFormat.JPEG, 100, outputStream1);
                        outputStream1.flush();
                        outputStream1.close();
                        takeScreenshot(v2, Bitmap.Config.ARGB_8888).compress(Bitmap.CompressFormat.JPEG, 100, outputStream2);
                        outputStream2.flush();
                        outputStream2.close();
                        Toast.makeText(getContext(),"Скриншоты сохранены в директории SAoS", Toast.LENGTH_SHORT).show();


                    } catch (Throwable e) {
                        Toast.makeText(getContext(),"Директория SAoS не найдена!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
                //помощь
                if (x >= w / 54 && x <= w * 5 / 54 && y >= h * 77 / 100 && y <= h * 92 / 100){

                    alertDialog_help.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            alertDialog_help.dismiss();

                        }
                    });
                    alertDialog_help.show();

                }

        }
        //invalidate();
        return true;
    }
    public static Bitmap takeScreenshot(View view, Bitmap.Config quality) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), quality);
        Canvas canvas = new Canvas(bitmap);

        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable!= null) {
            backgroundDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);

        return bitmap;
    }
}
