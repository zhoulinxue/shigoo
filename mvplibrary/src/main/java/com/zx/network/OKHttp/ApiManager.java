package com.zx.network.OKHttp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.zx.datafactory.bean.Cardbean;
import com.zx.datafactory.bean.ConsumeListData;
import com.zx.datafactory.bean.Consumebean;
import com.zx.datafactory.bean.Countbean;
import com.zx.datafactory.bean.ListData;
import com.zx.datafactory.bean.Member;
import com.zx.datafactory.bean.MemberLevel;
import com.zx.datafactory.bean.MemberReChargeGive;
import com.zx.datafactory.bean.ReChargeListData;
import com.zx.datafactory.bean.ReChargebean;
import com.zx.datafactory.bean.Sellerbean;
import com.zx.datafactory.bean.User;
import com.zx.api.api.netWork.NetRequest;
import com.zx.api.api.netWork.NetRequestCallBack;
import com.zx.api.api.utils.AppLog;
import com.zx.network.Fileutil;
import com.zx.network.NetBean;
import com.zx.network.Param;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Name: ApiManager
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 13:30
 */
public class ApiManager {
    private static ApiManager mApiManager;
    private static ApiServer mApiService;
    private static Context mContext;
    private static long NET_TIMEOUT = 15;
    private static long NET_WRITE_TIME_OUT = 10;
    private static long NET_READ_TIMEOUT = 30;

    public ApiManager() {
    }

    public static ApiManager getInstance() {
        if (mApiManager == null) {
            mApiManager = new ApiManager();
        }
        return mApiManager;
    }

    public static ApiServer init(Context context, String baseURL) {
        mContext = context;
        if (mApiService == null) {
            /**日志拦截器*/
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            /**设置超时和拦截器*/
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(NET_TIMEOUT, TimeUnit.SECONDS);
            client.setWriteTimeout(NET_WRITE_TIME_OUT, TimeUnit.SECONDS);
            client.setReadTimeout(NET_READ_TIMEOUT, TimeUnit.SECONDS);
            try {
                client.interceptors().add(new CommonInterceptor());
            } catch (Exception e) {
                e.getMessage();
            }
            client.interceptors().add(logging);
            /**创建Retrofit实例*/
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            mApiService = retrofit.create(ApiServer.class);
        }
        return mApiService;
    }

    /**
     * @param params
     * @return
     */
    private Map<String, String> genrateMap(String... params) {
        Map<String, String> map = new HashMap<>();
        if (params.length >= 2) {
            if (params.length % 2 == 0) {
                for (int i = 0; i < params.length; i++) {
                    if (i % 2 == 0) {
                        map.put(params[i], params[i + 1]);
                    }
                }
            } else {
                AppLog.print("ApiManager" + "param ....key...value  error");
            }
        }
        return map;
    }

    /**
     * 登录
     *
     * @param testCallback
     * @param user
     * @return
     */
    public NetRequest cashLogin(NetRequestCallBack<User> testCallback, User user) {
        Observable<NetBean<User>> observable = mApiService.login(genrateMap(Param.Keys.USER_NUM, user.getUserName(), Param.Keys.USER_PSW, user.getPsw()));
        return new OkHttpRequest<User>(observable, testCallback);
    }

    /**
     * 会员列表
     *
     * @param params
     * @param mCashLoginCallback
     * @return
     */
    public NetRequest getMemberList(String[] params, NetRequestCallBack<ListData<Member>> mCashLoginCallback) {
        Observable<NetBean<ListData<Member>>> observable = mApiService.getMemberList(genrateMap(params));
        return new OkHttpRequest<ListData<Member>>(observable, mCashLoginCallback);
    }

    /**
     * 新增会员及会员总数
     *
     * @param params
     * @param countCallback
     * @return
     */
    public NetRequest getCountData(String[] params, NetRequestCallBack<Countbean> countCallback) {
        Observable<NetBean<Countbean>> observable = mApiService.getCountData(genrateMap(params));
        return new OkHttpRequest<Countbean>(observable, countCallback);
    }

    /**
     * 获取充值列表
     *
     * @param params
     * @param reChargeCallback
     * @return
     */
    public NetRequest getReChargeList(String[] params, NetRequestCallBack<ReChargeListData> reChargeCallback) {
        Observable<NetBean<ReChargeListData>> observable = mApiService.getReChargeList(genrateMap(params));
        return new OkHttpRequest<ReChargeListData>(observable, reChargeCallback);
    }

    /**
     * 回去消费 明细
     *
     * @param params
     * @param reChargeCallback
     * @return
     */

    public NetRequest getConsumeList(String[] params, NetRequestCallBack<ConsumeListData> reChargeCallback) {
        Observable<NetBean<ConsumeListData>> observable = mApiService.getConsumeList(genrateMap(params));
        return new OkHttpRequest<ConsumeListData>(observable, reChargeCallback);
    }

    public NetRequest getMemberDetail(String[] params, NetRequestCallBack<Member> cemberCallback) {
        Observable<NetBean<Member>> observable = mApiService.getMemberDetail(genrateMap(params));
        return new OkHttpRequest<Member>(observable, cemberCallback);
    }

