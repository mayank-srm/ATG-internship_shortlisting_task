package com.mayank.atg_internship;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements View.OnTouchListener{

    ImageView imageView;
    TextView textView;
    EditText editText;
    ImageView el,er;
    String string;
    private RelativeLayout baseLayout;

    private int previousFingerPosition = 0;
    private int baseLayoutPosition = 0;
    private int defaultViewHeight;

    private boolean isClosing = false;
    private boolean isScrollingUp = false;
    private boolean isScrollingDown = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        baseLayout = findViewById(R.id.base_popup_layout);
        baseLayout.setOnTouchListener(this);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.title);

        editText = findViewById(R.id.editText);
        el = findViewById(R.id.edit_left);
        er = findViewById(R.id.edit_right);




        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                el.setVisibility(View.INVISIBLE);
                editText.setPadding(5,0,0,0);


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(i2==0){
                    el.setVisibility(View.VISIBLE);
                    editText.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
              //  editText.setPadding(40,0,0,0);

            }
        });
//        string = editText.getText().toString();
//        if(string==null){
//            el.setVisibility(View.VISIBLE);
//        }

        Intent i=getIntent();
        String name = i.getStringExtra("Title");
        String imgUrl = i.getStringExtra("URL");

        textView.setText(name);
        Picasso.get().load(imgUrl).into(imageView);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View view, MotionEvent event) {

        final int Y = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                defaultViewHeight = baseLayout.getHeight();
                previousFingerPosition = Y;
                baseLayoutPosition = (int) baseLayout.getY();
                break;

            case MotionEvent.ACTION_UP:
                if(isScrollingUp){
                    baseLayout.setY(0);
                    isScrollingUp = false;
                }

                if(isScrollingDown){
                    baseLayout.setY(0);
                    baseLayout.getLayoutParams().height = defaultViewHeight;
                    baseLayout.requestLayout();
                    isScrollingDown = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isClosing){
                    int currentYPosition = (int) baseLayout.getY();
                    if(previousFingerPosition >Y){
                        if(!isScrollingUp){
                            isScrollingUp = true;
                        }

                        if(baseLayout.getHeight()<defaultViewHeight){
                            baseLayout.getLayoutParams().height = baseLayout.getHeight() - (Y - previousFingerPosition);
                            baseLayout.requestLayout();
                        }
                        else {
                            if ((baseLayoutPosition - currentYPosition) > defaultViewHeight / 4) {
                                closeUpAndDismissDialog(currentYPosition);
                                return true;
                            }
                        }
                        baseLayout.setY(baseLayout.getY() + (Y - previousFingerPosition));
                    }
                    else{
                        if(!isScrollingDown){
                            isScrollingDown = true;
                        }
                        if (Math.abs(baseLayoutPosition - currentYPosition) > defaultViewHeight / 2)
                        {
                            closeDownAndDismissDialog(currentYPosition);
                            return true;
                        }

                        baseLayout.setY(baseLayout.getY() + (Y - previousFingerPosition));
                        baseLayout.getLayoutParams().height = baseLayout.getHeight() - (Y - previousFingerPosition);
                        baseLayout.requestLayout();
                    }
                    previousFingerPosition = Y;
                }
                break;
        }
        return true;
    }
    public void closeUpAndDismissDialog(int currentPosition){
        isClosing = true;
        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(baseLayout, "y", currentPosition, -baseLayout.getHeight());
        positionAnimator.setDuration(300);
        positionAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        positionAnimator.start();
    }

    public void closeDownAndDismissDialog(int currentPosition){
        isClosing = true;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        ObjectAnimator positionAnimator = ObjectAnimator.ofFloat(baseLayout, "y", currentPosition, screenHeight+baseLayout.getHeight());
        positionAnimator.setDuration(300);
        positionAnimator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        positionAnimator.start();
    }
}