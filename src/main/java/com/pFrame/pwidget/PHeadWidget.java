package com.pFrame.pwidget;

import com.pFrame.Position;

public class PHeadWidget extends PWidget {

    PFrame pFrame;

    public PHeadWidget(PWidget parent, Position p, PFrame pFrame) {
        super(parent, p);
        this.changeWidgetSize(pFrame.getFrameWidth(), pFrame.getFrameHeight());
        this.pFrame = pFrame;
        this.pFrame.setHeadWidget(this);
        this.pFrame.setVisible(true);
        this.pFrame.repaint();
        this.layout = new PLayout(this, null, 1, 1);
        this.layout.changeWidgetSize(this.widgetWidth,this.widgetHeight);
        this.layout.setPosition(Position.getPosition(0,0));
    }


    public void startRepaintThread() {
        Thread thread = new Thread(pFrame, "window flash thread");
        thread.start();
    }
}