package com.pFrame.pwidget;

import com.pFrame.PFrame;
import com.pFrame.PLayout;
import com.pFrame.Position;

public class PHeadWidget extends PWidget {
    public static int WHEN_MODIFY = 0;
    public static int CLOCK = 1;
    private int flashRule = 1;

    PFrame pFrame;

    public PHeadWidget(PWidget parent, Position p, PFrame pFrame) {
        super(parent, p);
        this.changeWidgetSize(pFrame.getFrameWidth(), pFrame.getFrameHeight());
        this.pFrame = pFrame;
        this.pFrame.setHeadWidget(this);
        this.pFrame.setDefaultCloseOperation(PFrame.EXIT_ON_CLOSE);
        this.pFrame.setVisible(true);
        this.pFrame.repaint();
        this.layout = new PLayout(this, null, 1, 1);

    }

    @Override
    public void update() {
        if (flashRule == WHEN_MODIFY)
            this.pFrame.repaint();
    }

    public void setFlashRule(int i) {
        this.flashRule = i;
    }

    public void startRepaintThread() {
        Thread thread = new Thread(pFrame, "window flash thread");
        thread.start();
    }

    @Override
    public void getMouseAndKeyMonitor(PWidget widget) {
        pFrame.setFixedFocus(widget);
    }

    @Override
    public void freeMouseAndKeyMonitor() {
        pFrame.freeFixedFocus();
    }
}