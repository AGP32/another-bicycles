package agp32.dev;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static android.view.View.OnTouchListener;

@NoArgsConstructor
public abstract class OnPressListener implements OnTouchListener {

    public OnPressListener(long minPressTime) {
        this.minPressTime = minPressTime;
    }

    /**
     * Determine minimum time you need to press
     * on view for this listener to handle event.
     */
    @Getter @Setter private long minPressTime = 1000;

    private boolean isDown;
    private long actionUpTime;
    private long actionDownTime;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            isDown = true;
            actionDownTime = event.getEventTime();
        }
        else if(isDown && event.getAction() == MotionEvent.ACTION_UP) {
            isDown = false;
            actionUpTime = event.getEventTime();

            if((actionUpTime-actionDownTime) >= minPressTime) {
                float x = event.getX();
                float y = event.getY();
                onPress(v, new PointF(x, y));
                return false;
            }
        }
        return true;
    }

    public abstract void onPress(View v, PointF coordinates);
}
