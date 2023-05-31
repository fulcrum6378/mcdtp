package ir.mahdiparastesh.mcdtp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewWithCircularIndicator extends AppCompatTextView {

    private static final int SELECTED_CIRCLE_ALPHA = 255;

    final Paint mCirclePaint = new Paint();

    private final String mItemIsSelectedText;

    private boolean mDrawCircle;

    public TextViewWithCircularIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mItemIsSelectedText = context.getResources().getString(R.string.item_is_selected);
        init();
    }

    private void init() {
        mCirclePaint.setFakeBoldText(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(McdtpUtils.themeColor(getContext(),
                com.google.android.material.R.attr.colorOnSurface));
        mCirclePaint.setTextAlign(Align.CENTER);
        mCirclePaint.setStyle(Style.FILL);
        mCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);
    }

    public void drawIndicator(boolean drawCircle) {
        mDrawCircle = drawCircle;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        if (mDrawCircle) {
            final int width = getWidth();
            final int height = getHeight();
            int radius = Math.min(width, height) / 2;
            canvas.drawCircle(width / 2f, height / 2f, radius, mCirclePaint);
        }
        setSelected(mDrawCircle);
        super.onDraw(canvas);
    }

    @SuppressLint("GetContentDescriptionOverride")
    @Override
    public CharSequence getContentDescription() {
        CharSequence itemText = getText();
        if (mDrawCircle)
            return String.format(mItemIsSelectedText, itemText);
        else return itemText;
    }
}
