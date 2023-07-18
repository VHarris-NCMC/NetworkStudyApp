package net.shadyoakdesign.netplus;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class InitialsDrawable extends Drawable {
    private static final int BACKGROUND_COLOR = Color.parseColor("#C7C7C7");

    private final GoogleSignInAccount account;
    private final TextPaint textPaint;
    private final Paint backgroundPaint;
    private final Rect textBounds;
    private Context mContext;

    public InitialsDrawable(GoogleSignInAccount account, Context context) {
        this.account = account;
        mContext = context;
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(BACKGROUND_COLOR);

        textBounds = new Rect();
    }

    @Override
    public void draw(Canvas canvas) {
        int width = getBounds().width();
        int height = getBounds().height();

        // Draw the circular background
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, backgroundPaint);

        // Draw the initials or profile picture inside the circle
        if (account != null && account.getPhotoUrl() != null) {
            Bitmap profileBitmap = getProfilePictureBitmap();
            if (profileBitmap != null) {
                // Scale and draw the profile picture
                float scale = (float) width / profileBitmap.getWidth();
                int scaledHeight = (int) (profileBitmap.getHeight() * scale);
                Rect destRect = new Rect(0, 0, width, scaledHeight);
                canvas.drawBitmap(profileBitmap, null, destRect, null);
            } else {
                drawInitials(canvas, width, height);
            }
        } else {
            drawInitials(canvas, width, height);
        }
    }

    private void drawInitials(Canvas canvas, int width, int height) {
        String initials = getInitials();
        textPaint.setTextSize(width / 2.5f);
        textPaint.getTextBounds(initials, 0, initials.length(), textBounds);
        float x = width / 2f;
        float y = height / 2f - textBounds.centerY();
        canvas.drawText(initials, x, y, textPaint);
    }

    private String getInitials() {
        String displayName = account.getDisplayName();
        if (displayName != null && !displayName.isEmpty()) {
            String[] nameParts = displayName.split("\\s+");
            StringBuilder initialsBuilder = new StringBuilder();
            for (String part : nameParts) {
                if (!part.isEmpty() && Character.isLetter(part.charAt(0))) {
                    initialsBuilder.append(Character.toUpperCase(part.charAt(0)));
                }
            }
            return initialsBuilder.toString();
        }
        return "";
    }

    private Bitmap getProfilePictureBitmap() {
        String photoUrl = account.getPhotoUrl().toString();
        try {
            return Glide.with(mContext)
                    .asBitmap()
                    .load(photoUrl)
                    .submit()
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void setAlpha(int alpha) {
        // Not implemented
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // Not implemented
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
