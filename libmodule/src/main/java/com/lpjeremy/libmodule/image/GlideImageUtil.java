package com.lpjeremy.libmodule.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;


/**
 * @desc:Glide工具类
 * @date:2018/5/5 15:44
 * @auther:lp
 * @version:1.1.6
 */

public class GlideImageUtil implements ImageApi {

    public static GlideImageUtil getInstance() {
        return GlideImageUtilHolder.mInstance;
    }

    private static class GlideImageUtilHolder {
        private static final GlideImageUtil mInstance = new GlideImageUtil();
    }

    /**
     * 默认图片资源
     */
    private static int placeholderResId;

    /**
     * 设置默认图片，使用之前最好设置一次
     *
     * @param placeholderResId
     * @return
     */
    public static GlideImageUtil setPlaceholderResId(int placeholderResId) {
        GlideImageUtil.placeholderResId = placeholderResId;
        return getInstance();
    }

    private static int getPlaceholderResId() {
        return placeholderResId >= 0 ? placeholderResId : 0;
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String imageUrl, int width) {
        Glide.with(context).asBitmap().load(imageUrl)
                .placeholder(getPlaceholderResId())
                .override(width, width)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String imageUrl, int width, int height) {
        Glide.with(context).asBitmap().load(imageUrl)
                .placeholder(getPlaceholderResId())
                .override(width, height)
                .into(imageView);

    }

    /**
     * 获取bitmap图片
     *
     * @param context
     * @param imageUrl
     */
    public Bitmap getBitmap(Context context, String imageUrl) {
        FutureTarget<Bitmap> futureTarget = Glide.with(context).asBitmap().load(imageUrl).submit(); //方法中设置asBitmap可以设置回调类型
        try {
            Bitmap bitmap = futureTarget.get();
            return bitmap;
        } catch (Exception e) {

        }
        return null;

    }

    @Override
    public void loadImage(Context context, ImageView imageView, String imageUrl) {
        try {
            Glide.with(context).asBitmap().load(imageUrl).placeholder(getPlaceholderResId()).into(imageView);
        } catch (IllegalArgumentException e) {
        }
    }


    @Override
    public void loadLocalImage(Context context, String imagePath, ImageView imageView) {
        try {
            Glide.with(context).asBitmap().load(new File(imagePath)).into(imageView);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void loadLocalImage(Context context, Uri uri, ImageView imageView) {
        try {
            Glide.with(context).asBitmap().load(uri).into(imageView);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void loadCircularImage(Context context, ImageView imageView, String imageUrl) {
        RequestOptions options = RequestOptions.circleCropTransform();
        //不做磁盘缓存
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
        options.skipMemoryCache(true);
        Glide.with(context).asBitmap().load(imageUrl).apply(options).into(imageView);
    }

    @Override
    public void loadRoundImage(Context context, ImageView imageView, String imageUrl, int roundingRadius) {
        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);//.override(300, 300);
        Glide.with(context).asBitmap().load(imageUrl).apply(options).into(imageView);
    }
}