    public NetRequest getCardInfo(String[] params, NetRequestCallBack<List<Cardbean>> cardCallback) {
        Observable<NetBean<List<Cardbean>>> observable = mApiService.getCardInfo(genrateMap(params));
        return new OkHttpRequest<List<Cardbean>>(observable, cardCallback);
    }

    public NetRequest addCardEntityNum(String[] params, NetRequestCallBack<String> cardCallback) {
        Observable<NetBean<String>> observable = mApiService.addCardNum(genrateMap(params));
        return new OkHttpRequest<String>(observable, cardCallback);
    }

    public NetRequest deleteCardEntityNum(String[] params, NetRequestCallBack<String> deleteCallback) {
        Observable<NetBean<String>> observable = mApiService.deleteCardNum(genrateMap(params));
        return new OkHttpRequest<String>(observable, deleteCallback);
    }

    public NetRequest addMember(String[] params, NetRequestCallBack<String> addmemberCallback) {
        Observable<NetBean<String>> observable = mApiService.addMember(genrateMap(params));
        return new OkHttpRequest<String>(observable, addmemberCallback);
    }

    public NetRequest getMemberLevelList(String[] params, NetRequestCallBack<ListData<MemberLevel>> levelsCallback) {
        Observable<NetBean<ListData<MemberLevel>>> observable = mApiService.getMemberLevels(genrateMap(params));
        return new OkHttpRequest<ListData<MemberLevel>>(observable, levelsCallback);
    }

    public NetRequest getReChargeGive(String[] params, NetRequestCallBack<ListData<MemberReChargeGive>> reChargeGivesCallback) {
        Observable<NetBean<ListData<MemberReChargeGive>>> observable = mApiService.getRechargeGives(genrateMap(params));
        return new OkHttpRequest<ListData<MemberReChargeGive>>(observable, reChargeGivesCallback);
    }

    public NetRequest getCardMsg(String[] params, NetRequestCallBack<Cardbean> cardCallback) {
        Observable<NetBean<Cardbean>> observable = mApiService.getCardMsg(genrateMap(params));
        return new OkHttpRequest<Cardbean>(observable, cardCallback);
    }

    public NetRequest exChanageCard(String[] params, NetRequestCallBack<String> chanageCardCallback) {
        Observable<NetBean<String>> observable = mApiService.changeCard(genrateMap(params));
        return new OkHttpRequest<String>(observable, chanageCardCallback);
    }

    public NetRequest refund(String[] params, NetRequestCallBack<String> refundCardCallback) {
        Observable<NetBean<String>> observable = mApiService.refund(genrateMap(params));
        return new OkHttpRequest<String>(observable, refundCardCallback);
    }

    public NetRequest deleteMember(String[] params, NetRequestCallBack<String> deleteCardCallback) {
        Observable<NetBean<String>> observable = mApiService.delete(genrateMap(params));
        return new OkHttpRequest<String>(observable, deleteCardCallback);
    }

    public NetRequest frozen(String[] params, NetRequestCallBack<String> deleteCardCallback) {
        Observable<NetBean<String>> observable = mApiService.frozen(genrateMap(params));
        return new OkHttpRequest<String>(observable, deleteCardCallback);
    }

    public NetRequest recharge(String[] params, NetRequestCallBack<String> reChargeCallback) {
        Observable<NetBean<String>> observable = mApiService.recharge(genrateMap(params));
        return new OkHttpRequest<String>(observable, reChargeCallback);
    }

    public NetRequest cancelRecharge(String[] params, NetRequestCallBack<String> cancelCallback) {
        Observable<NetBean<String>> observable = mApiService.cancelRecharge(genrateMap(params));
        return new OkHttpRequest<String>(observable, cancelCallback);
    }

    public NetRequest getGive(String[] params, NetRequestCallBack<Double> giveCallback) {
        Observable<NetBean<Double>> observable = mApiService.getGive(genrateMap(params));
        return new OkHttpRequest<Double>(observable, giveCallback);
    }

    /**
     * 根据卡号、手机号 查询用户信息
     * @param params
     * @param memberCallback
     * @return
     */
    public NetRequest getMemberMsgByCard(String[] params, NetRequestCallBack<Member> memberCallback) {
        Observable<NetBean<Member>> observable = mApiService.getMember(genrateMap(params));
        return new OkHttpRequest<Member>(observable, memberCallback);
    }

    /**
     * 获取销售列表
     * @param params
     * @param sellerListCallback
     * @return
     */
    public NetRequest getSellerList(String[] params, NetRequestCallBack<List<Sellerbean>> sellerListCallback) {
        Observable<NetBean<List<Sellerbean>>> observable = mApiService.getSellerList(genrateMap(params));
        return new OkHttpRequest<List<Sellerbean>>(observable, sellerListCallback);
    }

    public NetRequest updateMember(String[] params, NetRequestCallBack<String> updatemberCallback) {
        Observable<NetBean<String>> observable = mApiService.updateMember(genrateMap(params));
        return new OkHttpRequest<String>(observable, updatemberCallback);
    }
}
