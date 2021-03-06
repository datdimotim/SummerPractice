package com.katier.firstStep;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Arrows {

    private static Point moveFinishPoint(Point start, Point finish){
        final double norm=norm(start,finish);
        final double p=1- DraggedPanel.SIZE_OF_BUTTON/2/norm;
        return new Point((int)((finish.x-start.x)*p+start.x),(int)((finish.y-start.y)*p+start.y));
    }

    public static void drawConnectedArrow(Graphics g, Point start, Point finish, int weight, int color){
        drawArrow(g,start,moveFinishPoint(start,finish),weight, color);
    }

    public static void drawFloatingArrow(Graphics g, Point start, Point finish){
        drawArrow(g,start,finish,null,Color.BLACK.getRGB());
    }

    private static Point[] getOrtohonalLine(Point start, Point finish){
        final double size=10;
        final double norm=norm(start,finish);
        Point st=new Point(start.x-finish.x,start.y-finish.y);
        Point l=new Point((int)(st.y/norm*size),(int)(-st.x/norm*size));
        Point r=new Point(-l.x,-l.y);
        Point outL=new Point(l.x+finish.x,l.y+finish.y);
        Point outR=new Point(r.x+finish.x,r.y+finish.y);
        return new Point[]{outL,outR};
    }

    public static void drawParallelArrows(Graphics g, Point a, Point b, int abWeight, int baWeight, int abColor, int baColor){
        Point[] ob=getOrtohonalLine(a,b);
        Point[] oa=getOrtohonalLine(b,a);
        drawConnectedArrow(g,oa[0],ob[1],abWeight,abColor);
        drawConnectedArrow(g,ob[0],oa[1],baWeight,baColor);
    }

    private static void drawArrow(Graphics g, Point start, Point finish, Integer weight, int color){
        if(start.equals(finish))return;
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        g.setColor(new Color(color));
        g.drawLine(start.x,start.y,finish.x,finish.y);
        final double size= 30;
        final double norm=norm(start,finish);
        Point startFictive=new Point((int)((start.x-finish.x)/norm*size)+finish.x,((int)((start.y-finish.y)/norm*size))+finish.y);
        Point center=new Point((startFictive.x+finish.x)/2,(startFictive.y+finish.y)/2);
        Point finishTranslated=new Point(finish.x-center.x,finish.y-center.y);
        Point rp=new Point(finishTranslated.y,-finishTranslated.x);
        Point lp=new Point(-rp.x,-rp.y);
        Point lpTranslated=new Point(lp.x+center.x,lp.y+center.y);
        Point rpTranslated=new Point(rp.x+center.x,rp.y+center.y);
        Polygon polygon=new Polygon(
                new int[]{lpTranslated.x,rpTranslated.x,finish.x},
                new int[]{lpTranslated.y,rpTranslated.y,finish.y},
                3
        );
        g.fillPolygon(polygon);
        if(weight==null)return;
        drawTitle(g,weight,(start.x+finish.x)/2,(start.y+finish.y)/2);
    }

    private static void drawTitle(Graphics g,int title, int x,int y){
        g.setColor(Color.WHITE);
        FontMetrics fm=g.getFontMetrics();
        Rectangle2D r=fm.getStringBounds(Integer.toString(title),g);
        x-=(int)r.getWidth()/2;
        y+=fm.getAscent()/2;
        g.fillRect(x-1,y-fm.getAscent()-1,(int)r.getWidth()+2,(int)r.getHeight()+2);
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(title),x,y);
    }

    private static double norm(Point a, Point b){
        return sqrt(pow(a.x-b.x,2)+pow(a.y-b.y,2));
    }
}
