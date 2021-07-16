package cn.itcast.utils;


import cn.itcast.pojo.Member;

public class ThreadLocalUtils {

    private final static ThreadLocal<Member> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     *
     * @param user
     */
    public static void setUser(Member user) {
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     *
     * @return
     */
    public static Member getUser() {
        return userThreadLocal.get();
    }

    /**
     * 清空ThreadLocal数据
     */
    public static void remove() {
        userThreadLocal.remove();
    }
}