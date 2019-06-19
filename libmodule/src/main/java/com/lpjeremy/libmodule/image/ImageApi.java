package com.lpjeremy.libmodule.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;


/**
 * @desc:图片加载api定义
 * @date:2018/5/5 15:44
 * @auther:lp
 * @version:1.0
 */

public interface ImageApi {

    /**
     * 加载图片
     *
     * @param imageView
     * @param imageUrl
     * @param context
     */
    void loadImage(Context context, ImageView imageView, String imageUrl);

    /**
     * 加载图片 指定大小 宽高一样
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param width
     */
    void loadImage(Context context, ImageView imageView, String imageUrl, int width);

    /**
     * 加载图片 指定大小 宽高不一样
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param width
     */
    void loadImage(Context context, ImageView imageView, String imageUrl, int width, int height);

    /**
     * 加载本地图片
     *
     * @param context
     * @param imagePath
     * @param imageView
     */
    void loadLocalImage(Context context, String imagePath, ImageView imageView);

    /**
     * 加载uri图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    void loadLocalImage(Context context, Uri uri, ImageView imageView);

    /**
     * 获取图片
     *
     * @param context
     * @param imageUrl
     */
    Bitmap getBitmap(Context context, String imageUrl);

    /**
     * 加载圆角图片
     * @param context
     * @param imageView
     * @param imageUrl
     * @param roundingRadius 圆角幅度
     */
    void loadRoundImage(Context context, ImageView imageView, String imageUrl,int roundingRadius);

    /**
     * 加载圆形图片
     * @param context
     * @param imageView
     * @param imageUrl
     */
    void loadCircularImage(Context context, ImageView imageView, String imageUrl);
}
