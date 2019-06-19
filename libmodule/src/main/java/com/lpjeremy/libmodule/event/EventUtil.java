package com.lpjeremy.libmodule.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @desc:Event工具类
 * @date:2019/1/27 10:02
 * @auther:lp
 * @version:1.0
 */
public class EventUtil {
    /**
     * 静态内部类单例模式 start
     * 第一次加载EventUtil类时并不会初始化sInstance，
     * 只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化sInstance ，
     * 这样不仅能确保线程安全也能保证Singleton类的唯一性，所以推荐使用静态内部类单例模式。
     */
    private EventUtil() {
    }

    public static EventUtil getInstance() {
        return EventUtilHolder.sInstance;
    }

    private static class EventUtilHolder {
        private static final EventUtil sInstance = new EventUtil();
    }

    /**
     * 静态内部类单例模式 end
     * 第一次加载EventUtil类时并不会初始化sInstance，
     * 只有第一次调用getInstance方法时虚拟机加载SingletonHolder 并初始化sInstance ，
     * 这样不仅能确保线程安全也能保证Singleton类的唯一性，所以推荐使用静态内部类单例模式。
     */


    /**
     * 发送消息
     * @param event
     * @param <T>
     */
    public <T> void sendMsg(MessageEvent<T> event) {
        EventBus.getDefault().post(event);
    }
}

