package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

public class PaintActivity extends AppCompatActivity {
    private FrameLayout frame;
    private PaintView paintView;
    private Button undo, fillbtn;
    private int mDefaultColor;
    private int countSaved=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        frame = findViewById(R.id.frm);
        fillbtn=(Button) findViewById(R.id.fill);
        paintView = new PaintView(this);
        frame.addView(paintView);
        undo=(Button) findViewById(R.id.btnPoint);
        mDefaultColor= ContextCompat.getColor(PaintActivity.this,R.color.design_default_color_primary);


        undo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                paintView.undoPath();
                return false;
            }
        });
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.undo();
            }
        });

    }
    public void openColorPicker(){
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) { }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor=color;
                String hexColor = String.format("#%06X", (0xFFFFFF & mDefaultColor));
                paintView.setColor(hexColor);
            }
        });
        colorPicker.show();
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
        if(id==R.id.actionDelMax)
            paintView.DelBig();
        if(id==R.id.actionSave){
            Toast toast=null;
            paintView.setDrawingCacheEnabled(true);
            String imgSaved= MediaStore.Images.Media.insertImage(getContentResolver(),paintView.getDrawingCache(), "drawing_"+countSaved+".png","drawing");
            if(imgSaved!=null){
               toast=Toast.makeText(this,"Saving has been Successful ",Toast.LENGTH_SHORT);
               countSaved++;
            }
            else{
                toast=Toast.makeText(this,"Saving has been Unsuccessful ",Toast.LENGTH_SHORT);
            }
            toast.show();
            paintView.destroyDrawingCache();

        }
        return true;
    }

    public void addLine(View view) {
        paintView.addLine();
    }
    public void addRect(View view) {
        paintView.addRect();
    }
    public void addPath(View view) {
        paintView.addPath();
    }
    public void addCircle(View view) {
        paintView.addCircle();
    }

    public void changeColor(View view)
    {
        String color = view.getTag().toString();
        paintView.setColor(color);
    }

    public void clear(View view) {
        paintView.undo();
    }
    public void WidthPlus(View view){   paintView.WidthPlus();
        Toast.makeText(this,"width is now "+paintView.GetWidth(),Toast.LENGTH_SHORT).show();}
    public void WidthMinus(View view){   paintView.WidthMin();
        Toast.makeText(this,"width is now "+paintView.GetWidth(),Toast.LENGTH_SHORT).show();}
    public void FillAct(View view){   paintView.SetFill();
        if(paintView.GetFill()){
            Toast.makeText(this,"Fill is ON",Toast.LENGTH_SHORT).show();
            fillbtn.setText("Fill - ON");
        }
        else
        {
            Toast.makeText(this,"Fill is OFF",Toast.LENGTH_SHORT).show();
            fillbtn.setText("Fill - OFF");
        }}


}
