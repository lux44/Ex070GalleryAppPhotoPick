package com.lux.ex070galleryappphotopick;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(view -> {
            //갤러리 앱 또는 사진 앱을 실행하기
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");   //MIME 타입

            //이렇게 실행하면 사진앱이 실행은 되지만 선택한 결과를 받을 수 없다.
            //startActivity(intent);
            //startActivityForResult(intent,10); //deprecated

            //Android 10버전 이후 방법
            //결과를 받기 위해 새로운 액티비티를 실행시켜 주는 객체에게 요청해야 함.
            resultLauncher.launch(intent);
        });
    }
    //결과를 받기 위해 새로운 액티비티를 실행시켜 주는 객체 등록 및 생성
    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //파라미터 result : 결과를 가지고 온 객체

            //갤러리나 사진 앱에서 사진을 선택하고 돌아왔는지부터 확인해야 함.
            if (result.getResultCode()!=RESULT_CANCELED){
                //결과를 가지고 돌아온 택배기사(Intent) 소환
                Intent intent=result.getData();
                Uri uri =intent.getData();
                //이미지뷰에 uri 경로의 사진을 설정하기
                //iv.setImageUri(uri);  => 이미지 용량이 큰 경우 다운됨
                //이미지로드 라이브러리 사용권장 - Glide
                Glide.with(MainActivity.this).load(uri).into(iv);
            }
        }
    });
}