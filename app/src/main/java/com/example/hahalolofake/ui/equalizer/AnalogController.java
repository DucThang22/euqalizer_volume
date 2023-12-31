package com.example.hahalolofake.ui.equalizer;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Harjot on 23-May-16.
 */
public class AnalogController extends View {

    float midx, midy;
    Paint textPaint, circlePaint, circlePaint2, linePaint;
    String angle;
    float currdeg, deg = 3, downdeg;

    int progressColor, lineColor;

    onProgressChangedListener mListener;

    String label;

    public interface onProgressChangedListener {
        void onProgressChanged(int progress);
    }

    public void setOnProgressChangedListener(onProgressChangedListener listener) {
        mListener = listener;
    }

    public AnalogController(Context context) {
        super(context);
        init();
    }

    public AnalogController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnalogController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(33);
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#222222"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint2 = new Paint();

//        String[] colorsArray = {"#00FFFD", "#FF15FB", "#FF0000"};
//
//        for(int i= 0; i <= colorsArray.length; i++) {
//            circlePaint2.setColor(Color.parseColor(String.valueOf(i)));
//        }

        circlePaint2.setColor(EqualizerFrV2.themeColor);
        circlePaint2.setStyle(Paint.Style.FILL);
        linePaint = new Paint();
        linePaint.setColor(EqualizerFrV2.themeColor);
//        linePaint.setColor(Color.parseColor("#FFA036"));
        linePaint.setStrokeWidth(7);
        angle = "0.0";
//        label = "Label";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        midx = canvas.getWidth() / 2;
        midy = canvas.getHeight() / 2;

        int ang = 0;
        float x = 0, y = 0;
        int radius = (int) (Math.min(midx, midy) * ((float) 14.5 / 16));
        float deg2 = Math.max(3, deg);
        float deg3 = Math.min(deg, 21);

        int startColor = 0xFF00FFFD; // Màu bắt đầu: #00FFFD
        int endColor = 0xFFFF15FB;   // Màu kết thúc: #FF15FB
        int numberOfColors = (int) (deg3 - 3); // Số lượng màu trong dải

//        int[] colors = {Color.parseColor("#FF15FB"), Color.parseColor("#00FFFD")};
//        // Thiết lập điểm bắt đầu và kết thúc của gradient
//        float x0 = 0, y0 = 0, x11 = 0, y11 = getHeight();
//        // Tạo một LinearGradient
//        @SuppressLint("DrawAllocation") Shader shader = new LinearGradient(x0, y0, x11, y11, colors, null, Shader.TileMode.CLAMP);
//        circlePaint2.setShader(shader);

        for (int i = (int) (deg2); i < 22; i++) {
            Log.d("ThangTB", "onDraw deg2: " + deg2);
            float tmp = (float) i / 24;
            x = midx + (float) (radius * Math.sin(2 * Math.PI * (1.0 - tmp)));
            y = midy + (float) (radius * Math.cos(2 * Math.PI * (1.0 - tmp)));
            circlePaint.setColor(Color.parseColor("#524B62"));
            canvas.drawCircle(x, y, ((float) radius / 15), circlePaint);
        }
        for (int i = 3; i <= deg3; i++) {
            Log.d("ThangTB", "onDraw deg3: " + deg3);
            float tmp = (float) i / 24;
            x = midx + (float) (radius * Math.sin(2 * Math.PI * (1.0 - tmp)));
            y = midy + (float) (radius * Math.cos(2 * Math.PI * (1.0 - tmp)));

            float colorPosition = (float) (i - 3) / numberOfColors;
            @SuppressLint("DrawAllocation") int currentColor = (int) new ArgbEvaluator().evaluate(colorPosition, startColor, endColor);

            circlePaint2.setColor(currentColor);
            canvas.drawCircle(x, y, ((float) radius / 15), circlePaint2);
        }

        float tmp2 = deg / 24;
        float x1 = midx + (float) (radius * ((float) 2 / 5) * Math.sin(2 * Math.PI * (1.0 - tmp2)));
        float y1 = midy + (float) (radius * ((float) 2 / 5) * Math.cos(2 * Math.PI * (1.0 - tmp2)));
        float x2 = midx + (float) (radius * ((float) 3 / 5) * Math.sin(2 * Math.PI * (1.0 - tmp2)));
        float y2 = midy + (float) (radius * ((float) 3 / 5) * Math.cos(2 * Math.PI * (1.0 - tmp2)));

        circlePaint.setColor(Color.parseColor("#FBAE0A"));

        canvas.drawCircle(midx, midy, radius * ((float) 13 / 15), circlePaint);
        circlePaint.setColor(Color.parseColor("#73609F"));
        canvas.drawCircle(midx, midy, radius * ((float) 11 / 15), circlePaint);
        canvas.drawText("", midx, midy + (float) (radius * 1.1), textPaint);
        canvas.drawLine(x1, y1, x2, y2, linePaint);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        mListener.onProgressChanged((int) (deg - 2));

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            float dx = e.getX() - midx;
            float dy = e.getY() - midy;
            downdeg = (float) ((Math.atan2(dy, dx) * 180) / Math.PI);
            downdeg -= 90;
            if (downdeg < 0) {
                downdeg += 360;
            }
            downdeg = (float) Math.floor(downdeg / 15);
            return true;
        }
        if (e.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = e.getX() - midx;
            float dy = e.getY() - midy;
            currdeg = (float) ((Math.atan2(dy, dx) * 180) / Math.PI);
            currdeg -= 90;
            if (currdeg < 0) {
                currdeg += 360;
            }
            currdeg = (float) Math.floor(currdeg / 15);

            if (currdeg == 0 && downdeg == 23) {
                deg++;
                if (deg > 21) {
                    deg = 21;
                }
                downdeg = currdeg;
            } else if (currdeg == 23 && downdeg == 0) {
                deg--;
                if (deg < 3) {
                    deg = 3;
                }
                downdeg = currdeg;
            } else {
                deg += (currdeg - downdeg);
                if (deg > 21) {
                    deg = 21;
                }
                if (deg < 3) {
                    deg = 3;
                }
                downdeg = currdeg;
            }

            angle = String.valueOf(deg);
            invalidate();
            return true;
        }
        return e.getAction() == MotionEvent.ACTION_UP || super.onTouchEvent(e);
    }

    public int getProgress() {
        return (int) (deg - 2);
    }

    public void setProgress(int param) {
        deg = param + 2;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String txt) {
        label = txt;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }
}
