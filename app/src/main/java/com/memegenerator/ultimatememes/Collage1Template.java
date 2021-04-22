package com.memegenerator.ultimatememes;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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


public class Collage1Template extends Fragment {

    private EditText mEditTextBody;
    private Button mBtnPreview;
    private Button mBtnSave;
    private CardView mCardViewScreen;
    private ImageView mImageViewUploaded1;
    private ImageView mImageViewUploaded2;
    private TextView mTextViewBody;
    private View mRoot;
    private FloatingActionButton mFab_collage1;
    private int LOADIMG1_RESULT = 1;
    private int LOADIMG2_RESULT = 2;

    public Collage1Template() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         mRoot = inflater.inflate(R.layout.fragment_collage1_template, container, false);
        //Get the views
         GetViews();

         //make the fab disappear
         mFab_collage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab_collage1.setVisibility(mRoot.GONE);


                        Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent1,LOADIMG1_RESULT);

                        startActivityForResult(intent1,LOADIMG2_RESULT);


            }
         });


        //save the screenshot
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = screenShot(mCardViewScreen);
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
            mImageViewUploaded1.setImageBitmap(BitmapFactory.decodeFile(imagePath));


        }

        if(LOADIMG2_RESULT == requestCode && resultCode == RESULT_OK && null != data){


            Uri selectedImg = data.getData();
            String[] filePathCol = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImg,filePathCol,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathCol[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            mImageViewUploaded2.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            mImageViewUploaded1.setBackgroundColor(Color.WHITE);
            mImageViewUploaded2.setBackgroundColor(Color.WHITE);

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

        mEditTextBody = mRoot.findViewById(R.id.editText_body_collage1);
        mBtnSave = mRoot.findViewById(R.id.button_save_collage1);
        mCardViewScreen = mRoot.findViewById(R.id.cardview_collage1);
        mImageViewUploaded1 = mRoot.findViewById(R.id.imageView_uploaded1_collage1);
        mImageViewUploaded2 = mRoot.findViewById(R.id.imageView_uploaded2_collage1);
        mTextViewBody = mRoot.findViewById(R.id.textView_body_collage1);
        mFab_collage1 = mRoot.findViewById(R.id.fab_collage1);

    }
}
