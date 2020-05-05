package edu.sjsu.android.restaurantroller;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;


// Seekbar Design from: https://stackoverflow.com/questions/10722746/add-dynamic-text-over-android-seekbar-thumb

public class ThumbTextSeekBar extends LinearLayout {

    public ThumbTextView tvThumb;
    public SeekBar seekBar;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;

    public ThumbTextSeekBar(Context context) {
        super(context);
        init(null);
    }

    public ThumbTextSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_thumb_text_seekbar, this);
        setOrientation(LinearLayout.VERTICAL);
        tvThumb = (ThumbTextView) findViewById(R.id.tvThumb);
        seekBar = (SeekBar) findViewById(R.id.sbProgress);
        if(attrs != null){
            String seekMax = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "max");
            if(seekMax != null)
                seekBar.setMax(Integer.parseInt(seekMax));
        }
        tvThumb.attachToSeekBar(seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStopTrackingTouch(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onStartTrackingTouch(seekBar);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (onSeekBarChangeListener != null)
                    onSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                tvThumb.attachToSeekBar(seekBar);
            }
        });

    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        this.onSeekBarChangeListener = l;
    }

    public void setThumbText(String text) {
        tvThumb.setText(text);
    }

    public void setProgress(int progress) {
        if (progress == seekBar.getProgress() && progress == 0) {
            seekBar.setProgress(1);
            seekBar.setProgress(0);
        } else {
            seekBar.setProgress(progress);
        }
    }
    public int getProgress(){
        return seekBar.getProgress();
    }
}