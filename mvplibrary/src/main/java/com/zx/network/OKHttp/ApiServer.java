package com.zx.network.OKHttp;

import com.squareup.okhttp.ResponseBody;
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
import com.zx.network.NetBean;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.QueryMap;
import retrofit.http.Streaming;
import retrofit.http.Url;
import rx.Observable;

/**
 * Name: ApiService
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2018-11-12 13:35
 */
public interface ApiServer {
    //点单机登陆
    public static final String MAIN_LOGIN_URL = "/reserve/reserve/login";
    //用户列表
    public static final String MEMBER_LIST_URL = "/reserve/reserve/MemberIndex";
    //用户统计
    public static final String MEMBER_MAIN_COUNT = "/reserve/reserve/MemberCount";
    //充值明细
    public static final String MEMBER_RECHARGE_LIST = "/reserve/reserve/MemberStorageIndex";
    //消费明细
    public static final String MEMBER_CONSUME_LIST = "/reserve/reserve/MemberExpensesIndex";
    //会员详情
    public static final String MEMBER_DETAIL = "/reserve/reserve/MemberDetails";
    //批量制卡 获取卡信息
    public static final String CARD_NUM = "/reserve/reserve/MemberEntityGetAll";
    //获取单卡信息
    public static final String CARD_NUM_SIGN = "/reserve/reserve/MemberEntityGet";
    //绑定卡
    public static final String ADD_CARD_NUM = "/reserve/reserve/MemberEntityCardDetails";
    //删除卡
    public static final String DELETE_CARD_NUM = "/reserve/reserve/MemberEntityCardDeles";
    //新增用户
    public static final String ADD_MEMBER = "/reserve/reserve/MemberAdd";
    //用户等级列表
    public static final String MEMBER_LEVELS = "/reserve/reserve/MemberGradeIndex";
    //赠送
    public static final String MEMBER_GIVES = "/reserve/reserve/MemberValueGiveIndex";
    //换卡
    public static final String CHANAGE_CARD = "/reserve/reserve/MemberTieUp";
    //退款
    public static final String REFUND = "/reserve/reserve/MemberRefund";
    //删除 会员
    public static final String DELETE_MEMBER = "/reserve/reserve/MemberDeles";
    //冻结
    public static final String FROEN_MEMBER = "/reserve/reserve/MemberFrozen";
    //储值明细
    public static final String RECHARGE = "/reserve/reserve/MemberStorage";
    //撤销 充值明细
    public static final String CANCELATION_RECHARGE = "/reserve/reserve/MemberStorageRevoke";
    //获取赠送 金额
    public static final String GIVE_MONEY = "/reserve/reserve/MoneyGive";
    // 根据卡号 手机号查询 用户信息
    public static final String GET_MEMBER_INFO = "/reserve/reserve/MemberDateinfo";
    //获取销售信息
    public static final String SELLER_LIST = "/reserve/reserve/MemberFramePostSale";
    // 更新会员信息
    public static final String UPDATE_MEMBER = "/reserve/reserve/MemberDetails";


    //点单机登录
    @POST(MAIN_LOGIN_URL)
    Observable<NetBean<User>> login(@QueryMap Map<String, String> params);

    @POST(MEMBER_LIST_URL)
    Observable<NetBean<ListData<Member>>> getMemberList(@QueryMap Map<String, String> stringStringMap);

    @POST(MEMBER_MAIN_COUNT)
    Observable<NetBean<Countbean>> getCountData(@QueryMap Map<String, String> stringStringMap);

    @POST(MEMBER_RECHARGE_LIST)
    Observable<NetBean<ReChargeListData>> getReChargeList(@QueryMap Map<String, String> stringStringMap);

    @POST(MEMBER_CONSUME_LIST)
    Observable<NetBean<ConsumeListData>> getConsumeList(@QueryMap Map<String, String> stringStringMap);

    @GET(MEMBER_DETAIL)
    Observable<NetBean<Member>> getMemberDetail(@QueryMap Map<String, String> stringStringMap);

    @POST(CARD_NUM)
    Observable<NetBean<List<Cardbean>>> getCardInfo(@QueryMap Map<String, String> stringStringMap);

    @POST(ADD_CARD_NUM)
    Observable<NetBean<String>> addCardNum(@QueryMap Map<String, String> stringStringMap);

    @POST(DELETE_CARD_NUM)
    Observable<NetBean<String>> deleteCardNum(@QueryMap Map<String, String> stringStringMap);

    @POST(ADD_MEMBER)
    Observable<NetBean<String>> addMember(@QueryMap Map<String, String> stringStringMap);

    @GET(MEMBER_LEVELS)
    Observable<NetBean<ListData<MemberLevel>>> getMemberLevels(@QueryMap Map<String, String> stringStringMap);

    @POST(MEMBER_GIVES)
    Observable<NetBean<ListData<MemberReChargeGive>>> getRechargeGives(@QueryMap Map<String, String> stringStringMap);

    @POST(CARD_NUM_SIGN)
    Observable<NetBean<Cardbean>> getCardMsg(@QueryMap Map<String, String> stringStringMap);

    @POST(CHANAGE_CARD)
    Observable<NetBean<String>> changeCard(@QueryMap Map<String, String> stringStringMap);

    @POST(REFUND)
    Observable<NetBean<String>> refund(@QueryMap Map<String, String> stringStringMap);

    @POST(DELETE_MEMBER)
    Observable<NetBean<String>> delete(@QueryMap Map<String, String> stringStringMap);

    @POST(FROEN_MEMBER)
    Observable<NetBean<String>> frozen(@QueryMap Map<String, String> stringStringMap);

    @POST(RECHARGE)
    Observable<NetBean<String>> recharge(@QueryMap Map<String, String> stringStringMap);
    //撤销充值
    @POST(CANCELATION_RECHARGE)
    Observable<NetBean<String>> cancelRecharge(@QueryMap Map<String, String> stringStringMap);
    //获取赠送金额
    @POST(GIVE_MONEY)
    Observable<NetBean<Double>> getGive(@QueryMap Map<String, String> stringStringMap);

    // 根据卡号 手机号查询 用户信息
    @POST(GET_MEMBER_INFO)
    Observable<NetBean<Member>> getMember(@QueryMap Map<String, String> stringStringMap);
    // 销售列表
    @POST(SELLER_LIST)
    Observable<NetBean<List<Sellerbean>>> getSellerList(@QueryMap Map<String, String> stringStringMap);
    //修改用户信息
    @POST(UPDATE_MEMBER)
    Observable<NetBean<String>> updateMember(@QueryMap Map<String,String> stringStringMap);
}
