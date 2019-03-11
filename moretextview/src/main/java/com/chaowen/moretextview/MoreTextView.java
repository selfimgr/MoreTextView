package com.chaowen.moretextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;


import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;





@SuppressLint("AppCompatCustomView")
public class MoreTextView extends TextView {

    private static final String TAG = MoreTextView.class.getName();

    private int showingLine = 1;
    private int showingChar;
    private boolean isCharEnable;

    private String showMore = "Show more";
    private String showLess = "Show less";
    private String dotdot = "...";

    private int MAGIC_NUMBER = 5;

    private int showMoreTextColor = Color.RED;
    private int showLessTextColor = Color.RED;

    private String mainText;

    public MoreTextView(Context context) {
        super(context);
    }

    public MoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainText = getText().toString();
    }

    private void addShowMore() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                String text = getText().toString();
                String showingText = "";
                if (isCharEnable) {
                    if (showingChar >= text.length()) {
                        try {
                            throw new Exception("显示的字符数不能超过总字符数");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    String newText = text.substring(0, showingChar);
                    newText += dotdot + showMore;
                    setText(newText);
                    Log.d(TAG, "Text: " + newText);
                } else {

                    if (showingLine >= getLineCount()) {
                        try {
                            throw new Exception("显示行数不能超过总行数");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        return;
                    }
                    int start = 0;
                    int end;
                    for (int i = 0; i < showingLine; i++) {
                        end = getLayout().getLineEnd(i);
                        showingText += text.substring(start, end);
                        start = end;
                    }

                    String newText = showingText.substring(0, showingText.length() - (dotdot.length() + showMore.length() + MAGIC_NUMBER));
                    Log.d(TAG, "Text: " + newText);
                    Log.d(TAG, "Text: " + showingText);
                    newText += dotdot + showMore;

                    setText(newText);
                }

                setShowMoreColoringAndClickable();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });


    }

    private void setShowMoreColoringAndClickable() {
        final SpannableString spannableString = new SpannableString(getText());

        Log.d(TAG, "Text: " + getText());
        spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        ds.setUnderlineText(false);
                                    }

                                    @Override
                                    public void onClick( View view) {
                                        setMaxLines(Integer.MAX_VALUE);
                                        setText(mainText);
                                        showLessButton();
                                        Log.d(TAG, "被点击了 ");

                                    }
                                },
                getText().length() - (dotdot.length() + showMore.length()),
                getText().length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(showMoreTextColor),
                getText().length() - (dotdot.length() + showMore.length()),
                getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setMovementMethod(LinkMovementMethod.getInstance());
        setText(spannableString, BufferType.SPANNABLE);
    }

    private void showLessButton() {

        String text = getText() + dotdot + showLess;
        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new ClickableSpan() {
                                    @Override
                                    public void updateDrawState(TextPaint ds) {
                                        ds.setUnderlineText(false);
                                    }

                                    @Override
                                    public void onClick( View view) {

                                        setMaxLines(showingLine);

                                        addShowMore();

                                        Log.d(TAG, "被点击了");

                                    }
                                },
                text.length() - (dotdot.length() + showLess.length()),
                text.length(), 0);

        spannableString.setSpan(new ForegroundColorSpan(showLessTextColor),
                text.length() - (dotdot.length() + showLess.length()),
                text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        setMovementMethod(LinkMovementMethod.getInstance());
        setText(spannableString, BufferType.SPANNABLE);
    }




    /**
     * 可以添加至少显示的行号以显示折叠文本
     *
     * @param lineNumber int
     */
    public void setShowingLine(int lineNumber) {
        if (lineNumber == 0) {
            try {
                throw new Exception("行数不能是0");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        isCharEnable = false;

        showingLine = lineNumber;

        setMaxLines(showingLine);


        addShowMore();
    }

    /**
     * 可以限制文本的字符数限制显示
     *
     * @param character int
     */
    public void setShowingChar(int character) {
        if (character == 0) {
            try {
                throw new Exception("Character length cannot be 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        isCharEnable = true;
        this.showingChar = character;

        addShowMore();
    }

    /**
     *  展开文字
     *
     * @param text String
     */
    public void addShowMoreText(String text) {
        showMore = text;
    }

    /**
     *  收起文字
     *
     * @param text String
     */
    public void addShowLessText(String text) {
        showLess = text;
    }

    /**
     *  展开文字的颜色
     *
     * @param color Integer
     */
    public void setShowMoreColor(int color) {
        showMoreTextColor = color;
    }

    /**
     *  收起文字的颜色
     *
     * @param color Integer
     */
    public void setShowLessTextColor(int color) {
        showLessTextColor = color;
    }
}
