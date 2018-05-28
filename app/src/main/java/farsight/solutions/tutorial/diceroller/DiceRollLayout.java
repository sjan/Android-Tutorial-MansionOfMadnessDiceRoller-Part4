package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;

public class DiceRollLayout extends RelativeLayout {
    private MainPresenter presenter;

    private static final int ONE_SECOND = 1000;
    private static final int TWO_SECONDS = 2000;
    private static final int FULL_REVOLUTION = 360;
    private static final int THREE_REVOLUTION = 1080;
    private static final float CENTER = 0.5f;

    Random random = new Random();

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


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void render(List<Dice> diceList) {
        //count check
        if(presenter!=null) {
            int diceCount = diceList.size();

            //remove dice
            while (diceCount < getChildCount()) {
                this.removeViewAt(getChildCount() - 1);
            }

            //add dice
            while (getChildCount() < diceCount) {
                final int index = getChildCount();

                //initial layout parameters
                final DiceRollLayout.LayoutParams layoutParams = new DiceRollLayout.LayoutParams(
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

                                parms.leftMargin = boundifyX(x-dx);
                                parms.topMargin = boundifyY(y-dy);

                                if(inHoldZone(parms)) {
                                    presenter.diceInZone(index, Zone.HOLD);

                                    if(diceInLeftFlipZone(parms)) {
                                        presenter.diceInZone(index, Zone.SWITCH_BLANK);
                                    } else if(diceInRightFlipZone(parms)) {
                                        presenter.diceInZone(index, Zone.SWITCH_MAGNIFY);
                                    } else if(diceInCenterFlipZone(parms)) {
                                        presenter.diceInZone(index, Zone.SWITCH_STAR);
                                    }
                                } else {
                                    presenter.diceInZone(index, Zone.ROLL);
                                }

                                view.setLayoutParams(parms);
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

    public void spinDice(int diceIndex) {

        //roll all dice that are not being held
        DiceView diceView = (DiceView) this.getChildAt(diceIndex);
        diceView.refreshDiceFace();

        if (diceView != null) {
            int rotation = randomRotation();
            int duration = randomDuration();

            RotateAnimation rotate = new RotateAnimation(
                    0, rotation,
                    Animation.RELATIVE_TO_SELF, CENTER,
                    Animation.RELATIVE_TO_SELF, CENTER
            );
            rotate.setFillAfter(true);
            rotate.setFillEnabled(true);
            rotate.setDuration(duration);
            rotate.setInterpolator(new DecelerateInterpolator());

            diceView.startAnimation(rotate);
        }

    }

    private int randomDuration() {
        return ONE_SECOND + random.nextInt(TWO_SECONDS);
    }

    private int randomRotation() {
        return FULL_REVOLUTION + random.nextInt(THREE_REVOLUTION);
    }

    private boolean inHoldZone(RelativeLayout.LayoutParams param) {
        int holdAreaHeight =
            getResources().getDimensionPixelSize(R.dimen.hold_area_height) +
                getResources().getDimensionPixelSize(R.dimen.change_area_height);
        if(param.topMargin > (getHeight() - holdAreaHeight)) {
            return true;
        }
        return false;
    }

    private boolean diceInLeftFlipZone(RelativeLayout.LayoutParams param) {
        int changeAreaHeight  =
                getResources().getDimensionPixelSize(R.dimen.change_area_height);
        int changeAreaWidth = getWidth()/3;

        int diceCenterLeft =
                param.leftMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;
        int diceCenterTop =
                param.topMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;

        if(diceCenterTop > (getHeight() - changeAreaHeight )&&
                diceCenterLeft < changeAreaWidth) {
            return true;
        }
        return false;
    }

    private boolean diceInCenterFlipZone(RelativeLayout.LayoutParams param) {
        int changeAreaHeight  =
            getResources().getDimensionPixelSize(R.dimen.change_area_height);
        int changeAreaWidth = getWidth()/3;

        int diceCenterLeft =
                param.leftMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;
        int diceCenterTop =
                param.topMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;

        if(diceCenterTop > (getHeight() - changeAreaHeight )&&
            diceCenterLeft > changeAreaWidth &&
            diceCenterLeft < changeAreaWidth*2) {
            return true;
        }
        return false;
    }

    private boolean diceInRightFlipZone(RelativeLayout.LayoutParams param) {
        int changeAreaHeight  =
                getResources().getDimensionPixelSize(R.dimen.change_area_height);
        int changeAreaWidth = getWidth()/3;

        int diceCenterLeft =
                param.leftMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;
        int diceCenterTop =
                param.topMargin + getResources().getDimensionPixelSize(R.dimen.dice_size)/2;

        if(diceCenterTop > (getHeight() - changeAreaHeight )&&
                diceCenterLeft > changeAreaWidth*2) {
            return true;
        }
        return false;
    }

    private int boundifyX(float left) {
        return Math.min(
                Math.max(0, (int)left),
                getWidth()-getResources().getDimensionPixelSize(R.dimen.dice_size));
    }

    private int boundifyY(float top) {
        return Math.min(
                Math.max(0, (int)top),
                getHeight()-getResources().getDimensionPixelSize(R.dimen.dice_size));
    }
}
