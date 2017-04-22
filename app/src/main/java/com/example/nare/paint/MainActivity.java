package com.example.nare.paint;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int CAMERA_REQ = 1997;
    final int GALERY_REQ = 2000;
    Button btnClear, btnUndo, btnblack, btnred, btnblue, btngreen, btngrey, btnyellow,
            btnpen,btnret,btnsize,btnaddphoto,btncolorchose,btnshare;
    MyView myView;
    PopupWindow popupWindow;
    LayoutInflater layoutInflater;
    int prog = 10;
    ProgressBar progressBar;
    boolean isSaved = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        myView = (MyView) findViewById(R.id.myView);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnUndo = (Button) findViewById(R.id.btnUndo);

        btnpen = (Button) findViewById(R.id.pen);
        btnret = (Button) findViewById(R.id.btnret);
        btnsize = (Button) findViewById(R.id.btnsize);
        btnaddphoto = (Button)findViewById(R.id.addphoto);
        btncolorchose = (Button)findViewById(R.id.colorchose);
        btnshare = (Button)findViewById(R.id.share);
        progressBar = (ProgressBar)findViewById(R.id.progbar);



        btnaddphoto.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnUndo.setOnClickListener(this);

        btnpen.setOnClickListener(this);
        btnret.setOnClickListener(this);
        btnsize.setOnClickListener(this);
        btncolorchose.setOnClickListener(this);
        btnshare.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnClear:
                myView.cleanBac();
                break;
            case R.id.btnUndo:
                myView.undo();
                break;
            case R.id.btnret:
                myView.setCurentcolor(Color.WHITE);
                btnpen.setAlpha(1);
                btnret.setAlpha(0.6f);
                break;
            case R.id.pen:
                myView.setCurentcolor(Color.BLACK);
                btnret.setAlpha(1);
                btnpen.setAlpha(0.6f);
                break;
            case R.id.colorchose:
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container1 = (ViewGroup) layoutInflater.inflate(R.layout.color_layout, null);
                popupWindow = new PopupWindow(container1, 500, 70, true);
                popupWindow.showAtLocation(myView, Gravity.NO_GRAVITY, 320, 80);

                btnret.setAlpha(1);
                btnpen.setAlpha(0.6f);

                btnblack = (Button) container1.findViewById(R.id.black);
                btnblue = (Button) container1.findViewById(R.id.blue);
                btnred = (Button) container1.findViewById(R.id.red);
                btngreen = (Button)container1.findViewById(R.id.green);
                btngrey = (Button) container1.findViewById(R.id.gray);
                btnyellow = (Button) container1.findViewById(R.id.yellow);

                btnblack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.BLACK);
                        btncolorchose.setBackgroundResource(R.drawable.black);
                        popupWindow.dismiss();
                    }
                });
                btnblue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.BLUE);
                        btncolorchose.setBackgroundResource(R.drawable.blue);
                        popupWindow.dismiss();
                    }
                });
                btnred.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.RED);
                        btncolorchose.setBackgroundResource(R.drawable.red_button);
                        popupWindow.dismiss();
                    }
                });
                btngreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.GREEN);
                        btncolorchose.setBackgroundResource(R.drawable.green);
                        popupWindow.dismiss();
                    }
                });
                btngrey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.GRAY);
                        btncolorchose.setBackgroundResource(R.drawable.gray);
                        popupWindow.dismiss();
                    }
                });
                btnyellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myView.setCurentcolor(Color.YELLOW);
                        btncolorchose.setBackgroundResource(R.drawable.yellow);
                        popupWindow.dismiss();
                    }
                });


                container1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        popupWindow.dismiss();
                        return true;
                    }
                });
                break;
            case R.id.btnsize:
                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container2 = (ViewGroup) layoutInflater.inflate(R.layout.popup_window, null);


                popupWindow = new PopupWindow(container2, 400, 50, true);
                popupWindow.showAtLocation(myView, Gravity.NO_GRAVITY, 100, 80);

                SeekBar seekBar = (SeekBar)container2.findViewById(R.id.seekbar);
                seekBar.setProgress(prog);


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        prog = progress;
                        myView.setCurentsize(prog);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


                container2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        popupWindow.dismiss();
                        return true;
                    }
                });
                break;
            case R.id.addphoto:
                final CharSequence[] options = {"Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add Photo!!!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (options[which].equals("Take Photo")) {
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent,CAMERA_REQ);
//                        }
                        if (options[which].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent,GALERY_REQ);
                        }
                        else if(options[which].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
                break;
            case R.id.share:
                progressBar.setVisibility(View.VISIBLE);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM,getBitmap());
                startActivity(Intent.createChooser(shareIntent,"Share"));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==CAMERA_REQ && resultCode==RESULT_OK){
//            Bitmap bitmap1 = (Bitmap)data.getExtras().get("data");
//            myView.setBackbit(bitmap1);
//        }
        if (requestCode==GALERY_REQ && resultCode==RESULT_OK){
            Uri selectImage = data.getData();
            String [] filepath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectImage,filepath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filepath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap bitmap = (BitmapFactory.decodeFile(picturePath));

            myView.setBackbit(bitmap);
        }
    }


    private Uri getImageUri(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),bitmap,null,null));

    }
    private Uri getBitmap(){
        if (myView!=null){
            return getImageUri(myView.asBitmap());
        }
        else return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressBar.setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
