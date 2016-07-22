package in.showoffs.mobidb.custom;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Gaurav Ravi on 7/22/2016.
 */

public class ConstrainedImageView extends SimpleDraweeView {
    public ConstrainedImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public ConstrainedImageView(Context context) {
        super(context);
    }

    public ConstrainedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConstrainedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ConstrainedImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension(this.getMeasuredWidth(), (int)(1.5 * (double)this.getMeasuredWidth()));
    }
}
