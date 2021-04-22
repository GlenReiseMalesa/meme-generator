package com.memegenerator.ultimatememes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;

import static android.app.Activity.RESULT_OK;


public class Collage5Template extends Fragment {

    private ImageView mImageViewUploadAndUploaded1;
    private ImageView mImageViewUploadAndUploaded2;
    private ImageView mImageViewUploadAndUploaded3;
    private TextView mTextViewLabel;
    private TextView mTextViewBody;
    private Button mBtnSave;
    private Button mBtnPreview;
    private EditText mEditTextLabel;
    private EditText mEditTextBody;
    private View mRoot;
    private FloatingActionButton mFab_collage5;
    private int LOADIMG1_RESULT = 1;
    private int LOADIMG2_RESULT = 2;
    private int LOADIMG3_RESULT = 3;

    public Collage5Template() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_collage5_template, container, false);


        //Get and initialize the views
        GetViews();

        mFab_collage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab_collage5.setVisibility(mRoot.GONE);
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent1,LOADIMG1_RESULT);

                startActivityForResult(intent1,LOADIMG2_RESULT);

                startActivityForResult(intent1,LOADIMG3_RESULT);
            }
        });

        //save the screenshot
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = screenShot(mRoot.findViewById(R.id.cardview_collage5));
                save(bitmap,"UltimateMemes" + System.currentTimeMillis() + ".jpg");
            }
        });

        //set text while still typing
        mEditTextBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    if((s.length() == 1) || (s.length() == 156)
                    ){
                        Toast.makeText(getContext(), "You are limited to 156 characters!", Toast.LENGTH_SHORT).show();
                    }
                    mTextViewBody.setText(s.toString());
                }

                if (s.length() == 0 ){
                    mTextViewBody.setText("");
                }
            }
        });

        //set label text while still typing
        mEditTextLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() > 0){
                    if((s.length() == 1) || (s.length() == 14)
                    ){
                        Toast.makeText(getContext(), "You are limited to 14 characters!", Toast.LENGTH_SHORT).show();
                    }
                    mTextViewLabel.setText("@"+s.toString());
                }

                if(s.length() == 0){
                    mTextViewLabel.setText("");
                }
            }
        });

        return mRoot;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(LOADIMG1_RESULT == requestCode && resultCode == RESULT_OK && null != data){




            Uri selectedImg = data.getData();
            String[] filePathCol = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImg,filePathCol,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathCol[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            mImageViewUploadAndUploaded1.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        }

        if(LOADIMG2_RESULT == requestCode && resultCode == RESULT_OK && null != data){


            Uri selectedImg = data.getData();
            String[] filePathCol = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImg,filePathCol,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathCol[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            mImageViewUploadAndUploaded2.setImageBitmap(BitmapFactory.decodeFile(imagePath));

        }

        if(LOADIMG3_RESULT == requestCode && resultCode == RESULT_OK && null != data){


            Uri selectedImg = data.getData();
            String[] filePathCol = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImg,filePathCol,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathCol[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            mImageViewUploadAndUploaded3.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            mImageViewUploadAndUploaded1.setBackgroundColor(Color.WHITE);
            mImageViewUploadAndUploaded2.setBackgroundColor(Color.WHITE);
            mImageViewUploadAndUploaded3.setBackgroundColor(Color.WHITE);


        }
    }

    public static Bitmap screenShot(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void save(Bitmap bitmap,String fileName){

        String directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/UltimateMemes";
        File directory = new File(directoryPath);
        if (!directory.exists()){
            directory.mkdir();
        }

        File file = new File(directoryPath,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
            Toast.makeText(getContext(),"saved!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getContext(),"failed to save!",Toast.LENGTH_LONG).show();
        }
    }


    private void GetViews() {
        mEditTextBody = mRoot.findViewById(R.id.editText_body_collage5);
        mEditTextLabel = mRoot.findViewById(R.id.editText_label_collage5);
        mBtnSave = mRoot.findViewById(R.id.button_save_collage5);
        mTextViewBody = mRoot.findViewById(R.id.textView_body_collage5);
        mTextViewLabel = mRoot.findViewById(R.id.textView_label_collage5);
        mImageViewUploadAndUploaded1 = mRoot.findViewById(R.id.imageView_uploaded1_collage5);
        mImageViewUploadAndUploaded2 = mRoot.findViewById(R.id.imageView_uploaded2_collage5);
        mImageViewUploadAndUploaded3 = mRoot.findViewById(R.id.imageView_uploaded3_collage5);
        mFab_collage5 = mRoot.findViewById(R.id.fab_collage5);
    }
}
