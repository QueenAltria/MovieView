package com.jp.movieview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 2017/4/17
 */
public class LocusPassView extends View {
    public static final String TAG = "LocusPassView";

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
    PointF mPoints[][] = new PointF[3][3];
    private List<Integer> pathNodes = new ArrayList<Integer>();
    private float centerRadius; //每个实心点的半径
    private float circleRadius; //空心圆半径
    private float viewWidth;
    private float viewHeight;
    private float curX = 0;
    private float curY = 0;


    private OnCompleteListener onCompleteListener = null;


    public LocusPassView(Context context) {
        super(context);
    }

    public LocusPassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocusPassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_MOVE){
            curX=event.getX();
            curY=event.getY();
            detectGetPoint(curX,curY);
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            if(pathNodes.size()>=3){
                if(null != onCompleteListener) {
                    onCompleteListener.onComplete(pathToString(pathNodes));
                }
            }else {
                pathNodes.clear();
            }
        }
        this.postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewWidth=this.getMeasuredWidth();
        viewHeight=this.getMeasuredHeight();
        centerRadius = viewWidth / 24;
        circleRadius = viewWidth / 6 * 3 / 5;

        drawPoints(canvas);
        drawLines(canvas, curX, curY);
    }

    public void drawPoints(Canvas canvas)
    {
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                mPoints[i][j] = new PointF((int)(viewWidth / 6 + viewWidth / 3 * j),
                        (int)(viewHeight / 6 + viewHeight / 3 * i));
                canvas.drawCircle(mPoints[i][j].x, mPoints[i][j].y, centerRadius, mPaint);

            }
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(centerRadius / 6);
        for(int i = 0; i < pathNodes.size(); i++)
        {
            int m = pathNodes.get(i) / 3;
            int n = pathNodes.get(i) % 3;
            canvas.drawCircle(mPoints[m][n].x, mPoints[m][n].y, circleRadius, mPaint);
        }
    }

    public void drawLines(Canvas canvas, float curX, float curY)
    {
        mPaint.setStrokeWidth(centerRadius / 2);
        PointF lastPointF = null;
        for(int i = 0; i < pathNodes.size(); i++)
        {
            int m = pathNodes.get(i) / 3;
            int n = pathNodes.get(i) % 3;
            PointF curPointF  = mPoints[m][n];
            if(null != lastPointF)
            {
                canvas.drawLine(lastPointF.x, lastPointF.y, curPointF.x, curPointF.y, mPaint);
            }
            lastPointF = curPointF;
        }
        if(null != lastPointF)
        {
            canvas.drawLine(lastPointF.x, lastPointF.y, curX, curY, mPaint);
        }
    }

    private void detectGetPoint(float x, float y) {
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if((mPoints[i][j].x-x)*(mPoints[i][j].x-x)
                        +(mPoints[i][j].y-y)*(mPoints[i][j].y-y)
                        <centerRadius*centerRadius*4){
                        /*触点进入某一中心点范围, 半径平方 乘以4 触点更大  容易操作*/
                    int nodeNum =  i * 3 + j;
                    if(!pathNodes.contains(nodeNum)) {
                        pathNodes.add(nodeNum);
                    }
                    return;
                }
            }
        }
    }

    public String pathToString(List<Integer> list){
        String des="";
        for(int i = 0; i < list.size(); i++) {
            des += list.get(i).toString();
        }
        return des;
    }

    //设置完成事件监听回调
    public void setOnCompleteListener(OnCompleteListener o) {
        this.onCompleteListener = o;
    }

    public interface OnCompleteListener {
        public void onComplete(String pass);
    }
}
