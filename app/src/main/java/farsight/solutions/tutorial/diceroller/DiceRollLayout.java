package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

public class DiceRollLayout extends RelativeLayout {
    final static String TAG = DiceRollLayout.class.getName();

    MainPresenter presenter;

    public DiceRollLayout(Context context) {
        super(context);
    }

    public DiceRollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiceRollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    public void render() {
        //count check
        if(presenter!=null) {
            List<Dice> diceList = presenter.getDiceList();

            int diceCount = diceList.size();

            //remove dice
            while (diceCount < getChildCount()) {
                this.removeViewAt(getChildCount() - 1);
            }

            //add dice
            while (getChildCount() < diceCount) {
                final int index = getChildCount();

                //initial layout parameters
                DiceRollLayout.LayoutParams layoutParams = new DiceRollLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                int diceViewSize = getResources().getDimensionPixelSize(R.dimen.dice_size);
                layoutParams.setMarginStart((index%5)*diceViewSize );
                layoutParams.topMargin = ((index/5)*diceViewSize );

                //initialize DiceView
                DiceView diceView = new DiceView(getContext(), null, 0);

                diceView.setDice(diceList.get(index));
                diceView.setLayoutParams(layoutParams);
                diceView.setBorderColor(ContextCompat.getColor(getContext(), R.color.diceSelectionBorderColor));
                diceView.setBorderOverlay(true);
                diceView.setBorderWidth(0);

                //touch drag listener
                diceView.setOnTouchListener(new View.OnTouchListener() {
                    RelativeLayout.LayoutParams parms;
                    float dx=0,dy=0,x=0,y=0;

                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        switch(event.getAction())
                        {
                            case MotionEvent.ACTION_DOWN :
                            {
                                parms = (RelativeLayout.LayoutParams) view.getLayoutParams();
                                dx = event.getRawX() - parms.leftMargin;
                                dy = event.getRawY() - parms.topMargin;
                            }
                            break;
                            case MotionEvent.ACTION_MOVE :
                            {
                                x = event.getRawX();
                                y = event.getRawY();
                                parms.leftMargin = (int) (x-dx);
                                parms.topMargin = (int) (y-dy);
                                view.setLayoutParams(parms);
                            }
                            break;
                            case MotionEvent.ACTION_UP :
                            {
                                presenter.selectIndex(index);
                            }
                            break;
                        }
                        return true;
                    }
                });

                this.addView(diceView);
            }

            //refresh dice face
            for (int i = 0; i < getChildCount(); i++) {
                DiceView diceView = (DiceView) getChildAt(i);
                diceView.refreshDiceFace();
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if(presenter!=null) {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP: {
                            presenter.unselectIndex();
                        }
                        break;
                    }
                    return true;
                }
            });
        }
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
