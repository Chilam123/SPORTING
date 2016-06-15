package com.ycl.sportsing.ui;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ycl.sportsing.R;
import com.ycl.sportsing.app.AppContext;
import com.ycl.sportsing.common.NetUrlConstant;
import com.ycl.sportsing.domain.User;
import com.ycl.sportsing.utils.CircleImageView;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.OnResponseListener;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineActivity extends Activity implements View.OnClickListener {


    @Bind(R.id.profile_image)
    CircleImageView profileImage;
    private LinearLayout lL_mine, lL_mine2;// //账户
    private RelativeLayout rL_mine;// 我发布的活动
    private RelativeLayout rL_mine1;//我参与的活动
    private Button button_register;
    private TextView textview_username;
    private AppContext appContext;
    private User user;
    private final int REFRESHSPORT=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);

        init();

        appContext = (AppContext) getApplicationContext();
        user = appContext.getUser();
        if (user != null) {
            String username = user.getUsername();
            lL_mine.setVisibility(View.GONE);
            textview_username.setText(username);
            lL_mine2.setVisibility(View.VISIBLE);
        }


    }

    void init() {


        lL_mine = (LinearLayout) findViewById(R.id.lL_mine);
        rL_mine = (RelativeLayout) findViewById(R.id.rL_mine);
        rL_mine1 = (RelativeLayout) findViewById(R.id.rL_mine1);


        lL_mine2 = (LinearLayout) findViewById(R.id.lL_mine2);
        button_register = (Button) findViewById(R.id.button_register);
        textview_username = (TextView) findViewById(R.id.textview_username);


        lL_mine.setOnClickListener(this);
        rL_mine.setOnClickListener(this);
        rL_mine1.setOnClickListener(this);


        button_register.setOnClickListener(this);
        lL_mine2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_register:// 登录
                Intent intent1 = new Intent(MineActivity.this, LoginActivity.class);
                startActivityForResult(intent1, 12);

                break;
            case R.id.rL_mine:// 我的活动
                if (user != null) {
                    Intent intent2 = new Intent(MineActivity.this, MyPublishSportActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "亲，请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.rL_mine1:
                if (user != null) {
                    Intent intent3 = new Intent(MineActivity.this,
                            MyJoinSportActivity.class);
                    startActivity(intent3);
                } else {
                    Toast.makeText(this, "亲，请先登录", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12) {
            if (data != null) {
                String username = data.getStringExtra("username");
                String mytouxiang=data.getStringExtra("mytouxiang");
                if(mytouxiang!=null&&!mytouxiang.equals("")){

                }
                lL_mine.setVisibility(View.GONE);
                textview_username.setText(username);
                lL_mine2.setVisibility(View.VISIBLE);
                AppContext appContext=(AppContext) getApplicationContext();
                Request<Bitmap> request= NoHttp.createImageRequest(NetUrlConstant.GETPICURL, RequestMethod.GET);
                request.add("user_name",appContext.getUser().getUsername());
                request.add("pic",mytouxiang);

                Handler handler2=appContext.getHandler();
                if(handler2!=null)
                   handler2.sendEmptyMessage(REFRESHSPORT);

                appContext.getRequestQueue().add(1, request, new OnResponseListener<Bitmap>() {
                            @Override
                            public void onStart(int what) {

                            }

                            @Override
                            public void onSucceed(int what, Response<Bitmap> response) {
                                profileImage.setImageBitmap(response.get());
                            }

                            @Override
                            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                            }

                            @Override
                            public void onFinish(int what) {

                            }
                        });


                        profileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            }
                        });
            }
        } else if (requestCode == 11) {
            lL_mine2.setVisibility(View.GONE);
            lL_mine.setVisibility(View.VISIBLE);

        }else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
                InputStream is=getContentResolver().openInputStream(selectedImage);
                profileImage.setImageBitmap(BitmapFactory.decodeStream(is));
                String picPath=getPath(this,selectedImage);
                Log.v("TAG",picPath);
                AppContext appContext=(AppContext) getApplicationContext();
               Request<String> request= NoHttp.createStringRequest(NetUrlConstant.UPLOADPICURL, RequestMethod.POST);
                request.add("user_name",appContext.getUser().getUsername());
               request.add("file",new FileBinary(new File(picPath)));
                String test="touxiang";
                request.add("upload_type",test);


                appContext.getRequestQueue().add(0, request, new OnResponseListener<String>() {
                    @Override
                    public void onStart(int what) {

                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                           Log.v("TAG","成功了");
                    }

                    @Override
                    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                        Log.v("TAG","失败了");
                    }

                    @Override
                    public void onFinish(int what) {

                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        user = appContext.getUser();
    }



    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
