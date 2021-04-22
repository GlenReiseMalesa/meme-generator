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

public class Classic2Template extends Fragment {

    private ImageView mImageViewUploadAndUploaded;
    private TextView mTextViewLabel;
    private TextView mTextViewBody;
    private Button mBtnSave;
    private Button mBtnPreview;
    private EditText mEditTextLabel;
    private EditText mEditTextBody;
    private View mRoot;
    private FloatingActionButton mFab_classic2;
    private int LOADIMG_RESULT = 1;

    public Classic2Template() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_classic2_template, container, false);
        //Get and initialize the views
        GetViews();

        //make the fab disappear
        mFab_classic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab_classic2.setVisibility(mRoot.GONE);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,LOADIMG_RESULT);

            }
        });

        //save the image
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = screenShot(mRoot.findViewById(R.id.cardview_classic2));
                save(bitmap,"UltimateMemes" + System.currentTimeMillis() + ".jpg");
            }
        });


        //set body text while still texting
        mEditTextBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    if((s.length() == 1) || (s.length() == 156)
                    ){
                        Toast.makeText(getContext(), "You are limited to 156 characters!", Toast.LENGTH_SHORT).show();
                    }
                    mTextViewBody.setText(s.toString());
                }

                if (s.length() == 0){
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

        if(LOADIMG_RESULT == requestCode && resultCode == RESULT_OK && data != null){

            Uri selectedImg = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContext().getContentResolver().query(selectedImg,filePathColumn,null,null,null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();

            mImageViewUploadAndUploaded.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            mImageViewUploadAndUploaded.setBackgroundColor(Color.WHITE);

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
        File fileDirectory = new File(directoryPath);

        if (!fileDirectory.exists()){
            fileDirectory.mkdir();
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
        mEditTextBody = mRoot.findViewById(R.id.editText_body_classic2);
        mEditTextLabel = mRoot.findViewById(R.id.editText_label_classic2);
        mBtnSave = mRoot.findViewById(R.id.button_save_classic2);
        mTextViewBody = mRoot.findViewById(R.id.textView_body_classic2);
        mTextViewLabel = mRoot.findViewById(R.id.textView_label_classic2);
        mImageViewUploadAndUploaded = mRoot.findViewById(R.id.imageView_uploaded1_classic2);
        mFab_classic2 = mRoot.findViewById(R.id.fab_classic2);
    }
}
