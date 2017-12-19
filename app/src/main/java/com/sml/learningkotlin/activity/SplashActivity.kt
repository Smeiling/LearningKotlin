package com.sml.learningkotlin.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.avos.avoscloud.AVUser
import com.orhanobut.logger.Logger
import com.sml.learningkotlin.R
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem

/**
 * Created by Smeiling on 2017/12/19.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    /**
     * 检测登录状态并跳转至相应Activity
     * 已登录：主页，未登录：登录页
     */
    private fun checkLoginStateAndRedirectTo() {
        if (AVUser.getCurrentUser() == null) {
            startActivity(Intent(SplashActivity@ this, LoginActivity::class.java))
        } else {
            startActivity(Intent(SplashActivity@ this, CardActivity::class.java))
        }
    }

    /**
     * Check for required permissions
     */
    private fun checkPermission() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage))
        permissionItems.add(PermissionItem(Manifest.permission.CAMERA, "相机", R.drawable.permission_ic_camera))
        HiPermission.create(this)
                .title(baseContext.getString(R.string.acquire_permission_title))
                .msg(baseContext.getString(R.string.acquire_permission_content))
                .permissions(permissionItems)
                .style(R.style.PermissionDefaultBlueStyle)
                .animStyle(R.style.PermissionAnimFade)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onFinish() {
                        checkLoginStateAndRedirectTo()
                    }

                    override fun onDeny(permission: String?, position: Int) {
                        Logger.i("permission_onDeny")
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                        Logger.i(permission + ", permission_onGuarantee")
                    }

                    override fun onClose() {
                        Logger.i("permission_onClose")
                    }

                })

    }

}