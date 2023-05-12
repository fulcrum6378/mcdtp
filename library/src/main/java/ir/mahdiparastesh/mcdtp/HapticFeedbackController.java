package ir.mahdiparastesh.mcdtp;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.provider.Settings;

public class HapticFeedbackController {
    private static final int VIBRATE_DELAY_MS = 125;
    private static final int VIBRATE_LENGTH_MS = 50;

    private static boolean checkGlobalSetting(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.HAPTIC_FEEDBACK_ENABLED, 0) == 1; // FIXME DEPRECATED
    }

    private final Context mContext;
    private final ContentObserver mContentObserver;

    private boolean mIsGloballyEnabled;
    private long mLastVibrate;

    public HapticFeedbackController(Context context) {
        mContext = context;
        mContentObserver = new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                mIsGloballyEnabled = checkGlobalSetting(mContext);
            }
        };
    }

    public void start() {
        mIsGloballyEnabled = checkGlobalSetting(mContext);
        Uri uri = Settings.System.getUriFor(Settings.System.HAPTIC_FEEDBACK_ENABLED);
        mContext.getContentResolver().registerContentObserver(uri, false, mContentObserver);
    }

    public void stop() {
        mContext.getContentResolver().unregisterContentObserver(mContentObserver);
    }

    public void tryVibrate() {
        if (!mIsGloballyEnabled) return;
        long now = SystemClock.uptimeMillis();
        if (now - mLastVibrate >= VIBRATE_DELAY_MS) {
            Vibrator vib;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                vib = ((VibratorManager) mContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE))
                        .getDefaultVibrator();
            else vib = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                vib.vibrate(VibrationEffect
                        .createOneShot(VIBRATE_LENGTH_MS, VibrationEffect.DEFAULT_AMPLITUDE));
            else vib.vibrate(VIBRATE_LENGTH_MS);

            mLastVibrate = now;
        }
    }
}
