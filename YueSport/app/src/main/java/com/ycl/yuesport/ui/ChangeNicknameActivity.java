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

public class ChangeNicknameActivity extends BaseActivity {

    @Bind(R.id.my_toolbar)
    Toolbar myToolbar;
    @Bind(R.id.edittext_nickname_change_nickname)
    EditText edittextNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_nickname_activity);
        ButterKnife.bind(this);

        initComponent();
        initToolBar();

        //设置光标在文本末尾
        edittextNickname.setSelection(edittextNickname.getText().length());

        //设置字符长度限制:设置用户名最长为16位中文字符，32位英文/数字
        InputFilter[] filters = {new NameLengthFilter(32)};
        edittextNickname.setFilters(filters);
    }

    /**
     * 初始化组件
     */
    private void initComponent() {
        edittextNickname.setText(AppConfig.getInstance().getString("userNickname", ""));
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        myToolbar.setTitle("更改名字");
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
        String newNickname = edittextNickname.getText().toString().trim();
        if (newNickname.equals("")) {
            Toast.show("昵称不能为空哦");
            return;
        }
        //待处理...................................................................
        //请求网络修改用户名，返回成功继续执行

        //把新用户名写入sp文件里
        AppConfig.getInstance().putString("userNickname", newNickname);
        Toast.show("修改昵称成功");

        //关闭此页面
        setResult(IntentConstant.CHANGE_NICKNAME_SUCCESS);
        ChangeNicknameActivity.this.finish();
    }
}
