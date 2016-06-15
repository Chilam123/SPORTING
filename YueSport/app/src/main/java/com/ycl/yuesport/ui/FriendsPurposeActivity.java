package com.ycl.yuesport.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ycl.yuesport.R;
import com.ycl.yuesport.config.AppConfig;
import com.ycl.yuesport.utils.IntentConstant;
import com.ycl.yuesport.utils.NameLengthFilter;
import com.ycl.yuesport.utils.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FriendsPurposeActivity extends BaseActivity {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.edittext_description_friends_purpose)
    EditText edittextDescriptionFriendsPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_purpose_activity);
        ButterKnife.bind(this);

        initComponent();
        initToolBar();

        //设置光标在文本末尾
        edittextDescriptionFriendsPurpose.setSelection(edittextDescriptionFriendsPurpose.getText().length());

        //设置字符长度限制:设置交友意向最长为12位中文字符，24位英文/数字
        InputFilter[] filters = {new NameLengthFilter(24)};
        edittextDescriptionFriendsPurpose.setFilters(filters);
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        edittextDescriptionFriendsPurpose.setText(AppConfig.getInstance().getString("userFriendsPurpose", ""));
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        myToolbar.setTitle("交友意向");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //设置点击保存事件
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                saveHandle();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用inflate方法来把布局文件中的定义的菜单 加载给 第二个参数所对应的menu对象
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    /**
     * 点击保存处理事件
     */
    private void saveHandle() {
        String newFriendsPurpose = edittextDescriptionFriendsPurpose.getText().toString().trim();
        if (newFriendsPurpose.equals("")) {
            Toast.show("请描述一下您期待遇到的健身朋友吧");
            return;
        }
        //待处理...................................................................
        //请求网络修改用户名，返回成功继续执行

        //把新用户名写入sp文件里
        AppConfig.getInstance().putString("userFriendsPurpose", newFriendsPurpose);
        Toast.show("修改交友意向成功");

        //关闭此页面
        setResult(IntentConstant.CHANGE_FRIENDS_PURPOSE_SUCCESS);
        FriendsPurposeActivity.this.finish();
    }
}
